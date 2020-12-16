package team5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import team5.model.RoleType;
import team5.model.User;
import team5.nonEntityModel.UserListAJAX;
import team5.service.UserInterface;


@RestController
public class RestWebController {
	
	@Autowired
	private UserInterface userInterface;
	
	@PostMapping("/user/users")
	public ResponseEntity<?> viewUser(@RequestBody UserListAJAX userListForm) {
		List<User> users;
		if (userListForm.getRoleType().equals("ALL")) {
			users = userInterface.findAll();
		}else {
			users = userInterface.findByJobRole(RoleType.valueOf(userListForm.getRoleType()));
		}
		return ResponseEntity.ok(users);
	}
}