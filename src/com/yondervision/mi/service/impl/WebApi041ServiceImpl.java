package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.HeartBeat;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dao.CMi041DAO;
import com.yondervision.mi.dto.CMi041;
import com.yondervision.mi.dto.Mi041;
import com.yondervision.mi.dto.Mi041Example;
import com.yondervision.mi.result.WebApi04104_queryResult;
import com.yondervision.mi.service.WebApi041Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.couchbase.CouchBase;

public class WebApi041ServiceImpl implements WebApi041Service {
	
	@Autowired
	private CMi040DAO cmi040DAO;
	@Autowired
	private CMi041DAO cmi041DAO;
	@Autowired
	private CommonUtil commonUtil;


	public void webapi04101(CMi041 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		if(CommonUtil.isEmpty(form.getMonitor())){
			log.error(ERROR.PARAMS_NULL.getLogText("是否监控"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "是否监控为空");
		}
		if(CommonUtil.isEmpty(form.getCheckurl())){
			log.error(ERROR.PARAMS_NULL.getLogText("心跳地址"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "心跳地址为空");
		}
		//检查
		Mi041Example mi041Example = new Mi041Example();
		mi041Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andChannelEqualTo(form.getChannel())
		.andPidEqualTo(form.getPid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi041DAO.countByExample(mi041Example);
		if(0!=countByExample){
			log.error(ERROR.ADD_CHECK.getLogText("渠道监控MI041"));
			throw new TransRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "渠道监控MI041");
		}
		//新增
		String id = commonUtil.genKey("MI041", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("应用信息MI040"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("应用信息MI040"));
		}
		form.setMonitorid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi041DAO.insert(form);
	}

	public int webapi04102(CMi041 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getMonitorid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String[] Ids = id.split(",");
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < Ids.length; i++) {
			idList.add(Ids[i]);
		}

		Mi041Example example = new Mi041Example();
		example.createCriteria().andMonitoridIn(idList);
		//int result = cmi041DAO.updateByExampleSelective(mi041, example);
		int result = cmi041DAO.deleteByExample(example);
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道监控ID:" + form.getMonitorid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "渠道监控MI041");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int webapi04103(CMi041 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi041 mi041 = cmi041DAO.selectByPrimaryKey(form.getMonitorid());
		if(mi041==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		//非空检验
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		if(CommonUtil.isEmpty(form.getMonitor())){
			log.error(ERROR.PARAMS_NULL.getLogText("是否监控"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "是否监控为空");
		}
		if(CommonUtil.isEmpty(form.getCheckurl())){
			log.error(ERROR.PARAMS_NULL.getLogText("心跳地址"));
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(), "心跳地址为空");
		}
		
		//检查
		Mi041Example mi041Example = new Mi041Example();
		mi041Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andChannelEqualTo(form.getChannel())
		.andPidEqualTo(form.getPid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi041> selectByExample = cmi041DAO.selectByExample(mi041Example);
		if(selectByExample!=null && !selectByExample.isEmpty()){
			if(!form.getMonitorid().equals(selectByExample.get(0).getMonitorid())){
				log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道监控ID:" + form.getMonitorid()));
				throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "渠道监控MI041");
			}
		}
		//更新
		mi041.setPid(form.getPid());
		mi041.setCenterid(form.getCenterid());
		mi041.setChannel(form.getChannel());
		mi041.setCheckurl(form.getCheckurl());
		mi041.setMonitor(form.getMonitor());
		mi041.setDatemodified(CommonUtil.getSystemDate());
		int updateResult = cmi041DAO.updateByPrimaryKeySelective(mi041);
		if (0 == updateResult) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("渠道监控ID:" + form.getMonitorid()));
			throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(), "渠道监控MI041");
		}
		return updateResult;
	}

	public WebApi04104_queryResult webapi04104(CMi041 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控MI041", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控MI041");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控MI041", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控MI041");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控MI041", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控MI041");
		}
		WebApi04104_queryResult result = cmi041DAO.select041Page(form);
		if(!CommonUtil.isEmpty(result)){
			List<Mi041> list = result.getList041();
			List<Mi041> newList = new ArrayList<Mi041>();
			if(list !=null && !list.isEmpty()){
				for(int i=0;i<list.size();i++){
					Mi041 mi041 = list.get(i);
					String appname = cmi040DAO.selectByPrimaryKey(mi041.getPid()).getAppname();
					CMi041 cmi041 = new CMi041();
					BeanUtils.copyProperties(cmi041, mi041);
					cmi041.setAppname(appname);
					newList.add(i, cmi041);
					
				}
			}
			result.setList041(newList);
		}
		return result;
		
	}
	
	/**
	 * 渠道应用运行状态（从缓存中取实时数据open）
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HeartBeat> webapi04105(String centerid)throws Exception{
		List<HeartBeat> heartBeatList = new ArrayList<HeartBeat>();
		Mi041Example example = new Mi041Example();
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		example.createCriteria().andCenteridEqualTo(centerid);
		
		List<Mi041> selectList = cmi041DAO.selectByExample(example);
		if(selectList!=null && !selectList.isEmpty()){
			for(Mi041 mi041:selectList){
				if("1".equals(mi041.getMonitor().trim())){//监控开关1-打开0-关闭
					String channel = mi041.getChannel();
					String pid = mi041.getPid();
					String key = centerid+"|"+channel+"|"+pid+"|HeartBeat"; 
					CouchBase cb=CouchBase.getInstance();
					HeartBeat heartBeat = cb.get(key, HeartBeat.class);
					if(heartBeat!=null){
						heartBeatList.add(heartBeat);
					}
				}
			}
		}
		return heartBeatList;
	}
	
	public CMi041DAO getCmi041DAO() {
		return cmi041DAO;
	}

	public void setCmi041DAO(CMi041DAO cmi041dao) {
		cmi041DAO = cmi041dao;
	}

	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public CMi040DAO getCmi040DAO() {
		return cmi040DAO;
	}

	public void setCmi040DAO(CMi040DAO cmi040dao) {
		cmi040DAO = cmi040dao;
	}
}
