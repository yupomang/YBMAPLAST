package com.yondervision.mi.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class WebApi900Contorller extends HttpServlet {
	Logger log = Logger.getLogger("YBMAP");

	/**
	 *  下载
	 * @param
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi90001.{ext}")
	public String webapi90001( ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("webapi90001 begin.");
		modelMap.clear();
		 //获取文件下载路径
        String path =   request.getParameter("filepath"); 
        File file = new File(path);
        String filename = file.getName(); 
		log.info("filename="+filename);
        if(file.exists()){
    		log.info("文件存在");
            //设置相应类型application/octet-stream
        	response.setContentType("application/x-msdownload");
            //设置头信息                 Content-Disposition为属性名  附件形式打开下载文件   指定名称  为 设定的filename
        	response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            //输入流写入输出流
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream ouputStream = response.getOutputStream();
            byte b[] = new byte[1024];
            int n ;
            //循环读取 !=-1读取完毕
            while((n = inputStream.read(b)) != -1){
                //写入到输出流 从0读取到n
                ouputStream.write(b,0,n);
            }
            //关闭流、释放资源
            ouputStream.close();
            inputStream.close();
        }else{
        	request.setAttribute("errorResult", "文件不存在下载失败！");
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/01.jsp");
            dispatcher.forward(request, response);
        }

		log.info("webapi90001 end.");
		return "";
	}


}
