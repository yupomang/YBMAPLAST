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
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dao.Mi035DAO;
import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.dto.Mi035;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi04004_queryResult;
import com.yondervision.mi.service.WebApi040Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.security.Token;

public class WebApi040ServiceImpl implements WebApi040Service {

	@Autowired
	private CMi040DAO cmi040DAO;
	@Autowired
	private Mi035DAO mi035DAO;
	@Autowired
	private CommonUtil commonUtil;

	public void webapi04001(CMi040 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if (CommonUtil.isEmpty(form.getChannel())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道编码为空");
		}
		if (CommonUtil.isEmpty(form.getAppname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		
		if("1".equals(form.getStartup())){//1表示启用，则时间不可以为空
			if (CommonUtil.isEmpty(form.getEffectivedaytart())) {
				log.error(ERROR.PARAMS_NULL.getLogText("服务开始日期"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务开始日期为空");
			}
			if (CommonUtil.isEmpty(form.getEffectivedayend())) {
				log.error(ERROR.PARAMS_NULL.getLogText("服务结束日期"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务结束日期为空");
			}
		}else{
			form.setEffectivedaytart("");
			form.setEffectivedayend("");
		}
		
		// 采号
		String pid = commonUtil.genKey("MI040", 6).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.NULL_KEY.getLogText("应用信息MI040"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("应用信息MI040"));
		}
		
		Mi040 mi040 = new Mi040();
		// ID
		mi040.setPid(form.getChannel()+pid);// 采号生成，前补0，总长度20
		mi040.setCenterid(form.getCenterid());
		mi040.setChannel(form.getChannel());
		mi040.setAppname(form.getAppname());
		mi040.setStartup(form.getStartup());
		mi040.setEffectivedaytart(form.getEffectivedaytart());
		mi040.setEffectivedayend(form.getEffectivedayend());
		mi040.setAuthentication("0");
		mi040.setValidflag("1");
		mi040.setDatemodified(CommonUtil.getSystemDate());
		mi040.setDatecreated(CommonUtil.getSystemDate());
		cmi040DAO.insert(mi040);

	}

	public int webapi04002(CMi040 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String pid = form.getPid();
		if (CommonUtil.isEmpty(pid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String[] Ids = pid.split(",");
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < Ids.length; i++) {
			idList.add(Ids[i]);
		}

		Mi040 mi040 = new Mi040();
		// 修改时间
		mi040.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi040.setValidflag(Constants.IS_NOT_VALIDFLAG);
		Mi040Example example = new Mi040Example();
		example.createCriteria().andPidIn(idList);

		int result = cmi040DAO.updateByExampleSelective(mi040, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("应用MI040:" + form.getAppid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "应用MI040");
		}
		return result;
	}

	/**
	 * 应用修改
	 */
	public int webapi04003(CMi040 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "pid：" + form.getAppid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "PID不能为空");
		}
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "centerid：" + form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "centerid不能为空");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "channel：" + form.getChannel()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "channel不能为空");
		}
		if(CommonUtil.isEmpty(form.getAppname())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "appname：" + form.getAppname()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "appname不能为空");
		}
		if(CommonUtil.isEmpty(form.getStartup())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "startup：" + form.getStartup()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "startup不能为空");
		}
		if("1".equals(form.getStartup())){//1表示启用，则时间不可以为空
			if (CommonUtil.isEmpty(form.getEffectivedaytart())) {
				log.error(ERROR.PARAMS_NULL.getLogText("服务开始日期"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务开始日期为空");
			}
			if (CommonUtil.isEmpty(form.getEffectivedayend())) {
				log.error(ERROR.PARAMS_NULL.getLogText("服务结束日期"));
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "服务结束日期为空");
			}
		}else{
			form.setEffectivedaytart("");
			form.setEffectivedayend("");
		}
		// 检索待更新数据是否存在（主键）
		Mi040 mi040Tmp = cmi040DAO.selectByPrimaryKey(form.getPid());
		if (CommonUtil.isEmpty(mi040Tmp)) {
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "pid：" + form.getPid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "应用信息MI040");
		}
		
		mi040Tmp.setAppname(form.getAppname());
		mi040Tmp.setCenterid(form.getCenterid());
		mi040Tmp.setChannel(form.getChannel());
		mi040Tmp.setStartup(form.getStartup());
		mi040Tmp.setEffectivedaytart(form.getEffectivedaytart());
		mi040Tmp.setEffectivedayend(form.getEffectivedayend());
		
