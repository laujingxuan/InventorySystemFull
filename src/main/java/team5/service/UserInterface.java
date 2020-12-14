package team5.service;

import java.util.ArrayList;
import java.util.List;

import team5.model.RoleType;
import team5.model.User;


public interface UserInterface {

	public boolean authentication(User user);
	public boolean updateUser(User user);
	public boolean createUser(User user);
	public ArrayList<User> findByJobRole(RoleType roleType);
	public User findByUsername(String userName);
	public List<User> findAll();
	public void deleteUsers(String[] users);
	public boolean authenticate(User user);
	public User findByName(String name);
}