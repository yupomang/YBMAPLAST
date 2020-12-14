package com.yondervision.mi.shengchan;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WebService {
	public static void RPCClient() {

		EndpointReference targetEPR = new EndpointReference("http://172.16.10.159:88/pdjhwebservice/Service.asmx");
		
		Options options = new Options();  
		options.setAction("http://www.hzhnkj.com.cn/getGroupWaitting2");// 调用接口方法  
		
		options.setTo(targetEPR);  
		options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.  
		ServiceClient sender = null;  
		try {  
			sender = new ServiceClient();  
			sender.setOptions(options);  
			OMFactory fac = OMAbstractFactory.getOMFactory();  
			OMNamespace omNs = fac.createOMNamespace("http://www.hzhnkj.com.cn/", "");
			
			OMElement method = fac.createOMElement("getGroupWaitting2", omNs);  
			OMElement name = fac.createOMElement("ctrllerid", omNs);// 设置入参名称  
			OMElement name2 = fac.createOMElement("status", omNs);// 设置入参名称  
			name.setText("1");// 设置入参值  
			method.addChild(name);  
			method.addChild(name2);  

			method.build();
			System.out.println("method：" + method.toString());  
			OMElement response = sender.sendReceive(method);  
			System.out.println("response:" + response);  
			
			OMElement elementReturn = response.getFirstElement();  
			System.out.println("cityCode:" + elementReturn.getText());  
		} catch (AxisFault e) {  
			System.out.println("Error");  
			e.printStackTrace();  
		}


	}
	
	public static void main(String[] args) throws UnknownHostException {  
		RPCClient();
	}
}
