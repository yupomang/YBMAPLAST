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
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi106;
import com.yondervision.mi.result.WebApi40304_queryResult;
import com.yondervision.mi.service.WebApi403Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.twodimensional.QRCodeUtil;


/** 
* @ClassName: WebApi403Contorller 
* @Description:软件更新信息查询
* @author Caozhongyan
* @date Sep 29, 2013 2:49:31 PM   
* 
*/ 
@Controller
public class WebApi403Contorller {
	@Autowired
	private WebApi403Service webApi403ServiceImpl;

	public WebApi403Service getWebApi403ServiceImpl() {
		return webApi403ServiceImpl;
	}

	public void setWebApi403ServiceImpl(WebApi403Service webApi403ServiceImpl) {
		this.webApi403ServiceImpl = webApi403ServiceImpl;
	}

	/**
	 * 软件更新信息填加
	 * @param form 软件更新填加信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40301.{ext}")
	public String webapi40301(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息填加");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		if(form.getReleasecontent().getBytes().length>200){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(),"更新内容","200");
//		}
//		if(form.getDownloadurl().getBytes().length>200){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(),"下载地址","200");
//		}
		// TODO 业务处理
		webApi403ServiceImpl.webapi40301(form);			
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page403/page40301";
	}
	
	/**
	 * 软件更新信息删除
	 * @param form 软件更新填加信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40302.{ext}")
	public String webapi40302(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息删除");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		// TODO 业务处理
		webApi403ServiceImpl.webapi40302(form);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page403/page40301";
	}
	
	/**
	 * 软件更新信息修改
	 * @param form 软件更新修改参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40303.{ext}")
	public String webapi40303(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息修改");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
//		if(form.getReleasecontent().getBytes().length>200){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(),"更新内容","200");
//		}
//		if(form.getDownloadurl().getBytes().length>200){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
//					.getValue(),"下载地址","200");
//		}
		// TODO 业务处理
		int i = webApi403ServiceImpl.webapi40303(form);	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page403/page40301";
	}
	
	/**
	 * 软件更新信息查询
	 * @param form 软件更新信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40304.{ext}")
	public String webapi40304(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新信息查询");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		// TODO 业务处理
		WebApi40304_queryResult queryResult = webApi403ServiceImpl.webapi40304(form);
		if(queryResult.getList106().isEmpty()||queryResult.getList106().size()==0){
			//异常处理
			log.error(ERROR.NO_DATA.getLogText("centerId"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"软件更新信息");
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		modelMap.put("total", queryResult.getTotal());
		modelMap.put("pageSize", queryResult.getPageSize());
		modelMap.put("pageNumber", queryResult.getPageNumber());
		modelMap.put("rows", queryResult.getList106());
		
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page403/page40301";
	}
	
	/**
	 * 软件更新发布
	 * @param form 软件更新信息参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40305.{ext}")
	public String webapi40305(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新发布");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));			
		int i = webApi403ServiceImpl.webapi40305(form);	
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);			
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "page403/page40301";		
	}
	
	/**
	 * 图片上传并返回文件名称，路径
	 * @param form 版本信息
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40306.do")
	public String webapi40306(String magecenterId,String versionid_pic,String devtype_pic,String downloadurl_p, @RequestParam MultipartFile file, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName = "";
		try{	
			fileName = webApi403ServiceImpl.webapi40306(magecenterId, devtype_pic, file);
		}catch (NoRollRuntimeErrorException e){
			modelMap.put("recode", e.getErrcode());
			modelMap.put("msg", e.getMessage());	
			return "page302/upimgdata";
		}
		String filePath = CommonUtil.getDownloadFileUrl(
				"push_twodimensional", magecenterId+File.separator+fileName, true);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("fileName", fileName); // 改为fielName
		modelMap.put("downloadPath", filePath);
		
		
//		List<Mi202> list = this.codeListApi001Service.getAreaMessage(magecenterId);
//		modelMap.put("mi202list", list);
//		response.setContentType("Content-Type:text/html;charset=UTF-8");
		return "page302/upimgdata";
	}
	
	
	/**
	 * 二维码存储
	 * @param form 二维码存储参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/webapi40307.{ext}")
	public String webapi40307(CMi106 form , ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();					
		form.setBusinName("软件更新二维码存储");
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
				.getStringParams(form)));
		String filename = "";
		String filePath = CommonUtil.getFileFullPath("push_twodimensional", form.getCenterid(), true);
		if(CommonUtil.isEmpty(form.getFreeuse2())){
			filename = QRCodeUtil.encode(form.getCenterid()+CommonUtil.getSystemDateNumOnly()+".jpg", form.getDownloadurl(), filePath, true);
		}else{
			filename = QRCodeUtil.encode(form.getCenterid()+form.getFreeuse2(), form.getDownloadurl(), filePath+"/"+form.getFreeuse2(), filePath, true);
		}
		
		form.setFreeuse1(filename);
		int i = webApi403ServiceImpl.webapi40307(form);	
		
		String fileTwoPath = CommonUtil.getDownloadFileUrl(
				"push_twodimensional", form.getCenterid()+File.separator+filename, true);
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("fileName", filename);
		modelMap.put("downloadPath", fileTwoPath);
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";
	}
	
}
