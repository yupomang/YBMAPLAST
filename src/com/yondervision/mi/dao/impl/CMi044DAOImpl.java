package com.yondervision.mi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi044DAO;
import com.yondervision.mi.dto.CMi044;
import com.yondervision.mi.dto.Mi044;
import com.yondervision.mi.dto.Mi044Example;
import com.yondervision.mi.result.WebApi04404_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi044DAOImpl extends Mi044DAOImpl implements CMi044DAO {

	public WebApi04404_queryResult select044Page(CMi044 form) {
		Logger log = LoggerUtil.getLogger();
		Mi044Example mi044Example = new Mi044Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi044Example.Criteria ca = mi044Example.createCriteria();
		
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		ca.andCenteridEqualTo(centerid);
		
		if(!CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelEqualTo(form.getChannel());
		} 
		if(!CommonUtil.isEmpty(form.getPid())){
			ca.andPidEqualTo(form.getPid());
		}
		if(!CommonUtil.isEmpty(form.getBuztype())){
			ca.andBuztypeEqualTo(form.getBuztype());
		}
		if(!CommonUtil.isEmpty(form.getStartDate())&&!CommonUtil.isEmpty(form.getEndDate())){
			ca.andDatecreatedGreaterThan(form.getStartDate());
			ca.andDatecreatedLessThan(form.getEndDate());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi044> list = getSqlMapClientTemplate().queryForList("MI044.abatorgenerated_selectByExample", mi044Example, skipResults, rows);
		int total = this.countByExample(mi044Example);
		WebApi04404_queryResult queryResult = new WebApi04404_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList044(list);
		return queryResult;
	}

}
