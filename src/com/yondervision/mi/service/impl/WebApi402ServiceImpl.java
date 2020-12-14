package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi102DAO;
import com.yondervision.mi.dto.CMi102;
import com.yondervision.mi.dto.Mi102;
import com.yondervision.mi.dto.Mi102Example;
import com.yondervision.mi.service.WebApi402Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

public class WebApi402ServiceImpl implements WebApi402Service {

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	Mi102DAO mi102Dao = null;
	
	public Mi102DAO getMi102Dao() {
		return mi102Dao;
	}

	public void setMi102Dao(Mi102DAO mi102Dao) {
		this.mi102Dao = mi102Dao;
	}

	@SuppressWarnings("unchecked")
	public List<Mi102> webapi40208(CMi102 form) throws Exception{
		Mi102Example m102e=new Mi102Example();
		com.yondervision.mi.dto.Mi102Example.Criteria ca= m102e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi102> list=mi102Dao.selectByExample(m102e);
		return list;		
	}

	public void webapi40205(CMi102 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 判定符合条件记录是否存在
		int count = webapi40209(form);
		if (0 != count) {
			log.error(ERROR.ADD_CHECK.getLogText("中心城市编码："+form.getCenterid()+";客服类型："
					+form.getCustsvctype()+";客服账号:"+form.getCustsvcaccnum()));
			// 客服类型转换
			CodeListApi001ServiceImpl codeListApi001ServiceImpl = (CodeListApi001ServiceImpl)SpringContextUtil.getBean("codeListApi001Service");
			String custsvctypeValue = codeListApi001ServiceImpl.getCodeVal(form.getCenterid(), "contacttype."+form.getCustsvctype());
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"客服类型："
					+custsvctypeValue+";客服账号:"+form.getCustsvcaccnum());
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi102 mi102 = new Mi102();	
		mi102.setDatemodified(formatter.format(date));
		mi102.setDatecreated(formatter.format(date));
		mi102.setCustsvcaccname(form.getCustsvcaccname());
		mi102.setCustsvcaccnum(form.getCustsvcaccnum());
		mi102.setCustsvctype(form.getCustsvctype());
		mi102.setCenterid(form.getCenterid());
		mi102.setFreeuse1(form.getFreeuse1());
		mi102.setFreeuse2(form.getFreeuse2());
		mi102.setFreeuse3(form.getFreeuse3());
		mi102.setFreeuse4(form.getFreeuse4());
		mi102.setServiceid(commonUtil.genKey("MI102", 20));
		mi102.setValidflag(Constants.IS_VALIDFLAG);
		mi102.setLoginid(form.getUserid());
		mi102.setWebaddress(form.getWebaddress());
		mi102Dao.insert(mi102);
		
	}

	public void webapi40206(List<String> list) throws Exception{
		for(int i=0;i<list.size();i++){
			Mi102 mi102 = new Mi102();
			mi102.setValidflag(Constants.IS_NOT_VALIDFLAG);
			Mi102Example example=new Mi102Example();
			example.createCriteria().andServiceidEqualTo(list.get(i));
			mi102Dao.updateByExampleSelective(mi102, example);			
		}
	}

	public int webapi40207(CMi102 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		// 校验当前修改记录是否在库中已存在（唯一索引）
		if(!form.getCustsvctype().equals(form.getOldcustsvctype())
				|| !form.getCustsvcaccnum().equals(form.getOldcustsvcaccnum())){
			
			// 判定符合条件记录是否存在
			int count = webapi40209(form);
			if (0 != count) {
				log.error(ERROR.ADD_CHECK.getLogText("中心城市编码："+form.getCenterid()+";客服类型："
						+form.getCustsvctype()+";客服账号:"+form.getCustsvcaccnum()));
				// 客服类型转换
				CodeListApi001ServiceImpl codeListApi001ServiceImpl = (CodeListApi001ServiceImpl)SpringContextUtil.getBean("codeListApi001Service");
				String custsvctypeValue = codeListApi001ServiceImpl.getCodeVal(form.getCenterid(), "contacttype."+form.getCustsvctype());
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"客服类型："
						+custsvctypeValue+";客服账号:"+form.getCustsvcaccnum());
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi102 mi102 = new Mi102();
		mi102.setDatemodified(formatter.format(date));
		mi102.setCustsvcaccname(form.getCustsvcaccname());
		mi102.setCustsvcaccnum(form.getCustsvcaccnum());
		mi102.setCustsvctype(form.getCustsvctype());
		mi102.setWebaddress(form.getWebaddress());
		
		mi102.setFreeuse1(form.getFreeuse1());
		mi102.setFreeuse2(form.getFreeuse2());
		mi102.setFreeuse3(form.getFreeuse3());
		mi102.setFreeuse4(form.getFreeuse4());
		mi102.setServiceid(form.getServiceid());
		return mi102Dao.updateByPrimaryKeySelective(mi102);		
	}
	
	/*
	 * 判定符合条件记录是否存在
	 */
	private int webapi40209(CMi102 form) throws Exception{
		int count = 0;
		// 判定当前记录是否存在
		Mi102Example example = new Mi102Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid())
		.andCustsvctypeEqualTo(form.getCustsvctype()).andCustsvcaccnumEqualTo(form.getCustsvcaccnum());
		count = mi102Dao.countByExample(example);
		return count;
	}
}
