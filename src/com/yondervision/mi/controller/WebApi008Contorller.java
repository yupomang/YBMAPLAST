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
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi203;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.result.WebApi20304_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi008Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi008Contorller 
* @Description: 楼盘维护
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi008Contorller {
//	@Autowired
//	private CodeListApi001Service codeListApi001Service = null;
//
//	public void setCodeListApi001Service(
//			CodeListApi001Service codeListApi001Service) {
//		this.codeListApi001Service = codeListApi001Service;
//	}
	@Autowired
	private WebApi008Service webApi008ServiceImpl;

	public WebApi008Service getWebApi008ServiceImpl() {
		return webApi008ServiceImpl;
	}

	public void setWebApi008ServiceImpl(WebApi008Service webApi008ServiceImpl) {
		this.webApi008ServiceImpl = webApi008ServiceImpl;
	}

	/**
	 * 楼盘查询增加
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00801.{ext}")
	public String webapi00801(CMi203 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("楼盘查询增加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi008ServiceImpl.webapi00801(form);
		modelMap.clear();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "page008/page00801";
	}
	
	/**
	 * 楼盘查询删除
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00802.{ext}")
	public String webapi00802(CMi203 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("楼盘查询删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		webApi008ServiceImpl.webapi00802(form);		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "page008/page00804";
	}
	
	/**
	 * 楼盘查询修改
	 * @param form 楼盘修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00803.{ext}")
	public String webapi00803(CMi203 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();		
		form.setBusinName("楼盘查询修改");			
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		int i = webApi008ServiceImpl.webapi00803(form);		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));		
		return "page008/page00804";
	}
	
	/**
	 * 楼盘查询查询
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00804.{ext}")
	public String webapi00804(CMi203 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("楼盘查询查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		WebApi20304_queryResult queryResult = webApi008ServiceImpl.webapi00804(form);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList203());
				
		return "page008/page00801";
	}
	
	

	/**
	 * 图片上传并返回文件名称，路径
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00805.{ext}")
	public String webapi00805(String magecenterId,String imageid, @RequestParam MultipartFile file, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = webApi008ServiceImpl.webapi00806(magecenterId, imageid, file);
		//request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)
		
		String filePath = CommonUtil.getDownloadFileUrl(
				"push_house_img", magecenterId+File.separator+fileName, true);
		webApi008ServiceImpl.webapi00807(magecenterId, imageid, fileName);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("fileName", fileName); // TODO 改为fielName
		modelMap.put("downloadPath", filePath);
		
//		request.setAttribute("downloadPath", filePath);
//		List<Mi202> list = this.codeListApi001Service.getAreaMessage(magecenterId);
//		modelMap.put("mi202list", list);
//		response.setContentType("Content-Type:text/html;charset=UTF-8");
		return "";
	}
	
	/**
	 * 图片URL修改
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00806.{ext}")
	public String webapi00806(String magecenterId,String imageid, String filePath, ModelMap modelMap) throws Exception{
		webApi008ServiceImpl.webapi00807(magecenterId, imageid, filePath);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		return "page008/page00804";
	}
	
	/**
	 * 图片URL
	 * @param form 楼盘查询参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi00807.{ext}")
	public String webapi00807(String houseId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi203 mi203 = webApi008ServiceImpl.webapi00808(houseId);		
		if(!CommonUtil.isEmpty(mi203.getImageUrl())){
			UserContext user = UserContext.getInstance();
			String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
					"push_house_img", user.getCenterid()+File.separator+mi203.getImageUrl(), true);
			
			mi203.setImageUrl(url);
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("imageURL", mi203.getImageUrl());
		return "page008/page00804";
	}
}
