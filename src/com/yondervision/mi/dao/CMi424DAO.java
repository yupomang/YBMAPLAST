package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

public interface CMi424DAO extends Mi424DAO{
	/**群发消息、定制消息业务统计**/
	public List<HashMap> webapi42401(String centerid, String pid, String startDate, String endDate);
	/**模板消息业务统计**/
	public List<HashMap> webapi42402(String centerid, String pid, String startDate, String endDate);
}
