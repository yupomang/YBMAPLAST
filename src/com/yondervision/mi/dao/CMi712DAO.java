package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

public interface CMi712DAO extends Mi712DAO{
    
    public List<HashMap> webapi712(String centerId,
			String startDate, String endDate,String pid) throws Exception;
}