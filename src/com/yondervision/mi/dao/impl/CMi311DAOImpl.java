/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi311DAOImpl.java
 * 创建日期：2013-11-4
 */
package com.yondervision.mi.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.CMi311DAO;
import com.yondervision.mi.dto.Mi311;
import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 * 
 */
public class CMi311DAOImpl extends Mi311DAOImpl implements CMi311DAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.dao.CMi311DAO#selectAllByListConditionId(java.util
	 * .List)
	 */
	@SuppressWarnings("unchecked")
	public List<Mi311> selectAllByListConditionId(List<String> listConditionId) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("listConditionId", listConditionId);
		paraMap.put("listConditionId1", listConditionId);
		paraMap.put("listConditionId2", listConditionId);
		return this.getSqlMapClientTemplate().queryForList(
				"CMI311.selectAllByListConditionId", paraMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervision.mi.dao.CMi311DAO#selectByListConditionId(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public Mi311 selectByListConditionId(List<String> listConditionId) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("listConditionId", listConditionId);
		paraMap.put("listConditionId1", listConditionId);
		paraMap.put("listConditionId2", listConditionId);
		paraMap.put("size", listConditionId.size());
		
		List<Mi311> listMi311 = this.getSqlMapClientTemplate().queryForList(
				"CMI311.selectByListConditionId", paraMap);
		/*
		 * 查询到的组合信息不唯一是抛出系统异常
		 */
		if ((!CommonUtil.isEmpty(listMi311)) && listMi311.size() > 1) {
			List<String> listConditionRadioId = new ArrayList<String>();
			for (Iterator<Mi311> iterator = listMi311.iterator(); iterator
					.hasNext();) {
				Mi311 mi311 = (Mi311) iterator.next();
				listConditionRadioId.add(mi311.getConditionRadioId());
			}

			LoggerUtil.getLogger().error(
					ERROR.SYS.getLogText("Mi311中存在重复的条件组合数据"
							+ listConditionId.toString() + "\n组合ID为"
							+ listConditionRadioId.toString()));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),
					ERROR.SYS.getValue());
		}
		
		return CommonUtil.isEmpty(listMi311) ? null : listMi311.get(0);
	}

}
