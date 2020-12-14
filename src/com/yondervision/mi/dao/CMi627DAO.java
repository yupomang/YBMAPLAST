package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi627;
import com.yondervision.mi.dto.Mi627;
import com.yondervision.mi.util.CommonUtil;

public interface CMi627DAO extends Mi627DAO {
	/*
	 * 检查是否可以增加一年的信息
	 */
	public boolean checkOneYear(CMi627 form) throws Exception;
	/*
	 * 查询可预约的工作日
	 */
	public List<Mi627> selectResDates(CMi627 form);
}
