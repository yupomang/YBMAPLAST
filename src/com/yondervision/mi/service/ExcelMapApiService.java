package com.yondervision.mi.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** 
* @ClassName: ExcelMapApiService 
* @Description: 关于excel的java类
* @author Caozhongyan
* @date Oct 18, 2013 2:37:05 PM   
* 
*/ 
public interface ExcelMapApiService {
	 public String checkResult="";
	 public String checkFileName="";
	 public String getCheckResult();
	 public void setCheckResult(String checkResult);
	 public String getCheckFileName();
	 public void setCheckFileName(String checkFileName);
	 public void  excelMapToCheck(String loginid,String cityname, String cityCode,Map<String,String> ruleMap, String tableName ,java.io.InputStream is, HttpServletRequest request, HttpServletResponse response) throws Exception;
	 public void  excelMapToDB(String loginid,String cityname, String cityCode,Map<String,String> ruleMap, String tableName ,String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
