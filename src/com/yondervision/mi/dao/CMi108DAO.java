package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi108;
import com.yondervision.mi.dto.Mi108;
import com.yondervision.mi.form.WebApi40401Form;
import com.yondervision.mi.result.WebApi40401_queryResult;

/** 
* @ClassName: CMi108DAO 
* @Description: TODO
* @author Caozhongyan
* @date Oct 5, 2013 9:33:25 PM   
* 
*/ 
public interface CMi108DAO extends Mi108DAO {
	public List<Mi108> selectMi108(WebApi40401Form form);
	public int updateMi108(CMi108 form);
	public WebApi40401_queryResult selectMi108Page(WebApi40401Form form);
}
