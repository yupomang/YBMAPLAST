/**
  * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     WebApi302ServiceImpl.java
 * 创建日期：2013-10-18
 */
package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import cn.emay.sdk.client.api.Client;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.APPMessageI;
import com.yondervision.mi.common.message.MessageSendMessageUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.dao.CMi100DAO;
import com.yondervision.mi.dao.CMi401DAO;
import com.yondervision.mi.dao.CMi402DAO;
import com.yondervision.mi.dao.CMi403DAO;
import com.yondervision.mi.dao.CMi404DAO;
import com.yondervision.mi.dao.CMi421DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi002DAO;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dao.Mi029DAO;
import com.yondervision.mi.dao.Mi031DAO;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi100DAO;
import com.yondervision.mi.dao.Mi122DAO;
import com.yondervision.mi.dao.Mi124DAO;
import com.yondervision.mi.dao.Mi401DAO;
import com.yondervision.mi.dao.Mi402DAO;
import com.yondervision.mi.dao.Mi403DAO;
import com.yondervision.mi.dao.Mi404DAO;
import com.yondervision.mi.dao.Mi405DAO;
import com.yondervision.mi.dao.Mi406DAO;
import com.yondervision.mi.dao.Mi408DAO;
import com.yondervision.mi.dao.Mi409DAO;
import com.yondervision.mi.dao.Mi411DAO;
import com.yondervision.mi.dao.Mi412DAO;
import com.yondervision.mi.dao.Mi413DAO;
import com.yondervision.mi.dao.Mi414DAO;
import com.yondervision.mi.dao.Mi415DAO;
import com.yondervision.mi.dao.Mi421DAO;
import com.yondervision.mi.dto.CMi401;
import com.yondervision.mi.dto.CMi404;
import com.yondervision.mi.dto.Mi002;
import com.yondervision.mi.dto.Mi002Example;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi031Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi100;
import com.yondervision.mi.dto.Mi100Example;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi122Example;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.dto.Mi401Example;
import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.dto.Mi403;
import com.yondervision.mi.dto.Mi403Example;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.dto.Mi404Example;
import com.yondervision.mi.dto.Mi408;
import com.yondervision.mi.dto.Mi408Example;
import com.yondervision.mi.dto.Mi409;
import com.yondervision.mi.dto.Mi411;
import com.yondervision.mi.dto.Mi411Example;
import com.yondervision.mi.dto.Mi412;
import com.yondervision.mi.dto.Mi412Example;
import com.yondervision.mi.dto.Mi413;
import com.yondervision.mi.dto.Mi413Example;
import com.yondervision.mi.dto.Mi414;
import com.yondervision.mi.dto.Mi415;
import com.yondervision.mi.dto.Mi415Example;
import com.yondervision.mi.dto.Mi421;
import com.yondervision.mi.dto.Mi421Example;
import com.yondervision.mi.form.AppApi50004Form;
import com.yondervision.mi.form.AppApi90421Form;
import com.yondervision.mi.form.WebApi30201_deleteForm;
import com.yondervision.mi.form.WebApi30202_quertForm;
import com.yondervision.mi.form.WebApi30203Form;
import com.yondervision.mi.form.WebApi30204Form;
import com.yondervision.mi.form.WebApiCommonForm;
import com.yondervision.mi.result.WebApi100_queryResult;
import com.yondervision.mi.result.WebApi30202_queryResult;
import com.yondervision.mi.result.WebApi30203_queryResult;
import com.yondervision.mi.result.WebApi30204_queryResult;
import com.yondervision.mi.result.WebApi30205_queryResult;
import com.yondervision.mi.result.WebApi30206_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi040Service;
import com.yondervision.mi.service.WebApi302Service;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.WkfAccessTokenUtil;
import com.yondervision.mi.util.security.AES;

/**
 * @author LinXiaolong
 * 
 */
public class WebApi302ServiceImpl implements WebApi302Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	private Mi401DAO mi401Dao;
	private Mi421DAO mi421Dao;
	private CMi401DAO cMi401Dao;
	private CMi421DAO cMi421Dao;
	private CMi402DAO cMi402Dao;
	private CMi403DAO cMi403Dao;
	private CMi404DAO cMi404Dao;
	private Mi402DAO mi402Dao;
	private Mi403DAO mi403Dao;
	private Mi404DAO mi404Dao;
	private Mi100DAO mi100Dao;
	private CMi100DAO cMi100Dao;
	private Mi001DAO mi001Dao;
	private Mi002DAO mi002Dao;
	
	private Mi122DAO mi122Dao;
	private Mi124DAO mi124Dao;
	private WebApiUploadFile webApiUploadFile;
	@Autowired
	private CodeListApi001Service codeListApi001Service;
	@Autowired
	private WebApi040Service webApi040Service;
	
	private Mi405DAO mi405Dao;
	private Mi406DAO mi406Dao;
	private Mi408DAO mi408Dao;
	private Mi409DAO mi409Dao;
	private Mi411DAO mi411Dao;
	private Mi412DAO mi412Dao;
	private Mi413DAO mi413Dao;
	private Mi414DAO mi414Dao;
	private Mi415DAO mi415Dao;
	private Mi029DAO mi029Dao;
	private Mi031DAO mi031Dao;
	private Mi040DAO mi040Dao;
	private static final Map<String, String> centerTelMap = new HashMap<String, String>();
	
	
	public Mi415DAO getMi415Dao() {
		return mi415Dao;
	}

	public void setMi415Dao(Mi415DAO mi415Dao) {
		this.mi415Dao = mi415Dao;
	}

	public Mi414DAO getMi414Dao() {
		return mi414Dao;
	}

	public void setMi414Dao(Mi414DAO mi414Dao) {
		this.mi414Dao = mi414Dao;
	}

	public Mi040DAO getMi040Dao() {
		return mi040Dao;
	}

	public void setMi040Dao(Mi040DAO mi040Dao) {
		this.mi040Dao = mi040Dao;
	}

	public Mi421DAO getMi421Dao() {
		return mi421Dao;
	}

	public void setMi421Dao(Mi421DAO mi421Dao) {
		this.mi421Dao = mi421Dao;
	}

	public CMi401DAO getcMi401Dao() {
		return cMi401Dao;
	}

	public void setcMi401Dao(CMi401DAO cMi401Dao) {
		this.cMi401Dao = cMi401Dao;
	}

	public CMi421DAO getcMi421Dao() {
		return cMi421Dao;
	}

	public void setcMi421Dao(CMi421DAO cMi421Dao) {
		this.cMi421Dao = cMi421Dao;
	}

	public CMi403DAO getcMi403Dao() {
		return cMi403Dao;
	}

	public void setcMi403Dao(CMi403DAO cMi403Dao) {
		this.cMi403Dao = cMi403Dao;
	}

	public WebApi040Service getWebApi040Service() {
		return webApi040Service;
	}

	public void setWebApi040Service(WebApi040Service webApi040Service) {
		this.webApi040Service = webApi040Service;
	}

	public CMi404DAO getCMi404Dao() {
		return cMi404Dao;
	}

	public void setCMi404Dao(CMi404DAO mi404Dao) {
		cMi404Dao = mi404Dao;
	}

	public CMi100DAO getCMi100Dao() {
		return cMi100Dao;
	}

	public void setCMi100Dao(CMi100DAO mi100Dao) {
		cMi100Dao = mi100Dao;
	}

	public Mi002DAO getMi002Dao() {
		return mi002Dao;
	}

	public void setMi002Dao(Mi002DAO mi002Dao) {
		this.mi002Dao = mi002Dao;
	}
	
	public Mi412DAO getMi412Dao() {
		return mi412Dao;
	}

	public void setMi412Dao(Mi412DAO mi412Dao) {
		this.mi412Dao = mi412Dao;
	}

	public Mi411DAO getMi411Dao() {
		return mi411Dao;
	}

	public void setMi411Dao(Mi411DAO mi411Dao) {
		this.mi411Dao = mi411Dao;
	}

	public Mi413DAO getMi413Dao() {
		return mi413Dao;
	}

	public void setMi413Dao(Mi413DAO mi413Dao) {
		this.mi413Dao = mi413Dao;
	}

	public CMi404DAO getcMi404Dao() {
		return cMi404Dao;
	}

	public void setcMi404Dao(CMi404DAO cMi404Dao) {
		this.cMi404Dao = cMi404Dao;
	}

	public CMi100DAO getcMi100Dao() {
		return cMi100Dao;
	}

	public void setcMi100Dao(CMi100DAO cMi100Dao) {
		this.cMi100Dao = cMi100Dao;
	}
	
	public Mi408DAO getMi408Dao() {
		return mi408Dao;
	}

	public void setMi408Dao(Mi408DAO mi408Dao) {
		this.mi408Dao = mi408Dao;
	}

	public Mi409DAO getMi409Dao() {
		return mi409Dao;
	}

	public void setMi409Dao(Mi409DAO mi409Dao) {
		this.mi409Dao = mi409Dao;
	}
	
	public Mi031DAO getMi031Dao() {
		return mi031Dao;
	}

	public void setMi031Dao(Mi031DAO mi031Dao) {
		this.mi031Dao = mi031Dao;
	}

	public Mi029DAO getMi029Dao() {
		return mi029Dao;
	}

	public void setMi029Dao(Mi029DAO mi029Dao) {
		this.mi029Dao = mi029Dao;
	}

	public Mi405DAO getMi405Dao() {
		return mi405Dao;
	}

	public void setMi405Dao(Mi405DAO mi405Dao) {
		this.mi405Dao = mi405Dao;
	}

	public Mi406DAO getMi406Dao() {
		return mi406Dao;
	}

	public void setMi406Dao(Mi406DAO mi406Dao) {
		this.mi406Dao = mi406Dao;
	}
	/**
	 * 清楚中心客服电话缓存数据，在中心有客服电话变更时调用
	 */
	public static void clearCenterTelMap() {
		centerTelMap.clear();
	}

	/**
	 * @return the mi401Dao
	 */
	public Mi401DAO getMi401Dao() {
		return mi401Dao;
	}

	/**
	 * @param mi401Dao
	 *            the mi401Dao to set
	 */
	public void setMi401Dao(Mi401DAO mi401Dao) {
		this.mi401Dao = mi401Dao;
	}

	/**
	 * @return the cMi402Dao
	 */
	public CMi402DAO getcMi402Dao() {
		return cMi402Dao;
	}

	/**
	 * @param cMi402Dao
	 *            the cMi402Dao to set
	 */
	public void setcMi402Dao(CMi402DAO cMi402Dao) {
		this.cMi402Dao = cMi402Dao;
	}

	/**
	 * @return the mi403Dao
	 */
	public Mi403DAO getMi403Dao() {
		return mi403Dao;
	}

	/**
	 * @param mi403Dao
	 *            the mi403Dao to set
	 */
	public void setMi403Dao(Mi403DAO mi403Dao) {
		this.mi403Dao = mi403Dao;
	}

	/**
	 * @return the mi100Dao
	 */
	public Mi100DAO getMi100Dao() {
		return mi100Dao;
	}

	/**
	 * @param mi100Dao
	 *            the mi100Dao to set
	 */
	public void setMi100Dao(Mi100DAO mi100Dao) {
		this.mi100Dao = mi100Dao;
	}


	/**
	 * @return the mi001Dao
	 */
	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}

	/**
	 * @param mi001Dao
	 *            the mi001Dao to set
	 */
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}

	/**
	 * @return the webApiUploadFile
	 */
	public WebApiUploadFile getWebApiUploadFile() {
		return webApiUploadFile;
	}

	/**
	 * @param webApiUploadFile
	 *            the webApiUploadFile to set
	 */
	public void setWebApiUploadFile(WebApiUploadFile webApiUploadFile) {
		this.webApiUploadFile = webApiUploadFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi302Service#uploadFile(com.yondervision
	 * .mi.form.WebApiCommonForm,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	public String uploadFile(WebApiCommonForm form, MultipartFile file)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String filePath = CommonUtil.getFileFullPath("push_msg_img", form.getCenterId(), true);
		return this.getWebApiUploadFile().uploadFile(filePath, file, 1024 * 10);
	}

	
//	public String webapi30201_add(CMi401 form) throws Exception {
//		Logger log = LoggerUtil.getLogger();
//		
//		String commsgid = commonUtil.genKey("MI401", 0);
//		if (CommonUtil.isEmpty(commsgid)) {
//			log.error(ERROR.NULL_KEY.getLogText("MI401"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
//					ERROR.NULL_KEY.getValue());
//		}
//		
//		if (CommonUtil.isEmpty(form.getTsmsg())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "推送信息");
//		}
//		
//		String tel = this.getCustsvctel(form.getCenterId());
//		if(CommonUtil.isEmpty(form.getDetail())){
//			form.setDetail(" ");
//		}
//		form.setParam1(tel);
//		form.setCommsgid(commsgid);
//		form.setCenterid(form.getCenterId());
//		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
//		form.setValidflag(Constants.IS_VALIDFLAG);
//		form.setDatecreated(CommonUtil.getSystemDate());
//		form.setDatemodified(CommonUtil.getSystemDate());
//		form.setLoginid(form.getUserid());
//		this.getMi401Dao().insert(form);
//
//		return commsgid;
//	}

	/**
	 * 取中心电话号
	 * @param centerId
	 * @return
	 * @throws Exception
	 */
	private String getCustsvctel(String centerId) throws Exception {
		String tel = centerTelMap.get(centerId);
		if (CommonUtil.isEmpty(tel)) {
			tel = this.getMi001Dao().selectByPrimaryKey(centerId)
					.getCustsvctel();
			if (CommonUtil.isEmpty(tel)) {
				throw new NoRollRuntimeErrorException(WEB_ALERT.NEED_CUSTSVCTEL
						.getValue(), centerId);
			}
			centerTelMap.put(centerId, tel);
		}
		return tel;
	}

	
	public List<Mi401> webapi30201_query(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "城市中心代码");
		}
		Mi401Example mi401Example = new Mi401Example();
		mi401Example.setOrderByClause("datecreated desc");
		Mi401Example.Criteria mCriteria = mi401Example.createCriteria().andCenteridEqualTo(form.getCenterid());
		//mCriteria.andStatusEqualTo(Constants.PUSH_MSG_DEF_STATE);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mCriteria.andPusMessageTypeEqualTo(Constants.PUSH_TYPE_QT);
		
		List<Mi401> listMi401 = this.getMi401Dao().selectByExampleWithBLOBs(mi401Example);
		if (CommonUtil.isEmpty(listMi401)) {
			log.error(ERROR.NO_DATA.getLogText("MI401", mCriteria.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"公共推送信息");
		}		
		return listMi401;
	}
	
	/**
	 * 根据短信息ID查询公共短信息
	 * 
	 * @param commsgid
	 * 
	 * @return 公共短信息list
	 */
	public Mi401 webapi30201_queryById(String commsgid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
					.getValue(), "获取短信息ID失败");
		}
		Mi401 record = this.getMi401Dao().selectByPrimaryKey(commsgid);
		return record;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi302Service#webapi30201_delete(com.
	 * yondervision.mi.form.WebApi30201_deleteForm)
	 */
	public void webapi30201_delete(WebApi30201_deleteForm form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getListCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("listCommsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共短信息ID");
		}
		int count = 0;
		String[] commsgids = form.getListCommsgid().split(",");
		for(int i=0;i<commsgids.length;i++){
			Mi401Example mi401Example = new Mi401Example();
			Mi401Example.Criteria mCriteria = mi401Example.createCriteria()
					.andCommsgidEqualTo(commsgids[i]);
			mCriteria.andStatusNotEqualTo(Constants.PUSH_MSG_ARD_PUSH);
			mCriteria.andApproveEqualTo(Constants.APPROVE_NO);
			Mi401 mi401 = new Mi401();
			mi401.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi401.setDatemodified(CommonUtil.getSystemDate());
			int num = this.getMi401Dao().updateByExampleSelective(mi401,mi401Example);
			count = count + num;
		}
		
