package com.yondervision.mi.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.Mi106DAO;
import com.yondervision.mi.dto.Mi106;
import com.yondervision.mi.dto.Mi106Example;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi40301Result;
import com.yondervision.mi.service.AppApi403Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: AppApi403ServiceImpl 
* @Description: 软件更新
* @author Caozhongyan
* @date Oct 15, 2013 3:42:17 PM   
* 
*/ 
public class AppApi403ServiceImpl implements AppApi403Service {

	private Mi106DAO mi106Dao = null;
	
	public Mi106DAO getMi106Dao() {
		return mi106Dao;
	}
	
	public void setMi106Dao(Mi106DAO mi106Dao) {
		this.mi106Dao = mi106Dao;
	}
	
	public AppApi40301Result appapi40301(AppApiCommonForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		AppApi40301Result appApi40301Result = new AppApi40301Result();
		Mi106 mi106BuUserId = null;
		Mi106Example m106e=new Mi106Example();
		
		if(!"20".equals(request.getParameter("channel"))){
			com.yondervision.mi.dto.Mi106Example.Criteria ca= m106e.createCriteria();
			ca.andCenteridEqualTo(form.getCenterId());
			ca.andDevtypeEqualTo(form.getDeviceType());
			ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andVersionnoEqualTo(form.getCurrenVersion());
			ca.andPublishflagEqualTo("1");
			List<Mi106> list =	mi106Dao.selectByExample(m106e);
			if(list.isEmpty()){
				//手机上传版本不存在，异常处理
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP版本信息");
			}		
			if("0".equals(list.get(0).getUsableflag())){
				appApi40301Result.setUpdateforce("true");
			}else{
				appApi40301Result.setUpdateforce("false");
			}
			Mi106Example m106e1=new Mi106Example();
			com.yondervision.mi.dto.Mi106Example.Criteria ca1= m106e1.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andDevtypeEqualTo(form.getDeviceType());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca1.andUsableflagEqualTo(Constants.IS_VALIDFLAG);
			ca.andPublishflagEqualTo("1");
			ca1.andVersionnoGreaterThanOrEqualTo(form.getCurrenVersion());
			m106e1.setOrderByClause("versionno desc,releasedate desc");
			List<Mi106> list1 =	mi106Dao.selectByExample(m106e1);
			if(!list1.isEmpty()||list1.size()!=0){
				appApi40301Result.setVersion_number(list1.get(0).getVersionno());
				appApi40301Result.setEffective_date(list1.get(0).getReleasedate());
				if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
					appApi40301Result.setDownload_url(list1.get(0).getDownloadurl());
				}else{
					if(list1.get(0).getDownloadurl().startsWith("http://")||list1.get(0).getDownloadurl().startsWith("https://")||list1.get(0).getDownloadurl().startsWith("itms-services:")){
						appApi40301Result.setDownload_url(list1.get(0).getDownloadurl());
					}else{
						String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
								"push_app", form.getCenterId()+File.separator+"Release"+File.separator+list1.get(0).getVersionno()+File.separator+list1.get(0).getDownloadurl(), true);
						appApi40301Result.setDownload_url(url);
					}				
				}
				
				appApi40301Result.setDetails(list.get(0).getReleasecontent());//20131125确认修改为当前版本描述
				if(!CommonUtil.isEmpty(list1.get(0).getFreeuse1())){
					String fileTwoPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
							"push_twodimensional", form.getCenterId()+File.separator+list1.get(0).getFreeuse1(), true);
					appApi40301Result.setTwodimensional(fileTwoPath);
				}
				
//				appApi40301Result.setUpdatesize("");
			}else{
//				appApi40301Result.setVersion_number(form.getCurrenVersion());
//				appApi40301Result.setEffective_date();
//				appApi40301Result.setDownload_url();
//				appApi40301Result.setDetails("不需要更新");
				
				Mi106Example m106e2=new Mi106Example();
				com.yondervision.mi.dto.Mi106Example.Criteria ca2= m106e2.createCriteria();
				ca2.andCenteridEqualTo(form.getCenterId());
				ca2.andDevtypeEqualTo(form.getDeviceType());
				ca2.andValidflagEqualTo(Constants.IS_VALIDFLAG);
				ca2.andUsableflagEqualTo(Constants.IS_NOT_USABLEFLAG);
				ca2.andPublishflagEqualTo(Constants.IS_VALIDFLAG);
				ca2.andVersionnoGreaterThanOrEqualTo(form.getCurrenVersion());
				m106e2.setOrderByClause("versionno desc,releasedate desc");
				List<Mi106> list2 =	mi106Dao.selectByExample(m106e2);
				if(list2.size()>0){
					appApi40301Result.setVersion_number(list2.get(0).getVersionno());
					appApi40301Result.setEffective_date(list2.get(0).getReleasedate());
					if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
						appApi40301Result.setDownload_url(list2.get(0).getDownloadurl());
						if(!CommonUtil.isEmpty(list2.get(0).getFreeuse1())){
							String fileTwoPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
									"push_twodimensional", form.getCenterId()+File.separator+list2.get(0).getFreeuse1(), true);
							appApi40301Result.setTwodimensional(fileTwoPath);
						}
					}else{
						if(list2.get(0).getDownloadurl().startsWith("http://")||list2.get(0).getDownloadurl().startsWith("https://")||list2.get(0).getDownloadurl().startsWith("itms-services:")){
							appApi40301Result.setDownload_url(list1.get(0).getDownloadurl());
						}else{
							String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
									"push_app", form.getCenterId()+File.separator+"Release"+File.separator+list1.get(0).getVersionno()+File.separator+list1.get(0).getDownloadurl(), true);
							appApi40301Result.setDownload_url(url);
						}
						if(!CommonUtil.isEmpty(list1.get(0).getFreeuse1())){
							String fileTwoPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
									"push_twodimensional", form.getCenterId()+File.separator+list1.get(0).getFreeuse1(), true);
							appApi40301Result.setTwodimensional(fileTwoPath);
						}
					}
					
				}else{
					throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP版本信息");
				}			
				
			}
		}else{
			appApi40301Result.setUpdateforce("true");
			
			Mi106Example m106e1=new Mi106Example();
			com.yondervision.mi.dto.Mi106Example.Criteria ca1= m106e1.createCriteria();
			ca1.andCenteridEqualTo(form.getCenterId());
			ca1.andDevtypeEqualTo(form.getDeviceType());
			ca1.andValidflagEqualTo(Constants.IS_VALIDFLAG);
			ca1.andUsableflagEqualTo(Constants.IS_VALIDFLAG);
			ca1.andPublishflagEqualTo("1");			
			m106e1.setOrderByClause("versionno desc,releasedate desc");
			List<Mi106> list1 =	mi106Dao.selectByExample(m106e1);
			if(!list1.isEmpty()||list1.size()!=0){
				appApi40301Result.setVersion_number(list1.get(0).getVersionno());
				appApi40301Result.setEffective_date(list1.get(0).getReleasedate());
				if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
					appApi40301Result.setDownload_url(list1.get(0).getDownloadurl());
				}else{
					if(list1.get(0).getDownloadurl().startsWith("http://")||list1.get(0).getDownloadurl().startsWith("https://")||list1.get(0).getDownloadurl().startsWith("itms-services:")){
						appApi40301Result.setDownload_url(list1.get(0).getDownloadurl());
					}else{
						String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
								"push_app", form.getCenterId()+File.separator+"Release"+File.separator+list1.get(0).getVersionno()+File.separator+list1.get(0).getDownloadurl(), true);
						appApi40301Result.setDownload_url(url);
					}				
				}
				
				appApi40301Result.setDetails(list1.get(0).getReleasecontent());//20131125确认修改为当前版本描述
				if(!CommonUtil.isEmpty(list1.get(0).getFreeuse1())){
					String fileTwoPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
							"push_twodimensional", form.getCenterId()+File.separator+list1.get(0).getFreeuse1(), true);
					appApi40301Result.setTwodimensional(fileTwoPath);
				}
