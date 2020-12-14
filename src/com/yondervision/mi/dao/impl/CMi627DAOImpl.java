package com.yondervision.mi.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi627DAO;
import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.dto.Mi627Example;
import com.yondervision.mi.util.CommonUtil;

public class CMi627DAOImpl extends Mi627DAOImpl implements CMi627DAO {

	/*
	 * 查询可预约的工作日
	 */
	public List<Mi627> selectResDates(CMi627 form) {
		// TODO Auto-generated method stub
		Mi627Example mi627e=new Mi627Example();
		//mi627e.setOrderByClause("festivaldate asc fetch first "+form.getDatenum()+" rows only");
		mi627e.setOrderByClause("festivaldate asc");
		Mi627Example.Criteria ca=mi627e.createCriteria();
		ca.andValidflagEqualTo("1");
		ca.andFestivalflagEqualTo("0");
		ca.andFestivaldateGreaterThan(form.getStartdate());
		ca.andCenteridEqualTo(form.getCenterid());
		List<Mi627> listMi627 = this.selectByExample(mi627e);
		if(!CommonUtil.isEmpty(listMi627)){
			List<Mi627> list = new ArrayList();
			int num = 1;
			for(int i=0;i<listMi627.size();i++){
				if(num<=form.getDatenum()){
					Mi627 mi627 = listMi627.get(i);
					list.add(mi627);
					num++;
				}else{
					break;
				}
			}
			return list;
		}else{
			return null;
		}
	}

	/*
	 * 检查是否可以增加一年的信息
	 */
	public boolean checkOneYear(CMi627 form) throws Exception {
		// TODO Auto-generated method stub
		int year=form.getYear();
		Calendar cal=Calendar.getInstance();
		if(year<0||year>9999){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"["+0+"]至[9999]之间的值");
		}
		Mi627Example mi627e=new Mi627Example();
		Mi627Example.Criteria ca=mi627e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andFestivaldateLike(year+"%");
		int yearCount=this.countByExample(mi627e);
		//如果已经有这一年的节假日信息则不新添加
		if(yearCount>=365){
			return false;
		}
		return true;
	}
}
