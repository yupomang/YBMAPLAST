/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.form.CommUploadForm;
import com.yondervision.mi.result.WebApi20104_queryResult;
import com.yondervision.mi.service.WebApi101Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;


/** 
* @ClassName: WebApi008Contorller 
* @Description: 网点维护
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi101Contorller {
	@Autowired
	private WebApi101Service webApi101ServiceImpl;

	public WebApi101Service getWebApi101ServiceImpl() {
		return webApi101ServiceImpl;
	}

	public void setWebApi101ServiceImpl(WebApi101Service webApi101ServiceImpl) {
		this.webApi101ServiceImpl = webApi101ServiceImpl;
	}

	/**
	 * 网点查询增加
	 * @param form 网点查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10101.{ext}")
	public String webapi10101(CMi201 form , ModelMap modelMap) throws Exception{		
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("网点查询增加");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi101ServiceImpl.webapi10101(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page101/page10101";
	}
	
	/**
	 * 网点查询删除
	 * @param form 网点查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10102.{ext}")
	public String webapi10102(CMi201 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("网点信息删除");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));		
		// TODO 业务处理
		webApi101ServiceImpl.webapi10102(form);		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page101/page10104";
	}
	
	/**
	 * 网点信息修改
	 * @param form 网点修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10103.{ext}")
	public String webapi10103(CMi201 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("网点信息修改");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		// TODO 业务处理
		int i = webApi101ServiceImpl.webapi10103(form);		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page101/page10104";
	}
	
	/**
	 * 网点查询查询
	 * @param form 网点查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10104.{ext}")
	public String webapi10104(CMi201 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("网点信息查询");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));					
		// TODO 业务处理
		WebApi20104_queryResult queryResult = webApi101ServiceImpl.webapi10104(form);		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);

		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList201());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page101/page10104";
	}
	
	/**
	 * 图片上传并返回文件名称，路径
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10105.do")
	public void webapi10105(String magecenterId,String imageid, @RequestParam("file") MultipartFile file, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("网点图片上传"));
		String fileName = webApi101ServiceImpl.webapi10106(magecenterId, imageid, file);
		String filePath = CommonUtil.getDownloadFileUrl(
				"push_website_img", magecenterId+File.separator+fileName, true);
		CMi201 cmi201 = new CMi201();
		cmi201.setWebsiteId(imageid);
		cmi201.setImageUrl(fileName);
		cmi201.setCenterId(magecenterId);
		webApi101ServiceImpl.webapi10103_image(cmi201);
		JSONObject obj = new JSONObject();
		obj.put("fileName", fileName);
		obj.put("filePath", filePath);
		response.getWriter().print(obj.toString());
	}
	
	/**
	 * 图片查看图片
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi10106.{ext}")
	public void webapi10106(String websiteId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi201 mi201 = webApi101ServiceImpl.webapi10107(websiteId);
		if(!CommonUtil.isEmpty(mi201.getImageUrl())){
			UserContext user = UserContext.getInstance();
			String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
					"push_website_img", user.getCenterid()+File.separator+mi201.getImageUrl(), true);
			mi201.setImageUrl(url);
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("imageURL", mi201.getImageUrl());

	}
	
}
