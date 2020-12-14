package com.yondervision.mi.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi10106Form;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.WebService;

public class WebServiceImpl2{

	public String appApi10106(AppApi10106Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		form.setBusinName("获取网点排队信息");	
		log.info(LOG.START_BUSIN.getLogText(form.getBusinName()));
		log.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil.getStringParams(form)));	
		// 向12329服务热线获取网点排号人数
		String url = "http://122.225.204.195:8082/XFSRV/serviceXF/XFSRV?wsdl";
		OMElement omElement = WebService.WebServiceClient(url,
				"http://www.hzhnkj.com.cn/", "satisfyInvest",
				"beginDate,endDate", "20180101 121515" + "&"
						+ "20180301 121515");
		System.out.println("response:" + omElement);
//		int last = omElement.toString().lastIndexOf("</status>");
//		System.out.println("last:" + last);
//		System.out.println("status:" + omElement.toString().substring(last-1, last));
//		if(!"1".equals(omElement.toString().substring(last-1, last))){
//			modelMap.clear();
//			log.info("获取网点排队信息失败！网点编码：" + form.getWebsitecode());
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "获取网点排队信息失败！");
//			return "";
//		}
//		OMElement elementReturn = omElement.getFirstElement();
//		System.out.println("response1:" + elementReturn.getText());
//		
//		Document document = null;
//		SAXReader reader = new SAXReader();
//		//生产使用
//		ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes("UTF-8"));
//		//测试使用
////		ByteArrayInputStream inputStream = new ByteArrayInputStream(elementReturn.getText().getBytes());
//		InputStreamReader ir = new InputStreamReader(inputStream);
//		//document已经获取到xml文件
//		document = reader.read(ir);
//		Element rootElement = document.getRootElement();
//		List<Element> listElement = rootElement.elements();
//		int workcount = 0;
//		int queueNumber = 0;
//		List<HashMap> resultList = new ArrayList<HashMap>();
//		for(Element level2: listElement){//遍历二级节点
//			HashMap map = new HashMap();
//			workcount = workcount + (level2.elementText("AcceptanceNumber")=="" ? 0 : Integer.valueOf(level2.elementText("AcceptanceNumber")));
//			queueNumber = queueNumber + (level2.elementText("QueueNumber")=="" ? 0 : Integer.valueOf(level2.elementText("QueueNumber")));
//			
//			System.out.print("Ctrllerid:" + level2.elementText("Ctrllerid"));
//			map.put("websitecode", level2.elementText("Ctrllerid"));
//			System.out.print("GroupNo:" + level2.elementText("GroupNo"));
//			map.put("jobid", level2.elementText("GroupNo"));
//			System.out.print("GroupName:" + level2.elementText("GroupName"));
//			//生产使用
//			if(CommonUtil.isEmpty(level2.elementText("GroupName"))){
//				map.put("jobname", "");
//			}else{
//				map.put("jobname", new String(level2.elementText("GroupName").getBytes("iso-8859-1"), "utf-8"));
//			}
//			//测试使用
////			map.put("jobname", level2.elementText("GroupName"));
//			System.out.print("GroupMold:" + level2.elementText("GroupMold"));
//			//生产使用
//			if(CommonUtil.isEmpty(level2.elementText("GroupMold"))){
//				map.put("GroupMold", 0);
//			}else{
//				if("预约".equals(new String(level2.elementText("GroupMold").getBytes("iso-8859-1"), "utf-8"))){
//					map.put("GroupMold", "1");
//				}else{
//					map.put("GroupMold", "0");
//				}
//			}
//			//测试使用
////			if("预约".equals(level2.elementText("GroupMold"))){
////				map.put("GroupMold", "1");
////			}else{
////				map.put("GroupMold", "0");
////			}
//			System.out.print("WaitingNumber:" + level2.elementText("WaitingNumber"));
//			map.put("waitcount", level2.elementText("WaitingNumber"));
//			map.put("ordertype", "");
//			resultList.add(map);
//		}
//		
//		modelMap.clear();
//
//		modelMap.put("tips", "");
		log.info(LOG.END_BUSIN.getLogText(form.getBusinName()));
		return "";

	}

}
