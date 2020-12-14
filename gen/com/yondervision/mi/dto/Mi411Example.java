package com.yondervision.mi.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mi411Example {

	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Abator for iBATIS. This field corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public Mi411Example() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	protected Mi411Example(Mi411Example example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Abator for iBATIS. This method corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Abator for iBATIS. This class corresponds to the database table MI411
	 * @abatorgenerated  Sat Feb 18 11:30:09 CST 2017
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

		public Criteria andTemplateidIsNull() {
			addCriterion("TEMPLATEID is null");
			return this;
		}

		public Criteria andTemplateidIsNotNull() {
			addCriterion("TEMPLATEID is not null");
			return this;
		}

		public Criteria andTemplateidEqualTo(String value) {
			addCriterion("TEMPLATEID =", value, "templateid");
			return this;
		}

		public Criteria andTemplateidNotEqualTo(String value) {
			addCriterion("TEMPLATEID <>", value, "templateid");
			return this;
		}

		public Criteria andTemplateidGreaterThan(String value) {
			addCriterion("TEMPLATEID >", value, "templateid");
			return this;
		}

		public Criteria andTemplateidGreaterThanOrEqualTo(String value) {
			addCriterion("TEMPLATEID >=", value, "templateid");
			return this;
		}

		public Criteria andTemplateidLessThan(String value) {
			addCriterion("TEMPLATEID <", value, "templateid");
			return this;
		}

		public Criteria andTemplateidLessThanOrEqualTo(String value) {
			addCriterion("TEMPLATEID <=", value, "templateid");
			return this;
		}

		public Criteria andTemplateidLike(String value) {
			addCriterion("TEMPLATEID like", value, "templateid");
			return this;
		}

		public Criteria andTemplateidNotLike(String value) {
			addCriterion("TEMPLATEID not like", value, "templateid");
			return this;
		}

		public Criteria andTemplateidIn(List values) {
			addCriterion("TEMPLATEID in", values, "templateid");
			return this;
		}

		public Criteria andTemplateidNotIn(List values) {
			addCriterion("TEMPLATEID not in", values, "templateid");
			return this;
		}

		public Criteria andTemplateidBetween(String value1, String value2) {
			addCriterion("TEMPLATEID between", value1, value2, "templateid");
			return this;
		}

		public Criteria andTemplateidNotBetween(String value1, String value2) {
			addCriterion("TEMPLATEID not between", value1, value2, "templateid");
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

		public Criteria andChannelIsNull() {
			addCriterion("CHANNEL is null");
			return this;
		}

		public Criteria andChannelIsNotNull() {
			addCriterion("CHANNEL is not null");
			return this;
		}

		public Criteria andChannelEqualTo(String value) {
			addCriterion("CHANNEL =", value, "channel");
			return this;
		}

		public Criteria andChannelNotEqualTo(String value) {
			addCriterion("CHANNEL <>", value, "channel");
			return this;
		}

		public Criteria andChannelGreaterThan(String value) {
			addCriterion("CHANNEL >", value, "channel");
			return this;
		}

		public Criteria andChannelGreaterThanOrEqualTo(String value) {
			addCriterion("CHANNEL >=", value, "channel");
			return this;
		}

		public Criteria andChannelLessThan(String value) {
			addCriterion("CHANNEL <", value, "channel");
			return this;
		}

		public Criteria andChannelLessThanOrEqualTo(String value) {
			addCriterion("CHANNEL <=", value, "channel");
			return this;
		}

		public Criteria andChannelLike(String value) {
			addCriterion("CHANNEL like", value, "channel");
			return this;
		}

		public Criteria andChannelNotLike(String value) {
			addCriterion("CHANNEL not like", value, "channel");
			return this;
		}

		public Criteria andChannelIn(List values) {
			addCriterion("CHANNEL in", values, "channel");
			return this;
		}

		public Criteria andChannelNotIn(List values) {
			addCriterion("CHANNEL not in", values, "channel");
			return this;
		}

		public Criteria andChannelBetween(String value1, String value2) {
			addCriterion("CHANNEL between", value1, value2, "channel");
			return this;
		}

		public Criteria andChannelNotBetween(String value1, String value2) {
			addCriterion("CHANNEL not between", value1, value2, "channel");
			return this;
		}

		public Criteria andPidIsNull() {
			addCriterion("PID is null");
			return this;
		}

		public Criteria andPidIsNotNull() {
			addCriterion("PID is not null");
			return this;
		}

		public Criteria andPidEqualTo(String value) {
			addCriterion("PID =", value, "pid");
			return this;
		}

		public Criteria andPidNotEqualTo(String value) {
			addCriterion("PID <>", value, "pid");
			return this;
		}

		public Criteria andPidGreaterThan(String value) {
			addCriterion("PID >", value, "pid");
			return this;
		}

		public Criteria andPidGreaterThanOrEqualTo(String value) {
			addCriterion("PID >=", value, "pid");
			return this;
		}

		public Criteria andPidLessThan(String value) {
			addCriterion("PID <", value, "pid");
			return this;
		}

		public Criteria andPidLessThanOrEqualTo(String value) {
			addCriterion("PID <=", value, "pid");
			return this;
		}

		public Criteria andPidLike(String value) {
			addCriterion("PID like", value, "pid");
			return this;
		}

		public Criteria andPidNotLike(String value) {
			addCriterion("PID not like", value, "pid");
			return this;
		}

		public Criteria andPidIn(List values) {
			addCriterion("PID in", values, "pid");
			return this;
		}

		public Criteria andPidNotIn(List values) {
			addCriterion("PID not in", values, "pid");
			return this;
		}

		public Criteria andPidBetween(String value1, String value2) {
			addCriterion("PID between", value1, value2, "pid");
			return this;
		}

		public Criteria andPidNotBetween(String value1, String value2) {
			addCriterion("PID not between", value1, value2, "pid");
			return this;
		}

		public Criteria andThemeIsNull() {
			addCriterion("THEME is null");
			return this;
		}

		public Criteria andThemeIsNotNull() {
			addCriterion("THEME is not null");
			return this;
		}

		public Criteria andThemeEqualTo(String value) {
			addCriterion("THEME =", value, "theme");
			return this;
		}

		public Criteria andThemeNotEqualTo(String value) {
			addCriterion("THEME <>", value, "theme");
			return this;
		}

		public Criteria andThemeGreaterThan(String value) {
			addCriterion("THEME >", value, "theme");
			return this;
		}

		public Criteria andThemeGreaterThanOrEqualTo(String value) {
			addCriterion("THEME >=", value, "theme");
			return this;
		}

		public Criteria andThemeLessThan(String value) {
			addCriterion("THEME <", value, "theme");
			return this;
		}

		public Criteria andThemeLessThanOrEqualTo(String value) {
			addCriterion("THEME <=", value, "theme");
			return this;
		}

		public Criteria andThemeLike(String value) {
			addCriterion("THEME like", value, "theme");
			return this;
		}

		public Criteria andThemeNotLike(String value) {
			addCriterion("THEME not like", value, "theme");
			return this;
		}

		public Criteria andThemeIn(List values) {
			addCriterion("THEME in", values, "theme");
			return this;
		}

		public Criteria andThemeNotIn(List values) {
			addCriterion("THEME not in", values, "theme");
			return this;
		}

		public Criteria andThemeBetween(String value1, String value2) {
			addCriterion("THEME between", value1, value2, "theme");
			return this;
		}

		public Criteria andThemeNotBetween(String value1, String value2) {
			addCriterion("THEME not between", value1, value2, "theme");
			return this;
		}

		public Criteria andChanneltemplateIsNull() {
			addCriterion("CHANNELTEMPLATE is null");
			return this;
		}

		public Criteria andChanneltemplateIsNotNull() {
			addCriterion("CHANNELTEMPLATE is not null");
			return this;
		}

		public Criteria andChanneltemplateEqualTo(String value) {
			addCriterion("CHANNELTEMPLATE =", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateNotEqualTo(String value) {
			addCriterion("CHANNELTEMPLATE <>", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateGreaterThan(String value) {
			addCriterion("CHANNELTEMPLATE >", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateGreaterThanOrEqualTo(String value) {
			addCriterion("CHANNELTEMPLATE >=", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateLessThan(String value) {
			addCriterion("CHANNELTEMPLATE <", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateLessThanOrEqualTo(String value) {
			addCriterion("CHANNELTEMPLATE <=", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateLike(String value) {
			addCriterion("CHANNELTEMPLATE like", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateNotLike(String value) {
			addCriterion("CHANNELTEMPLATE not like", value, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateIn(List values) {
			addCriterion("CHANNELTEMPLATE in", values, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateNotIn(List values) {
			addCriterion("CHANNELTEMPLATE not in", values, "channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateBetween(String value1, String value2) {
			addCriterion("CHANNELTEMPLATE between", value1, value2,
					"channeltemplate");
			return this;
		}

		public Criteria andChanneltemplateNotBetween(String value1,
				String value2) {
			addCriterion("CHANNELTEMPLATE not between", value1, value2,
					"channeltemplate");
			return this;
		}

		public Criteria andTemplatecontentIsNull() {
			addCriterion("TEMPLATECONTENT is null");
			return this;
		}

		public Criteria andTemplatecontentIsNotNull() {
			addCriterion("TEMPLATECONTENT is not null");
			return this;
		}

		public Criteria andTemplatecontentEqualTo(String value) {
			addCriterion("TEMPLATECONTENT =", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentNotEqualTo(String value) {
			addCriterion("TEMPLATECONTENT <>", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentGreaterThan(String value) {
			addCriterion("TEMPLATECONTENT >", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentGreaterThanOrEqualTo(String value) {
			addCriterion("TEMPLATECONTENT >=", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentLessThan(String value) {
			addCriterion("TEMPLATECONTENT <", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentLessThanOrEqualTo(String value) {
			addCriterion("TEMPLATECONTENT <=", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentLike(String value) {
			addCriterion("TEMPLATECONTENT like", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentNotLike(String value) {
			addCriterion("TEMPLATECONTENT not like", value, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentIn(List values) {
			addCriterion("TEMPLATECONTENT in", values, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentNotIn(List values) {
			addCriterion("TEMPLATECONTENT not in", values, "templatecontent");
			return this;
		}

		public Criteria andTemplatecontentBetween(String value1, String value2) {
			addCriterion("TEMPLATECONTENT between", value1, value2,
					"templatecontent");
			return this;
		}

		public Criteria andTemplatecontentNotBetween(String value1,
				String value2) {
			addCriterion("TEMPLATECONTENT not between", value1, value2,
					"templatecontent");
			return this;
		}

		public Criteria andTemplatedemoIsNull() {
			addCriterion("TEMPLATEDEMO is null");
			return this;
		}

		public Criteria andTemplatedemoIsNotNull() {
			addCriterion("TEMPLATEDEMO is not null");
			return this;
		}

		public Criteria andTemplatedemoEqualTo(String value) {
			addCriterion("TEMPLATEDEMO =", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoNotEqualTo(String value) {
			addCriterion("TEMPLATEDEMO <>", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoGreaterThan(String value) {
			addCriterion("TEMPLATEDEMO >", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoGreaterThanOrEqualTo(String value) {
			addCriterion("TEMPLATEDEMO >=", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoLessThan(String value) {
			addCriterion("TEMPLATEDEMO <", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoLessThanOrEqualTo(String value) {
			addCriterion("TEMPLATEDEMO <=", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoLike(String value) {
			addCriterion("TEMPLATEDEMO like", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoNotLike(String value) {
			addCriterion("TEMPLATEDEMO not like", value, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoIn(List values) {
			addCriterion("TEMPLATEDEMO in", values, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoNotIn(List values) {
			addCriterion("TEMPLATEDEMO not in", values, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoBetween(String value1, String value2) {
			addCriterion("TEMPLATEDEMO between", value1, value2, "templatedemo");
			return this;
		}

		public Criteria andTemplatedemoNotBetween(String value1, String value2) {
			addCriterion("TEMPLATEDEMO not between", value1, value2,
					"templatedemo");
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