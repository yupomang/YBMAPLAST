package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi052DAO;
import com.yondervision.mi.dto.CMi052;
import com.yondervision.mi.dto.Mi052;
import com.yondervision.mi.dto.Mi052Example;
import com.yondervision.mi.result.WebApi05204_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi052DAOImpl extends Mi052DAOImpl implements CMi052DAO {

	public WebApi05204_queryResult select052Page(CMi052 form) {
		Mi052Example mi052Example = new Mi052Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi052Example.Criteria ca = mi052Example.createCriteria();
		if(CommonUtil.isEmpty(form.getApiid())){
			ca.andApiidLike("%"+form.getApiid()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi052> list = getSqlMapClientTemplate().queryForList("MI052.abatorgenerated_selectByExample", mi052Example, skipResults, rows);
		int total = this.countByExample(mi052Example);
		WebApi05204_queryResult queryResult = new WebApi05204_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList052(list);
		return queryResult;
	}

}
