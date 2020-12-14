package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi131Example {
    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    protected List oredCriteria;

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public Mi131Example() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    protected Mi131Example(Mi131Example example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
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
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table MI131
     *
     * @abatorgenerated Tue Sep 27 17:02:26 CST 2016
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

        public Criteria andPicidIsNull() {
            addCriterion("PICID is null");
            return this;
        }

        public Criteria andPicidIsNotNull() {
            addCriterion("PICID is not null");
            return this;
        }

        public Criteria andPicidEqualTo(String value) {
            addCriterion("PICID =", value, "picid");
            return this;
        }

        public Criteria andPicidNotEqualTo(String value) {
            addCriterion("PICID <>", value, "picid");
            return this;
        }

        public Criteria andPicidGreaterThan(String value) {
            addCriterion("PICID >", value, "picid");
            return this;
        }

        public Criteria andPicidGreaterThanOrEqualTo(String value) {
            addCriterion("PICID >=", value, "picid");
            return this;
        }

        public Criteria andPicidLessThan(String value) {
            addCriterion("PICID <", value, "picid");
            return this;
        }

        public Criteria andPicidLessThanOrEqualTo(String value) {
            addCriterion("PICID <=", value, "picid");
            return this;
        }

        public Criteria andPicidLike(String value) {
            addCriterion("PICID like", value, "picid");
            return this;
        }

        public Criteria andPicidNotLike(String value) {
            addCriterion("PICID not like", value, "picid");
            return this;
        }

        public Criteria andPicidIn(List values) {
            addCriterion("PICID in", values, "picid");
            return this;
        }

        public Criteria andPicidNotIn(List values) {
            addCriterion("PICID not in", values, "picid");
            return this;
        }

        public Criteria andPicidBetween(String value1, String value2) {
            addCriterion("PICID between", value1, value2, "picid");
            return this;
        }

        public Criteria andPicidNotBetween(String value1, String value2) {
            addCriterion("PICID not between", value1, value2, "picid");
            return this;
        }

        public Criteria andCenteridIsNull() {
            addCriterion("CENTERID is null");
            return this;
        }

        public Criteria andCenteridIsNotNull() {
            addCriterion("CENTERID is not null");
            return this;
        }

        public Criteria andCenteridEqualTo(String value) {
            addCriterion("CENTERID =", value, "centerid");
            return this;
        }

        public Criteria andCenteridNotEqualTo(String value) {
            addCriterion("CENTERID <>", value, "centerid");
            return this;
        }

        public Criteria andCenteridGreaterThan(String value) {
            addCriterion("CENTERID >", value, "centerid");
            return this;
        }

        public Criteria andCenteridGreaterThanOrEqualTo(String value) {
            addCriterion("CENTERID >=", value, "centerid");
            return this;
        }

        public Criteria andCenteridLessThan(String value) {
            addCriterion("CENTERID <", value, "centerid");
            return this;
        }

        public Criteria andCenteridLessThanOrEqualTo(String value) {
            addCriterion("CENTERID <=", value, "centerid");
            return this;
        }

        public Criteria andCenteridLike(String value) {
            addCriterion("CENTERID like", value, "centerid");
            return this;
        }

        public Criteria andCenteridNotLike(String value) {
            addCriterion("CENTERID not like", value, "centerid");
            return this;
        }

        public Criteria andCenteridIn(List values) {
            addCriterion("CENTERID in", values, "centerid");
            return this;
        }

        public Criteria andCenteridNotIn(List values) {
            addCriterion("CENTERID not in", values, "centerid");
            return this;
        }

        public Criteria andCenteridBetween(String value1, String value2) {
            addCriterion("CENTERID between", value1, value2, "centerid");
            return this;
        }

        public Criteria andCenteridNotBetween(String value1, String value2) {
            addCriterion("CENTERID not between", value1, value2, "centerid");
            return this;
        }

        public Criteria andUploadtimeIsNull() {
            addCriterion("UPLOADTIME is null");
            return this;
        }

        public Criteria andUploadtimeIsNotNull() {
            addCriterion("UPLOADTIME is not null");
            return this;
        }

        public Criteria andUploadtimeEqualTo(String value) {
            addCriterion("UPLOADTIME =", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeNotEqualTo(String value) {
            addCriterion("UPLOADTIME <>", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeGreaterThan(String value) {
            addCriterion("UPLOADTIME >", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPLOADTIME >=", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeLessThan(String value) {
            addCriterion("UPLOADTIME <", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeLessThanOrEqualTo(String value) {
            addCriterion("UPLOADTIME <=", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeLike(String value) {
            addCriterion("UPLOADTIME like", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeNotLike(String value) {
            addCriterion("UPLOADTIME not like", value, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeIn(List values) {
            addCriterion("UPLOADTIME in", values, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeNotIn(List values) {
            addCriterion("UPLOADTIME not in", values, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeBetween(String value1, String value2) {
            addCriterion("UPLOADTIME between", value1, value2, "uploadtime");
            return this;
        }

        public Criteria andUploadtimeNotBetween(String value1, String value2) {
            addCriterion("UPLOADTIME not between", value1, value2, "uploadtime");
            return this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("OPERATOR is null");
            return this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("OPERATOR is not null");
            return this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("OPERATOR =", value, "operator");
            return this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("OPERATOR <>", value, "operator");
            return this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("OPERATOR >", value, "operator");
            return this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATOR >=", value, "operator");
            return this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("OPERATOR <", value, "operator");
            return this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("OPERATOR <=", value, "operator");
            return this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("OPERATOR like", value, "operator");
            return this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("OPERATOR not like", value, "operator");
            return this;
        }

        public Criteria andOperatorIn(List values) {
            addCriterion("OPERATOR in", values, "operator");
            return this;
        }

        public Criteria andOperatorNotIn(List values) {
            addCriterion("OPERATOR not in", values, "operator");
            return this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("OPERATOR between", value1, value2, "operator");
            return this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("OPERATOR not between", value1, value2, "operator");
            return this;
        }

        public Criteria andSysnameIsNull() {
            addCriterion("SYSNAME is null");
            return this;
        }

        public Criteria andSysnameIsNotNull() {
            addCriterion("SYSNAME is not null");
            return this;
        }

        public Criteria andSysnameEqualTo(String value) {
            addCriterion("SYSNAME =", value, "sysname");
            return this;
        }

        public Criteria andSysnameNotEqualTo(String value) {
            addCriterion("SYSNAME <>", value, "sysname");
            return this;
        }

        public Criteria andSysnameGreaterThan(String value) {
            addCriterion("SYSNAME >", value, "sysname");
            return this;
        }

        public Criteria andSysnameGreaterThanOrEqualTo(String value) {
            addCriterion("SYSNAME >=", value, "sysname");
            return this;
        }

        public Criteria andSysnameLessThan(String value) {
            addCriterion("SYSNAME <", value, "sysname");
            return this;
        }

        public Criteria andSysnameLessThanOrEqualTo(String value) {
            addCriterion("SYSNAME <=", value, "sysname");
            return this;
        }

        public Criteria andSysnameLike(String value) {
            addCriterion("SYSNAME like", value, "sysname");
            return this;
        }

        public Criteria andSysnameNotLike(String value) {
            addCriterion("SYSNAME not like", value, "sysname");
            return this;
        }

        public Criteria andSysnameIn(List values) {
            addCriterion("SYSNAME in", values, "sysname");
            return this;
        }

        public Criteria andSysnameNotIn(List values) {
            addCriterion("SYSNAME not in", values, "sysname");
            return this;
        }

        public Criteria andSysnameBetween(String value1, String value2) {
            addCriterion("SYSNAME between", value1, value2, "sysname");
            return this;
        }

        public Criteria andSysnameNotBetween(String value1, String value2) {
            addCriterion("SYSNAME not between", value1, value2, "sysname");
            return this;
        }

        public Criteria andRealnameIsNull() {
            addCriterion("REALNAME is null");
            return this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("REALNAME is not null");
            return this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("REALNAME =", value, "realname");
            return this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("REALNAME <>", value, "realname");
            return this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("REALNAME >", value, "realname");
            return this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("REALNAME >=", value, "realname");
            return this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("REALNAME <", value, "realname");
            return this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("REALNAME <=", value, "realname");
            return this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("REALNAME like", value, "realname");
            return this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("REALNAME not like", value, "realname");
            return this;
        }

        public Criteria andRealnameIn(List values) {
            addCriterion("REALNAME in", values, "realname");
            return this;
        }

        public Criteria andRealnameNotIn(List values) {
            addCriterion("REALNAME not in", values, "realname");
            return this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("REALNAME between", value1, value2, "realname");
            return this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("REALNAME not between", value1, value2, "realname");
            return this;
        }

        public Criteria andPicurlIsNull() {
            addCriterion("PICURL is null");
            return this;
        }

        public Criteria andPicurlIsNotNull() {
            addCriterion("PICURL is not null");
            return this;
        }

        public Criteria andPicurlEqualTo(String value) {
            addCriterion("PICURL =", value, "picurl");
            return this;
        }

        public Criteria andPicurlNotEqualTo(String value) {
            addCriterion("PICURL <>", value, "picurl");
            return this;
        }

        public Criteria andPicurlGreaterThan(String value) {
            addCriterion("PICURL >", value, "picurl");
            return this;
        }

        public Criteria andPicurlGreaterThanOrEqualTo(String value) {
            addCriterion("PICURL >=", value, "picurl");
            return this;
        }

        public Criteria andPicurlLessThan(String value) {
            addCriterion("PICURL <", value, "picurl");
            return this;
        }

        public Criteria andPicurlLessThanOrEqualTo(String value) {
            addCriterion("PICURL <=", value, "picurl");
            return this;
        }

        public Criteria andPicurlLike(String value) {
            addCriterion("PICURL like", value, "picurl");
            return this;
        }

        public Criteria andPicurlNotLike(String value) {
            addCriterion("PICURL not like", value, "picurl");
            return this;
        }

        public Criteria andPicurlIn(List values) {
            addCriterion("PICURL in", values, "picurl");
            return this;
        }

        public Criteria andPicurlNotIn(List values) {
            addCriterion("PICURL not in", values, "picurl");
            return this;
        }

        public Criteria andPicurlBetween(String value1, String value2) {
            addCriterion("PICURL between", value1, value2, "picurl");
            return this;
        }

        public Criteria andPicurlNotBetween(String value1, String value2) {
            addCriterion("PICURL not between", value1, value2, "picurl");
            return this;
        }

        public Criteria andGroupidIsNull() {
            addCriterion("GROUPID is null");
            return this;
        }

        public Criteria andGroupidIsNotNull() {
            addCriterion("GROUPID is not null");
            return this;
        }

        public Criteria andGroupidEqualTo(String value) {
            addCriterion("GROUPID =", value, "groupid");
            return this;
        }

        public Criteria andGroupidNotEqualTo(String value) {
            addCriterion("GROUPID <>", value, "groupid");
            return this;
        }

        public Criteria andGroupidGreaterThan(String value) {
            addCriterion("GROUPID >", value, "groupid");
            return this;
        }

        public Criteria andGroupidGreaterThanOrEqualTo(String value) {
            addCriterion("GROUPID >=", value, "groupid");
            return this;
        }

        public Criteria andGroupidLessThan(String value) {
            addCriterion("GROUPID <", value, "groupid");
            return this;
        }

        public Criteria andGroupidLessThanOrEqualTo(String value) {
            addCriterion("GROUPID <=", value, "groupid");
            return this;
        }

        public Criteria andGroupidLike(String value) {
            addCriterion("GROUPID like", value, "groupid");
            return this;
        }

        public Criteria andGroupidNotLike(String value) {
            addCriterion("GROUPID not like", value, "groupid");
            return this;
        }

        public Criteria andGroupidIn(List values) {
            addCriterion("GROUPID in", values, "groupid");
            return this;
        }

        public Criteria andGroupidNotIn(List values) {
            addCriterion("GROUPID not in", values, "groupid");
            return this;
        }

        public Criteria andGroupidBetween(String value1, String value2) {
            addCriterion("GROUPID between", value1, value2, "groupid");
            return this;
        }

        public Criteria andGroupidNotBetween(String value1, String value2) {
            addCriterion("GROUPID not between", value1, value2, "groupid");
            return this;
        }

        public Criteria andFreeuse1IsNull() {
            addCriterion("FREEUSE1 is null");
            return this;
        }

        public Criteria andFreeuse1IsNotNull() {
            addCriterion("FREEUSE1 is not null");
            return this;
        }

        public Criteria andFreeuse1EqualTo(String value) {
            addCriterion("FREEUSE1 =", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1NotEqualTo(String value) {
            addCriterion("FREEUSE1 <>", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1GreaterThan(String value) {
            addCriterion("FREEUSE1 >", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE1 >=", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1LessThan(String value) {
            addCriterion("FREEUSE1 <", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE1 <=", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1Like(String value) {
            addCriterion("FREEUSE1 like", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1NotLike(String value) {
            addCriterion("FREEUSE1 not like", value, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1In(List values) {
            addCriterion("FREEUSE1 in", values, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1NotIn(List values) {
            addCriterion("FREEUSE1 not in", values, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1Between(String value1, String value2) {
            addCriterion("FREEUSE1 between", value1, value2, "freeuse1");
            return this;
        }

        public Criteria andFreeuse1NotBetween(String value1, String value2) {
            addCriterion("FREEUSE1 not between", value1, value2, "freeuse1");
            return this;
        }

        public Criteria andFreeuse2IsNull() {
            addCriterion("FREEUSE2 is null");
            return this;
        }

        public Criteria andFreeuse2IsNotNull() {
            addCriterion("FREEUSE2 is not null");
            return this;
        }

        public Criteria andFreeuse2EqualTo(String value) {
            addCriterion("FREEUSE2 =", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2NotEqualTo(String value) {
            addCriterion("FREEUSE2 <>", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2GreaterThan(String value) {
            addCriterion("FREEUSE2 >", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE2 >=", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2LessThan(String value) {
            addCriterion("FREEUSE2 <", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE2 <=", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2Like(String value) {
            addCriterion("FREEUSE2 like", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2NotLike(String value) {
            addCriterion("FREEUSE2 not like", value, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2In(List values) {
            addCriterion("FREEUSE2 in", values, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2NotIn(List values) {
            addCriterion("FREEUSE2 not in", values, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2Between(String value1, String value2) {
            addCriterion("FREEUSE2 between", value1, value2, "freeuse2");
            return this;
        }

        public Criteria andFreeuse2NotBetween(String value1, String value2) {
            addCriterion("FREEUSE2 not between", value1, value2, "freeuse2");
            return this;
        }

        public Criteria andFreeuse3IsNull() {
            addCriterion("FREEUSE3 is null");
            return this;
        }

        public Criteria andFreeuse3IsNotNull() {
            addCriterion("FREEUSE3 is not null");
            return this;
        }

        public Criteria andFreeuse3EqualTo(String value) {
            addCriterion("FREEUSE3 =", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3NotEqualTo(String value) {
            addCriterion("FREEUSE3 <>", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3GreaterThan(String value) {
            addCriterion("FREEUSE3 >", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE3 >=", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3LessThan(String value) {
            addCriterion("FREEUSE3 <", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE3 <=", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3Like(String value) {
            addCriterion("FREEUSE3 like", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3NotLike(String value) {
            addCriterion("FREEUSE3 not like", value, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3In(List values) {
            addCriterion("FREEUSE3 in", values, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3NotIn(List values) {
            addCriterion("FREEUSE3 not in", values, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3Between(String value1, String value2) {
            addCriterion("FREEUSE3 between", value1, value2, "freeuse3");
            return this;
        }

        public Criteria andFreeuse3NotBetween(String value1, String value2) {
            addCriterion("FREEUSE3 not between", value1, value2, "freeuse3");
            return this;
        }

        public Criteria andFreeuse4IsNull() {
            addCriterion("FREEUSE4 is null");
            return this;
        }

        public Criteria andFreeuse4IsNotNull() {
            addCriterion("FREEUSE4 is not null");
            return this;
        }

        public Criteria andFreeuse4EqualTo(String value) {
            addCriterion("FREEUSE4 =", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4NotEqualTo(String value) {
            addCriterion("FREEUSE4 <>", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4GreaterThan(String value) {
            addCriterion("FREEUSE4 >", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE4 >=", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4LessThan(String value) {
            addCriterion("FREEUSE4 <", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE4 <=", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4Like(String value) {
            addCriterion("FREEUSE4 like", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4NotLike(String value) {
            addCriterion("FREEUSE4 not like", value, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4In(List values) {
            addCriterion("FREEUSE4 in", values, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4NotIn(List values) {
            addCriterion("FREEUSE4 not in", values, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4Between(String value1, String value2) {
            addCriterion("FREEUSE4 between", value1, value2, "freeuse4");
            return this;
        }

        public Criteria andFreeuse4NotBetween(String value1, String value2) {
            addCriterion("FREEUSE4 not between", value1, value2, "freeuse4");
            return this;
        }

        public Criteria andFreeuse5IsNull() {
            addCriterion("FREEUSE5 is null");
            return this;
        }

        public Criteria andFreeuse5IsNotNull() {
            addCriterion("FREEUSE5 is not null");
            return this;
        }

        public Criteria andFreeuse5EqualTo(String value) {
            addCriterion("FREEUSE5 =", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5NotEqualTo(String value) {
            addCriterion("FREEUSE5 <>", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5GreaterThan(String value) {
            addCriterion("FREEUSE5 >", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE5 >=", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5LessThan(String value) {
            addCriterion("FREEUSE5 <", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE5 <=", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5Like(String value) {
            addCriterion("FREEUSE5 like", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5NotLike(String value) {
            addCriterion("FREEUSE5 not like", value, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5In(List values) {
            addCriterion("FREEUSE5 in", values, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5NotIn(List values) {
            addCriterion("FREEUSE5 not in", values, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5Between(String value1, String value2) {
            addCriterion("FREEUSE5 between", value1, value2, "freeuse5");
            return this;
        }

        public Criteria andFreeuse5NotBetween(String value1, String value2) {
            addCriterion("FREEUSE5 not between", value1, value2, "freeuse5");
            return this;
        }

        public Criteria andFreeuse6IsNull() {
            addCriterion("FREEUSE6 is null");
            return this;
        }

        public Criteria andFreeuse6IsNotNull() {
            addCriterion("FREEUSE6 is not null");
            return this;
        }

        public Criteria andFreeuse6EqualTo(String value) {
            addCriterion("FREEUSE6 =", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6NotEqualTo(String value) {
            addCriterion("FREEUSE6 <>", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6GreaterThan(String value) {
            addCriterion("FREEUSE6 >", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6GreaterThanOrEqualTo(String value) {
            addCriterion("FREEUSE6 >=", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6LessThan(String value) {
            addCriterion("FREEUSE6 <", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6LessThanOrEqualTo(String value) {
            addCriterion("FREEUSE6 <=", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6Like(String value) {
            addCriterion("FREEUSE6 like", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6NotLike(String value) {
            addCriterion("FREEUSE6 not like", value, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6In(List values) {
            addCriterion("FREEUSE6 in", values, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6NotIn(List values) {
            addCriterion("FREEUSE6 not in", values, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6Between(String value1, String value2) {
            addCriterion("FREEUSE6 between", value1, value2, "freeuse6");
            return this;
        }

        public Criteria andFreeuse6NotBetween(String value1, String value2) {
            addCriterion("FREEUSE6 not between", value1, value2, "freeuse6");
            return this;
        }

        public Criteria andMaterialtypeIsNull() {
            addCriterion("MATERIALTYPE is null");
            return this;
        }

        public Criteria andMaterialtypeIsNotNull() {
            addCriterion("MATERIALTYPE is not null");
            return this;
        }

        public Criteria andMaterialtypeEqualTo(String value) {
            addCriterion("MATERIALTYPE =", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeNotEqualTo(String value) {
            addCriterion("MATERIALTYPE <>", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeGreaterThan(String value) {
            addCriterion("MATERIALTYPE >", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeGreaterThanOrEqualTo(String value) {
            addCriterion("MATERIALTYPE >=", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeLessThan(String value) {
            addCriterion("MATERIALTYPE <", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeLessThanOrEqualTo(String value) {
            addCriterion("MATERIALTYPE <=", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeLike(String value) {
            addCriterion("MATERIALTYPE like", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeNotLike(String value) {
            addCriterion("MATERIALTYPE not like", value, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeIn(List values) {
            addCriterion("MATERIALTYPE in", values, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeNotIn(List values) {
            addCriterion("MATERIALTYPE not in", values, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeBetween(String value1, String value2) {
            addCriterion("MATERIALTYPE between", value1, value2, "materialtype");
            return this;
        }

        public Criteria andMaterialtypeNotBetween(String value1, String value2) {
            addCriterion("MATERIALTYPE not between", value1, value2, "materialtype");
            return this;
        }
    }
}