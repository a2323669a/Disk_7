package com.action;

import com.opensymphony.xwork2.ActionSupport;
import com.service.DeleteService;

public class DeleteAction extends ActionSupport{
	private int id;
	private int pid;
	private String result;
	private DeleteService deleteService;
	
	@Override
	public String execute(){
		deleteService.delete(id);
		result = "É¾³ý³É¹¦ !";
		return SUCCESS;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public DeleteService getDeleteService() {
		return deleteService;
	}

	public void setDeleteService(DeleteService deleteService) {
		this.deleteService = deleteService;
	}
}
