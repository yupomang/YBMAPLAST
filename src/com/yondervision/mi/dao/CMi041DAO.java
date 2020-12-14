package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi041;
import com.yondervision.mi.result.WebApi04104_queryResult;


public interface CMi041DAO extends Mi041DAO {
	
	public WebApi04104_queryResult select041Page(CMi041 form);
	
}
