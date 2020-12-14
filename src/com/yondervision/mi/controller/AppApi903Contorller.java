package com.yondervision.mi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.form.AppApi80202Form;
import com.yondervision.mi.util.WkfAccessTokenUtil;
import com.yondervision.mi.util.security.RSASignature;

@Controller
public class AppApi903Contorller {
	/**
	 * 发送消息
	 * 参数 request接受 data,appUser,nickName,appWBway
	 * 
	 * */
	@RequestMapping("/appapi90301.json")
	public String appapi90301( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		headCheck(request);
		WkfAccessTokenUtil.writeLog("[+]appapi90301 "+request.getQueryString());
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	 
	    JSONObject messageBody=new JSONObject();
	    String appUser="WB"+request.getParameter("appUser");
	    String appWBway=request.getParameter("appWBway");
	    String nickName=request.getParameter("nickName");
		messageBody.put("appUser", appUser);
		messageBody.put("appPlat", "sina");
		messageBody.put("data", request.getParameter("data"));
		if(appWBway!=null){
			//messageBody.put("appWBway", appWBway);
		}
		if(request.getParameter("cmd")!=null){
			messageBody.put("cmd", request.getParameter("cmd"));
		}
		String centerId= request.getParameter("centerId");  
		JSONObject infoobj=WkfAccessTokenUtil.WKF_WeixinPost(centerId, "/user/sendmsg", messageBody.toString());
		if(infoobj.getInt("errcode")==3060){
			String info="{\"name\":\""+appUser+"\",\"nickname\":\""+nickName+"\",\"passwd\":\"123456\"}";
			JSONObject obj=WkfAccessTokenUtil.WKF_WeixinPost(centerId, "/user/register", info);
			infoobj=WkfAccessTokenUtil.WKF_WeixinPost(centerId, "/user/sendmsg", messageBody.toString()); 
		} 
		//发送消息
		modelMap.put("wkfdata",infoobj);
		WkfAccessTokenUtil.writeLog("[-]appapi90301");
		return "";
	}
	/**
	 * 评分
	 * 参数 chatId，score
	 * */
	@RequestMapping("/appapi90302.json")
	public String appapi90302( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		headCheck(request);
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);	   
		
	    JSONObject messageBody=new JSONObject();
	    messageBody.put("chatId", request.getParameter("chatId"));
	    messageBody.put("appPlat", "sina");
	    messageBody.put("score", request.getParameter("score"));
	    String appUser="WB"+request.getParameter("appUser"); 
	    messageBody.put("appUser", appUser);
	    String centerId= request.getParameter("centerId");  
	    //发送消息
	    modelMap.put("wkfdata",
	    		WkfAccessTokenUtil.WKF_WeixinPost(
	    				centerId, "/user/score?chatId="+request.getParameter("chatId"), messageBody.toString())
	    		); 
		return "";
	}
	/**
	 * 注册
	 * 参数 appUser，nickname
	 * */	
	@RequestMapping("/appapi90303.json")
	public String appapi90303( ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		headCheck(request);	  
		String centerId= request.getParameter("centerId"); 	 
		String userid="WB"+request.getParameter("appUser"); 
		String nickname=request.getParameter("nickname"); 
		String info="{\"name\":\""+userid+"\",\"nickname\":\""+nickname+"\",\"passwd\":\"123456\"}";
		JSONObject obj=WkfAccessTokenUtil.WKF_WeixinPost(
 				centerId, "/user/register", info);
		 modelMap.put("wkfdata", obj );
		 return "";
	}
	private void headCheck(HttpServletRequest request){
		if(true){
			return;
		}
		StringBuffer values = new StringBuffer();
		String paras = request.getHeader("headpara");
		if(paras==null)
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		String[] par = paras.split(",");
		
		for(int i=0;i<par.length;i++){			
			if(i!=0){
				values.append("&");
			}						
			values.append(par[i]+"="+request.getParameter(par[i]));
		}
		String headMD5 = request.getHeader("headparaMD5");
		if(headMD5==null)
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		String paraMD5;
		try {
			paraMD5 = EncryptionByMD5.getMD5(values.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		} 
		if(RSASignature.doCheck(paraMD5, headMD5, RSASignature.RSA_ALIPAY_PUBLIC)){            
            System.out.println("APP服务==============================APP签名验证成功");
        }else{
        	System.out.println("APP服务==============================APP签名验证失败");
        	throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_MD5_CHECK.getValue());
        }
		
	}
	@RequestMapping("/appapi90306.json")
	public String appapi90306( ModelMap modelMap, @RequestParam MultipartFile importFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
