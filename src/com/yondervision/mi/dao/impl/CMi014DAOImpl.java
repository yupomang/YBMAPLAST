package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi014DAO;
import com.yondervision.mi.dto.CMi014;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi014Example;
import com.yondervision.mi.result.WebApi01404_queryResult;

public class CMi014DAOImpl extends Mi014DAOImpl implements CMi014DAO {

	public WebApi01404_queryResult select014Page(CMi014 form) {
		Mi014Example mi014Example = new Mi014Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi014Example.Criteria ca = mi014Example.createCriteria();
		
		//ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi014> list = getSqlMapClientTemplate().queryForList("MI014.abatorgenerated_selectByExample", mi014Example, skipResults, rows);
		int total = this.countByExample(mi014Example);
		WebApi01404_queryResult queryResult = new WebApi01404_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList014(list);
		return queryResult;
	}

}
