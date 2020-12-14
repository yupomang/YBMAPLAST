/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     AppApi302ServiceImpl.java
 * 创建日期：2013-10-24
 */
package com.yondervision.mi.service.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.Mi102DAO;
import com.yondervision.mi.dto.Mi102;
import com.yondervision.mi.dto.Mi102Example;
import com.yondervision.mi.form.AppApi30101Form;
import com.yondervision.mi.service.AppApi301Service;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi301ServiceImpl implements AppApi301Service {

	private Mi102DAO mi102Dao;

	

	public Mi102DAO getMi102Dao() {
		return mi102Dao;
	}



	public void setMi102Dao(Mi102DAO mi102Dao) {
		this.mi102Dao = mi102Dao;
	}



	public List<Mi102> appApi30101Select(AppApi30101Form form)
			throws Exception {
		// TODO Auto-generated method stub
//		Mi102 mi102 = new Mi102();
		Mi102Example m102e=new Mi102Example();
		com.yondervision.mi.dto.Mi102Example.Criteria ca= m102e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		ca.andCustsvctypeEqualTo(form.getCustsvctype());
		List<Mi102> list=mi102Dao.selectByExample(m102e);
		return list;		
	}



	

}
