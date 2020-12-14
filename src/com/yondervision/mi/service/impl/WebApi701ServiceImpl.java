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
import com.yondervision.mi.dao.CMi702DAO;
import com.yondervision.mi.dao.CMi703DAO;
import com.yondervision.mi.dao.CMi704DAO;
import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi702;
import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.dto.Mi703Example;
import com.yondervision.mi.dto.Mi704;
import com.yondervision.mi.dto.Mi704Example;
import com.yondervision.mi.dto.Mi701Example.Criteria;
import com.yondervision.mi.form.WebApi70101Form;
import com.yondervision.mi.form.WebApi70102Form;
import com.yondervision.mi.form.WebApi70103Form;
import com.yondervision.mi.form.WebApi70104Form;
import com.yondervision.mi.form.WebApi70105Form;
import com.yondervision.mi.result.WebApi70104_queryResult;
import com.yondervision.mi.service.WebApi701Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi701ServiceImpl 
* @Description: 报刊期次维护
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public class WebApi701ServiceImpl implements WebApi701Service {
	public CMi701DAO cmi701Dao = null;
	public CMi702DAO cmi702Dao = null;
	public CMi703DAO cmi703Dao = null;
	public CMi704DAO cmi704Dao = null;
	
	public void setCmi701Dao(CMi701DAO cmi701Dao) {
		this.cmi701Dao = cmi701Dao;
	}

	public void setCmi702Dao(CMi702DAO cmi702Dao) {
		this.cmi702Dao = cmi702Dao;
	}

	public void setCmi703Dao(CMi703DAO cmi703Dao) {
		this.cmi703Dao = cmi703Dao;
	}

	public void setCmi704Dao(CMi704DAO cmi704Dao) {
		this.cmi704Dao = cmi704Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	/*
	 * 报刊期次增加
	 */
	@SuppressWarnings("unchecked")
	public void webapi70101(WebApi70101Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		/*if (CommonUtil.isEmpty(form.getItemid().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"期次编号");
		}*/
		if (CommonUtil.isEmpty(form.getItemval().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊期次"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊期次");
		}
		
		// 校验添加的期次编号在当前centerid中对应的是不存在的才可以已进行添加，保证报刊编号的唯一性
		/*List<Mi702> mi702ResultList = new ArrayList<Mi702>();
		Mi702Example mi702example = new Mi702Example();
		mi702example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemidEqualTo(form.getItemid().trim())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi702ResultList = cmi702Dao.selectByExample(mi702example);
		if (mi702ResultList.size() != 0){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"期次编码:"+form.getItemid());
		}*/
		// 2. 校验添加的期次名称在当前centerid中对应的是不存在的才可以已进行添加，保证报刊编号的唯一性
		List<Mi702> mi702ResultList = new ArrayList<Mi702>();
		Mi702Example mi702example = new Mi702Example();
		mi702example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemvalEqualTo(form.getItemval().trim())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi702ResultList = cmi702Dao.selectByExample(mi702example);
		if (mi702ResultList.size() != 0){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"报刊期次："+form.getItemval());
		}
		
		// 3. 进行期次记录的增加入库操作
		String seqno = commonUtil.genKey("MI702").toString();
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("报刊期次MI702"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("报刊期次MI702"));
		}
		
		Mi702 record = new Mi702();
		record.setSeqno(Integer.parseInt(seqno));
		record.setCenterid(form.getCenterId());
		//record.setItemid(form.getItemid().trim());
		record.setItemid("T"+seqno);
		record.setItemval(form.getItemval().trim());
		record.setCreatedate(CommonUtil.getSystemDate());
		record.setCreateuser(form.getUsername());
		record.setPublishflag(Constants.PUBLISH_FLG_ZERO);
		record.setValidflag(Constants.IS_VALIDFLAG);
		cmi702Dao.insert(record);
		
	}

	@SuppressWarnings("unchecked")
	public void webapi70102(WebApi70102Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		// 1. 校验参数合法性
		if (CommonUtil.isEmpty(form.getSeqnoList())) {
			log.error(ERROR.PARAMS_NULL.getLogText("待删除的期次ID列表seqnolist"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"待删除的期次ID列表获取失败");
		}
		
		String[] seqNos = form.getSeqnoList().split(",");
		
		// 2. 对待删除内容进行校验，是否含有已发布的记录，包含的话，则提示不能删除
		Mi702 mi702QueResult = new Mi702();
		for (int i = 0; i < seqNos.length; i++){
			mi702QueResult = cmi702Dao.selectByPrimaryKey(Integer.parseInt(seqNos[i]));
			if (Constants.PUBLISH_FLG_ONE.equals(mi702QueResult.getPublishflag())){
				log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要删除", "包含已发布数据", "删除操作"));
				throw new TransRuntimeErrorException(WEB_ALERT.VALIDFLG_LIST_CHECK.getValue(),"要删除", "发布", "删除");
			}
		}
		
		// 3. 如果期次删除，对应的所属期次的版块栏目配置、新闻、对应的评论都级联删除
		Mi701WithBLOBs mi701 = new Mi701WithBLOBs();
		Mi702 mi702 = new Mi702();
		Mi703 mi703 = new Mi703();
		Mi701Example mi701Example = new Mi701Example();
		Mi703Example mi703Example = new Mi703Example();
		List<Mi701> mi701ResultList = new ArrayList<Mi701>();
		int delResult = 0;
		
		for(int i = 0; i < seqNos.length; i++){
			// 传入参数seqno同期次编号是一样的值
			// 查询版块栏目配置表，是否有updicid=要删除期次的记录，有则做删除版块栏目配置操作
			Mi704Example mi704Example = new Mi704Example();
			mi704Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo("T"+seqNos[i]);
			List<Mi704> mi704ResultList = cmi704Dao.selectByExample(mi704Example);
			for(int k = 0; k < mi704ResultList.size(); k++){
				mi704Example.clear();
				Mi704Example.Criteria mi704Criteria = mi704Example.createCriteria();
				 mi704Criteria.andCenteridEqualTo(form.getCenterId());
				 mi704Criteria.andUpdicidEqualTo(mi704ResultList.get(k).getDicid());
				 cmi704Dao.deleteByExample(mi704Example);
				 mi704Example.clear();
				 mi704Criteria = mi704Example.createCriteria();
				 mi704Criteria.andCenteridEqualTo(form.getCenterId());
				 mi704Criteria.andDicidEqualTo(mi704ResultList.get(k).getDicid());
				 cmi704Dao.deleteByExample(mi704Example);
			}
			
			
			// MI701 中获取符合centerid和期次Id的记录List
			mi701Example = new Mi701Example();
			Criteria mi701Criteria = mi701Example.createCriteria();
			mi701Criteria.andCenteridEqualTo(form.getCenterId())
			.andClassificationEqualTo("T"+seqNos[i])
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			mi701ResultList = cmi701Dao.selectByExampleWithoutBLOBs(mi701Example);
			
			// 根据Mi701中获取到的新闻id，先到Mi703中进行对应记录的查找删除，再删除Mi701中数据
			for (int j = 0; j < mi701ResultList.size(); j++){
				
				mi703Example = new Mi703Example();
				com.yondervision.mi.dto.Mi703Example.Criteria mi703Criteria = mi703Example.createCriteria();
				mi703Criteria.andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG)
				.andNewsseqnoEqualTo(mi701ResultList.get(j).getSeqno());
				
				int mi703Counts = cmi703Dao.countByExample(mi703Example);
				if (mi703Counts != 0){
					mi703 = new Mi703();
					mi703.setDatemodified(CommonUtil.getSystemDate());
					mi703.setModifieduser(form.getUsername());
					mi703.setValidflag(Constants.IS_NOT_VALIDFLAG);
					cmi703Dao.updateByExampleSelective(mi703, mi703Example);
				}

				mi701 = new Mi701WithBLOBs();
				mi701.setSeqno(mi701ResultList.get(j).getSeqno());
				mi701.setDatemodified(CommonUtil.getSystemDate());
				mi701.setValidflag(Constants.IS_NOT_VALIDFLAG);
				mi701.setLoginid(form.getUserid());
				cmi701Dao.updateByPrimaryKeySelective(mi701);
			}
			
			mi702 = new Mi702();
			mi702.setSeqno(Integer.parseInt(seqNos[i]));
			mi702.setModifieddate(CommonUtil.getSystemDate());
			mi702.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi702.setModifieduser(form.getUsername());
			delResult = cmi702Dao.updateByPrimaryKeySelective(mi702);
			if (0 == delResult) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("期次ID:"+seqNos[i]));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"报刊期次MI702");
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	public int webapi70103(WebApi70103Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getSeqno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"报刊期次ID获取失败");
		}
		/*if (CommonUtil.isEmpty(form.getItemid().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("期次编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊期次编号");
		}*/
		if (CommonUtil.isEmpty(form.getItemval().trim())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊期次"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"报刊期次");
		}
		Integer seqno = Integer.parseInt(form.getSeqno());
		//String itemId = form.getItemid().trim();
		String itemVal = form.getItemval().trim();
		
		// 2.校验当前seqno是否已发布
		Mi702 mi702 = new Mi702();
		mi702 = cmi702Dao.selectByPrimaryKey(seqno);
		if (Constants.PUBLISH_FLG_ONE.equals(mi702.getPublishflag())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要修改", "已发布", "修改"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此要修改记录已发布，不能进行修改，请确认后提交！");
		}

		// 3.校验当前要修改的报刊期次在centerid对应的编号中是否已经存在
		/*List<Mi702> mi702ResultList = new ArrayList<Mi702>();
		Mi702Example mi702example = new Mi702Example();
		mi702example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemidEqualTo(itemId)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi702ResultList = cmi702Dao.selectByExample(mi702example);
		if (mi702ResultList.size() != 0){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"期次编码:"+itemId);
		}*/
		List<Mi702> mi702ResultList = new ArrayList<Mi702>();
		Mi702Example mi702example = new Mi702Example();
		mi702example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andItemvalEqualTo(itemVal)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi702ResultList = cmi702Dao.selectByExample(mi702example);
		if (mi702ResultList.size() != 0){
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"报刊期次:"+itemVal);
		}
		
		// 4.进行记录的修改
		Mi702 record = new Mi702();
		record.setSeqno(seqno);
		record.setItemval(itemVal);
		record.setModifieddate(CommonUtil.getSystemDate());
		record.setModifieduser(form.getUsername());
		int result = cmi702Dao.updateByPrimaryKeySelective(record);
		/*if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("报刊期次Mi702","期次ID："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"期次编号："+itemId+"; 期次名称："+itemVal);
		}*/
		
		return result;
	}
	
	/**
	 * 报刊期次查询-分页
	 */
	public WebApi70104_queryResult webapi70104(WebApi70104Form form) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		
		WebApi70104_queryResult result = new WebApi70104_queryResult();
		result = cmi702Dao.selectMi702Page(form);
		return result;
	}
	
	/**
	 * 报刊期次发布
	 */
	public int webapi70105(WebApi70105Form form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		// 1. 检验参数合法性
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getSeqno())) {
			log.error(ERROR.PARAMS_NULL.getLogText("报刊期次ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"报刊期次ID获取失败");
		}
		
		Integer seqno = Integer.parseInt(form.getSeqno());
		
		// 2.校验当前seqno是否已发布
		Mi702 mi702 = new Mi702();
		mi702 = cmi702Dao.selectByPrimaryKey(seqno);
		if (Constants.PUBLISH_FLG_ONE.equals(mi702.getPublishflag())){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要发布", "已发布", "二次发布"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此条记录已发布，无需进行二次发布，请确认后提交！");
		}

		// 3. 校验要发布的期次是否已经进行过版块栏目配置，没有则不能发布
		Mi704Example mi704Example = new Mi704Example();
		mi704Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUpdicidEqualTo(form.getItemid());
		int count = cmi704Dao.countByExample(mi704Example);
		if (count == 0){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要发布", "没有进行版块栏目的配置", "发布"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此期次还没有进行版块栏目配置，不能进行发布，请确认后提交！");
		}
		// 4. 校验要发布的期次是否已经配置新闻，没有则不能发布
		Mi701Example mi701Example = new Mi701Example();
		mi701Example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andClassificationEqualTo(form.getItemid());
		int newsCount = cmi701Dao.countByExample(mi701Example);
		if (newsCount == 0){
			log.error(ERROR.VALIDFLG_AUTH_CHECK.getLogText("要发布", "没有进行新闻信息的配置", "发布"));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此期次还没有进行新闻信息配置，不能进行发布，请确认后提交！");
		}
		
		// 5.进行对应期次新闻的发布时间的修改,暂时不要，给移动客户端返回时，只要期次发布，对应新闻都获取
		/*Mi701Example mi701Example = new Mi701Example();
		mi701Example.createCriteria()
		.andCenteridEqualTo(form.getCenterId())
		.andClassificationEqualTo(form.getItemid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		Mi701 mi701Record = new Mi701();
		mi701Record.setReleasetime(CommonUtil.getSystemDate());
		cmi701Dao.updateByExampleSelective(mi701Record, mi701Example);*/
		
		// 6.进行发布标记的修改
		Mi702 record = new Mi702();
		record.setSeqno(seqno);
		record.setPublishflag(Constants.PUBLISH_FLG_ONE);
		record.setPublishdate(CommonUtil.getSystemDate().substring(0, 10));
		record.setPublishuser(form.getUsername());
		int result = cmi702Dao.updateByPrimaryKeySelective(record);
		/*if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("报刊期次Mi702","期次ID："+form.getSeqno()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), 
					"期次名称："+form.getItemval());
		}*/
		
		return result;
	}
}
