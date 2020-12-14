package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi423DAO;

public class CMi423DAOImpl extends Mi423DAOImpl implements CMi423DAO{
	public void batchInsert(String date){
		getSqlMapClientTemplate().insert("CMI423.insertBatch", date);
	}
	
	public List<HashMap> messageStatistics(String date){
		return getSqlMapClientTemplate().queryForList("CMI423.messageStatistics", date);
	}
	
	public List<HashMap> messageStatisticsTemplate(String date){
		return getSqlMapClientTemplate().queryForList("CMI423.messageStatisticsTemplate", date);
	}
}
