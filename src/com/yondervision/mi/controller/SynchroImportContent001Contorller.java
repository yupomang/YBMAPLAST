package com.yondervision.mi.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.SynchroImportContentScInfoBean;
import com.yondervision.mi.form.SynchroImportContentXgInfoBean;
import com.yondervision.mi.form.SynchroImportContentXzInfoBean;
import com.yondervision.mi.form.SynchroImportFileInfoBean;
import com.yondervision.mi.form.SynchroImportRelationBean;
import com.yondervision.mi.result.SynchroImportContentAddResult;
import com.yondervision.mi.service.SynchroImportContent001Service;
import com.yondervision.mi.service.WebApi700Service;
import com.yondervision.mi.service.impl.SynchroImportContent001ServiceImpl;

/** 
* @ClassName: SynchroImportContent001Contorller 
* @Description: 同步内容-导入到平台内部
* @author gongqi  
* @date April 13, 2016 9:33:25 AM   
*/ 
@Controller
public class SynchroImportContent001Contorller {
	@Autowired
	private SynchroImportContent001Service synchroImportContent001ServiceImpl;
	public void setSynchroImportContent001ServiceImpl(
			SynchroImportContent001ServiceImpl synchroImportContent001ServiceImpl) {
		this.synchroImportContent001ServiceImpl = synchroImportContent001ServiceImpl;
	}
	
	@Autowired
	private WebApi700Service webApi700ServiceImpl;
	public void setWebApi700ServiceImpl(WebApi700Service webApi700ServiceImpl) {
		this.webApi700ServiceImpl = webApi700ServiceImpl;
	}

