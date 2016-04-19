package com.action;

import com.exception.OutlineDowloadException;
import com.opensymphony.xwork2.ActionSupport;
import com.service.OutlineDowloadService;

public class OutlineDownloadAction extends ActionSupport{
	private String link;
	private int did;
	private OutlineDowloadService outlineDowloadService;
	private String result;
	private String aria2Path;
	
	@Override
	public String execute(){
		try{
			outlineDowloadService.dowload(aria2Path,link,did);
			result = "添加下载任务成功!";
		}catch(OutlineDowloadException e){
			result = e.getMessage();
		}
		
		return SUCCESS;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public OutlineDowloadService getOutlineDowloadService() {
		return outlineDowloadService;
	}
	public void setOutlineDowloadService(OutlineDowloadService outlineDowloadService) {
		this.outlineDowloadService = outlineDowloadService;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getAria2Path() {
		return aria2Path;
	}
	public void setAria2Path(String aria2Path) {
		this.aria2Path = aria2Path;
	}
}
