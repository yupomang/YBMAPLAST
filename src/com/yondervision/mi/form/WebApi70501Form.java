package com.yondervision.mi.form;

/** 
* @ClassName: WebApi70201Form 
* @Description: 新闻发布-增加请求Form
*/ 
public class WebApi70501Form extends WebApiCommonForm {
	
	private String classification;
	private String title;
	private String citedtitle;
	private String subtopics;
	private String source;
	private String introduction;
	private String blurbs;
	private String content;
	private String image;
	/** 内容的纯文本格式 **/
	private String contentTmp;
	
	private String attentionFlg;//是否是本期看点的标记
	
//	private String validFlg;//是否是过期的标记
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}
	/**
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the citedtitle
	 */
	public String getCitedtitle() {
		return citedtitle;
	}
	/**
	 * @param citedtitle the citedtitle to set
	 */
	public void setCitedtitle(String citedtitle) {
		this.citedtitle = citedtitle;
	}
	/**
	 * @return the subtopics
	 */
	public String getSubtopics() {
		return subtopics;
	}
	/**
	 * @param subtopics the subtopics to set
	 */
	public void setSubtopics(String subtopics) {
		this.subtopics = subtopics;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * @return the blurbs
	 */
	public String getBlurbs() {
		return blurbs;
	}
	/**
	 * @param blurbs the blurbs to set
	 */
	public void setBlurbs(String blurbs) {
		this.blurbs = blurbs;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the contentTmp
	 */
	public String getContentTmp() {
		return contentTmp;
	}
	/**
	 * @param contentTmp the contentTmp to set
	 */
	public void setContentTmp(String contentTmp) {
		this.contentTmp = contentTmp;
	}
	/**
	 * @return the attentionFlg
	 */
	public String getAttentionFlg() {
		return attentionFlg;
	}
	/**
	 * @param attentionFlg the attentionFlg to set
	 */
	public void setAttentionFlg(String attentionFlg) {
		this.attentionFlg = attentionFlg;
	}
//	public String getValidFlg() {
//		return validFlg;
//	}
//	public void setValidFlg(String validFlg) {
//		this.validFlg = validFlg;
//	}
}
