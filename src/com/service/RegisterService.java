package com.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.RegisterManager;
import com.model.User;
import com.ndktools.javamd5.Mademd5;
import com.util.MD5;
import com.vo.RegisterInfo;

@Component
public class RegisterService {
	RegisterManager registerManager;
	
	
	public boolean save(RegisterInfo info){
		if(registerManager.check(info.getName())){
			return false;
		}
		
		User user = new User()
		.setName(info.getName())
		.setEmail(info.getEmail())
		.setRdate(new Timestamp(System.currentTimeMillis()));
		
		user.setPassword_md5(MD5.tomd5(info.getPassword()));
		
		registerManager.save(user,info.getPath());
		return true;
	}
	
	public User getUser(String name){
		return registerManager.getUserByName(name);
	}

	public RegisterManager getRegisterManager() {
		return registerManager;
	}

	@Resource
	public void setRegisterManager(RegisterManager registerManager) {
		this.registerManager = registerManager;
	}
}
