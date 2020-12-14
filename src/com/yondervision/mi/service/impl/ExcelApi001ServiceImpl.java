package com.yondervision.mi.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.impl.Mi001DAOImpl;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.util.CommonUtil;

public class ExcelApi001ServiceImpl implements ExcelApi001Service {

	private Mi001DAO mi001Dao;
	
	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}

	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}

	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void excelToDB(Map<String,String> ruleMap, String tableName,java.io.InputStream is) {
		Logger log=LoggerUtil.getLogger();
		log.info("[+]excelToDB");
		Object mixxx=null;
		try{
			mixxx=Class.forName("com.yondervision.mi.dto."+tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase()).newInstance();
			Mi001DAOImpl dao=(Mi001DAOImpl)mi001Dao;
			Workbook wb=jxl.Workbook.getWorkbook(is);
			jxl.Sheet sheet=wb.getSheet(0);
	    	for(int i=1;i<sheet.getRows();i++){
	    		 List<String> list=new ArrayList<String>();
	    		 for(int col=0;col<sheet.getColumns();col++){ 
	    			 String v=sheet.getCell(col, i).getContents();
	    			 list.add(v);
	    		 }
	    		 for(String key:ruleMap.keySet()){
	    			 String val=ruleMap.get(key);
	    			 if(val.startsWith("#")){
	    				 int wz=Integer.parseInt(val.substring(1))-1;
	    				 val=list.get(wz);	    				 
	    			 }	    				 
	    			 com.yondervision.mi.util.JavaBeanUtil.setter(mixxx, key.toLowerCase(), val);
	    		 }
	    		 dao.getSqlMapClientTemplate().insert(tableName.toUpperCase()+".abatorgenerated_insert", mixxx);
	    		 	 
	    	 } 
	    	 wb.close();
		}catch(Exception e){
			e.printStackTrace();	
			log.error(e);
	    	throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),e.getMessage());
		}  
		
		log.info("[-]excelToDB");
	}

	/**
	 * 数据库内容批量写入excel文件
	 * @param titles 要导出的项目（多项以','分隔）
	 * @param obj 要导出数据的条件(拼装好的example)
	 * @param tableName 要导出的表名
	 * @param fileName 生成文件名 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public OutputStream generalExcelFileInOutputStream(OutputStream outputStream, List advertiseOrderList, String tableName, String titles)
				throws IOException, RowsExceededException, WriteException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Logger log=LoggerUtil.getLogger();
		log.info("[+]dbToExcel");

		WritableWorkbook wbook = Workbook.createWorkbook(outputStream); //建立excel文件
		WritableSheet wsheet = wbook.createSheet(tableName, 0); //工作表名称  
		//设置Excel字体  
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,  
		WritableFont.BOLD, false,  
		jxl.format.UnderlineStyle.NO_UNDERLINE,  
		jxl.format.Colour.BLACK);  
		WritableCellFormat titleFormat = new WritableCellFormat(wfont);  
		
		// 获取表格台头
		String[] title = titles.split(",");
		//设置Excel表头  
		for (int i = 0; i < title.length; i++) {  
			Label excelTitle = new Label(i, 0, title[i], titleFormat);  
			wsheet.addCell(excelTitle);
		}
		
		Object mixxx = null;
		mixxx = Class.forName("com.yondervision.mi.dto."+tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase()).newInstance();
		
		if (!CommonUtil.isEmpty(advertiseOrderList)) {
			int c = 1; //用于循环时Excel的行号  
			for (int i = 0; i < advertiseOrderList.size(); i++) {
				mixxx = advertiseOrderList.get(i);
				for(int j = 0; j < title.length; j++){
					Label content = new Label(j, c, com.yondervision.mi.util.JavaBeanUtil.getter(mixxx, title[j]));
					wsheet.addCell(content);
				}
				c++;
			}
		}else{
			Label content = new Label(0, 1, "没有符合条件的记录");
			wsheet.addCell(content);
		}

		wbook.write(); //写入文件  
		wbook.close();  
		outputStream.close();
		
		return outputStream;
	}
	
	/**
	 * 数据库内容批量写入excel文件
	 * @param titles 要导出的项目（多项以','分隔）
	 * @param obj 要导出数据的条件(拼装好的example)
	 * @param tableName 要导出的表名，不对表名做任何特殊处理
	 * @param titlesName 要导出的表头
	 * @param tableName 要导出的数据列
	 * @param fileName 生成文件名 
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public OutputStream excelFileInOutputStream(OutputStream outputStream, List advertiseOrderList, String tableName, String titlesName, String titles)
				throws IOException, RowsExceededException, WriteException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Logger log=LoggerUtil.getLogger();
		log.info("[+]dbToExcel");

		WritableWorkbook wbook = Workbook.createWorkbook(outputStream); //建立excel文件
		WritableSheet wsheet = wbook.createSheet(tableName, 0); //工作表名称  
		//设置Excel字体  
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,  
		WritableFont.BOLD, false,  
		jxl.format.UnderlineStyle.NO_UNDERLINE,  
		jxl.format.Colour.BLACK);  
		WritableCellFormat titleFormat = new WritableCellFormat(wfont);  
		
		// 获取表格台头
		String[] titlesname = titlesName.split(",");
		//设置Excel表头  
		for (int i = 0; i < titlesname.length; i++) {  
			Label excelTitle = new Label(i, 0, titlesname[i], titleFormat);  
			wsheet.addCell(excelTitle);
		}
		
		Object mixxx = null;
		mixxx = Class.forName("com.yondervision.mi.dto."+tableName).newInstance();
		
		String[] title = titles.split(",");
		
		if (!CommonUtil.isEmpty(advertiseOrderList)) {
			int c = 1; //用于循环时Excel的行号  
			for (int i = 0; i < advertiseOrderList.size(); i++) {
				mixxx = advertiseOrderList.get(i);
				for(int j = 0; j < title.length; j++){
					Label content = new Label(j, c, com.yondervision.mi.util.JavaBeanUtil.getter(mixxx, title[j]));
					wsheet.addCell(content);
				}
				c++;
			}
		}else{
			Label content = new Label(0, 1, "没有符合条件的记录");
			wsheet.addCell(content);
		}

		wbook.write(); //写入文件  
		wbook.close();  
		outputStream.close();
		
		return outputStream;
	}
}
