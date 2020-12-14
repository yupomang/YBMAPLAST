package com.yondervision.mi.test.myutils.soap;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
public class WebserviceRespond {

	@SuppressWarnings("unchecked")
	public Iterator GetAlarmInfo(String soapXml) {

		Iterator iterator = null;

		try {

			SOAPMessage msg = formatSoapString(soapXml);// 把soap字符串格式化为SOAPMessage

			SOAPBody body = msg.getSOAPBody();

			iterator = body.getChildElements();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return iterator;

	}

	public SOAPMessage formatSoapString(String soapString) {

		SOAPMessage reqMsg = null;

		try {

			MessageFactory msgFactory = MessageFactory.newInstance();

			reqMsg = msgFactory.createMessage(new MimeHeaders(),

			new ByteArrayInputStream(soapString.getBytes("UTF-8")));

			reqMsg.saveChanges();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return reqMsg;

	}

	@SuppressWarnings("unchecked")
	public SoapAlarmRespondBean parseAlarmXml(Iterator iterator) {

		SoapAlarmRespondBean webBean = new SoapAlarmRespondBean();

		while (iterator.hasNext()) {

			SOAPElement element = (SOAPElement) iterator.next();

			if ("ns1:otdr_geo_location".equals(element.getNodeName())) {// 得到该标签的

				webBean.setLatitude(element.getAttribute("Latitude"));

				element.setAttribute("Latitude", "11111");

				webBean.setLongitude(element.getAttribute("Longitude"));

				webBean.setUrl_image(element.getAttribute("url_image"));

			} else if ("ns1:Exception".equals(element.getNodeName())) {

				webBean.setMessage(element.getAttribute("Message"));

				webBean.setCodeExp(element.getAttribute("CodeExp"));

				webBean.setUrl_image(element.getAttribute("url_image"));

			} else if (null == element.getValue()
					&& element.getChildElements().hasNext()) {

				parseAlarmXml(element.getChildElements());

			}

		}

		return webBean;

	}

	@SuppressWarnings("unchecked")
	public void parseQQXml(Iterator iterator) {

		while (iterator.hasNext()) {

			SOAPElement element = (SOAPElement) iterator.next();

			if ("qqCheckOnlineResult".equals(element.getNodeName())) {// 得到该标签的

				System.out.println(element.getValue());

			} else if (null == element.getValue()
					&& element.getChildElements().hasNext()) {

				parseQQXml(element.getChildElements());

			}

		}

	}

}