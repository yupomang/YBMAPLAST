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
import com.yondervision.mi.dao.CMi029DAO;
import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.result.WebApi02904_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi029DAOImpl extends Mi029DAOImpl implements CMi029DAO {

	public WebApi02904_queryResult select029Page(CMi029 form, List<String> ids) {
		Mi029Example mi029Example = new Mi029Example();
		//mi029Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi029Example.Criteria ca = mi029Example.createCriteria();
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			UserContext instance = UserContext.getInstance();
			centerid = instance.getCenterid();
		}
		ca.andCenteridEqualTo(centerid);
		if(!CommonUtil.isEmpty(form.getPersonalname())){
			ca.andUsernameLike("%"+form.getPersonalname()+"%");
		}
		if(!CommonUtil.isEmpty(form.getCertinum())){
			ca.andCertinumLike("%"+form.getCertinum()+"%");
		}
		if(!CommonUtil.isEmpty(form.getTel())){
			ca.andTelLike("%"+form.getTel()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if(ids !=null && !ids.isEmpty()){
			ca.andPersonalidNotIn(ids);
		}
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi029> list = getSqlMapClientTemplate().queryForList("MI029.abatorgenerated_selectByExample", mi029Example, skipResults, rows);
		int total = this.countByExample(mi029Example);
		WebApi02904_queryResult queryResult = new WebApi02904_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList029(list);
		return queryResult;
	}

}
