package com.yondervision.mi.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi037DAO;
import com.yondervision.mi.dao.CMi039DAO;
import com.yondervision.mi.dao.CMi047DAO;
import com.yondervision.mi.dto.CMi037;
import com.yondervision.mi.dto.CMi039;
import com.yondervision.mi.dto.CMi047;
import com.yondervision.mi.dto.Mi039;
import com.yondervision.mi.dto.Mi039Example;
import com.yondervision.mi.dto.Mi047;
import com.yondervision.mi.dto.Mi047Example;
import com.yondervision.mi.result.WebApi03704_queryResult;
import com.yondervision.mi.result.WebApi03904_queryResult;
import com.yondervision.mi.result.WebApi04704_queryResult;
import com.yondervision.mi.service.WebApi047Service;
import com.yondervision.mi.util.CommonUtil;

public class WebApi047ServiceImpl implements WebApi047Service {
	
	@Autowired
	private CMi047DAO cmi047DAO;
	@Autowired
	private CMi039DAO cmi039DAO;
	@Autowired
	private CMi037DAO cmi037DAO;
	@Autowired
	private CommonUtil commonUtil;
	
	public void webapi04701(CMi047 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getName())){
			log.error(ERROR.PARAMS_NULL.getLogText("姓名"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "姓名为空");
		}
		if(CommonUtil.isEmpty(form.getPhone())){
			log.error(ERROR.PARAMS_NULL.getLogText("手机号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "手机号为空");
		}
		if(CommonUtil.isEmpty(form.getPosition())){
			log.error(ERROR.PARAMS_NULL.getLogText("职位"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "职位为空");
		}
		if(CommonUtil.isEmpty(form.getDepartment())){
			log.error(ERROR.PARAMS_NULL.getLogText("部门"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "部门为空");
		}
		if(CommonUtil.isEmpty(form.getControlid())){
			log.error(ERROR.PARAMS_NULL.getLogText("监控主题"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "监控主题为空");
		}
		String id = commonUtil.genKey("MI047", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("消息通知通讯录MI047"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("消息通知通讯录MI047"));
		}
		form.setCommunicationid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi047DAO.insert(form);		
	}
	public int webapi04702(CMi047 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getCommunicationid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));

		Mi047 mi047 = new Mi047();
		// 修改时间
		mi047.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi047.setValidflag(Constants.IS_NOT_VALIDFLAG);
		Mi047Example example = new Mi047Example();
		example.createCriteria().andCommunicationidIn(asList);

		int result = cmi047DAO.updateByExampleSelective(mi047, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("消息通知通讯录MI047:" + form.getCommunicationid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "消息通知通讯录MI047删除");
		}
		return result;
	}
	public int webapi04703(CMi047 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi047 mi047 = cmi047DAO.selectByPrimaryKey(form.getCommunicationid());
		if(mi047==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		mi047.setDatemodified(CommonUtil.getSystemDate());
		mi047.setName(form.getName());
		mi047.setDepartment(form.getDepartment());
		mi047.setPhone(form.getPhone());
		mi047.setPosition(form.getPosition());
		mi047.setControlid(form.getControlid());
		int updateResult = cmi047DAO.updateByPrimaryKey(mi047);
		return updateResult;
		
	}
	
	public WebApi04704_queryResult webapi04704(CMi047 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-消息通知通讯录MI047", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-消息通知通讯录MI047");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-消息通知通讯录MI047", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-消息通知通讯录MI047");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-消息通知通讯录MI047", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-消息通知通讯录MI047");
		}
		WebApi04704_queryResult select047Page = cmi047DAO.select047Page(form);
		return select047Page;
	}
	
	
	public void webapi03901(CMi039 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getMessage())){
			log.error(ERROR.PARAMS_NULL.getLogText("监控主题描述"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "监控主题描述为空");
		}
		if(CommonUtil.isEmpty(form.getType())){
			log.error(ERROR.PARAMS_NULL.getLogText("监控编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "监控编码为空");
		}
		String id = commonUtil.genKey("MI039").toString();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("监控主题MI039"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("监控主题MI039"));
		}
		form.setControlid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi039DAO.insert(form);		
	}
	public int webapi03902(CMi039 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getControlid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("MI039主键为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "MI039主键为空");
		}
		List<String> asList = Arrays.asList(id.split(","));

		Mi039 mi039 = new Mi039();
		// 修改时间
		mi039.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi039.setValidflag(Constants.IS_NOT_VALIDFLAG);
		Mi039Example example = new Mi039Example();
		example.createCriteria().andControlidIn(asList);

		int result = cmi039DAO.updateByExampleSelective(mi039, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("监控主题MI047:" + form.getControlid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "监控主题MI047删除");
		}
		return result;
	}
	public int webapi03903(CMi039 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi039 mi039 = cmi039DAO.selectByPrimaryKey(form.getControlid());
		if(mi039==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		mi039.setDatemodified(CommonUtil.getSystemDate());
		mi039.setMessage(form.getMessage());
		mi039.setType(form.getType());
		int updateResult = cmi039DAO.updateByPrimaryKey(mi039);
		return updateResult;
		
	}
	
	public WebApi03904_queryResult webapi03904(CMi039 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI039", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI039");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI039", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI039");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI039", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI039");
		}
		
		WebApi03904_queryResult select039Page = cmi039DAO.select039Page(form);
		return select039Page;
	}
	
	/**
	 * 获取所有的监控主题
	 * @return
	 * @throws Exception
	 */
	public List<Mi039> webapi03905() throws Exception{
		Mi039Example mi039Example = new Mi039Example();
		mi039Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi039> selectByExample = cmi039DAO.selectByExample(mi039Example);
		return selectByExample;
	}
	
	public WebApi03704_queryResult webapi03704(CMi037 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI037", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI037");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI037", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI037");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-监控消息通知通讯记录MI037", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-监控消息通知通讯记录MI037");
		}
		WebApi03704_queryResult select037Page = cmi037DAO.select037Page(form);
		return select037Page;
	}
	
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	public CMi047DAO getCmi047DAO() {
		return cmi047DAO;
	}
	public void setCmi047DAO(CMi047DAO cmi047dao) {
		cmi047DAO = cmi047dao;
	}
	public CMi039DAO getCmi039DAO() {
		return cmi039DAO;
	}
	public void setCmi039DAO(CMi039DAO cmi039dao) {
		cmi039DAO = cmi039dao;
	}
	public CMi037DAO getCmi037DAO() {
		return cmi037DAO;
	}
	public void setCmi037DAO(CMi037DAO cmi037dao) {
		cmi037DAO = cmi037dao;
	}
}
