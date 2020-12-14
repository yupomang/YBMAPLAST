/**
 * 
 */
package com.yondervision.mi.dto;

import com.yondervision.mi.util.CommonUtil;

/**
 * @author Administrator
 * 
 */
public class CMi013Key extends Mi013Key {
	public int hashCode() {
		if (CommonUtil.isEmpty(this.getCenterid())
				&& CommonUtil.isEmpty(this.getFuncid())) {
			return 0;
		}
		if (CommonUtil.isEmpty(this.getCenterid())) {
			return this.getFuncid().hashCode();
		}
		if (CommonUtil.isEmpty(this.getFuncid())) {
			return this.getCenterid().hashCode();
		}
		return (this.getCenterid() + "-" + this.getFuncid()).hashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Mi013Key) {
			Mi013Key temp = (Mi013Key) obj;
			if (CommonUtil.isEmpty(this.getCenterid())
					|| CommonUtil.isEmpty(this.getFuncid())) {
				return false;
			}
			return this.getCenterid().equals(temp.getCenterid())
					&& this.getFuncid().equals(temp.getFuncid());
		} else {
			return false;
		}
	}
}
