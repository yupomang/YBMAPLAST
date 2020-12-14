package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.Mi708DAO;
import com.yondervision.mi.dto.CMi701;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi708;
import com.yondervision.mi.dto.Mi708Example;
import com.yondervision.mi.form.SynchroExportBean;
import com.yondervision.mi.form.SynchroExportContentPubInfoBean;
import com.yondervision.mi.form.SynchroExportContentScInfoBean;
import com.yondervision.mi.form.SynchroImportFileInfoBean;
import com.yondervision.mi.form.WebApi70004Form;
import com.yondervision.mi.result.WebApi70004_queryResult;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.service.WebApi700Service;
import com.yondervision.mi.util.ClearWebCacheUtils;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.HttpUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: WebApi700ServiceImpl 
* @Description: 内容管理
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi700ServiceImpl implements WebApi700Service {
	public CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	public Mi708DAO mi708Dao = null;
	public void setMi708Dao(Mi708DAO mi708Dao) {
		this.mi708Dao = mi708Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}

	public void webapi70001(CMi701 form, String reqUrl) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getTitle())) {
			log.error(ERROR.PARAMS_NULL.getLogText("标题"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
		}
		if (CommonUtil.isEmpty(form.getContentTmp())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容");
		}
		// 校验seqno的采号
		String seqno = commonUtil.genKey("MI701").toString();
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("新闻动态MI701"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("新闻动态MI701"));
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		String systemdate = CommonUtil.getSystemDate();
		Date date = new Date();
		//如果是发布，先判断是否要同步到第三方平台
		if(Constants.PUBLISH_FLG_ONE.equals(form.getFreeuse3())){
			// 同步到其他渠道
			String synchroExportCenterids = PropertiesReader.getProperty("properties.properties", "synchro_export_content_centerids");
			if(synchroExportCenterids.indexOf(form.getCenterId())>=0){
				// TODO 此处考虑写成动态的可配置，不同中心采用各自的通信方法
				/*List<SynchroExportContentPubInfoBean> exportBeanList = new ArrayList<SynchroExportContentPubInfoBean>();
				SynchroExportContentPubInfoBean exportBean = new SynchroExportContentPubInfoBean();
				exportBean = new SynchroExportContentPubInfoBean();
				exportBean.setId(seqno);
				exportBean.setClassification(form.getClassification());
				exportBean.setDocType(Constants.DOC_TYPE_HTML);
				exportBean.setDocstatus(Constants.PUBLISH_FLG_ONE);
				if (CommonUtil.isEmpty(form.getImage())){
					exportBean.setTitleImg(form.getImage());
				}else{
					if(form.getImage().indexOf(reqUrl) < 0){
						exportBean.setTitleImg(reqUrl.concat(form.getImage()));
					}else{
						exportBean.setTitleImg(form.getImage());
					}
					
				}
				exportBean.setTitle(form.getTitle());
				if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
					if (form.getContentTmp().length() <= 30) {
						exportBean.setAbstracts(form.getContentTmp());
					}else{
						exportBean.setAbstracts(form.getContentTmp().substring(0, 30)+"...");
					}
				}else{
					exportBean.setAbstracts(form.getIntroduction());
				}

				String contentTmp = form.getContent();
				System.out.println("mi701.getContent()===="+contentTmp);
				exportBean.setContentHtml(contentTmp);
				exportBean.setContentTxt(form.getContentTmp());
				exportBean.setOperuser(form.getUserid());
				exportBean.setCreatetime(systemdate);
				exportBean.setModifytime("");
				exportBean.setPublishtime(form.getReleasetime() +" " + dateFormatter.format(date).substring(11));

				exportBeanList.add(exportBean);
				SynchroExportBean expotrBean = new SynchroExportBean();
				expotrBean.setPublishList(exportBeanList);
				expotrBean.setDeleteList(new ArrayList<SynchroExportContentScInfoBean>());
				ObjectMapper mapper = new  ObjectMapper();
				JSONObject paramJsonObj = mapper.convertValue(expotrBean, JSONObject.class);
				
				String resStr = msgSendApi001Service.synchroExportSend(form.getCenterId(), paramJsonObj.toString());
				System.out.println("向第三方平台同步发布内容结果=="+resStr);
				
				JSONObject resJsonObj = JSONObject.fromObject(resStr);
				String recode = null, msg = null;
				if (resJsonObj.has("recode")){
					recode = resJsonObj.getString("recode");
					if (resJsonObj.has("msg")){
						msg = resJsonObj.getString("msg");
					}
					if(!"000000".equals(recode)){
						log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
						throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生问题："+msg);
					}
				}else {
					log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生异常");
				}*/
			}
		}
		
		Mi701WithBLOBs record = new Mi701WithBLOBs();
		record.setSeqno(Integer.parseInt(seqno));
		record.setCenterid(form.getCenterId());
		record.setClassification(form.getClassification());
		record.setTitle(form.getTitle());
		if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
			if (form.getContentTmp().length() <= 30) {
				record.setIntroduction(form.getContentTmp());
			}else{
				record.setIntroduction(form.getContentTmp().substring(0, 30)+"...");
			}
		}else{
			record.setIntroduction(form.getIntroduction());
		}
		record.setContent(form.getContent());
		
		record.setReleasetime(form.getReleasetime() +" " + dateFormatter.format(date).substring(11));
		System.out.println("addd----image=="+form.getImage());
		if (CommonUtil.isEmpty(form.getImage())){
			System.out.println("addd----image==true----null---"+form.getImage());
			record.setImage("");
		}else{
			if(form.getImage().indexOf(reqUrl) < 0){
				System.out.println("addd----image==true----if---"+form.getImage());
				record.setImage(reqUrl.concat(form.getImage()));
			}else{
				System.out.println("addd----image==true---else---"+form.getImage());
				record.setImage(form.getImage());
			}
		}

		record.setValidflag(Constants.IS_VALIDFLAG);
		record.setDatecreated(systemdate);
		record.setLoginid(form.getUserid());
		String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+form.getCenterId()+"_"+form.getClassification());
		record.setFreeuse1(defaultImgUrl);
		record.setPraisecounts(0);
		record.setFreeuse3(form.getFreeuse3());//信息属性：0：草稿；1：发布;2:审批中；3已审批
		record.setFreeuse5(Constants.CONTENT_SOURCE_PLAT_EDIT);//内容管理_数据来源：1：后台维护;2：后台同步
		record.setFreeuse8(form.getFreeuse8());
		record.setContenttxt(form.getContentTmp());
		cmi701Dao.insert(record);
		ClearWebCacheUtils.clearInsert(form);//清除网站缓存
	}

	public int webapi70002(CMi701 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getSeqnos())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		
		String[] seqnos = form.getSeqnos().toString().split(",");
		List<String> seqnoList = new ArrayList<String>();
		List<Mi701WithBLOBs> deleteList = new ArrayList<Mi701WithBLOBs>();//清除网站缓存
		for (int i = 0; i < seqnos.length; i++) {
			// 判断要删除记录是否在走审批，走审批的不允许删除
			Mi701WithBLOBs qryResult = cmi701Dao.selectByPrimaryKey(Integer.parseInt(seqnos[i]));
			if(Constants.PUBLISH_FLG_TWO.equals(qryResult.getFreeuse3())){
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"存在正在审批中的待删除记录，请检查后提交！");
			}
			
			seqnoList.add(seqnos[i]);
			deleteList.add(qryResult);//清除网站缓存
		}
		
		String systemdate = CommonUtil.getSystemDate();
		//株洲没有删除同步
