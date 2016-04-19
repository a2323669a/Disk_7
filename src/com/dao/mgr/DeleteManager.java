package com.dao.mgr;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.FileDAO;
import com.model.Directory;
import com.model.MyFile;

@Component
public class DeleteManager {
	private DirectoryDAO directoryDAO;
	private FileDAO fileDAO;
	
	public void delete(int id){
		Directory dir = directoryDAO.getDirectory(id);
		Directory pdir = dir.getPdir();
		deleteDirectory(dir);
		
		if(!directoryDAO.hasChild(pdir)){
			directoryDAO.setLeaf(pdir.getId(),true);
		}
	}
	
	public void deleteDirectory(Directory dir){
		if(dir.isLeaf() && !dir.isFolder()){
			MyFile myFile = dir.getMyFile();
			directoryDAO.delete(dir);
			fileDAO.delete(myFile);
			return;
		}
		for(Directory directory : directoryDAO.getChildren(dir.getId())){
			deleteDirectory(directory);
		}
		directoryDAO.delete(dir);
	}
	
	public String getFileName(int id){
		Directory dir = directoryDAO.getDirectory(id);
		
		if(dir.isFolder()){
			String spath = directoryDAO.getFirst().getPath();
			String path = dir.getPath()+dir.getDir();
			return spath+path;
		}
		
		MyFile myFile = dir.getMyFile();
		return myFile.getPath()+myFile.getFileName();
	}
	
	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}
	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}
	public FileDAO getFileDAO() {
		return fileDAO;
	}
	@Resource
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}
}
