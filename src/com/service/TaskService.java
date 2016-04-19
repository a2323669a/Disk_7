package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.TaskDAO;
import com.model.Task;
import com.model.User;

@Component
public class TaskService {
	TaskDAO taskDAO;
	
	public List<Task> load(User user, int start ,int size) {
		return taskDAO.list(user,start,size);
	}

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}
	
	@Resource
	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

}
