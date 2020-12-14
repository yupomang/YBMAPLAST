/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service.impl
 * 文件名：     AppApi303ServiceImpl.java
 * 创建日期：2013-11-18
 */
package com.yondervision.mi.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi029DAO;
import com.yondervision.mi.dao.CMi031DAO;
import com.yondervision.mi.dao.CMi607DAO;
import com.yondervision.mi.dao.CMi623DAO;
import com.yondervision.mi.dao.CMi625DAO;
import com.yondervision.mi.dao.CMi627DAO;
import com.yondervision.mi.dao.Mi007DAO;
import com.yondervision.mi.dao.Mi110DAO;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dao.Mi620DAO;
import com.yondervision.mi.dao.Mi625DAO;
import com.yondervision.mi.dao.Mi626DAO;
import com.yondervision.mi.dto.CMi607;
import com.yondervision.mi.dto.CMi623;
import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi007Example;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi031Example;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi620Example;
import com.yondervision.mi.dto.Mi623;
import com.yondervision.mi.dto.Mi623Example;
import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.dto.Mi625Example;
import com.yondervision.mi.dto.Mi626;
import com.yondervision.mi.dto.Mi626Example;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.dto.Mi627Example;
import com.yondervision.mi.form.AppApi30301Form;
import com.yondervision.mi.form.AppApi30302Form;
import com.yondervision.mi.form.AppApi30303Form;
import com.yondervision.mi.form.AppApi30304Form;
import com.yondervision.mi.form.AppApi30305Form;
import com.yondervision.mi.form.AppApi30306Form;
import com.yondervision.mi.form.AppApi30309Form;
import com.yondervision.mi.form.AppApi62601Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi30301Result;
import com.yondervision.mi.result.AppApi30302Result;
import com.yondervision.mi.result.AppApi30303Result;
import com.yondervision.mi.result.AppApi30305Result;
import com.yondervision.mi.result.AppApi30307Result;
import com.yondervision.mi.result.AppApi30308Result;
import com.yondervision.mi.result.DateCountBean;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.service.AppApi303Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.twodimensional.QRCodeUtil;

import net.sf.json.JSONObject;

/**
 * @author LinXiaolong
 * 
 */
public class AppApi303ServiceImpl implements AppApi303Service {

	private Mi620DAO mi620Dao;
	
	private Mi625DAO mi625Dao;
	
	private CMi623DAO cmi623Dao;
	private Mi201DAO mi201Dao;

	private Mi626DAO mi626Dao=null;
	private CMi625DAO cmi625Dao;
	private CMi627DAO cmi627Dao;
	private CMi607DAO cmi607Dao;
	private Mi110DAO mi110Dao;
	@Autowired
	private CMi031DAO cmi031DAO;
	@Autowired
	private CMi029DAO cmi029DAO;
	@Autowired
	public Mi007DAO mi007Dao;
	
	
	public CMi031DAO getCmi031DAO() {
		return cmi031DAO;
	}

	public void setCmi031DAO(CMi031DAO cmi031dao) {
		cmi031DAO = cmi031dao;
	}

	public CMi627DAO getCmi627Dao() {
		return cmi627Dao;
	}

	public void setCmi627Dao(CMi627DAO cmi627Dao) {
		this.cmi627Dao = cmi627Dao;
	}

	public CMi625DAO getCmi625Dao() {
		return cmi625Dao;
	}

	public void setCmi625Dao(CMi625DAO cmi625Dao) {
		this.cmi625Dao = cmi625Dao;
	}

	/**
	 * @return the mi201Dao
	 */
	public Mi201DAO getMi201Dao() {
		return mi201Dao;
	}

