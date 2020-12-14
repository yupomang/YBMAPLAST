package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.form.AppApi70104Form;
import com.yondervision.mi.form.AppApi70105Form;
import com.yondervision.mi.form.AppApi70106Form;
import com.yondervision.mi.form.AppApi70107Form;
import com.yondervision.mi.form.AppApi70109Form;
import com.yondervision.mi.form.AppApi70110Form;
import com.yondervision.mi.form.AppApi701CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70101Result;
import com.yondervision.mi.result.AppApi70102Result;
import com.yondervision.mi.result.AppApi70103Result;
import com.yondervision.mi.result.AppApi70104Result;
import com.yondervision.mi.result.AppApi70108Result;
import com.yondervision.mi.result.AppApi70109Result;
import com.yondervision.mi.result.AppApi70110Result;

/** 
* @ClassName: AppApi701Service 
* @Description: 公积金报-移动客户端访问处理
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface AppApi701Service {
	
	/*
	 * 初始页面访问数据获取处理
	 */
	public AppApi70101Result appapi70101(AppApi701CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 根据报刊期次，进行信息展示数据获取处理
	 */
	public AppApi70102Result appapi70102(AppApi701CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 根据报刊期次，版块，进行信息展示数据获取处理
	 */
	public AppApi70103Result appapi70103(AppApi701CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 根据新闻ID,进行新闻详细页面的展示数据的获取
	 */
	public AppApi70104Result appapi70104(AppApi70104Form form) throws Exception;
	
	/*
	 * 对新闻进行评论
	 */
	public void appapi70105(AppApi70105Form form) throws Exception;
	
	/*
	 * 对新闻进行点赞
	 */
	public void appapi70106(AppApi70106Form form) throws Exception;
	
	/*
	 * 对评论进行点赞
	 */
	public void appapi70107(AppApi70107Form form) throws Exception;
	
	/*
	 * 订阅设置-获取待订阅的栏目范围
	 */
	public List<AppApi70108Result> appapi70108(AppApiCommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 我的订阅-初始页面访问数据获取处理
	 */
	public AppApi70109Result appapi70109(AppApi70109Form form,HttpServletRequest request) throws Exception;
	
	/*
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理
	 */
	public AppApi70110Result appapi70110(AppApi70110Form form,HttpServletRequest request) throws Exception;
	
	/*
	 * 订阅设置-获取待订阅的版块范围
	 */
	public List<AppApi70108Result> appapi70111(AppApiCommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 我的订阅-初始页面访问数据获取处理（根据订阅版块范围）
	 */
	public AppApi70109Result appapi70112(AppApi70109Form form,HttpServletRequest request) throws Exception;
	
	/*
	 * 我的订阅-根据报刊期次，进行信息展示数据获取处理（及订阅版块范围）
	 */
	public AppApi70110Result appapi70113(AppApi70110Form form,HttpServletRequest request) throws Exception;
	
	/*
	 * 今日新闻初始页面获取处理
	 */
	public AppApi70101Result appapi70114(AppApi701CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 往日新闻初始页面访问数据获取处理
	 */
	public AppApi70101Result appapi70115(AppApi701CommonForm form,HttpServletRequest request) throws Exception;
}
