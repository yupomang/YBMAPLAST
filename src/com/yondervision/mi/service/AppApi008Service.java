package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.form.AppApi00801Form;

/** 
* @ClassName: AppApi008Service 
* @Description: 楼盘信息查询
* @author Caozhongyan
* @date Sep 27, 2013 2:50:40 PM   
* 
*/ 
public interface AppApi008Service {
	public List<Mi203> appApi00801Select(AppApi00801Form form) throws Exception;
}
