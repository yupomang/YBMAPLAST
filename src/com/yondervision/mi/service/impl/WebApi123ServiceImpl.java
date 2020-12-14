package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi123DAO;
import com.yondervision.mi.dao.Mi123DAO;
import com.yondervision.mi.dto.CMi123;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi123;
import com.yondervision.mi.dto.Mi123Example;
import com.yondervision.mi.result.WebApi12301_queryResult;
import com.yondervision.mi.service.WebApi123Service;
import com.yondervision.mi.util.CommonUtil;


public class WebApi123ServiceImpl implements WebApi123Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	Mi123DAO mi123Dao = null;
	
	CMi123DAO cmi123Dao = null;

	public CMi123DAO getCmi123Dao() {
		return cmi123Dao;
	}

	public void setCmi123Dao(CMi123DAO cmi123Dao) {
		this.cmi123Dao = cmi123Dao;
	}

	public Mi123DAO getMi123Dao() {
		return mi123Dao;
	}

	public void setMi123Dao(Mi123DAO mi123Dao) {
		this.mi123Dao = mi123Dao;
	}

	public void webapi12301(CMi123 form) throws Exception{
				
		Mi123Example m123e=new Mi123Example();
		com.yondervision.mi.dto.Mi123Example.Criteria ca= m123e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andStarttimeEqualTo(form.getStarttime());
		ca.andEndtimeEqualTo(form.getEndtime());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		List<Mi123> mi123List = mi123Dao.selectByExample(m123e);
		if(mi123List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"推送免打扰时间己存在");
		}
		
		Mi123Example count=new Mi123Example();
		com.yondervision.mi.dto.Mi123Example.Criteria cou= count.createCriteria();
		cou.andCenteridEqualTo(form.getCenterid());
		form.setNum(mi123Dao.countByExample(count)+1);
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
				
		Mi123 mi123 = new Mi123();
		mi123.setTimeseqid(commonUtil.genKey("MI123", 0));
		mi123.setCenterid(form.getCenterid());
		mi123.setDatecreated(formatter.format(date));		
		mi123.setDatemodified(formatter.format(date));
		mi123.setTimename(form.getTimename());
		mi123.setStarttime(form.getStarttime());
		mi123.setEndtime(form.getEndtime());
		mi123.setNum(form.getNum());
		mi123.setMustsend(form.getMustsend());
		mi123.setFreeuse1(form.getFreeuse1());
		mi123.setFreeuse2(form.getFreeuse2());
		mi123.setFreeuse3(form.getFreeuse3());
		mi123.setFreeuse4(form.getFreeuse4());
		mi123.setLoginid(form.getUserid());		
		mi123.setValidflag(Constants.IS_VALIDFLAG);		
		mi123Dao.insert(mi123);
		
	}

	public void webapi12302(CMi123 form) throws Exception{
		String[] deletes=form.getListSeqid().split(",");
		for(int i=0;i<deletes.length;i++){
			SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			Date date = new Date();
			Mi123 mi123 = new Mi123();
			mi123.setTimeseqid(deletes[i]);
			mi123.setDatemodified(formatter.format(date));
			mi123.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi123.setLoginid(form.getUserid());
			mi123Dao.updateByPrimaryKeySelective(mi123);
		}		
	}

	public WebApi12301_queryResult webapi12304(CMi123 form) throws Exception{		
		return cmi123Dao.select123Page(form);
	}	

	public int webapi12303(CMi123 form) throws Exception{		
		Mi123Example m123e=new Mi123Example();
		com.yondervision.mi.dto.Mi123Example.Criteria ca= m123e.createCriteria();
		ca.andTimeseqidNotEqualTo(form.getTimeseqid());
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andStarttimeEqualTo(form.getStarttime());
		ca.andEndtimeEqualTo(form.getEndtime());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi123> mi123List = mi123Dao.selectByExample(m123e);
		if(mi123List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"推送免打扰时间己存在");
		}		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();		
			
		Mi123 mi123 = new Mi123();
		mi123.setTimeseqid(form.getTimeseqid());
		mi123.setCenterid(form.getCenterid());
		mi123.setDatemodified(formatter.format(date));
		mi123.setStarttime(form.getStarttime());
		mi123.setEndtime(form.getEndtime());
		mi123.setMustsend(form.getMustsend());
		mi123.setLoginid(form.getUserid());
		return mi123Dao.updateByPrimaryKeySelective(mi123);
		
	}
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void webapi12305(JSONArray arr) throws Exception {
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi123 mi123 = new Mi123();
			mi123.setTimeseqid((String)obj.getString("timeseqid"));
			mi123.setNum(Integer.parseInt(obj.getString("num")));
			mi123Dao.updateByPrimaryKeySelective(mi123);
		}
	}	
	
}
