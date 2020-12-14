package com.yondervision.mi.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.WeiXinMessageUtil;
import com.yondervision.mi.dao.CMi029DAO;
import com.yondervision.mi.dao.CMi031DAO;
import com.yondervision.mi.dao.CMi624DAO;
import com.yondervision.mi.dao.CMi625DAO;
import com.yondervision.mi.dao.Mi103DAO;
import com.yondervision.mi.dao.Mi625DAO;
import com.yondervision.mi.dao.Mi627DAO;
import com.yondervision.mi.dto.CMi625;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.dto.Mi624;
import com.yondervision.mi.dto.Mi625Example;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.dto.Mi627Example;
import com.yondervision.mi.result.WebApi62506_queryResult;
import com.yondervision.mi.service.WebApi625Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;

/** 
* @ClassName: WebApi623ServiceImpl 
* @Description: 预约业务类型维护实现
* @author sunxl
* @date Sep 29, 2013 2:56:45 PM   
* 
*/ 
public class WebApi625ServiceImpl implements WebApi625Service {
	
	@Autowired
	public Mi625DAO mi625Dao = null;
	private Mi627DAO mi627Dao=null;
	private CMi624DAO cmi624Dao=null;
	@Autowired
	private CMi031DAO cmi031DAO;
	@Autowired
	private CMi029DAO cmi029DAO;
	private Mi103DAO mi103Dao=null;
	@Autowired
	private CommonUtil commonUtil;
	
	
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}

	public CMi031DAO getCmi031DAO() {
		return cmi031DAO;
	}

	public void setCmi031DAO(CMi031DAO cmi031dao) {
		cmi031DAO = cmi031dao;
	}

	public CMi029DAO getCmi029DAO() {
		return cmi029DAO;
	}

	public void setCmi029DAO(CMi029DAO cmi029dao) {
		cmi029DAO = cmi029dao;
	}

	public Mi103DAO getMi103Dao() {
		return mi103Dao;
	}

	public void setMi103Dao(Mi103DAO mi103Dao) {
		this.mi103Dao = mi103Dao;
	}

	public void setMi625Dao(Mi625DAO mi625Dao) {
		this.mi625Dao = mi625Dao;
	}
	@Autowired
	private CMi625DAO cmi625Dao = null;

	public Mi627DAO getMi627Dao() {
		return mi627Dao;
	}

	public void setMi627Dao(Mi627DAO mi627Dao) {
		this.mi627Dao = mi627Dao;
	}
	public CMi624DAO getCmi624Dao() {
		return cmi624Dao;
	}

	public void setCmi624Dao(CMi624DAO cmi624Dao) {
		this.cmi624Dao = cmi624Dao;
	}

	public CMi625DAO getCmi625Dao() {
		return cmi625Dao;
	}

	public void setCmi625Dao(CMi625DAO cmi625Dao) {
		this.cmi625Dao = cmi625Dao;
	}

	
	public JSONObject webapi62504(CMi625 form, Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) {
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
		}
		String sql = null;
		String appobranchids = "";
		if(!CommonUtil.isEmpty(form.getAreaid())){
			String areaSql = null;
			if (Constants.YD_ADMIN.equals(centerid)){
				areaSql="select b.appobranchid from mi201 a join mi623 b on a.website_code = b.website_code where b.validflag = '"+Constants.IS_VALIDFLAG +"' and a.area_id = '"+ form.getAreaid()+"' and a.validflag = '"
						+Constants.IS_VALIDFLAG+"'";
			}else{
				areaSql="select b.appobranchid from mi201 a join mi623 b on a.website_code = b.website_code where b.validflag = '"+Constants.IS_VALIDFLAG +"' and b.centerid = '" +centerid+ "' and a.area_id = '"+ form.getAreaid()+"' and a.validflag = '"
						+Constants.IS_VALIDFLAG+"' and a.centerid = '" +centerid+ "'";
			}
			JSONObject objArea=CommonUtil.selectByPage(areaSql, 1, 100);//此处有分页，需要查出所有的 
			if(objArea!=null && !objArea.isEmpty()){
				HashMap map = (HashMap) JSONObject.toBean(objArea, HashMap.class);
				ArrayList<MorphDynaBean> list = (ArrayList<MorphDynaBean>)map.get("rows");
				for(MorphDynaBean bean:list){
					String id = bean.get("appobranchid").toString();
					appobranchids = appobranchids + id+ ",";
				} 
			}
			if(CommonUtil.isEmpty(appobranchids)){
				JSONObject result = new JSONObject();
				result.put("total", 0);
				result.put("rows", "[]");
				System.out.println("result.toString()=========="+result.toString());
				return result;
			}
		}
		
		if (Constants.YD_ADMIN.equals(centerid)){
			sql="select a.*,b.PERSONALID,b.CENTERID,b.USERNAME,b.CERTINUMTYPE,b.CERTINUM,b.CERTINUMCUSTOMERID,b.EMAIL,b.TEL,b.SEX,b.BIRTH,b.USELEVEL,b.VIP,b.SENSITIVE,b.UNITACCNUM,b.VALIDFLAG,b.DATEMODIFIED,b.FREEUSE1,b.FREEUSE2,b.FREEUSE3,b.FREEUSE4 from mi625 a join mi029 b on a.personalid = b.personalid where a.validflag = '"+Constants.IS_VALIDFLAG +"' and b.validflag = '"
					+ Constants.IS_VALIDFLAG + "'";
		}else{
			sql="select a.*,b.PERSONALID,b.CENTERID,b.USERNAME,b.CERTINUMTYPE,b.CERTINUM,b.CERTINUMCUSTOMERID,b.EMAIL,b.TEL,b.SEX,b.BIRTH,b.USELEVEL,b.VIP,b.SENSITIVE,b.UNITACCNUM,b.VALIDFLAG,b.DATEMODIFIED,b.FREEUSE1,b.FREEUSE2,b.FREEUSE3,b.FREEUSE4 from mi625 a join mi029 b on a.personalid = b.personalid where a.centerid = '"+centerid+ "' and a.validflag = '"+Constants.IS_VALIDFLAG + "' and b.validflag = '"+Constants.IS_VALIDFLAG +"' and b.centerid = '"
					+centerid +"'";	
		}
		
		if(!CommonUtil.isEmpty(form.getApponum())){
			sql += " and a.apponum like '%" +form.getApponum()+ "%'";
		}
		if(!CommonUtil.isEmpty(form.getAppobusiid())){
			sql += " and a.appobusiid = '" +form.getAppobusiid()+ "'";
		}
		if(!CommonUtil.isEmpty(form.getCertinum())){
			sql += " and b.certinum like '%" +form.getCertinum()+ "%'";
		}
		if(appobranchids !=null && !"".equals(appobranchids)){
			appobranchids = appobranchids.substring(0, appobranchids.length()-1);
			String[] ids = appobranchids.split(",");
			String appobranchid = "";
			for(String id:ids){
				appobranchid = appobranchid+ "'"+ id+ "',";
			}
			appobranchid = "("+appobranchid.substring(0, appobranchid.length()-1)+")";
			sql += " and a.appobranchid in " +appobranchid;
		}
		if(!CommonUtil.isEmpty(form.getPersonalname())){
			sql += " and b.username like '%" +form.getPersonalname()+ "%'";
		}
		if(!CommonUtil.isEmpty(form.getAppostate())){
			sql += " and a.appostate = '" +form.getAppostate()+ "'";
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			sql += " and a.channel = '" +form.getChannel()+ "'";
		}
		if(!CommonUtil.isEmpty(form.getPid())){
			sql += " and a.pid = '" +form.getPid()+ "'";
		}
		if(!CommonUtil.isEmpty(form.getStartdate()) && !CommonUtil.isEmpty(form.getEnddate())){
			sql += " and a.appodate >= '" + form.getStartdate() + "' and a.appodate <= '"+ form.getEnddate() +"'";
		}
		sql += " order by a.DATECREATED desc";
		JSONObject obj=CommonUtil.selectByPage(sql, page, rows); 
		System.out.println("resultobj.toString()=========="+obj.toString());

		return obj;
		
	}

	/** 查询某一个月的预约情况 **/
	public List<Mi627> webapi62505(CMi625 form) throws Exception {
		// TODO Auto-generated method stub
		/** 查询每天最大可预约数量 **/
		Mi624 mi624=new Mi624();
		mi624.setAppobranchid(form.getAppobranchid());
		mi624.setCenterid(form.getCenterid());
		int appocnts=cmi624Dao.selectMi624SumAppocnt(mi624);
		if(appocnts==0){
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"维护中心["+form.getCenterid()+"]的网点业务预约时段信息");
		}
		
		/** 查询某月节假日信息 **/
		Mi627Example mi627e=new Mi627Example();
		Mi627Example.Criteria ca627=mi627e.createCriteria();
		ca627.andCenteridEqualTo(form.getCenterid());
		ca627.andValidflagEqualTo("1");
		String yearMonth=form.getYearmonth();
		if(CommonUtil.isEmpty(yearMonth)){
			throw new TransRuntimeErrorException(WEB_ALERT.PARAMS_NULL.getValue(),"查询年月yyyy-mm"); 
		}
		if(yearMonth.length()==10){
			yearMonth=yearMonth.substring(0, 8);
		}
		ca627.andFestivaldateLike(yearMonth+"%");
		List<Mi627> list627=mi627Dao.selectByExample(mi627e);
		if(list627==null||list627.size()==0){
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"["+form.getYearmonth()+"]节假日信息");
		}
		
		/** 查询统计预约情况 **/
		
		for(int i=0;i<list627.size();i++){
			if(list627.get(i).getFestivalflag().equals("0")){
				Mi625Example mi625e=new Mi625Example();
				Mi625Example.Criteria ca =mi625e.createCriteria();
				ca.andAppobranchidEqualTo(form.getAppobranchid());
				ca.andAppobusiidEqualTo(form.getAppobusiid());
				ca.andCenteridEqualTo(form.getCenterId());
				ca.andValidflagEqualTo("1");
				String appodate="";
				appodate=list627.get(i).getFestivaldate();
				ca.andAppodateEqualTo(appodate);
				int appoNum=mi625Dao.countByExample(mi625e);
				list627.get(i).setFreeuse2(appoNum+"/"+appocnts);
			}else{
				list627.get(i).setFreeuse2(" ");
			}
		}
		return list627;
	}

	/** 查询某一天的预约明细情况 **/
	public WebApi62506_queryResult webapi63506(CMi625 form) throws Exception {
		// TODO Auto-generated method stub
		return cmi625Dao.selectOneDayAppoDetail(form);
	}

	public int webapi62503(CMi625 form){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.SELF_LOG.getLogText("[+]webApi625Service webapi62503" ));
		String[] appoids = form.getAppoid().split(",");
		HashMap map = new HashMap();
		map.put("appostate", form.getAppostate());
		map.put("appoids",appoids);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		if(form.getAppostate().equals("04")){
			map.put("completedate",sf.format(date));
			map.put("datecanceled","");
		}else{
			map.put("completedate","");
			map.put("datecanceled",sf.format(date));
		}
		map.put("datemodified",CommonUtil.getSystemDate());
		int result = getCmi625Dao().updateAppostate(map);
		if (0 == result) {
			log.error(ERROR.ERRCODE_LOG_800004.getLogText("预约信息:"+ form.getAppoid()));
			throw new TransRuntimeErrorException(WEB_ALERT.DEL_NO_DATA.getValue(), "预约信息表MI625");
		}
		return result;
	}
}
