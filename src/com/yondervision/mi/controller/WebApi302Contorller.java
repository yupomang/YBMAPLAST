/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.controller
 * 文件名：     WebApi302Contorller.java
 * 创建日期：2013-10-8
 */
package com.yondervision.mi.controller;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dto.CMi401;
import com.yondervision.mi.dto.CMi404;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.form.WebApi30201_deleteForm;
import com.yondervision.mi.form.WebApi30202_quertForm;
import com.yondervision.mi.form.WebApi30203Form;
import com.yondervision.mi.form.WebApi30204Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.WebApi30202_queryResult;
import com.yondervision.mi.result.WebApi30203_queryResult;
import com.yondervision.mi.result.WebApi30204_queryResult;
import com.yondervision.mi.result.WebApi30205_queryResult;
import com.yondervision.mi.result.WebApi30206_queryResult;
import com.yondervision.mi.result.WebApi30207_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;

/**
 * 消息推送WEB后台控制类
 * 
 * @author LinXiaolong
 * 
 */
@Controller
public class WebApi302Contorller {
	@Autowired 
	private WebApi302Service webApi302Service;
	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public CodeListApi001Service getCodeListApi001Service() {
		return codeListApi001Service;
	}

	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	/**
	 * 公共短信息图片上传
	 * 
	 * @param file
	 *            上传的图片
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/webapi30201_uploadimg.do")
	public String uploadimg(WebApiCommonForm form,
			@RequestParam MultipartFile file, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		try {
			if (CommonUtil.isEmpty(form.getCenterId())) {
				log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "城市中心代码");
			}
			
			if(file.getSize()>(1024*1024)){
				log.error(ERROR.PARAMS_NULL.getLogText("file size > 1024"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
						.getValue(), "图片","大小超过1M");
			}
			if(file.getOriginalFilename().lastIndexOf("jpg")<0){
				log.error(ERROR.PARAMS_NULL.getLogText("image not jpg"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "JPG格式图片");
			}
			// 业务处理
			String fileName = this.getWebApi302Service().uploadFile(form, file);

			modelMap.put("fielName", fileName);
			modelMap.put("downloadPath", CommonUtil.getDownloadFileUrl(
					"push_msg_img", form.getCenterId() + File.separator
							+ fileName, true));

			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
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
		return "page302/upimgdata";
	}

	/**
	 * 公共短信息音频上传
	 * 
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/webapi30201_uploadAudio.do")
	public String uploadAudio(WebApiCommonForm form,
			@RequestParam MultipartFile file, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		try {
			if (CommonUtil.isEmpty(form.getCenterId())) {
				log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "城市中心代码");
			}
			
			if(file.getSize()>(1024*1024*2)){
				log.error(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText("file size",
						String.valueOf(file.getSize()),"2M"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
						.getValue(), "音频","大小超过2M");
			}

			CommonsMultipartFile cf= (CommonsMultipartFile)file;
			DiskFileItem fi = (DiskFileItem)cf.getFileItem();
			File source = fi.getStoreLocation(); 
			Encoder encoder = new Encoder();
			MultimediaInfo m = encoder.getInfo(source);
			long ls = m.getDuration();
			if(ls>60000){
				log.error(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText("audio",
						String.valueOf(ls/1000) + "秒", "60秒"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
						.getValue(), "音频","时长超过60秒");
			}

			if(file.getOriginalFilename().lastIndexOf("mp3")<0 && 
					file.getOriginalFilename().lastIndexOf("amr")<0){
				log.error(ERROR.PARAMS_NULL.getLogText("audio not AMR/MP3"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "AMR/MP3格式音频");
			}
			// 业务处理
			String fileName = this.getWebApi302Service().uploadFile(form, file);

			modelMap.put("fielName", fileName);
			modelMap.put("downloadPath", CommonUtil.getDownloadFileUrl(
					"push_msg_img", form.getCenterId() + File.separator
					+ fileName, true));

			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
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
		return "page302/upimgdata";
	}
	
	/**
	 * 公共短信息视频上传
	 * 
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/webapi30201_uploadVideo.do")
	public String uploadVideo(WebApiCommonForm form,
			@RequestParam MultipartFile file, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		try {
			if (CommonUtil.isEmpty(form.getCenterId())) {
				log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "城市中心代码");
			}
			
			if(file.getSize()>(1024*1024*10)){
				log.error(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText("file size",
						String.valueOf(file.getSize()),"10M"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG
						.getValue(), "视频","大小超过10M");
			}
			if(file.getOriginalFilename().lastIndexOf("mp4")<0){
				log.error(ERROR.PARAMS_NULL.getLogText("video not mp4"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "MP4格式视频");
			}
			// 业务处理
			String fileName = this.getWebApi302Service().uploadFile(form, file);

			modelMap.put("fielName", fileName);
			modelMap.put("downloadPath", CommonUtil.getDownloadFileUrlNew(
					"downloadmedia.file","push_msg_img",
					form.getCenterId() + File.separator + fileName, true));

			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
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
		return "page302/upimgdata";
	}
	/**
	 * 添加公共短消息
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=add")
	public String webapi30201_add(CMi401 form, ModelMap modelMap) {
//		Logger log = LoggerUtil.getLogger();
//		String businName = "添加公共短信息";
//		try {
//			log.info(LOG.START_BUSIN.getLogText(businName));
//			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//					.getStringParams(form)));
//			// 业务处理
//			String[] type = form.getPusMessageType().split(",");
//			for(int i=0;i<type.length;i++){
//				form.setPusMessageType(type[i]);
//				String commsgid = this.getWebApi302Service().webapi30201_add(form);
//			}	
//			//modelMap.put("commsgid", commsgid);
//
//			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//			log.info(LOG.END_BUSIN.getLogText(businName));
//		} catch (TransRuntimeErrorException tre) {
//			log.error(tre.getMessage());
//			throw tre;
//		} catch (Exception e) {
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
//		}
		return "";
	}
	
	
	/**
	 * 添加公共短消息文本
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addText")
	public String webapi30201_addText(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共短信息——纯文本信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			String commsgid = this.getWebApi302Service().webapi30201_addText(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("commsgid", commsgid);
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
	

	/**
	 * 添加公共短消息图片
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addImage")
	public String webapi30201_addImage(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共短信息——图片消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			String commsgid = this.getWebApi302Service().webapi30201_addImage(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("commsgid", commsgid);
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
	
	/**
	 * 添加公共短消息音频
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addAudio")
	public String webapi30201_addAudio(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共短信息——音频消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			String commsgid = this.getWebApi302Service().webapi30201_addAudio(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("commsgid", commsgid);
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
	
	/**
	 * 添加公共短消息视频
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return  
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addVideo")
	public String webapi30201_addVideo(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共短信息——视频消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			String commsgid = this.getWebApi302Service().webapi30201_addVideo(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("commsgid", commsgid);
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
	
	/**
	 * 添加群推消息单条图文
	 * 
	 * @param form
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addTextImageSingle")
	public String webapi30201_addTextImageSingle(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加单图文群推消息";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));			
			
			if (CommonUtil.isEmpty(form.getTitle())) {
				log.error(ERROR.PARAMS_NULL.getLogText("mstitle"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "消息标题");
			}			
			
			if (CommonUtil.isEmpty(form.getDetail())) {
				log.error(ERROR.PARAMS_NULL.getLogText("detail()"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "消息描述");
			}
			
			if (CommonUtil.isEmpty(form.getTsmsg())) {
				log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "详细内容");
			}
			
			if (CommonUtil.isEmpty(form.getParam2())) {
				log.error(ERROR.PARAMS_NULL.getLogText("param2"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "图片信息");
			}
			
			if(CommonUtil.isEmpty(form.getCommsgid())){
				String commsgid = this.getWebApi302Service().webapi30201_addTextImageSingle401(form);
				form.setCommsgid(commsgid);
			}else{
				this.getWebApi302Service().webapi30201_editTextImageSingle401(form);
			}
			if(!CommonUtil.isEmpty(form.getMsmscommsgid())){
				int sucess = this.getWebApi302Service().webapi30201_updateTextImageSingle(form);				
			}else{
				this.getWebApi302Service().webapi30201_addTextImageSingle(form);
			}		
			modelMap.put("commsgid", form.getCommsgid());
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		return "";
	}
	
	/**
	 * 添加公共短消息图片
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=addTextImage")
	public String webapi30201_addTextImage(CMi404 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "添加公共短信息";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));			
			
						
			if (CommonUtil.isEmpty(form.getTitle())) {
				log.error(ERROR.PARAMS_NULL.getLogText("mstitle"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "消息标题");
			}			
//			
//			if (CommonUtil.isEmpty(form.getTheme())) {
//				log.error(ERROR.PARAMS_NULL.getLogText("theme"));
//				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//						.getValue(), "主题类型");
//			}
			
			if (CommonUtil.isEmpty(form.getDetail())) {
				log.error(ERROR.PARAMS_NULL.getLogText("detail()"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "消息描述");
			}
			
			if (CommonUtil.isEmpty(form.getTsmsg())) {
				log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "详细内容");
			}
			
			if (CommonUtil.isEmpty(form.getParam2())) {
				log.error(ERROR.PARAMS_NULL.getLogText("param2"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
						.getValue(), "图片信息");
			}
			
			if(CommonUtil.isEmpty(form.getCommsgid())){
				CMi401 mi401 = new CMi401();
				mi401.setCenterid(form.getCenterid());
				mi401.setCenterName(form.getCenterName());
				mi401.setTitle("多图文信息");
				mi401.setDetail("多图文信息,详细信息在MI404表中");
				mi401.setUserid(form.getUserid());
				mi401.setUsername(form.getUsername());	
				mi401.setSumcount(1);
				mi401.setPid(form.getPid());
				mi401.setMsgsource(form.getMsgsource());
				String commsgid = this.getWebApi302Service().webapi30201_addTextImageMai(mi401);
				this.getWebApi302Service().insertGroupWaitMessage(mi401);
				form.setCommsgid(commsgid);
			}
			if(!CommonUtil.isEmpty(form.getMsmscommsgid())){
				int sucess = this.getWebApi302Service().webapi30201_updateTextImage(form);				
			}else{
				CMi401 mi401 = new CMi401();
				mi401.setCenterid(form.getCenterId());
				mi401.setCommsgid(form.getCommsgid());
				List<Mi404> result = this.getWebApi302Service().webapi30201_queryTextImage(mi401);
				if(result.size()==10){
					log.error(ERROR.SYS.getLogText("微信推1次送最多10条图文信息"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA
							.getValue(), "图文短消息1次推送最多10条图文信息");
				}
				this.getWebApi302Service().webapi30201_addTextImage(form);
			}		
			modelMap.put("commsgid", form.getCommsgid());
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		return "";
	}
	
	
	
	/**
	 * 删除公共短信息
	 * 
	 * @param form
	 *            listCommsgid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=delete")
	public String webapi30201_delete(WebApi30201_deleteForm form,
			ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除公共短消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi302Service().webapi30201_delete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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

	/**
	 * 推送公共短信息
	 * 
	 * @param form
	 *            commsgid、centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=groupSend")
	public String webapi30201_groupSend(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		Logger log = LoggerUtil.getLogger();
		String businName = "推送公共短消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			int count = 0;
			
			this.getWebApi302Service().sendGroupWaitMessage(form);
			count = this.getWebApi302Service().webapi30201_send(form,modelMap,request,response);
			form.setSuccessnum(count);
			modelMap.clear();
			if(count == 0){
				form.setStatus("0");
				modelMap.put("recode", "999999");
				modelMap.put("msg", "发送失败!");
			}else{
				form.setStatus("1");
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}
			this.getWebApi302Service().updateGroupSendStatus(form);
			modelMap.put("count", count);
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

	/**
	 * 查询公共短信息
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=query")
	public String webapi30201_query(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询公共短消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			List<Mi401> result = this.getWebApi302Service().webapi30201_query(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("rows", result);
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
	
	/**
	 * 根据ID查询公共短信息
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=queryById")
	public String webapi30201_queryById(String commsgid, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "根据ID查询公共短消息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(commsgid));
			Mi401 result = this.getWebApi302Service().webapi30201_queryById(commsgid);
			modelMap.put("result", result);
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}
	
	
	/**
	 * 查询公共短信息图文明细
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=queryTextImage")
	public String webapi30201_queryTextImage(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询公共短消息微信图文多条";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			modelMap.clear();
			if(!CommonUtil.isEmpty(form.getCommsgid())){
				List<Mi404> result = this.getWebApi302Service().webapi30201_queryTextImage(form);
				modelMap.put("rows", result);
			}			

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
	

	/**
	 * 修改公共短消息
	 * 
	 * @param form
	 *            commsgid、centerid（可空）、title（可空）、detail（可空）、param1（可空）、param2（可空
	 *            ）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=edit")
	public String webapi30201_edit(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改公共短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			this.getWebApi302Service().webapi30201_edit(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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

	/**
	 * 审批公共短消息
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=auth")
	public String webapi30201_auth(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "审批公共短信息";
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi302Service().webapi30201_auth(form);

			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 审批报盘短消息
	 * 
	 * @param form
	 *            centerId、title、detail、param2（可空）
	 * @param modelMap
	 * @return
	 */
//	@RequestMapping(value = "/webapi30202.json", params = "method=auth")
//	public String webapi30202_auth(WebApi30201_deleteForm form, ModelMap modelMap) {
//		Logger log = LoggerUtil.getLogger();
//		String businName = "审批公共短信息";
//		try {
//			log.info(LOG.START_BUSIN.getLogText(businName));
//			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//					.getStringParams(form)));
//			// 业务处理
//			this.getWebApi302Service().webapi30202_auth(form);
//
//
//			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
//			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
//			log.info(LOG.END_BUSIN.getLogText(businName));
//		} catch (TransRuntimeErrorException tre) {
//			log.error(tre.getMessage());
//			throw tre;
//		} catch (Exception e) {
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
//		}
//		return "";
//	}
	
