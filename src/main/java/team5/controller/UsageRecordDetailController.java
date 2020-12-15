package team5.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.Product;
import team5.model.UsageRecordDetail;
import team5.repo.ProductRepository;
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
	
	
    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Product> listProducts = pservice.listAll(keyword);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("keyword", keyword);
         
        return "index";
    }
	
	
	
	
	
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