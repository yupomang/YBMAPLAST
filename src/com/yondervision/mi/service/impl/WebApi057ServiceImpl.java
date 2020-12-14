package com.yondervision.mi.service.impl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi057DAO;
import com.yondervision.mi.dto.CMi057;
import com.yondervision.mi.service.WebApi057Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;

public class WebApi057ServiceImpl implements WebApi057Service {
    
	@Autowired
	private Mi057DAO mi057Dao;
	@Autowired
	private CommonUtil commonUtil;
	
	public Mi057DAO getMi057Dao() {
		return mi057Dao;
	}

	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setMi057Dao(Mi057DAO mi057Dao) {
		this.mi057Dao = mi057Dao;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void webapi05701(CMi057 mi057) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(mi057.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心代码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心代码为空");
		}
		if(CommonUtil.isEmpty(mi057.getReqname())){
			log.error(ERROR.PARAMS_NULL.getLogText("插件调用者为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "插件调用者为空");
		}
		if(CommonUtil.isEmpty(mi057.getStartreferer())){
			log.error(ERROR.PARAMS_NULL.getLogText("启用开关为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "启用开关为空");
		}
		if(CommonUtil.isEmpty(mi057.getReferer())){
			log.error(ERROR.PARAMS_NULL.getLogText("插件调用者域名信息为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "插件调用者域名信息为空");
		}
		mi057.setId(commonUtil.genKey("MI057", 6).toString());
		mi057.setValidflag("1");
		mi057.setDatemodified(CommonUtil.getSystemDate());
		mi057.setDatecreated(CommonUtil.getSystemDate());
		mi057Dao.insert(mi057);
	}

	public void webapi05702(CMi057 mi057) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(mi057.getId())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键id为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键id为空");
		}
		String[] ids = mi057.getId().split(",");
		for(int i=0;i<ids.length;i++){
			mi057Dao.deleteByPrimaryKey(ids[i]);
		}
	}

	public void webapi05703(CMi057 mi057) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(mi057.getId())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键id为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键id为空");
		}
		if(CommonUtil.isEmpty(mi057.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心代码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心代码为空");
		}
		if(CommonUtil.isEmpty(mi057.getReqname())){
			log.error(ERROR.PARAMS_NULL.getLogText("插件调用者为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "插件调用者为空");
		}
		if(CommonUtil.isEmpty(mi057.getStartreferer())){
			log.error(ERROR.PARAMS_NULL.getLogText("启用开关为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "启用开关为空");
		}
		if(CommonUtil.isEmpty(mi057.getReferer())){
			log.error(ERROR.PARAMS_NULL.getLogText("插件调用者域名信息为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "插件调用者域名信息为空");
		}
		mi057.setDatemodified(CommonUtil.getSystemDate());
		mi057Dao.updateByPrimaryKeySelective(mi057);
	}

	public JSONObject webapi05704(CMi057 mi057) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(mi057.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(mi057.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		if(CommonUtil.isEmpty(mi057.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("centerid为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "centerid为空");
		}
		String centerid = mi057.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		
		String sql="select * from mi057 t where t.centerid = '"+centerid+ "' and t.validflag = '"+Constants.IS_VALIDFLAG  +"'";	
		System.out.println("输入SQL："+sql);
		JSONObject obj=CommonUtil.selectByPage(sql, mi057.getPage(), mi057.getRows()); 
		
		return obj;
	}
	
	
}
