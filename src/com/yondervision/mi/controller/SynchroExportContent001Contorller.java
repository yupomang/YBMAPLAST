package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi701;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.form.WebApi70004Form;
import com.yondervision.mi.result.WebApi70004_queryResult;
import com.yondervision.mi.service.WebApi700Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: SynchroExportContent001Contorller 
* @Description: 同步内容-从平台导出
* @author gongqi  
* @date April 13, 2016 9:33:25 AM   
*/ 
@Controller
public class SynchroExportContent001Contorller {
	@Autowired
	private WebApi700Service webApi700ServiceImpl;
	public void setWebApi700ServiceImpl(WebApi700Service webApi700ServiceImpl) {
		this.webApi700ServiceImpl = webApi700ServiceImpl;
	}

//	/**
//	 * 信息增加
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 * @throws Exception 
//	 */
//	@RequestMapping("/webapi70001.{ext}")
//	public String webapi70001(CMi701 form , ModelMap modelMap, HttpServletRequest request) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//
//		String businName = "信息增加";
//		log.info(LOG.START_BUSIN.getLogText(businName));
//		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
//		
//		String rquestUrlTmp = request.getRequestURL().toString();
//		String contextPath = request.getContextPath();
//		String reqUrl = rquestUrlTmp.substring(0,request.getRequestURL().lastIndexOf(contextPath));
//		System.out.println("reqUrl="+reqUrl);
//		
//		webApi700ServiceImpl.webapi70001(form, reqUrl);
//		
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		
//		log.debug(LOG.END_BUSIN.getLogText(businName));
//		log.info(LOG.END_BUSIN.getLogText(businName));
//		
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 信息删除
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 * @throws Exception 
//	 */
//	@RequestMapping("/webapi70002.{ext}")
//	public String webapi70002(CMi701 form , ModelMap modelMap) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//
//		String businName = "信息删除";
//		log.info(LOG.START_BUSIN.getLogText(businName));
//		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
//		
//		webApi700ServiceImpl.webapi70002(form);
//		
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		
//		log.debug(LOG.END_BUSIN.getLogText(businName));
//		log.info(LOG.END_BUSIN.getLogText(businName));
//		
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 信息修改
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 * @throws Exception 
//	 */
//	@RequestMapping("/webapi70003.{ext}")
//	public String webapi70003(CMi701 form , ModelMap modelMap, HttpServletRequest request) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//
//		String businName = "信息修改";
//		log.info(LOG.START_BUSIN.getLogText(businName));
//		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
//		
//		String rquestUrlTmp = request.getRequestURL().toString();
//		String contextPath = request.getContextPath();
//		String reqUrl = rquestUrlTmp.substring(0,request.getRequestURL().lastIndexOf(contextPath));
//		System.out.println("reqUrl="+reqUrl);
//		
//		webApi700ServiceImpl.webapi70003(form, reqUrl);
//
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		
//		log.debug(LOG.END_BUSIN.getLogText(businName));
//		log.info(LOG.END_BUSIN.getLogText(businName));
//		
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 信息查询--分页
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 */
//	@RequestMapping("/webapi70004.{ext}")
//	public String webapi70004(WebApi70004Form form , ModelMap modelMap) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//		form.setBusinName("信息查询");
//		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
//		// 业务处理
//		WebApi70004_queryResult queryResult = webApi700ServiceImpl.webapi70004(form);
//		if(queryResult.getList701().isEmpty()||queryResult.getList701().size()==0){
//			//异常处理
//			log.error(ERROR.NO_DATA.getLogText("MI701", "form"));
//			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"信息");
//		}
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("total", queryResult.getTotal());
//		modelMap.put("totalPage", queryResult.getTotalPage());
//		modelMap.put("pageSize", queryResult.getPageSize());
//		modelMap.put("pageNumber", queryResult.getPageNumber());
//		modelMap.put("rows", queryResult.getList701());
//		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 信息查询-根据seqno
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 */
//	@RequestMapping("/webapi70005.{ext}")
//	public String webapi70005(CMi701 form, ModelMap modelMap) throws Exception{
//		Logger log = LoggerUtil.getLogger();					
//		form.setBusinName("根据seqno信息查询");
//		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));			
//		// 业务处理
//		List<Mi701> mi701List = webApi700ServiceImpl.webapi70005(form);
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("resultList", mi701List);		
//		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 增加/修改弹出对话所属栏目默认图标获取-根据classification
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 */
//	@RequestMapping("/webapi70006.{ext}")
//	public String webapi70006(CMi701 form, ModelMap modelMap) throws Exception{
//		Logger log = LoggerUtil.getLogger();					
//		form.setBusinName("根据所属栏目获取 默认图标");
//		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));			
//		// 业务处理
//		String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+form.getCenterId()+"_"+form.getClassification());
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		modelMap.put("defaultImgUrl", defaultImgUrl);		
//		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
//		return "page700/page70001";
//	}
//	
//	/**
//	 * 信息发布
//	 * @param form 信息参数
//	 * @param modelMap 返回数据容器
//	 * @return 回调页面名
//	 * @throws Exception 
//	 */
//	@RequestMapping("/webapi70007.{ext}")
//	public String webapi70007(CMi701 form , ModelMap modelMap) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//
//		String businName = "信息发布";
//		log.info(LOG.START_BUSIN.getLogText(businName));
//		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(form)));
//		
//		webApi700ServiceImpl.webapi70007(form);
//		
//		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//		
//		log.debug(LOG.END_BUSIN.getLogText(businName));
//		log.info(LOG.END_BUSIN.getLogText(businName));
//		
//		return "page700/page70001";
//	}
//	@RequestMapping("/webapi70001_uploadimg.do")
//	public void uploadimg(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(value = "imgFile") MultipartFile image, ModelMap modelMap) {
//		Logger log = LoggerUtil.getLogger();
//		try {
//			// 业务处理
//			webApi700ServiceImpl.uploadImage(request, response, image);
//
//		} catch (TransRuntimeErrorException tre) {
//			log.error(tre.getMessage());
//			throw tre;
//		} catch (Exception e) {
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
//		}
//	}

}
