package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi006;
import com.yondervision.mi.dto.Mi006Example;
import java.util.List;
import com.yondervision.mi.dto.Mi006Key;

public interface Mi006DAO {

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	void insert(Mi006 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int updateByPrimaryKey(Mi006 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int updateByPrimaryKeySelective(Mi006 record);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	List selectByExample(Mi006Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	Mi006 selectByPrimaryKey(Mi006Key key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int deleteByExample(Mi006Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int deleteByPrimaryKey(Mi006Key key);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int countByExample(Mi006Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int updateByExampleSelective(Mi006 record, Mi006Example example);

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI006
	 * @abatorgenerated  Sun Nov 10 09:04:23 CST 2013
	 */
	int updateByExample(Mi006 record, Mi006Example example);
}