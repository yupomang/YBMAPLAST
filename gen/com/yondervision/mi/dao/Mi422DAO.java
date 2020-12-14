package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi422;
import com.yondervision.mi.dto.Mi422Example;
import java.util.List;

public interface Mi422DAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    void insert(Mi422 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByPrimaryKeyWithoutBLOBs(Mi422 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(Mi422 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByPrimaryKeySelective(Mi422 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    List selectByExampleWithoutBLOBs(Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    List selectByExampleWithBLOBs(Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    Mi422 selectByPrimaryKey(String msgid);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int deleteByExample(Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int deleteByPrimaryKey(String msgid);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int countByExample(Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByExampleSelective(Mi422 record, Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByExampleWithBLOBs(Mi422 record, Mi422Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI422
     *
     * @abatorgenerated Tue Oct 17 08:50:14 CST 2017
     */
    int updateByExampleWithoutBLOBs(Mi422 record, Mi422Example example);
}