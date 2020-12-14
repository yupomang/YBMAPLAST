package com.yondervision.mi.common.exp;
/** 
* @ClassName: NoRollRuntimeErrorException 
* @Description: 不回滚的业务异常
* @author 韩占远
* @date 2013-10-08
* 
*/ 
public class NoRollRuntimeErrorException extends TransRuntimeErrorException {
	
	public NoRollRuntimeErrorException(String errcode,String... mes){
		super(  errcode, mes);
	}

}
