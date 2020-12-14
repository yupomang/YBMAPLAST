package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.WebApi70401Form;
import com.yondervision.mi.form.WebApi70402Form;
import com.yondervision.mi.form.WebApi70403Form;
import com.yondervision.mi.form.WebApi70404Form;
import com.yondervision.mi.result.WebApi70404_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi704Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi704Contorller 
* @Description:版块栏目管理
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi704Contorller {
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(
			CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}
	@Autowired
	private WebApi704Service webApi704ServiceImpl;
	public void setWebApi704ServiceImpl(WebApi704Service webApi704ServiceImpl) {
		this.webApi704ServiceImpl = webApi704ServiceImpl;
	}
	
	@RequestMapping("/webapi704NewspaperforumList.json")
	public String getNewspaperforumList(ModelMap modelMap){
		UserContext user = UserContext.getInstance();
		modelMap.put("newspaperforumlist", codeListApi001Service.getCodeListJson(user.getCenterid(), Constants.FORUM_CODE));
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	@RequestMapping("/webapi704NewspapercolumnsList.json")
	public String getNewspapercolumnsList(ModelMap modelMap){
		UserContext user = UserContext.getInstance();
		modelMap.put("newspapercolumnslist", codeListApi001Service.getCodeListJson(user.getCenterid(), Constants.COLUMNS_CODE));
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return 	"";
	}
	
	/**
	 * 对应期次的版块栏目配置填加
	 * @param form 版块栏目配置填加信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70401.{ext}")
	public String webapi70401(WebApi70401Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次的版块栏目配置填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi704ServiceImpl.webapi70401(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page704/page70401";
	}
	
	/**
	 * 对应期次的版块栏目配置删除
	 * @param form 版块栏目配置信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70402.{ext}")
	public String webapi70402(WebApi70402Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次的版块栏目配置删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi704ServiceImpl.webapi70402(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page704/page70401";
	}
	
	/**
	 * 对应期次的版块栏目配置修改
	 * @param form 对应期次的版块栏目配置修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70403.{ext}")
	public String webapi70403(WebApi70403Form form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("报刊期次的版块栏目配置修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi704ServiceImpl.webapi70403(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page704/page70401";
	}

	/**
	 * 对应的期次的版块栏目配置查询
	 * @param form 报刊期次查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi70404.{ext}")
	public String webapi70404(WebApi70404Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("对应的期次的版块栏目配置查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		List<WebApi70404_queryResult> resultList = webApi704ServiceImpl.webapi70404(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", resultList);
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page704/page70401";
	}
}
