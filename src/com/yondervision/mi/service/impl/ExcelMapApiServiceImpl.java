package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi201DAO;
import com.yondervision.mi.dao.Mi202DAO;
import com.yondervision.mi.dao.Mi203DAO;
import com.yondervision.mi.dao.impl.Mi001DAOImpl;
import com.yondervision.mi.dto.Mi201;
import com.yondervision.mi.dto.Mi201Example;
import com.yondervision.mi.dto.Mi202;
import com.yondervision.mi.dto.Mi202Example;
import com.yondervision.mi.dto.Mi203;
import com.yondervision.mi.dto.Mi203Example;
import com.yondervision.mi.service.ExcelMapApiService;
import com.yondervision.mi.util.CommonUtil;

public class ExcelMapApiServiceImpl implements ExcelMapApiService {
	public String checkResult="";
	public String checkFileName="";

	private Mi001DAO mi001Dao;
	
	public Mi202DAO mi202Dao = null;
	
	public Mi201DAO mi201Dao = null;
	
	public Mi203DAO mi203Dao = null;

	public void setMi202Dao(Mi202DAO mi202Dao) {
		this.mi202Dao = mi202Dao;
	}
	
	public Mi202DAO getMi202Dao() {
		return mi202Dao;
	}

	public Mi001DAO getMi001Dao() {
		return mi001Dao;
	}

