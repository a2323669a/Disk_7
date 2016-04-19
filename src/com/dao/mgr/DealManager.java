package com.dao.mgr;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.FileDAO;
import com.model.Directory;

@Component
public class DealManager {
	FileDAO fileDAO;
	DirectoryDAO directoryDAO;

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

	public Directory getDir(int id) {
		return directoryDAO.getDirectory(id);
	}
}
