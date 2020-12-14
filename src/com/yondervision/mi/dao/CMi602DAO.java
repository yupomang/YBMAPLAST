package com.yondervision.mi.dao;

import java.util.List;
import com.yondervision.mi.dto.Mi602;
import com.yondervision.mi.form.AppApi60002Form;

/** 
* @ClassName: CMi602DAO 
* @Description: TODO
* @author gongqi
* @date July 16, 2014 9:33:25 PM
* 
*/ 
public interface CMi602DAO extends Mi602DAO {
	public List<Mi602> selectMi602(AppApi60002Form form);
}
