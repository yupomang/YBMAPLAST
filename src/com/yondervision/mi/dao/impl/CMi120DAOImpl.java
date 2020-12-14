package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi120DAO;
import com.yondervision.mi.dto.CMi120;
import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi120Example;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.result.WebApi41104_queryResult;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: CMi203DAOImpl 
* @Description: 楼盘查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi120DAOImpl extends Mi120DAOImpl implements CMi120DAO {
	public WebApi41104_queryResult selectAllByList(CMi120 form) {
		// TODO Auto-generated method stub
		Mi120Example mi120Example = new Mi120Example();
		mi120Example.setOrderByClause("centerid desc, devid asc, animateid asc");
		Mi120Example.Criteria ca = mi120Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterid())){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getAnimatename())){
			ca.andAnimatenameLike("%"+form.getAnimatename()+"%");
		}
		if(!CommonUtil.isEmpty(form.getDevid())){
			ca.andDevidLike("%"+form.getDevid()+"%");
		}
		if(!CommonUtil.isEmpty(form.getAnimatecode())){
			ca.andAnimatecodeLike("%"+form.getAnimatecode()+"%");
		}
		
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi120> list = getSqlMapClientTemplate().queryForList("MI120.abatorgenerated_selectByExample", mi120Example, skipResults, rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"app图片动画");
		}	
		int total = this.countByExample(mi120Example);
		WebApi41104_queryResult queryResult = new WebApi41104_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList120(list);
		return queryResult;
	}
}