//		// 先将要删除的内容同步到其他渠道
//		String synchroExportCenterids = PropertiesReader.getProperty("properties.properties", "synchro_export_content_centerids");
//		if(synchroExportCenterids.indexOf(form.getCenterId())>=0){
//			List<SynchroExportContentScInfoBean> exportBeanList = new ArrayList<SynchroExportContentScInfoBean>();
//			SynchroExportContentScInfoBean exportBean = new SynchroExportContentScInfoBean();
//			for(int i = 0; i < seqnoList.size(); i++){
//				
//				exportBean = new SynchroExportContentScInfoBean();
//				exportBean.setId(seqnoList.get(i));
//				exportBean.setModifytime(systemdate);
//				exportBean.setOperuser(form.getUserid());
//
//				exportBeanList.add(exportBean);
//			}
//			SynchroExportBean expotrBean = new SynchroExportBean();
//			expotrBean.setPublishList(new ArrayList<SynchroExportContentPubInfoBean>());
//			expotrBean.setDeleteList(exportBeanList);
//			ObjectMapper mapper = new  ObjectMapper();
//			JSONObject paramJsonObj = mapper.convertValue(expotrBean, JSONObject.class);
//			
//			String resStr = msgSendApi001Service.synchroExportSend(form.getCenterId(), paramJsonObj.toString());
//			System.out.println("向第三方平台同步删除内容结果=="+resStr);
//			
//			JSONObject resJsonObj = JSONObject.fromObject(resStr);
//			String recode = null, msg = null;
//			if (resJsonObj.has("recode")){
//				recode = resJsonObj.getString("recode");
//				if (resJsonObj.has("msg")){
//					msg = resJsonObj.getString("msg");
//				}
//				if(!"000000".equals(recode)){
//					log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
//					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步删除内容发生问题："+msg);
//				}
//			}else {
//				log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
//				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步删除内容发生异常");
//			}
//		}
		
		Mi701WithBLOBs record = new Mi701WithBLOBs();
		// 修改时间
		record.setDatemodified(systemdate);
		// 删除标记
		record.setValidflag(Constants.IS_NOT_VALIDFLAG);
		// 删除者
		record.setLoginid(form.getUserid());
		
		Mi701Example example = new Mi701Example();
		example.createCriteria().andSeqnoIn(seqnoList);
		
		int result = cmi701Dao.updateByExampleSelective(record, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号seqno:"+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"新闻动态MI701");
		}
		ClearWebCacheUtils.clearDelete(deleteList);//清除网站缓存
		return result;
	}

	public int webapi70003(CMi701 form, String reqUrl) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getSeqno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getTitle())) {
			log.error(ERROR.PARAMS_NULL.getLogText("标题"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
		}
		if (CommonUtil.isEmpty(form.getContentTmp())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容");
		}
		
		// 判断要修改记录是否在走审批，走审批的不允许修改
		Mi701WithBLOBs qryResult = cmi701Dao.selectByPrimaryKey(form.getSeqno());
		if(Constants.PUBLISH_FLG_TWO.equals(qryResult.getFreeuse3())){
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前记录正在审批中，不能进行修改！");
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		String systemdate = CommonUtil.getSystemDate();
		Date date = new Date();
		Mi701WithBLOBs qryMi701 = cmi701Dao.selectByPrimaryKey(form.getSeqno());
		//如果是发布，先判断是否要同步到第三方平台
		if(Constants.PUBLISH_FLG_ONE.equals(form.getFreeuse3())){
			// 同步到其他渠道
			String synchroExportCenterids = PropertiesReader.getProperty("properties.properties", "synchro_export_content_centerids");
			if(synchroExportCenterids.indexOf(form.getCenterId())>=0){
				
				List<SynchroExportContentPubInfoBean> exportBeanList = new ArrayList<SynchroExportContentPubInfoBean>();
				SynchroExportContentPubInfoBean exportBean = new SynchroExportContentPubInfoBean();
				exportBean = new SynchroExportContentPubInfoBean();
				exportBean.setId(form.getSeqno().toString());
				exportBean.setClassification(form.getClassification());
				exportBean.setDocType(Constants.DOC_TYPE_HTML);
				exportBean.setDocstatus(Constants.PUBLISH_FLG_ONE);
				if (CommonUtil.isEmpty(form.getImage())){
					exportBean.setTitleImg(form.getImage());
				}else{
					exportBean.setTitleImg(reqUrl.concat(form.getImage()));
				}
				exportBean.setTitle(form.getTitle());
				if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
					if (form.getContentTmp().length() <= 30) {
						exportBean.setAbstracts(form.getContentTmp());
					}else{
						exportBean.setAbstracts(form.getContentTmp().substring(0, 30)+"...");
					}
				}else{
					exportBean.setAbstracts(form.getIntroduction());
				}

				String contentTmp = form.getContent();
				System.out.println("mi701.getContent()===="+contentTmp);
				exportBean.setContentHtml(contentTmp);
				exportBean.setContentTxt(form.getContentTmp());
				exportBean.setOperuser(form.getUserid());
				exportBean.setCreatetime(qryMi701.getDatecreated());
				exportBean.setModifytime(systemdate);
				if(!CommonUtil.isEmpty(qryMi701.getReleasetime())){
					exportBean.setPublishtime(form.getReleasetime() +" " + qryMi701.getReleasetime().substring(11));
				}else{
					exportBean.setPublishtime(form.getReleasetime() +" " + dateFormatter.format(date).substring(11));
				}

				exportBeanList.add(exportBean);
				SynchroExportBean expotrBean = new SynchroExportBean();
				expotrBean.setPublishList(exportBeanList);
				expotrBean.setDeleteList(new ArrayList<SynchroExportContentScInfoBean>());
				ObjectMapper mapper = new  ObjectMapper();
				JSONObject paramJsonObj = mapper.convertValue(expotrBean, JSONObject.class);
				
				String resStr = msgSendApi001Service.synchroExportSend(form.getCenterId(), paramJsonObj.toString());
				System.out.println("向第三方平台同步发布内容结果=="+resStr);
				
				JSONObject resJsonObj = JSONObject.fromObject(resStr);
				String recode = null, msg = null;
				if (resJsonObj.has("recode")){
					recode = resJsonObj.getString("recode");
					if (resJsonObj.has("msg")){
						msg = resJsonObj.getString("msg");
					}
					if(!"000000".equals(recode)){
						log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
						throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生问题："+msg);
					}
				}else {
					log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生异常");
				}
			}
		}
		
		Mi701WithBLOBs mi701 = new Mi701WithBLOBs();
		mi701.setTitle(form.getTitle());
		mi701.setClassification(form.getClassification());
		if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
			if (form.getContentTmp().length() <= 30) {
				mi701.setIntroduction(form.getContentTmp());
			}else{
				mi701.setIntroduction(form.getContentTmp().substring(0, 30)+"...");
			}
		}else{
			mi701.setIntroduction(form.getIntroduction());
		}
		mi701.setContent(form.getContent());
		if (CommonUtil.isEmpty(form.getImage()) || "null".equals(form.getImage())){
			mi701.setImage("");
		}else{
			if(form.getImage().indexOf(reqUrl) < 0){
				mi701.setImage(reqUrl.concat(form.getImage()));
			}else{
				mi701.setImage(form.getImage());
			}
		}
		
		mi701.setDatemodified(systemdate);
		if(!CommonUtil.isEmpty(qryMi701.getReleasetime())){
			mi701.setReleasetime(form.getReleasetime() +" " + qryMi701.getReleasetime().substring(11));
		}else{
			mi701.setReleasetime(form.getReleasetime() +" " + dateFormatter.format(date).substring(11));
		}
		mi701.setLoginid(form.getUserid());
		mi701.setFreeuse3(form.getFreeuse3());
		mi701.setFreeuse8(form.getFreeuse8());
		mi701.setContenttxt(form.getContentTmp());
		
		mi701.setSeqno(form.getSeqno());
		
		int result = cmi701Dao.updateByPrimaryKeySelective(mi701);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("新闻动态MI701","序号seqno："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"序号："+form.getSeqno());
		}
		ClearWebCacheUtils.clearInsert(form);//清除网站缓存
		return result;
	}

	public WebApi70004_queryResult webapi70004(WebApi70004Form form)
			throws Exception {
		WebApi70004_queryResult result = cmi701Dao.selectMi701Page(form);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi701WithBLOBs> webapi70005(CMi701 form)throws Exception {
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno asc");
		Mi701Example.Criteria ca = mi701Example.createCriteria();
		ca.andSeqnoEqualTo(form.getSeqno());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi701WithBLOBs> resultList = cmi701Dao.selectByExampleWithBLOBs(mi701Example);
		for (int i =0; i <= resultList.size()-1; i ++) {
			Mi701WithBLOBs mi701 = resultList.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			resultList.set(i, mi701);
		}
		return resultList;
	}
	
	public void uploadImage(HttpServletRequest request, HttpServletResponse response,
			MultipartFile image, long maxSize, String fileExtPath, String centerid) throws Exception{
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			writeMsg(response, "请选择文件。");
			return;
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "目录名不正确");
		}

		if (image.getSize() > maxSize){
			writeMsg(response, "上传文件大小超过"+maxSize+"。");
			return;
		}
		
		// 检查扩展名
		String imageFileName = image.getOriginalFilename();
		String fileExt = imageFileName.substring(
				imageFileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(
				fileExt)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)+"格式。");
		}

		String filePath = CommonUtil.getFileFullPath(fileExtPath, centerid, true) + File.separator;
		File dirCityFile = new File(filePath);
		if (!dirCityFile.exists()) {
			dirCityFile.mkdirs();
		}
		// 检查目录
		File uploadDir = new File(filePath);
		System.out.println("uploadImage---filePath====="+filePath);
		if (!uploadDir.isDirectory()) {
			writeMsg(response, "上传目录不存在。");
			return;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			writeMsg(response, "上传目录没有写权限。");
			return;
		}

		String saveUrl = "";
		// 创建文件夹
		filePath += dirName + File.separator;
		saveUrl += dirName + File.separator;
		File saveDirFile = new File(filePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		filePath += ymd + File.separator;
		saveUrl += ymd + File.separator;
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + fileExt;
		try {
			InputStream in = image.getInputStream();

			File uploadedFile = new File(filePath, newFileName);
			OutputStream out = new FileOutputStream(uploadedFile);
			byte[] bs = new byte[1024 * 1024];
			int count = 0;
			while ((count = in.read(bs)) > 0) {
				out.write(bs, 0, count);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			writeMsg(response, "上传文件失败。");
			return;
		}

		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 0);
		String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
				fileExtPath, centerid+File.separator+ saveUrl + newFileName, true);
		msg.put("url", imgUrl);
		writeJson(response, msg);
	}
	
	public String uploadImageReturnStr(HttpServletRequest request, HttpServletResponse response,
			MultipartFile image, long maxSize, String fileExtPath, String centerid) throws Exception{
		String imgUploadUrl = "";
			
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");

		response.setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"上传文件获取失败");
		}
		
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "目录名不正确");
		}

		if (image.getSize() > maxSize){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "上传文件大小超过"+maxSize+"。");
		}
		
		// 检查扩展名
		String imageFileName = image.getOriginalFilename();
		String fileExt = imageFileName.substring(
				imageFileName.lastIndexOf(".") + 1).toLowerCase();
		if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(
				fileExt)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
					"上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)+"格式。");
		}

		String filePath = CommonUtil.getFileFullPath(fileExtPath, centerid, true) + File.separator;
		File dirCityFile = new File(filePath);
		if (!dirCityFile.exists()) {
			dirCityFile.mkdirs();
		}
		// 检查目录
		File uploadDir = new File(filePath);
		System.out.println("uploadImageReturnStr---filePath====="+filePath);
		if (!uploadDir.isDirectory()) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
					"上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
					"上传目录没有写权限。");
		}

		String saveUrl = "";
		// 创建文件夹
		filePath += dirName + File.separator;
		saveUrl += dirName + File.separator;
		File saveDirFile = new File(filePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		filePath += ymd + File.separator;
		saveUrl += ymd + File.separator;
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + "." + fileExt;
		try {
			InputStream in = image.getInputStream();

			File uploadedFile = new File(filePath, newFileName);
			OutputStream out = new FileOutputStream(uploadedFile);
			byte[] bs = new byte[1024 * 1024];
			int count = 0;
			while ((count = in.read(bs)) > 0) {
				out.write(bs, 0, count);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
			"上传文件失败。");
		}

 
		imgUploadUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
				fileExtPath, centerid+File.separator+ saveUrl + newFileName, true);
		return imgUploadUrl;
	}
	
	/**
	 * 输出信息
	 * @param response
	 * @param message
	 */
	private void writeMsg(HttpServletResponse response, String message)
	{
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		writeJson(response, msg);
		
	}
	
	/**
	 *输出json
	 * @param response
	 * @param msg
	 */
	private void writeJson(HttpServletResponse response, Object msg)
	{
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            
			writer.println(JSON.toJSONString(msg));
            
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * 内容发布
	 */
	@SuppressWarnings("unchecked")
	public int webapi70007(CMi701 form, HttpServletRequest request) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getSeqnos())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		if (CommonUtil.isEmpty(form.getFreeuse3())) {
			log.error(ERROR.PARAMS_NULL.getLogText("信息属性"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"信息属性获取失败");
		}
		
		String[] seqnos = form.getSeqnos().toString().split(",");
		List<String> seqnoList = new ArrayList<String>();
		Mi701 qryMi701 = new Mi701();
		for (int i = 0; i < seqnos.length; i++) {
			// 查询判断只有已审批状态的才能做发布
			qryMi701 = cmi701Dao.selectByPrimaryKey(Integer.parseInt(seqnos[i]));
			if(Constants.PUBLISH_FLG_ONE.equals(qryMi701.getFreeuse3())){
				log.error(LOG.SELF_LOG.getLogText("此条内容已发布过，不再发布！"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此条内容已发布过，不再发布！");
			}/*else if (!Constants.PUBLISH_FLG_THREE.equals(qryMi701)){
				log.error(LOG.SELF_LOG.getLogText("此条内容需要审批后，才可进行发布！"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此条内容需要审批后，才可进行发布！");
			}*/
			seqnoList.add(seqnos[i]);
		}
		
		String publishid = "";
		// 同步到其他渠道
		String synchroExportCenterids = PropertiesReader.getProperty("properties.properties", "synchro_export_content_centerids");
		if(synchroExportCenterids.indexOf(form.getCenterId())>=0){
			List<SynchroExportContentPubInfoBean> exportBeanList = new ArrayList<SynchroExportContentPubInfoBean>();
			List<Mi701WithBLOBs> mi701List = new ArrayList<Mi701WithBLOBs>();
			Mi701Example mi701QryExample = new Mi701Example();
			mi701QryExample.createCriteria().andSeqnoIn(seqnoList);
			mi701List = cmi701Dao.selectByExampleWithBLOBs(mi701QryExample);
			
			Mi701WithBLOBs mi701Bean = new Mi701WithBLOBs();
			SynchroExportContentPubInfoBean exportBean = new SynchroExportContentPubInfoBean();
			for(int i = 0; i < mi701List.size(); i++){
				mi701Bean = mi701List.get(i);
//				exportBean = new SynchroExportContentPubInfoBean();
//				exportBean.setId(mi701Bean.getSeqno().toString());
//				exportBean.setClassification(mi701Bean.getClassification());
//				exportBean.setDocType(Constants.DOC_TYPE_HTML);
//				exportBean.setDocstatus(Constants.PUBLISH_FLG_ONE);
//				if (CommonUtil.isEmpty(mi701Bean.getImage())){
//					exportBean.setTitleImg(mi701Bean.getImage());
//				}else{
//					String rquestUrlTmp = request.getRequestURL().toString();
//					String contextPath = request.getContextPath();
//					String reqUrl = rquestUrlTmp.substring(0,request.getRequestURL().lastIndexOf(contextPath));
//					System.out.println("reqUrl="+reqUrl);
//					exportBean.setTitleImg(reqUrl.concat(mi701Bean.getImage()));
//				}
//				exportBean.setTitle(mi701Bean.getTitle());
//				exportBean.setAbstracts(mi701Bean.getIntroduction());
//				String contentTmp = mi701Bean.getContent();
//				System.out.println("mi701.getContent()===="+contentTmp);
//				exportBean.setContentHtml(contentTmp);
//				if(CommonUtil.isEmpty(mi701Bean.getContenttxt())){
//					exportBean.setContentTxt(contentTmp.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", ""));
//				}else{
//					exportBean.setContentTxt(mi701Bean.getContenttxt());
//				}
//				exportBean.setOperuser(form.getUserid());
//				if(mi701Bean.getDatecreated().length() > 19){
//					exportBean.setCreatetime(mi701Bean.getDatecreated().substring(0, 19));
//				}else{
//					exportBean.setCreatetime(mi701Bean.getDatecreated());
//				}
//				exportBean.setModifytime(mi701Bean.getDatemodified());
//				//exportBean.setPublishtime(dateFormatter.format(date));
//				exportBean.setPublishtime(mi701Bean.getReleasetime());
//
//				exportBeanList.add(exportBean);
				
				String synchroUrl = "";
				Map<String, String> paramMap = new HashMap<String, String>();
				if(CommonUtil.isEmpty(mi701Bean.getFreeuse12())){
					synchroUrl = PropertiesReader.getProperty("properties.properties", "synchro_insert_url"+mi701Bean.getCenterid());
				}else{
					synchroUrl = PropertiesReader.getProperty("properties.properties", "synchro_modify_url"+mi701Bean.getCenterid());
					paramMap.put("iid", mi701Bean.getFreeuse12().substring(0, mi701Bean.getFreeuse12().indexOf(".")));
					System.out.println("iid==="+mi701Bean.getFreeuse12());
					System.out.println("iid==substring===="+mi701Bean.getFreeuse12().substring(0, mi701Bean.getFreeuse12().indexOf(".")));
					
				}
				
				String channelid = "";
				channelid = PropertiesReader.getProperty("properties.properties", "content_classfication_"+mi701Bean.getCenterid()+"_"+mi701Bean.getClassification());
				
				System.out.println("channelid==="+channelid);
				System.out.println("releasetime==="+mi701Bean.getReleasetime().replace("-", "").replace(":", ""));
				
				System.out.println("timestamp==="+String.valueOf(System.currentTimeMillis()));
				paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
				paramMap.put("appid", "2ryc8b7g");
				paramMap.put("appsecret", "enwac4j4gw9bwh8q");
				paramMap.put("sign", "");
				paramMap.put("title", mi701Bean.getTitle());
				paramMap.put("author", "株洲市住房公积金管理中心");
				paramMap.put("source", "");
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
				Date date = new Date();
				mi701Bean.setReleasetime(mi701Bean.getReleasetime().substring(0, 10)+" " + dateFormatter.format(date).substring(11));
				System.out.println("synchroUrl----releatime===="+mi701Bean.getReleasetime().substring(0, 10)+" " + dateFormatter.format(date).substring(11));
				paramMap.put("releasetime", mi701Bean.getReleasetime().replace("-", "").replace(":", "").replace(" ", ""));
				paramMap.put("bereplytime", "");
				if(CommonUtil.isEmpty(mi701Bean.getImage())){
					paramMap.put("logo", "");
				}else{
					paramMap.put("logo", mi701Bean.getImage());
				}
				
				//paramMap.put("channelid", channelid);
				paramMap.put("channelid", channelid);
				paramMap.put("subtitle", "");
				paramMap.put("linktitle", "");
				paramMap.put("keywords", "");
				paramMap.put("summary", mi701Bean.getIntroduction());
				paramMap.put("loginname", "zzgjjuser");
				paramMap.put("content", mi701Bean.getContent());
				
				System.out.println("timestamp=="+paramMap.get("timestamp"));
				System.out.println("appid=="+paramMap.get("appid"));
				System.out.println("appsecret=="+paramMap.get("appsecret"));
				System.out.println("sign=="+paramMap.get("sign"));
				System.out.println("title=="+paramMap.get("title"));
				System.out.println("author=="+paramMap.get("author"));
				System.out.println("source=="+paramMap.get("source"));
				System.out.println("releasetime=="+paramMap.get("releasetime"));
				System.out.println("bereplytime=="+paramMap.get("bereplytime"));
				System.out.println("logo=="+paramMap.get("logo"));
				System.out.println("channelid=="+paramMap.get("channelid"));
				System.out.println("subtitle=="+paramMap.get("subtitle"));
				System.out.println("linktitle=="+paramMap.get("linktitle"));
				System.out.println("keywords=="+paramMap.get("keywords"));
				System.out.println("summary=="+paramMap.get("summary"));
				System.out.println("loginname=="+paramMap.get("loginname"));
				System.out.println("content=="+paramMap.get("content"));
				
				String resStr = HttpUtil.sendRequest(synchroUrl, "POST", paramMap, "UTF-8");
				
				System.out.println("publicsh---synchroExportCenterids------resStr====="+resStr);
				//{"params":{"iid":342418},"code":"0000","msg":"成功"}
				HashMap hasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(resStr, HashMap.class);
				if("0000".equals((String)hasMap.get("code"))){
					
					Object params = (Object)hasMap.get("params");
					HashMap paramsHasMap = (HashMap) JsonUtil.getDisableHtmlEscaping().fromJson(params.toString(), HashMap.class);
					Double iid = (Double)paramsHasMap.get("iid");
					publishid = iid.toString();
				}else{
					String msg = (String)hasMap.get("msg");
					log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生问题："+msg);
				}
			}
//			SynchroExportBean expotrBean = new SynchroExportBean();
//			expotrBean.setPublishList(exportBeanList);
//			expotrBean.setDeleteList(new ArrayList<SynchroExportContentScInfoBean>());
//			ObjectMapper mapper = new  ObjectMapper();
//			JSONObject paramJsonObj = mapper.convertValue(expotrBean, JSONObject.class);
			
			//String resStr = msgSendApi001Service.synchroExportSend(form.getCenterId(), paramJsonObj.toString());

//			System.out.println("向第三方平台同步发布内容结果=="+resStr);
//			
//			JSONObject resJsonObj = JSONObject.fromObject(resStr);
//			String recode = null, msg = null;
//			if (resJsonObj.has("recode")){
//				recode = resJsonObj.getString("recode");
//				if (resJsonObj.has("msg")){
//					msg = resJsonObj.getString("msg");
//				}
//				if(!"000000".equals(recode)){
//					log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
//					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生问题："+msg);
//				}
//			}else {
//				log.error(ERROR.CONNECT_SEND_ERROR.getLogText(msg));
//				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"向第三方平台同步发布内容发生异常");
//			}
		}
		
		Mi701 qryMi701Tmp = new Mi701();
		List<Mi701> Mi701List = new ArrayList<Mi701>();
		for (int i = 0; i < seqnos.length; i++) {
			// 查询判断只有已审批状态的才能做发布
			qryMi701Tmp = cmi701Dao.selectByPrimaryKey(Integer.parseInt(seqnos[i]));
			Mi701WithBLOBs record = new Mi701WithBLOBs();
			//信息属性：0：草稿；1：发布;2:审批中；3已审批
			record.setFreeuse3(Constants.PUBLISH_FLG_ONE);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
			Date date = new Date();
			record.setReleasetime(qryMi701Tmp.getReleasetime().substring(0, 10)+" " + dateFormatter.format(date).substring(11));
			System.out.println("publish------releasetime=="+qryMi701Tmp.getReleasetime().substring(0, 10)+" " + dateFormatter.format(date).substring(11));
			record.setFreeuse12(publishid);
			
			// 发布者
			record.setLoginid(form.getUserid());
			
			Mi701Example example = new Mi701Example();
			example.createCriteria().andSeqnoEqualTo(Integer.parseInt(seqnos[i]));
			
			int result = cmi701Dao.updateByExampleSelective(record, example);
			
			if (0 == result) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号seqno:"+form.getSeqno()));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"新闻动态MI701");
			}
			Mi701List.add(qryMi701Tmp);
		}
		
		ClearWebCacheUtils.clearPublish(Mi701List);//清除网站缓存
		return 0;
	}
	
	/**
	 * 内容审批
	 */
	public int webapi70008(CMi701 form, HttpServletRequest request) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getSeqnos())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		if (CommonUtil.isEmpty(form.getFreeuse3())) {
			log.error(ERROR.PARAMS_NULL.getLogText("信息属性"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"信息属性获取失败");
		}
		
		String[] seqnos = form.getSeqnos().toString().split(",");
		List<String> seqnoList = new ArrayList<String>();
		for (int i = 0; i < seqnos.length; i++) {
			seqnoList.add(seqnos[i]);
		}
		// TODO 向工单系统提交审核申请，需工单返回工单实例号，记入表，暂定存放在freeuse10
		
		
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Date date = new Date();

		Mi701WithBLOBs record = new Mi701WithBLOBs();
		//信息属性：0：草稿；1：发布;2:审批中；3已审批
		record.setFreeuse3(Constants.PUBLISH_FLG_TWO);
		record.setFreeuse7(dateFormatter.format(date));// 记录审批申请时间
		record.setLoginid(form.getUserid());
		
		Mi701Example example = new Mi701Example();
		example.createCriteria().andSeqnoIn(seqnoList);
		
		int result = cmi701Dao.updateByExampleSelective(record, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号seqno:"+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"序号seqno:"+form.getSeqno());
		}
		
		return result;
	}
	
	/**
	 * 接收审批结果
	 */
	public int recAuthResultDeal(String seqno, String resultFlg, String msg) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		if (CommonUtil.isEmpty(resultFlg)) {
			log.error(ERROR.PARAMS_NULL.getLogText("审批结果"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"审批结果获取失败");
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Date date = new Date();

		Mi701WithBLOBs record = new Mi701WithBLOBs();
		//信息属性：0：草稿；1：发布;2:审批中；3已审批; 4-未通过
		// TODO 记录返回实际结果，不一定都是通过
		record.setFreeuse3(Constants.PUBLISH_FLG_THREE);
		record.setFreeuse10(dateFormatter.format(date));// Freeuse10记录审批结果返回日期
		record.setFreeuse11(msg);// Freeuse11记录审批未通过原因说明
		
		Mi701Example example = new Mi701Example();
		example.createCriteria().andSeqnoEqualTo(Integer.parseInt(seqno));
		
		int result = cmi701Dao.updateByExampleSelective(record, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号seqno:"+seqno));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"序号seqno:"+seqno);
		}
		
		return result;
	}
	
	// 上传文件与内容id关联关系管理MI708 增加数据
	@SuppressWarnings("unchecked")
	public void webapi70008(String centerid, Integer docid, List<SynchroImportFileInfoBean> fileinfolist, String operUser) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		if (CommonUtil.isEmpty(docid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("文档id"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档id");
		}
		if (CommonUtil.isEmpty(fileinfolist)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上传附件信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"上传附件信息");
		}

		Mi708Example mi708Example = new Mi708Example();
		mi708Example.createCriteria().andCenteridEqualTo(centerid)
		.andDocidEqualTo(docid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi708> mi708QryList = mi708Dao.selectByExample(mi708Example);
		if(!CommonUtil.isEmpty(mi708QryList)){
			for(int i = 0; i < mi708QryList.size(); i++){
				String fileRootPath = PropertiesReader.getProperty(
						Constants.PROPERTIES_FILE_NAME, mi708QryList.get(i).getFilepathparam());
				fileRootPath = fileRootPath.replace("/", File.separator).replace("\\\\", File.separator);
				fileRootPath = fileRootPath.endsWith(File.separator) ? fileRootPath : fileRootPath+File.separator;
				String filepathname = fileRootPath + mi708QryList.get(i).getFilename();
				new File(filepathname).delete();
				mi708Dao.deleteByPrimaryKey(mi708QryList.get(i).getSeqno());
			}
		}
		for(int i = 0; i < fileinfolist.size(); i++){
			// 校验seqno的采号
			String seqno = commonUtil.genKey("MI708").toString();
			if (CommonUtil.isEmpty(seqno)) {
				log.error(ERROR.NULL_KEY.getLogText("上传文件与内容id关联关系管理MI708"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
						ERROR.NULL_KEY.getLogText("上传文件与内容id关联关系管理MI708"));
			}
			
			Mi708 record = new Mi708();
			record.setSeqno(Integer.parseInt(seqno));
			record.setCenterid(centerid);
			record.setDocid(docid);
			record.setFiletype(fileinfolist.get(i).getFiletype());
			String uploadfilename = fileinfolist.get(i).getAppfile();
			record.setUploadfilename(uploadfilename);
			String filePathParamTmp = uploadfilename.substring(uploadfilename.indexOf("filePathParam="), uploadfilename.indexOf("&fileName="));
			System.out.println("filePathParamTmp==="+filePathParamTmp);
			System.out.println("filePathParamTmp.substring(14)==="+filePathParamTmp.substring(14));
			record.setFilepathparam(filePathParamTmp.substring(13));
			String fileNameTmp = uploadfilename.substring(uploadfilename.indexOf("fileName="), uploadfilename.indexOf("&isFullUrl=true"));
			System.out.println("fileNameTmp==="+fileNameTmp);
			System.out.println("fileNameTmp.substring(8)==="+fileNameTmp.substring(8));
			record.setFilename(fileNameTmp.substring(8));
			record.setValidflag(Constants.IS_VALIDFLAG);
			record.setDatecreated(CommonUtil.getSystemDate());
			record.setLoginid(operUser);
			mi708Dao.insert(record);
		}
	}
	
	// 统计查询调用接口
		@SuppressWarnings("unchecked")
		public JSONObject get701Count(String centerid, String startDate, String endDate, String yingyong) throws Exception {
			JSONObject obj=new JSONObject();
			System.out.println("查询统计获取发布内容数据量开始===");
			try{
			Mi701Example mi701Example = new Mi701Example();
			Mi701Example.Criteria ca = mi701Example.createCriteria();
			ca.andCenteridEqualTo(centerid);
			ca.andFreeuse8Like("%"+yingyong+"%");
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andFreeuse3EqualTo(Constants.PUBLISH_FLG_ONE);
			ca.andDatecreatedBetween(startDate, endDate);
			int count = cmi701Dao.countByExample(mi701Example);
			obj.put("counts", count);
			obj.put("successCount", count);
			obj.put("failCount", 0);
			}catch(Exception e ){
				e.printStackTrace();
				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"获取统计插叙内容发布数异常");
			}
			System.out.println("查询统计获取发布内容数据量结束===");
			return obj;
		}
}
