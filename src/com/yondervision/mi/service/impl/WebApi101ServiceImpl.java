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
import com.yondervision.mi.dao.CMi201DAO;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.result.WebApi20104_queryResult;
import com.yondervision.mi.service.WebApi101Service;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApp101ServiceImpl 
* @Description: 网点处理实现
* @author Caozhongyan
* @date Oct 4, 2013 3:47:38 PM   
* 
*/ 
public class WebApi101ServiceImpl implements WebApi101Service {
	protected final Logger log = LoggerUtil.getLogger();
	public Mi201DAO mi201Dao = null;
	
	public CMi201DAO cmi201Dao = null;
	
	private WebApiUploadFile webApiUploadFile=null;
	
	public WebApiUploadFile getWebApiUploadFile() {
		return webApiUploadFile;
	}

	public void setWebApiUploadFile(WebApiUploadFile webApiUploadFile) {
		this.webApiUploadFile = webApiUploadFile;
	}

	public Mi201DAO getMi201Dao() {
		return mi201Dao;
	}

	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}

	public CMi201DAO getCmi201Dao() {
		return cmi201Dao;
	}

	public void setCmi201Dao(CMi201DAO cmi201Dao) {
		this.cmi201Dao = cmi201Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void webapi10101(CMi201 form) throws Exception{
		// TODO Auto-generated method stub
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andWebsiteCodeEqualTo(form.getWebsiteCode());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi201> mi201List = mi201Dao.selectByExample(m201e);
		if(mi201List !=null && !mi201List.isEmpty()){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心网点编号己存在，违反唯一索引约束");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi201 mi201 = new Mi201();
		mi201.setAddress(form.getAddress());
		mi201.setAreaId(form.getAreaId());
		mi201.setBusinessType(form.getBusinessType());		
		mi201.setCenterid(form.getCenterId());		
		mi201.setDatecreated(formatter.format(date));
		mi201.setDatemodified(formatter.format(date));		
		mi201.setFreeuse1(form.getFreeuse1());
		mi201.setFreeuse2(form.getFreeuse2());
		mi201.setFreeuse3(form.getFreeuse3());
		mi201.setFreeuse4(form.getFreeuse4());
		mi201.setImageUrl(form.getImageUrl());
		mi201.setPositionX(form.getPositionX());
		mi201.setPositionY(form.getPositionY());
		mi201.setServiceTime(form.getServiceTime());
		mi201.setTel(form.getTel());
		mi201.setCooperationbank(form.getCooperationbank());//合作银行
		mi201.setValidflag(Constants.IS_VALIDFLAG);
		mi201.setWebsiteCode(form.getWebsiteCode());
		mi201.setWebsiteId(commonUtil.genKey("MI201", 20));
		mi201.setWebsiteName(form.getWebsiteName());
		mi201.setLoginid(form.getUserid());
		mi201.setWebsiteType(form.getWebsiteType());
		mi201Dao.insert(mi201);
	}

	public void webapi10102(CMi201 form) throws Exception{
		// TODO Auto-generated method stub
		String[] del = form.getDeletes().split(",");
		for(int i=0;i<del.length;i++){
//			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
//			Date date = new Date();
//			Mi201 mi201 = new Mi201();
//			mi201.setDatemodified(formatter.format(date));
//			mi201.setWebsiteId(del[i]);
//			mi201.setValidflag(Constants.IS_NOT_VALIDFLAG);
//			mi201Dao.updateByPrimaryKeySelective(mi201);
			mi201Dao.deleteByPrimaryKey(del[i]);
		}
		
	}

	public int webapi10103(CMi201 form) throws Exception{
		// TODO Auto-generated method stub
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andWebsiteCodeEqualTo(form.getWebsiteCode());
		ca.andWebsiteIdNotEqualTo(form.getWebsiteId());
		List<Mi201> mi201List = mi201Dao.selectByExample(m201e);
		if(mi201List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心网点编号己存在，违反唯一索引约束");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi201 mi201 = new Mi201();
		mi201.setAddress(form.getAddress());
		mi201.setAreaId(form.getAreaId());
		mi201.setBusinessType(form.getBusinessType());		
		mi201.setCenterid(form.getCenterId());
		mi201.setDatemodified(formatter.format(date));		
		mi201.setFreeuse1(form.getFreeuse1());
		mi201.setFreeuse2(form.getFreeuse2());
		mi201.setFreeuse3(form.getFreeuse3());
		mi201.setFreeuse4(form.getFreeuse4());
		mi201.setImageUrl(form.getImageUrl());
		mi201.setPositionX(form.getPositionX());
		mi201.setPositionY(form.getPositionY());
		mi201.setServiceTime(form.getServiceTime());
		mi201.setTel(form.getTel());		
		mi201.setWebsiteCode(form.getWebsiteCode());
		mi201.setWebsiteId(form.getWebsiteId());
		mi201.setWebsiteName(form.getWebsiteName());
		mi201.setWebsiteType(form.getWebsiteType());
		mi201.setCooperationbank(form.getCooperationbank());
		return mi201Dao.updateByPrimaryKeySelective(mi201);
	}

	public WebApi20104_queryResult webapi10104(CMi201 form) throws Exception{
		// TODO Auto-generated method stub
		//变更为分页查询
//		Mi201 mi201 = new Mi201();		
//		mi201.setCenterid(form.getCenterId());
//		mi201.setAddress(form.getAddress());
//		mi201.setAreaId(form.getAreaid());
//		mi201.setWebsiteName(form.getWebsiteName());
//		mi201.setServiceTime(form.getServiceTime());
//		mi201.setValidflag(Constants.IS_VALIDFLAG);
//		List<Mi201> list = cmi201Dao.selectWeb(mi201);
		//变更为分页查询
		WebApi20104_queryResult queryResult = cmi201Dao.selectWebPage(form);
		if (CommonUtil.isEmpty(queryResult.getList201())) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("网点信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"网点信息");
		}
		return queryResult;
	}

	public void webapi10105() throws Exception{
		// TODO Auto-generated method stub

	}

	public String webapi10106(String magecenterId, String imageid,
			MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(magecenterId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		String filePath = CommonUtil.getFileFullPath("push_website_img", magecenterId, true);
		String fileName = webApiUploadFile.uploadFile(filePath, file, 1024*5);
		return fileName;
	}

	public Mi201 webapi10107(String websiteId) throws Exception {
		// TODO Auto-generated method stub
		if (CommonUtil.isEmpty(websiteId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("websiteId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "网点代码");
		}
		Mi201 mi201 = mi201Dao.selectByPrimaryKey(websiteId);		
		return mi201;
	}
	
	
	public int webapi10103_image(CMi201 form) throws Exception{
		// TODO Auto-generated method stub	
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi201 mi201 = new Mi201();
		
		mi201.setImageUrl(form.getImageUrl());		
		mi201.setWebsiteId(form.getWebsiteId());		
		mi201.setDatemodified(formatter.format(date));
		return mi201Dao.updateByPrimaryKeySelective(mi201);
	}

}
