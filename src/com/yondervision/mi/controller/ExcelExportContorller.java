package com.yondervision.mi.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.mi.dto.Mi009;
import com.yondervision.mi.service.ExcelApi001Service;
import com.yondervision.mi.service.PtlApi002Service;

@Controller
public class ExcelExportContorller {
	@Autowired
	ExcelApi001Service excelApi001Service=null;
	public void setExcelApi001Service(ExcelApi001Service excelApi001Service) {
		this.excelApi001Service = excelApi001Service;
	}
	@Autowired
	private PtlApi002Service ptlApi002Service = null;
	public void setPtlApi002Service(PtlApi002Service ptlApi002Service) {
		this.ptlApi002Service = ptlApi002Service;
	}
	
	@RequestMapping("/dbToExcel.do")
	public void dbToExcel(HttpServletRequest request, HttpServletResponse response, String expotrTableName, String titles,
			String fileName, ModelMap modelMap) throws Exception{

		// 确定要查询表的查询条件
		List<Mi009> list = ptlApi002Service.queryLogCodeAll();
		
		// 直接往response的输出流中写excel
		OutputStream outputStream = response.getOutputStream();
		//清空输出流
		response.reset();
		
		// 下载格式设置、定义输出类型
		response.setContentType("APPLICATION/OCTET-STREAM");
		//设置响应头和下载保存的文件名   
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +".xls"+ "\"");
		
		excelApi001Service.generalExcelFileInOutputStream(outputStream, list, expotrTableName, titles);
		outputStream.close();

        //这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
	    response.flushBuffer();//强行将响应缓存中的内容发送到目的地        

	} 
}
