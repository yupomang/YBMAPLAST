package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi106DAO;
import com.yondervision.mi.dto.CMi106;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi106Example;
import com.yondervision.mi.result.WebApi40304_queryResult;

/** 
* @ClassName: CMi105DAOImpl 
* @Description: APP用户注册信息查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi106DAOImpl extends Mi106DAOImpl implements CMi106DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectAll(com.yondervision.mi.dto.Mi203)
	 */	

	public WebApi40304_queryResult select106Page(CMi106 form) {
		// TODO Auto-generated method stub
		Mi106Example mi106Example = new Mi106Example();
		mi106Example.setOrderByClause("centerId asc, devtype asc,versionno desc");
		Mi106Example.Criteria ca = mi106Example.createCriteria();	
		if(!form.getCenterid().isEmpty()||!form.getCenterid().equals("")){
			ca.andCenteridEqualTo(form.getCenterid());
		}		
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi106> list = getSqlMapClientTemplate().queryForList("MI106.abatorgenerated_selectByExample", mi106Example, skipResults, rows);
		int total = this.countByExample(mi106Example);
		WebApi40304_queryResult queryResult = new WebApi40304_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList106(list);		
		return queryResult;
	}	
}
