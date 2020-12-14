package com.yondervision.mi.result;

/** 
* @ClassName: AppApi00501Result 
* @Description: 提取明细查询
* @author Caozhongyan
* @date Sep 27, 2013 10:27:15 AM   
* 
*/ 
public class AppApi00501Result {
	/** 最新业务日期 */						
	private String operationDate = "";						
	/** 业务类型 */						
	private String operationType = "";						
	/** 金额 */						
	private String withdrawalAmount = "";						
	/** 业务摘要 */						
	private String operationMemo = "";						
							
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
	 *<pre> 执行获取业务类型处理 </pre>						
	 * @return operationType 业务类型						
	 */						
	public String getOperationType() {						
	    return operationType;						
	}						
							
	/**						
	 *<pre> 执行设定业务类型处理 </pre>						
	 * @param operationType 业务类型						
	 */						
	public void setOperationType(String operationType) {						
	    this.operationType = operationType;						
	}						
							
	/**						
	 *<pre> 执行获取金额处理 </pre>						
	 * @return withdrawalAmount 金额						
	 */						
	public String getWithdrawalAmount() {						
	    return withdrawalAmount;						
	}						
							
	/**						
	 *<pre> 执行设定金额处理 </pre>						
	 * @param withdrawalAmount 金额						
	 */						
	public void setWithdrawalAmount(String withdrawalAmount) {						
	    this.withdrawalAmount = withdrawalAmount;						
	}						
							
	/**						
	 *<pre> 执行获取业务摘要处理 </pre>						
	 * @return operationMemo 业务摘要						
	 */						
	public String getOperationMemo() {						
	    return operationMemo;						
	}						
							
	/**						
	 *<pre> 执行设定业务摘要处理 </pre>						
	 * @param operationMemo 业务摘要						
	 */						
	public void setOperationMemo(String operationMemo) {						
	    this.operationMemo = operationMemo;						
	}						

}
