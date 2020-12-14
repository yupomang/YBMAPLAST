package com.yondervision.mi.service;



import java.util.List;

import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi04004_queryResult;


public interface WebApi040Service {
	/**
	 * 应用新增
	 */
	public void webapi04001(CMi040 form) throws Exception;
	
	/**
	 * 应用删除
	 */
	public int webapi04002(CMi040 form) throws Exception;
	
	/**
	 * 应用修改
	 */
	public int webapi04003(CMi040 form) throws Exception;
	
	/**
	 * 查询
	 */
//	public List<Mi109> webapi00904(CMi109 form) throws Exception;
	
	/**
	 * 应用查询(分页)
	 * @param form
	 * @return
	 */
	public WebApi04004_queryResult webapi04004(CMi040 form)throws Exception;
	
	
	/**
	 * 认证设置（新增和修改）
	 * @return 
	 */
	public int webapi04005(CMi040 form)throws Exception;
	
	/**
	 * 按APPID,APPKEY信息查询并更新APPTOKEN
	 * @param form
	 * @return
	 */
	public Mi040 webapi04006(AppApiCommonForm form)throws Exception;
	
	
	/**
	 * 获取应用列表
	 * @param form
	 * @return
	 */
	public List<Mi040> webapi04007(CMi040 form)throws Exception;
	
	/**
	 * 根据PID查询应用
	 * @param form
	 * @return
	 */
	public Mi040 webapi04008(String pid)throws Exception;

}
