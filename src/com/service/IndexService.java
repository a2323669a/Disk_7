package com.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.IndexManager;
import com.model.Directory;
import com.model.User;

@Component
public class IndexService {
	private IndexManager indexManager;

	public List<Directory> list(User user,Directory dir){
		return indexManager.list(dir.getId());
	}
	
	public Directory getFirstDir(User user){
		return indexManager.getFirstDir(user);
	}
	
	public Directory getDir(int id){
		return indexManager.getDir(id);
	}
	
	public IndexManager getIndexManager() {
		return indexManager;
	}
	
	@Resource
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	
}
