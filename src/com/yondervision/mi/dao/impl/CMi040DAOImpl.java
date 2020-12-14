/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi402DAOImpl.java
 * 创建日期：2013-10-19
 */
package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.dao.CMi040DAO;
import com.yondervision.mi.dto.CMi040;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.result.WebApi04004_queryResult;
import com.yondervision.mi.util.CommonUtil;

public class CMi040DAOImpl extends Mi040DAOImpl implements CMi040DAO {

	public WebApi04004_queryResult select040Page(CMi040 form) {
		Mi040Example mi040Example = new Mi040Example();
		//mi040Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi040Example.Criteria ca = mi040Example.createCriteria();
		String centerid = form.getCenterid();
		if(CommonUtil.isEmpty(centerid)){
			centerid = UserContext.getInstance().getCenterid();
			if(!Constants.YD_ADMIN.equals(centerid)){
				ca.andCenteridEqualTo(form.getCenterid());
			}
		}else{
			ca.andCenteridEqualTo(form.getCenterid());
		}
		
		
		if(!CommonUtil.isEmpty(form.getAppname())){
			ca.andAppnameLike("%"+form.getAppname()+"%");
		}
		if(!CommonUtil.isEmpty(form.getChannel())){
			ca.andChannelLike("%"+form.getChannel()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		@SuppressWarnings("unchecked")
		List<Mi040> list = getSqlMapClientTemplate().queryForList("MI040.abatorgenerated_selectByExample", mi040Example, skipResults, rows);
		int total = this.countByExample(mi040Example);
		WebApi04004_queryResult queryResult = new WebApi04004_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList040(list);
		return queryResult;
	}

}
