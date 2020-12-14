package com.yondervision.mi.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.ExcelMapApiService;
import com.yondervision.mi.util.CommonUtil;

@Controller
public class ExcelMapImportContorller {
	@Autowired
	ExcelMapApiService excelMapApService=null;
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public CodeListApi001Service getCodeListApi001Service() {
		return codeListApi001Service;
	}

	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	public ExcelMapApiService getExcelMapApService() {
		return excelMapApService;
	}
	
	public void setExcelMapApService(ExcelMapApiService excelMapApService) {
		this.excelMapApService = excelMapApService;
	}

	@RequestMapping("/webapi10102_uploadimg.do")
	public String webapi10102_uploadimg(String cityname, String citycode, String path,String ruleMapStr, String tableName ,String fileName, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response ) throws Exception{
		UserContext user = UserContext.getInstance();
		Logger log=LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("批量导入"+tableName));
		try{
			if(CommonUtil.isEmpty(fileName)){
				log.error(ERROR.PARAMS_NULL.getLogText("fileName"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"批量文件名称");
			}	
			if(CommonUtil.isEmpty(citycode)){
				log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
			}
			if(CommonUtil.isEmpty(cityname)){
				log.error(ERROR.PARAMS_NULL.getLogText("cityname"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市名称");
			}
			if(CommonUtil.isEmpty(tableName)){
				log.error(ERROR.PARAMS_NULL.getLogText("tableName"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"导入业务名称");
			}
			if(CommonUtil.isEmpty(ruleMapStr)){
				log.error(ERROR.PARAMS_NULL.getLogText("ruleMapStr"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文件对应列描述");
			}
		
			JSONObject json=JSONObject.fromObject(ruleMapStr);
		
			excelMapApService.excelMapToDB(user.getLoginid(),cityname, citycode, json, tableName, fileName, request, response);
		}catch (TransRuntimeErrorException e){
			modelMap.put("recode", e.getErrcode());
			modelMap.put("msg", e.getMessage());
			return  "page302/upimgdata";
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(new Date());
		log.info(LOG.END_BUSIN.getLogText("批量导入"+tableName));
		return  "page302/upimgdata";
	}
	
	@RequestMapping("/webapi10101_check.do")//excelMapToCheck.do
	public String webapi10101_check(String cityname, String citycode, String path,String ruleMapStr, String tableName ,@RequestParam  MultipartFile  excelfile, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response ) throws Exception{
		UserContext user = UserContext.getInstance();
		Logger log=LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("批量导入检查"+tableName));
		log.info("上传文件 文件名："+excelfile.getOriginalFilename()+",文件大小："+excelfile.getSize());
		log.info(new Date());
		try{
			if (!excelfile.getOriginalFilename().endsWith(".xls")) {
				String fileName = excelfile.getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_FILE_TYPE
						.getValue(), fileType, "xls");
			}			
			JSONObject json=JSONObject.fromObject(ruleMapStr);		
			excelMapApService.excelMapToCheck(user.getLoginid(),cityname, citycode, json, tableName, excelfile.getInputStream(), request, response);
		}catch (TransRuntimeErrorException e){
			modelMap.put("recode", e.getErrcode());
			modelMap.put("msg", e.getMessage());
			return  "page302/upimgdata";
		}
		if("true".equals(excelMapApService.getCheckResult())){
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("checkfile", excelMapApService.getCheckFileName());
		}else{
			modelMap.put("recode", "000001");
			modelMap.put("msg", "文件中数据不合法");
			modelMap.put("checkfile", excelMapApService.getCheckFileName());
		}
		log.info(LOG.END_BUSIN.getLogText("批量导入检查"+tableName));
		return  "page302/upimgdata";
	}
}
