package com.yondervision.mi.common.filter;
/**
 * 修改记录 * 
 * 编号                          日期                            修改人                                              修改内容
 * #01         2015-01-27      韩占远                                   上传的数据的类型为multipart/form-data ，就说明示有文件长传，这个时候就不记录其他东西了，为了微客服的
 *                                                图片接口修改的。
 *  
 *                                                
 * 
 * 
 * */
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.DEBUG;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.security.SecurityCheck;
import com.yondervision.mi.common.security.SecurityCheckManager;
import com.yondervision.mi.dao.Mi107DAO;
import com.yondervision.mi.dto.Mi107;
import com.yondervision.mi.service.impl.CodeListApi001ServiceImpl;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.Datelet;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.AES;
import com.yondervision.mi.util.security.Base64Decoder;
import com.yondervision.mi.util.security.KeySignature;
import com.yondervision.mi.util.security.RSASignature;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PermissionEncodingFilter implements Filter {
	public static final ThreadLocal threadLocal = new ThreadLocal();
	public static final ThreadLocal threadRequestLocal = new ThreadLocal();
	public static final ThreadLocal threadRequetPath = new ThreadLocal();
	public static final ThreadLocal permissionThreadLocal = new ThreadLocal();
	private FilterConfig filterConfig = null;
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		SecurityCheckManager.init();
	}

	public void destroy() {
		SecurityCheckManager.destroy();
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ParameterRequestWrapper req = getFullPara((HttpServletRequest)request);
		HttpServletResponse rep = (HttpServletResponse)response;
		//校验安全处理2017-06-27syw
		SecurityCheck securityCheck = new SecurityCheck();
		HashMap<String,String> checkMap = securityCheck.securityService(req, rep,filterConfig);
		if(!"000000".equals(checkMap.get("recode")))
		{
			//response.setContentType("text/plain; charset=utf-8");
			//add by Carter King 2017-11-16 建行要求下传格式统一
			response.setContentType("application/json; charset=utf-8");
			response.setCharacterEncoding("utf-8");
			JSONObject obj = new JSONObject();
			obj.put("recode", WEB_ALERT.SELF_ERR
					.getValue());
			obj.put("msg", checkMap.get("msg"));
			response.getWriter().print(obj.toString());
//			throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR
//					.getValue(), checkMap.get("msg"));
			return;
		}
		if(req.getServletPath().startsWith("/webapi80001")||req.getServletPath().startsWith("/webapi80002")||req.getServletPath().startsWith("/webapi80003")
				||req.getServletPath().startsWith("/webapi80004")|| req.getServletPath().startsWith("/webapiInsertMi098")
				||req.getServletPath().startsWith("/webapi80005")||req.getServletPath().startsWith("/webapi80006")
				||req.getServletPath().startsWith("/webapi80007")||req.getServletPath().startsWith("/webapi80008")
				||req.getServletPath().startsWith("/appapi00228")||req.getServletPath().startsWith("/appapi00235")
				||req.getServletPath().startsWith("/appapi00237")||req.getServletPath().startsWith("/webapi80009")
				||req.getServletPath().startsWith("/webapi80010")||req.getServletPath().startsWith("/webapi80015")
				||req.getServletPath().startsWith("/webapi80016")||req.getServletPath().startsWith("/webapi80017")
				||req.getServletPath().startsWith("/webapi80018")||req.getServletPath().startsWith("/webapi80019")
				||req.getServletPath().startsWith("/webapi80020")){
			System.out.println("hello world：徐志文"+req.getServletPath());
			chain.doFilter(req, response);
			return; 
		}
		if(req.getServletPath().startsWith("/appapi00240")||req.getServletPath().startsWith("/appapi00241")||req.getServletPath().startsWith("/appapi00242")||req.getServletPath().startsWith("/appapi00243")
				||req.getServletPath().startsWith("/appapi00244")||req.getServletPath().startsWith("/appapi00245")){
			System.out.println("省厅发布:"+req.getServletPath());
			chain.doFilter(req, response);
			return;
		}
		if(req.getServletPath().startsWith("/indexStatic") || req.getServletPath().startsWith("/classficationStatic")
				 || req.getServletPath().startsWith("/contentStatic")){
			threadRequestLocal.set(req);
			System.out.println("hello world："+req.getServletPath());
			chain.doFilter(req, response); 
			return; 
		}
		if(req.getServletPath().startsWith("/userView")){
			System.out.println("统一客户视图调用，过滤器输出信息："+req.getServletPath());
			chain.doFilter(req, response); 
			return; 
		}
		System.out.println("##########    : "+req.getServletPath());
		if(req.getServletPath().startsWith("/js/3rd/dialogs/link/link.html")||
				req.getServletPath().startsWith("/js/3rd/dialogs/attachment/attachment.html")||
				req.getServletPath().startsWith("/js/3rd/dialogs/preview/preview.html")){
			chain.doFilter(req, response); 
			return;
		}
		
		UserContext user = (UserContext) req.getSession().getAttribute(
				UserContext.USERCONTEXT);
		long ss = System.currentTimeMillis();
		threadLocal.set(user);
		threadRequestLocal.set(req);
		setPathByUser(user, req);
		Logger logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
		
		SimpleDateFormat timeformatter = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSS");
		String log_seq_no_startdate = timeformatter.format(System.currentTimeMillis());
		CheckAppService check = new CheckAppService();
		try {
			
			//#01 开始
			String ct=req.getHeader("Content-Type")!=null?req.getHeader("Content-Type").toLowerCase():"";
			if(ct.startsWith("multipart/form-data")&&(req.getServletPath().startsWith("/appapi90106")
					||req.getServletPath().startsWith("/appapi90415")
					||req.getServletPath().startsWith("/appapi90206") 
					|| req.getServletPath().startsWith("/appapi40107"))){
				/*String IP = req.getHeader("x-forwarded-for");
				if (IP == null || IP.length() == 0) {
					IP = request.getRemoteAddr();
				} 
				Map<String, String[]> commonParam = new HashMap<String, String[]>();
				if(user!=null){
				   commonParam.put("centerId", new String[]{user.getCenterid()});
				   commonParam.put("centreName", new String[]{user.getCenterName()});
				   commonParam.put("userid", new String[]{user.getLoginid()});
				   commonParam.put("username", new String[]{user.getOpername()}); 
				}
				commonParam.put("longinip", new String[]{IP});
				MiHttpRequestWrapper newRequest = new MiHttpRequestWrapper(req, commonParam);
				newRequest.getParameter("centerId");
				chain.doFilter(newRequest, response); */
				chain.doFilter(req, response); 
				return; 
			}
			if (req.getServletPath().endsWith(".html")){
				if(req.getServletPath().indexOf("login.html")<0&&req.getServletPath().indexOf("lout.html")<0){
					if(req.getServletPath().indexOf("vericode.html")>0){
						//
					}else{
						if (CommonUtil.isEmpty(user)){							
							rep.sendRedirect("lout.html");
							return ;
						}
					}
				}
			}
			if (req.getServletPath().startsWith("/webapi")||req.getServletPath().startsWith("/page")||req.getServletPath().startsWith("/ptl")) {
				if(req.getServletPath().startsWith("/webapi500")||req.getServletPath().startsWith("/webapitest01")||req.getServletPath().startsWith("/webapitest01")||req.getServletPath().startsWith("/webapi90001")){
					chain.doFilter(req, response);
					return ;
				}else{
					if (CommonUtil.isEmpty(user)) {
						if(req.getServletPath().endsWith("json")){
							//add by Carter King 2017-11-16 建行要求下传格式统一
							response.setContentType("application/json; charset=utf-8");
							response.setCharacterEncoding("utf-8");
							JSONObject obj = new JSONObject();
							obj.put("recode", WEB_ALERT.LOGIN_TIMEOUT.getValue());
							obj.put("msg", "登录超时，重新登录");
							obj.put("rows", new net.sf.json.JSONArray());
							obj.put("total", Integer.parseInt("0"));
							response.getWriter().print(obj.toString());
						}else{
//						   	req.setAttribute("message", "登录超时，请<a href='login.html' target='_top' >重新登录</a>");
//						   	req.getRequestDispatcher("/WEB-INF/view/platform/error.jsp").forward(req, response);
							rep.sendRedirect("login.html");
						}
						return;
					}
					String IP = req.getHeader("x-forwarded-for");
					if (IP == null || IP.length() == 0) {
						IP = req.getRemoteAddr();
					}
					Map<String, String[]> commonParam = new HashMap<String, String[]>();
					commonParam.put("centerId", new String[]{user.getCenterid()});
					commonParam.put("centreName", new String[]{user.getCenterName()});
					commonParam.put("userid", new String[]{user.getLoginid()});
					commonParam.put("username", new String[]{user.getOpername()});
					commonParam.put("longinip", new String[]{IP});
					
					MiHttpRequestWrapper newRequest = new MiHttpRequestWrapper(req, commonParam);
					newRequest.getParameter("centerId");
					chain.doFilter(newRequest, response);
				}
				
			} else if(req.getServletPath().startsWith("/yfmapapi")){
				System.out.println("YFMAP 调用推送相关功能开始");
				StringBuffer values = new StringBuffer();
				String paras = req.getHeader("headpara");
				String headMD5 = req.getHeader("headparaMD5");
		        String keySignature = req.getHeader("KeySignature");
		        if ((paras == null) || (headMD5 == null) || (keySignature == null)) {
		          System.out.println("paras = " + paras);
		          System.out.println("headMD5 = " + headMD5);
		          System.out.println("keySignature = " + keySignature);
		          throw new TransRuntimeErrorException(ERROR.SYS.getValue(), new String[] { "请求验证信息不正确" });
		        }
		        System.out.println(keySignature);			  
				byte[] data = Base64Decoder.decodeToBytes(req.getHeader("KeySignature"));
				boolean status = KeySignature.verify(headMD5.getBytes(), data, PropertiesReader.getProperty("properties.properties", "certificatePathYfmap").trim());
				System.out.println("签名证书验证："+status);
				if(!status)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "中心前置证书签名验证失败");
				if(paras==null)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "中心前置请求信息包头不正确");
				  
				String[] par = paras.split(",");
					
				for(int i=0;i<par.length;i++){			
					if(i!=0){
						values.append("&");
					}						
					values.append(par[i]+"="+req.getParameter(par[i]));
				}				
				if(headMD5==null)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "中心前置请求信息包头不正确");
				String paraMD5 = EncryptionByMD5.getMD5(values.toString().getBytes("UTF-8"));
				System.out.println("values : "+values.toString());
				System.out.println("paraMD5 : "+paraMD5);
				System.out.println("headMD5 : "+headMD5);
				if(RSASignature.doCheck(paraMD5, headMD5, RSASignature.RSA_ALIPAY_PUBLIC)){            
			        System.out.println("APP前置==============================APP签名验证成功");
			    }else{
			    	System.out.println("APP前置==============================APP签名验证失败");
			        throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "中心前置请求APP签名验证不正确");
			    }	
				chain.doFilter(req, response);
			}else if (req.getServletPath().startsWith("/appapi99901")||req.getServletPath().startsWith("/appapi30314")
					||req.getServletPath().startsWith("/appapi30315")||req.getServletPath().startsWith("/appapi30292")
					||req.getServletPath().startsWith("/appapi30291")||req.getServletPath().startsWith("/appapi30316")
					||req.getServletPath().startsWith("/appapi50023")||req.getServletPath().startsWith("/appapi07030")
					||req.getServletPath().startsWith("/appapi00199")||req.getServletPath().startsWith("/appapi00144")
					||req.getServletPath().startsWith("/appapi00145")||req.getServletPath().startsWith("/appapi00158")
					||req.getServletPath().startsWith("/appapi00227")||req.getServletPath().startsWith("/appapi00239")
					||req.getServletPath().startsWith("/appapi00337")||req.getServletPath().startsWith("/appapi00338")
					||req.getServletPath().startsWith("/appapi00339")||req.getServletPath().startsWith("/appapi00341")
					||req.getServletPath().startsWith("/appapi00342")){
				chain.doFilter(req, response); 
			}else if (req.getServletPath().startsWith("/synchro")){
				System.out.println("第三方 调用内容同步导入数据相关功能开始");
				StringBuffer values = new StringBuffer();
				String paras = req.getHeader("headpara");
				String headMD5 = req.getHeader("headparaMD5");
		        String keySignature = req.getHeader("KeySignature");
		        if ((paras == null) || (headMD5 == null) || (keySignature == null)) {
		        	System.out.println("paras = " + paras);
		        	System.out.println("headMD5 = " + headMD5);
		        	System.out.println("keySignature = " + keySignature);
		        	throw new TransRuntimeErrorException(ERROR.SYS.getValue(), new String[] { "请求验证信息不正确" });
		        }
		        System.out.println("paras has value = " + paras);
		        System.out.println("headMD5 has value = " + headMD5);
		        System.out.println("keySignature  has value = " + keySignature);	  
				byte[] data = Base64Decoder.decodeToBytes(keySignature);
				boolean status = KeySignature.verify(headMD5.getBytes(), data, PropertiesReader.getProperty("properties.properties", "certificatePathYfmap").trim());
				System.out.println("签名证书验证："+status);
				if(!status)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "第三方平台证书签名验证失败");
				if(paras==null)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "第三方平台请求信息包头不正确");
				  
				String[] par = paras.split(",");
					
				for(int i=0;i<par.length;i++){			
					if(i!=0){
						values.append("&");
					}						
					values.append(par[i]+"="+req.getParameter(par[i]));
				}				
				if(headMD5==null)
					throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "第三方平台请求信息包头不正确");
				String paraMD5 = EncryptionByMD5.getMD5(values.toString().getBytes("UTF-8"));
				System.out.println("values : "+values.toString());
				System.out.println("paraMD5 : "+paraMD5);
				System.out.println("headMD5 : "+headMD5);
				if(RSASignature.doCheck(paraMD5, headMD5, RSASignature.RSA_ALIPAY_PUBLIC)){            
			        System.out.println("移动互联平台==============================第三方平台签名验证成功");
			    }else{
			    	System.out.println("移动互联平台==============================第三方平台签名验证失败");
			        throw new TransRuntimeErrorException(ERROR.SYS.getValue(), "第三方平台请求签名验证不正确");
			    }
				chain.doFilter(req, response);
			}else{
				
				HttpServletRequest request1=(HttpServletRequest) req;
				HashMap m=new HashMap(req.getParameterMap());
				AES aes = new AES();//"00087100","10","1","1"
				System.out.println("用户名 USERID :"+req.getParameter("userId"));
				System.out.println("用户名 buzType :"+req.getParameter("buzType"));
				System.out.println("用户名 channel :"+req.getParameter("channel"));
				System.out.println("用户名 appid :"+req.getParameter("appid"));
				System.out.println("用户名 appkey :"+req.getParameter("appkey"));
				System.out.println("城市中心:"+req.getParameter("centerId"));
				if(request1.getServletPath().startsWith("/appapi")){
					aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
				}
				
				if(!CommonUtil.isEmpty(req.getParameter("userId"))){
					String usid = (String)req.getParameter("userId");
					m.remove("userId");
					//java.net.URLDecoder.decode()
					System.out.println("********** usrid :"+usid+" URLDecoder :"+usid);
					//针对住建委网站加密问题修改 xzw
					if (!"30".equals(req.getParameter("channel"))) {
						m.put("userId", aes.decrypt(usid));
					}else{
						m.put("userId",usid);
					}
				}
//				if(!CommonUtil.isEmpty(m.get("surplusAccount"))){
//					String idcNumber = (String)request.getParameter("surplusAccount");
//					m.remove("surplusAccount");
//					System.out.println("********** surplusAccount :"+idcNumber+" URLDecoder :"+java.net.URLDecoder.decode(idcNumber));
//					m.put("surplusAccount", aes.decrypt(idcNumber));
//				}
				ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(request1,m);
				
				//String centeridStrs = PropertiesReader.getProperty("properties.properties", "WRITE_BUSINESS_LOG_TABLE");
				
				// 手机登陆用户(/appapi)的请求，向日志表中进行添加记录
				if(request1.getServletPath().startsWith("/appapi") || request1.getServletPath().startsWith("/yfapi")) {
					
					/**
					 * 20160308 解决记录日志问题，统一插入，不作更新
					// 向业务日志表进行登记
					boolean dealFlg = insertLogTable(wrapRequest, response);
					if(!dealFlg){
						return;
					}
					*/			
					
					
					check.appservice(aes ,req, rep, user);
				}
				
				String jsoncallback = req.getParameter("callback");
				
				if(request1.getServletPath().startsWith("/appapi") || request1.getServletPath().startsWith("/newsdetail")){
					String appFilterServer = PropertiesReader.getProperty("properties.properties", "appFilterServer");
					if(appFilterServer.indexOf(request1.getServletPath())>=0){
						if(jsoncallback==null||jsoncallback==""){
							chain.doFilter(req, response);
						}else{
							WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse)response); 
							chain.doFilter(req, wrapResponse); 
							byte[] data = wrapResponse.getResponseData(); 
							System.out.println("-----filter--1---start-------");
							System.out.println("原始数据： " + new String(data, "UTF-8"));
							String dataTmp = new String(data, "UTF-8");
							int endNum = dataTmp.indexOf(";callback");
							System.out.println("-----filter--1---end-------");
							String tempData = dataTmp.substring(0, endNum); 
							String encoding = "";
							if (CommonUtil.isEmpty(req.getCharacterEncoding())) {
								encoding = "UTF-8";
							}else {
								encoding = req.getCharacterEncoding();
							}

							response.setCharacterEncoding("utf-8");
							response.setContentType("text/html;charset=UTF-8");
							ServletOutputStream out = response.getOutputStream(); 

							if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){
								//add by Carter King 2017-11-16 建行要求下传格式统一
								response.setContentType("application/json; charset=utf-8");
								out.write(aes.encrypt(tempData.getBytes(encoding)).getBytes(encoding)); 
							}else{
								out.write(tempData.getBytes(encoding)); 
							}

							out.flush(); 
							out.close();
						}
					}else{
						if(jsoncallback==null||jsoncallback==""){
							chain.doFilter(wrapRequest, response);
						}else{
							WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse)response); 
							chain.doFilter(wrapRequest, wrapResponse); 
							byte[] data = wrapResponse.getResponseData(); 
							System.out.println("-----filter--2---start-------");
							System.out.println("原始数据： " + new String(data, "UTF-8"));
							String dataTmp = new String(data, "UTF-8");
							int endNum = dataTmp.indexOf(";callback");
							System.out.println("-----filter--2---end-------");
							String tempData = dataTmp.substring(0, endNum); 
							String encoding = "";
							if (CommonUtil.isEmpty(req.getCharacterEncoding())) {
								encoding = "UTF-8";
							}else {
								encoding = req.getCharacterEncoding();
							}

							response.setCharacterEncoding("utf-8");
							ServletOutputStream out = response.getOutputStream(); 
							if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){
								//add by Carter King 2017-11-16 建行要求下传格式统一
								response.setContentType("application/json; charset=utf-8");
								out.write(aes.encrypt(tempData.getBytes(encoding)).getBytes(encoding)); 
							}else{
								response.setContentType("text/html;charset=UTF-8");
								out.write(tempData.getBytes(encoding)); 
							}
							out.flush(); 
							out.close();
						}
					}					
				}else{
					if(jsoncallback==null||jsoncallback==""){
						chain.doFilter(wrapRequest, response);
					}else{
						WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse)response); 
						chain.doFilter(wrapRequest, wrapResponse); 
						byte[] data = wrapResponse.getResponseData(); 
						System.out.println("-----filter-3----start-------");
						System.out.println("原始数据： " + new String(data, "UTF-8"));
						String dataTmp = new String(data, "UTF-8");
						int endNum = dataTmp.indexOf(";callback");
						System.out.println("-----filter--3---end-------");
						String tempData = dataTmp.substring(0, endNum); 
						String encoding = "";
						if (CommonUtil.isEmpty(req.getCharacterEncoding())) {
							encoding = "UTF-8";
						}else {
							encoding = req.getCharacterEncoding();
						}

						response.setCharacterEncoding("utf-8");
						ServletOutputStream out = response.getOutputStream(); 
						if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){			
							//add by Carter King 2017-11-16 建行要求下传格式统一
							response.setContentType("application/json; charset=utf-8");
							out.write(aes.encrypt(tempData.getBytes(encoding)).getBytes(encoding)); 
						}else{
							response.setContentType("text/html;charset=UTF-8");
							out.write(tempData.getBytes(encoding)); 
						}
						out.flush(); 
						out.close();
					}
					
				}				       
				
				// 手机端的请求，更新业务日志表
				if(req.getServletPath().startsWith("/appapi") || req.getServletPath().startsWith("/yfapi")) {
//					check.minusFlow(request.getParameter("centerId"),request.getParameter("channel"));
					insertLogTable(check.getPid() ,req,response,"0","正常处理完毕", log_seq_no_startdate);
//					 更新业务日志表
//					updLogTable(req, "0", "正常处理完毕");	
				}
			}
		} catch (NestedServletException e2) {
			if (!(e2.getRootCause() instanceof TransRuntimeErrorException)) {
				e2.getRootCause().printStackTrace();
				logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
				logger.error(e2);
			}
			if (req.getServletPath().endsWith("json")) {
				String mes = e2.getMessage();
				int wz = mes.indexOf("Exception:");
				if (wz > 0)
					wz += 10;

				response.setCharacterEncoding("utf-8");
				JSONObject obj = new JSONObject();
				if(e2.getRootCause() instanceof TransRuntimeErrorException){
					obj.put("recode", ((TransRuntimeErrorException)e2.getRootCause()).getErrcode());
				} else {
					obj.put("recode", ERROR.SYS.getValue());
				}
				String description = null;
				if (wz > 0) {
					obj.put("msg", mes.substring(wz));
					description = mes.substring(wz);
				}
				else{
					obj.put("msg", mes);
					description = mes;
				}

				obj.put("rows", new net.sf.json.JSONArray());
				obj.put("total", Integer.parseInt("0"));
				
				//add  by Carter King 2018-03-16
				// 手机端请求、MI前置请求更新业务日志表
				if(req.getServletPath().startsWith("/appapi99901")){
					// 什么也不做
				}else if((req.getServletPath().startsWith("/appapi")||req.getServletPath().startsWith("/yfapi"))
						&& !WEB_ALERT.BUZ_LOG_SYS.getValue().equals(obj.getString("recode"))) {
					
					try {
						insertLogTable(check.getPid() ,req ,response,"1",description, log_seq_no_startdate);
//						 更新业务日志表
//						updLogTable(req, "1", description);
						
					} catch (Exception e) {
						logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
						String errcode = null;
						String errText = null;
						if(e instanceof TransRuntimeErrorException){
							TransRuntimeErrorException tre = (TransRuntimeErrorException)e;
							errcode = tre.getErrcode();
							errText = tre.getMessage();
						}else{
							e.printStackTrace();
							logger.error(e);
							
							errcode = ERROR.SYS.getValue();
							errText = "系统错误，请联系管理员！";
						}
						obj = buzLogResObj(errcode, errText);
					}
				}
				String jsoncallback = req.getParameter("callback");
				AES aes;
				try {
					if(jsoncallback==null||jsoncallback==""){					
						if(req.getServletPath().startsWith("/appapi")&&(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42"))){
							//add by Carter King 2017-11-16 建行要求下传格式统一
							response.setContentType("application/json; charset=utf-8");
							aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
							response.getWriter().print(aes.encrypt(obj.toString().getBytes("UTF-8")));
						}else{
							response.setContentType("text/plain; charset=utf-8");
							response.getWriter().print(obj.toString());
						}
					}else{
						if(req.getServletPath().startsWith("/appapi")&&(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42"))){
							//add by Carter King 2017-11-16 建行要求下传格式统一
							response.setContentType("application/json; charset=utf-8");
							aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
							response.getWriter().print( aes.encrypt((jsoncallback+"("+obj.toString()+")").getBytes("UTF-8")));
						}else{
							response.setContentType("text/plain; charset=utf-8");
							response.getWriter().print(jsoncallback+"("+obj.toString()+")");
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if ("/webapi30201_uploadimg.do".equals(req.getServletPath())
					|| "/webapi30201_uploadAudio.do".equals(req.getServletPath())
					|| "/webapi30201_uploadVideo.do".equals(req.getServletPath())) {
				String mes = e2.getMessage();
				String errcode = null;
				String errText = null;
				int wz = mes.indexOf("Exception:");
				if (wz > 0)
					wz += 10;
				response.setCharacterEncoding("utf-8");
				JSONObject obj = new JSONObject();
				if(e2.getRootCause() instanceof TransRuntimeErrorException){
					//obj.put("recode", ((TransRuntimeErrorException)e2.getRootCause()).getErrcode());
					errcode = ((TransRuntimeErrorException)e2.getRootCause()).getErrcode();
				} else {
					//obj.put("recode", ERROR.SYS.getValue());
					errcode = ERROR.SYS.getValue();
				}
				String description = null;
				if (wz > 0) {
//					obj.put("msg", mes.substring(wz));
//					description = mes.substring(wz);
					errText = mes.substring(wz);
				}
				else{
//					obj.put("msg", mes);
//					description = mes;
					errText = mes;
				}
				obj = buzLogResObj(errcode, errText);
				response.setContentType("text/plain; charset=utf-8");
				response.getWriter().print(obj.toString());
			} else {
				String msg = null;
				if (e2.getRootCause() instanceof TransRuntimeErrorException) {
					msg = ((TransRuntimeErrorException)e2.getRootCause()).getMessage();
				} else {
					msg = "系统错误，请联系管理员！";
				}
				req.setAttribute("message", msg);
				req.getRequestDispatcher("/WEB-INF/view/platform/error.html").forward(req, response);
				return;
			}

		} catch (Exception e) {
			String errcode=ERROR.SYS.getValue();
			if ((e instanceof TransRuntimeErrorException)) {	
				TransRuntimeErrorException e2=(TransRuntimeErrorException)e;
				errcode=e2.getErrcode();
				logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
				logger.error(e);
			}else{
				e.printStackTrace();
				logger.error(e);
			}	
			if (req.getServletPath().endsWith("json")) {
				String mes = e.getMessage();
				if (mes == null)
					mes = "空null";
				int wz = mes.indexOf("Exception:");
				if (wz > 0)
					wz += 10;
				response.setCharacterEncoding("utf-8");
				JSONObject obj = new JSONObject();
				obj.put("recode", errcode);
				String description = null;
				if (wz > 0){
					obj.put("msg", mes.substring(wz));
					description = mes.substring(wz);
				}else{
					obj.put("msg", mes);
					description = mes;
				}
				obj.put("rows", new net.sf.json.JSONArray());
				obj.put("total", Integer.parseInt("0"));
				
				// 手机端请求、MI前置请求更新业务日志表
				if(req.getServletPath().startsWith("/appapi99901")){
					//什么也不做
				}else if((req.getServletPath().startsWith("/appapi")||req.getServletPath().startsWith("/yfapi"))
						&& !WEB_ALERT.BUZ_LOG_SYS.getValue().equals(obj.getString("recode"))) {
					try {
						insertLogTable(check.getPid() ,req,response,"1",description, log_seq_no_startdate);
						
					} catch (Exception e1) {
						logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();

						String errcodeTmp = null;
						String errText = null;
						if(e instanceof TransRuntimeErrorException){
							TransRuntimeErrorException tre = (TransRuntimeErrorException)e;
							errcodeTmp = tre.getErrcode();
							errText = tre.getMessage();
						}else{
							e.printStackTrace();
							logger.error(e);
							
							errcodeTmp = ERROR.SYS.getValue();
							errText = "系统错误，请联系管理员！";
						}
						obj = buzLogResObj(errcodeTmp, errText);
					}
				}
				AES aes;
				try {
					if(req.getServletPath().startsWith("/appapi")&&(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42"))){
						//add by Carter King 2017-11-16 建行要求下传格式统一
						response.setContentType("application/json; charset=utf-8");
						aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
						response.getWriter().print(aes.encrypt(obj.toString().getBytes("UTF-8")));
					}else{
						response.setContentType("text/plain; charset=utf-8");
						response.getWriter().print(obj.toString());
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			} else { 
				String msg = null;
				if (e instanceof TransRuntimeErrorException) {
					TransRuntimeErrorException e2=(TransRuntimeErrorException)e;
					msg = e2.getMessage();
				} else {
					msg = "系统错误，请联系管理员！";
				}
				
				req.setAttribute("message", msg);
				req.getRequestDispatcher("/WEB-INF/view/platform/error.html").forward(req, response);
			}

		}finally{
			if(req.getServletPath().startsWith("/appapi") || req.getServletPath().startsWith("/yfapi")) {
				if("/appapi90106.json,/appapi90415.json,/appapi90206.json,/appapi40107.json,/appapi99901.json,/appapi30314.json,/appapi30316.json,/appapi30315.json,/appapi30292.json,/appapi30291.json,/appapi00199.json,/appapi00144.json,/appapi00145.json,/appapi00158.json,/appapi62591.json,/appapi62592.json,/appapi62593.json,/appapi50023.json,/appapi07030.json,/appapi00227.json,/appapi00239.json,/appapi00337.json,/appapi03824.json,/appapi00338.json,/appapi00339.json,/appapi00341.json,/appapi00342.json".indexOf(req.getServletPath())<0){
					check.minusFlow(req.getParameter("centerId"),req.getParameter("channel"));
				}
			}
		}
	}
	
	public static ParameterRequestWrapper getFullPara(HttpServletRequest req){
		HashMap m = new HashMap();
		m.putAll(req.getParameterMap());
		if("1".equals(req.getParameter("AESFlag"))){
			AES aes = new AES();
			try{
				aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
				String[] fullPara = aes.decrypt(req.getParameter("fullPara")).split("&");
				for(int i=0; i<fullPara.length; i++){
					String[] para = fullPara[i].split("=");
					if("userId".equals(para[0]) || "appid".equals(para[0]) || "appkey".equals(para[0])){
						if(para.length<2){
							m.put(para[0], "");
						}else{
							m.put(para[0], aes.encrypt(para[1].getBytes("UTF-8")));
						}
					}else{
						if(para.length<2){
							m.put(para[0], "");
						}else{
							m.put(para[0], para[1]);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		ParameterRequestWrapper wrapRequest=new ParameterRequestWrapper(req,m);
		return wrapRequest;
	}

	public static void setPathByUser(UserContext user,
			HttpServletRequest request) {
		String path = null;
		String qd = null;
		// 手机端请求
		if (request.getServletPath().startsWith("/appapi")){
			qd = "appapi";
		// 中心前置请求
		}else if(request.getServletPath().startsWith("/yfapi")){
			qd = "yfapi";
		// 后台web请求
		}else{
			qd = "manager";
		}

		if (user == null) {
			String centerId = request.getParameter("centerId");
			if (centerId == null) {
				path = Datelet.getCurrentDateString() + "/nocenter/nouser.log";
			} else {
				path = Datelet.getCurrentDateString() + "/" + centerId
						+ "/nouser.log";
			}
		} else {
			path = Datelet.getCurrentDateString() + "/" + user.getCenterid()
					+ "/" + user.getLoginid().trim() + ".log";
		}
		threadRequetPath.set(qd+"/"+path);
	}
	
	/**
	 * 业务日志表相关操作，异常、错误时，返回对象的封装（业务日志表只对APP的后缀.json的请求进行记录）
	 * @param errcode
	 * @param errText
	 * @return
	 */
	private JSONObject buzLogResObj(String errcode, String errText){

		JSONObject obj = new JSONObject();
		obj.put("recode", errcode);
		obj.put("msg", errText);
		obj.put("rows", new net.sf.json.JSONArray());
		obj.put("total", Integer.parseInt("0"));
		return obj;
	}
	
	
	
	/**
	 * 向业务日志表插入一条记录(压力测试问题解决)
	 * @param req
	 * @throws Exception
	 */
	private boolean insertLogTable(String pid ,HttpServletRequest req, ServletResponse response,String stat, String mess, String log_seq_no_startdate){
		Logger logger = com.yondervision.mi.common.log.LoggerUtil.getLogger();
		
		response.setCharacterEncoding("utf-8");
		String centerId = req.getParameter("centerId");
		String userId = req.getParameter("userId");
		String usertype = req.getParameter("usertype");
		String deviceType = req.getParameter("deviceType");
		String deviceToken = req.getParameter("deviceToken");
		String currenVersion = req.getParameter("currenVersion");
		String buzType = req.getParameter("buzType");
		String channel=req.getParameter("channel");
		String clientIp = req.getParameter("clientIp");
		String macaddress = req.getParameter("macaddress");
//		String pid = (String) req.getAttribute("MI040Pid");
		String appid = (String) req.getAttribute("MI040Appid");
		String money1 = (String) req.getAttribute("money");
		Double money2 = 0.0;
		String acc_flag = (String) req.getAttribute("acc_flag");
		String acc_amt = (String) req.getAttribute("acc_amt");
		System.out.println("filet资金类业务处理结果，是否记账：【"+acc_flag+"】");
		System.out.println("filet资金类业务处理结果，记账金额：【"+acc_amt+"】");
		if(!CommonUtil.isEmpty(acc_flag)){
			if("1".equals(acc_flag)){
				System.out.println("资金类业务记录日志表金额："+acc_amt+",渠道："+channel);
				money2 = Double.parseDouble(acc_amt);
			}
		}
		
//		System.out.println("日志表 centerId："+centerId);
//		System.out.println("日志表 userId："+userId);
//		System.out.println("日志表 usertype："+usertype);
//		System.out.println("日志表 deviceType："+deviceType);
//		System.out.println("日志表 deviceToken："+deviceToken);
//		System.out.println("日志表 currenVersion："+currenVersion);
//		System.out.println("日志表 buzType："+buzType);
//		System.out.println("日志表 channel："+channel);
//		System.out.println("日志表 clientIp："+clientIp);
//		System.out.println("日志表 macaddress："+macaddress);
//		System.out.println("日志表 appid："+appid);
//		System.out.println("日志表 pid："+pid);
//		System.out.println("日志表 money："+money2);
		
		try{
			// 空值判断
			if(CommonUtil.isEmpty(centerId)){
				logger.error(ERROR.PARAMS_NULL.getLogText("centerId"));
				JSONObject obj = buzLogResObj(WEB_ALERT.PARAMS_NULL.getValue(), "请输入【中心ID】");
				AES aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
				if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){
					//add by Carter King 2017-11-16 建行要求下传格式统一
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().print(aes.encrypt(obj.toString().getBytes("UTF-8")));
				}else{
					response.getWriter().print(obj.toString());
				}
				return false;
			}			
			if(CommonUtil.isEmpty(buzType)){
				logger.error(ERROR.PARAMS_NULL.getLogText("buzType"));
				JSONObject obj = buzLogResObj(WEB_ALERT.PARAMS_NULL.getValue(), "请输入【业务类型】");
				AES aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
				if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){
					//add by Carter King 2017-11-16 建行要求下传格式统一
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().print(aes.encrypt(obj.toString().getBytes("UTF-8")));
				}else{
					response.getWriter().print(obj.toString());
				}
				return false;
			}			
			Mi107 mi107 = new Mi107();
			SimpleDateFormat datamatter = new SimpleDateFormat("yyyy-MM-dd");
			int seqno = CommonUtil.genKeyStatic("MI107").intValue();
			
			System.out.println("######   YCMAP 写日志取参"+log_seq_no_startdate);
//			String appdate = (String)req.getParameter("LOG_SEQ_NO_STARTDATE");
			String appdate = log_seq_no_startdate;
			
			AES aes = new AES((String)req.getParameter("centerId") ,(String)req.getParameter("channel") ,null ,null);
			appdate = appdate.substring(0,10)+" "+appdate.substring(10);
			System.out.println("######   YCMAP 写日志取参"+appdate);

			CodeListApi001ServiceImpl codeListApi001Service = (CodeListApi001ServiceImpl)
			com.yondervision.mi.util.SpringContextUtil.getBean("codeListApi001Service");			
			String buzName = "";
			if (req.getServletPath().startsWith("/appapi")){
				buzName = codeListApi001Service.getCodeVal(centerId, "apptranstype."+buzType);
			}		
			if (CommonUtil.isEmpty(buzName)) {
				logger.error(ERROR.NO_DATA.getLogText("码表中","编码=apptranstype."+buzType));
				JSONObject obj = buzLogResObj(WEB_ALERT.VALUE_ERR.getValue(), "【业务类型】输入不合法，请确认后再提交");
				if(req.getParameter("channel").trim().equals("40")||req.getParameter("channel").trim().equals("42")){
					//add by Carter King 2017-11-16 建行要求下传格式统一
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().print(aes.encrypt(obj.toString().getBytes("UTF-8")));
				}else{
					response.getWriter().print(obj.toString());
				}
				return false;
			}
			SimpleDateFormat timesformatter1 = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss.SSS");
			Date date = timesformatter1.parse(log_seq_no_startdate);
			mi107.setSeqno(seqno);
			mi107.setCenterid(centerId);
			//针对住建委网站加密问题修改 xzw
			if (!"30".equals(req.getParameter("channel"))) {
				mi107.setUserid(CommonUtil.isEmpty(userId)?"":aes.decrypt(userId));//可为空
			}else{
				mi107.setUserid(CommonUtil.isEmpty(userId)?"":userId);//可为空
			}
			mi107.setChanneltype(channel);//有
			mi107.setTransdate(datamatter.format(System.currentTimeMillis()));
			mi107.setTranstype(CommonUtil.isEmpty(buzType)?"":buzType);
			mi107.setTransname(CommonUtil.isEmpty(buzName)?"":buzName);
			mi107.setVersionno(CommonUtil.isEmpty(currenVersion)?"1.0":currenVersion);
			mi107.setTranstime(CommonUtil.getHour());			
			mi107.setDevtype(CommonUtil.isEmpty(deviceType)?"":deviceType);
			mi107.setDevid(CommonUtil.isEmpty(deviceToken)?"":deviceToken);
			
			
			mi107.setRequesttime(appdate==null?timesformatter1.format(date):appdate);
			mi107.setResponsetime(timesformatter1.format(System.currentTimeMillis()));
			Date reqDate = new Date();			
			Long secondsafter = reqDate.getTime() - date.getTime();
			mi107.setSecondsafter(Integer.valueOf(secondsafter.intValue()));
			mi107.setValidflag(Constants.IS_VALIDFLAG);
			mi107.setDatemodified(new Date());
			mi107.setMoney(money2);
			mi107.setAppid(CommonUtil.isEmpty(appid)?"":appid);
			mi107.setPid(CommonUtil.isEmpty(pid)?"":pid);
			mi107.setUsertype(CommonUtil.isEmpty(usertype)?"":usertype);
			mi107.setIpaddress(CommonUtil.isEmpty(clientIp)?"":(clientIp.length()>40?clientIp.substring(0, 40):clientIp));
//			mi107.setMacaddress(macaddress);
			
			mi107.setFreeuse1(CommonUtil.isEmpty(stat)?"":(stat.length()>40)?stat.substring(0, 40):stat);
			mi107.setFreeuse2(CommonUtil.isEmpty(mess)?"":(mess.length()>200)?mess.substring(0, 200):mess);
			mi107.setFreeuse3(req.getServletPath());
			
			Mi107DAO mi107Dao = (Mi107DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi107Dao");
			logger.debug(DEBUG.SHOW_PARAM.getLogText(CommonUtil
					.getStringParams(mi107)));
			
			
			System.out.println("=========日志表 mi107.getSeqno()："+mi107.getSeqno());
			System.out.println("=========日志表 mi107.getCenterid()："+mi107.getCenterid());
			System.out.println("=========日志表 mi107.getUserid()："+mi107.getUserid());
			System.out.println("=========日志表 mi107.getChanneltype()："+mi107.getChanneltype());
			System.out.println("=========日志表 mi107.getTransdate()："+mi107.getTransdate());
			System.out.println("=========日志表 mi107.getTranstype()："+mi107.getTranstype());
			System.out.println("=========日志表 mi107.getTransname()："+mi107.getTransname());
			System.out.println("=========日志表 mi107.getVersionno()："+mi107.getVersionno());
			System.out.println("=========日志表 mi107.getDevtype()："+mi107.getDevtype());
			System.out.println("=========日志表 mi107.getDevid()："+mi107.getDevid());
			System.out.println("=========日志表 mi107.getRequesttime()："+mi107.getRequesttime());
			System.out.println("=========日志表 mi107.getResponsetime()："+mi107.getResponsetime());
			System.out.println("=========日志表 mi107.getSecondsafter()："+mi107.getSecondsafter());
			System.out.println("=========日志表 mi107.getValidflag()："+mi107.getValidflag());
			System.out.println("=========日志表 mi107.getDatemodified()："+mi107.getDatemodified());
			System.out.println("=========日志表 mi107.getTranstime()："+mi107.getTranstime());
			System.out.println("=========日志表 mi107.getMoney()："+mi107.getMoney());
			System.out.println("=========日志表 mi107.getAppid()："+mi107.getAppid());
			System.out.println("=========日志表mi107.getPid()："+mi107.getPid());
			System.out.println("=========日志表 mi107.getUsertype()："+mi107.getUsertype());
			System.out.println("=========日志表 mi107.getIpaddress()："+mi107.getIpaddress());
			System.out.println("=========日志表mi107.getMacaddress()："+mi107.getMacaddress());
			System.out.println("=========日志表mi107.getFreeuse1()："+mi107.getFreeuse1());
			System.out.println("=========日志表mi107.getFreeuse2()："+mi107.getFreeuse2());
			System.out.println("=========日志表mi107.getFreeuse3()："+mi107.getFreeuse3());
			System.out.println("=========日志表mi107.getFreeuse4()："+mi107.getFreeuse4());
			
			
			mi107Dao.insert(mi107);
		}catch(IOException e){
			e.printStackTrace();
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.BUZ_LOG_SYS.getValue(), "IOException:向response写入应答信息发生异常");
//			throw tre;
		}catch(Exception e){
			e.printStackTrace();
//			TransRuntimeErrorException tre = new TransRuntimeErrorException(
//					WEB_ALERT.BUZ_LOG_SYS.getValue(), "业务日志表登记记录发生异常");
//			throw tre;
		}

		return true;
	}
	
}