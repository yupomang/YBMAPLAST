package com.yondervision.mi.common;

/**
 * @author Caozhongyan
 *
 */
public class Constants {
	/**
	 * YD系统管理员
	 */
	public static final String YD_ADMIN="00000000";
	
	/**
	 * 数据无效
	 */
	public static final String IS_NOT_VALIDFLAG="0";
	/**
	 * 数据有效
	 */
	public static final String IS_VALIDFLAG="1";
	
	/**
	 * 版本无效
	 */
	public static final String IS_NOT_USABLEFLAG="0";
	/**
	 * 版本有效
	 */
	public static final String IS_USABLEFLAGG="1";
	
	/**
	 * IOS系统
	 */
	public static final String MI105_DEVTYPE_IOS = "1";
	/**
	 * 安卓系统
	 */
	public static final String MI105_DEVTYPE_ANDROID = "2";
	/**
	 * IOS-IPAD系统
	 */
	public static final String MI105_DEVTYPE_IOS_IPAD = "3";
	
	
	/*
	 * MI中心前置
	 */
	public static final String MI_CENTER_FRONT = "3";
	
	/**
	 * 渠道来源:APP
	 */
	public static final String 	CHANNELTYPE_APP = "10";//old:01
	/**
	 * 渠道来源:微信
	 */
	public static final String 	CHANNELTYPE_WEIXIN = "20";//new add
	
	/**
	 * 渠道来源:web浏览器
	 */
	public static final String CHANNELTYPE_WEB= "30";//old:02
	
	/**
	 * 渠道来源:中心前置
	 */
	public static final String CHANNELTYPE_CENTER_FRONT = "40";//old:03
	
	/**
	 * 所属标志:0：公有
	 */
	public static final String ATTRIBUTE_FLG_PUBLIC = "0";
	
	/**
	 * 所属标志:1：私有
	 */
	public static final String ATTRIBUTE_FLG_PRIVATE = "1";
	
	/**
	 * 格式：yyMMdd
	 */
	public static final String DATE_FORMAT_XH_YYMMDD = "yyMMdd";

	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 格式：HH:mm:ss
	 */
	public static final String TIME_FORMAT = "HH:mm:ss";
	
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String APP_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 格式：yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 格式：yyyy-MM-dd HH:mm:ss:SSS000
	 */
	public static final String DATE_TIME_FORMAT_M = "yyyy-MM-dd HH:mm:ss:SSS000";
	
	/**
	 * 配置文件文件名
	 */
	public static final String PROPERTIES_FILE_NAME = "properties.properties";
	
	/**
	 * 是叶子节点
	 */
	public static final String IS_LEAF_FLG = "1";
	
	/**
	 * 不是叶子节点
	 */
	public static final String IS_NOT_LEAF_FLG = "0";
	
	/** 等额本息还款 */
	public static final String REPAY_TYPE_A = "10";
	/** 等额本金还款 */
	public static final String REPAY_TYPE_B = "20";
	/**
	 * 公积金贷款利率类型
	 */
	public static final String RETA_TYPE_10 = "10";
	/**
	 * 商业贷款利率类型
	 */
	public static final String RETA_TYPE_20 = "20";
	/**
	 * 定期存款利率类型
	 */
	public static final String RETA_TYPE_30 = "30";
	/**
	 * 活期存款利率类型
	 */
	public static final String RETA_TYPE_40 = "40";
	
	/**
	 * WEB请求成功返回码
	 */
	public static final String WEB_SUCCESS_CODE = "000000";
	
	/**
	 * WEB请求成功返回信息
	 */
	public static final String WEB_SUCCESS_MSG = "成功";
	
	/**
	 * APP请求成功返回码
	 */
	public static final String APP_SUCCESS_CODE = "000000";
	
	/**
	 * APP请求成功返回信息
	 */
	public static final String APP_SUCCESS_MSG = "成功";
	
	/**
	 * MI308表中表示顶级咨询内容的parentId
	 */
	public static final String MI308_SRC_PARENT_ID = "src";
	
