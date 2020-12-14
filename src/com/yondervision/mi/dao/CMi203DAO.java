package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi203;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.dto.Mi203Example;
import com.yondervision.mi.form.AppApi00801Form;
import com.yondervision.mi.result.WebApi20304_queryResult;


/** 
* @ClassName: CMi203DAO 
* @Description: 楼盘查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi203DAO extends Mi203DAO {
	public List<Mi203> selectAll(AppApi00801Form form);
	public List<Mi203> selectLikeHouseName(AppApi00801Form form);
	public List<Mi203> selectByAreaId(AppApi00801Form form);
	public List<Mi203> selectWeb(Mi203 form);
	public WebApi20304_queryResult selectWebPage(CMi203 form);
}
