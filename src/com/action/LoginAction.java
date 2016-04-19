package com.action;

import com.model.Directory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.LoginService;
import com.vo.LoginInfo;

public class LoginAction extends ActionSupport implements ModelDriven{
	private LoginInfo info = new LoginInfo();
	private LoginService loginService;
	private StringBuffer result = new StringBuffer("");
	private Directory dir;
	
	public String execute(){
		return login();
	}
	
	public String login(){
		if(loginService.login(info,result)){
			ActionContext.getContext().getSession().put("user",loginService.getUser(info.getName()));
			dir = loginService.getParentDir(info); 
			return SUCCESS;
		}
		return INPUT;
	}
	
	@Override
	public Object getModel() {
		return info;
	}

	public LoginInfo getInfo() {
		return info;
	}

	public void setInfo(LoginInfo info) {
		this.info = info;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public StringBuffer getResult() {
		return result;
	}

	public void setResult(StringBuffer result) {
		this.result = result;
	}

	public Directory getDir() {
		return dir;
	}

	public void setDir(Directory dir) {
		this.dir = dir;
	}
	
}