	/**
	 * @param mi201Dao
	 *            the mi201Dao to set
	 */
	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}
	
	public Mi626DAO getMi626Dao() {
		return mi626Dao;
	}


	public void setMi626Dao(Mi626DAO mi626Dao) {
		this.mi626Dao = mi626Dao;
	}
	/**
	 * @return the mi620Dao
	 */
	public Mi620DAO getMi620Dao() {
		return mi620Dao;
	}

	public CMi623DAO getCmi623Dao() {
		return cmi623Dao;
	}

	public void setCmi623Dao(CMi623DAO cmi623Dao) {
		this.cmi623Dao = cmi623Dao;
	}
	/**
	 * @param mi620Dao
	 *            the mi620Dao to set
	 */
	public void setMi620Dao(Mi620DAO mi620Dao) {
		this.mi620Dao = mi620Dao;
	}


	public Mi625DAO getMi625Dao() {
		return mi625Dao;
	}

	public void setMi625Dao(Mi625DAO mi625Dao) {
		this.mi625Dao = mi625Dao;
	}
	@Autowired
	private CommonUtil commonUtil = null;
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30301(com.yondervision
	 * .mi.form.AppApi30301Form)
	 */
	@SuppressWarnings("unchecked")
	public List<AppApi30301Result> appApi30301(AppApi30301Form form)
			throws Exception {
		// TODO Auto-generated method stub
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 根据业务类型查询预约日期、网点及最大预约人数
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30302(com.yondervision
	 * .mi.form.AppApi30302Form)
	 */
	public List<AppApi30302Result> appApi30302(AppApi30302Form form)
			throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务ID获取失败");
		}
		SimpleDateFormat dfsdate = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Map<String ,String> mapwebsite = new HashMap<String,String>();
		mapwebsite.put("appobusiid", form.getAppobusiid());
		mapwebsite.put("centerId", form.getCenterId());
		//查询网点
		List<HashMap> list = this.getCmi623Dao().selectAppapi30302Website(mapwebsite);
		
		Map<String,String> mapCount = new HashMap<String,String>();
		mapCount.put("centerId",form.getCenterId());
		//查询mi624表中，在一个预约网点和时段模版中，最大可预约人数
		List<HashMap> listcount = this.getCmi623Dao().selectAppapi30302Count(mapCount);
		//查询每天该网点下所预约的总数
		List<HashMap> listallcount = this.getCmi623Dao().selectAppapi30302AllCount(mapCount);
		//预约网点的可预约人数明细信息
		List<HashMap> listdetail = this.getCmi623Dao().selectAppapi30302AppoCountDetail(mapCount);
		HashMap<String,Integer> mapCanAppoCount = new HashMap<String,Integer>();
		for(int i=0;i<listdetail.size();i++){
			HashMap temp = listdetail.get(i);
			String timeinterval = temp.get("timeinterval").toString();
			String freeuse1 = temp.get("freeuse1")==null?"0":temp.get("freeuse1").toString();
			if(freeuse1.equals("1")){
				long hour = timeDifference(timeinterval);
				String freeuse2 = temp.get("freeuse2")==null?"0":temp.get("freeuse2").toString();
				String apoobranchid = temp.get("appobranchid").toString();
				if(hour<Long.parseLong(freeuse2)){
					int count=0;
					if(mapCanAppoCount.get(apoobranchid)==null){
						count = Integer.parseInt(temp.get("appocnt").toString());
					}else{
						count=mapCanAppoCount.get(apoobranchid)+Integer.parseInt(temp.get("appocnt").toString());
					}
					mapCanAppoCount.put(apoobranchid, count);
				}
			}
		}
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("centerId",form.getCenterId());
		map1.put("appodate", dfsdate.format(date));
		List<HashMap> listUserAppToday = this.getCmi623Dao().selectTodayAppo(map1);
		//当天预约已过期的map，key值为预约网点id，value值位数量
		HashMap<String,Integer> mapAppoCount = new HashMap<String,Integer>();
		for(int i=0;i<listUserAppToday.size();i++){
			HashMap temp = listUserAppToday.get(i);
			String timeinterval = temp.get("timeinterval").toString();
			String freeuse1 = temp.get("freeuse1")==null?"0":temp.get("freeuse1").toString();
			if(freeuse1.equals("1")){
				long hour = timeDifference(timeinterval);
				String freeuse2 = temp.get("freeuse2")==null?"0":temp.get("freeuse2").toString();
				String apoobranchid = temp.get("appobranchid").toString();
				if(hour<Long.parseLong(freeuse2)){
					int count=mapAppoCount.get(apoobranchid)==null?1:mapAppoCount.get(apoobranchid)+1;
					mapAppoCount.put(apoobranchid, count);
				}
			}
		}
		
		List<AppApi30302Result> result = new ArrayList<AppApi30302Result>(list.size());
		for(int i=0;i<list.size();i++){
			HashMap map = list.get(i);
			String appocnt = "0";
			
			for(int j=0;j<listcount.size();j++){
				HashMap maptemp= listcount.get(j);
				if(maptemp.get("appobranchid").toString().equals(map.get("appobranchid").toString())&&maptemp.get("appotemplateid").toString().equals(map.get("appotemplateid").toString())){
					appocnt = maptemp.get("appocnt").toString();
					break;
				}
			}
			Integer maxdays = (Integer) map.get("maxdays");
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			String begindate =  map.get("begindate").toString();
			String isCanAppToday = map.get("freeuse1")==null?"0":map.get("freeuse1").toString(); //当天是否能预约
			//查询有效日期 去除节假日且需在启用日期后
			Date today = new Date();
			Date begin = formate.parse(begindate);
			Date yesterday = new Date();
			CMi627 mi627 = new CMi627();
			mi627.setDatenum(maxdays);
			mi627.setCenterid(form.getCenterId());
			 if (today.getTime() > begin.getTime()) {
				 //有效期在当天前，使用当天日期查询可预约日期
				 if("1".equals(isCanAppToday)){
					 yesterday = new Date(today.getTime()-1000*3600*24);
					 mi627.setStartdate(formate.format(yesterday));
				 }else{
					 mi627.setStartdate(formate.format(today));
				}
			 }else{
				 //有效期在当天后，使用有效日期查询可预约日期
				 mi627.setStartdate(formate.format(begin));
			 }
			 List<Mi627> mi627list = cmi627Dao.selectResDates(mi627);
			 List<DateCountBean> listdatecount = new ArrayList<DateCountBean>();
			 for(int j=0;j<mi627list.size();j++){
				 Mi627 sub672 = mi627list.get(j);
				 DateCountBean bean = new DateCountBean();
				 bean.setAppodate(sub672.getFestivaldate());
				 String appocnttemp=null;
				 int canAppo = mapCanAppoCount.get(map.get("appobranchid").toString())==null?0:new Integer(mapCanAppoCount.get(map.get("appobranchid").toString()));
				 
				 
				 
				 int tempcount = Integer.parseInt(appocnt);
				 if(sub672.getFestivaldate().equals(dfsdate.format(date))){
					 tempcount = tempcount-canAppo;
				 }
				 for(int m=0;m<listallcount.size();m++){
					HashMap maptemp= listallcount.get(m);
					
					if(maptemp.get("appobranchid").toString().equals(map.get("appobranchid").toString())&&maptemp.get("appodate").toString().equals(sub672.getFestivaldate())){
						if(maptemp.get("appodate").toString().equals(dfsdate.format(date))){
							int appo = mapAppoCount.get(maptemp.get("appobranchid").toString())==null?0:new Integer(mapAppoCount.get(maptemp.get("appobranchid").toString()));
							appocnttemp = (tempcount- ((new Integer(maptemp.get("appocnt").toString()))-appo))+"";
						}else{
							appocnttemp = (new Integer(appocnt)- new Integer(maptemp.get("appocnt").toString()))+"";
						}
						break;
					}
				 }
				 bean.setAppocnt(appocnttemp==null?tempcount+"":appocnttemp);
				 listdatecount.add(bean);
			 }
			
			//结束
			AppApi30302Result item = new AppApi30302Result();
			item.setAppobranchid((String) map.get("appobranchid"));
			item.setAppobranchname((String) map.get("websiteName"));
			item.setContent(listdatecount);
			result.add(item);
		}
		return result;
	}
	/***
	 * 计算时段描述的开始时间与当前时间的小时数差
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws ParseException 
	 */
	private static long timeDifference(String iteminterval) throws ParseException{
		SimpleDateFormat dfsdate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Pattern p = Pattern.compile(".*\\d+.*");
		if(iteminterval.indexOf("-")==-1||!p.matcher(iteminterval.split("-")[0]).matches()){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"时段明细数据配置不规范，请联系公积金中心");
		}
		Pattern p1 = Pattern.compile("\\d*");
		Matcher m = p.matcher(iteminterval.split("-")[0]);
		String[] stardates = new String[10];
		while (m.find()) {
			int n=0;
			if (!"".equals(m.group())){
				stardates[n]=m.group();
			}
		}
		String startdate = stardates[0];
		Date date = new Date();
		java.util.Date begintime=dfs.parse(dfsdate.format(date)+" "+startdate);
		java.util.Date nowtime = dfs.parse(dfs.format(date));
		long between=(begintime.getTime()-nowtime.getTime())/1000;//除以1000是为了转换成秒
		long hour1=between/3600;
		return hour1;
	}
	/*
	 * (non-Javadoc)
	 * 根据日期、业务类型、中心、网点查询具体时间段及时间段对应的可预约人数
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30303(com.yondervision
	 * .mi.form.AppApi30303Form)
	 */
	public List<AppApi30303Result> appApi30303(AppApi30303Form form)
			throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getAppobranchid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约网点ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"业务预约网点ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppodate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约日期"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约日期获取失败");
		}
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心ID获取失败");
		}
		
		List<AppApi30303Result> result = new ArrayList<AppApi30303Result>();
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("appobranchid", form.getAppobranchid());
		map.put("appobusiid", form.getAppobusiid());
		map.put("appodate",form.getAppodate());
		map.put("centerId",form.getCenterId());
		List<HashMap> list = this.getCmi623Dao().selectAppapi30303Count(map);
		
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("appobranchid", form.getAppobranchid());
		map1.put("centerId",form.getCenterId());
		List<HashMap> listdetail = this.getCmi623Dao().selectAppapi30303Detail(map1);
		
		for(int i=0;i<listdetail.size();i++){
			AppApi30303Result item = new AppApi30303Result();
			HashMap temp = listdetail.get(i);
			String iteminterval = temp.get("timeinterval").toString();
			item.setAppotpldetailid(temp.get("appotpldetailid").toString());
			item.setTimeinterval(iteminterval);
			String freeuse1 = temp.get("freeuse1")==null?"0":temp.get("freeuse1").toString();
			String freeuse2 = temp.get("freeuse2")==null?"0":temp.get("freeuse2").toString();
			SimpleDateFormat dfsdate = new SimpleDateFormat("yyyy-MM-dd");
			if(freeuse1.equals("1")){
				if(form.getAppodate().equals(dfsdate.format(new Date()))){
					long hour = timeDifference(iteminterval);
					 if(hour<Integer.parseInt(freeuse2))continue;
				}
			}
			String allcnt = temp.get("appocnt").toString();
			int appocnt=0;
			for(int j=0;j<list.size();j++){
				HashMap tempcount = list.get(j);
				if(temp.get("appotpldetailid").toString().equals(tempcount.get("appotpldetailid").toString())){
					appocnt = new Integer(tempcount.get("appocnt").toString());
				}
			}
			item.setRemainpeople(new Integer(allcnt)-appocnt);
			result.add(item);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 根据传回信息，进行预约表的插入
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30304(com.yondervision
	 * .mi.form.AppApi30304Form)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public synchronized String appApi30304(AppApi30304Form form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getAppobranchid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约网点ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"业务预约网点ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppobranchname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务网点名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务网点名称获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppobusiid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppobusiname())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约业务名称"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约业务名称获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppodate())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约日期"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约日期获取失败");
		}
		if (CommonUtil.isEmpty(form.getAppotpldetailid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约时段模板明细ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约时段模板明细ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getChannel())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道平台"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"渠道平台获取失败");
		}
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("用户ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"用户ID获取失败");
		}
		if (CommonUtil.isEmpty(form.getPhone())) {
			log.error(ERROR.PARAMS_NULL.getLogText("手机号码"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),"手机号码不能为空，请输入手机号码");
		}
		if (CommonUtil.isEmpty(form.getTimeinterval())) {
			log.error(ERROR.PARAMS_NULL.getLogText("时段描述"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"时段描述获取失败");
		}
		CMi607 cmi607 = new CMi607();
		cmi607.setCenterid(form.getCenterId());
		cmi607.setUserid(form.getUserId());
		Mi031Example mi031Example1 = new Mi031Example();
		mi031Example1.createCriteria().andCenteridEqualTo(form.getCenterId()).andUseridEqualTo(form.getUserId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi031> selectByExample = cmi031DAO.selectByExample(mi031Example1);
		if(selectByExample==null || selectByExample.isEmpty()){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"尚未注册");
		}
		Mi031 mi031 = selectByExample.get(0);
		cmi607.setPersonalid(mi031.getPersonalid());
		form.setPersonalid(mi031.getPersonalid());
		boolean flag = getCmi607Dao().selectAppInfo(cmi607);
		if(!flag){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"由于您多次预约业务未到中心办理，中心已将您放入黑名单，在此期间，您的预约业务受到限制！");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_FORMAT_XH_YYMMDD);
		Date date = new Date();
		
		String appodate = form.getAppodate();
		form.setAppodate(formatter.format(date));
		List<HashMap> mi625List =cmi625Dao.selectAppapi30304Detail(form);
		if(mi625List!=null&&!mi625List.isEmpty()){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"您已经预约过此业务且未办理,您可到【我的预约】中查看具体信息");
		}
		
		form.setAppodate(appodate);
		List<HashMap> remaiPeople =cmi625Dao.selectRemainPeople(form);
		int cnt624 =cmi625Dao.selectMi624SumAppocnt(form);
		if(remaiPeople.size()>0&&(cnt624-remaiPeople.size())<=0){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"预约已满，请另选预约网点或者日期");
		}
		
		//查出channelname
		Mi007Example example1 = new Mi007Example();
		example1.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo("channel")
		.andUpdicidEqualTo(0);
		Mi007 mi007 = (Mi007)mi007Dao.selectByExample(example1).get(0);
		Mi007Example example2 = new Mi007Example();
		example2.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo(form.getChannel())
		.andUpdicidEqualTo(mi007.getDicid());
		Mi007 newMi007 = (Mi007)mi007Dao.selectByExample(example2).get(0);
		form.setChannelname(newMi007.getItemval());
		
		//查出username
		Mi029 mi029 = cmi029DAO.selectByPrimaryKey(form.getPersonalid());
		String username = mi029.getUsername();
		String certiNum = mi029.getCertinum();//身份证
		
		//生成二维码
		String filePath = CommonUtil.getFileFullPath("push_twodimensional", form.getCenterId()+"YY", true);
		String content = form.getAppobranchid()+";"+form.getAppobusiid()+";0;"+certiNum;
		String filename = QRCodeUtil.encode(form.getCenterId()+CommonUtil.getSystemDateNumOnly()+".jpg", content, filePath, true);
