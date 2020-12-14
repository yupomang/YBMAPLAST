package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.form.AppApi70001Form;
import com.yondervision.mi.form.AppApi701CommonForm;
import com.yondervision.mi.form.AppApi702CommonForm;
import com.yondervision.mi.form.WebApi70004Form;
import com.yondervision.mi.form.WebApi70204Form;
import com.yondervision.mi.form.WebApi70305Form;
import com.yondervision.mi.form.WebApi70504Form;
import com.yondervision.mi.form.WebApi70605Form;
import com.yondervision.mi.result.NewsBean;
import com.yondervision.mi.result.WebApi70004_queryResult;
import com.yondervision.mi.result.WebApi70204_queryResult;
import com.yondervision.mi.result.WebApi70305_queryResult;
import com.yondervision.mi.result.WebApi70504_queryResult;
import com.yondervision.mi.result.WebApi70605_queryResult;

/** 
* @ClassName: CMi701DAO 
* @Description
* @author gongqi
* @date July 18, 2014 9:33:25 PM
* 
*/ 
public interface CMi701DAO extends Mi701DAO {
	public WebApi70004_queryResult selectMi701Page(WebApi70004Form form)throws Exception;
	public List<Mi701> selectMi701(AppApi70001Form form, String validDate,String yingyong)throws Exception;
	public WebApi70305_queryResult selectMi701Page_Title(WebApi70305Form form)throws Exception;
	public WebApi70204_queryResult selectMi701Page_WebApi70204(WebApi70204Form form) throws Exception;
	
	public List<HashMap<String,String>> selectColumns(AppApi701CommonForm form) throws Exception;
	
	public int updatePraisecountsByPrimaryKey(Mi701 record) throws Exception;//更新增加点赞数
	
	public int updatePraisecountsSubByPrimaryKey(Mi701 record) throws Exception;//更新减少点赞数
	
	// 根据期次，获取新闻信息表中包含的版块
	public List<HashMap<String, String>> selectForumByClassification(HashMap<String, String> paraMap) throws Exception;

	// 根据期次，获取新闻信息表中包含的栏目（栏目不为空）
	public List<HashMap<String, String>> selectColumnsByClassification(HashMap<String, String> paraMap) throws Exception;
	
	public int selectForumInMi701Count(HashMap<String, String> paraMap) throws Exception;
	
	// 根据期次，获取新闻信息表中是否包含栏目为空的记录
	public int selectColumnsNullCountByClassification(HashMap<String, String> paraMap) throws Exception;
	
	// 根据期次、版块，获取新闻信息表中包含的栏目（栏目不为空）
	public List<HashMap<String, String>> selectColumnsByTimesForum(HashMap<String, String> paraMap) throws Exception;
	
	// 根据期次，获取新闻信息表中是否包含栏目为空的记录
	public int selectColumnsNullCountByTimesForum(HashMap<String, String> paraMap) throws Exception;
	
	// 根据城市中心ID获取栏目列表
	public List<HashMap<String, String>> selfSelectColumnsByCenterid(HashMap<String, String> paraMap) throws Exception;
	
	// 根据城市中心ID获取版块列表
	public List<HashMap<String, String>> selfSelectForumByCenterid(HashMap<String, String> paraMap) throws Exception;
	
	public WebApi70504_queryResult selectMi701Page_WebApi70504(WebApi70504Form form) throws Exception;
	
	// 获取新闻信息表中包含的版块count
	public int selectClassificInMi701Count(HashMap<String, String> paraMap) throws Exception;

	// 获取新闻信息表中包含的栏目count（栏目不为空）
	public int selectColumnsInMi701Count(HashMap<String, String> paraMap) throws Exception;
	
	// 获取新闻信息表中是否包含栏目为空的记录（即只有版块归属没有栏目归属，父级为0的记录）
	public int selectUpdicidZeroCountFromMi701(HashMap<String, String> paraMap) throws Exception;
	
	// 评论管理中，获取新闻标题列表
	public WebApi70605_queryResult selectMi701Page_TitleNoTimes(WebApi70605Form form)throws Exception;
	
	public List<HashMap<String,String>> selectColumnsNoTimes(AppApi702CommonForm form) throws Exception;
	
	// 根据城市中心ID获取栏目列表（无期次，版块和栏目维护在同一字段）
	public List<HashMap<String, String>> selfSelectColumnsNoTimesByCenterid(HashMap<String, String> paraMap) throws Exception;
	
	// 根据期次、版块，获取新闻信息表中包含的栏目（栏目不为空）
	public List<HashMap<String, String>> selectColumnsByForum(HashMap<String, String> paraMap) throws Exception;
	
	// 根据版块，获取新闻信息表中是否包含栏目为空的记录
	public int selectColumnsNullCountByForum(HashMap<String, String> paraMap) throws Exception;
	
	public List<Mi701WithBLOBs> selectMi701ByRandom(AppApi70001Form form, String validDate,String yingyong)throws Exception;
	
	public NewsBean selectMi701ToApp(String centerid, String channel, String classification,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception;
	
	// 查询mi701，如果上传分页标记则分页查询，否则查询所有
	public NewsBean selectMi701ToAppForPageOrAll(String centerid, String channel, String classification,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception ;
	public NewsBean selectMi701ForAllClassfication(String centerid, String channel, List<String> classlist,
			String keyword, String pagenum, String pagerows, String validDate,String yingyong) throws Exception;
	public List<HashMap> webapi701(String centerId, String pid, String startDate, String endDate) throws Exception;
	public List<HashMap> webapi701_01(String centerId, String startDate, String endDate) throws Exception;
}
