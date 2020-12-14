package com.yondervision.mi.controller;

import java.net.URLDecoder;

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
import com.yondervision.mi.form.AppApi99901Form;
import com.yondervision.mi.service.AppApi999Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi999Contorller 
* @Description: 应聘信息管理Controller
* @author gongq
* 
*/ 
@Controller
public class AppApi999Contorller {
	@Autowired
	private AppApi999Service appApi999ServiceImpl = null;
	public void setAppApi999ServiceImpl(AppApi999Service appApi999ServiceImpl) {
		this.appApi999ServiceImpl = appApi999ServiceImpl;
	}

	/**
	 * 招聘信息-录入
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi99901.{ext}")
	public String appapi99901(AppApi99901Form form, ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("招聘信息-录入");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(CommonUtil.isEmpty(form.getUsername())){
			log.error(ERROR.PARAMS_NULL.getLogText("username"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"姓名");
		}
		if(CommonUtil.isEmpty(form.getPhone())){
			log.error(ERROR.PARAMS_NULL.getLogText("phone"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"电话");
		}
		if(CommonUtil.isEmpty(form.getEmail())){
			log.error(ERROR.PARAMS_NULL.getLogText("email"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"邮箱");
		}
		if(CommonUtil.isEmpty(form.getApplyarea())){
			log.error(ERROR.PARAMS_NULL.getLogText("applyarea"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应聘区域");
		}
		if(CommonUtil.isEmpty(form.getApplyposition())){
			log.error(ERROR.PARAMS_NULL.getLogText("applyposition"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"应聘岗位");
		}

		form.setUsername(URLDecoder.decode(form.getUsername(),"UTF-8"));
		
		appApi999ServiceImpl.appapi99901(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "";
	}
}
