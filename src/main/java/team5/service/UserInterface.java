package team5.service;


// import java.util.List;

import team5.model.User;

public interface UserInterface {
	/*
	public void createUser(User user);
	public void updateUser(User user);
	public List<User> listAllUser();
	public void deleteUser(User user); */
	public boolean authenticate(User user);
	public User findByName(String name);

}