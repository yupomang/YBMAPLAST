package com.yondervision.mi.controller;

import java.io.OutputStream;
import java.net.URLDecoder;
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
import com.yondervision.mi.dto.Mi059;
import com.yondervision.mi.form.WebApi05904Form;
import com.yondervision.mi.result.WebApi05904_queryResult;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.service.WebApi059Service;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class WebApi059Contorller {
	@Autowired 
	private WebApi059Service webApi059Service;
	@Autowired
	private ExcelApi001Service excelApi001ServiceImpl;
	
	@RequestMapping(value = "/webapi05901.{ext}")
	public String webapi05901(Mi059 form , ModelMap modelMap, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "添加脱敏规则";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			String id = this.getWebApi059Service().webapi05901(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("id", id);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}
	
	@RequestMapping(value = "/webapi05902.{ext}")
	public String webapi05902(Mi059 form , ModelMap modelMap, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "修改脱敏规则";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			if(this.getWebApi059Service().webapi05902(form)){
				modelMap.clear();
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "修改失败，请联系管理员！");
			}
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}
	
	@RequestMapping(value = "/webapi05903.{ext}")
	public String webapi05903(Mi059 form , ModelMap modelMap, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "删除脱敏规则";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			if(this.getWebApi059Service().webapi05903(form)){
				modelMap.clear();
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "删除失败，请联系管理员！");
			}
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}
	
	@RequestMapping(value = "/webapi05904.{ext}")
	public String webapi05904(WebApi05904Form form , ModelMap modelMap, Integer page,Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String businName = "查找脱敏规则";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			WebApi05904_queryResult result = this.getWebApi059Service().webapi05904(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("totalPage", result.getTotalPage());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getList059());
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}
	
	@RequestMapping("/mi059ToExcel.do")
	public void mi059ToExcel(HttpServletRequest request, HttpServletResponse response, 
			String fileName, String centerId, ModelMap modelMap) throws Exception {
		String expotrTableName = "Mi059";
		String titlesName = "脱敏主题ID,脱敏主题描述,首部字数,尾部字数,替换字符,日期类型,日期格式,示例脱敏前原信息,示例脱敏后信息,详细描述";
		String titles = "desensitizationid,desensitizationmsg,firstnum,tailnum,replacechar,datethem,datetype,demo1,demo2,detail";
		Logger log=LoggerUtil.getLogger();
		String businName = "导出脱敏规则";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName+";titlesName:"+titlesName));
		try{
			// 确定要查询表的查询条件
			WebApi05904Form form = new WebApi05904Form();
			form.setCenterId(centerId);
			WebApi05904_queryResult result = this.getWebApi059Service().webapi05904(form);
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; filename=\"TMGZ.xls\"");
			excelApi001ServiceImpl.excelFileInOutputStream(outputStream, result.getList059(), expotrTableName, URLDecoder.decode(titlesName, "UTF-8"), titles);
			outputStream.close();

			//这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
			response.flushBuffer();//强行将响应缓存中的内容发送到目的地    
		}catch(Exception e){
			log.error(e);
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), e.getMessage());
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
	}

	public WebApi059Service getWebApi059Service() {
		return webApi059Service;
	}

	public void setWebApi059Service(WebApi059Service webApi059Service) {
		this.webApi059Service = webApi059Service;
	}

	public ExcelApi001Service getExcelApi001ServiceImpl() {
		return excelApi001ServiceImpl;
	}

	public void setExcelApi001ServiceImpl(ExcelApi001Service excelApi001ServiceImpl) {
		this.excelApi001ServiceImpl = excelApi001ServiceImpl;
	}
}
