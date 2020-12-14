package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.Mi058DAO;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi058;
import com.yondervision.mi.dto.Mi058Example;
import com.yondervision.mi.form.AppApi058Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi05801Result;
import com.yondervision.mi.result.AppApi05802Result;
import com.yondervision.mi.service.AppApi058Service;
import com.yondervision.mi.util.CommonUtil;

public class AppApi058ServiceImpl implements AppApi058Service {
	
	private Mi058DAO mi058Dao = null;
	
	public Mi058DAO getMi058Dao() {
		return mi058Dao;
	}

	public void setMi058Dao(Mi058DAO mi058Dao) {
		this.mi058Dao = mi058Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public AppApi05802Result appApi05801Select(AppApi058Form form ,AppApi05802Result app058 ,Mi029 mi029) throws Exception{
		List<AppApi05801Result> list = new ArrayList<AppApi05801Result>();
		AppApi05802Result result = new AppApi05802Result();
		//1-all,2-已评，3-未评
		if("1".equals(form.getSelecttype())){
			for(int i=0;i<app058.getResult().size();i++){
				AppApi05801Result a058 = app058.getResult().get(i);
				Mi058Example m058e=new Mi058Example();
				m058e.setOrderByClause("datecreated desc");
				com.yondervision.mi.dto.Mi058Example.Criteria ca= m058e.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId());
				ca.andTransidEqualTo(app058.getResult().get(i).getId());
				ca.andTransdateEqualTo(app058.getResult().get(i).getTransdate());
				ca.andPersonalidEqualTo(mi029.getPersonalid());
				List<Mi058> list058 = mi058Dao.selectByExample(m058e);
				if(!CommonUtil.isEmpty(list058)){
					Mi058 mi058 = list058.get(0);
					a058.setFreedata(mi058.getEvaluate());
					a058.setFreeflag(mi058.getEvaluatetype());
					a058.setFreedate(mi058.getDatemodified());
					a058.setFreeuse1(mi058.getFreeuse1());
				}
				list.add(a058);
			}
		}else if("2".equals(form.getSelecttype())){
			Mi058Example m058e=new Mi058Example();
			m058e.setOrderByClause("datecreated desc");
			com.yondervision.mi.dto.Mi058Example.Criteria ca= m058e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andPersonalidEqualTo(mi029.getPersonalid());
			List<Mi058> list058 = mi058Dao.selectByExample(m058e);
			if(!CommonUtil.isEmpty(list058)){
				for(int i=0;i<list058.size();i++){
					Mi058 mi058 = list058.get(i);
					AppApi05801Result a058 = new AppApi05801Result();
					a058.setId(mi058.getTransid());
					a058.setAgentinstcode(mi058.getAgentinstcode());
					a058.setAgentinstmsg(mi058.getAgentinstmsg());
					a058.setTransdate(mi058.getTransdate());
					a058.setTradetype(mi058.getTradetcode());
					a058.setTrademsg(mi058.getTradetype());
					a058.setDetail(mi058.getDetail());
					a058.setCounternum(mi058.getCounternum());
					a058.setCountername(mi058.getCountername());
					a058.setFreedata(mi058.getEvaluate());
					a058.setFreeflag(mi058.getEvaluatetype());
					a058.setFreedate(mi058.getDatemodified());
					a058.setFreeuse1(mi058.getFreeuse1());
					list.add(a058);
				}
			}
		}else{
			for(int i=0;i<app058.getResult().size();i++){
				AppApi05801Result a058 = app058.getResult().get(i);
				Mi058Example m058e=new Mi058Example();
				m058e.setOrderByClause("datecreated desc");
				com.yondervision.mi.dto.Mi058Example.Criteria ca= m058e.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId());
				ca.andTransidEqualTo(app058.getResult().get(i).getId());
				ca.andTransdateEqualTo(app058.getResult().get(i).getTransdate());
				ca.andPersonalidEqualTo(mi029.getPersonalid());
				List<Mi058> list058 = mi058Dao.selectByExample(m058e);
				if(CommonUtil.isEmpty(list058)){
					list.add(a058);
				}
			}
		}
		result.setRecode(app058.getRecode());
		result.setMsg(app058.getMsg());
		result.setResult(list);
		return result;
	}
	
	
	public void appApi05802Select(AppApi058Form form , Mi029 mi029) throws Exception{
		Mi058Example m058e=new Mi058Example();
//		m058e.setOrderByClause("centerid desc, area_id asc, website_code asc");
		com.yondervision.mi.dto.Mi058Example.Criteria ca= m058e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andTransidEqualTo(form.getTransid());
		ca.andTransdateEqualTo(form.getTransdate());
		ca.andPersonalidEqualTo(mi029.getPersonalid());
		List<Mi058> list058 = mi058Dao.selectByExample(m058e);
		if(!CommonUtil.isEmpty(list058)){
			Mi058 mi058 = list058.get(0);
			mi058.setEvaluate(form.getFreedata());
			mi058.setEvaluatetype(form.getFreeflag());
			mi058.setDatemodified(CommonUtil.getSystemDate());
			mi058.setFreeuse1(form.getFreeuse1());
			mi058.setFreeuse2("channel:"+form.getChannel());
			
			if(!CommonUtil.isEmpty(form.getDetail())){
				mi058.setDetail(form.getDetail());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价描述为空**********");
			}
			if(!CommonUtil.isEmpty(form.getCounternum())){
				mi058.setCounternum(form.getCounternum());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价柜员号为空**********");
			}
			if(!CommonUtil.isEmpty(form.getCountername())){
				mi058.setCountername(form.getCountername());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价柜员姓名为空**********");
			}
			
			mi058Dao.updateByPrimaryKeySelective(mi058);
		}else{
			
			Mi058 mi058 = new Mi058();
			mi058.setId(commonUtil.genKey("MI058", 20));
			mi058.setCenterid(form.getCenterId());
			mi058.setPersonalid(mi029.getPersonalid());
			mi058.setTransid(form.getTransid());
			mi058.setTransdate(form.getTransdate());
			
			mi058.setTradetcode(form.getTradetype());
			mi058.setTradetype(form.getTrademsg());
			mi058.setAgentinstcode(form.getAgentinstcode());
			mi058.setAgentinstmsg(form.getAgentinstmsg());
			
			mi058.setEvaluate(form.getFreedata());
			mi058.setEvaluatetype(form.getFreeflag());
			mi058.setValidflag(Constants.IS_VALIDFLAG);
			mi058.setDatemodified(CommonUtil.getSystemDate());
			mi058.setDatecreated(CommonUtil.getSystemDate());
			
			if(!CommonUtil.isEmpty(form.getDetail())){
				mi058.setDetail(form.getDetail());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价描述为空**********");
			}
			if(!CommonUtil.isEmpty(form.getCounternum())){
				mi058.setCounternum(form.getCounternum());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价柜员号为空**********");
			}
			if(!CommonUtil.isEmpty(form.getCountername())){
				mi058.setCountername(form.getCountername());
			}else{
				System.out.println("服务TRANDSID："+form.getTransid()+",柜面服务时间："+form.getTransdate()+"服务评价柜员姓名为空**********");
			}
			
			mi058.setFreeuse1(form.getFreeuse1());
			mi058.setFreeuse2("channel:"+form.getChannel());
			mi058Dao.insert(mi058);
		}
	}
}
