package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi701DAO;
import com.yondervision.mi.dao.Mi708DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi708;
import com.yondervision.mi.dto.Mi708Example;
import com.yondervision.mi.form.SynchroImportContentScInfoBean;
import com.yondervision.mi.form.SynchroImportContentXgInfoBean;
import com.yondervision.mi.form.SynchroImportContentXzInfoBean;
import com.yondervision.mi.result.SynchroImportContentAddResult;
import com.yondervision.mi.service.SynchroImportContent001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

public class SynchroImportContent001ServiceImpl implements
		SynchroImportContent001Service {

	public CMi701DAO cmi701Dao = null;
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}
	public Mi708DAO mi708Dao = null;
	public void setMi708Dao(Mi708DAO mi708Dao) {
		this.mi708Dao = mi708Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	@SuppressWarnings("unchecked") 
	private List<SynchroImportContentAddResult> insertInfo(String centerid,
			List<SynchroImportContentXzInfoBean> xzList) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("城市中心代码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"城市中心代码");
		}
		
		List<SynchroImportContentAddResult> resultList = new ArrayList<SynchroImportContentAddResult>();
		
		int seqnoLength = xzList.size();
		// 校验seqnos的采号
		Integer minSeqno = commonUtil.genKeyAndIncrease("MI701", seqnoLength).intValue();
		
		if (CommonUtil.isEmpty(minSeqno)) {
			log.error(ERROR.NULL_KEY.getLogText("新闻动态MI701"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("新闻动态MI701"));
		}
		
		for (int i = 0; i < seqnoLength; i++){
			SynchroImportContentAddResult result = new SynchroImportContentAddResult();
			SynchroImportContentXzInfoBean xzInfoBean = xzList.get(i);
			if (CommonUtil.isEmpty(xzInfoBean.getId())) {
				log.error(ERROR.PARAMS_NULL.getLogText("同步文档id"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"同步文档id");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getClassification())) {
				log.error(ERROR.PARAMS_NULL.getLogText("所属栏目ID"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"所属栏目ID");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getDocType())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档类型"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档类型");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getDocstatus())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档状态"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档状态");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getTitle())) {
				log.error(ERROR.PARAMS_NULL.getLogText("标题"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getContentHtml())) {
				log.error(ERROR.PARAMS_NULL.getLogText("内容(带html标签)"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容(带html标签)");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getContentTxt())) {
				log.error(ERROR.PARAMS_NULL.getLogText("内容(纯文本)"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容(纯文本)");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getOperuser())) {
				log.error(ERROR.PARAMS_NULL.getLogText("操作人"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"操作人");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getCreatetime())) {
				log.error(ERROR.PARAMS_NULL.getLogText("创建时间"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"创建时间");
			}
			if (CommonUtil.isEmpty(xzInfoBean.getPublishtime())) {
				log.error(ERROR.PARAMS_NULL.getLogText("发布时间"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"发布时间");
			}
			
			// 先进行对应同步的id的查找，如果有，则更新
			Mi701Example qryExample = new Mi701Example();
			qryExample.createCriteria()
			.andFreeuse6EqualTo(xzInfoBean.getId())
			.andCenteridEqualTo(centerid);
			List<Mi701> qryList = cmi701Dao.selectByExampleWithoutBLOBs(qryExample);
			if(!CommonUtil.isEmpty(qryList)){
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				record.setClassification(xzInfoBean.getClassification());
				record.setTitle(xzInfoBean.getTitle());
				if(CommonUtil.isEmpty(xzInfoBean.getAbstracts())){
					String context = xzInfoBean.getContentTxt();
					if (context.length() <= 30) {
						record.setIntroduction(context);
					}else{
						record.setIntroduction(context.substring(0, 30)+"...");
					}
				}else{
					record.setIntroduction(xzInfoBean.getAbstracts());
				}
				
				record.setContent(xzInfoBean.getContentHtml());
				record.setReleasetime(xzInfoBean.getPublishtime());
				record.setImage(xzInfoBean.getTitleImg());
				record.setDatemodified(xzInfoBean.getModifytime());
				record.setLoginid(xzInfoBean.getOperuser());
				String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+centerid+"_"+xzInfoBean.getClassification());
				record.setFreeuse1(defaultImgUrl);
				record.setFreeuse3(xzInfoBean.getDocstatus());
				record.setSource(xzInfoBean.getDocsource());
				record.setDoctype(xzInfoBean.getDocType());
				record.setDoclink(xzInfoBean.getDoclink());
				record.setDocfilename(xzInfoBean.getDocfilename());
				record.setDocauthor(xzInfoBean.getDocauthor());
				record.setDoccreatetime(xzInfoBean.getDoccreatetime());
				record.setDockeyword(xzInfoBean.getDockeyword());
				record.setContenttxt(xzInfoBean.getContentTxt());
				
				record.setSeqno(qryList.get(0).getSeqno());
				cmi701Dao.updateByPrimaryKeySelective(record);
				
				result.setId(xzInfoBean.getId());
				result.setDocid(qryList.get(0).getSeqno().toString());
				resultList.add(result);
			}else{
				Mi701WithBLOBs record = new Mi701WithBLOBs();
				record.setSeqno(minSeqno);
				record.setCenterid(centerid);
				
				record.setClassification(xzInfoBean.getClassification());
				record.setTitle(xzInfoBean.getTitle());
				if(CommonUtil.isEmpty(xzInfoBean.getAbstracts())){
					String context = xzInfoBean.getContentTxt();
					if (context.length() <= 30) {
						record.setIntroduction(context);
					}else{
						record.setIntroduction(context.substring(0, 30)+"...");
					}
				}else{
					record.setIntroduction(xzInfoBean.getAbstracts());
				}
				record.setContent(xzInfoBean.getContentHtml());
				record.setReleasetime(xzInfoBean.getPublishtime());
				if(!CommonUtil.isEmpty(xzInfoBean.getTitleImg())){
					record.setImage(xzInfoBean.getTitleImg());
				}
				record.setValidflag(Constants.IS_VALIDFLAG);
				record.setDatecreated(xzInfoBean.getCreatetime());
				record.setLoginid(xzInfoBean.getOperuser());
				String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+centerid+"_1");
				record.setFreeuse1(defaultImgUrl);
				record.setFreeuse3(xzInfoBean.getDocstatus());// 发布标记
				record.setFreeuse5(Constants.CONTENT_SOURCE_PLAT_AUTO);// 内容管理的数据来源
				record.setFreeuse6(xzInfoBean.getId());//同步的数据的内容的id
				record.setSource(xzInfoBean.getDocsource());
				record.setPraisecounts(0);
				record.setDoctype(xzInfoBean.getDocType());
				if(!CommonUtil.isEmpty(xzInfoBean.getDoclink())){
					record.setDoclink(xzInfoBean.getDoclink());
				}
				if(!CommonUtil.isEmpty(xzInfoBean.getDocfilename())){
					record.setDocfilename(xzInfoBean.getDocfilename());
				}
				if(!CommonUtil.isEmpty(xzInfoBean.getDocauthor())){
					record.setDocauthor(xzInfoBean.getDocauthor());
				}
				if(!CommonUtil.isEmpty(xzInfoBean.getDoccreatetime())){
					record.setDoccreatetime(xzInfoBean.getDoccreatetime());
				}
				if(!CommonUtil.isEmpty(xzInfoBean.getDockeyword())){
					record.setDockeyword(xzInfoBean.getDockeyword());
				}
				record.setContenttxt(xzInfoBean.getContentTxt());
				
				cmi701Dao.insert(record);
				
				result.setId(xzInfoBean.getId());
				result.setDocid(minSeqno.toString());
				resultList.add(result);
			}
			minSeqno = minSeqno + 1;
		}
		return resultList;
	}
	
	private int modInfo(String centerid,
			List<SynchroImportContentXgInfoBean> xgList) throws Exception {
		Logger log = LoggerUtil.getLogger();
		for(int i = 0; i < xgList.size(); i++){
			SynchroImportContentXgInfoBean xgInfoBean = xgList.get(i);
			if (CommonUtil.isEmpty(xgInfoBean.getDocid())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档id"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档id");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getClassification())) {
				log.error(ERROR.PARAMS_NULL.getLogText("所属栏目ID"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"所属栏目ID");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getDocType())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档类型"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档类型");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getDocstatus())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档状态"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档状态");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getTitle())) {
				log.error(ERROR.PARAMS_NULL.getLogText("标题"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"标题");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getContentHtml())) {
				log.error(ERROR.PARAMS_NULL.getLogText("内容(带html标签)"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容(带html标签)");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getContentTxt())) {
				log.error(ERROR.PARAMS_NULL.getLogText("内容(纯文本)"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"内容(纯文本)");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getOperuser())) {
				log.error(ERROR.PARAMS_NULL.getLogText("操作人"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"操作人");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getModifytime())) {
				log.error(ERROR.PARAMS_NULL.getLogText("修改时间"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"修改时间");
			}
			if (CommonUtil.isEmpty(xgInfoBean.getPublishtime())) {
				log.error(ERROR.PARAMS_NULL.getLogText("发布时间"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"发布时间");
			}
			Mi701WithBLOBs record = new Mi701WithBLOBs();
			record.setClassification(xgInfoBean.getClassification());
			record.setTitle(xgInfoBean.getTitle());
			if(CommonUtil.isEmpty(xgInfoBean.getAbstracts())){
				String context = xgInfoBean.getContentTxt();
				if (context.length() <= 30) {
					record.setIntroduction(context);
				}else{
					record.setIntroduction(context.substring(0, 30)+"...");
				}
			}else{
				record.setIntroduction(xgInfoBean.getAbstracts());
			}
			
			record.setContent(xgInfoBean.getContentHtml());
			record.setReleasetime(xgInfoBean.getPublishtime());
			record.setImage(xgInfoBean.getTitleImg());
			record.setDatemodified(xgInfoBean.getModifytime());
			record.setLoginid(xgInfoBean.getOperuser());
			String defaultImgUrl = PropertiesReader.getProperty("properties.properties", "infoType_"+centerid+"_"+xgInfoBean.getClassification());
			record.setFreeuse1(defaultImgUrl);
			record.setFreeuse3(xgInfoBean.getDocstatus());
			record.setSource(xgInfoBean.getDocsource());
			record.setDoctype(xgInfoBean.getDocType());
			record.setDoclink(xgInfoBean.getDoclink());
			record.setDocfilename(xgInfoBean.getDocfilename());
			record.setDocauthor(xgInfoBean.getDocauthor());
			record.setDoccreatetime(xgInfoBean.getDoccreatetime());
			record.setDockeyword(xgInfoBean.getDockeyword());
			record.setContenttxt(xgInfoBean.getContentTxt());

			record.setSeqno(Integer.parseInt(xgInfoBean.getDocid()));
			cmi701Dao.updateByPrimaryKeySelective(record);
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	private int delInfo(String centerid,
			List<SynchroImportContentScInfoBean> scList) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi701 qryMi701 = new Mi701();
		
		for(int i = 0; i < scList.size(); i++){
			SynchroImportContentScInfoBean scInfoBean = scList.get(i);
			if (CommonUtil.isEmpty(scInfoBean.getDocid())) {
				log.error(ERROR.PARAMS_NULL.getLogText("文档id"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"文档id");
			}
			if (CommonUtil.isEmpty(scInfoBean.getOperuser())) {
				log.error(ERROR.PARAMS_NULL.getLogText("操作人"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"操作人");
			}
			if (CommonUtil.isEmpty(scInfoBean.getModifytime())) {
				log.error(ERROR.PARAMS_NULL.getLogText("删除时间"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"删除时间");
			}
			
			qryMi701 = cmi701Dao.selectByPrimaryKey(Integer.parseInt(scInfoBean.getDocid()));
			if(!CommonUtil.isEmpty(qryMi701)){
				if(Constants.IS_VALIDFLAG.equals(qryMi701.getValidflag())){
					Mi701WithBLOBs record = new Mi701WithBLOBs();
					// 修改时间
					record.setDatemodified(scInfoBean.getModifytime());
					// 删除标记
					record.setValidflag(Constants.IS_NOT_VALIDFLAG);
					// 删除者
					record.setLoginid(scInfoBean.getOperuser());
					
					record.setSeqno(qryMi701.getSeqno());
					cmi701Dao.updateByPrimaryKeySelective(record);
				}
			}
			
			List<Mi708> qryMi708List = new ArrayList<Mi708>();
			Mi708Example mi708Exa = new Mi708Example();
			mi708Exa.createCriteria().andCenteridEqualTo(centerid)
			.andDocidEqualTo(Integer.parseInt(scInfoBean.getDocid()))
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			qryMi708List = mi708Dao.selectByExample(mi708Exa);
			
			if(!CommonUtil.isEmpty(qryMi708List)){
				Mi708 mi708Record = new Mi708();
				mi708Record.setValidflag(Constants.IS_NOT_VALIDFLAG);
				mi708Record.setDatemodified(scInfoBean.getModifytime());
				mi708Record.setLoginid(scInfoBean.getOperuser());
				
				Mi708Example mi708Example = new Mi708Example();
				mi708Example.createCriteria().andCenteridEqualTo(centerid)
				.andDocidEqualTo(Integer.parseInt(scInfoBean.getDocid()))
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi708Dao.updateByExampleSelective(mi708Record, mi708Example);
			}
		}
		return 1;
	}
	
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public List<SynchroImportContentAddResult> synchroImportDataDeal(String centerid,
			List<SynchroImportContentXzInfoBean> xzList,
			List<SynchroImportContentXgInfoBean> xgList,
			List<SynchroImportContentScInfoBean> scList) throws Exception {
		List<SynchroImportContentAddResult> resultList = new ArrayList<SynchroImportContentAddResult>();
		if(!CommonUtil.isEmpty(xzList)){
			resultList = insertInfo(centerid, xzList);
		}
		if(!CommonUtil.isEmpty(xgList)){
			modInfo(centerid, xgList);
		}
		if(!CommonUtil.isEmpty(scList)){
			delInfo(centerid, scList);
		}
		return resultList;
	}
}
