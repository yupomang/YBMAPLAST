package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi115Example {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public Mi115Example() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected Mi115Example(Mi115Example example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI115
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List list = new ArrayList();
			list.add(value1);
			list.add(value2);
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
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

		public Criteria andRoleidIsNull() {
			addCriterion("ROLEID is null");
			return this;
		}

		public Criteria andRoleidIsNotNull() {
			addCriterion("ROLEID is not null");
			return this;
		}

		public Criteria andRoleidEqualTo(String value) {
			addCriterion("ROLEID =", value, "roleid");
			return this;
		}

		public Criteria andRoleidNotEqualTo(String value) {
			addCriterion("ROLEID <>", value, "roleid");
			return this;
		}

		public Criteria andRoleidGreaterThan(String value) {
			addCriterion("ROLEID >", value, "roleid");
			return this;
		}

		public Criteria andRoleidGreaterThanOrEqualTo(String value) {
			addCriterion("ROLEID >=", value, "roleid");
			return this;
		}

		public Criteria andRoleidLessThan(String value) {
			addCriterion("ROLEID <", value, "roleid");
			return this;
		}

		public Criteria andRoleidLessThanOrEqualTo(String value) {
			addCriterion("ROLEID <=", value, "roleid");
			return this;
		}

		public Criteria andRoleidLike(String value) {
			addCriterion("ROLEID like", value, "roleid");
			return this;
		}

		public Criteria andRoleidNotLike(String value) {
			addCriterion("ROLEID not like", value, "roleid");
			return this;
		}

		public Criteria andRoleidIn(List values) {
			addCriterion("ROLEID in", values, "roleid");
			return this;
		}

		public Criteria andRoleidNotIn(List values) {
			addCriterion("ROLEID not in", values, "roleid");
			return this;
		}

		public Criteria andRoleidBetween(String value1, String value2) {
			addCriterion("ROLEID between", value1, value2, "roleid");
			return this;
		}

		public Criteria andRoleidNotBetween(String value1, String value2) {
			addCriterion("ROLEID not between", value1, value2, "roleid");
			return this;
		}

		public Criteria andDatecreatedIsNull() {
			addCriterion("DATECREATED is null");
			return this;
		}

		public Criteria andDatecreatedIsNotNull() {
			addCriterion("DATECREATED is not null");
			return this;
		}

		public Criteria andDatecreatedEqualTo(String value) {
			addCriterion("DATECREATED =", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedNotEqualTo(String value) {
			addCriterion("DATECREATED <>", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedGreaterThan(String value) {
			addCriterion("DATECREATED >", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedGreaterThanOrEqualTo(String value) {
			addCriterion("DATECREATED >=", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedLessThan(String value) {
			addCriterion("DATECREATED <", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedLessThanOrEqualTo(String value) {
			addCriterion("DATECREATED <=", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedLike(String value) {
			addCriterion("DATECREATED like", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedNotLike(String value) {
			addCriterion("DATECREATED not like", value, "datecreated");
			return this;
		}

		public Criteria andDatecreatedIn(List values) {
			addCriterion("DATECREATED in", values, "datecreated");
			return this;
		}

		public Criteria andDatecreatedNotIn(List values) {
			addCriterion("DATECREATED not in", values, "datecreated");
			return this;
		}

		public Criteria andDatecreatedBetween(String value1, String value2) {
			addCriterion("DATECREATED between", value1, value2, "datecreated");
			return this;
		}

		public Criteria andDatecreatedNotBetween(String value1, String value2) {
			addCriterion("DATECREATED not between", value1, value2,
					"datecreated");
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

		public Criteria andFreeuse4EqualTo(Integer value) {
			addCriterion("FREEUSE4 =", value, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4NotEqualTo(Integer value) {
			addCriterion("FREEUSE4 <>", value, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4GreaterThan(Integer value) {
			addCriterion("FREEUSE4 >", value, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4GreaterThanOrEqualTo(Integer value) {
			addCriterion("FREEUSE4 >=", value, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4LessThan(Integer value) {
			addCriterion("FREEUSE4 <", value, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4LessThanOrEqualTo(Integer value) {
			addCriterion("FREEUSE4 <=", value, "freeuse4");
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

		public Criteria andFreeuse4Between(Integer value1, Integer value2) {
			addCriterion("FREEUSE4 between", value1, value2, "freeuse4");
			return this;
		}

		public Criteria andFreeuse4NotBetween(Integer value1, Integer value2) {
			addCriterion("FREEUSE4 not between", value1, value2, "freeuse4");
			return this;
		}
	}
}