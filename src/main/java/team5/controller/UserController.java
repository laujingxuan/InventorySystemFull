package team5.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import team5.model.User;
import team5.service.UserImplementation;
import team5.service.UserInterface;


@Controller
public class UserController {

	@Autowired 
	UserInterface uservice;
	
	@Autowired
	public void setUserImplementation(UserImplementation uimpl) {
		this.uservice = uimpl;
	}
	
	@RequestMapping(path = "/login")
	public String login(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {
		if(uservice.authenticate(user)) 
		{
			User u = uservice.findByName(user.getUserName());
			session.setAttribute("usession", u);
			return "welcome";
		}
		else
			return "login";
	}
	
	
}