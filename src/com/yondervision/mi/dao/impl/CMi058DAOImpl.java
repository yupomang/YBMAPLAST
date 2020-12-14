/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi107DAOImpl.java
 * 创建日期：2013-10-29
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi058DAO;
import com.yondervision.mi.form.WebApi05801Form;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CMi058DAOImpl extends Mi058DAOImpl implements CMi058DAO {

	public List<HashMap> selectWebapi05803(WebApi05801Form form) {
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI058.selectWebapi05803", form);
		return result;
	}
	
	public List<HashMap> selectWebapi05804(WebApi05801Form form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI058.selectWebapi05804", form);
		return result;
	}
	
	public List<HashMap> selectWebapi05805(WebApi05801Form form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI058.selectWebapi05805", form);
		return result;
	}
	
	public List<HashMap> selectWebapi05806(WebApi05801Form form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI058.selectWebapi05806", form);
		return result;
	}
	
	public List<HashMap> selectWebapi05807(WebApi05801Form form){
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI058.selectWebapi05807", form);
		return result;
	}
}
