/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi311DAO.java
 * 创建日期：2013-11-1
 */
package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi311;

/**
 * @author LinXiaolong
 *
 */
public interface CMi311DAO extends Mi311DAO {

	/**
	 * 根据选择的条件组合查询出完全匹配的条件组合信息
	 * @param listConditionId 选择的条件组合
	 * @return 完全匹配的条件组合信息
	 */
	Mi311 selectByListConditionId(List<String> listConditionId);
	
	/**
	 * 根据选择的条件组合查询出符合的条件（选择的条件ID包含组合信息中的条件ID）组合信息
	 * @param listConditionId 选择的条件组合
	 * @return 符合的条件组合信息
	 */
	List<Mi311> selectAllByListConditionId(List<String> listConditionId);

}
