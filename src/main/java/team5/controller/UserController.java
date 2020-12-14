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
import team5.repo.UserRepository;
import team5.service.UserInterface;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserInterface userInterface;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public ModelAndView viewUser() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("UserList");
		return mv;
	}
	
	@GetMapping("/add")
	public ModelAndView addUser() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("AddUser");
		mv.addObject("user", new User());
		mv.addObject("roleType", RoleType.values());
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

// Need login path and session over there
//	@GetMapping("/update")
//	public ModelAndView updateUser(HttpSession session) {
//		User user = (User) session.getAttribute("user");
//		ModelAndView mv = new ModelAndView();
//		if (user == null) {
//			mv.setViewName("redirect:/login");
//			return mv;
//		} else {
//			mv.setViewName("UpdateUser");
//			mv.addObject("username", user.getUserName());
//			mv.addObject("roleType", RoleType.values());
//			mv.addObject("user", user);
//			return mv;
//		}
//	}
//
//	@PostMapping("/update")
//	public ModelAndView updateUser(@ModelAttribute("user") User user, HttpSession session,
//			@RequestParam("confPassword") String confirmPassword) {
//		ModelAndView mv = new ModelAndView();
//		if (confirmPassword.equals(user.getPassword()) == false) {
//			mv.setViewName("redirect:/user/update");
//			return mv;
//		}
//		boolean success = userInterface.updateUser(user);
//		if (success == true) {
//			session.setAttribute("user", user);
//			mv.setViewName("redirect:/home");
//			return mv;
//		} else {
//			mv.setViewName("error");
//			return mv;
//		}
//	}

	@PostMapping("/delete")
	public ModelAndView deleteUser(@RequestParam(value = "deleteUser", required = false) String[] deleteUsers) {
		ModelAndView mv = new ModelAndView();
		if (deleteUsers == null) {
			mv.setViewName("redirect:/user/users");
			return mv;
		} else {
			userInterface.deleteUsers(deleteUsers);
			mv.setViewName("redirect:/user/users");
			return mv;
		}
	}
}
