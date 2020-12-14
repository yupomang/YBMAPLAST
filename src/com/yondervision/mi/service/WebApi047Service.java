package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi037;
import com.yondervision.mi.dto.CMi039;
import com.yondervision.mi.dto.CMi047;
import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.result.WebApi03704_queryResult;
import com.yondervision.mi.result.WebApi03904_queryResult;
import com.yondervision.mi.result.WebApi04704_queryResult;

public interface WebApi047Service {
	/**
	 * 消息通知通讯录新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi04701(CMi047 form) throws Exception;
	/**
	 * 消息通知通讯录删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04702(CMi047 form) throws Exception;
	
	/**
	 * 消息通知通讯录修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi04703(CMi047 form) throws Exception;
	
	/**
	 * 消息通知通讯录查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi04704_queryResult webapi04704(CMi047 form) throws Exception;


	
	/**
	 * 监控主题新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi03901(CMi039 form) throws Exception;
	/**
	 * 监控主题删除
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi03902(CMi039 form) throws Exception;
	
	/**
	 * 监控主题修改
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public int webapi03903(CMi039 form) throws Exception;
	
	/**
	 * 监控主题查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi03904_queryResult webapi03904(CMi039 form) throws Exception;
	/**
	 * 获取所有的监控主题
	 * @return
	 * @throws Exception
	 */
	public List<Mi039> webapi03905() throws Exception;
	
	/**
	 * 记录明细查询
	 * @param form
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws Exception
	 */
	public WebApi03704_queryResult webapi03704(CMi037 form) throws Exception;
}
