package com.yondervision.mi.test.myutils.soap;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.beanutils.BeanUtils;

public class SoapUtil {

/**
* 解析soapXML
* @param soapXML
* @return
*/
public static WebserviceResultBean parseSoapMessage(String soapXML) {
         WebserviceResultBean resultBean = new WebserviceResultBean();
         try {
            SOAPMessage msg = formatSoapString(soapXML);
            SOAPBody body = msg.getSOAPBody();
             Iterator<SOAPElement> iterator = body.getChildElements();
             parse(iterator, resultBean);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return resultBean;
     }
 
     /**
      * 把soap字符串格式化为SOAPMessage
      * 
      * @param soapString
      * @return
      * @see [类、类#方法、类#成员]
      */
     private static SOAPMessage formatSoapString(String soapString) {
         MessageFactory msgFactory;
         try {
             msgFactory = MessageFactory.newInstance();
             SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
                     new ByteArrayInputStream(soapString.getBytes("UTF-8")));
             reqMsg.saveChanges();
             return reqMsg;
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
 
     /**
      * 解析soap xml
      * @param iterator
      * @param resultBean
      */
     private static void parse(Iterator<SOAPElement> iterator, WebserviceResultBean resultBean) {
         while (iterator.hasNext()) {
             SOAPElement element = iterator.next();
 //            System.out.println("Local Name:" + element.getLocalName());
 //            System.out.println("Node Name:" + element.getNodeName());
 //            System.out.println("Tag Name:" + element.getTagName());
 //            System.out.println("Value:" + element.getValue());
             if ("ns:BASEINFO".equals(element.getNodeName())) {
                 continue;
             } else if ("ns:MESSAGE".equals(element.getNodeName())) {
                 Iterator<SOAPElement> it = element.getChildElements();
                 SOAPElement el = null;
                 while (it.hasNext()) {
                     el = it.next();
                     if ("RESULT".equals(el.getLocalName())) {
                         resultBean.setResult(el.getValue());
                         System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
                     } else if ("REMARK".equals(el.getLocalName())) {
                         resultBean.setRemark(null != el.getValue() ? el.getValue() : "");
                         System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
                     } else if ("XMLDATA".equals(el.getLocalName())) {
                         resultBean.setXmlData(el.getValue());
                         System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
                     }
                 }
             } else if (null == element.getValue()
                     && element.getChildElements().hasNext()) {
                 parse(element.getChildElements(), resultBean);
             }
         }
     }
     
     private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
         while (iterator.hasNext()) {
             SOAPElement element = (SOAPElement) iterator.next();
             System.out.println("Local Name:" + element.getLocalName());
             System.out.println("Node Name:" + element.getNodeName());
             System.out.println("Tag Name:" + element.getTagName());
			 System.out.println("Value:" + element.getValue());
			if (null == element.getValue()
                     && element.getChildElements().hasNext()) {
                 PrintBody(element.getChildElements(), side + "-----");
             }
         }
     }
     
	private static HashMap  getMap(Iterator<SOAPElement> iterator, String side) {
		HashMap map =new HashMap();
         while (iterator.hasNext()) {
             SOAPElement element = (SOAPElement) iterator.next();
             System.out.println("Local Name:" + element.getLocalName());
             System.out.println("Node Name:" + element.getNodeName());
             System.out.println("Tag Name:" + element.getTagName());
             if (!(element.getValue()==null)) {
 				System.out.println("1");
				System.out.println("Value:" + element.getValue());
				map.put("value", element.getValue());
				System.out.println("1");
			}
			if (null == element.getValue()
                     && element.getChildElements().hasNext()) {
				getMap(element.getChildElements(), side + "-----");
             }
         }
		 return map;
     }
     
     public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
         System.out.println("开始解析soap...");
         String deptXML = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:xyhcResponse xmlns:ns2=\"http://dao.xyhc.cssnb.com/\"><return>{\"code\":\"202000\",\"tysqsbm\":\"345242398708457\",\"tysbm\":\"9dd6d1a155fe487083ba02b99a30debc\",\"result\":{ \"heimd\":[{\"sxdm\":\"1005\",\"sxmc\":\"失信被执行人\",\"cslb\":[{}],\"data\": [{\"zdnr\": \"失信被执行人\", \"zdmc\": \"失信黑名单事项\"},{\"zdnr\": \"张三\", \"zdmc\": \"信用主体名称\"},{\"zdnr\": \"居民身份证号码\", \"zdmc\": \"证件类型\"},{\"zdnr\": \"330205197501011234\", \"zdmc\": \"证件号码\"},{\"zdnr\": \"金融借款合同纠纷\", \"zdmc\": \"列入名单原因\"},{\"zdnr\": \"2016-02-25\", \"zdmc\": \"产生日期\"},{\"zdnr\": \"\", \"zdmc\": \"公示有效期限\"},{\"zdnr\": \"市中级法院\", \"zdmc\": \"发布机关名称\"}]},{}],\"rst\":\"\"}}</return></ns2:xyhcResponse></soap:Body></soap:Envelope>";
         try {
             SOAPMessage msg = formatSoapString(deptXML);
             SOAPBody body = msg.getSOAPBody();
             Iterator<SOAPElement> iterator = body.getChildElements();
             String result = getMap(iterator, null).get("value").toString();
             System.out.println(result);
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         System.out.println("解析soap结束...");
     }
 
 }