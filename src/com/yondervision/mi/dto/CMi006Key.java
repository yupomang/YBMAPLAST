/**
 * 
 */
package com.yondervision.mi.dto;

import com.yondervision.mi.util.CommonUtil;

/**
 * @author Administrator
 * 
 */
public class CMi006Key extends Mi006Key {
	public int hashCode() {
		if (CommonUtil.isEmpty(this.getCenterid())
				&& CommonUtil.isEmpty(this.getFuncid())
				&& CommonUtil.isEmpty(this.getRoleid())) {
			return 0;
		}
		if (CommonUtil.isEmpty(this.getCenterid())) {
			return this.getFuncid().hashCode();
		}
		if (CommonUtil.isEmpty(this.getFuncid())) {
			return this.getCenterid().hashCode();
		}
		if (CommonUtil.isEmpty(this.getRoleid())) {
			return this.getRoleid().hashCode();
		}
		return (this.getCenterid() + "-" + this.getFuncid()+ "-" + this.getRoleid()).hashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Mi006Key) {
			Mi006Key temp = (Mi006Key) obj;
			if (CommonUtil.isEmpty(this.getCenterid())
					|| CommonUtil.isEmpty(this.getFuncid())
					|| CommonUtil.isEmpty(this.getRoleid())) {
				return false;
			}
			return this.getCenterid().equals(temp.getCenterid())
					&& this.getFuncid().equals(temp.getFuncid())
					&& this.getRoleid().equals(temp.getRoleid());
		} else {
			return false;
		}
	}
}
