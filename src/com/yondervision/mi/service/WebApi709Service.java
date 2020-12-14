package com.yondervision.mi.service;



import com.yondervision.mi.dto.Mi709;

import net.sf.json.JSONObject;


public interface WebApi709Service {
	/**
	 * 栏目与服务新增
	 */
	public void webapi70901(Mi709 form) throws Exception;
	
	/**
	 * 栏目与服务删除
	 */
	public void webapi70902(Mi709 form) throws Exception;
	
	/**
	 * 栏目与服务修改
	 */
	public void webapi70903(Mi709 form) throws Exception;
	
	/**
	 * 栏目与服务查询分页
	 */
	public JSONObject webapi70904(Mi709 form,Integer page,Integer rows) throws Exception;
	

}
