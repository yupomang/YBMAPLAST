/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi107DAO.java
 * 创建日期：2013-10-29
 */
package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.form.WebApi05801Form;

@SuppressWarnings("rawtypes")
public interface CMi058DAO extends Mi058DAO {
	
	public List<HashMap> selectWebapi05803(WebApi05801Form form);
	
	public List<HashMap> selectWebapi05804(WebApi05801Form form);
	
	public List<HashMap> selectWebapi05805(WebApi05801Form form);
	
	public List<HashMap> selectWebapi05806(WebApi05801Form form);
	
	public List<HashMap> selectWebapi05807(WebApi05801Form form);
	
}
