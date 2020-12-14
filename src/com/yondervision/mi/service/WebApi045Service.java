package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.common.Flow;
import com.yondervision.mi.dto.CMi045;
import com.yondervision.mi.dto.CMi046;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi046;
import com.yondervision.mi.result.WebApi04504_queryResult;
import com.yondervision.mi.result.WebApi04604_queryResult;

public interface WebApi045Service {
	/**
	 * 渠道流量控制新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi04501(CMi045 form, List<Mi007> list007) throws Exception;
	/**
	 * 渠道流量控制删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04502(CMi045 form) throws Exception;
	
	/**
	 * 渠道流量控制修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04503(CMi045 form, List<Mi007> list007) throws Exception;
	
	/**
	 * 渠道流量控制查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi04504_queryResult webapi04504(CMi045 form) throws Exception;

	/**
	 * 渠道流量监控新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi04601() throws Exception;
	/**
	 * 渠道流量监控分页查询
	 * @param form
	 * @throws Exception
	 */
	public WebApi04604_queryResult webapi04604(CMi046 form)throws Exception;
	/**
	 * 渠道流量监控历史详细查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi046> webapi04605(CMi046 form)throws Exception;

	/**
	 * 根据中心从缓存拿数据
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public Flow webapi04606(CMi046 form)throws Exception;
}
