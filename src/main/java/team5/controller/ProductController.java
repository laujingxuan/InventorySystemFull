package team5.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.Product;
import team5.repo.ProductRepository;
import team5.service.ProductInterface;

@Controller
@RequestMapping("/product")
public class ProductController {
	
//	@Autowired
//	ProductRepository productRepo;
//	
	
	@Autowired
	private ProductInterface pService;
	
	@Autowired
	public void setpService(ProductInterface pService) {
		this.pService = pService;
	}

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		
	}
	
	@GetMapping("/stock")
	public String showStockEntryForm(Model model, @Param("keyword") String keyword) {
		keyword = null;
		List<Product> listProducts = pService.listAllProducts(keyword);
		model.addAttribute("products", listProducts);
	    model.addAttribute("keyword", keyword);
		return "stockEntryForm";
	}
	
	@GetMapping("/add")
	public String showProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "productform";
	}
	
	@GetMapping("/save")
	public String saveProductForm(@ModelAttribute("product") @Valid Product product,BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			return "productform";
		}
		pService.createProduct(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("product", pService.findProductById(id));
		return "productform";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Long id) {
		Product product = pService.findProductById(id);
		pService.deleteProduct(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/listproducts")
	public String listProductForm(Model model, @Param("keyword") String keyword) {
		
		List<Product> listProducts = pService.listAllProducts(keyword);
		model.addAttribute("products", listProducts);
	    model.addAttribute("keyword", keyword);
		return "products";
	}
}
