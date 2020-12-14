package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.dto.Mi009Example;
import java.util.List;

public interface Mi009DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	void insert(Mi009 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	List selectByExample(Mi009Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	int deleteByExample(Mi009Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	int countByExample(Mi009Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	int updateByExampleSelective(Mi009 record, Mi009Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI009
	 * @abatorgenerated  Tue Oct 15 09:07:16 CST 2013
	 */
	int updateByExample(Mi009 record, Mi009Example example);
}