//				appApi40301Result.setUpdatesize("");
			}else{
//				appApi40301Result.setVersion_number(form.getCurrenVersion());
//				appApi40301Result.setEffective_date();
//				appApi40301Result.setDownload_url();
//				appApi40301Result.setDetails("不需要更新");
				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP版本信息");
			}
		}
		
		
		return appApi40301Result;
	}

	public AppApi40301Result appapi40302(AppApiCommonForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppApi40301Result re = new AppApi40301Result();
		Mi106Example m106e=new Mi106Example();
		m106e.setOrderByClause("versionno desc,releasedate desc");
		com.yondervision.mi.dto.Mi106Example.Criteria ca= m106e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andDevtypeEqualTo(form.getDeviceType());
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);			
		ca.andPublishflagEqualTo(Constants.IS_VALIDFLAG);
		ca.andUsableflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi106> list =	mi106Dao.selectByExample(m106e);
		if(list.isEmpty()){			
//				throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP版本信息");
		}else{
			re.setVersion_number(list.get(0).getVersionno());
			re.setEffective_date(list.get(0).getReleasedate());
			if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
				re.setDownload_url(list.get(0).getDownloadurl());
			}else{
				if(list.get(0).getDownloadurl().startsWith("http://")||list.get(0).getDownloadurl().startsWith("https://")||list.get(0).getDownloadurl().startsWith("itms-services:")){
					re.setDownload_url(list.get(0).getDownloadurl());
				}else{
					String url = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
							"push_app", form.getCenterId()+File.separator+"Release"+File.separator+list.get(0).getVersionno()+File.separator+list.get(0).getDownloadurl(), true);
					re.setDownload_url(url);
				}				
			}			
			re.setDetails(list.get(0).getReleasecontent());//20131125确认修改为当前版本描述
			if(!CommonUtil.isEmpty(list.get(0).getFreeuse1())){
				String fileTwoPath = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"push_twodimensional", form.getCenterId()+File.separator+list.get(0).getFreeuse1(), true);
				re.setTwodimensional(fileTwoPath);
			}
		}			
		return re;
	}
	
	public boolean appapi40303(AppApiCommonForm form) throws Exception {
		Mi106Example m106exa = new Mi106Example();
		m106exa.createCriteria().andCenteridEqualTo(form.getCenterId())
		.andDevtypeEqualTo(form.getDeviceType())
		.andVersionnoEqualTo(form.getCurrenVersion())
		.andValidflagEqualTo(Constants.IS_VALIDFLAG)	
		.andPublishflagEqualTo(Constants.IS_VALIDFLAG)
		.andUsableflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi106> curVerInfoList = mi106Dao.selectByExample(m106exa);
		if(CommonUtil.isEmpty(curVerInfoList)){
			if(Constants.MI105_DEVTYPE_IOS.equals(form.getDeviceType())){
				return false;
			}
		}
		return true;
	}

}
