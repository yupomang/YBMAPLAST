package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi405Example;
import com.yondervision.mi.result.WebApi40502_queryResult;

public interface CMi405DAO {

	/**
	 * @deprecated 消息模板分页查询
	 * @param form
	 * @return
	 */
	public WebApi40502_queryResult select40502Page(Mi405Example example,String page,String rows);
}
