package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi005DAO;
import com.yondervision.mi.dto.CMi005;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.result.WebApi00504_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi005DAOImpl extends Mi005DAOImpl implements CMi005DAO {

	public WebApi00504_queryResult select005Page(CMi005 form) {
		Mi005Example mi005Example = new Mi005Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi005Example.Criteria ca = mi005Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getFuncname())){
			ca.andFuncnameLike("%"+form.getFuncname()+"%");
		}
		if(!"00000000".equals(form.getCenterid())){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi005> list = getSqlMapClientTemplate().queryForList("MI005.abatorgenerated_selectByExample", mi005Example, skipResults, rows);
		int total = this.countByExample(mi005Example);
		WebApi00504_queryResult queryResult = new WebApi00504_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList005(list);
		return queryResult;
	}

}
