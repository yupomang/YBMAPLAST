package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi601DAO;
import com.yondervision.mi.dao.CMi602DAO;
import com.yondervision.mi.dto.CMi601;
import com.yondervision.mi.dto.Mi601;
import com.yondervision.mi.dto.Mi601Example;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.dto.Mi602Example;
import com.yondervision.mi.form.WebApi60001Form;
import com.yondervision.mi.form.WebApi60004Form;
import com.yondervision.mi.result.WebApi60001_queryResult;
import com.yondervision.mi.service.WebApi600Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi600ServiceImpl 
* @Description: 在线留言
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi600ServiceImpl implements WebApi600Service {
	@Autowired
	public CMi601DAO cmi601Dao = null;
	public void setCmi601Dao(CMi601DAO cmi601Dao) {
		this.cmi601Dao = cmi601Dao;
	}
	@Autowired
	public CMi602DAO cmi602Dao = null;
	public void setCmi602Dao(CMi602DAO cmi602Dao) {
		this.cmi602Dao = cmi602Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public WebApi60001_queryResult webapi60001(WebApi60001Form form) throws Exception{
		WebApi60001_queryResult queryResult = cmi601Dao.selectMi601Page(form);
		return queryResult;
	}
	
	public int webapi60002(CMi601 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 校验回复内容长度
		if (form.getAnswerdetail().length() > 100){
			log.error(ERROR.SYS.getLogText("回复内容超过100个长度！"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"回复内容不能超过100个长度，请修改后重新提交！");
		}
		
		// 将消息记录插入到Mi602
		Integer seqno = Integer.parseInt(commonUtil.genKey("MI602").toString());
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("留言信息Mi602"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("留言信息Mi602"));
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Date date = new Date();	
		
		Mi602 mi602 = new Mi602();
		mi602.setSeqno(seqno);
		mi602.setCenterid(form.getCenterid());
		mi602.setUserid(form.getUserid());
		mi602.setDevtype(form.getDevtype());
		mi602.setDevid(form.getDevid());
		mi602.setDetail(form.getAnswerdetail());
		mi602.setDetaildate(dateFormatter.format(date));
		mi602.setFlg(Constants.MSG_RES_FLG);
		mi602.setValidflag(Constants.IS_VALIDFLAG);
		mi602.setDatecreated(formatter.format(date));
		mi602.setDatemodified(formatter.format(date));
		mi602.setLoginid(form.getUsername());
		mi602.setFreeuse1(form.getSeqno().toString());
		mi602.setFreeuse5(form.getFreeuse5());//昵称,名字
		mi602.setFreeuse6(form.getFreeuse6());//渠道
		cmi602Dao.insert(mi602);
		
		form.setStatus(Constants.MSG_STATUS_FLG_ONE);
		form.setAnswerid(form.getUsername());
		form.setAnswertime(dateFormatter.format(date));
		form.setDatemodified(formatter.format(date));
		return cmi601Dao.updateMi601(form);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi601> webapi60003(WebApi60001Form form) throws Exception{
		Mi601Example mi601Example = new Mi601Example();
		mi601Example.setOrderByClause("seqno asc");
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
		List<Mi601> mi601List = cmi601Dao.selectByExample(mi601Example);
		return mi601List;
	}

	@SuppressWarnings("unchecked")
	public List<Mi602> webapi60004(WebApi60004Form form) throws Exception{
		Mi602Example mi602Example = new Mi602Example();
		mi602Example.setOrderByClause("seqno asc");
		Mi602Example.Criteria ca = mi602Example.createCriteria();
		ca.andFreeuse1EqualTo(form.getSeqno());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi602> resultList = cmi602Dao.selectByExample(mi602Example);
		
		for (int i = 0; i < resultList.size(); i++){
			Mi602 mi602 = new Mi602();
			mi602 = resultList.get(i);
			
			if(CommonUtil.compareCurentDate(mi602.getDetaildate())){
				String detailDate = mi602.getDetaildate();
				mi602.setDetaildate(detailDate.substring(11));
				resultList.set(i, mi602);
			}else if (CommonUtil.compareYesterDayDate(mi602.getDetaildate())){
				mi602.setDetaildate("昨天");
				resultList.set(i, mi602);
			}else if(CommonUtil.compareCurentSevenDayDate(mi602.getDetaildate())){
				String timeTmp = CommonUtil.getWeekday(mi602.getDetaildate().substring(0, 10));
				mi602.setDetaildate(timeTmp);
				resultList.set(i, mi602);
			}else {
				String detailDate = mi602.getDetaildate();
				mi602.setDetaildate(detailDate.substring(0,10));
				resultList.set(i, mi602);
			}
		}
		return resultList;
	}
}
