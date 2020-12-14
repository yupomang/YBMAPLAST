/**
 * 
 */
package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi20201Result;
import com.yondervision.mi.service.AppApi202Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi202Contorller 
* @Description: 区域查询
* @author Caozhongyan
* @date Oct 11, 2013 4:20:09 PM   
* 
*/ 
@Controller
public class AppApi202Contorller {
	protected final Logger log = LoggerUtil.getLogger();
	//Autowired设置Spring自动注入与其同名的bean
	@Autowired
	private AppApi202Service appApi202ServiceImpl = null;
	

	public AppApi202Service getAppApi202ServiceImpl() {
		return appApi202ServiceImpl;
	}


	public void setAppApi202ServiceImpl(AppApi202Service appApi202ServiceImpl) {
		this.appApi202ServiceImpl = appApi202ServiceImpl;
	}


	/**
	 * 区域查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi20201.{ext}")
	public String appApi20201(AppApiCommonForm form,  ModelMap modelMap) throws Exception{
		form.setBusinName("APP区域查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));	
		List<Mi202> list = appApi202ServiceImpl.appapi20201(form);		
		if(list.isEmpty()||list.size()==0){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"区域信息");
		}
		List<AppApi20201Result> listResult = new ArrayList<AppApi20201Result>();
		for(int i=0;i<list.size();i++){
			AppApi20201Result aa202r = new AppApi20201Result();
			aa202r.setAreaId(list.get(i).getAreaId());
			aa202r.setAreaName(list.get(i).getAreaName());
			listResult.add(aa202r);
		}			
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", listResult);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}		
}
