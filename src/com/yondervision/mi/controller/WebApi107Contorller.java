package com.yondervision.mi.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.service.WebApi107Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;
@SuppressWarnings("rawtypes")
@Controller
public class WebApi107Contorller {
	@Autowired
	private WebApi107Service webApi107ServiceImpl;

	public WebApi107Service getWebApi107ServiceImpl() {
		return webApi107ServiceImpl;
	}

	public void setWebApi107ServiceImpl(WebApi107Service webApi107ServiceImpl) {
		this.webApi107ServiceImpl = webApi107ServiceImpl;
	}
	/**
	 * 用户查询
	 * @param form 用户查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10704.{ext}")
	public String webapi10704(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		List<HashMap> list=webApi107ServiceImpl.webapi10704(form);
		modelMap.put("data", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10704";
	}
	@RequestMapping("/webapi10704Sun.{ext}")
	public String webapi10704Sun(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		List<HashMap> list=webApi107ServiceImpl.webapi10704Sun(form);
		modelMap.put("data", list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10704";
	}
	
	@RequestMapping("/webapi10705.{ext}")
	public String webapi10705(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("渠道运行情况统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		JSONArray ary = webApi107ServiceImpl.webapi10705(form);
		modelMap.clear();
		modelMap.put("rows", ary);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10704";
	}
	
	/**
	 * 渠道运行情况统计_new 2017-08-03
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi1070501.{ext}")
	public String webapi1070501(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("渠道运行情况统计_NEW");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		
		JSONArray ary = webApi107ServiceImpl.webapi1070501(form);
		modelMap.clear();
		modelMap.put("rows", ary);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10704";
	}
	
	@RequestMapping("/webapi1070601.{ext}")
	public String webapi1070601(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("渠道分析-渠道访问量统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		//渠道访问量统计
		List<LinkedHashMap> channelList = webApi107ServiceImpl.webapi1070601(form);
		
		modelMap.put("rows", channelList);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10706";
	}
	
	
	@RequestMapping("/webapi1070602.{ext}")
	public String webapi1070602(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("渠道分析-功能活跃度和访问时间段");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		System.out.println("webapi1070602===============form.getMifreeuse4()="+form.getMifreeuse4());
		List<LinkedHashMap> inquiryList = new ArrayList<LinkedHashMap>();
		List<LinkedHashMap> transTypelList = new ArrayList<LinkedHashMap>();
		if("0".equals(form.getMifreeuse4()))
		{
			//渠道功能活跃度统计
			transTypelList = webApi107ServiceImpl.webapiChannel21(form); 
		}else if("1".equals(form.getMifreeuse4()))
		{
			//访问时间段分布统计
			inquiryList = webApi107ServiceImpl.webapiChannel22(form);
		}
		
		modelMap.put("rows1", transTypelList);
		modelMap.put("rows2", inquiryList);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page107/page10706";
	}
	
	
	
	@RequestMapping("/webapi1070701.{ext}")
	public String webapi1070701(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户分析-用户年龄");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		//用户年龄属性统计
		List<LinkedHashMap> agelList = webApi107ServiceImpl.webapiChannel25(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", agelList);
	
		
		return "";
	}
	
	@RequestMapping("/webapi1070702.{ext}")
	public String webapi1070702(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户分析-用户性别");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道应用为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "渠道应用为空");
		}
		
		//用户性别属性统计
		List<LinkedHashMap> sexList = webApi107ServiceImpl.webapiChannel24(form);
		
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", sexList);
		return "page107/page10707";
	}
	
	@RequestMapping("/webapi10707User.{ext}")
	public void webapi10707User(CMi031 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户分析-用户增长统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		//用户增长统计
		List<List<LinkedHashMap>> list = webApi107ServiceImpl.webapi10707User(form); 
		
		modelMap.clear();
		modelMap.put("rows1",list.get(0));
		modelMap.put("rows2",list.get(1));
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		
	}
	
	@RequestMapping("/webapi10708.{ext}")
	public void webapi10708(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("业务分析-渠道分布统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		List<LinkedHashMap> list = webApi107ServiceImpl.webapi10708(form);
		modelMap.clear();
		modelMap.put("rows",list);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		
	}
	
	@RequestMapping("/webapi10709.{ext}")
	public void webapi10709(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("业务分析-时间段分布统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		List list = webApi107ServiceImpl.webapi10709(form);
		modelMap.clear();
		modelMap.put("rows",list);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		
	}
	
	@RequestMapping("/webapi1071001.{ext}")
	public void webapi1071001(CMi107 form ,HttpServletRequest request, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		form.setBusinName("业务分析-业务量统计非资金类");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		JSONObject obj = webApi107ServiceImpl.webapi1071001New(form,request);
		
		modelMap.clear();
		for(Object key:obj.keySet()){
			modelMap.put(key.toString(),obj.get(key));
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		
	}
	
	
	@RequestMapping("/webapi1071002.{ext}")
	public void webapi1071002(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "开始日期为空");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "结束日期为空");
		}
		form.setBusinName("业务分析-业务量统计资金类");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		JSONArray ary = webApi107ServiceImpl.webapi1071002(form);
		
		modelMap.clear();
		modelMap.put("rows",ary);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
	@RequestMapping("/webapi1071003.{ext}")
	public void webapi1071003(CMi107 form ,HttpServletRequest request, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("查询月份为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "查询月份为空");
		}
		form.setBusinName("业务分析-业务量统计表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		JSONObject obj = webApi107ServiceImpl.webapi1071003(form);
		
		modelMap.clear();
		for(Object key:obj.keySet()){
			modelMap.put(key.toString(),obj.get(key));
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
	@RequestMapping("/webapi1071004.{ext}")
	public void webapi1071004(CMi107 form ,HttpServletRequest request, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("查询月份为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "查询月份为空");
		}
		form.setBusinName("业务分析-各渠道业务量统计表");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		JSONObject obj = webApi107ServiceImpl.webapi1071004(form);
		
		modelMap.clear();
		for(Object key:obj.keySet()){
			modelMap.put(key.toString(),obj.get(key));
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
	@RequestMapping("/webapi1071005.{ext}")
	public void webapi1071005(CMi107 form ,HttpServletRequest request, ModelMap modelMap, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("查询月份为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "查询月份为空");
		}
		form.setBusinName("业务分析-业务量统计表导出");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		JSONObject obj = webApi107ServiceImpl.webapi1071003(form);
		HashMap m1 = new HashMap();
		m1.put("xxcx", obj.get("xxcx"));
		m1.put("xxfb", obj.get("xxfb"));
		m1.put("hdjl", obj.get("hdjl"));
		m1.put("ywbl", obj.get("ywbl"));
		m1.put("xszj", obj.get("xszj"));
		JSONArray ary = obj.getJSONArray("datas");
		m1.put("jcxsfwl", ary.getJSONObject(0).get("xsfwl"));
		m1.put("jcxsyw", ary.getJSONObject(0).get("xsyw"));
		m1.put("jcxxyw", ary.getJSONObject(0).get("xxyw"));
		m1.put("jcxsxx", ary.getJSONObject(0).get("xsxx"));
		m1.put("jcxszb", ary.getJSONObject(0).get("xszb"));
		
		m1.put("tqxsfwl", ary.getJSONObject(1).get("xsfwl"));
		m1.put("tqxsyw", ary.getJSONObject(1).get("xsyw"));
		m1.put("tqxxyw", ary.getJSONObject(1).get("xxyw"));
		m1.put("tqxsxx", ary.getJSONObject(1).get("xsxx"));
		m1.put("tqxszb", ary.getJSONObject(1).get("xszb"));

		m1.put("dkxsfwl", ary.getJSONObject(2).get("xsfwl"));
		m1.put("dkxsyw", ary.getJSONObject(2).get("xsyw"));
		m1.put("dkxxyw", ary.getJSONObject(2).get("xxyw"));
		m1.put("dkxsxx", ary.getJSONObject(2).get("xsxx"));
		m1.put("dkxszb", ary.getJSONObject(2).get("xszb"));

		m1.put("zjxsfwl", ary.getJSONObject(3).get("xsfwl"));
		m1.put("zjxsyw", ary.getJSONObject(3).get("xsyw"));
		m1.put("zjxxyw", ary.getJSONObject(3).get("xxyw"));
		m1.put("zjxsxx", ary.getJSONObject(3).get("xsxx"));
		m1.put("zjxszb", ary.getJSONObject(3).get("xszb"));
		
		XLSTransformer transformer = new XLSTransformer(); 
		String temppath = "";
		temppath = CommonUtil.getFullURL("download/template/business.xls");
		InputStream is =new BufferedInputStream(new FileInputStream(temppath));
		HSSFWorkbook workbook = (HSSFWorkbook)transformer.transformXLS(is, m1);
		
		//清空输出流
		response.reset();
		// 下载格式设置、定义输出类型
		response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
		//设置响应头和下载保存的文件名   
		//设置导出弹出框，以及下载文件名称  
		response.setHeader("Content-Disposition","attachment;filename=business.xls");
		OutputStream os;
		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.flushBuffer();
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return;
	}
	
	@RequestMapping("/webapi1071006.{ext}")
	public void webapi1071006(CMi107 form ,HttpServletRequest request, ModelMap modelMap, 
			HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("查询月份为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "查询月份为空");
		}
		form.setBusinName("业务分析-各渠道业务量统计表导出");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		JSONObject obj = webApi107ServiceImpl.webapi1071004(form);
		HashMap m1 = new HashMap();
		m1.put("item1", obj.getJSONArray("datas").get(0));
		m1.put("item2", obj.getJSONArray("datas").get(1));
		m1.put("item3", obj.getJSONArray("datas").get(2));
		m1.put("item4", obj.getJSONArray("datas").get(3));
		m1.put("item5", obj.getJSONArray("datas").get(4));
		m1.put("item6", obj.getJSONArray("datas").get(5));
		m1.put("item7", obj.getJSONArray("datas").get(6));
		m1.put("item8", obj.getJSONArray("datas").get(7));
		m1.put("item9", obj.getJSONArray("datas").get(8));
		m1.put("item10", obj.getJSONArray("datas").get(9));
		m1.put("item11", obj.getJSONArray("datas").get(10));
		m1.put("item12", obj.getJSONArray("datas").get(11));
		m1.put("item13", obj.getJSONArray("datas").get(12));
		m1.put("item14", obj.getJSONArray("datas").get(13));
		m1.put("item15", obj.getJSONArray("datas").get(14));
		m1.put("item16", obj.getJSONArray("datas").get(15));
		m1.put("item17", obj.getJSONArray("datas").get(16));
		m1.put("item18", obj.getJSONArray("datas").get(17));
		m1.put("item19", obj.getJSONArray("datas").get(18));
		m1.put("item20", obj.getJSONArray("datas").get(19));
		m1.put("item21", obj.getJSONArray("datas").get(20));
		m1.put("item22", obj.getJSONArray("datas").get(21));
		m1.put("item23", obj.getJSONArray("datas").get(22));
		m1.put("item24", obj.getJSONArray("datas").get(23));
		m1.put("item25", obj.getJSONArray("datas").get(24));
		m1.put("item26", obj.getJSONArray("datas").get(25));
		m1.put("item27", obj.getJSONArray("datas").get(26));
		m1.put("item28", obj.getJSONArray("datas").get(27));
		m1.put("item29", obj.getJSONArray("datas").get(28));
		m1.put("item30", obj.getJSONArray("datas").get(29));
		m1.put("item31", obj.getJSONArray("datas").get(30));
		m1.put("item32", obj.getJSONArray("datas").get(31));
		XLSTransformer transformer = new XLSTransformer(); 
		String temppath = "";
		temppath = CommonUtil.getFullURL("download/template/channelbusiness.xls");
		InputStream is =new BufferedInputStream(new FileInputStream(temppath));
		HSSFWorkbook workbook = (HSSFWorkbook)transformer.transformXLS(is, m1);
		
		//清空输出流
		response.reset();
		// 下载格式设置、定义输出类型
		response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
		//设置响应头和下载保存的文件名   
		//设置导出弹出框，以及下载文件名称  
		response.setHeader("Content-Disposition","attachment;filename=channelbusiness.xls");
		OutputStream os;
		try {
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.flushBuffer();
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return;
	}
	
	@RequestMapping("/webapi10711.{ext}")
	public void webapi10711(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("业务分析-用户分布统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		List<LinkedHashMap> list = webApi107ServiceImpl.webapi10711(form);
		
		modelMap.clear();
		modelMap.put("rows",list);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		
	}
	
	@RequestMapping("/webapi10713.{ext}")
	public void webapi10713(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("用户体验评价统计");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		
	}
	
	/**
	 * mi107数据统计到mi099
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi10714.{ext}")
	public void webapi10714(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("mi107数据统计到mi099");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		webApi107ServiceImpl.synMi107ToMi099(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	/**
	 * 渠道运行指标栏目内容更新量
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi10715.{ext}")
	public void webapi10715(CMi107 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("渠道运行指标栏目内容更新量");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
	
		List<HashMap> list = webApi107ServiceImpl.contentUpdate(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", list);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
	}
	
}
