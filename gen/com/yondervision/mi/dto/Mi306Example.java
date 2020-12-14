package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi306Example {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public Mi306Example() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	protected Mi306Example(Mi306Example example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI306
	 * @abatorgenerated  Tue Oct 22 20:16:22 CST 2013
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

		public Criteria andStepIdIsNull() {
			addCriterion("STEP_ID is null");
			return this;
		}

		public Criteria andStepIdIsNotNull() {
			addCriterion("STEP_ID is not null");
			return this;
		}

		public Criteria andStepIdEqualTo(String value) {
			addCriterion("STEP_ID =", value, "stepId");
			return this;
		}

		public Criteria andStepIdNotEqualTo(String value) {
			addCriterion("STEP_ID <>", value, "stepId");
			return this;
		}

		public Criteria andStepIdGreaterThan(String value) {
			addCriterion("STEP_ID >", value, "stepId");
			return this;
		}

		public Criteria andStepIdGreaterThanOrEqualTo(String value) {
			addCriterion("STEP_ID >=", value, "stepId");
			return this;
		}

		public Criteria andStepIdLessThan(String value) {
			addCriterion("STEP_ID <", value, "stepId");
			return this;
		}

		public Criteria andStepIdLessThanOrEqualTo(String value) {
			addCriterion("STEP_ID <=", value, "stepId");
			return this;
		}

		public Criteria andStepIdLike(String value) {
			addCriterion("STEP_ID like", value, "stepId");
			return this;
		}

		public Criteria andStepIdNotLike(String value) {
			addCriterion("STEP_ID not like", value, "stepId");
			return this;
		}

		public Criteria andStepIdIn(List values) {
			addCriterion("STEP_ID in", values, "stepId");
			return this;
		}

		public Criteria andStepIdNotIn(List values) {
			addCriterion("STEP_ID not in", values, "stepId");
			return this;
		}

		public Criteria andStepIdBetween(String value1, String value2) {
			addCriterion("STEP_ID between", value1, value2, "stepId");
			return this;
		}

		public Criteria andStepIdNotBetween(String value1, String value2) {
			addCriterion("STEP_ID not between", value1, value2, "stepId");
			return this;
		}

		public Criteria andConsultSubItemIdIsNull() {
			addCriterion("CONSULT_SUB_ITEM_ID is null");
			return this;
		}

		public Criteria andConsultSubItemIdIsNotNull() {
			addCriterion("CONSULT_SUB_ITEM_ID is not null");
			return this;
		}

		public Criteria andConsultSubItemIdEqualTo(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID =", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdNotEqualTo(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID <>", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdGreaterThan(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID >", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdGreaterThanOrEqualTo(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID >=", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdLessThan(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID <", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdLessThanOrEqualTo(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID <=", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdLike(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID like", value, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdNotLike(String value) {
			addCriterion("CONSULT_SUB_ITEM_ID not like", value,
					"consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdIn(List values) {
			addCriterion("CONSULT_SUB_ITEM_ID in", values, "consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdNotIn(List values) {
			addCriterion("CONSULT_SUB_ITEM_ID not in", values,
					"consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdBetween(String value1, String value2) {
			addCriterion("CONSULT_SUB_ITEM_ID between", value1, value2,
					"consultSubItemId");
			return this;
		}

		public Criteria andConsultSubItemIdNotBetween(String value1,
				String value2) {
			addCriterion("CONSULT_SUB_ITEM_ID not between", value1, value2,
					"consultSubItemId");
			return this;
		}

		public Criteria andStepIsNull() {
			addCriterion("STEP is null");
			return this;
		}

		public Criteria andStepIsNotNull() {
			addCriterion("STEP is not null");
			return this;
		}

		public Criteria andStepEqualTo(Integer value) {
			addCriterion("STEP =", value, "step");
			return this;
		}

		public Criteria andStepNotEqualTo(Integer value) {
			addCriterion("STEP <>", value, "step");
			return this;
		}

		public Criteria andStepGreaterThan(Integer value) {
			addCriterion("STEP >", value, "step");
			return this;
		}

		public Criteria andStepGreaterThanOrEqualTo(Integer value) {
			addCriterion("STEP >=", value, "step");
			return this;
		}

		public Criteria andStepLessThan(Integer value) {
			addCriterion("STEP <", value, "step");
			return this;
		}

		public Criteria andStepLessThanOrEqualTo(Integer value) {
			addCriterion("STEP <=", value, "step");
			return this;
		}

		public Criteria andStepIn(List values) {
			addCriterion("STEP in", values, "step");
			return this;
		}

		public Criteria andStepNotIn(List values) {
			addCriterion("STEP not in", values, "step");
			return this;
		}

		public Criteria andStepBetween(Integer value1, Integer value2) {
			addCriterion("STEP between", value1, value2, "step");
			return this;
		}

		public Criteria andStepNotBetween(Integer value1, Integer value2) {
			addCriterion("STEP not between", value1, value2, "step");
			return this;
		}

		public Criteria andStepNameIsNull() {
			addCriterion("STEP_NAME is null");
			return this;
		}

		public Criteria andStepNameIsNotNull() {
			addCriterion("STEP_NAME is not null");
			return this;
		}

		public Criteria andStepNameEqualTo(String value) {
			addCriterion("STEP_NAME =", value, "stepName");
			return this;
		}

		public Criteria andStepNameNotEqualTo(String value) {
			addCriterion("STEP_NAME <>", value, "stepName");
			return this;
		}

		public Criteria andStepNameGreaterThan(String value) {
			addCriterion("STEP_NAME >", value, "stepName");
			return this;
		}

		public Criteria andStepNameGreaterThanOrEqualTo(String value) {
			addCriterion("STEP_NAME >=", value, "stepName");
			return this;
		}

		public Criteria andStepNameLessThan(String value) {
			addCriterion("STEP_NAME <", value, "stepName");
			return this;
		}

		public Criteria andStepNameLessThanOrEqualTo(String value) {
			addCriterion("STEP_NAME <=", value, "stepName");
			return this;
		}

		public Criteria andStepNameLike(String value) {
			addCriterion("STEP_NAME like", value, "stepName");
			return this;
		}

		public Criteria andStepNameNotLike(String value) {
			addCriterion("STEP_NAME not like", value, "stepName");
			return this;
		}

		public Criteria andStepNameIn(List values) {
			addCriterion("STEP_NAME in", values, "stepName");
			return this;
		}

		public Criteria andStepNameNotIn(List values) {
			addCriterion("STEP_NAME not in", values, "stepName");
			return this;
		}

		public Criteria andStepNameBetween(String value1, String value2) {
			addCriterion("STEP_NAME between", value1, value2, "stepName");
			return this;
		}

		public Criteria andStepNameNotBetween(String value1, String value2) {
			addCriterion("STEP_NAME not between", value1, value2, "stepName");
			return this;
		}

		public Criteria andValidflagIsNull() {
			addCriterion("VALIDFLAG is null");
			return this;
		}

		public Criteria andValidflagIsNotNull() {
			addCriterion("VALIDFLAG is not null");
			return this;
		}

		public Criteria andValidflagEqualTo(String value) {
			addCriterion("VALIDFLAG =", value, "validflag");
			return this;
		}

		public Criteria andValidflagNotEqualTo(String value) {
			addCriterion("VALIDFLAG <>", value, "validflag");
			return this;
		}

		public Criteria andValidflagGreaterThan(String value) {
			addCriterion("VALIDFLAG >", value, "validflag");
			return this;
		}

		public Criteria andValidflagGreaterThanOrEqualTo(String value) {
			addCriterion("VALIDFLAG >=", value, "validflag");
			return this;
		}

		public Criteria andValidflagLessThan(String value) {
			addCriterion("VALIDFLAG <", value, "validflag");
			return this;
		}

		public Criteria andValidflagLessThanOrEqualTo(String value) {
			addCriterion("VALIDFLAG <=", value, "validflag");
			return this;
		}

		public Criteria andValidflagLike(String value) {
			addCriterion("VALIDFLAG like", value, "validflag");
			return this;
		}

		public Criteria andValidflagNotLike(String value) {
			addCriterion("VALIDFLAG not like", value, "validflag");
			return this;
		}

		public Criteria andValidflagIn(List values) {
			addCriterion("VALIDFLAG in", values, "validflag");
			return this;
		}

		public Criteria andValidflagNotIn(List values) {
			addCriterion("VALIDFLAG not in", values, "validflag");
			return this;
		}

		public Criteria andValidflagBetween(String value1, String value2) {
			addCriterion("VALIDFLAG between", value1, value2, "validflag");
			return this;
		}

		public Criteria andValidflagNotBetween(String value1, String value2) {
			addCriterion("VALIDFLAG not between", value1, value2, "validflag");
			return this;
		}

		public Criteria andDatemodifiedIsNull() {
			addCriterion("DATEMODIFIED is null");
			return this;
		}

		public Criteria andDatemodifiedIsNotNull() {
			addCriterion("DATEMODIFIED is not null");
			return this;
		}

		public Criteria andDatemodifiedEqualTo(String value) {
			addCriterion("DATEMODIFIED =", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedNotEqualTo(String value) {
			addCriterion("DATEMODIFIED <>", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedGreaterThan(String value) {
			addCriterion("DATEMODIFIED >", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedGreaterThanOrEqualTo(String value) {
			addCriterion("DATEMODIFIED >=", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedLessThan(String value) {
			addCriterion("DATEMODIFIED <", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedLessThanOrEqualTo(String value) {
			addCriterion("DATEMODIFIED <=", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedLike(String value) {
			addCriterion("DATEMODIFIED like", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedNotLike(String value) {
			addCriterion("DATEMODIFIED not like", value, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedIn(List values) {
			addCriterion("DATEMODIFIED in", values, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedNotIn(List values) {
			addCriterion("DATEMODIFIED not in", values, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedBetween(String value1, String value2) {
			addCriterion("DATEMODIFIED between", value1, value2, "datemodified");
			return this;
		}

		public Criteria andDatemodifiedNotBetween(String value1, String value2) {
			addCriterion("DATEMODIFIED not between", value1, value2,
					"datemodified");
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

		public Criteria andLoginidIsNull() {
			addCriterion("LOGINID is null");
			return this;
		}

		public Criteria andLoginidIsNotNull() {
			addCriterion("LOGINID is not null");
			return this;
		}

		public Criteria andLoginidEqualTo(String value) {
			addCriterion("LOGINID =", value, "loginid");
			return this;
		}

		public Criteria andLoginidNotEqualTo(String value) {
			addCriterion("LOGINID <>", value, "loginid");
			return this;
		}

		public Criteria andLoginidGreaterThan(String value) {
			addCriterion("LOGINID >", value, "loginid");
			return this;
		}

		public Criteria andLoginidGreaterThanOrEqualTo(String value) {
			addCriterion("LOGINID >=", value, "loginid");
			return this;
		}

		public Criteria andLoginidLessThan(String value) {
			addCriterion("LOGINID <", value, "loginid");
			return this;
		}

		public Criteria andLoginidLessThanOrEqualTo(String value) {
			addCriterion("LOGINID <=", value, "loginid");
			return this;
		}

		public Criteria andLoginidLike(String value) {
			addCriterion("LOGINID like", value, "loginid");
			return this;
		}

		public Criteria andLoginidNotLike(String value) {
			addCriterion("LOGINID not like", value, "loginid");
			return this;
		}

		public Criteria andLoginidIn(List values) {
			addCriterion("LOGINID in", values, "loginid");
			return this;
		}

		public Criteria andLoginidNotIn(List values) {
			addCriterion("LOGINID not in", values, "loginid");
			return this;
		}

		public Criteria andLoginidBetween(String value1, String value2) {
			addCriterion("LOGINID between", value1, value2, "loginid");
			return this;
		}

		public Criteria andLoginidNotBetween(String value1, String value2) {
			addCriterion("LOGINID not between", value1, value2, "loginid");
			return this;
		}
	}
}