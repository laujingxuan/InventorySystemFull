package team5.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.UsageRecord;
import team5.model.User;
import team5.service.ProductService;
import team5.service.ProductServiceImpl;
import team5.service.SessionService;
import team5.service.SessionServiceImpl;
import team5.service.UsageRecordService;
import team5.service.UsageRecordServiceImpl;

@Controller
@RequestMapping("/usage")
public class UsageRecordController {
	
	@Autowired
	UsageRecordService ur_svc;
	
	@Autowired
	private ProductService product_svc;
	
	@Autowired
	private SessionService session_svc;
	

	@Autowired 
	public void setImplimentation(UsageRecordServiceImpl ur_svcimpl, ProductServiceImpl product_svcimpl, SessionServiceImpl session_svcimpl) {
		this.ur_svc = ur_svcimpl;
		this.product_svc = product_svcimpl;
		this.session_svc = session_svcimpl;
	}
	
	@RequestMapping(value = "/add")
	public String addform(Model model,HttpSession session) {
		session_svc.redirectIfNotLoggedIn(session);
		
		model.addAttribute("usage",new UsageRecord());
		return "stock-usage-form";
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model,HttpSession session) {
		session_svc.redirectIfNotLoggedIn(session);
		
		model.addAttribute("usage", ur_svc.findAll());
		model.addAttribute("product",product_svc.findAll());
		return "stock-usage-list";
	}
	
	@RequestMapping(value = "/save")
    public String saveSupplier(@ModelAttribute("usage") @Valid UsageRecord usagerecord,
            BindingResult bindingResult, Model model,HttpSession session) {
		session_svc.redirectIfNotLoggedIn(session);
		
		if(bindingResult.hasErrors()) {
            return "stock-usage-form";
        }
		
		User user = (User) session.getAttribute("user");
		usagerecord.setUserName(user);
		ur_svc.save(usagerecord);
        return "stock-usage-list";
    }
	

}
