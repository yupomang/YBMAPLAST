package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi306DAO;
import com.yondervision.mi.dto.Mi306;
import com.yondervision.mi.dto.Mi306Example;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi306DAOImpl extends SqlMapClientDaoSupport implements Mi306DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public Mi306DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public void insert(Mi306 record) {
        getSqlMapClientTemplate().insert("MI306.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int updateByPrimaryKey(Mi306 record) {
        int rows = getSqlMapClientTemplate().update("MI306.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int updateByPrimaryKeySelective(Mi306 record) {
        int rows = getSqlMapClientTemplate().update("MI306.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public List selectByExample(Mi306Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI306.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public Mi306 selectByPrimaryKey(String stepId) {
        Mi306 key = new Mi306();
        key.setStepId(stepId);
        Mi306 record = (Mi306) getSqlMapClientTemplate().queryForObject("MI306.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int deleteByExample(Mi306Example example) {
        int rows = getSqlMapClientTemplate().delete("MI306.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int deleteByPrimaryKey(String stepId) {
        Mi306 key = new Mi306();
        key.setStepId(stepId);
        int rows = getSqlMapClientTemplate().delete("MI306.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int countByExample(Mi306Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI306.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int updateByExampleSelective(Mi306 record, Mi306Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI306.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    public int updateByExample(Mi306 record, Mi306Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI306.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI306
     *
     * @abatorgenerated Tue Oct 22 20:16:22 CST 2013
     */
    private static class UpdateByExampleParms extends Mi306Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi306Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}