package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi622DAO;
import com.yondervision.mi.dao.Mi624DAO;
import com.yondervision.mi.dto.CMi622;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.dto.Mi622Example;
import com.yondervision.mi.dto.Mi624Example;
import com.yondervision.mi.service.WebApi622Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi622ServiceImpl 
* @Description: 预约业务类型维护实现
* @author sunxl
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi622ServiceImpl implements WebApi622Service {
	
	@Autowired
	public Mi622DAO mi622Dao = null;

	public Mi622DAO getMi622Dao() {
		return mi622Dao;
	}
	public Mi624DAO getMi624Dao() {
		return mi624Dao;
	}
	public void setMi622Dao(Mi622DAO mi622Dao) {
		this.mi622Dao = mi622Dao;
	}
	@Autowired
	public Mi624DAO mi624Dao = null;

	public void setMi624Dao(Mi624DAO mi624Dao) {
		this.mi624Dao = mi624Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	public void webapi62201(CMi622 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppotemplateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版编号网点");
		}
		/*if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段明细编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段明细编号");
		}*/
		if (CommonUtil.isEmpty(form.getTimeinterval())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段描述"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段描述网点");
		}
		Mi622Example m622e=new Mi622Example();
		com.yondervision.mi.dto.Mi622Example.Criteria ca= m622e.createCriteria();
		ca.andAppotemplateidEqualTo(form.getAppotemplateid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi622> mi622List = mi622Dao.selectByExample(m622e);
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi622 mi622 = new Mi622();
		mi622.setAppotemplateid(form.getAppotemplateid());
		mi622.setAppotpldetailid(commonUtil.genKey("MI622").toString());
		mi622.setTimeinterval(form.getTimeinterval());
		mi622.setDatecreated(formatter.format(date));
		mi622.setDatemodified(formatter.format(date));
		mi622.setFreeuse1(form.getFreeuse1());
		mi622.setFreeuse2(form.getFreeuse2());
		mi622.setFreeuse3(form.getFreeuse3());
		mi622.setFreeuse4(mi622List.size()+1);
		mi622.setValidflag(Constants.IS_VALIDFLAG);
		mi622.setLoginid(form.getUserid());
		mi622Dao.insert(mi622);
	}
	public int webapi62202(CMi622 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		// 传入参数非空校验
				if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段明细编号"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"时段明细编号获取失败");
				}
				
				String[] appotpldetails = form.getAppotpldetailid().split(",");
				List<String> apptpldetailList = new ArrayList<String>();
				for (int i = 0; i < appotpldetails.length; i++) {
					apptpldetailList.add(appotpldetails[i]);
				}
				Mi624Example mi624e = new Mi624Example();
				mi624e.createCriteria().andAppotpldetailidIn(apptpldetailList);
				
				List m624result = mi624Dao.selectByExample(mi624e);
				if(m624result.size()>0){
					log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约业务编号:"+form.getAppotpldetailid()));
					throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"删除时段信息前请删除预约网点中配置的时段信息");
				}
				Mi622 mi622 = new Mi622();
				// 修改时间
				mi622.setDatemodified(CommonUtil.getSystemDate());
				// 删除标记
				mi622.setValidflag(Constants.IS_NOT_VALIDFLAG);
				// 删除者
				mi622.setLoginid(form.getUserid());
				
				Mi622Example example = new Mi622Example();
				example.createCriteria().andAppotpldetailidIn(apptpldetailList);
				
				int result = mi622Dao.updateByExampleSelective(mi622, example);
				
				if (0 == result) {
					log.error(ERROR.ERRCODE_LOG_800004.getLogText("时段明细编号:"+form.getAppotpldetailid()));
					throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"时段明细MI622");
				}
				
				return result;
	}
	public int webapi62203(CMi622 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppotemplateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版编号网点");
		}
		if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段明细编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段明细编号网点");
		}
		if (CommonUtil.isEmpty(form.getTimeinterval())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段描述"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段描述网点");
		}
		// 检索待更新数据是否存在（主键）
		Mi622 mi622Tmp = webapi62205(form);
		if (CommonUtil.isEmpty(mi622Tmp)) {
			log.error(ERROR.NO_DATA.getLogText("时段模版MI622","时段模版编号："+form.getAppotemplateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"时段模版MI622");
		}

		Mi622 mi622 = new Mi622();
		// ID
		mi622.setAppotemplateid(form.getAppotemplateid());
		
		mi622.setAppotpldetailid(form.getAppotpldetailid());
		
		mi622.setTimeinterval(form.getTimeinterval());
		// 修改时间
		mi622.setDatemodified(CommonUtil.getSystemDate());
		// 修改者
		mi622.setLoginid(form.getUserid());
		
		int result = mi622Dao.updateByPrimaryKeySelective(mi622);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("时段明细mi622","时段明细ID："+form.getAppotemplateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"时段明细ID："+form.getAppotpldetailid());
		}
		return result;
	}
	/**
	 * 利率信息主键记录查询
	 */
	private Mi622 webapi62205(CMi622 form) throws Exception {
		Mi622 mi622 = new Mi622();
		mi622 = mi622Dao.selectByPrimaryKey(form.getAppotpldetailid());
		return mi622;
	}
	public JSONObject webapi62204(CMi622 form) {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi622Service webapi62204 "));
		JSONObject obj = new JSONObject();
		Mi622Example mi622e = new Mi622Example();
		mi622e.setOrderByClause("freeuse4");
		com.yondervision.mi.dto.Mi622Example.Criteria ca = mi622e.createCriteria();
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG).andAppotemplateidEqualTo(form.getAppotemplateid());
		List<Mi622> list = mi622Dao.selectByExample(mi622e);
		obj.put("rows", list);
		log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
		log.info(LOG.SELF_LOG.getLogText("[+]webApi622Service webapi62204"));
	    return obj;
	}
	public List<Mi622> getBussTemplaDetail(String template){
		Mi622Example example = new Mi622Example();
		example.setOrderByClause("freeuse4");
		com.yondervision.mi.dto.Mi622Example.Criteria ca = example.createCriteria();
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		if(template!=null){
			ca.andAppotemplateidEqualTo(template);
		}
		List<Mi622> list = mi622Dao.selectByExample(example);
		return list;
	}
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public int webapiUpdateSort(JSONArray arr){
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi622 mi622 = new Mi622();
			mi622.setAppotpldetailid(obj.getString("appotpldetailid"));
			mi622.setFreeuse4(Integer.parseInt(obj.getString("freeuse4")));
			int result = mi622Dao.updateByPrimaryKeySelective(mi622);
		}
		return 1;
	} 
}
