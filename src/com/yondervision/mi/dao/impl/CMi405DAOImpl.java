package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi405DAO;
import com.yondervision.mi.dto.Mi405;
import com.yondervision.mi.dto.Mi405Example;
import com.yondervision.mi.result.WebApi40502_queryResult;

public class CMi405DAOImpl extends Mi405DAOImpl implements CMi405DAO {

	public WebApi40502_queryResult select40502Page(Mi405Example example, String spage, String srows) {
		int page = Integer.valueOf(spage);
		int rows = Integer.valueOf(srows);
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(15) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi405> list = getSqlMapClientTemplate().queryForList("MI405.abatorgenerated_selectByExample", example, skipResults, rows);
		int total = this.countByExample(example);
		WebApi40502_queryResult queryResult = new WebApi40502_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList122(list);		
		return queryResult;
	}

}	

