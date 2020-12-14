package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.dto.Mi107Example;

import java.util.List;

public interface Mi107DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	void insert(Mi107 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int updateByPrimaryKey(Mi107 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int updateByPrimaryKeySelective(Mi107 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	List selectByExample(Mi107Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	Mi107 selectByPrimaryKey(Integer seqno);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int deleteByExample(Mi107Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int deleteByPrimaryKey(Integer seqno);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int countByExample(Mi107Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int updateByExampleSelective(Mi107 record, Mi107Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI107
	 * @abatorgenerated  Sat Oct 08 19:35:53 CST 2016
	 */
	int updateByExample(Mi107 record, Mi107Example example);
}