 package com.yondervision.mi.common.log;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.RollingFileAppender;

import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.util.Datelet;

public class YDLogFileAppender extends RollingFileAppender {
	  static String initPath= null;
	  public void setFile(String file)
	  { System.out.println("[+]file="+file); 
	    String val = file.trim(); 
	    if(initPath==null){
	    	initPath=val;
	    } 
	    if(val.endsWith(".log")){
	    	 
	    	
	    }else{
	    	if(!val.endsWith("/"))
	    		val+="/";
	    	String path=(String)com.yondervision.mi.common.filter.PermissionEncodingFilter.threadRequetPath.get();
	    	if(path==null){
	    		val+="other.log";
	    	}
	    }
	    this.fileName = val;
	    System.out.println("[-]fileName="+fileName); 
	  }
	  public static String getInitPath(){
		  return initPath;
	  }

}
