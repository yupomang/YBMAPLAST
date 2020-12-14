package com.yondervision.mi.common.exp;
/** 
* @ClassName: TransRuntimeErrorException 
* @Description: 业务异常
* @author 韩占远
* @date 2013-10-05 
* 
*/ 
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yondervision.mi.dao.Mi010DAO;
import com.yondervision.mi.dto.Mi010Example;
import com.yondervision.mi.util.CommonUtil;

 
public class TransRuntimeErrorException extends RuntimeException {
  static Map<String,String> errTxtMap=new HashMap<String, String>();
  private String errcode=null;
  private String message=null;
  public String getMessage(){ 
		return message;
  } 
  public TransRuntimeErrorException(String errcode,String... mes){
	  this.errcode=errcode;
	  if(errTxtMap==null)
		  errTxtMap=new HashMap<String,String>();
	  String text="错误码="+errcode;	  
	  if(errTxtMap.get(errcode)==null){
		  try{
			if(errcode.length()>6){
			   text+=",错误码长度不能大于6位";
			}else{	
		       Mi010DAO   mi010Dao=	(Mi010DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi010Dao");
			   Mi010Example m10e=new Mi010Example();			 
			   m10e.createCriteria().andErrcodeEqualTo(errcode);
			   List<com.yondervision.mi.dto.Mi010> list=mi010Dao.selectByExample(m10e);
			  if(list.size()>0)
				text=list.get(0).getErrtext();
			  } 
		  }catch(Exception e){
			  System.out.println("------------------------");
			  e.printStackTrace();
			  
		  }
	  }	 
	  for (int i = 0; i < mes.length; i++) {	 
			text=text.replaceAll("\\{" + String.valueOf(i) + "\\}", mes[i]);
	  }
	  
	  message=text;
	  
  }
  public void printStackTrace(){
	  //System.out.println(">>>"+this.getMessage());
  }
  public void printStackTrace(PrintStream s) {
	  //System.out.println(">>>"+this.getMessage());
  }
  public void printStackTrace(PrintWriter s){
	  //s.println(">>>"+this.getMessage());
  }
  public String getErrcode() {
	return errcode;
  }
  
  
	/**
	 * 清楚异常内容缓存，在异常内容有变更时调用
	 */
	public static void clearErrTxtMap() {
		if(!CommonUtil.isEmpty(errTxtMap)){
			errTxtMap.clear();
		}
	}
}
