package com.yondervision.mi.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.message.WeiXinMessageUtil;
import com.yondervision.mi.dao.Mi012DAO;
import com.yondervision.mi.dao.Mi103DAO;
import com.yondervision.mi.dao.Mi105DAO;
import com.yondervision.mi.dao.Mi110DAO;
import com.yondervision.mi.dto.Mi012;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.dto.Mi105;
import com.yondervision.mi.dto.Mi105Example;
import com.yondervision.mi.dto.Mi110;
import com.yondervision.mi.dto.Mi110Example;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.service.AppApi110Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.PushIOSAndAndriod;

/**
 * @ClassName: AppApi101ServiceImpl
 * @Description: 统一用户管理
 * @author Caozhongyan
 * @date Oct 11, 2013 2:02:05 PM
 * 
 */
public class AppApi110ServiceImpl implements AppApi110Service {
		
	private Mi110DAO mi110Dao;
	private Mi103DAO mi103Dao;
	private Mi012DAO mi012Dao;
	private Mi105DAO mi105Dao;
	
	public Mi012DAO getMi012Dao() {
		return mi012Dao;
	}

	public void setMi012Dao(Mi012DAO mi012Dao) {
		this.mi012Dao = mi012Dao;
	}

	public Mi103DAO getMi103Dao() {
		return mi103Dao;
	}

	public void setMi103Dao(Mi103DAO mi103Dao) {
		this.mi103Dao = mi103Dao;
	}

	public Mi110DAO getMi110Dao() {
		return mi110Dao;
	}

	public void setMi110Dao(Mi110DAO mi110Dao) {
		this.mi110Dao = mi110Dao;
	}

