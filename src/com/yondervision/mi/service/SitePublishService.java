package com.yondervision.mi.service;

import javax.servlet.http.HttpServletRequest;

/** 
* @ClassName: SitePublishService
* @Description:站点发布处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface SitePublishService {
	/**
	 * 首页静态页面生成
	 * @param reqUrl
	 * @throws Exception
	 */
	public void indexStatic(String centerId, HttpServletRequest request) throws Exception;
	
	/**
	 * 栏目静态页面生成
	 * @param reqUrl
	 * @throws Exception
	 */
	public void classficationStatic(String centerId, HttpServletRequest request) throws Exception;
	
	/**
	 * 内容静态页面生成
	 * @param reqUrl
	 * @throws Exception
	 */
	public void contentStatic(String centerId, HttpServletRequest request) throws Exception;

}
