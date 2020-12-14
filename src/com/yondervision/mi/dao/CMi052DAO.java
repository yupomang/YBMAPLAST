package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi052;
import com.yondervision.mi.result.WebApi05204_queryResult;


public interface CMi052DAO extends Mi052DAO {
	
	public WebApi05204_queryResult select052Page(CMi052 form);
	
}
