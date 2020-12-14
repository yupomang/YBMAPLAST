/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.dao.impl
 * 文件名：     CMi304Impl.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dao.CMi625DAO;
import com.yondervision.mi.dto.CMi625;
import com.yondervision.mi.dto.Mi625;
import com.yondervision.mi.dto.Mi625Example;
import com.yondervision.mi.form.AppApi30304Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.WebApi62506_queryResult;

/**
 * @author LinXiaolong
 *
 */
public class CMi625DAOImpl extends Mi625DAOImpl implements CMi625DAO {
	
	public List<HashMap> selectAppapi30304Detail(AppApi30304Form map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI625.selectAppapi30304Bus", map);
		return result;
	}

	public List<HashMap> selectAppapi30305User(AppApiCommonForm map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI625.selectAppapi30305User", map);
		return result;
	}
	public List<CMi625> selectMi625All(CMi625 map) {
		// TODO Auto-generated method stub
		List<CMi625> result = getSqlMapClientTemplate().queryForList("CMI625.selectMi625All", map);
		return result;
	}
	public WebApi62506_queryResult selectOneDayAppoDetail(CMi625 form) {
		// TODO Auto-generated method stub
		Mi625Example mi625e=new Mi625Example();
		Mi625Example.Criteria ca =mi625e.createCriteria();
		ca.andAppobranchidEqualTo(form.getAppobranchid());
		ca.andAppobusiidEqualTo(form.getAppobusiid());
		ca.andCenteridEqualTo(form.getCenterid());
		ca.andAppodateEqualTo(form.getAppodate());
		ca.andValidflagEqualTo("1");
		List<Mi625> list625=this.selectByExample(mi625e);
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		int total = this.countByExample(mi625e);
		WebApi62506_queryResult queryResult=new WebApi62506_queryResult();
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList625(list625);
		return queryResult;
	}
	public int updateAppostate(HashMap form) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("CMI625.updateAppostate", form);
		return result;
	}

	public  List<HashMap> selectRemainPeople(AppApi30304Form form) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI625.selectRemainPeople", form);
		return result;
	}
	public  int selectMi624SumAppocnt(AppApi30304Form form) {
		// TODO Auto-generated method stub
		int result = 0;
		Object obj=getSqlMapClientTemplate().queryForObject("CMI625.selectMi624SumAppocnt", form);
		if(obj!=null&&obj!=""){
			result = Integer.parseInt(obj+"");
		}
		return result;
	}
}
