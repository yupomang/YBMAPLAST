package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi043;
import com.yondervision.mi.result.WebApi04304_queryResult;


public interface CMi043DAO extends Mi043DAO {
	
	public WebApi04304_queryResult select043Page(CMi043 form);
	
}
