package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.form.WebApi70602Form;
import com.yondervision.mi.form.WebApi70604Form;
import com.yondervision.mi.form.WebApi70605Form;
import com.yondervision.mi.form.WebApi70606Form;
import com.yondervision.mi.result.WebApi70604_queryResult;
import com.yondervision.mi.result.WebApi70605_queryResult;
import com.yondervision.mi.result.WebApi70606_queryResult;
import com.yondervision.mi.service.WebApi706Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi706ServiceImpl 
* @Description: 评论维护-无期次
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi706ServiceImpl implements WebApi706Service {
	public CMi701DAO cmi701Dao = null;
	public CMi703DAO cmi703Dao = null;
	
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}

	/**
	 * 评论删除
	 */
	public void webapi70602(WebApi70602Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getSeqnoList())) {
			log.error(ERROR.PARAMS_NULL.getLogText("评论ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"评论ID获取失败");
		}
		
		String[] seqNos = form.getSeqnoList().split(",");
		
		// 2. 进行评论删除
		Mi703 mi703 = new Mi703();
		int delResult = 0;
		for(int i = 0; i < seqNos.length; i++){
			mi703 = new Mi703();
			mi703.setSeqno(Integer.parseInt(seqNos[i]));
			mi703.setDatemodified(CommonUtil.getSystemDate());
			mi703.setModifieduser(form.getUsername());
			mi703.setValidflag(Constants.IS_NOT_VALIDFLAG);
			delResult = cmi703Dao.updateByPrimaryKeySelective(mi703);
			if (0 == delResult) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("评论ID:"+seqNos[i]));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"新闻评论MI703");
			}
		}
	}
	
	/**
	 * 评论查询-分页
	 */
	@SuppressWarnings("unchecked")
	public WebApi70604_queryResult webapi70604(WebApi70604Form form) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		// 2. 如果有选择条件，则先到Mi701中获取对应条件的seqno
		String classification = form.getClassification();
		String title = form.getTitle();
		List<String> newsSeqnoList = new ArrayList<String>();
		
		WebApi70604_queryResult result = new WebApi70604_queryResult();
		
		if (!CommonUtil.isEmpty(classification)
				|| !CommonUtil.isEmpty(title)){
			Mi701Example mi701Example = new Mi701Example();
			if (CommonUtil.isEmpty(classification)){
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId());
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				
				if (!CommonUtil.isEmpty(title)){
					ca.andTitleLike("%"+title+"%");
				}
			}else{
				Mi701Example.Criteria ca = mi701Example.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId());
				ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				ca.andClassificationEqualTo(classification);
				if (!CommonUtil.isEmpty(title)){
					ca.andTitleLike("%"+title+"%");
				}
				mi701Example.or(ca);
				Mi701Example.Criteria ca1 = mi701Example.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId());
				ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				ca1.andNewspaperforumLike('%'+classification+'%');
				if (!CommonUtil.isEmpty(title)){
					ca1.andTitleLike(title);
				}
				mi701Example.or(ca1);
			}
			
			List<Mi701> resultList = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
			if (CommonUtil.isEmpty(resultList)){
				result.setList703(new ArrayList<Mi703>());
				return result;
			}
			for (int i = 0; i < resultList.size(); i++){
				newsSeqnoList.add(resultList.get(i).getSeqno().toString());
			}
		}
		
		result = cmi703Dao.selectMi703PageNoTimes(form, newsSeqnoList);
		return result;
	}
	
	/**
	 * 新闻评论查询-新闻标题列表查询分页
	 */
	public WebApi70605_queryResult webapi70605(WebApi70605Form form) throws Exception {		
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		// 2.根据查询条件，到Mi701中进行新闻标题列表的分页查询
		WebApi70605_queryResult result = new WebApi70605_queryResult();
		result = cmi701Dao.selectMi701Page_TitleNoTimes(form);
		return result;
	}
	
	/**
	 * 新闻评论查询-评论查询（根据新闻seqno）分页
	 */
	public WebApi70606_queryResult webapi70606(WebApi70606Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		
		WebApi70606_queryResult result = new WebApi70606_queryResult();
		result = cmi703Dao.selectMi703Page_CommentNoTimes(form);
		return result;
	}
}
