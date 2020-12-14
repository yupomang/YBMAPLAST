package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi042;
import com.yondervision.mi.result.WebApi04204_queryResult;


public interface CMi042DAO extends Mi042DAO {
	
	public WebApi04204_queryResult select042Page(CMi042 form);
	
}
