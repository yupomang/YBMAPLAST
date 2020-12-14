package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi201DAO;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.form.AppApi10101Form;
import com.yondervision.mi.service.AppApi101Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.Distance;

/**
 * @ClassName: AppApi101ServiceImpl
 * @Description: 网点查询
 * @author Caozhongyan
 * @date Oct 11, 2013 2:02:05 PM
 * 
 */
public class AppApi101ServiceImpl implements AppApi101Service {

	private CMi201DAO cmi201Dao = null;
	
	private Mi201DAO mi201Dao = null;

	public Mi201DAO getMi201Dao() {
		return mi201Dao;
	}

	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}

	public CMi201DAO getCmi201Dao() {
		return cmi201Dao;
	}

	public void setCmi201Dao(CMi201DAO cmi201Dao) {
		this.cmi201Dao = cmi201Dao;
	}

	public List<Mi201> appApi10101Select(AppApi10101Form form) throws Exception {
		// TODO Auto-generated method stub
		List<Mi201> result = new ArrayList<Mi201>();
		// TODO 业务处理
		if ("1".equals(form.getSelectType())) {
			if(CommonUtil.isEmpty(form.getSelectValue())||form.getSelectValue().equals("%")||form.getSelectValue().equals("_")){
				return result;
			}
			Mi201Example m201e=new Mi201Example();
			m201e.setOrderByClause("centerid desc, area_id asc, website_code asc");
			com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
			if(!"20".equals(form.getChannel())){
				ca.andWebsiteNameLike("%"+form.getSelectValue()+"%");
			}else{
				ca.andWebsiteNameLike("%"+new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8")+"%");
			}
			
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		} else if ("3".equals(form.getSelectType())) {
			Mi201Example m201e=new Mi201Example();
			m201e.setOrderByClause("centerid desc, area_id asc, website_code asc");
			com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
			ca.andAreaIdEqualTo(form.getSelectValue());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		} else if("4".equals(form.getSelectType())){
			Mi201Example m201e=new Mi201Example();
			m201e.setOrderByClause("centerid desc, area_id asc, website_code asc");
			com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();			
			ca.andWebsiteTypeEqualTo(form.getSelectValue());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		} else {
			Mi201Example m201e=new Mi201Example();
			m201e.setOrderByClause("centerid desc, area_id asc, website_code asc");
			com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();		
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		}
		return result;
	}

	public List<Mi201> appApi10102Select(AppApi10101Form form) throws Exception {
		// TODO Auto-generated method stub
		List<Mi201> result = new ArrayList<Mi201>();
		//result = cmi201Dao.selectAll(form);
		Mi201Example mi201Example = new Mi201Example();
		Mi201Example.Criteria mi201Criteria = mi201Example.createCriteria();
		mi201Criteria.andFreeuse1EqualTo("1")
			.andCenteridEqualTo(form.getCenterId())
			.andValidflagEqualTo("1");
		result = cmi201Dao.selectByExample(mi201Example);
		for (int i = 0; i < result.size(); i++) {
			if(CommonUtil.isEmpty(result.get(i).getPositionX())||CommonUtil.isEmpty(result.get(i).getPositionY())){
				result.get(i).setDistance("--");
				continue;
			}
			result.get(i).setDistance(
					String.valueOf(Distance.getDistance(Double.parseDouble(form
							.getPositionX()), Double.parseDouble(form
							.getPositionY()), Double.parseDouble(CommonUtil.isEmpty(result.get(i)
							.getPositionX())?"0.00":result.get(i)
									.getPositionX()), Double.parseDouble(CommonUtil.isEmpty(result.get(i)
							.getPositionY())?"0.00":result.get(i)
									.getPositionY()))));

		}
		Collections.sort(result, new SortByDistance());		
		
		return result;
	}

	class SortByDistance implements Comparator {
		public int compare(Object o1, Object o2) {
			Mi201 s1 = (Mi201) o1;
			Mi201 s2 = (Mi201) o2;
			if (Double.parseDouble(s1.getDistance()) > Double.parseDouble(s2.getDistance()))
				return 1;
			else if(Double.parseDouble(s1.getDistance()) == Double.parseDouble(s2.getDistance()))
				return 0;
			else				
				return -1;
		}
	}

	public Mi201 appApi10103Select(String websiteName, String centerid, String channel) throws Exception {
		// TODO Auto-generated method stub
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
		if(!"20".equals(channel)){
			ca.andWebsiteNameLike("%"+websiteName+"%");
		}else{
			ca.andWebsiteNameLike("%"+new String(websiteName.getBytes("iso8859-1"),"UTF-8")+"%");
		}
		
		ca.andCenteridEqualTo(centerid);
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi201> list = null;
		list = mi201Dao.selectByExample(m201e);
		if(list == null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}		
	}

	public List<Mi201> appApi10104Select(AppApi10101Form form) throws Exception {
		List<Mi201> result = new ArrayList<Mi201>();
		Mi201Example m201e=new Mi201Example();
		m201e.setOrderByClause("centerid desc, area_id asc, website_code asc");
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();			
		if(form.getSelectType().equals("1")){
			ca.andAreaIdEqualTo(form.getAreaId());
			ca.andWebsiteTypeEqualTo(form.getWebsiteType());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		}else if(form.getSelectType().equals("2")){
			ca.andWebsiteTypeEqualTo(form.getWebsiteType());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		}else if(form.getSelectType().equals("3")){
			if(!"20".equals(form.getChannel())){
				ca.andWebsiteNameLike("%"+form.getWebsiteName()+"%");
			}else{
				ca.andWebsiteNameLike("%"+new String(form.getWebsiteName().getBytes("iso8859-1"),"UTF-8")+"%");
			}
			
			ca.andWebsiteTypeEqualTo(form.getWebsiteType());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi201Dao.selectByExample(m201e);
		}
		
		return result;
	}
	
	public Mi201 appApi10105Select(String websiteCode, String centerid) {
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
		ca.andCenteridEqualTo(centerid);
		ca.andWebsiteCodeEqualTo(websiteCode);
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi201> list = null;
		list = mi201Dao.selectByExample(m201e);
		if(list == null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}		
	}
	
	//获取对应网点编码的网点名称
	@SuppressWarnings("unchecked")
	public List<Mi201> getWebNameById(String centerid, String websitecode) throws Exception{
		List<Mi201> list = new ArrayList<Mi201>();
		Mi201Example exa = new Mi201Example();
		exa.createCriteria().andCenteridEqualTo(centerid)
		.andWebsiteCodeEqualTo(websitecode)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		list = mi201Dao.selectByExample(exa);
		return list;
	}
}
