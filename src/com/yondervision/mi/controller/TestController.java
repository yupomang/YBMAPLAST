package com.yondervision.mi.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.HeartBeatCheck;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.twodimensional.QRCodeUtil;
/**
 * 测试接口用
 * @author lixu
 *
 */
@Controller
public class TestController {
	@RequestMapping("/webapitest01.{ext}")
	public String webapitest01(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道应用运行状态监控";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		HeartBeatCheck check = new HeartBeatCheck();
		check.heartCheack();
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	//测试生成二维码
	@RequestMapping("/webapitest02.{ext}")
	public String webapitest02(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "测试生成二维码";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		String filePath = CommonUtil.getFileFullPath("push_twodimensional", "00087100YY", true);
		String filename = QRCodeUtil.encode("00087100"+CommonUtil.getSystemDateNumOnly()+".jpg", "00040;096;0;421181198809249875", filePath, true);
		
		String fileTwoPath = CommonUtil.getDownloadFileUrl(
				"push_twodimensional", "00087100YY"+File.separator+filename, true);
		
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
}
