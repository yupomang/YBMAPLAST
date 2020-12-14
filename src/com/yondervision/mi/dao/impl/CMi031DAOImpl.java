
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi031DAO;
import com.yondervision.mi.dto.CMi031;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CMi031DAOImpl extends Mi031DAOImpl implements CMi031DAO {

	
	public List<HashMap> selectWebapi03101(CMi031 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI031.selectPidAll", form);
		return result;
	}
	
	/**
	 * 根据中心，起止时间和pid获取当前pid的用户数
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi03102(CMi031 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI031.selectCountPid", form);
		return result;
	}
}
