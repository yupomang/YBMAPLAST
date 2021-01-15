package com.yondervision.mi.test.zizhuzhongduan;

import com.yd.util.FileUtil;
import com.yondervision.mi.util.ReadProperty;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Newappapi00337test {

    public static void wordToPdf(){
        File pdfFile = null;
        String destPath = "C:/Users/yd/Desktop/test/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String saleContractNo = dateFormat.format(new Date());

        String templateFile = destPath + saleContractNo + ".doc";//模板文件名 date : 2020-11-25
        Map<String, String> resultMap = new HashMap<String, String>();
        //resultMap.put("zgxm", "职工姓名");
        //resultMap.put("sfzh", "身份证号");
        //resultMap.put("dwmc", "单位名称");
        //resultMap.put("grgjjzh", "个人公积金账号");
       /* resultMap.put("accname", "张杰");
        resultMap.put("certinum", "330283198802270016");
        resultMap.put("unitaccname", "宁波易才人力资源咨询有限公司");
        resultMap.put("accnum", "0238557595");*/

        //填充数据，转pdf
        //String templatePath = ReadProperty.getString("template_path");
        //String voucherData = "[{\"accname\":\"张杰\",\"certinum\":\"330283198802270016\"}]";
        String voucherData = "[{\"table1\":[\"张杰~330283198802270016\"]}]";
        File docFile = FileUtil.createWord(templateFile, new File(destPath + saleContractNo + "1.doc"), voucherData);
        pdfFile = FileUtil.wordToPdf(docFile, new File(destPath + saleContractNo + ".pdf"));
    }

    public static void main(String[] args) {
        wordToPdf();
    }
}
