package com.yondervision.mi.zwfwutil;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlReader {

	/**
	 * 枚举项：使用文件绝对路径读取文件，应传入filePath
	 */
	public static int USE_FILE = 1;

	/**
	 * 枚举项：使用ClassLoader读取文件，应传入classPath
	 * 如："com/ydyd/common/resources/sys_para.xml"
	 */
	public static int USE_CLASSLOADER = 2;

	/**
	 * 枚举项：使用InputStream读取文件，应传入InputStream
	 */
	public static int USE_INPUTSTREAM = 3;

	/**
	 * 当前使用的读取文件方法
	 */
	private int currUseType = XmlReader.USE_FILE;

	/**
	 * Document对象
	 */
	private Document dom;

	/**
	 * 读取的文件路径及名称
	 */
	private String filePath = null;

	/**
	 * 使用ClassPath读取文件时文件的path
	 */
	private String classPath = null;

	/**
	 * InputStream流
	 */
	private InputStream inputStream = null;

	/**
	 * @return Document
	 */
	public Document getDom() {
		return dom;
	}

	/**
	 * @return String
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return InputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the dom.
	 * 
	 * @param dom
	 *            The dom to set
	 */
	public void setDom(Document dom) {
		this.dom = dom;
	}

	/**
	 * Sets the fileName.
	 * 
	 * @param fileName
	 *            The fileName to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Sets the is.
	 * 
	 * @param is
	 *            The is to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return String
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * Sets the classPath.
	 * 
	 * @param classPath
	 *            The classPath to set
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * @return int
	 */
	public int getCurrUseType() {
		return currUseType;
	}

	/**
	 * Sets the currUseType.
	 * 
	 * @param currUseType
	 *            The currUseType to set
	 */
	public void setCurrUseType(int currUseType) {
		this.currUseType = currUseType;
	}

	/**
	 * 构造函数
	 */
	public XmlReader() {
		super();
	}

	/**
	 * 构造函数<br>
	 * 通过fileName读文件：<br>
	 * path=文件路径；<br>
	 * useType=XmlReader.USE_FILE<br>
	 * <br>
	 * 通过ClassLoader读文件：<br>
	 * path=Class路径；<br>
	 * useType=XmlReader.USE_CLASSLOADER<br>
	 * 
	 * @param fileName
	 */
	public XmlReader(String path, int useType) {
		if (useType == XmlReader.USE_FILE) {
			setFilePath(path);
			setCurrUseType(useType);
		} else if (useType == XmlReader.USE_CLASSLOADER) {
			setClassPath(path);
			setCurrUseType(useType);
		}
		parse();
	}

	/**
	 * 构造函数，通过InputStream读文件
	 * 
	 * @param inputStream
	 */
	public XmlReader(InputStream inputStream) {
		setInputStream(inputStream);
		setCurrUseType(XmlReader.USE_INPUTSTREAM);
		parse();
	}

	/**
	 * 解析文件
	 * 
	 * @throws Exception
	 */
	public void parse() {

		SAXBuilder builder = new SAXBuilder();

		if (this.currUseType == XmlReader.USE_FILE) {
			// 创建DOM
			File file = null;
			file = new java.io.File(this.filePath);
			try {
				dom = builder.build(file);
			} catch (Exception e) {
				SysLog.writeError(e);
			}
		}

		if (this.currUseType == XmlReader.USE_CLASSLOADER) {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader == null) {
				classLoader = this.getClass().getClassLoader();
			}
			this.inputStream = classLoader.getResourceAsStream(this.classPath);
			try {
				dom = builder.build(this.inputStream);
			} catch (Exception e) {
				SysLog.writeError(e);
			}
		}

		if (this.currUseType == XmlReader.USE_INPUTSTREAM) {
			try {
				dom = builder.build(this.inputStream);
			} catch (Exception e) {
				SysLog.writeError(e);
			}
		}

	}

	/**
	 * 根据Element取下属的tagName标记的Elements
	 * 
	 * @param parent
	 * @param tagName
	 * @return Element[]
	 */
	public Element[] getElements(Element parent, String tagName) {

		Element element = parent;
		List children = element.getChildren();
		Vector found = new Vector();

		// 判断直接子节点。
		for (int i = 0; i < children.size(); i++) {
			Object nodei = children.get(i);
			if (nodei instanceof Element
					&& (((Element) nodei).getName()).equalsIgnoreCase(tagName)) {
				found.addElement(nodei);
			}
		}

		if (found.size() > 0) {
			Element[] rvs = new Element[found.size()];
			found.copyInto(rvs);
			return rvs;
		}
		return new Element[0];
	}

}
