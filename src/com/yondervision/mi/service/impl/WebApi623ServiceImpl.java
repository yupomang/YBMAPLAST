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
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dao.Mi202DAO;
import com.yondervision.mi.dao.Mi623DAO;
import com.yondervision.mi.dao.Mi624DAO;
import com.yondervision.mi.dto.CMi622;
import com.yondervision.mi.dto.CMi623;
import com.yondervision.mi.dto.CMi624;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi202Example;
import com.yondervision.mi.dto.Mi622;
import com.yondervision.mi.dto.Mi622Example;
import com.yondervision.mi.dto.Mi623;
import com.yondervision.mi.dto.Mi623Example;
import com.yondervision.mi.dto.Mi624;
import com.yondervision.mi.dto.Mi624Example;
import com.yondervision.mi.service.WebApi623Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi623ServiceImpl 
* @Description: 预约业务类型维护实现
* @author sunxl
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi623ServiceImpl implements WebApi623Service {
	
	@Autowired
	public Mi623DAO mi623Dao = null;

	public void setMi623Dao(Mi623DAO mi623Dao) {
		this.mi623Dao = mi623Dao;
	}
	@Autowired
	public Mi624DAO mi624Dao = null;

	public void setMi624Dao(Mi624DAO mi624Dao) {
		this.mi624Dao = mi624Dao;
	}
	@Autowired
	public Mi201DAO mi201Dao = null;
	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}
	@Autowired
	public Mi202DAO mi202Dao = null;
	public void setMi202Dao(Mi202DAO mi202Dao) {
		this.mi202Dao = mi202Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	public JSONObject webapi62304(CMi623 form, Integer page, Integer rows) {
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi623Service webapi62304 page="+page+",rows="+rows));
		
		String sql = null;
		if (form.getWebsiteCode()!=null){
			sql="select * from mi623 where website_code = '"+form.getWebsiteCode()+"' and validflag = '"+Constants.IS_VALIDFLAG +"' and centerid ='"+form.getCenterId()+"'";	
			sql += " order by DATECREATED asc";
			JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
			log.info(LOG.SELF_LOG.getLogText("查询JSONObj结果："+obj.toString()));
			log.info(LOG.SELF_LOG.getLogText("[+]webApi623Service webapi62304 page="+page+",rows="+rows));
			return obj;
		}
		return new JSONObject();
	}
	public List<Mi201> getWebsiteByArea(String pid) {
		Logger log = LoggerUtil.getLogger();
		log.debug("pid==="+pid);
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca = m201e.createCriteria();
		ca.andCenteridEqualTo(UserContext.getInstance().getCenterid());
		ca.andAreaIdEqualTo(pid);
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return  mi201Dao.selectByExample(m201e); 
	}
	public List<Mi202> getArea() {
		Logger log = LoggerUtil.getLogger();
		Mi202Example m202e=new Mi202Example();
		com.yondervision.mi.dto.Mi202Example.Criteria ca = m202e.createCriteria();
		ca.andCenteridEqualTo(UserContext.getInstance().getCenterid());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		return  mi202Dao.selectByExample(m202e); 
	}
	public void webapi62301(CMi623 form) throws Exception {
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				// 传入参数空值校验
				if (CommonUtil.isEmpty(form.getCenterId())) {
					log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotemplateid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段模版"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版获取失败");
				}
				if (CommonUtil.isEmpty(form.getBegindate())) {
					log.error(ERROR.PARAMS_NULL.getLogText("启用日期"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"启用日期获取失败");
				}
				if (CommonUtil.isEmpty(form.getMaxdays())) {
					log.error(ERROR.PARAMS_NULL.getLogText("最长可预约天数"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"最长可预约天数获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppobusiid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("业务类型"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务类型获取失败");
				}
				if (CommonUtil.isEmpty(form.getWebsiteCode())) {
					log.error(ERROR.PARAMS_NULL.getLogText("网点"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"网点获取失败");
				}
				Mi623Example m623e=new Mi623Example();
				com.yondervision.mi.dto.Mi623Example.Criteria ca= m623e.createCriteria();
				ca.andWebsiteCodeEqualTo(form.getWebsiteCode()).andCenteridEqualTo(form.getCenterId()).andAppobusiidEqualTo(form.getAppobusiid());
				List<Mi623> mi623List = mi623Dao.selectByExample(m623e);
				SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
				Date date = new Date();
				if(mi623List.size()>0){
					if(mi623List.get(0).getValidflag().equals(Constants.IS_VALIDFLAG)){
						throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"同一网点下，该业务类型已添加");
					}else{
						Mi623 mi623 = new Mi623();
						mi623.setAppobranchid(mi623List.get(0).getAppobranchid());
						mi623.setValidflag(Constants.IS_VALIDFLAG);
						mi623.setDatemodified(formatter.format(date));
						mi623.setMaxdays(form.getMaxdays());
						mi623.setAppotemplateid(form.getAppotemplateid());
						mi623.setBegindate(form.getBegindate());
						int result = mi623Dao.updateByPrimaryKeySelective(mi623);
						return;
					}
			}else{
				Mi623 mi623 = new Mi623();
				mi623.setAppobranchid(commonUtil.genKey("MI623").toString());
				mi623.setAppotemplateid(form.getAppotemplateid());
				mi623.setAppobusiid(form.getAppobusiid());
				mi623.setBegindate(form.getBegindate());
				mi623.setCenterid(form.getCenterId());
				mi623.setMaxdays(form.getMaxdays());
				mi623.setWebsiteCode(form.getWebsiteCode());
				mi623.setDatecreated(formatter.format(date));
				mi623.setDatemodified(formatter.format(date));
				mi623.setFreeuse1(form.getFreeuse1());
				mi623.setFreeuse2(form.getFreeuse2());
				mi623.setFreeuse3(form.getFreeuse3());
				mi623.setFreeuse4(form.getFreeuse4());
				mi623.setValidflag(Constants.IS_VALIDFLAG);
				mi623.setLoginid(form.getUserid());
				mi623Dao.insert(mi623);
			}
	}

	public int webapi62302(CMi623 form) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				// 传入参数非空校验
						if (CommonUtil.isEmpty(form.getAppobranchid())) {
							log.error(ERROR.PARAMS_NULL.getLogText("业务预约网点编号"));
							throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"业务预约网点获取失败");
						}
						
						String[] appotpldetails = form.getAppobranchid().split(",");
						List<String> apptpldetailList = new ArrayList<String>();
						for (int i = 0; i < appotpldetails.length; i++) {
							apptpldetailList.add(appotpldetails[i]);
						}
						
						Mi623 mi623 = new Mi623();
						// 修改时间
						mi623.setDatemodified(CommonUtil.getSystemDate());
						// 删除标记
						mi623.setValidflag(Constants.IS_NOT_VALIDFLAG);
						// 删除者
						mi623.setLoginid(form.getUserid());
						
						Mi623Example example = new Mi623Example();
						example.createCriteria().andAppobranchidIn(apptpldetailList);
						
						int result = mi623Dao.updateByExampleSelective(mi623, example);
						
						if (0 == result) {
							log.error(ERROR.ERRCODE_LOG_800004.getLogText("业务预约网点编号:"+form.getAppobranchid()));
							throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"业务预约网点MI623");
						}
						Mi624Example mi624e = new Mi624Example();
						com.yondervision.mi.dto.Mi624Example.Criteria ca = mi624e
								.createCriteria();
						ca.andAppobranchidEqualTo(form.getAppobranchid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
						List<Mi624> mi624List = mi624Dao.selectByExample(mi624e);
						if(mi624List.size()!=0){
							Mi624 mi624 = new Mi624();
							mi624.setValidflag(Constants.IS_NOT_VALIDFLAG);
							mi624.setLoginid(form.getUserid());
							mi624.setDatemodified(CommonUtil.getSystemDate());
							
							result = mi624Dao.updateByExampleSelective(mi624, mi624e);
							
							if (0 == result) {
								log.error(ERROR.ERRCODE_LOG_800004.getLogText("业务预约网点编号:"+ form.getAppobranchid()));
								throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "网点业务预约时段定义表MI624");
							}
						}
						return result;
	}

	public int webapi62303(CMi623 form) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				
				if (CommonUtil.isEmpty(form.getCenterId())) {
					log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotemplateid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版编号获取失败");
				}
				if (CommonUtil.isEmpty(form.getBegindate())) {
					log.error(ERROR.PARAMS_NULL.getLogText("启用日期"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"启用日期获取失败");
				}
				if (CommonUtil.isEmpty(form.getMaxdays())) {
					log.error(ERROR.PARAMS_NULL.getLogText("最长可预约天数"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"最长可预约天数获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppobusiid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("业务类型"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务类型获取失败");
				}
				if (CommonUtil.isEmpty(form.getWebsiteCode())) {
					log.error(ERROR.PARAMS_NULL.getLogText("网点"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"网点获取失败");
				}
				// 检索待更新数据是否存在（主键）
				Mi623 mi623Tmp = webapi62305(form);
				if (CommonUtil.isEmpty(mi623Tmp)) {
					log.error(ERROR.NO_DATA.getLogText("预约业务网点MI623","预约业务网点编号："+form.getAppobranchid()));
					throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"预约业务网点MI623");
				}

				Mi623 mi623 = new Mi623();
				// ID
				mi623.setAppobranchid(form.getAppobranchid());
				mi623.setAppotemplateid(form.getAppotemplateid());
				mi623.setAppobusiid(form.getAppobusiid());
				mi623.setBegindate(form.getBegindate());
				mi623.setCenterid(form.getCenterId());
				mi623.setMaxdays(form.getMaxdays());
				mi623.setWebsiteCode(form.getWebsiteCode());
				mi623.setFreeuse1(form.getFreeuse1());
				mi623.setFreeuse2(form.getFreeuse2());
				// 修改时间
				mi623.setDatemodified(CommonUtil.getSystemDate());
				// 修改者
				mi623.setLoginid(form.getUserid());
				
				int result = mi623Dao.updateByPrimaryKeySelective(mi623);
				if (0 == result){
					log.error(ERROR.UPDATE_NO_DATA.getLogText("业务预约网点mi623","业务预约网点ID："+form.getAppobranchid()));
					throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),"业务预约网点ID："+form.getAppobranchid());
				}
				return result;
	}
	private Mi623 webapi62305(CMi623 form) throws Exception {
		Mi623 mi623 = new Mi623();
		mi623 = mi623Dao.selectByPrimaryKey(form.getAppobranchid());
		return mi623;
	}
	public void webapi62401(CMi624 form) throws Exception {
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				// 传入参数空值校验
				if (CommonUtil.isEmpty(form.getCenterId())) {
					log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotemplateid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段模版"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段明细"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段明细获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppobranchid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("业务预约网点"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务预约网点获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppocnt())) {
					log.error(ERROR.PARAMS_NULL.getLogText("可预约人数上限"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"可预约人数上限获取失败");
				}
				Mi624Example m624e=new Mi624Example();
				com.yondervision.mi.dto.Mi624Example.Criteria ca= m624e.createCriteria();
				ca.andCenteridEqualTo(form.getCenterId()).andAppobranchidEqualTo(form.getAppobranchid()).andAppotpldetailidEqualTo(form.getAppotpldetailid());
				List<Mi624> mi624List = mi624Dao.selectByExample(m624e);
				SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
				Date date = new Date();
			if(mi624List.size()>0){
					if(mi624List.get(0).getValidflag().equals(Constants.IS_VALIDFLAG)){
						throw new TransRuntimeErrorException(WEB_ALERT.DATA_CHECK_INSERT.getValue(),"同一网点业务下，该时间段已添加");
					}else{
						Mi624 mi624 = new Mi624();
						mi624.setAppobrantimeid(mi624List.get(0).getAppobrantimeid());
						mi624.setValidflag(Constants.IS_VALIDFLAG);
						mi624.setAppocnt(form.getAppocnt());
						mi624.setDatemodified(formatter.format(date));
						int result = mi624Dao.updateByPrimaryKeySelective(mi624);
						return;
					}
			}else{
				
				Mi624Example m624e1=new Mi624Example();
				com.yondervision.mi.dto.Mi624Example.Criteria ca1= m624e.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId()).andAppobranchidEqualTo(form.getAppobranchid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
				mi624List = mi624Dao.selectByExample(m624e1);
				
				Mi624 mi624 = new Mi624();
				mi624.setAppobrantimeid(commonUtil.genKey("MI624").toString());
				mi624.setAppotemplateid(form.getAppotemplateid());
				mi624.setAppotpldetailid(form.getAppotpldetailid());
				mi624.setAppobranchid(form.getAppobranchid());
				mi624.setAppocnt(form.getAppocnt());
				mi624.setCenterid(form.getCenterId());
				mi624.setDatecreated(formatter.format(date));
				mi624.setDatemodified(formatter.format(date));
				mi624.setFreeuse1(form.getFreeuse1());
				mi624.setFreeuse2(form.getFreeuse2());
				mi624.setFreeuse3(form.getFreeuse3());
				mi624.setFreeuse4(mi624List.size()+1);
				mi624.setValidflag(Constants.IS_VALIDFLAG);
				mi624.setLoginid(form.getUserid());
				mi624Dao.insert(mi624);
			}
	}

	public int webapi62402(CMi624 form) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				// 传入参数非空校验
						if (CommonUtil.isEmpty(form.getAppobrantimeid())) {
							log.error(ERROR.PARAMS_NULL.getLogText("网点业务预约时段编号"));
							throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"网点业务预约时段获取失败");
						}
						
						String[] appotpldetails = form.getAppobrantimeid().split(",");
						List<String> apptpldetailList = new ArrayList<String>();
						for (int i = 0; i < appotpldetails.length; i++) {
							apptpldetailList.add(appotpldetails[i]);
						}
						
						Mi624 mi624 = new Mi624();
						// 修改时间
						mi624.setDatemodified(CommonUtil.getSystemDate());
						// 删除标记
						mi624.setValidflag(Constants.IS_NOT_VALIDFLAG);
						// 删除者
						mi624.setLoginid(form.getUserid());
						
						Mi624Example example = new Mi624Example();
						example.createCriteria().andAppobrantimeidIn(apptpldetailList);
						
						int result = mi624Dao.updateByExampleSelective(mi624, example);
						
						if (0 == result) {
							log.error(ERROR.ERRCODE_LOG_800004.getLogText("网点业务预约时段编号:"+form.getAppobrantimeid()));
							throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"网点业务预约时段MI624");
						}
						
						return result;
	}

	public int webapi62403(CMi624 form) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Logger log = LoggerUtil.getLogger();
				
				if (CommonUtil.isEmpty(form.getCenterId())) {
					log.error(ERROR.PARAMS_NULL.getLogText("中心城市编码"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心城市编码获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotemplateid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段模版编号"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段模版编号获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("时段明细"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"时段明细获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppobranchid())) {
					log.error(ERROR.PARAMS_NULL.getLogText("业务预约网点"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"业务预约网点获取失败");
				}
				if (CommonUtil.isEmpty(form.getAppocnt())) {
					log.error(ERROR.PARAMS_NULL.getLogText("可预约人数上限"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"可预约人数上限获取失败");
				}
				// 检索待更新数据是否存在（主键）
				Mi624 mi624Tmp = webapi62405(form);
				if (CommonUtil.isEmpty(mi624Tmp)) {
					log.error(ERROR.NO_DATA.getLogText("网点业务预约时段MI624","网点业务预约时段："+form.getAppobrantimeid()));
					throw new TransRuntimeErrorException(WEB_ALERT.UPD_CHECK.getValue(),"网点业务预约时段MI624");
				}

				Mi624 mi624 = new Mi624();
				// ID
				mi624.setAppobrantimeid(form.getAppobrantimeid());
				mi624.setAppotemplateid(form.getAppotemplateid());
				mi624.setAppotpldetailid(form.getAppotpldetailid());
				mi624.setAppobranchid(form.getAppobranchid());
				mi624.setAppocnt(form.getAppocnt());
				mi624.setCenterid(form.getCenterId());
				// 修改时间
				mi624.setDatemodified(CommonUtil.getSystemDate());
				// 修改者
				mi624.setLoginid(form.getUserid());
				
				int result = mi624Dao.updateByPrimaryKeySelective(mi624);
				if (0 == result){
					log.error(ERROR.UPDATE_NO_DATA.getLogText("网点业务预约时段mi624","网点业务预约时段ID："+form.getAppobrantimeid()));
					throw new TransRuntimeErrorException(WEB_ALERT.UPD_ERROR.getValue(),
							"网点业务预约时段ID："+form.getAppobrantimeid());
				}
				return result;
	}
	private Mi624 webapi62405(CMi624 form) throws Exception {
		Mi624 mi624 = new Mi624();
		mi624 = mi624Dao.selectByPrimaryKey(form.getAppobrantimeid());
		return mi624;
	}
	public JSONObject webapi62404(CMi624 form, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi624Service webapi62404 page="+page+",rows="+rows));
		
		String sql = null;
		if (form.getAppobranchid()!=null){
			Mi624 mi624 = new Mi624();
			Mi624Example m624e=new Mi624Example();
			m624e.setOrderByClause("freeuse4");
			com.yondervision.mi.dto.Mi624Example.Criteria ca= m624e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId()).andAppobranchidEqualTo(form.getAppobranchid()).andValidflagEqualTo(Constants.IS_VALIDFLAG);
			
			List<Mi624> mi624List = mi624Dao.selectByExample(m624e);
			JSONObject obj = new JSONObject();
		    obj.put("rows", mi624List);
		    return obj;
		}
		return new JSONObject();
	}
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public int webapiUpdateSort(JSONArray arr){
		for(int i=0;i<arr.size();i++){
			JSONObject obj = arr.getJSONObject(i);
			Mi624 mi624 = new Mi624();
			mi624.setAppobrantimeid(obj.getString("appobrantimeid"));
			mi624.setFreeuse4(Integer.parseInt(obj.getString("freeuse4")));
			int result = mi624Dao.updateByPrimaryKeySelective(mi624);
		}
		return 1;
	} 
}
