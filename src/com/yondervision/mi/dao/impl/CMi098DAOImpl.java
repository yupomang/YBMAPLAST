package com.yondervision.mi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi098DAO;
import com.yondervision.mi.dto.CMi107;

public class CMi098DAOImpl extends Mi098DAOImpl implements CMi098DAO{
	public List<HashMap> webapi107100301(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI098.webapi107100301", form);
		return result;
	}
	public List<HashMap> webapi107100302(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI098.webapi107100302", form);
		return result;
	}
	public List<HashMap> webapi1071004(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI098.webapi1071004", form);
		return result;
	}
	public List<HashMap> getLatestTransdate(){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI098.getLatestTransdate");
		return result;
	}
	public List<HashMap> selectAppointment(String transday, String currentDate){
		HashMap map = new HashMap();
		map.put("transday", transday);
		map.put("currentDate", currentDate);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI098.selectAppointment", map);
		return result;
	}
}
