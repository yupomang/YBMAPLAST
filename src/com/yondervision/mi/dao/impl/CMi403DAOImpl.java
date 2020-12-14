/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi402DAO;
import com.yondervision.mi.dao.CMi403DAO;
import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.dto.Mi403;
import com.yondervision.mi.dto.Mi403Example;
import com.yondervision.mi.result.WebApi30202_queryResult;
import com.yondervision.mi.result.WebApi30206_queryResult;

/**
 * @author LinXiaolong
 *
 */
public class CMi403DAOImpl extends Mi403DAOImpl implements CMi403DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi402DAO#selectByExamplePagination(com.yondervision.mi.dto.Mi402Example, java.lang.Integer, java.lang.Integer)
	 */
	public WebApi30206_queryResult selectByExamplePagination(
			Mi403Example mi403Example, Integer page, Integer rows) {
		WebApi30206_queryResult result = new WebApi30206_queryResult();
		
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi403> listMi403 = getSqlMapClientTemplate().queryForList("MI403.abatorgenerated_selectByExampleWithBLOBs", mi403Example, skipResults, rows.intValue());
		int total = this.countByExample(mi403Example);
		
		result.setPageNumber(page);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setRows(listMi403);
		
		return result;
	}

	public int webapi403(String centerId, String pid, String startDate, 
			String endDate, String status, String pushType) throws Exception{
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerId);
		paraMap.put("pid", pid);
		paraMap.put("startdate", startDate);
		paraMap.put("enddate", endDate);
		paraMap.put("status", status);
		paraMap.put("pushType", pushType);
		@SuppressWarnings("unchecked")
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI403.webapi403", paraMap);
		return Integer.valueOf(String.valueOf(result.get(0).get("cnt")));
	}
	
	public List<HashMap> webapi40301(String centerId, String pid, String startDate, 
			String endDate) throws Exception{
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("centerId", centerId);
		paraMap.put("pid", pid);
		paraMap.put("startdate", startDate);
		paraMap.put("enddate", endDate);
		@SuppressWarnings("unchecked")
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI403.webapi40301", paraMap);
		return result;
	}
}
