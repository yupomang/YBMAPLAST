package com.yondervision.mi.service;
 
import com.yondervision.mi.dto.CMi057;

import net.sf.json.JSONObject;
 
public interface WebApi057Service {
	
	/**
	 * @deprecated 新增
	 * @param mi057
	 * @throws Exception
	 */
	public void webapi05701(CMi057 mi057) throws Exception;
	
	/**
	 * 删除
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public void webapi05702(CMi057 form) throws Exception;
	
	/**
	 * 修改
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public void webapi05703(CMi057 form) throws Exception;
	
	/**
	 * 查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public JSONObject webapi05704(CMi057 form) throws Exception;
	
}
