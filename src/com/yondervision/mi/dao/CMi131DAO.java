package com.yondervision.mi.dao;

import java.util.List;
import com.yondervision.mi.dto.Mi131;
import com.yondervision.mi.dto.Mi131WithBLOBs;

/** 
* @ClassName: CMi131DAO
* @author zhanglei
* @date 2015-01-19
* 
*/ 
public interface CMi131DAO extends Mi131DAO {
	
	/**
	 * 分页查询图片信息
	 * @param mi131
	 * @return
	 */
	public List<Mi131WithBLOBs> selectByExample(Mi131 mi131,int skipResults, int rows);
}
