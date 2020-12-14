/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi100DAO;
import com.yondervision.mi.dto.Mi100;
import com.yondervision.mi.dto.Mi100Example;
import com.yondervision.mi.result.WebApi100_queryResult;

/**
 * @author LinXiaolong
 *
 */
public class CMi100DAOImpl extends Mi100DAOImpl implements CMi100DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi402DAO#selectByExamplePagination(com.yondervision.mi.dto.Mi402Example, java.lang.Integer, java.lang.Integer)
	 */
	public WebApi100_queryResult selectByExamplePagination(
			Mi100Example mi100Example, Integer page, Integer rows) {
		WebApi100_queryResult result = new WebApi100_queryResult();
		
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi100> listMi100 = getSqlMapClientTemplate().queryForList("MI100.abatorgenerated_selectByExample", mi100Example, skipResults, rows.intValue());
		int total = this.countByExample(mi100Example);
		
		result.setPageNumber(page);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setRows(listMi100);
		
		return result;
	}

}
