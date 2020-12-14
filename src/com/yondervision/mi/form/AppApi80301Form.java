package com.yondervision.mi.form;

/** 
* @ClassName: AppApi40102Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 3:31:37 PM   
* 
*/ 
public class AppApi80301Form extends AppApiCommonForm{
	/**
	 * 用户ID
	 */
	private String user_id="";
	/**
	 * 查看详细使用标题ID
	 */
	private String worksheet="";
	/**
	 * 查询己办工单列表页
	 */
	private String page="";
	/**
	 * 查询己办工单列表页显示记录数
	 */
	private String page_size="";
	/**
	 * 查询己办工单列表标题（模糊查询）
	 */
	private String title="";
	/**
	 * 申请人姓名（模糊查询）
	 */
	private String display_name="";
	/**
	 * 状态
	 */
	private String status="";
	/**
	 * 开始时间
	 */
	private String start_time="";
	/**
	 * 结束时间
	 */
	private String end_time="";
	/**
	 * 审批、驳回、转批、取回使用ID
	 */
	private String worksheet_id="";
	/**
	 * 操作描述
	 */
	private String comment="";	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getWorksheet() {
		return worksheet;
	}

	public void setWorksheet(String worksheet) {
		this.worksheet = worksheet;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getWorksheet_id() {
		return worksheet_id;
	}

	public void setWorksheet_id(String worksheet_id) {
		this.worksheet_id = worksheet_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
