package com.yondervision.mi.controller;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.service.ExcelApi001Service;

@Controller
public class ExcelImportContorller {
	@Autowired
	ExcelApi001Service excelApi001Service=null;
	
	
	public ExcelApi001Service getExcelApi001Service() {
		return excelApi001Service;
	}

	public void setExcelApi001Service(ExcelApi001Service excelApi001Service) {
		this.excelApi001Service = excelApi001Service;
	}
	
	@RequestMapping("/exceltest.html")
	public String exceltest(){ 
		return "exceltest";		
	}
	
	@RequestMapping("/excelToDB.do")
	public String excelToDB(String path,String ruleMapStr, String tableName ,@RequestParam  MultipartFile  excelfile){
		Logger log=LoggerUtil.getLogger();
		log.info("上传文件 文件名："+excelfile.getOriginalFilename()+",文件大小："+excelfile.getSize());
		JSONObject json=JSONObject.fromObject(ruleMapStr);
		try{
		    excelApi001Service.excelToDB(json, tableName, excelfile.getInputStream());
		}catch(Exception e){
			e.printStackTrace();	
			log.error(e);
	    	throw new TransRuntimeErrorException("000001",e.getMessage());
		}  
		return  path;		
	} 
}
