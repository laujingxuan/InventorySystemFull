package team5.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.UsageRecord;
import team5.model.UsageRecordDetail;
import team5.repo.ProductRepository;
import team5.service.ProductService;
import team5.service.ProductServiceImpl;
import team5.service.UsageRecordService;
import team5.service.UsageRecordServiceImpl;

@Controller
@RequestMapping("/usage")
public class UsageRecordController {
	
	@Autowired
	UsageRecordService uservice;
	
	@Autowired
	private ProductRepository prepo;
	

	@Autowired
	public void setUsageRecordService(UsageRecordServiceImpl uimpl) {
		this.uservice = uimpl;
	}
	
	@RequestMapping(value = "/add")
	public String addform(Model model) {
		model.addAttribute("usage",new UsageRecord());

		return "stock-usage-form";
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("usage", uservice.listUsageRecord());
		model.addAttribute("product",prepo.findAll());
		return "stock-usage-list";
	}
	
	@RequestMapping(value = "/save")
    public String saveSupplier(@ModelAttribute("usage") @Valid UsageRecord usagerecord,
            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "stock-usage-form";
        }
        uservice.addUsage(usagerecord);
        return "forward:/usage/list";
    }
	

}