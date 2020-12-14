package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi059;
import com.yondervision.mi.dto.Mi059Example;
import com.yondervision.mi.form.AppApi059Form;
import com.yondervision.mi.form.WebApi05904Form;
import com.yondervision.mi.result.WebApi05904_queryResult;

public interface WebApi059Service {
	/**
	 * 添加脱敏规则
	 */
	public String webapi05901(Mi059 form) throws Exception;
	/**
	 * 修改脱敏规则
	 */
	public boolean webapi05902(Mi059 form) throws Exception;
	/**
	 * 删除脱敏规则
	 */
	public boolean webapi05903(Mi059 form) throws Exception;
	/**
	 * 查询脱敏规则
	 */
	public WebApi05904_queryResult webapi05904(WebApi05904Form form) throws Exception;
	/**
	 * 渠道查询脱敏规则
	 */
	public WebApi05904_queryResult webapi05905(AppApi059Form form) throws Exception;
	
	/**
	 * 查询脱敏规则
	 * centerid 中心码4
	 * desensiCode 规则码
	 * flag 是否默认为  超级管理员规则，centerid=00000000
	 */
	public Mi059 selectDesensiInfo(String centerid, String desensiCode, String flag) throws Exception;
}
