package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.form.AppApi60001Form;
import com.yondervision.mi.form.AppApi60002Form;
import com.yondervision.mi.result.AppApi60002Result;

/** 
* @ClassName: AppApi600Service 
* @Description: 在线留言
* @author gongqi
* @date July 16, 2014 9:33:25 PM  
* 
*/ 
public interface AppApi600Service {
	public void appapi60001(AppApi60001Form form) throws Exception;
	public List<AppApi60002Result> appapi60002(AppApi60002Form form) throws Exception;
}
