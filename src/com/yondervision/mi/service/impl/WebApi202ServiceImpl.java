package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi202DAO;
import com.yondervision.mi.dto.CMi202;
import com.yondervision.mi.dto.Mi122;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi202Example;
import com.yondervision.mi.service.WebApi202Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi202ServiceImpl 
* @Description: 区域信息维护处理实现
* @author gongq
* @date Oct 16, 2013 2:56:45 PM   
* 
*/ 
public class WebApi202ServiceImpl implements WebApi202Service {

	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public Mi202DAO mi202Dao = null;

	public void setMi202Dao(Mi202DAO mi202Dao) {
		this.mi202Dao = mi202Dao;
	}
	
	/**
	 * 区域信息新增
	 */
	public void webapi20201(CMi202 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getAreaCode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域代码："+form.getAreaCode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"区域代码");
		}
		if (CommonUtil.isEmpty(form.getAreaName())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域名称："+form.getAreaName()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"区域名称");
		}
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		// 校验新增区域代码是否已经存在
		Mi202Example example = new Mi202Example();
		example.createCriteria().andAreaCodeEqualTo(form.getAreaCode())
		.andCenteridEqualTo(form.getCenterid());
		int count = mi202Dao.countByExample(example);
		if (1 == count) {
			log.error(ERROR.ADD_CHECK.getLogText("中心城市编码："+form.getCenterid()+";区域代码："+form.getAreaCode()));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"区域代码："+form.getAreaCode());
		}
		
		Mi202 mi202 = new Mi202();
		// 区域ID
		String areaId = commonUtil.genKey("MI202", 0);
		if (CommonUtil.isEmpty(areaId)) {
			log.error(ERROR.NULL_KEY.getLogText("区域信息MI202"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("区域信息MI202"));
		}
		mi202.setAreaId(areaId);
		// 中心ID
		mi202.setCenterid(form.getCenterid());
		// 区域代码
		mi202.setAreaCode(form.getAreaCode());
		// 区域名称
		mi202.setAreaName(form.getAreaName());
		// 删除标记
		mi202.setValidflag(Constants.IS_VALIDFLAG);
		// 修改时间
		mi202.setDatemodified(CommonUtil.getSystemDate());
		// 创建时间
		mi202.setDatecreated(CommonUtil.getSystemDate());
		// 创建者
		mi202.setLoginid(form.getUserid());
		// freeuse1
		mi202.setFreeuse1(form.getFreeuse1());
		// freeuse2
		mi202.setFreeuse2(form.getFreeuse2());
		// freeuse3
		mi202.setFreeuse3(form.getFreeuse3());
		// freeuse4
		Mi202Example example1 = new Mi202Example();
		example1.createCriteria().andCenteridEqualTo(form.getCenterid());
		int count1 = mi202Dao.countByExample(example1);
		mi202.setFreeuse4(count1+1);

		mi202Dao.insert(mi202);
	}

	/**
	 * 区域信息删除
	 */
	public int webapi20202(CMi202 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getAreaId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域ID："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"区域ID获取失败");
		}

		String[] areaIds = form.getAreaId().split(",");
		List<String> areaidList = new ArrayList<String>();
		for (int i = 0; i < areaIds.length; i++) {
			areaidList.add(areaIds[i]);
		}
		
		Mi202 mi202 = new Mi202();
		mi202.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi202.setDatemodified(CommonUtil.getSystemDate());
		mi202.setLoginid(form.getUserid());
		
		Mi202Example example = new Mi202Example();
		example.createCriteria().andAreaIdIn(areaidList);
		
		int result = mi202Dao.updateByExampleSelective(mi202, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("区域ID:"+form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"区域信息MI202");
		}
		
		return result;
	}

	/**
	 * 区域信息修改
	 */
	public int webapi20203(CMi202 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getAreaId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域ID："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"区域ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getAreaCode())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域代码："+form.getAreaCode()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"区域代码");
		}
		if (CommonUtil.isEmpty(form.getAreaName())) {
			log.error(ERROR.PARAMS_NULL.getLogText("区域名称："+form.getAreaName()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"区域名称");
		}
		
		// 检索待更新数据是否存在
		int count = webapi20205(form);
		if (0 == count) {
			log.error(ERROR.NO_DATA.getLogText("区域信息MI202","区域ID="+form.getAreaId()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"区域信息MI202");
		}
		
		Mi202 mi202 = new Mi202();
		// 区域信息ID
		mi202.setAreaId(form.getAreaId());
		// 区域代码
		mi202.setAreaCode(form.getAreaCode());
		// 区域名称
		mi202.setAreaName(form.getAreaName());
		// 修改时间
		mi202.setDatemodified(CommonUtil.getSystemDate());
		// 修改者
		mi202.setLoginid(form.getUserid());
		
		int result = mi202Dao.updateByPrimaryKeySelective(mi202);
		if (0 == result) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("区域信息MI202","区域ID="+form.getAreaId()+";区域代码="+form.getAreaCode()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"区域代码="+form.getAreaCode());
		}
		return result;	
	}

	/**
	 * 区域信息查询
	 */
	@SuppressWarnings("unchecked")
	public List<Mi202> webapi20204(CMi202 form) throws Exception{
		
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码："+form.getCenterid()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		
		List<Mi202> list = new ArrayList<Mi202>();
		Mi202Example mi202Example = new Mi202Example();
		mi202Example.setOrderByClause("freeuse4 asc");
		mi202Example.createCriteria()
		.andCenteridEqualTo(form.getCenterid())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);	
//		mi202Example.setOrderByClause("AREA_CODE ASC");
		
		list = mi202Dao.selectByExample(mi202Example);
		
		if(CommonUtil.isEmpty(list)){
			log.error(ERROR.NO_DATA.getLogText("区域信息MI202", 
					mi202Example.createCriteria().getCriteriaWithSingleValue().toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"区域信息");
		}
		
		return list;
	}
	
	/**
	 * 区域信息记录查询
	 */
	public int webapi20205(CMi202 form)throws Exception {
		int count = 0;
		Mi202Example example = new Mi202Example();
		example.createCriteria().andAreaIdEqualTo(form.getAreaId());
		count = mi202Dao.countByExample(example);
		return count;
	}
	
	/**
	 * 区域信息顺序号修改
	 */
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void webapi20206(JSONArray arr) throws Exception {
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi202 mi202 = new Mi202();
			mi202.setAreaId((String)obj.getString("seqid"));
			mi202.setFreeuse4(Integer.parseInt(obj.getString("num")));
			mi202Dao.updateByPrimaryKeySelective(mi202);
		}
		
	}
}
