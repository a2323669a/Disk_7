package com.dao.mgr;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.FileDAO;
import com.dao.UserDAO;
import com.exception.UploadException;
import com.model.Directory;
import com.model.MyFile;

@Component
public class UploadManager {
	UserDAO userDAO;
	FileDAO fileDAO;
	DirectoryDAO directoryDAO;
	
	public void save(int userid, int did, String uploadPath, String fileName,
			long size, List<String> errors) throws UploadException {
		
		//check the user is vaild
		if(!userDAO.exists(userid)){
			errors.add("your count is invaild !");
			throw new UploadException("Can't find the user in the database that the user's id is"+userid);
		}
		
		//insert into database
		try{
			if(directoryDAO.isLeaf(did)){
				directoryDAO.setLeaf(did,false);
			}else{
				if(directoryDAO.exists(userid,uploadPath,fileName)){
					errors.add("your disk in this path have already exists this file : "+fileName);
					throw new UploadException("your disk in this path have already exists this file : "+fileName);
				}
			}
			
			Directory pdir = directoryDAO.getDirectory(did);
			
			Directory dir = new Directory();
			dir.setCdate(new Timestamp(System.currentTimeMillis()));
			dir.setDir(fileName);
			dir.setLeaf(true);
			dir.setPath(pdir.getPath()+pdir.getDir()+"/");
			dir.setFolder(false);
			dir.setPdir(directoryDAO.getDirectory(did));
			dir.setUser(userDAO.get(userid));
			
			directoryDAO.save(dir);
			
			MyFile myFile = new MyFile();
			Directory directory = new Directory();
			directory.setId(did);
			
			myFile.setCdate(new Timestamp(System.currentTimeMillis()));
			myFile.setDirectory(directoryDAO.getDirectory(did));
			myFile.setFileName(fileName);
			myFile.setPath(uploadPath+pdir.getPath()+pdir.getDir()+"/");
			myFile.setSize(size);
			
			myFile.setDirectory(directory);
			myFile.setUser(userDAO.get(userid));
			
			dir.setMyFile(myFile);
			
			fileDAO.save(myFile);
			
		}catch(Throwable t){
			errors.add(t.getMessage());
			throw new UploadException(t.getMessage());
		}
		
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
	
	public String getUserName(int id){
		return userDAO.get(id).getName();
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	@Resource
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public FileDAO getFileDAO() {
		return fileDAO;
	}

	@Resource
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}
	
	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}
}
