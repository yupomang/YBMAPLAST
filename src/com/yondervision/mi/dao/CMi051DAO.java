package com.yondervision.mi.dao;

import net.sf.json.JSONObject;
import java.util.List;

import com.yondervision.mi.dto.CMi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.result.WebApi05104_queryResult;


public interface CMi051DAO extends Mi051DAO {
	public WebApi05104_queryResult select051Page(CMi051 form);
	public JSONObject mixSelect051Page(CMi051 form) throws Exception;
	public List selectByExampleForCenterid(CMi051 form);
}
