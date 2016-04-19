package com.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.dao.UserDAO;
import com.model.User;

@Component("userDAO")
public class MySQLUserDAO implements UserDAO {
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(User user) {
		hibernateTemplate.save(user);
	}

	@Override
	public boolean exists(int id) {
		User user = hibernateTemplate.get(User.class,id);
		if(user == null){
			return false;
		}
		return true;
	}

	@Override
	public boolean existsForName(String name) {
		List<Long> size = (List<Long>) hibernateTemplate.find("select count(*) from User u where u.name = ?",name);
		if(size.get(0) == 0){
			return false;
		}
		System.out.println("ss");
		return true;
	}

	@Override
	public List<User> list() {
		return (List<User>)hibernateTemplate.find("from User u");
	}

	@Override
	public User get(int id) {
		return hibernateTemplate.get(User.class,id);
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public User get(String name) {
		List<User> users = (List<User>)hibernateTemplate.find("from User u where u.name=?",name);
		if(users.size() == 0){
			return null;
		}
		return users.get(0);
	}

}
