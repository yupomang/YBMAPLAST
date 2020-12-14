package com.yondervision.mi.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yondervision.mi.dao.Mi001DAO;
import com.yondervision.mi.dao.Mi045DAO;
import com.yondervision.mi.dao.Mi415DAO;
import com.yondervision.mi.dto.Mi001;
import com.yondervision.mi.dto.Mi001Example;
import com.yondervision.mi.dto.Mi007;
import com.yondervision.mi.dto.Mi045;
import com.yondervision.mi.dto.Mi045Example;
import com.yondervision.mi.dto.Mi415;
import com.yondervision.mi.dto.Mi415Example;
import com.yondervision.mi.service.CodeListApi001Service;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;
import com.yondervision.mi.util.couchbase.CouchBase;
import com.yondervision.mi.util.couchbase.JsonUtil;

import net.spy.memcached.CASValue;

/**
 * 初始化的时候，在init方法 将流量清零,此Servlet不用来访问
 * @author lixu
 *
 */
public class FlowServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2194345162185728803L;
	public FlowServlet() {
		super();
	}

	//销毁时候调用
	public void destroy() {
		super.destroy();
		// Put your code here
	}

	//
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	//调用这个方法
	public void init() throws ServletException {
		System.out.println("【重务起动，清理流量信息！！！！！1】");
		Mi001DAO mi001Dao = (Mi001DAO)SpringContextUtil.getBean("mi001Dao");
		Mi001Example mi001Example = new Mi001Example();
		mi001Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi001> list = mi001Dao.selectByExample(mi001Example);
		System.out.println("【重务起动，清理流量信息！！！！！2】");
		if(list!=null && !list.isEmpty()){
			CouchBase cb=CouchBase.getInstance();
			for(Mi001 mi001:list){
				String centerid = mi001.getCenterid();
				String key = centerid+"|Flow";
				Object object = cb.get(key);
				if(object!=null && !"".equals(object.toString())){
					Flow flow = JsonUtil.getGson().fromJson(object.toString(), Flow.class);
					flow.setBussinessflow(0);
					flow.setUserFlow(0);
					List<ChannelFlow> channelList = flow.getChannelbusinesses();
					if(channelList!=null && !channelList.isEmpty()){
						for(int i=0;i<channelList.size();i++){
							ChannelFlow channelFlow = channelList.get(i);
							channelFlow.setCountBusinesses(0);
							channelFlow.setCountUser(0);
						}
					}
//					cb.delete(key);
					cb.save(key, flow);
//					CASValue cas = cb.getLock(key, 10);
//					if ( cas.getCas() != -1 ){
//						cb.delete(key);
//						cb.save(key, flow);
//						cb.unLock(key, cas.getCas());
//					}
				}else{
					Mi045DAO mi045Dao = (Mi045DAO)SpringContextUtil.getBean("mi045DAO");
					Mi045Example mi045Example = new Mi045Example();
					mi045Example.createCriteria().andCenteridEqualTo(centerid)
						.andValidflagEqualTo(Constants.IS_VALIDFLAG);
					List<Mi045> list045 = mi045Dao.selectByExample(mi045Example);
					if(!CommonUtil.isEmpty(list045)){
						CodeListApi001Service codeListApi001Service = (CodeListApi001Service)SpringContextUtil.getBean("codeListApi001Service");
						
						Flow flow = new Flow();
						flow.setCenterid(centerid);
						System.out.println("BussinessflowTOP:"+list045.get(0).getBussinessflow());
						if(!CommonUtil.isEmpty(list045.get(0).getBussinessflow())){
							flow.setBussinessflowTOP(Integer.parseInt(list045.get(0).getBussinessflow()));
						}else{
							flow.setBussinessflowTOP(0);
						}
						System.out.println("BussinessflowTOP:"+list045.get(0).getUserflow());
						if(!CommonUtil.isEmpty(list045.get(0).getUserflow())){
							flow.setUserFlowTop(Integer.parseInt(list045.get(0).getUserflow()));
						}else{
							flow.setUserFlowTop(0);
						}
						flow.setBussinessflow(0);
						flow.setUserFlow(0);
						List<Mi007> list1 = codeListApi001Service.getCodeList(centerid, "channel");
						List<ChannelFlow> channelList = new ArrayList<ChannelFlow>();
						if(list1!=null && !list1.isEmpty()){
							for(int i=0;i<list1.size();i++){
								ChannelFlow channelFlow = new ChannelFlow();
								channelFlow.setChannel(list1.get(i).getItemid());
								channelFlow.setCountBusinesses(0);
								channelFlow.setCountUser(0);
								channelList.add(channelFlow);
							}
						}
						flow.setChannelbusinesses(channelList);
						
						CouchBase couchBase = CouchBase.getInstance();
						couchBase.delete(key);
						couchBase.save(key, flow);
					}
				}
				Object obj = cb.get(key);
				System.out.println(centerid+"~~~obj:"+obj);
			}
		}
		System.out.println("流量清零");
		Mi415DAO mi415Dao = (Mi415DAO)SpringContextUtil.getBean("mi415Dao");
		Mi415Example mi415Example = new Mi415Example();
		mi415Example.createCriteria().andValidflagEqualTo(Constants.IS_VALIDFLAG);
		List<Mi415> list415 = mi415Dao.selectByExample(mi415Example);
		for(int i=0;i<list415.size();i++){
			Mi415 mi415 = list415.get(i);
			mi415.setDsstatus("0");
			mi415Dao.updateByPrimaryKeySelective(mi415);			
		}
		System.out.println("定时MI415状态初始化");
	}		
}
