package com.yondervision.mi.test;

import com.yd.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NEWappapi00227Testyuanshi {

    public static void wordToPdf(){
        File pdfFile = null;
        String destPath = "C:/Users/yd/Desktop/test/";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String saleContractNo = dateFormat.format(new Date());
        File tempFile = new File(destPath + saleContractNo + ".doc");
        pdfFile = FileUtil.wordToPdf(tempFile, new File(destPath, saleContractNo + ".pdf"));
    }

    public static void main(String[] args) {
        wordToPdf();
    }
}
