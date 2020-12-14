package com.yondervision.mi.dao.impl;

import java.util.List;

import org.omg.CORBA.COMM_FAILURE;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi039DAO;
import com.yondervision.mi.dto.CMi039;
import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.dto.Mi039Example;
import com.yondervision.mi.result.WebApi03904_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi039DAOImpl extends Mi039DAOImpl implements CMi039DAO {

	public WebApi03904_queryResult select039Page(CMi039 form) {
		Mi039Example mi039Example = new Mi039Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi039Example.Criteria ca = mi039Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getMessage())){
			ca.andMessageLike("%"+form.getMessage()+"%");
		}
		if(!CommonUtil.isEmpty(form.getType())){
			ca.andTypeEqualTo(form.getType());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi039> list = getSqlMapClientTemplate().queryForList("MI039.abatorgenerated_selectByExample", mi039Example, skipResults, rows);
		int total = this.countByExample(mi039Example);
		WebApi03904_queryResult queryResult = new WebApi03904_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList039(list);
		return queryResult;
	}

}
