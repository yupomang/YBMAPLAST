package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi421Example;

public interface CMi421DAO extends Mi421DAO {
	public void batchInsert(String date);
	public List selectByExamplePageWithBlobs(Mi421Example mi421Example, int skipResults, int rows);
}
