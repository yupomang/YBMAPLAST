package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi304;
import com.yondervision.mi.dto.Mi304Example;

import java.util.List;

public interface Mi304DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	void insert(Mi304 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int updateByPrimaryKey(Mi304 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int updateByPrimaryKeySelective(Mi304 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	List selectByExample(Mi304Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	Mi304 selectByPrimaryKey(String conditionGroupId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int deleteByExample(Mi304Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int deleteByPrimaryKey(String conditionGroupId);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int countByExample(Mi304Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int updateByExampleSelective(Mi304 record, Mi304Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI304
	 * @abatorgenerated  Tue Oct 22 20:16:21 CST 2013
	 */
	int updateByExample(Mi304 record, Mi304Example example);
}