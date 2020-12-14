package com.yondervision.mi.dao;

import java.util.List;

import com.yondervision.mi.dto.Mi702Example;
import com.yondervision.mi.form.WebApi70104Form;
import com.yondervision.mi.result.NewspapersTitleInfoBean;
import com.yondervision.mi.result.WebApi70104_queryResult;

/** 
* @ClassName: CMi702DAO 
* @Description:
* @author gongqi
* @date July 18, 2014 9:33:25 PM
* 
*/ 
public interface CMi702DAO extends Mi702DAO {
	public WebApi70104_queryResult selectMi702Page(WebApi70104Form form)throws Exception;
	
	public List<NewspapersTitleInfoBean> selectTimesList(Mi702Example example)throws Exception;
}
