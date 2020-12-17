package team5.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.tools.javac.code.Flags.Flag;

import team5.model.Product;
import team5.repo.ProductRepository;
import team5.service.ProductInterface;
import team5.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
//	@Autowired
//	ProductRepository productRepo;
//	
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    ProductService productService;

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
		Product p = new Product();
		model.addAttribute("products", listProducts);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("product", p);
		return "stockEntryForm";
	}
	
	@GetMapping("/add")
	public String showProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "productform";
	}
	
	@GetMapping("/updateProduct")
	public String updateStock(@ModelAttribute("product") @Valid @RequestBody Product product, BindingResult result, Model model) {
		Product p = pService.findProductById(product.getId());
		p.setUnit(product.getUnit() + p.getUnit());
		pService.updateProduct(p);
		return "forward:/product/listproducts";
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
	public String listProductForm(Model model, @RequestParam(value = "page", defaultValue = "0") Integer page,@Param("keyword") String keyword,
								@RequestParam(value ="size", defaultValue = "3") Integer size) {
		Page<Product> list = productService.findBookNoCriteria(page, size);
		List<Product> listProducts = pService.listAllProducts(keyword);
		model.addAttribute("products", list);
        model.addAttribute("pageCount",list.getTotalPages()-1);
	    model.addAttribute("keyword", keyword);
		return "products";
	}
	
    @RequestMapping("/findBookNoQuery")
    public String findBookNoQuery(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,@Param("keyword") String keyword,
                                  @RequestParam(value ="size", defaultValue = "3") Integer size){
        Page<Product> list = productService.findBookNoCriteria(page, size);
        model.addAttribute("products", list);
        model.addAttribute("pageCount",list.getTotalPages()-1);
	    model.addAttribute("keyword", keyword);

        return "products";
    }
}
