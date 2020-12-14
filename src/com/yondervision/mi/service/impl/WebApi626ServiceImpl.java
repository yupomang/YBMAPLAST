package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi626DAO;
import com.yondervision.mi.dao.Mi626DAO;
import com.yondervision.mi.dto.CMi626;
import com.yondervision.mi.dto.Mi626;
import com.yondervision.mi.dto.Mi626Example;
import com.yondervision.mi.result.WebApi62604_queryResult;
import com.yondervision.mi.service.WebApi626Service;
import com.yondervision.mi.util.CommonUtil;
/**
 * @ClassName WebApi626ServiceImpl
 * @Description 预约注意事项操作实现
 * @author Lihongjie
 * @date 2014-07-31 18:50
 */
public class WebApi626ServiceImpl implements WebApi626Service {
	protected final Logger log = LoggerUtil.getLogger();
	private Mi626DAO mi626Dao = null;
	private CMi626DAO cmi626Dao=null;
	
	public Mi626DAO getMi626Dao(){
		return mi626Dao;
	}
	
	public void setMi626Dao(Mi626DAO mi626Dao){
		this.mi626Dao=mi626Dao;
	}

	public CMi626DAO getCmi626Dao() {
		return cmi626Dao;
	}

	public void setCmi626Dao(CMi626DAO cmi626Dao) {
		this.cmi626Dao = cmi626Dao;
	}
	
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	/*
	 * 新增
	 */
	public void webapi62601(Mi626 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi626 mi626=new Mi626();
		mi626.setAppoattenid(commonUtil.genKey("MI626", 20));
		mi626.setTemplatename(form.getTemplatename());
		mi626.setCenterid(form.getCenterid());
		mi626.setLoginid(form.getLoginid());
		mi626.setValidflag(form.getValidflag());//0=禁用，1=启用
		mi626.setDatecreated(formatter.format(date));
		mi626.setDatemodified(formatter.format(date));
		mi626.setFreeuse1(form.getFreeuse1());
		mi626.setFreeuse2(form.getFreeuse2());
		mi626.setFreeuse3(form.getFreeuse3());
		mi626.setFreeuse4(form.getFreeuse4());
		mi626Dao.insert(mi626);
	}
	
	/*
	 * 删除
	 */
	public void webapi62602(Mi626 form) throws Exception {
		// TODO Auto-generated method stub
		String[] apidarr=form.getAppoattenid().split(",");
		for(int i=0;i<apidarr.length;i++){
			form.setAppoattenid(apidarr[i]);
			mi626Dao.deleteByPrimaryKey(form.getAppoattenid());
		}
	}

	/*
	 * 修改
	 */
	public int webapi62603(Mi626 form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi626 mi626=new Mi626();
		mi626.setAppoattenid(form.getAppoattenid());
		mi626.setDatemodified(formatter.format(date));
		mi626.setDatecreated(form.getDatecreated());
		mi626.setTemplatename(form.getTemplatename());
		mi626.setValidflag(form.getValidflag());
		mi626.setLoginid(form.getLoginid());
		mi626.setCenterid(form.getCenterid());
		mi626.setFreeuse1(form.getFreeuse1());
		mi626.setFreeuse2(form.getFreeuse2());
		mi626.setFreeuse3(form.getFreeuse3());
		mi626.setFreeuse4(form.getFreeuse4());
		return mi626Dao.updateByPrimaryKey(mi626);
	}

	/*
	 * 查询
	 */
	public WebApi62604_queryResult webapi62604(CMi626 form) throws Exception {
		// TODO Auto-generated method stub
//		Mi626Example mi626e=new Mi626Example();
//		mi626e.setOrderByClause("centerid desc, appoattenid asc, validflag desc");
//		UserContext user = UserContext.getInstance();
//		if(commonUtil.isEmpty(user.getCenterid())&&Constants.YD_ADMIN.equals(user.getCenterid())){
//			Mi626Example.Criteria ca=mi626e.createCriteria();
//			ca.andAppoattenidEqualTo(user.getCenterid());
//		}
		WebApi62604_queryResult list=cmi626Dao.selectWebPage(form);
		if (CommonUtil.isEmpty(list)) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约注意事项"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),
					"预约注意事项");
		}
		return list;
	}

}