//		Mi404Example mi404Example = new Mi404Example();
//		Mi404Example.Criteria cmCriteria = mi404Example.createCriteria()
//				.andCommsgidIn(form.getListCommsgid());
//		cmCriteria.andMsstatusNotEqualTo(Constants.PUSH_MSG_ARD_PUSH);
//		Mi404 mi404 = new Mi404();
//		mi404.setValidflag(Constants.IS_NOT_VALIDFLAG);
//		mi404.setDatemodified(CommonUtil.getSystemDate());
//		mi404Dao.updateByExampleSelective(mi404, mi404Example);
		
		/*
		 * 返回结果验证
		 */
		if (count == 0) {			
			throw new NoRollRuntimeErrorException(WEB_ALERT.UPD_CHECK
					.getValue(), "公共推送信息");
		}
	}
	
	public int insertGroupWaitMessage(Object obj) throws Exception{
		if(obj instanceof Mi401){
			Mi401 mi401 = (Mi401)obj;
			String[] pids = mi401.getPid().split(",");
			for(int i=0;i<pids.length;i++){
				Mi402 mi402 = new Mi402();
				String msgid = commonUtil.genKey("MI402", 0);
				
				mi402.setMsgid(msgid);
				mi402.setCommsgid(mi401.getCommsgid());
				mi402.setPersonalid("ALL_USER");
				mi402.setCenterid(mi401.getCenterid());				
				mi402.setTitle(mi401.getTitle());
				mi402.setDetail(mi401.getDetail());
				mi402.setPusMessageType(mi401.getPusMessageType());
				mi402.setParam1(mi401.getParam1());
				mi402.setParam2(mi401.getParam2());
				mi402.setStatus(mi401.getStatus());
				mi402.setPid(pids[i]);
				mi402.setValidflag(Constants.IS_VALIDFLAG);
				mi402.setDatecreated(CommonUtil.getSystemDate());
				mi402.setDatemodified(CommonUtil.getSystemDate());
				mi402.setTsmsg(mi401.getTsmsg());
				mi402.setTsmsgtype(mi401.getTsmsgtype());
				mi402.setMsgsource(mi401.getMsgsource());
				mi402.setSumcount(mi401.getSumcount());
				mi402.setSuccessnum(mi401.getSuccessnum());
				mi402.setLoginid(mi401.getLoginid());
				//微博头条文章0-普通文章，1-头条文章
				mi402.setIsheadline(mi401.getIsheadline());
				//头条文章导语
				mi402.setHeadleads(mi401.getHeadleads());
				mi402Dao.insert(mi402);
			}
		}
		return 1;
	}
	
	public void insertMi404(Object obj) throws Exception{
		Mi401 mi401 = (Mi401)obj;
		Mi404Example mi404Example = new Mi404Example();		
		mi404Example.createCriteria().andCenteridEqualTo(mi401.getCenterid()).andCommsgidEqualTo(mi401.getCommsgid());
		Mi404 mi404 = new Mi404();
		mi404.setCommsgid(mi401.getCommsgid());
		mi404.setTitle(mi401.getTitle());
		mi404.setDetail(mi401.getDetail());
		mi404.setPid(mi401.getPid());
		mi404.setTheme(mi401.getTheme());
		mi404.setTsmsg(mi401.getTsmsg());
		mi404.setTiming(mi401.getTiming());
		mi404.setDsdate(mi401.getDsdate());
		mi404.setMsgsource(mi401.getMsgsource());
		mi404.setTsmsgtype(mi401.getTsmsgtype());
		mi404.setLoginid(mi401.getLoginid());
		mi404.setMsnum(mi404Dao.countByExample(mi404Example)+1);
		mi404.setParam1(mi401.getParam1());
		mi404.setParam2(mi401.getParam2());
		mi404.setParam3(mi401.getParam3());
		mi404.setParam4(mi401.getParam4());
		mi404.setMsmscommsgid(commonUtil.genKey("MI404", 0));
		mi404.setCenterid(mi401.getCenterid());
		mi404.setPusMessageType(Constants.PUSH_TYPE_QT);
		mi404.setStatus(Constants.PUSH_MSG_DEF_STATE);
		mi404.setValidflag(Constants.IS_VALIDFLAG);
		mi404.setDatecreated(CommonUtil.getSystemDate());
		mi404.setDatemodified(CommonUtil.getSystemDate());
		mi404.setSendseqno(mi401.getSendseqno());
		mi404.setFreeuse3(mi401.getFreeuse3());
		mi404.setFreeuse4(mi401.getFreeuse4());
		//微博头条文章0-普通文章，1-头条文章
		mi404.setIsheadline(mi401.getIsheadline());
		//头条文章导语
		mi404.setHeadleads(mi401.getHeadleads());
		mi404Dao.insert(mi404);
	}
	
	public void updateMi404(Object obj) throws Exception{
		CMi401 cmi401 = (CMi401)obj;
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(cmi401.getCenterid());
		mCriteria.andCommsgidEqualTo(cmi401.getCommsgid());
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi404> list = mi404Dao.selectByExampleWithBLOBs(mi404Example);
		for(int i=0; i<list.size(); i++){
			Mi404 mi404 = list.get(i);
			mi404.setTitle(cmi401.getTitle());
			mi404.setTheme(cmi401.getTheme());
			mi404.setTsmsg(cmi401.getTsmsg());
			mi404.setDetail(cmi401.getDetail());
			mi404.setParam1(cmi401.getParam1());
			mi404.setParam2(cmi401.getParam2());
			mi404.setPid(cmi401.getPid());
			mi404.setFreeuse3(cmi401.getFreeuse3());
			mi404.setFreeuse4(cmi401.getFreeuse4());
			mi404Dao.updateByPrimaryKeyWithBLOBs(mi404);
		}
	}
	
	public Mi401 insertCustomizationWaitMessage(CMi401 cmi401) throws Exception{
//		Mi029 mi029 = null;
		Mi401 mi401 = null;
		
//		Mi029Example mi029Example = new Mi029Example();
//		Mi029Example.Criteria mi029Criteria = mi029Example.createCriteria();
//		mi029Criteria.andCenteridEqualTo(cmi401.getCenterid());
//		mi029Criteria.andTelEqualTo(cmi401.getTel());
//		mi029Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		List<Mi029> list029 = mi029Dao.selectByExample(mi029Example);
//		if(CommonUtil.isEmpty(list029)){
//			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "综合服务平台客户信息不存在");
//		}else{
//			mi029 = list029.get(0);
//		}	
		
		Mi401Example mi401Example = new Mi401Example();
		Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
		mi401Criteria.andSenddateEqualTo(cmi401.getSendseqno().trim());
		mi401Criteria.andCenteridEqualTo(cmi401.getCenterid());
		mi401Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi401> listMi401 = this.getMi401Dao().selectByExampleWithBLOBs(mi401Example);
		if(CommonUtil.isEmpty(listMi401)){
			mi401 = new Mi401();
			String commsgid = commonUtil.genKey("MI401", 0);
			mi401.setCommsgid(commsgid);
			mi401.setCenterid(cmi401.getCenterid());
			mi401.setTitle(cmi401.getTitle());
			mi401.setDetail(cmi401.getDetail());
			mi401.setPid(cmi401.getPid());
			mi401.setPusMessageType(Constants.PUSH_TYPE_BP);
			mi401.setTheme(cmi401.getTheme());
			mi401.setTsmsg(cmi401.getTsmsg());
			mi401.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);
			mi401.setTiming("0");
			mi401.setDsdate(cmi401.getDsdate());
			mi401.setMsgsource(cmi401.getMsgsource());
			mi401.setSendseqno(cmi401.getSendseqno());
			mi401.setLoginid(cmi401.getLoginid());
			mi401.setSumcount(1);
			mi401.setSuccessnum(0);
			mi401.setParam1(cmi401.getTel());
			mi401.setStatus(Constants.PUSH_MSG_DEF_STATE);
			mi401.setValidflag(Constants.IS_VALIDFLAG);
			mi401.setDatecreated(CommonUtil.getSystemDate());
			mi401.setDatemodified(CommonUtil.getSystemDate());
			mi401Dao.insert(mi401);
		}else{
			mi401 = listMi401.get(0);
			mi401.setSumcount(mi401.getSumcount()+1);
			mi401Dao.updateByPrimaryKeyWithoutBLOBs(mi401);
		}
		System.out.println("#######登录MI401完成");
		Mi402 mi402 = new Mi402();
		String msgid = commonUtil.genKey("MI402", 0);
		mi402.setMsgid(msgid);
		mi402.setCommsgid(mi401.getCommsgid());
		mi402.setPersonalid("");
		mi402.setUsername("");
		mi402.setCenterid(mi401.getCenterid());				
		mi402.setTitle(mi401.getTitle());
		mi402.setPid(mi401.getPid());		
		mi402.setDetail(mi401.getDetail());
		mi402.setPusMessageType(mi401.getPusMessageType());
		mi402.setTheme(mi401.getTheme());
		mi402.setTsmsg(mi401.getTsmsg());
		mi402.setTsmsgtype(mi401.getTsmsgtype());
		mi402.setTiming("0");
		mi402.setDsdate(mi401.getDsdate());
		mi402.setMsgsource(mi401.getMsgsource());
		mi402.setSendseqno(mi401.getSendseqno());
		mi402.setLoginid(mi401.getLoginid());
		mi402.setParam1(mi401.getParam1());
		mi402.setParam2(mi401.getParam2());
		mi402.setStatus(mi401.getStatus());
		mi402.setValidflag(Constants.IS_VALIDFLAG);
		mi402.setDatecreated(CommonUtil.getSystemDate());
		mi402.setDatemodified(CommonUtil.getSystemDate());
		mi402.setSumcount(1);
		mi402.setSuccessnum(0);
		System.out.println("#######登录MI402开始");
		try{
			System.out.println("MI402入表参数："+CommonUtil
					.getStringParams(mi402));
			mi402Dao.insert(mi402);
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("#######登录MI402结束");
		return mi401;
	}
	
	public int sendGroupWaitMessage(Object obj) throws Exception{
		if(obj instanceof Mi401){
			Mi401 mi401 = (Mi401)obj;
			Mi402Example mi402Example = new Mi402Example();
			Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
			mi402Criteria.andCommsgidEqualTo(mi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi402> list = mi402Dao.selectByExampleWithBLOBs(mi402Example);
			if (!CommonUtil.isEmpty(list)){
				Mi403Example mi403Example = new Mi403Example();
				Mi403Example.Criteria mi403Criteria = mi403Example.createCriteria();
				mi403Criteria.andCommsgidEqualTo(mi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi403Dao.deleteByExample(mi403Example);	
				
				for(int i=0;i<list.size();i++){
					Mi402 mi402 = list.get(i);
					boolean rep = false;
					Mi040 mi040 = webApi040Service.webapi04008(mi402.getPid());					
					if(!CommonUtil.isEmpty(mi040)){
						Mi403 mi403 = new Mi403();
						String pushid = commonUtil.genKey("MI403", 0);
						mi403.setPushid(pushid);
						mi403.setMsgid(mi402.getMsgid());
						mi403.setCommsgid(mi402.getCommsgid());
						mi403.setUserid("ALL_USER");
						mi403.setPersonalid("ALL_USER");
						mi403.setCenterid(mi402.getCenterid());				
						mi403.setTitle(mi402.getTitle());
						mi403.setDetail(mi402.getDetail());
						mi403.setPid(mi402.getPid());
						mi403.setPusMessageType(mi402.getPusMessageType());
						mi403.setTheme(mi402.getTheme());
						mi403.setTsmsg(mi402.getTsmsg());
						mi403.setTsmsgtype(mi402.getTsmsgtype());
						mi403.setTiming(mi402.getTiming());
						mi403.setDsdate(mi402.getDsdate());
						mi403.setMsgsource(mi402.getMsgsource());
						mi403.setSendseqno(mi402.getSendseqno());
						mi403.setLoginid(mi402.getLoginid());
						mi403.setParam1(mi402.getParam1());
						mi403.setParam2(mi402.getParam2());
						mi403.setParam3(mi402.getParam3());
						mi403.setParam4(mi402.getParam4());
						mi403.setParam5(mi402.getParam5());
						mi403.setStatus(Constants.PUSH_MSG_DEF_STATE);
						mi403.setValidflag(Constants.IS_VALIDFLAG);
						mi403.setDatecreated(CommonUtil.getSystemDate());
						mi403.setDatemodified(CommonUtil.getSystemDate());
						//微博头条文章0-普通文章，1-头条文章
						mi403.setIsheadline(mi402.getIsheadline());
						//头条文章导语
						mi403.setHeadleads(mi402.getHeadleads());
						mi403Dao.insert(mi403);
						
						//入100表
						Mi100 mi100 = new Mi100();
						mi100.setSeqno(Integer.parseInt(commonUtil.genKey("MI100", 0)));
						mi100.setCenterid(mi402.getCenterid());
						mi100.setCommsgid(mi402.getCommsgid());
						mi100.setPid(mi040.getPid());
						mi100.setPidname(mi040.getAppname());
						mi100.setTheme(mi100.getTheme());
						mi100.setTsmsgtype(mi402.getTsmsgtype());
						mi100.setPusMessageType(mi402.getPusMessageType());
						mi100.setMsgsource(mi402.getMsgsource());
						mi100.setTransdate(commonUtil.getDate());
						mi100.setValidflag(Constants.IS_VALIDFLAG);
						mi100.setDatecreated(CommonUtil.getSystemDate());
						mi100.setDatemodified(CommonUtil.getSystemDate());
						mi100Dao.insert(mi100);
					}
				}
			}
		}
		return 0;
	}
	
	public void updateGroupSendStatus(CMi401 mi401) throws Exception{
		Mi402Example mi402Example = new Mi402Example();
		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
		mi402Criteria.andCommsgidEqualTo(mi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi402> list = this.cMi402Dao.selectByExampleWithoutBLOBs(mi402Example);
		int sum = 0;
		for(int i=0;i<list.size();i++){
			sum = sum + list.get(i).getSuccessnum();
		}
		Mi401 upmi401 = new Mi401();
		upmi401.setCommsgid(mi401.getCommsgid());
		upmi401.setSuccessnum(sum);
		upmi401.setStatus(mi401.getStatus());
		upmi401.setDatecreated(CommonUtil.getSystemDate());
		upmi401.setDatemodified(CommonUtil.getSystemDate());
		mi401Dao.updateByPrimaryKeySelective(upmi401);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yondervision.mi.service.WebApi302Service#webapi30201_edit(com.
	 * yondervision.mi.dto.CMi401)
	 */
	public void webapi30201_edit(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共短信息ID");
		}
		Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
		if(Constants.APPROVE_YES.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "已审批", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"修改","审批","修改");
		}
		if(Constants.APPROVE_ING.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "审批中", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"修改","审批中","修改");
		}
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi401Dao().updateByPrimaryKeySelective(form);
		
		Mi402Example mi402Example = new Mi402Example();
		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
		mi402Criteria.andCommsgidEqualTo(mi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi402> list = mi402Dao.selectByExampleWithBLOBs(mi402Example);
		String existPid = "";
		for(int i=0;i<list.size();i++){
			Mi402 mi402 = list.get(i);
			existPid = existPid + mi402.getPid() + ",";
			if(form.getPid().indexOf(mi402.getPid())==-1){
				mi402Dao.deleteByPrimaryKey(mi402.getMsgid());
			}else{
				mi402.setTitle(form.getTitle());
				mi402.setDetail(form.getDetail());
				mi402.setTiming(form.getTiming());
				mi402.setDsdate(form.getDsdate());
				mi402.setTsmsg(form.getTsmsg());
				mi402.setParam2(form.getParam2());
				mi402Dao.updateByPrimaryKeySelective(mi402);
			}
		}
		String[] newPid = form.getPid().split(",");
		for(int i=0; i<newPid.length; i++){
			if(existPid.indexOf(newPid[i])==-1){
				Mi402 mi402 = new Mi402();
				String msgid = commonUtil.genKey("MI402", 0);
				mi402.setMsgid(msgid);
				mi402.setCommsgid(mi401.getCommsgid());
				mi402.setPersonalid("ALL_USER");
				mi402.setCenterid(mi401.getCenterid());				
				mi402.setTitle(mi401.getTitle());
				mi402.setDetail(mi401.getDetail());
				mi402.setPusMessageType(mi401.getPusMessageType());
				mi402.setParam1(mi401.getParam1());
				mi402.setParam2(mi401.getParam2());
				mi402.setStatus(mi401.getStatus());
				mi402.setPid(newPid[i]);
				mi402.setValidflag(Constants.IS_VALIDFLAG);
				mi402.setDatecreated(CommonUtil.getSystemDate());
				mi402.setDatemodified(CommonUtil.getSystemDate());
				mi402.setTsmsg(mi401.getTsmsg());
				mi402.setTsmsgtype(mi401.getTsmsgtype());
				mi402.setMsgsource(mi401.getMsgsource());
				mi402.setSumcount(mi401.getSumcount());
				mi402.setSuccessnum(mi401.getSuccessnum());
				mi402.setLoginid(mi401.getLoginid());
				mi402Dao.insert(mi402);
			}
		}
		updateMi404(form);
		
	}

	/**
	 * 审批公共短信息
	 * 
	 * @param form
	 * @throws Exception
	 */
	public void webapi30201_auth(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
		if(Constants.APPROVE_ING.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "审批中", "再次审批"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"待审批","审批中","审批");
		}
		if(Constants.APPROVE_YES.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "已审批", "再次审批"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"待审批","已审批","审批");
		}
		
		if(Constants.APPROVE_NO.equals(mi401.getApprove())||Constants.APPROVE_FAIL.equals(mi401.getApprove())){
			mi401.setApprove(Constants.APPROVE_YES);
			mi401.setApproverdate(CommonUtil.getSystemDate());
			mi401.setFreeuse2(form.getUserid());
			mi401.setFreeuse3(form.getUsername());
			int count = this.getMi401Dao().updateByPrimaryKeySelective(mi401);
			if (count == 0) {
				log.error(ERROR.NO_DATA.getLogText("MI401","公共消息ID："+form.getCommsgid()));
				throw new NoRollRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "公共推送信息");
			}else{
				if("1".equals(mi401.getTiming())){//ds
					Mi408 mi408 = new Mi408();
					mi408.setTimeid(commonUtil.genKey("MI408", 0));
					mi408.setCommsgid(mi401.getCommsgid());
					mi408.setPusMessageType(mi401.getPusMessageType());
					mi408.setTsmesstype(mi401.getTsmsgtype());
					mi408.setCenterid(mi401.getCenterid());
					mi408.setDsdate(mi401.getDsdate());
					mi408.setMsgsource(mi401.getMsgsource());
					mi408.setValidflag(mi401.getValidflag());
					mi408.setDatecreated(CommonUtil.getSystemDate());
					mi408.setDatecreated(CommonUtil.getSystemDate());
					mi408Dao.insert(mi408);
				}
			}
		}
	}
	
	
	
	public List<Mi401> webapi30202_query(WebApiCommonForm form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "城市中心代码");
		}

		List values = new ArrayList();
		Mi401Example mi401Example = new Mi401Example();
		mi401Example.setOrderByClause("datecreated desc");
		Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
		mi401Criteria.andCenteridEqualTo(form.getCenterId());
		mi401Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi401Criteria.andPusMessageTypeEqualTo(Constants.PUSH_TYPE_BP);
		mi401Criteria.andStatusEqualTo(Constants.PUSH_MSG_DEF_STATE);
		@SuppressWarnings("unchecked")
		List<Mi401> result = this.getMi401Dao().selectByExampleWithoutBLOBs(mi401Example);
		if (CommonUtil.isEmpty(result)) {
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"定制消息推送信息批次");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.WebApi302Service#webapi3020201_query(com.
	 * yondervision.mi.form.WebApi30202_quertForm)
	 */
	public WebApi30202_queryResult webapi3020201_query(
			WebApi30202_quertForm form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("comsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "消息编号");
		}

		/*
		 * 查询公共短信息数据
		 */
		Mi402Example mi402Example = new Mi402Example();
		mi402Example.setOrderByClause("datecreated desc");
		Mi402Example.Criteria mCriteria = mi402Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		mCriteria.andCommsgidEqualTo(form.getCommsgid());
		mCriteria.andStatusNotEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		WebApi30202_queryResult result = this.getcMi402Dao()
				.selectByExamplePagination(mi402Example, form.getPage(),
						form.getRows());
		
		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result.getRows())) {
			log.error(ERROR.NO_DATA.getLogText("MI402", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"报盘推送信息");
		}

		return result;
	}

	
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public CMi401 webapi30202_import(CMi401 form, MultipartFile importFile)
			throws Exception {
		String seqno = CommonUtil.getSystemDateNumOnly();
		form.setSendseqno(seqno);
		int transCount = 0;
		Logger log = LoggerUtil.getLogger();
		boolean importFileIsErr = false;
		form.setPusMessageType(Constants.PUSH_TYPE_BP);
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getUserid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "操作员代码");
		}
		if (CommonUtil.isEmpty(importFile)
				|| CommonUtil.isEmpty(importFile.getOriginalFilename())) {
			log.error(ERROR.PARAMS_NULL.getLogText("importFile"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "批量文件");
		}
		if (!importFile.getOriginalFilename().endsWith(".xls")) {
			String fileName = importFile.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_FILE_TYPE.getValue(), fileType, "xls");
		}
		/*
		 * 导入数据
		 */
		String commsgid = commonUtil.genKey("MI401", 0);
		form.setCommsgid(commsgid);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI401"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		try {
			String importFileName = form.getCenterid()+File.separator+commsgid+"_推送消息检查结果" + ".xls";
			boolean isFullUrl = true;
			String importFilePatch = CommonUtil.getFileFullPath(
					"push_msg_importfile", importFileName, isFullUrl);
			File importFileOnServer = new File(importFilePatch);
			if (!importFileOnServer.getParentFile().isDirectory()) {
				if(importFileOnServer.getParentFile().mkdirs())
					System.out.println("创建文件或文件夹:"+importFileOnServer.getParentFile());
			}
			// 写入文件
			FileOutputStream fs = new FileOutputStream(importFileOnServer);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			InputStream stream = importFile.getInputStream();
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
			// 上传的EXCEL
			Workbook importExcel = Workbook.getWorkbook(importFileOnServer);
			Sheet importData = importExcel.getSheet(0);

			WritableWorkbook wbook = Workbook.createWorkbook(importFileOnServer);
			WritableSheet wsheet = wbook.createSheet(importData.getName(), 0);
			wsheet.getSettings().setProtected(true);
			wsheet.setColumnView(0, 19);// 设置第1列(错误提示)宽度
			wsheet.setColumnView(1, 18);// 设置第2列(错误提示)宽度
			wsheet.setColumnView(2, 18);// 设置第3列(错误提示)宽度
			wsheet.setColumnView(3, 350);// 设置第4列(错误提示)宽度
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.RED);
			WritableCellFormat errFormat = new WritableCellFormat(wfont);
			errFormat.setWrap(true);// 自动换行
						
			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat errFormat1 = new WritableCellFormat(wfont1);
			errFormat1.setAlignment(Alignment.CENTRE);
			Cell[] row0 = importData.getRow(0);
			for(int i=0;i<row0.length;i++){
				Label content1 = new Label(i, 0, row0[i].getContents(), errFormat1);
		 		wsheet.addCell(content1);
			}
			Label content1 = new Label(row0.length, 0, "检查结果",errFormat1);
	 		wsheet.addCell(content1);
			
			/*
			 * 插入明细表
			 */
			Long[] msgids = new Long[importData.getRows()];
			for(int ii=0;ii<msgids.length;ii++){
				msgids[ii] = commonUtil.genKey("MI402");
				if (CommonUtil.isEmpty(msgids[ii])) {
					log.error(ERROR.NULL_KEY.getLogText("MI402"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
				}
			}
			for (int i = 1; i < importData.getRows(); i++) {
				Cell[] rowData = importData.getRow(i);
				for (int j = 0; j < rowData.length; j++) {
					if (CommonUtil.isEmpty(rowData[j])
							|| CommonUtil.isEmpty(rowData[j].getContents())) {
						continue;
					}
					Label content = new Label(j, i, rowData[j].getContents(),rowData[j].getCellFormat());
					wsheet.addCell(content);
				}

				if (i == 0) { // 第一行为表头
					continue;
				}
				if (rowData.length > 0) {
					try {
						
						//入402表，同时检查数据
						transCount = transCount+this.insertMi402(rowData ,form);
					} catch (NoRollRuntimeErrorException e) {
						importFileIsErr = true;
						Label content = new Label(3, i, e.getMessage(),
								errFormat);
						wsheet.addCell(content);
					}
				}
			}

			/*
			 * 保存导入的批量文件
			 */
			wbook.write();
			wbook.close();

			/*
			 * 如果批量文件未通过校验则抛出异常，回滚实物
			 */
			if (importFileIsErr) {
				String msg = CommonUtil.getDownloadFileUrl("push_msg_importfile",
						importFileName, isFullUrl).replaceAll("\\\\","\\\\\\\\");
//						"<a href='"
//						+ CommonUtil.getDownloadFileUrl("push_msg_importfile",
//								importFileName, isFullUrl).replaceAll("\\\\",
//								"\\\\\\\\") + "'>点击下载错误信息</a>";
				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA
						.getValue(), msg);
			}


			/*
			 * 插入批量业务登记表
			 */
//			Mi100 mi100 = new Mi100();
//			mi100.setSeqno(seqno);
//			mi100.setCenterid(form.getCenterId());
//			mi100.setTransSeqid(seqid);
//			mi100.setTransCount(transCount);
//			mi100.setTransKeyword1(codeListApi001Service.getCodeVal(form
//					.getCenterId(), "psh_message_type."
//					+ form.getPusMessageType()));
//			mi100.setTransKeyword2(Constants.PUSH_MSG_DEF_STATE);
//			mi100.setChanneltype(Constants.CHANNELTYPE_WEB);
//			mi100.setTransdate(CommonUtil.getSystemDate());
//			mi100.setTranstype(Constants.MI100_TRANSTYPE_PUSHMSG);
//			mi100.setTransname(codeListApi001Service.getCodeVal(form
//					.getCenterId(), "psh_message_type."
//					+ Constants.MI100_TRANSTYPE_PUSHMSG));
//			mi100.setVersionno(PropertiesReader.getProperty(
//					Constants.PROPERTIES_FILE_NAME, "version_no"));
//			mi100.setDevid(form.getLonginip());
//			mi100.setValidflag(Constants.IS_VALIDFLAG);
//			mi100.setLoginid(form.getUserid());
//						
//			if(n>0){
//				mi100.setFreeuse1("用户拒绝接收推送主题"+n+"条");
//			}else{
//				mi100.setFreeuse1("");
//			}
//			this.getMi100Dao().insert(mi100);
			
			Mi401 mi401s = this.getMi401Dao().selectByPrimaryKey(form.getCommsgid());
			
			if(CommonUtil.isEmpty(mi401s)){
				String tel = this.getCustsvctel(form.getCenterid());
				Mi401 mi401 = new Mi401();
				mi401.setCommsgid(form.getCommsgid());
				mi401.setParam1(tel);
				mi401.setCenterid(form.getCenterid());
				mi401.setPusMessageType(Constants.PUSH_TYPE_BP);		
				mi401.setTitle("定制消息");
				mi401.setDetail("定制消息");
				mi401.setTsmsg("定制消息");
				mi401.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);
				mi401.setTheme(form.getTheme());
				mi401.setLoginid(form.getUserid());
				mi401.setApprove(Constants.APPROVE_NO);
				mi401.setDsdate(form.getDsdate());
				mi401.setTiming(form.getTiming());
				mi401.setMsgsource(form.getMsgsource());
				mi401.setStatus(Constants.PUSH_MSG_DEF_STATE);
				mi401.setLoginid(form.getUserid());
				mi401.setValidflag(Constants.IS_VALIDFLAG);
				mi401.setDatecreated(CommonUtil.getSystemDate());
				mi401.setDatemodified(CommonUtil.getSystemDate());
				mi401.setSumcount(transCount);
				mi401.setSendseqno(seqno);
				System.out.println("报盘信息插入主信息表#########");
				log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
						.getStringParams(mi401)));
				this.getMi401Dao().insert(mi401);
			}
		} catch (NoRollRuntimeErrorException ne) {
			throw (TransRuntimeErrorException) ne;
		} catch (TransRuntimeErrorException te) {
			throw te;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
		return form;
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi30202_dlete(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getUserid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "操作员代码");
		}
		try {
			String[] values = form.getCommsgid().split(",");
			for(int i=0;i<values.length;i++){
				Mi401 mi401 = new Mi401();
				mi401.setCommsgid(form.getCommsgid());
				mi401.setValidflag(Constants.IS_NOT_VALIDFLAG);
				this.mi401Dao.updateByPrimaryKeySelective(mi401);
				
				Mi402Example mi402Example = new Mi402Example();
				Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
				mi402Criteria.andCommsgidEqualTo(form.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
				
				Mi402 mi402 = new Mi402();
				mi402.setValidflag(Constants.IS_NOT_VALIDFLAG);
				mi402.setDatemodified(CommonUtil.getSystemDate());
				this.getcMi402Dao().updateByExampleSelective(mi402 ,mi402Example);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	/**
	 * 将excel中的一行数据导入MI402表中
	 * 
	 * @param seqid
	 *            批次号
	 * @param rowData
	 *            excel中一行的数据
	 * @param centerId
	 *            中心代码
	 * @param pusMessageType
	 *            消息类型
	 * @throws Exception
	 */
	private int insertMi402(Cell[] rowData, CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String centerId = form.getCenterid();
		String pusMessageType = Constants.PUSH_TYPE_BP;		
		
		String keymessage = "";	
		
		StringBuffer strbuffErr = new StringBuffer();
		int countErr = 0;
		/*
		 * 参数校验
		 */
		// 文档列数校验，如果此行列数小于3则不进行其它校验，如果此行列数大于3则继续进行其它校验
		if (rowData == null || rowData.length < 3) {
			countErr++;
			String errMsg = countErr
					+ ". "
					+ ERROR.IMPORT_ROW_LENGTH_INCORRECT.getLogText(String
							.valueOf(rowData == null ? 0 : rowData.length),
							String.valueOf(3));
			log.info(errMsg);
			throw new NoRollRuntimeErrorException(
					WEB_ALERT.SELF_ERR.getValue(), errMsg);
		}
		if (rowData.length > 3) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_LENGTH_INCORRECT.getLogText(
					String.valueOf(rowData.length), String.valueOf(3)));
		}
		// 非空校验
		if (CommonUtil.isEmpty(rowData[0].getContents())
				&& CommonUtil.isEmpty(rowData[1].getContents())
				&& CommonUtil.isEmpty(rowData[2].getContents())) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_NULL.getLogText());
		}
		if (CommonUtil.isEmpty(rowData[0].getContents())) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_NULL.getLogText(keymessage));
		}
		if (CommonUtil.isEmpty(rowData[1].getContents())) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_NULL.getLogText("消息标题"));
		}
		if (CommonUtil.isEmpty(rowData[2].getContents())) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_NULL.getLogText("消息内容"));
		}
		// 参数长度校验
		int accnumMaxLength = 30;
		int titleMaxLength = 100;
		int detailMaxLength = 5000;
		if (rowData[0].getContents().trim().getBytes(
				Constants.DATABASE_ENCODING).length > accnumMaxLength) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText(
					keymessage, String.valueOf(rowData[0].getContents().trim()
							.getBytes(Constants.DATABASE_ENCODING).length),
					String.valueOf(accnumMaxLength)));
		}
		if (rowData[1].getContents().trim().getBytes(
				Constants.DATABASE_ENCODING).length > titleMaxLength) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText("消息标题",
					String.valueOf(rowData[1].getContents().trim().getBytes(
							Constants.DATABASE_ENCODING).length), String
							.valueOf(titleMaxLength)));
		}
		if (rowData[2].getContents().trim().getBytes(
				Constants.DATABASE_ENCODING).length > detailMaxLength) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_TOO_LONG.getLogText("消息内容",
					String.valueOf(rowData[0].getContents().trim().getBytes(
							Constants.DATABASE_ENCODING).length), String
							.valueOf(detailMaxLength)));
		}
		// 数据换行校验
		if (rowData[0].getContents().trim().indexOf("\n") > 0) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_CONT_CHANGE_LINE
					.getLogText(keymessage));
		}
		if (rowData[1].getContents().trim().indexOf("\n") > 0) {
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_CONT_CHANGE_LINE
					.getLogText("消息标题"));
		}

		
		String certinum = null;
		
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.setOrderByClause("datemodified desc");
		Mi029Example.Criteria mi029Criteria = mi029Example.createCriteria();
		mi029Criteria.andCertinumEqualTo(rowData[0].getContents().trim()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		System.out.println("报盘导入数据关键字："+rowData[0].getContents().trim());
		System.out.println("报盘导入数据关键字1："+rowData[1].getContents().trim());
		System.out.println("报盘导入数据关键字2："+rowData[2].getContents().trim());
		
		@SuppressWarnings("unchecked")
		List<Mi029> listMi029 = mi029Dao.selectByExample(mi029Example);
		if (CommonUtil.isEmpty(listMi029)) {
			log.debug(ERROR.NO_DATA.getLogText("MI029", mi029Criteria
					.getCriteriaWithSingleValue().toString()));
			countErr++;
			if (countErr > 1) {
				strbuffErr.append("\012");
			}
			strbuffErr.append(countErr);
			strbuffErr.append(". ");
			strbuffErr.append(ERROR.IMPORT_ROW_COL_HAS_NO_USER.getLogText(keymessage));
		} else {
			certinum = rowData[0].getContents().trim();
		}	

		/*
		 * 判断此行数据是否通过校验，如未通过
		 */
		if (countErr > 0) {
			log.info(LOG.SELF_LOG.getLogText(strbuffErr.toString()));
			throw new NoRollRuntimeErrorException(
					WEB_ALERT.SELF_ERR.getValue(), strbuffErr.toString());
		}

		// 取得中心热线电话

//		Mi122Example mi122Example = new Mi122Example();
//		mi122Example.setOrderByClause("num asc");
//		Mi122Example.Criteria mi122Criteria = mi122Example.createCriteria();
//		mi122Criteria.andCenteridEqualTo(form.getCenterId());
//		mi122Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		mi122Criteria.andMessageTopicTypeEqualTo(form.getTheme());
//		System.out.println("报盘文件上送主题:"+form.getTheme());
//		List<Mi122> list122 = mi122Dao.selectByExample(mi122Example);		
//		if(list122.get(0).getMustsend().equals("0")){
//			Mi124Example mi124Example = new Mi124Example();
//			Mi124Example.Criteria mi124Criteria = mi124Example.createCriteria();
//			mi124Criteria.andCenteridEqualTo(form.getCenterId());
//			mi124Criteria.andUseridEqualTo(certinum);
//			List<Mi124> list124 = mi124Dao.selectByExample(mi124Example);
//			if(list124.size()>0){
//				if(list124.get(0).getFirstzt().equals("1")){
//					if(list124.get(0).getTopictype().indexOf(form.getTheme())<0){
//						return 0;
//					}								
//				}							
//			}
//		}
//		
//		Mi031Example mi031Example = new Mi031Example();
//		Mi031Example.Criteria mi031Criteria = mi031Example.createCriteria();
//		mi031Criteria.andPersonalidEqualTo(listMi029.get(0).getPersonalid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		List<Mi031> list031 = mi031Dao.selectByExample(mi031Example);
		String msgid = commonUtil.genKey("MI402", 0);
		Mi402 mi402 = new Mi402();
		mi402.setMsgid(msgid);
		mi402.setCommsgid(form.getCommsgid());
		mi402.setPersonalid(listMi029.get(0).getPersonalid());
		mi402.setUsername(listMi029.get(0).getUsername());
		mi402.setCenterid(centerId);
		mi402.setMsgsource(form.getMsgsource());
		mi402.setTitle(rowData[1].getContents().trim());
		mi402.setDetail(rowData[2].getContents().trim());
		mi402.setPusMessageType(pusMessageType);
		mi402.setParam1(listMi029.get(0).getTel());
		mi402.setStatus(Constants.PUSH_MSG_DEF_STATE);
		mi402.setValidflag(Constants.IS_VALIDFLAG);
		mi402.setDatecreated(CommonUtil.getSystemDate());
		mi402.setDatemodified(CommonUtil.getSystemDate());
		mi402.setTsmsg(rowData[2].getContents().trim());
		mi402.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);
		mi402.setTheme(form.getTheme());
		mi402.setLoginid(form.getUserid());
		mi402.setFreeuse1(listMi029.get(0).getUsername());
		mi402.setSumcount(1);
		mi402.setSuccessnum(0);
		mi402.setTiming(CommonUtil.isEmpty(form.getTiming())?"0":form.getTiming());
		mi402.setDsdate(form.getDsdate());
		mi402.setFreeuse2(listMi029.get(0).getCertinum());
		mi402.setSendseqno(form.getSendseqno());
		this.getcMi402Dao().insert(mi402);
		return 1;
	}
	
	
	/**
	 * 微信短信息推送
	 * 
	 * @param seqid
	 *            短信息批次号（MI402.seqid）
	 * @param centerId
	 *            中心代码
	 * @return 推送的终端设备数
	 */
	@SuppressWarnings("unchecked")
	private int sendMsgWX(String seqid, String centerId,ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Logger log = LoggerUtil.getLogger();
//		System.out.println("开始推送");
//		int count = 0;
//		Mi402Example mi402Example = new Mi402Example();
//		mi402Example.setOrderByClause("msgid desc");
//		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
//		mi402Criteria.andCenteridEqualTo(centerId);
//		mi402Criteria.andSeqidEqualTo(seqid);
//		mi402Criteria.andStatusNotEqualTo(Constants.PUSH_MSG_ARD_PUSH);
//		mi402Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		int page = 0;
//		final int rows = 5000;
//		int total = 0;
//		do {
//			page++;
//			WebApi30202_queryResult rs = this.getcMi402Dao()
//					.selectByExamplePagination(mi402Example, page, rows);
//			total = rs.getTotal();
//			count = count+total;		
//			List<Mi402> listMi402 = rs.getRows();
//			System.out.println("得取待推送总数："+listMi402.size());
//			if (CommonUtil.isEmpty(listMi402)) {
//				log.error(ERROR.NO_DATA.getLogText("MI402", mi402Criteria
//						.getCriteriaWithSingleValue().toString()
//						+ mi402Criteria.getCriteriaWithoutValue().toString()));
//				throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA
//						.getValue(), "推送信息数据");
//			}
//			for (Iterator<Mi402> iterator = listMi402.iterator(); iterator
//					.hasNext();) {
//				Mi402 mi402 = (Mi402) iterator.next();
//				System.out.println("*******************待发送用户:"+mi402.getUserid());
//				System.out.println("开始登记403表========>");
//				Mi403Example mi403Example = new Mi403Example();
//				mi403Example.createCriteria().andCenteridEqualTo(centerId)
//						.andSeqidEqualTo(seqid).andMsgidEqualTo(
//								mi402.getMsgid());
//				int countMi403 = this.getMi403Dao().countByExample(
//						mi403Example);
//				if (countMi403 != 0) {
//					System.out.println("用户已堆送，不再重复推送。");
//					continue;
//				}
//				String pushid = commonUtil.genKey("MI403", 0);
//				if (CommonUtil.isEmpty(pushid)) {
//					log.error(ERROR.NULL_KEY.getLogText("MI403"));
//					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
//							.getValue(), ERROR.NULL_KEY.getValue());
//				}	
//				System.out.println("调用微信推送403_Pushid:"+pushid);
//				
//				
//				log.info(LOG.START_BUSIN.getLogText("微信公众短信息发送开始"));
//				String url = PropertiesReader.getProperty(
//						Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/sendpermsg";		
//				StringBuffer value = new StringBuffer("{\"regionId\":\""+centerId+"\",\"openId\":\""+mi402.getUserid()+"\",\"title\":\""+mi402.getTitle()+"\",\"message\":\""+mi402.getTsmsg()+"\"}");
//				log.debug("微信策略查询地址："+url);
//				log.debug("微信策略查询参数："+value.toString());		
//				WeiXinMessageUtil weixin = new WeiXinMessageUtil();
//				String msg = weixin.post(url, value.toString(), modelMap, request, response);
//				log.info(LOG.END_BUSIN.getLogText("微信公众短信息发送结束"));	
//				Mi403 mi403 = new Mi403();
//				if(msg.indexOf("\"errcode\":0")>=0){
//					mi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);					
//				}else {
//					HashMap map = JsonUtil.getGson().fromJson(msg, HashMap.class);
//					log.error(ERROR.CONNECT_SEND_ERROR.getLogText("消息ID："+mi402.getCommsgid()+"发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg")));
//					throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
//				}
//				mi403.setPushid(pushid);
//				mi403.setMsgid(mi402.getMsgid());
//				mi403.setCommsgid(mi402.getCommsgid());
//				mi403.setUserid(mi402.getUserid());
//				mi403.setCenterid(centerId);
//				mi403.setSeqid(mi402.getSeqid());
//				mi403.setTitle(mi402.getTitle());
//				mi403.setDetail(mi402.getDetail());
//				mi403.setPusMessageType(mi402.getPusMessageType());
//				mi403.setParam1(mi402.getParam1());
//				mi403.setParam2(mi402.getParam2());
//				mi403.setParam3(mi402.getParam3());
//				mi403.setParam4(mi402.getParam4());
//				mi403.setParam5(mi402.getParam5());
//				mi403.setValidflag(Constants.IS_VALIDFLAG);
//				mi403.setDatecreated(CommonUtil.getSystemDate());
//				mi403.setDatemodified(CommonUtil.getSystemDate());
//				mi403.setLoginid(mi402.getLoginid());
//				mi403.setTsmsg(mi402.getTsmsg());
//				mi403.setTsmsgtype(mi402.getTsmsgtype());
//				mi403.setTheme(mi402.getTheme());
//				this.getMi403Dao().insert(mi403);
//				long pushOrderNo = commonUtil.genKey("MI402.PUSH_ORDER_NO");
//				mi402.setPushOrderNo(pushOrderNo);
//				mi402.setDatemodified(CommonUtil.getSystemDate());
//				mi402.setStatus(mi403.getStatus());
//				this.getcMi402Dao().updateByPrimaryKeySelective(mi402);
//			}
//		} while (page * rows < total);

		return 0;
	}
	
	
	
	

	@SuppressWarnings("unchecked")
	public WebApi30203_queryResult webapi3020203(WebApi30203Form form) {
		WebApi30203_queryResult result = new WebApi30203_queryResult();
		int page = Integer.valueOf(form.getPage());;
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		int total = 0;
		List<Mi401> results = new ArrayList();
		if("1".equals(form.getIsToday())){
			Mi401Example mi401Example = new Mi401Example();
			mi401Example.setOrderByClause("datecreated desc");
			Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
			mi401Criteria.andCenteridEqualTo(form.getCenterId())
					.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH)
					.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			if(!CommonUtil.isEmpty(form.getPusMessageType())){
				mi401Criteria.andPusMessageTypeEqualTo(form.getPusMessageType());
			}
			if(!CommonUtil.isEmpty(form.getMsgsource())){
				mi401Criteria.andMsgsourceEqualTo(form.getMsgsource());
			}
			mi401Criteria.andDatecreatedGreaterThanOrEqualTo(CommonUtil.getDate()+" 00:00:00.000")
				.andDatecreatedLessThanOrEqualTo(CommonUtil.getDate()+" 23:59:59.999");
			
			total = this.getMi401Dao().countByExample(mi401Example);
			results = this.getcMi401Dao().selectByExamplePageWithBlobs(mi401Example, skipResults, rows);
		}else{
			Mi421Example mi421Example = new Mi421Example();
			mi421Example.setOrderByClause("datecreated desc");
			Mi421Example.Criteria mi421Criteria = mi421Example.createCriteria();
			mi421Criteria.andCenteridEqualTo(form.getCenterId())
					.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH)
					.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			if(!CommonUtil.isEmpty(form.getPusMessageType())){
				mi421Criteria.andPusMessageTypeEqualTo(form.getPusMessageType());
			}
			if(!CommonUtil.isEmpty(form.getMsgsource())){
				mi421Criteria.andMsgsourceEqualTo(form.getMsgsource());
			}
			if(!CommonUtil.isEmpty(form.getStartdate())&&!CommonUtil.isEmpty(form.getEnddate())){
				mi421Criteria.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000")
				.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
			}
			
			total = this.getMi421Dao().countByExample(mi421Example);
			results = this.getcMi421Dao().selectByExamplePageWithBlobs(mi421Example, skipResults, rows);
		}
		
		result.setRows(results);
		result.setPageNumber(form.getPage());
		result.setPageSize(form.getRows());
		result.setTotal(total);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi30202_queryResult webapi3020206(WebApi30203Form form){
		Mi402Example mi402Example = new Mi402Example();
		mi402Example.setOrderByClause("datecreated desc");
		mi402Example.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH)
				.andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		WebApi30202_queryResult result = this.cMi402Dao.selectByExamplePagination(mi402Example,
				Integer.valueOf(form.getPage()) , Integer.valueOf(form.getRows()));
		
		List<Mi402> list = result.getRows();
		for(int i=0;i<list.size();i++){	
			if(!CommonUtil.isEmpty(list.get(i).getPid())){
				Mi040 mi040 = mi040Dao.selectByPrimaryKey(list.get(i).getPid());
				list.get(i).setFreeuse1(mi040.getAppname());
			}
		}
		result.setRows(list);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public WebApi30204_queryResult webapi30204(WebApi30204Form form) {
		WebApi30204_queryResult result = new WebApi30204_queryResult();
		Mi100Example mi100Example = new Mi100Example();
		mi100Example.setOrderByClause("transdate desc");
		Mi100Example.Criteria mi100Criteria = mi100Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId()).andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if (!CommonUtil.isEmpty(form.getStartdate())) {
			mi100Criteria.andTransdateGreaterThanOrEqualTo(form.getStartdate());
		}
		if (!CommonUtil.isEmpty(form.getEnddate())) {
			mi100Criteria.andTransdateLessThanOrEqualTo(form.getEnddate());
		}
		
		WebApi100_queryResult results =cMi100Dao.selectByExamplePagination(mi100Example, form.getPage(),
				form.getRows());

		result.setRows(results.getRows());
		result.setPageNumber(results.getPageNumber());
		result.setPageSize(results.getPageSize());
		result.setTotal(results.getTotal());

		return result;
	}

	public WebApi30206_queryResult webapi3020401(WebApi30202_quertForm form) {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "消息ID");
		}
		
		/*
		 * 查询公共短信息数据
		 */
		Mi403Example mi403Example = new Mi403Example();
		mi403Example.setOrderByClause("datecreated desc");
		Mi403Example.Criteria mCriteria = mi403Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		mCriteria.andCommsgidEqualTo(form.getCommsgid());
		mCriteria.andPidEqualTo(form.getPid());
		mCriteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		WebApi30206_queryResult result = this.getcMi403Dao()
				.selectByExamplePagination(mi403Example, form.getPage() ,form.getRows());

		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result.getRows())) {
			log.error(ERROR.NO_DATA.getLogText("MI402", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"报盘推送信息");
		}
		
		List<Mi403> mi403s = result.getRows();
		for(int j=0;j<result.getRows().size();j++){
			Mi403 mi403 = mi403s.get(j);
			if(Constants.SEND_MESSAGE_TYPE_IMAGE.equals(mi403.getTsmsgtype())){
				String listImgUrl = "";				
				if (!CommonUtil.isEmpty(mi403.getParam2())) {					
					String[] pImgs = mi403.getParam2().split(",");
					for (int i = 0; i < pImgs.length; i++) {
						String img = pImgs[i];
						String imgUrl="";
						try {
							imgUrl = "<img src=\""+CommonUtil.getDownloadFileUrl("push_msg_img",
									form.getCenterId() + File.separator + img, true)+"\" style=\"width:294px;height:300px;\">";
						} catch (IOException e) {							
							e.printStackTrace();
						}
						if(i==0){
							listImgUrl = listImgUrl+imgUrl;
						}else{
							listImgUrl = listImgUrl+"<br/>"+imgUrl;
						}						
					}					
					mi403.setTsmsg(listImgUrl);
				}
			}
			if(!CommonUtil.isEmpty(mi403.getPid())){
				Mi040 mi040 = mi040Dao.selectByPrimaryKey(mi403.getPid());
				mi403.setFreeuse1(mi040.getAppname());
			}
			//单图文消息获取评论开关信息
			if("03".equals(mi403.getTsmsgtype())){
				Mi404Example mi404Example = new Mi404Example();
				Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria()
						.andCenteridEqualTo(form.getCenterId());
				mi404Criteria.andCommsgidEqualTo(form.getCommsgid());
				List<Mi404> mi404 = this.mi404Dao.selectByExampleWithoutBLOBs(mi404Example);
				mi403.setFreeuse3(mi404.get(0).getFreeuse3());
				mi403.setFreeuse4(mi404.get(0).getFreeuse4());
			}
			mi403s.set(j, mi403);
		}
		result.setRows(mi403s);
		return result;
	}

	public Mi402DAO getMi402Dao() {
		return mi402Dao;
	}

	public void setMi402Dao(Mi402DAO mi402Dao) {
		this.mi402Dao = mi402Dao;
	}

	/* (non-Javadoc)
	 * @see com.yondervision.mi.service.WebApi302Service#webapi30201_addText(com.yondervision.mi.dto.CMi401)
	 */
	public String webapi30201_addText(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		if (CommonUtil.isEmpty(form.getTsmsg())) {
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "信息内容");
		}
		if (CommonUtil.isEmpty(form.getPid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("pid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "推送应用");
		}
		if(CommonUtil.isEmpty(form.getDetail())){
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "描述信息");
		}
		
		if("1".equals(form.getIsheadline()))
		{
			if(CommonUtil.isEmpty(form.getTitle())){
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "标题");
			}
			if(form.getTitle().length()>32){
				throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_TOO_LONG.getValue(), "标题","32");
			}
		}
		String tel = this.getCustsvctel(form.getCenterid());
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);		
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setSumcount(1);
		form.setSuccessnum(0);