	/**
	 * 消息群推
	 */
	public static final String PUSH_TYPE_QT = "01";
	/**
	 * 报盘消息推送
	 */
	public static final String PUSH_TYPE_BP = "02";
	/**
	 * 模板消息推送
	 */
	public static final String PUSH_TYPE_MB = "03";
	
	/**
	 * 推送消息类型——文本消息
	 */
	public static final String SEND_MESSAGE_TYPE_TEXT = "01";
	
	/**
	 * 推送消息类型——图片
	 */
	public static final String SEND_MESSAGE_TYPE_IMAGE = "02";
	
	/**
	 * 推送消息类型——富文本
	 */
	public static final String SEND_MESSAGE_TYPE_RICHTEXT = "03";
	
	/**
	 * 推送消息类型——音频
	 */
	public static final String SEND_MESSAGE_TYPE_AUDIO = "04";
	
	/**
	 * 推送消息类型——视频
	 */
	public static final String SEND_MESSAGE_TYPE_VIDEO = "05";
	
	/**
	 * 推送消息类型——富文本多条，微信专用
	 */
	public static final String SEND_MESSAGE_TYPE_RICHTEXT_MORE = "06";
	
	/**
	 * 推送信息——初始状态未推送
	 */
	public static final String PUSH_MSG_DEF_STATE = "0";
	
	
	/**
	 * 推送信息——已推送
	 */
	public static final String PUSH_MSG_ARD_PUSH = "1";
	
	/**
	 * 推送信息——推送失败
	 */
	public static final String PUSH_MSG_ERROR_PUSH = "2";
	
	/**
	 * 未审批
	 */
	public static final String APPROVE_NO = "0";
	
	/**
	 * 审批中
	 */
	public static final String APPROVE_ING = "1";
	
	/**
	 * 审批通过
	 */
	public static final String APPROVE_YES = "2";
	
	/**
	 * 审批拒绝
	 */
	public static final String APPROVE_FAIL = "3";
	
	/**
	 * 渠道 10综合服务平台，20业务系统，30渠道
	 */
	public static final String DATA_SOURCE_CHANNEL = "10";
	/**
	 * 核心
	 */
	public static final String DATA_SOURCE_BSP = "20";
	/**
	 * 渠道
	 */
	public static final String DATA_SOURCE_YBMAP = "30";
	/**
	 * 推送信息已读
	 */
//	public static final String PUSH_MSG_IS_READ = "1";
	
	/**
	 * 推送信息未读
	 */
//	public static final String PUSH_MSG_IS_NOT_READ = "0";
	
	/**
	 * 批量流水表业务类型——APP公共
	 */
//	public static final String MI100_TRANSTYPE_PUSHCOMMSG = "00";
	
	/**
	 * 批量流水表业务类型——APP报盘
	 */
//	public static final String MI100_TRANSTYPE_PUSHMSG = "01";
	
	/**
	 * 批量流水表业务类型——微信公共
	 */
//	public static final String MI100_TRANSTYPE_WEIXINMSG = "02";
	/**
	 * 批量流水表业务类型——微信报盘
	 */
//	public static final String MI100_TRANSTYPE_WEIXINBPMSG = "03";
	
	/**
	 * 公共信息的条件组合ID
	 */
	public static final String MI312_CONDITION_RADIO_ID_COM = "COM_CONDITION_RADIO";
	
	/**
	 * 数据库编码
	 */
	public static final String DATABASE_ENCODING = "GBK";
	
	/**
	 * 留言问题标记
	 */
	public static final String MSG_REQ_FLG = "REQ";
	/**
	 * 留言答案标记
	 */
	public static final String MSG_RES_FLG = "RES";
	
	/**
	 * 留言状态标记 0：未回复
	 */
	public static final String MSG_STATUS_FLG_ZERO = "0";
	/**
	 * 留言状态标记1：已回复
	 */
	public static final String MSG_STATUS_FLG_ONE = "1";

