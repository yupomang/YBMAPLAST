package com.yondervision.mi.common.message;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.util.CommonUtil;

public class YDMappingJacksonJsonView extends MappingJacksonJsonView {

	protected void renderMergedOutputModel(Map<String,Object> model,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		
		if (CommonUtil.isEmpty(request.getAttribute("httpFlg"))) {
			if("/appapi00199.json,/appapi00144.json,/appapi00145.json,/appapi00158.json".indexOf(request.getServletPath())<0){
				if(CommonUtil.isEmpty(model.get("recode"))){
	
					model.put("recode", Constants.WEB_SUCCESS_CODE);			
					}
				super.renderMergedOutputModel(model, request, response);
			}
		}else{
			return;
		}
	}
}
