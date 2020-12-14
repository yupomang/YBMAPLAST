/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi312DAOImpl.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi312DAO;
import com.yondervision.mi.dto.Mi312Example;

/**
 * @author LinXiaolong
 * 
 */
public class CMi312DAOImpl extends Mi312DAOImpl implements CMi312DAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.dao.CMi312DAO#selectListConsultIdByListConditionRadioId
	 * (java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectListConsultIdByListConditionRadioId(
			List<String> conditionRadioId, String stepId) {
		Mi312Example mi312Example = new Mi312Example();
		mi312Example.createCriteria().andConditionRadioIdIn(conditionRadioId)
				.andStepIdEqualTo(stepId).andValidflagEqualTo(
						Constants.IS_VALIDFLAG);
		return this.getSqlMapClientTemplate().queryForList(
				"CMI312.selectListConsultIdByListConditionRadioId", mi312Example);
	}

}
