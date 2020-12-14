package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi702DAO;
import com.yondervision.mi.dto.Mi702;
import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.form.WebApi70104Form;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.result.WebApi70104_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi702DAOImpl extends Mi702DAOImpl implements CMi702DAO {

	@SuppressWarnings("unchecked")
	public WebApi70104_queryResult selectMi702Page(WebApi70104Form form)
			throws Exception {
		Mi702Example mi702Example = new Mi702Example();
		mi702Example.setOrderByClause("seqno desc");
		
		Mi702Example.Criteria ca = mi702Example.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		
		/*if (!CommonUtil.isEmpty(form.getQry_itemid())){
			ca.andItemidLike("%"+form.getQry_itemid()+"%");
		}*/
		if (!CommonUtil.isEmpty(form.getQry_itemid())){
			ca.andItemvalLike("%"+form.getQry_itemid()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<Mi702> list = getSqlMapClientTemplate().queryForList("MI702.abatorgenerated_selectByExample", mi702Example, skipResults, rows);
		int total = this.countByExample(mi702Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		
		WebApi70104_queryResult queryResult = new WebApi70104_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		for (int i =0; i <= list.size()-1; i ++) {
			Mi702 mi702 = list.get(i);
			mi702.setCreatedate(mi702.getCreatedate().substring(0, 10));
			if (!CommonUtil.isEmpty(mi702.getModifieddate())){
				mi702.setModifieddate(mi702.getModifieddate().substring(0, 10));
			}else{
				mi702.setModifieddate("-");
			}
			if(CommonUtil.isEmpty(mi702.getModifieduser())){
				mi702.setModifieduser("-");
			}
			if (!CommonUtil.isEmpty(mi702.getPublishdate())){
				mi702.setPublishdate(mi702.getPublishdate().substring(0, 10));
			}else{
				mi702.setPublishdate("-");
			}
			if(CommonUtil.isEmpty(mi702.getPublishuser())){
				mi702.setPublishuser("-");
			}
			list.set(i, mi702);
		}
		queryResult.setList702(list);
		return queryResult;
	}
	
	/*
	 * 查询期次列表（itemid，itemval）
	 */
	@SuppressWarnings("unchecked")
	public List<NewspapersTitleInfoBean> selectTimesList(Mi702Example example)throws Exception {
        List<NewspapersTitleInfoBean> list = getSqlMapClientTemplate().queryForList("CMI702.self_newspaperstimes_selectByExample", example);
        return list;
	}
}
