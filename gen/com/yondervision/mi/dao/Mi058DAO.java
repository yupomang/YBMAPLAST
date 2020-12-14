package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi058;
import com.yondervision.mi.dto.Mi058Example;
import java.util.List;

public interface Mi058DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	void insert(Mi058 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int updateByPrimaryKey(Mi058 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int updateByPrimaryKeySelective(Mi058 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	List selectByExample(Mi058Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	Mi058 selectByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int deleteByExample(Mi058Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int countByExample(Mi058Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int updateByExampleSelective(Mi058 record, Mi058Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI058
	 * @abatorgenerated  Fri Sep 16 14:49:52 CST 2016
	 */
	int updateByExample(Mi058 record, Mi058Example example);
}