package com.yondervision.mi.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * author: cjianquan
 * date: 2016/9/29
 */

public class HttpRequest {

    // 表示服务器端的url

    private HttpRequest() {
        // TODO Auto-generated constructor stub
    }

    /*
     * params 填写的URL的参数 encode 字节编码
     */
    public static String sendPostMessage(String strUrl,Map<String, String> params,
                                         String encode) {

        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringBuffer stringBuffer = new StringBuffer();

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    stringBuffer
                            .append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 删掉最后一个 & 字符
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            System.out.println("-->>" + stringBuffer.toString());

            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url
                        .openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);// 从服务器获取数据
                httpURLConnection.setDoOutput(true);// 向服务器写入数据

                // 获得上传信息的字节大小及长度
                byte[] mydata = stringBuffer.toString().getBytes();
                // 设置请求体的类型
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Content-Lenth",
                        String.valueOf(mydata.length));

                // 获得输出流，向服务器输出数据
                OutputStream outputStream = (OutputStream) httpURLConnection
                        .getOutputStream();
                outputStream.write(mydata);

                // 获得服务器响应的结果和状态码
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {

                    // 获得输入流，从服务器端获得数据
                    InputStream inputStream = (InputStream) httpURLConnection
                            .getInputStream();
                    return (changeInputStream(inputStream, encode));

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "";
    }

    /*
     * // 把从输入流InputStream按指定编码格式encode变成字符串String
     */
    public static String changeInputStream(InputStream inputStream,
                                           String encode) {

        // ByteArrayOutputStream 一般叫做内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {

            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);

                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    public static void main(String[] args) {  
/*        //发送 GET 请求  
        String s=HttpRequest.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");  
        System.out.println(s);  */
/*          HashMap map =new HashMap();
          map.put("baseInfoXml", "<?xml version=\"1.0\" encoding=\"gb2312\"?><RECORD><CALLINFO><CALLER>浙江省一窗受理平台</CALLER><CALLTIME>2018-06-28 09:51:27</CALLTIME><CALLBACK_URL>http://10.68.130.194:8080/ycslypt</CALLBACK_URL><ISSUE>33020100000001</ISSUE></CALLINFO><PROJID>330201191806289000001</PROJID><PROJPWD>601871</PROJPWD><IS_MANUBRIUM>0</IS_MANUBRIUM><SERVICECODE>其他-02491-006</SERVICECODE><SERVICECODE_ID>其他-02491-006</SERVICECODE_ID><SERVICE_DEPTID>001008002016015</SERVICE_DEPTID><BUS_MODE>00</BUS_MODE><BUS_MODE_DESC>无</BUS_MODE_DESC><SERVICEVERSION>6</SERVICEVERSION><SERVICENAME>偿还购房贷款本息提取住房公积金</SERVICENAME><PROJECTNAME>关于邵建宁申请偿还购房贷款本息提取住房公积金</PROJECTNAME><INFOTYPE>即办件</INFOTYPE><BUS_TYPE>0</BUS_TYPE><REL_BUS_ID></REL_BUS_ID><APPLYNAME>邵建宁</APPLYNAME><APPLY_CARDNUMBER>330204197508036114</APPLY_CARDNUMBER><APPLY_CARDTYPE>身份证</APPLY_CARDTYPE><CONTACTMAN>邵建宁</CONTACTMAN><CONTACTMAN_CARDNUMBER></CONTACTMAN_CARDNUMBER><CONTACTMAN_CARDTYPE>身份证</CONTACTMAN_CARDTYPE><TELPHONE>13362486339</TELPHONE><MOBILE></MOBILE><APPLY_TYPE>0</APPLY_TYPE><POSTCODE></POSTCODE><ADDRESS>1212121</ADDRESS><LEGALMAN></LEGALMAN><DEPTID>001008002016015</DEPTID><DEPTNAME>市住建委</DEPTNAME><SS_ORGCODE>419534650</SS_ORGCODE><RECEIVE_NAME>邵建宁</RECEIVE_NAME><RECEIVE_USEID>4028815e4834a0220148358cc98b6114</RECEIVE_USEID><APPLYFROM>9</APPLYFROM><APPROVE_TYPE>01</APPROVE_TYPE><APPLY_PROPERTIY>99</APPLY_PROPERTIY><RECEIVETIME>2018-06-28 08:09:39</RECEIVETIME><BELONGTO></BELONGTO><AREACODE>330201</AREACODE><DATASTATE>1</DATASTATE><BELONGSYSTEM>33000099001</BELONGSYSTEM><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:39</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION><MEMO></MEMO><APPLYCOUNT>1</APPLYCOUNT></RECORD>");
          map.put("attrXml", "<?xml version=\"1.0\" encoding=\"gb2312\"?><RECORDS><RECORD><UNID>b105f99aa1d2496d986cfd53fa376546</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>住房公积金提取申请表</ATTRNAME><SORTID>0</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:41</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>afff683d-30b1-49fc-93da-9a2dba6c616f</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:41</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>6868a819e76245058ff389438a1242e3</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>身份证明</ATTRNAME><SORTID>1</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:41</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>8364baae-630b-4ff7-aefe-608f9f34a37f</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:41</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>90dc197411414f13a9a250f50ebee0d6</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>借款合同</ATTRNAME><SORTID>2</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:41</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>7e6dd42a-b35d-4542-a4aa-deeb7f703a21</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:41</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>89b90993da3940b498308a41e2d90124</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>购房合同</ATTRNAME><SORTID>3</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:41</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>e4c8f3d3-4b6a-4792-9c6c-3df841ee411f</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:41</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>af76ccaf45a140e79aaf1c1b003155b1</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>还款明细</ATTRNAME><SORTID>4</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>47ad0168-e28b-48ac-b0a2-660a37bb78eb</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>841a2bd7ec2e4f33a2aa1253d1d296dc</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>贷款结清凭证</ATTRNAME><SORTID>5</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>8ee3b2a0-ce12-43b3-84a4-7395e9119497</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>d62181ad9eac4c1eb5d616e51adc5d0a</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>不动产权证（房屋所有权证）</ATTRNAME><SORTID>6</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>e6774e09-7f10-4480-860f-56842c3a5e02</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>dbeacb7f914c40b8bb72bb8afde0b033</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明</ATTRNAME><SORTID>7</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>2657d45f-d310-42c6-9266-58083e3db404</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>b24f77c4a1c24bd89bd4b57ff7bb4f73</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>委托代理人办理提取的，提供代理人身份证明及委托书</ATTRNAME><SORTID>8</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>41e389d5-585a-4945-8aad-402123d07f7d</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD><RECORD><UNID>911bab2ab3e04524bf1dc54306c34916</UNID><PROJID>330201191806289000001</PROJID><ATTRNAME>借记卡（或还贷卡）</ATTRNAME><SORTID>9</SORTID><TAKETYPE>纸质收取</TAKETYPE><ISTAKE>0</ISTAKE><AMOUNT>1</AMOUNT><TAKETIME>2018-06-28 08:09:42</TAKETIME><MEMO></MEMO><AREACODE>330201</AREACODE><BELONGSYSTEM>33000099001</BELONGSYSTEM><ATTRID>6451efdf-c351-48c6-9dd7-4cce75c54802</ATTRID><EXTEND></EXTEND><CREATE_TIME>2018-06-28 08:09:42</CREATE_TIME><SYNC_STATUS>I</SYNC_STATUS><DATAVERSION>3</DATAVERSION></RECORD></RECORDS>");
          map.put("formXml", "");
        //发送 POST 请求  ;
        String sr=HttpRequest.sendPostMessage("http://61.153.144.77:7006/YBMAPZH/webapi80004.json?centerId=00057400", map,"UTF-8");  
        System.out.println(sr);*/
    }
}  