package com.yondervision.mi.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi120DAO;
import com.yondervision.mi.dao.Mi121DAO;
import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi120Example;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.dto.Mi121Example;
import com.yondervision.mi.form.AppApi41101Form;
import com.yondervision.mi.service.AppApi411Service;
import com.yondervision.mi.util.CommonUtil;


/** 
* @ClassName: WebApi411ServiceImpl 
* @Description: TODO
* @author Caozhongyan
* @date Nov 14, 2013 3:44:22 PM   
* 
*/ 
public class AppApi411ServiceImpl implements AppApi411Service {
	
	private Mi120DAO mi120Dao = null;
	
	private Mi121DAO mi121Dao = null;
	
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
	
	public List<Mi120> appapi41101(AppApi41101Form form) throws Exception{
		// TODO Auto-generated method stub
		Mi120Example m120e=new Mi120Example();
		com.yondervision.mi.dto.Mi120Example.Criteria ca= m120e.createCriteria();
		ca.andAnimatecodeEqualTo(form.getAnimatecode());		
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andDevidEqualTo(form.getDeviceType());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return mi120Dao.selectByExample(m120e);
	}

	public List<Mi121> appapi41102(Mi120 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getAnimateid())){
			log.error(ERROR.PARAMS_NULL.getLogText("Animateid"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"动画ID");
		}
		Mi121Example m121e=new Mi121Example();
		com.yondervision.mi.dto.Mi121Example.Criteria ca= m121e.createCriteria();
		m121e.setOrderByClause("xh asc");
		ca.andAnimateidEqualTo(form.getAnimateid());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return mi121Dao.selectByExample(m121e);
	}
}
