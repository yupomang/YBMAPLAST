/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi100Example;
import com.yondervision.mi.result.WebApi100_queryResult;

/**
 * @author LinXiaolong
 * 
 */
public interface CMi100DAO extends Mi100DAO {

	/**
	 * 查询报盘短信息（分页）
	 * 
	 * @param mi402Example
	 *            查询条件
	 * @param page
	 *            要查询的页码
	 * @param rows
	 *            每页条数
	 * @return 报盘短信息分页数据
	 */
	WebApi100_queryResult selectByExamplePagination(
			Mi100Example mi100Example, Integer page, Integer rows);

}
