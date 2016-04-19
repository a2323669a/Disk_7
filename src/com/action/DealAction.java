package com.action;

import java.io.IOException;
import java.util.HashMap;
import org.junit.Test;
import com.exception.DealException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.DealService;
import com.vo.DealInfo;

public class DealAction extends ActionSupport implements ModelDriven<DealInfo>{
	DealService dealService;
	DealInfo dealInfo = new DealInfo();
	String dir;
	String fileName;
	String result;
	
	public String execute() throws IOException{
		String actionResult = "";
		HashMap<String,String> map = new HashMap<String,String>();
		try{
			dealService.deal(dealInfo,map);
			dir = map.get("dir");
			fileName = map.get("fileName");
			result = map.get("result");
		}catch(DealException e){
			result = e.getMessage();
			return SUCCESS;
		}
System.out.println("--------r"+map.get("actionResult"));
		return map.get("actionResult");
	}
	
	@Override
	public DealInfo getModel() {
		return dealInfo;
	}

	public DealService getDealService() {
		return dealService;
	}

	public void setDealService(DealService dealService) {
		this.dealService = dealService;
	}

	public DealInfo getDealInfo() {
		return dealInfo;
	}

	public void setDealInfo(DealInfo dealInfo) {
		this.dealInfo = dealInfo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
