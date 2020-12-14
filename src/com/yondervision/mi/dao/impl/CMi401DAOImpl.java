/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.dao.CMi401DAO;
import com.yondervision.mi.dto.Mi401Example;

public class CMi401DAOImpl extends Mi401DAOImpl implements CMi401DAO {

	public List selectByExamplePageWithBlobs(Mi401Example mi401Example, int skipResults, int rows){
    	return getSqlMapClientTemplate().queryForList("MI401.abatorgenerated_selectByExampleWithBLOBs", mi401Example, skipResults, rows);
    }
}
