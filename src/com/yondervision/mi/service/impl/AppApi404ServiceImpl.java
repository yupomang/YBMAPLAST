package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.dao.Mi108DAO;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi106Example;
import com.yondervision.mi.dto.Mi108;
import com.yondervision.mi.form.AppApi40401Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi40301Result;
import com.yondervision.mi.service.AppApi404Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi403ServiceImpl 
* @Description: 意见反馈
* @author Caozhongyan
* @date Oct 15, 2013 3:42:17 PM   
* 
*/ 
public class AppApi404ServiceImpl implements AppApi404Service {

	private Mi108DAO mi108Dao = null;
	
	public Mi108DAO getMi108Dao() {
		return mi108Dao;
	}

	public void setMi108Dao(Mi108DAO mi108Dao) {
		this.mi108Dao = mi108Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void appapi40401(AppApi40401Form form) throws Exception {
		// TODO Auto-generated method stub		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi108 mi108 = new Mi108();
		mi108.setSeqno(commonUtil.genKey("MI108").intValue());
		mi108.setCenterid(form.getCenterId());
		mi108.setUserid(CommonUtil.isEmpty(form.getUserId())?"匿名":form.getUserId());
		mi108.setVersionno(form.getCurrenVersion());
		mi108.setDevtype(form.getDeviceType());
		mi108.setDevid(form.getDeviceToken());
		mi108.setDetail(form.getMessage());
		mi108.setStatus("0");
		mi108.setValidflag(Constants.IS_VALIDFLAG);
		mi108.setDatecreated(formatter.format(date));
		mi108.setDatemodified(formatter.format(date));
		mi108.setChannel(form.getChannel());
		mi108.setTel(form.getTel());
		mi108.setEmail(form.getEmail());
		mi108Dao.insert(mi108);
	}

}
