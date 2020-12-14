package com.yondervision.mi.service.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.Mi051DAO;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.form.AppApi05101Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi051Service;
import com.yondervision.mi.util.CommonUtil;

public class AppApi051ServiceImpl implements AppApi051Service {
	
	private Mi051DAO mi051Dao = null;

	public Mi051DAO getMi051Dao() {
		return mi051Dao;
	}

	public void setMi051Dao(Mi051DAO mi051Dao) {
		this.mi051Dao = mi051Dao;
	}

	public Mi051 appApi05101Select(AppApi05101Form form) throws Exception{
		if(CommonUtil.isEmpty(form.getBuzType())){
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"渠道信息");
		}
		Mi051Example example = new Mi051Example();
		example.createCriteria().andBuztypeEqualTo(form.getCheckBuzType()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi051> list = mi051Dao.selectByExample(example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}
}
