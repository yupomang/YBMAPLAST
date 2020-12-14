package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi057DAO;
import com.yondervision.mi.dto.CMi057;
import com.yondervision.mi.dto.Mi057;
import com.yondervision.mi.dto.Mi057Example;
import com.yondervision.mi.result.WebApi05701_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi057DAOImpl extends Mi057DAOImpl implements CMi057DAO {

	public WebApi05701_queryResult select057Page(CMi057 form) {
		Mi057Example mi057Example = new Mi057Example();
		mi057Example.setOrderByClause("datecreated desc");
		Mi057Example.Criteria ca = mi057Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterid())){
			ca.andCenteridLike("%"+form.getCenterid()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi057> list = getSqlMapClientTemplate().queryForList("MI057.abatorgenerated_selectByExample", mi057Example, skipResults, rows);
		int total = this.countByExample(mi057Example);
		WebApi05701_queryResult queryResult = new WebApi05701_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList057(list);
		return queryResult;
	}

}
