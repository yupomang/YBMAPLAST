/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.common
 * 文件名：     ERRCODE.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.common;

import com.yondervision.mi.common.log.LoggerUtil;

/**
 * 错误码定义类
 * ERROR、LOG、DEBUG为输出日志使用的错误码
 * APP_ALERT、WEB_ALERT为抛出异常时使用的错误码
 * 抛出的异常由filer统一处理，发送到客户端
 * @author LinXiaolong
 *
 */
public class ERRCODE {
	/**
	 * 错误日志码
	 * 对应mi009
	 * @author LinXiaolong
	 *
	 */
    public static enum ERROR{
    	/*
    	 * 错误码定义
    	 * 以7、8、9开头
    	 */
    	/** 系统异常：{0} **/
        SYS("999999"),
    	/** 登录异常：{0}**/
        LOGIN_EXC("999998"),
    	/** 通讯发送失败：{0} **/
        CONNECT_SEND_ERROR("900001"),
    	/** 通讯错误：{0} **/
        CONNECT_ERROR("900002"),
        /** 账户未实名认证 **/
        AUTHFLAG_ERROR("999995"),
        /** 账户认证 {0}**/
        AUTH_ERROR("999996"),
        /** 账户未绑定 **/
        BINDFLAG_ERROR("999997"),
        
        /** 表【{0}】条件【{1}】无数据 **/
        NO_DATA("800001"),
        /** 表【{0}】取得主键失败 **/
        NULL_KEY("800002"),
        /** 更新失败！表【{0}】、主键【{1}】无对应数据 **/
        UPDATE_NO_DATA("800003"),
        /** 未找到符合条件的【{0}】数据 */
        ERRCODE_LOG_800004("800004"),
        /** 缺少参数【{0}】**/
        PARAMS_NULL("700001"),
        /** 此记录【{0}】已存在,不能进行维护操作。**/
        ADD_CHECK("700002"),
        /** 删除记录【{0}】失败。**/
        DEL_CHECK("700003"),
        /** 此【{0}】记录已注销,不能进行维护操作，需“激活”。**/
        VALIDFLG_CHECK("700004"),
        /** 此记录【{0}】已处于“有效”状态。**/
        VALIDFLG_CHECK_TRUE("700005"),
        /** 此【{0}】记录【{1}】,不能进行【{2}】，请确认后提交。**/
        VALIDFLG_AUTH_CHECK("700006"),
        
        /** 此行数据编辑过，且整行为空,请删除 **/
        IMPORT_ROW_NULL("710001"),
        /** 此行数据[{0}]为空 **/
        IMPORT_ROW_COL_NULL("710002"),
        /** 此行数据[{0}]超长。数据长度[{1}]，最大长度[{2}] **/
        IMPORT_ROW_COL_TOO_LONG("710003"),
        /** 此行数据[{0}]不可换行 **/
        IMPORT_ROW_COL_CONT_CHANGE_LINE("710004"),
        /** 此行数据[{0]没有对应的注册用户 **/
        IMPORT_ROW_COL_HAS_NO_USER("710005"),
        /** 此行数据列数为[{0}]，要求列数为[{1}] **/
        IMPORT_ROW_LENGTH_INCORRECT("710006"),
        ;
        
        
        /*
         * 内部数据
         */
        private final char[] value;
        /**
         * 取得错误码
         */
        public String getValue() {
            return new String(value);
        }
        /**
         * 取得错误信息
         * @param params 此错误码对应的参数
         * @return 错误信息
         */
        public String getLogText(String...params) {
            return LoggerUtil.getLogText(new String(value), params);
        }
        ERROR(String value) {
            this.value = value.toCharArray();
        }
    }
    
    /**
	 * 业务日志码
	 * 对应mi009
	 * @author LinXiaolong
	 *
	 */
    public static enum LOG{
    	/*
    	 * 业务日志码定义
    	 * 以4、5、6开头
    	 */
    	/** 【{0}】 **/
        SELF_LOG("400000"),
    	/** 【{0}】业务处理开始 **/
        START_BUSIN("400001"),
        /** 【{0}】业务处理结束 **/
        END_BUSIN("400002"),
        /** 已接收到返回信息:{0} **/
        REV_INFO("400003"),
        ;
        
        
        /*
         * 内部数据
         */
        private final char[] value;
        /**
         * 取得错误码
         */
        public String getValue() {
            return new String(value);
        }
        /**
         * 取得错误信息
         * @param params 此错误码对应的参数
         * @return 错误信息
         */
        public String getLogText(String...params) {
            return LoggerUtil.getLogText(new String(value), params);
        }
        LOG(String value) {
            this.value = value.toCharArray();
        }
    }
    
    /**
	 * 调试日志码
	 * 对应mi009
	 * @author LinXiaolong
	 *
	 */
    public static enum DEBUG{
    	/*
    	 * 调试日志码定义
    	 * 以0开头
    	 */
    	/**请求参数为：{0}*/
        SHOW_PARAM("000001"),
        /**返回结果为：{0}*/
        SHOW_RESULT("000009"),
        ;
        
        
        /*
         * 内部数据
         */
        private final char[] value;
        /**
         * 取得错误码
         */
        public String getValue() {
            return new String(value);
        }
        /**
         * 取得错误信息
         * @param params 此错误码对应的参数
         * @return 错误信息
         */
        public String getLogText(String...params) {
            return LoggerUtil.getLogText(new String(value), params);
        }
        DEBUG(String value) {
            this.value = value.toCharArray();
        }
    }
    
