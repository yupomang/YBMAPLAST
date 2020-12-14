package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi014;
import com.yondervision.mi.result.WebApi01404_queryResult;


public interface CMi014DAO extends Mi014DAO {
	
	public WebApi01404_queryResult select014Page(CMi014 form);
	
}