//		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);//01text,02image,03RICHTEXT,04audio,05video

		form.setMsgsource(form.getMsgsource());//10综合服务平台，20业务系统，30渠道
		form.setApprove(Constants.APPROVE_NO);
		this.getMi401Dao().insert(form);
		
		insertGroupWaitMessage(form);
		insertMi404(form);
		
		return commsgid;
	}
	
	public String webapi30201_addImage(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI401"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		if (CommonUtil.isEmpty(form.getParam2())) {
			log.error(ERROR.PARAMS_NULL.getLogText("param2"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "图片信息");
		}
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			form.setDetail("");
		}
		form.setTsmsg(CommonUtil.isEmpty(form.getTsmsg())?"图片群推":form.getTsmsg());
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_IMAGE);
		form.setMsgsource(form.getMsgsource());
		form.setApprove(Constants.APPROVE_NO);
		form.setSumcount(1);
		form.setSuccessnum(0);
		this.getMi401Dao().insert(form);
		
		insertGroupWaitMessage(form);
		insertMi404(form);
		
		return commsgid;
	}
	
	public String webapi30201_addTextImageSingle401(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		if (CommonUtil.isEmpty(form.getTsmsg())) {
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "信息内容");
		}
		if (CommonUtil.isEmpty(form.getPid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("pid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "推送应用");
		}
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "描述信息");
		}
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);		
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setSumcount(1);
		form.setSuccessnum(0);
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_RICHTEXT);
		form.setMsgsource(form.getMsgsource());
		form.setApprove(Constants.APPROVE_NO);
		this.getMi401Dao().insert(form);
		insertGroupWaitMessage(form);
		return commsgid;
	}

	public String webapi30201_editTextImageSingle401(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getTsmsg())) {
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "信息内容");
		}
		if (CommonUtil.isEmpty(form.getPid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("pid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "推送应用");
		}
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "描述信息");
		}
		form.setParam1(tel);
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setApprove(Constants.APPROVE_NO);
		this.getMi401Dao().updateByPrimaryKeySelective(form);
		
		Mi402Example mi402Example = new Mi402Example();
		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
		mi402Criteria.andCommsgidEqualTo(form.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi402> list = mi402Dao.selectByExampleWithBLOBs(mi402Example);
		for(int i=0;i<list.size();i++){
			Mi402 mi402 = list.get(i);
			mi402.setTitle(form.getTitle());
			mi402.setDetail(form.getDetail());
			mi402.setTiming(form.getTiming());
			mi402.setDsdate(form.getDsdate());
			mi402.setTsmsg(form.getTsmsg());
			mi402.setParam2(form.getParam2());
			mi402Dao.updateByPrimaryKeySelective(mi402);
		}
		return form.getCommsgid();
	}
	
	public int webapi30201_updateTextImageSingle(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "批次号");			
		}
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid());
		mCriteria.andMsmscommsgidEqualTo(form.getMsmscommsgid());
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		Mi404 mi404 = new Mi404();
		mi404.setTitle(form.getTitle());
		mi404.setTheme(form.getTheme());
		//mi404.setMsnum(form.getMsnum());
		mi404.setTsmsg(form.getTsmsg());
		mi404.setDetail(form.getDetail());
		mi404.setParam1(form.getParam1());
		mi404.setParam2(form.getParam2());
		mi404.setFreeuse4(Integer.valueOf(form.getCanComment()));
		mi404.setFreeuse3(form.getIsAllComment());
		return mi404Dao.updateByExampleSelective(mi404, mi404Example);
		//return mi404Dao.updateByExampleWithBLOBs(mi404,mi404Example);	
	}
	
	public void webapi30201_addTextImageSingle(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.NULL_KEY.getLogText("MI404"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		
		if (CommonUtil.isEmpty(form.getTitle())) {
			log.error(ERROR.PARAMS_NULL.getLogText("mstitle"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "标题信息");
		}
		
		if (CommonUtil.isEmpty(form.getTsmsg())) {
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "详细信息");
		}
		
		if (CommonUtil.isEmpty(form.getParam2())) {
			log.error(ERROR.PARAMS_NULL.getLogText("param2"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图片信息");
		}
		
		String msmscommsgid = commonUtil.genKey("MI404", 0);
		if (CommonUtil.isEmpty(msmscommsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI404"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		
		Mi404Example mi404Example = new Mi404Example();		
		Mi404 mi404 = new Mi404();
		mi404.setTitle(form.getTitle());
		mi404.setDetail(form.getDetail());
		mi404.setFreeuse3(form.getIsAllComment());
		mi404.setFreeuse4(Integer.valueOf(form.getCanComment()));
		mi404.setCommsgid(form.getCommsgid());
		mi404.setPid(form.getPid());
		mi404.setTheme(form.getTheme());
		mi404.setTsmsg(form.getTsmsg());
		mi404.setTiming(form.getTiming());
		mi404.setDsdate(form.getDsdate());
		mi404.setSendseqno(form.getSendseqno());
		mi404.setMsnum(1);
		// 取得中心热线电话
		String tel = this.getCustsvctel(form.getCenterid());
		mi404.setParam1(tel);
		mi404.setParam2(form.getParam2());
		mi404.setParam3(form.getParam3());
		mi404.setParam4(form.getParam4());
		mi404.setParam5(form.getParam5());
		mi404.setMsmscommsgid(msmscommsgid);
		mi404.setCenterid(form.getCenterid());
		mi404.setPusMessageType(Constants.PUSH_TYPE_QT);
		mi404.setStatus(Constants.PUSH_MSG_DEF_STATE);
		mi404.setValidflag(Constants.IS_VALIDFLAG);
		mi404.setDatecreated(CommonUtil.getSystemDate());
		mi404.setDatemodified(CommonUtil.getSystemDate());
		mi404.setLoginid(form.getUserid());
		mi404.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_RICHTEXT);
		mi404.setMsgsource(Constants.DATA_SOURCE_YBMAP);
		mi404Dao.insert(mi404);
	}
	
	public String webapi30201_addTextImageMai(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();

		if(!CommonUtil.isEmpty(form.getCommsgid())){
			Mi401Example mi401Example = new Mi401Example();
			mi401Example.setOrderByClause("datecreated desc");
			Mi401Example.Criteria mCriteria = mi401Example.createCriteria()
					.andCenteridEqualTo(form.getCenterid());
			mCriteria.andCommsgidEqualTo(form.getCommsgid());			
			mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			Mi401 mi401 = new Mi401();
			mi401.setTitle(form.getTitle());
			mi401.setCommsgid(form.getCommsgid());
			mi401Dao.updateByPrimaryKeySelective(mi401);
			return form.getCommsgid();
		}
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI401"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		
//		if (CommonUtil.isEmpty(form.getTsmsg())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "推送信息");
//		}
		
		// 取得中心热线电话
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			form.setDetail("");
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "描述信息");
		}
		form.setTsmsg(CommonUtil.isEmpty(form.getTsmsg())?"图文群推":form.getTsmsg());
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_RICHTEXT_MORE);
		form.setTiming(form.getTiming());
		form.setDsdate(form.getDsdate());
		form.setMsgsource(form.getMsgsource());
		form.setApprove(Constants.APPROVE_NO);
		form.setSumcount(1);
		form.setSuccessnum(0);
		this.getMi401Dao().insert(form);
		
		return commsgid;
		
	}
	
	public String webapi30201_addAudio(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI401"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		if (CommonUtil.isEmpty(form.getParam2())) {
			log.error(ERROR.PARAMS_NULL.getLogText("param2"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "音频信息");
		}
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			form.setDetail("");
		}
		form.setTsmsg(CommonUtil.isEmpty(form.getTsmsg())?"音频群推":form.getTsmsg());
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_AUDIO);
		form.setMsgsource(form.getMsgsource());
		form.setApprove(Constants.APPROVE_NO);
		form.setSumcount(1);
		form.setSuccessnum(0);
		this.getMi401Dao().insert(form);
		
		insertGroupWaitMessage(form);
		insertMi404(form);
		return commsgid;
	}
	
	public String webapi30201_addVideo(CMi401 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String commsgid = commonUtil.genKey("MI401", 0);
		if (CommonUtil.isEmpty(commsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI401"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),ERROR.NULL_KEY.getValue());
		}
		if (CommonUtil.isEmpty(form.getParam2())) {
			log.error(ERROR.PARAMS_NULL.getLogText("param2"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "视频信息");
		}
		String tel = this.getCustsvctel(form.getCenterid());
		if(CommonUtil.isEmpty(form.getDetail())){
			form.setDetail("");
		}
		form.setTsmsg(CommonUtil.isEmpty(form.getTsmsg())?"视频群推":form.getTsmsg());
		form.setParam1(tel);
		form.setCommsgid(commsgid);
		form.setCenterid(form.getCenterid());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_VIDEO);
		form.setMsgsource(form.getMsgsource());
		form.setApprove(Constants.APPROVE_NO);
		form.setSumcount(1);
		form.setSuccessnum(0);
		this.getMi401Dao().insert(form);
		
		insertGroupWaitMessage(form);
		insertMi404(form);
		return commsgid;
	}

	public List<Mi404> webapi30201_queryTextImage(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "批次号");
			return null;
		}
		
		/*
		 * 查询公共短信息数据
		 */
		Mi404Example mi404Example = new Mi404Example();
