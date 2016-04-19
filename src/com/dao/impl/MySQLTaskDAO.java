package com.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import com.dao.TaskDAO;
import com.model.Task;
import com.model.User;

@Component("taskDAO")
public class MySQLTaskDAO implements TaskDAO{
	HibernateTemplate hibernateTemplate;

	@Override
	public Task get(int id) {
		return hibernateTemplate.get(Task.class,id);
	}

	@Override
	public Task save(Task task) {
		hibernateTemplate.save(task);
		return task;
	}

	@Override
	public void delete(Task task) {
		hibernateTemplate.delete(task);
	}

	@Override
	public void setCurrent(Task task, int current) {
		String sql = "update Task task set task.current = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,current,task.getId());
	}

	@Override
	public void setSpeed(Task task, String speed) {
		String sql = "update Task task set task.speed = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,speed,task.getId());
	}

	@Override
	public void setSuccess(Task task, boolean success) {
		String sql = "update Task task set task.success = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,success,task.getId());
	}

	@Override
	public void setComplete(Task task, boolean complete) {
		String sql = "update Task task set task.complete = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,complete,task.getId());
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void setCtime(Task task, String ctime) {
		String sql = "update Task task set task.ctime = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,ctime,task.getId());
	}

	@Override
	public void setSize(Task task, int size) {
		String sql = "update Task task set task.size = ? where task.id = ?";
		hibernateTemplate.bulkUpdate(sql,size,task.getId());
	}

	@Override
	public List<Task> list(User user) {
		List<Task> tasks = (List<Task>) hibernateTemplate.find(
				"from Task task where task.user = ?"
				,user);
		return tasks;
	}

	@Override
	public List<Task> list(User user, int start, int size) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from Task task where task.user = ?").setParameter(0,user);
		query.setMaxResults(size);  
        query.setFirstResult(start);  
        List<Task> tasks = query.list();
		return tasks;
	}

	@Override
	public List<Task> list(User user, boolean complete, int start, int size) {
		Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from Task task where task.user = ? and task.complete = ?")
											.setParameter(0,user)
											.setParameter(1,complete);
		query.setMaxResults(size);  
        query.setFirstResult(start);  
        List<Task> tasks = query.list();
		return tasks;
	}
	
	
}
