/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi102;
import com.yondervision.mi.form.AppApi30101Form;
import com.yondervision.mi.service.AppApi301Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author CaoZhongYan
 *
 */
@Controller
public class AppApi301Contorller {
	protected final Logger log = LoggerUtil.getLogger();
	
	@Autowired	
	private AppApi301Service appApi301Service;
	
	public AppApi301Service getAppApi301Service() {
		return appApi301Service;
	}

	public void setAppApi301Service(AppApi301Service appApi301Service) {
		this.appApi301Service = appApi301Service;
	}

	/**
	 * 客服信息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30101.{ext}")
	public String appApi30101(AppApi30101Form form,  ModelMap modelMap) throws Exception{		
		form.setBusinName("客服信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCustsvctype())){
			log.error(ERROR.PARAMS_NULL.getLogText("custsvctype"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(),"客服类型");
		}
		if(form.getCenterId().isEmpty()||form.getCenterId().equals("")){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}		
		List<Mi102> list = appApi301Service.appApi30101Select(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "redirect:"+list.get(0).getWebaddress();
	}	
	
}
