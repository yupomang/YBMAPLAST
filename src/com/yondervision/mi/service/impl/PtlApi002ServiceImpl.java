package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi107DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi009DAO;
import com.yondervision.mi.dao.Mi010DAO;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dao.Mi012DAO;
import com.yondervision.mi.dto.CMi007;
import com.yondervision.mi.dto.CMi011;
import com.yondervision.mi.dto.CMi012;
import com.yondervision.mi.dto.CMi107;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.dto.Mi009Example;
import com.yondervision.mi.dto.Mi010;
import com.yondervision.mi.dto.Mi010Example;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.dto.Mi012;
import com.yondervision.mi.dto.Mi012Example;
import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.dto.Mi107Example;
import com.yondervision.mi.result.PtlApi002PageQueryResult;
import com.yondervision.mi.result.PtlApi20000ResultAttr;
import com.yondervision.mi.result.PtlApiTreeResult;
import com.yondervision.mi.service.PtlApi002Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.Datelet;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: PtlApi002ServiceImpl
* @Description: 客户化管理实现
* @author gongqi
* @date 2013-10-10
* 
*/ 
public class PtlApi002ServiceImpl implements PtlApi002Service {

	public Mi001DAO mi001Dao = null;
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	
	public Mi007DAO mi007Dao = null;
	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}
	
	public Mi009DAO mi009Dao = null;
	public void setMi009Dao(Mi009DAO mi009Dao) {
		this.mi009Dao = mi009Dao;
	}
	
	public Mi010DAO mi010Dao = null;
	public void setMi010Dao(Mi010DAO mi010Dao) {
		this.mi010Dao = mi010Dao;
	}
	
	public Mi011DAO mi011Dao = null;
	public void setMi011Dao(Mi011DAO mi011Dao) {
		this.mi011Dao = mi011Dao;
	}

	public Mi012DAO mi012Dao = null;
	public void setMi012Dao(Mi012DAO mi012Dao) {
		this.mi012Dao = mi012Dao;
	}

	public CMi107DAO cmi107Dao = null;
	public void setCmi107Dao(CMi107DAO cmi107Dao) {
		this.cmi107Dao = cmi107Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	/**      码表管理 start     **/
	/**
	 * 检索码表中相应条件的子级的记录
	 * @param centerid
	 * @param dicid
	 * @return
	 */
	public int getChildCounts(String centerid, int dicid)throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(dicid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(centerid).andUpdicidEqualTo(dicid);
		return mi007Dao.countByExample(example);
	}
	
	/**
	 * 城市中心列表查询
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi001> getCenterIdList() throws Exception{
		Mi001Example example = new Mi001Example();
		example.setOrderByClause("CENTERID ASC");
		return mi001Dao.selectByExample(example);
	}
	
	/**
	 * 城市中心列表查询Json
	 * @param pid
	 * @return JSONArray
	 */
	public JSONArray getCenterIdListJson() throws Exception{
		 ObjectMapper mapper = new  ObjectMapper();
		 List<Mi001> list = getCenterIdList();
		 return mapper.convertValue(list, JSONArray.class); 
	}
	
	/**
	 * 父级编码列表查询Json
	 * @param centerid
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getParentCodeListJson(String centerid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		ObjectMapper mapper = new  ObjectMapper();
		Mi007Example example = new Mi007Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(0);
		List<Mi007> list = mi007Dao.selectByExample(example);
		return mapper.convertValue(list, JSONArray.class);
	}
	
	/**
	 * 码表查询展示树
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi007> getCodeListByPid(String pid, String centerid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 上传参数空值校验
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+pid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		if ("000000000".equals(pid) && "000000000".equals(centerid)) {
			List<Mi001> centerList = getCenterIdList();
			List<Mi007> resultList = new ArrayList<Mi007>();
			for (int i = 0 ;i <centerList.size(); i++) {
				Mi007 mi007 = new Mi007();
				mi007.setCenterid(centerList.get(i).getCenterid());
				mi007.setDicid(0);
				mi007.setItemid(centerList.get(i).getCenterid());
				mi007.setItemval(centerList.get(i).getCentername());
				mi007.setUpdicid(0);
				resultList.add(mi007);
			}
			return resultList;
		}
		// 要打开节点为城市中心名称
		else if ("000000000".equals(pid)) {
			//查询符合当前中心id的所有上级编码为0的记录
			Mi007Example example = new Mi007Example();
			
			example.createCriteria()
			.andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(0);
			example.setOrderByClause("DICID ASC");
			return mi007Dao.selectByExample(example);
		}
		//查询符合当前中心id的所有上级编码为0的记录
		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(centerid).andUpdicidEqualTo(Integer.parseInt(pid));
		example.setOrderByClause("DICID ASC");
		
		return mi007Dao.selectByExample(example);
	}
	
	/**
	 * 校验数据能否入库的合法性(添加、修改)
	 * @param form
	 * @param method （add/modify）
	 * @return
	 */
	public boolean checkData(CMi007 form, String method) throws Exception{
		
		// 如果是修改操作，则先判断是否对编码进行修改
		if ("modify".equals(method)) {
			// 校验编码是否发生变化，编码发生变化，则进行查询操作，否则可直接入库
			if (form.getItemid().equals(form.getOlditemid())) {
				return true;
			}
		}

		Mi007Example example = new Mi007Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andUpdicidEqualTo(form.getUpdicid())
		.andItemidEqualTo(form.getItemid());
		
		int result = mi007Dao.countByExample(example);
		if (result != 0) {
			return false;
		}
		
		return true;
	}

	/**
	 * 在结果集中放入节点相关业务信息
	 * @param mi007
	 * @return
	 * @throws Exception
	 */
	public PtlApi20000ResultAttr setNodeAttributes(Mi007 mi007) throws Exception{
		PtlApi20000ResultAttr attributes = new PtlApi20000ResultAttr();
		
		String centerid = mi007.getCenterid();
		int updicid = mi007.getUpdicid();
		String itemid = mi007.getItemid();
		String itemval = mi007.getItemval();
		
		attributes.setCenterid(centerid);
		attributes.setCentername(getCenterName(centerid));
		attributes.setDicid(mi007.getDicid());
		attributes.setItemid(itemid);
		attributes.setItemval(itemval);
		attributes.setUpdicid(updicid);
		attributes.setUpdicname(getUpdicname(centerid, updicid, itemid, itemval));
		
		return attributes;
	}
	
	/**
	 * 根据中心ID获取中心名称
	 * @param centerid
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getCenterName(String centerid) throws Exception {
		String centername = null;
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridEqualTo(centerid);
		List<Mi001> mi001ResultList = mi001Dao.selectByExample(example);
		if (CommonUtil.isEmpty(mi001ResultList)) {
			throw new Exception("获取所属中心信息失败！");
		}
		
		centername = ((Mi001)mi001ResultList.get(0)).getCentername();
		return centername;
	}
	
	/**
	 * 根据中心ID 上一级编码，获取上一级编码名称
	 * @param centerid
	 * @param updicid
	 * @param itemid
	 * @param itemval
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getUpdicname(String centerid, int updicid, String itemid, String itemval) throws Exception{
		String updicname = null;
		
		// 如果本身以为最上级的编码，最直接将其编码作为上一级的输出
		if (updicid == 0) {
			updicname = itemid + '-' + itemval;
		}else {
			Mi007Example example = new Mi007Example();
			example.createCriteria().andCenteridEqualTo(centerid).andDicidEqualTo(updicid);
			List<Mi007> mi007ResultList = mi007Dao.selectByExample(example);
			
			if (CommonUtil.isEmpty(mi007ResultList)) {
				throw new Exception("获取上一级编码信息失败！");
			}
			
			updicname = ((Mi007)mi007ResultList.get(0)).getItemid()
						+ '-' + ((Mi007)mi007ResultList.get(0)).getItemval();
		}

		return updicname;
	}
	
	/**
	 * 码表新增
	 */
	public void addMulDic(CMi007 form) throws Exception{
		Mi007 mi007 = new Mi007();
		
		mi007.setCenterid(form.getCenterid());
		mi007.setDicid(Integer.valueOf(commonUtil.genKey("mi007").intValue()));
		mi007.setItemid(form.getItemid());
		mi007.setItemval(form.getItemval());
		mi007.setUpdicid(form.getUpdicid());
		
		mi007Dao.insert(mi007);
	}
	
	/**
	 * 码表新增,并返回追加对象信息
	 * @throws Exception 
	 */
	public PtlApiTreeResult addMulDicReturnObj(CMi007 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 上传参数空值校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getUpdicid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+form.getUpdicid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getItemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("当前编码："+form.getItemid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"当前编码");
		}
		if (CommonUtil.isEmpty(form.getItemval())) {
			log.error(ERROR.PARAMS_NULL.getLogText("当前编码名称："+form.getItemval()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"当前编码名称");
		}
		
		// 添加内容进行入库合法性检查
		boolean checkFlg = checkData(form, "add");
		if (!checkFlg) {
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}
		
		PtlApiTreeResult treeResult = new PtlApiTreeResult();
		Mi007 mi007 = new Mi007();
		
		mi007.setCenterid(form.getCenterid());
		mi007.setDicid(Integer.valueOf(commonUtil.genKey("MI007").intValue()));
		mi007.setItemid(form.getItemid());
		mi007.setItemval(form.getItemval());
		mi007.setUpdicid(form.getUpdicid());
		
		mi007Dao.insert(mi007);
		
		//生成添加节点的信息，返回页面用于append
		treeResult.setId(mi007.getDicid().toString());
		treeResult.setText(mi007.getItemval());
		treeResult.setState("open");
		
		treeResult.setAttributes(setNodeAttributes(mi007));

		return treeResult;
	}

	/**
	 * 码表删除
	 */
	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public int delMulDic(CMi007 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getDicids())) {
			log.error(ERROR.PARAMS_NULL.getLogText("当前编码："+form.getDicids()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"当前编码获取失败");
		}
		
		// 是否允许删除的判定
		String[] dicids = form.getDicids().split(",");
		if ("000000000".equals(form.getCenterid()) || "0".equals(dicids[0])) {
			log.info(LOG.SELF_LOG.getLogText("中心级码表显示菜单节点不能被删除！"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"不允许操作，请到中心管理菜单进行维护");
		}
		
		int result = 0;
		
		for(int i = 0; i < dicids.length; i++) {
			// 检索删除内容是否含有子项
			Mi007Example queryExample = new Mi007Example();
			if (CommonUtil.isEmpty(dicids[i])) {
				log.info(LOG.SELF_LOG.getLogText("没有在码表信息显示区域选择记录进行删除维护！"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请到上级菜单进行操作");
			}
			queryExample.createCriteria().andCenteridEqualTo(form.getCenterid())
			.andUpdicidEqualTo(Integer.parseInt(dicids[i]));
			List<Mi007> queryResultList = mi007Dao.selectByExample(queryExample);
			
			// 如果含有子项，则先将子项删除
			Mi007 mi007 = new Mi007();
			for (int j = 0; j < queryResultList.size(); j++) {
				mi007 = queryResultList.get(j);

				// 进行子项内容的删除
				int delChildresult = delMulDicDetail(mi007.getCenterid(), mi007.getDicid());
				
				// 删除子项失败，直接返回
				if(0 == delChildresult) {
					log.error(ERROR.ERRCODE_LOG_800004.getLogText("上级编码为当前编码:"+mi007.getDicid()));
					throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"码表信息MI007");
				}
			}
			
			// 进行码表内容的删除
			result = delMulDicDetail(form.getCenterid(), Integer.parseInt(dicids[i]));
			if (0 == result) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("当前编码:"+mi007.getDicid()));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"码表信息MI007");
			}
		}
		return result;

	}
	
	private int delMulDicDetail(String centerid, int dicid) throws Exception{
		int result = 0;
		// 进行码表内容的删除
		Mi007Example example = new Mi007Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andDicidEqualTo(dicid);
		
		result = mi007Dao.deleteByExample(example);
		return result;
	}

	/**
	 * 码表修改
	 */
	public int updMulDic(CMi007 form)throws Exception {

		Logger log = LoggerUtil.getLogger();
		
		// 上传参数空值校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getDicid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("编码："+form.getDicid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getItemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("当前编码："+form.getItemid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"当前编码");
		}
		if (CommonUtil.isEmpty(form.getItemval())) {
			log.error(ERROR.PARAMS_NULL.getLogText("当前编码名称："+form.getItemval()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"当前编码名称");
		}
		if (CommonUtil.isEmpty(form.getUpdicid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+form.getUpdicid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
		}

		// 添加内容进行入库合法性检查
		boolean checkFlg = checkData(form, "modify");
		if (!checkFlg) {
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}
		
		Mi007 mi007 = new Mi007();
		Mi007Example example = new Mi007Example();

		mi007.setCenterid(form.getCenterid());
		mi007.setDicid(form.getDicid());
		mi007.setItemid(form.getItemid());
		mi007.setItemval(form.getItemval());
		mi007.setUpdicid(form.getUpdicid());
		
		example.createCriteria()
		.andCenteridEqualTo(form.getCenterid())
		.andDicidEqualTo(form.getDicid());
		
		int result =  mi007Dao.updateByExampleSelective(mi007, example);
		if (0 == result) {
			log.error(ERROR.NO_DATA.getLogText("码表MI007", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"码表MI007");
		}

		return result;
	}
	/**      码表管理end     **/
	
	/**      通讯表管理 start     **/
	/**
	 * 中心列表获取
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCenterListJson()throws Exception {
		  ObjectMapper mapper = new  ObjectMapper();
		  Mi001Example example = new Mi001Example();
		  example.setOrderByClause("CENTERID ASC");
		  List<Mi001> list = mi001Dao.selectByExample(example);
		  HashMap map = new HashMap();
		  map.put("rows", list);
		  return mapper.convertValue(map, JSONObject.class); 
	}
	
	/**
	 * 添加数据时，校验城市ID是否存在，存在才可进行通讯记录添加
	 * @param form
	 * @param method 
	 * @return
	 */
	private boolean checkCenterInfo(String centerid) throws Exception{
		
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridEqualTo(centerid);
		int queryResult = mi001Dao.countByExample(example);
		// 无此记录,不能进行通讯信息的添加
		if (0 == queryResult) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 通讯表查询记录数
	 */
	private int getMessageCounts(CMi011 form) throws Exception{
		
		int counts = 0;
		
		Mi011Example example = new Mi011Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		
		counts = mi011Dao.countByExample(example);

		return counts;
	}
	
	/**
	 * 通讯表新增记录
	 */
	public void addMessage(CMi011 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		// 校验添加中心是否存在
		boolean checkFlg = checkCenterInfo(form.getCenterid());
		if (checkFlg) {
			// 获取当前是否有相同记录
			int count = getMessageCounts(form);
			if (0 == count) {
				Mi011 mi011 = new Mi011();
				mi011.setCenterid(form.getCenterid());
				mi011.setClassname(form.getClassname());
				mi011.setUrl(form.getUrl());
				mi011Dao.insert(mi011);
				
			}else{
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
			}
		}else{
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("中心城市编码:"+form.getCenterid()+"的中心信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前添加中心不存在，请先添加中心信息后进行此操作!");
		}
	}
	
	/**
	 * 通讯表删除记录
	 */
	public int delMessage(CMi011 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		int result = 0;
		Mi011Example example = new Mi011Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		result = mi011Dao.deleteByExample(example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("中心城市编码:"+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"通讯表MI011");
		}
		
		return result;
	}

	/**
	 * 通讯表修改记录
	 */
	public int updMessage(CMi011 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		// 获取当前记录是否存在
		int count = getMessageCounts(form);
		if (0 == count) {
			log.error(ERROR.NO_DATA.getLogText("通讯表MI011","中心城市编码："+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"通讯表MI011");
		}
		
		int result = 0;
		Mi011Example example = new Mi011Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		Mi011 mi011 = new Mi011();
		mi011.setCenterid(form.getCenterid());
		mi011.setClassname(form.getClassname());
		mi011.setUrl(form.getUrl());
		result = mi011Dao.updateByExample(mi011, example);
		
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("通讯表MI011","中心城市编码："+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "中心城市编码："+form.getCenterid());
		}
		return result;
	}

	/**
	 * 通讯表查询记录
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<CMi011> queryMessage(CMi011 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		List<CMi011> list = new ArrayList<CMi011>();
		// Mi001获取中心名称
		Mi001Example mi001example = new Mi001Example();
		mi001example.createCriteria().andCenteridEqualTo(form.getCenterid());
		List<Mi001> mi001ResultList = mi001Dao.selectByExample(mi001example);
		if (!CommonUtil.isEmpty(mi001ResultList)) {
			Mi001 mi001 = mi001ResultList.get(0);
			
			Mi011Example mi011example = new Mi011Example();
			mi011example.createCriteria().andCenteridEqualTo(form.getCenterid());
			List<Mi011> listTmp = new ArrayList<Mi011>();
			listTmp = mi011Dao.selectByExample(mi011example);
			
			if (!CommonUtil.isEmpty(listTmp)) {
				CMi011 cmi011 = new CMi011();
				cmi011.setCenterid(listTmp.get(0).getCenterid());
				cmi011.setCentername(mi001.getCentername());
				cmi011.setClassname(listTmp.get(0).getClassname());
				cmi011.setUrl(listTmp.get(0).getUrl());
				list.add(cmi011);
			}
		}

		return list;
	}
	/**      通讯表管理 end     **/
	
	/**      日志代码表管理start     **/
	/**
	 * 日志代码表新增记录
	 */
	public void addLog(Mi009 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getLogcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("日志代码："+form.getLogcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"日志代码");
		}
		if (CommonUtil.isEmpty(form.getLogtext())) {
			log.error(ERROR.PARAMS_NULL.getLogText("日志内容："+form.getLogtext()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"日志内容");
		}
		
		// 校验新增信息是否已经存在
		int count = getLogCounts(form);
		if(0 != count){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}
		
		Mi009 mi009 = new Mi009();
		mi009.setLogcode(form.getLogcode());
		mi009.setLogtext(form.getLogtext());
		mi009.setModitime(Datelet.getCurrentDateTime());

		mi009Dao.insert(mi009);
	}
	
	/**
	 * 日志代码表删除记录
	 */
	public int delLog(Mi009 form)throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getLogcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("日志代码："+form.getLogcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"日志代码");
		}
		
		String[] logCodes = form.getLogcode().split(",");
		List<String> logCodeList = new ArrayList<String>();
		for (int i = 0; i < logCodes.length; i++) {
			logCodeList.add(logCodes[i]);
		}
		
		int result = 0;
		Mi009Example example = new Mi009Example();
		example.createCriteria().andLogcodeIn(logCodeList);
		result = mi009Dao.deleteByExample(example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("日志代码："+form.getLogcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"日志代码MI009");
		}
		return result;
	}

	/**
	 * 日志代码表修改记录
	 */
	public int updLog(Mi009 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getLogcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("日志代码："+form.getLogcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"日志代码");
		}
		
		// 检索待更新数据是否存在
		int count = getLogCounts(form);
		if (0 == count) {
			log.error(ERROR.NO_DATA.getLogText("日志代码MI009","日志代码："+form.getLogcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"日志代码MI009");
		}
		
		int result = 0;
		Mi009Example example = new Mi009Example();
		example.createCriteria().andLogcodeEqualTo(form.getLogcode());
		Mi009 mi009 = new Mi009();
		mi009.setLogcode(form.getLogcode());
		mi009.setLogtext(form.getLogtext());
		mi009.setModitime(Datelet.getCurrentDateTime());
		result = mi009Dao.updateByExample(mi009, example);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("日志代码MI009","日志代码："+form.getLogcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "日志代码："+form.getLogcode());
		}
		return result;
	}

	/**
	 * 日志代码表查询记录（分页）
	 * @throws Exception 
	 */
	public JSONObject queryLog(Mi009 form,Integer page,Integer rows) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]ptlApi002 queryLog page="+page+",rows="+rows));

		String sql="select * from mi009 ";		
		if(form.getLogcode()!=null)
			sql+="where logcode like '"+form.getLogcode()+"%'";
		sql += " order by LOGCODE ASC";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		
		log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
		log.info(LOG.SELF_LOG.getLogText("[+]ptlApi002 queryLog page="+page+",rows="+rows));
		
		return obj;
	}
	
	/**
	 * 日志代码表查询记录数
	 */
	private int getLogCounts(Mi009 form){
		int counts = 0;
		
		Mi009Example example = new Mi009Example();
		example.createCriteria().andLogcodeEqualTo(form.getLogcode());
		
		counts = mi009Dao.countByExample(example);

		return counts;
	}
	
	/**
	 * 日志代码表查询
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi009> queryLogCodeAll() throws Exception{
		List<Mi009> list = new ArrayList<Mi009>();
		Mi009Example example = new Mi009Example();

		example.createCriteria();
		
		list = mi009Dao.selectByExample(example);

		return list; 
	}
	/**      日志代码表管理 end     **/
	
	/**      错误代码表管理start     **/
	/**
	 * 错误代码表新增记录
	 */
	public void addErrCode(Mi010 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getErrcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("错误代码："+form.getErrcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"错误代码");
		}
		if (CommonUtil.isEmpty(form.getErrtext())) {
			log.error(ERROR.PARAMS_NULL.getLogText("错误内容："+form.getErrtext()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"错误内容");
		}
		
		// 校验新增信息是否已经存在
		int count = getErrCounts(form);
		if(0 != count){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}
		
		Mi010 mi010= new Mi010();
		mi010.setErrcode(form.getErrcode());
		mi010.setErrtext(form.getErrtext());
		mi010.setModitime(Datelet.getCurrentDateTime());

		mi010Dao.insert(mi010);
	}
	
	/**
	 * 错误代码表删除记录
	 */
	public int delErrCode(Mi010 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getErrcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("错误代码："+form.getErrcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"错误代码");
		}
		
		String[] errCodes = form.getErrcode().split(",");
		List<String> errCodeList = new ArrayList<String>();
		for (int i = 0; i < errCodes.length; i++) {
			errCodeList.add(errCodes[i]);
		}
		
		int result = 0;
		Mi010Example example = new Mi010Example();
		example.createCriteria().andErrcodeIn(errCodeList);
		result = mi010Dao.deleteByExample(example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("错误代码："+form.getErrcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"错误代码MI010");
		}
		return result;
	}

	/**
	 * 错误代码表修改记录
	 */
	public int updErrCode(Mi010 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getErrcode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("错误代码："+form.getErrcode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"错误代码");
		}
		
		// 检索待更新数据是否存在
		int count = getErrCounts(form);
		if (0 == count) {
			log.error(ERROR.NO_DATA.getLogText("错误代码MI010","错误代码："+form.getErrcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"错误代码MI010");
		}
		
		int result = 0;
		Mi010Example example = new Mi010Example();
		example.createCriteria().andErrcodeEqualTo(form.getErrcode());
		Mi010 mi010 = new Mi010();
		mi010.setErrcode(form.getErrcode());
		mi010.setErrtext(form.getErrtext());
		mi010.setModitime(Datelet.getCurrentDateTime());
		result = mi010Dao.updateByExample(mi010, example);
		
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("错误代码MI010","错误代码："+form.getErrcode()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "错误代码："+form.getErrcode());
		}
		
		return result;
	}

	/**
	 * 错误代码表查询(条件)
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi010> queryErrCode(Mi010 form){
		
		List<Mi010> list = new ArrayList<Mi010>();
		Mi010Example example = new Mi010Example();
		if (CommonUtil.isEmpty(form.getErrcode())) {
			example.createCriteria();
		}else {
			example.createCriteria().andErrcodeLike(form.getErrcode()+'%');
		}
		list = mi010Dao.selectByExample(example);

		return list;
	}
	
	/**
	 * 错误代码表查询记录（分页）
	 * @throws Exception 
	 */
	public JSONObject pageQueryErrCode(Mi010 form,Integer page,Integer rows) throws Exception{
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]ptlApi002Service pageQueryErrCode page="+page+",rows="+rows));
		
		String sql="select * from mi010 ";
		if(form.getErrcode()!=null)
			sql+=" where  errcode like '"+form.getErrcode()+"%'";
		sql+=" ORDER BY ERRCODE ASC";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
		log.info(LOG.SELF_LOG.getLogText("[+]ptlApi002Service pageQueryErrCode page="+page+",rows="+rows));
		return obj;
	}
	
	/**
	 * 错误代码表查询记录数
	 */
	private int getErrCounts(Mi010 form){
		int counts = 0;
		
		Mi010Example example = new Mi010Example();
		example.createCriteria().andErrcodeEqualTo(form.getErrcode());
		
		counts = mi010Dao.countByExample(example);

		return counts;
	}
	
	/**
	 * 错误代码表查询
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi010> queryErrCodeAll() throws Exception{
		List<Mi010> list = new ArrayList<Mi010>();
		Mi010Example example = new Mi010Example();

		example.createCriteria();
		
		list = mi010Dao.selectByExample(example);

		return list;
	}
	/**      错误代码表管理 end     **/
	
	/**      推送消息通讯参数配置表管理 start     **/
	/**
	 * 推送消息通讯参数配置表查询记录数
	 */
	private int getSendMessageParamCounts(CMi012 form) throws Exception{
		
		int counts = 0;
		
		Mi012Example example = new Mi012Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		
		counts = mi012Dao.countByExample(example);

		return counts;
	}
	
	/**
	 * 推送消息通讯参数配置表新增记录
	 */
	public void addSendMessageParam(CMi012 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		// 校验添加中心是否存在
		boolean checkFlg = checkCenterInfo(form.getCenterid());
		if (checkFlg) {
			// 获取当前是否有相同记录
			int count = getSendMessageParamCounts(form);
			if (0 == count) {
				Mi012 mi012 = new Mi012();
				mi012.setCenterid(form.getCenterid());
				mi012.setCertificatename(form.getCertificatename());
				mi012.setCertificatepassword(form.getCertificatepassword());
				mi012.setApikey(form.getApikey());
				mi012.setSecritkey(form.getSecritkey());
				mi012Dao.insert(mi012);
				
			}else{
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
				throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
			}
		}else{
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("中心城市编码:"+form.getCenterid()+"的中心信息"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前添加中心不存在，请先添加中心信息后进行此操作!");
		}
	}
	
	/**
	 * 推送消息通讯参数配置表删除记录
	 */
	public int delSendMessageParam(CMi012 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		int result = 0;
		Mi012Example example = new Mi012Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		result = mi012Dao.deleteByExample(example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("中心城市编码:"+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"推送消息通讯参数配置表MI012");
		}
		
		return result;
	}

	/**
	 * 推送消息通讯参数配置表修改记录
	 */
	public int updSendMessageParam(CMi012 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		// 获取当前记录是否存在
		int count = getSendMessageParamCounts(form);
		if (0 == count) {
			log.error(ERROR.NO_DATA.getLogText("推送消息通讯参数配置表MI012","中心城市编码："+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"推送消息通讯参数配置表MI012");
		}
		
		int result = 0;
		Mi012Example example = new Mi012Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid());
		Mi012 mi012 = new Mi012();
		mi012.setApikey(form.getApikey());
		mi012.setCertificatename(form.getCertificatename());
		mi012.setCertificatepassword(form.getCertificatepassword());
		mi012.setSecritkey(form.getSecritkey());

		result = mi012Dao.updateByExampleSelective(mi012, example);
		
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("推送消息通讯参数配置表MI012","中心城市编码："+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "中心城市编码："+form.getCenterid());
		}
		return result;
	}

	/**
	 * 推送消息通讯参数配置表查询记录
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<CMi012> querySendMessageParam(CMi012 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心城市编码");
		}
		
		List<CMi012> list = new ArrayList<CMi012>();
		// Mi001获取中心名称
		Mi001Example mi001example = new Mi001Example();
		mi001example.createCriteria().andCenteridEqualTo(form.getCenterid());
		List<Mi001> mi001ResultList = mi001Dao.selectByExample(mi001example);
		if (!CommonUtil.isEmpty(mi001ResultList)) {
			Mi001 mi001 = mi001ResultList.get(0);
			
			Mi012Example mi012example = new Mi012Example();
			mi012example.createCriteria().andCenteridEqualTo(form.getCenterid());
			List<Mi012> listTmp = new ArrayList<Mi012>();
			listTmp = mi012Dao.selectByExample(mi012example);
			
			if (!CommonUtil.isEmpty(listTmp)) {
				CMi012 cmi012 = new CMi012();
				cmi012.setCenterid(listTmp.get(0).getCenterid());
				cmi012.setCentername(mi001.getCentername());
				cmi012.setCertificatename(listTmp.get(0).getCertificatename());
				cmi012.setCertificatepassword(listTmp.get(0).getCertificatepassword());
				cmi012.setApikey(listTmp.get(0).getApikey());
				cmi012.setSecritkey(listTmp.get(0).getSecritkey());

				list.add(cmi012);
			}
		}

		return list;
	}
	/**      推送消息通讯参数配置表管理 end   **/
	
	/**      业务日志表管理 start   **/
	/**
	 * APP业务日志表按条件分页查询
	 * @param form
	 * @return
	 */
	public PtlApi002PageQueryResult pageQueryAppBusiLog(CMi107 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		// 中心ID
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心名称"+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心名称");
		}
		// 业务类型为空或者是请选择时
		if (CommonUtil.isEmpty(form.getTranstype()) || "0".equals(form.getTranstype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("业务类型"+form.getTranstype()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务类型");
		}
		// 版本号
		if (CommonUtil.isEmpty(form.getVersionno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版本号"+form.getVersionno()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"版本号");
		}
		// 设备区分
		if (CommonUtil.isEmpty(form.getDevtype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("设备区分"+form.getDevtype()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"设备区分");
		}
		
		PtlApi002PageQueryResult result = new PtlApi002PageQueryResult();
		Mi107Example example = new Mi107Example();
		
		Mi107Example.Criteria mCriteria = example.createCriteria()
		.andCenteridEqualTo(form.getCenterid());
		mCriteria.andTranstypeEqualTo(form.getTranstype());
		mCriteria.andVersionnoEqualTo(form.getVersionno());
		mCriteria.andDevtypeEqualTo(form.getDevtype());
//		mCriteria.andChanneltypeEqualTo(Constants.CHANNELTYPE_APP);//业务渠道来源"01":app
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		if (!CommonUtil.isEmpty(form.getChanneluser())) {
			mCriteria.andUseridEqualTo(form.getChanneluser());
		}
		if(!CommonUtil.isEmpty(form.getFreeuse1())) {
			mCriteria.andFreeuse1EqualTo(form.getFreeuse1());
//			// 处理状态：无应答 ，0、1以外，为空
//			if ("2".equals(form.getFreeuse1())){
//				mCriteria.andFreeuse1IsNull();
//			}else{
//				mCriteria.andFreeuse1EqualTo(form.getFreeuse1());
//			}
		}
		if(!CommonUtil.isEmpty(form.getTransdate())){
			mCriteria.andTransdateEqualTo(form.getTransdate());
		}
		if(!CommonUtil.isEmpty(form.getDevid())){
			mCriteria.andDevidEqualTo(form.getDevid());
		}

		example.setOrderByClause("seqno asc");
		
		result = cmi107Dao.selectByExamplePagination(example, form.getPage(), form.getRows());
		
		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result.getMi107Rows())) {
			log.error(ERROR.NO_DATA.getLogText("MI107", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"业务日志信息");
		}
		
		return result;
	}
	
	/**
	 * 业务日志表条件查询
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Mi107 queryBusiLog(String seqno)throws Exception {
		Mi107 mi107 = new Mi107();
		Mi107Example example = new Mi107Example();
		example.createCriteria().andSeqnoEqualTo(new Integer(seqno))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi107> qryList = cmi107Dao.selectByExample(example);
		mi107 = qryList.get(0);
		return mi107;
	}
	
	/**
	 * APP业务日志表查询
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi107> queryAppBusiLogAll() throws Exception{
		List<Mi107> list = new ArrayList<Mi107>();
		Mi107Example example = new Mi107Example();

		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		list = cmi107Dao.selectByExample(example);

		return list; 
	}
	
	/**
	 * 业务日志表删除
	 * @param form
	 * @return
	 */
	public int delBusiLog(String seqno) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.PARAMS_NULL.getLogText("日志序号："+seqno));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"日志序号");
		}
		
		String[] seqnos = seqno.split(",");
		List<String> seqNoList = new ArrayList<String>();
		for (int i = 0; i < seqnos.length; i++) {
			seqNoList.add(seqnos[i]);
		}
		
		Mi107 mi107 = new Mi107();
		// 删除标记
		mi107.setValidflag(Constants.IS_NOT_VALIDFLAG);
		
		int result = 0;
		Mi107Example example = new Mi107Example();
		example.createCriteria().andSeqnoIn(seqNoList);
		result = cmi107Dao.updateByExampleSelective(mi107, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("日志序号"+seqno));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"业务日志MI107");
		}
		return result;
	}
	
	/**
	 * MI中心前置业务日志表按条件分页查询
	 * @param form
	 * @return
	 */
	public PtlApi002PageQueryResult pageQueryMiBusiLog(CMi107 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		// 中心ID
		if (CommonUtil.isEmpty(form.getMicenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心名称"+form.getMicenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"中心名称");
		}
		// 业务类型为空或者是请选择时
		if (CommonUtil.isEmpty(form.getMitranstype()) || "0".equals(form.getMitranstype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("业务类型"+form.getMitranstype()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务类型");
		}
		
		PtlApi002PageQueryResult result = new PtlApi002PageQueryResult();
		Mi107Example example = new Mi107Example();
		
		Mi107Example.Criteria mCriteria = example.createCriteria()
		.andCenteridEqualTo(form.getMicenterid());
		mCriteria.andTranstypeEqualTo(form.getMitranstype());
		mCriteria.andVersionnoEqualTo(PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "version_no"));
		mCriteria.andDevtypeEqualTo(Constants.MI_CENTER_FRONT);
		mCriteria.andChanneltypeEqualTo(Constants.CHANNELTYPE_CENTER_FRONT);
		mCriteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		if (!CommonUtil.isEmpty(form.getMiuserid())) {
			mCriteria.andUseridEqualTo(form.getMiuserid());
		}
		if(!CommonUtil.isEmpty(form.getMifreeuse1())) {
			// 处理状态：无应答、0、1以外 ，为空
			if ("2".equals(form.getMifreeuse1())){
				mCriteria.andFreeuse1IsNull();
			}else{
				mCriteria.andFreeuse1EqualTo(form.getMifreeuse1());
			}
		}
		if(!CommonUtil.isEmpty(form.getMitransdate())){
			mCriteria.andTransdateEqualTo(form.getMitransdate());
		}
		if(!CommonUtil.isEmpty(form.getMidevid())){
			mCriteria.andDevidEqualTo(form.getMidevid());
		}

		example.setOrderByClause("seqno asc");
		
		result = cmi107Dao.selectByExamplePagination(example, form.getPage(), form.getRows());
		
		/*
		 * 返回结果验证
		 */
		if (CommonUtil.isEmpty(result.getMi107Rows())) {
			log.error(ERROR.NO_DATA.getLogText("MI107", mCriteria
					.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"MI中心前置业务日志信息");
		}
		
		// result.getMi107Rows() 向result.getCmi107Rows() 转换
		List<CMi107> list = new ArrayList<CMi107>();
		for(int i = 0; i < result.getMi107Rows().size(); i++){
			Mi107 mi107 = result.getMi107Rows().get(i);
			CMi107 cmi107 = new CMi107();
			cmi107.setMiseqno(mi107.getSeqno().toString());
			cmi107.setMicenterid(mi107.getCenterid());
			cmi107.setMiuserid(mi107.getUserid());
			cmi107.setMichanneltype(mi107.getChanneltype());
			cmi107.setMitransdate(mi107.getTransdate());
			cmi107.setMitranstype(mi107.getTranstype());
			cmi107.setMitransname(mi107.getTransname());
			cmi107.setMiversionno(mi107.getVersionno());
			cmi107.setMidevtype(mi107.getDevtype());
			cmi107.setMidevid(mi107.getDevid());
			cmi107.setMirequesttime(mi107.getRequesttime());
			cmi107.setMiresponsetime(mi107.getResponsetime());
			if(!CommonUtil.isEmpty(mi107.getSecondsafter())){
				cmi107.setMisecondsafter(mi107.getSecondsafter().toString());
			}
			cmi107.setMivalidflag(mi107.getValidflag());
			cmi107.setMifreeuse1(mi107.getFreeuse1());
			cmi107.setMifreeuse2(mi107.getFreeuse2());
			cmi107.setMifreeuse3(mi107.getFreeuse3());
			if(!CommonUtil.isEmpty(mi107.getFreeuse4())) {
				cmi107.setMifreeuse4(mi107.getFreeuse4().toString());
			}
			
			list.add(cmi107);
		}
		
		result.setCmi107Rows(list);
		
		return result;
	}
	/**      业务日志表管理 end   **/
}
