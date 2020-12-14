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

/**
 * @author 俞文杰
 * @version V1.0
 * @date 2020/9/9 15:51
 */

@Controller
public class AppApi060Controller {
    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }

    /**
    * 功能描述：<br>提前还贷获取利息信息
    * <>
    * @Param: [form, modelMap, request, response]
    * @Return: java.lang.String
    * @Author: win
    * @Date: 2020/9/9 15:54
    */
    @RequestMapping("/appapi00261.{ext}")
    public String appapi00261(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
        Logger log = LoggerUtil.getLogger();
        log.debug("进入指定appapi00261接口");
        log.info("进入指定appapi00261接口");
        log.info("获取指定261userId" + form.getUserId());
        form.setBusinName("提前还贷获取利息查询");
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
        ApiUserContext.getInstance();
        request.setAttribute("centerId", form.getCenterId());
        log.info("获取指定261userId" + form.getUserId());
        log.info("获取指定261channel" + form.getChannel());
        if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
            log.info("进入指定appapi00261接口if");
            String rep=msgSendApi001Service.send(request, response);
            System.out.println(rep);
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
            response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
            log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }else
        {
            log.info("进入指定appapi00261接口else");
            String rep=msgSendApi001Service.send(request, response);
            response.getOutputStream().write(rep.getBytes("UTF-8"));
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }
    }
    /**
    * 功能描述：<br>自助机终端提前还贷
    * <>
    * @Param: [form, modelMap, request, response]
    * @Return: java.lang.String
    * @Author: win
    * @Date: 2020/9/10 10:12
    */
    @RequestMapping("/appapi00262.{ext}")
    public String appapi00262(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
        Logger log = LoggerUtil.getLogger();
        form.setBusinName("自助机终端提前还贷");
        log.info("进入指定appapi00262接口");
        log.info("获取指定262userId" + form.getUserId());
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
        ApiUserContext.getInstance();
        request.setAttribute("centerId", form.getCenterId());
        log.info("获取指定262userId" + form.getUserId());
        log.info("获取指定262channel" + form.getChannel());
        if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
            log.info("进入指定appapi00262接口if");
            String rep=msgSendApi001Service.send(request, response);
            System.out.println(rep);
            log.info("rep="+rep);
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
            response.getOutputStream().write(aes.encrypt(change(rep).getBytes("UTF-8")).getBytes("UTF-8"));
            log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }else
        {
            log.info("进入指定appapi00262接口else");
            String rep=msgSendApi001Service.send(request, response);
            response.getOutputStream().write(rep.getBytes("UTF-8"));
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
