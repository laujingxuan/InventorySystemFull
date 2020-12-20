package team5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import team5.model.Product;
import team5.model.ProductMapForm;
import team5.model.ProductMapFormWrapper;
import team5.model.StockTransaction;
import team5.service.EmailService;
import team5.service.ProductService;
import team5.service.ProductServiceImpl;
import team5.service.SessionService;
import team5.service.SessionServiceImpl;
import team5.service.StockTransactionService;
import team5.service.UsageRecordService;
import team5.service.UsageRecordServiceImpl;

@Controller
@RequestMapping("/stock")
public class StockTransactionController {
	
	@Autowired
	private ProductService product_svc;
	
    @Autowired
    UsageRecordService ur_svc;
    
    @Autowired
    StockTransactionService st_svc;
    
    @Autowired
	private SessionService session_svc;
    
    @Autowired 
    public void setImplementation(ProductServiceImpl product_svcimpl, UsageRecordServiceImpl ur_svcimpl, StockTransactionService st_svcimpl, SessionServiceImpl session_svcimpl){
    	this.product_svc = product_svcimpl;
    	this.ur_svc = ur_svcimpl;
    	this.st_svc = st_svcimpl;
    	this.session_svc = session_svcimpl;
    }
    
	@Autowired
	private EmailService emailService;
	
	/*------------------------------------Create------------------------------------*/
	
	// create stock transaction entry to increase quantity
	// for admin when stock arrives 
	@GetMapping("/add")
	public String addStock(Model model, @Param("keyword") String keyword, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		/*
		}else if(user.getRole()==RoleType.ADMIN){
			keyword = null;*/

		model.addAttribute("products", product_svc.findAll());
		model.addAttribute("keyword", keyword);
		model.addAttribute("product", new Product());			

		return "stockEntryForm";
	}
	
	@GetMapping("/save")
	public String saveStockEntry(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		if (result.hasErrors()) return "stockEntryForm";
		
		Product p = product_svc.findById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		product_svc.save(p);
		return "forward:/product/listproducts";
	}
	
	/*--------------------------------Read/Retrieve------------------------------------*/
	
	// find all StockTransaction records, both restock(+) and withdrawals(-)
	// done with the findAll() method built into JPARepository 
	@GetMapping("/list")
	public String showAllTransactions(Model model) {

		model.addAttribute("stranxs", st_svc.findAll());
		return "stockTranxHistory";
	}
	
	@GetMapping("/report")
	public String usageReport(Model model) {
		List<Product> products = product_svc.findAll();
		model.addAttribute("products", products);
		return "usageReport";
	}
	
