package com.yondervision.mi.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.APP_ALERT;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi103DAO;
import com.yondervision.mi.dao.Mi105DAO;
import com.yondervision.mi.dao.Mi119DAO;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.dto.Mi105;
import com.yondervision.mi.dto.Mi105Example;
import com.yondervision.mi.dto.Mi119;
import com.yondervision.mi.dto.Mi119Example;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi40108Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi401Service;
import com.yondervision.mi.util.AppVeriCode;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi401ServiceImpl 
* @Description: APP用户管理
* @author Caozhongyan
* @date Oct 12, 2013 4:34:44 PM   
* 
*/ 
public class AppApi401ServiceImpl implements AppApi401Service {
	
	@Autowired
	private CommonUtil commonUtil = null;
	@Autowired
	private AppApi110ServiceImpl appApi110ServiceImpl = null;
	public AppApi110ServiceImpl getAppApi110ServiceImpl() {
		return appApi110ServiceImpl;
	}

	public void setAppApi110ServiceImpl(AppApi110ServiceImpl appApi110ServiceImpl) {
		this.appApi110ServiceImpl = appApi110ServiceImpl;
	}

	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
	}
	
	private final Logger log = LoggerUtil.getLogger();
	private Mi103DAO mi103Dao = null;
	private Mi105DAO mi105Dao = null;
	private Mi119DAO mi119Dao = null;
	public Mi119DAO getMi119Dao() {
		return mi119Dao;
	}

	public void setMi119Dao(Mi119DAO mi119Dao) {
		this.mi119Dao = mi119Dao;
	}

	public Mi105DAO getMi105Dao() {
		return mi105Dao;
	}

	public void setMi105Dao(Mi105DAO mi105Dao) {
		this.mi105Dao = mi105Dao;
	}

	public Mi103DAO getMi103Dao() {
		return mi103Dao;
	}

	public void setMi103Dao(Mi103DAO mi103Dao) {
		this.mi103Dao = mi103Dao;
	}

	public void appapi40101(AppApiCommonForm form, HttpServletResponse response) throws TransRuntimeErrorException {
		// TODO Auto-generated method stub
//		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi119 mi119 = new Mi119();
		AppVeriCode ac = new AppVeriCode();
		if("00041100".equals(form.getCenterId())){
			ac.getImageTW(response);
		}else{
			ac.getImage(response);
		}
		
		if(ac.getVeriCode().isEmpty()||"".equals(ac.getVeriCode())){
			//验证码获取失败
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"验证码获取失败");
		}		
		Mi119Example m119e=new Mi119Example();
		com.yondervision.mi.dto.Mi119Example.Criteria ca= m119e.createCriteria();
		ca.andAppdevidEqualTo(form.getDeviceToken());
