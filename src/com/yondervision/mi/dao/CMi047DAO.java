package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi047;
import com.yondervision.mi.result.WebApi04704_queryResult;


public interface CMi047DAO extends Mi047DAO {
	
	public WebApi04704_queryResult select047Page(CMi047 form);
	
}
