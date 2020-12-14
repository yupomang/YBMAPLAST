package com.yondervision.mi.controller;

import net.sf.json.JSONArray;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.yondervision.mi.dto.CMi122;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.result.WebApi12201_queryResult;
import com.yondervision.mi.service.WebApi122Service;
import com.yondervision.mi.util.CommonUtil;


@Controller
public class WebApi122Contorller {
	@Autowired
	private WebApi122Service webApi122ServiceImpl;

	public WebApi122Service getWebApi122ServiceImpl() {
		return webApi122ServiceImpl;
	}

	public void setWebApi122ServiceImpl(WebApi122Service webApi122ServiceImpl) {
		this.webApi122ServiceImpl = webApi122ServiceImpl;
	}

	/**
	 * 消息主题类型填加
	 * @param form 消息主题类型信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12201.{ext}")
	public String webapi12201(CMi122 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("消息主题类型填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi122ServiceImpl.webapi12201(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30205";
	}
	
	/**
	 * 消息主题类型删除
	 * @param form 消息主题类型参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12202.{ext}")
	public String webapi12202(CMi122 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("消息主题类型删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi122ServiceImpl.webapi12202(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page122/page12201";
	}
	
	/**
	 * 消息主题类型修改
	 * @param form 消息主题类型参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12203.{ext}")
	public String webapi12203(CMi122 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		int i = webApi122ServiceImpl.webapi12203(form);	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page122/page12201";
	}
	
	/**
	 * 软件更新信息查询
	 * @param form 软件更新信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12204.{ext}")
	public String webapi12204(CMi122 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		WebApi12201_queryResult queryResult = webApi122ServiceImpl.webapi12204(form);
		if(queryResult.getList122().isEmpty()||queryResult.getList122().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"推送消息主题信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList122());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30205";
	}
	
	@RequestMapping("/webapi12205.{ext}")
	public String webapi12205(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));		
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi122ServiceImpl.webapi12205(arr);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page302/page30205";
	} 
	
	@RequestMapping("/webapi12206.{ext}")
	public void webapi12206(CMi122 form,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));		
		
	    List<Mi122> list = webApi122ServiceImpl.webapi12206(form);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
	} 
	
}
