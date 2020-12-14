/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi304Impl.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yondervision.mi.dao.CMi623DAO;

/**
 * @author LinXiaolong
 *
 */
public class CMi623DAOImpl extends Mi623DAOImpl implements CMi623DAO {
	//查询一个中心可预约的所有网点
	public List<HashMap> selectAppapi30302Website(Map<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30302Website", map);
		return result;
	}
	//查询mi624表中，在一个预约网点和时段模版中，最大可预约人数
	public List<HashMap> selectAppapi30302Count(Map<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30302Count", map);
		return result;
	}
	//查询mi624表中，所有预约网点可预约人数明细
		public List<HashMap> selectAppapi30302AppoCountDetail(Map<String,String> map) {
			// TODO Auto-generated method stub
			List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30302AppoCountDetail", map);
			return result;
		}
	//查询每天该网点下所预约的总数
	public List<HashMap> selectAppapi30302AllCount(Map<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30302AllCount", map);
		return result;
	}
	//查询每天该网点下所预约的信息
		public List<HashMap> selectTodayAppo(Map<String,String> map) {
			// TODO Auto-generated method stub
			List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectTodayAppo", map);
			return result;
		}
	//根据网点、业务、日期，查询每个时段明细下的总预约人数
	public List<HashMap> selectAppapi30303Count(Map<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30303Count", map);
		return result;
	}
	//查询预约网店的时段明细配置
	public List<HashMap> selectAppapi30303Detail(Map<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI623.selectAppapi30303Detail", map);
		return result;
	}
	//查询一个中心的可预约网点（for 网点预约情况查询 页面条件区域的 下拉框）
	public List<HashMap> selectWebSiteInfo4AppoQuery(String centerid) {
		// TODO Auto-generated method stub
		List<HashMap> result= getSqlMapClientTemplate().queryForList("CMI623.selectWebSiteInfo4AppoQuery",centerid);
		return result;
	}
	
}
