package team5.controller;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import team5.model.RoleType;
import team5.model.User;
import team5.nonEntityModel.UserForm;
import team5.service.UserService;
import team5.validator.UserFormValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService usvc;
	
	@Autowired
	private UserFormValidator userFormValidator;
	
	@InitBinder("userForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(userFormValidator);
	}
	
	//only admin can retrieve the list
	@GetMapping("/users")
	public ModelAndView viewUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			mv.setViewName("UserList");
		}else {
			mv.addObject("errorMessage","You have no access to this page");
			mv.setViewName("error");
		}
		return mv;
	}
	
	//only admin can add
	@GetMapping("/add")
	public ModelAndView addUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			mv.setViewName("editUser");
			mv.addObject("roleType", RoleType.values());
			UserForm userForm = new UserForm();
			mv.addObject("userForm", userForm);
			mv.addObject("path","/user/validate");
		}else {
			mv.addObject("errorMessage","You have no access to this page");
			mv.setViewName("error");
		}
		return mv;
	}
	
	@PostMapping("/validate")
	public ModelAndView addUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("roleType", RoleType.values());
		mv.addObject("path","/user/validate");
		if (bindingResult.hasErrors()) {
			mv.setViewName("editUser");
			return mv;
		}
		User user = new User(userForm);
		//if (userRepo.findByUserName(user.getUserName())== null) {}
		boolean success = usvc.save(user);
		if (success == false) {
			mv.setViewName("error");
			mv.addObject("errorMessage","Adding user fail");
		} else {
			mv.setViewName("redirect:/");
		}
		return mv;
	}
		
	
	//only admin can edit
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView editUser(@PathVariable("id") Integer id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			User toChange = usvc.findById(id);
			mv.addObject("roleType", RoleType.values());
			UserForm userForm = new UserForm(toChange);
			mv.addObject("userForm", userForm);
			mv.addObject("path","/user/save");
			mv.setViewName("editUser");
		}else {
			mv.addObject("errorMessage","You have no access to this page");
			mv.setViewName("error");
		}
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView editUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("roleType", RoleType.values());
		mv.addObject("path","/user/save");
		if (bindingResult.hasErrors()) {
			mv.setViewName("editUser");
			return mv;
		}
		User user = new User(userForm);
		long id = user.getId();
		boolean success = usvc.updateUser(user);
		if (success == true) {
			mv.setViewName("redirect:/user/users");
			return mv;
		} else {
			mv.setViewName("error");
			return mv;
		}
	}
	
	//everyone can change password
	@GetMapping("/update")
	public ModelAndView updateUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
			return mv;
		} else {
			mv.setViewName("editUser");
			mv.addObject("roleType", RoleType.values());
			UserForm userForm = new UserForm(user);
			mv.addObject("userForm", userForm);
			mv.addObject("path","/user/update");
			return mv;
		}
	}

	@PostMapping("/update")
	public ModelAndView updateUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("roleType", RoleType.values());
		mv.addObject("path","/user/update");
		if (bindingResult.hasErrors()) {
			mv.setViewName("editUser");
			return mv;
		}
		User user = new User(userForm);
		boolean success = usvc.updateUser(user);
		if (success == true) {
			session.setAttribute("user", user);
			mv.setViewName("redirect:/");
			return mv;
		} else {
			mv.setViewName("error");
			mv.addObject("errorMessage","Update password fail");
			return mv;
		}
	}
	
	//only admin can delete
	/*
	@PostMapping("/delete")
	public ModelAndView deleteUser(@RequestParam(value = "deleteUser", required = false) String[] deleteUsers, HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN && deleteUsers != null){
			userInterface.deleteUsers(deleteUsers);
			mv.setViewName("redirect:/user/users");
		}else {
			mv.setViewName("redirect:/user/users");
		}
		return mv;
	}*/
}
