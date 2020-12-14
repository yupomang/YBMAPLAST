package com.yondervision.mi.service.impl;

import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi804Service;

public class AppApi804ServiceImpl implements AppApi804Service {

	private Mi001DAO mi001Dao = null;
	
	
	public Mi001 appapi80401(AppApiCommonForm form) throws Exception {
		// TODO Auto-generated method stub
		Mi001 mi001 = mi001Dao.selectByPrimaryKey(form.getCenterId());
		return mi001;
	}

	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}

	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	
}
