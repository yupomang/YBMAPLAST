package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi626;
import com.yondervision.mi.result.WebApi62604_queryResult;

public interface CMi626DAO extends Mi626DAO {

	public WebApi62604_queryResult selectWebPage(CMi626 form);
}
