package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.yondervision.mi.dao.Mi620DAO;
import com.yondervision.mi.dao.Mi623DAO;
import com.yondervision.mi.dto.CMi620;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi620Example;
import com.yondervision.mi.dto.Mi623Example;
import com.yondervision.mi.service.WebApi620Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi620ServiceImpl 
* @Description: 预约业务类型维护实现
* @author sunxl
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi620ServiceImpl implements WebApi620Service {
	
	@Autowired
	public Mi620DAO mi620Dao = null;

	public void setMi620Dao(Mi620DAO mi620Dao) {
		this.mi620Dao = mi620Dao;
	}
	@Autowired
	public Mi623DAO mi623Dao = null;

	public void setMi623Dao(Mi623DAO mi623Dao) {
		this.mi623Dao = mi623Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	public void webapi62001(CMi620 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		// 传入参数空值校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		/*if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预约业务编号");
		}*/
		if (CommonUtil.isEmpty(form.getAppobusiname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预约业务名称");
		}
		/*Mi620Example m620e=new Mi620Example();
		com.yondervision.mi.dto.Mi620Example.Criteria ca= m620e.createCriteria();
		ca.andAppobusiidEqualTo(form.getAppobusiid());
		List<Mi620> mi620List = mi620Dao.selectByExample(m620e);
		if(mi620List.size()>0){
			throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"预约业务编号己存在，违反唯一索引约束");
		}*/
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi620 mi620 = new Mi620();
		String appobusiid = "000".substring(commonUtil.genKey("MI620").toString().length())+commonUtil.genKey("MI620").toString();
		mi620.setAppobusiid(appobusiid);
		mi620.setAppobusiname(form.getAppobusiname());
		mi620.setCenterid(form.getCenterId());
		mi620.setDatecreated(formatter.format(date));
		mi620.setDatemodified(formatter.format(date));
		mi620.setFreeuse1(form.getFreeuse1());
		mi620.setFreeuse2(form.getFreeuse2());
		mi620.setFreeuse3(form.getFreeuse3());
		mi620.setFreeuse4(form.getFreeuse4());
		mi620.setValidflag(Constants.IS_VALIDFLAG);
		mi620.setLoginid(form.getUserid());
		mi620Dao.insert(mi620);
	}
	public int webapi62002(CMi620 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务编号获取失败");
		}
		
		String[] appobusiids = form.getAppobusiid().split(",");
		List<String> appobusiList = new ArrayList<String>();
		for (int i = 0; i < appobusiids.length; i++) {
			appobusiList.add(appobusiids[i]);
		}
		Mi623Example mi623e = new Mi623Example();
		mi623e.createCriteria().andAppobusiidIn(appobusiList);
		
		List m623result = mi623Dao.selectByExample(mi623e);
		if(m623result.size()>0){
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约业务编号:"+form.getAppobusiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"删除预约业务前请删除预约网点中为其配置的时段信息");
		}
		Mi620 mi620 = new Mi620();
		// 修改时间
		mi620.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi620.setValidflag(Constants.IS_NOT_VALIDFLAG);
		// 删除者
		mi620.setLoginid(form.getUserid());
		
		Mi620Example example = new Mi620Example();
		example.createCriteria().andAppobusiidIn(appobusiList);
		
		int result = mi620Dao.updateByExampleSelective(mi620, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约业务编号:"+form.getAppobusiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"预约业务类型MI620");
		}
		
		return result;
	}
	public int webapi62003(CMi620 form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		
		// 传入参数非空校验
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预约业务编号");
		}
		if (CommonUtil.isEmpty(form.getAppobusiname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"预约业务名称");
		}
		// 检索待更新数据是否存在（主键）
		Mi620 mi620Tmp = webapi62005(form);
		if (CommonUtil.isEmpty(mi620Tmp)) {
			log.error(ERROR.NO_DATA.getLogText("预约业务MI620","预约业务编号："+form.getAppobusiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"预约业务MI620");
		}

		Mi620 mi620 = new Mi620();
		// 利率ID
		mi620.setAppobusiid(form.getAppobusiid());
		// 利率类型
		mi620.setAppobusiname(form.getAppobusiname());
		// 月数期限
		mi620.setCenterid(form.getCenterId());
		// 修改时间
		mi620.setDatemodified(CommonUtil.getSystemDate());
		// 修改者
		mi620.setLoginid(form.getUserid());
		
		int result = mi620Dao.updateByPrimaryKeySelective(mi620);
		if (0 == result){
			log.error(ERROR.UPDATE_NO_DATA.getLogText("预约业务类型MI620","预约业务ID："+form.getAppobusiid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
					"预约业务ID："+form.getAppobusiid()+"; 预约业务名称："
					+form.getAppobusiname());
		}
		return result;
	}
	/**
	 * 信息主键记录查询
	 */
	private Mi620 webapi62005(CMi620 form) throws Exception {
		Mi620 mi620 = new Mi620();
		mi620 = mi620Dao.selectByPrimaryKey(form.getAppobusiid());
		return mi620;
	}
	public JSONObject webapi62004(CMi620 form, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi620Service webapi62004 page="+page+",rows="+rows));

		String sql = null;
		if (Constants.YD_ADMIN.equals(form.getCenterId())){
			sql="select * from mi620 where validflag = '"+Constants.IS_VALIDFLAG +"'";
		}else{
			sql="select * from mi620 where centerid = '"+form.getCenterId()+"' and validflag = '"+Constants.IS_VALIDFLAG +"'";	
		}
		sql += " order by DATECREATED asc";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
		log.info(LOG.SELF_LOG.getLogText("[+]webApi620Service webapi62004 page="+page+",rows="+rows));
		return obj;
	}
	
}
