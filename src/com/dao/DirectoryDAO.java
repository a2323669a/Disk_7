package com.dao;

import java.util.List;

import com.model.Directory;
import com.model.User;

public interface DirectoryDAO {
	public void save(Directory directory);
	public boolean exists(int id);
	public boolean exists(String dir);
	public boolean exists(Directory pdir,String name);
	public boolean exists(int userid,String path,String dir);
	public boolean isLeaf(int id);
	public List<Directory> getChildren(int pid);
	public Directory getChild(User user,Directory dir);
	public void setLeaf(int id,boolean isLeaf);
	public void delete(Directory directory);
	public void delete(int id);
	public void delelte(int userid);
	public void delete(int userid,String path);
	public void detele(int userid,String path,String dir);
	public Directory getDirectory(int id);
	public Directory getFirst();
	public boolean hasChild(Directory dir);
	
}
