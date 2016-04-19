
package com.dao.mgr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.model.Directory;
import com.model.User;

@Component
public class IndexManager {
	DirectoryDAO directoryDAO;

	public List<Directory> list(int pid){
		if(directoryDAO.isLeaf(pid)){
			return null;
		}
		
		return directoryDAO.getChildren(pid);
	}
	
	public Directory getFirstDir(User user){
		return directoryDAO.getChild(user,directoryDAO.getFirst());
	}
	
	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}

	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}

	public Directory getDir(int id) {
		return directoryDAO.getDirectory(id);
	}
	
}
