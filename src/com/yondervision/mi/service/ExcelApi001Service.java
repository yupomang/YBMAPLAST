package com.yondervision.mi.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/** 
* @ClassName: ExcelApi001Service 
* @Description: 关于excel的java类
* @author 韩占远
* @date 2013-10-09
* 
*/ 
public interface ExcelApi001Service {
	
	/**
	 * 批量导入方法
	 * @param ruleMap
	 * @param tableName
	 * @param is
	 */
	 public void  excelToDB(Map<String,String> ruleMap, String tableName ,java.io.InputStream is);
	 
	 /**
	  * 批量导出方法
	  * @param outputStream
	  * @param advertiseOrderList
	  * @param tableName
	  * @param titles
	  * @return
	  * @throws IOException
	  * @throws RowsExceededException
	  * @throws WriteException
	  * @throws InstantiationException
	  * @throws IllegalAccessException
	  * @throws ClassNotFoundException
	  */
	 public OutputStream generalExcelFileInOutputStream(OutputStream outputStream, List advertiseOrderList, String tableName, String titles)
	 		throws IOException, RowsExceededException, WriteException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	 
	 /**
	  * 批量导出方法
	  * @param outputStream
	  * @param advertiseOrderList
	  * @param tableName
	  * @param titlesName
	  * @param titles
	  * @return
	  * @throws IOException
	  * @throws RowsExceededException
	  * @throws WriteException
	  * @throws InstantiationException
	  * @throws IllegalAccessException
	  * @throws ClassNotFoundException
	  */
	 public OutputStream excelFileInOutputStream(OutputStream outputStream, List advertiseOrderList, String tableName, String titlesName, String titles)
		throws IOException, RowsExceededException, WriteException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
