package com.action;

import java.util.ArrayList;
import java.util.List;

import com.model.Directory;
import com.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.IndexService;

public class IndexAction extends ActionSupport{
	IndexService indexService;
	Directory dir;
	int id;
	List<Directory> dirs;
	String result;
	
	public String execute(){
		System.out.println(id);
		//bulid the dir
		if(id == 0){
			User user = (User) ActionContext.getContext().getSession().get("user");
			if(user == null){
				result = "ÇëÏÈµÇÂ¼"; 
				return ERROR;
			}
			dir = indexService.getFirstDir(user);
		}else{
			dir = indexService.getDir(id);
		}
		
		return list();
	}
	
	public String list(){
		 User user = (User)ActionContext.getContext().getSession().get("user");
		 if(user == null){
			 result = "ÇëÏÈµÇÂ¼";
			 return ERROR;
		 }
		 dirs = indexService.list(user,dir);
		 return SUCCESS;
	}

	public List<Directory> getDirs() {
		return dirs;
	}

	public void setDirs(List<Directory> dirs) {
		this.dirs = dirs;
	}

	public Directory getDir() {
		return dir;
	}

	public void setDir(Directory dir) {
		this.dir = dir;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
