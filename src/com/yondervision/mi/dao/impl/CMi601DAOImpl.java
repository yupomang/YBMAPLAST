package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi601DAO;
import com.yondervision.mi.dto.CMi601;
import com.yondervision.mi.dto.Mi601;
import com.yondervision.mi.dto.Mi601Example;
import com.yondervision.mi.form.WebApi60001Form;
import com.yondervision.mi.result.WebApi60001_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi601DAOImpl extends Mi601DAOImpl implements CMi601DAO {

	@SuppressWarnings("unchecked")
	public WebApi60001_queryResult selectMi601Page(WebApi60001Form form) throws Exception {
		Mi601Example mi601Example = new Mi601Example();
		mi601Example.setOrderByClause("seqno desc");
		Mi601Example.Criteria ca = mi601Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterId())){
			if (!"00000000".equals(form.getCenterId())) {
				ca.andCenteridEqualTo(form.getCenterId());
			}
		}
		if(!CommonUtil.isEmpty(form.getStatus())){
			ca.andStatusEqualTo(form.getStatus());
		}
		if(!CommonUtil.isEmpty(form.getStartdate())){
			ca.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000");
		}
		if(!CommonUtil.isEmpty(form.getEnddate())){
			ca.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
		}			
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi601> list = getSqlMapClientTemplate().queryForList("MI601.abatorgenerated_selectByExample", mi601Example, skipResults, rows);
		int total = this.countByExample(mi601Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		for (int i = 0; i < list.size(); i++){
			Mi601 mi601 = new Mi601();
			mi601 = list.get(i);
			
			// 临时使用freeuse2作为页面展现名字的中间变量
			if(CommonUtil.isEmpty(mi601.getFreeuse5())){
				mi601.setFreeuse2(mi601.getUserid());
			}else {
				mi601.setFreeuse2(mi601.getFreeuse5());
			}
			
			if(CommonUtil.compareCurentDate(mi601.getDetaildate())){
				String detailDate = mi601.getDetaildate();
				mi601.setDetaildate(detailDate.substring(11));
				list.set(i, mi601);
			}else if (CommonUtil.compareYesterDayDate(mi601.getDetaildate())){
				mi601.setDetaildate("昨天");
				list.set(i, mi601);
			}else if(CommonUtil.compareCurentSevenDayDate(mi601.getDetaildate())){
				String timeTmp = CommonUtil.getWeekday(mi601.getDetaildate().substring(0, 10));
				mi601.setDetaildate(timeTmp);
				list.set(i, mi601);
			}else {
				String detailDate = mi601.getDetaildate();
				mi601.setDetaildate(detailDate.substring(0,10));
				list.set(i, mi601);
			}
		}
		WebApi60001_queryResult queryResult = new WebApi60001_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		queryResult.setList601(list);
		return queryResult;
	}
	
	public int updateMi601(CMi601 form) {
		return getSqlMapClientTemplate().update("CMI601.abatorgenerated_updateByExampleSelective", form);
	}
}
