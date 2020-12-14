package com.yondervision.mi.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi050DAO;
import com.yondervision.mi.dao.CMi051DAO;
import com.yondervision.mi.dao.CMi052DAO;
import com.yondervision.mi.dao.CMi053DAO;
import com.yondervision.mi.dto.CMi007;
import com.yondervision.mi.dto.CMi050;
import com.yondervision.mi.dto.CMi051;
import com.yondervision.mi.dto.CMi052;
import com.yondervision.mi.dto.CMi053;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi050Example;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.dto.Mi052;
import com.yondervision.mi.dto.Mi052Example;
import com.yondervision.mi.dto.Mi053;
import com.yondervision.mi.dto.Mi053Example;
import com.yondervision.mi.result.WebApi05004_queryResult;
import com.yondervision.mi.result.WebApi05104_queryResult;
import com.yondervision.mi.result.WebApi05204_queryResult;
import com.yondervision.mi.result.WebApi05304_queryResult;
import com.yondervision.mi.service.WebApi050Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebApi050ServiceImpl implements WebApi050Service {
	
	@Autowired
	private CMi050DAO cmi050DAO;
	@Autowired
	private CMi051DAO cmi051DAO;
	@Autowired
	private CMi052DAO cmi052DAO;
	@Autowired
	private CMi053DAO cmi053DAO;
	@Autowired
	private CommonUtil commonUtil;
	
	public void webapi05001(CMi050 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getUrl())){
			log.error(ERROR.PARAMS_NULL.getLogText("url"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "url为空");
		}
		if(CommonUtil.isEmpty(form.getApiname())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "接口名称为空");
		}
		if(CommonUtil.isEmpty(form.getApimsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("请求描述"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "请求描述为空");
		}
		//检查url是否已经存在
		Mi050Example mi050Example = new Mi050Example();
		mi050Example.createCriteria().andUrlEqualTo(form.getUrl()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi050DAO.countByExample(mi050Example);
		if(0!=countByExample){
			log.error(ERROR.ADD_CHECK.getLogText("渠道接口配置Mi050："+form.getUrl()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "渠道接口配置Mi050："+form.getUrl());
		}
		
		//新增
		String id = commonUtil.genKey("MI050", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("渠道接口配置MI050"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("渠道接口配置MI050"));
		}
		form.setApiid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi050DAO.insert(form);		
	}
	@Transactional
	public int webapi05002(CMi050 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getApiid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));
		//接口服务中间表存在，不许删除
		for(String apiid:asList){
			Mi052Example mi052Example = new Mi052Example();
			mi052Example.createCriteria().andApiidEqualTo(apiid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			int count = cmi052DAO.countByExample(mi052Example);
			if(count!=0){
				log.error(ERROR.DEL_CHECK.getLogText("该接口已被配置不许删除："+apiid));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_CHECK.getValue(), "该接口已被配置不许删除："+apiid);
			}
		}
		
		//删除自己
		Mi050Example example = new Mi050Example();
		example.createCriteria().andApiidIn(asList);
		int result = cmi050DAO.deleteByExample(example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道接口配置MI050:" + form.getApiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "渠道接口配置MI050删除");
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int webapi05003(CMi050 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getApiid())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主键为空");
		}
		if(CommonUtil.isEmpty(form.getUrl())){
			log.error(ERROR.PARAMS_NULL.getLogText("url"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "url为空");
		}
		if(CommonUtil.isEmpty(form.getApiname())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "接口名称为空");
		}
		if(CommonUtil.isEmpty(form.getApimsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("请求描述"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "请求描述为空");
		}
		Mi050 mi050 = cmi050DAO.selectByPrimaryKey(form.getApiid());
		if(mi050==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		//检查url是否已经存在
		Mi050Example mi050Example = new Mi050Example();
		mi050Example.createCriteria().andUrlEqualTo(form.getUrl()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi050> select = cmi050DAO.selectByExample(mi050Example);
		if(select != null && !select.isEmpty()){
			Mi050 mi050Selelct = select.get(0);
			if(!mi050Selelct.getApiid().equals(form.getApiid())){
				log.error(ERROR.ADD_CHECK.getLogText("渠道接口配置Mi050："+form.getUrl()));
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "渠道接口配置Mi050："+form.getUrl());
			}
		}
		//修改
		mi050.setDatemodified(CommonUtil.getSystemDate());
		mi050.setApiname(form.getApiname());
		mi050.setApimsg(form.getApimsg());
		mi050.setApitype(form.getApitype());
		mi050.setUrl(form.getUrl());
		mi050.setIslogin(form.getIslogin());
		mi050.setReqtype(form.getReqtype());
		mi050.setReqparam(form.getReqparam());
		mi050.setRepparam(form.getRepparam());
		mi050.setRemarks(form.getRemarks());
		int updateResult = cmi050DAO.updateByPrimaryKeySelective(mi050);
		return updateResult;
		
	}
	
	public WebApi05004_queryResult webapi05004(CMi050 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		WebApi05004_queryResult select050Page = cmi050DAO.select050Page(form);
		return select050Page;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi05104_queryResult webapi05005(CMi050 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		if(CommonUtil.isEmpty(form.getApiid())){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050主键为空", "apiid：" + form.getApiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050主键为空");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道接口配置表MI050", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道接口配置表MI050");
		}
		Mi052Example mi052Example = new Mi052Example();
		mi052Example.createCriteria().andApiidEqualTo(form.getApiid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi052> mi052List = cmi052DAO.selectByExample(mi052Example);
		if(null!=mi052List&&!mi052List.isEmpty()){
			StringBuilder serviceids= new StringBuilder();
			for(int i=0;i<mi052List.size();i++){
				if(i==mi052List.size()-1){
					serviceids.append(mi052List.get(i).getServiceid());
				}else{
					serviceids.append(mi052List.get(i).getServiceid()).append(",");
				}
			}
			CMi051 cmi051 = new CMi051();
			cmi051.setServiceid(serviceids.toString());
			cmi051.setPage(form.getPage());
			cmi051.setRows(form.getRows());
			log.info("我要去051表查数据了："+cmi051.getServiceid());
			WebApi05104_queryResult select051Page = cmi051DAO.select051Page(cmi051);
			log.info("我回来了，看看是啥："+select051Page);
			return select051Page;
		}else{
			log.error(ERROR.NO_DATA.getLogText("该接口下尚未配置服务："+form.getApiid() ));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "该接口下尚未配置服务："+form.getApiid());
		}
	}	
	@SuppressWarnings("unchecked")
	public List<Mi050> webapi05006() throws Exception {
			Mi050Example mi050Example = new Mi050Example();
			mi050Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi050> mi050List = cmi050DAO.selectByExample(mi050Example);
			return mi050List;
		
	}
	public List<Mi050> queryMi050ForExcel() throws Exception{
		Mi050Example mi050Example = new Mi050Example();
		Mi050Example.Criteria ca = mi050Example.createCriteria();
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi050> list = cmi050DAO.selectByExample(mi050Example);
		return list;
	}
	
	//渠道服务配置
	public void webapi05101(CMi051 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getServicename())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务名称为空");
		}
		if(CommonUtil.isEmpty(form.getServicemsg())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务描述"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务描述为空");
		}
		if(CommonUtil.isEmpty(form.getBuztype())){
			log.error(ERROR.PARAMS_NULL.getLogText("业务类型码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "业务类型码为空");
		}
		if(CommonUtil.isEmpty(form.getServicetype())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务类型"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务类型为空");
		}
		if(CommonUtil.isEmpty(form.getUselevel())){
			log.error(ERROR.PARAMS_NULL.getLogText("客户级别"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "客户级别为空");
		}
		if(CommonUtil.isEmpty(form.getStatus())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务状态"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务状态为空");
		}
		if("1".equals(form.getOpencouch())){//取缓存
			if(CommonUtil.isEmpty(form.getCouchtime())||0==form.getCouchtime()){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("开启缓存后，缓存时间不可以为空或者0"));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "开启缓存后，缓存时间不可以为空或者0");
			}
			form.setCouchtime(form.getCouchtime());
		}else{
			form.setCouchtime(0);
		}
		
		
		//检查buztype是否已存在
		Mi051Example mi051Example = new Mi051Example();
		mi051Example.createCriteria().andBuztypeEqualTo(form.getBuztype())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi051DAO.countByExample(mi051Example);
		if(0!=countByExample){
			log.error(ERROR.ADD_CHECK.getLogText("业务类型码:"+form.getBuztype()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "业务类型码:"+form.getBuztype());
		}
		//新增
		String id = commonUtil.genKey("MI051").toString();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("渠道服务配置MI051"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("渠道服务配置MI051"));
		}
		form.setServiceid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi051DAO.insert(form);		
	}
	public int webapi05102(CMi051 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getServiceid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("MI051主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "MI051主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));
		//接口服务中间表存在，不许删除
		for(String serviceid:asList){
			Mi052Example mi052Example = new Mi052Example();
			mi052Example.createCriteria().andServiceidEqualTo(serviceid).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			int count = cmi052DAO.countByExample(mi052Example);
			if(count!=0){
				log.error(ERROR.DEL_CHECK.getLogText("该服务已被配置不许删除："+serviceid));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_CHECK.getValue(), "该服务已被配置不许删除："+serviceid);
			}
		}
		
		Mi051Example example = new Mi051Example();
		example.createCriteria().andServiceidIn(asList);
		int result = cmi051DAO.deleteByExample(example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道服务配置MI051:" + form.getServiceid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "渠道服务配置MI051删除");
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int webapi05103(CMi051 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi051 mi051 = cmi051DAO.selectByPrimaryKey(form.getServiceid());
		if(mi051==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		//检查buztype是否已存在
		Mi051Example mi051Example = new Mi051Example();
		mi051Example.createCriteria().andBuztypeEqualTo(form.getBuztype())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi051> select = cmi051DAO.selectByExample(mi051Example);
		if(select !=null && !select.isEmpty()){
			Mi051 mi051Select = select.get(0);
			if(!form.getServiceid().equals(mi051Select.getServiceid())){
				log.error(ERROR.ADD_CHECK.getLogText("业务类型码:"+form.getBuztype()));
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "业务类型码:"+form.getBuztype());
			}
		}
		
		//修改
		mi051.setDatemodified(CommonUtil.getSystemDate());
		mi051.setServicemsg(form.getServicemsg());
		mi051.setServicetype(form.getServicetype());
		mi051.setServicename(form.getServicename());
		mi051.setStatus(form.getStatus());
		mi051.setBuztype(form.getBuztype());
		mi051.setOpencouch(form.getOpencouch());
		if("1".equals(form.getOpencouch())){//取缓存
			if(CommonUtil.isEmpty(form.getCouchtime())||0==form.getCouchtime()){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("开启缓存后，缓存时间不可以为空或者0"));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "开启缓存后，缓存时间不可以为空或者0");
			}
			mi051.setCouchtime(form.getCouchtime());
		}else{
			mi051.setCouchtime(0);
		}
		mi051.setMoneytype(form.getMoneytype());
		mi051.setUselevel(form.getUselevel());
		mi051.setFreeuse1(form.getFreeuse1());
		int updateResult = cmi051DAO.updateByPrimaryKeySelective(mi051);
		return updateResult;
		
	}
	
	public WebApi05104_queryResult webapi05104(CMi051 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		WebApi05104_queryResult select051Page = cmi051DAO.select051Page(form);
		return select051Page;
	}
	
	public JSONObject webapi05104_01(CMi051 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("服务配置表MI051", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务配置表MI051");
		}
		return cmi051DAO.mixSelect051Page(form);
	}
	/**
	 * 根据服务获取接口列表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi050> webapi05105(CMi051 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务Mi051主键为空："+form.getServiceid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务Mi051主键为空："+form.getServiceid());
		}
		Mi052Example mi052Example = new Mi052Example();
		mi052Example.createCriteria().andServiceidEqualTo(form.getServiceid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi052Example.setOrderByClause("orderid");
		List<Mi052> select = cmi052DAO.selectByExample(mi052Example);
		List<Mi050> list = new ArrayList<Mi050>();
		if(select!=null && !select.isEmpty()){
			for(Mi052 mi052:select){
				Mi050 mi050 = cmi050DAO.selectByPrimaryKey(mi052.getApiid());
				if(mi050!=null){
					if(CommonUtil.isEmpty(mi052.getFreeuse1())){
						mi050.setFreeuse1("0");
					}else{
						mi050.setFreeuse1(mi052.getFreeuse1());
					}
					list.add(mi050);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取所有服务列表
	 * @return
	 * @throws Exception
	 */
	public List<Mi051> webapi05106() throws Exception{
		Mi051Example example = new Mi051Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.setOrderByClause("datecreated");
		
		List<Mi051> list = cmi051DAO.selectByExample(example);
		
		return list;
	}
	
	/**
	 * 保存统计标识
	 * @return
	 * @throws Exception
	 */
	public void webapi05107(CMi052 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务主键"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务主键为空");
		}
		if(CommonUtil.isEmpty(form.getApiid())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口主键"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "接口主键为空");
		}
		if(CommonUtil.isEmpty(form.getFreeuse1())){
			log.error(ERROR.PARAMS_NULL.getLogText("统计标识"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "统计标识为空");
		}
		
		String[] apiids = form.getApiid().split(",");
		String[] freeuse1s = form.getFreeuse1().split(",");
		for(int i=0;i<apiids.length;i++){
			Mi052Example mi052Example = new Mi052Example();
			mi052Example.createCriteria().andApiidEqualTo(apiids[i]).andServiceidEqualTo(form.getServiceid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi052> selectByExample = cmi052DAO.selectByExample(mi052Example);
			if(!CommonUtil.isEmpty(selectByExample)){
				Mi052 mi052 = selectByExample.get(0);
				mi052.setFreeuse1(freeuse1s[i]);
				cmi052DAO.updateByPrimaryKeySelective(mi052);
			}
			
		}
		
	}
	
	
	/**
	 * 获取中心下所有已经使用服务列表
	 * @return
	 * @throws Exception
	 */
	public List<Mi051> webapi05108(CMi051 form) throws Exception{
				
		List<Mi051> list = cmi051DAO.selectByExampleForCenterid(form);
		
		return list;
	}
	
	//接口-服务配置
	@SuppressWarnings("unchecked")
	public void webapi05201(CMi052 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务名称为空");
		}
		if(CommonUtil.isEmpty(form.getApiid())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "接口名称为空");
		}
		if(CommonUtil.isEmpty(form.getOrderid())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口排序"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "接口排序为空");
		}
		//检查是否已存在
		Mi052Example mi052Example = new Mi052Example();
		mi052Example.createCriteria().andApiidEqualTo(form.getApiid()).andServiceidEqualTo(form.getServiceid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi052DAO.countByExample(mi052Example);
		if(0!=countByExample){
			log.error(ERROR.ADD_CHECK.getLogText("渠道接口-服务配置MI052接口名称已存在："));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), ERROR.NULL_KEY.getLogText("渠道接口-服务配置MI052"));
		}
		//检查排序是否已存在
		Mi052Example mi052Example2 = new Mi052Example();
		mi052Example2.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidEqualTo(form.getOrderid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = cmi052DAO.countByExample(mi052Example2);
		if(count!=0){
			//调整排序
			Mi052Example mi052Example3 = new Mi052Example();
			mi052Example3.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidGreaterThanOrEqualTo(form.getOrderid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi052> select1 = cmi052DAO.selectByExample(mi052Example3);
			if(select1!=null && !select1.isEmpty()){
				for(Mi052 mi052:select1){
					mi052.setOrderid(mi052.getOrderid()+1);
					cmi052DAO.updateByPrimaryKey(mi052);
				}
			}
			
			
			Mi052Example mi052Example4 = new Mi052Example();
			mi052Example4.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidLessThan(form.getOrderid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi052> select2 = cmi052DAO.selectByExample(mi052Example4);
			if(select2!=null && !select2.isEmpty()){
				for(Mi052 mi052:select2){
					mi052.setOrderid(mi052.getOrderid()-1);
					cmi052DAO.updateByPrimaryKey(mi052);
				}
			}
			
		}
		
		//新增
		String id = commonUtil.genKey("MI052").toString();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("渠道接口-服务配置MI052"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("渠道接口-服务配置MI052"));
		}
		form.setId(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi052DAO.insert(form);		
	}
	public int webapi05202(CMi052 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getId();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("MI052主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "MI052主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));

		Mi052Example example = new Mi052Example();
		example.createCriteria().andIdIn(asList);
		int result = cmi052DAO.deleteByExample(example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道接口-服务配置MI052:" + form.getId()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "渠道接口-服务配置MI052删除");
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int webapi05203(CMi052 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi052 mi052 = cmi052DAO.selectByPrimaryKey(form.getId());
		if(mi052==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		
		//检查排序是否已存在
		Mi052Example mi052Example2 = new Mi052Example();
		mi052Example2.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidEqualTo(form.getOrderid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = cmi052DAO.countByExample(mi052Example2);
		if(count!=0){
			//调整排序
			Mi052Example mi052Example3 = new Mi052Example();
			mi052Example3.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidGreaterThanOrEqualTo(form.getOrderid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi052> select1 = cmi052DAO.selectByExample(mi052Example3);
			if(select1!=null && !select1.isEmpty()){
				for(Mi052 mi052e:select1){
					mi052e.setOrderid(mi052e.getOrderid()+1);
					cmi052DAO.updateByPrimaryKey(mi052e);
				}
			}
			
			
			Mi052Example mi052Example4 = new Mi052Example();
			mi052Example4.createCriteria().andServiceidEqualTo(form.getServiceid()).andOrderidLessThan(form.getOrderid())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi052> select2 = cmi052DAO.selectByExample(mi052Example4);
			if(select2!=null && !select2.isEmpty()){
				for(Mi052 mi052e:select2){
					mi052e.setOrderid(mi052e.getOrderid()-1);
					cmi052DAO.updateByPrimaryKey(mi052e);
				}
			}
			
		}
		
		
		//修改
		mi052.setDatemodified(CommonUtil.getSystemDate());
		mi052.setApiid(form.getApiid());
		mi052.setServiceid(form.getServiceid());
		int updateResult = cmi052DAO.updateByPrimaryKey(mi052);
		return updateResult;
		
	}
	
	public WebApi05204_queryResult webapi05204(CMi052 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("服务-接口依赖服务MI052", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-接口依赖服务MI052");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("服务-接口依赖服务MI052", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-接口依赖服务MI052");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("服务-接口依赖服务MI052", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-接口依赖服务MI052");
		}
		WebApi05204_queryResult select052Page = cmi052DAO.select052Page(form);
		return select052Page;
	}
	
	//保存接口排序
	@Transactional
	public void webapi05205(JSONArray arr)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(arr)){
			log.error(ERROR.PARAMS_NULL.getLogText("要保存顺序的数组为空:"+arr));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"要保存顺序的数组为空:"+arr);
		}
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			String serviceid = obj.getString("serviceid");
			String apiid = obj.getString("apiid");
			int orderid = obj.getInt("orderid");
			Mi052 mi052 = new Mi052();
			mi052.setOrderid(orderid);
			Mi052Example mi052Example = new Mi052Example();
			mi052Example.createCriteria().andServiceidEqualTo(serviceid).andApiidEqualTo(apiid)
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int updateresult = cmi052DAO.updateByExampleSelective(mi052, mi052Example);
			if(updateresult!=1){
				log.error(ERROR.UPDATE_NO_DATA.getLogText("保存顺序失败:"+arr));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"保存顺序失败:"+arr);
			}
		}
	}
	
	/**
	 * 删除服务下的接口
	 * @param arr
	 * @throws Exception
	 */
	public void webapi05206(CMi052 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getApiid())){
			log.error(ERROR.PARAMS_NULL.getLogText("接口主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "接口主键为空");
		}
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "服务主键为空");
		}
		String[] apiids = form.getApiid().trim().split(",");
		String[] serviceids = form.getServiceid().trim().split(",");
		Mi052Example mi052Example = new Mi052Example();
		mi052Example.createCriteria().andApiidIn(Arrays.asList(apiids)).andServiceidIn(Arrays.asList(serviceids))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		cmi052DAO.deleteByExample(mi052Example);
	}
	
	//监控服务-渠道-应用
	public void webapi05301(CMi053 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务名称为空");
		}
		if(!CommonUtil.isEmpty(form.getStarttime())&&!CommonUtil.isEmpty(form.getEndtime())){
			if(form.getStarttime().compareTo(form.getEndtime())>0){
				log.error(ERROR.PARAMS_NULL.getLogText("开始时间不能大于结束时间"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始时间不能大于结束时间");
			}
		}
		//检查是否已存在
		Mi053Example mi053Example = new Mi053Example();
		mi053Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andChannelEqualTo(form.getChannel())
		.andServiceidEqualTo(form.getServiceid()).andPidEqualTo(form.getPid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi053DAO.countByExample(mi053Example);
		if(0!=countByExample){
			log.error(ERROR.ADD_CHECK.getLogText("监控服务-渠道-应用Mi053"));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "监控服务-渠道-应用Mi053");
		}
		//新增
		String id = commonUtil.genKey("MI053").toString();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("监控服务-渠道-应用MI053"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("监控服务-渠道-应用MI053"));
		}
		if(!CommonUtil.isEmpty(form.getStartDay())&&!CommonUtil.isEmpty(form.getEndDay()))
		{
			form.setFreeuse2(form.getStartDay()+"-"+form.getEndDay());
		}
		form.setId(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi053DAO.insert(form);		
	}
	public int webapi05302(CMi053 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getId();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("MI053主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "MI053主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));

		Mi053 mi053 = new Mi053();
		// 修改时间
		mi053.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi053.setValidflag(Constants.IS_NOT_VALIDFLAG);
		Mi053Example example = new Mi053Example();
		example.createCriteria().andIdIn(asList);

		int result = cmi053DAO.updateByExampleSelective(mi053, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("监控服务-渠道-应用MI053:" + form.getId()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "监控服务-渠道-应用MI053删除");
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public int webapi05303(CMi053 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi053 mi053 = cmi053DAO.selectByPrimaryKey(form.getId());
		if(mi053==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		if(!CommonUtil.isEmpty(form.getStarttime())&&!CommonUtil.isEmpty(form.getEndtime())){
			if(form.getStarttime().compareTo(form.getEndtime())>0){
				log.error(ERROR.PARAMS_NULL.getLogText("开始时间不能大于结束时间"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "开始时间不能大于结束时间");
			}
		}
		//检查是否已存在
		Mi053Example mi053Example = new Mi053Example();
		mi053Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andChannelEqualTo(form.getChannel())
		.andServiceidEqualTo(form.getServiceid()).andPidEqualTo(form.getPid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi053> select = cmi053DAO.selectByExample(mi053Example);
		if( select !=null && !select.isEmpty()){
			String id = select.get(0).getId();
			if(!id.equals(form.getId())){
				log.error(ERROR.ADD_CHECK.getLogText("监控服务-渠道-应用Mi053违反唯一约束"));
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "监控服务-渠道-应用Mi053违反唯一约束");
			}
		}
		if(!CommonUtil.isEmpty(form.getStartDay())&&!CommonUtil.isEmpty(form.getEndDay()))
		{
			form.setFreeuse2(form.getStartDay()+"-"+form.getEndDay());
		}
		//修改
		mi053.setDatemodified(CommonUtil.getSystemDate());
		mi053.setChannel(form.getChannel());
		mi053.setServiceid(form.getServiceid());
		mi053.setPid(form.getPid());
		mi053.setStartserver(form.getStartserver());
		mi053.setStarttime(form.getStarttime());
		mi053.setEndtime(form.getEndtime());
		mi053.setFreeuse2(form.getFreeuse2());
		int updateResult = cmi053DAO.updateByPrimaryKey(mi053);
		return updateResult;
		
	}
	
	public WebApi05304_queryResult webapi05304(CMi053 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("服务-渠道-应用控制表MI053", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-渠道-应用控制表MI053");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("服务-渠道-应用控制表MI053", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-渠道-应用控制表MI053");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("服务-渠道-应用控制表MI053", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "服务-渠道-应用控制表MI053");
		}
		//获取模糊查询服务名称的Servicename对应的serviceid
		if(!CommonUtil.isEmpty(form.getServiceid()))
		{
			Mi051Example mi051Example = new Mi051Example();
			Mi051Example.Criteria ca = mi051Example.createCriteria();
			ca.andServicenameLike("%"+form.getServiceid()+"%");
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi051> mi051List = cmi051DAO.selectByExample(mi051Example);
			String pidTemp = "";
			for(Mi051 mi051:mi051List)
			{
				pidTemp += mi051.getServiceid()+"_";
			}
			System.out.println("pidTemp:"+pidTemp);
			form.setServiceid(pidTemp);
		}
		WebApi05304_queryResult select053Page = cmi053DAO.select053Page(form);
		return select053Page;
	}
	
	public List<Mi007> webapi05107(List<Mi007> list,List<String> slist) throws Exception
	{
		List<Mi007> newList = new ArrayList<Mi007>();
		Mi051Example mi051Example = new Mi051Example();
		Mi051Example.Criteria mi051c = mi051Example.createCriteria();
		mi051c.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi051c.andBuztypeIn(slist);
		List<Mi051> mi051List = cmi051DAO.selectByExample(mi051Example);
		for(Mi007 mi007:list)
		{
			CMi007 cmi007 = new CMi007();
			BeanUtils.copyProperties(cmi007, mi007);
			if(!CommonUtil.isEmpty(mi051List))
			{
				for(Mi051 mi051:mi051List)
				{
					if(mi051.getBuztype().equals(mi007.getItemid()))
					{
						cmi007.setIsmoneytype(mi051.getMoneytype());
					}
				}
				newList.add(cmi007);
			}else
			{
				
				newList.add(cmi007);
			}
		}
		return newList;
	}
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	public CMi050DAO getCmi050DAO() {
		return cmi050DAO;
	}
	public void setCmi050DAO(CMi050DAO cmi050dao) {
		cmi050DAO = cmi050dao;
	}
	public CMi051DAO getCmi051DAO() {
		return cmi051DAO;
	}
	public void setCmi051DAO(CMi051DAO cmi051dao) {
		cmi051DAO = cmi051dao;
	}
	public CMi052DAO getCmi052DAO() {
		return cmi052DAO;
	}
	public void setCmi052DAO(CMi052DAO cmi052dao) {
		cmi052DAO = cmi052dao;
	}
	public CMi053DAO getCmi053DAO() {
		return cmi053DAO;
	}
	public void setCmi053DAO(CMi053DAO cmi053dao) {
		cmi053DAO = cmi053dao;
	}
}
