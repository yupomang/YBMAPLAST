package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi708DAO;
import com.yondervision.mi.dto.Mi708;
import com.yondervision.mi.dto.Mi708Example;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi708DAOImpl extends SqlMapClientDaoSupport implements Mi708DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public Mi708DAOImpl() {
		super();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public void insert(Mi708 record) {
		getSqlMapClientTemplate()
				.insert("MI708.abatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int updateByPrimaryKey(Mi708 record) {
		int rows = getSqlMapClientTemplate().update(
				"MI708.abatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int updateByPrimaryKeySelective(Mi708 record) {
		int rows = getSqlMapClientTemplate().update(
				"MI708.abatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public List selectByExample(Mi708Example example) {
		List list = getSqlMapClientTemplate().queryForList(
				"MI708.abatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public Mi708 selectByPrimaryKey(Integer seqno) {
		Mi708 key = new Mi708();
		key.setSeqno(seqno);
		Mi708 record = (Mi708) getSqlMapClientTemplate().queryForObject(
				"MI708.abatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int deleteByExample(Mi708Example example) {
		int rows = getSqlMapClientTemplate().delete(
				"MI708.abatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int deleteByPrimaryKey(Integer seqno) {
		Mi708 key = new Mi708();
		key.setSeqno(seqno);
		int rows = getSqlMapClientTemplate().delete(
				"MI708.abatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int countByExample(Mi708Example example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"MI708.abatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int updateByExampleSelective(Mi708 record, Mi708Example example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"MI708.abatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	public int updateByExample(Mi708 record, Mi708Example example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"MI708.abatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI708
	 * @abatorgenerated  Thu Apr 14 17:08:28 CST 2016
	 */
	private static class UpdateByExampleParms extends Mi708Example {
		private Object record;

		public UpdateByExampleParms(Object record, Mi708Example example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}