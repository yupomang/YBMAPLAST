package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi059DAO;
import com.yondervision.mi.dto.Mi059;
import com.yondervision.mi.dto.Mi059Example;
import com.yondervision.mi.dto.Mi401;
import com.yondervision.mi.dto.Mi401Example;
import com.yondervision.mi.form.AppApi059Form;
import com.yondervision.mi.form.WebApi05904Form;
import com.yondervision.mi.result.WebApi05904_queryResult;
import com.yondervision.mi.service.WebApi059Service;
import com.yondervision.mi.util.CommonUtil;

public class WebApi059ServiceImpl implements WebApi059Service{
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	private Mi059DAO mi059Dao;
	
	public Mi059DAO getMi059Dao() {
		return mi059Dao;
	}
	public void setMi059Dao(Mi059DAO mi059Dao) {
		this.mi059Dao = mi059Dao;
	}
	
	public String webapi05901(Mi059 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("CenterID为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "CenterID为空");
		}
		if(CommonUtil.isEmpty(form.getDesensitizationid())){
			log.error(ERROR.PARAMS_NULL.getLogText("脱敏主题为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "脱敏主题为空");
		}
		String key = commonUtil.genKey("MI059", 0);
		System.out.println("*****"+key);
		form.setId(key);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		mi059Dao.insert(form);
		return key;
	}
	
	public boolean webapi05902(Mi059 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getId())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键为空");
		}
		Mi059 mi059 = mi059Dao.selectByPrimaryKey(form.getId());
		mi059.setDatemodified(CommonUtil.getSystemDate());
		mi059.setDatethem(form.getDatethem());
		mi059.setDatetype(form.getDatetype());
		mi059.setDemo1(form.getDemo1());
		mi059.setDemo2(form.getDemo2());
		mi059.setDesensitizationid(form.getDesensitizationid());
		mi059.setDesensitizationmsg(form.getDesensitizationmsg());
		mi059.setDetail(form.getDetail());
		mi059.setFirstnum(form.getFirstnum());
		mi059.setTailnum(form.getTailnum());
		mi059.setFreeuse1(form.getFreeuse1());
		mi059.setFreeuse2(form.getFreeuse2());
		mi059.setFreeuse3(form.getFreeuse3());
		mi059.setFreeuse4(form.getFreeuse4());
		mi059.setReplacechar(form.getReplacechar());
		mi059.setUpsysuserid(form.getUpsysuserid());
		mi059.setValidflag(form.getValidflag());
		int cnt = mi059Dao.updateByPrimaryKey(mi059);
		if(cnt > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean webapi05903(Mi059 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getId())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键为空");
		}
		String[] dataId = form.getId().split(",");
		for(int i = 0; i<dataId.length; i++){
			//改为逻辑删除，validFlag=0 逻辑删除，1正常使用
			//int cnt = mi059Dao.deleteByPrimaryKey(dataId[i]);
			Mi059 mi059 = mi059Dao.selectByPrimaryKey(dataId[i]);
			if(!CommonUtil.isEmpty(mi059)){
				mi059.setValidflag("0");
				int cnt = mi059Dao.updateByPrimaryKey(mi059);
				if(cnt <= 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public WebApi05904_queryResult webapi05904(WebApi05904Form form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("CenterID为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "CenterID为空");
		}
		Mi059Example mi059Example = new Mi059Example();
		mi059Example.setOrderByClause("datecreated desc");
		Mi059Example.Criteria mCriteria = mi059Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo("1");
		List<Mi059> listMi059 = new ArrayList();
		int page = 0;
		int rows = 0;
		int totalPage = 0;
		int total = this.getMi059Dao().countByExample(mi059Example);
		//不分页分支用于导出
		if("0".equals(form.getIspaging()) || CommonUtil.isEmpty(form.getIspaging())){
			List<Mi059> tmplistMi059 = this.getMi059Dao().selectByExample(mi059Example);
			for(Mi059 mi059 : tmplistMi059){
				if("1".equals(mi059.getDatethem())){
					mi059.setDatethem("是");
				}else{
					mi059.setDatethem("否");
				}
				if("1".equals(mi059.getFreeuse1())){
					mi059.setFreeuse1("是");
				}else{
					mi059.setFreeuse1("否");
				}
				listMi059.add(mi059);
			}
		}else{
			page = Integer.valueOf(form.getPagenum());
			rows = Integer.valueOf(form.getPagesize());
			page = page==0 ? Integer.valueOf(1) : page;
			rows = rows==0 ? Integer.valueOf(10) : rows;
			int skipResults = (page-1) * rows;
			listMi059 = this.getMi059Dao().selectByExamplePage(mi059Example, skipResults, rows);
			totalPage = total/rows;
			int mod = total%rows;
			if(mod > 0){
				totalPage = totalPage + 1;
			}
		}
		
		if (CommonUtil.isEmpty(listMi059)) {
			log.error(ERROR.NO_DATA.getLogText("MI059", mCriteria.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"脱敏规则");
		}		
		
		WebApi05904_queryResult result = new WebApi05904_queryResult();
		result.setList059(listMi059);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setPageNumber(page);
		result.setTotalPage(totalPage);
		return result;
	}
	
	public WebApi05904_queryResult webapi05905(AppApi059Form form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterId())){
			log.error(ERROR.PARAMS_NULL.getLogText("CenterID为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "CenterID为空");
		}
		Mi059Example mi059Example = new Mi059Example();
		mi059Example.setOrderByClause("datecreated desc");
		Mi059Example.Criteria mCriteria = mi059Example.createCriteria()
				.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo("1");
		if(!("all".equals(form.getDesensitizationId()) || CommonUtil.isEmpty(form.getDesensitizationId()))){
			mCriteria.andDesensitizationidEqualTo(form.getDesensitizationId());
		}
				
		List<Mi059> listMi059 = new ArrayList();
		int page = 0;
		int rows = 0;
		int totalPage = 0;
		int total = this.getMi059Dao().countByExample(mi059Example);
		if("0".equals(form.getIspaging()) || CommonUtil.isEmpty(form.getIspaging())){
			listMi059 = this.getMi059Dao().selectByExample(mi059Example);
		}else{
			page = Integer.valueOf(form.getPagenum());
			rows = Integer.valueOf(form.getPagesize());
			page = page==0 ? Integer.valueOf(1) : page;
			rows = rows==0 ? Integer.valueOf(10) : rows;
			int skipResults = (page-1) * rows;
			listMi059 = this.getMi059Dao().selectByExamplePage(mi059Example, skipResults, rows);
			totalPage = total/rows;
			int mod = total%rows;
			if(mod > 0){
				totalPage = totalPage + 1;
			}
		}
		
		if (CommonUtil.isEmpty(listMi059)) {
			log.error(ERROR.NO_DATA.getLogText("MI059", mCriteria.getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"脱敏规则");
		}		
		
		List<Mi059> resultList = new ArrayList();
		for(Mi059 mi059 : listMi059){
			if("1".equals(mi059.getDatethem())){
				mi059.setDatethem("是");
			}else{
				mi059.setDatethem("否");
			}
			if("1".equals(mi059.getFreeuse1())){
				mi059.setFreeuse1("是");
			}else{
				mi059.setFreeuse1("否");
			}
			resultList.add(mi059);
		}
		
		WebApi05904_queryResult result = new WebApi05904_queryResult();
		result.setList059(resultList);
		result.setPageSize(rows);
		result.setTotal(total);
		result.setPageNumber(page);
		result.setTotalPage(totalPage);
		return result;
	}
	
	public Mi059 selectDesensiInfo(String centerid, String desensiCode, String flag){
		Mi059Example m059e=new Mi059Example();
		com.yondervision.mi.dto.Mi059Example.Criteria ca= m059e.createCriteria();
		ca.andCenteridEqualTo(centerid).andFreeuse1EqualTo("1")
			.andValidflagEqualTo("1").andDesensitizationidEqualTo(desensiCode);
		List<Mi059> list059 = this.getMi059Dao().selectByExample(m059e);
		if("1".equals(flag) && (list059.size()==0)){
			Mi059Example m059e1=new Mi059Example();
			com.yondervision.mi.dto.Mi059Example.Criteria ca1= m059e1.createCriteria();
			ca1.andCenteridEqualTo("00000000").andFreeuse1EqualTo("1")
				.andValidflagEqualTo("1").andDesensitizationidEqualTo(desensiCode);
			list059 = this.getMi059Dao().selectByExample(m059e1);
		}
		if(list059.size()>0){
			return list059.get(0);
		}else{
			return null;
		}
	}
}
