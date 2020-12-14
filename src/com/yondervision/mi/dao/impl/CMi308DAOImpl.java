/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi308DAOImpl.java
 * 创建日期：2013-10-17
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi308DAO;
import com.yondervision.mi.dto.CMi308;

/**
 * @author LinXiaolong
 *
 */
public class CMi308DAOImpl extends Mi308DAOImpl implements CMi308DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi308DAO#selectAllCMi308ByStepId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<CMi308> selectAllCMi308ByStepId(String stepId) {
		List<CMi308> result = getSqlMapClientTemplate().queryForList("CMI308.selectAllCMi308ByStepId", stepId);
		return result;
	}

}
