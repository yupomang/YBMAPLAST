package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi049;
import com.yondervision.mi.dto.Mi049Example;
import java.util.List;

public interface Mi049DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	void insert(Mi049 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int updateByPrimaryKey(Mi049 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int updateByPrimaryKeySelective(Mi049 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	List selectByExample(Mi049Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	Mi049 selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int deleteByExample(Mi049Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int countByExample(Mi049Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int updateByExampleSelective(Mi049 record, Mi049Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI049
	 * @abatorgenerated  Mon Oct 12 16:18:13 CST 2015
	 */
	int updateByExample(Mi049 record, Mi049Example example);
}