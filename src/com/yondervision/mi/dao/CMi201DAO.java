package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.CMi201;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.form.AppApi10101Form;
import com.yondervision.mi.result.WebApi20104_queryResult;


/** 
* @ClassName: CMi201DAO 
* @Description: 网点查询接口
* @author Caozhongyan
* @date Sep 27, 2013 4:02:10 PM   
* 
*/ 
public interface CMi201DAO extends Mi201DAO {
	public List<Mi201> selectAll(AppApi10101Form form);
	public List<Mi201> selectLikeWebsiteName(AppApi10101Form form);
	public List<Mi201> selectByAreaId(AppApi10101Form form);
	public List<Mi201> selectWeb(Mi201 form);
	public WebApi20104_queryResult selectWebPage(CMi201 form);
}