//		mi404Example.setOrderByClause("msnum");
		mi404Example.setOrderByClause("msnum asc,datecreated desc");
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid());
		mCriteria.andCommsgidEqualTo(form.getCommsgid());
		//mCriteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi404> result = mi404Dao.selectByExampleWithBLOBs(mi404Example);
		return result;
	}

	public Mi404DAO getMi404Dao() {
		return mi404Dao;
	}

	public void setMi404Dao(Mi404DAO mi404Dao) {
		this.mi404Dao = mi404Dao;
	}

	public String webapi30201_addTextImage(CMi404 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.NULL_KEY.getLogText("MI404"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		
		if (CommonUtil.isEmpty(form.getTitle())) {
			log.error(ERROR.PARAMS_NULL.getLogText("mstitle"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "标题信息");
		}
		
		if (CommonUtil.isEmpty(form.getTsmsg())) {
			log.error(ERROR.PARAMS_NULL.getLogText("tsmsg"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "详细信息");
		}
		
		if (CommonUtil.isEmpty(form.getParam2())) {
			log.error(ERROR.PARAMS_NULL.getLogText("param2"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图片信息");
		}
		
		String msmscommsgid = commonUtil.genKey("MI404", 0);
		if (CommonUtil.isEmpty(msmscommsgid)) {
			log.error(ERROR.NULL_KEY.getLogText("MI404"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getValue());
		}
		
		Mi404Example mi404Example = new Mi404Example();		
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId()).andCommsgidEqualTo(form.getCommsgid());
		form.setMsnum(mi404Dao.countByExample(mi404Example)+1);
		// 取得中心热线电话
		String tel = this.getCustsvctel(form.getCenterId());
		form.setParam1(tel);
		form.setMsmscommsgid(msmscommsgid);
		form.setCenterid(form.getCenterId());
		form.setPusMessageType(Constants.PUSH_TYPE_QT);
		form.setStatus(Constants.PUSH_MSG_DEF_STATE);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setLoginid(form.getUserid());
		form.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_RICHTEXT_MORE);
		form.setMsgsource(Constants.DATA_SOURCE_YBMAP);
		mi404Dao.insert(form);	
		
		return null;
	}

	public int webapi30201_updateTextImage(CMi404 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "批次号");			
		}
		
		/*
		 * 查询公共短信息数据
		 */
		Mi404Example mi404Example = new Mi404Example();
		mi404Example.setOrderByClause("datecreated desc");
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		mCriteria.andMsmscommsgidEqualTo(form.getMsmscommsgid());
		//mCriteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		Mi404 mi404 = new Mi404();
		mi404.setTitle(form.getTitle());
		mi404.setTheme(form.getTheme());
		//mi404.setMsnum(form.getMsnum());
		mi404.setTsmsg(form.getTsmsg());
		mi404.setDetail(form.getDetail());
		mi404.setParam1(form.getParam1());
		mi404.setParam2(form.getParam2());
		mi404.setFreeuse4(form.getFreeuse4());
		mi404.setFreeuse3(form.getFreeuse3());
		return mi404Dao.updateByExampleSelective(mi404, mi404Example);
		//return mi404Dao.updateByExampleWithBLOBs(mi404,mi404Example);		
	}

	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor = NoRollRuntimeErrorException.class)
	public void webapi30201_deletems(CMi404 form)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数校验
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getUserid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "操作员代码");
		}
		if (CommonUtil.isEmpty(form.getMsmscommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "要删除信息");
		}
		
		try {
			String[] delids = form.getMsmscommsgid().split(",");
			
			for(int i=0;i<delids.length;i++){				
				Mi404 mi404 = new Mi404();
				mi404.setMsmscommsgid(delids[i]);
				mi404.setValidflag(Constants.IS_NOT_USABLEFLAG);
				mi404Dao.updateByPrimaryKeySelective(mi404);
			}
		} catch (Exception e) {
			log.error(ERROR.SYS.getLogText(e.getMessage()), e);
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
	}

	public void webapi30201_editImage(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共短信息ID");
		}

		// 判定当前要修改记录是否已经被审批
		Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
		if(!Constants.APPROVE_NO.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "已审批", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"修改","审批","修改");
		}

		/*
		 * 修改公共短信息
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi401Dao().updateByPrimaryKeySelective(form);
	}

	public void webapi30201_editText(CMi401 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "公共短信息ID");
		}

		// 判定当前要修改记录是否已经被审批
		Mi401 mi401 = mi401Dao.selectByPrimaryKey(form.getCommsgid());
		if(!Constants.APPROVE_NO.equals(mi401.getApprove())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "已审批", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"修改","审批","修改");
		}

		/*
		 * 修改公共短信息
		 */
		form.setDatemodified(CommonUtil.getSystemDate());
		this.getMi401Dao().updateByPrimaryKeySelective(form);
	}

	public int webapi30201_sendText(CMi401 form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Logger log = LoggerUtil.getLogger();
		
//		log.info(LOG.START_BUSIN.getLogText("微信公众短信息发送开始"));
//		String url = PropertiesReader.getProperty(
//				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/push";		
//		StringBuffer value = new StringBuffer("{\"regionId\":\""+form.getCenterId()+"\",\"pushcontent\":\""+mi401.getTitle()+"\"}");
//		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
//		String msg = weixin.post(url, value.toString(), modelMap, request, response);
//		log.info(LOG.END_BUSIN.getLogText("微信公众短信息发送结束"));	
//		if(msg.indexOf("\"errcode\":0")>=0){
//			String pushid = commonUtil.genKey("MI403", 0);
//			if (CommonUtil.isEmpty(pushid)) {
//				log.error(ERROR.NULL_KEY.getLogText("MI403"));
//				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
//						.getValue(), ERROR.NULL_KEY.getValue());
//			}
//		}else{
//			HashMap map = JsonUtil.getGson().fromJson(msg, HashMap.class);
//			log.error(ERROR.CONNECT_SEND_ERROR.getLogText("消息ID："+form.getCommsgid()+"发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg")));
//			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
//		}
				
//					Mi122Example mi122Example = new Mi122Example();
//					mi122Example.setOrderByClause("num asc");
//					Mi122Example.Criteria mi122Criteria = mi122Example.createCriteria();
//					mi122Criteria.andCenteridEqualTo(form.getCenterId());
//					mi122Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//					mi122Criteria.andMessageTopicTypeEqualTo(mi401.getTheme());
//					List<Mi122> list122 = mi122Dao.selectByExample(mi122Example); 
//					if(list122.get(0).getMustsend().equals("0")){							
//						Mi124Example mi124ExampleZT = new Mi124Example();
//						Mi124Example.Criteria mi124CriteriaZT = mi124ExampleZT.createCriteria();
//						mi124CriteriaZT.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//						mi124CriteriaZT.andUseridEqualTo();
//						List<Mi124> list124ZT = mi124Dao.selectByExample(mi124ExampleZT);
//						
//						
//						if(list124ZT.size()>0){
//							Mi124 mi124 = list124ZT.get(0);
//							if(mi124.getFirstzt().equals("1")){
//								if(mi124.getTopictype().indexOf(mi401.getTheme())<0){
//									continue;
//								}								
//							}							
//						}
//					}			
		
		return 0;
	}

	public int webapi30201_sendImage(CMi401 form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		Logger log = LoggerUtil.getLogger();
//		String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
//		if (CommonUtil.isEmpty(form.getCenterid())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "中心代码");
//		}
//		form.setCommsgid(form.getFreeuse1());
//		if (CommonUtil.isEmpty(form.getCommsgid())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "公共短信息ID");
//		}		
//			
//		Mi401 mi401Tmp = mi401Dao.selectByPrimaryKey(form.getCommsgid());
//		if(Constants.PUSH_MSG_DEF_STATE.equals(mi401Tmp.getStatus())){
//			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getCommsgid(), "未审批", "短消息发送"));
//			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息未审批，请审批后再进行发送");
//		}
//		Mi402Example mi402Example = new Mi402Example();
//		mi402Example.createCriteria().andCenteridEqualTo(form.getCenterid())
//				.andCommsgidEqualTo(form.getCommsgid()).andValidflagEqualTo(
//						Constants.IS_VALIDFLAG);
//		int countMi402 = this.getcMi402Dao().countByExample(mi402Example);
//		String seqid = null;
//		int count=0;
//		if (countMi402 != 0) {
//			List<Mi402> listMi402 = this.getcMi402Dao()
//					.selectByExamplePagination(mi402Example, 1, 1).getRows();
//			seqid = listMi402.get(0).getSeqid();
//		}
//		else {			
//			Mi401Example mi401Example = new Mi401Example();
//			Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
//			mi401Criteria.andCenteridEqualTo(form.getCenterid());
//			mi401Criteria.andCommsgidEqualTo(form.getCommsgid());
//			mi401Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//			mi401Criteria.andStatusNotEqualTo(Constants.PUSH_MSG_ARD_PUSH);
//			List<Mi401> listMi401 = this.getMi401Dao().selectByExampleWithBLOBs(mi401Example);
//			if (CommonUtil.isEmpty(listMi401)) {
//				log.error(ERROR.NO_DATA
//						.getLogText("MI401", mi401Criteria
//								.getCriteriaWithListValue().toString()
//								+ mi401Criteria.getCriteriaWithoutValue()
//										.toString()
//								+ mi401Criteria.getCriteriaWithSingleValue()
//										.toString()));
//				throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA
//						.getValue(), "公共推送信息");
//			}
//			Mi401 mi401 = listMi401.get(0);
//			
//			String[] channels = form.getChannel().split(",");
//			seqid = commonUtil.genKey("MI402.SEQID", 0);
//			for(int i=0;i<channels.length;i++){
//				if("20".equals(channels[i])){					
//					if (CommonUtil.isEmpty(seqid)) {
//						log.error(ERROR.NULL_KEY.getLogText("MI402.SEQID"));
//						throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
//								ERROR.NULL_KEY.getValue());
//					}
//					Long msgid = commonUtil.genKey("MI402");
//					if (CommonUtil.isEmpty(msgid)) {
//						log.error(ERROR.NULL_KEY.getLogText("MI402"));
//						throw new NoRollRuntimeErrorException(WEB_ALERT.SYS
//								.getValue(), ERROR.NULL_KEY.getValue());
//					}						
//					
//					
//					log.info(LOG.START_BUSIN.getLogText("微信公众短信息发送开始"));
//					String url = PropertiesReader.getProperty(
//							Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/pushpic";					
//					
//					String[] pImgs = mi401.getParam2().split(",");
//					String img = pImgs[0];
//					String imgUrl = path+"/YBMAPZH/"+CommonUtil.getDownloadFileUrl(
//							"push_msg_img", form.getCenterId() + "/"
//									+ img, true);
//						
//					log.info("图片地址:"+imgUrl);					
//					
//					StringBuffer value = new StringBuffer("{\"centerid\":\""+form.getCenterId()+"\",\"imgurl\":\""+imgUrl+"\"}");
//					
//					log.debug("微信图片群推地址："+url);
//					log.debug("微信图片群推参数："+value.toString());
//					WeiXinMessageUtil weixin = new WeiXinMessageUtil();
//					String msg = weixin.post(url, value.toString(), modelMap, request, response);
////					String msg = "\"errcode\":0";
//					log.info(LOG.END_BUSIN.getLogText("微信公众短信息发送结束"));	
//					if(msg.indexOf("\"errcode\":0")>=0){
//						
//						Integer seqno = commonUtil.genKey("MI100").intValue();
//						if (CommonUtil.isEmpty(seqno)) {
//							log.error(ERROR.NULL_KEY.getLogText("MI100"));
//							throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
//									ERROR.NULL_KEY.getValue());
//						}
//												
//						
//						
//					}else{
//						HashMap map = JsonUtil.getGson().fromJson(msg, HashMap.class);
//						log.error(ERROR.CONNECT_SEND_ERROR.getLogText("消息ID："+form.getCommsgid()+"发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg")));
//						throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
//					}					
//					count+=1;
//				}
//			}
//		}
		return 0;
	}

	public int webapi30201_sendTextImage(CMi401 form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/uploadpic";					
//		StringBuffer mess = new StringBuffer();			
//		for(int ii=0;ii<10;ii++){
//			if(ii==0){
//				mess.append("[{");
//			}else{
//				mess.append(",{");
//			}						
//			String[] pImgs = mi404.getMsparam2().split(",");
//			String img = pImgs[0];
//			String imgUrl = path+"/YBMAPZH/"+CommonUtil.getDownloadFileUrl(
//					"push_msg_img", form.getCenterId() + "/"
//							+ img, true);
//			Mi002 mi002 = new Mi002();
//			Mi002Example mi002Example = new Mi002Example();
//			Mi002Example.Criteria mi002C = mi002Example.createCriteria();
//			mi002C.andLoginidEqualTo(mi404.getMsloginid());
//			List<Mi002> listMi002 = mi002Dao.selectByExample(mi002Example);
//			
//			mess.append("\"thumb_media_id\":\""+imgUrl+"\",");
//			mess.append("\"author\":\""+listMi002.get(0).getOpername()+"\",");
//			mess.append("\"title\":\""+mi404.getMstitle()+"\",");
//			mess.append("\"content_source_url\":\"\",");
//			mess.append("\"content\":'"+mi404.getMstsmess().replaceAll("src=\"/YBMAPZH/", "src=\""+path+"/YBMAPZH/")+"',");
//			mess.append("\"digest\":\"\",");
//			mess.append("\"show_cover_pic\":\"1\"}");
//		}
//		mess.append("]");
//								
//					
//		StringBuffer value = new StringBuffer("{\"centerid\":\""+form.getCenterid()+"\",\"articles\":"+mess.toString()+"}");
//					
//		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
//		String msg = weixin.post(url, value.toString(), modelMap, request, response);
//		if(msg.indexOf("\"errcode\":0")>=0){
//			
//			Integer seqno = commonUtil.genKey("MI100").intValue();
//			if (CommonUtil.isEmpty(seqno)) {
//				throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
//						ERROR.NULL_KEY.getValue());
//			}
//			
//		}else{
//			HashMap map = JsonUtil.getGson().fromJson(msg, HashMap.class);
//			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
//		}					
		return 0;
	}

	public int webapi30201_send(CMi401 form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Mi403Example mi403Example = new Mi403Example();
		mi403Example.createCriteria().andCommsgidEqualTo(form.getCommsgid())
				.andCenteridEqualTo(form.getCenterid()).andStatusEqualTo(Constants.PUSH_MSG_DEF_STATE).
				andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi403> list = this.getMi403Dao().selectByExampleWithBLOBs(mi403Example);
		int sum = 0;
		if(!CommonUtil.isEmpty(list)){
			for(int i=0;i<list.size();i++){
				Mi403 mi403 = list.get(i);
//				Mi040 mi040 = mi040Dao.selectByPrimaryKey(mi403.getPid());
				boolean isSend = false;
				if("10".equals(mi403.getPid().substring(0, 2))){
					sum = sum + sendApp(mi403 ,request ,response);
				}else if("20".equals(mi403.getPid().substring(0, 2))){
					sum = sum + sendWeixin(mi403 , request,  response);
				}
//				else if("yondervisionsms70".equals(mi403.getPid())){
//					//群推短信渠道异步处理
//					sum = sum + sendSMS(mi403);
//					isSend = true;
//				}
				else if("80".equals(mi403.getPid().substring(0, 2))){
					//微博头条文章0-普通文章，1-头条文章
					if("1".equals(mi403.getIsheadline()))
					{
						sum = sum + sendWeiBoHeadLine(mi403 , request, response);
					}else{
						sum = sum + sendWeiBo(mi403 , request, response);
					}
				}
				if(sum>0){
					mi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);
					this.getMi403Dao().updateByPrimaryKeySelective(mi403);
					Mi402 mi402 = this.cMi402Dao.selectByPrimaryKey(mi403.getMsgid());
					mi402.setSuccessnum(mi402.getSuccessnum()+1);
					mi402.setStatus(Constants.PUSH_MSG_ARD_PUSH);
					this.cMi402Dao.updateByPrimaryKeySelective(mi402);
				}
			}
			
			return sum;
		}else{
			return 0;
		}
		
	}
	
	

	public void webapi30202_insertWaitSend(CMi401 form,ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Logger log = LoggerUtil.getLogger();
		HashMap map = new HashMap();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "中心代码");
		}
		
		Mi402Example mi402Example = new Mi402Example();
		mi402Example.createCriteria().andCommsgidEqualTo(form.getCommsgid())
				.andCenteridEqualTo(form.getCenterid()).andStatusEqualTo(Constants.PUSH_MSG_DEF_STATE).
				andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi402> list = this.getMi402Dao().selectByExampleWithoutBLOBs(mi402Example);
		if(CommonUtil.isEmpty(list)){
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "未查询到待推送数据");
		}else{
			Mi402 mi402 = list.get(0);
			String personalid = list.get(0).getPersonalid();
			for(int i=0;i<list.size();i++){
				Mi031Example mi031Example = new Mi031Example();
				mi031Example.createCriteria().andPersonalidEqualTo(personalid)
				.andSendmessageEqualTo("1").andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi031> list031 = mi031Dao.selectByExample(mi031Example);
				if(!CommonUtil.isEmpty(list031)){
					for(int j=0;j<list031.size();j++){
						Mi031 mi031 = list031.get(j);
						String pushid = commonUtil.genKey("MI403", 0);
						Mi403 mi403 = new Mi403();
						mi403.setPushid(pushid);
						mi403.setMsgid(mi402.getMsgid());
						mi403.setCommsgid(mi402.getCommsgid());
						mi403.setPersonalid(personalid);
						mi403.setCenterid(mi402.getCenterid());
						mi403.setTitle(mi402.getTitle());
						mi403.setDetail(mi402.getDetail());
						mi403.setPid(mi031.getPid());
						mi403.setUserid(mi031.getUserid());
						mi403.setUsername(mi402.getUsername());
						mi403.setPusMessageType(mi402.getPusMessageType());
						mi403.setTheme(mi402.getTheme());
						mi403.setTsmsg(mi402.getTsmsg());
						mi403.setTsmsgtype(mi402.getTsmsgtype());
						mi403.setMsgsource(mi402.getMsgsource());
						mi403.setSendseqno(mi402.getSendseqno());
						mi403.setLoginid(mi402.getLoginid());
						mi403.setStatus(mi402.getStatus());
						mi403.setParam1(mi402.getParam1());
						mi403.setParam2(mi402.getParam2());
						mi403.setFreeuse2(mi402.getFreeuse2());
						mi403.setValidflag(Constants.IS_VALIDFLAG);
						mi403.setDatecreated(CommonUtil.getSystemDate());
						mi403.setDatemodified(CommonUtil.getSystemDate());
						mi403Dao.insert(mi403);
						
						if(CommonUtil.isEmpty(map.get(mi031.getPid()))){
							map.put(mi031.getPid() ,1);
						}else{
							map.put(mi031.getPid() ,((Integer)map.get(mi031.getPid()))+1);
						}
						
					}
				}
			}
		    Iterator<Entry<String, Integer>> entryKeyIterator = map.entrySet().iterator();  
	        while (entryKeyIterator.hasNext()) {
	            Entry<String, Integer> e = entryKeyIterator.next();
	            int value=e.getValue();
	            Integer seqno = commonUtil.genKey("MI100").intValue();
	    		Mi100 mi100 = new Mi100();
	    		mi100.setSeqno(seqno);
	    		mi100.setCenterid(form.getCenterid());
	    		mi100.setCommsgid(form.getCommsgid());
	    		mi100.setPid(e.getKey());
	    		mi100.setTheme(mi402.getTheme());
	    		mi100.setTsmsgtype(mi402.getTsmsgtype());
	    		mi100.setPusMessageType(mi402.getPusMessageType());
	    		mi100.setMsgsource(mi402.getMsgsource());
	    		mi100.setTransdate(CommonUtil.getDate());
	    		mi100.setTransCount(e.getValue());
	    		mi100.setValidflag(Constants.IS_VALIDFLAG);
	    		mi100.setDatecreated(CommonUtil.getSystemDate());
	    		mi100.setDatemodified(CommonUtil.getSystemDate());
	    		this.getMi100Dao().insert(mi100);
			}
		}
	}


