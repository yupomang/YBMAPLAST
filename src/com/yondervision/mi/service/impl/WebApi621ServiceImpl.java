package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi621DAO;
import com.yondervision.mi.dao.Mi622DAO;
import com.yondervision.mi.dto.CMi621;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi620Example;
import com.yondervision.mi.dto.Mi621;
import com.yondervision.mi.dto.Mi621Example;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.dto.Mi622Example;
import com.yondervision.mi.service.WebApi621Service;
import com.yondervision.mi.util.CommonUtil;

/**
 * @ClassName: WebApi621ServiceImpl
 * @Description: 时段模版维护实现
 * @author sunxl
 * @date Sep 29, 2013 2:56:45 PM
 * 
 */
public class WebApi621ServiceImpl implements WebApi621Service {

	@Autowired
	public Mi621DAO mi621Dao = null;
	@Autowired
	public Mi622DAO mi622Dao = null;

	public void setMi621Dao(Mi621DAO mi621Dao) {
		this.mi621Dao = mi621Dao;
	}

	public void setMi622Dao(Mi622DAO mi622Dao) {
		this.mi622Dao = mi622Dao;
	}

	@Autowired
	private CommonUtil commonUtil = null;

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public void webapi62101(CMi621 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					"中心城市编码获取失败");
		}
		/*if (CommonUtil.isEmpty(form.getAppotemplateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "时段模版编号");
		}*/
		if (CommonUtil.isEmpty(form.getTemplatename())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "时段模版名称");
		}
		/*Mi621Example m621e = new Mi621Example();
		com.yondervision.mi.dto.Mi621Example.Criteria ca = m621e
				.createCriteria();
		ca.andAppotemplateidEqualTo(form.getAppotemplateid());
		List<Mi621> mi621List = mi621Dao.selectByExample(m621e);
		if (mi621List.size() > 0) {
			throw new TransRuntimeErrorException(
					WEB_ALERT.DATA_CHECK_INSERT.getValue(),
					"时段模版编号己存在，违反唯一索引约束");
		}*/
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi621 mi621 = new Mi621();
		mi621.setAppotemplateid(commonUtil.genKey("MI621").toString());
		mi621.setTemplatename(form.getTemplatename());
		mi621.setCenterid(form.getCenterId());
		mi621.setDatecreated(formatter.format(date));
		mi621.setDatemodified(formatter.format(date));
		mi621.setFreeuse1(form.getFreeuse1());
		mi621.setFreeuse2(form.getFreeuse2());
		mi621.setFreeuse3(form.getFreeuse3());
		mi621.setFreeuse4(form.getFreeuse4());
		mi621.setValidflag(Constants.IS_VALIDFLAG);
		mi621.setLoginid(form.getUserid());
		mi621Dao.insert(mi621);
	}
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public int webapi62102(CMi621 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getAppotemplateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"时段模版编号获取失败");
		}

		String[] templateids = form.getAppotemplateid().split(",");
		List<String> templateList = new ArrayList<String>();
		for (int i = 0; i < templateids.length; i++) {
			templateList.add(templateids[i]);
		}

		Mi621 mi621 = new Mi621();
		// 修改时间
		mi621.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi621.setValidflag(Constants.IS_NOT_VALIDFLAG);
		// 删除者
		mi621.setLoginid(form.getUserid());

		Mi621Example example = new Mi621Example();
		example.createCriteria().andAppotemplateidIn(templateList);

		int result = mi621Dao.updateByExampleSelective(mi621, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("时段模版编号:"+ form.getAppotemplateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "时段模版MI621");
		}
		
		Mi622Example mi622e = new Mi622Example();
		com.yondervision.mi.dto.Mi622Example.Criteria ca = mi622e
				.createCriteria();
		ca.andAppotemplateidEqualTo(form.getAppotemplateid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi622> mi622List = mi622Dao.selectByExample(mi622e);
		if(mi622List.size()!=0){
			Mi622 mi622 = new Mi622();
			mi622.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi622.setLoginid(form.getUserid());
			mi622.setDatemodified(CommonUtil.getSystemDate());
			
			result = mi622Dao.updateByExampleSelective(mi622, mi622e);
			
			if (0 == result) {
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("时段模版编号:"+ form.getAppotemplateid()));
				throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "时段明细MI622");
			}
		}
		return result;
	}

	public int webapi62103(CMi621 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();

		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppotemplateid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "时段模版编号");
		}
		if (CommonUtil.isEmpty(form.getTemplatename())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段模版名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "时段模版名称");
		}
		// 检索待更新数据是否存在（主键）
		Mi621 mi621Tmp = webapi62105(form);
		if (CommonUtil.isEmpty(mi621Tmp)) {
			log.error(ERROR.NO_DATA.getLogText("时段模版MI621","时段模版编号：" + form.getAppotemplateid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "时段模版MI621");
		}

		Mi621 mi621 = new Mi621();
		// ID
		mi621.setAppotemplateid(form.getAppotemplateid());

		mi621.setTemplatename(form.getTemplatename());

		mi621.setCenterid(form.getCenterId());
		// 修改时间
		mi621.setDatemodified(CommonUtil.getSystemDate());
		// 修改者
		mi621.setLoginid(form.getUserid());

		int result = mi621Dao.updateByPrimaryKeySelective(mi621);
		if (0 == result) {
			log.error(ERROR.UPDATE_NO_DATA.getLogText("时段模版mi621", "时段模版ID："
					+ form.getAppotemplateid()));
			throw new TransRuntimeErrorException(
					WEB_ALERT.UPD_ERROR.getValue(), "时段模版ID："
							+ form.getAppotemplateid());
		}
		return result;
	}

	/**
	 * 信息主键记录查询
	 */
	private Mi621 webapi62105(CMi621 form) throws Exception {
		Mi621 mi621 = new Mi621();
		mi621 = mi621Dao.selectByPrimaryKey(form.getAppotemplateid());
		return mi621;
	}

	public JSONObject webapi62104(CMi621 form) {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		String sql = null;
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		if(Constants.YD_ADMIN.equals(centerid)){
			sql = "select * from mi621 where validflag = '" + Constants.IS_VALIDFLAG + "'";
		}else{
			sql = "select * from mi621 where validflag = '" + Constants.IS_VALIDFLAG + "' and centerid = '" + centerid + "'";
		}
		JSONObject obj = commonUtil.selectByPage(sql, new Integer(form.getPage()), new Integer(form.getRows()));
		return obj;
	}
}
