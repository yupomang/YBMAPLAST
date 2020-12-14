package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.yondervision.mi.dto.CMi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.form.WebApi70201Form;
import com.yondervision.mi.form.WebApi70202Form;
import com.yondervision.mi.form.WebApi70203Form;
import com.yondervision.mi.form.WebApi70204Form;
import com.yondervision.mi.form.WebApi70205Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.result.WebApi70204_queryResult;
import com.yondervision.mi.result.WebApi70205_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi702Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: WebApi702Contorller 
* @Description: 新闻发布
* @author gongqi  
* @date July 18, 2014 9:33:25 PM   
*/ 
@Controller
public class WebApi702Contorller {

	@Autowired
	private WebApi702Service webApi702ServiceImpl;
	public void setWebApi702ServiceImpl(WebApi702Service webApi702ServiceImpl) {
		this.webApi702ServiceImpl = webApi702ServiceImpl;
	}
	
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	/**
	 * 增加新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70201.{ext}")
	public String webapi70201(WebApi70201Form form , ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "增加新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String rquestUrlTmp = request.getRequestURL().toString();
		String reqUrl = rquestUrlTmp.substring(0,rquestUrlTmp.length()-23);
		System.out.println("reqUrl="+reqUrl);
		
		webApi702ServiceImpl.webapi70201(form, reqUrl);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page702/page70201";
	}
	
	/**
	 * 删除新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70202.{ext}")
	public String webapi70202(WebApi70202Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "删除新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi702ServiceImpl.webapi70202(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page702/page70201";
	}
	
	/**
	 * 修改新闻
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/webapi70203.{ext}")
	public String webapi70203(WebApi70203Form form , ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "修改新闻";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		String rquestUrlTmp = request.getRequestURL().toString();
		String reqUrl = rquestUrlTmp.substring(0,rquestUrlTmp.length()-23);
		System.out.println("reqUrl="+reqUrl);
		
		webApi702ServiceImpl.webapi70203(form,reqUrl);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page702/page70201";
	}
	
	/**
	 * 信息查询--分页
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70204.{ext}")
	public String webapi70204(WebApi70204Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("分页查询新闻信息");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		// 业务处理
		WebApi70204_queryResult queryResult = webApi702ServiceImpl.webapi70204(form);
		if(queryResult.getList701().isEmpty()||queryResult.getList701().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("MI701", "form"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"新闻");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("totalPage", queryResult.getTotalPage());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList701());
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page702/page70201";
	}
	
	/**
	 * 信息查询-根据seqno
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70205.{ext}")
	public String webapi70205(WebApi70205Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据seqno信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		List<Mi701WithBLOBs> mi701List = webApi702ServiceImpl.webapi70205(form);//有且只有一条
		
		WebApi70205_queryResult queryResult = new WebApi70205_queryResult();
		Mi701WithBLOBs mi701 = (Mi701WithBLOBs)mi701List.get(0);
		queryResult.setSeqno(mi701.getSeqno());
		queryResult.setCenterid(mi701.getCenterid());
		queryResult.setClassification(mi701.getClassification());
		queryResult.setNewspaperforum(mi701.getNewspaperforum());
		queryResult.setNewspapercolumns(mi701.getNewspapercolumns());
		queryResult.setTitle(mi701.getTitle());
		queryResult.setCitedtitle(mi701.getCitedtitle());
		queryResult.setSubtopics(mi701.getSubtopics());
		queryResult.setSource(mi701.getSource());
		queryResult.setIntroduction(mi701.getIntroduction());
		queryResult.setBlurbs(mi701.getBlurbs());
		queryResult.setReleasetime(mi701.getReleasetime());
		queryResult.setImage(mi701.getImage());
		queryResult.setValidflag(mi701.getValidflag());
		queryResult.setDatemodified(mi701.getDatemodified());
		queryResult.setDatecreated(mi701.getDatecreated());
		queryResult.setLoginid(mi701.getLoginid());
		queryResult.setPraisecounts(mi701.getPraisecounts());
		queryResult.setFreeuse1(mi701.getFreeuse1());
		queryResult.setFreeuse2(mi701.getFreeuse2());
		queryResult.setFreeuse3(mi701.getFreeuse3());
		queryResult.setFreeuse4(mi701.getFreeuse4());
		queryResult.setFreeuse5(mi701.getFreeuse5());
		queryResult.setFreeuse6(mi701.getFreeuse6());
		queryResult.setContent(mi701.getContent());

		List<Mi704> forumList = codeListApi001Service.getForumByTimesFromMi704(form.getCenterId(), mi701.getClassification());
		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesForumFromMi704(
				form.getCenterId(), mi701.getClassification(), mi701.getNewspaperforum());
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray forumJsonArray = mapper.convertValue(forumList, JSONArray.class);
		JSONArray columnsJsonArray = mapper.convertValue(columnsList, JSONArray.class);
		queryResult.setForumJsonArray(forumJsonArray);
		queryResult.setColumnsJsonArray(columnsJsonArray);
		//获取待编辑记录的期次所包含的版块jsonarray
		//String times = mi701List.get(0).getClassification();
		//String forum = mi701List.get(0).getNewspaperforum();
		//List<Mi704> forumList = codeListApi001Service.getForumByTimesFromMi704(form.getCenterId(), times);
		//List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesForumFromMi704(
		//		form.getCenterId(), times, forum);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", queryResult);
		//modelMap.put("resultList", mi701List);
		//ObjectMapper mapper = new  ObjectMapper();
		//modelMap.put("forumJsonArray", mapper.convertValue(forumList, JSONArray.class));
		//modelMap.put("columnsJsonArray", mapper.convertValue(columnsList, JSONArray.class));
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page702/page70201";
	}
	
	/**
	 * 增加/修改弹出对话所属栏目默认图标获取-根据classification
	 * @param form 信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70206.{ext}")
	public String webapi70206(CMi701 form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据所属栏目获取 默认图标");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType"+form.getClassification());
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("defaultImgUrl", defaultImgUrl);		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page702/page70201";
	}

	/**
	 * 根据报刊期次，Mi704获取页面版块、栏目下拉选择框的级联数据
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/getForumColumnsJsonArrayFromMi704.{ext}")
	public String getforumJsonArray(WebApiCommonForm form, String centeridTmp, String classification, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据报刊期次，获取页面版块、栏目下拉选择框的级联数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			

		List<Mi704> forumList = codeListApi001Service.getForumByTimesFromMi704(centeridTmp, classification);
		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesFromMi704(centeridTmp, classification);
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray forumJsonArray = mapper.convertValue(forumList, JSONArray.class);
		JSONArray columnsJsonArray = mapper.convertValue(columnsList, JSONArray.class);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("forumJsonArray", forumJsonArray);
		modelMap.put("columnsJsonArray", columnsJsonArray);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page702/page70201";
	}
	
	/**
	 * 根据报刊期次、版块，Mi704获取页面栏目下拉选择框的级联数据
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/getColumnsJsonArrayFromMi704.{ext}")
	public String getColumnsJsonArray(WebApiCommonForm form, String centeridTmp, String classification, String newspaperforum, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据报刊期次、版块，获取页面栏目下拉选择框的级联数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			

		List<NewspapersTitleInfoBean> columnsList = codeListApi001Service.getColumnsByTimesForumFromMi704(
				centeridTmp, classification, newspaperforum);
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray columnsJsonArray = mapper.convertValue(columnsList, JSONArray.class);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("columnsJsonArray", columnsJsonArray);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page702/page70201";
	}
}
