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
import com.yondervision.mi.form.AppApi70203Form;
import com.yondervision.mi.form.AppApi70204Form;
import com.yondervision.mi.form.AppApi70205Form;
import com.yondervision.mi.form.AppApi70206Form;
import com.yondervision.mi.form.AppApi70208Form;
import com.yondervision.mi.form.AppApi702CommonForm;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi70102Result;
import com.yondervision.mi.result.AppApi70103Result;
import com.yondervision.mi.result.AppApi70104Result;
import com.yondervision.mi.result.AppApi70108Result;
import com.yondervision.mi.result.AppApi70109Result;
import com.yondervision.mi.result.AppApi70110Result;
import com.yondervision.mi.result.AppApi70201Result;
import com.yondervision.mi.result.AppApi70202Result;
import com.yondervision.mi.result.AppApi70203Result;
import com.yondervision.mi.result.AppApi70207Result;
import com.yondervision.mi.result.AppApi70208Result;

/** 
* @ClassName: AppApi702Service 
* @Description: 公积金报-移动客户端访问处理-无期次
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface AppApi702Service {
	
	/*
	 * 初始页面访问数据获取处理
	 */
	public AppApi70201Result appapi70201(AppApi702CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 根据版块，进行信息展示数据获取处理
	 */
	public AppApi70202Result appapi70202(AppApi702CommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 根据新闻ID,进行新闻详细页面的展示数据的获取
	 */
	public AppApi70203Result appapi70203(AppApi70203Form form) throws Exception;
	
	/*
	 * 对新闻进行评论
	 */
	public void appapi70204(AppApi70204Form form) throws Exception;
	
	/*
	 * 对新闻进行点赞
	 */
	public void appapi70205(AppApi70205Form form) throws Exception;
	
	/*
	 * 对评论进行点赞
	 */
	public void appapi70206(AppApi70206Form form) throws Exception;
	
	/*
	 * 订阅设置-获取待订阅的栏目范围
	 */
	public List<AppApi70207Result> appapi70207(AppApiCommonForm form,HttpServletRequest request) throws Exception;
	
	/*
	 * 我的订阅-初始页面访问数据获取处理
	 */
	public AppApi70208Result appapi70208(AppApi70208Form form,HttpServletRequest request) throws Exception;
}
