package com.yondervision.mi.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.Mi030DAO;
import com.yondervision.mi.dao.Mi032DAO;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi030Example;
import com.yondervision.mi.dto.Mi032;
import com.yondervision.mi.dto.Mi032Example;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.form.AppApi50002Form;
import com.yondervision.mi.service.WebApi030Service;
import com.yondervision.mi.util.CommonUtil;

public class WebApi030ServiceImpl implements WebApi030Service {
	
	@Autowired
	private Mi030DAO mi030DAO;
	
	@Autowired
	private Mi032DAO mi032DAO;
	
	@Autowired
	private Mi040DAO mi040DAO;
	
	@Autowired
	private CommonUtil commonUtil;
	
	public Mi030DAO getMi030DAO() {
		return mi030DAO;
	}



	public void setMi030DAO(Mi030DAO mi030dao) {
		mi030DAO = mi030dao;
	}

	

	

	

	public Mi032DAO getMi032DAO() {
		return mi032DAO;
	}



	public void setMi032DAO(Mi032DAO mi032dao) {
		mi032DAO = mi032dao;
	}



	public Mi040DAO getMi040DAO() {
		return mi040DAO;
	}



	public void setMi040DAO(Mi040DAO mi040dao) {
		mi040DAO = mi040dao;
	}



	public CommonUtil getCommonUtil() {
		return commonUtil;
	}



	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}



	@SuppressWarnings("unchecked")
	public Mi030 webapi03001(AppApi50002Form form) throws Exception {
		Mi030Example mi030Example = new Mi030Example();
		//相当于sql语句where查询条件
		mi030Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andUnitcustomeridEqualTo(form.getUnitcustid());
		List<Mi030> list = mi030DAO.selectByExample(mi030Example);
		if(!CommonUtil.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		} 
	}

	@SuppressWarnings("unchecked")
	public Mi030 webapi03002(AppApi50002Form form) throws Exception {
		Mi030 mi030 = new Mi030();
		mi030.setUnitid(commonUtil.genKey("MI030", 20));
		mi030.setCenterid(form.getCenterId());
		mi030.setUnitname(form.getUnitaccname());
		mi030.setOrganizationid(form.getOrgcode());
		mi030.setUnitcustomerid(form.getUnitcustid());
		mi030.setSociologycreditid(form.getLicensenum());
		mi030.setUnittel(form.getUnitlinkphone2());
		mi030.setValidflag(Constants.IS_VALIDFLAG);
		mi030.setDatecreated(commonUtil.getSystemDate());
		mi030.setDatemodified(commonUtil.getSystemDate());
		mi030DAO.insert(mi030);
		return mi030;
	}



	public void webapi03003(AppApi50002Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Mi030Example mi030Example = new Mi030Example();
		mi030Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andUnitcustomeridEqualTo(form.getUnitcustid());		
		
		List<Mi030> list = mi030DAO.selectByExample(mi030Example);
		if(!CommonUtil.isEmpty(list)){
			Mi030 mi030 = list.get(0);
			mi030.setUnitname(form.getUnitaccname());
			mi030.setUnitaddress(form.getUnitaddr());
			mi030.setUnittel(form.getUnitlinkphone2());
			mi030.setStartday(form.getExitdate());
			mi030DAO.updateByPrimaryKeySelective(mi030);

			String pid = (String)request.getAttribute("MI040Pid");
			Mi032Example mi032Example = new Mi032Example();
			mi032Example.createCriteria().andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo(Constants.IS_VALIDFLAG)
			.andUseridEqualTo(form.getUserId()).andChannelEqualTo(form.getChannel())
			.andPidEqualTo(pid);
			System.out.println("1234#####   "+form.getCenterId());
			System.out.println("1234#####   "+form.getUserId());
			System.out.println("1234#####   "+form.getChannel());
			System.out.println("1234#####   "+pid);
			System.out.println("1234#####   "+form.getChannel());
			List<Mi032> listMi032 = mi032DAO.selectByExample(mi032Example);
			if(CommonUtil.isEmpty(listMi032)){
				Mi032 mi032 = new Mi032();
				mi032.setId(commonUtil.genKey("MI032", 20));
				mi032.setUnitid(mi030.getUnitid());
				mi032.setCenterid(form.getCenterId());
				mi032.setChannel(form.getChannel()); 
				mi032.setPid(pid);
				//mi032.setUserid(form.getOpercode());
				mi032.setUserid(form.getUnitcustid());
				mi032.setValidflag(Constants.IS_VALIDFLAG);
				mi032.setDatecreated(commonUtil.getSystemDate());
				mi032.setDatemodified(commonUtil.getSystemDate());
				mi032DAO.insert(mi032);
			}
		}
	}
	
	
}
