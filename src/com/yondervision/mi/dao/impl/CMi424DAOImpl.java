package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi424DAO;

public class CMi424DAOImpl extends Mi424DAOImpl implements CMi424DAO{
	public List<HashMap> webapi42401(String centerid, String pid, 
			String startDate, String endDate){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("centerid", centerid);
		map.put("pid", pid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("CMI424.webapi42401", map);
	}
	public List<HashMap> webapi42402(String centerid, String pid, 
			String startDate, String endDate){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("centerid", centerid);
		map.put("pid", pid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("CMI424.webapi42402", map);
	}
}
