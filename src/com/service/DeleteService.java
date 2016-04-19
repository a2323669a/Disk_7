package com.service;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.DeleteManager;

@Component
public class DeleteService {
	private DeleteManager deleteManager;
	
	public void delete(int id){
		File file = new File(deleteManager.getFileName(id));
		deleteFile(file);
		deleteManager.delete(id);
	}
	
	public void deleteFile(File file) {
	    if (file.isFile()) {  
	        file.delete();  
	        return;  
	    }
	    
	    File[] files = file.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteFile(files[i]);  
	    }  
	    file.delete();  
	}

	public DeleteManager getDeleteManager() {
		return deleteManager;
	}
	
	@Resource
	public void setDeleteManager(DeleteManager deleteManager) {
		this.deleteManager = deleteManager;
	}
	
}
