package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi308DAO;
import com.yondervision.mi.dto.Mi308;
import com.yondervision.mi.dto.Mi308Example;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi308DAOImpl extends SqlMapClientDaoSupport implements Mi308DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public Mi308DAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public void insert(Mi308 record) {
		getSqlMapClientTemplate()
				.insert("MI308.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int updateByPrimaryKey(Mi308 record) {
		int rows = getSqlMapClientTemplate().update(
				"MI308.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int updateByPrimaryKeySelective(Mi308 record) {
		int rows = getSqlMapClientTemplate().update(
				"MI308.abatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public List selectByExample(Mi308Example example) {
		List list = getSqlMapClientTemplate().queryForList(
				"MI308.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public Mi308 selectByPrimaryKey(String consultId) {
		Mi308 key = new Mi308();
		key.setConsultId(consultId);
		Mi308 record = (Mi308) getSqlMapClientTemplate().queryForObject(
				"MI308.abatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int deleteByExample(Mi308Example example) {
		int rows = getSqlMapClientTemplate().delete(
				"MI308.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int deleteByPrimaryKey(String consultId) {
		Mi308 key = new Mi308();
		key.setConsultId(consultId);
		int rows = getSqlMapClientTemplate().delete(
				"MI308.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int countByExample(Mi308Example example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"MI308.abatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int updateByExampleSelective(Mi308 record, Mi308Example example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"MI308.abatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	public int updateByExample(Mi308 record, Mi308Example example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"MI308.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI308
	 * @abatorgenerated  Fri Nov 01 15:40:02 CST 2013
	 */
	private static class UpdateByExampleParms extends Mi308Example {
		private Object record;

		public UpdateByExampleParms(Object record, Mi308Example example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}