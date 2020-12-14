package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi601DAO;
import com.yondervision.mi.dao.CMi602DAO;
import com.yondervision.mi.dto.Mi601;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.form.AppApi60001Form;
import com.yondervision.mi.form.AppApi60002Form;
import com.yondervision.mi.result.AppApi60002Result;
import com.yondervision.mi.service.AppApi600Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi600ServiceImpl 
* @Description: 在线留言
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class AppApi600ServiceImpl implements AppApi600Service {

	private CMi601DAO cmi601Dao = null;
	public void setCmi601Dao(CMi601DAO cmi601Dao) {
		this.cmi601Dao = cmi601Dao;
	}
	
	private CMi602DAO cmi602Dao = null;
	public void setCmi602Dao(CMi602DAO cmi602Dao) {
		this.cmi602Dao = cmi602Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void appapi60001(AppApi60001Form form) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Date date = new Date();
		Mi601 mi601 = new Mi601();
		mi601.setSeqno(commonUtil.genKey("MI601").intValue());
		mi601.setCenterid(form.getCenterId());
		mi601.setUserid(form.getUserId());
		mi601.setDevtype(form.getDeviceType());
		mi601.setDevid(form.getDeviceToken());
		mi601.setDetail(form.getMessage());
		mi601.setDetaildate(dateFormatter.format(date));
		mi601.setStatus(Constants.MSG_STATUS_FLG_ZERO);//未回复
		mi601.setValidflag(Constants.IS_VALIDFLAG);
		mi601.setDatecreated(formatter.format(date));
		mi601.setDatemodified(formatter.format(date));
		if(!CommonUtil.isEmpty(form.getUsername())){
			mi601.setFreeuse5(form.getUsername());
		}
		mi601.setFreeuse6(form.getChannel());
		cmi601Dao.insert(mi601);
		
		Mi602 mi602 = new Mi602();
		mi602.setSeqno(commonUtil.genKey("MI602").intValue());
		mi602.setCenterid(form.getCenterId());
		mi602.setUserid(form.getUserId());
		mi602.setDevtype(form.getDeviceType());
		mi602.setDevid(form.getDeviceToken());
		mi602.setDetail(form.getMessage());
		mi602.setDetaildate(dateFormatter.format(date));
		mi602.setFlg(Constants.MSG_REQ_FLG);
		mi602.setValidflag(Constants.IS_VALIDFLAG);
		mi602.setDatecreated(formatter.format(date));
		mi602.setDatemodified(formatter.format(date));
		if(!CommonUtil.isEmpty(form.getUsername())){
			mi601.setFreeuse5(form.getUsername());
		}
		mi601.setFreeuse6(form.getChannel());
		cmi602Dao.insert(mi602);
	}

	public List<AppApi60002Result> appapi60002(AppApi60002Form form)
			throws Exception {
		List<Mi602> list = cmi602Dao.selectMi602(form);
		List<AppApi60002Result> resultList = new ArrayList<AppApi60002Result>();
		AppApi60002Result result;
		for (int i = list.size()-1; i >= 0; i--) {
			result = new AppApi60002Result();
			result.setMsg(list.get(i).getDetail());
			result.setDate(list.get(i).getDetaildate());
			result.setMsgFrom(list.get(i).getFlg());
			result.setSeqno(list.get(i).getSeqno().toString());
			resultList.add(result);
		}

		return resultList;
	}

}
