/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.yondervision.mi.dao.CMi099DAO;
import com.yondervision.mi.dto.CMi107;

/**
 * @author LinXiaolong
 *
 */
public class CMi099DAOImpl extends Mi099DAOImpl implements CMi099DAO {

	public List<HashMap> webapi09901(CMi107 form) throws Exception {
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.webapi09901", form);
		return result;
	}

	/**
	 * 统计各个渠道的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi09905(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.selectCountByChannel05", form);
		return result;
	}
	
	/**
	 * 统计渠道的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi09906(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.selectCountByChannel06", form);
		return result;
	}
	
	/**
	 * 统计一个渠道下面功能活跃度的情况
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi09921(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.selectCountByChannel21", form);
		return result;
	}

	public List<HashMap> webapi10708(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.webapi10708", form);
		return result;
	}
	
	public List<HashMap> selectSearchInfo(String transday, String currentDate){
		HashMap map = new HashMap();
		map.put("transday", transday);
		map.put("currentDate", currentDate);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.selectSearchInfo", map);
		return result;
	}
	public List<HashMap> selectTransactions(String transday, String currentDate){
		HashMap map = new HashMap();
		map.put("transday", transday);
		map.put("currentDate", currentDate);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI099.selectTransactions", map);
		return result;
	}
}
