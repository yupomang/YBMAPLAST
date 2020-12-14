package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.yondervision.mi.dto.Mi059;
import com.yondervision.mi.form.AppApi059Form;
import com.yondervision.mi.result.WebApi05904_queryResult;
import com.yondervision.mi.service.WebApi059Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class AppApi059Contorller {
	@Autowired 
	private WebApi059Service webApi059Service;
	
	public WebApi059Service getWebApi059Service() {
		return webApi059Service;
	}

	public void setWebApi059Service(WebApi059Service webApi059Service) {
		this.webApi059Service = webApi059Service;
	}

	@RequestMapping("/appapi05901.{ext}")
	public String appApi05901(AppApi059Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取脱敏规则");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		try {
			WebApi05904_queryResult result = this.getWebApi059Service().webapi05905(form);
			List<Mi059> listMi059 = result.getList059();
			List<HashMap> listMap = new ArrayList();
			if (!CommonUtil.isEmpty(listMi059)) {
				for (int i = 0; i < listMi059.size(); i++) {
					Mi059 tmp = listMi059.get(i);
					HashMap map = new HashMap();
					map.put("desensitizationid", tmp.getDesensitizationid());
					map.put("desensitizationmsg", tmp.getDesensitizationmsg());
					map.put("firstnum", tmp.getFirstnum());
					map.put("tailnum", tmp.getTailnum());
					map.put("replacechar", tmp.getReplacechar());
					map.put("datethem", tmp.getDatethem());
					map.put("datetype", tmp.getDatetype());
					map.put("demo1", tmp.getDemo1());
					map.put("demo2", tmp.getDemo2());
					map.put("detail", tmp.getDetail());
					map.put("freeuse1", tmp.getFreeuse1());
					listMap.add(map);
				}
			}
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("totalPage", result.getTotalPage());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", listMap);
			System.out.println(modelMap);
			log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		return "";
	}
}
