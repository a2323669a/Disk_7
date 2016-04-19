package com.dao.mgr;

import java.io.File;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.UserDAO;
import com.exception.UserNotFoundException;
import com.model.Directory;
import com.model.User;

@Component
public class RegisterManager {
	DirectoryDAO directoryDAO;
	UserDAO userDAO;
	
	public boolean check(String name){
		return userDAO.existsForName(name);
	}
	
	public void save(User user , String path){
		//insert into user;
		userDAO.save(user);
		
		//create a disk directory
		File file = new File(path,user.getName());
		file.mkdir();
		
		//insert into directory
		Directory directory = new Directory();
		directory.setCdate(new Timestamp(System.currentTimeMillis()));
		directory.setDir(user.getName());
		directory.setLeaf(true);
		directory.setPath("");
		directory.setMyFile(null);
		directory.setFolder(true);
		directory.setPdir(directoryDAO.getFirst());
		directory.setUser(userDAO.get(user.getName()));
		
		directoryDAO.save(directory);
	
	}
	
	public User getUserByName(String name){
		User user = userDAO.get(name);
		if(user == null){
			throw new UserNotFoundException();
		}
		return user;
	}
	
	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}
	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
	@Resource
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
