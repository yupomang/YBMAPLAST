/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     YfApi302Contorller.java
 * 创建日期：2013-10-31
 */
package com.yondervision.mi.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.YfApi30201Form;
import com.yondervision.mi.form.YfApi30202Form;
import com.yondervision.mi.service.YfApi302Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 *
 */
@Controller
public class YfApi302Contorller {
	
	//@Autowired
	private YfApi302Service yfApi302Service;
	
	/**
	 * @return the yfApi302Service
	 */
	public YfApi302Service getYfApi302Service() {
		return yfApi302Service;
	}

	/**
	 * @param yfApi302Service the yfApi302Service to set
	 */
	public void setYfApi302Service(YfApi302Service yfApi302Service) {
		this.yfApi302Service = yfApi302Service;
	}

	/**
	 * 公积金业务系统发起单笔短信息推送
	 * @param form centerId、userId、buzType、accnum、title、detail
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "yfapi30201.json")
	public String yfapi30201(YfApi30201Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "公积金业务系统发起单笔短信息推送";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String mi100ID = this.getYfApi302Service().yfapi30201(form);

			modelMap.put("miSeqno", mi100ID);
			
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			log.error(tre.getStackTrace());
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
	
	/**
	 * 公积金业务系统发起批量短信息推送
	 * @param form centerId、userId、buzType、count
	 * @param file 上传的批量数据文件
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "yfapi30202.json")
	public String yfapi30202(YfApi30202Form form,@RequestParam String file, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "公积金业务系统发起单笔短信息推送";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			String mi100ID = this.getYfApi302Service().yfapi30202(form, file);

			modelMap.put("miSeqno", mi100ID);
			
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			log.error(tre.getStackTrace());
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
