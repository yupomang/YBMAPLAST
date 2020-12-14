package com.yondervision.mi.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yondervision.mi.dao.Mi037DAO;
import com.yondervision.mi.dto.Mi037;
import com.yondervision.mi.dto.Mi037Example;

public class Mi037DAOImpl extends SqlMapClientDaoSupport implements Mi037DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public Mi037DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public void insert(Mi037 record) {
        getSqlMapClientTemplate().insert("MI037.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int updateByPrimaryKey(Mi037 record) {
        int rows = getSqlMapClientTemplate().update("MI037.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int updateByPrimaryKeySelective(Mi037 record) {
        int rows = getSqlMapClientTemplate().update("MI037.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public List selectByExample(Mi037Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI037.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public Mi037 selectByPrimaryKey(String communicationid) {
        Mi037 key = new Mi037();
        key.setCommunicationid(communicationid);
        Mi037 record = (Mi037) getSqlMapClientTemplate().queryForObject("MI037.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int deleteByExample(Mi037Example example) {
        int rows = getSqlMapClientTemplate().delete("MI037.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int deleteByPrimaryKey(String communicationid) {
        Mi037 key = new Mi037();
        key.setCommunicationid(communicationid);
        int rows = getSqlMapClientTemplate().delete("MI037.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int countByExample(Mi037Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI037.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int updateByExampleSelective(Mi037 record, Mi037Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI037.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    public int updateByExample(Mi037 record, Mi037Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI037.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI037
     *
     * @abatorgenerated Mon Aug 08 17:14:41 CST 2016
     */
    private static class UpdateByExampleParms extends Mi037Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi037Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}