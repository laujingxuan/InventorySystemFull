package team5.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductRepository productRepo;
	
	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		
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
		productRepo.save(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("product", productRepo.findById(id).get());
		return "productform";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Long id) {
		Product product = productRepo.findById(id).get();
		productRepo.delete(product);
		return "forward:/product/listproducts";
	}
	
	@GetMapping("/listproducts")
	public String listProductForm(Model model) {
		model.addAttribute("products", productRepo.findAll());
		return "products";
	}
}
