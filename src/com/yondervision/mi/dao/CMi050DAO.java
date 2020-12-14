package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi050;
import com.yondervision.mi.result.WebApi05004_queryResult;


public interface CMi050DAO extends Mi050DAO {
	
	public WebApi05004_queryResult select050Page(CMi050 form);
	
}
