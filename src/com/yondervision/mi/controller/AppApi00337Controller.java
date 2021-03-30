package com.yondervision.mi.controller;

import com.aspose.words.*;
import com.yd.util.FileUtil;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00225Form;
import com.yondervision.mi.form.AppApi00226Form;
import com.yondervision.mi.form.AppApi030Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.ConvertUpMoneyUtil;
import com.yondervision.mi.util.DigitalSealService;
import com.yondervision.mi.util.ReadProperty;
import com.yondervision.mi.util.security.AES;
import com.yondervision.mi.util.security.Base64Decoder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.ERRCODE.LOG;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AppApi00337Controller {

    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }
    //date : 2020-11-25
    private static String TEMP_PATH = ReadProperty.getString("temp_path");

    //date: 2020-11-25
    private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");

    private static String TEMPLATE_PATH = ReadProperty.getString("template_path");

    @RequestMapping("/appapi00337.{ext}")
    public String appapi00337(AppApi00226Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("appapi00337--异地贷款缴存使用证明打印");
        Logger log = LoggerUtil.getLogger();
        log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
        long starTime = System.currentTimeMillis();
        log.info("appapi00337--form.getAccnum()======" + form.getAccnum());
        log.info("appapi00337--form.getCertinum()======" + form.getCertinum());
        log.info("appapi00337--form.getCentername()======" + form.getCentername());
        log.info("appapi00337--form.getUserid()======" + form.getUserid());
        log.info("appapi00337--form.getBrccode()======" + form.getBrccode());
        log.info("appapi00337--form.getChannel()======" + form.getChannel());
        log.info("appapi00337--form.getProjectname()======" + form.getProjectname());
        log.info("appapi00337--form.getCenterId()======" + form.getCenterId());
        log.info("appapi00337--form.getBuzType()======" + form.getBuzType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        log.info("");
        ApiUserContext.getInstance();

        // "accnum=0076458492&certinum=330903199308240613&centername=宁波市住房公积金管理中心&userid=9990&brccode=05740008&channel=10&projectname=异地缴存证明");

        request.setAttribute("centerId", form.getCenterId()); //date : 2020-11-25
        request.setAttribute("accnum",form.getAccnum());
        request.setAttribute("seqno", "1");
        request.setAttribute("projectname", form.getProjectname());
        request.setAttribute("qdapprnum", "YTJ" + UUID.randomUUID());
        request.setAttribute("transdate", dateFormat.format(new Date()));
        request.setAttribute("certinum", form.getCertinum());
        request.setAttribute("userId", "9990");
        request.setAttribute("buzType", form.getBuzType());
        request.setAttribute("channel", form.getChannel());

        log.info("请求参数centerId的值为: " + request.getAttribute("centerId"));
        log.info("请求参数accnum的值为: " + request.getAttribute("accnum"));
        log.info("请求参数seqno的值为: " + request.getAttribute("seqno"));
        log.info("请求参数projectname的值为: " + request.getAttribute("projectname"));
        log.info("请求参数qdapprnum的值为: " + request.getAttribute("qdapprnum"));
        log.info("请求参数transdate的值为: " + request.getAttribute("transdate"));
        log.info("请求参数certinum的值为: " + request.getAttribute("certinum"));
        log.info("请求参数userId的值为: " + request.getAttribute("userId"));
        log.info("请求参数buzType的值为: " + request.getAttribute("buzType"));

        log.info("appapi00337---获取请求路径: "  + request.getRequestURI());
        log.info("appapi00337---调用同公积金中心通信接口开始。begin");

        /*log.info("验证appapi0227数据结果");
        log.info("请求参数\"transdate\"的值为: " + form.getTransdate());
        log.info("请求参数\"loancontrnum\"的值为: " + form.getLoancontrnum());
        log.info("请求参数\"indiaccstate\"的值为: " + form.getIndiaccstate());
        log.info("请求参数\"addr\"的值为: " + form.getAddr());
        log.info("请求参数\"accnum\"的值为: " + form.getAccnum());
        log.info("请求参数\"unitaccname\"的值为: " + form.getUnitaccname());
        log.info("请求参数\"year\"的值为: " + form.getYear());
        log.info("请求参数\"certinum\"的值为: " + form.getCertinum());
        log.info("请求参数\"unitprop\"的值为: " + form.getUnitprop());

        log.info("请求参数\"bal\"的值为: " + form.getBal());
        log.info("请求参数\"linkphone\"的值为: " + form.getLinkman());
        log.info("请求参数\"indiprop\"的值为: " + form.getIndiprop());
        log.info("请求参数\"projectname\"的值为: " + form.getProjectname());

        log.info("请求参数\"amt\"的值为: " + form.getAmt());
        log.info("请求参数\"flag\"的值为: " + form.getFlag());
        log.info("请求参数\"month\"的值为: " + form.getMonth());
        log.info("请求参数\"month1\"的值为: " + form.getMonth1());
        log.info("请求参数\"opnaccdate\"的值为: " + form.getOpnaccdate());
        log.info("请求参数\"linkman\"的值为: " + form.getLinkman());
        log.info("请求参数\"indipaysum\"的值为: " + form.getIndipaysum());
        log.info("请求参数\"year1\"的值为: " + form.getYear1());
        log.info("请求参数\"basenum\"的值为: " + form.getBasenum());

        log.info("appapi00337------参数查询失败");*/

        //本地YB调appapi03824接口
        ModelMap modelMap = new ModelMap();
        AppApi030Form formk = new AppApi030Form();
        formk.setCenterId(form.getCenterId());
        formk.setAccnum(form.getAccnum());
        formk.setSeqno("1");
        formk.setProjectname(form.getProjectname());
        formk.setQdapprnum("YTJ" + UUID.randomUUID());
        formk.setTransdate(dateFormat.format(new Date()));
        formk.setCertinum(form.getCertinum());
        formk.setUserId(form.getUserId());
        formk.setBuzType(form.getBuzType());

        //formk.setChannel("40");
        AppApi038Contorller apppApi038Contorller = new AppApi038Contorller();
        String rep = apppApi038Contorller.appapi03824(formk,modelMap,request,response);
        System.out.println("03824查询结果："+rep);
        JSONObject json1 = JSONObject.fromObject(rep);
        log.info("json1.toString()==========" + json1.toString());
        String accname = json1.get("accname").toString();
        form.setAccname(accname);

        String indiaccstate = json1.get("indiaccstate").toString();
        form.setIndiaccstate(indiaccstate);

        String addr = json1.get("addr").toString();
        form.setAddr(addr);

        String accnum1 = json1.get("accnum").toString();
        form.setAccnum(accnum1);

        String unitaccname1 = json1.get("unitaccname").toString();
        form.setUnitaccname(unitaccname1);

        String year = json1.get("year").toString();
        form.setYear(year);

        String certinum2 = json1.get("certinum").toString();
        form.setCertinum(certinum2);

        String unitprop = json1.get("unitprop").toString();
        form.setUnitprop(unitprop);

        String recode1 = json1.get("recode").toString();
        form.setRecode(recode1);

        String bal = json1.get("bal").toString();
        form.setBal(bal);

        String linkphone = json1.get("linkphone").toString();
        form.setLinkphone(linkphone);

        String indiprop = json1.get("indiprop").toString();
        form.setIndiprop(indiprop);

        String projectname = json1.get("projectname").toString();
        form.setProjectname(projectname);

        String amt = json1.get("amt").toString();
        form.setAmt(amt);

        String flag = json1.get("flag").toString();
        form.setFlag(flag);

        String month1 = json1.get("month1").toString();
        form.setMonth1(month1);

        String month = json1.get("month").toString();
        form.setMonth(month);

        String opnaccdate = json1.get("opnaccdate").toString();
        form.setOpnaccdate(opnaccdate);

        String linkman = json1.get("linkman").toString();
        form.setLinkman(linkman);

        String indipaysum = json1.get("indipaysum").toString();
        form.setIndipaysum(indipaysum);

        String year1 = json1.get("year1").toString();
        form.setYear1(year1);

        //开始打印
        String templateFile = TEMP_PATH + "temp.doc";//模板文件名 date : 2020-11-25
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("appapi00337--请求耗时" + Time + "毫秒");
        String recode = form.getRecode();
        log.info("appapi00337----------- recode字符串的值为: " + recode);
        //JSONObject json = JSONObject.fromObject(request);
        JSONObject json = new JSONObject();

        log.info("appai00337--- json值為: " + json.toString());
       /* if (recode.equals("999999")){
            log.info("appapi00337----recode字段的值为：999999" );
            json.put("recode","999999");
            String errMsg =  json.get("errMsg").toString();
            json.put("errMsg",new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
            log.info("errMsg1===" + new String(errMsg.getBytes("iso-8859-1"),"utf-8"));
            log.info("json.toString()===" + json.toString());
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
            return json.toString();
        }else{*/
            log.info("appapi00337---存放pdf路径");
            Map<String, String> resultMap = new HashMap<String, String>();
        log.info("请求参数\"accinstcode\"的值为: " + form.getTransdate());
        resultMap.put("Accinstcode",  form.getCentername()+": \r" +
                "                   我中心缴存职工的住房公积金缴存使用情况如下。");
        resultMap.put("accname", form.getAccname());
        //表格固定值
        /*resultMap.put("zgxm", "职工姓名");
        resultMap.put("sfzh", "身份证号");
        resultMap.put("dwmc", "单位名称");
        resultMap.put("grgjjzh", "个人公积金账号");*/
        //+++++++++++++++++++++++++++++++++++++++++++++++
            log.info("请求参数\"transdate\"的值为: " + form.getTransdate());
            resultMap.put("loancontrnum", form.getLoancontrnum());
            log.info("resultMap请求编号参数\"loancontrnum\"的值为: " + form.getLoancontrnum());
            resultMap.put("indiaccstate", form.getIndiaccstate());
        log.info("resultMap请求参数账户状态\"indiaccstate\"的值为: " + form.getIndiaccstate());
            resultMap.put("addr", form.getAddr());
        log.info("resultMap请求参数公积金贷款城市\"addr\"的值为: " + form.getAddr());
            resultMap.put("accnum", form.getAccnum());
        log.info("resultMap请求个人公积金账号参数\"accnum\"的值为: " + form.getAccnum());
        String unitaccname = form.getUnitaccname();
            resultMap.put("unitaccname", unitaccname);
        log.info("resultMap请求单位名称参数\"unitaccname\"的值为: " + form.getUnitaccname());
            resultMap.put("year", form.getYear());
        log.info("resultMap请求参数\"year\"的值为: " + form.getYear());
            resultMap.put("certinum", form.getCertinum());
        log.info("resultMap请求身份证号参数\"certinum\"的值为: " + form.getCertinum());
        String unitp = getNumber(form.getUnitprop());
        resultMap.put("unitprop", "单位：  "+unitp+"%");
        log.info("resultMap请求参数\"unitprop\"的值为: " + form.getUnitprop());
        String money2 =  form.getBal();
        String my2 = ConvertUpMoneyUtil.toChinese(money2);
            resultMap.put("bal", my2);
        log.info("resultMap请求缴存余额参数\"bal\"的值为: " + form.getBal());
            resultMap.put("linkphone", form.getLinkphone());
        log.info("resultMap请求参数\"linkphone\"的值为: " + form.getLinkphone());
        String indip = getNumber(form.getIndiprop());
        resultMap.put("indiprop", "单位：  "+indip+"%");
        log.info("resultMap请求参数\"indiprop\"的值为: " + form.getIndiprop());
            resultMap.put("projectname", form.getProjectname());
        log.info("resultMap请求参数\"projectname\"的值为: " + form.getProjectname());
        String money3 =  form.getAmt();
        String my3 = ConvertUpMoneyUtil.toChinese(money3);
            resultMap.put("amt", my3);
        log.info("resultMap请求参数\"amt\"的值为: " + form.getAmt());
            resultMap.put("flag", form.getFlag());
        log.info("resultMap请求参数\"flag\"的值为: " + form.getFlag());
            resultMap.put("month", form.getMonth());
        log.info("resultMap请求参数\"month\"的值为: " + form.getMonth());
            resultMap.put("opnaccdate", form.getOpnaccdate());
        log.info("resultMap请求参数\"opnaccdate\"的值为: " + form.getOpnaccdate());
            resultMap.put("linkman", form.getLinkman());
        log.info("resultMap请求参数\"linkman\"的值为: " + form.getLinkman());
        String money1 = form.getIndipaysum();
        String my1 = ConvertUpMoneyUtil.toChinese(money1);
        resultMap.put("indipaysum", my1);
        log.info("resultMap请求参数\"indipaysum\"的值为: " + form.getIndipaysum());
            resultMap.put("year1", form.getYear1());
        log.info("resultMap请求参数\"year1\"的值为: " + form.getYear1());
        String money = form.getBasenum();
        String my = ConvertUpMoneyUtil.toChinese(money);
        resultMap.put("basenum", my);
        log.info("resultMap请求参数\"basenum\"的值为: " + form.getBasenum());

            resultMap.put("time", form.getYear() + "   年   " + form.getMonth() + "   月" + "      —      " +
                    form.getYear1() + "   年   " + form.getMonth1() + "   月");
        log.info("resultMap请求参数\"time\"的值为: " + resultMap.get("time"));

            if(StringUtils.equalsIgnoreCase("0", form.getIndiaccstate())){
                resultMap.put("indiaccstate","■    正常       □    封存\r" + "□    欠款       □    其他" );
            } else if(StringUtils.equalsIgnoreCase("1",form.getIndiaccstate())){
                resultMap.put("indiaccstate","□    正常       ■    封存\r" + "□    欠款       □    其他" );
            }else if(StringUtils.equalsIgnoreCase("2",form.getIndiaccstate())){
                resultMap.put("indiaccstate","□    正常       ■    封存\r" + "□    欠款       □    其他" );
            }else{
                resultMap.put("indiaccstate","□    正常       □    封存\r" + "□    欠款       ■    其他" );
            }

        log.info("resultMap请求参数\"ndiaccstate\"的值为: " + resultMap.get("ndiaccstate"));

            if(StringUtils.equalsIgnoreCase("0",form.getFlag())){
                resultMap.put("flag","   ■无贷款记录        □仅有一次贷款记录且贷款已还清\r" +
                        "   □有两次以上(含两次)贷款记录且贷款已还清");
            } else if(StringUtils.equalsIgnoreCase("1",form.getFlag())){
                resultMap.put("flag","   □无贷款记录        ■仅有一次贷款记录且贷款已还清\r" +
                        "   □有两次以上(含两次)贷款记录且贷款已还清" );
            } else {
                resultMap.put("flag","   □无贷款记录        □仅有一次贷款记录且贷款已还清\r" +
                        "   ■有两次以上(含两次)贷款记录且贷款已还清");
            }
        log.info("resultMap请求参数\"flag\"的值为: " + resultMap.get("flag"));
            resultMap.put("linkman", "   我中心保证以上信息真实准确。 " +
                    "                 本证明自开具之日起，2个月内有效。\r " + " \r" +
                    "       单位经办人： " + form.getLinkman() + "       联系电话：" + form.getLinkphone() +
                    "       单位公章（或业务专用章）：\r" +
                    "       " + form.getTransdate());


        /*resultMap.put("huizhiTemp", "回执                              编号" +
                "    你中心缴存职工（公积金账号：             ）缴存使用证明收悉。根据我" +
                "中心异地贷款政策规定，该职工（□ 本人  □参贷）异地贷款申请审核结果为：  □准予贷款    □不予贷款");

        resultMap.put("repayment", "   □等额本金 \n" +
                "   □等额本息 \n" +
                "   □其他 \n");*/
        log.info("resultMap请求参数\"linkman\"的值为: " + resultMap.get("linkman"));
            String saleContractNo = dateFormat.format(new Date());
            //文件路径 @date : 2020-11-25 eg: /ispshare/ftpdir/身份证号
            //String filePath = saleContractPDFfilepath + form.getCertinum();
        log.info("appapi00337----开始替换并生成doc文件");
        log.info("appapi00337----生成doc文件路径: " + templateFile.split(".doc"));


            //searchAndReplace(templateFile, templateFile.split(".doc") + "_" +saleContractNo + form.getCertinum() +".doc", resultMap);
        /*searchAndReplace(templateFile, TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc", resultMap);
        log.info("appapi00337----模板文件转换pdf");
        //FileUtil.wordToPdf(new File(templateFile), new File( TEMP_PATH + form.getCertinum() +saleContractNo + "123456.pdf"));
        log.info("appapi00337----模板文件转换pdf完成");
        log.info("appapi00337----完成替换并生成doc文件");
            File tempFile = new File(TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc");

            log.info("appapi00337------ 替换生成doc1文件的文件路径为: " + TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc");
        log.info("appapi00337------ 替换生成doc2文件的文件路径为: " + tempFile.getAbsoluteFile().getPath());
        log.info("appapi00337----doc文件转PDF文件--01");
            File pdfFile = FileUtil.wordToPdf(tempFile, new File( TEMP_PATH + form.getCertinum() +saleContractNo + ".pdf"));*/
        doc2pdf(templateFile, TEMP_PATH + form.getCertinum() +saleContractNo + ".pdf", resultMap);
        File pdfFile = new File( TEMP_PATH + form.getCertinum() +saleContractNo + ".pdf");

            //  doc文件转pdf
        log.info("appapi00337----doc文件转PDF文件--02");
            //FileUtil.wordToPdf(tempFile, pdfFile);
        log.info("appapi00337----doc文件转PDF文件--完成");
            String fileName = form.getCertinum() + "_" + saleContractNo;
            log.info("appapi00337---文件fileName===========" + fileName);
            System.out.println("appapi00337---生成pdf文件结束");
            json.remove("fileByte");
            if (pdfFile == null) {
                log.error("生成的PDF文件为空！");
                json.remove("voucherData");
                json.put("returnCode", "1");
                json.put("message", "凭证文件生成异常！");
                response.getOutputStream().write(json.toString().getBytes("UTF-8"));
                return "/index";
            }
            json.remove("voucherData");
            String pdftempUrl = TEMP_PATH + pdfFile.getName();//盖章前pdf存放位置
        log.info("appapi00337---盖章前pdf存放位置===========" + pdftempUrl);
            String pdfUrl = signed_pdf_path + pdfFile.getName();//盖章后pdf存放位置
        log.info("appapi00337---盖章后pdf存放位置===========" + pdfUrl);
            String errmassage = DigitalSealService.digitalSealByKey("单位公章（或业务专用章）：",pdftempUrl,pdfUrl);
        log.info("appapi00337---pdf盖章状态===========" + errmassage);
            if (errmassage.equals("成功")){
                File file = new File(pdfUrl);
                String name = pdfFile.getName().split(".pdf")[0];
                String PDFToBase64 = PDFToBase64(file);//对pdf文件进行Base64加密
                log.info("appapi00337---对pdf文件进行Base64加密===========");
                contentToTxt(TEMP_PATH + name + ".txt",PDFToBase64);
                //json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
                json.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
                json.put("filename",pdfFile.getName()+".txt");
                response.getOutputStream().write(json.toString().getBytes("UTF-8"));
                log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
            }else{
                json.put("errmassage",errmassage);
                response.getOutputStream().write(json.toString().getBytes("UTF-8"));
            }
            return "/index";
        //}

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


    public static void searchAndReplace(String srcPath, String destPath, Map<String, String> map) {
        try {
            Logger log = LoggerUtil.getLogger();

            log.info("appapi00337---searchAndReplace----替换模板文件字段内容");
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                Set<String> set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<XWPFRun> run = paragraph.getRuns();
                    for (int i = 0; i < run.size(); i++) {
                        if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
                                run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
                            run.get(i).setText(map.get(key), 0);
                            //run.get(i).setTextPosition(10);
                        }
                    }
                }
                log.info("appapi00337---searchAndReplace----替换模板文件字段内容完成");
            }

            /**
             * 单元格
             */
            log.info("appapi00337---searchAndReplace----替换模板文件单元格字段内容");
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();
                for (int i = 0; i < count; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        for (Map.Entry<String, String> e : map.entrySet()) {
                            if (e.getKey().equals(cell.getText())) {
                                if (e.getValue().indexOf("\n") != -1) {
                                    //删除原单元格值
                                    cell.removeParagraph(0);
                                    XWPFParagraph xwpfParagraph1 = cell.addParagraph();
                                    String[] split = e.getValue().split("\n");
                                    xwpfParagraph1.setAlignment(ParagraphAlignment.CENTER);//需要设置，否则中文换行会很生硬很难看
                                    for (String s : split) {
                                        XWPFRun run = xwpfParagraph1.createRun();//对某个段落设置格式
                                        run.setText(s);

                                    }
                                } else {
                                    //删除原数据
                                    cell.removeParagraph(0);
                                    //填充新数据
                                    cell.setText(e.getValue());
                                    CTTc cttc = cell.getCTTc();
                                    CTP ctp = cttc.getPList().get(0);
                                    CTPPr ctppr = ctp.getPPr();
                                    if (ctppr == null) {
                                        ctppr = ctp.addNewPPr();
                                    }
                                    CTJc ctjc = ctppr.getJc();
                                    if (ctjc == null) {
                                        ctjc = ctppr.addNewJc();
                                    }
                                    ctjc.setVal(STJc.CENTER); //水平居中
                                }
                            }
                        }
                    }
                }
            }
            log.info("appapi00337---searchAndReplace----替换模板文件单元字段内容完成");
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean getLicense() {
        boolean result = false;
        License aposeLic = new License();
        StringBuffer strlic = new StringBuffer();
        strlic.append("<License>");
        strlic.append("  <Data>");
        strlic.append("    <Products>");
        strlic.append("      <Product>Aspose.Total for Java</Product>");
        strlic.append("      <Product>Aspose.Words for Java</Product>");
        strlic.append("    </Products>");
        strlic.append("    <EditionType>Enterprise</EditionType>");
        strlic.append("    <SubscriptionExpiry>20991231</SubscriptionExpiry>");
        strlic.append("    <LicenseExpiry>20991231</LicenseExpiry>");
        strlic.append("    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>");
        strlic.append("  </Data>");
        strlic.append("  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>");
        strlic.append("</License>");

        try {
            aposeLic.setLicense(new ByteArrayInputStream(strlic.toString().getBytes()));
            result = true;
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return result;
    }
    public static boolean doc2pdf(String inPath, String outPath, Map<String, String> map) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }
        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            com.aspose.words.Document doc = new com.aspose.words.Document(inPath);
            if(map.get("loancontrnum") != null && map.get("loancontrnum")!="") {
                doc.getRange().replace(Pattern.compile("loancontrnum"), map.get("loancontrnum"));
            }
            NodeCollection tables = doc.getChildNodes(NodeType.TABLE, true);
            for (Table table : (Iterable<Table>) tables) {
                for (Row row : table.getRows()) {
                    for (Cell cell : row.getCells()) {
                        for (Run run : (Iterable<Run>) cell.getChildNodes(NodeType.RUN, true)) {
                            String text = run.getText();
                            String tempText = map.get(text);
                            if (tempText != null) {
                                text = tempText;
                                run.getFont().setName("Courier New");
                                run.getFont().setSize(10.0);
                            }
                            run.setText(text);
                            boolean hidden = run.getFont().getHidden();
                            if (hidden) {
                                run.setText("");
                            }
                        }
                    }
                }
            }
            doc.save(os, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            // 转化用时
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 创建新文件并写入内容
     * @param filePath,content
     * @return
     */
    public static void contentToTxt(String filePath, String content) {
        Logger logger = LoggerUtil.getLogger();
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
                f =new File(filePath);
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

			/*while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);*/
            input.close();
            s1 += content;
            logger.info("weu_s1======"+s1);

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: 将pdf文件转换为Base64编码
     * @param  file
     * @Author fuyuwei
     * Create Date: 2015年8月3日 下午9:52:30
     */
    public static String PDFToBase64(File file) {
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;
        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fin.close();
                bin.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getNumber(String str) {
        // 需要取整数和小数的字符串
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }

   /* public String appapi03824(AppApi030Form form, ModelMap modelMap, HttpServletRequest request,
                              HttpServletResponse response) throws Exception{
        Logger log = LoggerUtil.getLogger();
        form.setBusinName("职工住房公积金缴存情况证明");
        log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil
                .getStringParams(form)));
        ApiUserContext.getInstance();
        request.setAttribute("centerId", form.getCenterId());
        if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("42")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("91")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("93")||form.getChannel().trim().equals("94")||form.getChannel().trim().equals("95")||form.getChannel().trim().equals("96")){
            log.info("zhangchenw_request:" + request.toString() + "response:" + response.toString());
            String rep=msgSendApi001Service.send(request, response);
            log.info("rep="+rep);
            log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
            AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,form.getAppkey());
            response.getOutputStream().write(aes.encrypt(rep.getBytes("UTF-8")).getBytes("UTF-8"));
            log.info("加密后rep="+aes.encrypt(rep.getBytes("UTF-8")));
            log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }else
        {
            log.info("zhangwang_request:" + request.toString() + "response:" + response.toString());
            String rep=msgSendApi001Service.send(request, response);
            response.getOutputStream().write(rep.getBytes("UTF-8"));
            log.info("rep="+rep);
            log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
            return "/index";
        }
    }*/
}
