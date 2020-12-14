package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi411DAO;
import com.yondervision.mi.dto.CMi411;
import com.yondervision.mi.dto.Mi411;
import com.yondervision.mi.dto.Mi411Example;
import com.yondervision.mi.result.WebApi41104Query_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi411DAOImpl extends Mi411DAOImpl implements CMi411DAO {

	public WebApi41104Query_queryResult select411Page(CMi411 form) {
		Mi411Example mi411Example = new Mi411Example();
		Mi411Example.Criteria ca = mi411Example.createCriteria();
		if(!"00000000".equals(form.getCenterid())){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getTheme())){
			ca.andThemeEqualTo(form.getTheme());
		}
		
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi411> list = getSqlMapClientTemplate().queryForList("MI411.abatorgenerated_selectByExample", mi411Example, skipResults, rows);
		int total = this.countByExample(mi411Example);
		WebApi41104Query_queryResult queryResult = new WebApi41104Query_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList411(list);
		return queryResult;
	}

}
