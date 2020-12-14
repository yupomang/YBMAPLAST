package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.CMi422DAO;

public class CMi422DAOImpl extends Mi422DAOImpl implements CMi422DAO{
	public void batchInsert(String date){
		getSqlMapClientTemplate().insert("CMI422.insertBatch", date);
	}
}
