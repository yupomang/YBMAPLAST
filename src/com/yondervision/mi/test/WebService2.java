package com.yondervision.mi.test;

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

public class WebService2 {
	public static void RPCClient() {
		/*try {
			String url = "http://10.19.143.173:88/pdjhservice/Service.asmx";
			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(url);
			Options options = serviceClient.getOptions();
			//确定目标服务地址  
			options.setTo(targetEPR);
			//确定调用方法  
			options.setAction("getGroupWaitting2");
			*//** 
			* 指定要调用的getPrice方法及WSDL文件的命名空间 
			* 如果 webservice 服务端由axis2编写 
			* 命名空间 不一致导致的问题 
			* org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0 
			*//*
			QName qname = new QName("http://www.hzhnkj.com.cn/", "getGroupWaitting2");
			// 指定getPrice方法的参数值  
			Object[] parameters = new Object[] { "1" };
		
			// 指定getPrice方法返回值的数据类型的Class对象  
			Class[] returnTypes = new Class[] { double.class };
		
			// 调用方法一 传递参数，调用服务，获取服务返回结果集 
			OMElement element = serviceClient.invokeBlocking(qname, parameters);
			//值得注意的是，返回结果就是一段由OMElement对象封装的xml字符串。 
			//我们可以对之灵活应用,下面我取第一个元素值，并打印之。因为调用的方法返回一个结果 
			String result = element.getFirstElement().getText();
			System.out.println(result);
		
			// 调用方法二 getPrice方法并输出该方法的返回值  
			Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
			// String r = (String) response[0];
			Double r = (Double) response[0];
			System.out.println(r);
		
		} catch (AxisFault e) {
			e.printStackTrace();
		}*/
		
		/*Service service = new Service();  
		Call call = null;  
		try {  
			call = (Call) service.createCall();  
			call.setTargetEndpointAddress(new URL("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx"));  
			call.setOperationName(new QName("http://WebXml.com.cn/","getWeatherbyCityName"));  
			call.addParameter(new QName("http://WebXml.com.cn/", "theCityName"),XMLType.SOAP_VECTOR,ParameterMode.IN);  
			call.setReturnType(XMLType.SOAP_VECTOR);  
			call.setUseSOAPAction(true);  
			call.setSOAPActionURI("http://WebXml.com.cn/getWeatherbyCityName");  
			System.out.println(call.invoke(new Object[]{"广州"}));  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  */
		
		EndpointReference targetEPR = new EndpointReference("http://10.19.143.173:88/pdjhwebservice/Service.asmx");
		
		//2,3
		Options options = new Options();  
		//options.setAction("http://www.hzhnkj.com.cn/getGroupWaitting2");// 调用接口方法  
		options.setAction("http://www.hzhnkj.com.cn/getTicket");// 调用接口方法//取号
		//options.setAction("http://www.hzhnkj.com.cn/getMyQnoInfo2");// 调用接口方法
		//options.setAction("http://www.hzhnkj.com.cn/getLocalQnoInfo");// 调用接口方法
		
		options.setTo(targetEPR);  
		options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.  
		ServiceClient sender = null;  
		try {  
			sender = new ServiceClient();  
			sender.setOptions(options);  
			OMFactory fac = OMAbstractFactory.getOMFactory();  
			OMNamespace omNs = fac.createOMNamespace("http://www.hzhnkj.com.cn/", "");
			
/*			OMElement method = fac.createOMElement("getGroupWaitting2", omNs);  
			OMElement name = fac.createOMElement("ctrllerid", omNs);// 设置入参名称  
			OMElement name2 = fac.createOMElement("status", omNs);// 设置入参名称  
			name.setText("1");// 设置入参值  
			method.addChild(name);  
			method.addChild(name2);  */
			
			//取号
			OMElement method = fac.createOMElement("getTicket", omNs);
			OMElement name = fac.createOMElement("ctrllerid", omNs);// 设置入参名称  
			name.setText("1");// 设置入参值  
			method.addChild(name);
			OMElement name1 = fac.createOMElement("groupno", omNs);// 设置入参名称  
			name1.setText("2");// 设置入参值  
			method.addChild(name1);
			OMElement name2 = fac.createOMElement("sfzh", omNs);// 设置入参名称  
			name2.setText("330211196011230034");// 设置入参值  
			method.addChild(name2);
			OMElement name3 = fac.createOMElement("Tickitmode", omNs);// 设置入参名称  
			name3.setText("01");// 设置入参值  
			method.addChild(name3);
			OMElement name4 = fac.createOMElement("status", omNs);// 设置入参名称  
			method.addChild(name4);
			
/*			OMElement method = fac.createOMElement("getMyQnoInfo2", omNs);
			OMElement name = fac.createOMElement("sfzh", omNs);// 设置入参名称  
			name.setText("330211196011230034");// 设置入参值  
			method.addChild(name);
			OMElement name1 = fac.createOMElement("status", omNs);// 设置入参名称  
			method.addChild(name1);*/
			
/*			OMElement method = fac.createOMElement("getLocalQnoInfo", omNs);  
			OMElement name = fac.createOMElement("ctrllerid", omNs);// 设置入参名称  
			name.setText("1");// 设置入参值  
			method.addChild(name);  
			OMElement name1 = fac.createOMElement("qno", omNs);// 设置入参名称  
			name1.setText("1");// 设置入参值  
			method.addChild(name1);  
			OMElement name2 = fac.createOMElement("sfzh", omNs);// 设置入参名称 
			name2.setText("330211196011230034");// 设置入参值  
			method.addChild(name2);
			OMElement name3 = fac.createOMElement("status", omNs);// 设置入参名称  
			method.addChild(name3);  */
			
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
