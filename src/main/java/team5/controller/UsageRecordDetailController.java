package team5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import team5.model.Product;
import team5.model.ProductMapForm;
import team5.model.ProductMapFormWrapper;
import team5.model.UsageRecordDetail;
import team5.repo.ProductRepository;
import team5.service.ProductInterface;
import team5.service.ProductService;
import team5.service.ProductServiceImpl;
import team5.service.UsageRecordDetailService;
import team5.service.UsageRecordDetailServiceImpl;


 

@Controller
@RequestMapping("/detail")
public class UsageRecordDetailController {

	@Autowired
	ProductService pservice;
	
	@Autowired
	ProductRepository prepo;
	
	@Autowired
    private ProductInterface pService;
	
	@Autowired
	public void setProductService(ProductServiceImpl pimpl) {
		this.pservice = pimpl;
	}
	

    @Autowired
    UsageRecordDetailService urdservice;
    
    @Autowired
    public void setUsageRecordDetailService(UsageRecordDetailServiceImpl urdserviceImpl) {
        this.urdservice = urdserviceImpl;
    }
    

	@RequestMapping(value = "/add-part")
	public String addpart(Model model) {
		model.addAttribute("part",new UsageRecordDetail());
		ArrayList<String> partnum = pservice.FindAllPartNumber();
		model.addAttribute("partnum", partnum);
		return "stock-usage-detail-form";
	}
	
    @RequestMapping("/part-list")
    public String viewPartList(Model model, @Param("keyword") String keyword) {
        List<Product> listProducts = pService.listAllProducts(keyword);
        ArrayList<ProductMapForm> productMapFormL = new ArrayList<ProductMapForm>();
        for (Product x: listProducts) {
        	ProductMapForm temp = new ProductMapForm(x);
        	productMapFormL.add(temp);
        }
        ProductMapFormWrapper wrapper = new ProductMapFormWrapper();
        System.out.println(productMapFormL.get(0).getId());
        wrapper.setProductMapFormL(productMapFormL);
        model.addAttribute("productMapFormWrapper", wrapper);
        model.addAttribute("products", listProducts);
        model.addAttribute("keyword", keyword);
        
        return "part-list";
       
    }
    
    @RequestMapping(value = "/update-stock", method = RequestMethod.POST)
    public String updateStock(@ModelAttribute ProductMapFormWrapper productMapFormW, Model model) {
    	System.out.println("1"+productMapFormW.getProductMapFormL().get(0).getDescription());
    	System.out.println("2"+productMapFormW.getProductMapFormL().get(0).getId());
    	System.out.println("2"+productMapFormW.getProductMapFormL().get(0).getQuantityUsed());
    	//    	pService.updateStock(quantity, id);
    	return "stock-usage-list";
    }
    
//    @RequestMapping("/part-list")
//    public String viewPartList(Model model, @Param("keyword") String keyword) {
//        List<Product> listProducts = pService.listAllProducts(keyword);
//        model.addAttribute("products", listProducts);
//        model.addAttribute("keyword", keyword);
//        
//        Map<Long,Integer> map=new HashMap<Long,Integer>();
//        for(Product x:listProducts) {
//        	map.put(x.getId(),0);
//        }
//        model.addAttribute("map",map);
//        
//        MapForm mapForm = new MapForm();
//        Map<Long,Integer> properties =new HashMap<Long,Integer>();
//        mapForm.setProperties(properties);
//        model.addAttribute("mapForm", mapForm);
//        
//        return "part-list";
//       
//    }
    
//    @RequestMapping("/update-stock")
//    public String updateStock(Model model, @Param("id") Long id, @Param("quantity") Long quantity) {
//    	pService.updateStock(quantity, id);
//    	return "stock-usage-list";
//    }
	
//	@GetMapping("/part")
//    public String processFindForm(Product product, BindingResult result, Map<String, Object> model) {
//
//
//        // find owners by last name
//        Collection<Product> results = this.prepo.findByPartNumber(product.getPartNumber());
//        if (results.isEmpty()) {
//            // no owners found
//            result.rejectValue("part number", "notFound", "not found");
//            return "forward:/detail/stock-usage-detail-form";
//        }
//        else if (results.size() == 1) {
//            // 1 owner found
//            product = results.iterator().next();
//            return "redirect:/owners/" + product.getId();
//        }
//        else {
//            // multiple owners found
//            model.put("selections", results);
//            return "owners/ownersList";
//        }
//    }
// 
//	@GetMapping("/owners/find")
//    public String initFindForm(Map<String, Object> model) {
//        model.put("owner", new Owner());
//        return "owners/findOwners";
//    }

}