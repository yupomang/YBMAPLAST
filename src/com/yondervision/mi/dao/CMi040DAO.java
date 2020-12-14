package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.result.WebApi04004_queryResult;


public interface CMi040DAO extends Mi040DAO {
	
	public WebApi04004_queryResult select040Page(CMi040 form);
	
}
