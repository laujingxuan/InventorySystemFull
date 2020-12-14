package team5.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team5.model.User;
import team5.repo.UserRepository;

@Service
@Transactional
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;
	
	@Override
	public boolean authenticate(User user) {
		User dbuser = urepo.findUserByUserName(user.getUserName());
		String tmp = dbuser.getUserName();
		if (dbuser.getUserName().equals(user.getUserName()) && dbuser.getPassword().equals(user.getPassword()))
			return true;
		else
			return false;
	}
	
	public User findByName(String name) {
		return urepo.findUserByUserName(name);
	}


	/*
	@Override
	public void createUser(User user) {
		urepo.save(user);
	}

	@Override
	public void updateUser(User user) {
		urepo.save(user);
	}

	@Override
	public List<User> listAllUser() {
		return urepo.findAll();
	}

	@Override
	public void deleteUser(User user) {
		urepo.delete(user);
	}
	*/
}