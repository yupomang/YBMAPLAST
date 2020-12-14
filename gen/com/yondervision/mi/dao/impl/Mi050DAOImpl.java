package com.yondervision.mi.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yondervision.mi.dao.Mi050DAO;
import com.yondervision.mi.dto.Mi050;
import com.yondervision.mi.dto.Mi050Example;

public class Mi050DAOImpl extends SqlMapClientDaoSupport implements Mi050DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public Mi050DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public void insert(Mi050 record) {
        getSqlMapClientTemplate().insert("MI050.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int updateByPrimaryKey(Mi050 record) {
        int rows = getSqlMapClientTemplate().update("MI050.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int updateByPrimaryKeySelective(Mi050 record) {
        int rows = getSqlMapClientTemplate().update("MI050.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public List selectByExample(Mi050Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI050.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public Mi050 selectByPrimaryKey(String apiid) {
        Mi050 key = new Mi050();
        key.setApiid(apiid);
        Mi050 record = (Mi050) getSqlMapClientTemplate().queryForObject("MI050.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int deleteByExample(Mi050Example example) {
        int rows = getSqlMapClientTemplate().delete("MI050.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int deleteByPrimaryKey(String apiid) {
        Mi050 key = new Mi050();
        key.setApiid(apiid);
        int rows = getSqlMapClientTemplate().delete("MI050.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int countByExample(Mi050Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI050.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int updateByExampleSelective(Mi050 record, Mi050Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI050.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    public int updateByExample(Mi050 record, Mi050Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI050.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI050
     *
     * @abatorgenerated Tue Aug 09 09:44:03 CST 2016
     */
    private static class UpdateByExampleParms extends Mi050Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi050Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}