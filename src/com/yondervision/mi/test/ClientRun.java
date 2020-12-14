package com.yondervision.mi.test;

import java.io.OutputStream;
import java.net.Socket;

public class ClientRun {

    public static void main(String[] args) throws Exception {

        
        
        String xml ="<root>\r\n"+
        "<body>\r\n" +
        "<keynum>123456</keynum>\r\n" +
        "<free_use1></free_use1>\r\n" +
        "<free_use2><free_use2>\r\n" +
        "<free_use3><free_use3>\r\n" +
        "<free_use4><free_use4>\r\n" +
        "<free_use5></free_use5>\r\n" +
        "<free_use6></free_use6>\r\n" +
        "/body>\r\n" +
        "</root>";
        Socket client = new Socket("18.18.18.201",9092);
        OutputStream out = client.getOutputStream();

        byte[] b = xml.getBytes("UTF-8");

        out.write(int2Bytes8(b.length));
        out.write(b);
        out.close();
        client.close();
    }

    /**
     * @Title: int2Bytes8   
     * @Description: 数字[2] 变成八个字节的 ['0' '0' '0' '0' '0' '0' '0' '2']   
     * @param: @param num
     * @param: @return      
     * @return: byte[]      
     */
    public static byte[] int2Bytes8(int num) {
        StringBuffer sb = new StringBuffer(String.valueOf(num));
        int length = 8 - sb.length();
        for (int i = 0; i < length; i++) {
            sb.insert(0, '0');
        }
        return sb.toString().getBytes();
    }
}