package com.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.dao.FileDAO;
import com.model.MyFile;
import com.model.User;

@Component("fileDAO")
public class MySQLFileDAO implements FileDAO {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public boolean exists(int userid, String fileName) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from File file where file.user=? and file.fileName=?"
				,hibernateTemplate.load(User.class,userid)
				,fileName);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean exists(int userid, String path, String fileName) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from File file where file.user =? and file.path=?,and file.fileName=?"
				,hibernateTemplate.load(User.class,userid)
				,path
				,fileName);
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean exists(int id) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from File file where file.id = ?"
				,id);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public void save(MyFile myFile) {
		hibernateTemplate.save(myFile);
	}

	@Override
	public void delete(MyFile myFile) {
		hibernateTemplate.delete(myFile);
	}

	@Override
	public void delete(int id) {
		String sql = "delete from MyFile file where file.id = ?";
		hibernateTemplate.bulkUpdate(sql,id);
	}

	@Override
	public void delete(String fileName) {
		String sql = "delete from MyFile file where file.fileName = ?";
		hibernateTemplate.bulkUpdate(sql,fileName);
	}

	@Override
	public void delete(int userid, String path, String fileName) {
		String sql = "delete from MyFile file where file.user = ? and file.path=? and file.fileName=?";
		hibernateTemplate.bulkUpdate(sql,hibernateTemplate.load(User.class,userid),path,fileName);
	}

	@Override
	public void delete(int userid, String fileName) {
		String sql = "delete from MyFile file where file.user=? and file.fileName = ?";
		hibernateTemplate.bulkUpdate(sql,hibernateTemplate.load(User.class,userid),fileName);

	}

}
