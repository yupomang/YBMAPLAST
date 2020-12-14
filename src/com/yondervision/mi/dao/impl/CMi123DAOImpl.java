package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi123DAO;
import com.yondervision.mi.dto.CMi123;
import com.yondervision.mi.dto.Mi123;
import com.yondervision.mi.dto.Mi123Example;
import com.yondervision.mi.result.WebApi12201_queryResult;
import com.yondervision.mi.result.WebApi12301_queryResult;

/** 
* @ClassName: CMi122DAOImpl 
* @Description: 消息主题类型分页查询
* @author Caozhongyan
* @date Jan 12, 2015 10:02:11 AM   
* 
*/ 
public class CMi123DAOImpl extends Mi123DAOImpl implements CMi123DAO {
	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi122DAO#select122Page(com.yondervision.mi.dto.Mi122)
	 */
	public WebApi12301_queryResult select123Page(CMi123 form) {
		Mi123Example mi123Example = new Mi123Example();
		mi123Example.setOrderByClause("centerid asc,num asc,datemodified desc");
		Mi123Example.Criteria ca = mi123Example.createCriteria();
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
		List<Mi123> list = getSqlMapClientTemplate().queryForList("MI123.abatorgenerated_selectByExample", mi123Example, skipResults, rows);
		int total = this.countByExample(mi123Example);
		WebApi12301_queryResult queryResult = new WebApi12301_queryResult();		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList123(list);		
		return queryResult;
	}	
}
