/**
 * 
 */
package com.yondervision.mi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.form.AppApi05101Form;
import com.yondervision.mi.service.AppApi051Service;
import com.yondervision.mi.util.CommonUtil;


@Controller
public class AppApi051Contorller {
	
	@Autowired
	private AppApi051Service appApi051ServiceImpl = null;
	public AppApi051Service getAppApi051ServiceImpl() {
		return appApi051ServiceImpl;
	}
	public void setAppApi051ServiceImpl(AppApi051Service appApi051ServiceImpl) {
		this.appApi051ServiceImpl = appApi051ServiceImpl;
	}

	@RequestMapping("/appapi05101.{ext}")
	public String appApi05101(AppApi05101Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("服务缓存时间查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		Mi051 mi051 = appApi051ServiceImpl.appApi05101Select(form);
		if(!CommonUtil.isEmpty(mi051)){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("opencouch", mi051.getOpencouch());
			modelMap.put("couchtime", mi051.getCouchtime());
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", form.getBuzType()+"未查询到缓存时间信息");
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
