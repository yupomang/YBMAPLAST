package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi105DAO;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.dto.Mi105;
import com.yondervision.mi.dto.Mi105Example;
import com.yondervision.mi.result.WebApi40102_queryResult;

/** 
* @ClassName: CMi105DAOImpl 
* @Description: APP用户注册信息查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi105DAOImpl extends Mi105DAOImpl implements CMi105DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectAll(com.yondervision.mi.dto.Mi203)
	 */	

	public WebApi40102_queryResult select105Page(CMi103 form) {
		// TODO Auto-generated method stub
		Mi105Example mi105Example = new Mi105Example();
		mi105Example.setOrderByClause("devtype asc");
		Mi105Example.Criteria ca = mi105Example.createCriteria();		
		ca.andUserIdEqualTo(form.getUserId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi105> list = getSqlMapClientTemplate().queryForList("MI105.abatorgenerated_selectByExample", mi105Example, skipResults, rows);
		int total = this.countByExample(mi105Example);
		WebApi40102_queryResult queryResult = new WebApi40102_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList105(list);		
		return queryResult;
	}	
}
