package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.form.WebApi70401Form;
import com.yondervision.mi.form.WebApi70402Form;
import com.yondervision.mi.form.WebApi70403Form;
import com.yondervision.mi.form.WebApi70404Form;
import com.yondervision.mi.result.WebApi70404_queryResult;

/** 
* @ClassName: WebApi704Service 
* @Description:版块栏目配置处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi704Service {
	/**
	 * 版块栏目配置新增
	 */
	public void webapi70401(WebApi70401Form form) throws Exception;
	
	/**
	 * 版块栏目配置删除
	 */
	public void webapi70402(WebApi70402Form form) throws Exception;
	
	/**
	 * 版块栏目配置修改
	 */
	public void webapi70403(WebApi70403Form form) throws Exception;
	
	/**
	 * 版块栏目配置查询
	 */
	public List<WebApi70404_queryResult> webapi70404(WebApi70404Form form) throws Exception;
}
