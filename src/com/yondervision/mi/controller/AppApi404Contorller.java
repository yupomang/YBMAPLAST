/**
 * 
 */
package com.yondervision.mi.controller;

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
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi40401Form;
import com.yondervision.mi.service.AppApi404Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: AppApi401Contorller
 * @Description: 意见返馈
 * @author Caozhongyan
 * @date Oct 12, 2013 3:29:54 PM
 * 
 */
@Controller
public class AppApi404Contorller {
	@Autowired
	private AppApi404Service appApi404ServiceImpl = null;

	public AppApi404Service getAppApi404ServiceImpl() {
		return appApi404ServiceImpl;
	}

	public void setAppApi404ServiceImpl(AppApi404Service appApi404ServiceImpl) {
		this.appApi404ServiceImpl = appApi404ServiceImpl;
	}

	/**
	 * 手机APP意见反馈
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40401.{ext}")
	public String appapi40401(AppApi40401Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("手机APP意见反馈");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}	
		form.setBusinName("手机APP意见反馈");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		appApi404ServiceImpl.appapi40401(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
	
}
