package com.yondervision.mi.dao;

import com.yondervision.mi.dto.CMi601;
import com.yondervision.mi.form.WebApi60001Form;
import com.yondervision.mi.result.WebApi60001_queryResult;

/** 
* @ClassName: CMi601DAO 
* @Description: TODO
* @author gongqi
* @date July 16, 2014 9:33:25 PM
* 
*/ 
public interface CMi601DAO extends Mi601DAO {
	public int updateMi601(CMi601 form);
	public WebApi60001_queryResult selectMi601Page(WebApi60001Form form) throws Exception;
}
