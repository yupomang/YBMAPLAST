package com.yondervision.mi.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.dto.CMi029;
import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi030;
import com.yondervision.mi.dto.Mi031;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.form.AppApi50002Form;
import com.yondervision.mi.form.AppApiCommonForm;

public interface WebApi030Service {
	/**
	 * 单位用户信息查询
	 * @param form
	 * @throws Exception
	 */
	public Mi030 webapi03001(AppApi50002Form form) throws Exception;
	/**
	 * 单位用户信息添加
	 * @param form
	 * @throws Exception
	 */
	public Mi030 webapi03002(AppApi50002Form form) throws Exception;
	/**
	 * 单登登录返回信息更新
	 * @param form
	 * @throws Exception
	 */
	public void webapi03003(AppApi50002Form form ,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
