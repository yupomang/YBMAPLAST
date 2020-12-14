package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi704DAO;

public class CMi704DAOImpl extends Mi704DAOImpl implements CMi704DAO {
	/**
	 * 根据期次，获取该期次下所有配置的栏目，按栏目编号升序
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getColumnsByTimes(HashMap<String,String> paraMap) throws Exception{
		List<HashMap<String, String>> columnsMapList = getSqlMapClientTemplate().queryForList("CMI704.self_selectColumnsByTimes", paraMap);
		return columnsMapList;
	}
	
	/**
	 * 根据期次、版块，获取栏目，按栏目编号升序
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getColumnsByTimesForum(HashMap<String,String> paraMap) throws Exception{
		List<HashMap<String, String>> columnsMapList = getSqlMapClientTemplate().queryForList("CMI704.self_selectColumnsByTimesForum", paraMap);
		return columnsMapList;
	}
}
