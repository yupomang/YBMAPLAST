package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

public interface CMi423DAO extends Mi423DAO{
	public void batchInsert(String date);
	//统计模板类消息
	public List<HashMap> messageStatisticsTemplate(String date);
	//统计定制消息和群推消息
	public List<HashMap> messageStatistics(String date);
}
