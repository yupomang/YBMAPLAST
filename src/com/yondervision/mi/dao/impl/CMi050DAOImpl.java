package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi050DAO;
import com.yondervision.mi.dto.CMi050;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi050Example;
import com.yondervision.mi.result.WebApi05004_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi050DAOImpl extends Mi050DAOImpl implements CMi050DAO {

	public WebApi05004_queryResult select050Page(CMi050 form) {
		Mi050Example mi050Example = new Mi050Example();
		mi050Example.setOrderByClause("datecreated desc");
		Mi050Example.Criteria ca = mi050Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getApiname())){
			ca.andApinameLike("%"+form.getApiname()+"%");
		}
		if(!CommonUtil.isEmpty(form.getApimsg())){
			ca.andApimsgLike("%"+form.getApimsg()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi050> list = getSqlMapClientTemplate().queryForList("MI050.abatorgenerated_selectByExample", mi050Example, skipResults, rows);
		int total = this.countByExample(mi050Example);
		WebApi05004_queryResult queryResult = new WebApi05004_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList050(list);
		return queryResult;
	}

}
