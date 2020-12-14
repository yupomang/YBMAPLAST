package com.yondervision.mi.service;

import java.util.List;
import com.yondervision.mi.form.SynchroImportContentScInfoBean;
import com.yondervision.mi.form.SynchroImportContentXgInfoBean;
import com.yondervision.mi.form.SynchroImportContentXzInfoBean;
import com.yondervision.mi.result.SynchroImportContentAddResult;

/** 
* @ClassName: SynchroImportContent001Service 
* @Description:同步内容-导入到平台内部处理接口
* @author gongqi
* @date April 13, 2016 9:33:25 AM   
* 
*/ 
public interface SynchroImportContent001Service {
	public List<SynchroImportContentAddResult> synchroImportDataDeal(String centerid,
			List<SynchroImportContentXzInfoBean> xzList,
			List<SynchroImportContentXgInfoBean> xgList,
			List<SynchroImportContentScInfoBean> scList) throws Exception;
}
