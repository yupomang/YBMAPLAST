package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.form.AppApiCommonForm;


/** 
* @ClassName: WebApi101Service 
* @Description:区域处理
* @author Caozhongyan
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
public interface AppApi202Service {
	/**
	 * 楼盘新增
	 */
	public List<Mi202> appapi20201(AppApiCommonForm form) throws Exception;	
}
