package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi70002Form;
import com.yondervision.mi.form.AppApi70003Form;
import com.yondervision.mi.form.AppApi70004Form;
import com.yondervision.mi.form.AppApi70008Form;
import com.yondervision.mi.form.AppApi70009Form;
import com.yondervision.mi.form.AppApi70010Form;
import com.yondervision.mi.form.AppApi70011Form;
import com.yondervision.mi.form.AppApi70012Form;
import com.yondervision.mi.result.AppApi70001Result;
import com.yondervision.mi.result.AppApi70002Result;
import com.yondervision.mi.result.AppApi70003Result;
import com.yondervision.mi.result.AppApi70004Result;
import com.yondervision.mi.result.AppApi70009Result;
import com.yondervision.mi.result.AppApi70010Result;
import com.yondervision.mi.result.AppApi70013Result;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.result.ViewItemBean;
import com.yondervision.mi.service.AppApi700Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.HtmlUtil;
import com.yondervision.mi.util.PropertiesReader;

/**
 * @ClassName: AppApi700Contorller
 * @Description: 新闻动态
 * @author gongqi
 * @date July 16, 2014 9:33:25 PM
 */
@Controller
public class AppApi700Contorller {
	@Autowired
	private AppApi700Service appApi700ServiceImpl = null;
	public void setAppApi700ServiceImpl(AppApi700Service appApi700ServiceImpl) {
		this.appApi700ServiceImpl = appApi700ServiceImpl;
	}
	
