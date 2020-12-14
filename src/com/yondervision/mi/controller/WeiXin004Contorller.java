package com.yondervision.mi.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.WeiXinMessageUtil;
import com.yondervision.mi.form.WeiXin00102Form;
import com.yondervision.mi.form.WeiXin00401Form;
import com.yondervision.mi.form.WeiXin00402Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.PropertiesReader;

@Controller
public class WeiXin004Contorller {
	/**
	 * 上传图片
	 * 
	 * */
	@RequestMapping("/weixinapi00401.{ext}")
	public Object uploadimg(@RequestParam("uploadfile")MultipartFile file,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信关注信上传图片"));
		File uploadedFile = null;
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
				writeMsg(response, "请选择文件。");
				return null;
			}
			//验证城市中心码
			UserContext user = UserContext.getInstance();
			if (CommonUtil.isEmpty(user.getCenterid())) {
				writeMsg(response, "请传入城市中心代码！");
				return null;
			}
			//根据分组定义图片保存路劲
			String filePath = CommonUtil.getFileFullPath("weixin_subscribe_img", user.getCenterid(), true) + File.separator;
			File dirCityFile = new File(filePath);
			if (!dirCityFile.exists()) {
				dirCityFile.mkdirs();
			}
			//图片名称
			String imageFileName = file.getOriginalFilename();
			imageFileName = new String(imageFileName.getBytes("ISO8859-1"),"UTF-8");
			//图片格式
			String fileExt = imageFileName.substring(imageFileName.lastIndexOf(".") + 1).toLowerCase();
			//定义图片保存名称
			String newFileName = UUID.randomUUID().toString();
			InputStream in = file.getInputStream();
			uploadedFile = new File(filePath, newFileName + "." + fileExt);
			OutputStream out = new FileOutputStream(uploadedFile);
			byte[] bs = new byte[1024 * 1024];
			int count = 0;
			while ((count = in.read(bs)) > 0) {
				out.write(bs, 0, count);
			}
			out.close();
			in.close();
			String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
					"weixin_subscribe_img", user.getCenterid() + File.separator
					+ newFileName + "." + fileExt, true);
			modelMap.put("imgUrl", imgUrl);
			modelMap.put("realUrl", uploadedFile);
			log.info(LOG.END_BUSIN.getLogText("微信关注信上传图片"));	
			return modelMap;
		} catch (IOException e1) {
			if(uploadedFile!=null){
				uploadedFile.delete();
			}
			System.out.println("上传文件失败。");
			System.out.println("e1====" + e1);
			return null;
		}catch (Exception e) {
			if(uploadedFile!=null){
				uploadedFile.delete();
			}
			System.out.println("上传文件失败。");
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 删除图片
	 * 
	 * */
	@RequestMapping("/weixinapi00402.{ext}")
	public String DeletePic(ModelMap modelMap,String realurl){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信关注信删除图片"));
		modelMap.clear();
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return null;
		}
		File file = new File(realurl);
		file.delete();
		modelMap.put("recode", "000000");
		log.info(LOG.END_BUSIN.getLogText("微信关注信删除图片"));	
		return null;
	}
	
	/**
	 * 添加功能配置
	 * 
	 * */
	@RequestMapping("/weixinapi00403.{ext}")
	public void AddFunc(ModelMap modelMap,WeiXin00401Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("微信关注信息功能配置业务开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=add&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信添加功能配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		String str = weixin.post(url, value, modelMap, request, response);

		log.info(LOG.END_BUSIN.getLogText("微信关注信息功能配置业务结束"));	
	}
	
	/**
	 * 查询功能配置
	 * 
	 * */
	@RequestMapping("/weixinapi00404.{ext}")
	public void QueryFunc(ModelMap modelMap,WeiXin00401Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("查询微信关注信息功能配置业务开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=query&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信查询功能配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		String str = weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("查询微信关注信息功能配置业务结束"));	
	}
	
	/**
	 * 删除功能配置
	 * 
	 * */
	@RequestMapping("/weixinapi00405.{ext}")
	public void DeleteFunc(ModelMap modelMap,WeiXin00401Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("删除微信关注信息功能配置开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=delete&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信删除功能配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		String str = weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("删除微信关注信息功能配置业务结束"));	
	}
	
	/**
	 * 添加子项功能配置
	 * 
	 * */
	@RequestMapping("/weixinapi00406.{ext}")
	public void AddFuncChild(ModelMap modelMap,WeiXin00401Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("添加微信关注信息子项功能配置业务开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=addchild&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信添加子项功能配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		String str = weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("添加微信关注信息子项功能配置结束"));	
	}
	
	/**
	 * 添加关注信息配置
	 * 
	 * */
	@RequestMapping("/weixinapi00407.{ext}")
	public void AddSubcribeMessage(ModelMap modelMap,WeiXin00402Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("添加关注信息配置业务开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		Map<String,String> map = form.getFuncArray();
		Map<String,String> resultmap = this.sortMapByKey(map);
		form.setFuncArray(resultmap);
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=save&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		System.out.println(value);
		log.debug("微信添加关注信息配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		String str = weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("添加关注信息配置业务结束"));	
	}
	
	/**
	 * 查询关注信息配置
	 * 
	 * */
	@RequestMapping("/weixinapi00408.{ext}")
	public void GetSubcribeMessage(ModelMap modelMap,WeiXin00102Form form,HttpServletRequest request, HttpServletResponse response){
		Logger log = LoggerUtil.getLogger();
		log.info(LOG.START_BUSIN.getLogText("查询关注信息配置业务开始"));
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			modelMap.put("recode", "999999");
			return;
		}
		String url = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "weixinurl").trim()+"/configure?method=querysub&type=subscribe";
		String value = JsonUtil.getGson().toJson(form);
		log.debug("微信查询关注信息配置参数："+value);
		WeiXinMessageUtil weixin = new WeiXinMessageUtil();
		weixin.post(url, value, modelMap, request, response);
		log.info(LOG.END_BUSIN.getLogText("查询关注信息配置业务结束"));	
	}
	
	/**
	 * 输出信息
	 * @param response
	 * @param message
	 */
	private void writeMsg(HttpServletResponse response, String message){
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		writeJson(response, msg);
		
	}
	/**
	 *输出json
	 * @param response
	 * @param msg
	 */
	private void writeJson(HttpServletResponse response, Object msg){
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            
			writer.println(JSON.toJSONString(msg));
            
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	private Map<String, String> sortMapByKey(Map<String, String> oriMap) {  
	    if (oriMap == null || oriMap.isEmpty()) {  
	        return null;  
	    }  
	    Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {  
	        public int compare(String key1, String key2) {  
	            int intKey1 = 0, intKey2 = 0;  
	            try {  
	                intKey1 = getInt(key1);  
	                intKey2 = getInt(key2);  
	            } catch (Exception e) {  
	                intKey1 = 0;   
	                intKey2 = 0;  
	            }  
	            return intKey1 - intKey2;  
	        }});  
	    sortedMap.putAll(oriMap);  
	    return sortedMap;  
	}  
	  
	private int getInt(String str) {  
	    int i = 0;  
	    try {  
	        Pattern p = Pattern.compile("^\\d+");  
	        Matcher m = p.matcher(str);  
	        if (m.find()) {  
	            i = Integer.valueOf(m.group());  
	        }  
	    } catch (NumberFormatException e) {  
	        e.printStackTrace();  
	    }  
	    return i;  
	}  
}
