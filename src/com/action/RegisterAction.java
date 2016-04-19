package com.action;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.RegisterService;
import com.vo.RegisterInfo;

public class RegisterAction extends ActionSupport implements ModelDriven<RegisterInfo> {
	RegisterService registerService;
	RegisterInfo info = new RegisterInfo();
	String result;

	public String execute() {
		save();
		return SUCCESS;
	}

	private void save() {
		if(registerService.save(info)){
			result = "注册成功";
			//set the user into session
			ActionContext.getContext().getSession().put("user",registerService.getUser(info.getName()));
		}else{
			result = "此用户名已被占用";
		}
		
	}


	@Override
	public RegisterInfo getModel() {
		return info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public RegisterInfo getInfo() {
		return info;
	}

	public void setInfo(RegisterInfo info) {
		this.info = info;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
}
