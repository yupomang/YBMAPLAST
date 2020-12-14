package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi311;
import com.yondervision.mi.dto.Mi311Example;
import java.util.List;

public interface Mi311DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	void insert(Mi311 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	List selectByExample(Mi311Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	int deleteByExample(Mi311Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	int countByExample(Mi311Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	int updateByExampleSelective(Mi311 record, Mi311Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI311
	 * @abatorgenerated  Fri Nov 01 15:40:03 CST 2013
	 */
	int updateByExample(Mi311 record, Mi311Example example);
}