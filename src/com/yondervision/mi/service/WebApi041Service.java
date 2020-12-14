package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.common.HeartBeat;
import com.yondervision.mi.dto.CMi041;
import com.yondervision.mi.result.WebApi04104_queryResult;


public interface WebApi041Service {
	/**
	 * 渠道监控新增
	 */
	public void webapi04101(CMi041 form) throws Exception;
	
	/**
	 * 渠道监控删除
	 */
	public int webapi04102(CMi041 form) throws Exception;
	
	/**
	 * 渠道监控修改
	 */
	public int webapi04103(CMi041 form) throws Exception;
	
	/**
	 * 渠道监控查询(分页)
	 * @param form
	 * @return
	 */
	public WebApi04104_queryResult webapi04104(CMi041 form)throws Exception;
	/**
	 * 渠道应用运行状态（从缓存中取实时数据open）
	 * @return
	 * @throws Exception
	 */
	public List<HeartBeat> webapi04105(String centerid)throws Exception;
	
}
