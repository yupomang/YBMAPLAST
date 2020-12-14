package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi406;
import com.yondervision.mi.dto.Mi406Example;
import java.util.List;

public interface Mi406DAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    void insert(Mi406 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int updateByPrimaryKey(Mi406 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int updateByPrimaryKeySelective(Mi406 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    List selectByExample(Mi406Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    Mi406 selectByPrimaryKey(String templateDetailId);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int deleteByExample(Mi406Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int deleteByPrimaryKey(String templateDetailId);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int countByExample(Mi406Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int updateByExampleSelective(Mi406 record, Mi406Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI406
     *
     * @abatorgenerated Fri May 06 13:43:30 CST 2016
     */
    int updateByExample(Mi406 record, Mi406Example example);
}