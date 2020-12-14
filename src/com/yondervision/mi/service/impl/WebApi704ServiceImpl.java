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
import com.yondervision.mi.dao.CMi702DAO;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi704Example.Criteria;
import com.yondervision.mi.form.WebApi70401Form;
import com.yondervision.mi.form.WebApi70402Form;
import com.yondervision.mi.form.WebApi70403Form;
import com.yondervision.mi.form.WebApi70404Form;
import com.yondervision.mi.result.WebApi70404_queryResult;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi704Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi704ServiceImpl 
* @Description: 版块栏目配置维护
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi704ServiceImpl implements WebApi704Service {
	public CMi704DAO cmi704Dao = null;
	public CMi702DAO cmi702Dao = null;
	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}

	public void setCmi702Dao(CMi702DAO cmi702Dao) {
		this.cmi702Dao = cmi702Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	@Autowired
	private CodeListApi001Service codeListApi001Service = null;
	public void setCodeListApi001Service(CodeListApi001Service codeListApi001Service) {
		this.codeListApi001Service = codeListApi001Service;
	}

	/*
	 * 版块栏目配置增加
	 */
	@SuppressWarnings("unchecked")
	public void webapi70401(WebApi70401Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getNewsitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"期次编号获取失败");
		}
		if (CommonUtil.isEmpty(form.getForum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版块"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择版块");
		}
		if (CommonUtil.isEmpty(form.getColumns())) {
			log.error(ERROR.PARAMS_NULL.getLogText("栏目"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"请选择栏目");
		}
		
		// 2.验证要添加的记录所属的期次，是否已经发布，发布了则不能进行添加操作
		Mi702Example mi702Example = new Mi702Example();
		mi702Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getNewsitemid())
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		int count = cmi702Dao.countByExample(mi702Example);
		if (count != 0){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要添加", "的期次已发布", "记录的添加"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"对应该配置的期次已经发布，不能进行添加，请确认后提交！");
		}
		
		// 3. 校验对应的当前期次编号是否已经添加过同一版块
		List<Mi704> mi704ResultList = new ArrayList<Mi704>();
		Mi704Example mi704example = new Mi704Example();
		mi704example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andUpdicidEqualTo(form.getNewsitemid())
		.andItemidEqualTo(form.getForum());
		mi704ResultList = cmi704Dao.selectByExample(mi704example);
		if (mi704ResultList.size() != 0){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"版块:"+codeListApi001Service.getCodeVal(form.getCenterId(),
					Constants.FORUM_CODE+"."+form.getForum()));
		}
		
		// 4. 进行对应期次编号的版块栏目配置的入库操作
		
		Integer minSeqno = commonUtil.genKeyAndIncrease("MI704", form.getColumns().length + 1).intValue();
		
		Mi704 record = new Mi704();
		record.setCenterid(form.getCenterId());
		record.setDicid(minSeqno.toString());
		record.setItemid(form.getForum());
		record.setItemval(codeListApi001Service.getCodeVal(form.getCenterId(), Constants.FORUM_CODE+"."+form.getForum()));
		record.setUpdicid(form.getNewsitemid());
		cmi704Dao.insert(record);
		
		Integer upDicid = minSeqno;
		for(int i = 0; i < form.getColumns().length;i++){
			minSeqno = minSeqno + 1;
			record = new Mi704();
			record.setCenterid(form.getCenterId());
			record.setDicid(minSeqno.toString());
			record.setItemid(form.getColumns()[i]);
			record.setItemval(codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+form.getColumns()[i]));
			record.setUpdicid(upDicid.toString());
			cmi704Dao.insert(record);
		}
	}

	public void webapi70402(WebApi70402Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getDicidList())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版块栏目配置ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"版块栏目配置ID获取失败");
		}
		
		String[] dicids = form.getDicidList().split(",");
		
		// 2.验证要删除的记录所属的期次，是否已经发布，发布了则不能删除
		Mi702Example mi702Example = new Mi702Example();
		mi702Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getNewspapertimes())
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		int count = cmi702Dao.countByExample(mi702Example);
		if (count != 0){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要删除", "已发布", "删除"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"对应该配置的期次已经发布，不能进行删除，请确认后提交！");
		}
		
		// 3. 先删除updicid与要删除的dicid相等的，然后删除满足dicid=传入的dicid的记录
		Mi704Example mi704Example = new Mi704Example();
		for (int i = 0; i < dicids.length; i++){
			 mi704Example.clear();
			 Criteria mi704Criteria = mi704Example.createCriteria();
			 mi704Criteria.andCenteridEqualTo(form.getCenterId());
			 mi704Criteria.andUpdicidEqualTo(dicids[i]);
			 cmi704Dao.deleteByExample(mi704Example);
			 mi704Example.clear();
			 mi704Criteria = mi704Example.createCriteria();
			 mi704Criteria.andCenteridEqualTo(form.getCenterId());
			 mi704Criteria.andDicidEqualTo(dicids[i]);
			 cmi704Dao.deleteByExample(mi704Example);
		}
	}

	public void webapi70403(WebApi70403Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getNewsitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"期次编号获取失败");
		}
		if (CommonUtil.isEmpty(form.getForumdicid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版块栏目配置ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"版块栏目配置ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getForum())) {
			log.error(ERROR.PARAMS_NULL.getLogText("版块"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"版块");
		}
		if (CommonUtil.isEmpty(form.getColumns())) {
			log.error(ERROR.PARAMS_NULL.getLogText("栏目"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"栏目");
		}

		// 判断要修改的配置信息所属的期次是否已经发布，发布则不能被修改
		Mi702Example mi702Example = new Mi702Example();
		mi702Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andItemidEqualTo(form.getNewsitemid())
		.andPublishflagEqualTo(Constants.PUBLISH_FLG_ONE);
		int count = cmi702Dao.countByExample(mi702Example);
		if (count != 0){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要修改", "已发布", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"对应该配置的期次已经发布，不能进行修改，请确认后提交！");
		}
		
		// 2. 先删除updicid=传入dicid相等的记录，然后删除满足dicid=传入的dicid的记录
		Mi704Example mi704example = new Mi704Example();
		mi704example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(form.getForumdicid());
		cmi704Dao.deleteByExample(mi704example);
		mi704example.clear();
		mi704example.createCriteria().andCenteridEqualTo(form.getCenterId()).andDicidEqualTo(form.getForumdicid());
		cmi704Dao.deleteByExample(mi704example);
		
		// 3. 进行记录的添加
		Integer minSeqno = commonUtil.genKeyAndIncrease("MI704", form.getColumns().length + 1).intValue();
		Mi704 record = new Mi704();
		record.setCenterid(form.getCenterId());
		record.setDicid(minSeqno.toString());
		record.setItemid(form.getForum());
		record.setItemval(codeListApi001Service.getCodeVal(form.getCenterId(), Constants.FORUM_CODE+"."+form.getForum()));
		record.setUpdicid(form.getNewsitemid());
		cmi704Dao.insert(record);
		
		Integer upDicid = minSeqno;
		for(int i = 0; i < form.getColumns().length;i++){
			minSeqno = minSeqno + 1;
			record = new Mi704();
			record.setCenterid(form.getCenterId());
			record.setDicid(minSeqno.toString());
			record.setItemid(form.getColumns()[i]);
			record.setItemval(codeListApi001Service.getCodeVal(form.getCenterId(), Constants.COLUMNS_CODE+"."+form.getColumns()[i]));
			record.setUpdicid(upDicid.toString());
			cmi704Dao.insert(record);
		}
	}
	
	/**
	 * 版块栏目配置查询-分页
	 */
	@SuppressWarnings("unchecked")
	public List<WebApi70404_queryResult> webapi70404(WebApi70404Form form) throws Exception{
		
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getQry_newsitemid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"期次编号获取失败");
		}

		List<Mi704> mi704ForumList = new ArrayList<Mi704>();
		Mi704Example example = new Mi704Example();
		example.setOrderByClause("abs(dicid) asc");
		example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(form.getQry_newsitemid());
		mi704ForumList = cmi704Dao.selectByExample(example);
		
		List<WebApi70404_queryResult> resultList = new ArrayList<WebApi70404_queryResult>();
		WebApi70404_queryResult queryResult = new WebApi70404_queryResult();
		List<Mi704> mi704ColumnsList = new ArrayList<Mi704>();
		for (int i = 0; i < mi704ForumList.size(); i++){
			Mi704 record = mi704ForumList.get(i);
			example.clear();
			example.setOrderByClause("abs(dicid) asc");
			example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(record.getDicid());
			
			mi704ColumnsList = cmi704Dao.selectByExample(example);
			
			queryResult = new WebApi70404_queryResult();
			queryResult.setNewsitemid(form.getQry_newsitemid());
			queryResult.setDicid(record.getDicid().toString());
			queryResult.setNewspaperforum(record.getItemid());
			queryResult.setNewspapercolumnsList(mi704ColumnsList);
			resultList.add(queryResult);
		}
		return resultList;
	}

}
