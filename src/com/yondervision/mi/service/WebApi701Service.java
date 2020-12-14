package com.yondervision.mi.service;

import com.yondervision.mi.form.WebApi70101Form;
import com.yondervision.mi.form.WebApi70102Form;
import com.yondervision.mi.form.WebApi70103Form;
import com.yondervision.mi.form.WebApi70104Form;
import com.yondervision.mi.form.WebApi70105Form;
import com.yondervision.mi.result.WebApi70104_queryResult;

/** 
* @ClassName: WebApi701Service 
* @Description:报刊期次维护处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi701Service {
	/**
	 * 报刊期次新增
	 */
	public void webapi70101(WebApi70101Form form) throws Exception;
	
	/**
	 * 报刊期次删除
	 */
	public void webapi70102(WebApi70102Form form) throws Exception;
	
	/**
	 * 报刊期次修改
	 */
	public int webapi70103(WebApi70103Form form) throws Exception;
	
	/**
	 * 报刊期次查询-分页
	 */
	public WebApi70104_queryResult webapi70104(WebApi70104Form form) throws Exception;
	
	/**
	 * 报刊期次发布
	 */
	public int webapi70105(WebApi70105Form form) throws Exception;
	
	/**
	 * 报刊期次查询
	 */
//	public List<Mi702> webapi70105(WebApi70104Form form) throws Exception;	
}
