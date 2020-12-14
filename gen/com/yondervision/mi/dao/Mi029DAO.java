package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.dto.Mi029Example;
import java.util.List;

public interface Mi029DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	void insert(Mi029 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int updateByPrimaryKey(Mi029 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int updateByPrimaryKeySelective(Mi029 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	List selectByExample(Mi029Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	Mi029 selectByPrimaryKey(String personalid);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int deleteByExample(Mi029Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int deleteByPrimaryKey(String personalid);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int countByExample(Mi029Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int updateByExampleSelective(Mi029 record, Mi029Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI029
	 * @abatorgenerated  Thu Sep 29 16:10:29 CST 2016
	 */
	int updateByExample(Mi029 record, Mi029Example example);
}