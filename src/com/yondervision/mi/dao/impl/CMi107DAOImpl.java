/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi107DAOImpl.java
 * 创建日期：2013-10-29
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi107DAO;
import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.dto.Mi107Example;
import com.yondervision.mi.result.PtlApi002PageQueryResult;

/**
 * @author gongqi
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CMi107DAOImpl extends Mi107DAOImpl implements CMi107DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi107DAO#selectByExamplePagination(com.yondervision.mi.dto.Mi107Example, java.lang.Integer, java.lang.Integer)
	 */
	public PtlApi002PageQueryResult selectByExamplePagination(
			Mi107Example mi107Example, Integer page, Integer rows) {
		PtlApi002PageQueryResult result = new PtlApi002PageQueryResult();
		
		page = page.compareTo(0)==0 ? Integer.valueOf(1) : page;
		rows = rows.compareTo(0)==0 ? Integer.valueOf(10) : rows;
		
		int skipResults = (page-1) * rows;
		List<Mi107> listMi107 = getSqlMapClientTemplate().queryForList("MI107.abatorgenerated_selectByExample", mi107Example, skipResults, rows.intValue());
		int total = this.countByExample(mi107Example);
		
		result.setPageNumber(page);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setMi107Rows(listMi107);
		
		return result;
	}
	public List<HashMap> selectWebapi10704(CMi107 form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectFunctionAll", form);
		return result;
	}
	public List<HashMap> selectWebapi10704Sun(CMi107 form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectFunction", form);
		return result;
	}
	
	/**
	 * 统计各个渠道的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10705(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel05", form);
		return result;
	}
	/**
	 * 统计各渠道下面的业务成功或失败的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel06", form);
		return result;
	}
	
	/**
	 * 统计各渠道下面的活跃用户的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10708(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel08", form);
		return result;
	}
	/**
	 * 栏目内容更新数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706U(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectWebapi10706U", form);
		return result;
	}
	
	/**
	 * 信息推送量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706T(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectWebapi10706T", form);
		return result;
	}
	
	/**
	 * 渠道注册人数
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706Z(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectWebapi10706Z", form);
		return result;
	}
	/**
	 * 渠道注册人数2,不传时间，查询一个渠道所有的userid
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706ZZ(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectWebapi10706ZZ", form);
		return result;
	}
	/**
	 * 统计一个渠道下面功能活跃度的情况
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10721(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel21", form);
		return result;
	}
	
	/**
	 * 访问时间段分布统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10722(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel22", form);
		return result;
	}
	
	/**
	 * 访问时间段分布统计
	 * @param form
	 * @return
	 */
	public List<HashMap<String,String>> selectWebapi10723(CMi107 form){
		List<HashMap<String,String>> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel23", form);
		return result;
	}
	/**
	 * 用户增长统计-累计的
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi10707User01(CMi031 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi10707User01", form);
		return result;
	}
	/**
	 * 用户增长统计-新增的
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi10707User02(CMi031 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi10707User02", form);
		return result;
	}
	
	
	/**
	 * 用户性别属性统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10724(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel24", form);
		return result;
	}
	/**
	 * 用户年龄属性统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10725(CMi107 form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectCountByChannel25", form);
		return result;
	}
	
	//业务分析-渠道分布统计
	public List<HashMap> webapi10708(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi10708", form);
		return result;
	}
	//业务分析-时间段分布统计
	public List<HashMap> webapi10709(CMi107 form) throws Exception{
		List<HashMap> result = null;
		String startdate = form.getStartdate();
		String enddate = form.getEnddate();
		if(!startdate.substring(0, 4).equals(enddate.substring(0, 4))){//跨年
			result = getSqlMapClientTemplate().queryForList("CMI107.webapi1070901", form);
		}else if(!startdate.substring(5, 7).equals(enddate.substring(5, 7))){//跨月
			result = getSqlMapClientTemplate().queryForList("CMI107.webapi1070902", form);
		}else{//跨天
			result = getSqlMapClientTemplate().queryForList("CMI107.webapi1070903", form);
		}
		
		
		return result;
	}
	//业务分析-业务量统计非资金类
	public List<HashMap> webapi1071001(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi1071001", form);
		return result;
	}
	
	//业务分析-业务量统计非资金类
	public List<HashMap> webapi1071001New(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi1071001New", form);
		return result;
	}
	//业务分析-业务量统计资金类
	public List<HashMap> webapi1071002(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi1071002", form);
		return result;
	}
	//业务分析-业务量统计定时任务插入mi099表
	public List<HashMap> webapi1071003(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi1071003", form);
		return result;
	}
	//业务分析-用户分布统计
	public List<HashMap> webapi10711(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi10711", form);
		return result;
	}
	//业务分析-预约
	public List<HashMap> selectAppoint(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi107Appoint", form);
		return result;
	}
	
	public List<HashMap> selectWebApiActive(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.webapi107Active", form);
		return result;
	}
	
	public List<HashMap> selectUserCount(CMi107 form) throws Exception{
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectUserCount", form);
		return result;
	}
	
	public List<HashMap> selectWebApiRegister(CMi107 form) throws Exception{
		form.setEnddate(form.getEnddate()+" 23:59:59");
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI107.selectWebApiRegister", form);
		return result;
	}
}
