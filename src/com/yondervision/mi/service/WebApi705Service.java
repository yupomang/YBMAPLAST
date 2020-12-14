package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.form.WebApi70501Form;
import com.yondervision.mi.form.WebApi70502Form;
import com.yondervision.mi.form.WebApi70503Form;
import com.yondervision.mi.form.WebApi70504Form;
import com.yondervision.mi.form.WebApi70505Form;
import com.yondervision.mi.result.WebApi70504_queryResult;

/** 
* @ClassName: WebApi705Service 
* @Description:新闻发布处理接口-无期次
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi705Service {
	/**
	 * 新闻新增
	 */
	public void webapi70501(WebApi70501Form form, String reqUrl) throws Exception;
	
	/**
	 * 新闻删除
	 */
	public int webapi70502(WebApi70502Form form) throws Exception;
	
	/**
	 * 新闻修改
	 */
	public int webapi70503(WebApi70503Form form, String reqUrl) throws Exception;
	
	/**
	 * 新闻查询-分页
	 */
	public WebApi70504_queryResult webapi70504(WebApi70504Form form) throws Exception;
	
	/**
	 * 新闻查询-根据seqno
	 */
	public List<Mi701WithBLOBs> webapi70505(WebApi70505Form form) throws Exception;
}
