package com.yondervision.mi.result;

public class QueueStatusModel {
	private String CellID;   //网点编号
	private String CellName; //网点名称
	private String JobID;       //业务编号
	private String JobName;  //业务名称
	private String Status;   //排队状态：01，排队中；02，服务中；03，已完成
	private String WaitNo;      //等待人数
	private String Result;   //数据结果状态：成功返回SUCCESS
	private String CurTime;  //系统当前时间
	public String getCellID() {
		return CellID;
	}
	public void setCellID(String cellID) {
		CellID = cellID;
	}
	public String getCellName() {
		return CellName;
	}
	public void setCellName(String cellName) {
		CellName = cellName;
	}
	public String getJobID() {
		return JobID;
	}
	public void setJobID(String jobID) {
		JobID = jobID;
	}
	public String getJobName() {
		return JobName;
	}
	public void setJobName(String jobName) {
		JobName = jobName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getWaitNo() {
		return WaitNo;
	}
	public void setWaitNo(String waitNo) {
		WaitNo = waitNo;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getCurTime() {
		return CurTime;
	}
	public void setCurTime(String curTime) {
		CurTime = curTime;
	}	
}
