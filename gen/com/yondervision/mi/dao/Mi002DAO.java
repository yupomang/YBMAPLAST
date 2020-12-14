package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi002;
import com.yondervision.mi.dto.Mi002Example;
import java.util.List;

public interface Mi002DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	void insert(Mi002 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	List selectByExample(Mi002Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	int deleteByExample(Mi002Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	int countByExample(Mi002Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	int updateByExampleSelective(Mi002 record, Mi002Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI002
	 * @abatorgenerated  Fri Sep 27 20:46:09 CST 2013
	 */
	int updateByExample(Mi002 record, Mi002Example example);
}