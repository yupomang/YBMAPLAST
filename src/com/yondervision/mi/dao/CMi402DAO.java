/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.result.WebApi30202_queryResult;

/**
 * @author LinXiaolong
 * 
 */
public interface CMi402DAO extends Mi402DAO {

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
	WebApi30202_queryResult selectByExamplePagination(
			Mi402Example mi402Example, Integer page, Integer rows);

}
