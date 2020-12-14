package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi902;


/** 
* @ClassName: AppApi902Service 
* @Description: 短信验证
* @author sunxl
* @date July 18, 2014 9:33:25 PM  
* 
*/ 
public interface AppApi902Service {
	 public String createSms( String centerid, String phone ,String channel);
	 public List<Mi902> validCode( String phone ,String vcode ,String channel);
}
