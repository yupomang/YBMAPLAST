/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.result
 * 文件名：     WebApi20111Result.java
 * 创建日期：2013-10-16
 */
package com.yondervision.mi.result;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yondervision.mi.dao.Mi303DAO;
import com.yondervision.mi.dto.Mi304;
import com.yondervision.mi.dto.Mi305;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.SpringContextUtil;

/**
 * @author LinXiaolong
 *
 */
public class WebApi20111Result extends Mi304 {
	/** 公共条件内容 **/
	private List<Mi305> listMi305;
	/** 公共条件项目ID **/
	private String conditionItemName;

	/**
	 * @return the listMi305
	 */
	public List<Mi305> getListMi305() {
		return listMi305;
	}

	/**
	 * @param listMi305 the listMi305 to set
	 */
	public void setListMi305(List<Mi305> listMi305) {
		this.listMi305 = listMi305;
	}

	/**
	 * @return the conditionItemName
	 */
	public String getConditionItemName() {
		if (CommonUtil.isEmpty(conditionItemName)) {
			conditionItemName = ((Mi303DAO) SpringContextUtil.getBean("mi303Dao", Mi303DAO.class)).selectByPrimaryKey(getConditionItemId()).getConditionItemName();
		}
		return conditionItemName;
	}

	/**
	 * @param conditionItemName the conditionItemName to set
	 */
	public void setConditionItemName(String conditionItemName) {
		this.conditionItemName = conditionItemName;
	}
}
