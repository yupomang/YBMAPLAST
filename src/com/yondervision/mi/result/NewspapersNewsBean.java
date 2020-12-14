package com.yondervision.mi.result;

public class NewspapersNewsBean {
	private String titleId = "";
	private String title = "";
	private String introduction = "";
	//private String releaseDate = "";
	private String imgUrl = "";
	private String source = "";
	private int praisecounts = 0;
	private int commentcounts = 0;

	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/*public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}*/
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	 * @return the praisecounts
	 */
	public int getPraisecounts() {
		return praisecounts;
	}
	/**
	 * @param praisecounts the praisecounts to set
	 */
	public void setPraisecounts(int praisecounts) {
		this.praisecounts = praisecounts;
	}
	/**
	 * @return the commentcounts
	 */
	public int getCommentcounts() {
		return commentcounts;
	}
	/**
	 * @param commentcounts the commentcounts to set
	 */
	public void setCommentcounts(int commentcounts) {
		this.commentcounts = commentcounts;
	}
	
}
