package com.yondervision.mi.dao.impl;

import com.yondervision.mi.dao.Mi620DAO;
import com.yondervision.mi.dto.Mi620;
import com.yondervision.mi.dto.Mi620Example;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Mi620DAOImpl extends SqlMapClientDaoSupport implements Mi620DAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public Mi620DAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public void insert(Mi620 record) {
        getSqlMapClientTemplate().insert("MI620.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int updateByPrimaryKey(Mi620 record) {
        int rows = getSqlMapClientTemplate().update("MI620.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int updateByPrimaryKeySelective(Mi620 record) {
        int rows = getSqlMapClientTemplate().update("MI620.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public List selectByExample(Mi620Example example) {
        List list = getSqlMapClientTemplate().queryForList("MI620.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public Mi620 selectByPrimaryKey(String appobusiid) {
        Mi620 key = new Mi620();
        key.setAppobusiid(appobusiid);
        Mi620 record = (Mi620) getSqlMapClientTemplate().queryForObject("MI620.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int deleteByExample(Mi620Example example) {
        int rows = getSqlMapClientTemplate().delete("MI620.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int deleteByPrimaryKey(String appobusiid) {
        Mi620 key = new Mi620();
        key.setAppobusiid(appobusiid);
        int rows = getSqlMapClientTemplate().delete("MI620.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int countByExample(Mi620Example example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("MI620.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int updateByExampleSelective(Mi620 record, Mi620Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI620.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    public int updateByExample(Mi620 record, Mi620Example example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("MI620.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI620
     *
     * @abatorgenerated Tue Jul 29 14:50:57 CST 2014
     */
    private static class UpdateByExampleParms extends Mi620Example {
        private Object record;

        public UpdateByExampleParms(Object record, Mi620Example example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}