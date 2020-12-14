package com.yondervision.mi.zwfwutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class SendSocketMessage {

	StringBuffer val = new StringBuffer();

	public String receive() {
		return val.toString();
	}

	/**
	 * @param message
	 * 根据config.properties文件获得ip、端口号将报文发送到csp，并获得返回的报文
	 */
	public void send(String message) {
		String ip = ReadProperties.getString("socketip");
		int iPort = Integer.parseInt(ReadProperties.getString("socketport"));
		Socket socket = null;
		try {
			SysLog.writeSCBW(message);
			socket = new Socket(ip, iPort);
			// 发送报文
			//OutputStream os=socket.getOutputStream();
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"),true);
			
			//PrintWriter out = new PrintWriter(os, true);
//			message = new String(message.getBytes("ISO-8859-1"), "ISO-8859-1");
//			byte[]bs=message.getBytes("GBK");
//			message=new String(bs,"GBK");
			//message="00000343122814RQ000000001################        000000001<flag>0</><TellCode>8809</><ChkCode>8809</><BrcCode>04310102</><TranIP>10.22.21.26</><STimeStamp>2013-07-24 09:55:28:966000</><TranCode>122814</><TranChannel>00</><TranDate>2013-09-25</><ChannelSeq>398243</><MTimeStamp>2013-07-24 09:55:28:967000</><AuthFlag>0</><AuthCode2>8809</><AuthCode3>8809</><AuthCode1>8809</><projectname>项目名称-邹放测试专用</>9";
			out.println(new String(message.getBytes(),"utf-8"));
			out.flush();
			//os.write(message.getBytes("utf-8"));
			//os.flush();
			InputStream in = socket.getInputStream();
			
			/*byte[] b = new byte[1024];
			String temp = "";
			while (in.read(b) > 0) {
				temp += new String(b, "GBK");
			System.out.println(temp);
			}*/
			//00000804600001RS0                                yd6000011<TranCode>600001</><TranDate>2013-03-19</><STimeStamp>2013-03-26 09:27:48</><MTimeStamp>2013-03-26 09:27:48</><BrcCode>00063000</><TellCode>0000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>127.0.0.1</><ChannelSeq>1231</><TranSeq>6895</><BusiSeq>6895</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>12311231</><FinancialDate>2011-01-25</><HostBank>aaa</><SubBank>aaa</><UnitAccNum>000001</><UnitAccName>威海宏达工程咨询有限公司</><AccState>0</><ChargeType></><AccInstCode>00063100</><DeputyCodest1></><DeputyCodest2></><DeputyCodest3></><LastPayDate>2010-12-01</><UnitProp>0.080</><PerProp>0.080</><SelFinType>0</><DeputyName></><DeputyIdCardNum></><DeputyPhone></><DeputyRole></><Balance>200030.67</><PaySum>0.00</><PayNum>0.00</>9
			SocketConfig[] scary = new SocketConfig[] {
					new SocketConfig("bwcd", "报文长度", 8),
					new SocketConfig("cranCode", "交易码", 6),
					new SocketConfig("bwType", "报文类型", 2),
					new SocketConfig("fileTag", "文件传送标志", 1),
					new SocketConfig("space", "保留 32", 32),
					new SocketConfig("serverName", "服务名称", 8),
					new SocketConfig("serverTreaty", "服务协议", 1)
			};
			readXcbw(in, scary);
			SysLog.writeXCBW(val.toString());
			//out.flush();
			in.close();
			//os.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			SysLog.writeError(e);
			throw new RuntimeException("接收后台信息出错" + e.getMessage());
		}
	}

	
	/**
	 * @param is
	 * @param scary
	 * 将返回的报文封装到val中
	 */
	private void readXcbw(InputStream is, SocketConfig[] scary) {
		try {
			
			byte[] b = new byte[50];
			int pyl = 0;
			HashMap hs = new HashMap();
			// 根据scary的name
			for (int i = 0; i < scary.length; i++) {
				SocketConfig sc = scary[i];
				pyl += is.read(b, 0, sc.length);
				String tmp = new String(b, 0, sc.length);
				hs.put(sc.name, tmp);
			}
			
			int bwcd = 0;
			bwcd = Integer.parseInt(hs.get("bwcd").toString().trim());
			//bwcd = bwcd - 1;
			byte[] bw = new byte[bwcd];
			int l = 0;
			while (l < bwcd) {
				int t = 0;
				if (l + 50 < bwcd)
					t = is.read(b);
				else
					t = is.read(b, 0, bwcd - l);
				for (int i = 0; i < t; i++) {
					bw[l + i] = b[i];
				}
				l += t;
			}
			val.append(new String(bw, "GBK"));
			/*int bzw = Integer.parseInt(hs.get("bzw").toString());
			if (bzw == 3) {
				readXcbw(is, scary);
			}*/
		} catch (NumberFormatException e) {
			SysLog.writeError(e);
		} catch (IOException e) {
			SysLog.writeError(e);
		}
	}

}

