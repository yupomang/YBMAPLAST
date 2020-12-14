package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi013Example {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public Mi013Example() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	protected Mi013Example(Mi013Example example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI013
	 * @abatorgenerated  Tue Nov 05 20:41:35 CST 2013
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

		public Criteria andPermissionIsNull() {
			addCriterion("PERMISSION is null");
			return this;
		}

		public Criteria andPermissionIsNotNull() {
			addCriterion("PERMISSION is not null");
			return this;
		}

		public Criteria andPermissionEqualTo(String value) {
			addCriterion("PERMISSION =", value, "permission");
			return this;
		}

		public Criteria andPermissionNotEqualTo(String value) {
			addCriterion("PERMISSION <>", value, "permission");
			return this;
		}

		public Criteria andPermissionGreaterThan(String value) {
			addCriterion("PERMISSION >", value, "permission");
			return this;
		}

		public Criteria andPermissionGreaterThanOrEqualTo(String value) {
			addCriterion("PERMISSION >=", value, "permission");
			return this;
		}

		public Criteria andPermissionLessThan(String value) {
			addCriterion("PERMISSION <", value, "permission");
			return this;
		}

		public Criteria andPermissionLessThanOrEqualTo(String value) {
			addCriterion("PERMISSION <=", value, "permission");
			return this;
		}

		public Criteria andPermissionLike(String value) {
			addCriterion("PERMISSION like", value, "permission");
			return this;
		}

		public Criteria andPermissionNotLike(String value) {
			addCriterion("PERMISSION not like", value, "permission");
			return this;
		}

		public Criteria andPermissionIn(List values) {
			addCriterion("PERMISSION in", values, "permission");
			return this;
		}

		public Criteria andPermissionNotIn(List values) {
			addCriterion("PERMISSION not in", values, "permission");
			return this;
		}

		public Criteria andPermissionBetween(String value1, String value2) {
			addCriterion("PERMISSION between", value1, value2, "permission");
			return this;
		}

		public Criteria andPermissionNotBetween(String value1, String value2) {
			addCriterion("PERMISSION not between", value1, value2, "permission");
			return this;
		}
	}
}