//	public void webapi30202_auth(CMi401 form) throws Exception {
//		Logger log = LoggerUtil.getLogger();
//		if (CommonUtil.isEmpty(form.getCommsgid())) {
//			log.error(ERROR.PARAMS_NULL.getLogText("Commsgid"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
//					.getValue(), "公共短信息ID");
//		}
//		
//		List<Mi402> list402 = null;
//		List<Mi402> list402Tmp = null;
//		for(int i=0;i<form.getListCommsgid().size();i++){
//			
//			Mi100 mi100 = mi100Dao.selectByPrimaryKey(Integer.valueOf(form.getListCommsgid().get(i)));
//			
//			Mi402Example mi402Example = new Mi402Example();
//			Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
//			mi402Criteria.andCenteridEqualTo(form.getCenterId());
//			mi402Criteria.andSeqidEqualTo(String.valueOf(mi100.getTransSeqid()));
//			mi402Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//			mi402Criteria.andStatusEqualTo(Constants.PUSH_MSG_DEF_STATE);
//			
//			list402 = mi402Dao.selectByExampleWithoutBLOBs(mi402Example);
//			
//			Mi402Example mi402ExampleTmp = new Mi402Example();
//			Mi402Example.Criteria mi402CriteriaTmp = mi402ExampleTmp.createCriteria();
//			mi402CriteriaTmp.andCenteridEqualTo(form.getCenterId());
//			mi402CriteriaTmp.andSeqidEqualTo(String.valueOf(mi100.getTransSeqid()));
//			mi402CriteriaTmp.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//			list402Tmp = mi402Dao.selectByExampleWithoutBLOBs(mi402ExampleTmp);
//			for(int j=0;j<list402Tmp.size();j++){
//				Mi402 mi402 = list402Tmp.get(j);
//				if(Constants.PUSH_MSG_AUTH_STATE.equals(mi402.getStatus())){
//					log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("消息ID："+form.getListCommsgid().get(i), "已审批", "再次审批"));
//					throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"待审批","审批","审批");
//				}
//			}
//		}
//		
//		for(int i=0;i<list402.size();i++){
//			Mi402 mi402 = list402.get(i);
//			mi402.setStatus(Constants.PUSH_MSG_AUTH_STATE);
//			mi402.setDatemodified(CommonUtil.getSystemDate());	
//			mi402.setFreeuse1(form.getUserid());
//			mi402.setLoginid(form.getUserid());
//			mi402Dao.updateByPrimaryKeySelective(mi402);
//			
//			Mi401 mi401 = new Mi401();
//			mi401.setCommsgid(mi402.getCommsgid());
//			mi401.setStatus(Constants.PUSH_MSG_AUTH_STATE);
//			mi401.setDatemodified(CommonUtil.getSystemDate());
//			mi401.setApprover(form.getUserid());
//			mi401.setApproverdate(CommonUtil.getSystemDate());			
//			mi401Dao.updateByPrimaryKeySelective(mi401);
//		}
//	}
	
	public WebApi30205_queryResult webapi3020402(WebApi30202_quertForm form) {
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("comsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "批次号");
		}

		/*
		 * 查询公共短信息数据
		 */
		Mi404Example mi404Example = new Mi404Example();
		mi404Example.setOrderByClause("datecreated desc");
		Mi404Example.Criteria mCriteria = mi404Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId());
		mCriteria.andCommsgidEqualTo(form.getCommsgid());
		mCriteria.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		WebApi30205_queryResult result = cMi404Dao
				.selectByExamplePagination(mi404Example ,form.getPage() ,form.getRows());
		
		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result.getRows())) {
			log.error(ERROR.NO_DATA.getLogText("MI404", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"图文推送信息");
		}
		
		for(int j=0;j<result.getRows().size();j++){
			String listImgUrl = "";				
			if (!CommonUtil.isEmpty(result.getRows().get(j).getParam2())) {					
				String[] pImgs =result.getRows().get(j).getParam2().split(",");
				for (int i = 0; i < pImgs.length; i++) {
					String img = pImgs[i];
					String imgUrl="";
					try {
						imgUrl = "<img src=\""+CommonUtil.getDownloadFileUrl("push_msg_img",
								form.getCenterId() + File.separator + img, true)+"\" style=\"width:50px;height:50px;\">";
					} catch (IOException e) {							
						e.printStackTrace();
					}
					if(i==0){
						listImgUrl = listImgUrl+imgUrl;
					}else{
						listImgUrl = listImgUrl+"<br/>"+imgUrl;
					}						
				}
			}
				result.getRows().get(j).setParam2(listImgUrl);
		}
		
		
		return result;
	}

	public WebApi30204_queryResult webapi30205(WebApi30204Form form) {
		WebApi30204_queryResult result = new WebApi30204_queryResult();
		
//		Mi402Example mi402Example = new Mi402Example();		
//		Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria()
//				.andCenteridEqualTo(form.getCenterId())
//				.andStatusEqualTo(Constants.PUSH_MSG_ARD_PUSH)
//				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		if (!CommonUtil.isEmpty(form.getStartdate())) {
//			mi402Criteria.andDatemodifiedGreaterThanOrEqualTo(form.getStartdate());
//		}
//		if (!CommonUtil.isEmpty(form.getEnddate())) {
//			mi402Criteria.andDatemodifiedLessThanOrEqualTo(form.getEnddate());
//		}
//		if (!CommonUtil.isEmpty(form.getChecktitle())) {
//			mi402Criteria.andTitleLike("%"+form.getChecktitle()+"%");
//		}
//		if (!CommonUtil.isEmpty(form.getChecktext())) {
//			mi402Criteria.andTsmsgLike("%"+form.getChecktext()+"%");
//		}
//		List<Mi402> rows402 = mi402Dao.selectByExampleWithBLOBs(mi402Example);
//		List seqids = new ArrayList();
//		for(int i=0;i<rows402.size();i++){			
//			seqids.add(rows402.get(i).getSeqid());
//		}
//		
//		
//		Mi100Example mi100Example = new Mi100Example();
//		mi100Example.setOrderByClause("transdate desc");
//		Mi100Example.Criteria mi100Criteria = mi100Example.createCriteria()
//				.andCenteridEqualTo(form.getCenterId())
//				.andTransKeyword2EqualTo(Constants.PUSH_MSG_ARD_PUSH)
//				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		if (!CommonUtil.isEmpty(form.getStartdate())) {
//			mi100Criteria.andTransdateGreaterThanOrEqualTo(form.getStartdate());
//		}
//		if (!CommonUtil.isEmpty(form.getEnddate())) {
//			mi100Criteria.andTransdateLessThanOrEqualTo(form.getEnddate());
//		}
//		mi100Criteria.andTransSeqidIn(seqids);
//		/*
//		 * 查询公共短信息数据
//		 */
//		
//		WebApi100_queryResult results =cMi100Dao
//				.selectByExamplePagination(mi100Example, form.getPage(),
//						form.getRows());
//		 
//		
//
//		result.setRows(results.getRows());
//		result.setPageNumber(results.getPageNumber());
//		result.setPageSize(results.getPageSize());
//		result.setTotal(results.getTotal());

		return result;
	}
	
	
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void webapi30201_orderbynum(JSONArray arr) throws Exception {
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi404 mi404 = new Mi404();
			mi404.setMsmscommsgid((String)obj.getString("seqid"));
			mi404.setMsnum(Integer.parseInt(obj.getString("num")));
			mi404Dao.updateByPrimaryKeySelective(mi404);
		}
	}

	public Mi124DAO getMi124Dao() {
		return mi124Dao;
	}

	public void setMi124Dao(Mi124DAO mi124Dao) {
		this.mi124Dao = mi124Dao;
	}

	public Mi122DAO getMi122Dao() {
		return mi122Dao;
	}

	public void setMi122Dao(Mi122DAO mi122Dao) {
		this.mi122Dao = mi122Dao;
	}
	
	public String webapi30210_CSPSend_Batch_Select(CMi401 form) throws TransRuntimeErrorException {
		Mi401Example mi401Example = new Mi401Example();
		mi401Example.setOrderByClause("datemodified desc");
		Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
		mi401Criteria.andSendseqnoEqualTo(form.getSendseqno());
		mi401Criteria.andCenteridEqualTo(form.getCenterid());
		mi401Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi401> list = this.getMi401Dao().selectByExampleWithoutBLOBs(mi401Example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0).getCommsgid();
		}else{
			return null;
		}
	}

//	public String webapi30211_CSP_MB_Send_Batch(SendMBApiCommonForm form,
//			ModelMap modelMap, HttpServletRequest request,
//			HttpServletResponse response) throws TransRuntimeErrorException {
//		String keytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());	
//		
//		int seqno=0;
//		try {
//			seqno = commonUtil.genKey("MI100").intValue();
//		} catch (Exception e) {
//			e.printStackTrace();
//			TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"模板批次号");
//			throw error;
//		}	
//		
//		String filepath = PropertiesReader.getProperty("properties.properties", "mobanpath")+form.getCenterId()+"/"+form.getFileName();//时实推送模板消息模板文件位置,对方需FTP上传至服务器
//		System.out.println("模板批量文件路径为 : "+filepath);
//		File isFile = new File(filepath);
//		if(isFile.exists()){
//			final String filename = filepath;
//			final String mbid = form.getSmssource();
//			final String title = CommonUtil.isEmpty(form.getTitle())?"":form.getTitle();
//			final String detail = CommonUtil.isEmpty(form.getDetail())?"":form.getDetail();
//			final String theme = CommonUtil.isEmpty(form.getTheme())?"":form.getTheme();
//			final String centerId = CommonUtil.isEmpty(form.getCenterId())?"":form.getCenterId();
//			final String chanel = CommonUtil.isEmpty(form.getChanel())?"":form.getChanel();
//			final String num = CommonUtil.isEmpty(form.getNum())?"":form.getNum();
//			final String sendSeqno = CommonUtil.isEmpty(form.getSendSeqno())?"":form.getSendSeqno();
//			final String bindkeytype = keytype;
//			final String centerid = form.getCenterId();
//			final String longinip = form.getLonginip();
//			final int seqno1 = seqno;
//			Thread th = new Thread(new Runnable(){
//				public void run() {
//					batchMBMessage(seqno1,longinip,centerid, bindkeytype,filename,mbid,title,detail,theme,centerId,chanel,num,sendSeqno);
//				}
//			});
//			th.start();
//		}else{
//			TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"文件"+form.getFileName()+"不存在");
//			throw error;
//		}		
//		return Integer.toString(seqno);
//	}


	
//	public void insertTimingMessage(CMi401 mi401) throws Exception{
//		Mi408 mi408 = new Mi408();
//		String timeid = commonUtil.genKey("MI408",20);
//		mi408.setTimeid(timeid);
//		mi408.setPusMessageType(mi401.getPusMessageType());
//		mi408.setTsmesstype(mi401.getTsmsgtype());
//		mi408.setCenterid(mi401.getCenterid());
//		mi408.setDsdate(mi401.getDsdate());
//		mi408.setCommsgid(mi401.getCommsgid());
//		mi408.setStatus(Constants.PUSH_MSG_DEF_STATE);
//		mi408.setValidflag(Constants.IS_VALIDFLAG);
//		mi408.setDatecreated(CommonUtil.getSystemDate());
//		mi408.setDatemodified(CommonUtil.getSystemDate());
//		mi408Dao.insert(mi408);
//	}
	
	public String insertTemplateParam(CMi401 cmi401 , Mi029 mi029 ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.debug("【***推送开始时间6.1***】"+CommonUtil.getSystemDate());
		Mi401 mi401 = null;
		String commsgid = "";
		Mi401Example mi401Example = new Mi401Example();
		Mi401Example.Criteria mi401Criteria = mi401Example.createCriteria();
		mi401Criteria.andSendseqnoEqualTo(cmi401.getSendseqno());
		List<Mi401> list401 = null;
				//mi401Dao.selectByExampleWithoutBLOBs(mi401Example);
		log.debug("【***推送开始时间6.2***】"+CommonUtil.getSystemDate());
		String tempTitle = "模板消息";
		String tempDetail = "模板消息";
		String tempTsmsg = "模板消息";
		if(!CommonUtil.isEmpty(cmi401.getTheme()))
		{
			String tempName = codeListApi001Service.getCodeVal(cmi401.getCenterid(), "message_topic_type." + cmi401.getTheme());
			if(!CommonUtil.isEmpty(tempName))
			{
				tempTitle = tempName;
				tempDetail = tempName;
				tempTsmsg = tempName;
			}
		}
		if(CommonUtil.isEmpty(list401)){
			//String tel = this.getCustsvctel(cmi401.getCenterid());
			commsgid = commonUtil.genKey("MI401",0);
			mi401 = new Mi401();
			mi401.setCommsgid(commsgid);
			//mi401.setParam1(tel);
			mi401.setCenterid(cmi401.getCenterid());
			mi401.setPusMessageType(Constants.PUSH_TYPE_MB);		
			mi401.setTitle(tempTitle);
			mi401.setDetail(tempDetail);
			mi401.setTsmsg(tempTsmsg);
			mi401.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);
			mi401.setTheme(cmi401.getTheme());
			mi401.setApprove(Constants.APPROVE_YES);//模板消息直接发无需审批
			mi401.setTiming(cmi401.getTiming());
			mi401.setDsdate(cmi401.getDsdate());
			mi401.setMsgsource(cmi401.getMsgsource());
			mi401.setStatus(Constants.PUSH_MSG_DEF_STATE);
			mi401.setSendseqno(cmi401.getSendseqno());
			mi401.setValidflag(Constants.IS_VALIDFLAG);
			mi401.setDatecreated(CommonUtil.getSystemDate());
			mi401.setDatemodified(CommonUtil.getSystemDate());
			mi401.setSumcount(1);
			mi401.setSuccessnum(0);
			this.getMi401Dao().insert(mi401);
		}else{
			mi401 = list401.get(0);
			mi401.setSumcount(mi401.getSumcount()+1);
			this.getMi401Dao().updateByPrimaryKeySelective(mi401);
			commsgid = mi401.getCommsgid();
		}
		log.debug("【***推送开始时间6.3***】"+CommonUtil.getSystemDate());
		Mi411Example mi411Example = new Mi411Example();
		mi411Example.setOrderByClause("datemodified desc");
		Mi411Example.Criteria mi411Criteria = mi411Example.createCriteria();
		mi411Criteria.andThemeEqualTo(cmi401.getTheme());
		mi411Criteria.andCenteridEqualTo(cmi401.getCenterid());
		mi411Criteria.andValidflagEqualTo("1");
		List<Mi411> listMi411 = mi411Dao.selectByExample(mi411Example);
		String keyname = "";
		log.debug("【***推送开始时间6.4***】"+CommonUtil.getSystemDate());
		//add by xzw 20180213
