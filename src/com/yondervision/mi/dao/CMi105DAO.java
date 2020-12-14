package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.result.WebApi40102_queryResult;


/** 
* @ClassName: CMi105DAO 
* @Description: 设备查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi105DAO extends Mi105DAO {
	
	public WebApi40102_queryResult select105Page(CMi103 form);
	
}
