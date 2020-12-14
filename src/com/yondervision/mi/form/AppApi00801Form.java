package com.yondervision.mi.form;

/** 
* @ClassName: AppApi00801Form 
* @Description: 提取明细查询请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi00801Form extends AppApiCommonForm {
	private String selectType = "";						
	/** 查询信息条件值 */						
	private String selectValue = "";						
							
	/**						
	 *<pre> 执行获取查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @return selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public String getSelectType() {						
	    return selectType;						
	}						
							
	/**						
	 *<pre> 执行设定查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @param selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public void setSelectType(String selectType) {						
	    this.selectType = selectType;						
	}						
							
	/**						
	 *<pre> 执行获取查询信息条件值处理 </pre>						
	 * @return selectValue 查询信息条件值						
	 */						
	public String getSelectValue() {						
	    return selectValue;						
	}						
							
	/**						
	 *<pre> 执行设定查询信息条件值处理 </pre>						
	 * @param selectValue 查询信息条件值						
	 */						
	public void setSelectValue(String selectValue) {						
	    this.selectValue = selectValue;						
	}						

}
