package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.dto.CMi031;
import com.yondervision.mi.dto.CMi107;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: WebApi103Service 
* @Description:目前其点处理接口
* @author Sunxl
* @date Sep 29, 2013 2:55:31 PM   
* 
*/ 
@SuppressWarnings("rawtypes")
public interface WebApi107Service {

	List<HashMap> webapi10704(CMi107 form) throws Exception;
	public List<HashMap> webapi10704Sun(CMi107 form) throws Exception;
	
	//渠道运行情况统计---开始
	public JSONArray webapi10705(CMi107 form) throws Exception;
	//渠道运行情况统计---结束
	
	//渠道运行情况统计---NEW 2017-08-03
	public JSONArray webapi1070501(CMi107 form) throws Exception;
	
	//渠道访问量统计
	public List<LinkedHashMap> webapi1070601(CMi107 form) throws Exception;
	//渠道功能活跃度统计
	public List<LinkedHashMap> webapiChannel21(CMi107 form) throws Exception;
	
	//访问时间段分布统计
	public List<LinkedHashMap> webapiChannel22(CMi107 form) throws Exception;
	
	//用户增长统计
	public List<List<LinkedHashMap>> webapi10707User(CMi031 form) throws Exception;
	
	//用户性别属性统计
	public List<LinkedHashMap> webapiChannel24(CMi107 form) throws Exception;
	
	//用户年龄属性统计
	public List<LinkedHashMap> webapiChannel25(CMi107 form) throws Exception;
	
	//业务分析-渠道分布统计
	public List<LinkedHashMap> webapi10708(CMi107 form) throws Exception;
	//业务分析-时间段分布统计
	public List webapi10709(CMi107 form) throws Exception;
	//业务分析-业务量统计非资金类
	public JSONArray webapi1071001(CMi107 form) throws Exception;
	//业务分析-业务量统计非资金类
	public JSONObject webapi1071001New(CMi107 form, HttpServletRequest request) throws Exception;
	//业务分析-业务量统计资金类
	public JSONArray webapi1071002(CMi107 form) throws Exception;
	//业务分析-用户分布统计
	public List<LinkedHashMap> webapi10711(CMi107 form) throws Exception;
	
	//mi107数据统计到mi099
	public void synMi107ToMi099(CMi107 form) throws Exception;
	
	//业务量统计表
	public JSONObject webapi1071003(CMi107 form) throws Exception;
	//各渠道业务量统计表
	public JSONObject webapi1071004(CMi107 form) throws Exception;
	//渠道运行指标栏目内容更新量
	public List<HashMap> contentUpdate(CMi107 form) throws Exception;
}
