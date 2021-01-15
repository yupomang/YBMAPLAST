package com.yondervision.mi.test;

//import com.yd.svrplatform.pdf.Office2PDF;
import com.yd.util.FileUtil;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.util.Office2PdfUtil;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class NEWappapi00227Test {

    public static void wordToPdf(){
        File pdfFile = null;
        //String destPath = "C:/Users/yd/Desktop/test/原始模板/原始模板/";
        String destPath = "C:/Users/yd/Desktop/test/newmodel/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String saleContractNo = dateFormat.format(new Date());
        //File tempFile = new File(destPath + saleContractNo + ".doc");x

        String templateFile = destPath + saleContractNo + ".docx";//模板文件名 date : 2020-11-25
        Map<String, String> resultMap = new HashMap<String, String>();

        resultMap.put("zgxm", "职工姓名");
        resultMap.put("sfzh", "身份证号");
        resultMap.put("dwmc", "单位名称");
        resultMap.put("grgjjzh", "个人公积金账号");
        resultMap.put("accname", "张杰");
        resultMap.put("certinum", "330283198802270016");
        resultMap.put("unitaccname", "宁波易才人力资源咨询有限公司");
        resultMap.put("accnum", "0238557595");

        /*resultMap.put("khsj", "开户时间");
        resultMap.put("zhzt", "账户状态");
        resultMap.put("jcjsdx","缴存基数 \n"+ " （大写）");
        resultMap.put("jcbl", "缴存比例");*/
        searchAndReplace(templateFile, destPath + saleContractNo +"1.doc", resultMap);
        File tempFile = new File(destPath + saleContractNo + "1.doc");
        pdfFile = FileUtil.wordToPdf(tempFile, new File(destPath, saleContractNo + ".pdf"));
        //新方法
        //word转成pdf
        /*Office2PDF pdf = Office2PDF.newInstance();
        pdf.change(tempFile);
        tempFile = pdf.change(tempFile);*/
    }

    public static void searchAndReplace(String srcPath, String destPath, Map<String, String> map) {
        try {
            //Logger log = LoggerUtil.getLogger();

            //log.info("appapi00337---searchAndReplace----替换模板文件字段内容");
            System.out.println("appapi00337---searchAndReplace----替换模板文件字段内容");
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            //Iterator<XWPFTable> itPara = document.getTablesIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                //XWPFTable paragraph = (XWPFTable) itPara.next();
                Set<String> set = map.keySet();
                //paragraph.setVerticalAlignment(TextAlignment.CENTER);
               // paragraph.setCellMargins();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<XWPFRun> run = paragraph.getRuns();
                    //XWPFTableCell cell = null;
                    for (int i = 0; i < run.size(); i++) {
                        if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
                                run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
                            //cell = cells.get(index);
                            run.get(i).setText(map.get(key), 10);
                            //run.get(i).setFontFamily();

                        }
                    }
                }
                //log.info("appapi00337---searchAndReplace----替换模板文件字段内容完成");
                System.out.println("appapi00337---searchAndReplace----替换模板文件字段内容完成");
            }

            /**
             * 单元格
             */
            //log.info("appapi00337---searchAndReplace----替换模板文件单元格字段内容");
            System.out.println("appapi00337---searchAndReplace----替换模板文件单元格字段内容");
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = (XWPFTable) itTable.next();
                int count = table.getNumberOfRows();
                for (int i = 0; i < count; i++) {
                    XWPFTableRow row = table.getRow(i);
                    //List<XWPFTableCell> cells = row.getTableCells();
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        for (Map.Entry<String, String> e : map.entrySet()) {
                            if (e.getKey().equals(cell.getText())) {
                                if (e.getValue().indexOf("\n") != -1) {
                                    //删除原单元格值
                                    cell.removeParagraph(0);
                                    //XWPFParagraph xwpfParagraph1 = cell.addParagraph();
                                    XWPFParagraph xwpfParagraph1 = new XWPFParagraph(cell.getCTTc().addNewP(), cell);
                                    String[] split = e.getValue().split("\n");
                                   // xwpfParagraph1.setAlignment(ParagraphAlignment.LEFT);//需要设置，否则中文换行会很生硬很难看
                                    xwpfParagraph1.setAlignment(ParagraphAlignment.LEFT);
                                   // XWPFParagraph newPara = new XWPFParagraph(cell.getCTTc().addNewP(), cell);
                                    //newPara.setAlignment(ParagraphAlignment.CENTER);
                                    for (String s : split) {
                                        if(s != null) {
                                            XWPFRun run = xwpfParagraph1.createRun();//对某个段落设置格式
                                            run.setText(s);
                                        }
                                    }
                                } else {
                                    //删除原数据
                                    cell.removeParagraph(0);
                                    //cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER); //垂直居中
                                    //cell.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //垂直居中
                                    //填充新数据
                                    cell.setText(e.getValue());
                                    //==================
                                    /*XSSFWorkbook wb = new XSSFWorkbook();
                                    XSSFCellStyle alignStyle = (XSSFCellStyle) wb.createCellStyle();
                                    alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
                                    alignStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中

                                    cell.setCellStyle(alignStyle)*/
                                    //居中修改

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
                                    //cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER); //垂直居中
                                    //cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                                }
                            }
                        }
                    }
                }
            }
            //log.info("appapi00337---searchAndReplace----替换模板文件单元字段内容完成");
            System.out.println("appapi00337---searchAndReplace----替换模板文件单元字段内容完成");
            FileOutputStream outStream = null;
            outStream = new FileOutputStream(destPath);
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        wordToPdf();
    }
}
