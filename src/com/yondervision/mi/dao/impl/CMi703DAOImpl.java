package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.form.WebApi70304Form;
import com.yondervision.mi.form.WebApi70306Form;
import com.yondervision.mi.form.WebApi70604Form;
import com.yondervision.mi.form.WebApi70606Form;
import com.yondervision.mi.result.WebApi70304_queryResult;
import com.yondervision.mi.result.WebApi70306_queryResult;
import com.yondervision.mi.result.WebApi70604_queryResult;
import com.yondervision.mi.result.WebApi70606_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi703DAOImpl extends Mi703DAOImpl implements CMi703DAO {

	@SuppressWarnings("unchecked")
	public WebApi70304_queryResult selectMi703Page(WebApi70304Form form, List<String> newsSeqnoList )
			throws Exception {
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.setOrderByClause("newsseqno asc,seqno asc");
		Mi703Example.Criteria ca = mi703Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		if (!CommonUtil.isEmpty(newsSeqnoList)){
			ca.andNewsseqnoIn(newsSeqnoList);
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi703> list = getSqlMapClientTemplate().queryForList("MI703.abatorgenerated_selectByExample", mi703Example, skipResults, rows);
		int total = this.countByExample(mi703Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70304_queryResult queryResult = new WebApi70304_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi703 mi703 = list.get(i);
			mi703.setDatecreated(mi703.getDatecreated().substring(0, 10));
			list.set(i, mi703);
		}
		queryResult.setList703(list);
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70306_queryResult selectMi703Page_Comment(WebApi70306Form form)throws Exception {
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.setOrderByClause("seqno desc");
		Mi703Example.Criteria ca = mi703Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		if (!CommonUtil.isEmpty(form.getNewsseqno())){
			ca.andNewsseqnoEqualTo(Integer.parseInt(form.getNewsseqno()));
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi703> list = getSqlMapClientTemplate().queryForList("MI703.abatorgenerated_selectByExample", mi703Example, skipResults, rows);
		int total = this.countByExample(mi703Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70306_queryResult queryResult = new WebApi70306_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi703 mi703 = list.get(i);
			mi703.setDatecreated(mi703.getDatecreated().substring(0, 10));
			list.set(i, mi703);
		}
		queryResult.setList703(list);
		return queryResult;
	}
	
	public int updatePraisecountsByPrimaryKey(Mi703 record) throws Exception{
        int rows = getSqlMapClientTemplate().update("CMI703.updatePraisecountsByPrimaryKey", record);
        return rows;
	}
	
	public int updatePraisecountsSubByPrimaryKey(Mi703 record) throws Exception{
        int rows = getSqlMapClientTemplate().update("CMI703.updatePraisecountsSubByPrimaryKey", record);
        return rows;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70604_queryResult selectMi703PageNoTimes(WebApi70604Form form, List<String> newsSeqnoList )
			throws Exception {
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.setOrderByClause("newsseqno asc,seqno asc");
		Mi703Example.Criteria ca = mi703Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		if (!CommonUtil.isEmpty(newsSeqnoList)){
			ca.andNewsseqnoIn(newsSeqnoList);
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi703> list = getSqlMapClientTemplate().queryForList("MI703.abatorgenerated_selectByExample", mi703Example, skipResults, rows);
		int total = this.countByExample(mi703Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70604_queryResult queryResult = new WebApi70604_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi703 mi703 = list.get(i);
			mi703.setDatecreated(mi703.getDatecreated().substring(0, 10));
			list.set(i, mi703);
		}
		queryResult.setList703(list);
		return queryResult;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi70606_queryResult selectMi703Page_CommentNoTimes(WebApi70606Form form)throws Exception {
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.setOrderByClause("seqno desc");
		Mi703Example.Criteria ca = mi703Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		if (!CommonUtil.isEmpty(form.getNewsseqno())){
			ca.andNewsseqnoEqualTo(Integer.parseInt(form.getNewsseqno()));
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi703> list = getSqlMapClientTemplate().queryForList("MI703.abatorgenerated_selectByExample", mi703Example, skipResults, rows);
		int total = this.countByExample(mi703Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70606_queryResult queryResult = new WebApi70606_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi703 mi703 = list.get(i);
			mi703.setDatecreated(mi703.getDatecreated().substring(0, 10));
			list.set(i, mi703);
		}
		queryResult.setList703(list);
		return queryResult;
	}
}
