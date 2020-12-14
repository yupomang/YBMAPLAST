package com.yondervision.mi.common.message.sendIos;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.yondervision.mi.util.PropertiesReader;


public class  SendMessage
{
	public void send_IOS(SendParam param) throws Exception
	{	
		 String sendIP = PropertiesReader.getProperty("properties.properties", "ios_send_id");
		 String sendPORT = PropertiesReader.getProperty("properties.properties", "ios_send_port");
		
		 Socket socket=new Socket(sendIP,Integer.valueOf(sendPORT));
		 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		 String message="<TranCode>"+param.getTranCode()+"</><host>"+param.getHost()+"</><port>"+param.getPort()+"</><certificate>"+param.getCertificate()+"</><pwd>"+param.getPwd()+"</><alert>"+param.getAlert()+"</><badge>"+param.getBadge()+"</><customdictionary>"+param.getCustomdictionary()+"</><device>"+param.getDevice()+"</><userid>"+param.getUserid()+"</><pushid>"+param.getPushid()+"</>";
		 System.out.println("======= ios 推送服务 mmg message : "+message);
		 out.print(createLengthString(message)+message+"9");
		 out.flush();
		 InputStream is = socket.getInputStream();
			
		 SocketConfig[] scary = new SocketConfig[] {
					new SocketConfig("bwcd", "报文体长度", 8),
					new SocketConfig("jydm", "交易代码", 6),
					new SocketConfig("bwlx", "报文类型", 2),
					new SocketConfig("csbz", "文件传送标志", 1),
					new SocketConfig("jym", "报文校验码（MAC)", 32),
					new SocketConfig("wfcx", "服务程序名", 8),
					new SocketConfig("bwxy", "报文协议", 1) };
	    byte[] b = new byte[50];
		int pyl = 0;
		HashMap hs = new HashMap();
		for (int i = 0; i < scary.length; i++) {
				SocketConfig sc = scary[i];
				pyl += is.read(b, 0, sc.length);
				String tmp = new String(b, 0, sc.length);			 
				hs.put(sc.name, tmp);			 
				System.out.println(sc.desc + ":" + tmp);
		}
		int bwcd= 0;
		try{			
		  bwcd = Integer.parseInt(hs.get("bwcd").toString());
		}catch(Exception e){
			throw new Exception("接受报文长度出错，请检查后台交易");
		}
		 
		byte[] bw = new byte[bwcd];
		int sumBwLen=is.read(bw);
		System.out.println("sumBwLen="+sumBwLen);
				// 2010 年4月9日 加的      开始   为了处理长报文处理 上面那句也改了 原来是 is.read(bw);
		if(sumBwLen<bw.length){
			 
		    byte[] tmpbyte=new byte[50];
		    while(sumBwLen<bw.length){
		    	int t = 0;
		    	if (sumBwLen + 50 < bw.length)
					t = is.read(tmpbyte);
				else
					t = is.read(tmpbyte, 0, bw.length - sumBwLen); 
		    	System.arraycopy(tmpbyte, 0,bw,sumBwLen,t);
		    	sumBwLen+=t;		     
		    }			 
		}      
		String responseLine = new String(bw,"utf-8");
		System.out.println(responseLine);
		socket.close();		 
	}
	
	private static String createLengthString(String sb) throws Exception {
		int size = sb.getBytes("utf-8").length;
		String sizeStr = size + "";
		StringBuffer zeroSB = new StringBuffer();
		for (int i = 0; i < 8 - sizeStr.length(); i++) {
			zeroSB.append("0");
		}
		return zeroSB + sizeStr;
	}
}
