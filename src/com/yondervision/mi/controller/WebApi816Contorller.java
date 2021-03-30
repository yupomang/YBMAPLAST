package com.yondervision.mi.controller;

import com.aspose.words.*;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00239Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.DigitalSealService;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.ReadProperty;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class WebApi816Contorller {
    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }

    private static String TEMP_PATH = ReadProperty.getString("temp_path");
    private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");
    private static String TEMPLATE_PATH = ReadProperty.getString("template_path");

    @RequestMapping("/webapi80016.{ext}")
    public Map<String, String> webapi80016(AppApi00239Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("webapi80016--公积金缴存明细查询打印");
        Logger log = LoggerUtil.getLogger();
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        long starTime = System.currentTimeMillis();
        log.info("webapi80016--form.getAccnum()======" + form.getAccnum());
        log.info("webapi80016--form.getSpt_indiaccstatename()======" + form.getSpt_indiaccstatename());
        log.info("webapi80016--form.getBasenum()======" + form.getBasenum());
        log.info("webapi80016--form.getBal()======" + form.getBal());
        log.info("webapi80016--form.getBal1()======" + form.getBal1());
        log.info("webapi80016--form.getIndipaysum()======" + form.getIndipaysum());
        log.info("webapi80016--form.getLpaym()======" + form.getLpaym());
        log.info("webapi80016--form.getOpnaccdate()======" + form.getOpnaccdate());
        log.info("webapi80016--form.getUnitaccname()======" + form.getUnitaccname());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("centerId", form.getCenterId()); //date : 2020-11-25
        request.setAttribute("seqno", "3");
        request.setAttribute("userId", "9990");
        request.setAttribute("buzType", form.getBuzType());
        request.setAttribute("channel", form.getChannel());
        request.setAttribute("year1", "3");

        form.setYear1("3");
        log.debug("yaer1========"+form.getYear1());
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
       /* //开始打印
        String templateFile = TEMP_PATH + "grzhcxtemp.doc";
        //填入表格的Map
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("instcode", form.getInstcode());
        resultMap.put("accname", form.getAccname());
        resultMap.put("buzType", form.getBuzType());
        resultMap.put("certinum", form.getCertinum());
        resultMap.put("linkphone", form.getLinkphone());

        resultMap.put("accnum", form.getAccnum());
        resultMap.put("spt_indiaccstatename", form.getSpt_indiaccstatename());
        resultMap.put("basenum", form.getBasenum());
        resultMap.put("bal", form.getBal());
        resultMap.put("bal1", form.getBal1());
        resultMap.put("bal2", form.getBal2());
        resultMap.put("bal3", form.getBal3());
        resultMap.put("bal4", form.getBal4());
        resultMap.put("indipaysum", form.getIndipaysum());
        resultMap.put("lpaym", form.getLpaym());
        resultMap.put("opnaccdate", form.getOpnaccdate());
        resultMap.put("spt_unitaccname", form.getSpt_unitaccname());
        resultMap.put("linkman", "   我中心保证以上信息真实准确。 " +
                "                 本证明自开具之日起，2个月内有效。\r " + " \r" +
                "       " + form.getTransdate()+"                          单位公章（或业务专用章）：\r" );


        String saleContractNo = dateFormat.format(new Date());
        log.info("appapi00337----生成doc文件路径: " + templateFile.split(".doc"));
        //Map<String, String> map = new HashMap<String, String>();
        //  doc文件转pdf
        doc2pdf(templateFile, TEMP_PATH + form.getCertinum() + "-" + saleContractNo + "-grzhjbxx.pdf", resultMap);
        File pdfFile = new File( TEMP_PATH + form.getCertinum() + "-" + saleContractNo + "-grzhjbxx.pdf");
        //盖章
        log.info("webapi80016----doc文件转PDF文件--完成");
        String fileName = form.getCertinum() + "_" + saleContractNo+"-grzhjbxx";
        log.info("webapi80016---文件fileName===========" + fileName);
        System.out.println("appapi00337---生成pdf文件结束");
        if (pdfFile == null) {
            log.error("生成的PDF文件为空！");
            map.put("returnCode", "1");
            map.put("message", "凭证文件生成异常！");
            response.getOutputStream().write(map.toString().getBytes("UTF-8"));
            return map;
        }
        String saleContractPDFfilepath = PropertiesReader.getProperty("properties.properties", "salePDFfilepath");
        String pdftempUrl = TEMP_PATH + pdfFile.getName();//盖章前pdf存放位置
        log.info("webapi80016---盖章前pdf存放位置===========" + pdftempUrl);
        String pdfUrl = saleContractPDFfilepath + pdfFile.getName();//盖章后pdf存放位置
        log.info("webapi80016---盖章后pdf存放位置===========" + pdfUrl);
        String errmassage = DigitalSealService.digitalSealByKey("单位公章（或业务专用章）：",pdftempUrl,pdfUrl);
        log.info("webapi80016---pdf盖章状态===========" + errmassage);

        //JSONObject json1 = new JSONObject();
        //String rep = "{\"msg\":\"成功\",\"recode\":\"000000\"}";
        //request.setAttribute("centerId", form.getCenterId());
        //String rep=msgSendApi001Service.send(request, response);
        //log.debug("rep========"+rep);

        //JSONObject json1 = JSONObject.fromObject(rep);
        if (errmassage.equals("成功")){
            File file = new File(pdfUrl);
            //String name = pdfFile.getName().split(".pdf")[0];
            String name = pdfFile.getName();
            String PDFToBase64 = PDFToBase64(file);//对pdf文件进行Base64加密
            log.info("webapi80016---对pdf文件进行Base64加密===========");
            contentToTxt(saleContractPDFfilepath + name + ".txt",PDFToBase64);
            //json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
            json1.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
            json1.put("filename",pdfFile.getName()+".txt");
            //json1.put("channel1",channel1);
            log.info("webapi80016---json1==========="+json1.toString());
            response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
        }else{
            //log.info("rep的值为: " + rep);
            log.info("rep1的值为: " + json1.toString());
            json1.put("errmassage",errmassage);
            response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            //response.getOutputStream().write(rep.getBytes("UTF-8"));
        }

        //请求YF查询数据
        log.debug(ERRCODE.DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));
        Thread.sleep(500);
        request.setAttribute("seqno", "1");
        form.setYear1("1");
        log.debug("yaer1========"+form.getYear1());
        //request.setAttribute("spt_ywid", form.getYear1());
        log.info("spt_ywid==========" + form.getSpt_ywid());
        request.setAttribute("centerId", form.getCenterId());
        String rep1=msgSendApi001Service.send(request, response);
        //log.debug("rep========"+rep);
        //JSONObject json1 = JSONObject.fromObject(rep);
        log.info("json1.toString()==========" + json1.toString());
        //Map<String, String> map = new HashMap<String, String>();
        if(json1.get("recode").equals("999999")||json1.get("recode").equals("E90063")){
            log.info("rep的值为: " + rep1);
            //response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            map.put("message", "推送文件名和路径失败！");
            return map;
        }*/
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("webapi80016--请求耗时" + Time + "毫秒");
        return map;
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

}
