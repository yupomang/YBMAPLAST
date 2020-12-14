/**
 * 
 */
package com.yondervision.mi.common.authority;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi005DAO;
import com.yondervision.mi.dao.Mi057DAO;
import com.yondervision.mi.dto.Mi005Example;
import com.yondervision.mi.dto.Mi057;
import com.yondervision.mi.dto.Mi057Example;

/**
 * URL访问权限控制处理
 * @author gongqi
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

	private String mappingURL = null;
	
	@Autowired
	private Mi005DAO mi005Dao;
	
	@Autowired
	private Mi057DAO mi057DAO;

	/**
	 * @return the mappingURL
	 */
	public String getMappingURL() {
		return mappingURL;
	}

	/**
	 * @param mappingURL the mappingURL to set
	 */
	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}

	/**
	 * @return the mi005Dao
	 */
	public Mi005DAO getMi005Dao() {
		return mi005Dao;
	}

	/**
	 * @param mi005Dao the mi005Dao to set
	 */
	public void setMi005Dao(Mi005DAO mi005Dao) {
		this.mi005Dao = mi005Dao;
	}
	
	
	public Mi057DAO getMi057DAO() {
		return mi057DAO;
	}

	public void setMi057DAO(Mi057DAO mi057DAO) {
		this.mi057DAO = mi057DAO;
	}

	/**
	 * 预处理
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Logger log = LoggerUtil.getLogger();
		
		String url = request.getServletPath().substring(1);
		//if (mappingURL == null || url.matches(mappingURL))
		if(url.startsWith("userView")){
			String referer = request.getHeader("referer");
			if(referer.indexOf("?")>=0){
				referer = referer.substring(0, referer.indexOf("?"));
			}			
			System.out.println("统一客户视图访问者 referer : "+referer);
			Mi057Example example = new Mi057Example();
			
		    Matcher slashMatcher = Pattern.compile("/").matcher(referer);
		    int mIdx = 0;
		    while(slashMatcher.find()) {
		       mIdx++;
		       //当"/"符号第三次出现的位置
		       if(mIdx == 3){
		          break;
		       }
		    }
		    if(mIdx==3){
		    	example.createCriteria().andRefererLike("%"+referer.substring(0, slashMatcher.start())+"%");
		    }else{
		    	example.createCriteria().andRefererLike("%"+referer+"%");
		    }
		    
			
		    example.createCriteria().andStartrefererEqualTo("1");
			
			
			List<Mi057> list = mi057DAO.selectByExample(example);
			if (list==null||list.size()==0) {
				log.info(LOG.SELF_LOG.getLogText("对不起，您无权访问，请联系管理人员！"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "对不起，您无权访问，请联系管理人员！");
			}			
			
			return true; 
		}
		if (url.contains(".html")) {
			// 获取当前登录用户信息
			UserContext user = (UserContext) request.getSession().getAttribute(
					UserContext.USERCONTEXT);
			// 判断当前URL是否是在权限控制维护范围内
			Mi005Example example = new Mi005Example();
			example.createCriteria().andUrlEqualTo(url);
			if(user!=null){
				example.createCriteria().andCenteridEqualTo(user.getCenterid());//综合服务平台加入中心代码
			}
			
			int count = mi005Dao.countByExample(example);
			if (0 == count) {
				return true;
			}			
			if (null == user) {
				log.info(LOG.SELF_LOG.getLogText("用户未登录或登录超时"));
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "用户未登录或登录超时");
			}else{
				// 校验当前访问的URL是否在当前用户被允许访问的菜单项中
				boolean checkFlg = user.checkFuncUrlExist(url);
				if (!checkFlg) {
					log.info(LOG.SELF_LOG.getLogText("当前用户无访问权限"));
					throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(), "当前用户无访问权限");
				}
			}
			
			
			
		}
		return true;    
	}
}
