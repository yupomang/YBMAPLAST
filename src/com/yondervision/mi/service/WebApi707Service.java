package com.yondervision.mi.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.yondervision.mi.dto.CMi707;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.form.WebApi70401Form;
import com.yondervision.mi.form.WebApi70402Form;
import com.yondervision.mi.form.WebApi70403Form;
import com.yondervision.mi.form.WebApi70404Form;
import com.yondervision.mi.form.WebApi70701Form;
import com.yondervision.mi.form.WebApi70702Form;
import com.yondervision.mi.form.WebApi70704Form;
import com.yondervision.mi.result.Page70701QryResultAttr;
import com.yondervision.mi.result.Page70701TreeResult;
import com.yondervision.mi.result.WebApi70404_queryResult;
import com.yondervision.mi.result.WebApi70704_queryResult;

/** 
* @ClassName: WebApi707Service 
* @Description:栏目配置处理接口
* @author gongqi
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface WebApi707Service {
	/**
	 * 检索栏目中相应条件的子级的记录
	 * @param centerid
	 * @param dicid
	 * @return
	 */
	public int getChildCounts(String centerid, int dicid) throws Exception;
	
	/**
	 * 城市中心列表查询
	 * @param pid
	 * @return
	 */
	public List<Mi001> getCenterIdList() throws Exception;
	
	/**
	 * 对应城市中心ID信息查询
	 * @param pid
	 * @return
	 */
	public List<Mi001> getCenterIdRecord(String centerid) throws Exception;
	
//	/**
//	 * 城市中心列表查询Json
//	 * @param pid
//	 * @return JSONArray
//	 */
//	public JSONArray getCenterIdListJson() throws Exception;
//	
//	/**
//	 * 父级编码列表查询Json
//	 * @param centerid
//	 * @return JSONArray
//	 */
//	public JSONArray getParentCodeListJson(String centerid) throws Exception;

	/**
	 * 栏目查询展示树
	 * @param pid
	 * @return
	 */
	public List<Mi707> getCodeListByPid(String pid, String centerid) throws Exception;
	
	/**
	 * 根据中心id获取中心名称
	 * @param centerid
	 * @return
	 */
	public String getCenterName(String centerid)throws Exception;
	
//	/**
//	 * 根据中心ID 上一级编码，获取上一级编码名称
//	 * @param centerid
//	 * @param updicid
//	 * @param itemid
//	 * @param itemval
//	 * @return
//	 * @throws Exception
//	 */
//	public String getUpdicname(String centerid, int updicid, String itemid, String itemval) throws Exception;
	
	/**
	 * 校验数据能否入库的合法性(添加、修改)
	 * @param form
	 * @param method
	 * @return
	 */
	public boolean checkData(CMi707 form, String method) throws Exception;
	
	/**
	 * 在结果集中放入节点相关业务信息
	 * @param mi007
	 * @return
	 * @throws Exception
	 */
	public Page70701QryResultAttr setNodeAttributes(Mi707 mi707) throws Exception;
	
	/**
	 * 栏目新增
	 * @param form
	 */
	public void addMulDic(CMi707 form) throws Exception;
	
	/**
	 * 栏目新增,并返回追加对象信息
	 * @throws Exception 
	 */
	public Page70701TreeResult addMulDicReturnObj(CMi707 form) throws Exception;
	
	/**
	 * 栏目删除
	 * @param form
	 * @return
	 */
	public int delMulDic(CMi707 form) throws Exception;
	
	/**
	 * 栏目修改
	 * @param form
	 * @return
	 */
	public int updMulDic(CMi707 form) throws Exception;
	
//	/**
//	 * 查询对应 上级编码和城市中心代码的树
//	 * @param pid
//	 * @param centerid
//	 * @return
//	 * @throws Exception
//	 */
//	public JSONArray getClassificationTreeJsonArray(String pid, String centerid) throws Exception;
	
	/**
	 * 内容展现项配置新增
	 */
	public void webapi70701(WebApi70701Form form) throws Exception;
	
	/**
	 * 内容展现项配置删除
	 */
	public void webapi70702(WebApi70702Form form) throws Exception;
	
//	/**
//	 * 内容展现项配置修改
//	 */
//	public void webapi70703(WebApi70703Form form) throws Exception;
	
	/**
	 * 内容展现项配置查询
	 */
	public List<WebApi70704_queryResult> webapi70704(WebApi70704Form form) throws Exception;
	/**
	 * 根据中心查询所有的栏目
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public List<Mi707> webapi70705(String centerid) throws Exception;
	
	public Mi707 get707(String centerid, String itemid);
}
