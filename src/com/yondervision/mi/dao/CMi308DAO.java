/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi308DAO.java
 * 创建日期：2013-10-17
 */
package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi308;


/**
 * @author LinXiaolong
 *
 */
public interface CMi308DAO extends Mi308DAO {
	
	/**
	 * 根据业务咨询步骤向导ID查询业务咨询向导内容及其所有子内容
	 * @param stepId 业务咨询步骤向导ID
	 * @return 业务咨询向导内容及其所有子内容
	 */
	List<CMi308> selectAllCMi308ByStepId(String stepId);
}
