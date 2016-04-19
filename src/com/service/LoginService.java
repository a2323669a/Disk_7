package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.LoginManager;
import com.model.Directory;
import com.model.User;
import com.opensymphony.xwork2.ActionSupport;
import com.util.MD5;
import com.vo.LoginInfo;

@Component
public class LoginService {
	private LoginManager loginManager;
	
	public boolean login(LoginInfo info,StringBuffer result){
		//do md5
		info.setPassword(MD5.tomd5(info.getPassword()));
		
		//check
		return loginManager.check(info.getName(),info.getPassword(), result);
		
	}
	
	public Directory getParentDir(LoginInfo info){
		return loginManager.getFirstDir(info.getName());
	}
	
	public User getUser(String name){
		return loginManager.getUserByName(name);
	}
	
	public LoginManager getLoginManager() {
		return loginManager;
	}
	
	@Resource
	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}
	
}