    /**
	 * 手机提示信息码
	 * 对应mi010
	 * @author LinXiaolong
	 *
	 */
    public static enum APP_ALERT{
    	/*
    	 * 调试日志码定义
    	 * 以1开头
    	 */
    	/** 系统错误 **/
        SYS("199999"),	//系统异常
        /** 请输入【{0}】 **/
        PARAMS_NULL("100001"),
        /** APP用户未登录 **/
        APP_NO_LOGIN("100002"),
        /** APP输入参数【{0}】不正确 */
        APP_DATA_ERROR("100003"),
        /** 未找到符合条件的【{0}】数据 **/
        NO_DATA("180001"),
        /** 用户注册失败【{0}】 **/
        APP_ZC("100004"),
        /**预约失败**/
        APP_YY("100005")
        ;
        
        
        /*
         * 内部数据
         */
        private final char[] value;
        /**
         * 取得错误码
         */
        public String getValue() {
            return new String(value);
        }
        APP_ALERT(String value) {
            this.value = value.toCharArray();
        }
    }
    
    /**
	 * web请求提示信息码（公共部分web页面提示信息码，以29开头）
	 * 对应mi010
	 * @author LinXiaolong
	 *
	 */
    public static enum WEB_ALERT{
    	/*
    	 * 调试日志码定义
    	 * 以2开头
    	 */
    	/** 系统错误[{0}]，请联系系统管理员 **/
        SYS("299999"),	//系统异常
        /** 请输入【{0}】 **/
        PARAMS_NULL("200001"),
        /** 参数【{0}】超过最大长度[{1}] **/
        PARAMS_TOO_LONG("200002"),
        /** 上传文件格式为[{0}]不正确，要求上传格式为[{1}] */
        PARAMS_FILE_TYPE("200003"),
        /** 此记录{0}已存在，请确认后再提交。 */
        ADD_CHECK("200004"),
        /** 删除记录【{0}】失败。 */
        DEL_CHECK("200005"),
        /** 更新0条！表【{0}】无对应数据。 */
        UPD_CHECK("200006"),
        /** 更新记录失败:【{0}】 */
        UPD_ERROR("200007"),
        /** 删除0条！表【{0}】无对应数据。 */
        DEL_NO_DATA("200008"),
        /** 新增数据错误：【{0}】 */
        DATA_CHECK_INSERT("200009"),
        /** 导入的批量数据文件有误，详情：{0} */
        IMPORT_FILE_ERR("210001"),
        /** 要删除的业务咨询项目包含[{0}]个业务咨询子项、[{1}]个公共条件项目，请先删除业务咨询子项和公共条件项目。 **/
        MI301_COUNT_DELETE("260001"),
        /** 要删除的业务咨询子项包含[{0}]个业务咨询向导步骤，请先删除业务咨询向导步骤。 **/
        MI302_COUNT_DELETE("260002"),
        /** 要删除的公共条件项目包含[{0}]个公共条件分组，请先删除公共条件分组。 **/
        MI303_COUNT_DELETE("260003"),
        /** 要删除的公共条件分组包含[{0}]个公共条件内容，请先删除公共条件内容。 **/
        MI304_COUNT_DELETE("260004"),
        /** 要删除的公共条件内容被[{0}]个公共条件组合使用，请先删除对应公共条件组合的业务咨询内容。 **/
        MI305_COUNT_DELETE("260005"),
        /** 要删除的业务咨询向导步骤包含[{0}]个业务咨询向导内容，请先删除业务咨询向导内容。 **/
        MI306_COUNT_DELETE("260006"),
        /** 每个业务咨询项目下最多添加8个公共条件项目 **/
        MI303_CONSULTITEM_THAN_MAX("260007"),
        /** 中心[{0}]未录入客服电话，请在中心基本信息中录入客服电话！ **/
        NEED_CUSTSVCTEL("270001"),
        /** 未找到符合条件的【{0}】数据 **/
        NO_DATA("280001"),
        
        // 公共部分web页面提示信息码，以29开头
        /** {0} **/
        SELF_ERR("290001"),
        /** 此【{0}】记录已注销,请“激活”后，再进行其他操作。**/
        VALIDFLG_CHECK("290002"),
        /** 当前【{0}】内容中包含已【{1}】记录，不能进行【{2}】，请重新选择！**/
        VALIDFLG_LIST_CHECK("290003"),
        
        
    	/**【{0}】输入不合法，请确认后再提交 **/
        VALUE_ERR("299996"),
    	/**操作业务日志表，系统异常【{0}】，请联系系统管理员 **/
        BUZ_LOG_SYS("299997"),	//业务日志特有，系统异常，
        /** 登录超时，请重新登录 **/
        LOGIN_TIMEOUT("299998"),
        /** 请求验证失败 **/
        LOGIN_MD5_CHECK("299995"),
        /** 请求信息包头不正确 **/
        LOGIN_HEAD_CHECK("299994"),
        /** 用户登录错误【{0}】 **/
        LOGIN_NO_USER("299993"),
        /** {0} **/
        LOGIN_ERROR_PARA("299992"),
        ;
        
        
        /*
         * 内部方法
         */
        private final char[] value;
        /**
         * 取得错误码
         */
        public String getValue() {
            return new String(value);
        }
        WEB_ALERT(String value) {
            this.value = value.toCharArray();
        }
    }
    
    /*
    public static void main(String[] args) {
    	//取错误码方法
		System.out.println(ERRCODE.ERROR.SYS.getValue());
	}*/

}
