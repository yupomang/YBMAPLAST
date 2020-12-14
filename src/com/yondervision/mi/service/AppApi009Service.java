package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.form.AppApi00901Form;
import com.yondervision.mi.form.AppApi00902Form;
import com.yondervision.mi.form.AppApi00903Form;
import com.yondervision.mi.form.AppApi00905Form;
import com.yondervision.mi.result.AppApi00901Result;
import com.yondervision.mi.result.AppApi00902Result;
import com.yondervision.mi.result.AppApi00903Result;
import com.yondervision.mi.result.AppApi00905Result;

/** 
* @ClassName: AppApi009Service 
* @Description: 贷款试算
* @author Caozhongyan
* @date Oct 11, 2013 5:18:02 PM   
* 
*/ 
public interface AppApi009Service {
	public AppApi00901Result appapi00901(AppApi00901Form form) throws Exception;
	public AppApi00902Result appapi00902(AppApi00902Form form) throws Exception;
	public List<AppApi00903Result> appapi00903(AppApi00903Form form) throws Exception;
	public void appapi00904() throws Exception;
	public AppApi00905Result appapi00905(AppApi00905Form form) throws Exception;
	
}
