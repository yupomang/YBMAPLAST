package com.yondervision.mi.controller;

import com.yd.util.FileUtil;
import com.yondervision.mi.common.ApiUserContext;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00225Form;
import com.yondervision.mi.form.AppApi00226Form;
import com.yondervision.mi.util.DigitalSealService;
import com.yondervision.mi.util.ReadProperty;
import com.yondervision.mi.util.security.Base64Decoder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.ERRCODE.LOG;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppApi00337Controller {

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
        request.setAttribute("seqno", form.getChannel());
        request.setAttribute("projectname", form.getProjectname());
        request.setAttribute("qdapprnum", "YTJ" + UUID.randomUUID());
        request.setAttribute("transdate", dateFormat.format(new Date()));
        request.setAttribute("certinum", form.getCertinum());
        request.setAttribute("userId", form.getUserid());
        request.setAttribute("buzType", form.getBuzType());

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

        log.info("验证appapi0227数据结果");
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

        log.info("appapi00337------参数查询失败");

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
        resultMap.put("accinstcode", form.getCentername() + "宁波公积金中心: \n" +
                "                       我中心缴存职工的住房公积金缴存使用情况如下。");
        resultMap.put("accname", form.getAccnum());
            log.info("请求参数\"transdate\"的值为: " + form.getTransdate());
            resultMap.put("loancontrnum", form.getLoancontrnum());
            log.info("resultMap请求参数\"loancontrnum\"的值为: " + form.getLoancontrnum());
            resultMap.put("indiaccstate", form.getIndiaccstate());
        log.info("resultMap请求参数\"indiaccstate\"的值为: " + form.getIndiaccstate());
            resultMap.put("addr", form.getAddr());
        log.info("resultMap请求参数\"addr\"的值为: " + form.getAddr());
            resultMap.put("accnum", form.getAccnum());
        log.info("resultMap请求参数\"accnum\"的值为: " + form.getAccnum());
            resultMap.put("unitaccname", form.getUnitaccname());
        log.info("resultMap请求参数\"unitaccname\"的值为: " + form.getUnitaccname());
            resultMap.put("year", form.getYear());
        log.info("resultMap请求参数\"year\"的值为: " + form.getYear());
            resultMap.put("certinum", form.getCertinum());
        log.info("resultMap请求参数\"certinum\"的值为: " + form.getCertinum());
            resultMap.put("unitprop", form.getUnitprop());
        log.info("resultMap请求参数\"unitprop\"的值为: " + form.getUnitprop());
            resultMap.put("bal", form.getBal());
        log.info("resultMap请求参数\"bal\"的值为: " + form.getBal());
            resultMap.put("linkphone", form.getLinkphone());
        log.info("resultMap请求参数\"linkphone\"的值为: " + form.getLinkphone());
            resultMap.put("indiprop", form.getIndiprop());
        log.info("resultMap请求参数\"indiprop\"的值为: " + form.getIndiprop());
            resultMap.put("projectname", form.getProjectname());
        log.info("resultMap请求参数\"projectname\"的值为: " + form.getProjectname());
            resultMap.put("amt", form.getAmt());
        log.info("resultMap请求参数\"amt\"的值为: " + form.getAmt());
            resultMap.put("flag", form.getFlag());
        log.info("resultMap请求参数\"flag\"的值为: " + form.getFlag());
            resultMap.put("month", form.getMonth());
        log.info("resultMap请求参数\"month\"的值为: " + form.getMonth());
            resultMap.put("opnaccdate", form.getOpnaccdate());
        log.info("resultMap请求参数\"opnaccdate\"的值为: " + form.getOpnaccdate());
            resultMap.put("linkman", form.getLinkman());
        log.info("resultMap请求参数\"linkman\"的值为: " + form.getLinkman());
            resultMap.put("indipaysum", form.getIndipaysum());
        log.info("resultMap请求参数\"indipaysum\"的值为: " + form.getIndipaysum());
            resultMap.put("year1", form.getYear1());
        log.info("resultMap请求参数\"year1\"的值为: " + form.getYear1());
            resultMap.put("basenum", form.getBasenum());
        log.info("resultMap请求参数\"basenum\"的值为: " + form.getBasenum());

            resultMap.put("time", form.getYear() + "年" + form.getMonth() + "月" + "—" +
                    form.getYear() + "年" + form.getMonth() + "月");
        log.info("resultMap请求参数\"time\"的值为: " + resultMap.get("time"));

            if(StringUtils.equalsIgnoreCase("正常", form.getIndiaccstate())){
                resultMap.put("indiaccstate","□正常  □封存\n" + "□欠款 ■其他" );
            } else if(StringUtils.equalsIgnoreCase("封存",form.getIndiaccstate())){
                resultMap.put("indiaccstate","□正常  ■封存\n" + "□欠款 □其他" );
            }else if(StringUtils.equalsIgnoreCase("欠款",form.getIndiaccstate())){
                resultMap.put("indiaccstate","□正常  □封存\n" + "■欠款 □其他" );
            }else{
                resultMap.put("indiaccstate","□正常  □封存\n" + "□欠款 ■其他" );
            }

        log.info("resultMap请求参数\"ndiaccstate\"的值为: " + resultMap.get("ndiaccstate"));

            if(StringUtils.equalsIgnoreCase("无贷款记录",form.getFlag())){
                resultMap.put("flag","■无贷款记录 □仅有一次贷款记录且贷款已还清\n" +
                        "□有两次以上(含两次)贷款记录且贷款已还清");
            } else if(StringUtils.equalsIgnoreCase("仅有一次贷款记录且贷款已还清",form.getFlag())){
                resultMap.put("flag","□无贷款记录 ■仅有一次贷款记录且贷款已还清\n" +
                        "□有两次以上(含两次)贷款记录且贷款已还清" );
            } else {
                resultMap.put("flag","□无贷款记录 □仅有一次贷款记录且贷款已还清\n" +
                        "■有两次以上(含两次)贷款记录且贷款已还清");
            }
        log.info("resultMap请求参数\"flag\"的值为: " + resultMap.get("flag"));
            resultMap.put("linkman", "   我中心保证以上信息真实准确。 " +
                    "                 本证明自开具之日起，2个月内有效。\n " +
                    "单位经办人： " + form.getLinkman() + "联系电话：" + form.getLinkphone() +
                    "   单位公章（或业务专用章）：\n" +
                    form.getTransdate());


        resultMap.put("huizhiTemp", "回执                              编号" +
                "    你中心缴存职工（公积金账号：             ）缴存使用证明收悉。根据我" +
                "中心异地贷款政策规定，该职工（□ 本人  □参贷）异地贷款申请审核结果为：  □准予贷款    □不予贷款");

        resultMap.put("repayment", "   □等额本金 \n" +
                "   □等额本息 \n" +
                "   □其他 \n");
        log.info("resultMap请求参数\"linkman\"的值为: " + resultMap.get("linkman"));
            String saleContractNo = dateFormat.format(new Date());
            //文件路径 @date : 2020-11-25 eg: /ispshare/ftpdir/身份证号
            //String filePath = saleContractPDFfilepath + form.getCertinum();
        log.info("appapi00337----开始替换并生成doc文件");
        log.info("appapi00337----生成doc文件路径: " + templateFile.split(".doc"));


            //searchAndReplace(templateFile, templateFile.split(".doc") + "_" +saleContractNo + form.getCertinum() +".doc", resultMap);
        searchAndReplace(templateFile, TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc", resultMap);
        log.info("appapi00337----模板文件转换pdf");
        //FileUtil.wordToPdf(new File(templateFile), new File( TEMP_PATH + form.getCertinum() +saleContractNo + "123456.pdf"));
        log.info("appapi00337----模板文件转换pdf完成");
        log.info("appapi00337----完成替换并生成doc文件");
            File tempFile = new File(TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc");

            log.info("appapi00337------ 替换生成doc1文件的文件路径为: " + TEMP_PATH + saleContractNo + "_" +form.getCertinum() +".doc");
        log.info("appapi00337------ 替换生成doc2文件的文件路径为: " + tempFile.getAbsoluteFile().getPath());
        log.info("appapi00337----doc文件转PDF文件--01");
            File pdfFile = FileUtil.wordToPdf(tempFile, new File( TEMP_PATH + form.getCertinum() +saleContractNo + ".pdf"));

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
                                    xwpfParagraph1.setAlignment(ParagraphAlignment.LEFT);//需要设置，否则中文换行会很生硬很难看
                                    for (String s : split) {
                                        XWPFRun run = xwpfParagraph1.createRun();//对某个段落设置格式
                                        run.setText(s);

                                    }
                                } else {
                                    //删除原数据
                                    cell.removeParagraph(0);
                                    //填充新数据
                                    cell.setText(e.getValue());
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


}