	public void setMi001Dao(Mi001DAO mi001Dao) {
		this.mi001Dao = mi001Dao;
	}
	@Transactional(noRollbackFor=NoRollRuntimeErrorException.class)
	public void excelMapToDB(String loginid, String cityname, String cityCode, Map<String, String> ruleMap,
			String tableName, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		File importFileOnServer = new File(fileName);
		if (!importFileOnServer.getParentFile().isDirectory()) {
			if(importFileOnServer.getParentFile().mkdirs())
				System.out.println("创建文件或文件夹:"+importFileOnServer.getParentFile());
		}
		StringBuffer errormessage = new StringBuffer("");
		
		Logger log=LoggerUtil.getLogger();
		log.info("[+]excelToDB");
		Object mixxx=null;
		Object mi = null;
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		try{
			mixxx=Class.forName("com.yondervision.mi.dto."+tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase()).newInstance();
			Mi001DAOImpl dao=(Mi001DAOImpl)mi001Dao;
			Workbook wb=jxl.Workbook.getWorkbook(importFileOnServer);
			jxl.Sheet sheet=wb.getSheet(0);
			System.out.println(" ############### 文件中记录数：  "+sheet.getRows());
			
			//处理检查结果
//			WritableWorkbook wbook = Workbook
//					.createWorkbook(importFileOnServer, wb);
//			WritableWorkbook wbook = Workbook.createWorkbook(importFileOnServer);
//			WritableSheet wsheet = wbook.createSheet("检查结果", 1);
			
			
//			WorkbookSettings settings = new WorkbookSettings();  
//			settings.setWriteAccess(null); 
//			settings.setFormulaAdjust(false);
//			settings.setMergedCellChecking(false);
//			WritableWorkbook wbook = wb.createWorkbook(importFileOnServer, wb, settings);
//			WritableSheet wsheet = wbook.getSheet(sheet.getName());
//
//			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12,
//					WritableFont.BOLD, false,
//					jxl.format.UnderlineStyle.NO_UNDERLINE,
//					jxl.format.Colour.RED);
//			WritableCellFormat errFormat = new WritableCellFormat(wfont);
//			errFormat.setWrap(true);
//			wsheet.getSettings().setProtected(true);
//			wsheet.setColumnView(sheet.getRow(0).length, 350);
//			errFormat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
//			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 12,
//					WritableFont.BOLD, false,
//					jxl.format.UnderlineStyle.NO_UNDERLINE,
//					jxl.format.Colour.BLACK);
//			WritableCellFormat errFormat1 = new WritableCellFormat(wfont1);
//			errFormat1.setAlignment(Alignment.CENTRE);
//			
//			Cell[] row0 = sheet.getRow(0);
//			for(int i=0;i<row0.length;i++){
//				Label content1 = new Label(i, 0, row0[i].getContents(), errFormat1);
//		 		wsheet.addCell(content1);
//			}
//			Label content1 = new Label(row0.length, 0, "检查结果",errFormat1);
//	 		wsheet.addCell(content1);
			
	    	for(int i=1;i<sheet.getRows();i++){	   
	    		Cell[] rowData = sheet.getRow(i);
	    		List<String> list=new ArrayList<String>();
	    		int sum = 0;
	    		 for(int j=0;j<rowData.length;j++){ 
	    			 String v=sheet.getCell(j, i).getContents();
	    			 list.add(v);
	    			 if(CommonUtil.isEmpty(v)){
	    				 sum++;
	    			 }
	    			 if(CommonUtil.isEmpty(rowData[j]) || CommonUtil.isEmpty(rowData[j].getContents())){
	    				 continue;
	    			 }	    			
	    		 }
	    		 if(sheet.getColumns()<=sum){
	    			 continue;
	    		 }
	    		 for(String key:ruleMap.keySet()){
	    			 String val=ruleMap.get(key);
	    			 if(val.startsWith("#")){
	    				 int wz=Integer.parseInt(val.substring(1))-1;
	    				 val=list.get(wz); 
	    			 }	    				 
	    			 com.yondervision.mi.util.JavaBeanUtil.setter(mixxx, key, val);
	    		 }
	    		 errormessage.setLength(0);
	    		 if(tableName.equals("mi201")){
	    			 Mi201 mi201 = new Mi201();
	    			 mi201 = (Mi201)mixxx;
	    			 mi201.setCenterid(cityCode);
	    			 mi201.setWebsiteId(CommonUtil.genKeyAndCommit("MI201", 20));
	    			 mi201.setValidflag(Constants.IS_VALIDFLAG);
	    			 mi201.setDatecreated(formatter.format(date));
	    			 mi201.setDatemodified(formatter.format(date));
	    			 Mi202Example mi202Example = new Mi202Example();
	    				mi202Example.createCriteria()
	    				.andCenteridEqualTo(cityCode).andAreaNameLike("%"+mi201.getAreaId().trim()+"%")
	    				.andValidflagEqualTo(Constants.IS_VALIDFLAG);			
    				List<Mi202> list202 = mi202Dao.selectByExample(mi202Example);
    				if(!CommonUtil.isEmpty(list202)){    					
    					mi201.setAreaId(list202.get(0).getAreaId());
    				}		
	    			 
	    			 mi201.setLoginid(loginid);
	    			 MsgSendApi001ServiceImpl msasi = new MsgSendApi001ServiceImpl();
	    			 request.setAttribute("centerId", "00000000");
	    			 if(!CommonUtil.isEmpty(mi201.getAddress())&&!CommonUtil.isEmpty(cityname)){
		    			 msasi.mapHttpSend(request, response, cityname, mi201.getAddress());
		    			 JSONObject json = (JSONObject) request.getAttribute("recMapJsonObj");	    			 
		    			 if(json.getString("status").equals("0")){
		    				 System.out.println(json.getString("result"));
		    				 if(!"[]".equals(json.getString("result"))){
		    					 JSONObject j = json.getJSONObject("result");
			    				 JSONObject jsons = j.getJSONObject("location");
			    				 if("1".equals(j.getString("precise"))){
				    				 mi201.setPositionX(jsons.getString("lng"));
				    				 mi201.setPositionY(jsons.getString("lat"));
			    				 }
		    				 }		    				 	    				 
		    			 }	    
	    			 }			 
	    			 mi = mi201;
	    		 }else if(tableName.equals("mi203")){	    			
	    			 Mi203 mi203 = new Mi203();
	    			 mi203 = (Mi203)mixxx;
	    			 mi203.setCenterid(cityCode);
	    			 mi203.setHousesId(CommonUtil.genKeyAndCommit("MI203", 20));
	    			 mi203.setValidflag(Constants.IS_VALIDFLAG);
	    			 mi203.setDatecreated(formatter.format(date));
	    			 mi203.setDatemodified(formatter.format(date));
	    			 mi203.setLoginid(loginid);
	    			 Mi202Example mi202Example = new Mi202Example();
	    				mi202Example.createCriteria()
	    				.andCenteridEqualTo(cityCode).andAreaNameLike("%"+mi203.getAreaId().trim()+"%")
	    				.andValidflagEqualTo(Constants.IS_VALIDFLAG);			
    				List<Mi202> list202 = mi202Dao.selectByExample(mi202Example);
    				if(!CommonUtil.isEmpty(list202)){    					
    					mi203.setAreaId(list202.get(0).getAreaId());
    				}
	    			 
	    			 MsgSendApi001ServiceImpl msasi = new MsgSendApi001ServiceImpl();
	    			 request.setAttribute("centerId", "00000000");
	    			 if(!CommonUtil.isEmpty(mi203.getAddress())&&!CommonUtil.isEmpty(cityname)){
		    			 msasi.mapHttpSend(request, response, cityname, mi203.getAddress());
		    			 JSONObject json = (JSONObject) request.getAttribute("recMapJsonObj");	    			 
		    			 if(json.getString("status").equals("0")){
		    				 System.out.println(json.getString("result"));
		    				 if(!"[]".equals(json.getString("result"))){
		    					 JSONObject j = json.getJSONObject("result");
			    				 JSONObject jsons = j.getJSONObject("location");
			    				 if("1".equals(j.getString("precise"))){
				    				 mi203.setPositionX(jsons.getString("lng"));
				    				 mi203.setPositionY(jsons.getString("lat"));
			    				 } 
		    				 }		    				 	    				 
		    			 }	   
	    			 }
	    			 mi = mi203;
	    		 }
	    		 if(errormessage.toString().equals("")){
	    			 dao.getSqlMapClientTemplate().insert(tableName.toUpperCase()+".abatorgenerated_insert", mi); 
	    		 }
	    	 }	   
//	    	 wbook.write();
//			 wbook.close();
	    	
	    	 wb.close();	    	 
		}catch(Exception e){
			e.printStackTrace();
			log.error(errormessage.toString());
	    	throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),e.getMessage());
		}  
		if(!errormessage.toString().equals("")){
			log.error(errormessage.toString());
			throw new TransRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),errormessage.toString());
		}
		log.info("[-]excelToDB");
	}
	
	public Mi201 checkExcelMapMi201(int i, Mi201 mi201, String cityCode,StringBuffer errormessage) throws UnsupportedEncodingException{
		
		if(CommonUtil.isEmpty(mi201.getWebsiteCode())){
			errormessage.append("网点编码为空。");
		}else{
			if(mi201.getWebsiteCode().getBytes("GBK").length>20){
				errormessage.append("网点编码长度超长，不可超过20位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getBusinessType())){
			errormessage.append("网点类型为空。");
		}else{
			if(mi201.getBusinessType().getBytes("GBK").length>100){
				errormessage.append("业务类型长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getWebsiteName())){
			errormessage.append("网点名称为空。");
		}else{
			if(mi201.getWebsiteName().getBytes("GBK").length>100){
				errormessage.append("网点名称长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getAreaId())){
			errormessage.append("区域为空。");
		}else{
			if(mi201.getAreaId().getBytes("GBK").length>100){
				errormessage.append("区域名称长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getAddress())){
			errormessage.append("网点地址为空。");
		}else{
			if(mi201.getAddress().getBytes("GBK").length>200){
				errormessage.append("区域名称长度超长，不可超过200位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getTel())){
			errormessage.append("联系电话为空。");
		}else{
			if(mi201.getTel().getBytes("GBK").length>50){
				errormessage.append("联系电话长度超长，不可超过50位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi201.getServiceTime())){
			errormessage.append("营业时间为空。");
		}else{
			if(mi201.getServiceTime().getBytes("GBK").length>100){
				errormessage.append("营业时间长度超长，不可超过100位长度。");
			}
		}
		if(errormessage.toString().equals("")){
			Mi202Example mi202Example = new Mi202Example();
			mi202Example.createCriteria()
			.andCenteridEqualTo(cityCode).andAreaNameLike("%"+mi201.getAreaId().trim()+"%")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);			
			List<Mi202> list = mi202Dao.selectByExample(mi202Example);
			if(CommonUtil.isEmpty(list)){
				errormessage.append("处理区域信息"+mi201.getAreaId()+"异常，未匹配到相应的区域信息，请联系管理员。");
			}else{
				mi201.setAreaId(list.get(0).getAreaId());
			}		
		}
		
		Mi201Example m201e=new Mi201Example();
		com.yondervision.mi.dto.Mi201Example.Criteria ca= m201e.createCriteria();
		ca.andCenteridEqualTo(cityCode);
		ca.andWebsiteCodeEqualTo(mi201.getWebsiteCode());		
		List<Mi201> mi201List = mi201Dao.selectByExample(m201e);
		if(mi201List.size()>0){
			errormessage.append("中心网点编号己存在。");
		}		
		return mi201;
	}
	
	public Mi203 checkExcelMapMi203(int i, Mi203 mi203, String cityCode,StringBuffer errormessage) throws UnsupportedEncodingException{
		
		if(CommonUtil.isEmpty(mi203.getHouseCode())){
			errormessage.append("楼盘编码为空。");
		}else{
			if(mi203.getHouseCode().getBytes("GBK").length>20){
				errormessage.append("楼盘编码长度超长，不可超过20位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getHouseType())){
			errormessage.append("楼盘类型为空。");
		}else{
			if(mi203.getHouseType().getBytes("GBK").length>50){
				errormessage.append("楼盘类型长度超长，不可超过50位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getHouseName())){
			errormessage.append("楼盘名称为空。");
		}else{
			if(mi203.getHouseName().getBytes("GBK").length>100){
				errormessage.append("楼盘名称长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getAreaId())){
			errormessage.append("区域为空。");
		}else{
			if(mi203.getAreaId().getBytes("GBK").length>100){
				errormessage.append("区域名称长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getAddress())){
			errormessage.append("楼盘地址为空。");
		}else{
			if(mi203.getAddress().getBytes("GBK").length>200){
				errormessage.append("楼盘地址长度超长，不可超过200位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getDeveloperName())){
			errormessage.append("开发商名称为空。");
		}else{
			if(mi203.getDeveloperName().getBytes("GBK").length>100){
				errormessage.append("开发商名称长度超长，不可超过100位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getTel())){
			errormessage.append("联系电话为空。");
		}else{
			if(mi203.getTel().getBytes("GBK").length>50){
				errormessage.append("联系电话称长度超长，不可超过50位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getContacterName())){
			errormessage.append("联系人姓名为空。");
		}else{
			if(mi203.getContacterName().getBytes("GBK").length>20){
				errormessage.append("联系人姓名称长度超长，不可超过20位长度。");
			}
		}
		if(CommonUtil.isEmpty(mi203.getBankNames())){
			errormessage.append("合作银行为空。");
		}else{
			if(mi203.getBankNames().getBytes("GBK").length>100){
				errormessage.append("合作银行称长度超长，不可超过100位长度。");
			}
		}
		if(errormessage.toString().equals("")){
			Mi202Example mi202Example = new Mi202Example();
			mi202Example.createCriteria()
			.andCenteridEqualTo(cityCode).andAreaNameLike("%"+mi203.getAreaId().trim()+"%")
			.andValidflagEqualTo(Constants.IS_VALIDFLAG);			
			List<Mi202> list = mi202Dao.selectByExample(mi202Example);
			if(CommonUtil.isEmpty(list)){
				errormessage.append("处理区域信息"+mi203.getAreaId()+"异常，未匹配到相应的区域信息，请联系管理员。");
			}else{
				mi203.setAreaId(list.get(0).getAreaId());
			}			
		}
		
		Mi203Example m203e=new Mi203Example();
		com.yondervision.mi.dto.Mi203Example.Criteria ca= m203e.createCriteria();
		ca.andCenteridEqualTo(cityCode);
		ca.andHouseCodeEqualTo(mi203.getHouseCode());		
		List<Mi203> mi203List = mi203Dao.selectByExample(m203e);
		if(mi203List.size()>0){
			errormessage.append("中心楼盘编号己存在。");
		}		
		return mi203;
	}

	public void excelMapToCheck(String loginid, String cityname,
			String cityCode, Map<String, String> ruleMap, String tableName,
			InputStream is, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean checkResult = true;
		String fname = System.currentTimeMillis() + ".xls";
		String filename = cityCode + File.separator + fname;
		boolean isFullUrl = true;
		String importFilePatch = CommonUtil.getFileFullPath(
				"push_lp_wd_importfile", filename, isFullUrl);
		File importFileOnServer = new File(importFilePatch);
		if (!importFileOnServer.getParentFile().isDirectory()) {
			if(importFileOnServer.getParentFile().mkdirs())
				System.out.println("创建文件或文件夹:"+importFileOnServer.getParentFile());
		}
		// 写入文件
		FileOutputStream fs = new FileOutputStream(importFileOnServer);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;		
		while ((byteread = is.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		StringBuffer errormessage = new StringBuffer("");
		Logger log=LoggerUtil.getLogger();
		log.info("[+]excelToCheck");
		Object mixxx=null;
		Object mi = null;
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		Date date = new Date();
		try{
			mixxx=Class.forName("com.yondervision.mi.dto."+tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase()).newInstance();
			Workbook wb=jxl.Workbook.getWorkbook(importFileOnServer);
			jxl.Sheet sheet=wb.getSheet(0);			
			WritableWorkbook wbook = Workbook.createWorkbook(importFileOnServer);
			WritableSheet wsheet = wbook.createSheet("检查结果", 1);
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.RED);
			WritableCellFormat errFormat = new WritableCellFormat(wfont);
			errFormat.setWrap(true);
			wsheet.getSettings().setProtected(true);
			wsheet.setColumnView(sheet.getRow(0).length, 350);
			WritableFont wfont1 = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			WritableCellFormat errFormat1 = new WritableCellFormat(wfont1);
			errFormat1.setAlignment(Alignment.CENTRE);
			
			Cell[] row0 = sheet.getRow(0);
			for(int i=0;i<row0.length;i++){
				Label content1 = new Label(i, 0, row0[i].getContents(), errFormat1);
		 		wsheet.addCell(content1);
			}
			Label content1 = new Label(row0.length, 0, "检查结果",errFormat1);
	 		wsheet.addCell(content1);
			
	    	for(int i=1;i<sheet.getRows();i++){	   
	    		Cell[] rowData = sheet.getRow(i);
	    		List<String> list=new ArrayList<String>();
	    		int sum = 0;
	    		 for(int j=0;j<rowData.length;j++){ 
	    			 String v=sheet.getCell(j, i).getContents();
	    			 list.add(v);
	    			 if(CommonUtil.isEmpty(v)){
	    				 sum++;
	    			 }
	    			 if(CommonUtil.isEmpty(rowData[j]) || CommonUtil.isEmpty(rowData[j].getContents())){
	    				 continue;
	    			 }
	    			 Label content = new Label(j, i, rowData[j].getContents());
					 wsheet.addCell(content);
	    		 }
	    		 if(sheet.getColumns()<=sum){
	    			 continue;
	    		 }
	    		 for(String key:ruleMap.keySet()){
	    			 String val=ruleMap.get(key);
	    			 if(val.startsWith("#")){
	    				 int wz=Integer.parseInt(val.substring(1))-1;
	    				 val=list.get(wz); 
	    			 }	    				 
	    			 com.yondervision.mi.util.JavaBeanUtil.setter(mixxx, key, val);
	    		 }
	    		 errormessage.setLength(0);
	    		 if(tableName.equals("mi201")){
	    			 Mi201 mi201 = new Mi201();
	    			 mi201 = (Mi201)mixxx;
	    			 mi201.setCenterid(cityCode);
	    			 mi201.setWebsiteId(CommonUtil.genKeyAndCommit("MI201", 20));
	    			 mi201.setValidflag(Constants.IS_VALIDFLAG);
	    			 mi201.setDatecreated(formatter.format(date));
	    			 mi201.setDatemodified(formatter.format(date));
	    			 mi201 = checkExcelMapMi201(i, mi201, cityCode, errormessage);
	    			 
	    			 if(!(errormessage.length()==0)){
	    				 checkResult = false;
	    				 Label content = new Label(rowData.length, i, errormessage.toString(),errFormat);
	 					wsheet.addCell(content);
	    			 }else{
	    				 Label content = new Label(rowData.length, i, "");
		 				 wsheet.addCell(content);
	    			 }	    			 
	    		 }else if(tableName.equals("mi203")){	    			
	    			 Mi203 mi203 = new Mi203();
	    			 mi203 = (Mi203)mixxx;
	    			 mi203.setCenterid(cityCode);
	    			 mi203.setHousesId(CommonUtil.genKeyAndCommit("MI203", 20));
	    			 mi203.setValidflag(Constants.IS_VALIDFLAG);
	    			 mi203.setDatecreated(formatter.format(date));
	    			 mi203.setDatemodified(formatter.format(date));
	    			 mi203.setLoginid(loginid);
	    			 mi203 = checkExcelMapMi203(i, mi203, cityCode, errormessage);
	    			 if(!(errormessage.length()==0)){
	    				checkResult = false;
	    				Label content = new Label(rowData.length, i, errormessage.toString(),errFormat);
	 					wsheet.addCell(content);
	    			 }else{
	    				 Label content = new Label(rowData.length, i, "");
		 				 wsheet.addCell(content);
	    			 }
	    		 }		    		 
	    	 }	   
	    	 wbook.write();
			 wbook.close();
	    	 wb.close(); 
	    	 if(checkResult){
	    		 setCheckResult("true");
	    		 setCheckFileName(importFilePatch);
	    	 }else{
	    		 setCheckResult("false");
	    		 setCheckFileName("downloadimg.file?filePathParam=push_lp_wd_importfile&fileName="+filename+"&isFullUrl=true");
	    	 }
		}catch(Exception e){
			e.printStackTrace();
	    	throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"文件格式检查");
		}		
		log.info("[-]excelToDB");
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckFileName() {
		return checkFileName;
	}

	public void setCheckFileName(String checkFileName) {
		this.checkFileName = checkFileName;
	}

	public Mi201DAO getMi201Dao() {
		return mi201Dao;
	}

	public void setMi201Dao(Mi201DAO mi201Dao) {
		this.mi201Dao = mi201Dao;
	}

	public Mi203DAO getMi203Dao() {
		return mi203Dao;
	}

	public void setMi203Dao(Mi203DAO mi203Dao) {
		this.mi203Dao = mi203Dao;
	}
}
