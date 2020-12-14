package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi037;
import com.yondervision.mi.result.WebApi03704_queryResult;


public interface CMi037DAO extends Mi037DAO {
	
	public WebApi03704_queryResult select037Page(CMi037 form);
	
}
