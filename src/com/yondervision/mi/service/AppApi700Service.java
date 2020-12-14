	package com.yondervision.mi.service;

	import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi70002Form;
import com.yondervision.mi.form.AppApi70004Form;
import com.yondervision.mi.form.AppApi70008Form;
import com.yondervision.mi.form.AppApi70009Form;
import com.yondervision.mi.form.AppApi70010Form;
import com.yondervision.mi.form.AppApi70012Form;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.result.AppApi70002Result;
import com.yondervision.mi.result.AppApi70004Result;
import com.yondervision.mi.result.AppApi70009Result;
import com.yondervision.mi.result.AppApi70010Result;
import com.yondervision.mi.result.AppApi70013Result;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.result.ViewItemBean;

	/** 
	* @ClassName: AppApi700Service 
	* @Description: 内容管理
	* @author gongqi
	* @date July 18, 2014 9:33:25 PM  
	* 
	*/ 
	public interface AppApi700Service {
		public List<AppApi70001Result> appapi70001(AppApi70001Form form, HttpServletRequest request) throws Exception;
		public List<AppApi70002Result> appapi70002(AppApi70002Form form, HttpServletRequest request) throws Exception;
		public int appapi70003(AppApi70004Form form) throws Exception;
		public List<AppApi70001Result> appapi70004(AppApi70004Form form) throws Exception;
		public int appapi70005(AppApi70004Form form) throws Exception;
		public List<Mi707> getClassificationList(String centerid,String channel) throws Exception;//获取对应城市id下的所有有效栏目列表（不分开放关闭）
		public List<AppApi70004Result> appapi70006(AppApi70001Form form, HttpServletRequest request) throws Exception;
		
		//获取某一栏目下的某条新闻的详细内容
		public Mi701WithBLOBs appapi70007(String centerid, String classification, String num, String keyword, String seqno) throws Exception;
		
		//获取对应页面展示展示项的配置内容
		public List<ViewItemBean> appapi70008(AppApi70008Form form) throws Exception;
		//获取对应页面itemid的配置内容(适用于传递的itemid为父级的itemid),
		//返回该itemid包含的所有配置子项及对应子项的内容列表,如对应子项为空，则默认取第一个子项的内容列表返回
		public AppApi70009Result appapi70009(AppApi70009Form form,String yingyong) throws Exception;
		//获取对应itemid的所有归属父级列表
		public AppApi70010Result appapi70010(AppApi70010Form form) throws Exception;
		// 获取某一栏目（包含此栏目）下所有子栏目包含的内容列表，按发布时间倒序
		public NewsBean appapi70012(AppApi70012Form form, HttpServletRequest request) throws Exception;
		//对应展示项的所有子项+其详细信息查询
		public List<AppApi70013Result> appapi70013(AppApi70009Form form,HttpServletRequest request) throws Exception;
	}