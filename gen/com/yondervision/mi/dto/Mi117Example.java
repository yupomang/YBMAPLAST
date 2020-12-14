package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi117Example {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public Mi117Example() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected Mi117Example(Mi117Example example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
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
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI117
	 * @abatorgenerated  Thu Sep 26 10:13:18 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI117
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

		public Criteria andModuleidIsNull() {
			addCriterion("MODULEID is null");
			return this;
		}

		public Criteria andModuleidIsNotNull() {
			addCriterion("MODULEID is not null");
			return this;
		}

		public Criteria andModuleidEqualTo(String value) {
			addCriterion("MODULEID =", value, "moduleid");
			return this;
		}

		public Criteria andModuleidNotEqualTo(String value) {
			addCriterion("MODULEID <>", value, "moduleid");
			return this;
		}

		public Criteria andModuleidGreaterThan(String value) {
			addCriterion("MODULEID >", value, "moduleid");
			return this;
		}

		public Criteria andModuleidGreaterThanOrEqualTo(String value) {
			addCriterion("MODULEID >=", value, "moduleid");
			return this;
		}

		public Criteria andModuleidLessThan(String value) {
			addCriterion("MODULEID <", value, "moduleid");
			return this;
		}

		public Criteria andModuleidLessThanOrEqualTo(String value) {
			addCriterion("MODULEID <=", value, "moduleid");
			return this;
		}

		public Criteria andModuleidLike(String value) {
			addCriterion("MODULEID like", value, "moduleid");
			return this;
		}

		public Criteria andModuleidNotLike(String value) {
			addCriterion("MODULEID not like", value, "moduleid");
			return this;
		}

		public Criteria andModuleidIn(List values) {
			addCriterion("MODULEID in", values, "moduleid");
			return this;
		}

		public Criteria andModuleidNotIn(List values) {
			addCriterion("MODULEID not in", values, "moduleid");
			return this;
		}

		public Criteria andModuleidBetween(String value1, String value2) {
			addCriterion("MODULEID between", value1, value2, "moduleid");
			return this;
		}

		public Criteria andModuleidNotBetween(String value1, String value2) {
			addCriterion("MODULEID not between", value1, value2, "moduleid");
			return this;
		}

		public Criteria andFuncnameIsNull() {
			addCriterion("FUNCNAME is null");
			return this;
		}

		public Criteria andFuncnameIsNotNull() {
			addCriterion("FUNCNAME is not null");
			return this;
		}

		public Criteria andFuncnameEqualTo(String value) {
			addCriterion("FUNCNAME =", value, "funcname");
			return this;
		}

		public Criteria andFuncnameNotEqualTo(String value) {
			addCriterion("FUNCNAME <>", value, "funcname");
			return this;
		}

		public Criteria andFuncnameGreaterThan(String value) {
			addCriterion("FUNCNAME >", value, "funcname");
			return this;
		}

		public Criteria andFuncnameGreaterThanOrEqualTo(String value) {
			addCriterion("FUNCNAME >=", value, "funcname");
			return this;
		}

		public Criteria andFuncnameLessThan(String value) {
			addCriterion("FUNCNAME <", value, "funcname");
			return this;
		}

		public Criteria andFuncnameLessThanOrEqualTo(String value) {
			addCriterion("FUNCNAME <=", value, "funcname");
			return this;
		}

		public Criteria andFuncnameLike(String value) {
			addCriterion("FUNCNAME like", value, "funcname");
			return this;
		}

		public Criteria andFuncnameNotLike(String value) {
			addCriterion("FUNCNAME not like", value, "funcname");
			return this;
		}

		public Criteria andFuncnameIn(List values) {
			addCriterion("FUNCNAME in", values, "funcname");
			return this;
		}

		public Criteria andFuncnameNotIn(List values) {
			addCriterion("FUNCNAME not in", values, "funcname");
			return this;
		}

		public Criteria andFuncnameBetween(String value1, String value2) {
			addCriterion("FUNCNAME between", value1, value2, "funcname");
			return this;
		}

		public Criteria andFuncnameNotBetween(String value1, String value2) {
			addCriterion("FUNCNAME not between", value1, value2, "funcname");
			return this;
		}

		public Criteria andFuncdescIsNull() {
			addCriterion("FUNCDESC is null");
			return this;
		}

		public Criteria andFuncdescIsNotNull() {
			addCriterion("FUNCDESC is not null");
			return this;
		}

		public Criteria andFuncdescEqualTo(String value) {
			addCriterion("FUNCDESC =", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescNotEqualTo(String value) {
			addCriterion("FUNCDESC <>", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescGreaterThan(String value) {
			addCriterion("FUNCDESC >", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescGreaterThanOrEqualTo(String value) {
			addCriterion("FUNCDESC >=", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescLessThan(String value) {
			addCriterion("FUNCDESC <", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescLessThanOrEqualTo(String value) {
			addCriterion("FUNCDESC <=", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescLike(String value) {
			addCriterion("FUNCDESC like", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescNotLike(String value) {
			addCriterion("FUNCDESC not like", value, "funcdesc");
			return this;
		}

		public Criteria andFuncdescIn(List values) {
			addCriterion("FUNCDESC in", values, "funcdesc");
			return this;
		}

		public Criteria andFuncdescNotIn(List values) {
			addCriterion("FUNCDESC not in", values, "funcdesc");
			return this;
		}

		public Criteria andFuncdescBetween(String value1, String value2) {
			addCriterion("FUNCDESC between", value1, value2, "funcdesc");
			return this;
		}

		public Criteria andFuncdescNotBetween(String value1, String value2) {
			addCriterion("FUNCDESC not between", value1, value2, "funcdesc");
			return this;
		}

		public Criteria andFuncpyIsNull() {
			addCriterion("FUNCPY is null");
			return this;
		}

		public Criteria andFuncpyIsNotNull() {
			addCriterion("FUNCPY is not null");
			return this;
		}

		public Criteria andFuncpyEqualTo(String value) {
			addCriterion("FUNCPY =", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyNotEqualTo(String value) {
			addCriterion("FUNCPY <>", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyGreaterThan(String value) {
			addCriterion("FUNCPY >", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyGreaterThanOrEqualTo(String value) {
			addCriterion("FUNCPY >=", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyLessThan(String value) {
			addCriterion("FUNCPY <", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyLessThanOrEqualTo(String value) {
			addCriterion("FUNCPY <=", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyLike(String value) {
			addCriterion("FUNCPY like", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyNotLike(String value) {
			addCriterion("FUNCPY not like", value, "funcpy");
			return this;
		}

		public Criteria andFuncpyIn(List values) {
			addCriterion("FUNCPY in", values, "funcpy");
			return this;
		}

		public Criteria andFuncpyNotIn(List values) {
			addCriterion("FUNCPY not in", values, "funcpy");
			return this;
		}

		public Criteria andFuncpyBetween(String value1, String value2) {
			addCriterion("FUNCPY between", value1, value2, "funcpy");
			return this;
		}

		public Criteria andFuncpyNotBetween(String value1, String value2) {
			addCriterion("FUNCPY not between", value1, value2, "funcpy");
			return this;
		}

		public Criteria andParentfuncidIsNull() {
			addCriterion("PARENTFUNCID is null");
			return this;
		}

		public Criteria andParentfuncidIsNotNull() {
			addCriterion("PARENTFUNCID is not null");
			return this;
		}

		public Criteria andParentfuncidEqualTo(String value) {
			addCriterion("PARENTFUNCID =", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidNotEqualTo(String value) {
			addCriterion("PARENTFUNCID <>", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidGreaterThan(String value) {
			addCriterion("PARENTFUNCID >", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidGreaterThanOrEqualTo(String value) {
			addCriterion("PARENTFUNCID >=", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidLessThan(String value) {
			addCriterion("PARENTFUNCID <", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidLessThanOrEqualTo(String value) {
			addCriterion("PARENTFUNCID <=", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidLike(String value) {
			addCriterion("PARENTFUNCID like", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidNotLike(String value) {
			addCriterion("PARENTFUNCID not like", value, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidIn(List values) {
			addCriterion("PARENTFUNCID in", values, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidNotIn(List values) {
			addCriterion("PARENTFUNCID not in", values, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidBetween(String value1, String value2) {
			addCriterion("PARENTFUNCID between", value1, value2, "parentfuncid");
			return this;
		}

		public Criteria andParentfuncidNotBetween(String value1, String value2) {
			addCriterion("PARENTFUNCID not between", value1, value2,
					"parentfuncid");
			return this;
		}

		public Criteria andIsleafIsNull() {
			addCriterion("ISLEAF is null");
			return this;
		}

		public Criteria andIsleafIsNotNull() {
			addCriterion("ISLEAF is not null");
			return this;
		}

		public Criteria andIsleafEqualTo(String value) {
			addCriterion("ISLEAF =", value, "isleaf");
			return this;
		}

		public Criteria andIsleafNotEqualTo(String value) {
			addCriterion("ISLEAF <>", value, "isleaf");
			return this;
		}

		public Criteria andIsleafGreaterThan(String value) {
			addCriterion("ISLEAF >", value, "isleaf");
			return this;
		}

		public Criteria andIsleafGreaterThanOrEqualTo(String value) {
			addCriterion("ISLEAF >=", value, "isleaf");
			return this;
		}

		public Criteria andIsleafLessThan(String value) {
			addCriterion("ISLEAF <", value, "isleaf");
			return this;
		}

		public Criteria andIsleafLessThanOrEqualTo(String value) {
			addCriterion("ISLEAF <=", value, "isleaf");
			return this;
		}

		public Criteria andIsleafLike(String value) {
			addCriterion("ISLEAF like", value, "isleaf");
			return this;
		}

		public Criteria andIsleafNotLike(String value) {
			addCriterion("ISLEAF not like", value, "isleaf");
			return this;
		}

		public Criteria andIsleafIn(List values) {
			addCriterion("ISLEAF in", values, "isleaf");
			return this;
		}

		public Criteria andIsleafNotIn(List values) {
			addCriterion("ISLEAF not in", values, "isleaf");
			return this;
		}

		public Criteria andIsleafBetween(String value1, String value2) {
			addCriterion("ISLEAF between", value1, value2, "isleaf");
			return this;
		}

		public Criteria andIsleafNotBetween(String value1, String value2) {
			addCriterion("ISLEAF not between", value1, value2, "isleaf");
			return this;
		}

		public Criteria andLevelidIsNull() {
			addCriterion("LEVELID is null");
			return this;
		}

		public Criteria andLevelidIsNotNull() {
			addCriterion("LEVELID is not null");
			return this;
		}

		public Criteria andLevelidEqualTo(String value) {
			addCriterion("LEVELID =", value, "levelid");
			return this;
		}

		public Criteria andLevelidNotEqualTo(String value) {
			addCriterion("LEVELID <>", value, "levelid");
			return this;
		}

		public Criteria andLevelidGreaterThan(String value) {
			addCriterion("LEVELID >", value, "levelid");
			return this;
		}

		public Criteria andLevelidGreaterThanOrEqualTo(String value) {
			addCriterion("LEVELID >=", value, "levelid");
			return this;
		}

		public Criteria andLevelidLessThan(String value) {
			addCriterion("LEVELID <", value, "levelid");
			return this;
		}

		public Criteria andLevelidLessThanOrEqualTo(String value) {
			addCriterion("LEVELID <=", value, "levelid");
			return this;
		}

		public Criteria andLevelidLike(String value) {
			addCriterion("LEVELID like", value, "levelid");
			return this;
		}

		public Criteria andLevelidNotLike(String value) {
			addCriterion("LEVELID not like", value, "levelid");
			return this;
		}

		public Criteria andLevelidIn(List values) {
			addCriterion("LEVELID in", values, "levelid");
			return this;
		}

		public Criteria andLevelidNotIn(List values) {
			addCriterion("LEVELID not in", values, "levelid");
			return this;
		}

		public Criteria andLevelidBetween(String value1, String value2) {
			addCriterion("LEVELID between", value1, value2, "levelid");
			return this;
		}

		public Criteria andLevelidNotBetween(String value1, String value2) {
			addCriterion("LEVELID not between", value1, value2, "levelid");
			return this;
		}

		public Criteria andLevelsortIsNull() {
			addCriterion("LEVELSORT is null");
			return this;
		}

		public Criteria andLevelsortIsNotNull() {
			addCriterion("LEVELSORT is not null");
			return this;
		}

		public Criteria andLevelsortEqualTo(Integer value) {
			addCriterion("LEVELSORT =", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortNotEqualTo(Integer value) {
			addCriterion("LEVELSORT <>", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortGreaterThan(Integer value) {
			addCriterion("LEVELSORT >", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortGreaterThanOrEqualTo(Integer value) {
			addCriterion("LEVELSORT >=", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortLessThan(Integer value) {
			addCriterion("LEVELSORT <", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortLessThanOrEqualTo(Integer value) {
			addCriterion("LEVELSORT <=", value, "levelsort");
			return this;
		}

		public Criteria andLevelsortIn(List values) {
			addCriterion("LEVELSORT in", values, "levelsort");
			return this;
		}

		public Criteria andLevelsortNotIn(List values) {
			addCriterion("LEVELSORT not in", values, "levelsort");
			return this;
		}

		public Criteria andLevelsortBetween(Integer value1, Integer value2) {
			addCriterion("LEVELSORT between", value1, value2, "levelsort");
			return this;
		}

		public Criteria andLevelsortNotBetween(Integer value1, Integer value2) {
			addCriterion("LEVELSORT not between", value1, value2, "levelsort");
			return this;
		}

		public Criteria andLevelnoIsNull() {
			addCriterion("LEVELNO is null");
			return this;
		}

		public Criteria andLevelnoIsNotNull() {
			addCriterion("LEVELNO is not null");
			return this;
		}

		public Criteria andLevelnoEqualTo(Integer value) {
			addCriterion("LEVELNO =", value, "levelno");
			return this;
		}

		public Criteria andLevelnoNotEqualTo(Integer value) {
			addCriterion("LEVELNO <>", value, "levelno");
			return this;
		}

		public Criteria andLevelnoGreaterThan(Integer value) {
			addCriterion("LEVELNO >", value, "levelno");
			return this;
		}

		public Criteria andLevelnoGreaterThanOrEqualTo(Integer value) {
			addCriterion("LEVELNO >=", value, "levelno");
			return this;
		}

		public Criteria andLevelnoLessThan(Integer value) {
			addCriterion("LEVELNO <", value, "levelno");
			return this;
		}

		public Criteria andLevelnoLessThanOrEqualTo(Integer value) {
			addCriterion("LEVELNO <=", value, "levelno");
			return this;
		}

		public Criteria andLevelnoIn(List values) {
			addCriterion("LEVELNO in", values, "levelno");
			return this;
		}

		public Criteria andLevelnoNotIn(List values) {
			addCriterion("LEVELNO not in", values, "levelno");
			return this;
		}

		public Criteria andLevelnoBetween(Integer value1, Integer value2) {
			addCriterion("LEVELNO between", value1, value2, "levelno");
			return this;
		}

		public Criteria andLevelnoNotBetween(Integer value1, Integer value2) {
			addCriterion("LEVELNO not between", value1, value2, "levelno");
			return this;
		}

		public Criteria andFunciconIsNull() {
			addCriterion("FUNCICON is null");
			return this;
		}

		public Criteria andFunciconIsNotNull() {
			addCriterion("FUNCICON is not null");
			return this;
		}

		public Criteria andFunciconEqualTo(String value) {
			addCriterion("FUNCICON =", value, "funcicon");
			return this;
		}

		public Criteria andFunciconNotEqualTo(String value) {
			addCriterion("FUNCICON <>", value, "funcicon");
			return this;
		}

		public Criteria andFunciconGreaterThan(String value) {
			addCriterion("FUNCICON >", value, "funcicon");
			return this;
		}

		public Criteria andFunciconGreaterThanOrEqualTo(String value) {
			addCriterion("FUNCICON >=", value, "funcicon");
			return this;
		}

		public Criteria andFunciconLessThan(String value) {
			addCriterion("FUNCICON <", value, "funcicon");
			return this;
		}

		public Criteria andFunciconLessThanOrEqualTo(String value) {
			addCriterion("FUNCICON <=", value, "funcicon");
			return this;
		}

		public Criteria andFunciconLike(String value) {
			addCriterion("FUNCICON like", value, "funcicon");
			return this;
		}

		public Criteria andFunciconNotLike(String value) {
			addCriterion("FUNCICON not like", value, "funcicon");
			return this;
		}

		public Criteria andFunciconIn(List values) {
			addCriterion("FUNCICON in", values, "funcicon");
			return this;
		}

		public Criteria andFunciconNotIn(List values) {
			addCriterion("FUNCICON not in", values, "funcicon");
			return this;
		}

		public Criteria andFunciconBetween(String value1, String value2) {
			addCriterion("FUNCICON between", value1, value2, "funcicon");
			return this;
		}

		public Criteria andFunciconNotBetween(String value1, String value2) {
			addCriterion("FUNCICON not between", value1, value2, "funcicon");
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
	}
}