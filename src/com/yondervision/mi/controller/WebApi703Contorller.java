package com.yondervision.mi.controller;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.WebApi70302Form;
import com.yondervision.mi.form.WebApi70304Form;
import com.yondervision.mi.form.WebApi70305Form;
import com.yondervision.mi.form.WebApi70306Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.result.WebApi70304_queryResult;
import com.yondervision.mi.result.WebApi70305_queryResult;
import com.yondervision.mi.result.WebApi70306_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi703Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi703Contorller 
* @Description:评论维护
* @author gongq
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi703Contorller {
	@Autowired
	private WebApi703Service webApi703ServiceImpl;
	public void setWebApi703ServiceImpl(WebApi703Service webApi703ServiceImpl) {
		this.webApi703ServiceImpl = webApi703ServiceImpl;
	}
	
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
	/**
	 * 评论删除
	 * @param form 评论删除信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70302.{ext}")
	public String webapi70302(WebApi70302Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("评论删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi703ServiceImpl.webapi70302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
	
	/**
	 * 评论列表分页查询
	 * @param form 评论查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70304.{ext}")
	public String webapi70304(WebApi70304Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("评论查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		WebApi70304_queryResult queryResult = webApi703ServiceImpl.webapi70304(form);
		if(queryResult.getList703().isEmpty()||queryResult.getList703().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("新闻评论Mi703", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"评论信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList703());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
	
	/**
	 * 新闻标题列表分页查询
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70305.{ext}")
	public String webapi70305(WebApi70305Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("新闻标题列表查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		WebApi70305_queryResult queryResult = webApi703ServiceImpl.webapi70305(form);
		if(queryResult.getList701().isEmpty()||queryResult.getList701().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("新闻信息Mi701", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"新闻");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList701());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
	
	/**
	 * 对应新闻seqno的评论信息的分页查询
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70306.{ext}")
	public String webapi70306(WebApi70306Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("对应新闻seqno的评论信息的分页查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		WebApi70306_queryResult queryResult = webApi703ServiceImpl.webapi70306(form);
		if(queryResult.getList703().isEmpty()||queryResult.getList703().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("新闻评论Mi703", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"评论信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList703());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
	
	/**
	 * 根据报刊期次，Mi701获取页面版块、栏目下拉选择框的级联数据
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/getForumColumnsJsonArray.{ext}")
	public String getforumJsonArray(WebApiCommonForm form, String centeridTmp, String classification, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据报刊期次，获取页面版块、栏目下拉选择框的级联数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			

		List<NewspapersTitleInfoBean> forumList = codeListApi001Service.getForumByTimesFromMi701(centeridTmp, classification);
		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesFromMi701(centeridTmp, classification);
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray forumJsonArray = mapper.convertValue(forumList, JSONArray.class);
		JSONArray columnsJsonArray = mapper.convertValue(columnsList, JSONArray.class);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("forumJsonArray", forumJsonArray);
		modelMap.put("columnsJsonArray", columnsJsonArray);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
	
	/**
	 * 根据报刊期次、版块，Mi701获取页面栏目下拉选择框的级联数据
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/getColumnsJsonArray.{ext}")
	public String getColumnsJsonArray(WebApiCommonForm form, String centeridTmp, String classification, String newspaperforum, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据报刊期次、版块，获取页面栏目下拉选择框的级联数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			

		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesForumFromMi701(
				centeridTmp, classification, newspaperforum);
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray columnsJsonArray = mapper.convertValue(columnsList, JSONArray.class);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("columnsJsonArray", columnsJsonArray);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page703/page70301";
	}
}
