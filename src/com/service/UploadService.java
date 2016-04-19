package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dao.mgr.UploadManager;
import com.exception.UploadException;
import com.vo.UploadInfo;

@Component
public class UploadService {
	private UploadManager uploadManager;
	
	public String upload(File upload, String uploadPath ,String uploadFileName, String fileName,List<String>errors) throws UploadException {
		InputStream is = null;
		OutputStream os = null;

		try {
	
			// set the file name
			if (fileName.trim().equals("")) {
				fileName = uploadFileName;
			} else {
				try {
					fileName = fileName
							+ uploadFileName.substring(uploadFileName
									.lastIndexOf("."));
				} catch (IndexOutOfBoundsException e) {
				}
			}
			is = new FileInputStream(upload);
	
			os = new FileOutputStream(new File(uploadPath, fileName));
			
			byte[] buffer = new byte[8192];
			int length = 0;
	
			while (-1 != (length = is.read(buffer, 0, buffer.length))) {
				os.write(buffer);
			}
		} catch (FileNotFoundException e) {
			errors.add(e.getMessage());
			throw new UploadException(e.getMessage());
		} catch (IOException e) {
			errors.add(e.getMessage());
			throw new UploadException(e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				errors.add(e.getMessage());
				throw new UploadException(e.getMessage());
			}
		}
		
		return fileName==null? uploadFileName : fileName;
	}

	public List<String> uploads(UploadInfo info) {
		List<String> errors = new ArrayList<String>();
		
		if(info.getUpload() == null || info.getUpload().size() == 0){
			errors.add("您没有选择任何文件");
		}else{
			try{
				for(int index = 0;index < info.getUpload().size();index++){
					
					//execute save
					String fileName = upload(info.getUpload().get(index)
							,uploadManager.getPath(info.getDid())
							,info.getUploadFileName().get(index)
							,info.getFileName().get(index)
							,errors);

					//insert into database
					uploadManager.save(info.getUserid(),info.getDid(),info.getUploadPath(),fileName,info.getUpload().get(index).length(),errors);
				}
			}catch(UploadException e){
				//rollback
				errors.add("already rollback !");
			}
		}
		
		return errors;
	}

	public String getUploadPath(){
		return uploadManager.getFirstDir().getPath();
	}
	
	public UploadManager getUploadManager() {
		return uploadManager;
	}
	
	@Resource
	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}

}
