package com.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.UploadService;
import com.vo.UploadInfo;

public class FileUploadAction extends ActionSupport implements ModelDriven<UploadInfo> {
	private UploadInfo info = new UploadInfo();
	private String result;
	private UploadService uploadService;

	@Override
	public String execute() {
		info.setUploadPath(uploadService.getUploadPath());
		return upload();
	}

	private String upload() {
		List<String> errors = uploadService.uploads(info);
		if (errors.isEmpty()) {
			result = "±£´æ³É¹¦ !";
			return SUCCESS;
		}
		for(String error : errors){
			addFieldError("upload",error);
		}
		return ERROR;
	}

	@Override
	public UploadInfo getModel() {
		return info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	public UploadInfo getInfo() {
		return info;
	}

	public void setInfo(UploadInfo info) {
		this.info = info;
	}

}
