/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     WebApiTreeResult.java
 * 创建日期：2013-9-27
 */
package com.yondervision.mi.result;

import java.util.List;

/**
 * 树型结构返回值
 * @author gongqi
 *
 */
public class PtlApiTreeResult {
	/** 树节点ID **/
	private String id;
	/** 树节点名称 **/
	private String text;
	/** 树节点状态（"oper"=展开；"closed"=合并） **/
	private String state;
	/** 上级树节点ID **/
	private String _parentId;
	/** 树节点带的其他参数（javaBean） **/
	private Object attributes;
	
	/** 子节点 **/
	List<PtlApiTreeResult> children;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the attributes
	 */
	public Object getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the _parentId
	 */
	public String get_parentId() {
		return _parentId;
	}

	/**
	 * @param parentId the _parentId to set
	 */
	public void set_parentId(String parentId) {
		_parentId = parentId;
	}

	/**
	 * @return the children
	 */
	public List<PtlApiTreeResult> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<PtlApiTreeResult> children) {
		this.children = children;
	}

	
}