//		if(!CommonUtil.isEmpty(listMi411)){
		if(CommonUtil.isEmpty(listMi411)){
			Mi412Example mi412Example = new Mi412Example();
			mi412Example.setOrderByClause("datemodified desc");
			Mi412Example.Criteria mi412Criteria = mi412Example.createCriteria();
//			mi412Criteria.andTemplateidEqualTo(listMi411.get(0).getTemplateid());
			mi412Criteria.andTemplatekeyEqualTo("1");      
			List<Mi412> listMi412 = mi412Dao.selectByExample(mi412Example);
			log.debug("【***推送开始时间6.5***】"+CommonUtil.getSystemDate());
			keyname = listMi412.get(0).getApidata();
			System.out.println("模板消息对应主题主键存放变量名："+keyname);
			String getMethodName = "get" + keyname.substring(0, 1).toUpperCase() + keyname.substring(1); // 获得和属性对应的getXXX()方法的名字
			
		    String value ="";
		    Field field=cmi401.getClass().getDeclaredField(keyname);
			if(field!=null){
				field.setAccessible(true);
				value = String.valueOf(field.get(cmi401));
			}
			
//		    Mi029Example mi029Example = new Mi029Example();
//			mi029Example.setOrderByClause("datemodified desc");
//			Mi029Example.Criteria mi029Criteria = mi029Example.createCriteria();
//			mi029Criteria.andTelEqualTo(value);
//			mi029Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//			@SuppressWarnings("unchecked")
//			List<Mi029> listMi029 = this.getMi029Dao().selectByExample(mi029Example);
//			if(CommonUtil.isEmpty(listMi029)){
//				System.out.println("#######     等推送客户手机号【"+value+"】，未查询相应客户信息");
//				return commsgid;
//			}
			Mi402 mi402 = new Mi402();
			mi402.setMsgid(commonUtil.genKey("MI402",0));
			mi402.setCommsgid(commsgid);
//			mi402.setPersonalid(listMi029.get(0).getPersonalid());
//			mi402.setUsername(listMi029.get(0).getUsername());
			mi402.setPersonalid(mi029.getPersonalid());
			mi402.setUsername(mi029.getUsername());
			mi402.setCenterid(cmi401.getCenterid());
			mi402.setMsgsource(cmi401.getMsgsource());
			mi402.setTitle(tempTitle);
			mi402.setDetail(tempDetail);
			mi402.setPusMessageType(Constants.PUSH_TYPE_MB);
			mi402.setParam1(mi029.getTel());
			mi402.setParam2(cmi401.getParam2());
			mi402.setStatus(Constants.PUSH_MSG_DEF_STATE);
			mi402.setValidflag(Constants.IS_VALIDFLAG);
			mi402.setDatecreated(CommonUtil.getSystemDate());
			mi402.setDatemodified(CommonUtil.getSystemDate());
			mi402.setTsmsg(tempTsmsg);
			mi402.setTsmsgtype(Constants.SEND_MESSAGE_TYPE_TEXT);
			mi402.setTheme(cmi401.getTheme());
			mi402.setSendseqno(cmi401.getSendseqno());
			mi402.setSumcount(1);
			mi402.setSuccessnum(0);
			this.getcMi402Dao().insert(mi402);
			log.debug("【***推送开始时间6.5***】"+CommonUtil.getSystemDate());
			cmi401.setFreeuse2(mi402.getMsgid());
			Mi413 mi413 = new Mi413();
			try{
				mi413.setCenterid(cmi401.getCenterid());
				mi413.setPusMessageType(Constants.PUSH_TYPE_MB);
				mi413.setTheme(cmi401.getTheme());
				mi413.setCommsgid(commsgid);
				mi413.setMsgsource(cmi401.getMsgsource());
				mi413.setSendseqno(cmi401.getSendseqno());
				mi413.setTiming(cmi401.getTiming());
				mi413.setDsdate(cmi401.getDsdate());
				mi413.setChanneltemplate(cmi401.getChannelTemplate());
				mi413.setApidata1(cmi401.getApiData1());
				mi413.setApidata2(cmi401.getApiData2());
				mi413.setApidata3(cmi401.getApiData3());
				mi413.setApidata4(cmi401.getApiData4());
				mi413.setApidata5(cmi401.getApiData5());
				mi413.setApidata6(cmi401.getApiData6());
				mi413.setApidata7(cmi401.getApiData7());
				mi413.setApidata8(cmi401.getApiData8());
				mi413.setApidata9(cmi401.getApiData9());
				mi413.setApidata10(cmi401.getApiData10());
				mi413.setApidata11(cmi401.getApiData11());
				mi413.setApidata12(cmi401.getApiData12());
				mi413.setApidata13(cmi401.getApiData13());
				mi413.setApidata14(cmi401.getApiData14());
				mi413.setApidata15(cmi401.getApiData15());
				mi413.setApidata16(cmi401.getApiData16());
				mi413.setApidata17(cmi401.getApiData17());
				mi413.setApidata18(cmi401.getApiData18());
				mi413.setApidata19(cmi401.getApiData19());
				mi413.setApidata20(cmi401.getApiData20());
				mi413.setStatus("0");
				mi413.setValidflag("1");
				mi413.setMsgid(mi402.getMsgid());
				mi413.setDatecreated(CommonUtil.getSystemDate());
				mi413.setDatemodified(CommonUtil.getSystemDate());
				String tid = commonUtil.genKey("MI413",0);
				mi413.setApitemplateid(tid);
				mi413.setPersonalid(mi029.getPersonalid());
				mi413.setFreeuse1(cmi401.getFilename());
				mi413Dao.insert(mi413);
				log.debug("【***推送开始时间6.6***】"+CommonUtil.getSystemDate());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//定时处理
		System.out.println("流水号："+mi401.getSendseqno()+"模板消息判断是否定时，定时标记：【"+cmi401.getTiming()+"】，定时时间：【"+"】");
		if(Constants.TINING_YES.equals(cmi401.getTiming())){
			log.debug("【***推送开始时间6.7***】"+CommonUtil.getSystemDate());
			Mi408Example mi408Example = new Mi408Example();
			Mi408Example.Criteria mi408Criteria = mi408Example.createCriteria();
			mi408Criteria.andCommsgidEqualTo(commsgid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi408> listMi408 = mi408Dao.selectByExample(mi408Example);
			log.debug("【***推送开始时间6.8***】"+CommonUtil.getSystemDate());
			if(CommonUtil.isEmpty(listMi408)){
				Mi408 mi408 = new Mi408();
				String timeid = commonUtil.genKey("MI408",20);
				mi408.setTimeid(timeid);
				mi408.setPusMessageType(mi401.getPusMessageType());
				mi408.setTsmesstype(mi401.getTsmsgtype());
				mi408.setMsgsource(mi401.getMsgsource().trim());
				mi408.setCenterid(mi401.getCenterid());
				mi408.setDsdate(mi401.getDsdate());
				mi408.setCommsgid(mi401.getCommsgid());
				mi408.setStatus(Constants.PUSH_MSG_DEF_STATE);
				mi408.setValidflag(Constants.IS_VALIDFLAG);
				mi408.setDatecreated(CommonUtil.getSystemDate());
				mi408.setDatemodified(CommonUtil.getSystemDate());
				mi408.setFreeuse1(mi401.getSendseqno());
				mi408Dao.insert(mi408);
				log.debug("【***推送开始时间6.9***】"+CommonUtil.getSystemDate());
			}
			return commsgid;
		}else{
			//入403表
			System.out.println("#######[开始调用推送]#######");
			log.debug("【***推送开始时间6.10***】"+CommonUtil.getSystemDate());
			cmi401.setCommsgid(mi401.getCommsgid());
			cmi401.setPusMessageType(mi401.getPusMessageType());
			insertSendTable(cmi401 ,request ,response);
			log.debug("【***推送开始时间6.11***】"+CommonUtil.getSystemDate());
		}
		return commsgid;
		
		
		
		
		
//		SELECT
//		  timestampdiff (256, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔年",
//		  timestampdiff (128, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔季度",
//		  timestampdiff (64, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔月",
//		  timestampdiff (32, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔周",
//		  timestampdiff (16, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔日",
//		  timestampdiff (8, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔时",
//		  timestampdiff (4, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔分",
//		  timestampdiff (2, char(timestamp('2013-12-30 20:30:30') - timestamp('2001-09-26 15:24:23'))) AS "间隔秒"
//		FROM SYSIBM.SYSDUMMY1;
		
		
	}
	
	public void insertSendTable(CMi401 cmi401 ,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.debug("【***推送开始时间6.12_insertSendTable***】"+CommonUtil.getSystemDate());
		List<Mi040> list040 = getChannelPid(cmi401.getCenterid() ,"70");
		if(Constants.PUSH_TYPE_MB.equals(cmi401.getPusMessageType())){//MB			
			Mi402Example mi402Example = new Mi402Example();
			Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
			mi402Criteria.andCommsgidEqualTo(cmi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo("0");
			System.out.println("#######[开始调用推送]####### Commsgid"+cmi401.getCommsgid());
			List<Mi402> listmi402 = mi402Dao.selectByExampleWithoutBLOBs(mi402Example);
			log.debug("【***推送开始时间6.13_insertSendTable***】"+CommonUtil.getSystemDate());
			if(CommonUtil.isEmpty(listmi402)){
				TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
				throw error;
			}
			int count = 0;
			//##################################################1 start
			log.debug("【***推送开始时间6.14_insertSendTable***】"+CommonUtil.getSystemDate());
			for(int i=0;i<listmi402.size();i++){
				Mi402 mi402 = listmi402.get(i);
				
				Mi031Example mi031Example = new Mi031Example();
				Mi031Example.Criteria mi031Criteria = mi031Example.createCriteria();
				mi031Criteria.andPersonalidEqualTo(mi402.getPersonalid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				//.andSendmessageEqualTo(Constants.IS_VALIDFLAG);新加入模板开关，再判断是否强推
				@SuppressWarnings("unchecked")
				List<Mi031> listMi031 = this.getMi031Dao().selectByExample(mi031Example);
				log.debug("【***推送开始时间6.15_insertSendTable***】"+CommonUtil.getSystemDate());
//				for(int aa=0;aa<listMi031.size();aa++){
//					System.out.println("#######[开始调用推送]####### $$$$$$  "+listMi031.get(aa).getChannel());
//					System.out.println("#######[开始调用推送]####### $$$$$$  "+listMi031.get(aa).getUserid());
//					System.out.println("#######[开始调用推送]####### $$$$$$  "+listMi031.get(aa).getPid());
//					System.out.println("#######[开始调用推送]####### $$$$$$  "+listMi031.get(aa).getPersonalid());
//					System.out.println("#######[开始调用推送]####### $$$$$$  ===================================");
//				}
				if(CommonUtil.isEmpty(listMi031)){
					continue;
				}
				Mi413Example mi413Example = new Mi413Example();
				Mi413Example.Criteria mi413Criteria = mi413Example.createCriteria();
				mi413Criteria.andPersonalidEqualTo(mi402.getPersonalid())
				.andCommsgidEqualTo(mi402.getCommsgid())
				.andMsgidEqualTo(mi402.getMsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi413> listmi413 = mi413Dao.selectByExample(mi413Example);
				log.debug("【***推送开始时间6.16_insertSendTable***】"+CommonUtil.getSystemDate());
				if(CommonUtil.isEmpty(listmi413)){
					continue;
				}
				Mi413 mi413 = listmi413.get(0);
				
				//##################################################2 start
				
				for(int j=0;j<listMi031.size();j++){
					Mi031 mi031 = listMi031.get(j);
					Mi411Example mi411Example = new Mi411Example();
					Mi411Example.Criteria mi411Criteria = mi411Example.createCriteria();
					mi411Criteria.andChannelEqualTo(mi031.getChannel())
					.andThemeEqualTo(mi413.getTheme()).andCenteridEqualTo(mi031.getCenterid())
					.andValidflagEqualTo(Constants.IS_VALIDFLAG).andFreeuse2EqualTo("1");//freeuse2新加入开关功能
					List<Mi411> listMi411 = mi411Dao.selectByExample(mi411Example);
					System.out.println("#######[开始调用推送]####### 8 mi413.getTheme():"+mi413.getTheme()+"    ,mi031.getCenterid():"+mi031.getCenterid()+"    ,"+mi031.getChannel()+"   .MI411:"+listMi411.size());
					if(CommonUtil.isEmpty(listMi411)){
						continue;
					}else{
						//##################################################3 start
						//判断MI031用户接收标记，与强推标记对比
						if(mi031.getSendmessage().equals("0")){
							Mi122Example mi122Example = new Mi122Example();
							Mi122Example.Criteria mi122Criteria = mi122Example.createCriteria();
							mi122Criteria.andCenteridEqualTo(mi031.getCenterid());
							mi122Criteria.andMessageTopicTypeEqualTo(mi413.getTheme());
							mi122Criteria.andMustsendEqualTo("0");
							List<Mi122> list122 = mi122Dao.selectByExample(mi122Example);
							if(!CommonUtil.isEmpty(listmi413)){
								continue;
							}
						}
						
						for(int k=0;k<listMi411.size();k++){
							Mi411 mi411 = listMi411.get(k);
							Mi412Example mi412Example = new Mi412Example();
							mi412Example.setOrderByClause("orderid");
							Mi412Example.Criteria mi412Criteria = mi412Example.createCriteria();
							mi412Criteria.andTemplateidEqualTo(mi411.getTemplateid())
							.andValidflagEqualTo(Constants.IS_VALIDFLAG);
							log.debug("【***推送开始时间6.17_insertSendTable***】"+CommonUtil.getSystemDate());
							List<Mi412> listMi412 = mi412Dao.selectByExample(mi412Example);
							
							//##################################################4 start
							StringBuffer sendmessage = new StringBuffer();
							for(int h=0;h<listMi412.size();h++){
								Mi412 mi412 = listMi412.get(h);
								
								String value ="";
							    Field field=mi413.getClass().getDeclaredField(mi412.getApidata().toLowerCase());
								if(field!=null){
									field.setAccessible(true);
									value = String.valueOf(field.get(mi413));
								}

								if(sendmessage.length()==0){
									sendmessage.append(value);
								}else{
									sendmessage.append(";"+value);
								}
							}
							System.out.println("###########    [待推送信息] = "+sendmessage.toString()+" ，应用ID："+mi031.getPid()+" ，渠道："+mi031.getChannel()+"   ,用户ID："+mi031.getUserid());
							log.debug("【***推送开始时间6.18_insertSendTable***】"+CommonUtil.getSystemDate());
							//##################################################4 end
							Mi403 mi403 = new Mi403();
							mi403.setPushid(commonUtil.genKey("MI403",0));
							mi403.setMsgid(mi402.getMsgid());
							mi403.setCommsgid(mi402.getCommsgid());
							mi403.setPersonalid(mi402.getPersonalid());
							mi403.setUsername(mi402.getUsername());
							mi403.setUserid(mi031.getUserid());
							mi403.setCenterid(mi402.getCenterid());
							mi403.setTitle(mi402.getTitle());
							mi403.setDetail(mi402.getDetail());
							mi403.setPid(mi031.getPid());
							mi403.setChanneltemplate(mi411.getChanneltemplate());
							mi403.setPusMessageType(mi402.getPusMessageType());
							mi403.setTheme(mi402.getTheme().trim());
							mi403.setTsmsgtype(mi402.getTsmsgtype().trim());
							mi403.setTsmsg(sendmessage.toString());
							mi403.setTiming(mi402.getTiming());
							mi403.setDsdate(mi402.getDsdate());
							mi403.setMsgsource(mi402.getMsgsource());
							mi403.setSendseqno(mi402.getSendseqno());
							mi403.setLoginid(mi402.getLoginid());
							mi403.setParam1(mi402.getParam1());
							mi403.setStatus(Constants.PUSH_MSG_DEF_STATE);
							mi403.setValidflag(Constants.IS_VALIDFLAG);
							mi403.setDatecreated(CommonUtil.getSystemDate());
							mi403.setDatemodified(CommonUtil.getSystemDate());
							System.out.println("#######[开始调用推送]####### 11");
							mi403Dao.insert(mi403);
							log.debug("【***推送开始时间6.19_insertSendTable***】"+CommonUtil.getSystemDate());
							//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
							int sendNum = 0;
							//if("70000133".equals(mi403.getPid())){//SMS
							if(list040.get(0).getPid().equals(mi403.getPid())){
								try{
									sendNum = sendSms(mi403 ,request ,response);
								}catch (Exception e){
									e.printStackTrace();
								}
							}else if(mi403.getPid().indexOf("1000")==0){//APP//"10000136".equals(mi403.getChanneltemplate())
								try{
									sendNum = sendApp(mi403 ,request ,response);
								}catch(Exception e){
									e.printStackTrace();
								}
							}else if(mi403.getPid().indexOf("2000")==0){//"20000128".equals(mi403.getPid())
								try{
									sendNum = sendWeixin(mi403 ,request ,response);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							
							//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
							
							log.debug("【***推送开始时间6.20_insertSendTable***】"+CommonUtil.getSystemDate());
							
							//##########处理历史信息表 start
							if(sendNum>0){
								mi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);
								mi403Dao.updateByPrimaryKeySelective(mi403);
								Mi414 mi414 = new Mi414();
								mi414.setId(commonUtil.genKey("MI414",0));
								mi414.setApitemplateid(mi413.getApitemplateid());
								mi414.setPersonalid(mi413.getPersonalid());
								mi414.setCenterid(mi402.getCenterid());
								mi414.setPusMessageType(Constants.PUSH_TYPE_MB);
								mi414.setTheme(mi402.getTheme());
								mi414.setCommsgid(mi402.getCommsgid());
								mi414.setMsgsource(mi402.getMsgsource());
								mi414.setSendseqno(mi402.getSendseqno());
								mi414.setTiming(mi413.getTiming());
								mi414.setDsdate(mi413.getDsdate());
								mi414.setMsgid(mi402.getMsgid());
								mi414.setChanneltemplate(mi411.getChanneltemplate());
								mi414.setApidata1(mi413.getApidata1());
								mi414.setApidata2(mi413.getApidata2());
								mi414.setApidata3(mi413.getApidata3());
								mi414.setApidata4(mi413.getApidata4());
								mi414.setApidata5(mi413.getApidata5());
								mi414.setApidata6(mi413.getApidata6());
								mi414.setApidata7(mi413.getApidata7());
								mi414.setApidata8(mi413.getApidata8());
								mi414.setApidata9(mi413.getApidata9());
								mi414.setApidata10(mi413.getApidata10());
								mi414.setApidata11(mi413.getApidata11());
								mi414.setApidata12(mi413.getApidata12());
								mi414.setApidata13(mi413.getApidata13());
								mi414.setApidata14(mi413.getApidata14());
								mi414.setApidata15(mi413.getApidata15());
								mi414.setApidata16(mi413.getApidata16());
								mi414.setApidata17(mi413.getApidata17());
								mi414.setApidata18(mi413.getApidata18());
								mi414.setApidata19(mi413.getApidata19());
								mi414.setApidata20(mi413.getApidata20());
								mi414.setStatus("1");
								mi414.setValidflag("1");
								mi414.setDatecreated(mi413.getDatecreated());
								mi414.setDatemodified(CommonUtil.getSystemDate());
								mi414.setFreeuse1(mi413.getFreeuse1());
								mi414Dao.insert(mi414);
								log.debug("【***推送开始时间6.21_insertSendTable***】"+CommonUtil.getSystemDate());
								mi413Dao.deleteByPrimaryKey(mi413.getApitemplateid());
								Mi403 remi403 = new Mi403();
								remi403.setPushid(mi403.getPushid());
								remi403.setDatemodified(CommonUtil.getSystemDate());
								remi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);
								mi403Dao.updateByPrimaryKeySelective(remi403);
								log.debug("【***推送开始时间6.22_insertSendTable***】"+CommonUtil.getSystemDate());
								count++;
							}else{
								mi403.setStatus(Constants.PUSH_MSG_ERROR_PUSH);
								mi403Dao.updateByPrimaryKeySelective(mi403);
							}
							
							//##########处理历史信息表end
							
						}
						
					}
					//##################################################3 end
				}
				//##################################################2 end
				if(count>0){
					mi402.setStatus(Constants.PUSH_MSG_ARD_PUSH);
					mi402.setSuccessnum(count);
					mi402Dao.updateByPrimaryKeySelective(mi402);					
				}
				
				Mi401 mi401 = new Mi401();
				mi401.setCommsgid(cmi401.getCommsgid());
				mi401.setSuccessnum(count);
				mi401.setDatemodified(CommonUtil.getSystemDate());
				if(count>0){
					mi401.setStatus(Constants.PUSH_MSG_ARD_PUSH);
				}
				mi401Dao.updateByPrimaryKeySelective(mi401);
				
			}
			
			//##################################################1 end
			
		}else if(Constants.PUSH_TYPE_BP.equals(cmi401.getPusMessageType())){
			System.out.println("#######[自定义消息推送1]####### Commsgid"+cmi401.getCommsgid());
			log.debug("【***推送开始时间7.01_BP***】"+CommonUtil.getSystemDate());
			Mi402Example mi402Example = new Mi402Example();
			Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
			mi402Criteria.andCommsgidEqualTo(cmi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi402> listmi402 = mi402Dao.selectByExampleWithBLOBs(mi402Example);
			if(CommonUtil.isEmpty(listmi402)){
				TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
				throw error;
			}
			
			Mi403Example mi403Example = new Mi403Example();
			Mi403Example.Criteria mi403Criteria = mi403Example.createCriteria();
			mi403Criteria.andCommsgidEqualTo(cmi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi403Dao.deleteByExample(mi403Example);
			
			System.out.println("#######[自定义消息推送2]#######"+listmi402.size());
			log.debug("【***推送开始时间7.01_BP***】"+CommonUtil.getSystemDate());
			int sum = 0;
			for(int ii=0;ii<listmi402.size();ii++){
				List<String> list031 = new ArrayList<String>(3);
				list031.add("10");
				list031.add("20");
				list031.add("70");
				
				Mi402 mi402 = listmi402.get(ii);
				Mi031Example mi031Example = new Mi031Example();
				Mi031Example.Criteria mi031Criteria = mi031Example.createCriteria();
				mi031Criteria.andPersonalidEqualTo(mi402.getPersonalid()).andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andChannelIn(list031);
				@SuppressWarnings("unchecked")
				List<Mi031> listMi031 = this.getMi031Dao().selectByExample(mi031Example);
				if(CommonUtil.isEmpty(listMi031)){
					System.out.println("#######[开始调用定制消息推送]####### stup 2");
					if(Constants.DATA_SOURCE_YBMAP.equals(mi402.getMsgsource())){
						System.out.println("#######[开始调用定制消息推送]####### stup 3");
						//渠道消息发送，不检查人信息，直接发送
						Mi031 mi031 = new Mi031();
						mi031.setPid(mi402.getPid());
						mi031.setPersonalid(mi402.getPersonalid());
						listMi031.add(mi031);
					}else{
						continue;
					}
				}
				int count=0;
				System.out.println("#######[开始调用定制消息推送]####### stup 4");
				for(int i=0;i<listMi031.size();i++){
					System.out.println("#######[开始调用定制消息推送]####### stup 5");
					Mi031 mi031 = listMi031.get(i);
					Mi403 mi403 = new Mi403();
					mi403.setPushid(commonUtil.genKey("MI403",0));
					mi403.setMsgid(mi402.getMsgid());
					mi403.setCommsgid(mi402.getCommsgid());
					mi403.setPersonalid(mi402.getPersonalid());
					mi403.setUserid(listMi031.get(i).getUserid());
					mi403.setUsername(mi402.getUsername());
					mi403.setCenterid(mi402.getCenterid());
					mi403.setTitle(mi402.getTitle());
					mi403.setDetail(mi402.getDetail());
					mi403.setPid(mi031.getPid());
					mi403.setPusMessageType(mi402.getPusMessageType());
					mi403.setTheme(mi402.getTheme());
					mi403.setTsmsgtype(mi402.getTsmsgtype());
					mi403.setTsmsg(mi402.getTsmsg());
					mi403.setTiming(mi402.getTiming());
					mi403.setDsdate(mi402.getDsdate());
					mi403.setMsgsource(mi402.getMsgsource());
					mi403.setSendseqno(mi402.getSendseqno());
					mi403.setLoginid(mi402.getLoginid());
					mi403.setParam1(mi402.getParam1());
					mi403.setStatus(Constants.PUSH_MSG_DEF_STATE);
					mi403.setValidflag(Constants.IS_VALIDFLAG);
					mi403.setDatecreated(CommonUtil.getSystemDate());
					mi403.setDatemodified(CommonUtil.getSystemDate());
					mi403.setFreeuse2(mi402.getFreeuse2());
					
					int num = 0;
					System.out.println("#######[开始调用定制消息推送]####### stup 6");
					//if("70000133".equals(mi403.getPid())){//SMS
					if(list040.get(0).getPid().equals(mi403.getPid())){
						System.out.println("#######[开始调用定制消息推送]####### stup 7");
						try{
							num = sendSms(mi403 ,request ,response);
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}else if(mi403.getPid().indexOf("1000")==0){//APP
						try{
							num = sendApp(mi403 ,request ,response);
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}else if(mi403.getPid().indexOf("2000")==0){
						try{
							num = sendWeixin(mi403 ,request ,response);
						}catch(Exception e){
							e.printStackTrace();
						}						
					}
					
					if(num>0){
						count++;
						mi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);
						mi403Dao.insert(mi403);						
//						mi403Dao.updateByPrimaryKeySelective(mi403);
					}else{
						mi403.setStatus(Constants.PUSH_MSG_ERROR_PUSH);
						mi403Dao.insert(mi403);
					}
					
				}
				mi402.setSuccessnum(count);
				if(count>0){
					mi402.setStatus(Constants.PUSH_MSG_ARD_PUSH);
					mi402Dao.updateByPrimaryKeySelective(mi402);
				}
				sum = sum + count;
			}
			Mi401 mi401 = new Mi401();
			mi401.setCommsgid(cmi401.getCommsgid());
			mi401.setSumcount(sum);
			mi401.setSuccessnum(sum);
			if(sum>0){
				mi401.setStatus(Constants.PUSH_MSG_ARD_PUSH);
				mi401Dao.updateByPrimaryKeySelective(mi401);
			}
		}else if(Constants.PUSH_TYPE_QT.equals(cmi401.getPusMessageType())){
			Mi402Example mi402Example = new Mi402Example();
			Mi402Example.Criteria mi402Criteria = mi402Example.createCriteria();
			mi402Criteria.andCommsgidEqualTo(cmi401.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi402> listmi402 = mi402Dao.selectByExampleWithoutBLOBs(mi402Example);
			if(CommonUtil.isEmpty(listmi402)){
				TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
				throw error;
			}
			for(int ii=0;ii<listmi402.size();ii++){
				Mi402 mi402 = listmi402.get(ii);
				Mi403 mi403 = new Mi403();
				mi403.setPushid(commonUtil.genKey("MI403",0));
				mi403.setMsgid(mi402.getMsgid());
				mi403.setCommsgid(mi402.getCommsgid());
				mi403.setPersonalid(mi402.getPersonalid());
//				mi403.setUserid();
//				mi403.setUsername(mi402.getUsername());
				mi403.setCenterid(mi402.getCenterid());
				mi403.setTitle(mi402.getTitle());
				mi403.setDetail(mi402.getDetail());
				mi403.setPid(mi402.getPid());
				mi403.setPusMessageType(mi402.getPusMessageType());
				mi403.setTheme(mi402.getTheme());
				mi403.setTsmsgtype(mi402.getTsmsgtype());
				mi403.setTsmsg(mi402.getTsmsg());
				mi403.setTiming(mi402.getTiming());
				mi403.setDsdate(mi402.getDsdate());
				mi403.setMsgsource(mi402.getMsgsource());
				mi403.setSendseqno(mi402.getSendseqno());
				mi403.setLoginid(mi402.getLoginid());
				mi403.setParam1(mi402.getParam1());
				mi403.setStatus(Constants.PUSH_MSG_ARD_PUSH);
				mi403.setValidflag(Constants.IS_VALIDFLAG);
				mi403.setDatecreated(CommonUtil.getSystemDate());
				mi403.setDatemodified(CommonUtil.getSystemDate());
				mi403Dao.insert(mi403);
			}
		}
	}
	
	public int sendApp(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("[调用APP信息推送开始，类型"+mi403.getPusMessageType()+"]");
		String appurl = PropertiesReader.getHeartbeatURL(mi403.getCenterid(), mi403.getPid().substring(0, 2)).trim();
		if(Constants.PUSH_TYPE_QT.equals(mi403.getPusMessageType())){
//			./pushpublicmsg.json
//			private String centerid = "";
//			private String commsgid = "";
//			private String title = "";
//			private String detail = "";//纯文本
//			private String msgtype = "";//内容类型
//			private String tsmsg = "";//消息富文本内容
			//String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appurl").trim()+"/pushpublicmsg.json";
			//不再使用属性文件的配置，改为从心跳地址获取url
			String url = appurl+"/pushpublicmsg.json";
			HashMap map = new HashMap();
			map.put("centerid", mi403.getCenterid());
			map.put("commsgid", mi403.getCommsgid());
			map.put("title", mi403.getTitle());
			map.put("detail", mi403.getDetail());
			map.put("msgtype", mi403.getTsmsgtype());
			map.put("tsmsg", mi403.getTsmsg());
			String value = JsonUtil.getGson().toJson(map);
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			//String msg = "{\"recode\":\"000000\"}";
			HashMap repmap = JsonUtil.getGson().fromJson(msg, HashMap.class);
			System.out.println();
			if("000000".equals(repmap.get("recode"))){
				return 1;
			}else{
				//throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
				return 0;
			}
			
			
		}else if(Constants.PUSH_TYPE_BP.equals(mi403.getPusMessageType())){
//			个人（单条）短消息推送
//			pushprivatesinglemsg.json
//			private String centerid = "";
//			private String title = "";
//			private String detail = "";
//			private String msgtype = "";
//			private String userid = "";
//			private String tsmsg = "";//消息富文本内容
			//String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appurl").trim()+"/pushprivatesinglemsg.json";
			//不再使用属性文件的配置，改为从心跳地址获取url
			String url = appurl+"/pushprivatesinglemsg.json";
			HashMap map = new HashMap();
			map.put("centerid", mi403.getCenterid());
			map.put("commsgid", mi403.getCommsgid());
			map.put("title", mi403.getTitle());
			map.put("detail", mi403.getDetail());
			map.put("msgtype", mi403.getTsmsgtype());
			map.put("userid", mi403.getUserid());
			map.put("tsmsg", mi403.getTsmsg());
			String value = JsonUtil.getGson().toJson(map);
			System.out.println("【APP个人报盘短信推送地址】---"+url);
			System.out.println("【APP个人报盘短信推送参数】---"+value);
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			HashMap repmap = JsonUtil.getGson().fromJson(msg, HashMap.class);
			System.out.println("【APP个人报盘短信推送反回】---"+msg);
			if("000000".equals(repmap.get("recode"))){
				return 1;
			}else{
				//throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
				return 0;
			}
			
		}else if(Constants.PUSH_TYPE_MB.equals(mi403.getPusMessageType())){
			
//			pushPrivateSingleTempMsg.json
//
//			private String centerid = "";
//			private String title = "";
//			private String detail = "";
//			private String msgtype = "";
//			private String userid = "";
//			private String templateId = "";
			
			//String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appurl").trim()+"/pushPrivateSingleTempMsg.json";
//			String url = appUrl+"/pushPrivateSingleTempMsg.json";
			//不再使用属性文件的配置，改为从心跳地址获取url
			String url = appurl+"/pushPrivateSingleTempMsg.json";
			HashMap map = new HashMap();
			map.put("centerid", mi403.getCenterid());
			map.put("commsgid", mi403.getCommsgid());
			map.put("title", mi403.getTitle());
			map.put("detail", mi403.getTsmsg());
			map.put("msgtype", mi403.getTsmsgtype());
			map.put("userid", mi403.getUserid());
			map.put("templateId", mi403.getChanneltemplate());
//			map.put("detail", mi403.getTsmsg());
			String value = JsonUtil.getGson().toJson(map);
			System.out.println("【调用APP模板推送，上传参数为：  "+value+" 】");
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			System.out.println("【调用APP模板推送，下传参数为： "+msg+" 】");
			HashMap repmap = JsonUtil.getGson().fromJson(msg, HashMap.class);
			
			if("000000".equals(repmap.get("recode"))){
				return 1;
			}else{
				//throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前要发送短息发送失败 "+map.get("errmsg")==null?"":(String)map.get("errmsg"));
				return 0;
			}
		}
		return 0;
	}
	
	public int sendWeixin(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception{
		String weixinurl = PropertiesReader.getHeartbeatURL(mi403.getCenterid(), mi403.getPid().substring(0, 2)).trim();
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		if(Constants.PUSH_TYPE_QT.equals(mi403.getPusMessageType())){
			HashMap mapreq = new HashMap();
			mapreq.put("centerid", mi403.getCenterid());
			if(Constants.SEND_MESSAGE_TYPE_TEXT.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "text");
				mapreq.put("file_suffix", "");
				mapreq.put("media_id", "");
				mapreq.put("imgurl", "");
				mapreq.put("articles", "");
				mapreq.put("pushcontent", mi403.getTitle());
				mapreq.put("content", mi403.getTsmsg());
				
			}else if(Constants.SEND_MESSAGE_TYPE_IMAGE.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "image");
				mapreq.put("file_suffix", "jpg");
				mapreq.put("media_id", "");
//				List<String> listImgUrl = new ArrayList<String>();
//				if (!CommonUtil.isEmpty(mi403.getParam2())) {
//					String[] pImgs = mi403.getParam2().split(",");
//					for (int j = 0; j < pImgs.length; j++) {
//						String img = pImgs[j];
//				String path  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
				String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
						"push_msg_img", mi403.getCenterid() + "/"
								+ mi403.getParam2(), true);
//						listImgUrl.add(imgUrl);
//					}
//				}
				mapreq.put("imgurl", imgUrl);
				mapreq.put("articles", "");
				mapreq.put("pushcontent", mi403.getTitle());
				
			}else if(Constants.SEND_MESSAGE_TYPE_RICHTEXT.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "mpnews");
				mapreq.put("file_suffix", "");
				mapreq.put("media_id", "");
				mapreq.put("imgurl", "");
				
				String ntranet_url  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
				List<HashMap> mess = new ArrayList();
				HashMap messMap = new HashMap();
				String[] pImgs = mi403.getParam2().split(",");
				String img = pImgs[0];
				String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
						"push_msg_img", mi403.getCenterid() + "/"
								+ img, true);
				Mi002 mi002 = new Mi002();
				Mi002Example mi002Example = new Mi002Example();
				Mi002Example.Criteria mi002C = mi002Example.createCriteria();
				mi002C.andLoginidEqualTo(mi403.getLoginid());
				List<Mi002> listMi002 = mi002Dao.selectByExample(mi002Example);
				messMap.put("thumb_media_id", imgUrl);
				messMap.put("author", listMi002.get(0).getOpername());
				messMap.put("title", mi403.getTitle());
				messMap.put("content_source_url", "");
				messMap.put("content", mi403.getTsmsg().replaceAll(ntranet_url, YBMAPZH_URL));
				messMap.put("digest", "");
				messMap.put("show_cover_pic", "1");
				Mi404Example mi404Example = new Mi404Example();
				Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
				mi404Criteria.andCommsgidEqualTo(mi403.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi404> listmi404 = mi404Dao.selectByExampleWithBLOBs(mi404Example);
				if(CommonUtil.isEmpty(listmi404)){
					TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
					throw error;
				}
				messMap.put("need_open_comment", String.valueOf(listmi404.get(0).getFreeuse4()));
				messMap.put("only_fans_can_comment", listmi404.get(0).getFreeuse3());
				mess.add(messMap);
				
				mapreq.put("articles", mess);//封装富文本多条信息
				mapreq.put("pushcontent", "");
			}else if(Constants.SEND_MESSAGE_TYPE_RICHTEXT_MORE.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "mpnews");
				mapreq.put("file_suffix", "");
				mapreq.put("media_id", "");
				mapreq.put("imgurl", "");
//				
				Mi404Example mi404Example = new Mi404Example();
				Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
				mi404Criteria.andCommsgidEqualTo(mi403.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi404> listmi404 = mi404Dao.selectByExampleWithBLOBs(mi404Example);
				if(CommonUtil.isEmpty(listmi404)){
					TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
					throw error;
				}
				String ntranet_url  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
				List<HashMap> mess = new ArrayList();
				for(int ii=0;ii<listmi404.size();ii++){
					Mi404 mi404 = listmi404.get(ii);
					String[] pImgs = mi404.getParam2().split(",");
					String img = pImgs[0];
					String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
							"push_msg_img", mi403.getCenterid() + "/"
									+ img, true);
					Mi002 mi002 = new Mi002();
					Mi002Example mi002Example = new Mi002Example();
					Mi002Example.Criteria mi002C = mi002Example.createCriteria();
					mi002C.andLoginidEqualTo(mi404.getLoginid());
					List<Mi002> listMi002 = mi002Dao.selectByExample(mi002Example);
					HashMap messMap = new HashMap();
					messMap.put("thumb_media_id", imgUrl);
					messMap.put("author", listMi002.get(0).getOpername());
					messMap.put("title", mi404.getTitle());
					messMap.put("content_source_url", "");
					messMap.put("content", mi404.getTsmsg().replaceAll(ntranet_url, YBMAPZH_URL));
					messMap.put("digest", "");
					messMap.put("show_cover_pic", "1");
					messMap.put("need_open_comment", String.valueOf(mi404.getFreeuse4()));
					messMap.put("only_fans_can_comment", mi404.getFreeuse3());
					mess.add(messMap);
				}
				
				mapreq.put("articles", mess);//封装富文本多条信息
				mapreq.put("pushcontent", "");
			}else if(Constants.SEND_MESSAGE_TYPE_AUDIO.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "voice");
				mapreq.put("file_suffix", mi403.getParam2().substring(mi403.getParam2().indexOf(".")+1));
				/*List<String> listAudio = new ArrayList<String>();
				if (!CommonUtil.isEmpty(mi403.getParam2())) {
					String[] pImgs = mi403.getParam2().split(",");
					for (int j = 0; j < pImgs.length; j++) {
						String img = pImgs[j];
						String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
								"push_msg_img", mi403.getCenterid() + "/"
										+ img, true);
						listAudio.add(imgUrl);
					}
				}*/
				String media_id = "";
				if (!CommonUtil.isEmpty(mi403.getParam2())) {
					media_id = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
							"push_msg_img", mi403.getCenterid() + "/"
									+ mi403.getParam2(), true);
				}
				mapreq.put("media_id", media_id);
				mapreq.put("imgurl", "");
				mapreq.put("articles", "");
				mapreq.put("pushcontent", "");
			}else if(Constants.SEND_MESSAGE_TYPE_VIDEO.equals(mi403.getTsmsgtype())){
				mapreq.put("sendType", "video");
				mapreq.put("file_suffix", mi403.getParam2().substring(mi403.getParam2().indexOf(".")+1));
				/*List<String> listVideo= new ArrayList<String>();
				if (!CommonUtil.isEmpty(mi403.getParam2())) {
					String[] pImgs = mi403.getParam2().split(",");
					for (int j = 0; j < pImgs.length; j++) {
						String img = pImgs[j];
						String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
								"push_msg_img", mi403.getCenterid() + "/"
										+ img, true);
						listVideo.add(imgUrl);
					}
				}*/
				String media_id = "";
				if (!CommonUtil.isEmpty(mi403.getParam2())) {
					media_id = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrlNew(
							"downloadmedia.file", "push_msg_img",
							mi403.getCenterid() + "/" + mi403.getParam2(), true);
				}
				mapreq.put("media_id", media_id);
				mapreq.put("imgurl", "");
				mapreq.put("articles", "");
				mapreq.put("pushcontent", "");
				mapreq.put("title", mi403.getTitle());
				mapreq.put("description", mi403.getDetail());
			}
			
			
//			rs.setTsmsg(mi404.getMstsmess().replaceAll("src=\"/YBMAPZH/", "src=\""+path+"/YBMAPZH/"));
			/*String url = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/sendall";*/
			String url = weixinurl+"/sendall";
			String value = JsonUtil.getGson().toJson(mapreq);
			System.out.println("微信群推JSON："+value);
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			//String msg = "{\"errcode\":\"000000\",\"msg_data_id\":\"123456\",\"msg_id\":\"111111\"}";
			System.out.println("微信群推返回JSON："+msg);
			HashMap remap = JsonUtil.getGson().fromJson(msg, HashMap.class);
			if("000000".equals((String)remap.get("errcode"))){
				//if(Constants.SEND_MESSAGE_TYPE_RICHTEXT_MORE.equals(mi403.getTsmsgtype())){
					Logger log = LoggerUtil.getLogger();	
					log.info("多图文返回更新mi404 msg_data_id");
					Mi404Example mi404Example = new Mi404Example();
					Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
					mi404Criteria.andCommsgidEqualTo(mi403.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
					List<Mi404> listmi404 = mi404Dao.selectByExampleWithoutBLOBs(mi404Example);
					for(int i=0; i<listmi404.size(); i++){
						Mi404 mi404 = listmi404.get(i);
						mi404.setFreeuse1(String.valueOf(remap.get("msg_data_id")));
						mi404.setFreeuse2(String.valueOf(remap.get("msg_id")));
						mi404.setStatus(Constants.PUSH_MSG_ARD_PUSH);
						mi404Dao.updateByPrimaryKeyWithoutBLOBs(mi404);
					}
				//}
				return 1;
			}else{
				return 0;
			}
		}else if(Constants.PUSH_TYPE_BP.equals(mi403.getPusMessageType())){
			/*String url = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/sendpersonalnew";*/
			String url = weixinurl+"/sendpersonalnew";
//			StringBuffer value = new StringBuffer("{\"regionId\":\""+mi403.getCenterid()+"\","
//					+ "\"openId\":\""+mi403.getUserid()+"\",\"title\":\""+mi403.getTitle()+"\","
//							+ "\"message\":\""+mi403.getTsmsg()+"\"}");
			HashMap mapreq = new HashMap();
			mapreq.put("regionId", mi403.getCenterid());
			mapreq.put("openId", mi403.getUserid());
			mapreq.put("title", mi403.getTitle());
			mapreq.put("message", mi403.getTsmsg());
			String value = JsonUtil.getGson().toJson(mapreq);
			System.out.println("【WEIXIN个人报盘短信推送地址】---"+url);
			System.out.println("【WEIXIN个人报盘短信推送参数】---"+value);
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			System.out.println("【WEIXIN个人报盘短信推送返回】---"+msg);
			HashMap maprep = JsonUtil.getGson().fromJson(msg, HashMap.class);
			if("000000".equals((String)maprep.get("errcode"))){
				return 1;
			}else{
				return 0;
			}
		}else if(Constants.PUSH_TYPE_MB.equals(mi403.getPusMessageType())){
			/*String url = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/sendtemplatenew";*/
			String url = weixinurl+"/sendtemplatenew";
//			StringBuffer value = new StringBuffer("{\"method\":\"sendmess\",\"regionId\":\""+centerId+"\","
//					+ "\"openId\":\""+userid+"\",\"template_id\":\""+mi405.getTemplateCode()+"\",\"contents\":"
//							+ "\""+mi402.getTsmsg()+"\"}");
			HashMap mapreq = new HashMap();
			mapreq.put("method", "sendmess");
			mapreq.put("regionId", mi403.getCenterid());
			mapreq.put("openId", mi403.getUserid());
			mapreq.put("template_id", mi403.getChanneltemplate());
			mapreq.put("contents", mi403.getTsmsg());
			String value = JsonUtil.getGson().toJson(mapreq);
			System.out.println("[微信推送信息，上传参数："+value+"]");
			MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
			String msg = sendMessage.post(url, value.toString(), null, null, null);
			System.out.println("[微信推送信息，下传参数："+msg+"]");
			HashMap maprep = JsonUtil.getGson().fromJson(msg, HashMap.class);
			if("000000".equals((String)maprep.get("errcode"))){
				return 1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	public int sendSms(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.debug("【***推送开始时间6.21_insertSendTable***】"+CommonUtil.getSystemDate());
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		if(CommonUtil.isEmpty(request)){
			encoding = "UTF-8";
		}else{
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
		}
		
		//各中心差异化处理
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"smsurl").trim();
		String rep = "";
		if("00087100".equals(mi403.getCenterid())||"00087600".equals(mi403.getCenterid())
				||"00087500".equals(mi403.getCenterid())){//昆明文山共用
			if(Constants.PUSH_TYPE_BP.equals(mi403.getPusMessageType().trim())){
				HashMap hashMap = new HashMap();
				hashMap.put("snid", mi403.getSendseqno());
				hashMap.put("mobilephone", mi403.getParam1());
				hashMap.put("text", mi403.getTsmsg());
				hashMap.put("centerid", mi403.getCenterid());
				url = url +"/rsModelAction_rsShortMsg.action";
				System.out.println("###### ===== SMS ===== URL:"+url);
				rep = msm.sendPost(url, hashMap, encoding);
			}else if(Constants.PUSH_TYPE_MB.equals(mi403.getPusMessageType().trim())){
				HashMap hashMap = new HashMap();
				hashMap.put("modelid", mi403.getChanneltemplate());
				hashMap.put("snid", mi403.getSendseqno());
				hashMap.put("mobilephone", mi403.getParam1());
				hashMap.put("modelcode", mi403.getTsmsg());
				hashMap.put("centerid", mi403.getCenterid());
				url = url +"/rsModelAction_rsModelMsg.action";
				System.out.println("###### ===== SMS ===== URL:"+url);
				log.debug("【***推送开始时间6.22_insertSendTable***】"+CommonUtil.getSystemDate());
				rep = msm.sendPost(url, hashMap, encoding);
				log.debug("【***推送开始时间6.23_insertSendTable***】"+CommonUtil.getSystemDate());
			}
		}else if("00073300".equals(mi403.getCenterid())){
			if(Constants.PUSH_TYPE_BP.equals(mi403.getPusMessageType().trim())){
//				HashMap hashMap = new HashMap();
//				hashMap.put("snid", mi403.getSendseqno());
//				hashMap.put("mobilephone", mi403.getParam1());
//				hashMap.put("text", mi403.getTsmsg());
//				hashMap.put("centerid", mi403.getCenterid());
//				url = url +"/rsModelAction_rsShortMsg.action";
//				System.out.println("###### ===== SMS ===== URL:"+url);
//				rep = msm.sendPost(url, hashMap, encoding);
			}else if(Constants.PUSH_TYPE_MB.equals(mi403.getPusMessageType().trim())){
				HashMap hashMap = new HashMap();
//				hashMap.put("modelid", mi403.getChanneltemplate());
//				hashMap.put("snid", mi403.getSendseqno());
//				hashMap.put("mobilephone", mi403.getParam1());
//				hashMap.put("modelcode", mi403.getTsmsg());
//				hashMap.put("centerid", mi403.getCenterid());
				
				mi403.setTsmsg(URLEncoder.encode(URLEncoder.encode(mi403.getTsmsg(), "UTF-8"),"UTF-8"));
				hashMap.put("flag", "0");//单笔0，批量1
				hashMap.put("filename", "");
				hashMap.put("modelid", mi403.getChanneltemplate());//模板编号
				hashMap.put("snid", mi403.getSendseqno());//流水号
				hashMap.put("mobilephone", mi403.getParam1());//手机号
				hashMap.put("modelcode", mi403.getTsmsg());//要素信息
				
				hashMap.put("centerid", mi403.getCenterid());
				hashMap.put("sendcount", "1");//发送次数,默认1
				hashMap.put("priority", "2");//优先级
				
				
				
				url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
						"smsurlMB").trim();
				System.out.println("###### ===== SMS ===== URL:"+url);
				rep = msm.sendPost(url, hashMap, encoding);
			}
		}
		
		
		
		System.out.println("##########  ====短信平台返回信息====="+rep);
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("recode"))){
			if("000000".equals((String)remap.get("recode"))){
				mi403.setStatus("1");
				System.out.println("##########  ====短信推送=====  ["+mi403.getPushid()+"]成功，类型["+mi403.getPusMessageType()+"]");
				mi403Dao.updateByPrimaryKeySelective(mi403);				
				return 1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	public HashMap sendSmsCheckAndMessage(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HashMap resmap = null;
		try{
			if("00087100".equals(form.getCenterId())||"00087600".equals(form.getCenterId())){
				resmap = sendSmsCheckAndMessage00087100( form, request, response);
			}else if("00073300".equals(form.getCenterId())){
				resmap = sendSmsCheckAndMessage00073300( form, request, response);
			}
			
			// add by lwj @2017-05-27 保山中心临时解决方案，使用老短信平台进行发送
			else if("00087500".equals(form.getCenterId())){
				resmap = sendSmsCheckAndMessage00087500( form, request, response);
			}
			// add by Carter King @2018-02-12 宁波短信平台进行发送
			else if("00057400".equals(form.getCenterId())){
				resmap = sendSmsCheckAndMessage00057400( form, request, response);
			}
		}catch(Exception e){
			throw e;
		}
		return resmap;
		
		
//		int sendResult = 0;
//		try {
//			String msg = "";
//			if(Constants.PUSH_TYPE_BP.equals(form.getSmstype())){
//				msg = form.getMessage();
//			}else{
//				msg = "【昆明公积金中心】您好！您正在"+form.getMessage()+"，短信动态验证码"+form.getVcode()+"，请切勿将短信动态验证码告诉他人。";
//			}
//			try {
//				Client client = new Client("9SDK-EMY-0999-JCVPL","892703");
//				 sendResult = client.sendSMSEx(new String[]{form.getTel()}, msg, "GBK", 5);
//			 }catch(Exception e) {
//				 e.printStackTrace();
//			 }
//			System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。");
//			System.out.println("########【调用艺美短信发送接口】,当前发送的手机号：["+form.getTel()+"]。\n当前发送短信内容：["+msg+"]");
//			if(sendResult == 0) {
//				System.out.println("########【调用艺美短信发送接口】，当前发送成功");
//			} else {
//				if (sendResult == -1) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。发送信息失败（短信内容长度越界）！");
//				} else if (sendResult == 17) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。发送信息失败（未激活序列号或序列号和KEY值不对，或账户没有余额等）");
//				} else if (sendResult == 101) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。发送信息失败（短信内容长度越界）！");
//				} else if (sendResult == 305) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。服务器端返回错误，错误的返回值（返回值不是数字字符串）");
//				}else if (sendResult == 306) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。\n客户端发送队列满！\n请检查网络状况，如网络正常，请参照亿美手册异常处理进行解决！");
//				} else if (sendResult == 307) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。\n目标电话号码不符合规则，电话号码必须是以0、1开头！");
//				} else if (sendResult == 997) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。\n平台返回找不到超时的短信，该信息是否成功无法确定！");
//				} else if (sendResult == 303) {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。\n由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定！");
//				} else {
//					System.out.println("########【调用艺美短信发送接口】，当前发送结果sendResult="+sendResult+"。\n需要亿美提供正确的注册序列号后，才可以使用！");
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		if(sendResult==0)
//			return 1;
//		else
//			return 0;
	}
	
	public HashMap sendSmsCheckAndMessage00057400(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {
		    SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		    String encoding = "UTF-8";
		    if (CommonUtil.isEmpty(request.getCharacterEncoding()))
		      encoding = "UTF-8";
		    else {
		      encoding = request.getCharacterEncoding();
		    }

		    String rep = "";
		    String seqno = CommonUtil.getSystemDateNumOnly();
		    System.out.println("【渠道主动发送短信息00057400】---主题：【" + form.getSendTheme() + "】，流水号：【" + seqno + "】 , 手机号：【" + form.getTel() + "】");
		    if (!"02".equals(form.getSmstype()))
		    {
		      if ("03".equals(form.getSmstype())) {
		        String url = PropertiesReader.getProperty("properties.properties", 
		          "smsurl").trim();
		        String mess = "";
		        if (CommonUtil.isEmpty(form.getSendTheme())) {
		          form.setSendTheme("custom");
		          mess = "您的验证码为:" + form.getVcode() + ",宁波公积金中心";
		        }
		        else if ("18".equals(form.getSendTheme())) {
		          form.setSendTheme("custom");
		          mess = "您的验证码为:" + form.getVcode() + ",宁波公积金中心";
		        } else {
		          mess = form.getMessage();
		        }

		        HashMap hashMap = new HashMap();
		        System.out.println("【渠道主动发送短信验证码信息】---要素转码前信息---【" + mess + "】:");
		        System.out.println("【渠道主动发送短信验证码信息】---主题编码---【" + form.getSendTheme() + "】:");
//		        hashMap.put("flag", "0");
//		        hashMap.put("filename", "");
//		        hashMap.put("smssource", form.getSendTheme());
//		        hashMap.put("phone", form.getTel());
//		        hashMap.put("elements", mess);
//		        hashMap.put("charset", "utf-8");
//		        hashMap.put("sendtime", "");
//		        hashMap.put("sendlx", "0");
//		        hashMap.put("names", "");
//		        hashMap.put("username", "");
//		        hashMap.put("jgh", form.getJgh());
		        
		        hashMap.put("SJHM", form.getTel());
		        hashMap.put("JGH", form.getJgh());
		        hashMap.put("YWLBDM", "ZH");
		        hashMap.put("YWMCDM", "30");
		        hashMap.put("MSG", mess);
		        hashMap.put("YXJ", "1");
//		        hashMap.put("FSSJ", "");
//		        hashMap.put("EXTNO", "");
//		        hashMap.put("CALLBACKURL", "");
//		        hashMap.put("DYID", "");

		        System.out.println("【渠道主动发送短信验证码信息】---等推送短信息流水号---【" + seqno + "】 URL:" + url);
		        rep = msm.sendPost(url, hashMap, encoding);
		      }
		    }
		    System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【" + seqno + "】，返回信息为【" + rep + "】");
		    System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【" + seqno + "】，返回信息为【" + change(rep) + "】");

//		    HashMap remap = (HashMap)JsonUtil.getGson().fromJson(rep, HashMap.class);
		    String repString=rep.toString();
		    String repflag=repString.substring(7, 8);
			System.out.println("repflag=============="+repflag);
		    String miSeqno=repString.replace("】", "").substring(repString.length()-21);
			System.out.println("miSeqno=============="+miSeqno);
		    HashMap remap =new HashMap();

		    if (repflag.equals("1")) {
			   remap.put("recode", "000000");
			   remap.put("msg", "处理成功");
			   remap.put("miSeqno", miSeqno);
			   System.out.println("recode==============="+remap.get("recode"));
			   System.out.println("msg================="+remap.get("msg"));
			   System.out.println("miSeqno=============="+remap.get("miSeqno"));
			   return remap;
		    }
		    return null;
		  }

	public HashMap sendSmsCheckAndMessage00087100(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"smsurl").trim();
		String rep = "";
		String seqno = CommonUtil.getSystemDateNumOnly();
		System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+" , 手机号："+form.getTel()+"】");
		if(Constants.PUSH_TYPE_BP.equals(form.getSmstype())){
			HashMap hashMap = new HashMap();
			hashMap.put("snid", seqno);
			hashMap.put("mobilephone", form.getTel());
			hashMap.put("text", form.getMessage());
			hashMap.put("centerid", form.getCenterId());
			url = url +"/rsModelAction_rsShortMsg.action";
			System.out.println("###### ===== SMS ===== URL:"+url);
			System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+"】,URL："+url);
			rep = msm.sendPost(url, hashMap, encoding);
		}else if(Constants.PUSH_TYPE_MB.equals(form.getSmstype())){
			String mess = form.getMessage()+";"+form.getVcode()+";"+form.getTel()+";";
			HashMap hashMap = new HashMap();
			hashMap.put("modelid", "KMDX0018");
			hashMap.put("snid", seqno);
			hashMap.put("mobilephone", form.getTel());
			hashMap.put("modelcode", mess);
			hashMap.put("centerid", form.getCenterId());
			url = url +"/rsModelAction_rsModelMsg.action";
			System.out.println("【渠道主动发送短信验证码信息】---等推送短信息流水号---【"+seqno+"】 URL:"+url);
			rep = msm.sendPost(url, hashMap, encoding);
		}
		System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+"】，返回信息为【"+rep+"】");
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("recode"))){
			return remap;
		}else{
			return null;
		}
	}
	
	/**
	 * 株洲短信验证码发送，及非模板类信息发送处理
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public HashMap sendSmsCheckAndMessage00073300(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		//株洲短信发送地址
		
		String rep = "";
		String seqno = CommonUtil.getSystemDateNumOnly();
		System.out.println("【渠道主动发送短信息00073300】---主题：【"+form.getSendTheme()+"】，流水号：【"+seqno+"】 , 手机号：【"+form.getTel()+"】");
		if(Constants.PUSH_TYPE_BP.equals(form.getSmstype())){
//			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
//					"smsurl").trim();
//			HashMap hashMap = new HashMap();
//			hashMap.put("snid", seqno);
//			hashMap.put("mobilephone", form.getTel());
//			hashMap.put("text", form.getMessage());
//			hashMap.put("centerid", form.getCenterId());
//			url = url +"/rsModelAction_rsShortMsg.action";
//			System.out.println("###### ===== SMS ===== URL:"+url);
//			System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【"+seqno+"】,URL："+url);
//			rep = msm.sendPost(url, hashMap, encoding);
		}else if(Constants.PUSH_TYPE_MB.equals(form.getSmstype())){
			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
					"smsurlMB").trim();
			String mess = "";
			if(CommonUtil.isEmpty(form.getSendTheme())){
				form.setSendTheme("D73332");
				mess = form.getMessage()+";"+form.getVcode()+";"+form.getTel()+";";
			}else{
				if("18".equals(form.getSendTheme())){
					form.setSendTheme("D73332");
					mess = form.getMessage()+";"+form.getVcode()+";"+form.getTel()+";";
				}else{
					//form.setSendTheme("D73332");
					String[] trs = form.getMessage().split(";");
					   StringBuffer msg = new StringBuffer();
					   for(int i=0;i<trs.length;i++){
							if(i!=2){
								msg.append(trs[i]+";");
							}
					   }
					   mess = msg.toString()+form.getTel();
				}
			}
			   
			
			HashMap hashMap = new HashMap();
			System.out.println("【渠道主动发送短信验证码信息】---要素转码前信息---【"+mess+"】:");
			mess = URLEncoder.encode(URLEncoder.encode(mess, "UTF-8"),"UTF-8");
			System.out.println("【渠道主动发送短信验证码信息】---主题编码---【"+form.getSendTheme()+"】:");
			hashMap.put("flag", "0");//单笔0，批量1
			hashMap.put("filename", "");
			hashMap.put("modelid", form.getSendTheme());//模板编号
			hashMap.put("snid", seqno);//流水号
			hashMap.put("mobilephone", form.getTel());//手机号
			hashMap.put("modelcode", mess);//要素信息
			
			hashMap.put("centerid", form.getCenterId());
			hashMap.put("sendcount", "1");//发送次数,默认1
			hashMap.put("priority", "2");//优先级
			/***
			1	验证码	高
			2	信息变更	高
			3	提取	高
			4	缴存	中
			5	贷款	中
			6	红冲	中
			7	结息	低
			8	通知公告	低
			9	催缴	低
			*/
			
			
//			url = url +"/sendMessageAction!insertMessage.action";
			System.out.println("【渠道主动发送短信验证码信息】---等推送短信息流水号---【"+seqno+"】 URL:"+url);
			rep = msm.sendPost(url, hashMap, encoding);
		}
		System.out.println("【渠道主动发送短信息】---等推送短信息流水号---【"+seqno+"】，返回信息为【"+rep+"】");
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("recode"))){
			return remap;
		}else{
			return null;
		}
	}
	
	public HashMap sendSmsCheckAndMessage00087500(AppApi50004Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"smsurl").trim();
		String rep = "";
		String seqno = CommonUtil.getSystemDateNumOnly();
		System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+" , 手机号："+form.getTel()+"】");
		if(Constants.PUSH_TYPE_BP.equals(form.getSmstype())){	//自定义信息
			HashMap hashMap = new HashMap();
			hashMap.put("snid", seqno);
			hashMap.put("mobilephone", form.getTel());
			hashMap.put("text", form.getMessage());
			hashMap.put("centerid", form.getCenterId());
			url = url +"/rsModelAction_rsShortMsg.action";
			System.out.println("###### ===== SMS ===== URL:"+url);
			System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+"】,URL："+url);
			rep = msm.sendPost(url, hashMap, encoding);
		}else if(Constants.PUSH_TYPE_MB.equals(form.getSmstype())){	//验证码或其它直发短信
			if(CommonUtil.isEmpty(form.getSendTheme())){
				form.setSendTheme("18");
				form.setMessage(form.getVcode());
			}	
			Mi411Example mi411Example = new Mi411Example();
			mi411Example.setOrderByClause("datemodified desc");
			Mi411Example.Criteria mi411Criteria = mi411Example.createCriteria();
			mi411Criteria.andThemeEqualTo(form.getSendTheme());
			mi411Criteria.andCenteridEqualTo(form.getCenterId());
			mi411Criteria.andValidflagEqualTo("1");
			List<Mi411> listMi411 = mi411Dao.selectByExample(mi411Example);
			
			//转换模板为BSDX格式
			form.setSendTheme(listMi411.get(0).getChanneltemplate());
			HashMap hashMap = new HashMap();
			hashMap.put("modelid", form.getSendTheme());
			hashMap.put("snid", seqno);
			hashMap.put("mobilephone", form.getTel());
			hashMap.put("modelcode", form.getMessage());
			hashMap.put("centerid", form.getCenterId());
			url = url +"/rsModelAction_rsModelMsg.action";
			System.out.println("###### ===== SMS ===== URL:"+url);
			System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+"】,URL："+url);
			rep = msm.sendPost(url, hashMap, encoding);
		}
		System.out.println("【渠道主动发送短信息"+form.getCenterId()+"】---等推送短信息流水号---【"+seqno+"】，返回信息为【"+rep+"】");
		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("recode"))){
			return remap;
		}else{
			return null;
		}
	}
	
	public void insertTimingTable(CMi401 mi401) throws Exception{
		Mi408 mi408 = new Mi408();
		String timeid = commonUtil.genKey("MI408",20);
		mi408.setTimeid(timeid);
		mi408.setPusMessageType(mi401.getPusMessageType());
		mi408.setTsmesstype(mi401.getTsmsgtype());
		mi408.setCenterid(mi401.getCenterid());
		mi408.setDsdate(mi401.getDsdate());
		mi408.setCommsgid(mi401.getCommsgid());
		mi408.setMsgsource(mi401.getMsgsource());
		mi408.setStatus(Constants.PUSH_MSG_DEF_STATE);
		mi408.setValidflag(Constants.IS_VALIDFLAG);
		mi408.setDatecreated(CommonUtil.getSystemDate());
		mi408.setDatemodified(CommonUtil.getSystemDate());
		mi408Dao.insert(mi408);
	}
	
	public int sendWeiBo(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception{
		String accessToken = WkfAccessTokenUtil.getWBTokenWithCouchBase(request.getParameter("centerId"));
		String url = PropertiesReader.getHeartbeatURL(request.getParameter("centerId"),
				"80").trim() + "/proc/sina/share";
		//"/proc/sina/upload";
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		
		
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		HashMap hashMap = new HashMap();
		hashMap.put("token", accessToken);
		hashMap.put("org_user_id", mi403.getCenterid());
		hashMap.put("org_user_name", mi403.getCenterid());//用户名称
		hashMap.put("org_user_nickname", mi403.getCenterid());//用户昵称
		hashMap.put("title", URLEncoder.encode(mi403.getTitle(), request.getCharacterEncoding()));//标题
		hashMap.put("digest", URLEncoder.encode(mi403.getDetail(), request.getCharacterEncoding()));//"digest":"摘要", //摘要，可空
		//"pic":"图片文件", //要上传的图片url地址，仅支持JPEG、GIF、PNG格式，图片大小小于5M，纯文本则不上传此项
		if("02".equals(mi403.getTsmsgtype())){
			String pic = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
					"push_msg_img", mi403.getCenterid() + "/" + mi403.getParam2(), true);
			hashMap.put("pic", pic);
		}else if("03".equals(mi403.getTsmsgtype())){
			if(!CommonUtil.isEmpty(mi403.getParam2())){
				String pic = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
						"push_msg_img", mi403.getCenterid() + "/" + mi403.getParam2(), true);
				hashMap.put("pic", pic);
			}
		}
		String nwurl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "NW_YBMAPZH_URL");
		hashMap.put("content", mi403.getTsmsg().replaceAll(nwurl, YBMAPZH_URL));
		hashMap.put("type", mi403.getTsmsgtype());
		
		
		//hashMap.put("status", URLEncoder.encode(mi403.getTsmsg(), request.getCharacterEncoding()));
		//hashMap.put("pic", "");
		System.out.println("微博发布参数："+hashMap);
		//java.net.URLEncoder.encode(mi403.getTsmsg(),"utf-8")		
		System.out.println("【群推业务——调用微博群推送开始】");
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		System.out.println("【微信纯文本类型群推返回信息】 "+rep);
		System.out.println("【群推业务——调用微博群推送结束】");

		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				return 1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	public int sendWeiBoHeadLine(Mi403 mi403 , HttpServletRequest request, HttpServletResponse response) throws Exception{
		String accessToken = WkfAccessTokenUtil.getWBTokenWithCouchBase(request.getParameter("centerId"));
		String url = PropertiesReader.getHeartbeatURL(request.getParameter("centerId"),
				"80").trim() + "/proc/sina/publish";
		//"/proc/sina/upload";
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");

		//"summary":"文章导语导个啥",//文章导语

		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		HashMap hashMap = new HashMap();
		hashMap.put("token", accessToken);
		hashMap.put("org_user_id", mi403.getCenterid());
		hashMap.put("org_user_name", mi403.getCenterid());//用户名称
		hashMap.put("org_user_nickname", mi403.getCenterid());//用户昵称
//		hashMap.put("title", URLEncoder.encode(mi403.getTitle(), request.getCharacterEncoding()));//标题
//		hashMap.put("text", URLEncoder.encode(mi403.getDetail(), request.getCharacterEncoding()));//摘要，不可空
		hashMap.put("title", mi403.getTitle());//标题
		hashMap.put("text", mi403.getDetail());//摘要，不可空
		hashMap.put("summary", mi403.getHeadleads());//导语，可空
		//"pic":"图片文件", //要上传的图片url地址，仅支持JPEG、GIF、PNG格式，图片大小小于5M，纯文本则不上传此项
		if("03".equals(mi403.getTsmsgtype())){
			if(!CommonUtil.isEmpty(mi403.getParam2())){
				String pic = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
						"push_msg_img", mi403.getCenterid() + "/" + mi403.getParam2(), true);
				hashMap.put("cover", pic);
			}
		}
		String nwurl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "NW_YBMAPZH_URL");
