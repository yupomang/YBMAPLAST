package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi627DAO;
import com.yondervision.mi.dao.Mi627DAO;
import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.dto.Mi627Example;
import com.yondervision.mi.service.WebApi627Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.Datelet;

public class WebApi627ServiceImpl implements WebApi627Service {
	protected final Logger log = LoggerUtil.getLogger();
	private Mi627DAO mi627Dao=null;
	private CMi627DAO cmi627Dao=null;

	public CMi627DAO getCmi627Dao() {
		return cmi627Dao;
	}

	public void setCmi627Dao(CMi627DAO cmi627Dao) {
		this.cmi627Dao = cmi627Dao;
	}

	public Mi627DAO getMi627Dao() {
		return mi627Dao;
	}

	public void setMi627Dao(Mi627DAO mi627Dao) {
		this.mi627Dao = mi627Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	/*
	 * 新增一年
	 */
	public void webapi62701(CMi627 form) throws Exception {
		// TODO Auto-generated method stub
		Calendar cal=Calendar.getInstance();
		if(!cmi627Dao.checkOneYear(form)){
			return;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Datelet datelet = Datelet.getInstance();
		int year=form.getYear();
		cal.set(year, 0, 1);
		while(cal.get(Calendar.YEAR)==year){
			Mi627 mi627=new Mi627();
			mi627.setFestivaldate(datelet.cal2String(cal));
			mi627.setCenterid(form.getCenterid());
			mi627.setLoginid(form.getLoginid());
			mi627.setDatecreated(formatter.format(date));
			mi627.setDatemodified(formatter.format(date));
			mi627.setValidflag("1");
			mi627.setFestivalid(commonUtil.genKey("MI627", 20));
			mi627.setFreeuse2("");
			switch(cal.get(Calendar.DAY_OF_WEEK)){
			case 2:
				mi627.setFreeuse1("一");
				mi627.setFestivalflag("0");
				break;
			case 3:
				mi627.setFreeuse1("二");
				mi627.setFestivalflag("0");
				break;
			case 4:	
				mi627.setFreeuse1("三");
				mi627.setFestivalflag("0");
				break;
			case 5:
				mi627.setFreeuse1("四");
				mi627.setFestivalflag("0");
				break;
			case 6:
				mi627.setFreeuse1("五");
				mi627.setFestivalflag("0");
				break;
			case 7:
				mi627.setFreeuse1("六");
				mi627.setFestivalflag("1");
				break;
			case 1:
				mi627.setFreeuse1("日");
				mi627.setFestivalflag("1");
				break;
			default:
				break;
			}
			mi627Dao.insert(mi627);
			cal.add(cal.DAY_OF_MONTH, 1);
		}
	}

	/*
	 * 查询可预约工作日集合
	 */
	public List<Mi627> webapi62702(CMi627 form) throws Exception {
		CMi627 cmi627=new CMi627();
		cmi627.setCenterid(form.getCenterid());
		cmi627.setStartdate(form.getStartdate());
		cmi627.setDatenum(form.getDatenum());
		return cmi627Dao.selectResDates(cmi627);
	}

	/*
	 * 查询某一个月
	 */
	public List<Mi627> webapi62703(CMi627 form) throws Exception {
		// TODO Auto-generated method stub
		Mi627Example mi627e=new Mi627Example();
		Mi627Example.Criteria ca=mi627e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andValidflagEqualTo("1");
		String yearMonth=form.getYearmonth();
		if(CommonUtil.isEmpty(yearMonth)){
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"查询年月yyyy-mm"); 
		}
		ca.andFestivaldateLike(form.getYearmonth()+"%");
		return mi627Dao.selectByExample(mi627e);
	}

	/*
	 * 修改
	 */
	public int webbapi62704(Mi627 form) throws Exception {
		// TODO Auto-generated method stub
		Mi627Example mi627e=new Mi627Example();
		Mi627Example.Criteria ca=mi627e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andFestivaldateEqualTo(form.getFestivaldate());
		ca.andValidflagEqualTo("1");
		List<Mi627> sellist = mi627Dao.selectByExample(mi627e);
		if(sellist.size()!=1){
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"日期为["+form.getFestivaldate()+"]的有效信息不存在或有多条");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi627 mi627=new Mi627();
		mi627.setDatemodified(formatter.format(date));
		mi627.setFestivaldate(form.getFestivaldate());
		mi627.setFestivalflag(form.getFestivalflag());
		mi627.setFreeuse2(form.getFreeuse2());
		mi627.setDatecreated(sellist.get(0).getDatecreated());
		mi627.setFestivalid(sellist.get(0).getFestivalid());
		mi627.setLoginid(sellist.get(0).getLoginid());
		mi627.setValidflag("1");
		mi627.setCenterid(form.getCenterid());
		mi627.setFreeuse1(sellist.get(0).getFreeuse1());
		mi627.setFreeuse3(sellist.get(0).getFreeuse3());
		mi627.setFreeuse4(sellist.get(0).getFreeuse4());
		return mi627Dao.updateByExample(mi627, mi627e);
	}

	public boolean webbapi62705(String centerId, String datePara) throws Exception{
		Mi627Example mi627e=new Mi627Example();
		Mi627Example.Criteria ca=mi627e.createCriteria();
		ca.andCenteridEqualTo(centerId);
		ca.andFestivaldateEqualTo(datePara);
		ca.andValidflagEqualTo("1");
		ca.andFestivalflagEqualTo("1");
		List<Mi627> sellist = mi627Dao.selectByExample(mi627e);
		if(sellist.size() == 0){
			return false;
		}
		return true;
	}
}
