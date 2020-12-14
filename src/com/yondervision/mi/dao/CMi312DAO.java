/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi312DAO.java
 * 创建日期：2013-11-1
 */
package com.yondervision.mi.dao;

import java.util.List;

/**
 * @author LinXiaolong
 *
 */
public interface CMi312DAO extends Mi312DAO {

	/**
	 * 根据条件组合list查询咨询内容IDlist
	 * @param conditionRadioId 条件组合list
	 * @return 咨询内容IDlist
	 */
	List<String> selectListConsultIdByListConditionRadioId(
			List<String> conditionRadioId, String stepId);

}
