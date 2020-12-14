package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.dao.CMi103DAO;
import com.yondervision.mi.dto.CMi103;
import com.yondervision.mi.dto.Mi103;
import com.yondervision.mi.dto.Mi103Example;
import com.yondervision.mi.result.WebApi40101_queryResult;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: CMi103DAOImpl 
* @Description: APP用户注册信息查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi103DAOImpl extends Mi103DAOImpl implements CMi103DAO {

	/* (non-Javadoc)
	 * @see com.yondervision.mi.dao.CMi203DAO#selectAll(com.yondervision.mi.dto.Mi203)
	 */
	public List<Mi103> select103(CMi103 form) {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("CMI103.selectWebMi103", form);
        return list;
	}

	public WebApi40101_queryResult select103Page(CMi103 form) {
		// TODO Auto-generated method stub
		Mi103Example mi103Example = new Mi103Example();
		mi103Example.setOrderByClause("centerid desc,datecreated desc, user_id asc");
		Mi103Example.Criteria ca = mi103Example.createCriteria();
		
		if(!form.getCenterId().equals("00000000")){
			ca.andCenteridEqualTo(form.getCenterId());
		}else{
			
			if(!"".equals(form.getCentid())){
				ca.andCenteridEqualTo(form.getCentid());
			}
		}
		if(!CommonUtil.isEmpty(form.getAccnum())){
			ca.andAccnumLike("%"+form.getAccnum()+"%");
		}
		if(!CommonUtil.isEmpty(form.getAccname())){
			ca.andAccnameLike("%"+form.getAccname()+"%");
		}
		if(!CommonUtil.isEmpty(form.getCertinum())){
			ca.andCertinumLike("%"+form.getCertinum()+"%");
		}
		if(!CommonUtil.isEmpty(form.getEmail())){
			ca.andEmailLike("%"+form.getEmail()+"%");
		}
		if(!CommonUtil.isEmpty(form.getCardno())){
			ca.andCardnoLike("%"+form.getCardno()+"%");
		}
		if(!CommonUtil.isEmpty(form.getStartdate())){
			ca.andDatecreatedGreaterThanOrEqualTo(form.getStartdate()+" 00:00:00.000");			
		}
		if(!CommonUtil.isEmpty(form.getEnddate())){
			ca.andDatecreatedLessThanOrEqualTo(form.getEnddate()+" 23:59:59.999");
		}
		if(!CommonUtil.isEmpty(form.getUserId())){
			ca.andUserIdLike("%"+form.getUserId()+"%");
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi103> list = getSqlMapClientTemplate().queryForList("MI103.abatorgenerated_selectByExample", mi103Example, skipResults, rows);
		int total = this.countByExample(mi103Example);
		WebApi40101_queryResult queryResult = new WebApi40101_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList103(list);
		
		return queryResult;
	}
	public List<HashMap> selectWebapi10304App(CMi103 form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI103.selectWebApiAppuser", form);
		return result;
	}
	public List<HashMap> selectWebapi10304Wx(CMi103 form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI103.selectWebApiWxuser", form);
		return result;
	}
	public List<HashMap> selectWebapi10304All(CMi103 form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI103.selectWebApiAll", form);
		return result;
	}
	public List<HashMap> selectWebapi10301app(CMi103 form){
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI103.selectWebApi10301app", form);
		return result;
	}
	public List<HashMap> selectWebapi10301wx(CMi103 form){
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI103.selectWebApi10301wx", form);
		return result;
	}
}
