package com.yondervision.mi.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.CMi121DAO;
import com.yondervision.mi.dto.CMi121;
import com.yondervision.mi.dto.Mi121;
import com.yondervision.mi.dto.Mi121Example;
import com.yondervision.mi.result.WebApi41108_queryResult;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: CMi203DAOImpl 
* @Description: 楼盘查询接口实现
* @author Caozhongyan
* @date Sep 27, 2013 4:02:17 PM   
* 
*/ 
public class CMi121DAOImpl extends Mi121DAOImpl implements CMi121DAO {
	public WebApi41108_queryResult selectAllByList(CMi121 form) {
		// TODO Auto-generated method stub
		Mi121Example mi121Example = new Mi121Example();
		mi121Example.setOrderByClause("xh asc");
		Mi121Example.Criteria ca = mi121Example.createCriteria();
		if(!CommonUtil.isEmpty(form.getAnimateid())){
			ca.andAnimateidEqualTo(form.getAnimateid());
		}
		ca.andValidflagEqualTo(Constants.IS_VALIDFLAG);
		
		
		int page = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getRows());
		page = page==0 ? Integer.valueOf(1) : page;
		rows = rows==0 ? Integer.valueOf(10) : rows;		
		int skipResults = (page-1) * rows;
		List<Mi121> list = getSqlMapClientTemplate().queryForList("MI121.abatorgenerated_selectByExample", mi121Example, skipResults, rows);
		if(list.size()==0){			
			throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"图片动画明细信息");
		}else{
			for(int i=0;i<list.size();i++){
				try {
					String filePath = CommonUtil.getDownloadFileUrl(
							"pushdhtp", form.getCid()+File.separator+list.get(i).getImgpath(), true);
					list.get(i).setFreeuse1(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new TransRuntimeErrorException(WEB_ALERT.NO_DATA.getValue(),"APP图片动画不存在请重新上传");
				}
			}
			
		}	
		int total = this.countByExample(mi121Example);
		WebApi41108_queryResult queryResult = new WebApi41108_queryResult();
		
		queryResult.setPageNumber(page);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setList121(list);
		return queryResult;
	}
}
