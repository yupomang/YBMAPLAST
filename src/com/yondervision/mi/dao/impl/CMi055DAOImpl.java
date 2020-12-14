package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi055DAO;
import com.yondervision.mi.dto.CMi055;
import com.yondervision.mi.dto.Mi055;
import com.yondervision.mi.dto.Mi055Example;
import com.yondervision.mi.result.WebApi05504_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi055DAOImpl extends Mi055DAOImpl implements CMi055DAO {

	public WebApi05504_queryResult select055Page(CMi055 form) {
		Mi055Example mi055Example = new Mi055Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi055Example.Criteria ca = mi055Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getFuncid())){
			ca.andFuncidLike("%"+form.getFuncid()+"%");
		}
		if(!CommonUtil.isEmpty(form.getFuncname())){
			ca.andFuncnameLike("%"+form.getFuncname()+"%");
		}
		if(!CommonUtil.isEmpty(form.getUselevel())){
			ca.andUselevelEqualTo(form.getUselevel());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi055Example.setOrderByClause("funcid asc");
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi055> list = getSqlMapClientTemplate().queryForList("MI055.abatorgenerated_selectByExample", mi055Example, skipResults, rows);
		int total = this.countByExample(mi055Example);
		WebApi05504_queryResult queryResult = new WebApi05504_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList055(list);
		return queryResult;
	}

}