	// filter by productId and date range 
	// done with the custom method defined in StockTransactionRepo extended from JPARepository 
	@PostMapping("/report")
	public String usageReport(Model model, @RequestParam("startDate") String startD, @RequestParam("endDate") String endD, @RequestParam("productSelected") long id) throws ParseException {
		if (endD == "" || startD == "") {
			return "redirect:/stock/report";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = formatter.parse(startD);
		Date endDate = formatter.parse(endD);
		Product product = product_svc.findById(id);
		List<StockTransaction> fullUsageList = product.getStockTranxList();
		List<StockTransaction> usageList = new ArrayList<StockTransaction>();
		for(StockTransaction x : fullUsageList) {
			if(!x.getUsageRecord().getDate().after(endDate) && !x.getUsageRecord().getDate().before(startDate)) {
				usageList.add(x);
			}
		}

		model.addAttribute("product",product);
		model.addAttribute("usageList", usageList);
		model.addAttribute("fromDate", startD);
		model.addAttribute("ToDate", endD);
		return "usageReportDetails"; // "stockTranxHistory";
	}
	

	// filter by UsageRecordId
	// done with the custom method defined in StockTransactionRepo extended from JpaRepository 
	// the page shows the details of the UsageRecord and a list of StockTransaction records 
	// quantity change is implied to be negative (withdraw from inventory)
	// can change 
    @RequestMapping(value = "/part-list/{id}")
    public String viewPartList(Model model, @Param("keyword") String keyword , @PathVariable("id") Long id,HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		
        List<Product> listProducts = product_svc.findAll();
        ArrayList<ProductMapForm> productMapFormL = new ArrayList<ProductMapForm>();
        for (Product x: listProducts) {
        	ProductMapForm temp = new ProductMapForm(x);
        	productMapFormL.add(temp);
        }
        ProductMapFormWrapper wrapper = new ProductMapFormWrapper();        
        wrapper.setProductMapFormL(productMapFormL);
        model.addAttribute("productMapFormWrapper", wrapper);
        model.addAttribute("products", listProducts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("usagerecordid",id);
        
        return "part-list";
    }
    
    
    @RequestMapping(value = "/update-stock", method = RequestMethod.POST)
    public String updateStock(@ModelAttribute ProductMapFormWrapper productMapFormW, Model model
                             ,@RequestParam("usageRecordId") Long id, HttpSession session) {
            
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";

		System.out.println("Used Quantity");
		for (int i = 0; i < productMapFormW.getProductMapFormL().size(); i++) {

			Product p = product_svc.findById(productMapFormW.getProductMapFormL().get(i).getId());
			if (p.getUnit() > productMapFormW.getProductMapFormL().get(i).getQuantityUsed()) {
				p.setUnit(p.getUnit() - productMapFormW.getProductMapFormL().get(i).getQuantityUsed());
				product_svc.save(p);
				if (p.getUnit() < p.getMinReoderLevel()) {
					emailService.sendMail("eaintchitthae94@gmail.com", "Remainder for product",
							"Product (" + p.getName() + ") is lower than the minimun stock level");
				}
			}
		}
		ArrayList<StockTransaction> urdList = new ArrayList<StockTransaction>();
		StockTransaction urd;
		for (int x = 0; x < productMapFormW.getProductMapFormL().size(); x++) {
			//pService.updateStock(productMapFormW.getProductMapFormL().get(x).getQuantityUsed(),
					//productMapFormW.getProductMapFormL().get(x).getId());

			if ((productMapFormW.getProductMapFormL().get(x).getQuantityUsed()) != 0) {
				urd = new StockTransaction(
						product_svc.findById(productMapFormW.getProductMapFormL().get(x).getId()),
						ur_svc.findById(id), productMapFormW.getProductMapFormL().get(x).getQuantityUsed());
				urdList.add(urd);
				st_svc.save(urd);
			}

		}

		model.addAttribute("usage", urdList);
		model.addAttribute("usagerecordid", id);

		return "updatestock";
	}
	
	
	/*------------------------------------Update------------------------------------*/
	
    // won't be used in this project. listed and defined for clarity for developers 
    // for 1 StockTransaction record 
	/*@GetMapping("/updateProduct")
	public String updateStock0(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		if (result.hasErrors()) return "stockEntryForm";
		f
		Product p = product_svc.findById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		product_svc.save(p);
		return "forward:/product/listproducts";
	}*/
	
	// for admin and mechanic when changing quantity of product used in 1 UsageRecord
    // for multiple StockTransaction record 
	@GetMapping("/updateProduct")
	public String updateStock(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		if (result.hasErrors()) return "stockEntryForm";
		
		Product p = product_svc.findById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		product_svc.save(p);
		return "forward:/product/listproducts";
	}
	
	/*
	@RequestMapping(value = "/add-part")
	public String addpart(Model model,HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		
		model.addAttribute("part",new UsageRecordDetail());
		ArrayList<String> partnum = product_svc.FindAllPartNumber();
		model.addAttribute("partnum", partnum);
		return "stock-usage-detail-form";
	}*/	
}

//public String updateStock(@ModelAttribute ProductMapFormWrapper productMapFormW @Valid @RequestBody Product product, Model model) {

/*
 * @RequestMapping(value = "/update-stock", method = RequestMethod.POST) public
 * String updateStock(@ModelAttribute ProductMapFormWrapper productMapFormW,
 * Model model) {
 * System.out.println("1"+productMapFormW.getProductMapFormL().get(0).
 * getDescription());
 * System.out.println("2"+productMapFormW.getProductMapFormL().get(0).getId());
 * System.out.println("2"+productMapFormW.getProductMapFormL().get(0).
 * getQuantityUsed()); // pService.updateStock(quantity, id); return
 * "stock-usage-list"; }
 */

//@RequestMapping("/part-list")
//public String viewPartList(Model model, @Param("keyword") String keyword) {
//    List<Product> listProducts = pService.listAllProducts(keyword);
//    model.addAttribute("products", listProducts);
//    model.addAttribute("keyword", keyword);
//    
//    Map<Long,Integer> map=new HashMap<Long,Integer>();
//    for(Product x:listProducts) {
//    	map.put(x.getId(),0);
//    }
//    model.addAttribute("map",map);
//    
//    MapForm mapForm = new MapForm();
//    Map<Long,Integer> properties =new HashMap<Long,Integer>();
//    mapForm.setProperties(properties);
//    model.addAttribute("mapForm", mapForm);
//    
//    return "part-list";
//   
//}

//@RequestMapping("/update-stock")
//public String updateStock(Model model, @Param("id") Long id, @Param("quantity") Long quantity) {
//	pService.updateStock(quantity, id);
//	return "stock-usage-list";
//}

//@GetMapping("/part")
//public String processFindForm(Product product, BindingResult result, Map<String, Object> model) {
//
//
//    // find owners by last name
//    Collection<Product> results = this.prepo.findByPartNumber(product.getPartNumber());
//    if (results.isEmpty()) {
//        // no owners found
//        result.rejectValue("part number", "notFound", "not found");
//        return "forward:/detail/stock-usage-detail-form";
//    }
//    else if (results.size() == 1) {
//        // 1 owner found
//        product = results.iterator().next();
//        return "redirect:/owners/" + product.getId();
//    }
//    else {
//        // multiple owners found
//        model.put("selections", results);
//        return "owners/ownersList";
//    }
//}
//
//@GetMapping("/owners/find")
//public String initFindForm(Map<String, Object> model) {
//    model.put("owner", new Owner());
//    return "owners/findOwners";
//}






