/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi304Impl.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi304DAO;
import com.yondervision.mi.dto.Mi304;
import com.yondervision.mi.result.WebApi20111Result;

/**
 * @author LinXiaolong
 *
 */
public class CMi304DAOImpl extends Mi304DAOImpl implements CMi304DAO {

	/*
	 * (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi304DAO#selectWebApi20111ResultByConsultItemId(com.yondervision.mi.dto.Mi304)
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi20111Result> selectWebApi20111ResultByConditionItemId(
			String conditionItemId) {
		Mi304 key = new Mi304();
        key.setConditionItemId(conditionItemId);
		List<WebApi20111Result> result = getSqlMapClientTemplate().queryForList("CMI304.selectWebApi20111ResultByConditionItemId", key);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi304DAO#selectWebApi20111ResultByCenterId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi20111Result> selectWebApi20111ResultByCenterId(
			String centerId) {
		List<WebApi20111Result> result = getSqlMapClientTemplate().queryForList("CMI304.selectWebApi20111ResultByCenterId", centerId);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi304DAO#selectWebApi20111ResultByConsultItemId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi20111Result> selectWebApi20111ResultByConsultItemId(
			String consultItemId) {
		List<WebApi20111Result> result = getSqlMapClientTemplate().queryForList("CMI304.selectWebApi20111ResultByConsultItemId", consultItemId);
		return result;
	}

}
