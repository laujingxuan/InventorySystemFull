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

import team5.model.User;
import team5.nonEntityModel.UserForm;
import team5.service.SessionService;
import team5.service.SessionServiceImpl;
import team5.service.UserService;
import team5.service.UserServiceImpl;
import team5.validator.UserFormValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService user_svc;
	
	@Autowired
	private SessionService session_svc;
	
	@Autowired 
	public void setImplimentation(UserServiceImpl user_svcimpl, SessionServiceImpl session_svcimpl) {
		this.user_svc = user_svcimpl;
		this.session_svc = session_svcimpl;
	}
	
	@Autowired
	private UserFormValidator userFormValidator;
	
	@InitBinder("userForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(userFormValidator);
	}
	
	//only admin can add
	@GetMapping("/add")
	public String addUser(Model model, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		
		model.addAttribute("userForm", new UserForm());
		return "editUser";
	}
	
	@PostMapping("/validate")
	public String addUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, HttpSession session) {
		
		if (bindingResult.hasErrors()) {
			return "editUser"; // "forward:/user/validate";
		}
		
		User user = new User(userForm);
		//if (userRepo.findByUserName(user.getUserName())== null) {}
		user_svc.save(user);
		return "redirect:/";
	}
	
	//only admin can retrieve the list
	@GetMapping("/users")
	public String viewUser(Model model, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		
		model.addAttribute("users", user_svc.findAll());
		return "UserList";
	}
		
	
	//only admin can edit
	@RequestMapping(value = "/edit/{id}")
	public String editUser(@PathVariable("id") long id, Model model, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		
		User toChange = user_svc.findById(id);
		// mv.addObject("roleType", RoleType.values());
		UserForm userForm = new UserForm(toChange);
		model.addAttribute("userForm", userForm);
		return "editUser";
	}
	
	@PostMapping("/save")
	public String editUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		if (bindingResult.hasErrors()) return "editUser";
		
		User user = new User(userForm);
		user_svc.save(user);
		return "redirect:/user/users";
	}
	
	//everyone can change password
	@GetMapping("/update")
	public String updateUser(Model model, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		
		model.addAttribute("userForm", new UserForm());
		return "editUser";
	}

	@PostMapping("/update")
	public String updateUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		if (bindingResult.hasErrors()) return "editUser";
		
		User user = new User(userForm);
		user_svc.save(user);
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	//only admin can delete
	@RequestMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id,HttpSession session) {
		if (session_svc.isNotLoggedIn(session)) return "redirect:/user/login";
		if (session_svc.hasNoPermission(session)) return "nopermission";
		
		user_svc.delete(user_svc.findById(id));
		return "forward:/supplier/list";
	}
}
