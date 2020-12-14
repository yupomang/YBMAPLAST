package com.yondervision.mi.dao;

import com.yondervision.mi.dto.Mi624;

public interface CMi624DAO extends Mi624DAO {
	/**
	 * 根据网点和业务类型查询一天最大可预约数
	 * @param form
	 * @return
	 */
	public int selectMi624SumAppocnt(Mi624 form);
}
