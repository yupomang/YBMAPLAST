package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.CMi031;

@SuppressWarnings("rawtypes")
public interface CMi031DAO extends Mi031DAO {

	/**
	 * 根据中心id，起止时间获取pid
	 * @param form
	 * @return
	 */
	
	public List<HashMap> selectWebapi03101(CMi031 form);
	/**
	 * 根据中心，起止时间和pid获取当前pid的用户数
	 * @param form
	 * @return
	 */
	public List<HashMap> selectWebapi03102(CMi031 form);
}
