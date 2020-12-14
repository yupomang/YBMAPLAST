package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi045DAO;
import com.yondervision.mi.dto.CMi045;
import com.yondervision.mi.dto.Mi045;
import com.yondervision.mi.dto.Mi045Example;
import com.yondervision.mi.result.WebApi04504_queryResult;

public class CMi045DAOImpl extends Mi045DAOImpl implements CMi045DAO {

	public WebApi04504_queryResult select045Page(CMi045 form) {
		Mi045Example mi045Example = new Mi045Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi045Example.Criteria ca = mi045Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi045> list = getSqlMapClientTemplate().queryForList("MI045.abatorgenerated_selectByExample", mi045Example, skipResults, rows);
		int total = this.countByExample(mi045Example);
		WebApi04504_queryResult queryResult = new WebApi04504_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList045(list);
		return queryResult;
	}

}
