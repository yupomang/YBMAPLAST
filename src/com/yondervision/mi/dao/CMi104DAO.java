package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.result.WebApi40103_queryResult;


/** 
* @ClassName: CMi105DAO 
* @Description: 设备查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi104DAO extends Mi104DAO {
	
	public WebApi40103_queryResult select104Page(CMi103 form);
	
}
