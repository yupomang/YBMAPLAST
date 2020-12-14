package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi703;
import com.yondervision.mi.form.WebApi70304Form;
import com.yondervision.mi.form.WebApi70306Form;
import com.yondervision.mi.form.WebApi70604Form;
import com.yondervision.mi.form.WebApi70606Form;
import com.yondervision.mi.result.WebApi70304_queryResult;
import com.yondervision.mi.result.WebApi70306_queryResult;
import com.yondervision.mi.result.WebApi70604_queryResult;
import com.yondervision.mi.result.WebApi70606_queryResult;

/** 
* @ClassName: CMi703DAO 
* @Description:
* @author gongqi
* @date July 18, 2014 9:33:25 PM
* 
*/ 
public interface CMi703DAO extends Mi703DAO {
	public WebApi70304_queryResult selectMi703Page(WebApi70304Form form, List<String> newsSeqnoList )throws Exception;
	public WebApi70306_queryResult selectMi703Page_Comment(WebApi70306Form form)throws Exception;
	public int updatePraisecountsByPrimaryKey(Mi703 record) throws Exception;//更新点赞数 增加
	public int updatePraisecountsSubByPrimaryKey(Mi703 record) throws Exception;//更新点赞数 减少
	public WebApi70604_queryResult selectMi703PageNoTimes(WebApi70604Form form, List<String> newsSeqnoList )throws Exception;
	public WebApi70606_queryResult selectMi703Page_CommentNoTimes(WebApi70606Form form)throws Exception;
}
