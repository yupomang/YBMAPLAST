/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi304DAO.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.CMi625;
import com.yondervision.mi.form.AppApi30304Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi62506_queryResult;


/**
 * 
 *
 */
public interface CMi625DAO extends Mi625DAO {
	/**
	 * 查询用户是否重复预约
	 * @param consultItemId 业务咨询项目ID
	 * @return 预约网点信息
	 */
	public List<HashMap> selectAppapi30304Detail(AppApi30304Form map);
	/**
	 * 查询用户所有预约
	 * @param map
	 * @return
	 */
	public List<HashMap> selectAppapi30305User(AppApiCommonForm map) ;
	public WebApi62506_queryResult selectOneDayAppoDetail(CMi625 form);
	public int updateAppostate(HashMap form);
	public  List<HashMap> selectRemainPeople(AppApi30304Form form);
	public  int selectMi624SumAppocnt(AppApi30304Form form);
	public List<CMi625> selectMi625All(CMi625 map);
}
