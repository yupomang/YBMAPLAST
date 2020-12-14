package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi045;
import com.yondervision.mi.result.WebApi04504_queryResult;


public interface CMi045DAO extends Mi045DAO {
	
	public WebApi04504_queryResult select045Page(CMi045 form);
	
}