		if("1".equals(form.getStartup())){
			if(!mi040Tmp.getEffectivedaytart().trim().equals(form.getEffectivedaytart())&&!mi040Tmp.getEffectivedayend().trim().equals(form.getEffectivedayend())){
				Mi035 mi035 = new Mi035();
				String id = commonUtil.genKey("MI040", 20).toString();// 采号生成，前补0，总长度20
				if(CommonUtil.isEmpty(id)){
					log.error(ERROR.NULL_KEY.getLogText("应用信息MI040"));
					throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("应用信息MI040"));
				}
				mi035.setEffectivedayend(form.getEffectivedayend());
				mi035.setEffectivedaytart(form.getEffectivedaytart());
				mi035.setDatecreated(CommonUtil.getSystemDate());
				mi035.setDatemodified(CommonUtil.getSystemDate());
				mi035DAO.insert(mi035);
			}
		}
		
		int result = cmi040DAO.updateByPrimaryKeySelective(mi040Tmp);
		
		return result;
	}

	public WebApi04004_queryResult webapi04004(CMi040 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "应用信息MI040");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "应用信息MI040");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "应用信息MI040");
		}
		WebApi04004_queryResult result = cmi040DAO.select040Page(form);
		return result;
	}


	/**
	 * 认证设置（新增修改）
	 * @return 
	 */
	@Transactional
	public int webapi04005(CMi040 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		//参数校验
		//...
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "pid：" + form.getAppid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "PID不能为空");
		}
		if(CommonUtil.isEmpty(form.getAppid())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "appid：" + form.getAppid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "appid不能为空");
		}
		if(CommonUtil.isEmpty(form.getAppkey())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "appkey：" + form.getAppkey()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "appkey不能为空");
		}
		if(CommonUtil.isEmpty(form.getSecretkey())){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "secretkey：" + form.getSecretkey()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "secretkey不能为空");
		}
		String validtime5 = form.getValidtime();
		String validtime4 = validtime5;
		String validtime3 = validtime4;
		String validtime2 = validtime3;
		String validtime = validtime2;
		if("1".equals(form.getChecktoken())){//启用token
			if(CommonUtil.isEmpty(validtime)){
				log.error(ERROR.PARAMS_NULL.getLogText("启用token，则有效时间不许为空"));
				throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "启用token，则有效时间不许为空");
			}
		}else{
			form.setValidtime("");
		}
		Mi040 mi040 = cmi040DAO.selectByPrimaryKey(form.getPid());
		if(mi040==null){
			log.error(ERROR.NO_DATA.getLogText("应用MI040", "appid：" + form.getPid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(), "应用信息MI040");
		}
//		if("1".equals(mi040.getAuthentication())){
//			log.error("该应用已经认证过了");
//			throw new RuntimeException("该应用已经认证过了");
//		}
		mi040.setAuthentication("1");
		mi040.setAppid(form.getAppid());
		mi040.setAppkey(form.getAppkey());
		mi040.setChecktoken(form.getChecktoken());
		mi040.setSecretkey(form.getSecretkey());
		mi040.setDatemodified(CommonUtil.getSystemDate());
		mi040.setEffectivedaytart(form.getEffectivedaytart());
		mi040.setEffectivedayend(form.getEffectivedayend());
		mi040.setValidtime(validtime);
		
		CouchBase cb=CouchBase.getInstance();
		String key = mi040.getCenterid()+"|"+mi040.getChannel()+"|"+mi040.getAppid()+"|"+mi040.getAppkey();
		if("1".equals(form.getChecktoken())){
			String oldValidtime = mi040.getValidtime();
			String newValidTime = form.getValidtime();
			if(!newValidTime.equals(oldValidtime)){
				cb.delete(key);
				cb.save(key, Token.newAccessToken(),Integer.parseInt(validtime));
			}
		}else{
			cb.delete(key);
		}
		
		
		
		
		int result = cmi040DAO.updateByPrimaryKeySelective(mi040);
		return result;
	}

	
	
	public Mi040 webapi04006(AppApiCommonForm form) throws Exception {
		Mi040Example mi040Example = new Mi040Example();
		Mi040Example.Criteria ca = mi040Example.createCriteria();
		ca.andAppidEqualTo(form.getAppid()).andAppkeyEqualTo(form.getAppkey());
		ca.andChannelEqualTo(form.getChannel()).andCenteridEqualTo(form.getCenterId());
		List<Mi040> list = cmi040DAO.selectByExample(mi040Example);
		Mi040 mi040 = null;
		if(!CommonUtil.isEmpty(list)){
			mi040 = list.get(0);
			CouchBase cb=CouchBase.getInstance();
			String key = mi040.getCenterid()+"|"+mi040.getChannel()+"|"+mi040.getAppid()+"|"+mi040.getAppkey();
			String apptoken = Token.newAccessToken();
			cb.save(key, apptoken,Integer.parseInt(mi040.getValidtime()));
			mi040.setApptoken(apptoken);
		}
		return mi040;
	}

	/**
	 * 获取应用列表
	 * @param form
	 * @return
	 */	
	public List<Mi040> webapi04007(CMi040 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "中心为空");
		}
		
		Mi040Example mi040Example = new Mi040Example();
		mi040Example.setOrderByClause("centerid asc,channel asc");
		Mi040Example.Criteria ca = mi040Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getChannel())){
			String[] values = form.getChannel().split(",");
			if(values.length==1){
				ca.andChannelEqualTo(form.getChannel());
			}else{
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < values.length; i++) {
					idList.add(values[i]);
				}
				ca.andChannelIn(idList);
			}
			
		}
		ca.andCenteridEqualTo(form.getCenterid());
		if(!CommonUtil.isEmpty(form.getPid())){
			ca.andPidEqualTo(form.getPid());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		@SuppressWarnings("unchecked")
		List<Mi040> list = cmi040DAO.selectByExample(mi040Example);
		return list;
	}
	
	
	
	public Mi040 webapi04008(String pid) throws Exception {		
		Mi040 mi040 = cmi040DAO.selectByPrimaryKey(pid);
		return mi040;
	}

	public CMi040DAO getCmi040DAO() {
		return cmi040DAO;
	}

	public void setCmi040DAO(CMi040DAO cmi040dao) {
		cmi040DAO = cmi040dao;
	}

	public Mi035DAO getMi035DAO() {
		return mi035DAO;
	}

	public void setMi035DAO(Mi035DAO mi035dao) {
		mi035DAO = mi035dao;
	}

	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
}

