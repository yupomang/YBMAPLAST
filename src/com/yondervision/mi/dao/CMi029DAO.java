package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.result.WebApi02904_queryResult;


public interface CMi029DAO extends Mi029DAO {
	
	public WebApi02904_queryResult select029Page(CMi029 form, List<String> ids);
	
}
