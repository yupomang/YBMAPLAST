package com.yondervision.mi.service.impl;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi709DAO;
import com.yondervision.mi.dto.Mi709;
import com.yondervision.mi.dto.Mi709Example;
import com.yondervision.mi.service.WebApi709Service;
import com.yondervision.mi.util.CommonUtil;

import net.sf.json.JSONObject;

public class WebApi709ServiceImpl implements WebApi709Service {

	@Autowired
	private Mi709DAO mi709Dao;
	@Autowired
	private CommonUtil commonUtil;

	/**
	 * 栏目与服务新增
	 */
	public void webapi70901(Mi709 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
			
		}
		if(CommonUtil.isEmpty(form.getItemid())){
			log.error(ERROR.PARAMS_NULL.getLogText("栏目编码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "栏目编码为空");
			
		}
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "服务为空");
			
		}
		Mi709Example example = new Mi709Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andItemidEqualTo(form.getItemid())
		.andServiceidEqualTo(form.getServiceid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = mi709Dao.countByExample(example);
		if(count!=0){
			log.error(ERROR.ADD_CHECK.getLogText("违反唯一约束：中心+栏目编码+业务类型"));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "违反唯一约束：中心+栏目编码+业务类型");
		}
		
		//新增
		String seqno = commonUtil.genKey("MI709", 6).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(seqno)) {
			log.error(ERROR.NULL_KEY.getLogText("栏目与服务MI709"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("栏目与服务MI709"));
		}
		form.setSeqno(seqno);
		form.setValidflag(Constants.IS_VALIDFLAG);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		
		mi709Dao.insert(form);
		
	}
	
	/**
	 * 栏目与服务删除
	 */
	public void webapi70902(Mi709 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String seqno = form.getSeqno();
		if(CommonUtil.isEmpty(seqno)){
			log.error(ERROR.PARAMS_NULL.getLogText("主键seqno为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键seqno为空");
		}
		String[] split = seqno.split(",");
		
		Mi709Example example = new Mi709Example();
		example.createCriteria().andSeqnoIn(Arrays.asList(split)).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi709Dao.deleteByExample(example);
	}
	
	/**
	 * 栏目与服务修改
	 */
	public void webapi70903(Mi709 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getSeqno())){
			log.error(ERROR.PARAMS_NULL.getLogText("主键为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "主键为空");
			
		}
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
			
		}
		if(CommonUtil.isEmpty(form.getItemid())){
			log.error(ERROR.PARAMS_NULL.getLogText("栏目编码为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "栏目编码为空");
			
		}
		if(CommonUtil.isEmpty(form.getServiceid())){
			log.error(ERROR.PARAMS_NULL.getLogText("服务为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "服务为空");
			
		}
		Mi709Example example = new Mi709Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andItemidEqualTo(form.getItemid())
		.andServiceidEqualTo(form.getServiceid()).andSeqnoNotEqualTo(form.getSeqno()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int count = mi709Dao.countByExample(example);
		if(count!=0){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("违反唯一约束：中心+栏目编码+业务类型"));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "违反唯一约束：中心+栏目编码+业务类型");
		}
		//更新
		form.setDatemodified(CommonUtil.getSystemDate());
		mi709Dao.updateByPrimaryKeySelective(form);
	}
	
	/**
	 * 栏目与服务查询分页
	 */
	public JSONObject webapi70904(Mi709 form,Integer page,Integer rows) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(rows)){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
			
		}
		if(CommonUtil.isEmpty(page)){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
			
		}
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		String sql = "select * from mi709 where centerid='"+centerid+"' and validflag='"+Constants.IS_VALIDFLAG+"'";
		if(!CommonUtil.isEmpty(form.getItemid())){
			sql += " and itemid = '"+form.getItemid()+"'";
		}
		if(!CommonUtil.isEmpty(form.getItemval())){
			sql += " and itemval like '%"+form.getItemval()+"%'";
		}
		if(!CommonUtil.isEmpty(form.getServiceid())){
			sql += " and serviceid = '"+form.getServiceid()+"'";
		}
		if(!CommonUtil.isEmpty(form.getServicename())){
			sql += " and servicename like '%"+form.getServicename()+"%'";
		}
		JSONObject obj = CommonUtil.selectByPage(sql, page, rows);
		return obj;
	}


	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public Mi709DAO getMi709Dao() {
		return mi709Dao;
	}

	public void setMi709Dao(Mi709DAO mi709Dao) {
		this.mi709Dao = mi709Dao;
	}
}

