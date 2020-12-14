package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi108DAO;
import com.yondervision.mi.dto.CMi108;
import com.yondervision.mi.dto.Mi108;
import com.yondervision.mi.dto.Mi108Example;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.form.WebApi40401Form;
import com.yondervision.mi.result.WebApi20304_queryResult;
import com.yondervision.mi.result.WebApi40401_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi108DAOImpl extends Mi108DAOImpl implements CMi108DAO {

	public List<Mi108> selectMi108(WebApi40401Form form) {
		// TODO Auto-generated method stub
		List<Mi108> list = getSqlMapClientTemplate().queryForList("CMI108.selectWeb", form);
        return list;
	}

	public int updateMi108(CMi108 form) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("CMI108.abatorgenerated_updateByPrimaryKeySelective", form);
	}

	public WebApi40401_queryResult selectMi108Page(WebApi40401Form form) {
		// TODO Auto-generated method stub
		Mi108Example mi108Example = new Mi108Example();
		mi108Example.setOrderByClause("centerid asc, devtype asc, versionno desc, status asc ");
		Mi108Example.Criteria ca = mi108Example.createCriteria();		
		if(!CommonUtil.isEmpty(form.getCenterId())){
			ca.andCenteridEqualTo(form.getCenterId());
		}
		if(!CommonUtil.isEmpty(form.getVersionno())){
			ca.andVersionnoLike("%"+form.getVersionno()+"%");
		}
		if(!CommonUtil.isEmpty(form.getDevType())){
			ca.andDevtypeEqualTo(form.getDevType());
		}
		if(!CommonUtil.isEmpty(form.getStatus())){
			ca.andStatusEqualTo(form.getStatus());
		}
		if(!CommonUtil.isEmpty(form.getStartdate())){
			ca.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000");
		}
		if(!CommonUtil.isEmpty(form.getEnddate())){
			ca.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
		}			
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi108> list = getSqlMapClientTemplate().queryForList("MI108.abatorgenerated_selectByExample", mi108Example, skipResults, rows);
		int total = this.countByExample(mi108Example);
		WebApi40401_queryResult queryResult = new WebApi40401_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList108(list);
		return queryResult;
	}

}
