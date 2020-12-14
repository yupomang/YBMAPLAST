package com.yondervision.mi.util;

import com.yondervision.mi.form.Addon;

public class ApiJson {
	public static StringBuffer paramAddon(Addon[] addon,StringBuffer value){
		for(int i=0;i<addon.length;i++){
			System.out.println("*******************   "+addon[i].getName());
			if(i==0){				
				if(addon[i].getChildren()!=null){
					if(addon[i].getChildren().length>0){
						value.append("{\""+addon[i].getName()+"\":");
					}else{
						value.append("{\""+addon[i].getName()+"\":"+"\""+addon[i].getCode()+"\"");
					}
				}else{
					value.append("{\""+addon[i].getName()+"\":"+"\""+addon[i].getCode()+"\"");
				}
			}else{
				if(addon[i].getChildren()!=null){
					if(addon[i].getChildren().length>0){
						value.append(",\""+addon[i].getName()+"\":");
					}else{
						value.append(",\""+addon[i].getName()+"\":"+"\""+addon[i].getCode()+"\"");
					}
				}else{
					value.append(",\""+addon[i].getName()+"\":"+"\""+addon[i].getCode()+"\"");
				}
			}
			if(addon[i].getChildren()!=null){
				if(addon[i].getChildren().length>0){
					paramAddon(addon[i].getChildren(),value);					
				}
			}
		}
		if(addon.length>0)
			value.append("}");
		return value;
	}
}
