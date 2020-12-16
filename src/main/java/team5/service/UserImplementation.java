package team5.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.RoleType;
import team5.model.User;
import team5.repo.UserRepository;

@Service
@Transactional
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public User findById(long id) {
		return userRepo.findById(id).get();
	}
	
	@Override
	public boolean authenticate(User user) {
		User dbuser = userRepo.findByUserName(user.getUserName());
		if (dbuser==null) {
			return false;
		}else if(dbuser.getUserName().equals(user.getUserName()) && dbuser.getPassword().equals(user.getPassword())){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean updateUser(User user) {
		User userCheck = userRepo.findById(user.getId()).get();
		if (userCheck == null) {
			return false;
		}else {
			userCheck.setUserName(user.getUserName());
			userCheck.setPassword(user.getPassword());
			userCheck.setRole(user.getRole());
			userRepo.save(userCheck);
			return true;
		}
	}

	@Override
	public boolean createUser(User user) {
		if (userRepo.findByUserName(user.getUserName())== null) {
			userRepo.save(user);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public ArrayList<User> findByJobRole(RoleType roleType) {
		ArrayList<User> userList = userRepo.findByRole(roleType);
		return userList;
	}

	@Override
	public User findByUsername(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepo.findAll();
		return users;
	}

	@Override
	public void deleteUsers(String[] users) {
		for(String user: users) {
			User temp = userRepo.findByUserName(user);
			userRepo.delete(temp);
		}
		return;

	}

}

