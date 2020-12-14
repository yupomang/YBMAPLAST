/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi404Example;
import com.yondervision.mi.result.WebApi30205_queryResult;

/**
 * @author LinXiaolong
 * 
 */
public interface CMi404DAO extends Mi404DAO {

	/**
	 * 查看图文短信息
	 * 
	 * @param mi404Example
	 *            查询条件
	 * @param page
	 *            要查询的页码
	 * @param rows
	 *            每页条数
	 * @return 图文短信息分页数据
	 */
	WebApi30205_queryResult selectByExamplePagination(
			Mi404Example mi404Example, Integer page, Integer rows);

}
