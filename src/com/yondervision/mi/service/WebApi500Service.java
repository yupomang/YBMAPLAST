package com.yondervision.mi.service;

import java.util.List;

import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.dto.Mi034;
import com.yondervision.mi.dto.Mi048;

import net.sf.json.JSONObject;

public interface WebApi500Service {
	
	/**
	 * 获取中心列表，除去00000000
	 * @return
	 * @throws Exception
	 */
	public List<Mi001> webapi500centerList()throws Exception; 
	
	/**
	 * 根据姓名，证件号，手机获取个人信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Mi029 webapi50001(CMi029 form)throws Exception;
	
	/**
	 * 根据用户获取单位信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Mi030 webapi50002(CMi029 form)throws Exception;
	
	/**
	 * 根据用户，服务渠道信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi031> webapi50005(CMi029 form)throws Exception;
	/**
	 * 根据用户查询关联人
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi034> webapi50006(CMi029 form)throws Exception;
	/**
	 * 根据用户查询关联账户
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<Mi048> webapi50007(CMi029 form)throws Exception;
	/**
	 * 根据personalid查询个人的预约信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public JSONObject webapi50018(CMi029 form)throws Exception;
	
}
