package com.yondervision.mi.service.impl;

import com.yondervision.mi.dao.Mi103DAO;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.service.AppApi103Service;

public class AppApi103ServiceImpl implements AppApi103Service {
	private Mi103DAO mi103Dao = null;
	public Mi103 appApi10301Select(String useidString) throws Exception {
		Mi103 result = new Mi103();		
		result = mi103Dao.selectByPrimaryKey(useidString);		
		return result;
	}
	public Mi103DAO getMi103Dao() {
		return mi103Dao;
	}
	public void setMi103Dao(Mi103DAO mi103Dao) {
		this.mi103Dao = mi103Dao;
	}

	
}
