package com.yondervision.mi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.form.AppApi80202Form;
import com.yondervision.mi.util.WkfAccessTokenUtil;

@Controller
public class AppApi901Contorller {
	@RequestMapping("/appapi90101.json")
	public String appapi90101( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	   
		modelMap.put("wkfdata",WkfAccessTokenUtil.WKF_SendMsg(request.getParameter("data"),request.getParameter("cmd")) );
		return "";
	}
	@RequestMapping("/appapi90102.json")
	public String appapi90102( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	   
		JSONObject obj=WkfAccessTokenUtil.WKF_closechat(request.getParameter("chatId"), Integer.parseInt(request.getParameter("score"))); 
		modelMap.put("wkfdata", obj );
		return "";
	}
	@RequestMapping("/appapi90103.json")
	public String appapi90103( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	   
		JSONObject obj=WkfAccessTokenUtil.WKF_getMqttToken();
		modelMap.put("wkfdata", obj );
		return "";
	}
	@RequestMapping("/appapi90104.json")
	public String appapi90104( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	   
		JSONObject obj=WkfAccessTokenUtil.WKF_reGetMqtt();
		modelMap.put("wkfdata", obj );
		return "";
	}
	@RequestMapping("/appapi90105.json")
	public String appapi90105( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		WkfAccessTokenUtil.writeLog("[+]appapi90105 param "+request.getQueryString()); 
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	
		if(request.getParameter("startId")==null){
			throw new TransRuntimeErrorException("299992", new String[] { "startId==null" }); 
		}
		if(request.getParameter("startPage")==null){
			throw new TransRuntimeErrorException("299992", new String[] { "startPage==null" }); 
		}
		if(request.getParameter("size")==null){
			throw new TransRuntimeErrorException("299992", new String[] { "size==null" }); 
		}
		if(request.getParameter("current")==null){
			throw new TransRuntimeErrorException("299992", new String[] { "current==null" }); 
		}
		
				
		try{
		   int startId=Integer.parseInt(request.getParameter("startId"));
		   int startPage=Integer.parseInt(request.getParameter("startPage"));
		   int size=Integer.parseInt(request.getParameter("size"));
		   int current=Integer.parseInt(request.getParameter("current"));
		   JSONObject obj=WkfAccessTokenUtil.WKF_chatlog(request.getParameter("chatId"),startId,startPage,size,current);
		   modelMap.put("wkfdata", obj );
		   WkfAccessTokenUtil.writeLog("[-]appapi90105 obj="+obj.toString());
		}catch(Exception e){
			WkfAccessTokenUtil.writeLog(e);
			throw e;
		}
		
		return "";
	}
	@RequestMapping("/appapi90106.json")
	public String appapi90106( ModelMap modelMap, @RequestParam MultipartFile importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
	    String filepath="/was/"+System.currentTimeMillis()+"_"+Thread.currentThread().getId()+".jpg";
		FileOutputStream fs = new FileOutputStream(filepath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		InputStream stream = importFile.getInputStream();
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close(); 
		JSONObject obj=WkfAccessTokenUtil.WKF_upload(request.getParameter("centerId"), filepath);		
		modelMap.put("wkfdata", obj );
		try{
			new File(filepath).delete();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}   
 
}
