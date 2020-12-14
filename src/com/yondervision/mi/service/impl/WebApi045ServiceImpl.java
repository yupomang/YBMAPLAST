package com.yondervision.mi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.ChannelFlow;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.Flow;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi045DAO;
import com.yondervision.mi.dao.CMi046DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dto.CMi045;
import com.yondervision.mi.dto.CMi046;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi045;
import com.yondervision.mi.dto.Mi045Example;
import com.yondervision.mi.dto.Mi046;
import com.yondervision.mi.dto.Mi046Example;
import com.yondervision.mi.result.WebApi04504_queryResult;
import com.yondervision.mi.result.WebApi04604_queryResult;
import com.yondervision.mi.service.WebApi045Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.couchbase.JsonUtil;

import net.spy.memcached.CASValue;

public class WebApi045ServiceImpl implements WebApi045Service {
	
	@Autowired
	private CMi045DAO cmi045DAO;
	@Autowired
	private CMi046DAO cmi046DAO;
	@Autowired
	private CommonUtil commonUtil;
	
	public void webapi04501(CMi045 form, List<Mi007> list007) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
//		if(CommonUtil.isEmpty(form.getUserflow())){
//			log.error(ERROR.PARAMS_NULL.getLogText("用户量上限"));
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "用户量上限为空");
//		}
		if(CommonUtil.isEmpty(form.getBussinessflow())){
			log.error(ERROR.PARAMS_NULL.getLogText("业务量上限"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "业务量上限为空");
		}

		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi045DAO.insert(form);
		
		List<ChannelFlow> channelFlowList = new ArrayList<ChannelFlow>();
		for(Mi007 mi007:list007){
			ChannelFlow channelFlow = new ChannelFlow();
			channelFlow.setChannel(mi007.getItemid());
			channelFlowList.add(channelFlow);
		}
		
		Flow flow = new Flow();
		flow.setCenterid(form.getCenterid());
		flow.setBussinessflowTOP(Integer.parseInt(form.getBussinessflow()));
		if(!CommonUtil.isEmpty(form.getUserflow())){//说明用户量需要统计
			flow.setUserFlowTop(Integer.parseInt(form.getUserflow()));
		}
		flow.setChannelbusinesses(channelFlowList);
		
		String key=form.getCenterid()+"|Flow";
		CouchBase couchBase = CouchBase.getInstance();
		couchBase.save(key, flow);
//		CASValue cas = couchBase.getLock(key, 10);
//		if ( cas.getCas() != -1 ){
//			couchBase.save(key, flow);
//			couchBase.unLock(key, cas.getCas());
//		}
		
	}
	public int webapi04502(CMi045 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String centerid = form.getCenterid();
		if (CommonUtil.isEmpty(centerid)) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心参数为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心参数为空");
		}
		String[] Ids = centerid.split(",");
		List<String> idList = new ArrayList<String>();
		for (int i = 0; i < Ids.length; i++) {
			idList.add(Ids[i]);
		}

		Mi045 mi045 = new Mi045();
		// 修改时间
		mi045.setDatemodified(CommonUtil.getSystemDate());
		// 删除标记
		mi045.setValidflag(Constants.IS_NOT_VALIDFLAG);
		Mi045Example example = new Mi045Example();
		example.createCriteria().andCenteridIn(idList);
		int result = cmi045DAO.deleteByExample(example);
