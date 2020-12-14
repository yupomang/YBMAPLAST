package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi057;
import com.yondervision.mi.result.WebApi05701_queryResult;


public interface CMi057DAO extends Mi057DAO {
	
	public WebApi05701_queryResult select057Page(CMi057 form);
	
}
