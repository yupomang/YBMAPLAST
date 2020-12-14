package com.yondervision.mi.controller;

import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.yondervision.mi.form.AppApi50001Form;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class AppApi151Contorller {

    Logger log = Logger.getLogger("YBMAP");
    @RequestMapping("/appapi00239.{ext}")
    public String appapi00239 (AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        form.setBusinName("最多跑一次公务员审批信息接口");
        log.info("BBBBBBBBBBBBB++BB----AAAAAAAAAAAAAAAAAAA");
        log.info("api/appapi00239 begin.");

        long starTime=System.currentTimeMillis();
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);

        //读取配置文件，连接钉钉接口
        String api = "/bpms/openapi/procInst/executeInstTodo.json";
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //PostClient postClient = ExecutableClient.getInstance().newPostClient(api);
        ExecutableClient executableClient = (ExecutableClient) app.getBean("executableClient");

        //log.info("executableClient="+executableClient.getUri());
        log.info("appapi00239推送参数："+form.getProcInsId()+"+++++"+form.getLoginWorkNo()+"+++++"+form.getAction()+"+++++"+form.getRemark());
        GetClient getClient =
                executableClient.getInstance().newGetClient(api);
        //加参数
        String procInsId = form.getProcInsId();
        log.info("form.getProcInsId()="+form.getProcInsId());
        getClient.addParameter("procInsId", procInsId);
        String loginWorkNo = form.getLoginWorkNo();
        log.info("form.getLoginWorkNo()="+form.getLoginWorkNo());
        getClient.addParameter("loginWorkNo", loginWorkNo);
        String action = form.getAction();
        log.info("form.getAction()="+form.getAction());
        getClient.addParameter("action", action);
        String remark = form.getRemark();
        log.info("form.getRemark()="+form.getRemark());
        getClient.addParameter("remark", remark);
        String apiResult = getClient.get();

        System.out.println(apiResult);
        form.setWorkSheetLogVo(apiResult);

        log.info("最多跑一次公务员审批信息接口"+apiResult.replace("\\\"", "\""));

        return apiResult;


    }
}
