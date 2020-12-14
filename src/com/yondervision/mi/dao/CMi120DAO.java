/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi311DAO.java
 * 创建日期：2013-11-1
 */
package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi120;
import com.yondervision.mi.result.WebApi41104_queryResult;

/** 
* @ClassName: CMi120DAO 
* @Description: TODO
* @author Caozhongyan
* @date Dec 2, 2013 2:54:28 PM   
* 
*/ 
public interface CMi120DAO extends Mi120DAO {
	public WebApi41104_queryResult selectAllByList(CMi120 form);
}
