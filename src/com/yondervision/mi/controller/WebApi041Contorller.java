package com.yondervision.mi.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.HeartBeat;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi041;
import com.yondervision.mi.result.WebApi04104_queryResult;
import com.yondervision.mi.service.WebApi041Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class WebApi041Contorller {
	@Autowired
	private WebApi041Service webApi041ServiceImpl;
	
	
	@RequestMapping("/webapi04101.{ext}")
	public String webapi04101(CMi041 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控增加";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi041ServiceImpl.webapi04101(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page041/page04101";
	}

	@RequestMapping("/webapi04102.{ext}")
	public String webapi04102(CMi041 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi041ServiceImpl.webapi04102(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page041/page04101";
	}
	
	@RequestMapping("/webapi04103.{ext}")
	public String webapi04103(CMi041 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi041ServiceImpl.webapi04103(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page041/page04003";
	}
	
	@RequestMapping("/webapi04104.{ext}")
	public String webapi04004(CMi041 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道监控查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		WebApi04104_queryResult queryResult = webApi041ServiceImpl.webapi04104(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList041());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page041/page04104";
	}
	
	@RequestMapping("/webapi04105.{ext}")
	public String webapi04105(String centerid ,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道应用运行状态控制-查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<HeartBeat> list = webApi041ServiceImpl.webapi04105(centerid);
		//模拟数据---YD-admin
//		if(list==null || list.isEmpty()){
//			HeartBeat hb1 = new HeartBeat();
//			hb1.setCenterid(centerid);
//			hb1.setChannel("10");
//			hb1.setPid("10000119");
//			hb1.setCheckurl("http://请不要删除1");
//			hb1.setOpen("open");
//			hb1.setPidname("测试1");
//			
//			HeartBeat hb2 = new HeartBeat();
//			hb2.setCenterid(centerid);
//			hb2.setChannel("20");
//			hb2.setPid("20000120");
//			hb2.setCheckurl("http://请不要删除2");
//			hb2.setOpen("close");
//			hb2.setPidname("测试2");
//			HeartBeat hb3 = new HeartBeat();
//			hb3.setCenterid(centerid);
//			hb3.setChannel("30");
//			hb3.setPid("30000121");
//			hb3.setCheckurl("http://请不要删除3");
//			hb3.setOpen("close");
//			hb3.setPidname("测试3");
//			list.add(hb1);
//			list.add(hb2);
//			list.add(hb3);
//		}
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page041/page04104";
	}
	

	public WebApi041Service getWebApi041ServiceImpl() {
		return webApi041ServiceImpl;
	}

	public void setWebApi041ServiceImpl(WebApi041Service webApi041ServiceImpl) {
		this.webApi041ServiceImpl = webApi041ServiceImpl;
	}
}
