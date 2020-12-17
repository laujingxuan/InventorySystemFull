package team5.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.RoleType;
import team5.model.Supplier;
import team5.model.User;
import team5.service.SupplierService;
import team5.service.SupplierServiceImpl;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supservice;
	
	@Autowired
	public void setSupplierService(SupplierServiceImpl supServiceImpl) {
		this.supservice = supServiceImpl;
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		else if(user.getRole()==RoleType.ADMIN)
			model.addAttribute("suppliers", supservice.findAll());
			return "suppliers";
			
	}
	
	@RequestMapping(value = "/add")
	public String addForm(Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		else if(user.getRole()==RoleType.ADMIN)
			model.addAttribute("suppliers", new Supplier());
			return "supplierform";
		
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		else if(user.getRole()==RoleType.ADMIN)
			model.addAttribute("suppliers", supservice.findById(id));
			return "supplierform";

	}
	
	@RequestMapping(value = "/save")
	public String saveSupplier(@ModelAttribute("supplier") @Valid Supplier supplier,
			BindingResult bindingResult, Model model,HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		
		if(bindingResult.hasErrors()) {
			return "supplierform";
		}
		
		if(user.getRole()==RoleType.ADMIN)
			supservice.save(supplier);
			return "forward:/supplier/list";

	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteSupplier(@PathVariable("id") Long id,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}else if(user.getRole()==RoleType.ADMIN)
			supservice.delete(supservice.findById(id));
			return "forward:/supplier/list";
	}
}
