package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.JobOffers;
import com.yondervision.mi.form.ItemInfo;
import com.yondervision.mi.form.WebApi99901Form;
import com.yondervision.mi.form.WebApi99902Form;
import com.yondervision.mi.result.WebApi99901_queryResult;

/** 
* @ClassName: WebApi999Service 
* @Description:应聘信息管理处理接口
* @author gongqi
* 
*/ 
public interface WebApi999Service {
	/**
	 * 应聘区域列表获取
	 */
	public List<ItemInfo> getApplyAreaList(String nodeName) throws Exception;
	
	/**
	 * 应聘职位列表获取
	 */
	public List<ItemInfo> getApplyPositionList(String nodeName, String code) throws Exception;
	
	/**
	 * 应聘信息查询
	 */
	public WebApi99901_queryResult webapi99901(WebApi99901Form form) throws Exception;
	
	/**
	 * 应聘信息已读设置
	 */
	public int webapi99902(WebApi99902Form form) throws Exception;

	/**
	 * 应聘信息删除
	 */
	public int webapi99903(WebApi99902Form form) throws Exception;
	
	/**
	 * 应聘信息查询
	 */
	public List<JobOffers> webapi99904(WebApi99901Form form) throws Exception;
	
	/**
	 * 应聘信息查询
	 */
	public List<JobOffers> webapi99905(String seqno) throws Exception;
}
