package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi005;
import com.yondervision.mi.result.WebApi00504_queryResult;


public interface CMi005DAO extends Mi005DAO {
	
	public WebApi00504_queryResult select005Page(CMi005 form);
	
}
