package com.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.model.Directory;
import com.model.User;

@Component("directoryDAO")
public class MySQLDirectoryDAO implements DirectoryDAO {
	HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Directory directory) {
		hibernateTemplate.save(directory);
	}

	@Override
	public boolean exists(int id) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from Directory dir where dir.id=?"
				,id);
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean exists(String dir) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from Directory dir where dir.dir=?"
				,dir);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean exists(int userid, String path, String dir) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from Directory dir where dir.user=? and dir.path=? and dir.dir=?"
				,hibernateTemplate.load(User.class,userid)
				,path
				,dir);
		System.out.println(count.get(0));
System.out.println(path+"\\"+dir+"\\"+userid);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean isLeaf(int id) {
		List<Boolean> count = (List<Boolean>) hibernateTemplate.find(
				"select dir.leaf from Directory dir where dir.id=?"
				,id);
		
		return count.get(0);
	}

	@Override
	public List<Directory> getChildren(int pid) {
		List<Directory> dirs = (List<Directory>) hibernateTemplate.find(
				"from Directory dir where dir.pdir = ?"
				,hibernateTemplate.load(Directory.class,pid));
		return dirs;
	}

	@Override
	public void setLeaf(int id ,boolean isLeaf) {
		String sql = "update Directory dir set dir.leaf = ? where dir.id = ?";
		hibernateTemplate.bulkUpdate(sql,isLeaf,id);
	}

	@Override
	public void delete(int id) {
		String sql = "delete from Directory dir where dir.id = ?";
		hibernateTemplate.bulkUpdate(sql,id);
	}

	@Override
	public void delelte(int userid) {
		String sql = "delete from Directory dir where dir.user = ?";
		hibernateTemplate.bulkUpdate(sql,hibernateTemplate.load(User.class,userid));

	}

	@Override
	public void delete(int userid, String path) {
		String sql = "delete from Directory dir where dir.user = ? and dir.path = ?";
		hibernateTemplate.bulkUpdate(sql,hibernateTemplate.load(User.class,userid),path);

	}

	@Override
	public void detele(int userid, String path, String dir) {
		String sql = "delete from Directory dir where dir.user = ? and dir.path = ? and dir.dir=?";
		hibernateTemplate.bulkUpdate(sql,hibernateTemplate.load(User.class,userid),path,dir);

	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void delete(Directory directory) {
		hibernateTemplate.delete(directory);
	}

	@Override
	public Directory getDirectory(int id) {
		return hibernateTemplate.get(Directory.class,id);

	}

	@Override
	public Directory getFirst() {
		List<Integer> id = (List<Integer>) hibernateTemplate.find("select min(dir.id) from Directory dir");
		List<Directory> dir = (List<Directory>) hibernateTemplate.find("from Directory dir where dir.id = ?",id.get(0));
		return dir.get(0);
	}

	@Override
	public Directory getChild(User user, Directory dir) {
		List<Directory> dirs = (List<Directory>) hibernateTemplate.find(
				"from Directory dir where dir.user = ? and dir.pdir = ?"
				,user
				,dir);
		return dirs.get(0);
	}

	@Override
	public boolean exists(Directory pdir, String name) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from Directory dir where dir.pdir=? and dir.dir=?"
				,pdir
				,name);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean hasChild(Directory dir) {
		List<Long> count = (List<Long>) hibernateTemplate.find(
				"select count(*) from Directory dir where dir.pdir=?"
				,dir);
		
		if(count.get(0) == 0){
			return false;
		}
		return true;
	}

}