//		int result = cmi045DAO.updateByExampleSelective(mi045, example);

		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("Mi405渠道流量控制删除:" + form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "Mi405渠道流量控制删除:"+form.getCenterid());
		}
		String key=form.getCenterid()+"|Flow";
		CouchBase couchBase = CouchBase.getInstance();
		couchBase.delete(key);
		return result;
	}
	public int webapi04503(CMi045 form, List<Mi007> list007) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi045 mi045 = cmi045DAO.selectByPrimaryKey(form.getCenterid());
		if(mi045==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		mi045.setDatemodified(CommonUtil.getSystemDate());
		mi045.setUserflow(form.getUserflow());
		mi045.setBussinessflow(form.getBussinessflow());
		int updateResult = cmi045DAO.updateByPrimaryKey(mi045);
		
		
		List<ChannelFlow> channelFlowList = new ArrayList<ChannelFlow>();
		for(Mi007 mi007:list007){
			ChannelFlow channelFlow = new ChannelFlow();
			channelFlow.setChannel(mi007.getItemid());
			channelFlowList.add(channelFlow);
		}
		
		Flow flow = new Flow();
		flow.setCenterid(form.getCenterid());
		flow.setBussinessflowTOP(Integer.parseInt(form.getBussinessflow()));
		if(!CommonUtil.isEmpty(form.getUserflow())){//说明用户量需要统计
			flow.setUserFlowTop(Integer.parseInt(form.getUserflow()));
		}
		flow.setChannelbusinesses(channelFlowList);
		
		String key=form.getCenterid()+"|Flow";
		CouchBase couchBase = CouchBase.getInstance();
		couchBase.delete(key);
		couchBase.save(key, flow);
//		CASValue cas = couchBase.getLock(key, 10);
//		if ( cas.getCas() != -1 ){
//			couchBase.save(key, flow);
//			couchBase.unLock(key, cas.getCas());
//		}
		return updateResult;
		
	}
	
	public WebApi04504_queryResult webapi04504(CMi045 form) throws Exception {
		
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道流量控制MI045", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道流量控制MI045");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道流量控制MI045", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道流量控制MI045");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道流量控制MI045", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道流量控制MI045");
		}
		WebApi04504_queryResult select045Page = cmi045DAO.select045Page(form);
		return select045Page;
	}
	//046表插入Service方法
	public void webapi04601() throws Exception{
		Logger log = LoggerUtil.getLogger();	
		
		Mi001DAO mi001Dao = (Mi001DAO)SpringContextUtil.getBean("mi001Dao");
		Mi001Example mi001Example = new Mi001Example();
		mi001Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);//取出标识validFlag为有效的
		List<Mi001> list1 = mi001Dao.selectByExample(mi001Example);
		if(list1!=null && !list1.isEmpty()){
			CouchBase cb=CouchBase.getInstance();
			for(Mi001 mi001:list1){
				String centerid = mi001.getCenterid();
				String key = centerid+"|Flow";
				Object objFlow = cb.get(key);
				System.out.println("Flow:"+objFlow);
				if (!CommonUtil.isEmpty(objFlow)) {
					log.info("业务量统计缓存信息 ："+JsonUtil.getGson().toJson(cb.get(key)));
					
					Flow flow = JsonUtil.getGson().fromJson((String)cb.get(key).toString(), Flow.class);
					log.info("#$#$#$#$[ "+flow.getBussinessflow()+" ]");
					log.info("#$#$#$#$[ "+flow.getBussinessflowTOP()+" ]");
					
					List<ChannelFlow> list = flow.getChannelbusinesses();			
					for(int i=0;i<list.size();i++){
						log.info("当前业务渠道channel[ "+list.get(i).getChannel()+" ]");
						log.info("当前业务访问量countBusinesses[ "+list.get(i).getCountBusinesses()+" ]");
						log.info("当前业务用户量countUser[ "+list.get(i).getCountUser()+" ]");
						
						Mi046 mi046 = new Mi046();
						mi046.setId(commonUtil.genKey("MI046", 0));
						mi046.setCenterid(centerid);
						mi046.setChannel(list.get(i).getChannel());
						mi046.setCountbusiness(String.valueOf(list.get(i).getCountBusinesses()));
						mi046.setCountuser(String.valueOf(list.get(i).getCountUser()));	
						mi046.setValidflag(Constants.IS_VALIDFLAG);
						mi046.setDatecreated(CommonUtil.getSystemDate());
						mi046.setDatemodified(CommonUtil.getSystemDate());				
						cmi046DAO.insert(mi046);					
					}
				}else {
					log.info("业务量统计缓存为空 ");
				}
			}
		}
	}
	
	public WebApi04604_queryResult webapi04604(CMi046 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		WebApi04604_queryResult select046Page = cmi046DAO.select046Page(form);
		return select046Page;
	}
	/**
	 * 渠道流量监控历史详细查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi046> webapi04605(CMi046 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form.getChannel()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getStartTime())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form.getStartTime()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		if(CommonUtil.isEmpty(form.getEndTime())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form.getEndTime()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		
		//查询
		Mi046Example mi046Example = new Mi046Example();
		mi046Example.createCriteria().andCenteridEqualTo(form.getCenterid()).andChannelEqualTo(form.getChannel())
		.andDatecreatedBetween(form.getStartTime(), form.getEndTime()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi046> select = cmi046DAO.selectByExample(mi046Example);
		return select;
		
	}
	
	/**
	 * 根据中心从缓存拿数据
	 * @param centerid
	 * @return
	 * @throws Exception
	 */
	public Flow webapi04606(CMi046 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.NO_DATA.getLogText("渠道监控-渠道流量历史监控表MI046", "form：" + form.getCenterid()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道监控-渠道流量历史监控表MI046");
		}
		CouchBase couchBase = CouchBase.getInstance();
		Object obj = couchBase.get(form.getCenterid()+"|Flow");
		if(!CommonUtil.isEmpty(obj)){
			Flow flow = JsonUtil.getGson().fromJson(obj.toString(), Flow.class);
			return flow;
		}else{
			log.error(ERROR.NO_DATA.getLogText("该中心下渠道流量监控没有配置，请检查！"));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "该中心下渠道流量监控没有配置，请检查！");
		}
	}
	
	public CMi045DAO getCmi045DAO() {
		return cmi045DAO;
	}
	public void setCmi045DAO(CMi045DAO cmi045dao) {
		cmi045DAO = cmi045dao;
	}
	public CMi046DAO getCmi046DAO() {
		return cmi046DAO;
	}
	public void setCmi046DAO(CMi046DAO cmi046dao) {
		cmi046DAO = cmi046dao;
	}
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
}
