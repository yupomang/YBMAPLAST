package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi044;
import com.yondervision.mi.dto.Mi044Example;
import java.util.List;

public interface Mi044DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	void insert(Mi044 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int updateByPrimaryKey(Mi044 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int updateByPrimaryKeySelective(Mi044 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	List selectByExample(Mi044Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	Mi044 selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int deleteByExample(Mi044Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int countByExample(Mi044Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int updateByExampleSelective(Mi044 record, Mi044Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI044
	 * @abatorgenerated  Sat Aug 20 19:04:54 CST 2016
	 */
	int updateByExample(Mi044 record, Mi044Example example);
}