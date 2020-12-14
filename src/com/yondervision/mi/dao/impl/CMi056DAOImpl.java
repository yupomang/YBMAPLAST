package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi056DAO;
import com.yondervision.mi.dto.CMi056;
import com.yondervision.mi.dto.Mi056;
import com.yondervision.mi.dto.Mi056Example;
import com.yondervision.mi.result.WebApi05604_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi056DAOImpl extends Mi056DAOImpl implements CMi056DAO {

	public WebApi05604_queryResult select056Page(CMi056 form) {
		Mi056Example mi056Example = new Mi056Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi056Example.Criteria ca = mi056Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getUrlname())){
			ca.andUrlnameLike("%"+form.getUrlname()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi056Example.setOrderByClause("orderid");
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi056> list = getSqlMapClientTemplate().queryForList("MI056.abatorgenerated_selectByExample", mi056Example, skipResults, rows);
		int total = this.countByExample(mi056Example);
		WebApi05604_queryResult queryResult = new WebApi05604_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList056(list);
		return queryResult;
	}

}
