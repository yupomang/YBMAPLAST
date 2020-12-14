/**
 * 
 */
package com.yondervision.mi.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.yondervision.mi.dto.CMi202;
import com.yondervision.mi.dto.Mi202;

/** 
* @ClassName: WebApi202Service
* @Description:区域信息表维护
* @author gongqi
* @date Oct 16, 2013 17:55:31 PM
* 
*/ 
public interface WebApi202Service {
	/**
	 * 区域信息新增
	 */
	public void webapi20201(CMi202 form) throws Exception;
	
	/**
	 * 区域信息删除
	 */
	public int webapi20202(CMi202 form)throws Exception;
	
	/**
	 * 区域信息修改
	 */
	public int webapi20203(CMi202 form)throws Exception;
	
	/**
	 * 区域信息查询
	 */
	public List<Mi202> webapi20204(CMi202 form)throws Exception;
	
	/**
	 * 区域信息记录查询
	 */
	public int webapi20205(CMi202 form)throws Exception;
	
	/**
	 * 区域信息顺序号修改
	 */
	public void webapi20206(JSONArray arr) throws Exception;
}
