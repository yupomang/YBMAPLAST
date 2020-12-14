package com.yondervision.mi.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi050;
import com.yondervision.mi.dto.CMi051;
import com.yondervision.mi.dto.CMi052;
import com.yondervision.mi.dto.CMi053;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.result.WebApi05004_queryResult;
import com.yondervision.mi.result.WebApi05104_queryResult;
import com.yondervision.mi.result.WebApi05204_queryResult;
import com.yondervision.mi.result.WebApi05304_queryResult;

public interface WebApi050Service {
	/**
	 * 渠道接口配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05001(CMi050 form) throws Exception;
	/**
	 * 渠道接口配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05002(CMi050 form) throws Exception;
	
	/**
	 * 渠道接口配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05003(CMi050 form) throws Exception;
	
	/**
	 * 渠道接口配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05004_queryResult webapi05004(CMi050 form) throws Exception;

	/**
	 * 依赖服务查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05104_queryResult webapi05005(CMi050 form) throws Exception;
	/**
	 * 查询所有的接口
	 * @return
	 * @throws Exception
	 */
	public List<Mi050> webapi05006() throws Exception;
	
	/**
	 * 查询050表全表数据
	 * @return
	 * @throws Exception
	 */
	public List<Mi050> queryMi050ForExcel() throws Exception;
	
	/**
	 * 渠道服务配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05101(CMi051 form) throws Exception;
	/**
	 * 渠道服务配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05102(CMi051 form) throws Exception;
	
	/**
	 * 渠道服务配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05103(CMi051 form) throws Exception;
	
	/**
	 * 渠道服务配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05104_queryResult webapi05104(CMi051 form) throws Exception;
	/**
	 * 根据服务获取接口列表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi050> webapi05105(CMi051 form) throws Exception;
	/**
	 * 获取所有服务列表
	 * @return
	 * @throws Exception
	 */
	public List<Mi051> webapi05106() throws Exception;
	/**
	 * 保存统计标识
	 * @return
	 * @throws Exception
	 */
	public void webapi05107(CMi052 form) throws Exception;
	/**
	 * 获取中心下所有已使用服务列表
	 * @return
	 * @throws Exception
	 */
	public List<Mi051> webapi05108(CMi051 form) throws Exception;
	/**
	 * 渠道接口-服务配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05201(CMi052 form) throws Exception;
	/**
	 * 渠道接口-服务配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05202(CMi052 form) throws Exception;
	
	/**
	 * 渠道接口-服务配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05203(CMi052 form) throws Exception;
	
	/**
	 * 渠道接口-服务配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05204_queryResult webapi05204(CMi052 form) throws Exception;
	/**
	 * 接口保存顺序
	 * @param arr
	 * @throws Exception
	 */
	public void webapi05205(JSONArray arr)throws Exception;
	/**
	 * 删除服务下的接口
	 * @param arr
	 * @throws Exception
	 */
	public void webapi05206(CMi052 form)throws Exception;
	
	/**
	 * 渠道接口-服务-应用配置新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi05301(CMi053 form) throws Exception;
	/**
	 * 渠道接口-服务-应用配置删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05302(CMi053 form) throws Exception;
	
	/**
	 * 渠渠道接口-服务-应用配置修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi05303(CMi053 form) throws Exception;
	
	/**
	 * 渠道接口-服务-应用配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi05304_queryResult webapi05304(CMi053 form) throws Exception;
	/**
	 * 渠道服务配置查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public JSONObject webapi05104_01(CMi051 form) throws Exception;
	
	/**
	 * 获取渠道服务列表
	 * @param list slist
	 * @return
	 * @throws Exception
	 */
	public List<Mi007> webapi05107(List<Mi007> list,List<String> slist) throws Exception;
	
	
	
	
	
	
	
	
	
	
}
