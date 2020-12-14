/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     WebApiUploadFileImpl.java
 * 创建日期：2013-10-28
 */
package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 *
 */
public class WebApiUploadFileImpl implements WebApiUploadFile {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.service.WebApiUploadFile#uploadFile(java.lang.String, org.springframework.web.multipart.MultipartFile, int)
	 */
	public String uploadFile(String filePath, MultipartFile file, int maxSize)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数校验
		 */
		if (file==null || file.isEmpty()) {
			// TODO 文件为空
			throw new NoRollRuntimeErrorException("", "");
		}
		long fileSize = file.getSize();
		if (fileSize>maxSize*1024) {
			// TODO 文件大小超过限制
			throw new NoRollRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(), "文件应小于"+(maxSize*1024)+"B");
		}
		
		
		String upFileName = new String(file.getOriginalFilename().getBytes(
				"iso8859-1"), "UTF-8");
		int index = upFileName.lastIndexOf(".");
		String fileExt = index<upFileName.length()-1 ? upFileName.substring(index) : "";
		
		/*
		 * 文件上传
		 */
		log.info("Upload img begin!");
		String fileName = CommonUtil.getSystemDateNumOnly() + fileExt;
		String filePatch = SaveFileFromInputStream(file.getInputStream(),
				filePath, fileName);
		log.info(filePatch);
		log.info("Upload img end! fileName="+fileName);
		
		return fileName;
	}
	
	/**
	 * 保存文件
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	private String SaveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		String filePath = path + File.separator + filename;
		System.out.println("SaveFileFromInputStream: "+path);
		File fPath = new File(path);
		if (!fPath.isDirectory()) {
			if(fPath.mkdirs())
				System.out.println("创建文件或文件夹:"+fPath);
		}
		FileOutputStream fs = new FileOutputStream(filePath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
		return filePath;
	}

}
