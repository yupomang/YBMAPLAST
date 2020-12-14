package com.yondervision.mi.controller;

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
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi804Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class AppApi804Contorller {
	
	@Autowired
	private AppApi804Service appApi804ServiceImpl = null;
	

	public AppApi804Service getAppApi804ServiceImpl() {
		return appApi804ServiceImpl;
	}


	public void setAppApi804ServiceImpl(AppApi804Service appApi804ServiceImpl) {
		this.appApi804ServiceImpl = appApi804ServiceImpl;
	}
	/**
	 * 查询wifi密码
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi80401.{ext}")
	public String appApi80401(AppApiCommonForm form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("查询wifi密码");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		ApiUserContext.getInstance();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		Mi001 mi001 = appApi804ServiceImpl.appapi80401(form);
		if(mi001==null){
			modelMap.put("recode", "000001");
			modelMap.put("msg","查询出错，请联系管理员");
		}else{
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("wifipassword", mi001.getFreeuse2());
		}
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
}