//		String fileTwoPath = CommonUtil.getDownloadFileUrl(
//				"push_twodimensional", "00087100YY"+File.separator+filename, true);
		
		String fileTwoPath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
				"YBMAPZH_URL").trim()+"/"+CommonUtil.getDownloadFileUrl(
				"push_twodimensional", form.getCenterId()+"YY"+File.separator+filename, true);
		if(CommonUtil.isEmpty(filename)||CommonUtil.isEmpty(fileTwoPath)){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"二维码生成失败，请检查");
		}
		System.out.println("看看预约确定二维码返回啥了："+content);
		System.out.println("看看预约确定二维码返回啥了："+fileTwoPath);
		//入表
		Mi625 mi625 = new Mi625();
		String appoid=commonUtil.genKey("MI625").toString();
		String apponum=commonUtil.genKey("MI625.APPONUM").toString();
		apponum =formatter1.format(date)+ form.getAppobusiid()+"0000000000".substring(apponum.length())+apponum;
		
		//查看如表参数
		System.out.println("625如表参数appoid："+appoid);
		System.out.println("625如表参数appobranchid："+form.getAppobranchid());
		System.out.println("625如表参数appobranchname："+form.getAppobranchname());
		System.out.println("625如表参数appobusiid："+form.getAppobusiid());
		System.out.println("625如表参数appobusiname："+form.getAppobusiname());
		System.out.println("625如表参数appodate："+appodate);
		System.out.println("625如表参数apponum："+apponum);
		System.out.println("625如表参数appotpldetailid："+form.getAppotpldetailid());
		System.out.println("625如表参数centerid："+form.getCenterId());
		System.out.println("625如表参数channel："+form.getChannel());
		System.out.println("625如表参数channelname()："+form.getChannelname());
		System.out.println("625如表参数username："+username);
		System.out.println("625如表参数pid："+form.getPid());
		System.out.println("625如表参数pidname："+form.getPidname());
		System.out.println("625如表参数userid："+form.getUserId());
		System.out.println("625如表参数tel："+form.getPhone());
		System.out.println("625如表参数timeinterval："+form.getTimeinterval());
		System.out.println("625如表参数personalid："+form.getPersonalid());
		System.out.println("625如表参数content："+content);
		System.out.println("625如表参数fileTwoPath："+fileTwoPath);
		
		
		mi625.setAppoid(appoid);
		mi625.setAppobranchid(form.getAppobranchid());
		mi625.setAppobranchname(form.getAppobranchname());
		mi625.setAppobusiid(form.getAppobusiid());
		mi625.setAppobusiname(form.getAppobusiname());
		mi625.setAppodate(appodate);
		mi625.setApponum(apponum);
		mi625.setAppostate("01");
		mi625.setAppotpldetailid(form.getAppotpldetailid());
		mi625.setCenterid(form.getCenterId());
		mi625.setChannel(form.getChannel());
		mi625.setChannelname(form.getChannelname());
		mi625.setUsername(username);
		mi625.setPid(form.getPid());
		mi625.setPidname(form.getPidname());
		mi625.setUserid(form.getUserId());
		mi625.setTel(form.getPhone());
		mi625.setTimeinterval(form.getTimeinterval());
		mi625.setPersonalid(form.getPersonalid());
		mi625.setDatecreated(formatter.format(date));
		mi625.setDatemodified(formatter.format(date));
		mi625.setValidflag(Constants.IS_VALIDFLAG);
		mi625.setFreeuse1(content);
		mi625.setFreeuse2(filename);
		mi625Dao.insert(mi625);
		
		return apponum+","+content+","+fileTwoPath;
	}

	/*
	 * (non-Javadoc)
	 * 根据用户id，查询该用户下所有已预约的预约
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30305(com.yondervision
	 * .mi.form.AppApiCommonForm)
	 */
	public List<AppApi30305Result> appApi30305(AppApi30305Form form)
			throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getUserId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("用户ID"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"用户ID获取失败");
		}
		Mi031Example mi031Example = new Mi031Example();
		mi031Example.createCriteria().andCenteridEqualTo(form.getCenterId()).andUseridEqualTo(form.getUserId())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi031> select = cmi031DAO.selectByExample(mi031Example);
		if(select==null || select.isEmpty()){
			throw new TransRuntimeErrorException(APP_ALERT.APP_YY.getValue(),"尚未注册");
		}
		form.setPersonalid(select.get(0).getPersonalid());
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		form.setAppodate(formatter.format(date));
		List<HashMap> list = cmi625Dao.selectAppapi30305User(form);
		List<AppApi30305Result> result = new ArrayList<AppApi30305Result>(list.size());
		for(int i =0;i<list.size();i++){
			AppApi30305Result item = new AppApi30305Result();
			HashMap mi625 = list.get(i);
			item.setAppointid(mi625.get("appoid").toString());
			List<TitleInfoBean> beanList = new ArrayList<TitleInfoBean>();
			TitleInfoBean bean = new TitleInfoBean();
			bean.setTitle("预约号码");
			bean.setInfo(mi625.get("apponum").toString().trim());
			bean.setName("apponum");
			bean.setFormat("");
			beanList.add(bean);
			bean = new TitleInfoBean();
			bean.setTitle("预约日期");
			bean.setInfo(mi625.get("appodate").toString().trim());
			bean.setName("appodate");
			bean.setFormat("");
			beanList.add(bean);
			bean = new TitleInfoBean();
			bean.setTitle("预约时段");
			bean.setInfo(mi625.get("timeinterval").toString().trim());
			bean.setName("timeinterval");
			bean.setFormat("");
			beanList.add(bean);
			bean = new TitleInfoBean();
			bean.setTitle("网点名称");
			bean.setInfo(mi625.get("appobranchname").toString().trim());
			bean.setName("appobranchname");
			bean.setFormat("");
			beanList.add(bean);
			bean = new TitleInfoBean();
			bean.setTitle("预约业务");
			bean.setInfo(mi625.get("appobusiname").toString().trim());
			bean.setName("appobusiname");
			bean.setFormat("");
			beanList.add(bean);
			
			bean = new TitleInfoBean();
			bean.setTitle("预约二维码");
			Object filename = mi625.get("freeuse2");
			System.out.println("我的预约二维码："+filename);
			if(CommonUtil.isEmpty(filename)){
				bean.setInfo("");
			}else{
				String fileTwoPath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
						"YBMAPZH_URL").trim()+"/"+CommonUtil.getDownloadFileUrl(
						"push_twodimensional", form.getCenterId()+"YY"+File.separator+filename.toString().trim(), true);
				bean.setInfo(fileTwoPath);
			}
			bean.setName("appoQRcode");
			bean.setFormat("");
			beanList.add(bean);
			item.setAppointmes(beanList);
			result.add(item);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *根据预约编号撤销预约
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30306(com.yondervision
	 * .mi.form.AppApi30306Form)
	 */
	public void appApi30306(AppApi30306Form form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"中心为空");
		}
		if (CommonUtil.isEmpty(form.getAppointid())) {
			log.error(ERROR.PARAMS_NULL.getLogText("预约编号"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"预约号获取失败");
		}
		if (CommonUtil.isEmpty(form.getChannel())) {
			log.error(ERROR.PARAMS_NULL.getLogText("渠道为空"));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"渠道为空");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		Mi625 mi625 = new Mi625();
		// 修改时间
		mi625.setDatemodified(CommonUtil.getSystemDate());
		
		mi625.setDatecanceled(formatter.format(date));
		// 撤销标记
		mi625.setAppostate("08");
		//查出channelname
		Mi007Example example1 = new Mi007Example();
		example1.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo("channel")
		.andUpdicidEqualTo(0);
		Mi007 mi007 = (Mi007)mi007Dao.selectByExample(example1).get(0);
		Mi007Example example2 = new Mi007Example();
		example2.createCriteria().andCenteridEqualTo(form.getCenterId()).andItemidEqualTo(form.getChannel())
		.andUpdicidEqualTo(mi007.getDicid());
		Mi007 newMi007 = (Mi007)mi007Dao.selectByExample(example2).get(0);
		form.setChannelname(newMi007.getItemval());
		
		mi625.setPid2(form.getPid());
		mi625.setPidname2(form.getPidname());
		mi625.setChannel2(form.getChannel());
		mi625.setChannelname2(form.getChannelname());
		
		Mi625Example example = new Mi625Example();
		example.createCriteria().andAppoidEqualTo(form.getAppointid());
		
		int result = mi625Dao.updateByExampleSelective(mi625, example);
		
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约编号:"+form.getAppointid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(),"预约编号");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.service.AppApi303Service#appApi30307(com.yondervision
	 * .mi.form.AppApiCommonForm)
	 */
	@SuppressWarnings("unchecked")
	public List<AppApi30307Result> appApi30307(AppApi62601Form form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		List<Mi626> mi626 = null;
		Mi626Example mi626e=new Mi626Example();
		mi626e.setOrderByClause("centerid desc, appoattenid asc, validflag desc");
		Mi626Example.Criteria ca=mi626e.createCriteria();
		if(!form.getCenterId().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterId());
		}
		ca.andValidflagEqualTo("1");
		mi626=mi626Dao.selectByExample(mi626e);
		if (CommonUtil.isEmpty(mi626)) {
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),
					"预约注意事项");
		}
		List<AppApi30307Result> result=new ArrayList<AppApi30307Result>(1);
		String strinfo="";
		for(int i=0;i<mi626.size();i++){
			if(strinfo.length()>1){
				strinfo+="\n";
			}
			strinfo+=(i+1)+"、"+mi626.get(i).getTemplatename();
		}
		AppApi30307Result item=new AppApi30307Result();
		item.setTemplates(strinfo);
		result.add(item);
		return result;
	}
	/**
	 * 查询预约业务
	 */
	public List<AppApi30308Result> appApi30308(AppApiCommonForm form) throws Exception {
		// TODO Auto-generated method stub
		Logger log = LoggerUtil.getLogger();
		/*
		 * 参数验证
		 */
		if (CommonUtil.isEmpty(form.getCenterId())) {
			log.error(ERROR.PARAMS_NULL.getLogText("centerId"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}

		/*
		 * 查询预约业务类型
		 */
		Mi620Example mi620Example = new Mi620Example();
		mi620Example.createCriteria().andCenteridEqualTo(form.getCenterId())
				.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		mi620Example.setOrderByClause("appobusiid asc");
		List<Mi620> listMi620 = this.getMi620Dao().selectByExample(mi620Example);
		if (CommonUtil.isEmpty(listMi620)) {
			throw new NoRollRuntimeErrorException(APP_ALERT.NO_DATA.getValue(),"预约业务类型");
		}

		/*
		 * 映射返回结果
		 */
		List<AppApi30308Result> result = new ArrayList<AppApi30308Result>(listMi620.size());
		for (Iterator<Mi620> iterator = listMi620.iterator(); iterator.hasNext();) {
			Mi620 mi620 = (Mi620) iterator.next();
			AppApi30308Result item = new AppApi30308Result();
			item.setAppobusiid(mi620.getAppobusiid());
			item.setAppobusiname(mi620.getAppobusiname());
			result.add(item);
		}
		return result;
	}

	//根据身份证查询预约情况，未预约的或未查到信息的返回空
	public JSONObject appApi30314(AppApi30301Form form)throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("recode", "999999");
		Logger log = LoggerUtil.getLogger();
		String centerid = form.getCenterid();
		String bodyCardNumber = form.getBodyCardNumber();//身份证
		String websiteCode = form.getWebsiteCode();//预约网点编号
		if(CommonUtil.isEmpty(centerid)){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if(CommonUtil.isEmpty(bodyCardNumber)){
			log.error(ERROR.PARAMS_NULL.getLogText("身份证为空"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "身份证为空");
		}
		Mi029Example example = new Mi029Example();
		example.createCriteria().andCenteridEqualTo(centerid).andCertinumEqualTo(bodyCardNumber)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(example);
		if(list==null || list.isEmpty()){
			log.error(ERROR.PARAMS_NULL.getLogText("查询mi029为空"));
			obj.put("msg", "未查询到当日预约信息");
			return obj;
		}
		String personalid = list.get(0).getPersonalid();//个人信息主键
		Mi623Example mi623Example = new Mi623Example();
		mi623Example.createCriteria().andWebsiteCodeEqualTo(websiteCode).andCenteridEqualTo(centerid);
		List<Mi623> list623 = cmi623Dao.selectByExample(mi623Example);
		if(list623==null || list623.isEmpty()){
			obj.put("msg", "当前网点不存在");
			return obj;
		}
		
		Mi625Example mi625Example = new Mi625Example();
		mi625Example.createCriteria().andCenteridEqualTo(centerid).andPersonalidEqualTo(personalid).andAppodateEqualTo(CommonUtil.getDate())
		.andAppostateEqualTo("01").andValidflagEqualTo(Constants.IS_VALIDFLAG).andAppobranchidEqualTo(list623.get(0).getAppobranchid());
		List<Mi625> list2 = mi625Dao.selectByExample(mi625Example);
		if(list2==null || list2.isEmpty()){
			log.error(ERROR.PARAMS_NULL.getLogText("查询mi625为空"));
			obj.put("msg", "未查询到当日预约信息");
			return obj;
		}
		Mi625 mi625 = list2.get(0);
		
		//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String currFormat = sdf.format(new Date());
		long currTime = sdf.parse(currFormat).getTime();
		
		//9:00-11:00
		String[] str = mi625.getTimeinterval().trim().split("-");
		
		long time1 = sdf.parse(str[0]).getTime();//9:00
		long time2 = sdf.parse(str[1]).getTime();//11:00
		if(currTime<time1){
			obj.put("msg", "您预约的时间段是："+mi625.getTimeinterval()+"，请等候！");
			return obj;
		}
		if(currTime>time2){
			obj.put("msg", "您预约的时间段是："+mi625.getTimeinterval()+"，已过期！");
			return obj;
		}
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("apponum", mi625.getApponum()==null?"":mi625.getApponum());
		map.put("appobusiname", mi625.getAppobusiname()==null?"":mi625.getAppobusiname());
		map.put("appobusiid",mi625.getAppobusiid()==null?"":mi625.getAppobusiid());
		map.put("appodate", mi625.getAppodate()==null?"":mi625.getAppodate());
		map.put("appobranchname", mi625.getAppobranchname()==null?"":mi625.getAppobranchname());
		map.put("appostate", mi625.getAppostate()==null?"":mi625.getAppostate());
		map.put("timeinterval", mi625.getTimeinterval()==null?"":mi625.getTimeinterval());
		map.put("userid", mi625.getUserid()==null?"":mi625.getUserid());
		map.put("pidname", mi625.getPidname()==null?"":mi625.getPidname());
		map.put("channelname", mi625.getChannelname()==null?"":mi625.getChannelname());
		obj.put("recode", Constants.WEB_SUCCESS_CODE);
		obj.put("msg", "查询到当日预约信息");
		obj.put("result", map);
		return obj;
	}
	
	//根据节假日标志判断是否为节假日
	public List<Mi627> appApi30315(AppApi30309Form form)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String centerid = form.getCenterId();
		if(CommonUtil.isEmpty(centerid)){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if(CommonUtil.isEmpty(form.getStartdate())){
			log.error(ERROR.PARAMS_NULL.getLogText("开始日期"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "开始日期");
		}
		if(CommonUtil.isEmpty(form.getEnddate())){
			log.error(ERROR.PARAMS_NULL.getLogText("结束日期"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "结束日期");
		}
		Mi627Example example = new Mi627Example();
		example.createCriteria().andCenteridEqualTo(centerid)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)
		.andFestivaldateGreaterThanOrEqualTo(form.getStartdate())
		.andFestivaldateLessThanOrEqualTo(form.getEnddate());
		List<Mi627> list = cmi627Dao.selectByExample(example);
		if(list==null || list.isEmpty()){
			return null;
		}else{
			return list;
		}
	}
	
	//预约办结确认
	public JSONObject appApi30316(AppApi30301Form form)throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("recode", "999999");
		Logger log = LoggerUtil.getLogger();
		String centerid = form.getCenterid();
		String bodyCardNumber = form.getBodyCardNumber();//身份证
		String websiteCode = form.getWebsiteCode();//预约网点编号
		if(CommonUtil.isEmpty(centerid)){
			log.error(ERROR.PARAMS_NULL.getLogText("中心为空"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "城市中心代码");
		}
		if(CommonUtil.isEmpty(bodyCardNumber)){
			log.error(ERROR.PARAMS_NULL.getLogText("身份证为空"));
			throw new NoRollRuntimeErrorException(APP_ALERT.PARAMS_NULL
					.getValue(), "身份证为空");
		}
		Mi029Example example = new Mi029Example();
		example.createCriteria().andCenteridEqualTo(centerid).andCertinumEqualTo(bodyCardNumber)
		.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi029> list = cmi029DAO.selectByExample(example);
		if(list==null || list.isEmpty()){
			obj.put("msg", "未查询到当日预约信息");
			return obj;
		}
		String personalid = list.get(0).getPersonalid();//个人信息主键
		Mi623Example mi623Example = new Mi623Example();
		mi623Example.createCriteria().andWebsiteCodeEqualTo(websiteCode).andCenteridEqualTo(centerid);
		List<Mi623> list623 = cmi623Dao.selectByExample(mi623Example);
		if(list623==null || list623.isEmpty()){
			obj.put("msg", "当前网点不存在");
			return obj;
		}
		
		Mi625Example mi625Example = new Mi625Example();
		mi625Example.createCriteria().andCenteridEqualTo(centerid).andPersonalidEqualTo(personalid).andAppodateEqualTo(CommonUtil.getDate())
		.andAppostateEqualTo("01").andValidflagEqualTo(Constants.IS_VALIDFLAG).andAppobranchidEqualTo(list623.get(0).getAppobranchid())
		.andAppobusiidEqualTo(form.getAppobusiid());
		List<Mi625> list2 = mi625Dao.selectByExample(mi625Example);
		if(list2==null || list2.isEmpty()){
			obj.put("msg", "未查询到当日预约信息");
			return obj;
		}
		Mi625 mi625 = list2.get(0);
		mi625.setAppostate(form.getAppostate());
		mi625Dao.updateByPrimaryKey(mi625);
		obj.put("recode", Constants.WEB_SUCCESS_CODE);
		obj.put("msg", "预约办结确认成功");
		return obj;
	}
	
	public CMi607DAO getCmi607Dao() {
		return cmi607Dao;
	}

	public void setCmi607Dao(CMi607DAO cmi607Dao) {
		this.cmi607Dao = cmi607Dao;
	}

	public Mi110DAO getMi110Dao() {
		return mi110Dao;
	}

	public void setMi110Dao(Mi110DAO mi110Dao) {
		this.mi110Dao = mi110Dao;
	}
	public static void main(String[] args) throws Exception{
		AppApi30304Form form = new AppApi30304Form();
		form.setAppobranchid("105");
		form.setAppobranchname(	"住房公积金办事大厅");
		form.setAppobusiid("008");
		form.setAppobusiname("提取预约");
		form.setAppodate("2015-01-09");
		form.setAppotpldetailid("10");
		form.setBuzType("5341");
		form.setCenterId("00076000");
		form.setChannel("20");
		form.setNickname("66666111");
		form.setPhone("13632953415");
		form.setTimeinterval("09:15-09:30");
		form.setUserId("666666");
		AppApi303ServiceImpl impl = new AppApi303ServiceImpl();
		String result=impl.appApi30304( form);
		
	}

	public CMi029DAO getCmi029DAO() {
		return cmi029DAO;
	}

	public void setCmi029DAO(CMi029DAO cmi029dao) {
		cmi029DAO = cmi029dao;
	}

	public Mi007DAO getMi007Dao() {
		return mi007Dao;
	}

	public void setMi007Dao(Mi007DAO mi007Dao) {
		this.mi007Dao = mi007Dao;
	}

}
