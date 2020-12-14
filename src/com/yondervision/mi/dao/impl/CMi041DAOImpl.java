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
import com.yondervision.mi.dao.CMi041DAO;
import com.yondervision.mi.dto.CMi041;
import com.yondervision.mi.dto.Mi041;
import com.yondervision.mi.dto.Mi041Example;
import com.yondervision.mi.result.WebApi04104_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi041DAOImpl extends Mi041DAOImpl implements CMi041DAO {

	public WebApi04104_queryResult select041Page(CMi041 form) {
		Mi041Example mi041Example = new Mi041Example();
		mi041Example.setOrderByClause("datecreated desc");
		Mi041Example.Criteria ca = mi041Example.createCriteria();
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
		if(!CommonUtil.isEmpty(form.getCheckurl())){
			ca.andCheckurlLike("%"+form.getCheckurl()+"%");
		}
		if(!CommonUtil.isEmpty(form.getMonitor())){
			ca.andMonitorEqualTo(form.getMonitor());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi041> list = getSqlMapClientTemplate().queryForList("MI041.abatorgenerated_selectByExample", mi041Example, skipResults, rows);
		int total = this.countByExample(mi041Example);
		WebApi04104_queryResult queryResult = new WebApi04104_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList041(list);
		return queryResult;
	}

}
