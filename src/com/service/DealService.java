package com.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.DealManager;
import com.exception.DealException;
import com.model.Directory;
import com.model.MyFile;
import com.util.ConvertVideo;
import com.util.FileDeal;
import com.vo.DealInfo;

@Component
public class DealService {
	DealManager dealManager;
	ConvertVideo convertVideo;
	FileDeal fileDeal;

	public String video = "video";

	public void deal(DealInfo dealInfo, HashMap<String, String> map)
			throws DealException {
		String actionResult = "";
		Directory directory = dealManager.getDir(dealInfo.getId());

		MyFile myFile = directory.getMyFile();
		
		String category = this.category(directory.getDir());
		
		switch (category) {
		case "mp4":
			actionResult = copyVideo(map,directory,myFile,dealInfo);
			break;
		case "avi":
		case "flv":
			actionResult = convertVideo(map,directory,myFile,dealInfo,category);
			break;
		}

		map.put("fileName", dealInfo.getPath() + map.get("fileName"));
		map.put("actionResult", actionResult);
		map.put("dir", directory.getDir());
	}
	
	private String category(String dir) {
		if(dir == null || dir.trim().equals("")){
			return "null";
		}

		int index = dir.lastIndexOf(".");
		if(index == -1 || index == dir.length()-1){
			return "unkown";
		}
		
		return dir.substring(index+1);
	}

	private String convertVideo(Map<String,String> map,Directory directory,MyFile myFile,DealInfo dealInfo,String category) throws DealException{
		String outputPath = dealInfo.getOutputPath().replaceAll("\\\\", "/")
				+ "/";
		outputPath += dealInfo.getPath();
		try {
			convertVideo.convert(myFile.getPath() + myFile.getFileName(),
					outputPath + directory.getId(),
					dealInfo.getFfmpeg());
			map.put("fileName", directory.getId() + ".mp4");
System.out.println("-----------------------"+category);
		} catch (Throwable t) {
			throw new DealException("ÊÓÆµ×ªÂë³ö´í!");
		}
		
		return video;
	}
	
	private String copyVideo(Map<String,String> map,Directory directory,MyFile myFile,DealInfo dealInfo){
		String outputPath = dealInfo.getOutputPath().replaceAll("\\\\", "/")+ "/";
		outputPath += dealInfo.getPath();
		map.put("fileName", directory.getId() + ".mp4");
		outputPath += map.get("fileName");
		fileDeal.copy(new File(myFile.getPath() + myFile.getFileName()),outputPath);
		return video;
	}

	public Directory getDir(DealInfo dealInfo) {
		return dealManager.getDir(dealInfo.getId());
	}

	public DealManager getDealManager() {
		return dealManager;
	}

	@Resource
	public void setDealManager(DealManager dealManager) {
		this.dealManager = dealManager;
	}

	public ConvertVideo getConvertVideo() {
		return convertVideo;
	}

	@Resource
	public void setConvertVideo(ConvertVideo convertVideo) {
		this.convertVideo = convertVideo;
	}

	public FileDeal getFileDeal() {
		return fileDeal;
	}

	@Resource
	public void setFileDeal(FileDeal fileDeal) {
		this.fileDeal = fileDeal;
	}

}
