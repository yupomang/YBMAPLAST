package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi712DAO;

public class CMi712DAOImpl extends Mi712DAOImpl implements CMi712DAO {

    @SuppressWarnings("unchecked")
	public List<HashMap> webapi712(String centerId,
			String startDate, String endDate,String pid) throws Exception{
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerId);
		paraMap.put("startdate", startDate);
		paraMap.put("enddate", endDate);
		paraMap.put("pid", pid);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI712.webapi712", paraMap);
		return result;
	}
}