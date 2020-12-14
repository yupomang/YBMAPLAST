package com.yondervision.mi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi602DAO;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.dto.Mi602Example;
import com.yondervision.mi.form.AppApi60002Form;
import com.yondervision.mi.util.CommonUtil;

public class CMi602DAOImpl extends Mi602DAOImpl implements CMi602DAO {

	@SuppressWarnings("unchecked")
	public List<Mi602> selectMi602(AppApi60002Form form) {
		List<Mi602> list = new ArrayList<Mi602>();
		Mi602Example mi602Example = new Mi602Example();
		mi602Example.setOrderByClause("seqno desc");
		Mi602Example.Criteria ca = mi602Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterId())){
			ca.andCenteridEqualTo(form.getCenterId());
		}
		if(!CommonUtil.isEmpty(form.getUserId())){
			ca.andUseridEqualTo(form.getUserId());
		}
		if (!CommonUtil.isEmpty(form.getLastSeqno())
				&& !"0".equals(form.getLastSeqno())){
			ca.andSeqnoLessThan(Integer.parseInt(form.getLastSeqno()));
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		page = page==0 ? Integer.valueOf(1) : (page+1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		list = getSqlMapClientTemplate().queryForList("CMI602.abatorgenerated_selectByExample", mi602Example, skipResults, rows);
		return list;
	}

}
