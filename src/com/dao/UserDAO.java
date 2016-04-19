package com.dao;

import java.util.List;

import com.model.User;

public interface UserDAO {
	public void save(User user);
	public boolean exists(int id);
	public boolean existsForName(String name);
	public List<User> list();
	public User get(int id);
	public User get(String name);
	
}
