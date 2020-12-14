package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi039;
import com.yondervision.mi.result.WebApi03904_queryResult;


public interface CMi039DAO extends Mi039DAO {
	
	public WebApi03904_queryResult select039Page(CMi039 form);
	
}
