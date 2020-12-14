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
import com.yondervision.mi.dao.Mi707DAO;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.dto.Mi707;
import com.yondervision.mi.dto.Mi707Example;
import com.yondervision.mi.form.WebApi70501Form;
import com.yondervision.mi.form.WebApi70502Form;
import com.yondervision.mi.form.WebApi70503Form;
import com.yondervision.mi.form.WebApi70504Form;
import com.yondervision.mi.form.WebApi70505Form;
import com.yondervision.mi.result.WebApi70504_queryResult;
import com.yondervision.mi.service.WebApi705Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi705ServiceImpl 
* @Description: 新闻发布-无期次
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
* 新调整说明：
* 1.关于表中classfication字段，将存放的是版块/栏目的dicid，即版块和栏目不再分为两个字段维护在数据库中。
* 2.关于表中的newspaperforum字段将用于存放对应当前所属分类的上一级的dicid，没有父级的置为0，
* 	newspapercolumns存放期次（以后有期次之分备用,现在置为-），可以考虑用一个freeuse存对应分类的名字（暂不记录）。
* 【以上的前提，版块是必输项】
* 3.新增一个表示此条新闻过期/不过期标记的表示，暂用freeuse4存放；0表示过期，1表示未过期
*/ 
public class WebApi705ServiceImpl implements WebApi705Service {
	public CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	public CMi703DAO cmi703Dao = null;
	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}
	public Mi707DAO mi707Dao = null;
	public void setMi707Dao(Mi707DAO mi707Dao) {
		this.mi707Dao = mi707Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void webapi70501(WebApi70501Form form, String reqUrl) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getClassification())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版块栏目"));
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
		String classficationUps = getClassficationUpdicid(form.getCenterId(), form.getClassification());
		if (CommonUtil.isEmpty(classficationUps)){
			classficationUps = "0";
		}
		record.setNewspaperforum(classficationUps);
		record.setNewspapercolumns("-");//该字段以后存放期次
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
		//record.setFreeuse4(Integer.parseInt(form.getValidFlg()));//是否过期的标记
		cmi701Dao.insert(record);
	}

	public int webapi70502(WebApi70502Form form) throws Exception {
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

	public int webapi70503(WebApi70503Form form, String reqUrl) throws Exception {
		
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
			log.error(ERROR.PARAMS_NULL.getLogText("版块栏目"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"版块栏目");
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
		String classficationUps = getClassficationUpdicid(form.getCenterId(), form.getClassification());
		if (CommonUtil.isEmpty(classficationUps)){
			classficationUps = "0";
		}
		mi701.setNewspaperforum(classficationUps);
		mi701.setNewspapercolumns("-");
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
		//mi701.setFreeuse4(Integer.parseInt(form.getValidFlg()));
		
		int result = cmi701Dao.updateByPrimaryKeySelective(mi701);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("新闻动态MI701","序号seqno："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"序号："+form.getSeqno());
		}
		return result;
	}

	public WebApi70504_queryResult webapi70504(WebApi70504Form form)
			throws Exception {
		WebApi70504_queryResult result = new WebApi70504_queryResult();
		result = cmi701Dao.selectMi701Page_WebApi70504(form);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi701WithBLOBs> webapi70505(WebApi70505Form form) throws Exception {
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
	
	private String getClassficationUpdicid(String centerid, String dicid){
		String classficationStr = "";
		String updicidStr = getClassficationUpdicidFromMi707(centerid, dicid);
		while(!"0".equals(updicidStr)){
			if (CommonUtil.isEmpty(classficationStr)){
				classficationStr = updicidStr;
			}else{
				classficationStr = classficationStr.concat(",").concat(updicidStr);
			}
			updicidStr = getClassficationUpdicidFromMi707(centerid, updicidStr);
		}
		System.out.println("classficationStr==="+classficationStr);
		
		
		String[] updicidsTmp = classficationStr.split(",");
		String updicids = "";
		// 将倒序的父级列表正序存放
		for (int i = updicidsTmp.length - 1; i >= 0; i--) {
			if (CommonUtil.isEmpty(updicids)){
				updicids = updicidsTmp[i];
			}else {
				updicids = updicids.concat(",").concat(updicidsTmp[i]);
			}
		}
		System.out.println("updicids==="+updicids);
		return updicids;
	}
	
	/**
	 * 查找对应dicid的所有父级，倒序逗号分隔
	 * @param centerid
	 * @param dicid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getClassficationUpdicidFromMi707(String centerid, String dicid){
		String updicidTmp = null;
		Mi707Example example = new Mi707Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andDicidEqualTo(Integer.parseInt(dicid))
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi707> list = mi707Dao.selectByExample(example);
		updicidTmp = list.get(0).getUpdicid().toString();
		
		return updicidTmp;
	}
}
