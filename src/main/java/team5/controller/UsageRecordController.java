package team5.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import team5.model.RoleType;
import team5.model.UsageRecord;
import team5.model.User;
import team5.repo.ProductRepo;
import team5.service.SessionService;
import team5.service.UsageRecordService;
import team5.service.UsageRecordServiceImpl;

@Controller
@RequestMapping("/usage")
public class UsageRecordController {
	
	@Autowired
	UsageRecordService uservice;
	
	@Autowired
	private ProductRepo prepo;
	
	@Autowired
	private SessionService ssvc;
	

	@Autowired
	public void setUsageRecordService(UsageRecordServiceImpl uimpl) {
		this.uservice = uimpl;
	}
	
	@RequestMapping(value = "/add")
	public String addform(Model model,HttpSession session) {
		ssvc.redirectIfNotLoggedIn(session);
		
		model.addAttribute("usage",new UsageRecord());
		return "stock-usage-form";
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model,HttpSession session) {
		ssvc.redirectIfNotLoggedIn(session);
		
		model.addAttribute("usage", uservice.listUsageRecord());
		model.addAttribute("product",prepo.findAll());
		return "stock-usage-list";
	}
	
	@RequestMapping(value = "/save")
    public String saveSupplier(@ModelAttribute("usage") @Valid UsageRecord usagerecord,
            BindingResult bindingResult, Model model,HttpSession session) {
		ssvc.redirectIfNotLoggedIn(session);
		
		if(bindingResult.hasErrors()) {
            return "stock-usage-form";
        }
		usagerecord.setUserName(user);
        uservice.addUsage(usagerecord);
        return "stock-usage-list";
    }
	

}
