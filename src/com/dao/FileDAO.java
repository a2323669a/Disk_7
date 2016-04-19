package com.dao;

import org.springframework.stereotype.Component;

import com.model.MyFile;

public interface FileDAO {
	public boolean exists(int userid,String fileName);
	public boolean exists(int userid,String path,String fileName);
	public boolean exists(int id);
	public void save(MyFile myFile);
	public void delete(MyFile myFile);
	public void delete(int id);
	public void delete(String fileName);
	public void delete(int userid,String path,String fileName);
	public void delete(int userid,String fileName);
}
