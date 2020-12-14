package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi122DAO;
import com.yondervision.mi.dto.CMi122;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi122Example;
import com.yondervision.mi.result.WebApi12201_queryResult;
import com.yondervision.mi.result.WebApi40304_queryResult;

/** 
* @ClassName: CMi122DAOImpl 
* @Description: 消息主题类型分页查询
* @author Caozhongyan
* @date Jan 12, 2015 10:02:11 AM   
* 
*/ 
public class CMi122DAOImpl extends Mi122DAOImpl implements CMi122DAO {
	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi122DAO#select122Page(com.yondervision.mi.dto.Mi122)
	 */
	public WebApi12201_queryResult select122Page(CMi122 form) {
		// TODO Auto-generated method stub
		Mi122Example mi122Example = new Mi122Example();
		mi122Example.setOrderByClause("centerId asc,num asc");
		Mi122Example.Criteria ca = mi122Example.createCriteria();		
		if(!form.getCenterid().isEmpty()||!form.getCenterid().equals("")){
			ca.andCenteridEqualTo(form.getCenterid());
		}else{
			ca.andCenteridEqualTo(form.getCenterId());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi122> list = getSqlMapClientTemplate().queryForList("MI122.abatorgenerated_selectByExample", mi122Example, skipResults, rows);
		int total = this.countByExample(mi122Example);
		WebApi12201_queryResult queryResult = new WebApi12201_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList122(list);		
		return queryResult;
	}	
}
