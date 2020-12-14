package com.yondervision.mi.dao.impl;

import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi203DAO;
import com.yondervision.mi.dto.CMi203;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.dto.Mi203Example;
import com.yondervision.mi.form.AppApi00801Form;
import com.yondervision.mi.result.WebApi20304_queryResult;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: CMi203DAOImpl 
* @Description: 楼盘查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi203DAOImpl extends Mi203DAOImpl implements CMi203DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectAll(com.yondervision.mi.dto.Mi203)
	 */
	public List<Mi203> selectAll(AppApi00801Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI203.selectAll", form);
        return list;
	}

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectByAreaId(com.yondervision.mi.dto.Mi203)
	 */
	public List<Mi203> selectByAreaId(AppApi00801Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI203.selectByAreaId", form);
        return list;
	}

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectLikeHouseName(com.yondervision.mi.dto.Mi203)
	 */
	public List<Mi203> selectLikeHouseName(AppApi00801Form form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI203.selectLikeHouseName", form);
        return list;
	}

	public List<Mi203> selectWeb(Mi203 form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI203.selectWeb", form);
        return list;
	}

	public WebApi20304_queryResult selectWebPage(CMi203 form){
		Mi203Example mi203Example = new Mi203Example();
		if("00076000".equals(form.getCenterId())){
			mi203Example.setOrderByClause("centerid desc,house_code asc,area_id asc");
		}else{
			mi203Example.setOrderByClause("centerid desc, area_id asc, house_code asc");
		}
		
		Mi203Example.Criteria ca = mi203Example.createCriteria();
		if(!form.getCenterId().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterId());
		}
		if(!CommonUtil.isEmpty(form.getAddress())){
			ca.andAddressLike("%"+form.getAddress()+"%");
		}
		if(!CommonUtil.isEmpty(form.getBankNames())){
			ca.andBankNamesLike("%"+form.getBankNames()+"%");
		}
		if(!CommonUtil.isEmpty(form.getDeveloperName())){
			ca.andDeveloperNameLike("%"+form.getDeveloperName()+"%");
		}
		if(!CommonUtil.isEmpty(form.getHouseName())){
			ca.andHouseNameLike("%"+form.getHouseName()+"%");
		}
		if(!CommonUtil.isEmpty(form.getHouseType())){
			ca.andHouseTypeLike("%"+form.getHouseType()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi203> list = getSqlMapClientTemplate().queryForList("MI203.abatorgenerated_selectByExample", mi203Example, skipResults, rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"楼盘信息");
		}	
		int total = this.countByExample(mi203Example);
		WebApi20304_queryResult queryResult = new WebApi20304_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList203(list);
		return queryResult;
	}

}
