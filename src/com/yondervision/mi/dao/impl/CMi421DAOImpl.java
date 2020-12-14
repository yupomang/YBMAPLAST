package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi421DAO;
import com.yondervision.mi.dto.Mi421Example;

public class CMi421DAOImpl extends Mi421DAOImpl implements CMi421DAO{
	public void batchInsert(String date){
		getSqlMapClientTemplate().insert("CMI421.insertBatch", date);
	}
	public List selectByExamplePageWithBlobs(Mi421Example mi421Example, int skipResults, int rows){
    	return getSqlMapClientTemplate().queryForList("MI421.abatorgenerated_selectByExampleWithBLOBs", mi421Example, skipResults, rows);
    }
}
