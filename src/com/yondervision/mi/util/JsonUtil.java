package com.yondervision.mi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private static Gson gson = null;
	private static Gson disablehtmlgson = null;
	
	public static Gson getGson(){
		if (gson == null){
			gson = new Gson();
		}
		return gson;
	}

	public static Gson getDisableHtmlEscaping(){
		if (disablehtmlgson == null){
			disablehtmlgson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		}
		return disablehtmlgson;
	}

	public JsonUtil() {
		
	}

}
