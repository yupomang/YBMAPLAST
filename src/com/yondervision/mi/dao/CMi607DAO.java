package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.Mi607;
import com.yondervision.mi.result.WebApi60702_queryResult;

public interface CMi607DAO extends Mi607DAO {
	public WebApi60702_queryResult selectWebPage(CMi607 form);
	public boolean selectAppInfo(CMi607 form) throws Exception;
}
