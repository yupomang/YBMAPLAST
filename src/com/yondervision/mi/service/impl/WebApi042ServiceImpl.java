package com.yondervision.mi.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi042DAO;
import com.yondervision.mi.dto.CMi042;
import com.yondervision.mi.result.WebApi04204_queryResult;
import com.yondervision.mi.service.WebApi042Service;
import com.yondervision.mi.util.CommonUtil;

public class WebApi042ServiceImpl implements WebApi042Service {
	
	@Autowired
	private CMi042DAO cmi042DAO;
	@Autowired
	private CommonUtil commonUtil;


	public void webapi04201(CMi042 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
				
		String id = commonUtil.genKey("MI042", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("渠道监控-渠道应用运行状态监控MI042"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("渠道监控-渠道应用运行状态监控MI042"));
		}

		form.setId(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi042DAO.insert(form);
	}

	

	public WebApi04204_queryResult webapi04204(CMi042 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道应用运行状态监控MI042", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道应用运行状态监控MI042");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道应用运行状态监控MI042", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道应用运行状态监控MI042");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道应用运行状态监控MI042", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道应用运行状态监控MI042");
		}
		WebApi04204_queryResult select042Page = cmi042DAO.select042Page(form);
		return select042Page;
	}
	
	

	public CMi042DAO getCmi042DAO() {
		return cmi042DAO;
	}

	public void setCmi042DAO(CMi042DAO cmi042dao) {
		cmi042DAO = cmi042dao;
	}

	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
}