//		hashMap.put("content", URLEncoder.encode(mi403.getTsmsg().replaceAll(nwurl, YBMAPZH_URL), request.getCharacterEncoding()));
		hashMap.put("content", mi403.getTsmsg().replaceAll(nwurl, YBMAPZH_URL));
//		hashMap.put("type", mi403.getTsmsgtype());
		
		
		//hashMap.put("status", URLEncoder.encode(mi403.getTsmsg(), request.getCharacterEncoding()));
		//hashMap.put("pic", "");
		System.out.println("微博头条发布参数："+hashMap);
		//java.net.URLEncoder.encode(mi403.getTsmsg(),"utf-8")
		System.out.println("【群推业务——调用微博头条群推送开始】");
		String rep = msm.sendPost(url, hashMap, request.getCharacterEncoding());
		System.out.println("【微博头条群推返回信息】 "+rep);
		System.out.println("【群推业务——调用微博头条群推送结束】");

		HashMap remap = JsonUtil.getGson().fromJson(rep, HashMap.class);
		if(!CommonUtil.isEmpty(remap.get("code"))){
			if("0000".equals(remap.get("code"))){
				return 1;
			}else{
				return 0;
			}
		}
		return 0;
	}
	
	public  void checkMi415() throws Exception{
		Mi415Example mi415Example = new Mi415Example();
		Mi415Example.Criteria mi415Criteria = mi415Example.createCriteria();
		mi415Criteria.andDsopenEqualTo("1")
		.andValidflagEqualTo("1")
		.andDstypeEqualTo("01");
		List<Mi415> listMi415 = mi415Dao.selectByExample(mi415Example);
		for(Mi415 mi415 : listMi415) {
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(mi415.getDatemodified()));	
		    Long nowdate =System.currentTimeMillis();
		    if(!mi415.getDsstatus().equals("1")) {
		    	continue;
		    }
		    if(nowdate-calendar.getTimeInMillis()>600*1000) {
		    	System.out.println("=====nowdate:"+nowdate+",centerid:"+mi415.getCenterid());
		    	listMi415.remove(mi415);
		    	mi415.setDsstatus("0");
				mi415.setDatemodified(CommonUtil.getSystemDate());
				mi415Dao.updateByPrimaryKeySelective(mi415);
		    }			
		}
	}
	
	public void checkTimingAndSend() throws Exception{
		
		Mi415Example mi415Example = new Mi415Example();
		Mi415Example.Criteria mi415Criteria = mi415Example.createCriteria();
		mi415Criteria.andValidflagEqualTo("1").andDstypeEqualTo("01");//1
		mi415Criteria.andDsstatusEqualTo("0");//2
		mi415Criteria.andDsopenEqualTo("1");//3 调整多中心时定时处理。
		//Mi415 mi415 = mi415Dao.selectByPrimaryKey("00000000000000000001");
		List<Mi415> listMi415 = mi415Dao.selectByExample(mi415Example);
		if(!CommonUtil.isEmpty(listMi415)){
			Mi415 mi415 = listMi415.get(0);
			if("1".equals(mi415.getDsopen())&&"0".equals(mi415.getDsstatus())){
				
				mi415.setDsstatus("1");
				mi415.setDatemodified(CommonUtil.getSystemDate());
				mi415Dao.updateByPrimaryKeySelective(mi415);
				
				Mi408Example mi408Example = new Mi408Example();
				mi408Example.setOrderByClause("dsdate");
				Mi408Example.Criteria mi408Criteria = mi408Example.createCriteria();
				mi408Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG).andStatusEqualTo("0");
				mi408Criteria.andDsdateGreaterThanOrEqualTo(CommonUtil.getDate()+" 00:00:00");
				List<Mi408> list = mi408Dao.selectByExample(mi408Example);
				System.out.println("定时推送待推数据："+list.size());
				int sum = 0;
				try{
					if(!CommonUtil.isEmpty(list)){
						for(int i=0;i<list.size();i++){
							 Mi408 mi408 = list.get(i);
						     java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						     Date dsdate = formatter.parse(mi408.getDsdate(), new java.text.ParsePosition(0));
						     Date nowdate = new Date(System.currentTimeMillis());
						     //###########################################
						     if(nowdate.before(dsdate)){
						    	 continue;
						     }else{
						    	 Mi401 mi401 = mi401Dao.selectByPrimaryKey(mi408.getCommsgid());
						    	 if(sum>=40){
						    		 break;
						    	 }
						    	 if(!CommonUtil.isEmpty(mi401)){
						    		 sum++;
						    		 CMi401 cmi401 = new CMi401();
						    		 cmi401.setCommsgid(mi401.getCommsgid());
						    		 cmi401.setCenterid(mi401.getCenterid());
						    		 cmi401.setPusMessageType(mi401.getPusMessageType());
						    		 cmi401.setTsmsgtype(mi401.getTsmsgtype());
						    		 insertSendTable(cmi401 ,null ,null);
						    		 
						    		 mi408.setStatus("1");
						    		 mi408.setDatemodified(CommonUtil.getSystemDate());
						    		 //mi408Dao.updateByPrimaryKeySelective(mi408);
						    	 }else{
						    		 //待推数据问题，强行更新已经推送
						    		 mi408.setStatus("1");
						    		 mi408.setDatemodified(CommonUtil.getSystemDate());
//						    		 mi408Dao.updateByPrimaryKeySelective(mi408);
						    	 }
						    	 
						    	 Mi409 mi409 = new Mi409();
						    	 mi409.setTimeid(mi408.getTimeid());
						    	 mi409.setPusMessageType(mi408.getPusMessageType());
						    	 mi409.setTsmesstype(mi408.getTsmesstype());
						    	 mi409.setCenterid(mi408.getCenterid());
						    	 mi409.setDsdate(mi408.getDsdate());
						    	 mi409.setCommsgid(mi408.getCommsgid());
						    	 mi409.setMsgsource(mi408.getMsgsource());
						    	 mi409.setStatus("1");
						    	 mi409.setValidflag(mi408.getValidflag());
						    	 mi409.setDatemodified(CommonUtil.getSystemDate());
						    	 mi409.setDatecreated(mi408.getDatecreated());
						    	 mi409.setFreeuse1(mi408.getFreeuse1());
						    	 mi409Dao.insert(mi409);
						    	 mi408Dao.deleteByPrimaryKey(mi408.getTimeid());
						     }
						   //###########################################
						}
					}else{
						//System.out.println("【INFO----定时推送MI408无待推送数据】");
					}
				}catch(Exception e){
					
				}finally{
					mi415.setDsstatus("0");
					mi415.setDatemodified(CommonUtil.getSystemDate());
					mi415Dao.updateByPrimaryKeySelective(mi415);
				}
				
				
			}else{
				//System.out.println("【INFO----定时推送MI415标记信息为处理中】");
			}
		}else{
			System.out.println("【ERROR----未找到定时推送MI415标记信息】");
		}		
	}
	
	public List<Mi040> getChannelPid(String centerid ,String channel) throws Exception{
		Mi040Example mi040Example = new Mi040Example();
		mi040Example.setOrderByClause("centerid asc,channel asc");
		Mi040Example.Criteria ca = mi040Example.createCriteria();
		ca.andCenteridEqualTo(centerid).andChannelEqualTo(channel);
		ca.andValidflagEqualTo("1");
		List<Mi040> list = mi040Dao.selectByExample(mi040Example);
		return list;
	}
	
	public String webapi30201_commentCtrl(CMi404 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "批次号");
		}
		/*if (CommonUtil.isEmpty(form.getMsmscommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("msmsCommsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图文ID");
		}*/
		
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
		if(CommonUtil.isEmpty(form.getMsmscommsgid())){
			mi404Criteria.andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}else{
			mi404Criteria.andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andMsmscommsgidEqualTo(form.getMsmscommsgid());
		}
		List<Mi404> listmi404 = mi404Dao.selectByExampleWithoutBLOBs(mi404Example);
		Mi404 mi404 = listmi404.get(0);
		
		//发送开关
		String weixinurl = PropertiesReader.getHeartbeatURL(mi404.getCenterid(), "20").trim();
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		String url = weixinurl+"/mpnewscomment";
		url = url + "?centerid=" + form.getCenterId();
		url = url + "&msg_data_id=" + mi404.getFreeuse1();
		url = url + "&index=" + String.valueOf(mi404.getMsnum()-1);
		if("1".equals(form.getFreeuse4())){
			url = url + "&method=close";
		}else{
			url = url + "&method=open";
		}
		System.out.println("图文消息开关发送："+url);
		MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
		String msg = sendMessage.post(url, "", null, null, null);
		HashMap remap = JsonUtil.getGson().fromJson(msg, HashMap.class);
		log.info("remap:" + remap);
		if(!"000000".equals((String)remap.get("errcode"))){
			return "发送图文消息开关失败！";
		}
		//更新数据库开关
		mi404.setFreeuse4(form.getFreeuse4());
		int rows = mi404Dao.updateByPrimaryKeySelective(mi404);
		if(rows<=0){
			return "更新图文消息开关失败！";
		}
		return "";
	}
	
	public HashMap getTextImage(AppApi90421Form form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		HashMap map = new HashMap();
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
		mi404Criteria.andFreeuse1EqualTo(form.getMsg_data_id())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andMsnumEqualTo(Integer.valueOf(form.getIndex()) + 1);
		List<Mi404> listmi404 = mi404Dao.selectByExampleWithBLOBs(mi404Example);
		if(listmi404.size()==0){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
					.getValue(), "记录不存在！");
		}
		Mi404 mi404 = listmi404.get(0);
		map.put("title", mi404.getTitle());
		map.put("detail", mi404.getTsmsg());
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		String pic = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
				"push_msg_img", form.getCenterId() + "/" + mi404.getParam2(), true);
		map.put("pic", pic);
		return map;
	}
	
	public String webapi30201_perviewSend(CMi401 form, ModelMap modelMap) throws Exception{
		Mi029Example mi029Example = new Mi029Example();
		mi029Example.createCriteria().andCenteridEqualTo(form.getCenterid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andCertinumEqualTo(form.getCertinum());
		List<Mi029> mi029List = this.mi029Dao.selectByExample(mi029Example);
		if(0 == mi029List.size()){
			return "用户不存在！";
		}
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andPersonalidEqualTo(mi029List.get(0).getPersonalid())
			.andChannelEqualTo("20");
		List<Mi031> mi031List = this.mi031Dao.selectByExample(mi031Example);
		if(0 == mi031List.size()){
			return "渠道用户不存在！";
		}
		
		//String weixinurl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "weixinurl");
		String weixinurl = PropertiesReader.getHeartbeatURL(form.getCenterid(), "20").trim();
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		HashMap mapreq = new HashMap();
		mapreq.put("centerid", form.getCenterid());
		mapreq.put("sendType", "preview");
		mapreq.put("file_suffix", "");
		mapreq.put("media_id", "");
		mapreq.put("imgurl", "");
		mapreq.put("openid", mi031List.get(0).getUserid());
		
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
		mi404Criteria.andCommsgidEqualTo(form.getCommsgid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi404> listmi404 = mi404Dao.selectByExampleWithBLOBs(mi404Example);
		if(CommonUtil.isEmpty(listmi404)){
			TransRuntimeErrorException error = new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"无待推送数据");
			throw error;
		}
		String ntranet_url  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "serverPath");
		List<HashMap> mess = new ArrayList();
		for(int ii=0;ii<listmi404.size();ii++){
			Mi404 mi404 = listmi404.get(ii);
			String[] pImgs = mi404.getParam2().split(",");
			String img = pImgs[0];
			String imgUrl = YBMAPZH_URL+"/"+CommonUtil.getDownloadFileUrl(
					"push_msg_img", form.getCenterid() + "/"
							+ img, true);
			Mi002 mi002 = new Mi002();
			Mi002Example mi002Example = new Mi002Example();
			Mi002Example.Criteria mi002C = mi002Example.createCriteria();
			mi002C.andLoginidEqualTo(mi404.getLoginid());
			List<Mi002> listMi002 = mi002Dao.selectByExample(mi002Example);
			HashMap messMap = new HashMap();
			messMap.put("thumb_media_id", imgUrl);
			messMap.put("author", listMi002.get(0).getOpername());
			messMap.put("title", mi404.getTitle());
			messMap.put("content_source_url", "");
			messMap.put("content", mi404.getTsmsg().replaceAll(ntranet_url, YBMAPZH_URL));
			messMap.put("digest", "");
			messMap.put("show_cover_pic", "1");
			messMap.put("need_open_comment", String.valueOf(mi404.getFreeuse4()));
			messMap.put("only_fans_can_comment", mi404.getFreeuse3());
			mess.add(messMap);
		}
		
		mapreq.put("articles", mess);//封装富文本多条信息
		mapreq.put("pushcontent", "");
			
		String url = weixinurl+"/sendall";
		String value = JsonUtil.getGson().toJson(mapreq);
		System.out.println("微信群推JSON："+value);
		MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
		String msg = sendMessage.post(url, value.toString(), null, null, null);
		//String msg = "{\"errcode\":\"000000\",\"errmsg\":\"\"}";
		System.out.println("微信群推返回JSON："+msg);
		HashMap remap = JsonUtil.getGson().fromJson(msg, HashMap.class);
		if("000000".equals((String)remap.get("errcode"))){
			return "000000";
		}else{
			return "发送失败！";
		}
	}
	
	public String webapi30201_deleteSend(CMi404 form, ModelMap modelMap) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(form.getCommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("commsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "批次号");
		}
		/*if (CommonUtil.isEmpty(form.getMsmscommsgid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("msmsCommsgid"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "图文ID");
		}*/
		
		Mi404Example mi404Example = new Mi404Example();
		Mi404Example.Criteria mi404Criteria = mi404Example.createCriteria();
		if(CommonUtil.isEmpty(form.getMsmscommsgid())){
			mi404Criteria.andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		}else{
			mi404Criteria.andCommsgidEqualTo(form.getCommsgid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andMsmscommsgidEqualTo(form.getMsmscommsgid());
		}
		List<Mi404> listmi404 = mi404Dao.selectByExampleWithoutBLOBs(mi404Example);
		Mi404 mi404 = listmi404.get(0);
		if(!"1".equals(mi404.getStatus()) || CommonUtil.isEmpty(mi404.getFreeuse2())){
			return "此消息没有发送成功，无需删除！";
		}
		
		String weixinurl = PropertiesReader.getHeartbeatURL(mi404.getCenterid(), "20").trim();
		String YBMAPZH_URL  = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "YBMAPZH_URL");
		String url = weixinurl+"/sendall";
		HashMap mapreq = new HashMap();
		mapreq.put("sendType", "delete");
		mapreq.put("file_suffix", "");
		mapreq.put("media_id", "");
		mapreq.put("imgurl", "");
		mapreq.put("articles", "");
		mapreq.put("pushcontent", "");
		mapreq.put("centerid", form.getCenterId());
		mapreq.put("msg_id", mi404.getFreeuse2());
		mapreq.put("article_idx", String.valueOf(mi404.getMsnum()));
		
		String value = JsonUtil.getGson().toJson(mapreq);
		System.out.println("微信群推JSON："+value);
		System.out.println("删除消息发送："+url);
		MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
		String msg = sendMessage.post(url, value.toString(), null, null, null);
		//String msg = "{\"errcode\":\"000000\",\"errmsg\":\"\"}";
		HashMap remap = JsonUtil.getGson().fromJson(msg, HashMap.class);
		log.info("remap:" + remap);
		if(!"000000".equals((String)remap.get("errcode"))){
			return "删除消息失败！";
		}
		//更新数据状态
		mi404.setValidflag(Constants.IS_NOT_VALIDFLAG);
		int rows = mi404Dao.updateByPrimaryKeySelective(mi404);
		if(rows<=0){
			return "删除消息失败！";
		}
		return "";
	}
	
	
	public static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("ISO-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output;  
    } 
}
