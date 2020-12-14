package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi131DAO;
import com.yondervision.mi.dto.Mi131;
import com.yondervision.mi.dto.Mi131WithBLOBs;

public class CMi131DAOImpl extends Mi131DAOImpl implements CMi131DAO{

	public List<Mi131WithBLOBs> selectByExample(Mi131 mi131, int skipResults,int rows) {
		 List<Mi131WithBLOBs> list = getSqlMapClientTemplate().queryForList("CMI131.selectPicInfoByGroup", mi131,skipResults,rows);
	     return list;
	}

}
