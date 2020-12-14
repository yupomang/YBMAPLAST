package com.yondervision.mi.service;

import com.yondervision.mi.form.WebApi70602Form;
import com.yondervision.mi.form.WebApi70604Form;
import com.yondervision.mi.form.WebApi70605Form;
import com.yondervision.mi.form.WebApi70606Form;
import com.yondervision.mi.result.WebApi70604_queryResult;
import com.yondervision.mi.result.WebApi70605_queryResult;
import com.yondervision.mi.result.WebApi70606_queryResult;

/** 
* @ClassName: WebApi706Service 
* @Description:报刊-新闻评论维护处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi706Service {
	
	/**
	 * 新闻评论删除
	 */
	public void webapi70602(WebApi70602Form form) throws Exception;
	
	/**
	 * 新闻评论查询-评论信息分页
	 */
	public WebApi70604_queryResult webapi70604(WebApi70604Form form) throws Exception;
	
	/**
	 * 新闻评论查询-新闻标题列表查询分页
	 */
	public WebApi70605_queryResult webapi70605(WebApi70605Form form) throws Exception;
	
	/**
	 * 新闻评论查询-评论查询（根据新闻seqno）分页
	 */
	public WebApi70606_queryResult webapi70606(WebApi70606Form form) throws Exception;
}
