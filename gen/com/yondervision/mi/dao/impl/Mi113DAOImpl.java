package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi113DAO;
import com.yondervision.mi.dto.Mi113;
import com.yondervision.mi.dto.Mi113Example;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi113DAOImpl extends SqlMapClientDaoSupport implements Mi113DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public Mi113DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public void insert(Mi113 record) {
        getSqlMapClientTemplate().insert("MI113.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int updateByPrimaryKey(Mi113 record) {
        int rows = getSqlMapClientTemplate().update("MI113.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int updateByPrimaryKeySelective(Mi113 record) {
        int rows = getSqlMapClientTemplate().update("MI113.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public List selectByExample(Mi113Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI113.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public Mi113 selectByPrimaryKey(String opcode) {
        Mi113 key = new Mi113();
        key.setOpcode(opcode);
        Mi113 record = (Mi113) getSqlMapClientTemplate().queryForObject("MI113.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int deleteByExample(Mi113Example example) {
        int rows = getSqlMapClientTemplate().delete("MI113.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int deleteByPrimaryKey(String opcode) {
        Mi113 key = new Mi113();
        key.setOpcode(opcode);
        int rows = getSqlMapClientTemplate().delete("MI113.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int countByExample(Mi113Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI113.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int updateByExampleSelective(Mi113 record, Mi113Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI113.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    public int updateByExample(Mi113 record, Mi113Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI113.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI113
     *
     * @abatorgenerated Thu Sep 26 10:13:18 CST 2013
     */
    private static class UpdateByExampleParms extends Mi113Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi113Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}