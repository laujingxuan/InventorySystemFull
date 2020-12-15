package team5.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import team5.model.RoleType;
import team5.model.User;
import team5.service.UserInterface;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserInterface userInterface;
	
	@RequestMapping(path = "/login")
	public String login(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "login";
	}
	
	@RequestMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session) {
		if(userInterface.authenticate(user)) 
		{
			User u = userInterface.findByUsername(user.getUserName());
			session.setAttribute("user", u);
			return "welcome";
		}
		else
			return "login";
	}

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
	
	@GetMapping("/add")
	public ModelAndView addUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
		}else if(user.getRole()==RoleType.ADMIN){
			mv.setViewName("AddUser");
			mv.addObject("user", new User());
			mv.addObject("roleType", RoleType.values());
			mv.setViewName("AddUser");
		}else {
			mv.addObject("errorMessage","You have no access to this page");
			mv.setViewName("error");
		}
		return mv;
	}
	
	@PostMapping("/validate")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleType", RoleType.values());
		if (bindingResult.hasErrors()) {
			return "AddUser";
		}
		boolean success = userInterface.createUser(user);
		if (success == false) {
			return "redirect:/user/add";
		} else {
			return "redirect:/";
		}
	}
	
	//everyone can update 
	@GetMapping("/update")
	public ModelAndView updateUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if (user == null) {
			mv.setViewName("redirect:/user/login");
			return mv;
		} else {
			mv.setViewName("updateUser");
			mv.addObject("username", user.getUserName());
			mv.addObject("roleType", RoleType.values());
			mv.addObject("user", user);
			return mv;
		}
	}

	@PostMapping("/update")
	public ModelAndView updateUser(@ModelAttribute("user") User user, HttpSession session,
			@RequestParam("confPassword") String confirmPassword) {
		ModelAndView mv = new ModelAndView();
		if (confirmPassword.equals(user.getPassword()) == false) {
			mv.setViewName("redirect:/user/update");
			return mv;
		}
		boolean success = userInterface.updateUser(user);
		if (success == true) {
			session.setAttribute("user", user);
			mv.setViewName("redirect:/");
			return mv;
		} else {
			mv.setViewName("error");
			return mv;
		}
	}

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
	}
}
