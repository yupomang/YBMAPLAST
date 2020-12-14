package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi120DAO;
import com.yondervision.mi.dao.CMi121DAO;
import com.yondervision.mi.dao.Mi120DAO;
import com.yondervision.mi.dao.Mi121DAO;
import com.yondervision.mi.dto.CMi120;
import com.yondervision.mi.dto.CMi121;
import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi120Example;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.dto.Mi121Example;
import com.yondervision.mi.result.WebApi41104_queryResult;
import com.yondervision.mi.result.WebApi41108_queryResult;
import com.yondervision.mi.service.WebApi411Service;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi401ServiceImpl
* @Description: 图片动画处理实现
* @author Caozhongyan
* @date Oct 5, 2013 10:24:14 AM
* 
*/ 
public class WebApi411ServiceImpl implements WebApi411Service {
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	protected final Logger log = LoggerUtil.getLogger();
	private Mi120DAO mi120Dao = null;
	private Mi121DAO mi121Dao = null;
	private CMi120DAO cmi120Dao = null;
	private CMi121DAO cmi121Dao = null;
	private WebApiUploadFile webApiUploadFile=null;
	
	public WebApiUploadFile getWebApiUploadFile() {
		return webApiUploadFile;
	}

	public void setWebApiUploadFile(WebApiUploadFile webApiUploadFile) {
		this.webApiUploadFile = webApiUploadFile;
	}
	public Mi120DAO getMi120Dao() {
		return mi120Dao;
	}
	public void setMi120Dao(Mi120DAO mi120Dao) {
		this.mi120Dao = mi120Dao;
	}
	public Mi121DAO getMi121Dao() {
		return mi121Dao;
	}
	public void setMi121Dao(Mi121DAO mi121Dao) {
		this.mi121Dao = mi121Dao;
	}
	public CMi120DAO getCmi120Dao() {
		return cmi120Dao;
	}
	public void setCmi120Dao(CMi120DAO cmi120Dao) {
		this.cmi120Dao = cmi120Dao;
	}
	public CMi121DAO getCmi121Dao() {
		return cmi121Dao;
	}
	public void setCmi121Dao(CMi121DAO cmi121Dao) {
		this.cmi121Dao = cmi121Dao;
	}
	public void webapi41101(CMi120 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi120 mi120 = new Mi120();
		mi120.setAnimateid(commonUtil.genKey("MI120", 30));
		mi120.setAnimatecode(form.getAnimatecode());
		mi120.setAnimatename(form.getAnimatename());
		mi120.setAnimatedescript(form.getAnimatedescript());
		mi120.setCenterid(form.getCenterid());
		mi120.setDevid(form.getDevid());
		mi120.setIntervaltime(form.getIntervaltime());
		mi120.setLooptype(form.getLooptype());
		mi120.setImgheight(form.getImgheight());
		mi120.setImgwidth(form.getImgwidth());
		mi120.setValidflag(Constants.IS_VALIDFLAG);
		mi120.setDatecreated(formatter.format(date));
		mi120.setDatemodified(formatter.format(date));		
		Mi120Example m120e=new Mi120Example();
		com.yondervision.mi.dto.Mi120Example.Criteria ca= m120e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andAnimatecodeEqualTo(form.getAnimatecode());
		ca.andDevidEqualTo(form.getDevid());
		List<Mi120> mi120List = mi120Dao.selectByExample(m120e);
		if(mi120List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心设备图片动画编号己存在，违反唯一索引约束");
		}		
		mi120Dao.insert(mi120);		
	}
	public void webapi41102(CMi120 form) throws Exception {
		// TODO Auto-generated method stub		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		String[] del = form.getAnimateid().split(",");
		for(int i=0;i<del.length;i++){			
			Mi120 mi120 = new Mi120();
			mi120.setAnimateid(del[i]);
			mi120.setDatemodified(formatter.format(date));
			mi120.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi120Dao.updateByPrimaryKeySelective(mi120);
		}
	}
	public void webapi41103(CMi120 form) throws Exception {
		// TODO Auto-generated method stub
		Mi120Example m120e=new Mi120Example();
		com.yondervision.mi.dto.Mi120Example.Criteria ca= m120e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andAnimatecodeEqualTo(form.getAnimatecode());
		ca.andDevidEqualTo(form.getDevid());
		ca.andAnimateidNotEqualTo(form.getAnimateid());
		List<Mi120> mi120List = mi120Dao.selectByExample(m120e);
		if(mi120List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"中心设备图片动画编号己存在，违反唯一索引约束");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi120 mi120 = new Mi120();
		mi120.setAnimateid(form.getAnimateid());
		mi120.setAnimatecode(form.getAnimatecode());
		mi120.setAnimatename(form.getAnimatename());
		mi120.setAnimatedescript(form.getAnimatedescript());
		mi120.setCenterid(form.getCenterid());
		mi120.setDevid(form.getDevid());
		mi120.setIntervaltime(form.getIntervaltime());
		mi120.setLooptype(form.getLooptype());
		mi120.setImgheight(form.getImgheight());
		mi120.setImgwidth(form.getImgwidth());
		mi120.setValidflag(Constants.IS_VALIDFLAG);		
		mi120.setDatemodified(formatter.format(date));
		mi120Dao.updateByPrimaryKeySelective(mi120);		
	}
	public WebApi41104_queryResult webapi41104(CMi120 form) throws Exception {
		// TODO Auto-generated method stub
		WebApi41104_queryResult list = cmi120Dao.selectAllByList(form);
		if (CommonUtil.isEmpty(list)) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("图片动画信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"图片动画信息");
		}
		return list; 	
	}
	
	
	
	
	public void webapi41105(CMi121 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi121 mi121 = new Mi121();
		mi121.setMxid(commonUtil.genKey("MI121", 30));
		mi121.setXh(form.getXh());
		mi121.setAnimateid(form.getAnid());
		mi121.setImgpath(form.getImgpath());
		mi121.setContentlink(form.getContentlink());
		mi121.setDisplaydirection(form.getDisplaydirection());		
		mi121.setValidflag(Constants.IS_VALIDFLAG);
		mi121.setDatecreated(formatter.format(date));
		mi121.setDatemodified(formatter.format(date));		
		Mi121Example m121e=new Mi121Example();
		com.yondervision.mi.dto.Mi121Example.Criteria ca= m121e.createCriteria();
		ca.andXhEqualTo(form.getXh());
		ca.andAnimateidEqualTo(form.getAnid());		
		List<Mi121> mi121List = mi121Dao.selectByExample(m121e);
		if(mi121List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心设备图片动画明细编号己存在，违反唯一索引约束");
		}		
		mi121Dao.insert(mi121);	
	}
	public void webapi41106(CMi121 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		String[] del = form.getMxid().split(",");
		for(int i=0;i<del.length;i++){			
			Mi121 mi121 = new Mi121();
			mi121.setMxid(del[i]);
			mi121.setDatemodified(formatter.format(date));
			mi121.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi121Dao.updateByPrimaryKeySelective(mi121);
		}
	}
	public void webapi41107(CMi121 form) throws Exception {
		// TODO Auto-generated method stub
		Mi121Example m121e=new Mi121Example();
		com.yondervision.mi.dto.Mi121Example.Criteria ca= m121e.createCriteria();
		ca.andMxidNotEqualTo(form.getMxid());
		ca.andAnimateidEqualTo(form.getAnid());		
		ca.andXhEqualTo(form.getXh());
		List<Mi121> mi121List = mi121Dao.selectByExample(m121e);
		if(mi121List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"中心设备图片动画明细编号己存在，违反唯一索引约束");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		
		Mi121 mi121 = new Mi121();
		mi121.setMxid(form.getMxid());
		mi121.setXh(form.getXh());
		mi121.setAnimateid(form.getAnid());
		mi121.setImgpath(form.getImgpath());
		mi121.setContentlink(form.getContentlink());
		mi121.setDisplaydirection(form.getDisplaydirection());		
		mi121.setValidflag(Constants.IS_VALIDFLAG);		
		mi121.setDatemodified(formatter.format(date));	
		mi121Dao.updateByPrimaryKeySelective(mi121);
	}
	public WebApi41108_queryResult webapi41108(CMi121 form) throws Exception {
		// TODO Auto-generated method stub
		WebApi41108_queryResult list = cmi121Dao.selectAllByList(form);
		if (CommonUtil.isEmpty(list)) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("图片动画明细信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"图片动画明细信息");
		}
		return list;
	}
	public String webapi41109(String magecenterId, MultipartFile file)
			throws Exception {
		// TODO Auto-generated method stub
		if (CommonUtil.isEmpty(magecenterId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		String filePath = CommonUtil.getFileFullPath("pushdhtp", magecenterId, true);
		String fileName = webApiUploadFile.uploadFile(filePath, file, 1024*5);
		return fileName;
	}	
}
