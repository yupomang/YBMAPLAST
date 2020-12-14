package com.yondervision.mi.zwfwutil;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ParseTranConfig {

	public static final ThreadLocal local = new ThreadLocal(); // 保存request数据
	public static HashMap hm = new HashMap(); // 保存上传报文的数据
	
	/**
	 * @param tranCode 交易码
	 * @return
	 * 根据交易码读取tranConfig.xml文件组装报文体
	 * 
	 */
	public static String transBody(String tranCode) {
		if (hm.get(tranCode) != null) {
			Element em = (Element) hm.get(tranCode);
			String body = transBody(em);
			String length = transLength(body.getBytes().length);
			String scbw = length + transHead(tranCode) + body + 9;
			return scbw;
		}
		XmlReader xmlReader = null;
		try {
			xmlReader = new XmlReader("resources/tranConfig.xml", XmlReader.USE_CLASSLOADER);
		} catch (Exception e) {
			SysLog.writeError(e);
		}
		// 根据交易码和tranConfig.xml的tran节点id匹配，将匹配的节点保存到hm中
		Element em = xmlReader.getDom().getRootElement();
		Element[] ems = xmlReader.getElements(em, "tran");
		for (int i = 0; i < ems.length; i++) {
			em = ems[i];
			if (em.getAttributeValue("id").equals(tranCode)) {
				hm.put(tranCode, em);
				break;
			}
		}
		
		String body = transBody(em);
		String length = transLength(body.getBytes().length);
		String scbw = length + transHead(tranCode) + body + "9";
		return scbw;
	}

	/**
	 * 组装em节点数据
	 * @param em
	 * @return
	 */
	private static String transBody(Element em) {
		HttpServletRequest req = null;
		if (local.get() != null) {
			req = (HttpServletRequest) local.get();
		}
		List child = em.getChildren();
		String name = "", val = "";
		StringBuffer retval = new StringBuffer();
		for (int i = 0; i < child.size(); i++) {
			em = (Element) child.get(i);
			name = em.getName();
			if (req != null) {
				val = req.getParameter(name);
				if(val==null||"".equals(val)){
					val=(String)req.getAttribute(name);
				}
			}
			if (val == null) {
				val = em.getText();
			}
			val = val.trim();
			if("pwd".equals(name)||"newqrypwd".equals(name)||"cfmqrypwd".equals(name)){
				try {
					byte[] b=DESForJava.encryption(val, "12345678");
					byte[] c=DESForJava.bcd_to_asc(b);
					val=new String(c);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if("TranIP".equals(name)){
				val=req.getRemoteAddr();
			}
			if("MTimeStamp".equals(name)||"STimeStamp".equals(name)){
				Date date= new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				val=simpleDateFormat.format(date);
			}
			if("TranDate".equals(name)){
				Date date= new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				val=simpleDateFormat.format(date);
			}
			retval.append("<" + name + ">" + val + "</>");
		}
		return retval.toString();
	}
	
	/**
	 * 组装报文头
	 * @param tranCode
	 * @return
	 */
	private static String transHead(String tranCode) {
		
		// 报文头，此处硬编码了，需要修改 james
		StringBuffer head = new StringBuffer();
		
		// 交易代码 6 后台交易代码
		head.append(tranCode);
		
		// 报文类型 2 “RQ”请求报文“RS”回应报文
		head.append("RQ");
		
		// 文件传送标志 1 0-无文件 1-有文件上传 2-有文件下传
		head.append("0");
		
		// 加密卡ID 8 getenv(“DEVICE_ID”) 前补零
		head.append("00000001");
		
		// 报文校验码 16 不包括报文头 实际8字节，扩展成16进制的ASCII 16字节
		head.append("################");
		
		// 保留 8
		head.append("        ");
		
		// 服务名称 8 后台服务程序名
		head.append("00000000");
		
		// 服务协议 1 0:本地 1:CICS 2:MQ 3:TCPIP 4:TEXUDO 5:SNA
		head.append("1");
		
		return head.toString();
	}
	
	
	/**
	 * 上传报文保留8位，不足补0
	 * @param length
	 * @return
	 */
	private static String transLength(int length) {
		String temp = length + ""; 
		StringBuffer lengthStr = new StringBuffer();
		for (int i = 0; i < (8 - temp.length()); i++) {
			lengthStr.append("0");
		}
		return lengthStr.append(temp).toString();
	}

}
