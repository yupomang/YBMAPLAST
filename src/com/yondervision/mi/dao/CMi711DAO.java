package com.yondervision.mi.dao;

import java.util.HashMap;
import java.util.List;

import com.yondervision.mi.dto.Mi711Example;

public interface CMi711DAO extends Mi711DAO{
	
	 public List<HashMap<String,Object>> selectGroupMi711(Mi711Example example);
}