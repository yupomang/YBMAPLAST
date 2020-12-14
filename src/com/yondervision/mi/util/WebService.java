package com.yondervision.mi.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

public class WebService {
	public static OMElement WebServiceClient(String url, String nameSpace, String methodName, 
			String paramName, String paramValue){
		EndpointReference targetEPR = new EndpointReference(url);
		 
		Options options = new Options();
		options.setAction(nameSpace + methodName);// 调用接口方法  
		options.setTo(targetEPR);
		options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
		//add by Carter King 2018/07/13  为了防止访问排队机进程阻塞
		options.setTimeOutInMilliSeconds(60000);//设置超时(60秒)
		ServiceClient sender = null;
		try {
			sender = new ServiceClient();
			sender.setOptions(options);
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace(nameSpace, "");
			OMElement method = fac.createOMElement(methodName, omNs);
			String[] param = paramName.split(",");
			String[] value = paramValue.split("&");
			for(int i=0; i<param.length; i++){
				OMElement name = fac.createOMElement(param[i], omNs);// 设置入参名称  
				name.setText(value[i].trim());//设置入参值  
				method.addChild(name);
			}
			method.build();
			System.out.println("method：" + method.toString());
			return sender.sendReceive(method);
		} catch (AxisFault e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return null;
	}
}
