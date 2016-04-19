package com.service;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.FolderManager;
import com.model.User;
import com.vo.FolderInfo;

@Component
public class FolderService {
	private FolderManager folderManager;

	public String createFolder(User user,FolderInfo info){
		if(folderManager.exists(info.getDirectoryId(),info.getName())){
			return "文件夹已经存在";
		}
		
		create(folderManager.getPath(info.getDirectoryId()),user.getName(),info.getName());
		folderManager.create(user,info.getDirectoryId(),info.getName());
		return "创建成功";
	}
	
	private void create(String path, String userName, String name) {
		File file = new File(path,name);
		if(!file.exists()){
			file.mkdir();
		}
	}

	public FolderManager getFolderManager() {
		return folderManager;
	}
	
	@Resource
	public void setFolderManager(FolderManager folderManager) {
		this.folderManager = folderManager;
	}
	
}
