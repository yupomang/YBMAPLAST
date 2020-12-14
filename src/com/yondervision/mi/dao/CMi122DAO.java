package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi122;
import com.yondervision.mi.result.WebApi12201_queryResult;

/** 
* @ClassName: CMi122DAO 
* @Description: 推送消息主题分页查询
* @author Caozhongyan
* @date Jan 12, 2015 10:03:05 AM   
* 
*/ 
public interface CMi122DAO extends Mi122DAO {
	
	/**
	 * @deprecated 推送消息主题分页查询
	 * @param form
	 * @return
	 */
	public WebApi12201_queryResult select122Page(CMi122 form);
	
}
