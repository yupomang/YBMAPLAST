package com.yondervision.mi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.exolab.castor.types.Month;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.WeiXinMessageUtil;
import com.yondervision.mi.dao.CMi103DAO;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.service.WebApi103Service;
import com.yondervision.mi.util.PropertiesReader;

/**
 * @ClassName: WebApp103ServiceImpl
 * @Description: 用户处理
 * @author Caozhongyan
 * @date Oct 4, 2013 3:47:38 PM
 * 
 */
public class WebApi103ServiceImpl implements WebApi103Service {
	protected final Logger log = LoggerUtil.getLogger();
	private CMi103DAO cmi103Dao = null;

	public CMi103DAO getCmi103Dao() {
		return cmi103Dao;
	}
	static String weixinAttention="";
	public void setCmi103Dao(CMi103DAO cmi103Dao) {
		this.cmi103Dao = cmi103Dao;
	}
	private Mi001DAO mi001Dao = null;
	
	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}
	public JSONObject webapi10304(CMi103 form, Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String sql ="select wxcountnum,wxcreatedate,(select count(*) from mi110 where SUBSTR(datecreated,1,10)<=wxcreatedate "
				+ "and DATEMODIFIED=DATECREATED and CHANNEL='20') wxsumcount,appcountnum,appcreatedate,(select count(*) "
				+ "from mi103 where  SUBSTR(datecreated,1,10)<=appcreatedate) appsumcount from (select count(*) "
				+ "wxcountnum,SUBSTR(datecreated,1,10) wxcreatedate from mi110 where DATEMODIFIED=DATECREATED "
				+ "and CHANNEL='20' group by SUBSTR(datecreated,1,10) having SUBSTR(datecreated,1,10)>='"+form.getStartdate()+"' "
				+ "and SUBSTR(datecreated,1,10) <= '"+form.getEnddate()+"') full join (select count(*) appcountnum,"
				+ "SUBSTR(datecreated,1,10) appcreatedate from mi103 group by SUBSTR(datecreated,1,10) "
				+ "having SUBSTR(datecreated,1,10)>='"+form.getStartdate()+"' "
						+ "and  SUBSTR(datecreated,1,10) <= '"+form.getEnddate()+"') "
				+ "on wxcreatedate=appcreatedate order by wxcreatedate,appcreatedate";
		List<HashMap> allresult = cmi103Dao.selectWebapi10304All(form);
		Calendar dayStart = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		JSONArray result=new JSONArray();
		System.out.println(allresult.size());
		String appsumcount="0";
		String wxsumcount="0";
		for(int i=0;i<allresult.size();i++){
			HashMap map = allresult.get(i);
			if(!map.get("wxsumcount").toString().equals("0")){
				wxsumcount=(Integer.parseInt(map.get("wxsumcount").toString())-Integer.parseInt(map.get("wxcountnum")==null?"0":map.get("wxcountnum").toString()))+"";
				break;
			}
		}
		for(int i=0;i<allresult.size();i++){
			HashMap map = allresult.get(i);
			if(!map.get("appsumcount").toString().equals("0")){
				appsumcount=(Integer.parseInt(map.get("appsumcount").toString())-Integer.parseInt(map.get("appcountnum")==null?"0":map.get("appcountnum").toString()))+"";
				break;
			}
		}
		HashMap wxAtten = getWeixinAttention(form,request,response,false);
		HashMap wxAttenTotal = (HashMap) wxAtten.get("wxAttenTotal");
		HashMap wxAttenNew = (HashMap) wxAtten.get("wxAttenNew");
		
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			HashMap temp = new HashMap();
			boolean flag=false;
			HashMap tempJson = new HashMap();
			for(int i=0;i<allresult.size();i++){
				tempJson=allresult.get(i);
				if(tmpdate.equals(tempJson.get("wxcreatedate"))&&tmpdate.equals(tempJson.get("appcreatedate"))){
					appsumcount=tempJson.get("appsumcount").toString();
					wxsumcount=tempJson.get("wxsumcount").toString();
					flag=true;
					break;
				}else if(tmpdate.equals(tempJson.get("wxcreatedate"))&&!tmpdate.equals(tempJson.get("appcreatedate"))){
					tempJson.put("appcreatedate", tmpdate);
					tempJson.put("appcountnum",0);
					tempJson.put("appsumcount",appsumcount);
					wxsumcount=tempJson.get("wxsumcount").toString();
					flag=true;
					break;
				}else if(!tmpdate.equals(tempJson.get("wxcreatedate"))&&tmpdate.equals(tempJson.get("appcreatedate"))){
					tempJson.put("wxcreatedate", tmpdate);
					tempJson.put("wxcountnum",0);
					tempJson.put("wxsumcount",wxsumcount);
					appsumcount=tempJson.get("appsumcount").toString();
					flag=true;
					break;
				}
			}
			if(!flag){
				tempJson.put("wxcreatedate", tmpdate);
				tempJson.put("wxcountnum",0);
				tempJson.put("wxsumcount",wxsumcount);
				tempJson.put("appcreatedate", tmpdate);
				tempJson.put("appcountnum",0);
				tempJson.put("appsumcount",appsumcount);
			}
			tempJson.put("wxattcountnum", wxAttenNew.get(tmpdate)==null?"0":wxAttenNew.get(tmpdate));
			tempJson.put("wxattsumcount", wxAttenTotal.get(tmpdate)==null?"0":wxAttenTotal.get(tmpdate));
			result.add(tempJson);
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		JSONObject obj = new JSONObject();
		JSONArray arr=new JSONArray();
		for(int i=result.size();i>0;i--){
			arr.add(result.get(i-1));
		}
	    obj.put("rows", arr);
		return obj;
	}

	
	public List<HashMap> webapi10304New(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> appresult = cmi103Dao.selectWebapi10304App(form);
		List<HashMap> wxresult = cmi103Dao.selectWebapi10304Wx(form);
		List<HashMap> result = new ArrayList();
		HashMap allresult = new HashMap();
		Calendar dayStart = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		HashMap wxdatemap=new HashMap();
		HashMap appdatemap=new HashMap();
		for (int i = 0; i < wxresult.size(); i++) {
			wxdatemap.put(wxresult.get(i).get("createdate"), wxresult.get(i).get("countnum"));
		}
		for (int i = 0; i < appresult.size(); i++) {
			appdatemap.put(appresult.get(i).get("createdate"), appresult.get(i).get("countnum"));
		}
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		ArrayList<String> legendlist=new ArrayList();
		legendlist.add("微信关注");
		legendlist.add("微信绑定");
		legendlist.add("手机注册");
    	List<String> xaxis = new ArrayList<String>();
		ArrayList<JSONObject> singleSeries = new ArrayList<JSONObject>();
		JSONObject wxdata=new JSONObject();
    	List<String> wxlist = new ArrayList<String>();
		JSONObject appdata=new JSONObject();
    	List<String> applist = new ArrayList<String>();
    	
		HashMap wxAttendatemap = getWeixinAttention(form,request,response,true).get("wxAttenNew");
		Object sumcountwxAtten = "0";
		JSONObject wxAttendata=new JSONObject();
		List wxAttenlist = new ArrayList();	
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			if(wxAttendatemap.get(tmpdate)==null){
				wxAttenlist.add(sumcountwxAtten);
			}else{
				wxAttenlist.add(wxAttendatemap.get(tmpdate));
				sumcountwxAtten=wxAttendatemap.get(tmpdate);
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		wxAttendata.put("name", "微信关注");
		wxAttendata.put("type", "line");
		wxAttendata.put("data", wxAttenlist);
		singleSeries.add(wxAttendata);
		dayStarttmp.setTime(startdate);
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			xaxis.add(tmpdate);
			if(wxdatemap.get(tmpdate)==null){
				wxlist.add(0+"");
			}else{
				wxlist.add(wxdatemap.get(tmpdate).toString());
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		wxdata.put("name", "微信绑定");
		wxdata.put("type", "line");
		wxdata.put("data", wxlist);
		singleSeries.add(wxdata);
		dayStarttmp.setTime(startdate);
		//过滤每天的日期，若某天微信或app没有用户数据，讲新增数信息记为0
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			if(appdatemap.get(tmpdate)==null){
				applist.add(0+"");
			}else{
				applist.add(appdatemap.get(tmpdate).toString());
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		appdata.put("name", "手机注册");
		appdata.put("type", "line");
		appdata.put("data", applist);
		singleSeries.add(appdata);
		
		allresult.put("legend", legendlist);
		allresult.put("xaxis", xaxis);
		allresult.put("data", JSONArray.fromObject(singleSeries));
		result.add(allresult);
		return result;
	}
	/**
	 * 查询app和微信累计信息
	 */
	
	public List<HashMap> webapi10304Sum(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> appresult = cmi103Dao.selectWebapi10304App(form);
		List<HashMap> wxresult = cmi103Dao.selectWebapi10304Wx(form);
		List<HashMap> result = new ArrayList();
		HashMap allresult = new HashMap();
		Calendar dayStart = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		HashMap wxdatemap=new HashMap();
		HashMap appdatemap=new HashMap();
		HashMap wxAttendatemap=new HashMap();
		for (int i = 0; i < wxresult.size(); i++) {
			wxdatemap.put(wxresult.get(i).get("createdate"), wxresult.get(i).get("sumcount"));
		}
		for (int i = 0; i < appresult.size(); i++) {
			appdatemap.put(appresult.get(i).get("createdate"), appresult.get(i).get("sumcount"));
		}
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		Object sumcountapp=appresult.size()==0?0:appresult.get(0).get("sumcount");
		Object sumcountwx=wxresult.size()==0?0:wxresult.get(0).get("sumcount");
		ArrayList<String> legendlist=new ArrayList();
		legendlist.add("微信关注");
		legendlist.add("微信绑定");
		legendlist.add("手机注册");
    	List<String> xaxis = new ArrayList<String>();
		ArrayList<JSONObject> singleSeries = new ArrayList<JSONObject>();
		JSONObject wxdata=new JSONObject();
    	List wxlist = new ArrayList();
		JSONObject appdata=new JSONObject();
    	List applist = new ArrayList();
		JSONObject wxAttendata=new JSONObject();
		List wxAttenlist = new ArrayList();
		
		//过滤每天的日期，若某天微信活app没有用户数据，讲累计信息记为上一天的数据
		wxAttendatemap = getWeixinAttention(form,request,response,false).get("wxAttenTotal");
		Object sumcountwxAtten = "0";
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			if(wxAttendatemap.get(tmpdate)==null){
				wxAttenlist.add(sumcountwxAtten);
			}else{
				wxAttenlist.add(wxAttendatemap.get(tmpdate));
				sumcountwxAtten=wxAttendatemap.get(tmpdate);
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		wxAttendata.put("name", "微信关注");
		wxAttendata.put("type", "line");
		wxAttendata.put("data", wxAttenlist);
		singleSeries.add(wxAttendata);

		dayStarttmp.setTime(startdate);
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			xaxis.add(tmpdate);
			if(wxdatemap.get(tmpdate)==null){
				wxlist.add(sumcountwx);
			}else{
				wxlist.add(wxdatemap.get(tmpdate));
				sumcountwx =wxdatemap.get(tmpdate);
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		wxdata.put("name", "微信绑定");
		wxdata.put("type", "line");
		wxdata.put("data", wxlist);
		singleSeries.add(wxdata);
		dayStarttmp.setTime(startdate);
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			if(appdatemap.get(tmpdate)==null){
				applist.add(sumcountapp);
			}else{
				applist.add(appdatemap.get(tmpdate));
				sumcountapp=appdatemap.get(tmpdate);
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		appdata.put("name", "手机注册");
		appdata.put("type", "line");
		appdata.put("data", applist);
		singleSeries.add(appdata);
		
		allresult.put("legend", legendlist);
		allresult.put("xaxis", xaxis);
		allresult.put("data", JSONArray.fromObject(singleSeries));
		result.add(allresult);
		return result;
	}
	public JSONObject webapi10301(CMi103 form,Integer page,Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String centerlist = form.getCenterlist();
		JSONObject result = new JSONObject();
		if(!form.getCenterId().equals("00000000")){
			centerlist = form.getCenterId();
		}
		Mi001Example example = new Mi001Example();
		String[] center = centerlist.split(",");
		
		List seleCond = new ArrayList();
		for(int i=0;i<center.length;i++){
			seleCond.add(center[i]);
		}
		example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG).andCenteridIn(seleCond);
		List<Mi001> list = mi001Dao.selectByExample(example);
		HashMap centerNameMap = new HashMap();
		for(int i = 0;i<list.size();i++){
			Mi001 temp = list.get(i);
			centerNameMap.put(temp.getCenterid(), temp.getCentername());
		}
		String centerlist1 = "";
		for(int i =0;i<center.length;i++){
			if(i>0)centerlist1+=",";
			//if(i!=0)centerlist1+="'";
			centerlist1 +="'"+center[i]+"'";
		}
		form.setCenterlist(centerlist);
		HashMap<String,HashMap> wxatten = getWeixinAttentionGroupbyCenter(form,request,response,true);
		HashMap wxattenTotal=wxatten.get("wxAttenTotal");
		HashMap wxattenNew=wxatten.get("wxAttenNew");
		form.setCenterlist(centerlist1);
		List<HashMap> resultapregis = cmi103Dao.selectWebapi10301app(form);
		List<HashMap> resultwxbind = cmi103Dao.selectWebapi10301wx(form);
		HashMap appTotal = new HashMap();
		HashMap appNew = new HashMap();
		HashMap wxbindTotal = new HashMap();
		HashMap wxbindNew = new HashMap();
		for(int i=0;i<resultapregis.size();i++){
			HashMap temp = resultapregis.get(i);
			appTotal.put(temp.get("centerid"), temp.get("sumcount"));
			appNew.put(temp.get("centerid"), temp.get("countnum"));
		}
		for(int i=0;i<resultwxbind.size();i++){
			HashMap temp = resultwxbind.get(i);
			wxbindTotal.put(temp.get("centerid"), temp.get("sumcount"));
			wxbindNew.put(temp.get("centerid"), temp.get("countnum"));
		}
		List alltotallist = new ArrayList();
		List allnewlist = new ArrayList();
		List allapptotallist = new ArrayList();
		List allappnewlist = new ArrayList();
		List allwxtotallist = new ArrayList();
		List allwxnewlist = new ArrayList();
		List allcenterLegend = new ArrayList();
		JSONObject perCenterObj = new JSONObject();
		for(int i =0;i<center.length;i++){
			String centerid = center[i];
			allcenterLegend.add(centerNameMap.get(centerid));
			//每个中心所有累计数
			JSONObject alltotalobj = new JSONObject();
			alltotalobj.put("centerid", centerid);
			alltotalobj.put("name", centerNameMap.get(centerid));
			int totalnum = Integer.parseInt(wxattenTotal.get(centerid)==null?"0":wxattenTotal.get(centerid).toString())+Integer.parseInt(appTotal.get(centerid)==null?"0":appTotal.get(centerid).toString());
			alltotalobj.put("value", totalnum);
			//每个中心所有新增数
			JSONObject allnewobj = new JSONObject();
			allnewobj.put("centerid", centerid);
			allnewobj.put("name", centerNameMap.get(centerid));
			int newnum = Integer.parseInt(wxattenNew.get(centerid)==null?"0":wxattenNew.get(centerid).toString())+Integer.parseInt(appNew.get(centerid)==null?"0":appNew.get(centerid).toString());
			allnewobj.put("value", newnum);
			
			alltotallist.add(alltotalobj);
			allnewlist.add(allnewobj);
			
			//每个中心app累计数
			JSONObject allapptotalobj = new JSONObject();
			allapptotalobj.put("centerid", centerid);
			allapptotalobj.put("name", centerNameMap.get(centerid));
			int apptotalnum = Integer.parseInt(appTotal.get(centerid)==null?"0":appTotal.get(centerid).toString());
			allapptotalobj.put("value", apptotalnum);
			//每个中心app新增数
			JSONObject allappnewobj = new JSONObject();
			allappnewobj.put("centerid", centerid);
			allappnewobj.put("name", centerNameMap.get(centerid));
			int appnewnum = Integer.parseInt(appNew.get(centerid)==null?"0":appNew.get(centerid).toString());
			allappnewobj.put("value", appnewnum);
			
			allapptotallist.add(allapptotalobj);
			allappnewlist.add(allappnewobj);
			
			//每个中心wx累计数
			JSONObject allwxtotalobj = new JSONObject();
			allwxtotalobj.put("centerid", centerid);
			allwxtotalobj.put("name", centerNameMap.get(centerid));
			int wxtotalnum = Integer.parseInt(wxattenTotal.get(centerid)==null?"0":wxattenTotal.get(centerid).toString());
			allwxtotalobj.put("value", wxtotalnum);
			//每个中心wx新增数
			JSONObject allwxnewobj = new JSONObject();
			allwxnewobj.put("centerid", centerid);
			allwxnewobj.put("name", centerNameMap.get(centerid));
			int wxnewnum = Integer.parseInt(wxattenNew.get(centerid)==null?"0":wxattenNew.get(centerid).toString());
			allwxnewobj.put("value", wxnewnum);
			
			allwxtotallist.add(allwxtotalobj);
			allwxnewlist.add(allwxnewobj);
			
			//每个中心累计数（手机和微信分开）
			List perCenterTotallist = new ArrayList();
			JSONObject perCenterTotalapp = new JSONObject();
			perCenterTotalapp.put("channel", "10");
			perCenterTotalapp.put("name", "App注册");
			perCenterTotalapp.put("value", appTotal.get(centerid)==null?"0":appTotal.get(centerid).toString());
			JSONObject perCenterTotalWx = new JSONObject();
			perCenterTotalWx.put("channel", "20");
			perCenterTotalWx.put("name", "微信关注(绑定和未绑定)");
			perCenterTotalWx.put("value", wxattenTotal.get(centerid)==null?"0":wxattenTotal.get(centerid).toString());
			perCenterTotallist.add(perCenterTotalapp);
			perCenterTotallist.add(perCenterTotalWx);
			//每个中心新增数（手机和微信分开）
			List perCenterNewlist = new ArrayList();
			JSONObject perCenterNewapp = new JSONObject();
			perCenterNewapp.put("channel", "10");
			perCenterNewapp.put("name", "App注册");
			perCenterNewapp.put("value", appNew.get(centerid)==null?"0":appNew.get(centerid).toString());
			JSONObject perCenterNewWx = new JSONObject();
			perCenterNewWx.put("channel", "20");
			perCenterNewWx.put("name", "微信关注(绑定和未绑定)");
			perCenterNewWx.put("value", wxattenNew.get(centerid)==null?"0":wxattenNew.get(centerid).toString());
			perCenterNewlist.add(perCenterNewapp);
			perCenterNewlist.add(perCenterNewWx);
			
			//各个中心微信绑定与不绑定累计
			List perCenterBindTotallist = new ArrayList();
			JSONObject perCenterBindTotal = new JSONObject();
			perCenterBindTotal.put("bindflag", "1");
			perCenterBindTotal.put("name", "微信绑定");
			perCenterBindTotal.put("value", wxbindTotal.get(centerid)==null?"0":wxbindTotal.get(centerid).toString());
			JSONObject perCenterUnbindTotal = new JSONObject();
			perCenterUnbindTotal.put("bindflag", "0");
			perCenterUnbindTotal.put("name", "微信未绑定");
			int unbindTotal = Integer.parseInt(wxattenTotal.get(centerid)==null?"0":wxattenTotal.get(centerid).toString())-Integer.parseInt(wxbindTotal.get(centerid)==null?"0":wxbindTotal.get(centerid).toString());
			perCenterUnbindTotal.put("value", unbindTotal);
			perCenterBindTotallist.add(perCenterBindTotal);
			perCenterBindTotallist.add(perCenterUnbindTotal);
			
			//各个中心微信绑定与不绑定新增
			List perCenterBindNewlist = new ArrayList();
			JSONObject perCenterBindNew = new JSONObject();
			perCenterBindNew.put("bindflag", "1");
			perCenterBindNew.put("name", "微信绑定");
			perCenterBindNew.put("value", wxbindNew.get(centerid)==null?"0":wxbindNew.get(centerid).toString());
			JSONObject perCenterUnbindNew = new JSONObject();
			perCenterUnbindNew.put("bindflag", "0");
			perCenterUnbindNew.put("name", "微信未绑定");
			int unbindNew = Integer.parseInt(wxattenNew.get(centerid)==null?"0":wxattenNew.get(centerid).toString())-Integer.parseInt(wxbindNew.get(centerid)==null?"0":wxbindNew.get(centerid).toString());
			perCenterUnbindNew.put("value", unbindNew);
			perCenterBindNewlist.add(perCenterBindNew);
			perCenterBindNewlist.add(perCenterUnbindNew);
			
			JSONObject perCenterObjtemp = new JSONObject();
			JSONObject perCenterAllTotal =  new JSONObject();
			JSONObject perCenterAllNew =  new JSONObject();
			JSONObject perCenterPartTotal =  new JSONObject();
			JSONObject perCenterPartNew =  new JSONObject();
			ArrayList<String> legendAll=new ArrayList();
			legendAll.add("微信关注(绑定和未绑定)");
			legendAll.add("App注册");
			perCenterAllTotal.put("legend", legendAll);
			perCenterAllTotal.put("data", perCenterTotallist);
			perCenterAllNew.put("legend", legendAll);
			perCenterAllNew.put("data", perCenterNewlist);
			JSONObject perCenterPart =  new JSONObject();
			ArrayList<String> legendPart=new ArrayList();
			legendPart.add("微信绑定");
			legendPart.add("微信未绑定");
			perCenterPartTotal.put("legend", legendPart);
			perCenterPartTotal.put("data", perCenterBindTotallist);
			perCenterPartNew.put("legend", legendPart);
			perCenterPartNew.put("data", perCenterBindNewlist);
			
			perCenterObjtemp.put("allTotal", perCenterAllTotal);
			perCenterObjtemp.put("allNew", perCenterAllNew);
			perCenterObjtemp.put("bindTotal", perCenterPartTotal);
			perCenterObjtemp.put("bindNew", perCenterPartNew);
			
			perCenterObj.put(centerid, perCenterObjtemp);
		}
		JSONObject allCenterTotal =  new JSONObject();
		JSONObject allCenterNew =  new JSONObject();
		allCenterTotal.put("legend", allcenterLegend);
		allCenterTotal.put("data", alltotallist);
		allCenterNew.put("legend", allcenterLegend);
		allCenterNew.put("data", allnewlist);

		JSONObject allCenterAppTotal =  new JSONObject();
		JSONObject allCenterAppNew =  new JSONObject();
		JSONObject allCenterWxTotal =  new JSONObject();
		JSONObject allCenterWXNew =  new JSONObject();
		allCenterAppTotal.put("legend", allcenterLegend);
		allCenterAppTotal.put("data", allapptotallist);
		allCenterAppNew.put("legend", allcenterLegend);
		allCenterAppNew.put("data", allappnewlist);
		allCenterWxTotal.put("legend", allcenterLegend);
		allCenterWxTotal.put("data", allwxtotallist);
		allCenterWXNew.put("legend", allcenterLegend);
		allCenterWXNew.put("data", allwxnewlist);
		result.put("allAppTotal", allCenterAppTotal);
		result.put("allAppNew", allCenterAppNew);
		result.put("allWxTotal", allCenterWxTotal);
		result.put("allWxNew", allCenterWXNew);
		
		result.put("allTotal", allCenterTotal);
		result.put("allNew", allCenterNew);
		result.put("perCenterObj", perCenterObj);
		
		return result;
	}
	public JSONObject webapi10301All(CMi103 form,Integer page,Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
	public HashMap<String,HashMap> getWeixinAttention(CMi103 form,HttpServletRequest request, HttpServletResponse response,boolean flag) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		form.setEnddate(sdf.format(dayEnd.getTime()));
		if(flag){
			String centerid=form.getCenterId();
			String url = PropertiesReader.getProperty(
						Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/luser";
			if("00000000".equals(form.getCenterId())){
				centerid=PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "weixinAttenCenterUnComputation").trim();
			}
			String value = "{\"centerid\":\""+centerid+"\",\"sublist\":\"1\",\"startDate\":\""+form.getStartdate()+"\",\"endDate\":\""+form.getEnddate()+"\"}";
			WeiXinMessageUtil weixin = new WeiXinMessageUtil();
			weixinAttention=weixin.post(url, value, request, response);
		}
		JSONObject msgobj = new JSONObject().fromObject(weixinAttention);
		if(!"0".equals(msgobj.getString("errcode"))){
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信关注数据获取失败，请重试！");
		}
		JSONObject wxAttenResult = msgobj.getJSONObject("result");
//		JSONObject wxAttenResult = new JSONObject();
		HashMap wxAttenTotal = new HashMap();
		HashMap wxAttenNew = new HashMap();
		for(Object key:wxAttenResult.keySet()){
			JSONObject temp = wxAttenResult.getJSONObject(key.toString());
			for(Object keyinner:temp.keySet()){
				JSONObject tempinner = temp.getJSONObject(keyinner.toString());
				if(wxAttenTotal.get(keyinner)==null){
					wxAttenTotal.put(keyinner, tempinner.getString("total"));
				}else{
					wxAttenTotal.put(keyinner, Integer.parseInt(tempinner.getString("total"))+Integer.parseInt(wxAttenTotal.get(keyinner).toString()));
					System.out.println(tempinner.getString("total")+"==="+wxAttenTotal.get(keyinner).toString());
					System.out.println(tempinner.getString("newpeople")+"==="+wxAttenNew.get(keyinner).toString());
				}
				if(wxAttenNew.get(keyinner)==null){
					wxAttenNew.put(keyinner, tempinner.getString("newpeople"));
				}else{
					wxAttenNew.put(keyinner, Integer.parseInt(tempinner.getString("newpeople"))+Integer.parseInt(wxAttenNew.get(keyinner).toString()));
				}
			}
		}
		HashMap<String,HashMap> result = new HashMap<String,HashMap>();
		result.put("wxAttenTotal", wxAttenTotal);
		result.put("wxAttenNew", wxAttenNew);
		return result;
	}
	public HashMap<String,HashMap> getWeixinAttentionGroupbyCenter(CMi103 form,HttpServletRequest request, HttpServletResponse response,boolean flag) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		form.setEnddate(sdf.format(dayEnd.getTime()));
		if(flag){
			String centerid=form.getCenterlist();
			String url = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/luser";
			String value = "{\"centerid\":\""+centerid+"\",\"sublist\":\"1\",\"startDate\":\""+form.getStartdate()+"\",\"endDate\":\""+form.getEnddate()+"\"}";
			WeiXinMessageUtil weixin = new WeiXinMessageUtil();
			weixinAttention=weixin.post(url, value, request, response);
		}
		JSONObject msgobj = new JSONObject().fromObject(weixinAttention);
		if(!"0".equals(msgobj.getString("errcode"))){
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信关注数据获取失败，请重试！");
		}
		JSONObject wxAttenResult = msgobj.getJSONObject("result");
//		JSONObject wxAttenResult = new JSONObject();
		HashMap wxAttenTotal = new HashMap();
		HashMap wxAttenNew = new HashMap();
//		dayEnd.setTime(enddate);
		for(Object key:wxAttenResult.keySet()){
			JSONObject temp = wxAttenResult.getJSONObject(key.toString());
			dayStarttmp.setTime(startdate);
			while (dayStarttmp.before(dayEnd)) {
				String tmpdate=sdf.format(dayStarttmp.getTime());
				JSONObject tempinner = temp.getJSONObject(tmpdate);
				if(wxAttenNew.get(key)==null){
					wxAttenNew.put(key, tempinner==null?"0":tempinner.getString("newpeople"));
				}else{
					wxAttenNew.put(key, Integer.parseInt(tempinner==null?"0":tempinner.getString("newpeople"))+Integer.parseInt(wxAttenNew.get(key).toString()));
				}
				dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
				if(dayStarttmp.equals(dayEnd))wxAttenTotal.put(key, tempinner==null?"0":tempinner.getString("total"));
			}
		}
		HashMap<String,HashMap> result = new HashMap<String,HashMap>();
		result.put("wxAttenTotal", wxAttenTotal);
		result.put("wxAttenNew", wxAttenNew);
		return result;
	}
	/**
	 * 查询app用户统计图
	 */
	public List<HashMap> webapi10305(CMi103 form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> appresult = cmi103Dao.selectWebapi10304App(form);
		
		List<HashMap> result = new ArrayList();
		HashMap allresult = new HashMap();
		Calendar dayStart = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		
		HashMap appsumdatemap=new HashMap();
		HashMap appnewdatemap=new HashMap();
		
		for (int i = 0; i < appresult.size(); i++) {
			appsumdatemap.put(appresult.get(i).get("createdate"), appresult.get(i).get("sumcount"));
			appnewdatemap.put(appresult.get(i).get("createdate"), appresult.get(i).get("countnum"));
		}
		
		
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		ArrayList<String> legendlist=new ArrayList();
		legendlist.add("累计人数");
		legendlist.add("新增人数");
    	List<String> xaxis = new ArrayList<String>();
		ArrayList<JSONObject> singleSeries = new ArrayList<JSONObject>();
		
		JSONObject appsumdata=new JSONObject();
		Object sumcountapp=appresult.size()==0?0:appresult.get(0).get("sumcount");
    	List appsumlist = new ArrayList();
    	
    	JSONObject appnewdata=new JSONObject();
//		Object sumcountapp=appresult.size()==0?0:appresult.get(0).get("sumcount");
    	List<String> appnewlist = new ArrayList<String>();
    	
		dayStarttmp.setTime(startdate);
		//过滤每天的日期，若某天微信或app没有用户数据，讲新增数信息记为0
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			xaxis.add(tmpdate);
			if(appnewdatemap.get(tmpdate)==null){
				appnewlist.add(0+"");
			}else{
				appnewlist.add(appnewdatemap.get(tmpdate).toString());
			}
			if(appsumdatemap.get(tmpdate)==null){
				appsumlist.add(sumcountapp);
			}else{
				appsumlist.add(appsumdatemap.get(tmpdate));
				sumcountapp=appsumdatemap.get(tmpdate);
			}
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		appsumdata.put("name", "累计人数");
		appsumdata.put("type", "line");
		appsumdata.put("data", appsumlist);
		singleSeries.add(appsumdata);
		
		appnewdata.put("name", "新增人数");
		appnewdata.put("type", "line");
		appnewdata.put("data", appnewlist);
		singleSeries.add(appnewdata);
		
		allresult.put("legend", legendlist);
		allresult.put("xaxis", xaxis);
		allresult.put("data", JSONArray.fromObject(singleSeries));
		result.add(allresult);
		return result;
	}
	/**
	 * 查询app用户统计表格
	 */
	public Map<String,Object> webapi1030501(CMi103 form, Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> allresult = cmi103Dao.selectWebapi10304All(form);
		Calendar dayStart = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = sdf.parse(form.getStartdate());
		dayStart.setTime(startdate);
		Date enddate = sdf.parse(form.getEnddate());
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTime(enddate);
		Calendar dayStarttmp = Calendar.getInstance();
		dayStarttmp.setTime(startdate);
		JSONArray result=new JSONArray();
		System.out.println(allresult.size());
		String appsumcount="0";
		for(int i=0;i<allresult.size();i++){
			HashMap map = allresult.get(i);
			if(!map.get("appsumcount").toString().equals("0")){
				appsumcount=(Integer.parseInt(map.get("appsumcount").toString())-Integer.parseInt(map.get("appcountnum")==null?"0":map.get("appcountnum").toString()))+"";
				break;
			}
		}
		dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		while (dayStarttmp.before(dayEnd)) {
			String tmpdate=sdf.format(dayStarttmp.getTime());
			HashMap temp = new HashMap();
			boolean flag=false;
			HashMap tempJson = new HashMap();
			for(int i=0;i<allresult.size();i++){
				tempJson=allresult.get(i);
				if(tmpdate.equals(tempJson.get("appcreatedate"))){
					appsumcount=tempJson.get("appsumcount").toString();
					flag=true;
					break;
				}
			}
			if(!flag){
				tempJson.put("appcreatedate", tmpdate);
				tempJson.put("appcountnum",0);
				tempJson.put("appsumcount",appsumcount);
			}
			tempJson.remove("wxcreatedate");
			tempJson.remove("wxcountnum");
			tempJson.remove("wxsumcount");
			result.add(tempJson);
			dayStarttmp.add(Calendar.DAY_OF_MONTH, 1);
		}
		for(int i=0;i<result.size();i++){
			JSONObject temp = result.getJSONObject(i);
			for(Object key : temp.keySet()){
				System.out.println(key+"="+temp.get(key));
			}
		}
//		JSONObject obj = new JSONObject();
		Map<String,Object> obj = new HashMap<String,Object>();
		JSONArray arr=new JSONArray();
		for(int i=result.size();i>0;i--){
			arr.add(result.get(i-1));
		}
	    obj.put("rows", arr);
		return obj;	
	}
}
