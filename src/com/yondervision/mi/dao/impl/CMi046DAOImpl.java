package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi046DAO;
import com.yondervision.mi.dto.CMi046;
import com.yondervision.mi.dto.Mi046;
import com.yondervision.mi.dto.Mi046Example;
import com.yondervision.mi.result.WebApi04604_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi046DAOImpl extends Mi046DAOImpl implements CMi046DAO {

	public WebApi04604_queryResult select046Page(CMi046 form) {
		Mi046Example mi046Example = new Mi046Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi046Example.Criteria ca = mi046Example.createCriteria();
		if(!"00000000".equals(form.getCenterid())){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelLike("%"+form.getChannel()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi046> list = getSqlMapClientTemplate().queryForList("MI046.abatorgenerated_selectByExample", mi046Example, skipResults, rows);
		int total = this.countByExample(mi046Example);
		WebApi04604_queryResult queryResult = new WebApi04604_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList046(list);
		return queryResult;
	}

}
