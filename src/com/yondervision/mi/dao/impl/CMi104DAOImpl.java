package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi104DAO;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.Mi104;
import com.yondervision.mi.dto.Mi104Example;
import com.yondervision.mi.result.WebApi40103_queryResult;

/** 
* @ClassName: CMi105DAOImpl 
* @Description: APP用户注册信息查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi104DAOImpl extends Mi104DAOImpl implements CMi104DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectAll(com.yondervision.mi.dto.Mi203)
	 */	

	public WebApi40103_queryResult select104Page(CMi103 form) {
		// TODO Auto-generated method stub
		Mi104Example mi104Example = new Mi104Example();
		mi104Example.setOrderByClause("comunacationid asc");
		Mi104Example.Criteria ca = mi104Example.createCriteria();		
		ca.andUserIdEqualTo(form.getUserId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi104> list = getSqlMapClientTemplate().queryForList("MI104.abatorgenerated_selectByExample", mi104Example, skipResults, rows);
		int total = this.countByExample(mi104Example);
		WebApi40103_queryResult queryResult = new WebApi40103_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList104(list);		
		return queryResult;
	}	
}
