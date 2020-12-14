package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi008;
import com.yondervision.mi.dto.Mi008Example;
import java.util.List;

public interface Mi008DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	void insert(Mi008 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	List selectByExample(Mi008Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	int deleteByExample(Mi008Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	int countByExample(Mi008Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	int updateByExampleSelective(Mi008 record, Mi008Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI008
	 * @abatorgenerated  Fri Oct 04 15:07:58 CST 2013
	 */
	int updateByExample(Mi008 record, Mi008Example example);
}