package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi034;
import com.yondervision.mi.dto.Mi034Example;
import java.util.List;

public interface Mi034DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	void insert(Mi034 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int updateByPrimaryKey(Mi034 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int updateByPrimaryKeySelective(Mi034 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	List selectByExample(Mi034Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	Mi034 selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int deleteByExample(Mi034Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int countByExample(Mi034Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int updateByExampleSelective(Mi034 record, Mi034Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI034
	 * @abatorgenerated  Thu Sep 08 10:00:23 CST 2016
	 */
	int updateByExample(Mi034 record, Mi034Example example);
}