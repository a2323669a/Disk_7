package com.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.model.Task;
import com.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.TaskService;

public class TaskAction extends ActionSupport{
	private int did;
	private TaskService taskService;
	private List<Task> tasks;
	private int size = 10;
	private int start;
	private String result;
	
	public String execute(){
		User user = (User)ActionContext.getContext().getSession().get("user");
		tasks = taskService.load(user,start,size);
		return SUCCESS;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public TaskService getTaskService() {
		return taskService;
	}
	
	@Resource
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
