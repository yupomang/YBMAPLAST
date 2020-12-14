package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi411;
import com.yondervision.mi.result.WebApi41104Query_queryResult;

public interface CMi411DAO extends Mi411DAO {
	
	public WebApi41104Query_queryResult select411Page(CMi411 form);

}
