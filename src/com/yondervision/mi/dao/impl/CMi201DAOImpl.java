package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi201DAO;
import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.form.AppApi10101Form;
import com.yondervision.mi.result.WebApi20104_queryResult;
import com.yondervision.mi.result.WebApi20304_queryResult;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: CMi201DAOImpl 
* @Description: 网点查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi201DAOImpl extends Mi201DAOImpl implements CMi201DAO {
	
	public List<Mi201> selectWeb(Mi201 form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI201.selectWeb", form);
        return list;
	}

	
	public List<Mi201> selectAll(AppApi10101Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI201.selectAll", form);
        return list;
	}

	
	public List<Mi201> selectByAreaId(AppApi10101Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI201.selectByAreaId", form);
        return list;
	}

	
	public List<Mi201> selectLikeWebsiteName(AppApi10101Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI201.selectLikeHouseName", form);
        return list;
	}


	public WebApi20104_queryResult selectWebPage(CMi201 form) {
		// TODO Auto-generated method stub
		Mi201Example mi201Example = new Mi201Example();
		mi201Example.setOrderByClause("centerid desc, area_id asc, website_code asc");
		Mi201Example.Criteria ca = mi201Example.createCriteria();
		if(!form.getCenterId().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterId());
		}
		if(!CommonUtil.isEmpty(form.getWebsiteName())){
			ca.andWebsiteNameLike("%"+form.getWebsiteName()+"%");
		}
		if(!CommonUtil.isEmpty(form.getAreaId())){
			ca.andAreaIdEqualTo(form.getAreaId());
		}
		if(!CommonUtil.isEmpty(form.getAddress())){
			ca.andAddressLike("%"+form.getAddress()+"%");
		}
		//营业时间
		if(!CommonUtil.isEmpty(form.getServiceTime())){
			
		}
		if(!CommonUtil.isEmpty(form.getAreaid())){
			ca.andAreaIdEqualTo(form.getAreaid());
		}		
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi201> list = getSqlMapClientTemplate().queryForList("MI201.abatorgenerated_selectByExample", mi201Example, skipResults, rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"网点信息");
		}
		int total = this.countByExample(mi201Example);
		WebApi20104_queryResult queryResult = new WebApi20104_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList201(list);
		return queryResult;
	}

	

}
