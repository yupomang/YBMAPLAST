package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi014Example {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public Mi014Example() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    protected Mi014Example(Mi014Example example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI014
     *
     * @abatorgenerated Mon Nov 04 20:08:44 CST 2013
     */
    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andFuncidIsNull() {
            addCriterion("FUNCID is null");
            return this;
        }

        public Criteria andFuncidIsNotNull() {
            addCriterion("FUNCID is not null");
            return this;
        }

        public Criteria andFuncidEqualTo(String value) {
            addCriterion("FUNCID =", value, "funcid");
            return this;
        }

        public Criteria andFuncidNotEqualTo(String value) {
            addCriterion("FUNCID <>", value, "funcid");
            return this;
        }

        public Criteria andFuncidGreaterThan(String value) {
            addCriterion("FUNCID >", value, "funcid");
            return this;
        }

        public Criteria andFuncidGreaterThanOrEqualTo(String value) {
            addCriterion("FUNCID >=", value, "funcid");
            return this;
        }

        public Criteria andFuncidLessThan(String value) {
            addCriterion("FUNCID <", value, "funcid");
            return this;
        }

        public Criteria andFuncidLessThanOrEqualTo(String value) {
            addCriterion("FUNCID <=", value, "funcid");
            return this;
        }

        public Criteria andFuncidLike(String value) {
            addCriterion("FUNCID like", value, "funcid");
            return this;
        }

        public Criteria andFuncidNotLike(String value) {
            addCriterion("FUNCID not like", value, "funcid");
            return this;
        }

        public Criteria andFuncidIn(List values) {
            addCriterion("FUNCID in", values, "funcid");
            return this;
        }

        public Criteria andFuncidNotIn(List values) {
            addCriterion("FUNCID not in", values, "funcid");
            return this;
        }

        public Criteria andFuncidBetween(String value1, String value2) {
            addCriterion("FUNCID between", value1, value2, "funcid");
            return this;
        }

        public Criteria andFuncidNotBetween(String value1, String value2) {
            addCriterion("FUNCID not between", value1, value2, "funcid");
            return this;
        }

        public Criteria andSubnameIsNull() {
            addCriterion("SUBNAME is null");
            return this;
        }

        public Criteria andSubnameIsNotNull() {
            addCriterion("SUBNAME is not null");
            return this;
        }

        public Criteria andSubnameEqualTo(String value) {
            addCriterion("SUBNAME =", value, "subname");
            return this;
        }

        public Criteria andSubnameNotEqualTo(String value) {
            addCriterion("SUBNAME <>", value, "subname");
            return this;
        }

        public Criteria andSubnameGreaterThan(String value) {
            addCriterion("SUBNAME >", value, "subname");
            return this;
        }

        public Criteria andSubnameGreaterThanOrEqualTo(String value) {
            addCriterion("SUBNAME >=", value, "subname");
            return this;
        }

        public Criteria andSubnameLessThan(String value) {
            addCriterion("SUBNAME <", value, "subname");
            return this;
        }

        public Criteria andSubnameLessThanOrEqualTo(String value) {
            addCriterion("SUBNAME <=", value, "subname");
            return this;
        }

        public Criteria andSubnameLike(String value) {
            addCriterion("SUBNAME like", value, "subname");
            return this;
        }

        public Criteria andSubnameNotLike(String value) {
            addCriterion("SUBNAME not like", value, "subname");
            return this;
        }

        public Criteria andSubnameIn(List values) {
            addCriterion("SUBNAME in", values, "subname");
            return this;
        }

        public Criteria andSubnameNotIn(List values) {
            addCriterion("SUBNAME not in", values, "subname");
            return this;
        }

        public Criteria andSubnameBetween(String value1, String value2) {
            addCriterion("SUBNAME between", value1, value2, "subname");
            return this;
        }

        public Criteria andSubnameNotBetween(String value1, String value2) {
            addCriterion("SUBNAME not between", value1, value2, "subname");
            return this;
        }

        public Criteria andSubnoIsNull() {
            addCriterion("SUBNO is null");
            return this;
        }

        public Criteria andSubnoIsNotNull() {
            addCriterion("SUBNO is not null");
            return this;
        }

        public Criteria andSubnoEqualTo(Integer value) {
            addCriterion("SUBNO =", value, "subno");
            return this;
        }

        public Criteria andSubnoNotEqualTo(Integer value) {
            addCriterion("SUBNO <>", value, "subno");
            return this;
        }

        public Criteria andSubnoGreaterThan(Integer value) {
            addCriterion("SUBNO >", value, "subno");
            return this;
        }

        public Criteria andSubnoGreaterThanOrEqualTo(Integer value) {
            addCriterion("SUBNO >=", value, "subno");
            return this;
        }

        public Criteria andSubnoLessThan(Integer value) {
            addCriterion("SUBNO <", value, "subno");
            return this;
        }

        public Criteria andSubnoLessThanOrEqualTo(Integer value) {
            addCriterion("SUBNO <=", value, "subno");
            return this;
        }

        public Criteria andSubnoIn(List values) {
            addCriterion("SUBNO in", values, "subno");
            return this;
        }

        public Criteria andSubnoNotIn(List values) {
            addCriterion("SUBNO not in", values, "subno");
            return this;
        }

        public Criteria andSubnoBetween(Integer value1, Integer value2) {
            addCriterion("SUBNO between", value1, value2, "subno");
            return this;
        }

        public Criteria andSubnoNotBetween(Integer value1, Integer value2) {
            addCriterion("SUBNO not between", value1, value2, "subno");
            return this;
        }

        public Criteria andSubdescIsNull() {
            addCriterion("SUBDESC is null");
            return this;
        }

        public Criteria andSubdescIsNotNull() {
            addCriterion("SUBDESC is not null");
            return this;
        }

        public Criteria andSubdescEqualTo(String value) {
            addCriterion("SUBDESC =", value, "subdesc");
            return this;
        }

        public Criteria andSubdescNotEqualTo(String value) {
            addCriterion("SUBDESC <>", value, "subdesc");
            return this;
        }

        public Criteria andSubdescGreaterThan(String value) {
            addCriterion("SUBDESC >", value, "subdesc");
            return this;
        }

        public Criteria andSubdescGreaterThanOrEqualTo(String value) {
            addCriterion("SUBDESC >=", value, "subdesc");
            return this;
        }

        public Criteria andSubdescLessThan(String value) {
            addCriterion("SUBDESC <", value, "subdesc");
            return this;
        }

        public Criteria andSubdescLessThanOrEqualTo(String value) {
            addCriterion("SUBDESC <=", value, "subdesc");
            return this;
        }

        public Criteria andSubdescLike(String value) {
            addCriterion("SUBDESC like", value, "subdesc");
            return this;
        }

        public Criteria andSubdescNotLike(String value) {
            addCriterion("SUBDESC not like", value, "subdesc");
            return this;
        }

        public Criteria andSubdescIn(List values) {
            addCriterion("SUBDESC in", values, "subdesc");
            return this;
        }

        public Criteria andSubdescNotIn(List values) {
            addCriterion("SUBDESC not in", values, "subdesc");
            return this;
        }

        public Criteria andSubdescBetween(String value1, String value2) {
            addCriterion("SUBDESC between", value1, value2, "subdesc");
            return this;
        }

        public Criteria andSubdescNotBetween(String value1, String value2) {
            addCriterion("SUBDESC not between", value1, value2, "subdesc");
            return this;
        }
    }
}