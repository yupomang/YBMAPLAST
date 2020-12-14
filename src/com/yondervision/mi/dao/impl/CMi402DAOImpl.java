/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi402DAO;
import com.yondervision.mi.dto.Mi402;
import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.result.WebApi30202_queryResult;

/**
 * @author LinXiaolong
 *
 */
public class CMi402DAOImpl extends Mi402DAOImpl implements CMi402DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi402DAO#selectByExamplePagination(com.yondervision.mi.dto.Mi402Example, java.lang.Integer, java.lang.Integer)
	 */
	public WebApi30202_queryResult selectByExamplePagination(
			Mi402Example mi402Example, Integer page, Integer rows) {
		WebApi30202_queryResult result = new WebApi30202_queryResult();
		
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi402> listMi402 = getSqlMapClientTemplate().queryForList("MI402.abatorgenerated_selectByExampleWithBLOBs", mi402Example, skipResults, rows.intValue());
		int total = this.countByExample(mi402Example);
		
		result.setPageNumber(page);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setRows(listMi402);
		
		return result;
	}

}
