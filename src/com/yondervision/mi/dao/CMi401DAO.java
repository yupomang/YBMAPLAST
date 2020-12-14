/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao
 * 文件名：     CMi402DAO.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi401Example;

public interface CMi401DAO extends Mi401DAO {

	List selectByExamplePageWithBlobs(Mi401Example mi401Example, int skipResults, int rows);

}
