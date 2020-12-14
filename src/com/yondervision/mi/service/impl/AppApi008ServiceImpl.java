package com.yondervision.mi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi203DAO;
import com.yondervision.mi.dao.Mi203DAO;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.dto.Mi203Example;
import com.yondervision.mi.form.AppApi00801Form;
import com.yondervision.mi.service.AppApi008Service;
import com.yondervision.mi.util.CommonUtil;

public class AppApi008ServiceImpl implements AppApi008Service {
	
	private CMi203DAO cmi203Dao = null;
	
	private Mi203DAO mi203Dao = null;
	
	public Mi203DAO getMi203Dao() {
		return mi203Dao;
	}

	public void setMi203Dao(Mi203DAO mi203Dao) {
		this.mi203Dao = mi203Dao;
	}

	public CMi203DAO getCmi203Dao() {
		return cmi203Dao;
	}

	public void setCmi203Dao(CMi203DAO cmi203Dao) {
		this.cmi203Dao = cmi203Dao;
	}

	public List<Mi203> appApi00801Select(AppApi00801Form form) throws Exception{
		// TODO Auto-generated method stub
		List<Mi203> result = null;
		if(form.getCenterId().isEmpty()||form.getCenterId().equals("")){
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心ID");
		}
	    //TODO 业务处理
		if("1".equals(form.getSelectType())){
			if(CommonUtil.isEmpty(form.getSelectValue())||form.getSelectValue().equals("%")||form.getSelectValue().equals("_")){
				return result;
			}
			Mi203Example m203e=new Mi203Example();
			m203e.setOrderByClause("centerid desc, area_id asc, house_code asc");
			com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();
			if(!"20".equals(form.getChannel())){
				ca.andHouseNameLike("%"+form.getSelectValue()+"%");
			}else{
				ca.andHouseNameLike("%"+new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8")+"%");
			}
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi203Dao.selectByExample(m203e);
		}else if("3".equals(form.getSelectType())){
			Mi203Example m203e=new Mi203Example();
			m203e.setOrderByClause("centerid desc, area_id asc, house_code asc");
			com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();
			ca.andAreaIdEqualTo(form.getSelectValue());
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = mi203Dao.selectByExample(m203e);			
		}else{
			Mi203Example m203e=new Mi203Example();
			m203e.setOrderByClause("centerid desc, area_id asc, house_code asc");
			com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();			
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			result = cmi203Dao.selectAll(form);
		}    		    	
		return result;
	}
	
	public static void main(String[] args){
		String path = "D:/download/pushmsgimg/00075500/";
		File file=new File(path);
		String names[];
		names=file.list();  
		for(int i=0;i<names.length;i++)  {
			System.out.println(names[i]);  
		}
	}
}
