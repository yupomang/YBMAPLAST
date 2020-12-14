package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.Quotai;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi036DAO;
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dao.CMi043DAO;
import com.yondervision.mi.dao.CMi044DAO;
import com.yondervision.mi.dto.CMi036;
import com.yondervision.mi.dto.CMi043;
import com.yondervision.mi.dto.CMi044;
import com.yondervision.mi.dto.Mi036;
import com.yondervision.mi.dto.Mi043;
import com.yondervision.mi.dto.Mi043Example;
import com.yondervision.mi.dto.Mi044;
import com.yondervision.mi.result.WebApi03604_queryResult;
import com.yondervision.mi.result.WebApi04304_queryResult;
import com.yondervision.mi.result.WebApi04404_queryResult;
import com.yondervision.mi.service.WebApi043Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.couchbase.JsonUtil;

public class WebApi043ServiceImpl implements WebApi043Service {
	
	@Autowired
	private CMi040DAO cmi040DAO;
	@Autowired
	private CMi043DAO cmi043DAO;
	@Autowired
	private CMi044DAO cmi044DAO;
	@Autowired
	private CMi036DAO cmi036DAO;
	@Autowired
	private CommonUtil commonUtil;
	
	public void webapi04301(CMi043 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		if(CommonUtil.isEmpty(form.getBuztype())){
			log.error(ERROR.PARAMS_NULL.getLogText("业务类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "业务类型为空");
		}
		if(CommonUtil.isEmpty(form.getDayquotai())){
			log.error(ERROR.PARAMS_NULL.getLogText("单日限额"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "单日限额为空");
		}
		if(CommonUtil.isEmpty(form.getOnequotai())){
			log.error(ERROR.PARAMS_NULL.getLogText("单笔限额"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "单笔限额为空");
		}
		//
		Mi043Example example = new Mi043Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andPidEqualTo(form.getPid())
		.andChannelEqualTo(form.getChannel()).andBuztypeEqualTo(form.getBuztype()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int countByExample = cmi043DAO.countByExample(example);
		if(countByExample !=0){
			log.error(ERROR.ADD_CHECK.getLogText("违反唯一约束：中心+渠道+应用+业务类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "违反唯一约束：中心+渠道+应用+业务类型");
		}
		
		String id = commonUtil.genKey("MI043", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("渠道限额MI043"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("渠道限额MI043"));
		}

		form.setQuotaid(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi043DAO.insert(form);	
		
		Quotai quotai = new Quotai();
		quotai.setCenterid(form.getCenterid());
		quotai.setChannel(form.getChannel());
		quotai.setPid(form.getPid());
		quotai.setBuztype(form.getBuztype());
		quotai.setDayquotaiTop(Double.parseDouble(form.getDayquotai().toString()));
		quotai.setOnequotaiTop(Double.parseDouble(form.getOnequotai().toString()));
		quotai.setDayquotai(0.0);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		String date = formatter.format(System.currentTimeMillis());
		quotai.setToday(date);
		String key = form.getCenterid()+"|"+form.getChannel()+"|"+form.getBuztype()+"|"+"quotai";
		CouchBase couchBase = CouchBase.getInstance();
		couchBase.save(key, JsonUtil.getGson().toJson(quotai));
//		CASValue cas = couchBase.getLock(key, 10);
//		if ( cas.getCas() != -1 ){
//			couchBase.save(key, map);
//			couchBase.unLock(key, cas.getCas());
//		}
		String object = (String)couchBase.get(key);
		System.out.println("WEB管理设置资金限额信息，KEY:"+key+", 内容："+object);
		
	}
	public int webapi04302(CMi043 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		String id = form.getQuotaid();
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空");
		}
		String[] Ids = id.split(",");
		for (int i = 0; i < Ids.length; i++) {
			Mi043 mi043 = cmi043DAO.selectByPrimaryKey(Ids[i]);
			if(mi043==null){
				log.error(ERROR.NO_DATA.getLogText("表中无此记录"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "表中无此记录");
			}
			cmi043DAO.deleteByPrimaryKey(Ids[i]);
			String key = mi043.getCenterid()+"|"+mi043.getChannel()+"|"+mi043.getBuztype()+"|"+"quotai";
			CouchBase.getInstance().delete(key);
			
		}
		return 1;
	}
	@SuppressWarnings("unchecked")
	public int webapi04303(CMi043 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		Mi043 mi043 = cmi043DAO.selectByPrimaryKey(form.getQuotaid());
		if(mi043==null){
			log.error(ERROR.PARAMS_NULL.getLogText("参数为空或者表中无此记录"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "参数为空或者表中无此记录");
		}
		Mi043Example example = new Mi043Example();
		example.createCriteria().andCenteridEqualTo(form.getCenterid()).andPidEqualTo(form.getPid())
		.andChannelEqualTo(form.getChannel()).andBuztypeEqualTo(form.getBuztype()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi043> select = cmi043DAO.selectByExample(example);
		if(select !=null && !select.isEmpty()){
			if(!form.getQuotaid().equals(select.get(0).getQuotaid())){
				log.error(ERROR.ADD_CHECK.getLogText("违反唯一约束：中心+渠道+应用+业务类型"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.ADD_CHECK.getValue(), "违反唯一约束：中心+渠道+应用+业务类型");
			}
		}
		
		if(CommonUtil.isEmpty(form.getCenterid())){
			log.error(ERROR.PARAMS_NULL.getLogText("中心编码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "中心编码获取失败");
		}
		if(CommonUtil.isEmpty(form.getChannel())){
			log.error(ERROR.PARAMS_NULL.getLogText("渠道名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "渠道名称为空");
		}
		if(CommonUtil.isEmpty(form.getPid())){
			log.error(ERROR.PARAMS_NULL.getLogText("应用名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "应用名称为空");
		}
		if(CommonUtil.isEmpty(form.getBuztype())){
			log.error(ERROR.PARAMS_NULL.getLogText("业务类型"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "业务类型为空");
		}
		if(CommonUtil.isEmpty(form.getDayquotai())){
			log.error(ERROR.PARAMS_NULL.getLogText("单日限额"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "单日限额为空");
		}
		if(CommonUtil.isEmpty(form.getOnequotai())){
			log.error(ERROR.PARAMS_NULL.getLogText("单笔限额"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), "单笔限额为空");
		}
		
		//更新
		mi043.setCenterid(form.getCenterid());
		mi043.setChannel(form.getChannel());
		mi043.setPid(form.getPid());
		mi043.setDayquotai(form.getDayquotai());
		mi043.setOnequotai(form.getOnequotai());
		mi043.setDatemodified(CommonUtil.getSystemDate());
		
		String key = mi043.getCenterid()+"|"+mi043.getChannel()+"|"+mi043.getBuztype()+"|"+"quotai";
		CouchBase couchBase = CouchBase.getInstance();
		couchBase.delete(key);
		int updateResult = cmi043DAO.updateByPrimaryKey(mi043);
		
		Quotai quotai = new Quotai();
		quotai.setCenterid(form.getCenterid());
		quotai.setChannel(form.getChannel());
		quotai.setPid(form.getPid());
		quotai.setBuztype(form.getBuztype());
		quotai.setDayquotaiTop(Double.parseDouble(form.getDayquotai().toString()));
		quotai.setOnequotaiTop(Double.parseDouble(form.getOnequotai().toString()));
		quotai.setDayquotai(0.0);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		String date = formatter.format(System.currentTimeMillis());
		quotai.setToday(date);

		
		String newkey = form.getCenterid()+"|"+form.getChannel()+"|"+form.getBuztype()+"|"+"quotai";
		couchBase.save(newkey, JsonUtil.getGson().toJson(quotai));
		String object = (String)couchBase.get(key);
		System.out.println("WEB管理设置资金限额信息，KEY:"+key+", 内容："+object);
		
		return updateResult;
		
	}
	
	public WebApi04304_queryResult webapi04304(CMi043 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form)){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道限额控制MI043", "form：" + form));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道限额控制MI043");
		}
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道限额控制MI043", "page：" + form.getPage()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道限额控制MI043");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.NO_DATA.getLogText("渠道控制-渠道限额控制MI043", "rows：" + form.getRows()));
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(), "渠道控制-渠道限额控制MI043");
		}
		WebApi04304_queryResult result = cmi043DAO.select043Page(form);
		if(!CommonUtil.isEmpty(result)){
			List<Mi043> list = result.getList043();
			List<Mi043> newList = new ArrayList<Mi043>();
			if(list !=null && !list.isEmpty()){
				for(Mi043 mi043:list){
					String appname = cmi040DAO.selectByPrimaryKey(mi043.getPid()).getAppname();
					CMi043 cmi043 = new CMi043();
					BeanUtils.copyProperties(cmi043, mi043);
					cmi043.setAppname(appname);
					newList.add(cmi043);
				}
			}
			result.setList043(newList);
		}
		return result;
	}
	
	public void webapi04401(CMi044 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String id = commonUtil.genKey("MI044", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("MI044"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("MI044"));
		}

		form.setId(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi044DAO.insert(form);	
	}
	
	public WebApi04404_queryResult webapi04404(CMi044 form) throws Exception {
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPage())){
			log.error(ERROR.PARAMS_NULL.getLogText("page为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "page为空");
		}
		if(CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("rows为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), "rows为空");
		}
		WebApi04404_queryResult result = cmi044DAO.select044Page(form);
		if(!CommonUtil.isEmpty(result)){
			List<Mi044> list = result.getList044();
			List<Mi044> newList = new ArrayList<Mi044>();
			if(list !=null && !list.isEmpty()){
				for(Mi044 mi044:list){
					String appname = cmi040DAO.selectByPrimaryKey(mi044.getPid()).getAppname();
					CMi044 cmi044 = new CMi044();
					BeanUtils.copyProperties(cmi044, mi044);
					cmi044.setAppname(appname);
					newList.add(cmi044);
				}
			}
			result.setList044(newList);
		}
		return result;
	}
	
	/**
	 * 单日限额新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi03601(CMi036 form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String id = commonUtil.genKey("MI036", 20).toString();// 采号生成，前补0，总长度20
		if (CommonUtil.isEmpty(id)) {
			log.error(ERROR.NULL_KEY.getLogText("MI036"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(), ERROR.NULL_KEY.getLogText("MI036"));
		}

		form.setId(id);
		form.setDatecreated(CommonUtil.getSystemDate());
		form.setDatemodified(CommonUtil.getSystemDate());
		form.setValidflag(Constants.IS_VALIDFLAG);
		cmi036DAO.insert(form);	
	}
	
	/**
	 * 单日限额分页查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public WebApi03604_queryResult webapi03604(CMi036 form) throws Exception{
		Logger log = LoggerUtil.getLogger();
		if(CommonUtil.isEmpty(form.getPage())||CommonUtil.isEmpty(form.getRows())){
			log.error(ERROR.PARAMS_NULL.getLogText("单日限额分页查询MI036"));
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(), ERROR.PARAMS_NULL.getLogText("单日限额分页查询MI036"));
		}
		WebApi03604_queryResult result = cmi036DAO.select036Page(form);
		
		if(!CommonUtil.isEmpty(result)){
			List<Mi036> list = result.getList036();
			List<Mi036> newList = new ArrayList<Mi036>();
			if(list !=null && !list.isEmpty()){
				for(Mi036 mi036:list){
					String appname = cmi040DAO.selectByPrimaryKey(mi036.getPid()).getAppname();
					CMi036 cmi036 = new CMi036();
					BeanUtils.copyProperties(cmi036, mi036);
					cmi036.setAppname(appname);
					newList.add(cmi036);
				}
			}
			result.setList036(newList);
		}
		return result;
	}
	
	
	public CMi043DAO getCmi043DAO() {
		return cmi043DAO;
	}
	public void setCmi043DAO(CMi043DAO cmi043dao) {
		cmi043DAO = cmi043dao;
	}
	public CMi044DAO getCmi044DAO() {
		return cmi044DAO;
	}
	public void setCmi044DAO(CMi044DAO cmi044dao) {
		cmi044DAO = cmi044dao;
	}
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	public CMi036DAO getCmi036DAO() {
		return cmi036DAO;
	}
	public void setCmi036DAO(CMi036DAO cmi036dao) {
		cmi036DAO = cmi036dao;
	}
	public CMi040DAO getCmi040DAO() {
		return cmi040DAO;
	}
	public void setCmi040DAO(CMi040DAO cmi040dao) {
		cmi040DAO = cmi040dao;
	}
}