	/**
	 * 导入报盘短信息
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/webapi30202.do", params = "method=import")
	public void webapi30202_import(CMi401 form,@RequestParam MultipartFile importFile, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "WEB端导入报盘短信息";
		System.out.println(businName+"start");
		HashMap map = new HashMap();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			form = this.getWebApi302Service().webapi30202_import(form,importFile);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
//			log.error(tre.getMessage());
//			throw tre;
			map.put("recode", tre.getErrcode());
			map.put("msg", tre.getMessage());
			String rep = JsonUtil.getDisableHtmlEscaping().toJson(map);
			response.getOutputStream().write(rep.getBytes(encoding));
			return ;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
//			log.error(tre.getMessage());
//			log.error(e.getMessage(), e);
//			throw tre;
			map.put("recode", tre.getErrcode());
			map.put("msg", tre.getMessage());
			String rep = JsonUtil.getDisableHtmlEscaping().toJson(map);
			response.getOutputStream().write(rep.getBytes(encoding));
			return ;
		}
		System.out.println(businName+"end");
		
		
		map.put("recode", "000000");
		map.put("msg", "成功");
		String rep = JsonUtil.getDisableHtmlEscaping().toJson(map);
		response.getOutputStream().write(rep.getBytes(encoding));
		
//		return "forward:page30202.html";
	}
	
	
	/**
	 * 查询报盘短信息批次
	 * 
	 * @param form
	 *            centerId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30202.json", params = "method=query")
	public String webapi30202_query(WebApiCommonForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询报盘短信息（分页）";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			
			List<Mi401> result = this.getWebApi302Service().webapi30202_query(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("rows", result);
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

	/**
	 * 查询报盘短信息明细（分页）
	 * 
	 * @param form
	 *            centerId、seqid、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi3020201.json", params = "method=query")
	public String webapi3020201_query(WebApi30202_quertForm form,
			ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询报盘短信息（分页）";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			WebApi30202_queryResult result = this.getWebApi302Service().webapi3020201_query(form);

			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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

	

	/**
	 * 推送报盘短信息推送
	 * 
	 * @param form
	 *            centerId、seqid
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30202.json", params = "method=send")
	public String webapi30202_send(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		Logger log = LoggerUtil.getLogger();
		String businName = "推送报盘短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
//			this.getWebApi302Service().webapi30202_insertWaitSend(form,modelMap,request,response);//推送前入403表
			form.setPusMessageType("02");
			String pusMessageType = CommonUtil.isEmpty(form.getPusMessageType())?"02":form.getPusMessageType();
			this.getWebApi302Service().insertSendTable(form ,request ,response);
			
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "forward:page30202.html";
	}

	/**
	 * 删除报盘短信息
	 * 
	 * @param form
	 *            centerId、listSerno
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30202.json", params = "method=delete")
	public String webapi30202_dlete(CMi401 form ,ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除报盘短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			// 业务处理
			this.getWebApi302Service().webapi30202_dlete(form);

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		return "forward:page30202.html";
	}

	/**
	 * 查询已推送公共短信息(第一层汇总信息)
	 * 
	 * @param form
	 *            centerId、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30203.json", params = "method=query")
	public String webapi30203(WebApi30203Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询已推送公共短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			// 业务处理
			WebApi30203_queryResult result = this.getWebApi302Service().webapi3020203(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());
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
	
	
	/**
	 * 查询已推送公共短信息(第二层汇总信息)
	 * 
	 * @param form
	 *            centerId、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30203.json", params = "method=queryTwo")
	public String queryTwo(WebApi30203Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询已推送应用短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			// 业务处理
			WebApi30202_queryResult result = this.getWebApi302Service().webapi3020206(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());
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
	
	
	

	/**
	 * 已推送信息查询——MI100表，应用信息列表
	 * 
	 * @param form
	 *            centerId、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30204.json", params = "method=query")
	public String webapi30204(WebApi30204Form form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询已推送报盘短信息批次（分页）";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
			WebApi30204_queryResult result=null;
//			if (!CommonUtil.isEmpty(form.getChecktitle())||!CommonUtil.isEmpty(form.getChecktext())) {
//				result = this.getWebApi302Service().webapi30205(form);
//			}else{
//				result = this.getWebApi302Service().webapi30204(form);
//			}
			
			result = this.getWebApi302Service().webapi30204(form);

			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());
			
			log.info(LOG.END_BUSIN.getLogText(businName));
		} catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		System.out.println(businName+"end");
		return "";
	}

	/**
	 * 查询已推送MI403个信息，传入消息ID，PID
	 * 
	 * @param form
	 *            centerId、seqid、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi3020401.json", params = "method=query")
	public String webapi3020401(WebApi30202_quertForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询已推送信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			WebApi30206_queryResult result = this.getWebApi302Service().webapi3020401(form);
			modelMap.clear();
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		System.out.println(businName+"start");
		return "";
	}
	
	/**
	 * 查询多图文信息
	 * 
	 * @param form
	 *            centerId、seqid、page、rows
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi3020402.json", params = "method=query")
	public String webapi3020402(WebApi30202_quertForm form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "查询已推送报盘短信息（分页）";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			WebApi30205_queryResult result = this.getWebApi302Service().webapi3020402(form);
			modelMap.clear();
			modelMap.put("total", result.getTotal());
			modelMap.put("pageSize", result.getPageSize());
			modelMap.put("pageNumber", result.getPageNumber());
			modelMap.put("rows", result.getRows());

			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
		return "";
	}

	/**
	 * @return the webApi302Service
	 */
	public WebApi302Service getWebApi302Service() {
		return webApi302Service;
	}

