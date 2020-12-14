package com.yondervision.mi.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Caozhongyan
 *
 */
public class AppApi00201Result {
	/** 交易日期 */					
	private String operationDate = "";					
	/** 业务类型 */					
	private String operationType = "";					
	/** 金额 */					
	private String amount = "";					
	/** 业务摘要 */					
	private String operationMemo = "";
						
	/**					
	 *<pre> 执行获取交易日期处理 </pre>					
	 * @return operationDate 交易日期					
	 */					
	public String getOperationDate() {					
	    return operationDate;					
	}					
						
	/**					
	 *<pre> 执行设定交易日期处理 </pre>					
	 * @param operationDate 交易日期					
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
	 * @return amount 金额					
	 */					
	public String getAmount() {					
	    return amount;					
	}					
						
	/**					
	 *<pre> 执行设定金额处理 </pre>					
	 * @param amount 金额					
	 */					
	public void setAmount(String amount) {					
	    this.amount = amount;					
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
