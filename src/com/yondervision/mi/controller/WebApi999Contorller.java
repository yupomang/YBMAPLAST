package com.yondervision.mi.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.JobOffers;
import com.yondervision.mi.form.ItemInfo;
import com.yondervision.mi.form.WebApi99901Form;
import com.yondervision.mi.form.WebApi99902Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.WebApi99901_queryResult;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.service.WebApi999Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi999Contorller 
* @Description: 应聘信息管理
* @author gongqi  
*/ 
@Controller
public class WebApi999Contorller {
	@Autowired
	private WebApi999Service webApi999ServiceImpl;
	public void setWebApi999ServiceImpl(WebApi999Service webApi999ServiceImpl) {
		this.webApi999ServiceImpl = webApi999ServiceImpl;
	}
	
	@Autowired
	private ExcelApi001Service excelApi001Service = null;
	public void setExcelApi001Service(ExcelApi001Service excelApi001Service) {
		this.excelApi001Service = excelApi001Service;
	}

	/**
	 * 根据应聘区域，获取应聘职位下拉列表
	 * @param form 新闻标题列表查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/getApplyPostionJsonArray.{ext}")
	public String getApplyPostionJsonArray(WebApiCommonForm form, String centeridTmp, String applyarea, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("根据应聘区域，获取应聘职位的级联数据");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			

		List<ItemInfo> positionList = webApi999ServiceImpl.getApplyPositionList("area", applyarea);
		ObjectMapper mapper = new  ObjectMapper();
		JSONArray applyPostionJsonArray = mapper.convertValue(positionList, JSONArray.class);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("applyPostionJsonArray", applyPostionJsonArray);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 应聘信息查询-分页
	 * @param form 应聘信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi99901.{ext}")
	public String webapi99901(WebApi99901Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("应聘信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// 业务处理
		WebApi99901_queryResult queryResult = webApi999ServiceImpl.webapi99901(form);
		if(queryResult.getListJobOffers().isEmpty()||queryResult.getListJobOffers().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("应聘信息管理", "分页查询"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"应聘信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("totalPage", queryResult.getTotalPage());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getListJobOffers());		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 应聘信息已读设置
	 * @param form 已读设置参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi99902.{ext}")
	public String webapi99902(WebApi99902Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("应聘信息已读设置");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));	
		// 业务处理
		webApi999ServiceImpl.webapi99902(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	/**
	 * 应聘信息删除
	 * @param form 已读设置参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi99903.{ext}")
	public String webapi99903(WebApi99902Form form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("应聘信息删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));	
		// 业务处理
		webApi999ServiceImpl.webapi99903(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
	//应聘信息批量导出数据到excel文件
	@RequestMapping("/joboffersinfoToExcel.do")
	public void joboffersinfoToExcel(HttpServletRequest request, HttpServletResponse response, String expotrTableName,
			String titles, String fileName, String seqnos, ModelMap modelMap){
		Logger log=LoggerUtil.getLogger();
		
		String businName = "应聘信息批量导出数据到excel文件";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		log.debug(DEBUG.SHOW_PARAM.getLogText("expotrTableName:"+expotrTableName+";titles:"+titles+";fileName:"+fileName));
		try{
			// 确定要查询表的查询条件
			List<JobOffers> list = new ArrayList<JobOffers>();
			if(!CommonUtil.isEmpty(seqnos)){
				list = webApi999ServiceImpl.webapi99905(seqnos);
				
			}else{
				WebApi99901Form form = new WebApi99901Form();
				UserContext user = UserContext.getInstance();
				form.setCenterId(user.getCenterid());
				list = webApi999ServiceImpl.webapi99904(form);
			}
			
			for(int i = 0 ; i < list.size(); i++){
				JobOffers jobOffers = list.get(i);
				String detailDate = jobOffers.getDatecreated();
				jobOffers.setDatecreated(detailDate.substring(0,10));
				
				String applyPosition = jobOffers.getApplyposition();
				List<ItemInfo> positionList = webApi999ServiceImpl.getApplyPositionList("area", jobOffers.getApplyarea());
				for(int j = 0; j < positionList.size(); j++){
					if(applyPosition.equals(positionList.get(j).getItemid())){
						jobOffers.setApplyposition(positionList.get(j).getItemval());
					}
				}
				
				String applyArea = jobOffers.getApplyarea();
				List<ItemInfo> applyAreaList = this.webApi999ServiceImpl.getApplyAreaList("area");
				for(int j = 0; j < applyAreaList.size(); j++){
					if(applyArea.equals(applyAreaList.get(j).getItemid())){
						jobOffers.setApplyarea(applyAreaList.get(j).getItemval());
					}
				}
				
				String isread = jobOffers.getFreeuse1();
				if(Constants.IS_VALIDFLAG.equals(isread)){
					jobOffers.setFreeuse1("已读");
				}else{
					jobOffers.setFreeuse1("未读");
				}
				list.set(i, jobOffers);
			}
			// 直接往response的输出流中写excel
			OutputStream outputStream = response.getOutputStream();
			//清空输出流
			response.reset();
			
			// 下载格式设置、定义输出类型
			response.setContentType("APPLICATION/OCTET-STREAM");
			//设置响应头和下载保存的文件名   
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			String titlesName = "应聘区域,应聘岗位,姓名,电话,邮箱,应聘日期,是否已读";
			excelApi001Service.excelFileInOutputStream(outputStream, list, expotrTableName, titlesName, titles);
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

}
