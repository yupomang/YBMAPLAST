package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi014DAO;
import com.yondervision.mi.dto.Mi014;
import com.yondervision.mi.dto.Mi014Example;
import com.yondervision.mi.dto.Mi014Key;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi014DAOImpl extends SqlMapClientDaoSupport implements Mi014DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public Mi014DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public void insert(Mi014 record) {
        getSqlMapClientTemplate().insert("MI014.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int updateByPrimaryKey(Mi014 record) {
        int rows = getSqlMapClientTemplate().update("MI014.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int updateByPrimaryKeySelective(Mi014 record) {
        int rows = getSqlMapClientTemplate().update("MI014.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public List selectByExample(Mi014Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI014.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public Mi014 selectByPrimaryKey(Mi014Key key) {
        Mi014 record = (Mi014) getSqlMapClientTemplate().queryForObject("MI014.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int deleteByExample(Mi014Example example) {
        int rows = getSqlMapClientTemplate().delete("MI014.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int deleteByPrimaryKey(Mi014Key key) {
        int rows = getSqlMapClientTemplate().delete("MI014.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int countByExample(Mi014Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI014.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int updateByExampleSelective(Mi014 record, Mi014Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI014.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public int updateByExample(Mi014 record, Mi014Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI014.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    private static class UpdateByExampleParms extends Mi014Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi014Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}