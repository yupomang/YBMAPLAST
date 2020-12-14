package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi098;
import com.yondervision.mi.dto.Mi098Example;
import java.util.List;

public interface Mi098DAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    void insert(Mi098 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int updateByPrimaryKey(Mi098 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int updateByPrimaryKeySelective(Mi098 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    List selectByExample(Mi098Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    Mi098 selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int deleteByExample(Mi098Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int countByExample(Mi098Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int updateByExampleSelective(Mi098 record, Mi098Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI098
     *
     * @abatorgenerated Thu Sep 28 10:28:04 CST 2017
     */
    int updateByExample(Mi098 record, Mi098Example example);
}