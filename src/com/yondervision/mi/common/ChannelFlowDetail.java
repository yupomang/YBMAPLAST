package com.yondervision.mi.common;

public class ChannelFlowDetail {
	public String channel = "";
	public String pid = "";
	public String isopen = "";
	public String channelName = "";
	public int bussinessflowTOP = 0;
	public int userFlowTop = 0;
	public int bussinessflow = 0;
	public int userFlow = 0;


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIsopen() {
		return isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

	public int getBussinessflowTOP() {
		return bussinessflowTOP;
	}

	public void setBussinessflowTOP(int bussinessflowTOP) {
		this.bussinessflowTOP = bussinessflowTOP;
	}

	public int getUserFlowTop() {
		return userFlowTop;
	}

	public void setUserFlowTop(int userFlowTop) {
		this.userFlowTop = userFlowTop;
	}

	public int getBussinessflow() {
		return bussinessflow;
	}

	public void setBussinessflow(int bussinessflow) {
		this.bussinessflow = bussinessflow;
	}

	public int getUserFlow() {
		return userFlow;
	}

	public void setUserFlow(int userFlow) {
		this.userFlow = userFlow;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
