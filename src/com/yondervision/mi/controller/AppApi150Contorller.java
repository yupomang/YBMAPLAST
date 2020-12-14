package com.yondervision.mi.controller;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;
import com.yondervision.mi.form.AppApi00225Form;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.AES;
import com.yondervision.mi.util.security.Base64Decoder;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 张杰
 * @version V1.0
 * @date 2020/11/19 11:19
 */
@Controller
public class AppApi150Contorller {

    @RequestMapping("/appapi00271.{ext}")
    public String appapi00271(AppApi00225Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("异地贷款缴存使用证明打印");
        Logger log = LoggerUtil.getLogger();
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));

        long starTime = System.currentTimeMillis();
        HashMap params = new HashMap();
        String url = "http://172.16.0.208:7001/gjj-wsyyt/servlet/EmpPrintServlet";

        log.info("form.getAccnum()======" + form.getAccnum());
        log.info("form.getCertinum()======" + form.getCertinum());
        log.info("form.getCentername()======" + form.getCentername());
        log.info("form.getUserid()======" + form.getUserid());
        log.info("form.getBrccode()======" + form.getBrccode());
        log.info("form.getChannel()======" + form.getChannel());
        log.info("form.getProjectname()======" + form.getProjectname());
        log.info("form.getCenterId()======" + form.getCenterId());
        log.info("form.getBuzType()======" + form.getBuzType());

        params.put("accnum", form.getAccnum());
        params.put("certinum", form.getCertinum());
        params.put("centername", form.getCentername());
        params.put("userid", form.getUserid());
        params.put("brccode", form.getBrccode());
        params.put("channel", "ZWFWW");
        params.put("projectname", form.getProjectname());

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\n", ""));
        if (json.get("recode").equals("999999")){
            json.put("recode","999999");
            String errMsg =  json.get("errMsg").toString();
            json.put("errMsg",new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
            log.info("errMsg1===" + new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
            log.info("json.toString()===" + json.toString());
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
            return json.toString();
        }else{
            String fileByte = json.get("fileByte").toString();
            System.out.println("fileByte================" + fileByte);
            String pdfBase64Str = fileByte;
            log.info("存放pdf路径");
            String saleContractPDFfilepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "salePDFfilepath");
            log.info("saleContractPDFfilepath============" + saleContractPDFfilepath);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String saleContractNo = dateFormat.format(new Date());
            String filepath = saleContractPDFfilepath + form.getCertinum() + "-" + saleContractNo + ".pdf";
            String filename = form.getCertinum() + "-" + saleContractNo + ".pdf";
            log.info("filepath============" + filepath);
            log.info("filename============" + filename);
            //fileuoload3(filename);
            //String filepath = "D://2.pdf";
            BasetoPdffile(pdfBase64Str, filepath); //生成base64txt
            BasetoPdffile1(pdfBase64Str, filepath);//生成pdf
            System.out.println("生成文件结束");
            json.remove("fileByte");
            //测试
            //json.put("filepath", "http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/");
            //生产
            json.put("filepath", "http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
            json.put("filename", filename+".txt");
            json.remove("dataCount");
            json.remove("data");
            log.info("appApi00227 end.");
            log.info("form.getChannel()=" + form.getChannel());

            log.info("gbk");
            log.info("json.toString()==========" + json);
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
            return "/index";
        }

    }

    /*文件流解密为pdf格式*/
    public static void BasetoPdffile1(String pdfBase64Str,String filepath){
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            byte[] bytes= Base64Decoder.decodeToBytes(pdfBase64Str);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            bis=new BufferedInputStream(byteArrayInputStream);
            File file=new File(filepath);
            File path=file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos=new FileOutputStream(file);
            bos=new BufferedOutputStream(fos);

            byte[] buffer=new byte[1024];
            int length=bis.read(buffer);
            while(length!=-1){
                bos.write(buffer,0,length);
                length=bis.read(buffer);
            }
            bos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                bis.close();
                bos.close();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /*文件流解密为pdf格式*/
    public static void BasetoPdffile(String pdfBase64Str,String filepath){
        BufferedInputStream bis = null;//缓冲输入流
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            //BASE64Decoder encoder = new BASE64Encoder();
            //byte[] bytes = pdfBase64Str.getBytes();
            //String Base64Str = encoder.encode(bytes);
            //byte[] bytes= Base64Decoder.decodeToBytes(pdfBase64Str);//Base64解密字符串pdfBase64Str
            byte[] bytes1 = pdfBase64Str.getBytes();
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes1);//把字符串数组新建输入流
            bis=new BufferedInputStream(byteArrayInputStream);//变为缓存输入流
            File file=new File(filepath+".txt");//把filepath下文件存入新建file文件中
            File path=file.getParentFile();//获取文件所在目录
            if(!path.exists()){
                path.mkdirs();
            }
            fos=new FileOutputStream(file);//输出流
            bos=new BufferedOutputStream(fos);//缓存输出流

            byte[] buffer=new byte[1024];
            int length=bis.read(buffer);//每次读取1024个长度字节,length为读取的数据长度
            while(length!=-1){//如果碰到-1说明没有值了.
                bos.write(buffer,0,length);//写入到bos缓存输出流，从下标索引0开始，长度为length
                length=bis.read(buffer);
            }
            bos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                bis.close();
                bos.close();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

   /* public String getJiaoCunQingKuang(AppApi030Form form, ModelMap modelMap) throws Exception {
        Logger log = LoggerUtil.getLogger();
        form.setBusinName("职工住房公积金缴存情况证明");
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil
                .getStringParams(form)));
        ApiUserContext.getInstance();
        request.setAttribute("centerId", form.getCenterId());
        if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
            String rep=msgSendApi001Service.send(request, response);
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
            response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
            log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }else
        {
            String rep=msgSendApi001Service.send(request, response);
            response.getOutputStream().write(rep.getBytes("UTF-8"));
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }
    }*/
}
