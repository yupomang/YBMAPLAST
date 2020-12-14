package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi108DAO;
import com.yondervision.mi.dto.CMi108;
import com.yondervision.mi.dto.Mi108;
import com.yondervision.mi.form.WebApi40401Form;
import com.yondervision.mi.result.WebApi40401_queryResult;
import com.yondervision.mi.service.WebApi404Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi404ServiceImpl 
* @Description: 意见反馈
* @author Caozhongyan
* @date Oct 5, 2013 9:13:23 PM   
* 
*/ 
public class WebApi404ServiceImpl implements WebApi404Service {
	
	CMi108DAO cmi108Dao = null;
		
	public CMi108DAO getCmi108Dao() {
		return cmi108Dao;
	}
	public void setCmi108Dao(CMi108DAO cmi108Dao) {
		this.cmi108Dao = cmi108Dao;
	}

	public WebApi40401_queryResult webapi40401(WebApi40401Form form) throws Exception{
		// TODO Auto-generated method stub				
		WebApi40401_queryResult queryResult=cmi108Dao.selectMi108Page(form);		
		return queryResult;
	}
	public int webapi40402(CMi108 form) throws Exception{
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();		
		form.setConfirmid(form.getUsername());
		form.setConfirmtime(formatter.format(date));
//		form.setStatus(form.getStatus());
		form.setDatemodified(formatter.format(date));
		return cmi108Dao.updateMi108(form);
	}

}
