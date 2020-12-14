package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.form.AppApi10101Form;

/** 
* @ClassName: AppApi008Service 
* @Description: 网点信息查询
* @author Caozhongyan
* @date Sep 27, 2013 2:50:40 PM   
* 
*/ 
public interface AppApi101Service {
	public List<Mi201> appApi10101Select(AppApi10101Form form) throws Exception;
	public List<Mi201> appApi10102Select(AppApi10101Form form) throws Exception;
	public Mi201 appApi10103Select(String websiteName, String centerid, String channel) throws Exception;
	public List<Mi201> appApi10104Select(AppApi10101Form form) throws Exception;
	public Mi201 appApi10105Select(String websiteCode, String centerid) throws Exception;
	//获取对应网点编码的网点名称
	public List<Mi201> getWebNameById(String centerid, String websitecode) throws Exception;
}
