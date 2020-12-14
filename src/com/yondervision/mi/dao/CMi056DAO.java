package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi056;
import com.yondervision.mi.result.WebApi05604_queryResult;


public interface CMi056DAO extends Mi056DAO {
	
	public WebApi05604_queryResult select056Page(CMi056 form);
	
}
