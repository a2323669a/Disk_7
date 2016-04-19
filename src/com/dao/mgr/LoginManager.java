package com.dao.mgr;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.UserDAO;
import com.model.Directory;
import com.model.User;

@Component
public class LoginManager {
	private UserDAO userDAO;
	private DirectoryDAO directoryDAO;
	
	public boolean check(String name,String password , StringBuffer result){
		User user = userDAO.get(name);
		if(user == null){
			result.append("用户不存在 !");
			return false;
		}
		
		if(user.getPassword_md5().equals(password)){
			return true;
		}
		
		result.append("密码错误");
		return false;
	}
	
	
	public Directory getFirstDir(String name){
		User user = userDAO.get(name);
		return directoryDAO.getChild(user,directoryDAO.getFirst());
	}
	
	
	public User getUserByName(String name){
		return userDAO.get(name);
	}


	public UserDAO getUserDAO() {
		return userDAO;
	}

	
	@Resource
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}

	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}
}
