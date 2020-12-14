package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.Mi120;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.form.AppApi41101Form;
 
/** 
* @ClassName: WebApi411Service 
* @Description: APP动画图片
* @author Caozhongyan
* @date Nov 14, 2013 3:42:55 PM   
* 
*/ 
public interface AppApi411Service {
		
	/**
	 * APP动画图片
	 */
	public List<Mi120> appapi41101(AppApi41101Form form) throws Exception;
	/**
	 * APP动画图片明细
	 */
	public List<Mi121> appapi41102(Mi120 form) throws Exception;
	
}
