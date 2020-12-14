package com.yondervision.mi.result;

/** 
* @ClassName: AppApi00301Result 
* @Description: 接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 9:14:36 AM 
* 
*/ 
public class AppApi00301Result {
	/** 公积金账户 */						
	private String surplusAccount = "";						
	/** 账户状态 */						
	private String accountState = "";						
	/** 账户余额 */						
	private String amount = "";						
	/** 冻结金额 */						
	private String freezeMoney = "";						
	/** 冻结日期 */						
	private String freezeDate = "";						
	/** 最新业务日期 */						
	private String operationDate = "";						
	/** 备注 */						
	private String remark = "";						
							
	/**						
	 *<pre> 执行获取公积金账户处理 </pre>						
	 * @return surplusAccount 公积金账户						
	 */						
	public String getSurplusAccount() {						
	    return surplusAccount;						
	}						
							
	/**						
	 *<pre> 执行设定公积金账户处理 </pre>						
	 * @param surplusAccount 公积金账户						
	 */						
	public void setSurplusAccount(String surplusAccount) {						
	    this.surplusAccount = surplusAccount;						
	}						
							
	/**						
	 *<pre> 执行获取账户状态处理 </pre>						
	 * @return accountState 账户状态						
	 */						
	public String getAccountState() {						
	    return accountState;						
	}						
							
	/**						
	 *<pre> 执行设定账户状态处理 </pre>						
	 * @param accountState 账户状态						
	 */						
	public void setAccountState(String accountState) {						
	    this.accountState = accountState;						
	}						
							
	/**						
	 *<pre> 执行获取账户余额处理 </pre>						
	 * @return amount 账户余额						
	 */						
	public String getAmount() {						
	    return amount;						
	}						
							
	/**						
	 *<pre> 执行设定账户余额处理 </pre>						
	 * @param amount 账户余额						
	 */						
	public void setAmount(String amount) {						
	    this.amount = amount;						
	}						
							
	/**						
	 *<pre> 执行获取冻结金额处理 </pre>						
	 * @return freezeMoney 冻结金额						
	 */						
	public String getFreezeMoney() {						
	    return freezeMoney;						
	}						
							
	/**						
	 *<pre> 执行设定冻结金额处理 </pre>						
	 * @param freezeMoney 冻结金额						
	 */						
	public void setFreezeMoney(String freezeMoney) {						
	    this.freezeMoney = freezeMoney;						
	}						
							
	/**						
	 *<pre> 执行获取冻结日期处理 </pre>						
	 * @return freezeDate 冻结日期						
	 */						
	public String getFreezeDate() {						
	    return freezeDate;						
	}						
							
	/**						
	 *<pre> 执行设定冻结日期处理 </pre>						
	 * @param freezeDate 冻结日期						
	 */						
	public void setFreezeDate(String freezeDate) {						
	    this.freezeDate = freezeDate;						
	}						
							
	/**						
	 *<pre> 执行获取最新业务日期处理 </pre>						
	 * @return operationDate 最新业务日期						
	 */						
	public String getOperationDate() {						
	    return operationDate;						
	}						
							
	/**						
	 *<pre> 执行设定最新业务日期处理 </pre>						
	 * @param operationDate 最新业务日期						
	 */						
	public void setOperationDate(String operationDate) {						
	    this.operationDate = operationDate;						
	}						
							
	/**						
	 *<pre> 执行获取备注处理 </pre>						
	 * @return remark 备注						
	 */						
	public String getRemark() {						
	    return remark;						
	}						
							
	/**						
	 *<pre> 执行设定备注处理 </pre>						
	 * @param remark 备注						
	 */						
	public void setRemark(String remark) {						
	    this.remark = remark;						
	}						

}
