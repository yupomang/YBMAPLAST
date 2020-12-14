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
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.form.WebApi70201Form;
import com.yondervision.mi.form.WebApi70202Form;
import com.yondervision.mi.form.WebApi70203Form;
import com.yondervision.mi.form.WebApi70204Form;
import com.yondervision.mi.form.WebApi70205Form;
import com.yondervision.mi.result.WebApi70204_queryResult;
import com.yondervision.mi.service.WebApi702Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi702ServiceImpl 
* @Description: 新闻发布
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi702ServiceImpl implements WebApi702Service {
	public CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	public CMi703DAO cmi703Dao = null;
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void webapi70201(WebApi70201Form form, String reqUrl) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getClassification())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊期次"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊期次");
		}
		if (CommonUtil.isEmpty(form.getNewspaperforum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊版块"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊版块");
		}
		if (CommonUtil.isEmpty(form.getTitle().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("标题"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
		}
		if (CommonUtil.isEmpty(form.getContentTmp().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容");
		}
		
		// 校验seqno的采号
		String seqno = commonUtil.genKey("MI701").toString();
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("新闻动态MI701"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("新闻动态MI701"));
		}
		
		Mi701WithBLOBs record = new Mi701WithBLOBs();
		record.setSeqno(Integer.parseInt(seqno));
		record.setCenterid(form.getCenterId());
		record.setClassification(form.getClassification());
		record.setNewspaperforum(form.getNewspaperforum());
		record.setNewspapercolumns(form.getNewspapercolumns());
		record.setTitle(form.getTitle().trim());
		record.setCitedtitle(form.getCitedtitle().trim());
		record.setSubtopics(form.getSubtopics().trim());
		record.setSource(form.getSource().trim());
		if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
			if (form.getContentTmp().length() <= 30) {
				record.setIntroduction(form.getContentTmp());
			}else{
				record.setIntroduction(form.getContentTmp().substring(0, 30)+"...");
			}
		}else{
			record.setIntroduction(form.getIntroduction());
		}
		record.setBlurbs(form.getBlurbs().trim());
		record.setContent(form.getContent());
		record.setPraisecounts(0);
		record.setReleasetime("9999-99-99 24:59:59");
		if (CommonUtil.isEmpty(form.getImage())){
			record.setImage(form.getImage());
		}else{
			record.setImage(reqUrl.concat(form.getImage()));
		}
		record.setValidflag(Constants.IS_VALIDFLAG);
		record.setDatecreated(CommonUtil.getSystemDate());
		record.setLoginid(form.getUserid());
		//String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType"+form.getClassification());
		//record.setFreeuse1(defaultImgUrl);
		record.setFreeuse1("/YBMAP/ui/zxly/images/news_icon.png");
		record.setFreeuse2(form.getAttentionFlg());//是否本期看点的标记
		cmi701Dao.insert(record);
	}

	public int webapi70202(WebApi70202Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if (CommonUtil.isEmpty(form.getSeqno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		
		String[] seqnos = form.getSeqno().toString().split(",");
		List<String> seqnoList = new ArrayList<String>();
		for (int i = 0; i < seqnos.length; i++) {
			seqnoList.add(seqnos[i]);
		}
		Mi703Example mi703Example = new Mi703Example();
		mi703Example.createCriteria()
		.andCenteridEqualTo(form.getCenterId())
		.andNewsseqnoIn(seqnoList)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		Mi703 mi703 = new Mi703();
		mi703.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi703.setDatemodified(CommonUtil.getSystemDate());
		mi703.setModifieduser(form.getUsername());
		cmi703Dao.updateByExampleSelective(mi703, mi703Example);
		
		Mi701WithBLOBs record = new Mi701WithBLOBs();
		// 修改时间
		record.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		record.setValidflag(Constants.IS_NOT_VALIDFLAG);
		// 删除者
		record.setLoginid(form.getUserid());
		
		Mi701Example example = new Mi701Example();
		example.createCriteria().andSeqnoIn(seqnoList);
		
		int result = cmi701Dao.updateByExampleSelective(record, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("序号seqno:"+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"新闻动态MI701");
		}
		
		return result;
	}

	public int webapi70203(WebApi70203Form form, String reqUrl) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getSeqno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("序号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"序号获取失败");
		}
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getClassification())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊期次"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊期次");
		}
		if (CommonUtil.isEmpty(form.getNewspaperforum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊版块"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊版块");
		}
		if (CommonUtil.isEmpty(form.getTitle().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("标题"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
		}
		if (CommonUtil.isEmpty(form.getContentTmp().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("内容"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容");
		}
		
		Mi701WithBLOBs mi701 = new Mi701WithBLOBs();
		mi701.setClassification(form.getClassification());
		mi701.setNewspaperforum(form.getNewspaperforum());
		mi701.setNewspapercolumns(form.getNewspapercolumns());
		mi701.setTitle(form.getTitle().trim());
		mi701.setCitedtitle(form.getCitedtitle().trim());
		mi701.setSubtopics(form.getSubtopics().trim());
		mi701.setSource(form.getSource().trim());
		if(form.getIntroduction() == null || form.getIntroduction().isEmpty()){
			if (form.getContentTmp().length() <= 30) {
				mi701.setIntroduction(form.getContentTmp());
			}else{
				mi701.setIntroduction(form.getContentTmp().substring(0, 30)+"...");
			}
		}else{
			mi701.setIntroduction(form.getIntroduction());
		}
		mi701.setBlurbs(form.getBlurbs().trim());
		mi701.setContent(form.getContent().trim());
		if (CommonUtil.isEmpty(form.getImage())){
			mi701.setImage(form.getImage());
		}else{
			mi701.setImage(reqUrl.concat(form.getImage()));
		}
		
		mi701.setDatemodified(CommonUtil.getSystemDate());
		mi701.setLoginid(form.getUserid());
		mi701.setSeqno(form.getSeqno());
		mi701.setFreeuse2(form.getAttentionFlg());
		
		int result = cmi701Dao.updateByPrimaryKeySelective(mi701);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("新闻动态MI701","序号seqno："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"序号："+form.getSeqno());
		}
		return result;
	}

	public WebApi70204_queryResult webapi70204(WebApi70204Form form)
			throws Exception {
		WebApi70204_queryResult result = new WebApi70204_queryResult();
		result = cmi701Dao.selectMi701Page_WebApi70204(form);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi701WithBLOBs> webapi70205(WebApi70205Form form) throws Exception {
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.setOrderByClause("seqno asc");
		Mi701Example.Criteria ca = mi701Example.createCriteria();
		ca.andSeqnoEqualTo(form.getSeqno());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi701WithBLOBs> resultList = cmi701Dao.selectByExampleWithBLOBs(mi701Example);
		for (int i =0; i <= resultList.size()-1; i ++) {
			Mi701WithBLOBs mi701 = resultList.get(i);
			if (!CommonUtil.isEmpty(mi701.getReleasetime())){
				mi701.setReleasetime(mi701.getReleasetime().substring(0, 10));
			}
			resultList.set(i, mi701);
		}
		return resultList;
	}
}