//		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi119> mi119List = mi119Dao.selectByExample(m119e);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, 60*5);
		if(mi119List.isEmpty()||mi119List.size()==0){			
			mi119.setAppdevid(form.getDeviceToken());
			mi119.setCenterid(form.getCenterId());
			mi119.setCurrenversion(form.getCurrenVersion());
			mi119.setDevicetype(form.getDeviceType());
			mi119.setCheckid(ac.getVeriCode().toLowerCase());
			mi119.setCheckdate(formatter1.format((Date)calendar.getTime()));
			mi119.setValidflag(Constants.IS_VALIDFLAG);
			mi119.setDatecreated(formatter1.format(date));
			mi119.setDatemodified(formatter1.format(date));
			mi119Dao.insert(mi119);
		}else{
			mi119.setCheckid(ac.getVeriCode().toLowerCase());
			mi119.setCheckdate(formatter1.format((Date)calendar.getTime()));
			mi119.setDatemodified(formatter1.format(date));
			Mi119Example m119e1=new Mi119Example();
			com.yondervision.mi.dto.Mi119Example.Criteria ca1= m119e1.createCriteria();
			ca1.andAppdevidEqualTo(form.getDeviceToken());
			mi119Dao.updateByExampleSelective(mi119, m119e1);
		}
	}
	
	public void appapi40102(AppApi40102Form form) throws TransRuntimeErrorException {
		
		/*
		Mi103Example m103e1=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
		ca1.andCenteridEqualTo(form.getCenterId());
		ca1.andAccnumEqualTo(form.getSurplusAccount());
		int conut1 =	mi103Dao.countByExample(m103e1);
		if(conut1>0){
			//用户名已注册，异常抛出处理
			throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),"公积金账户已注册");
		}*/
		if(!"1002".equals(form.getIscheck())){
			appApi110ServiceImpl.appApi11003Insert(form);
		}
		
		SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103 = new Mi103();
		mi103.setUserId(form.getUserId());
		//密码为MD5双层加密，不存明文
		mi103.setUserpwd(form.getPassword());
		mi103.setCenterid(form.getCenterId());
		mi103.setAccnum(form.getSurplusAccount());
		mi103.setAccname(form.getFullName());
		mi103.setEmail(form.getEmail());
		mi103.setPhone(form.getMobileNumber());
		mi103.setCardno(form.getCardno());
		mi103.setCertinum(form.getIdcardNumber());
		mi103.setValidflag(Constants.IS_VALIDFLAG);
		mi103.setDatecreated(formatter1.format(date));
		mi103.setDatemodified(formatter1.format(date));
		mi103.setWaitdate(formatter1.format(date));
		mi103.setWaitnum(0);
		if(!form.getNewpassword().isEmpty()){
			mi103.setFreeuse1("1");
		}else{
			mi103.setFreeuse1("0");
		}
		mi103Dao.insert(mi103);		
		
		Mi105 mi105 = new Mi105();
		try{
			mi105.setDevid(commonUtil.genKey("MI105", 30));
		}catch(Exception e){
			e.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"采号异常");
		}
		mi105.setUserId(form.getUserId());
		mi105.setAppdevid(form.getDeviceToken());
		mi105.setDevtype(form.getDeviceType());
		mi105.setLogintimes(0);
		mi105.setDevtoken(form.getDevtoken());
		mi105.setLastlogindate(formatter1.format(date));
		mi105.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi105.setDatecreated(formatter1.format(date));
		mi105.setDatemodified(formatter1.format(date));
		mi105Dao.insert(mi105);	
	}

	public boolean appapi40103(AppApi40102Form form) throws Exception {
//		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		SimpleDateFormat formatterSFM = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103BuUserId = null;
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		ca.andUserpwdEqualTo(form.getPassword());
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = mi103Dao.selectByExample(m103e);
		if(!(list.size()>0)){//未查询到用户时返回	
			Mi103Example m103e1=new Mi103Example();
			com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
			ca1.andUserIdEqualTo(form.getUserId());
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi103> list1 = mi103Dao.selectByExample(m103e1);
			if(list1.size()>0){
				SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
				Mi103 mi1031 = new Mi103();
				mi1031.setWaitdate(formatter1.format(date));
				mi1031.setWaitnum(list1.get(0).getWaitnum()+1);
				mi1031.setDatemodified(formatter1.format(date));
				mi103Dao.updateByExampleSelective(mi1031, m103e1);
			}
			return false;
		}else{
			Mi103Example m103e1=new Mi103Example();
			com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
			ca1.andUserIdEqualTo(form.getUserId());	
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			Mi103 mi1031 = new Mi103();
			mi1031.setWaitnum(0);
			mi1031.setDatemodified(formatterSFM.format(date));
			mi103Dao.updateByExampleSelective(mi1031, m103e1);
		}
		/**
		 * 以下为更新设备表中设备信息
		 * 1.新用户、新设备插入记录有效位为“1”。
		 * 2.新用户、旧设备（旧设备指登录过其他用户，并且登录过105设备信息表信息），非新用户、旧设备信息置无效“0”，插入新用户旧设备置有效“1”。		
		 * 3.旧用户（用户在多个设备中登录过，并且登录过105设备信息表信息）、新设备，旧用户且非新设备信息置无效“0”，插入旧用户、新设备有效“1”。		
		 * 4.旧用户、旧设备，设置当前设备下其他用户信息为无效“0”，更新旧用户旧设备为有效“1”。			
		 */
		boolean shaishuan = true;
		Mi105 mi105BuUserId = null;
		Mi105Example m105e=new Mi105Example();//查询设备表中，指定用户ID及设备识别码信息
		com.yondervision.mi.dto.Mi105Example.Criteria ca105= m105e.createCriteria();
		ca105.andUserIdEqualTo(form.getUserId());
		ca105.andAppdevidEqualTo(form.getDeviceToken());
		System.out.println("登录用户名:"+form.getUserId()+"Token:"+form.getDevtoken()+"设备识别码:"+form.getDeviceToken());
		List<Mi105> list105 = mi105Dao.selectByExample(m105e);
		if(list105.size()>0){//旧用户、旧设备
//			Mi103Example m103e1=new Mi103Example();
//			com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
//			ca1.andUserIdNotEqualTo(form.getUserId());//不等于指定用户ID	
//			ca1.andCenteridEqualTo(form.getCenterId());//同中心
//			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);//有效用户
//			List<Mi103> listMi103 = mi103Dao.selectByExample(m103e1);//103表中不等于上传用户ID的用户信息			
			
//			Mi105 mi105up = new Mi105();
//			mi105up.setValidflag(Constants.IS_NOT_VALIDFLAG);//设备无效
//			mi105up.setDatemodified(formatterSFM.format(date));
			/**
			 * 待更新设备无效，检查当前设备是否有其它用户信息在设备表，如果有，均设置为无效。 
			 */
			Mi105Example m105eup=new Mi105Example();//主要检查设备识别码及用户ID对应的设备
			com.yondervision.mi.dto.Mi105Example.Criteria caUp= m105eup.createCriteria();
			caUp.andAppdevidEqualTo(form.getDeviceToken());//指定设备识别码			
			caUp.andUserIdNotEqualTo(form.getUserId());//非登录用户
			//20151120修改IN个数超长 start
//			if(listMi103.size()>0){
//				List values = new ArrayList();
//				for(int a=0;a<listMi103.size();a++){
//					values.add(listMi103.get(a).getUserId());
//				}
//				caUp.andUserIdIn(values);
//			}			
//			mi105Dao.updateByExampleSelective(mi105up, m105eup);
			//20151120修改IN个数超长 end
			List<Mi105> list105_no_userid = mi105Dao.selectByExample(m105eup);
			if(list105_no_userid.size()>0){
				for(int i=0;i<list105_no_userid.size();i++){
					Mi103Example m103e1=new Mi103Example();
					com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
					ca1.andUserIdEqualTo(list105_no_userid.get(i).getUserId());//不等于指定用户ID	
					ca1.andCenteridEqualTo(form.getCenterId());//同中心
					ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);//有效用户
					if(mi103Dao.selectByExample(m103e1).size()>0){//103表中不等于上传用户ID的用户信息
						Mi105 mi105up = list105_no_userid.get(i);
						mi105up.setValidflag(Constants.IS_NOT_VALIDFLAG);//设备无效
						mi105up.setDatemodified(formatterSFM.format(date));
						mi105Dao.updateByPrimaryKey(mi105up);
					}
				}
			}
			
			
			
			
			com.yondervision.mi.dto.Mi105Example.Criteria caUpdate= m105e.createCriteria();
			caUpdate.andUserIdEqualTo(form.getUserId());
			caUpdate.andAppdevidEqualTo(form.getDeviceToken());
			Mi105 mi105 = new Mi105();
			mi105.setDevtoken(form.getDevtoken());
			mi105.setLogintimes(list105.get(0).getLogintimes()+1);
			mi105.setLastlogindate(formatterSFM.format(date));
			mi105.setDatemodified(formatterSFM.format(date));
			mi105.setDevtype(form.getDeviceType());
			mi105.setValidflag(Constants.IS_VALIDFLAG);
			mi105Dao.updateByExampleSelective(mi105, m105e);
			System.out.println("用户登录发现设备变更，更新用户信息");
		}else{
//			Mi103Example m103e1=new Mi103Example();
//			com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e1.createCriteria();
//			ca1.andUserIdNotEqualTo(form.getUserId());//不等于指定用户ID	
//			ca1.andCenteridEqualTo(form.getCenterId());//同中心
//			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);//有效用户
//			List<Mi103> listMi103 = mi103Dao.selectByExample(m103e1);//103表中不等于上传用户ID的用户信息	
			/**
			 *	caUpType2查询同设备下其他用户
			 *	caUpType3查询同用户下不同设备
			 */		
			Mi105 mi105up = new Mi105();
			mi105up.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi105up.setDatemodified(formatterSFM.format(date));
			Mi105Example m105eup=new Mi105Example();
//			com.yondervision.mi.dto.Mi105Example.Criteria caUpType2= m105eup.createCriteria();//新用户、旧设备			
//			caUpType2.andAppdevidEqualTo(form.getDeviceToken());			
//			caUpType2.andUserIdNotEqualTo(form.getUserId());
//			if(listMi103.size()>0){
//				List values2 = new ArrayList();
//				for(int a=0;a<listMi103.size();a++){
//					values2.add(listMi103.get(a).getUserId());
//				}
//				caUpType2.andUserIdIn(values2);
//			}
			com.yondervision.mi.dto.Mi105Example.Criteria caUpType3= m105eup.createCriteria();//旧用户、新设备
			caUpType3.andAppdevidNotEqualTo(form.getDeviceToken());			
			caUpType3.andUserIdEqualTo(form.getUserId());
//			if(listMi103.size()>0){
//				List values3 = new ArrayList();
//				for(int a=0;a<listMi103.size();a++){
//					values3.add(listMi103.get(a).getUserId());
//				}
//				caUpType3.andUserIdIn(values3);
//			}	
			m105eup.or(caUpType3);
			mi105Dao.updateByExampleSelective(mi105up, m105eup);//新用户旧设行或旧用户新设备更新为无效再插入新的设备及用户	
			
			Mi105 mi105 = new Mi105();
			mi105.setDevid(commonUtil.genKey("MI105", 30));
			mi105.setUserId(form.getUserId());
			mi105.setDevtoken(form.getDevtoken());
			mi105.setDevtype(form.getDeviceType());
			mi105.setAppdevid(form.getDeviceToken());
			mi105.setLogintimes(1);
			mi105.setLastlogindate(formatterSFM.format(date));
			mi105.setValidflag(Constants.IS_VALIDFLAG);
			mi105.setDatecreated(formatterSFM.format(date));
			mi105.setDatemodified(formatterSFM.format(date));
			mi105Dao.insert(mi105);//新用户、新设备
			System.out.println("用户登录发现新设备，添加用户信息");
		}
		return true;
	}
	
	public int appapi40104(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103 = new Mi103();
		mi103.setUserpwd(form.getPassword());
		mi103.setDatemodified(formatter.format(date));
		mi103.setWaitnum(0);
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		ca.andAccnumEqualTo(form.getSurplusAccount());
		ca.andAccnameEqualTo(form.getFullName());
		ca.andCertinumEqualTo(form.getIdcardNumber());
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int i = mi103Dao.updateByExampleSelective(mi103, m103e);
		return i;
	}
	
	public int appapi40105(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103 = new Mi103();
		if(!CommonUtil.isEmpty(form.getNewpassword())){
			mi103.setUserpwd(form.getNewpassword());
		}		
		mi103.setDatemodified(formatter.format(date));
		mi103.setPhone(form.getMobileNumber());
		mi103.setEmail(form.getEmail());
		mi103.setWaitnum(0);
		mi103.setWaitdate(formatter.format(date));
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());		
		ca.andUserpwdEqualTo(form.getPassword());		
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		
		return mi103Dao.updateByExampleSelective(mi103, m103e);
	}
	
	public void appapi40106(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		Date date = new Date();
		Mi103 mi103BuUserId = null;
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());	
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = mi103Dao.selectByExample(m103e);
		if(list.size()>0){			
			SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			Mi103 mi103 = new Mi103();			
			com.yondervision.mi.dto.Mi103Example.Criteria ca1= m103e.createCriteria();
			ca1.andUserIdEqualTo(form.getUserId());	
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			int sum = list.get(0).getWaitnum();			
			if(sum==0){		
				mi103.setWaitdate(formatter1.format(date));
				mi103.setWaitnum(++sum);
			}else{
				Date date1 = formatter1.parse(list.get(0).getWaitdate());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date1);
				++sum;
				if(formatter.format(date).compareTo(formatter.format(formatter.parse(list.get(0).getWaitdate())))>0){
					sum = 0;
					mi103.setWaitnum(sum);					
				}else{
					mi103.setWaitnum(sum);
				}		
				
				calendar.add(Calendar.SECOND, 60*sum);
				mi103.setWaitdate(formatter1.format((Date)calendar.getTime()));
				
			}
			mi103.setDatemodified(formatter.format(date));
			mi103Dao.updateByExampleSelective(mi103, m103e);
		}		
	}

	public List<Mi103> appapi40107(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub		
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = null; 
		list = mi103Dao.selectByExample(m103e);
		if(list.isEmpty()||list.size()==0){
			//抛出异常，用户不存在
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户不存在");
		}
		return list;
	}
	
	public List<Mi119> appapi40108(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub
		Mi119Example m119e=new Mi119Example();
		com.yondervision.mi.dto.Mi119Example.Criteria ca= m119e.createCriteria();
		ca.andAppdevidEqualTo(form.getDeviceToken());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);		 
		return mi119Dao.selectByExample(m119e);
	}
	
	
	
	public static void main(String[] args) throws ParseException{
//		SimpleDateFormat formatter1 = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
//		Date date1 = null;
//		try {
//			date1 = formatter1.parse(formatter1.format(new Date()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(formatter1.format(new Date()));
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date1);
//		calendar.add(Calendar.SECOND, 60*1);
//		System.out.println(formatter1.format((Date)calendar.getTime()));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   java.util.Date now = df.parse("2004-03-26 13:31:40");
		   java.util.Date date=df.parse("2004-03-21 13:33:54");
		   long l=now.getTime()-date.getTime();
		   long day=l/(24*60*60*1000);
		   long hour=(l/(60*60*1000));
		   long min=((l/(60*1000)));
		   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");

		
		
	}

	public void appapi40109(AppApi40102Form form) throws TransRuntimeErrorException {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi105 mi105 = new Mi105();
		mi105.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi105.setDatemodified(formatter.format(date));
		
		Mi105Example m105e=new Mi105Example();
		com.yondervision.mi.dto.Mi105Example.Criteria ca= m105e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		ca.andAppdevidEqualTo(form.getDeviceToken());
		ca.andDevtypeEqualTo(form.getDeviceType());
		int i = mi105Dao.updateByExampleSelective(mi105, m105e);
		if(i==0){
			log.error(ERROR.SYS.getLogText("APP用户注销失败"+"用户ID:"+form.getUserId()+",设备ID："+form.getDeviceToken()+",设备类型："+form.getDeviceType()));
			throw new TransRuntimeErrorException(APP_ALERT.SYS.getValue(),"APP用户注销失败");
		}
	}
	
	public List<Mi103> appapi40110(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub		
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andAccnumEqualTo(form.getSurplusAccount());
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = null; 
		list = mi103Dao.selectByExample(m103e);
		
		return list;
	}

	public void appapi40111(AppApi40102Form form) throws Exception {
		Mi103 mi103BuUserId = null;
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		int conut =	mi103Dao.countByExample(m103e);
		if(conut>0){
			//用户名已注册，异常抛出处理
			throw new TransRuntimeErrorException(APP_ALERT.APP_ZC.getValue(),"用户名已注册");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Mi103> appapi40112(String userid, String centerid) throws Exception {
		// TODO Auto-generated method stub		
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(userid);
		ca.andCenteridEqualTo(centerid);
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = null; 
		list = mi103Dao.selectByExample(m103e);
		if(list.isEmpty()||list.size()==0){
			//抛出异常，用户不存在
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_NO_USER.getValue(),"用户不存在");
		}
		return list;
	}
	
	public int appapi40113(AppApi40108Form form) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103 = new Mi103();
		mi103.setUserId(form.getUserId());
		mi103.setFreeuse2(form.getImgurl());
		mi103.setDatemodified(formatter.format(date));
		return mi103Dao.updateByPrimaryKeySelective(mi103);
	}

	public List<Mi103> appapi40114(AppApi40102Form form) throws Exception {
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi103> list = mi103Dao.selectByExample(m103e);		
		return list;
	}
	public int appapi40117(AppApi40102Form form) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		Mi103 mi103 = new Mi103();
		mi103.setUserpwd(form.getPassword());
		mi103.setDatemodified(formatter.format(date));
		mi103.setWaitnum(0);
		Mi103Example m103e=new Mi103Example();
		com.yondervision.mi.dto.Mi103Example.Criteria ca= m103e.createCriteria();
		ca.andUserIdEqualTo(form.getUserId());
//		ca.andAccnumEqualTo(form.getSurplusAccount());
//		ca.andAccnameEqualTo(form.getFullName());
		ca.andCertinumEqualTo(form.getIdcardNumber());
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		int i = mi103Dao.updateByExampleSelective(mi103, m103e);
		return i;
	}
}
