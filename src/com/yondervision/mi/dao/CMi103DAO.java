package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.result.WebApi40101_queryResult;


/** 
* @ClassName: CMi201DAO 
* @Description: 网点查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi103DAO extends Mi103DAO {
	
	public List<Mi103> select103(CMi103 form);
	public WebApi40101_queryResult select103Page(CMi103 form);
	public List<HashMap> selectWebapi10304Wx(CMi103 form);
	public List<HashMap> selectWebapi10304App(CMi103 form);
	public List<HashMap> selectWebapi10304All(CMi103 form);
	public List<HashMap> selectWebapi10301app(CMi103 form);
	public List<HashMap> selectWebapi10301wx(CMi103 form);
	
}
