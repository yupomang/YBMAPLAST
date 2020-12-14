package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi411;
import com.yondervision.mi.dto.Mi412;
import com.yondervision.mi.result.WebApi41104Query_queryResult;

import net.sf.json.JSONArray;

public interface WebApi412Service {
	//增加
	public String webapi411Add(CMi411 form) throws Exception;
	//删除
	public void webapi411Remove(CMi411 form) throws Exception;
	//修改（保存）
	public void webapi411Update(CMi411 form) throws Exception;
	//修改发送标记
	public void webapi411UpdateSend(CMi411 form) throws Exception;
	//查询分页
	public WebApi41104Query_queryResult webapi411Query(CMi411 form) throws Exception;
	//根据模板查询要素
	public List<Mi412> webapi411Detail(CMi411 form) throws Exception;
	//模板要素修改
	public void webapi41203(JSONArray arr) throws Exception;
	
}
