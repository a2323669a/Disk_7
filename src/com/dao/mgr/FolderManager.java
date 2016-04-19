package com.dao.mgr;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.model.Directory;
import com.model.User;

@Component
public class FolderManager {
	private DirectoryDAO directoryDAO;

	public boolean exists(int id,String name){
		return directoryDAO.exists(directoryDAO.getDirectory(id),name);
	}
	
	public void create(User user,int id,String name){
		Directory pdir = directoryDAO.getDirectory(id); 
		
		Directory dir = new Directory();
		dir.setCdate(new Timestamp(System.currentTimeMillis()));
		dir.setDir(name);
		dir.setLeaf(true);
		dir.setPath(pdir.getPath()+pdir.getDir()+"/");
		dir.setFolder(true);
		dir.setPdir(pdir);
		dir.setUser(user);
		dir.setMyFile(null);
		
		directoryDAO.save(dir);
		
		//change leaf
		directoryDAO.setLeaf(pdir.getId(),false);
	}
	
	public String getPath(int id){
		String spath = this.getFirstDir().getPath();
		Directory dir = directoryDAO.getDirectory(id);
		String path = dir.getPath()+dir.getDir()+"/";
		return spath+path;
	}
	
	public Directory getFirstDir(){
		return directoryDAO.getFirst();
	}
	
	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}
	
	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	} 
}
