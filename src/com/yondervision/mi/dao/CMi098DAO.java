package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.CMi107;

public interface CMi098DAO extends Mi098DAO{
	/**
	 * 查询四大类业务量汇总
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi107100301(CMi107 form);
	/**
	 * 查询业务办理类交易量汇总
	 * @param form
	 * @return
	 */
	public List<HashMap> webapi107100302(CMi107 form);
	public List<HashMap> webapi1071004(CMi107 form);
	/**
	 * 获取信息查询类mi098表数据统计，不包含文件下载
	 * @return
	 */
	public List<HashMap> getLatestTransdate();
	/**
	 * 统计业务办理预约数据，插入098表使用
	 * @param transday
	 * @param currentDate
	 * @return
	 */
	public List<HashMap> selectAppointment(String transday, String currentDate);
}
