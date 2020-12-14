/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dao.CMi043DAO;
import com.yondervision.mi.dto.CMi043;
import com.yondervision.mi.dto.Mi043;
import com.yondervision.mi.dto.Mi043Example;
import com.yondervision.mi.result.WebApi04304_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi043DAOImpl extends Mi043DAOImpl implements CMi043DAO {

	public WebApi04304_queryResult select043Page(CMi043 form) {
		Mi043Example mi043Example = new Mi043Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi043Example.Criteria ca = mi043Example.createCriteria();
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
		
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi043> list = getSqlMapClientTemplate().queryForList("MI043.abatorgenerated_selectByExample", mi043Example, skipResults, rows);
		int total = this.countByExample(mi043Example);
		WebApi04304_queryResult queryResult = new WebApi04304_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList043(list);
		return queryResult;
	}

}
