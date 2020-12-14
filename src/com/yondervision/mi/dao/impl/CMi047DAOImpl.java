package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi047DAO;
import com.yondervision.mi.dto.CMi047;
import com.yondervision.mi.dto.Mi047;
import com.yondervision.mi.dto.Mi047Example;
import com.yondervision.mi.result.WebApi04704_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi047DAOImpl extends Mi047DAOImpl implements CMi047DAO {

	public WebApi04704_queryResult select047Page(CMi047 form) {
		Mi047Example mi047Example = new Mi047Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi047Example.Criteria ca = mi047Example.createCriteria();
		if(!"00000000".equals(form.getCenterid())){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getName())){
			ca.andNameLike("%"+form.getName()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi047> list = getSqlMapClientTemplate().queryForList("MI047.abatorgenerated_selectByExample", mi047Example, skipResults, rows);
		int total = this.countByExample(mi047Example);
		WebApi04704_queryResult queryResult = new WebApi04704_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList047(list);
		return queryResult;
	}

}
