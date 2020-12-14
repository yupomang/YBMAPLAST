package com.yondervision.mi.result;

/** 
* @ClassName: AppApi41101Result01 
* @Description: APP动画图片返回明细
* @author Caozhongyan
* @date Nov 14, 2013 4:20:51 PM   
* 
*/ 
public class AppApi41101Result02 {
	   
		/** 序号 */					
		private String xh = "";					
		/** 图片名称（路径） */					
		private String imgpath = "";
		/** 图片内容链接内容路径 */					
		private String contentlink = "";
		/** 图片切换显示方向(01:自底向上,02:自左向右,03:自右向左[默认],
		 * 04:自底向下，05：自左下至右上,06:自左上至右下，07：自右下至左上,08：自右上至左下) */					
		private String displaydirection = "";
		public String getXh() {
			return xh;
		}
		public void setXh(String xh) {
			this.xh = xh;
		}
		public String getImgpath() {
			return imgpath;
		}
		public void setImgpath(String imgpath) {
			this.imgpath = imgpath;
		}
		public String getContentlink() {
			return contentlink;
		}
		public void setContentlink(String contentlink) {
			this.contentlink = contentlink;
		}
		public String getDisplaydirection() {
			return displaydirection;
		}
		public void setDisplaydirection(String displaydirection) {
			this.displaydirection = displaydirection;
		}
}
