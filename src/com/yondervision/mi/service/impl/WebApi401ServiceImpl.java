package com.yondervision.mi.service.impl;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi103DAO;
import com.yondervision.mi.dao.CMi104DAO;
import com.yondervision.mi.dao.CMi105DAO;
import com.yondervision.mi.dao.Mi104DAO;
import com.yondervision.mi.dao.Mi105DAO;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.result.WebApi40101_queryResult;
import com.yondervision.mi.result.WebApi40102_queryResult;
import com.yondervision.mi.result.WebApi40103_queryResult;
import com.yondervision.mi.service.WebApi401Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi401ServiceImpl 
* @Description: APP用户注册信息处理实现
* @author Caozhongyan
* @date Oct 5, 2013 10:24:14 AM   
* 
*/ 
public class WebApi401ServiceImpl implements WebApi401Service {
	protected final Logger log = LoggerUtil.getLogger();
	private CMi103DAO cmi103Dao = null;	
	
	private Mi104DAO mi104Dao = null;
	
	private Mi105DAO mi105Dao = null;
	
	private CMi105DAO cmi105Dao = null;
	
	private CMi104DAO cmi104Dao = null;
	public CMi104DAO getCmi104Dao() {
		return cmi104Dao;
	}

	public void setCmi104Dao(CMi104DAO cmi104Dao) {
		this.cmi104Dao = cmi104Dao;
	}

	public CMi105DAO getCmi105Dao() {
		return cmi105Dao;
	}

	public void setCmi105Dao(CMi105DAO cmi105Dao) {
		this.cmi105Dao = cmi105Dao;
	}

	public Mi105DAO getMi105Dao() {
		return mi105Dao;
	}

	public void setMi105Dao(Mi105DAO mi105Dao) {
		this.mi105Dao = mi105Dao;
	}

	public Mi104DAO getMi104Dao() {
		return mi104Dao;
	}

	public void setMi104Dao(Mi104DAO mi104Dao) {
		this.mi104Dao = mi104Dao;
	}

	public CMi103DAO getCmi103Dao() {
		return cmi103Dao;
	}

	public void setCmi103Dao(CMi103DAO cmi103Dao) {
		this.cmi103Dao = cmi103Dao;
	}

	public WebApi40101_queryResult webapi40101(CMi103 form) throws Exception{
		// TODO Auto-generated method stub
		//变更为分页查询
//		form.setValidflag(Constants.IS_VALIDFLAG);
//		return cmi103Dao.select103(form);
		//变更为分页查询
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		return cmi103Dao.select103Page(form);
	}

	public WebApi40102_queryResult webapi40102(CMi103 form) throws Exception{
		// TODO Auto-generated method stub	
		//变更为分页查询
//		Mi105Example m105e=new Mi105Example();
//		com.yondervision.mi.dto.Mi105Example.Criteria ca= m105e.createCriteria();
//		ca.andUserIdEqualTo(form.getUserId()); 
//		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		List<Mi105> list=mi105Dao.selectByExample(m105e);
		//变更为分页查询
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "用户代码");
		}		
		return cmi105Dao.select105Page(form);
	}

	public WebApi40103_queryResult webapi40103(CMi103 form) throws Exception{
		// TODO Auto-generated method stub	
		//变更为分页查询
//		Mi104Example m104e=new Mi104Example();
//		com.yondervision.mi.dto.Mi104Example.Criteria ca= m104e.createCriteria();
//		ca.andUserIdEqualTo(form.getUserId());
//		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
//		List<Mi104> list=mi104Dao.selectByExample(m104e);
		//变更为分页查询
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("userId"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL
					.getValue(), "用户代码");
		}
		return cmi104Dao.select104Page(form);
	}
}
