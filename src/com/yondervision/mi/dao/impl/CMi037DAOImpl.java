package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi037DAO;
import com.yondervision.mi.dto.CMi037;
import com.yondervision.mi.dto.Mi037;
import com.yondervision.mi.dto.Mi037Example;
import com.yondervision.mi.result.WebApi03704_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi037DAOImpl extends Mi037DAOImpl implements CMi037DAO {

	public WebApi03704_queryResult select037Page(CMi037 form) {
		Mi037Example mi037Example = new Mi037Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi037Example.Criteria ca = mi037Example.createCriteria();
		if(CommonUtil.isEmpty(form.getName())){
			ca.andNameLike("%"+form.getName()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi037> list = getSqlMapClientTemplate().queryForList("MI037.abatorgenerated_selectByExample", mi037Example, skipResults, rows);
		int total = this.countByExample(mi037Example);
		WebApi03704_queryResult queryResult = new WebApi03704_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList037(list);
		return queryResult;
	}

}
