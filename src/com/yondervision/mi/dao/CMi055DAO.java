package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi055;
import com.yondervision.mi.result.WebApi05504_queryResult;


public interface CMi055DAO extends Mi055DAO {
	
	public WebApi05504_queryResult select055Page(CMi055 form);
	
}
