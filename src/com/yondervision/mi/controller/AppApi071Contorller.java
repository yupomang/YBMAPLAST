package com.yondervision.mi.controller;

import com.yd.util.DateUtils;
import com.yd.util.FileUtil;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00240Form;
import com.yondervision.mi.util.DigitalSealService;
import com.yondervision.mi.util.ReadProperty;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AppApi071Contorller {
    private static String TEMP_PATH = ReadProperty.getString("temp_path");
    private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");
    private static String TEMPLATE_PATH = ReadProperty.getString("template_path");

    @RequestMapping("/appapi00338.{ext}")
    public String appapi00338(AppApi00240Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Logger logger = LoggerUtil.getLogger();
        form.setBusinName("公积金缴存明细查询打印接口");
        logger.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        String voucherData = form.getVoucherData();
        String tranDate = DateUtils.getyyyyMMddHHmmss();
        logger.debug("voucherData========"+voucherData);
        Map<String, String> map = new HashMap<String, String>();

        request.setAttribute("centerId", form.getCenterId()); //date : 2020-11-25
        request.setAttribute("seqno", "3");
        request.setAttribute("userId", "9990");

        request.setAttribute("channel", "10");

        //开始打印
        File pdfFile = null;
        String templateName = "gjjjcmxcxdy.doc"; //模板文件名
        String fullName = tranDate + "_" + templateName;
        String templatePath = ReadProperty.getString("template_path");
        File docFile = FileUtil.createWord(templatePath + templateName, new File(TEMP_PATH, fullName + ".doc"), voucherData);
        pdfFile = FileUtil.wordToPdf(docFile, new File(TEMP_PATH, fullName + ".pdf"));

        String rep = "{\"msg\":\"成功\",\"recode\":\"000000\"}";
        //request.setAttribute("centerId", form.getCenterId());
        //String rep=msgSendApi001Service.send(request, response);
        logger.debug("rep========"+rep);

        JSONObject json = JSONObject.fromObject(rep);
        if (pdfFile == null) {
            logger.error("生成的PDF文件为空！");
            //json.remove("voucherData");
            json.put("returnCode", "1");
            json.put("message", "凭证文件生成异常！");
            response.getOutputStream().write(json.toString().getBytes("UTF-8"));
            return json.toString();
        }
        logger.info("PDF文件生成完毕！");

        //盖章
        String pdftempUrl = TEMP_PATH + pdfFile.getName();//盖章前pdf存放位置
        String pdfUrl = signed_pdf_path + pdfFile.getName();//盖章后pdf存放位置
        //===============调用天谷系统盖章方法根据关键字盖章==========================
        //String errmassage = DigitalSealService.digitalSealByKey("(章)",pdftempUrl,pdfUrl);
        String errmassage = DigitalSealService.digitalSealByLocation(90,740,pdftempUrl,pdfUrl);
        if (errmassage.equals("成功")){
            //json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
            json.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/pdf/");
            json.put("filename",pdfFile.getName());
            response.getOutputStream().write(json.toString().getBytes("UTF-8"));
            logger.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            map.clear();
            return json.toString();
        }else{
            json.put("errmassage",errmassage);
            response.getOutputStream().write(json.toString().getBytes("UTF-8"));
            return json.toString();
        }
    }
}
