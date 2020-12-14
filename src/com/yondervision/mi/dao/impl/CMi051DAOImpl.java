package com.yondervision.mi.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi051DAO;
import com.yondervision.mi.dto.CMi051;
import com.yondervision.mi.dto.Mi051;
import com.yondervision.mi.dto.Mi051Example;
import com.yondervision.mi.result.WebApi05104_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi051DAOImpl extends Mi051DAOImpl implements CMi051DAO {

	public WebApi05104_queryResult select051Page(CMi051 form) {
		Logger log = LoggerUtil.getLogger();
		Mi051Example mi051Example = new Mi051Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi051Example.Criteria ca = mi051Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getBuztype())){
			ca.andBuztypeEqualTo(form.getBuztype());
		}
		if(!CommonUtil.isEmpty(form.getServicename())){
			ca.andServicenameLike("%"+form.getServicename()+"%");
		}
		if(!CommonUtil.isEmpty(form.getServiceid())){
			String serviceids = form.getServiceid();
			List<String> asList = Arrays.asList(serviceids.split(","));
//			for(String id:asList){
//				
//				log.info("看看我长啥样："+id+"  heh**");
//			}
			ca.andServiceidIn(asList);
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
//		log.info("看看参数page:"+page);
//		log.info("看看参数rows:"+rows);
//		log.info("看看参数skipResults:"+skipResults);
		@SuppressWarnings("unchecked")		
		List<Mi051> list = getSqlMapClientTemplate().queryForList("MI051.abatorgenerated_selectByExample", mi051Example, skipResults, rows);
		int total = this.countByExample(mi051Example);
		WebApi05104_queryResult queryResult = new WebApi05104_queryResult();
//		log.info("看看参数total:"+total);
//		log.info("看看参数rows:"+rows);
//		log.info("看看参数list:"+list);
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList051(list);
		return queryResult;
	}

	public JSONObject mixSelect051Page(CMi051 form) throws Exception{
		JSONObject result = new JSONObject();
		Logger log = LoggerUtil.getLogger();
		Mi051Example mi051Example = new Mi051Example();
		Mi051Example.Criteria ca = mi051Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getBuztype())){
			ca.andBuztypeEqualTo(form.getBuztype());
		}
		if(!CommonUtil.isEmpty(form.getServicename())){
			ca.andServicenameLike("%"+form.getServicename()+"%");
		}
		if(!CommonUtil.isEmpty(form.getServiceid())){
			String serviceids = form.getServiceid();
			List<String> asList = Arrays.asList(serviceids.split(","));
//			for(String id:asList){
//				
//				log.info("看看我长啥样："+id+"  heh**");
//			}
			ca.andServiceidIn(asList);
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
//		log.info("看看参数page:"+page);
//		log.info("看看参数rows:"+rows);
//		log.info("看看参数skipResults:"+skipResults);
		@SuppressWarnings("unchecked")		
		List<HashMap<String,Object>> list = getSqlMapClientTemplate().queryForList("CMI051.abatorgenerated_selectByExample_new", mi051Example, skipResults, rows);
		int total = this.countByExample(mi051Example);
//		log.info("看看参数total:"+total);
//		log.info("看看参数rows:"+rows);
//		log.info("看看参数list:"+list);
		for(HashMap<String,Object> hm : list)
		{
			for (Map.Entry<String, Object> entry : hm.entrySet()) {
				if(entry.getValue()==null)
				{
					hm.put(entry.getKey(), "");
				}
			}
		}
		result.put("pageSize", rows);
		result.put("pageNumber", page);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}

	public List selectByExampleForCenterid(CMi051 form) {
		@SuppressWarnings("unchecked")	
		List<Mi051> list = getSqlMapClientTemplate().queryForList("CMI051.abatorgenerated_selectByExampleForCenterId", form);
		return list;
	}
	
}
