package team5.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.Supplier;
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
	public String list(Model model) {
		model.addAttribute("suppliers", supservice.findAllSuppliers());
		return "suppliers";
	}
	
	@RequestMapping(value = "/add")
	public String addForm(Model model) {
		model.addAttribute("suppliers", new Supplier());
		return "supplierform";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("suppliers", supservice.findSupplierById(id));
		return "supplierform";
	}
	
	@RequestMapping(value = "/save")
	public String saveSupplier(@ModelAttribute("supplier") @Valid Supplier supplier,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "supplierform";
		}
		supservice.saveSupplier(supplier);
		return "forward:/supplier/list";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteSupplier(@PathVariable("id") Long id) {
		supservice.deleteSupplier(supservice.findSupplierById(id));
		return "forward:/supplier/list";
	}
	
	
	
	
	
	
}
