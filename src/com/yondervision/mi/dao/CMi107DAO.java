/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi107DAO.java
 * 创建日期：2013-10-29
 */
package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi107Example;
import com.yondervision.mi.result.PtlApi002PageQueryResult;

@SuppressWarnings("rawtypes")
public interface CMi107DAO extends Mi107DAO {

	/**
	 * 查询app业务日志（分页）
	 * 
	 * @param mi107Example
	 *            查询条件
	 * @param page
	 *            要查询的页码
	 * @param rows
	 *            每页条数
	 * @return app业务日志分页数据
	 */
	PtlApi002PageQueryResult selectByExamplePagination(
			Mi107Example mi107Example, Integer page, Integer rows);
	public List<HashMap> selectWebapi10704(CMi107 form);
	public List<HashMap> selectWebapi10704Sun(CMi107 form);
	/**
	 * 统计各个渠道的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10705(CMi107 form);
	/**
	 * 统计各渠道下面的业务成功或失败的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706(CMi107 form);
	/**
	 * 栏目内容更新数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706U(CMi107 form);
	/**
	 * 信息推送量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706T(CMi107 form);
	/**
	 * 渠道注册人数
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706Z(CMi107 form);
	/**
	 * 渠道注册人数2,不传时间，查询一个渠道所有的userid
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10706ZZ(CMi107 form);
	
	/**
	 * 统计各渠道下面的活跃用户的数量
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10708(CMi107 form);
	
	/**
	 * 统计一个渠道下面功能活跃度的情况
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10721(CMi107 form);
	/**
	 * 访问时间段分布统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10722(CMi107 form); 
	/**
	 * 用户增长统计-累计的
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi10707User01(CMi031 form);
	/**
	 * 用户增长统计-新增的
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi10707User02(CMi031 form);
	
	/**
	 * 用户性别属性统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10724(CMi107 form);
	/**
	 * 用户年龄属性统计
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi10725(CMi107 form);
	
	//业务分析-渠道分布统计
	public List<HashMap> webapi10708(CMi107 form) throws Exception;
	//业务分析-时间段分布统计
	public List<HashMap> webapi10709(CMi107 form) throws Exception;
	//业务分析-业务量统计非资金类
	public List<HashMap> webapi1071001(CMi107 form) throws Exception;
	//业务分析-业务量统计非资金类
	public List<HashMap> webapi1071001New(CMi107 form) throws Exception;
	//业务分析-业务量统计资金类
	public List<HashMap> webapi1071002(CMi107 form) throws Exception;
	//业务分析-业务量统计定时任务插入mi099表
	public List<HashMap> webapi1071003(CMi107 form) throws Exception;
	//业务分析-用户分布统计
	public List<HashMap> webapi10711(CMi107 form) throws Exception;
	
	
	/**
	 * 访问时间段分布统计-根据业务类型
	 * @param form
	 * @return
	 */
	public List<HashMap<String,String>> selectWebapi10723(CMi107 form); 
	/**
	 * 业务量统计   从625表获取预约数据
	 * @param form
	 * @return
	 */
	public List<HashMap> selectAppoint(CMi107 form) throws Exception;
	/**
	 * 渠道用户活跃度
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> selectWebApiActive(CMi107 form) throws Exception;
	
	/**
	 * 获取每个渠道用户总数
	 * @param centerid
	 * @return
	 */
	public List<HashMap> selectUserCount(CMi107 form) throws Exception;
	/**
	 * 获取时间段内各渠道用户注册数
	 * @param centerid
	 * @return
	 */
	public List<HashMap> selectWebApiRegister(CMi107 form) throws Exception;
}
