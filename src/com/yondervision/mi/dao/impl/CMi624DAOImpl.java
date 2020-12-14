package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.CMi624DAO;
import com.yondervision.mi.dao.impl.Mi624DAOImpl;
import com.yondervision.mi.dto.Mi624;

public class CMi624DAOImpl extends Mi624DAOImpl implements CMi624DAO {

	public int selectMi624SumAppocnt(Mi624 form) {
		// TODO Auto-generated method stub
		int result = 0;
		Object obj=getSqlMapClientTemplate().queryForObject("CMI624.selectMi624SumAppocnt", form);
		if(obj!=null&&obj!=""){
			result = Integer.parseInt(obj+"");
		}
		return result;
	}

}