	/**
	 * @param webApi302Service
	 *            the webApi302Service to set
	 */
	public void setWebApi302Service(WebApi302Service webApi302Service) {
		this.webApi302Service = webApi302Service;
	}
	
	
	/**
	 * 删除图文信息
	 * 
	 * @param form
	 *            centerId、listSerno
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30202.json", params = "method=deletems")
	public String webapi30202_deletems(CMi404 form,
			ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "删除图文信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			// 业务处理
			this.getWebApi302Service().webapi30201_deletems(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
	
	
	/**
	 * 修改文本短消息
	 * 
	 * @param form
	 *            commsgid、centerid（可空）、title（可空）、detail（可空）、param1（可空）、param2（可空
	 *            ）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=editText")
	public String webapi30201_editText(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改公共短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			this.getWebApi302Service().webapi30201_editText(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
	
	
	/**
	 * 修改图片短消息
	 * 
	 * @param form
	 *            commsgid、centerid（可空）、title（可空）、detail（可空）、param1（可空）、param2（可空
	 *            ）
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=editImage")
	public String webapi30201_editImage(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改图片短信息";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			
			this.getWebApi302Service().webapi30201_editText(form);
			modelMap.clear();
			modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
			modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
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
	
	/**
	 * 修改图文短消息顺序
	 * 
	 * @param form
	 *            
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=orderbynum")
	public String webapi30201_orderbynum(String datalist, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "修改图文短消息顺序";		
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));		
		
	    JSONArray arr= JSONArray.fromObject(datalist);	    
	    try {
	    	this.getWebApi302Service().webapi30201_orderbynum(arr);
	    }catch (TransRuntimeErrorException tre) {
			log.error(tre.getMessage());
			throw tre;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			log.error(tre.getMessage());
			log.error(e.getMessage(), e);
			throw tre;
		}
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		return "";
	}
	
	/**
	 * 打开和关闭评论
	 * @param form
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=commentCtrl")
	public void webapi30201_multiTextImageSetting(CMi404 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "开关图文消息评论";		
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		try {
			String result = this.getWebApi302Service().webapi30201_commentCtrl(form);
			if(CommonUtil.isEmpty(result)){
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.put("recode", "999999");
				modelMap.put("msg", result);
			}
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
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
	}
	
	/**
	 * 多图文预览发送
	 * @param form
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=perviewSend")
	public void webapi30201_perviewSend(CMi401 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "图文推送消息预览";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			String result = this.getWebApi302Service().webapi30201_perviewSend(form,modelMap);
			modelMap.clear();
			if("000000".equals(result)){
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.put("recode", "999999");
				modelMap.put("msg", result);
			}
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
		log.info(LOG.END_BUSIN.getLogText(businName));
	}
	
	/**
	 * 微信推送消息删除
	 * @param form
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/webapi30201.json", params = "method=delSend")
	public void webapi30201_deleteSend(CMi404 form, ModelMap modelMap) {
		Logger log = LoggerUtil.getLogger();
		String businName = "微信推送消息删除";
		System.out.println(businName+"start");
		try {
			log.info(LOG.START_BUSIN.getLogText(businName));
			log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(form)));
			String result = this.getWebApi302Service().webapi30201_deleteSend(form,modelMap);
			modelMap.clear();
			if("".equals(result)){
				modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
				modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
			}else{
				modelMap.put("recode", "999999");
				modelMap.put("msg", result);
			}
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
		log.info(LOG.END_BUSIN.getLogText(businName));
	}
}
