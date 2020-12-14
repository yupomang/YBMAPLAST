package com.yondervision.mi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi036DAO;
import com.yondervision.mi.dto.CMi036;
import com.yondervision.mi.dto.Mi036;
import com.yondervision.mi.dto.Mi036Example;
import com.yondervision.mi.result.WebApi03604_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi036DAOImpl extends Mi036DAOImpl implements CMi036DAO {

	public WebApi03604_queryResult select036Page(CMi036 form) {
		Logger log = LoggerUtil.getLogger();
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
		Mi036Example mi036Example = new Mi036Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi036Example.Criteria ca = mi036Example.createCriteria();
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		ca.andCenteridEqualTo(centerid);
		if(!CommonUtil.isEmpty(form.getPid())){
			ca.andPidEqualTo(form.getPid());
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelEqualTo(form.getChannel());
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
		List<Mi036> list = getSqlMapClientTemplate().queryForList("MI036.abatorgenerated_selectByExample", mi036Example, skipResults, rows);
		int total = this.countByExample(mi036Example);
		WebApi03604_queryResult queryResult = new WebApi03604_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList036(list);
		log.info("CMI036DAO层测试结果返回："+queryResult.getTotal());
		return queryResult;
	}

}
