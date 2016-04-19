package com.dao;

import java.util.List;

import com.model.Task;
import com.model.User;

public interface TaskDAO {
	public Task get(int id);
	public Task save(Task task);
	public List<Task> list(User user);
	public List<Task> list(User user,int start,int size);
	public List<Task> list(User user,boolean complete,int start,int size);
	public void delete(Task task);
	public void setCurrent(Task task , int current);
	public void setSpeed(Task task , String speed);
	public void setCtime(Task task,String ctime);
	public void setSuccess(Task task , boolean success);
	public void setComplete(Task task , boolean complete);
	public void setSize(Task task,int size);
}
