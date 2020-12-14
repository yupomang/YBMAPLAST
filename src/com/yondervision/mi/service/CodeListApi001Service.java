package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi621;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.result.NewspapersTitleInfoBean;

public interface CodeListApi001Service {
	
	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return String
	 */
	public String getCodeVal(String centerid, String param);
	
	/**
	 * 获取码表中对应param的子级编码列表
	 * @param centerid
	 * @param param
	 * @return List
	 */
	public List<Mi007> getCodeList(String centerid, String param);
	
	/**
	 * 获取码表中子级编码列表
	 * @param centerid
	 * @param param
	 * @return List
	 */
	public List<Mi007> getSubCodeList(String centerid, int dicid);
	
	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return JSONObject
	 */
	public JSONObject getCodeValJson(String centerid, String param);
	
	/**
	 * 获取码表中对应param的子级编码列表
	 * @param centerid
	 * @param param
	 * @return JSONArray
	 */
	public JSONArray getCodeListJson(String centerid, String param);
	
	/**
	 * 取城市中心信息
	 */
	public List<Mi001> getCityMessage();
	/**
	 * 取城区域信息
	 */
	public List<Mi202> getAreaMessage(String centerid);
	
	/**
	 * 查询有效的软件版本编号
	 */
	public List<Mi106> getVersionno(String centerid);
	
	/**
	 * 查询有效的软件版本编号
	 */
	public JSONObject getVersionnoJson();
	
	/**
	 * 查询有效的软件版本编号
	 */
	public List<Mi201> getMi201(String centerid);
	/**
	 * 查询预约业务类型
	 * @param centerid
	 * @return
	 */
	public List<Mi620> getBussType(String centerid);
	/**
	 * 查询预约时段模版信息
	 * @param centerid
	 * @return
	 */
	public List<Mi621> getBussTempla(String centerid);
	/**
	 * 查询预约时段明细信息
	 * @param centerid
	 * @return
	 */
	public List<Mi622> getBussTemplaDetail(String template);
	
	/**
	 * 查询一个中心的可预约网点
	 * @param centerid
	 * @return
	 */
	public List<HashMap> getWebSiteInfo(String centerid);
	/**
	 * 根据报刊期次，Mi701获取页面版块下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getForumByTimesFromMi701(String centerid, String newspapertimes)throws Exception;
	
	/**
	 * 根据报刊期次，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesFromMi701(String centerid, String newspapertimes)throws Exception;
	
	/**
	 * 根据报刊期次、版块，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesForumFromMi701(String centerid, String newspapertimes, String newspaperforum)throws Exception;
	
	
	/**
	 * 根据报刊期次，Mi704获取页面版块下拉选择框的级联数据
	 */
	public List<Mi704> getForumByTimesFromMi704(String centerid, String newspapertimes)throws Exception;
	
	/**
	 * 根据报刊期次，Mi704获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesFromMi704(String centerid, String newspapertimes)throws Exception;
	
	/**
	 * 根据报刊期次、版块，Mi704获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByTimesForumFromMi704(String centerid, String newspapertimes, String newspaperforum)throws Exception;
	
	// TODO 
	/**
	 * [版块栏目升级合并后废弃]
	 * Mi701获取页面版块下拉选择框数据（Mi007配合mi701共同实现）
	 * 根据公共参数centerid获取当前新闻中实际已发布过新闻的版块的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getForumFromMi007AndMi701(String centerid)throws Exception;
	
	/**
	 * Mi701获取页面栏目下拉选择框数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsFromMi007AndMi701(String centerid)throws Exception;
	
	/**
	 * 获取码表中对应编码的名称
	 * @param centerid
	 * @param dicid
	 * @return String
	 */
	public String getCodeValWithDicid(String centerid, int dicid);
	
	/**
	 * 根据报刊版块，Mi701获取页面栏目下拉选择框的级联数据
	 */
	public List<NewspapersTitleInfoBean> getColumnsByForumFromMi701(String centerid,
			String newspaperforum)throws Exception;
	
	/**
	 * 查询对应 上级编码和城市中心代码的树
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public JSONArray getClassificationTreeJsonArray(String pid, String centerid) throws Exception;
	
	/**
	 * Mi707获取版块数据（Mi707配合mi701共同实现）
	 * 根据公共参数centerid和所属上级栏目，获取当前新闻中实际已发布过新闻的版块的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getClassificFromMi707AndMi701(String updicid, String centerid,String channel)throws Exception;
	
	/**
	 * 查询对应 上级编码和城市中心代码，状态为开放的记录的 信息失效日期【主要用于app】
	 * @param pid
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public String getClassificationInvalidDate(String pid, String centerid) throws Exception;
	
	/**
	 * 获取栏目管理中对应编码的名称
	 * @param centerid
	 * @param param
	 * @return String
	 */
	public String getCodeValWithDicidFromMi707(String centerid, int dicid);
	
	/**
	 * Mi707获取版块数据（Mi707配合mi701共同实现,Mi707中，父级不等于某值，且在mi701中有对应记录存在的栏目列表）
	 * 根据公共参数centerid和所属上级编码获取当前新闻中实际已发布过新闻的版块/栏目的列表(按编号从小到大升序)
	 */
	public List<NewspapersTitleInfoBean> getClassificNotEqualValFromMi707AndMi701(String updicid, String centerid,String channel)throws Exception;
	
	//根据中心和设备获取版本号
	public List<Mi106> ptl40013Verno(String centerid,String devtype)throws Exception;
}

