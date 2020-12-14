package com.yondervision.mi.service;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yondervision.mi.dto.CMi007;
import com.yondervision.mi.dto.CMi011;
import com.yondervision.mi.dto.CMi012;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.dto.Mi010;
import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.result.PtlApi002PageQueryResult;
import com.yondervision.mi.result.PtlApi20000ResultAttr;
import com.yondervision.mi.result.PtlApiTreeResult;

/** 
* @ClassName: PtlApiService 
* @Description: 客户化管理接口
* @author gongqi
* @date 2013-10-10
* 
*/ 
public interface PtlApi002Service {
	
	/**      码表管理 start       **/
	/**
	 * 检索码表中相应条件的子级的记录
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
	 * 城市中心列表查询Json
	 * @param pid
	 * @return JSONArray
	 */
	public JSONArray getCenterIdListJson() throws Exception;
	
	/**
	 * 父级编码列表查询Json
	 * @param centerid
	 * @return JSONArray
	 */
	public JSONArray getParentCodeListJson(String centerid) throws Exception;

	/**
	 * 码表查询展示树
	 * @param pid
	 * @return
	 */
	public List<Mi007> getCodeListByPid(String pid, String centerid) throws Exception;
	
	/**
	 * 根据中心id获取中心名称
	 * @param centerid
	 * @return
	 */
	public String getCenterName(String centerid)throws Exception;
	
	/**
	 * 根据中心ID 上一级编码，获取上一级编码名称
	 * @param centerid
	 * @param updicid
	 * @param itemid
	 * @param itemval
	 * @return
	 * @throws Exception
	 */
	public String getUpdicname(String centerid, int updicid, String itemid, String itemval) throws Exception;
	
	/**
	 * 校验数据能否入库的合法性(添加、修改)
	 * @param form
	 * @param method
	 * @return
	 */
	public boolean checkData(CMi007 form, String method) throws Exception;
	
	/**
	 * 在结果集中放入节点相关业务信息
	 * @param mi007
	 * @return
	 * @throws Exception
	 */
	public PtlApi20000ResultAttr setNodeAttributes(Mi007 mi007) throws Exception;
	
	/**
	 * 码表新增
	 * @param form
	 */
	public void addMulDic(CMi007 form) throws Exception;
	
	/**
	 * 码表新增,并返回追加对象信息
	 * @throws Exception 
	 */
	public PtlApiTreeResult addMulDicReturnObj(CMi007 form) throws Exception;
	
	/**
	 * 码表删除
	 * @param form
	 * @return
	 */
	public int delMulDic(CMi007 form) throws Exception;
	
	/**
	 * 码表修改
	 * @param form
	 * @return
	 */
	public int updMulDic(CMi007 form) throws Exception;
	/**      码表管理 end         **/
	
	/**      通讯表管理 start     **/
	/**
	 * 中心列表获取
	 * @param form
	 */
	public JSONObject getCenterListJson()throws Exception;
	
	/**
	 * 通讯表新增
	 * @param form
	 */
	public void addMessage(CMi011 form) throws Exception;
	
	/**
	 * 通讯表删除
	 * @param form
	 * @return
	 */
	public int delMessage(CMi011 form) throws Exception;
	
	/**
	 * 通讯表修改
	 * @param form
	 * @return
	 */
	public int updMessage(CMi011 form) throws Exception;
	
	/**
	 * 通讯表查询
	 * @param form
	 * @return
	 */
	public List<CMi011> queryMessage(CMi011 form)throws Exception;
	/**      通讯表管理 end     **/
	
	/**      日志代码表管理 start    **/
	/**
	 * 日志代码表新增
	 * @param form
	 */
	public void addLog(Mi009 form)throws Exception;
	
	/**
	 * 日志代码表删除
	 * @param form
	 * @return
	 */
	public int delLog(Mi009 form) throws Exception;
	
	/**
	 * 日志代码表修改
	 * @param form
	 * @return
	 */
	public int updLog(Mi009 form) throws Exception;
	
	/**
	 * 日志代码表查询分页(条件)
	 * @param form
	 * @return
	 */
	public JSONObject queryLog(Mi009 form,Integer page,Integer rows) throws Exception;
	
	/**
	 * 日志代码表查询
	 * @param form
	 * @return
	 */
	public List<Mi009> queryLogCodeAll() throws Exception;
	/**      日志代码表管理 end     **/
	
	/**      错误代码表管理 start    **/
	/**
	 * 错误代码表新增
	 * @param form
	 */
	public void addErrCode(Mi010 form) throws Exception;
	
	/**
	 * 错误代码表删除
	 * @param form
	 * @return
	 */
	public int delErrCode(Mi010 form) throws Exception;
	
	/**
	 * 错误代码表修改
	 * @param form
	 * @return
	 */
	public int updErrCode(Mi010 form) throws Exception;
	
	/**
	 * 错误代码表查询(条件)
	 * @param form
	 * @return
	 */
	public List<Mi010> queryErrCode(Mi010 form) throws Exception;
	
	/**
	 * 错误代码表查询分页
	 * @param form
	 * @return
	 */
	public JSONObject pageQueryErrCode(Mi010 form, Integer page, Integer rows) throws Exception;

	/**
	 * 错误代码表查询
	 * @param form
	 * @return
	 */
	public List<Mi010> queryErrCodeAll() throws Exception;
	/**      错误代码表管理 end     **/
	
	/**      推送消息通讯参数配置表管理 start     **/
	/**
	 * 推送消息通讯参数配置表新增
	 * @param form
	 */
	public void addSendMessageParam(CMi012 form) throws Exception;
	
	/**
	 * 推送消息通讯参数配置表删除
	 * @param form
	 * @return
	 */
	public int delSendMessageParam(CMi012 form) throws Exception;
	
	/**
	 * 推送消息通讯参数配置表修改
	 * @param form
	 * @return
	 */
	public int updSendMessageParam(CMi012 form) throws Exception;
	
	/**
	 * 推送消息通讯参数配置表查询
	 * @param form
	 * @return
	 */
	public List<CMi012> querySendMessageParam(CMi012 form)throws Exception;
	/**      推送消息通讯参数配置表管理 end    **/
	
	/**      业务日志表管理 start   **/
	/**
	 * APP业务日志表按条件分页查询
	 * @param form
	 * @return
	 */
	public PtlApi002PageQueryResult pageQueryAppBusiLog(CMi107 form)throws Exception;
	
	/**
	 * 业务日志表条件查询
	 * @param form
	 * @return
	 */
	public Mi107 queryBusiLog(String seqno)throws Exception;
	
	/**
	 * APP业务日志表查询所有记录
	 * @param form
	 * @return
	 */
	public List<Mi107> queryAppBusiLogAll() throws Exception;
	
	/**
	 * 业务日志表删除
	 * @param form
	 * @return
	 */
	public int delBusiLog(String seqno) throws Exception;
	
	/**
	 * MI中心前置业务日志表按条件分页查询
	 * @param form
	 * @return
	 */
	public PtlApi002PageQueryResult pageQueryMiBusiLog(CMi107 form)throws Exception;
	/**      业务日志表管理 end     **/
}
