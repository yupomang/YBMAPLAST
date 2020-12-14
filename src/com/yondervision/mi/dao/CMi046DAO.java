package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi046;
import com.yondervision.mi.result.WebApi04604_queryResult;


public interface CMi046DAO extends Mi046DAO {
	
	public WebApi04604_queryResult select046Page(CMi046 form);
	
}
