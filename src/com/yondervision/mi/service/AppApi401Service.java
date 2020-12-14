package com.yondervision.mi.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi119;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.form.AppApi40108Form;
import com.yondervision.mi.form.AppApiCommonForm;

/** 
* @ClassName: AppApi401Service 
* @Description: 用户管理
* @author Caozhongyan
* @date Oct 12, 2013 3:39:28 PM   
* 
*/ 
public interface AppApi401Service {
	public void appapi40101(AppApiCommonForm form, HttpServletResponse response) throws Exception;
	public void appapi40102(AppApi40102Form form) throws Exception;
	public boolean appapi40103(AppApi40102Form form) throws Exception;
	public int appapi40104(AppApi40102Form form) throws Exception;
	public int appapi40105(AppApi40102Form form) throws Exception;
	public void appapi40106(AppApi40102Form form) throws Exception;
	public List<Mi103> appapi40107(AppApi40102Form form) throws Exception;
	public List<Mi119> appapi40108(AppApi40102Form form) throws Exception;
	public void appapi40109(AppApi40102Form form) throws Exception;
	public List<Mi103> appapi40110(AppApi40102Form form) throws Exception;
	public void appapi40111(AppApi40102Form form) throws Exception;
	public List<Mi103> appapi40112(String userid, String centerid) throws Exception;
	public int appapi40113(AppApi40108Form form) throws Exception;
	public List<Mi103> appapi40114(AppApi40102Form form) throws Exception;
	public int appapi40117(AppApi40102Form form) throws Exception;
}
