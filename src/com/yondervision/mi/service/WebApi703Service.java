package com.yondervision.mi.service;

import com.yondervision.mi.form.WebApi70302Form;
import com.yondervision.mi.form.WebApi70304Form;
import com.yondervision.mi.form.WebApi70305Form;
import com.yondervision.mi.form.WebApi70306Form;
import com.yondervision.mi.result.WebApi70304_queryResult;
import com.yondervision.mi.result.WebApi70305_queryResult;
import com.yondervision.mi.result.WebApi70306_queryResult;

/** 
* @ClassName: WebApi703Service 
* @Description:报刊-新闻评论维护处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi703Service {
	
	/**
	 * 新闻评论删除
	 */
	public void webapi70302(WebApi70302Form form) throws Exception;
	
	/**
	 * 新闻评论查询-评论信息分页
	 */
	public WebApi70304_queryResult webapi70304(WebApi70304Form form) throws Exception;
	
	/**
	 * 新闻评论查询-新闻标题列表查询分页
	 */
	public WebApi70305_queryResult webapi70305(WebApi70305Form form) throws Exception;
	
	/**
	 * 新闻评论查询-评论查询（根据新闻seqno）分页
	 */
	public WebApi70306_queryResult webapi70306(WebApi70306Form form) throws Exception;
}
