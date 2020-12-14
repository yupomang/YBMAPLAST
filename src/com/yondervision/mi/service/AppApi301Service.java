package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi102;
import com.yondervision.mi.form.AppApi30101Form;

/** 
* @ClassName: AppApi008Service 
* @Description: 手机APP更多处理
* @author Caozhongyan
* @date Sep 27, 2013 2:50:40 PM   
* 
*/ 
public interface AppApi301Service {
	public List<Mi102> appApi30101Select(AppApi30101Form form) throws Exception;	
}
