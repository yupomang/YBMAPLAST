package com.yondervision.mi.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.CMi707;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.form.WebApi70701Form;
import com.yondervision.mi.form.WebApi70702Form;
import com.yondervision.mi.form.WebApi70704Form;
import com.yondervision.mi.result.Page70701QryResultAttr;
import com.yondervision.mi.result.Page70701TreeResult;
import com.yondervision.mi.result.WebApi70704_queryResult;
import com.yondervision.mi.service.WebApi707Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi707ServiceImpl 
* @Description: 栏目配置维护
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi707ServiceImpl implements WebApi707Service {
	
	public Mi001DAO mi001Dao = null;
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	
	public Mi707DAO mi707Dao = null;
	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public CMi704DAO cmi704Dao = null;
	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}
	
	/**
	 * 检索栏目管理表中相应条件的子级的记录
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
		Mi707Example example = new Mi707Example();
		example.createCriteria().andCenteridEqualTo(centerid).andUpdicidEqualTo(dicid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return mi707Dao.countByExample(example);
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
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return mi001Dao.selectByExample(example);
	}
	
	/**
	 * 对应城市中心ID信息查询
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi001> getCenterIdRecord(String centerid) throws Exception{
		Mi001Example example = new Mi001Example();
		example.createCriteria().andCenteridEqualTo(centerid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return mi001Dao.selectByExample(example);
	}
	
//	/**
//	 * 城市中心列表查询Json
//	 * @param pid
//	 * @return JSONArray
//	 */
//	public JSONArray getCenterIdListJson() throws Exception{
//		 ObjectMapper mapper = new  ObjectMapper();
//		 List<Mi001> list = getCenterIdList();
//		 return mapper.convertValue(list, JSONArray.class); 
//	}
//	
//	/**
//	 * 父级编码列表查询Json
//	 * @param centerid
//	 * @return JSONArray
//	 */
//	@SuppressWarnings("unchecked")
//	public JSONArray getParentCodeListJson(String centerid) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//		// 上传参数空值校验
//		if (CommonUtil.isEmpty(centerid)) {
//			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
//		}
//		
//		ObjectMapper mapper = new  ObjectMapper();
//		Mi007Example example = new Mi007Example();
//		
//		example.createCriteria()
//		.andCenteridEqualTo(centerid)
//		.andUpdicidEqualTo(0);
//		List<Mi007> list = mi007Dao.selectByExample(example);
//		return mapper.convertValue(list, JSONArray.class);
//	}
	
	/**
	 * 栏目查询展示树
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi707> getCodeListByPid(String pid, String centerid) throws Exception{
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
			UserContext user = UserContext.getInstance();
			String centeridTmp = user.getCenterid();
			
			List<Mi001> centerList = new ArrayList<Mi001>();
			if ("00000000".equals(centeridTmp)){
				centerList = getCenterIdList();
			}else{
				centerList = getCenterIdRecord(centeridTmp);
			}
			
			List<Mi707> resultList = new ArrayList<Mi707>();
			for (int i = 0 ;i <centerList.size(); i++) {
				Mi707 mi707 = new Mi707();
				mi707.setCenterid(centerList.get(i).getCenterid());
				mi707.setDicid(0);
				mi707.setItemid(centerList.get(i).getCenterid());
				mi707.setItemval(centerList.get(i).getCentername());
				mi707.setUpdicid(0);
				resultList.add(mi707);
			}
			return resultList;
		}
		// 要打开节点为城市中心名称
		else if ("000000000".equals(pid)) {
			//查询符合当前中心id的所有上级编码为0的记录
			Mi707Example example = new Mi707Example();
			
			example.createCriteria()
			.andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(0).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			example.setOrderByClause("abs(ITEMID) ASC");
			return mi707Dao.selectByExample(example);
		}
		//查询符合当前中心id的所有上级编码为0的记录
		Mi707Example example = new Mi707Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(Integer.parseInt(pid))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("abs(ITEMID) ASC");
		
		return mi707Dao.selectByExample(example);
	}
	
	/**
	 * 校验数据能否入库的合法性(添加、修改)
	 * @param form
	 * @param method （add/modify）
	 * @return
	 */
	public boolean checkData(CMi707 form, String method) throws Exception{
		
		// 如果是修改操作，则先判断是否对编码进行修改
		if ("modify".equals(method)) {
			// 校验编码是否发生变化，编码发生变化，则进行查询操作，否则可直接入库
			if (form.getItemid().equals(form.getOlditemid())) {
				return true;
			}
		}

		//校验itemid的唯一性
		Mi707Example exampleTmp = new Mi707Example();
		exampleTmp.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andItemidEqualTo(form.getItemid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int resultTmp = mi707Dao.countByExample(exampleTmp);
		if (resultTmp != 0) {
			return false;
		}
		
		/*Mi707Example example = new Mi707Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andUpdicidEqualTo(form.getUpdicid())
		.andItemidEqualTo(form.getItemid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int result = mi707Dao.countByExample(example);
		if (result != 0) {
			return false;
		}*/
		
		return true;
	}

	/**
	 * 在结果集中放入节点相关业务信息
	 * @param mi707
	 * @return
	 * @throws Exception
	 */
	public Page70701QryResultAttr setNodeAttributes(Mi707 mi707) throws Exception{
		Page70701QryResultAttr attributes = new Page70701QryResultAttr();
		
		String centerid = mi707.getCenterid();
		int updicid = mi707.getUpdicid();
		String itemid = mi707.getItemid();
		String itemval = mi707.getItemval();
		
		attributes.setCenterid(centerid);
		attributes.setCentername(getCenterName(centerid));
		attributes.setDicid(mi707.getDicid());
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
			Mi707Example example = new Mi707Example();
			example.createCriteria().andCenteridEqualTo(centerid).andDicidEqualTo(updicid);
			List<Mi707> mi707ResultList = mi707Dao.selectByExample(example);
			
			if (CommonUtil.isEmpty(mi707ResultList)) {
				throw new Exception("获取上一级编码信息失败！");
			}
			
			updicname = ((Mi707)mi707ResultList.get(0)).getItemid()
						+ '-' + ((Mi707)mi707ResultList.get(0)).getItemval();
		}

		return updicname;
	}
	
	/**
	 * 码表新增
	 */
	public void addMulDic(CMi707 form) throws Exception{
		Mi707 mi707 = new Mi707();
		
		mi707.setCenterid(form.getCenterid());
		mi707.setDicid(Integer.valueOf(commonUtil.genKey("mi707").intValue()));
		mi707.setItemid(form.getItemid());
		mi707.setItemval(form.getItemval());
		mi707.setUpdicid(form.getUpdicid());
		mi707.setStatus(Constants.IS_OPEN);
		mi707.setValidflag(Constants.IS_VALIDFLAG);
		mi707.setDatecreated(CommonUtil.getSystemDate());
		
		mi707Dao.insert(mi707);
	}
	
	/**
	 * 栏目新增,并返回追加对象信息
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Page70701TreeResult addMulDicReturnObj(CMi707 form) throws Exception {
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
		if (CommonUtil.isEmpty(form.getStat())) {
			log.error(ERROR.PARAMS_NULL.getLogText("状态："+form.getStat()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"状态");
		}
		if (CommonUtil.isEmpty(form.getFreeuse3())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道："+form.getFreeuse3()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"渠道");
		}
		
		// 添加内容进行入库合法性检查
		boolean checkFlg = checkData(form, "add");
		if (!checkFlg) {
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前编码已存在，请确认后提交！");
		}
		
		String status = null;
		String validDate = null;
		if ("0".equals(form.getUpdicid().toString())){
			status = form.getStat();
			validDate = form.getFreeuse1();
		}else {
			// 子级状态为开的情况，如果父级为关，提示message
			if (Constants.IS_OPEN.equals(form.getStat())){
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				String upStatus = selectMi707.get(0).getStatus();
				if (Constants.IS_CLOSE.equals(upStatus)){
					throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"上一级的状态为“关闭”时，其子级的状态也必须为“关闭”!");
				}else{
					status = form.getStat();
				}
			}else {
				status = form.getStat();
			}
			
			// 有效日期为空，用父级有效日期作为日期入库
			if (CommonUtil.isEmpty(form.getFreeuse1())){
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				validDate = selectMi707.get(0).getFreeuse1();
			}else{
				// 有效日期不为空，与父级有效日期进行比较，大了提示message，小了入库
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				String upValidDate = selectMi707.get(0).getFreeuse1();

				if (!CommonUtil.isEmpty(upValidDate)){
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date childDate = df.parse(form.getFreeuse1());
					Date parentDate = df.parse(upValidDate);
					if (childDate.before(parentDate)){
						validDate = form.getFreeuse1();
					}else{
						throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"子级的有效日期不能大于上一级的有效日期：【"+upValidDate+"】");
					}
				}else{
					validDate = form.getFreeuse1();
				}
			}
		}
		
		Page70701TreeResult treeResult = new Page70701TreeResult();
		Mi707 mi707 = new Mi707();
		
		mi707.setCenterid(form.getCenterid());
		mi707.setDicid(Integer.valueOf(commonUtil.genKey("MI707").intValue()));
		mi707.setItemid(form.getItemid());
		mi707.setItemval(form.getItemval());
		mi707.setUpdicid(form.getUpdicid());
		mi707.setStatus(status);
		mi707.setValidflag(Constants.IS_VALIDFLAG);
		mi707.setDatecreated(CommonUtil.getSystemDate());
		mi707.setFreeuse1(validDate);
		mi707.setFreeuse3(form.getFreeuse3());
		mi707.setFreeuse4(form.getFreeuse4());
		
		mi707Dao.insert(mi707);
		
		//生成添加节点的信息，返回页面用于append
		treeResult.setId(mi707.getDicid().toString());
		treeResult.setText(mi707.getItemval());
		treeResult.setState("open");
		
		treeResult.setAttributes(setNodeAttributes(mi707));

		return treeResult;
	}

	/**
	 * 栏目删除
	 */
	@SuppressWarnings("unchecked")
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public int delMulDic(CMi707 form) throws Exception{
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
			log.info(LOG.SELF_LOG.getLogText("中心级显示菜单节点不能被删除！"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"不允许操作，请到中心管理菜单进行维护");
		}
		
		int result = 0;
		
		for(int i = 0; i < dicids.length; i++) {
			// 检索删除内容是否含有子项
			Mi707Example queryExample = new Mi707Example();
			if (CommonUtil.isEmpty(dicids[i])) {
				log.info(LOG.SELF_LOG.getLogText("没有在栏目信息显示区域选择记录进行删除维护！"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请到上级菜单进行操作");
			}
			queryExample.createCriteria().andCenteridEqualTo(form.getCenterid())
			.andUpdicidEqualTo(Integer.parseInt(dicids[i])).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi707> queryResultList = mi707Dao.selectByExample(queryExample);
			
			// 如果含有子项，则先将子项删除
			Mi707 mi707 = new Mi707();
			for (int j = 0; j < queryResultList.size(); j++) {
				mi707 = queryResultList.get(j);

				// 进行子项内容的删除
				int delChildresult = delMulDicDetail(mi707.getCenterid(), mi707.getDicid());
				
				// 删除子项失败，直接返回
				if(0 == delChildresult) {
					log.error(ERROR.ERRCODE_LOG_800004.getLogText("上级编码为当前编码:"+mi707.getDicid()));
					throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"栏目管理MI707");
				}
			}
			
			// 进行栏目内容的删除
			result = delMulDicDetail(form.getCenterid(), Integer.parseInt(dicids[i]));
			if (0 == result) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("当前编码:"+mi707.getDicid()));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"栏目管理MI707");
			}
		}
		return result;

	}
	
	private int delMulDicDetail(String centerid, int dicid) throws Exception{
		int result = 0;
		// 进行码表内容的删除
		Mi707Example example = new Mi707Example();
		
		example.createCriteria()
		.andCenteridEqualTo(centerid)
		.andDicidEqualTo(dicid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		result = mi707Dao.deleteByExample(example);
		
		/*索引会冲突
		Mi707 record = new Mi707();
		record.setValidflag(Constants.IS_NOT_VALIDFLAG);
		record.setDatemodified(CommonUtil.getSystemDate());
		result = mi707Dao.updateByExampleSelective(record, example);*/
		return result;
	}

	/**
	 * 栏目修改
	 */
	@SuppressWarnings("unchecked")
	public int updMulDic(CMi707 form)throws Exception {

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
		if (CommonUtil.isEmpty(form.getFreeuse3())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道："+form.getFreeuse3()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"渠道");
		}
		
		// 添加内容进行入库合法性检查
		boolean checkFlg = checkData(form, "modify");
		if (!checkFlg) {
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"当前编码已存在，请确认后提交！");
		}
		
		// 对状态和有效日期进行校验
		String status = null;
		String validDate = null;
		if ("0".equals(form.getUpdicid().toString())){
			status = form.getStat();
			validDate = form.getFreeuse1();
		}else{
			// 子级状态为开的情况，如果父级为关，提示message
			if (Constants.IS_OPEN.equals(form.getStat())){
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				String upStatus = selectMi707.get(0).getStatus();
				if (Constants.IS_CLOSE.equals(upStatus)){
					throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"上一级的状态为“关闭”时，其子级的状态也必须为“关闭”!");
				}else{
					status = form.getStat();
				}
			}else {
				status = form.getStat();
			}
			
			// 有效日期为空，用父级有效日期作为日期入库
			if (CommonUtil.isEmpty(form.getFreeuse1())){
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				validDate = selectMi707.get(0).getFreeuse1();
			}else{
				// 有效日期不为空，与父级有效日期进行比较，大了提示message，小了入库
				Mi707Example mi707Example = new Mi707Example();
				mi707Example.createCriteria()
				.andCenteridEqualTo(form.getCenterid())
				.andDicidEqualTo(form.getUpdicid())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				List<Mi707> selectMi707 = mi707Dao.selectByExample(mi707Example);
				String upValidDate = selectMi707.get(0).getFreeuse1();

				if (!CommonUtil.isEmpty(upValidDate)){
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date childDate = df.parse(form.getFreeuse1());
					Date parentDate = df.parse(upValidDate);
					if (childDate.before(parentDate)){
						validDate = form.getFreeuse1();
					}else{
						throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"子级的有效日期不能大于上一级的有效日期：【"+upValidDate+"】");
					}
				}else{
					validDate = form.getFreeuse1();
				}

			}
		}
		
		Mi707 mi707 = new Mi707();
		Mi707Example example = new Mi707Example();

		mi707.setItemid(form.getItemid());
		mi707.setItemval(form.getItemval());
		mi707.setStatus(status);
		mi707.setDatemodified(CommonUtil.getSystemDate());
		mi707.setFreeuse1(validDate);
		mi707.setFreeuse3(form.getFreeuse3());
		mi707.setFreeuse4(form.getFreeuse4());
		
		example.createCriteria()
		.andCenteridEqualTo(form.getCenterid())
		.andDicidEqualTo(form.getDicid());
		
		int result =  mi707Dao.updateByExampleSelective(mi707, example);
		if (0 == result) {
			log.error(ERROR.NO_DATA.getLogText("栏目管理MI707", CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"栏目管理MI707");
		}
		
		// 如果修改项为栏目状态，则考虑该栏目的子栏目的状态的同步
		if (!form.getOldstat().equals(form.getStat())
				&& !form.getOldfreeuse1().equals(form.getFreeuse1())){
			Mi707 mi707Child = new Mi707();
			Mi707Example exaChild = new Mi707Example();

			mi707Child.setStatus(form.getStat());
			mi707Child.setFreeuse1(form.getFreeuse1());
			mi707Child.setDatemodified(CommonUtil.getSystemDate());
			
			exaChild.createCriteria()
			.andCenteridEqualTo(form.getCenterid())
			.andUpdicidEqualTo(form.getDicid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			mi707Dao.updateByExampleSelective(mi707Child, exaChild);
		}else if (!form.getOldstat().equals(form.getStat())){
			Mi707 mi707Child = new Mi707();
			Mi707Example exaChild = new Mi707Example();

			mi707Child.setStatus(form.getStat());
			mi707Child.setDatemodified(CommonUtil.getSystemDate());
			
			exaChild.createCriteria()
			.andCenteridEqualTo(form.getCenterid())
			.andUpdicidEqualTo(form.getDicid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			mi707Dao.updateByExampleSelective(mi707Child, exaChild);
		}else if (!form.getOldfreeuse1().equals(form.getFreeuse1())){
			Mi707 mi707Child = new Mi707();
			Mi707Example exaChild = new Mi707Example();

			mi707Child.setFreeuse1(form.getFreeuse1());
			mi707Child.setDatemodified(CommonUtil.getSystemDate());
			
			exaChild.createCriteria()
			.andCenteridEqualTo(form.getCenterid())
			.andUpdicidEqualTo(form.getDicid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			mi707Dao.updateByExampleSelective(mi707Child, exaChild);
		}

		return result;
	}
	
	
//	/**
//	 * 查询对应 上级编码和城市中心代码的记录列表
//	 * @param pid
//	 * @param centerid
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	private List<Mi707> getClassificationData(String pid, String centerid) throws Exception{
//		Logger log = LoggerUtil.getLogger();
//		// 上传参数空值校验
//		if (CommonUtil.isEmpty(pid)) {
//			log.error(ERROR.PARAMS_NULL.getLogText("上级编码："+pid));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"上级编码获取失败");
//		}
//		if (CommonUtil.isEmpty(centerid)) {
//			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+centerid));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
//		}
//		
//		//查询符合当前中心id的所有上级编码为0的记录
//		Mi707Example example = new Mi707Example();
//		
//		example.createCriteria()
//		.andCenteridEqualTo(centerid)
//		.andUpdicidEqualTo(Integer.parseInt(pid)).andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		example.setOrderByClause("DICID ASC");
//		return mi707Dao.selectByExample(example);
//	}
	
//	/**
//	 * 查询对应 上级编码和城市中心代码的树
//	 * @param pid
//	 * @param centerid
//	 * @return
//	 * @throws Exception
//	 */
//	public JSONArray getClassificationTreeJsonArray(String pid, String centerid) throws Exception{
//		JSONArray ary = new JSONArray();
//		List<Mi707> list = getClassificationData(pid, centerid) ;
//		 Mi707 mi707 = new Mi707();
//		 for(int i = 0; i < list.size(); i++){
//			 mi707 = list.get(i);
//		     JSONObject obj=new JSONObject();
//		     obj.put("id", mi707.getDicid());
//		     obj.put("text", mi707.getItemval());
//		     JSONArray childAry = getClassificationTreeJsonArray(mi707.getDicid().toString(), centerid);
//		     if (childAry != null && childAry.size() != 0){
//		    	 obj.put("children", childAry);
//		     }
//		     ary.add(obj);
//		 }
//		return ary;
//	}
	
	/*
	 * 内容展现项配置增加
	 */
	@SuppressWarnings("unchecked")
	public void webapi70701(WebApi70701Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getNewsviewitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容展现项编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"内容展现项编号获取失败");
		}
		if (CommonUtil.isEmpty(form.getColumns())) {
			log.error(ERROR.PARAMS_NULL.getLogText("配置内容项"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择配置内容项");
		}
		
		// 2. 进行对应内容展现项编号的配置内容项的配置信息的入库操作
		// 先删除对应此对应内容展现项编号的所有配置信息，注意子项数据的删除
		delAllData(form.getCenterId(), "C"+form.getNewsviewitemid());
		
		// 3.获取每一项是否有子项，统计所有要入表数据项（包括子项）的长度，用于下面入表使用
		int countAll = 0;
		for(int i = 0; i < form.getColumns().length;i++){
			int count = qryAlllength(form.getCenterId(), "0", form.getColumns()[i]);
			countAll = countAll + count;
		}
		
		// 4. 进行对应内容展现项编号的配置内容项的配置信息的入库操作
		Integer minSeqno = commonUtil.genKeyAndIncrease("MI704", countAll).intValue();
		
		//Integer upDicid = minSeqno;
		Mi704 record = new Mi704();
		List<Mi707> mi707QryList = new ArrayList<Mi707>();
		for(int i = 0; i < form.getColumns().length;i++){
			record = new Mi704();
			record.setCenterid(form.getCenterId());
			record.setDicid(minSeqno.toString());
			record.setItemid(form.getColumns()[i]);
			record.setItemval(qryItemVal(form.getCenterId(), "0", form.getColumns()[i]));
			record.setUpdicid("C"+form.getNewsviewitemid());
			cmi704Dao.insert(record);
			// Mi707中查询对应记录的数据，获取进行子项查询的信息条件
			mi707QryList = new ArrayList<Mi707>();
			Mi707Example mi707QryExa = new Mi707Example();
			mi707QryExa.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andItemidEqualTo(form.getColumns()[i])
			.andUpdicidEqualTo(0)
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
			mi707QryList = mi707Dao.selectByExample(mi707QryExa);
			minSeqno = minSeqno + 1;
			//进行子项数据的添加
			minSeqno = insertChilditem(minSeqno, form.getCenterId(), mi707QryList.get(0).getDicid().toString(), String.valueOf(minSeqno.intValue()-1));
			

		}
	}

	/*
	 * 内容展现项配置删除
	 */
	public void webapi70702(WebApi70702Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getNewsviewitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容展现项编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"内容展现项编号获取失败");
		}
		
		delAllData(form.getCenterId(), "C"+form.getNewsviewitemid());
	}
	
	/**
	 * 内容展现项配置查询-分页
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi70704_queryResult> webapi70704(WebApi70704Form form) throws Exception{
		
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getQry_newsviewitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容展现项编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"内容展现项编号获取失败");
		}

		List<Mi704> mi704ColumnsList = new ArrayList<Mi704>();
		Mi704Example example = new Mi704Example();
		example.setOrderByClause("abs(dicid) asc");
		example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo("C"+form.getQry_newsviewitemid());
		mi704ColumnsList = cmi704Dao.selectByExample(example);
		
		List<WebApi70704_queryResult> resultList = new ArrayList<WebApi70704_queryResult>();
		if(!CommonUtil.isEmpty(mi704ColumnsList)){
			WebApi70704_queryResult queryResult = new WebApi70704_queryResult();
			
			queryResult.setNewsviewitemid(form.getQry_newsviewitemid());
			queryResult.setNewspapercolumnsList(mi704ColumnsList);
			resultList.add(queryResult);
		}
		
		return resultList;
	}
	
	/**
	 * 根据中心查询所有的栏目
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public List<Mi707> webapi70705(String centerid) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(centerid)){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心为空");
		}
		Mi707Example example = new Mi707Example();
		example.createCriteria().andCenteridEqualTo(centerid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("itemid");
		
		List<Mi707> select = mi707Dao.selectByExample(example);
		return select;
		
	}
	
	// 删除现有配置中所有相关的配置数据
	@SuppressWarnings("unchecked")
	private void delAllData(String centerid, String updicid){
		Mi704Example mi704Exa = new Mi704Example();
		mi704Exa.createCriteria().andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(updicid);
		List<Mi704> mi704List = new ArrayList<Mi704>();
		mi704List = cmi704Dao.selectByExample(mi704Exa);
		if (!CommonUtil.isEmpty(mi704List)){
			cmi704Dao.deleteByExample(mi704Exa);
			for(int i = 0; i < mi704List.size(); i++){
				delAllData(centerid, mi704List.get(i).getDicid());
			}
		}
	}
	
	// 获取对应此项配置的包含子项的所有的配置项的长度
	@SuppressWarnings("unchecked")
	private int qryAlllength(String centerid, String updicid, String itemid){
		int count = 0;
		Mi707Example mi707Exa = new Mi707Example();
		if(CommonUtil.isEmpty(itemid)){
			mi707Exa.createCriteria().andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(Integer.parseInt(updicid))
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
		}else{
			mi707Exa.createCriteria().andCenteridEqualTo(centerid)
			.andUpdicidEqualTo(Integer.parseInt(updicid))
			.andItemidEqualTo(itemid).andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andStatusEqualTo(Constants.IS_OPEN);
		}
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(mi707Exa);
		if(!CommonUtil.isEmpty(mi707List)){
			count = mi707List.size();
			for(int i = 0; i < mi707List.size(); i++){
				count = count + qryAlllength(centerid, mi707List.get(i).getDicid().toString(), "");
			}
		}
		return count;
	}
	
	// 处理对应所有子项的数据入库Mi704,返回minSeqno
	@SuppressWarnings("unchecked")
	private Integer insertChilditem(Integer minSeqno, String centerid, String qryupdicid, String insertupdicid){
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(Integer.parseInt(qryupdicid))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(mi707Exa);
		Mi704 record = new Mi704();
		for(int i = 0; i < mi707List.size(); i++){
			record = new Mi704();
			record.setCenterid(centerid);
			record.setDicid(minSeqno.toString());
			record.setItemid(mi707List.get(i).getItemid());
			record.setItemval(mi707List.get(i).getItemval());
			record.setUpdicid(insertupdicid);
			cmi704Dao.insert(record);
			minSeqno = minSeqno+1;
			minSeqno = insertChilditem(minSeqno, centerid, mi707List.get(i).getDicid().toString(), String.valueOf(minSeqno.intValue()-1));
			//minSeqno = minSeqno+1;
		}
		
		return minSeqno;
	}
	
	@SuppressWarnings("unchecked")
	private String qryItemVal(String centerid, String updicid, String itemid){
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(centerid)
		.andUpdicidEqualTo(Integer.parseInt(updicid))
		.andItemidEqualTo(itemid).andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(mi707Exa);
		if(!CommonUtil.isEmpty(mi707List)){
			return mi707List.get(0).getItemval();
		}else{
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public Mi707 get707(String centerid, String itemid){
		Mi707Example mi707Exa = new Mi707Example();
		mi707Exa.createCriteria().andCenteridEqualTo(centerid)
		.andItemidEqualTo(itemid).andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andStatusEqualTo(Constants.IS_OPEN);
		List<Mi707> mi707List = new ArrayList<Mi707>();
		mi707List = mi707Dao.selectByExample(mi707Exa);
		if(!CommonUtil.isEmpty(mi707List)){
			return mi707List.get(0);
		}else{
			return null;
		}
	}
}
