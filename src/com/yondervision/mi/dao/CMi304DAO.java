/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi304DAO.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.result.WebApi20111Result;


/**
 * @author LinXiaolong
 *
 */
public interface CMi304DAO extends Mi304DAO {
	/**
	 * 根据公共条件项目ID查询公共条件分组和公共条件内容数据
	 * @param conditionItemId 公共条件项目ID
	 * @return 公共条件分组和公共条件内容数据
	 */
	List<WebApi20111Result> selectWebApi20111ResultByConditionItemId(String conditionItemId);
	
	/**
	 * 根据城市代码查询公共条件分组和公共条件内容数据
	 * @param centerId 城市代码
	 * @return 公共条件分组和公共条件内容数据
	 */
	List<WebApi20111Result> selectWebApi20111ResultByCenterId(String centerId);

	/**
	 * 根据业务咨询项目ID查询公共条件分组和公共条件内容数据
	 * @param consultItemId 业务咨询项目ID
	 * @return 公共条件分组和公共条件内容数据
	 */
	List<WebApi20111Result> selectWebApi20111ResultByConsultItemId(
			String consultItemId);
}