	public List<Mi110> appApi11001Select(AppApiCommonForm form1)
			throws TransRuntimeErrorException {
		AppApi40102Form form = (AppApi40102Form)form1;
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());	
		Mi110Example m110e=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca= m110e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andChannelEqualTo(form.getChannel());
		ca.andBindkeytypeEqualTo(bindkeytype);
		Mi103 mi103 = new Mi103();
		mi103 = mi103Dao.selectByPrimaryKey(form.getUserId());
		form.setSurplusAccount(mi103.getAccnum());
		form.setIdcardNumber(mi103.getCertinum());
		if("00".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getSurplusAccount());
		}else if("01".equals(bindkeytype)){			
			ca.andBindkeyEqualTo(form.getIdcardNumber());
		}else if("02".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getCardno());
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		ca.andUseridEqualTo(form.getUserId());
		
		List<Mi110> list=mi110Dao.selectByExample(m110e);
		if(list!=null&&list.size()>0){//存在绑定相关信息中心、用户、类型、关键字等
			System.out.println("查询MI110表中有相关记录，判断是否互踢");
			if(list.get(0).getBindflag().equals(Constants.IS_VALIDFLAG)){
				System.out.println("登录时确认相关统一用户表记录已绑定");
				if("0".equals(list.get(0).getValidflag())){
					Mi110 mi110 = new Mi110();
					mi110.setUnifyuserid(list.get(0).getUnifyuserid());
					mi110.setValidflag(Constants.IS_VALIDFLAG);	
					mi110.setDatemodified(CommonUtil.getSystemDate());
					mi110Dao.updateByPrimaryKeySelective(mi110);
				}
				
				
				Mi110Example m110e1=new Mi110Example();
				m110e1.setOrderByClause("datemodified desc");
				com.yondervision.mi.dto.Mi110Example.Criteria ca1= m110e1.createCriteria();
				ca1.andCenteridEqualTo(form.getCenterId());
				ca1.andChannelEqualTo(form.getChannel());
				ca1.andBindkeytypeEqualTo(bindkeytype);
				if("00".equals(bindkeytype)){
					ca1.andBindkeyEqualTo(form.getSurplusAccount());
				}else if("01".equals(bindkeytype)){									
					ca1.andBindkeyEqualTo(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					ca1.andBindkeyEqualTo(form.getCardno());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				} 
				ca1.andBindflagEqualTo(Constants.IS_VALIDFLAG);
				ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				ca1.andUseridNotEqualTo(form.getUserId());
				
				List<Mi110> list1=mi110Dao.selectByExample(m110e1);
				
				Mi012 mi012 = this.getMi012Dao().selectByPrimaryKey(form.getCenterId());
				

				// IOS推送
				String host = PropertiesReader.getProperty(
						Constants.PROPERTIES_FILE_NAME, "ios_push_ip");
				int port = Integer.parseInt(PropertiesReader.getProperty(
						Constants.PROPERTIES_FILE_NAME, "ios_push_port"));
				String certificatePath;
				try {
					certificatePath = CommonUtil.getFileFullPath(
							"ios_push_certificate_path", mi012.getCertificatename(), true);
				} catch (IOException e) {
					e.printStackTrace();
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(),"读取配置文件IOS推送文件异常");
				}
				
				//只推送满足条件，绑定、有效、最近时间用户，找到该用户有效设备
				if(list1!=null&&list1.size()>0){
					for(int i=0;i<list1.size();i++){
						
						Mi110 m110 = new Mi110();
						m110.setUnifyuserid(list1.get(i).getUnifyuserid());
						m110.setValidflag(Constants.IS_NOT_VALIDFLAG);
						mi110Dao.updateByPrimaryKeySelective(m110);
						
						Mi105Example mi105Example = new Mi105Example();
						mi105Example.setOrderByClause("datemodified desc");
						
						Mi105Example.Criteria mi105Criteria = mi105Example
								.createCriteria();
						mi105Criteria.andUserIdEqualTo(list1.get(i).getUserid());
						mi105Criteria.andDevtokenNotEqualTo("");
						mi105Criteria.andValidflagEqualTo(Constants.IS_VALIDFLAG);
						List<Mi105> listMi105 = this.getMi105Dao().selectByExample(
								mi105Example);
						int num = 0;
						if(listMi105!=null&&listMi105.size()>0){//查询到待推送设备信息推送，未查到不进行互踢推送							
							while(true){
								if("".equals(listMi105.get(num).getDevtoken())||listMi105.get(num).getDevtoken()==null){
									num++;
								}else{
									break;
								}
								if(num>listMi105.size()){return null;}
							}
							PushIOSAndAndriod push = new PushIOSAndAndriod();
							push.setUserId(listMi105.get(num).getUserId());
							push.setApikey(mi012.getApikey());
							push.setSecritkey(mi012.getSecritkey());
							push.setPassword(mi012.getCertificatepassword());
							push.setPath(certificatePath);
							push.setHost(host);
							push.setPort(Integer.toString(port));
							push.setDeviceType(listMi105.get(num).getDevtype());
							push.setDeviceToken(listMi105.get(num).getDevtoken());
							push.pushMessage(push);							
						}
						break;//20141224需求变更，判断绑定且有效，最近一次登录的用户，发送互踢通知
					}
				}
			}else{
				System.out.println("登录时确认相关统一用户表记录未绑定");
				throw new TransRuntimeErrorException(ERROR.BINDFLAG_ERROR.getValue(),"");
			}			
		}else{
			System.out.println("查询MI110表中没有相关记录");
			throw new TransRuntimeErrorException(ERROR.BINDFLAG_ERROR.getValue(),"");
		}
		
		return list;
	}

	public List<Mi110> appApi11002Select(AppApiCommonForm form1)
			throws TransRuntimeErrorException {
		AppApi40102Form form = (AppApi40102Form)form1;
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());	
		Mi110Example m110e=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca= m110e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andChannelEqualTo(form.getChannel());
		ca.andBindkeytypeEqualTo(bindkeytype);
		if("00".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getSurplusAccount());
		}else if("01".equals(bindkeytype)){
			if("20".equals(form.getChannel())){
				ca.andBindkeyEqualTo(form.getIdcardNumber());
			}else{
				Mi103 mi103 = new Mi103();
				mi103 = mi103Dao.selectByPrimaryKey(form.getUserId());				
				ca.andBindkeyEqualTo(mi103.getCertinum());
			}
			
		}else if("02".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getCardno());
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		ca.andUseridEqualTo(form.getUserId());
		List<Mi110> list=mi110Dao.selectByExample(m110e);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		if(list!=null&&list.size()>0){
			Mi110 mi110 = new Mi110();
			mi110.setUnifyuserid(list.get(0).getUnifyuserid());
			mi110.setBindflag(Constants.IS_NOT_VALIDFLAG);
			if(form.getChannel().equals("20")){
				mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);				
			}
			mi110.setDatemodified(formatter.format(new Date()));
			if(!CommonUtil.isEmpty(list.get(0).getAuthflag())){
				mi110.setAuthflag(Constants.IS_NOT_VALIDFLAG);
				mi110.setAuthdate(formatter.format(new Date()));
			}
			mi110Dao.updateByPrimaryKeySelective(mi110);
		}
		return list;
	}

	public String appApi11003Insert(AppApiCommonForm form1) throws TransRuntimeErrorException {		
		AppApi40102Form form = (AppApi40102Form)form1;
		Mi110Example m110e1=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca1= m110e1.createCriteria();
		ca1.andCenteridEqualTo(form.getCenterId());		
		ca1.andChannelEqualTo(form.getChannel());
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());		
		ca1.andBindkeytypeEqualTo(bindkeytype);
		if("00".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getSurplusAccount());
		}else if("01".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getIdcardNumber());
		}else if("02".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getCardno());
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		ca1.andUseridEqualTo(form.getUserId());
		List<Mi110> list1=mi110Dao.selectByExample(m110e1);
		if(list1!=null&&list1.size()>0){
			return null;
		}
		
		
		Mi110Example m110e=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca= m110e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());		
		ca.andChannelEqualTo(form.getChannel());
		ca.andBindkeytypeEqualTo(bindkeytype);
		if("00".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getSurplusAccount());
		}else if("01".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getIdcardNumber());
		}else if("02".equals(bindkeytype)){
			ca.andBindkeyEqualTo(form.getCardno());
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		ca.andUseridNotEqualTo(form.getUserId());		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		List<Mi110> list=mi110Dao.selectByExample(m110e);
		String name = "";
		if(list!=null&&list.size()>0){		
			name = list.get(0).getUnifyuserno();				
		}else{
			name = formatter1.format(date);
		}		
		Mi110 mi110 = new Mi110();		
		mi110.setUnifyuserid(formatter1.format(date));
		mi110.setUnifyuserno(name);
		mi110.setCenterid(form.getCenterId());
		mi110.setChannel(form.getChannel());
		mi110.setUserid(form.getUserId());
		mi110.setBindkeytype(bindkeytype);
		if("00".equals(bindkeytype)){
			mi110.setBindkey(form.getSurplusAccount());
		}else if("01".equals(bindkeytype)){
			mi110.setBindkey(form.getIdcardNumber());
		}else if("02".equals(bindkeytype)){
			mi110.setBindkey(form.getCardno());
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
		mi110.setBindflag(Constants.IS_VALIDFLAG);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		
		mi110.setDatecreated(formatter.format(date));
		mi110.setDatemodified(formatter.format(date));
		mi110Dao.insert(mi110);
				
		return "true";
	}

	public String appApi11004Insert(AppApiCommonForm form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws TransRuntimeErrorException {
		AppApi40102Form form = (AppApi40102Form)form1;
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);	
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());
		Mi110Example m110e=new Mi110Example();
		m110e.setOrderByClause("datemodified desc");
		com.yondervision.mi.dto.Mi110Example.Criteria ca= m110e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());		
		ca.andChannelEqualTo(form.getChannel());				
		ca.andBindkeytypeEqualTo(bindkeytype);
		ca.andUseridEqualTo(form.getUserId());
		List<Mi110> list1=mi110Dao.selectByExample(m110e);
		if(list1!=null&&list1.size()>0){
			System.out.println("绑定用户信息已存在，执行正常。");
			Mi110Example m110e1=new Mi110Example();
			m110e1.setOrderByClause("datemodified desc");
			com.yondervision.mi.dto.Mi110Example.Criteria ca1= m110e1.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId());		
			//ca1.andChannelEqualTo(form.getChannel());
			ca1.andBindkeytypeEqualTo(bindkeytype);
			//ca1.andUseridEqualTo(form.getUserId());
			if("00".equals(bindkeytype)){
				ca1.andBindkeyEqualTo(form.getSurplusAccount());
			}else if("01".equals(bindkeytype)){
				ca1.andBindkeyEqualTo(form.getIdcardNumber());
			}else if("02".equals(bindkeytype)){
				ca1.andBindkeyEqualTo(form.getCardno());
			}else if("03".equals(bindkeytype)){
				
			}else if("04".equals(bindkeytype)){
				
			}
			ca1.andUseridNotEqualTo(form.getUserId());
			List<Mi110> list2=mi110Dao.selectByExample(m110e1);
			if(list2!=null&&list2.size()>0){			
				Mi110 mi110 = new Mi110();		
				mi110.setUnifyuserid(list1.get(0).getUnifyuserid());
				mi110.setUnifyuserno(list2.get(0).getUnifyuserno());				
				mi110.setBindkeytype(bindkeytype);
				if("00".equals(bindkeytype)){
					mi110.setBindkey(form.getSurplusAccount());
				}else if("01".equals(bindkeytype)){
					mi110.setBindkey(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					mi110.setBindkey(form.getCardno());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				}
				if(form.getChannel().equals("20")){
					mi110.setValidflag(Constants.IS_VALIDFLAG);
				}else if(form.getChannel().equals("10")){
					//mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
				}
				mi110.setBindflag(Constants.IS_VALIDFLAG);
				mi110.setDatemodified(formatter.format(date));
				mi110Dao.updateByPrimaryKeySelective(mi110);
				System.out.println("绑定用户信息已存在，取得统一用户信息并更新");
			}else{
				Mi110 mi110 = new Mi110();		
				mi110.setUnifyuserid(list1.get(0).getUnifyuserid());
				mi110.setUnifyuserno(formatter1.format(date));				
				mi110.setBindkeytype(bindkeytype);
				if("00".equals(bindkeytype)){
					mi110.setBindkey(form.getSurplusAccount());
				}else if("01".equals(bindkeytype)){
					mi110.setBindkey(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					mi110.setBindkey(form.getCardno());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				}
				mi110.setUserid(form.getUserId());
				mi110.setChannel(form.getChannel());
				mi110.setCenterid(form.getCenterId());
				if(form.getChannel().equals("20")){
					mi110.setValidflag(Constants.IS_VALIDFLAG);
				}else if(form.getChannel().equals("10")){
					//mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
				}		
				mi110.setBindflag(Constants.IS_VALIDFLAG);
				mi110.setDatemodified(formatter.format(date));
				mi110Dao.updateByPrimaryKeySelective(mi110);
				System.out.println("绑定用户信息已存在，没有取得统一用户信息并更新");
			}			
		}else{
			Mi110Example m110e2=new Mi110Example();
			com.yondervision.mi.dto.Mi110Example.Criteria ca2= m110e2.createCriteria();
			ca2.andCenteridEqualTo(form.getCenterId());		
			//ca2.andChannelEqualTo(form.getChannel());
			ca2.andBindkeytypeEqualTo(bindkeytype);
			if("00".equals(bindkeytype)){
				ca2.andBindkeyEqualTo(form.getSurplusAccount());
			}else if("01".equals(bindkeytype)){
				ca2.andBindkeyEqualTo(form.getIdcardNumber());
			}else if("02".equals(bindkeytype)){
				ca2.andBindkeyEqualTo(form.getCardno());
			}else if("03".equals(bindkeytype)){
				
			}else if("04".equals(bindkeytype)){
				
			}
			List<Mi110> list3=mi110Dao.selectByExample(m110e2);
			if(list3!=null&&list3.size()>0){
				Mi110 mi110 = new Mi110();		
				mi110.setUnifyuserid(formatter1.format(date));
				mi110.setUnifyuserno(list3.get(0).getUnifyuserno());
				mi110.setCenterid(form.getCenterId());
				mi110.setChannel(form.getChannel());
				mi110.setUserid(form.getUserId());
				mi110.setBindkeytype(bindkeytype);
				if("00".equals(bindkeytype)){
					mi110.setBindkey(form.getSurplusAccount());
				}else if("01".equals(bindkeytype)){
					mi110.setBindkey(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					mi110.setBindkey(form.getCardno());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				}
				if(form.getChannel().equals("20")){
					mi110.setValidflag(Constants.IS_VALIDFLAG);
				}else if(form.getChannel().equals("10")){
					mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
				}
				mi110.setBindflag(Constants.IS_VALIDFLAG);
				mi110.setDatecreated(formatter.format(date));
				mi110.setDatemodified(formatter.format(date));
				mi110Dao.insert(mi110);
				System.out.println("绑定用户信息不存在，通过关建字取得统一用户信息并插入");
			}else{
				Mi110 mi110 = new Mi110();		
				mi110.setUnifyuserid(formatter1.format(date));
				mi110.setUnifyuserno(formatter1.format(date));
				mi110.setCenterid(form.getCenterId());
				mi110.setChannel(form.getChannel());
				mi110.setUserid(form.getUserId());
				mi110.setBindkeytype(bindkeytype);
				if("00".equals(bindkeytype)){
					mi110.setBindkey(form.getSurplusAccount());
				}else if("01".equals(bindkeytype)){
					mi110.setBindkey(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					mi110.setBindkey(form.getCardno());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				}
				if(form.getChannel().equals("20")){
					mi110.setValidflag(Constants.IS_VALIDFLAG);
				}else if(form.getChannel().equals("10")){
					mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
				}
				mi110.setBindflag(Constants.IS_VALIDFLAG);			
				mi110.setDatecreated(formatter.format(date));
				mi110.setDatemodified(formatter.format(date));
				mi110Dao.insert(mi110);
				System.out.println("绑定用户信息不存在，未取得统一用户信息并插入");
			}			
		}
		if("20".equals(form.getChannel())){
			Mi110Example m110e4=new Mi110Example();
			com.yondervision.mi.dto.Mi110Example.Criteria ca4= m110e4.createCriteria();
			ca4.andCenteridEqualTo(form.getCenterId());		
			ca4.andChannelEqualTo(form.getChannel());
			ca4.andBindkeytypeEqualTo(bindkeytype);
			//ca1.andUseridEqualTo(form.getUserId());
			if("00".equals(bindkeytype)){
				ca4.andBindkeyEqualTo(form.getSurplusAccount());
			}else if("01".equals(bindkeytype)){
				ca4.andBindkeyEqualTo(form.getIdcardNumber());
			}else if("02".equals(bindkeytype)){
				ca4.andBindkeyEqualTo(form.getCardno());
			}else if("03".equals(bindkeytype)){
				
			}else if("04".equals(bindkeytype)){
				
			}
			ca4.andUseridNotEqualTo(form.getUserId());
			ca4.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			List<Mi110> list4=mi110Dao.selectByExample(m110e4);
			if(list4!=null&&list4.size()>0){
				String url = PropertiesReader.getProperty(
						Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/kick";
				StringBuffer value = new StringBuffer();
				value.append("{\"regionId\":\""+form.getCenterId()+"\",\"openId\":\"");
				for(int i=0;i<list4.size();i++){
					if(i==0){
						value.append(list4.get(i).getUserid());
					}else{
						value.append(","+list4.get(i).getUserid());
					}					
				}
				value.append("\"}");
				WeiXinMessageUtil weixin = new WeiXinMessageUtil();
				weixin.post(url, value.toString(),modelMap, request, response);	
				for(int j=0;j<list4.size();j++){
					Mi110 mi110 = list4.get(j);
					mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
					mi110.setBindflag(Constants.IS_NOT_VALIDFLAG);
					mi110.setDatemodified(formatter.format(date));
					mi110Dao.updateByPrimaryKeySelective(mi110);
				}
			}
			
			
			
		}
		if(!"20".equals(form.getChannel())){
			Mi103Example m103e=new Mi103Example();
			com.yondervision.mi.dto.Mi103Example.Criteria ca103= m103e.createCriteria();
			ca103.andUserIdEqualTo(form.getUserId());
			ca103.andCenteridEqualTo(form.getCenterId());		
			List<Mi103> list103=mi103Dao.selectByExample(m103e);
			if(list103!=null&&list103.size()>0){
				Mi103 mi103 = new Mi103();
				mi103.setUserId(form.getUserId());			
				mi103.setAccname(form.getFullName());
				if("00".equals(bindkeytype)){
					mi103.setAccnum(form.getSurplusAccount());
					mi103.setCertinum(form.getIdcardNumber());
				}else if("01".equals(bindkeytype)){
					mi103.setAccnum(form.getSurplusAccount());
					mi103.setCertinum(form.getIdcardNumber());
				}else if("02".equals(bindkeytype)){
					mi103.setCardno(form.getCardno());
					mi103.setCertinum(form.getIdcardNumber());
				}else if("03".equals(bindkeytype)){
					
				}else if("04".equals(bindkeytype)){
					
				}			
				mi103.setDatemodified(formatter.format(date));
				if(!form.getNewpassword().isEmpty()){
					mi103.setFreeuse1("1");
				}else{
					mi103.setFreeuse1("0");
				}
				mi103Dao.updateByPrimaryKeySelective(mi103);
				System.out.println("绑定用户信息存在，更新用户表信息成功");				
			}
			
			if(!CommonUtil.isEmpty(form.getDevtoken())){
				Mi105 mi105BuUserId = null;
				Mi105Example m105e=new Mi105Example();//查询设备表中，指定用户ID及设备识别码信息
				m105e.setOrderByClause("datemodified desc");
				com.yondervision.mi.dto.Mi105Example.Criteria ca105= m105e.createCriteria();
				ca105.andUserIdEqualTo(form.getUserId());
				ca105.andAppdevidEqualTo(form.getDeviceToken());
				ca105.andDevtypeEqualTo(form.getDeviceType());
				List<Mi105> listMi105 = mi105Dao.selectByExample(m105e);
				if(listMi105.size()>0){
					if(CommonUtil.isEmpty(listMi105.get(0).getDevtoken())){
						mi105BuUserId = listMi105.get(0);
						mi105BuUserId.setDevtoken(form.getDevtoken());
						mi105BuUserId.setDatemodified(CommonUtil.getSystemDate());
						mi105Dao.updateByPrimaryKeySelective(mi105BuUserId);
					}
				}
			}
			
		}		
		return "true";
	}

	public Mi105DAO getMi105Dao() {
		return mi105Dao;
	}

	public void setMi105Dao(Mi105DAO mi105Dao) {
		this.mi105Dao = mi105Dao;
	}

	public List<Mi110> appApi11005Select(AppApiCommonForm form1)
			throws Exception {		
		AppApi40102Form form = (AppApi40102Form)form1;
		Mi103 mi103 = new Mi103();
		mi103 = mi103Dao.selectByPrimaryKey(form.getUserId());
		form.setSurplusAccount(mi103.getAccnum());
		String certinum = mi103.getCertinum();		
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);	
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());
		Mi110Example m110e=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca1= m110e.createCriteria();
		ca1.andCenteridEqualTo(form.getCenterId());	//城市中心	
		ca1.andChannelEqualTo(form.getChannel());//渠道
		ca1.andBindkeytypeEqualTo(bindkeytype);//关建字类型
		ca1.andUseridEqualTo(form.getUserId());//用户名
		if("00".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getSurplusAccount());//公积金号
		}else if("01".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(certinum);//身份证号
		}else if("02".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getCardno());//联名卡号
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}		
		List<Mi110> list110=mi110Dao.selectByExample(m110e);
		if(list110!=null&&list110.size()>0){//注销后绑定表对应为无效，绑定状态未改变
			Mi110 mi110 = new Mi110();
			mi110.setUnifyuserid(list110.get(0).getUnifyuserid());
			mi110.setValidflag(Constants.IS_NOT_VALIDFLAG);
			mi110.setDatemodified(formatter.format(date));
			mi110Dao.updateByPrimaryKeySelective(mi110);
		}
		return null;
	}

	/**绑定信息检索
	 * @param form
	 * @return list<Mi110>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Mi110> appApi11006Select(AppApi40102Form form) throws Exception{
		List<Mi110> resultList = new ArrayList<Mi110>();

		String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+form.getCenterId());
		Mi110Example m110e=new Mi110Example();
		com.yondervision.mi.dto.Mi110Example.Criteria ca1= m110e.createCriteria();
		ca1.andCenteridEqualTo(form.getCenterId());	//城市中心	
		ca1.andChannelEqualTo(form.getChannel());//渠道
		ca1.andBindkeytypeEqualTo(bindkeytype);//关建字类型
		ca1.andUseridEqualTo(form.getUserId());//用户名
		
		System.out.println("appApi11006Select---userid=="+form.getUserId());
		System.out.println("appApi11006Select---idcardnumber=="+form.getIdcardNumber());
		System.out.println("appApi11006Select---surplusaccount=="+form.getSurplusAccount());
		System.out.println("appApi11006Select---cardno=="+form.getCardno());
		if("00".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getSurplusAccount());//公积金号
		}else if("01".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getIdcardNumber());//身份证号
		}else if("02".equals(bindkeytype)){
			ca1.andBindkeyEqualTo(form.getCardno());//联名卡号
		}else if("03".equals(bindkeytype)){
			
		}else if("04".equals(bindkeytype)){
			
		}
		ca1.andBindflagEqualTo(Constants.IS_VALIDFLAG);
		resultList = mi110Dao.selectByExample(m110e);
		
		return resultList;
	}
	
	/**更新实名身份认证信息
	 * @param unifyuserid
	 * @param form
	 * @return 
	 * @throws Exception
	 */
	public void appApi11007Update(String unifyuserid, AppApi40102Form form) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);	
		Date date = new Date();
		Mi110 mi110 = new Mi110();
		mi110.setUnifyuserid(unifyuserid);
		mi110.setAuthflag(Constants.IS_VALIDFLAG);
		mi110.setAuthdate(formatter.format(date));
		mi110.setSignedphone(form.getMobileNumber());
		mi110.setBankaccnum(form.getBankaccnum());
		mi110Dao.updateByPrimaryKeySelective(mi110);
	}
}
