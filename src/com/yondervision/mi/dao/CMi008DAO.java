package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi008;
import com.yondervision.mi.dto.Mi008Example;

/** 
* @ClassName: CMi008DAO
* @author gongqi
* @date Nov 7, 2013 9:33:25 PM   
* 
*/ 
public interface CMi008DAO extends Mi008DAO {
	
	/**
	 * 查询加行级锁
	 * @param example
	 * @return
	 */
	List<Mi008> selectByExampleForUpdate(Mi008Example example);
}
