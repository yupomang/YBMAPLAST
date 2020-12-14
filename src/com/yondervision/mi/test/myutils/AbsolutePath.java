package com.yondervision.mi.test.myutils;

/*
 * 前一段做个程序，遇到了这样一个问题，想利用相对路径删掉一个文件(实际存在的),老是删不掉. 真是急人呀，
 * 最后让我费了好大力气才算把它解决掉，问题不防跟大家说说，万一遇到这样的问题，就不用再费劲了！ 
 情况是这样的：我的Tomcat装在了c盘，而我的虚拟目录设在了E:/work下， 我在E:/work/test/image下有个图片，
 test.gif 我想通过程序删掉它，但他的绝对路径不确定(为了考虑到程序以后的移植,绝对路径是不确定的)。 
 假设del.jsp文件在e:/work/test 下，用下面的程序好像可以删掉： 
 <!--原始的del.jsp源文件--> 
 <%@ page contentType="text/html; charset=GBK" errorPage="" %> 
 <%request.setCharacterEncoding("GBK");%> 
 <%@ page language="java" import="java.sql.*" import="java.util.*" import ="java.text.*" import="java.io.*"%> 
 <html> 
 <head> 
 <meta http-equiv="Content-Type" content="text/html; charset=GBK"> 
 <title>删除成功页面</title> 
 </head> 
 <body> 
 File f=new File("/image/",test.gif); 
 boolean a=f.delete(); 
 out.print("a="+a); 
 </body> 
 </html> 
 但事实上不行，你会发现a=false; 
 这就需要获取其绝对路径， 我们用java程序来做一个专门来获取绝对路径的javaBean(AbsolutePath.java)就可以了。 
 AbsolutePath.java的代码如下： */
import java.io.*;
import javax.servlet.*;
import javax.servlet.jsp.PageContext;//导入PageContext类，不要忘了 

public class AbsolutePath {

	protected ServletContext m_application;
	private boolean m_denyPhysicalPath;

	public AbsolutePath() {

	}

	public final void initialize(PageContext pageContext)
			throws ServletException {
		m_application = pageContext.getServletContext();

	}

	public String getPhysicalPath(String filePathName, int option)
			throws IOException {
		String path = new String();
		String fileName = new String();
		String fileSeparator = new String();
		boolean isPhysical = false;
		fileSeparator = System.getProperty("file.separator");
		if (filePathName == null)
			throw new IllegalArgumentException(
					"There is no specified destination file (1140).");
		if (filePathName.equals(""))
			throw new IllegalArgumentException(
					"There is no specified destination file (1140).");
		if (filePathName.lastIndexOf("\\") >= 0) {
			path = filePathName.substring(0, filePathName.lastIndexOf("\\"));
			fileName = filePathName
					.substring(filePathName.lastIndexOf("\\") + 1);
		}
		if (filePathName.lastIndexOf("/") >= 0) {
			path = filePathName.substring(0, filePathName.lastIndexOf("/"));
			fileName = filePathName
					.substring(filePathName.lastIndexOf("/") + 1);
		}
		path = path.length() != 0 ? path : "/";
		java.io.File physicalPath = new java.io.File(path);
		if (physicalPath.exists())
			isPhysical = true;
		if (option == 0) {
			if (isVirtual(path)) {
				path = m_application.getRealPath(path);
				if (path.endsWith(fileSeparator))
					path = path + fileName;
				else
					path = String.valueOf((new StringBuffer(String
							.valueOf(path))).append(fileSeparator).append(
							fileName));
				return path;
			}
			if (isPhysical) {
				if (m_denyPhysicalPath)
					throw new IllegalArgumentException(
							"Physical path is denied (1125).");
				else
					return filePathName;
			} else {
				throw new IllegalArgumentException(
						"This path does not exist (1135).");
			}
		}
		if (option == 1) {
			if (isVirtual(path)) {
				path = m_application.getRealPath(path);
				if (path.endsWith(fileSeparator))
					path = path + fileName;
				else
					path = String.valueOf((new StringBuffer(String
							.valueOf(path))).append(fileSeparator).append(
							fileName));
				return path;
			}
			if (isPhysical)
				throw new IllegalArgumentException(
						"The path is not a virtual path.");
			else
				throw new IllegalArgumentException(
						"This path does not exist (1135).");
		}
		if (option == 2) {
			if (isPhysical)
				if (m_denyPhysicalPath)
					throw new IllegalArgumentException(
							"Physical path is denied (1125).");
				else
					return filePathName;
			if (isVirtual(path))
				throw new IllegalArgumentException(
						"The path is not a physical path.");
			else
				throw new IllegalArgumentException(
						"This path does not exist (1135).");
		}

		else {
			return null;
		}

	}

	private boolean isVirtual(String pathName) // 判断是否是虚拟路径
	{
		if (m_application.getRealPath(pathName) != null) {
			java.io.File virtualFile = new java.io.File(
					m_application.getRealPath(pathName));
			return virtualFile.exists();
		}

		else {
			return false;
		}
	}

}

/*
 * 对AbsolutePath.java编译后，得到包pathtest,里面有AbsolutePath.class类，
 * 把整个包放到虚拟目录的classes下，然后再把del.jsp文件改成如下程序，一切都OK了！ <!--改后的del.jsp的源文件--> <%@
 * page contentType="text/html; charset=GBK" errorPage="" %>
 * <%request.setCharacterEncoding("GBK");%> <%@ page language="java"
 * import="java.sql.*" import="java.util.*" import ="java.text.*"
 * import="java.io.*"%> <html> <head> <meta http-equiv="Content-Type"
 * content="text/html; charset=GBK"> <title>删除成功页面</title> </head> <body>
 * <jsp:useBean id="pathtest" scope="page" class="pathtest.AbsolutePath" />
 * pathtest.initialize(pageContext);//初始化 String
 * dir1=pathtest.getPhysicalPath("/test/image/",0);//传参数
 * out.print(dir1);//输出的是绝对路径 File file=new File(dir1,"test.gif");//生成文件对象
 * boolean a=file.delete(); out.print("a="+a); </body"> </html> 此时a=true;表示删除成功！
 * 到此为止，问题全部搞定。
 */