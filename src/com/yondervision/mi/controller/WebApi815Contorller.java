package com.yondervision.mi.controller;

import com.aspose.words.*;
import com.yondervision.mi.common.ERRCODE;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00226Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class WebApi815Contorller {
    @Autowired
    private MsgSendApi001Service msgSendApi001Service = null;
    public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
        this.msgSendApi001Service = msgSendApi001Service;
    }
    private static String TEMP_PATH = ReadProperty.getString("temp_path");
    private static String signed_pdf_path = ReadProperty.getString("signed_pdf_path");
    private static String TEMPLATE_PATH = ReadProperty.getString("template_path");

    @RequestMapping("/webapi80015.{ext}")
    public Map<String, String> webapi80015(AppApi00226Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        form.setBusinName("webapi80015--新异地贷款缴存使用证明打印");
        Logger log = LoggerUtil.getLogger();
        log.info(ERRCODE.LOG.START_BUSIN.getLogText(form.getBusinName()));
        long starTime = System.currentTimeMillis();

        log.info("webapi80015--form.getAccnum()======" + form.getAccnum());
        log.info("webapi80015--form.getCertinum()======" + form.getCertinum());
        log.info("webapi80015--form.getCentername()======" + form.getCentername());
        log.info("webapi80015--form.getUserid()======" + form.getUserid());
        log.info("webapi80015--form.getBrccode()======" + form.getBrccode());
        log.info("webapi80015--form.getChannel()======" + form.getChannel());
        log.info("webapi80015--form.getProjectname()======" + form.getProjectname());
        log.info("webapi80015--form.getCenterId()======" + form.getCenterId());
        log.info("webapi80015--form.getBuzType()======" + form.getBuzType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        request.setAttribute("centerId", form.getCenterId()); //date : 2020-11-25
        request.setAttribute("accnum",form.getAccnum());
        String channel1 = form.getChannel();
        if(StringUtils.equalsIgnoreCase("10",channel1)){
            request.setAttribute("seqno", "3");
        }else if(StringUtils.equalsIgnoreCase("50",channel1)){
            request.setAttribute("seqno", "2");
        }
        request.setAttribute("projectname", form.getProjectname());
        request.setAttribute("qdapprnum", "YTJ" + UUID.randomUUID());
        request.setAttribute("transdate", dateFormat.format(new Date()));
        request.setAttribute("certinum", form.getCertinum());
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
            map.put("message", "查询凭证文件数据异常！");
            return map;
        }
        //
        String loancontrnum = json1.get("loancontrnum").toString();
        form.setLoancontrnum(loancontrnum);
        String basenum = json1.get("basenum").toString();
        form.setBasenum(basenum);
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
        System.out.println("webapi80015--请求耗时" + Time + "毫秒");
        String recode = form.getRecode();
        log.info("webapi80015----------- recode字符串的值为: " + recode);

        //填入表格的Map
        Map<String, String> resultMap = new HashMap<String, String>();
        log.info("请求参数\"accinstcode\"的值为: " + form.getTransdate());
        resultMap.put("Accinstcode",  form.getProjectname()+": \r" +
                "                   我中心缴存职工的住房公积金缴存使用情况如下。");
        resultMap.put("accname", form.getAccname());
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
        log.info("resultMap请求参数\"linkman\"的值为: " + resultMap.get("linkman"));
        String saleContractNo = dateFormat.format(new Date());
        log.info("appapi00337----开始替换并生成doc文件");
        log.info("appapi00337----生成doc文件路径: " + templateFile.split(".doc"));

        //  doc文件转pdf
        doc2pdf(templateFile, TEMP_PATH + form.getCertinum() + "-" + saleContractNo + ".pdf", resultMap);
        File pdfFile = new File( TEMP_PATH + form.getCertinum() + "-" + saleContractNo + ".pdf");
        //盖章
        log.info("appapi00337----doc文件转PDF文件--完成");
        String fileName = form.getCertinum() + "_" + saleContractNo;
        log.info("appapi00337---文件fileName===========" + fileName);
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
        log.info("appapi00337---盖章前pdf存放位置===========" + pdftempUrl);
        String pdfUrl = saleContractPDFfilepath + pdfFile.getName();//盖章后pdf存放位置
        log.info("appapi00337---盖章后pdf存放位置===========" + pdfUrl);
        String errmassage = DigitalSealService.digitalSealByKey("单位公章（或业务专用章）：",pdftempUrl,pdfUrl);
        log.info("appapi00337---pdf盖章状态===========" + errmassage);

        if (errmassage.equals("成功")){
            File file = new File(pdfUrl);
            //String name = pdfFile.getName().split(".pdf")[0];
            String name = pdfFile.getName();
            String PDFToBase64 = PDFToBase64(file);//对pdf文件进行Base64加密
            log.info("appapi00337---对pdf文件进行Base64加密===========");
            contentToTxt(saleContractPDFfilepath + name + ".txt",PDFToBase64);
            //json.put("filepath","http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/");
            json1.put("filepath","http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/");
            json1.put("filename",pdfFile.getName()+".txt");
            json1.put("channel1",channel1);
            response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            log.info(ERRCODE.LOG.END_BUSIN.getLogText(form.getBusinName()));
        }else{
            log.info("rep的值为: " + rep);
            log.info("rep1的值为: " + json1.toString());
            json1.put("errmassage",errmassage);
            response.getOutputStream().write(json1.toString().getBytes("UTF-8"));
            //response.getOutputStream().write(rep.getBytes("UTF-8"));
        }
        return map;
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
    public static String getNumber(String str) {
        Pattern  p = Pattern.compile("(\\d+)");
        Matcher  m = p.matcher(str);
        if (m.find()) {
            //如果有整数相匹配
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
            str = "";
        }
        //}
        return str;
    }
}
