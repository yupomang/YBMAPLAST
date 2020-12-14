package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi106;
import com.yondervision.mi.result.WebApi40304_queryResult;


/** 
* @ClassName: CMi105DAO 
* @Description: 设备查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi106DAO extends Mi106DAO {
	
	public WebApi40304_queryResult select106Page(CMi106 form);
	
}
