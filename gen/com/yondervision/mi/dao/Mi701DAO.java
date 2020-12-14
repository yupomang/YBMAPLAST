package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi701;
import com.yondervision.mi.dto.Mi701Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import java.util.List;

public interface Mi701DAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    void insert(Mi701WithBLOBs record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByPrimaryKey(Mi701 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByPrimaryKey(Mi701WithBLOBs record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByPrimaryKeySelective(Mi701WithBLOBs record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    List selectByExampleWithoutBLOBs(Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    List selectByExampleWithBLOBs(Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    Mi701WithBLOBs selectByPrimaryKey(Integer seqno);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int deleteByExample(Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int deleteByPrimaryKey(Integer seqno);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int countByExample(Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByExampleSelective(Mi701WithBLOBs record, Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByExample(Mi701WithBLOBs record, Mi701Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI701
     *
     * @abatorgenerated Thu Apr 28 17:32:31 CST 2016
     */
    int updateByExample(Mi701 record, Mi701Example example);
}