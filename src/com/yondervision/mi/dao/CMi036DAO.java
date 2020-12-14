package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi036;
import com.yondervision.mi.result.WebApi03604_queryResult;


public interface CMi036DAO extends Mi036DAO {
	
	public WebApi03604_queryResult select036Page(CMi036 form);
	
}
