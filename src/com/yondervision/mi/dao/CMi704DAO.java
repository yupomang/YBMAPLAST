package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

/** 
* @ClassName: CMi704DAO 
* @Description:
* @author gongqi
* @date July 18, 2014 9:33:25 PM
* 
*/ 
public interface CMi704DAO extends Mi704DAO {

	/**
	 * 根据期次，获取该期次下所有配置的栏目，按栏目编号升序
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getColumnsByTimes(HashMap<String,String> paraMap) throws Exception;
	
	/**
	 * 根据期次、版块，获取栏目，按栏目编号升序
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getColumnsByTimesForum(HashMap<String,String> paraMap) throws Exception;
}
