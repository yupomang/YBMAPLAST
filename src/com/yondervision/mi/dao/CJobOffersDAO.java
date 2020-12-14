package com.yondervision.mi.dao;

import com.yondervision.mi.form.WebApi99901Form;
import com.yondervision.mi.result.WebApi99901_queryResult;

/** 
* @ClassName: CJobOffersDAO 
* @Description: TODO
* @author gongqi
* 
*/ 
public interface CJobOffersDAO extends JobOffersDAO {
	public WebApi99901_queryResult selectJobOffersPage(WebApi99901Form form) throws Exception;
}
