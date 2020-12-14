package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi044;
import com.yondervision.mi.result.WebApi04404_queryResult;


public interface CMi044DAO extends Mi044DAO {
	
	public WebApi04404_queryResult select044Page(CMi044 form);
	
}
