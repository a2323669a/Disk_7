package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dao.DirectoryDAO;
import com.model.Directory;
import com.model.MyFile;

@Component
public class DownloadService {
	DirectoryDAO directoryDAO;

	public void download(HttpServletResponse response,int id){
		Directory dir = directoryDAO.getDirectory(id);
		MyFile myFile = dir.getMyFile();
		
		String fileName = myFile.getPath()+myFile.getFileName();
		String outName = myFile.getFileName();
		
		try {
			//对fileName 解码
			fileName = java.net.URLDecoder.decode(fileName,"utf-8");
			//对outName转码
			outName = java.net.URLEncoder.encode(outName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		File file = new File(fileName);
		
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;fileName=" + outName);
		response.setHeader("Content-Length",String.valueOf(file.length()));
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = new FileInputStream(fileName);
			os = response.getOutputStream();
			byte[] buffer = new byte[8192];
			int count = 0;
			while((count = is.read(buffer)) > 0){
				os.write(buffer,0,count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				is = null;
				os.flush();
				os.close();
				os = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public DirectoryDAO getDirectoryDAO() {
		return directoryDAO;
	}
	
	@Resource
	public void setDirectoryDAO(DirectoryDAO directoryDAO) {
		this.directoryDAO = directoryDAO;
	}
}
