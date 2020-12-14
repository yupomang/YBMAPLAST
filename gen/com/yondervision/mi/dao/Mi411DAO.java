package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi411;
import com.yondervision.mi.dto.Mi411Example;
import java.util.List;

public interface Mi411DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	void insert(Mi411 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int updateByPrimaryKey(Mi411 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int updateByPrimaryKeySelective(Mi411 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	List selectByExample(Mi411Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	Mi411 selectByPrimaryKey(String templateid);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int deleteByExample(Mi411Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int deleteByPrimaryKey(String templateid);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int countByExample(Mi411Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int updateByExampleSelective(Mi411 record, Mi411Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	int updateByExample(Mi411 record, Mi411Example example);
}