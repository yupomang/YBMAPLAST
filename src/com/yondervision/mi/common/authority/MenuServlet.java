/**
 * 
 */
package com.yondervision.mi.common.authority;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.mi.common.PermissionContext;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.dao.Mi005DAO;
import com.yondervision.mi.dto.Mi005;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.util.SpringContextUtil;


/**
 * 菜单Servlet
 */
public class MenuServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303743138513767719L;

	/**
	 * Constructor of the object.
	 */
	public MenuServlet() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest request, HttpServletResponse response)throws IOException {
		String requestUrl = request.getServletPath().substring(1, 17);
		
		PermissionContext permissionContext = new PermissionContext(requestUrl);
		request.getSession().setAttribute(PermissionContext.PERMISSIONCONTEXT, permissionContext);
		PermissionEncodingFilter.permissionThreadLocal.set(permissionContext); 
		
		String funcid = requestUrl.substring(0, 8);
		
		UserContext user = (UserContext) request.getSession().getAttribute(
				UserContext.USERCONTEXT);
		
		
		// 查询Mi005，获取跳转url
	    Mi005DAO mi005Dao =	(Mi005DAO)SpringContextUtil.getBean("mi005Dao");
		Mi005Example example = new Mi005Example();
		example.createCriteria().andFuncidEqualTo(funcid);
		if(user!=null){
			example.createCriteria().andCenteridEqualTo(user.getCenterid());
		}
		List<Mi005> mi005List = mi005Dao.selectByExample(example);
		String url = mi005List.get(0).getUrl();
		if(!url.startsWith("/")){
			url = "/" + url;
		}
		if(request.getRequestURL().indexOf("124.202.206.82")>=0){
			response.sendRedirect("http://124.202.206.82"+request.getContextPath()+url);
		}else{
			response.sendRedirect(request.getContextPath()+url);
		}
		
	}
}
