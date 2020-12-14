package com.yondervision.mi.controller;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.yondervision.mi.dto.CMi050;
import com.yondervision.mi.dto.CMi051;
import com.yondervision.mi.dto.CMi052;
import com.yondervision.mi.dto.CMi053;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.result.WebApi05004_queryResult;
import com.yondervision.mi.result.WebApi05104_queryResult;
import com.yondervision.mi.result.WebApi05204_queryResult;
import com.yondervision.mi.result.WebApi05304_queryResult;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.service.WebApi050Service;
import com.yondervision.mi.util.CommonUtil;


/**
 * 接口-服务-应用
 * @author lixu
 *
 */
@Controller
public class WebApi050Contorller {
	@Autowired
	private WebApi050Service webApi050ServiceImpl;
	@Autowired
	private ExcelApi001Service excelApi001ServiceImpl;
	
	
	//渠道接口
	@RequestMapping("/webapi05001.{ext}")
	public String webapi05001(CMi050 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道接口新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05001(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page050/page05001";
	}

	@RequestMapping("/webapi05002.{ext}")
	public String webapi05002(CMi050 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道接口删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05002(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page050/page05002";
	}
	
	@RequestMapping("/webapi05003.{ext}")
	public String webapi05003(CMi050 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道接口修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05003(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page050/page05003";
	}
	@RequestMapping("/webapi05004.{ext}")
	public String webapi05004(CMi050 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道接口查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05004_queryResult queryResult = webApi050ServiceImpl.webapi05004(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList050());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page050/page05004";
	}

	@RequestMapping("/webapi05005.{ext}")
	public String webapi05005(CMi050 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "依赖服务查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05104_queryResult queryResult = webApi050ServiceImpl.webapi05005(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList051());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page050/page05005";
	}
	
	@RequestMapping("/webapi05006.{ext}")
	public String webapi05006(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询所有的接口";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi050> list = webApi050ServiceImpl.webapi05006();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page050/page05006";
	}
	
	@RequestMapping("/mi050ToExcel.do")
	public void mi050ToExcel(HttpServletRequest request, HttpServletResponse response,String expotrTableName, String titlesName, String titles,
			String fileName, ModelMap modelMap) throws Exception {
		Logger log=LoggerUtil.getLogger();
		String businName = "渠道接口-服务配置批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName+";titlesName:"+titlesName));
		try{
			// 确定要查询表的查询条件
			List<Mi050> mi050List = webApi050ServiceImpl.queryMi050ForExcel();
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM;charset=utf-8");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			excelApi001ServiceImpl.excelFileInOutputStream(outputStream, mi050List, expotrTableName, URLDecoder.decode(titlesName, "UTF-8"), titles);
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
	
	
	//渠道服务配置
	@RequestMapping("/webapi05101.{ext}")
	public String webapi05101(CMi051 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05101(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page051/page05101";
	}

	@RequestMapping("/webapi05102.{ext}")
	public String webapi05102(CMi051 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05102(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page051/page05102";
	}
	
	@RequestMapping("/webapi05103.{ext}")
	public String webapi05103(CMi051 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05103(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page051/page05103";
	}
	
	@RequestMapping("/webapi05104.{ext}")
	public String webapi05104(CMi051 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		JSONObject queryResult = webApi050ServiceImpl.webapi05104_01(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.get("total"));
		modelMap.put("pageSize", queryResult.get("pageSize"));
		modelMap.put("pageNumber", queryResult.get("pageNumber"));
		modelMap.put("rows", queryResult.get("rows"));
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page051/page05104";
	}
	
	@RequestMapping("/webapi05105.{ext}")
	public String webapi05105(CMi051 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "根据渠道服务主键查询所有渠道接口";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		List<Mi050> webapi05105 = webApi050ServiceImpl.webapi05105(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", webapi05105);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page051/page05105";
	}

	/**
	 * 查询所有服务列表
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi05106.{ext}")
	public String webapi05106(ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询所有服务列表";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi051> list = webApi050ServiceImpl.webapi05106();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	@RequestMapping("/webapi05107.{ext}")
	public String webapi05107(CMi052 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "保存统计标识";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		webApi050ServiceImpl.webapi05107(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", "[]");
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 查询所有服务列表
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/webapi05108.{ext}")
	public String webapi05108(CMi051 form ,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "查询中心下所有使用服务列表";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		List<Mi051> list = webApi050ServiceImpl.webapi05108(form);
				
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("rows", list);
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	//渠道服务-接口配置
	@RequestMapping("/webapi05201.{ext}")
	public String webapi05201(CMi052 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务-接口配置新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05201(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page052/page05201";
	}

	@RequestMapping("/webapi05202.{ext}")
	public String webapi05202(CMi052 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务-接口配置删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05202(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page052/page05202";
	}
	
	@RequestMapping("/webapi05203.{ext}")
	public String webapi05203(CMi052 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务-接口配置修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05203(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page052/page05203";
	}
	@RequestMapping("/webapi05204.{ext}")
	public String webapi05204(CMi052 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "渠道服务-接口配置查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05204_queryResult queryResult = webApi050ServiceImpl.webapi05204(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList052());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page052/page05204";
	}

	@RequestMapping("/webapi05205.{ext}")
	public String webapi05205(String datalist,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "子功能保存排序";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(datalist));
		
	    JSONArray arr= JSONArray.fromObject(datalist);
	    webApi050ServiceImpl.webapi05205(arr);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page052/page05205";
	}
	
	@RequestMapping("/webapi05206.{ext}")
	public String webapi05206(CMi052 form,ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "依据接口和服务的主键删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
	    webApi050ServiceImpl.webapi05206(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page052/page05206";
	}
	
	
	//服务-渠道-应用控制
	@RequestMapping("/webapi05301.{ext}")
	public String webapi05301(CMi053 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "服务-渠道-应用控制新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05301(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page053/page05301";
	}

	@RequestMapping("/webapi05302.{ext}")
	public String webapi05302(CMi053 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "服务-渠道-应用控制删除";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page053/page05302";
	}
	
	@RequestMapping("/webapi05303.{ext}")
	public String webapi05303(CMi053 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "服务-渠道-应用控制修改";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		
		webApi050ServiceImpl.webapi05303(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page053/page05303";
	}
	
	
	
	@RequestMapping("/webapi05304.{ext}")
	public String webapi05304(CMi053 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "服务-渠道-应用控制查询";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		
		WebApi05304_queryResult queryResult = webApi050ServiceImpl.webapi05304(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList053());
		
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "page053/page05304";
	}

//	服务-渠道-批量添加吸取务
	@RequestMapping("/webapi05305.{ext}")
	public String webapi05305(CMi053 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "服务-渠道-应用控制批量新增";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		//批量新增时serviceid需要用逗号分割。
		String[] services = form.getServiceid().split(",");
		for(int i=0;i<services.length;i++){
			try{
				form.setServiceid(services[i]);
				webApi050ServiceImpl.webapi05301(form);
			}catch(TransRuntimeErrorException e){
				log.info(e.getMessage());
			}
		}
		
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "page053/page05301";
	}
	
	public WebApi050Service getWebApi050ServiceImpl() {
		return webApi050ServiceImpl;
	}

	public void setWebApi050ServiceImpl(WebApi050Service webApi050ServiceImpl) {
		this.webApi050ServiceImpl = webApi050ServiceImpl;
	}

	public ExcelApi001Service getExcelApi001ServiceImpl() {
		return excelApi001ServiceImpl;
	}

	public void setExcelApi001ServiceImpl(ExcelApi001Service excelApi001ServiceImpl) {
		this.excelApi001ServiceImpl = excelApi001ServiceImpl;
	}
}
