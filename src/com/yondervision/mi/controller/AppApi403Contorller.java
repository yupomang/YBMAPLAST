/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi40301Result;
import com.yondervision.mi.service.AppApi403Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: AppApi401Contorller
 * @Description: 软件更新
 * @author Caozhongyan
 * @date Oct 12, 2013 3:29:54 PM
 * 
 */
@Controller
public class AppApi403Contorller {
	@Autowired
	private AppApi403Service appApi403ServiceImpl = null;

	public AppApi403Service getAppApi403ServiceImpl() {
		return appApi403ServiceImpl;
	}

	public void setAppApi403ServiceImpl(AppApi403Service appApi403ServiceImpl) {
		this.appApi403ServiceImpl = appApi403ServiceImpl;
	}

	/**
	 * 手机APP软件更新
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40301.{ext}")
	public String appapi40301(AppApiCommonForm form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("APP区域查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(form.getCenterId().isEmpty()||form.getCenterId().equals("")){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		AppApi40301Result appApi40301Result = appApi403ServiceImpl.appapi40301(form, request, response);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", appApi40301Result);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 手机APP软件二维码
	 * 
	 * @param form
	 *            请求参数
	 * @param modelMap
	 *            返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi40302.{ext}")
	public String appapi40302(AppApiCommonForm form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("APP区域查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(form.getCenterId().isEmpty()||form.getCenterId().equals("")){
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
		if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
			boolean flag = appApi403ServiceImpl.appapi40303(form);
			if(!flag){
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP版本信息");
			}
		}
		form.setDeviceType("1");
		AppApi40301Result resultIOS = appApi403ServiceImpl.appapi40302(form, request, response);
		form.setDeviceType("3");
		AppApi40301Result resultIOSPAD = appApi403ServiceImpl.appapi40302(form, request, response);
		form.setDeviceType("2");
		AppApi40301Result resultAndroid = appApi403ServiceImpl.appapi40302(form, request, response);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("resultIOS", resultIOS);
		modelMap.put("resultIOSPAD", resultIOSPAD);
		modelMap.put("resultAndroid", resultAndroid);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}	
}
