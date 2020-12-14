package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi053;
import com.yondervision.mi.result.WebApi05304_queryResult;


public interface CMi053DAO extends Mi053DAO {
	
	public WebApi05304_queryResult select053Page(CMi053 form);
	
}
