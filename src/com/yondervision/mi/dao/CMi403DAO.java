/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.Mi402Example;
import com.yondervision.mi.dto.Mi403Example;
import com.yondervision.mi.result.WebApi30202_queryResult;
import com.yondervision.mi.result.WebApi30206_queryResult;


public interface CMi403DAO extends Mi403DAO {

	/**
	 *
	 * 
	 * @param mi402Example
	 *            查询条件
	 * @param page
	 *            要查询的页码
	 * @param rows
	 *            每页条数
	 * @return 报盘短信息分页数据
	 */
	WebApi30206_queryResult selectByExamplePagination(
			Mi403Example mi403Example, Integer page, Integer rows);
	
	/**
	 * 业务统计-信息发布-app、微信消息推送-消息群推、定制消息统计
	 * @param centerId
	 * @param pid
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param pushType
	 * @return
	 * @throws Exception
	 */
	public int webapi403(String centerId, String pid, String startDate, 
			String endDate, String status, String pushType) throws Exception;
	/**
	 * 业务统计-信息发布-app、微信消息推送-模板消息统计
	 * @param centerId
	 * @param pid
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> webapi40301(String centerId, String pid, String startDate, 
			String endDate) throws Exception;
}
