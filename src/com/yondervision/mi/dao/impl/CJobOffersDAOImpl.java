package com.yondervision.mi.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CJobOffersDAO;
import com.yondervision.mi.dto.JobOffers;
import com.yondervision.mi.dto.JobOffersExample;
import com.yondervision.mi.form.ItemInfo;
import com.yondervision.mi.form.WebApi99901Form;
import com.yondervision.mi.result.WebApi99901_queryResult;
import com.yondervision.mi.service.WebApi999Service;
import com.yondervision.mi.util.CommonUtil;

public class CJobOffersDAOImpl extends JobOffersDAOImpl implements CJobOffersDAO {

	@Autowired
	private WebApi999Service webApi999ServiceImpl;
	public void setWebApi999ServiceImpl(WebApi999Service webApi999ServiceImpl) {
		this.webApi999ServiceImpl = webApi999ServiceImpl;
	}
	
	@SuppressWarnings("unchecked")
	public WebApi99901_queryResult selectJobOffersPage(WebApi99901Form form) throws Exception {
		JobOffersExample jobOffersExample = new JobOffersExample();
		jobOffersExample.setOrderByClause("FREEUSE1 ASC, SEQNO DESC");
		JobOffersExample.Criteria ca = jobOffersExample.createCriteria();
		if(!CommonUtil.isEmpty(form.getCenterId())){
			if (!"00000000".equals(form.getCenterId())) {
				ca.andCenteridEqualTo(form.getCenterId());
			}
		}
		if(!CommonUtil.isEmpty(form.getApplyareaQry())){
			ca.andApplyareaEqualTo(form.getApplyareaQry());
		}
		if(!CommonUtil.isEmpty(form.getApplypositionQry())){
			ca.andApplypositionEqualTo(form.getApplypositionQry());
		}
		if(!CommonUtil.isEmpty(form.getStartdate())){
			ca.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000");
		}
		if(!CommonUtil.isEmpty(form.getEnddate())){
			ca.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
		}
		if(!CommonUtil.isEmpty(form.getIsread())){
			ca.andFreeuse1EqualTo(form.getIsread());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (page-1) * rows;
		List<JobOffers> list = getSqlMapClientTemplate().queryForList("JOB_OFFERS.abatorgenerated_selectByExample", jobOffersExample, skipResults, rows);
		int total = this.countByExample(jobOffersExample);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		for (int i = 0; i < list.size(); i++){
			JobOffers jobOffers = new JobOffers();
			jobOffers = list.get(i);
			
			String applyPosition = jobOffers.getApplyposition();
			List<ItemInfo> positionList = webApi999ServiceImpl.getApplyPositionList("area", jobOffers.getApplyarea());
			for(int j = 0; j < positionList.size(); j++){
				System.out.println("applyPosition==="+applyPosition);
				System.out.println("positionList.get(j).getItemid()==="+positionList.get(j).getItemid());
				if(applyPosition.equals(positionList.get(j).getItemid())){
					System.out.println("==="+positionList.get(j).getItemval());
					jobOffers.setFreeuse2(positionList.get(j).getItemval());
				}
			}
			
			String detailDate = jobOffers.getDatecreated();
			jobOffers.setDatecreated(detailDate.substring(0,10));
			list.set(i, jobOffers);
		}
		WebApi99901_queryResult queryResult = new WebApi99901_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		queryResult.setListJobOffers(list);
		return queryResult;
	}
}
