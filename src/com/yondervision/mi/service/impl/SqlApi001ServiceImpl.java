package com.yondervision.mi.service.impl;
/** 
* @ClassName: SqlApi001ServiceImpl
* @Description: 关于sql的服务，一般用于查询
* @author 韩占远
* @date 2013-10-21
* 
*/ 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.util.JsonUtil;

public class SqlApi001ServiceImpl  extends SqlMapClientDaoSupport{ 
	public JSONObject selectByPage(String sql,int page,int rows) throws Exception{
		 
		Logger logger=LoggerUtil.getLogger();
		logger.debug("sql="+sql);
		JSONObject root=new JSONObject();
	    JSONArray ary=new JSONArray();
	   
		Connection conn= getSqlMapClientTemplate().getDataSource().getConnection();
		{
		   Statement stmtcnt= conn.createStatement();		
		   ResultSet rs=stmtcnt.executeQuery("select count(*) from ("+sql+") cntsql");
		   if(rs.next()){
			   root.put("total", rs.getInt(1));
		   }
		}
		Statement stmt= conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		System.out.println("resultxzw============="+rs.toString());
		ResultSetMetaData rsmd=rs.getMetaData();
		int curSN = 0;
		int startPosition = (page - 1) * rows + 1;
		while(rs.next()){
			curSN++;				 
			if (curSN < startPosition) {			 
				continue;
			}
             
			if (curSN > rows * page) {
			 
				break;
			} 
			
			JSONObject obj=new JSONObject();
			for (int j = 1; j <= rsmd.getColumnCount(); j++) {
				String columnName = rsmd.getColumnName(j);			 
				obj.put(columnName.toLowerCase(), rs.getString(j));
			 
			}
			ary.add(obj);
		 
		}// end while	
		root.put("rows", ary);
		System.out.println("执行selectByPage结束！"+JsonUtil.getGson().toJsonTree(ary));
	    rs.close();
	    stmt.close();
	    conn.close();
		return root;
	}
}
