package com.action;

import com.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.FolderService;
import com.vo.FolderInfo;

public class FolderAction extends ActionSupport implements ModelDriven{
	FolderService folderService;
	FolderInfo info = new FolderInfo();
	String result;

	public String execute(){
		User user = (User) ActionContext.getContext().getSession().get("user");
		if(user == null){
			result = "ÇëÏÈµÇÂ¼";
			return ERROR;
		}
		
		result = folderService.createFolder(user,info);
		
		return SUCCESS;
	}
	
	public FolderService getFolderService() {
		return folderService;
	}

	public void setFolderService(FolderService folderService) {
		this.folderService = folderService;
	}

	public FolderInfo getInfo() {
		return info;
	}

	public void setInfo(FolderInfo info) {
		this.info = info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public Object getModel() {
		return info;
	}
}