	/**
	 * 内容状态标记 0-未发布
	 */
	public static final String PUBLISH_FLG_ZERO = "0";
	/**
	 * 内容状态标记 1-发布
	 */
	public static final String  PUBLISH_FLG_ONE = "1";
	/**
	 * 内容状态标记 2-审批中
	 */
	public static final String  PUBLISH_FLG_TWO = "2";
	/**
	 * 内容状态标记 3-已审批
	 */
	public static final String  PUBLISH_FLG_THREE = "3";
	
	/**
	 * 内容状态标记 4-未通过
	 */
	public static final String  PUBLISH_FLG_FOUR = "4";
	
	/**
	 * 本期看点标记 0-否
	 */
	public static final String ATTENTION_FLG_ZERO = "0";
	/**
	 * 本期看点标记 1-是
	 */
	public static final String  ATTENTION_FLG_ONE = "1";
	
	/**
	 * 码表中版块的编码
	 */
	public static final String  FORUM_CODE = "newspaperforum";
	
	/**
	 * 码表中栏目的编码
	 */
	public static final String  COLUMNS_CODE = "newspapercolumns";
	
	/**
	 * 码表中移动客户端的编码
	 */
	public static final String  MOBILE_CLIENT_CODE = "mobileClient";
	
	/**
	 * 码表中移动客户端的编码-微信客户端
	 */
	public static final String  WEIXIN_MOBILE_CLIENT = "1";
	
	/**
	 * 码表中移动客户端的编码-android客户端
	 */
	public static final String  ANDROID_MOBILE_CLIENT = "2";
	
	/**
	 * 码表中移动客户端的编码-ios客户端
	 */
	public static final String  IOS_MOBILE_CLIENT = "3";
	
	/**
	 * 点赞标记：0取消点赞
	 */
	public static final String IS_NOT_PRAISEFLG = "0";
	/**
	 * 点赞标记：1点赞
	 */
	public static final String IS_PRAISEFLG = "1";
	
	/**
	 * 栏目状态：1：开
	 */
	public static final String IS_OPEN = "1";
	/**
	 * 栏目状态：0:关
	 */
	public static final String IS_CLOSE = "0";
	
	/**
	 * 是否头版：1：是
	 */
	public static final String IS_FIRST_FORUM = "1";
	/**
	 * 是否头版：0：否
	 */
	public static final String IS_NOT_FIRST_FORUM = "0";
	
	/**
	 * 内容管理_数据来源：1：后台维护
	 */
	public static final String CONTENT_SOURCE_PLAT_EDIT = "1";
	/**
	 * 内容管理_数据来源：2：后台同步
	 */
	public static final String CONTENT_SOURCE_PLAT_AUTO = "2";
	/**
	 * 大连日志标记：0：失败
	 */
	public static final String DL_LOG_BZ_ERR = "0";
	/**
	 * 大连日志标记：1：成功
	 */
	public static final String DL_LOG_BZ_SUC = "1";
	
	/**
	 * 文档类型：10为文本文档
	 */
	public static final String DOC_TYPE_TXT = "10";
	/**
	 * 文档类型：20为HTML文档
	 */
	public static final String DOC_TYPE_HTML = "20";
	/**
	 * 文档类型：30为链接文档
	 */
	public static final String DOC_TYPE_LINK = "30";
	/**
	 * 文档类型：40为外部文件文档
	 */
	public static final String DOC_TYPE_EXTERNAL_TXT = "40";
	
	/**
	 * 消息推送——定时
	 */
	public static final String TINING_YES = "1";
	/**
	 * 消息推送——非定时
	 */
	public static final String TINING_NO = "0";
	
	
	
	/**
	 * 索引页
	 */
	public static final String INDEX = "index";
	/**
	 * 首页模板
	 */
	public static final String TPLDIR_INDEX = "index";
	/**
	 * 首页模板名称
	 */
	public static final String TPL_INDEX = "tpl.index";
}
