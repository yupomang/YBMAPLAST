package com.yondervision.mi.controller;

import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi50001Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.AES;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class AppApi047Contorller {

    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }

    @RequestMapping("/appapi00175.{ext}")
    public String appapi00175(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
        Logger log = LoggerUtil.getLogger();
        form.setBusinName("新契税完税信息接口");
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        log.info("AAAAAAAAAAAAAAAAAAAAAAAAA--zhangchen--AAAAAAAAAAAAAAAAAAA");
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil
                .getStringParams(form)));
        ApiUserContext.getInstance();
        request.setAttribute("centerId", form.getCenterId());//获取中心机构码
        //channel 10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博
        if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
            String rep=msgSendApi001Service.send(request, response);//发送去YF，接收返回数据
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
            response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
            log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }else
        {
            String rep=msgSendApi001Service.send(request, response);
            response.getOutputStream().write(change(rep).getBytes("UTF-8"));
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }
    }

    private static String change(String input) {
        String output = null;
        try {
            output = new String(input.getBytes("iso-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    }
}