	/**
	 * 内容列表获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70001.{ext}")
	public String appapi70001(AppApi70001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getClassification())){
			log.error(ERROR.PARAMS_NULL.getLogText("classification"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"获取内容的所属分类");
		}
		String jsoncallback = request.getParameter("callback");
		
		List<AppApi70001Result> resultList = appApi700ServiceImpl.appapi70001(form, request);
		if(CommonUtil.isEmpty(resultList)){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"信息动态");
		}
		
		if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", resultList);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
		}else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", resultList);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
		}

	}
	
	/**
	 * 内容详细获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70002.{ext}")
	public String appapi70002(AppApi70002Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("内容详细获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getTitleId())){
			log.error(ERROR.PARAMS_NULL.getLogText("titleId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容序号");
		}
		String jsoncallback = request.getParameter("callback");
		
		List<AppApi70002Result> resultList = appApi700ServiceImpl.appapi70002(form, request);
		if(CommonUtil.isEmpty(resultList)){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"详细信息");
		}
		
		if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", resultList);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
		}else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", resultList);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
		}
		

	}
	
	/**
	 * 分类列表（名称）获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70003.{ext}")
	public String appapi70003(AppApi70003Form form, ModelMap modelMap,HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("栏目列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getClassifications())){
			log.error(ERROR.PARAMS_NULL.getLogText("待查询的分类ID列表：classifications"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"获取内容的所属分类");
		}
		
		List<AppApi70003Result> resultList = new ArrayList<AppApi70003Result>();
		List<Mi707> classificationList = appApi700ServiceImpl.getClassificationList(form.getCenterId(),request.getAttribute("MI040Pid").toString());
		String[] classifications = form.getClassifications().split(","); 
		String classification = null;
		for(int i = 0; i < classifications.length; i++){
			classification = classifications[i];
			for (int j = 0; j < classificationList.size(); j++){
				if(classification.equals(classificationList.get(j).getItemid())){
					AppApi70003Result result = new AppApi70003Result();
					result.setTitleId(classifications[i]);
					result.setTitleName(classificationList.get(j).getItemval());
					resultList.add(result);
					break;
				}
			}
		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 个人收藏设置
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70004.{ext}")
	public String appapi70004(AppApi70004Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("个人收藏设置");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户名");
		}
		if(CommonUtil.isEmpty(form.getNewsId())){
			log.error(ERROR.PARAMS_NULL.getLogText("newsId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新闻ID");
		}
		int i = appApi700ServiceImpl.appapi70003(form);
		modelMap.clear();
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("collect", String.valueOf(i));
		modelMap.put("newsId", form.getNewsId());
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 新闻收藏列表获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70005.{ext}")
	public String appapi70005(AppApi70004Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻收藏列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户名");
		}
		
		List<AppApi70001Result> resultList = appApi700ServiceImpl.appapi70004(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	
	/**
	 * 新闻收藏列表册除
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70006.{ext}")
	public String appapi70006(AppApi70004Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("新闻收藏列表获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUserId())){
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"用户名");
		}
		if(CommonUtil.isEmpty(form.getNewsId())){
			log.error(ERROR.PARAMS_NULL.getLogText("newsId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"新闻ID");
		}
		
		appApi700ServiceImpl.appapi70005(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
	/**
	 * 摇一摇：随机一项内容获取
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70007.{ext}")
	public String appapi70007(AppApi70001Form form, ModelMap modelMap, HttpServletRequest request) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("摇一摇，内容项获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getClassification())){
			log.error(ERROR.PARAMS_NULL.getLogText("classification"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"获取内容的所属分类");
		}
		List<AppApi70004Result> resultList = appApi700ServiceImpl.appapi70006(form, request);
		if(CommonUtil.isEmpty(resultList)){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"信息动态");
		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 获取对应页面展示展示项的配置内容(改版网站)
	 * 需要传入要获取的展示项的参数  展示项所属名称_展示项id
	 */
	@RequestMapping("/appapi70008.{ext}")
	public String appapi70008(AppApi70008Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对应页面展示展示项的配置内容获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getClassification())){
			log.error(ERROR.PARAMS_NULL.getLogText("待查询的展示项：classification"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"获取待查询的展示项");
		}
		
		String jsoncallback = request.getParameter("callback");
		
		List<ViewItemBean> resultList = new ArrayList<ViewItemBean>();
		resultList = appApi700ServiceImpl.appapi70008(form);
		
	    if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", resultList);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
	    }else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", resultList);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
	    }
	}
	
	/**

	 * 获取对应页面itemid的配置内容(改版网站)
	 * 返回该itemid包含的所有配置子项及对应上传子项（参数curChildViewItemId）的内容列表
	 * 如curChildViewItemId上传为空，则默认取第一个子项的内容列表返回
	 * 如curChildViewItemId上传为all，则默认返回改itemid下的所有的内容列表，且其所包含的配置子项中增加all（全部一项）
	 * 如果传递的itemid（参数parentViewItemId）为空，直接到701中进行内容列表的查询
	 */
	@RequestMapping("/appapi70009.{ext}")
	public String appapi70009(AppApi70009Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对应页面展示展示项的内容获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		String jsoncallback = request.getParameter("callback");
		
		AppApi70009Result qryresult = new AppApi70009Result();
		qryresult = appApi700ServiceImpl.appapi70009(form,request.getAttribute("MI040Pid").toString());
		
	    if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", qryresult);
			
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
	    }else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", qryresult);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}

			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
	    }
	}

	/**
	 * 获取对应curViewItemId的所有上级归属（即其所有的 父级itemid及itemval直至最上层）(改版网站)
	 */
	@RequestMapping("/appapi70010.{ext}")
	public String appapi70010(AppApi70010Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对应当前展示项curViewItemId的归属获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getCurViewItemId())){
			log.error(ERROR.PARAMS_NULL.getLogText("待查询的展示项：curViewItemId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"获取待查询的展示项");
		}
		
		String jsoncallback = request.getParameter("callback");
		
		AppApi70010Result qryresult = new AppApi70010Result();
		qryresult = appApi700ServiceImpl.appapi70010(form);
		
	    if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", qryresult);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
	    }else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", qryresult);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
	    }
	}
	
	/**
	 * 获取某一栏目下的某条新闻的详细内容,并且去掉html标签
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70011.{ext}")
	public String appapi70011(AppApi70011Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("去掉html标签的内容详细获取");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getSeqno()) && CommonUtil.isEmpty(form.getClassification())){
			log.error(ERROR.PARAMS_NULL.getLogText("seqno，classfication"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"待查询内容序号或所属分类中的任意一个参数");
		}
		if(!CommonUtil.isEmpty(form.getClassification())){
			if(CommonUtil.isEmpty(form.getKeyword()) && CommonUtil.isEmpty(form.getNum())){
				log.error(ERROR.PARAMS_NULL.getLogText("keyword，num"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"待查询内容列表位置序号或内容关键字中的任意一个参数");
			}
		}
		System.out.println("appapi70011---keyword=="+form.getKeyword());
		String contentTxt = "";
		Mi701WithBLOBs mi701 = appApi700ServiceImpl.appapi70007(form.getCenterId(),
				form.getClassification(), form.getNum(), form.getKeyword(), form.getSeqno());
		
		/*if(!CommonUtil.isEmpty(mi701)){
			if(CommonUtil.isEmpty(mi701.getContenttxt())){
				if(!CommonUtil.isEmpty(mi701.getContent())){
					contentTxt = HtmlUtil.delHTMLTag(mi701.getContent());
				}else{
					contentTxt = "";
				}
			}else{
				contentTxt = mi701.getContenttxt();
			}
			System.out.println("栏目"+form.getClassification()+"下内容条目"+form.getNum()+"的去掉HTML标签的contentTxt=="+contentTxt);
		}*/
		if(!CommonUtil.isEmpty(mi701)){
			if(!CommonUtil.isEmpty(mi701.getContent())){
					String turnImageUrl  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "turnImageUrl"+form.getCenterId());
					if(turnImageUrl.equals("1")){
						String pathYuan  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath"+form.getCenterId());
						String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appServerPath"+form.getCenterId());
						mi701.setContent(mi701.getContent().replaceAll(pathYuan, path));
					}
				contentTxt = HtmlUtil.delHTMLTag(mi701.getContent());
			}else{
				contentTxt = "";
			}
			System.out.println("栏目"+form.getClassification()+"下内容条目"+form.getNum()+"的去掉HTML标签的contentTxt=="+contentTxt);
		}
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("contenttxt", contentTxt);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";

	}
	
	/**
	 * 获取某一栏目（包含此栏目）下所有子栏目包含的内容列表，按发布时间倒序
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi70012.{ext}")
	public String appapi70012(AppApi70012Form form, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取某一栏目（包含此栏目）下所有子栏目包含的内容列表，按发布时间倒序");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getClassification())){
			log.error(ERROR.PARAMS_NULL.getLogText("栏目ID: classification"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"栏目ID");
		}
		
		String jsoncallback = request.getParameter("callback");
		
		NewsBean result = appApi700ServiceImpl.appapi70012(form, request);
		if(result.getNewsList().isEmpty()||result.getNewsList().size()==0){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"内容列表");
		}
		
		if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("totalPage", result.getTotalPage());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getNewsList());
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
		}else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("total", result.getTotal());
			data.put("totalPage", result.getTotalPage());
			data.put("pageSize", result.getPageSize());
			data.put("pageNumber", result.getPageNumber());
			data.put("rows", result.getNewsList());
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String resresult = "";
		    
		    resresult = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(resresult.getBytes(encoding));
			response.getOutputStream().close();
			return "";
		}
	}
	
	/**
	 * 
	 * 对应展示项的所有子项+其详细信息查询
	 * (appapi70009扩展，查询结果包含所有的子栏目下的内容列表)
	 */
	@RequestMapping("/appapi70013.{ext}")
	public String appapi70013(AppApi70009Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("对应展示项的所有子项+其详细信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心ID：centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		String jsoncallback = request.getParameter("callback");
		
		List<AppApi70013Result> qryresult = new ArrayList<AppApi70013Result>();
		qryresult = appApi700ServiceImpl.appapi70013(form,request);
		
	    if(jsoncallback==null||jsoncallback==""){
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("result", qryresult);
			
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
			return "";
	    }else{
			ObjectMapper mapper = new ObjectMapper();
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("recode", Constants.WEB_SUCCESS_CODE);
			data.put("msg", Constants.WEB_SUCCESS_MSG);
			data.put("result", qryresult);
			JSONObject resJsonObjTmp = mapper.convertValue(data, JSONObject.class);
			String json = resJsonObjTmp.toString();
		    String result = "";
		    
	    	result = jsoncallback+"("+json+");callback";
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}

			response.getOutputStream().write(result.getBytes(encoding));
			response.getOutputStream().close();
			return "";
	    }
	}
}
