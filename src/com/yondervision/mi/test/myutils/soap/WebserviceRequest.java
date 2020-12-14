package com.yondervision.mi.test.myutils.soap;

import java.io.ByteArrayOutputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URL;

public class WebserviceRequest {

@SuppressWarnings("resource")

public String SendSoamXml(String requsetUrl, String actionUrl, String sendXmlPath){

System.out.println("1=====================SendSoamXml===========================");

String result = "";

try {

File fileToSend = new File(sendXmlPath);

URL url = new URL(requsetUrl);

HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

byte[] buf = new byte[(int) fileToSend.length()];

       new FileInputStream(sendXmlPath).read(buf);

       

       httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));

       httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

//        httpConn.setRequestProperty("soapActionString", actionUrl);

       httpConn.setRequestMethod("POST");

       httpConn.setDoOutput(true);

       httpConn.setDoInput(true);

       httpConn.setConnectTimeout(600000);

       httpConn.setReadTimeout(600000);

       

       OutputStream out = httpConn.getOutputStream();

       out.write(buf);

       out.close();

       

       byte[] datas=readInputStream(httpConn.getInputStream());

       result = new String(datas);

       //打印返回结果

//        System.out.println("result:" + result);

       System.out.println("2=====================SendSoamXml===========================");

} catch (Exception e) {

e.printStackTrace();

}

return result;

}

   

    public byte[] readInputStream(InputStream inStream) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int len = 0;

        while( (len = inStream.read(buffer)) !=-1 ){

            outStream.write(buffer, 0, len);

        }

        byte[] data = outStream.toByteArray();//网页的二进制数据

        outStream.close();

        inStream.close();

        return data;

    }

}