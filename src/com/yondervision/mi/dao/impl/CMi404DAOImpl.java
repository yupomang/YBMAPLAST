/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi404DAO;
import com.yondervision.mi.dto.Mi404;
import com.yondervision.mi.dto.Mi404Example;
import com.yondervision.mi.result.WebApi30205_queryResult;

/**
 * @author LinXiaolong
 *
 */
public class CMi404DAOImpl extends Mi404DAOImpl implements CMi404DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi402DAO#selectByExamplePagination(com.yondervision.mi.dto.Mi402Example, java.lang.Integer, java.lang.Integer)
	 */
	public WebApi30205_queryResult selectByExamplePagination(
			Mi404Example mi404Example, Integer page, Integer rows) {
		WebApi30205_queryResult result = new WebApi30205_queryResult();
		
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi404> listMi404 = getSqlMapClientTemplate().queryForList("MI404.abatorgenerated_selectByExampleWithBLOBs", mi404Example, skipResults, rows.intValue());
		int total = this.countByExample(mi404Example);
		
		result.setPageNumber(page);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setRows(listMi404);
		
		return result;
	}

}
