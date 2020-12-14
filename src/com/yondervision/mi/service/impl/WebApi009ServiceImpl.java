package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi109DAO;
import com.yondervision.mi.dto.CMi109;
import com.yondervision.mi.dto.Mi109;
import com.yondervision.mi.dto.Mi109Example;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.service.WebApi009Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

/** 
* @ClassName: WebApi008ServiceImpl 
* @Description: 利率维护处理实现
* @author gongq
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi009ServiceImpl implements WebApi009Service {
	
	public Mi109DAO mi109Dao = null;

	public void setMi109Dao(Mi109DAO mi109Dao) {
		this.mi109Dao = mi109Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	/**
	 * 利率新增
	 */
	public void webapi00901(CMi109 form) throws Exception{
		
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getRatetype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("利率类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"利率类型");
		}
		if (CommonUtil.isEmpty(form.getTerms())) {
			log.error(ERROR.PARAMS_NULL.getLogText("月数期限"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"月数期限");
		}
		if (CommonUtil.isEmpty(form.getRate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("基准利率"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"基准利率");
		}
		if (CommonUtil.isEmpty(form.getEffective_date())) {
			log.error(ERROR.PARAMS_NULL.getLogText("生效日期"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"生效日期");
		}
		
		// 校验新增信息是否已经存在（唯一索引）
		Mi109Example example = new Mi109Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andRatetypeEqualTo(form.getRatetype())
		.andTermsEqualTo(form.getTerms())
		.andEffectiveDateEqualTo(form.getEffective_date());
		int count = mi109Dao.countByExample(example);
		if (0 != count) {
			log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"此记录已存在，请确认后提交！");
		}

		// 校验利率ID的采号
		String rateId = commonUtil.genKey("MI109").toString();
		if (CommonUtil.isEmpty(rateId)) {
			log.error(ERROR.NULL_KEY.getLogText("利率信息信息MI109"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.NULL_KEY.getLogText("利率信息信息MI109"));
		}
		
		Mi109 mi109 = new Mi109();
		// 利率ID
		mi109.setRateid(rateId);
		// 中心ID
		mi109.setCenterid(form.getCenterId());
		// 利率类型
		mi109.setRatetype(form.getRatetype());
		// 月数期限
		mi109.setTerms(form.getTerms());
		// 基准利率
		mi109.setRate(form.getRate());
		// 生效日期
		mi109.setEffectiveDate(form.getEffective_date());
		// 删除标记
		mi109.setValidflag(Constants.IS_VALIDFLAG);
		// 修改时间
		mi109.setDatemodified(CommonUtil.getSystemDate());
		// 创建时间
		mi109.setDatecreated(CommonUtil.getSystemDate());
		// 创建者
		mi109.setLoginid(form.getUserid());
		// freeuse1
		mi109.setFreeuse1(form.getFreeuse1());
		// freeuse2
		mi109.setFreeuse2(form.getFreeuse2());
		// freeuse3
		mi109.setFreeuse3(form.getFreeuse3());
		// freeuse4
		mi109.setFreeuse4(form.getFreeuse4());
		// loginid
		mi109.setLoginid(form.getUserid());
		
		mi109Dao.insert(mi109);

	}

	/**
	 * 利率删除
	 */
	public int webapi00902(CMi109 form) throws Exception {

		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getRateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("利率ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"利率ID获取失败");
		}
		
		String[] rateIds = form.getRateid().split(",");
		List<String> rateIdList = new ArrayList<String>();
		for (int i = 0; i < rateIds.length; i++) {
			rateIdList.add(rateIds[i]);
		}
		
		Mi109 mi109 = new Mi109();
		// 修改时间
		mi109.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi109.setValidflag(Constants.IS_NOT_VALIDFLAG);
		// 删除者
		mi109.setLoginid(form.getUserid());
		
		Mi109Example example = new Mi109Example();
		example.createCriteria().andRateidIn(rateIdList);
		
		int result = mi109Dao.updateByExampleSelective(mi109, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("利率ID:"+form.getRateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"利率信息MI109");
		}
		
		return result;
	}

	/**
	 * 利率修改
	 */
	public int webapi00903(CMi109 form) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getRatetype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("利率类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"利率类型");
		}
		if (CommonUtil.isEmpty(form.getTerms())) {
			log.error(ERROR.PARAMS_NULL.getLogText("月数期限"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"月数期限");
		}
		if (CommonUtil.isEmpty(form.getRate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("基准利率"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"基准利率");
		}
		if (CommonUtil.isEmpty(form.getEffective_date())) {
			log.error(ERROR.PARAMS_NULL.getLogText("生效日期"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"生效日期");
		}
		if (CommonUtil.isEmpty(form.getOldratetype())) {
			log.error(ERROR.PARAMS_NULL.getLogText("修改前利率类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"修改前利率类型");
		}
		if (CommonUtil.isEmpty(form.getOldterms())) {
			log.error(ERROR.PARAMS_NULL.getLogText("修改前月数期限"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"修改前月数期限");
		}
		if (CommonUtil.isEmpty(form.getOldeffectiveDate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("修改前生效日期："+form.getOldeffectiveDate()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"修改前生效日期");
		}

		// 检索待更新数据是否存在（主键）
		Mi109 mi109Tmp = webapi00905(form);
		if (CommonUtil.isEmpty(mi109Tmp)) {
			log.error(ERROR.NO_DATA.getLogText("利率信息MI109","利率ID："+form.getRateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"利率信息MI109");
		}
		
		if (!form.getRatetype().equals(form.getOldratetype())
				|| !form.getTerms().toString().equals(form.getOldterms())
				|| !form.getEffective_date().equals(form.getOldeffectiveDate())) {
			// 校验当前修改记录是否在库中已存在（唯一索引）
			int indexCount = webapi00906(form, mi109Tmp.getCenterid());
			if (0 != indexCount) {
				log.error(ERROR.ADD_CHECK.getLogText(CommonUtil.getStringParams(form)));
				//获取利率类型的汉化
				CodeListApi001Service codeListApi001Service = (CodeListApi001Service)SpringContextUtil.getBean("codeListApi001Service");
				String ratetypeVal = codeListApi001Service.getCodeVal(form.getCenterId(), "ratetype."+form.getRatetype());
				throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(),"利率类型:"
						+ratetypeVal+"; 月数期限："+form.getTerms()+"期; 生效日期："+form.getEffective_date());
			}
		}

		Mi109 mi109 = new Mi109();
		// 利率ID
		mi109.setRateid(form.getRateid());
		// 利率类型
		mi109.setRatetype(form.getRatetype());
		// 月数期限
		mi109.setTerms(form.getTerms());
		// 基准利率
		mi109.setRate(form.getRate());
		// 生效日期
		mi109.setEffectiveDate(form.getEffective_date());
		// 修改时间
		mi109.setDatemodified(CommonUtil.getSystemDate());
		// 修改者
		mi109.setLoginid(form.getUserid());
		
		int result = mi109Dao.updateByPrimaryKeySelective(mi109);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("利率信息MI109","利率ID："+form.getRateid()));
			//获取利率类型的汉化
			CodeListApi001Service codeListApi001Service = (CodeListApi001Service)SpringContextUtil.getBean("codeListApi001Service");
			String ratetypeVal = codeListApi001Service.getCodeVal(form.getCenterId(), "ratetype."+form.getRatetype());
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"利率类型："+ratetypeVal+"; 月数期限："
					+form.getTerms()+"期; 基准利率："+form.getRate()+"; 生效日期："+form.getEffective_date());
		}
		return result;
	}
	
	/**
	 * 利率查询分页
	 */
	public JSONObject webapi00904(CMi109 form, Integer page, Integer rows) {
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi009Service webapi00904 page="+page+",rows="+rows));

		String sql = null;
		if (Constants.YD_ADMIN.equals(form.getCenterId())){
			sql="select * from mi109 where validflag = '"+Constants.IS_VALIDFLAG +"'";
		}else{
			sql="select * from mi109 where centerid = '"+form.getCenterId()+"' and validflag = '"+Constants.IS_VALIDFLAG +"'";	
		}
	
		if(!CommonUtil.isEmpty(form.getRatetypeQry()))
			sql+=" and ratetype = '"+form.getRatetypeQry()+"'";
		sql += " order by DATECREATED asc";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
		log.info(LOG.SELF_LOG.getLogText("[+]webApi009Service webapi00904 page="+page+",rows="+rows));
		return obj;
	}
	
	/**
	 * 利率信息主键记录查询
	 */
	private Mi109 webapi00905(CMi109 form) throws Exception {
		Mi109 mi109 = new Mi109();
		mi109 = mi109Dao.selectByPrimaryKey(form.getRateid());
		return mi109;
	}
	
	/**
	 * 利率信息唯一索引记录查询
	 */
	private int webapi00906(CMi109 form, String centerid) throws Exception {
		int count = 0;
		Mi109Example example = new Mi109Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andRatetypeEqualTo(form.getRatetype())
		.andTermsEqualTo(form.getTerms())
		.andEffectiveDateEqualTo(form.getEffective_date());
		
		count = mi109Dao.countByExample(example);
		return count;
	}
}