	/**
	 * 同步导入文档详细内容
	 * @param paramdata 信息参数
	 * @param modelMap 返回数据容器
	 * @param request 请求request
	 * @return 回调页面名
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/synchroImportContent.json")
	public String synchroImportContent(String paramdata, ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "同步导入文档详细内容";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		System.out.println("synchroImportContent.json------paramdata==="+paramdata);
		
		List<SynchroImportContentAddResult> resultXzList = new ArrayList<SynchroImportContentAddResult>();
		
		List<SynchroImportContentXzInfoBean> xzList = new ArrayList<SynchroImportContentXzInfoBean>();
		List<SynchroImportContentXgInfoBean> xgList = new ArrayList<SynchroImportContentXgInfoBean>();
		List<SynchroImportContentScInfoBean> scList = new ArrayList<SynchroImportContentScInfoBean>();
		JSONObject paramdataJsonObj = new JSONObject(paramdata);
		String centerid = "";
		if(paramdataJsonObj.has("centerid")){
			centerid = paramdataJsonObj.getString("centerid");
			if (paramdataJsonObj.has("xzlist")) {
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse(paramdataJsonObj.getString("xzlist"));
				JsonArray ja = el.getAsJsonArray();
				Gson gson = new GsonBuilder().create();
				Iterator<JsonElement> it = ja.iterator();
				while (it.hasNext()) {
					JsonElement je = (JsonElement) it.next();
					SynchroImportContentXzInfoBean bean = gson.fromJson(je, SynchroImportContentXzInfoBean.class);
					xzList.add(bean);
				}
			}
			if (paramdataJsonObj.has("xglist")) {
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse(paramdataJsonObj.getString("xglist"));
				JsonArray ja = el.getAsJsonArray();
				Gson gson = new GsonBuilder().create();
				Iterator<JsonElement> it = ja.iterator();
				while (it.hasNext()) {
					JsonElement je = (JsonElement) it.next();
					SynchroImportContentXgInfoBean bean = gson.fromJson(je, SynchroImportContentXgInfoBean.class);
					xgList.add(bean);
				}
			}
			if (paramdataJsonObj.has("sclist")) {
				JsonParser parser = new JsonParser();
				JsonElement el = parser.parse(paramdataJsonObj.getString("sclist"));
				JsonArray ja = el.getAsJsonArray();
				Gson gson = new GsonBuilder().create();
				Iterator<JsonElement> it = ja.iterator();
				while (it.hasNext()) {
					JsonElement je = (JsonElement) it.next();
					SynchroImportContentScInfoBean bean = gson.fromJson(je, SynchroImportContentScInfoBean.class);
					scList.add(bean);
				}
			}
			
			// 操作数据库
			System.out.println("synchroImportContent.json------xzList.size==="+xzList.size());
			System.out.println("synchroImportContent.json------xgList.size==="+xgList.size());
			System.out.println("synchroImportContent.json------scList.size==="+scList.size());
			resultXzList = synchroImportContent001ServiceImpl.synchroImportDataDeal(centerid, xzList,
					xgList, scList);
			
		}else{
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心代码centerid");
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("xzlist", resultXzList);
		modelMap.put("xglist", new ArrayList());
		modelMap.put("sclist", new ArrayList());
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	/**
	 * 同步导入文档-附件上传
	 * @param request
	 * @param response
	 * @param importFile
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/synchroImportFileuploader.json")
	public String uploadimg(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile importFile, ModelMap modelMap) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String businName = "同步导入文档与附件关联关系";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
		
		String imgUploadUrl = "";
		// 业务处理
		long maxSize = 1024*1024;
		String fileExtPath = "synchro_import_content_upload_manage";
		// TODO 注意下 dir  参数的传递是否有
		imgUploadUrl = webApi700ServiceImpl.uploadImageReturnStr(request, response,
				importFile, maxSize, fileExtPath, "syupload");
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("showname", imgUploadUrl);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}
	
	/**
	 * 同步导入文档与附件关联关系
	 * @param paramdata 信息参数
	 * @param modelMap 返回数据容器
	 * @param request 请求request
	 * @return 回调页面名
	 * @throws Exception
	 */
	@RequestMapping("/synchroImportRelation.json")
	public String synchroImportRelation(String paramdata, ModelMap modelMap, HttpServletRequest request) throws Exception{
		Logger log = LoggerUtil.getLogger();

		String businName = "同步导入文档与附件关联关系";
		log.info(LOG.START_BUSIN.getLogText(businName));
		log.debug(LOG.START_BUSIN.getLogText(businName));
//		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
//				.getStringParams(paramdata)));
		
		System.out.println("synchroImportRelation.json------paramdata==="+paramdata);
		
		List<SynchroImportRelationBean> ralationList = new ArrayList<SynchroImportRelationBean>();
		JSONObject paramdataJsonObj = new JSONObject(paramdata);
		String centerid = "";
		String operUser = "";
		if(paramdataJsonObj.has("centerid")){
			centerid = paramdataJsonObj.getString("centerid");
		}else{
			//throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心代码centerid");
			centerid = "syupload";
		}
		
		if (paramdataJsonObj.has("relationlist")) {
			JsonParser parser = new JsonParser();
			JsonElement el = parser.parse(paramdataJsonObj.getString("relationlist"));
			JsonArray ja = el.getAsJsonArray();
			Gson gson = new GsonBuilder().create();
			Iterator<JsonElement> it = ja.iterator();
			while (it.hasNext()) {
				JsonElement je = (JsonElement) it.next();
				SynchroImportRelationBean bean = gson.fromJson(je, SynchroImportRelationBean.class);
				ralationList.add(bean);
			}
		}

		for(int i = 0; i < ralationList.size(); i++){
			List<SynchroImportFileInfoBean> fileinfolist = ralationList.get(i).getFileinfolist();
			webApi700ServiceImpl.webapi70008(centerid, Integer.parseInt(ralationList.get(i).getDocid()), fileinfolist, operUser);
		}
		
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		
		log.debug(LOG.END_BUSIN.getLogText(businName));
		log.info(LOG.END_BUSIN.getLogText(businName));
		
		return "";
	}

}
