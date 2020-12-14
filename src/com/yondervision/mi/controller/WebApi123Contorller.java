package com.yondervision.mi.controller;

import net.sf.json.JSONArray;

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
import com.yondervision.mi.dto.CMi123;
import com.yondervision.mi.result.WebApi12301_queryResult;
import com.yondervision.mi.service.WebApi123Service;
import com.yondervision.mi.util.CommonUtil;


@Controller
public class WebApi123Contorller {
	@Autowired
	private WebApi123Service webApi123ServiceImpl;

	public WebApi123Service getWebApi123ServiceImpl() {
		return webApi123ServiceImpl;
	}

	public void setWebApi123ServiceImpl(WebApi123Service webApi123ServiceImpl) {
		this.webApi123ServiceImpl = webApi123ServiceImpl;
	}

	/**
	 * 免打扰填加
	 * @param form 免打扰信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12301.{ext}")
	public String webapi12301(CMi123 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("免打扰信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi123ServiceImpl.webapi12301(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30206";
	}
	
	/**
	 * 免打扰删除
	 * @param form 免打扰参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12302.{ext}")
	public String webapi12302(CMi123 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("免打扰信息删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi123ServiceImpl.webapi12302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30206";
	}
	
	/**
	 * 免打扰修改
	 * @param form 免打扰参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12303.{ext}")
	public String webapi12303(CMi123 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("免打扰信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));

		int i = webApi123ServiceImpl.webapi12303(form);	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30206";
	}
	
	/**
	 * 免打扰查询
	 * @param form 免打扰参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi12304.{ext}")
	public String webapi12304(CMi123 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("免打扰信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
//		if(CommonUtil.isEmpty(form.getCenterid())){			
//			log.error(ERROR.NO_DATA.getLogText("centerid"));
//			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"待查城市信息");
//		}
		WebApi12301_queryResult queryResult = webApi123ServiceImpl.webapi12304(form);
		if(queryResult.getList123().isEmpty()||queryResult.getList123().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"免打扰");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList123());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page302/page30206";
	}
	
	@RequestMapping("/webapi12305.{ext}")
	public String webapi12305(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "时段明细修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));		
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi123ServiceImpl.webapi12305(arr);

		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page302/page30205";
	} 
	
}
