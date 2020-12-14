/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi042DAO;
import com.yondervision.mi.dto.CMi042;
import com.yondervision.mi.dto.Mi042;
import com.yondervision.mi.dto.Mi042Example;
import com.yondervision.mi.result.WebApi04204_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi042DAOImpl extends Mi042DAOImpl implements CMi042DAO {

	public WebApi04204_queryResult select042Page(CMi042 form) {
		Logger log = LoggerUtil.getLogger();
		Mi042Example mi042Example = new Mi042Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		//add by ZhiwenXu 2018-05-08
		mi042Example.setOrderByClause("datecreated desc");
		Mi042Example.Criteria ca = mi042Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterid())){
			log.info("中心："+form.getCenterid());
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			log.info("渠道："+form.getChannel());
			ca.andChannelEqualTo(form.getChannel());
		}
		if(!CommonUtil.isEmpty(form.getPid())){
			log.info("应用："+form.getPid());
			ca.andPidEqualTo(form.getPid());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi042> list = getSqlMapClientTemplate().queryForList("MI042.abatorgenerated_selectByExample", mi042Example, skipResults, rows);
		int total = this.countByExample(mi042Example);
		WebApi04204_queryResult queryResult = new WebApi04204_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList042(list);
		return queryResult;
	}

}
