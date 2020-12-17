package team5.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.Product;
import team5.service.ProductService;
import team5.service.SessionService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService product_svc;
	
	@Autowired
	private SessionService session_svcimpl;
	
	@Autowired
	public void setpService(ProductService pService) {
		this.product_svc = pService;
	}

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		
	}
	
	@GetMapping("/stock")
	public String showStockEntryForm(Model model, @Param("keyword") String keyword, HttpSession session) {
		session_svcimpl.redirectIfNotLoggedIn(session);
		session_svcimpl.redirectIfNoPermission(session);
		/*
		}else if(user.getRole()==RoleType.ADMIN){
			keyword = null;*/

		model.addAttribute("products", product_svc.findAll());
		model.addAttribute("keyword", keyword);
		model.addAttribute("product", new Product());			

		return "stockEntryForm";
	}
	
	@GetMapping("/add")
	public String showProductForm(Model model) {
		
		model.addAttribute("product", new Product());
		return "productform";
	}
	
	@GetMapping("/updateProduct")
	public String updateStock(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		Product p = product_svc.findById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		if(result.hasErrors()) {
			return "stockEntryForm";
		}
		product_svc.save(p);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/save")
	public String saveProductForm(@ModelAttribute("product") @Valid Product product,BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			return "productform";
		}
		product_svc.save(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("product", product_svc.findById(id));
		return "productform";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Long id) {
		Product product = product_svc.findById(id);
		product_svc.delete(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/listproducts")
	public String listProductForm(Model model, @Param("keyword") String keyword, HttpSession session) {
		session_svcimpl.redirectIfNotLoggedIn(session);
		session_svcimpl.redirectIfNoPermission(session);
		
		/*
		}else if(user.getRole()==RoleType.ADMIN){
			model.addAttribute("role", true);
		}else {
			model.addAttribute("role", falses);
		}*/

		model.addAttribute("products",  product_svc.findAll());
	    model.addAttribute("keyword", keyword);
		return "products";
	}
}
