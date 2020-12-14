package com.yondervision.mi.service;

import com.yondervision.mi.dto.CMi042;
import com.yondervision.mi.result.WebApi04204_queryResult;

public interface WebApi042Service {
	/**
	 * 新增
	 * @param form
	 * @throws Exception
	 */
	public void webapi04201(CMi042 form) throws Exception;
	
	/**
	 * 分页查询
	 * @param form
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws Exception
	 */
	public WebApi04204_queryResult webapi04204(CMi042 form) throws Exception;
}
