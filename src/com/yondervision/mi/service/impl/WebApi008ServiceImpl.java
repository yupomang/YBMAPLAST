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
import com.yondervision.mi.dao.CMi203DAO;
import com.yondervision.mi.dao.Mi203DAO;
import com.yondervision.mi.dto.CMi203;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.dto.Mi203Example;
import com.yondervision.mi.result.WebApi20304_queryResult;
import com.yondervision.mi.service.WebApi008Service;
import com.yondervision.mi.service.WebApiUploadFile;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi008ServiceImpl 
* @Description: 楼盘处理实现
* @author Caozhongyan
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi008ServiceImpl implements WebApi008Service {
	protected final Logger log = LoggerUtil.getLogger();
	public Mi203DAO mi203Dao = null;
	
	private WebApiUploadFile webApiUploadFile=null;
	
	public WebApiUploadFile getWebApiUploadFile() {
		return webApiUploadFile;
	}

	public void setWebApiUploadFile(WebApiUploadFile webApiUploadFile) {
		this.webApiUploadFile = webApiUploadFile;
	}
	public Mi203DAO getMi203Dao() {
		return mi203Dao;
	}

	public void setMi203Dao(Mi203DAO mi203Dao) {
		this.mi203Dao = mi203Dao;
	}

	private CMi203DAO cmi203Dao = null;
	
	public CMi203DAO getCmi203Dao() {
		return cmi203Dao;
	}

	public void setCmi203Dao(CMi203DAO cmi203Dao) {
		this.cmi203Dao = cmi203Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public void webapi00801(CMi203 form) throws Exception{
		// TODO Auto-generated method stub
		Mi203Example m203e=new Mi203Example();
		com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andHouseCodeEqualTo(form.getHouseCode());
		List<Mi203> mi203List = mi203Dao.selectByExample(m203e);
		if(mi203List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心楼盘编号己存在，违反唯一索引约束");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi203 mi203 = new Mi203();
		mi203.setAddress(form.getAddress());
		mi203.setAreaId(form.getAreaId());
		mi203.setBankNames(form.getBankNames());
		mi203.setCenterid(form.getCenterId());
		mi203.setContacterName(form.getContacterName());
		mi203.setDatecreated(formatter.format(date));
		mi203.setDatemodified(formatter.format(date));
		mi203.setDeveloperName(form.getDeveloperName());
		mi203.setFreeuse1(form.getFreeuse1());
		mi203.setFreeuse2(form.getFreeuse2());
		mi203.setFreeuse3(form.getFreeuse3());
		mi203.setFreeuse4(form.getFreeuse4());
		mi203.setHouseCode(form.getHouseCode());
		mi203.setHouseName(form.getHouseName());
		mi203.setHousesId(commonUtil.genKey("MI203", 20));
		mi203.setHouseType(form.getHouseType());
		mi203.setImageUrl(form.getImageUrl());
		mi203.setPositionX(form.getPositionX());
		mi203.setPositionY(form.getPositionY());
		mi203.setTel(form.getTel());
		mi203.setValidflag(Constants.IS_VALIDFLAG);
		mi203.setLoginid(form.getUserid());
		mi203Dao.insert(mi203);
	}

	public void webapi00802(CMi203 form) throws Exception{
		// TODO Auto-generated method stub
		String[] del = form.getListHousesId().split(",");
		for(int i=0;i<del.length;i++){
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			Date date = new Date();
			Mi203 mi203 = new Mi203();
			mi203.setDatemodified(formatter.format(date));
			mi203.setHousesId(del[i]);
			mi203.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi203Dao.updateByPrimaryKeySelective(mi203);	
		}
	}

	public int webapi00803(CMi203 form) throws Exception{
		// TODO Auto-generated method stub
		Mi203Example m203e=new Mi203Example();
		com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andHouseCodeEqualTo(form.getHouseCode());
		ca.andHousesIdNotEqualTo(form.getHousesId());
		List<Mi203> mi203List = mi203Dao.selectByExample(m203e);
		if(mi203List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"中心楼盘编号己存在，违反唯一索引约束");
		}
		
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi203 mi203 = new Mi203();
		mi203.setAddress(form.getAddress());
		mi203.setAreaId(form.getAreaId());
		mi203.setBankNames(form.getBankNames());
		mi203.setCenterid(form.getCenterId());
		mi203.setContacterName(form.getContacterName());		
		mi203.setDatemodified(formatter.format(date));
		mi203.setDeveloperName(form.getDeveloperName());
		mi203.setFreeuse1(form.getFreeuse1());
		mi203.setFreeuse2(form.getFreeuse2());
		mi203.setFreeuse3(form.getFreeuse3());
		mi203.setFreeuse4(form.getFreeuse4());
		mi203.setHouseCode(form.getHouseCode());
		mi203.setHouseName(form.getHouseName());
		mi203.setHousesId(form.getHousesId());
		mi203.setHouseType(form.getHouseType());
		mi203.setImageUrl(form.getImageUrl());
		mi203.setPositionX(form.getPositionX());
		mi203.setPositionY(form.getPositionY());
		mi203.setTel(form.getTel());
		return mi203Dao.updateByPrimaryKeySelective(mi203);		
	}

	public WebApi20304_queryResult webapi00804(CMi203 form) throws Exception{
		// TODO Auto-generated method stub
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		//不分页查询，自定义sql_map 20131028
//		Mi203 mi203 = new Mi203();
//		mi203.setCenterid(form.getCenterId());
//		mi203.setAddress(form.getAddress());
//		mi203.setBankNames(form.getBankNames());
//		mi203.setDeveloperName(form.getDeveloperName());	
//		mi203.setHouseName(form.getHouseName());		
//		mi203.setHouseType(form.getHouseType());	
//		mi203.setValidflag(Constants.IS_VALIDFLAG);
//		List<Mi203> list = cmi203Dao.selectWeb(mi203);
		//不分页查询，自定义sql_map 20131028
			
		
		WebApi20304_queryResult list = cmi203Dao.selectWebPage(form);
		if (CommonUtil.isEmpty(list)) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("楼盘信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"楼盘信息");
		}
		return list;
	}

	public void webapi00805() throws Exception{
		// TODO Auto-generated method stub
		
	}

	public String webapi00806(String magecenterId, String imageid, MultipartFile file)
			throws Exception {
		// TODO Auto-generated method stub
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(magecenterId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		String filePath = CommonUtil.getFileFullPath("push_house_img", magecenterId, true);
		String fileName = webApiUploadFile.uploadFile(filePath, file, 1024*5);
		return fileName;
	}	

	public void webapi00807(String magecenterId, String houseId, String filePath)
			throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		if (CommonUtil.isEmpty(magecenterId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if (CommonUtil.isEmpty(houseId)) {
			log.error(ERROR.PARAMS_NULL.getLogText("houseId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "楼盘代码");
		}
		Mi203 mi203 = new Mi203();
		mi203.setHousesId(houseId);
		mi203.setImageUrl(filePath);
		mi203.setDatemodified(formatter.format(date));
		mi203Dao.updateByPrimaryKeySelective(mi203);
	}

	public Mi203 webapi00808(String houseId) throws Exception {
		// TODO Auto-generated method stub
		Mi203 mi203 = mi203Dao.selectByPrimaryKey(houseId);		
		return mi203;
	}	
}
