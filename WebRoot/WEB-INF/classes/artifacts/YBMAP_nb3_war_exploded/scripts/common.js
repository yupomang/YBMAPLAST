/**
 * 公共脚本函数库
 */

//返回码
var SUCCESS_CODE = '000000';//操作成功时返回码
var BLANK_CODE = '280001';	//查询无数据时返回码


/************************************************************************************************
 * JS内部类型原型扩展
 ************************************************************************************************/

/**
 * 字符串去左右空白字符（包括空格、制表符、回车、换行、换页符等）
 * 直接在字符串变量或字符串直接量上使用
 * （只有IE不支持String.trim()，其他浏览器的String对象已经包含了此方法。）
 * @example stringValue.rtrim(); ' hello world '.trim();
 * @returns {String} 去空白字符后的字符串
 */
if (!String.prototype.trim) String.prototype.trim = function() {
	return this.replace(/^[　\s]+|[　\s]+$/g, '');
};
if (!String.prototype.ltrim) String.prototype.ltrim = function() {
	return this.replace(/^[　\s]+/, '');
};
if (!String.prototype.rtrim) String.prototype.rtrim = function() {
	return this.replace(/[　\s]+$/, '');
};
String.prototype.delSpace = function() {
	return this.replace(/[　\s]/g, '');
};

/**
 * 计算字符串中的中文字符数（不含全角标点符号和特殊字符）
 * @example '你好China'.hzLength(); //返回值是2
 * @returns {Number} 字符串中的中文字符数
 */
if (!String.prototype.hzLength) String.prototype.hzLength = function() {
	return this.replace(/[^\u4e00-\u9fa5]/g, '').length;
};

/**
 * 按全角两个字符，半角一个字符计算的字符串长度
 * @example '你好China'.length2();	//返回值是9
 * @returns {Number} 长度值
 */
if (!String.prototype.length2) String.prototype.length2 = function() {
	return this.replace(/[^\x00-\xff]/g, '..').length;
};

/**
 * 将yyyy-MM-dd格式的日期字符串转成日期对象
 * @example var d = '2011-03-01'.toDate()
 * @returns {Date} 日期对象，如果字符串不是正确的日期格式，返回null
 */
if (!String.prototype.toDate) String.prototype.toDate = function() {
	if (!this) return null;
	var ms = Date.parse(this.replace(/-/g, '/').replace(/^(\d{4})(\d{2})(\d{2})$/, '$1/$2/$3'));
	if (isNaN(ms)) return null;
	else return new Date(ms);
};


//库函数命名空间
var ydl = {};

(function (ydl) {
	
/**
 * 显示正在运行动画，并禁止点击页面
 * @param {Boolean} [isRunning=true] 是否正在运行
 * @returns undefined
 */
ydl.displayRunning = function(isRunning) {
	if (isRunning === undefined) isRunning = true;
	var overlay = top.document.getElementById('page-running-overlay');
	if (isRunning) $(overlay).show();
	else $(overlay).hide();
};

/**
 * 表单校验，获得第一个校验不通过输入域的焦点--暂无用
 * @param {String} formName 表单id
 * @returns {Boolean} 校验是否通过
 */
ydl.formValidate = function(formId) {
	if (!$('#'+formId).form( "validate" ,function(v){})) {
		$('#'+formId+' .validatebox-invalid:eq(0)').focus();
		return false;
	}
	else return true;
};

/**
 * 清除表单输入组件上的校验错误信息
 * @param {String} formName 表单id
 * @returns undefined
 */
ydl.delErrorMessage = function(formId) {
	var $form = $('#'+formId);
	$form.find('.validatebox-invalid').removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');
};

/**
 * 设置easyui对话框的top值 
 * @param {String} dlgId 对话框id
 * @param {Object} option {top:'0px',left:'0px'} 
 * @returns undefined
 */
ydl.setDlgPosition = function(dlgId,option){
	var $window = $('#'+dlgId).closest('.window');
	if (option) $window.css('top',option.top || '0px').css('left',option.left || '0px');
	else {
		var _top = $window.css('top');
		var _left = $window.css('left');
		if (_top.indexOf('-') >= 0) $window.css('top','0px');
		if (_left.indexOf('-') >= 0) $window.css('left','0px');
	}
};


})(ydl);


/************************************************************************************************
 * easyui原型扩展
 ************************************************************************************************/
