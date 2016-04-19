package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.stereotype.Component;
import com.dao.mgr.OutlineDowloadManager;
import com.exception.OutlineDowloadException;
import com.model.Directory;
import com.model.Task;
import com.model.User;
import com.util.Dowload;

@Component
public class OutlineDowloadService {
	Dowload dowload;
	OutlineDowloadManager outlineDowloadManager;

	public void dowload(String aria2Path, String link, int did)
			throws OutlineDowloadException {
		check(link);
		Directory dir = outlineDowloadManager.getDir(did);
		String path = outlineDowloadManager.getPath(dir);
		InputStream in = null;
		try {
			in = dowload.dowload(aria2Path, path, link);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new DowloadThread(in,dir,outlineDowloadManager,link).start();
	}

	private void check(String link) throws OutlineDowloadException {
		if (link == null) {
			throw new OutlineDowloadException("链接为空!");
		}
		link = link.trim();
		if (link.equals("")) {
			throw new OutlineDowloadException("链接为空!");
		}
		Pattern p = Pattern.compile("(http|ftp|magnet).*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(link);
		if (!matcher.matches()) {
			throw new OutlineDowloadException("链接无效,支持 HTTP FTP MAGNET!");
		}

	}

	public Dowload getDowload() {
		return dowload;
	}

	@Resource
	public void setDowload(Dowload dowload) {
		this.dowload = dowload;
	}

	public OutlineDowloadManager getOutlineDowloadManager() {
		return outlineDowloadManager;
	}

	@Resource
	public void setOutlineDowloadManager(
			OutlineDowloadManager outlineDowloadManager) {
		this.outlineDowloadManager = outlineDowloadManager;
	}
}


class DowloadThread extends Thread {
	DeleteService deleteService;
	BufferedReader bf;
	String path;
	Directory dir;
	User user;
	Task task;
	String link;
	File file;
	double dowload = 0;
	boolean start = false;
	OutlineDowloadManager outlineDowloadManager;

	public DowloadThread(InputStream in,Directory dir,OutlineDowloadManager outlineDowloadManager,String link) {
		bf = new BufferedReader(new InputStreamReader(in));
		this.outlineDowloadManager = outlineDowloadManager;
		path = outlineDowloadManager.getPath(dir);
		user = dir.getUser();
		this.link = link;
		this.dir = dir;
	}

	@Override
	public void run() {
		PrintWriter pw = null;
		try {

			String s = "", file = "";
			boolean success = true;
			boolean readSize = false;
			Pattern p = Pattern
					.compile("\\[#.*\\s(.*)MiB/(.*)MiB(.*)\\sCN:(.*)\\sSD.*DL:(.*)KiB\\s.*ETA:(.*)\\]");
			Matcher m = null;
			
			//读取数据
			while ((s = bf.readLine()) != null) {
				if (!start && s.matches(".*//.*")) {
					file = s.substring(s.indexOf("//") + 2);
					int index = file.indexOf("/");
					if (index > 0) {
						file = file.substring(0, index);
					}
//System.out.println("------------" + file + "-------------");
//System.out.println(new String(file.getBytes("GBK"),"UTF-8"));
					task = outlineDowloadManager.createTask(user,link,file);
					this.file = new File(path + file);
					start = true;
				}
				
				//记录下载速度
				if (start) {
					m = p.matcher(s);

					while (m.find()) {
						double dow = Double.parseDouble(m.group(1));
						if (dow - dowload >= 2) {
							dowload = dow;
//System.out.println("已下载 : " + m.group(1) + "MB");
//System.out.println("大小 : " + m.group(2) + "MB");
//System.out.println("已完成 : " + m.group(3));
//System.out.println("连接数 : " + m.group(4));
//System.out.println("下载速度 : " + m.group(5) + "kb/s");
//System.out.println("剩余 : " + m.group(6));
//System.out.println();
							if(!readSize){
								outlineDowloadManager.setSize(task,(int)Double.parseDouble(m.group(2)));
								task.setSize((int)Double.parseDouble(m.group(2)));
								readSize = true;
							}
							outlineDowloadManager.setTask(task,(int)Double.parseDouble(m.group(1)),m.group(5),m.group(6));
						}
					}
				}
				
				//超时停止
				if (s.matches(".*Stop downloading torrent due to --bt-stop-timeout option.*")) {
					success = false;
				}
				
				//报错
				if (s.matches("Exception caught.*")) {
					success = false;
				}
			}
			if (success) {
				outlineDowloadManager.setComplete(task,true);
				outlineDowloadManager.setSuccess(task,true);
				
				if (this.file.isDirectory()) {
					read(this.file,dir);
				} else {
					insert(this.file);
				}
			} else {
//System.out.println("rollback");
				if(this.file != null){
					deleteService.deleteFile(this.file);
				}
				if(this.task == null){
					task = outlineDowloadManager.createTask(user,link,"未知的文件");
				}
				outlineDowloadManager.setError(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bf != null) {
					bf.close();
					bf = null;
				}

				if (pw != null) {
					pw.flush();
					pw.close();
					pw = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void read(File file,Directory pdir) {
		File[] files = file.listFiles();
		
		Directory directory = outlineDowloadManager.insert(user,file,pdir);

		for (File f : files) {
			if (f.isDirectory()) {
				read(f,directory);
			} else {
				outlineDowloadManager.insert(user,f,directory);
			}
		}
	}

	private void insert(File file) {
		outlineDowloadManager.insert(user,file,dir);
	}

	public DeleteService getDeleteService() {
		return deleteService;
	}
	
	@Resource
	public void setDeleteService(DeleteService deleteService) {
		this.deleteService = deleteService;
	}
}

