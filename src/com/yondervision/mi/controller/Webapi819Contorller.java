package com.yondervision.mi.controller;

import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00240Form;
import com.yondervision.mi.form.AppApi00241Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.ReadProperty;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Webapi819Contorller {
    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }
    private static String TEMP_PATH = ReadProperty.getString("temp_path");
    private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");
    private static String TEMPLATE_PATH = ReadProperty.getString("template_path");

    @RequestMapping("/webapi80019.{ext}")
    public Map<String, String> webapi80019(AppApi00241Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        form.setBusinName("webapi80019--公积金贷款信息查询打印");
        Logger log = LoggerUtil.getLogger();
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        long starTime = System.currentTimeMillis();
        log.info("webapi80018--form.getUnitaccname()======" + form.getSpt_ywid());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("centerId", form.getCenterId()); //date : 2020-11-25
        request.setAttribute("seqno", "3");
        request.setAttribute("userId", "9990");
        request.setAttribute("buzType", form.getBuzType());
        request.setAttribute("channel", form.getChannel());

        //请求YF查询数据
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
        request.setAttribute("centerId", form.getCenterId());
        String rep=msgSendApi001Service.send(request, response);
        log.debug("rep========"+rep);
        JSONObject json1 = JSONObject.fromObject(rep);
        log.info("json1.toString()==========" + json1.toString());
        Map<String, String> map = new HashMap<String, String>();
        if(json1.get("recode").equals("999999")||json1.get("recode").equals("E90063")){
            log.info("rep的值为: " + rep);
            response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            map.put("message", "推送文件名和路径失败！");
            return map;
        }

        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("webapi80018--请求耗时" + Time + "毫秒");
        return map;
    }
}
