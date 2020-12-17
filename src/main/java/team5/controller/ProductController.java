package team5.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import team5.model.Product;
import team5.model.RoleType;
import team5.model.User;
import team5.service.ProductService;
import team5.service.SessionService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
//	@Autowired
//	ProductRepository productRepo;
//	
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private SessionService session_svcimpl;
	
	@Autowired
	public void setpService(ProductService pService) {
		this.pService = pService;
	}

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		
	}
	
	@GetMapping("/stock")
	public ModelAndView showStockEntryForm(Model model, @Param("keyword") String keyword, HttpSession session) {
		session_svcimpl.redirectIfNotLoggedIn(session);
		session_svcimpl.ensureUserHasPermission(session);
		
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			keyword = null;
			List<Product> listProducts = pService.findAll();
			Product p = new Product();
			mv.setViewName("stockEntryForm");
			mv.addObject("products", listProducts);
			mv.addObject("keyword", keyword);
			mv.addObject("product", p);			
		}else {
			mv.addObject("errorMessage","You have no access to this page");
			mv.setViewName("error");
		}
		return mv;
	}
	
	@GetMapping("/add")
	public String showProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "productform";
	}
	
	@GetMapping("/updateProduct")
	public String updateStock(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		Product p = pService.findById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		if(result.hasErrors()) {
			return "stockEntryForm";
		}
		pService.save(p);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/save")
	public String saveProductForm(@ModelAttribute("product") @Valid Product product,BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			return "productform";
		}
		pService.save(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("product", pService.findById(id));
		return "productform";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Long id) {
		Product product = pService.findById(id);
		pService.delete(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/listproducts")
	public String listProductForm(Model model, @Param("keyword") String keyword, HttpSession session) {
		session_svcimpl.redirectIfNotLoggedIn(session);
		session_svcimpl.ensureUserHasPermission(session);
		
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			model.addAttribute("role", true);
		}else {
			model.addAttribute("role", false);
		}
		System.out.println(RoleType.ADMIN);
		System.out.println(user.getRole());
		List<Product> listProducts = pService.findAll();
		model.addAttribute("products", listProducts);
	    model.addAttribute("keyword", keyword);
		return "products";
	}
}
