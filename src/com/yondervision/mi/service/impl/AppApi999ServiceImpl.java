package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.JobOffersDAO;
import com.yondervision.mi.dto.JobOffers;
import com.yondervision.mi.form.AppApi99901Form;
import com.yondervision.mi.service.AppApi999Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi999ServiceImpl 
* @Description: 应聘信息
* @author gongqi
* 
*/ 
public class AppApi999ServiceImpl implements AppApi999Service {

	private JobOffersDAO jobOffersDAO = null;
	public void setJobOffersDAO(JobOffersDAO jobOffersDAO) {
		this.jobOffersDAO = jobOffersDAO;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	/**
	 * 应聘信息录入
	 * @param form
	 * @throws Exception
	 */
	public void appapi99901(AppApi99901Form form) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		JobOffers jobOffers = new JobOffers();
		jobOffers.setSeqno(commonUtil.genKey("JOBOFFERS").intValue());
		jobOffers.setCenterid(form.getCenterId());
		jobOffers.setUsername(form.getUsername());
		jobOffers.setPhone(form.getPhone());
		jobOffers.setEmail(form.getEmail());
		jobOffers.setApplyarea(form.getApplyarea());
		jobOffers.setApplyposition(form.getApplyposition());
		jobOffers.setValidflag(Constants.IS_VALIDFLAG);
		jobOffers.setDatecreated(formatter.format(date));
		jobOffers.setDatemodified(formatter.format(date));
		jobOffers.setFreeuse1(Constants.IS_NOT_VALIDFLAG);//0：未读，1已读
		jobOffersDAO.insert(jobOffers);
	}

}
