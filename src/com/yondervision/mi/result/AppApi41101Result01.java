package com.yondervision.mi.result;

/** 
* @ClassName: AppApi41101Result01 
* @Description: APP动画图片返回
* @author Caozhongyan
* @date Nov 14, 2013 4:20:51 PM   
* 
*/ 
public class AppApi41101Result01 {
	   
		/** 间隔时间（秒数） */					
		private String intervaltime = "";					
		/** 循环方式（00:固定，01：一次，02：连续 03：随机) */					
		private String looptype = "";
		public String getIntervaltime() {
			return intervaltime;
		}
		public void setIntervaltime(String intervaltime) {
			this.intervaltime = intervaltime;
		}
		public String getLooptype() {
			return looptype;
		}
		public void setLooptype(String looptype) {
			this.looptype = looptype;
		}					
							
			
}
