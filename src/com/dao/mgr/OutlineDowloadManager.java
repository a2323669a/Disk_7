package com.dao.mgr;

import java.io.File;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.dao.FileDAO;
import com.dao.TaskDAO;
import com.model.Directory;
import com.model.MyFile;
import com.model.Task;
import com.model.User;

@Component
public class OutlineDowloadManager {
	DirectoryDAO directoryDAO;
	FileDAO fileDAO;
	TaskDAO taskDAO;

	public Directory getDir(int id) {
		return directoryDAO.getDirectory(id);
	}

	public void save(Directory dir) {
		directoryDAO.save(dir);
	}

	public void save(MyFile myFile) {
		fileDAO.save(myFile);
	}

	public void save(Task task) {
		taskDAO.save(task);
	}

	public void setCurrent(Task task, int current) {
		taskDAO.setCurrent(task, current);
	}

	public void setSpeed(Task task, String speed) {
		taskDAO.setSpeed(task, speed);
	}

	public void setComplete(Task task, boolean complete) {
		taskDAO.setComplete(task, complete);
	}

	public void setCtime(Task task, String ctime) {
		taskDAO.setCtime(task, ctime);
	}

	public void setSuccess(Task task, boolean success) {
		taskDAO.setSuccess(task, success);
		taskDAO.setCurrent(task,task.getSize());
		taskDAO.setSpeed(task,"0");
		taskDAO.setCtime(task,"任务完成");
	}

	public String getPath(Directory dir) {
		String spath = directoryDAO.getFirst().getPath();
		String path = dir.getPath() + dir.getDir() + "/";
		return spath + path;
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

	public TaskDAO getTaskDAO() {
		return taskDAO;
	}
	
	@Resource
	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	public Task createTask(User user, String link, String file) {
		Task task = new Task();
		task.setCdate(new Timestamp(System.currentTimeMillis()));
		task.setComplete(false);
		task.setCtime("未知");
		task.setCurrent(0);
		task.setLink(link);
		task.setName(file);
		task.setSize(0);
		task.setSpeed("正在解析");
		task.setSuccess(false);
		task.setUser(user);

		return taskDAO.save(task);
	}

	public void setSize(Task task, int size) {
		taskDAO.setSize(task, size);
	}

	public void setTask(Task task, int current, String speed, String ctime) {
		this.setCtime(task, ctime);
		this.setCurrent(task, current);
		this.setSpeed(task, speed);
	}

	public Task getTask(int id) {
		return taskDAO.get(id);
	}

	public void setError(Task task) {
		taskDAO.setComplete(task, true);
		taskDAO.setSuccess(task, false);
	}

	public Directory insert(User user, File file, Directory dir) {
		Directory directory = new Directory();
		directory.setCdate(new Timestamp(System.currentTimeMillis()));
		directory.setDir(file.getName());
		directory.setFolder(file.isDirectory());
		
		if (file.isDirectory() && file.list().length > 0) {
			directory.setLeaf(false);
		} else {
			directory.setLeaf(true);
		}

		directory.setPath(dir.getPath() + dir.getDir() + "/");
		directory.setPdir(dir);
		directory.setUser(user);
		
		//关联myfile
		if (!file.isDirectory()) {
			MyFile myFile = new MyFile();
			myFile.setCdate(new Timestamp(System.currentTimeMillis()));
			myFile.setFileName(file.getName());
			myFile.setPath(this.getPath(dir));
			myFile.setSize(file.length());
			myFile.setUser(user);
			
			directory.setMyFile(myFile);
			myFile.setDirectory(directory);
			fileDAO.save(myFile);
		}

		directoryDAO.save(directory);
		
		//set parent leaf
		
		if(dir.isLeaf()){
			directoryDAO.setLeaf(dir.getId(),false);
		}
		
		return directory;
	}
}
