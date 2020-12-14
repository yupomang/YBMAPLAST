package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi008DAO;
import com.yondervision.mi.dto.Mi008;
import com.yondervision.mi.dto.Mi008Example;

public class CMi008DAOImpl extends Mi008DAOImpl implements CMi008DAO {

	/**
	 * 查询加行级锁
	 * @param example
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi008> selectByExampleForUpdate(Mi008Example example){
        List<Mi008> list = getSqlMapClientTemplate().queryForList("CMI008.abatorgenerated_selectByExample", example);
        return list;
	}

}