//校验 
$.extend($.fn.validatebox.defaults.rules, {   
	//电话号码
    phone: {   
		validator: function(value,param){  
			var fieldvalue = value.replace(/（/g, '(').replace(/）/g, ')');
			return /^(1\d{10}|(0\d{2,3}-|\(0\d{2,3}\))?[1-9]\d{4,7}(-\d{1,8})?)$/.test(fieldvalue);
	    }, 
	    message: '请输入正确的电话号码格式！\n（例如13812345678、010-12345678、(010)12345678-1234等）'  
	},
	//邮政编码
	zipcode: {
		validator: function(value,param){  
			return /^\d{6}$/.test(value);
	    }, 
	    message: '此输入项必须是正确的邮政编码！\n（6位数字）'  
	}, 
	//yyyy-mm-dd日期格式
	date: {
		validator: function(value,param){  
			return /^(\d{4})-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01])$/.test(value) && checkValidDate([value.substr(0, 4), value.substr(5, 2), value.substr(8, 2)]);
	    }, 
	    message: '此输入项必须是正确的日期！\n（yyyy-mm-dd）'  
	},
	//不能输入特殊字符,参数：自定义不允许输入的字符
	validchar: {
		validator: function(value,param){  
			if (param) {
				for (var i =0; i <  param[0].length; i++) {
					if (value.indexOf(param[0][i]) >= 0) return false;
				}
				return true;
			}
	    }, 
	    message: '此输入项中不能含有以下字符！\n（{0}）'  
	},
	//最小日期
	mindate: {
		validator: function(value,param){ 
			var mindate = $('#'+param[0]).datebox('getValue');
			var r = (value >= mindate) || (mindate == '');
			if (r) $('#'+param[0]).next().find('.validatebox-invalid').validatebox('remove'); //删除
			return r;
	    }, 
	    message: '请输入大于或等于{1}的日期！' 
	},
	//最大日期
	maxdate: {
		validator: function(value,param){ 
			var maxdate = $('#'+param[0]).datebox('getValue');
			var r = (value <= maxdate) || (maxdate == '');
			if (r) $('#'+param[0]).next().find('.validatebox-invalid').validatebox('remove'); //删除
			return r;
	    }, 
	    message: '请输入小于或等于{1}的日期！' 
	},
	//数字与字母
	numletter: {
		validator: function(value,param){ 
			return /^([0-9a-zA-Z])*$/.test(value);
	    }, 
	    message: '此输入项中只允许输入数字或字母！' 
	},
	//版本编号versionno
	versionno: {
		validator: function(value,param){ 
			return /^\d+(\.\d+)+$/.test(value);
	    }, 
	    message: '请输入正确的版本编号格式！(例如1.1、1.2.11等)' 
	},
	//图片格式
	fileType: {
		validator: function(value,param){ 
			if (param) {
				for (var i =0; i <  param[0].length; i++) {
					if (value.indexOf('.'+param[0][i].toUpperCase()) >= 0 || value.indexOf('.'+param[0][i].toLowerCase()) >= 0) return true;
				}
				return false;
			}
	    }, 
	    message: '请选择({0})格式的图片！' 
	},
	//长度范围
	length: {
		validator: function(value,param){ 
			var len = value.replace(/[^\x00-\xff]/g, '..').length;
			return len >= param[0] && len <= param[1];
	    }, 
	    message: '输入内容长度必须介于{0}与{1}之间！' 
	},
	url: {
		validator: function(value,param){ 
			return /^((https|http):\/\/).*$/.test(value);
	    }, 
	    message: '请输入正确的url地址！(以https://或http://开头)' 
	}
});

//删除验证错误状态
$.extend($.fn.validatebox.methods, {    
    remove: function(jq, newposition){    
        return jq.each(function(){    
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus').unbind('blur');  
        });    
    }  
}); 




/**
 * 检查日期是否合法
 * @param {String[]|Number[]} arr [年, 月, 日]或者[月, 日]
 * @returns {Boolean} 是否合法日期
 */
function checkValidDate(arr) {
	for (var i = 0; i < arr.length; i++) {
		if (typeof arr[i] === 'string') arr[i] = arr[i].replace(/^0+/, '');
	}
	var y, m, d;
	switch (arr.length) {
	case 2:
		y = 2000;	//一个任意的闰年
		m = parseInt(arr[0]) - 1;
		d = parseInt(arr[1]);
		break;
	case 3:
		y = parseInt(arr[0]);
		m = parseInt(arr[1]) - 1;
		d = parseInt(arr[2]);
		break;
	default:
		//checkValidDate出错: date参数数组长度不正确！
		return false;
	}
	//检查原理：Date构造函数支持超出正常范围的月、日值，自动向下一年、月进位，
	//　　　　　检测构造后的年月日是否与输入参数一致就可以知道参数是否在正常范围内。
	var date = new Date(y, m, d);
	return !isNaN(date) && date.getFullYear() == y && date.getMonth() == m && date.getDate() == d;
}


//附带不用修改浏览器安全配置的javascript代码，兼容ie， firefox全系列 --linxiaolong add
/**
 * 取得input[file]选择文件的正确路径
 * @param {input[file] Element} fileElement input[file]的dom对象
 * @return {String} 选择文件的正确路径
 */
function getPath(fileElement) {
	if (fileElement) {
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			fileElement.select();
			window.parent.document.body.focus();
			return document.selection.createRange().text;
		}
		else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
			if (fileElement.files) {
				return fileElement.files.item(0).getAsDataURL();
			}
			return fileElement.value;
		}
		return fileElement.value;
	}
}