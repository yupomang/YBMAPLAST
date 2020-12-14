package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dao.CMi053DAO;
import com.yondervision.mi.dto.CMi053;
import com.yondervision.mi.dto.Mi053;
import com.yondervision.mi.dto.Mi053Example;
import com.yondervision.mi.result.WebApi05304_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi053DAOImpl extends Mi053DAOImpl implements CMi053DAO {

	public WebApi05304_queryResult select053Page(CMi053 form) {
		Mi053Example mi053Example = new Mi053Example();
		mi053Example.setOrderByClause("pid asc, datecreated desc");
		Mi053Example.Criteria ca = mi053Example.createCriteria();
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		ca.andCenteridEqualTo(centerid);
		if(!CommonUtil.isEmpty(form.getPid())){
			ca.andPidEqualTo(form.getPid());
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelLike("%"+form.getChannel()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi053> list = getSqlMapClientTemplate().queryForList("MI053.abatorgenerated_selectByExample", mi053Example, skipResults, rows);
		int total = this.countByExample(mi053Example);
		WebApi05304_queryResult queryResult = new WebApi05304_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList053(list);
		return queryResult;
	}

}
