package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi626DAO;
import com.yondervision.mi.dto.Mi626;
import com.yondervision.mi.dto.CMi626;
import com.yondervision.mi.dto.Mi626Example;
import com.yondervision.mi.result.WebApi62604_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi626DAOImpl extends Mi626DAOImpl implements CMi626DAO {

	public WebApi62604_queryResult selectWebPage(CMi626 form) {
		// TODO Auto-generated method stub
		Mi626Example mi626e=new Mi626Example();
		mi626e.setOrderByClause("centerid desc, appoattenid asc, validflag desc");
		Mi626Example.Criteria ca=mi626e.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterid())&&!form.getCenterid().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterid());
		}
		if(!CommonUtil.isEmpty(form.getAppoattenid())){
			ca.andAppoattenidEqualTo(form.getAppoattenid());
		}
		if(!CommonUtil.isEmpty(form.getTemplatename())){
			ca.andTemplatenameLike("%"+form.getTemplatename()+"%");
		}
		if(!CommonUtil.isEmpty(form.getLoginid())){
			ca.andLoginidEqualTo(form.getAppoattenid());
		}
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi626> list=getSqlMapClientTemplate().queryForList("MI626.abatorgenerated_selectByExample",mi626e,skipResults,rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"预约注意事项");
		}
		int total = this.countByExample(mi626e);
		WebApi62604_queryResult queryResult = new WebApi62604_queryResult();
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList626(list);
		return queryResult;
	}

}
