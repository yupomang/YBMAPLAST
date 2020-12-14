package com.yondervision.mi.service;

import com.yondervision.mi.dto.Mi029;
import com.yondervision.mi.form.AppApi058Form;
import com.yondervision.mi.form.AppApiCommonForm;
import com.yondervision.mi.result.AppApi05802Result;


public interface AppApi058Service {
	public AppApi05802Result appApi05801Select(AppApi058Form form , AppApi05802Result app058 ,Mi029 mi029) throws Exception;
	
	public void appApi05802Select(AppApi058Form form , Mi029 mi029) throws Exception;
}
