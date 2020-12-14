package com.yondervision.mi.common;

import java.util.List;

public class Flow {
	public String centerid = "";
	public int bussinessflowTOP = 0;
	public int userFlowTop = 0;
	public int bussinessflow = 0;
	public int userFlow = 0;
	public List<ChannelFlow> channelbusinesses;
	public List<ChannelFlowDetail> channelFlowDetails;

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
	
	
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public List<ChannelFlow> getChannelbusinesses() {
		return channelbusinesses;
	}
	public void setChannelbusinesses(List<ChannelFlow> channelbusinesses) {
		this.channelbusinesses = channelbusinesses;
	}

	public List<ChannelFlowDetail> getChannelFlowDetails() {
		return channelFlowDetails;
	}

	public void setChannelFlowDetails(List<ChannelFlowDetail> channelFlowDetails) {
		this.channelFlowDetails = channelFlowDetails;
	}
}
