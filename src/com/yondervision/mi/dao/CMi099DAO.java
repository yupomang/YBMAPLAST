/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi100Example;
import com.yondervision.mi.result.WebApi100_queryResult;

/**
 * @author LinXiaolong
 * 
 */
public interface CMi099DAO extends Mi099DAO {

	public List<HashMap> webapi09901(CMi107 form) throws Exception;

	public List<HashMap> selectWebapi09905(CMi107 form);
	
	public List<HashMap> selectWebapi09906(CMi107 form);

	public List<HashMap> selectWebapi09921(CMi107 form);
	
	public List<HashMap> webapi10708(CMi107 form);

	/**
	 * 从099表统计信息查询类信息插入098，不包含文件下载
	 * @param transday
	 * @param currentDate
	 * @return
	 */
	public List<HashMap> selectSearchInfo(String transday, String currentDate);
	/**
	 * 从099表统计业务办理预约和其他类信息插入098
	 * @param transday
	 * @param currentDate
	 * @return
	 */
	public List<HashMap> selectTransactions(String transday, String currentDate);
	
}
