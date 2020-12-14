package com.yondervision.mi.service.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.Mi202DAO;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi202Example;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi202Service;

/** 
* @ClassName: AppApi202ServiceImpl 
* @Description: 区域
* @author Caozhongyan
* @date Oct 11, 2013 4:59:40 PM   
* 
*/ 
public class AppApi202ServiceImpl implements AppApi202Service {
	
	private Mi202DAO mi202Dao = null;
	
	public Mi202DAO getMi202Dao() {
		return mi202Dao;
	}

	public void setMi202Dao(Mi202DAO mi202Dao) {
		this.mi202Dao = mi202Dao;
	}

	public List<Mi202> appapi20201(AppApiCommonForm form) throws Exception {
		// TODO Auto-generated method stub
		Mi202Example m202e=new Mi202Example();
		m202e.setOrderByClause("freeuse4 asc");
		com.yondervision.mi.dto.Mi202Example.Criteria ca= m202e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi202> list=mi202Dao.selectByExample(m202e);
		return list;
	}

}
