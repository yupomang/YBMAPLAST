package com.yondervision.mi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi40301Result;

/** 
* @ClassName: AppApi403Service 
* @Description: 软件更新
* @author Caozhongyan
* @date Oct 12, 2013 3:39:28 PM   
* 
*/ 
public interface AppApi403Service {
	/**
	 * @deprecated APP版本更新检查
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AppApi40301Result appapi40301(AppApiCommonForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;	
	/**
	 * @deprecated 二维码查询
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AppApi40301Result appapi40302(AppApiCommonForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public boolean appapi40303(AppApiCommonForm form) throws Exception;
}
