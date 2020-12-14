<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>公共短信息推送</title>
<%@ include file="/include/styles.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
  //UserContext user=UserContext.getInstance();

  //if(user==null)  {
    // out.println("超时");
     //return;
  //}
  //user.setBeiyong("duanxinImg");
  session.setAttribute("beiyong","duanxinImg");
%>


<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/message/comMessage.css" />
<%@ include file="/include/scripts.jsp"%>
<script type="text/javascript" src="<%=_contexPath%>/scripts/datagrid-detailview.js"></script>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/themes/default/css/ueditor.css" />
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.all.js"> </script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>




<script type="text/javascript">
var editor;
var meod="";
var editMeod="00";
var pusMessageType= <%=request.getAttribute("pusMessageType")%>;

var themeType= <%=request.getAttribute("themeType")%>;

var tsmsgtype= <%=request.getAttribute("tsmsgtype")%>;
var editid="null";
$(function() {
	$('#dg').datagrid({
		title : '公共短消息推送管理',
		height : $(parent).height() - 85,
		url : 'webapi30201.json',
		method : 'post',
		queryParams : {'method' : 'query'},
		singleSelect: true,
		toolbar : '#tb',
		columns : [[
			//{field : 'commsgid',checkbox : true},
			{field:'commsgid',field:'checkbox',title:'选择',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.commsgid + '"/>';
			}},
			{title : '消息标题',field : 'title',width : 380},
			{title : '消息类型',field : 'tsmsgtype',width : 180,formatter : function(value) {
					$(tsmsgtype).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '消息内容',hidden:true,field : 'detail',width : 480},
			{title : '消息内容',hidden:true,field : 'tsmsg',width : 480},
			{title : '客服电话',field : 'param1',width : 100},
			{title : '是否有图片',field : 'param2',width : 100,formatter : function(value) {
					return value == null || value == "" ? "否": "是";
				}
			},
			{title : '是否审批',field : 'status',width : 100,formatter : function(value) {
					return value == '1' || value == "" ? "已审批": "未审批";
				}
			},
			{field:'action',title:'操作',align:'center',width:150,formatter:function (value,row,index){
				var e = '<a href="#" onclick="editRow('+row.commsgid+')">修改</a> ';
                var a = '<a href="#" onclick="authRow('+row.commsgid+')">审批</a> ';
                var p = '<a href="#" onclick="pushRow('+row.commsgid+','+row.status+','+row.tsmsgtype+')">推送</a>';
				return e+a+p;
			}}
		]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="padding:5px 0"></div>';
		},
		onExpandRow : function(index, row) {
			$('#ddv-' + index).panel({
				height : 350,
				border : false,
				cache : false,
				href : 'page3020101.html?commsgid=' + row.commsgid+'&tsmsgtype='+row.tsmsgtype,
				onLoad : function() {
					$('#dg').datagrid('fixDetailRowHeight',index);
				}
			});
			$('#dg').datagrid('fixDetailRowHeight', index);
		}
	});
	$('#dlg-buttons,#d_dlg2,#d_dlg').hide();
});

function add() {
	var load = {
		"method" : "add"
	};
	meod="add";
	var href = "page3020102.html";
	var title = "添加公共短信息";
	$('#d_dlg').show().dialog({
		title : title,
		width : 800,
		height : 500,
		closed : false,
		cache : false,
		href : href,
		onLoad : function() {
			ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
			$('#h_action').attr('value', 'webapi30201.json');
			$('#h_formName').attr('value', 'form30201');
			//$('form').form('load', load);
			$('#showImg').closest('.panel').hide();
			/*
			editor = KindEditor.create('textarea[name="tsmsg"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
			});*/
			
			
		},
		buttons : '#dlg-buttons',
		modal : true
	});
}


//添加文本开始
function addText() {
	var load = {
		"method" : "addText"
	};
	editMeod='01';
	meod="addText";
	var href = "page3020103.html";
	var title = "添加文本短信息";	
	$('#d_dlg').show().dialog({
		title : title,
		width : 1100,
		height : 530,
		closed : false,
		cache : false,
		href : href,
		onLoad : function() {
			ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
			$('#h_action').attr('value', 'webapi30201.json');
			$('#h_formName').attr('value', 'form30201');
			$('form').form('load', load);
			$('#showImg').closest('.panel').hide();
			/*
			editor = KindEditor.create('textarea[name="tsmsg"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
			});*/
			
			editor = UE.getEditor('tsmsg',{
				toolbars:[[
		          		'fullscreen', 'source', '|', 'undo', 'redo', '|',
		          		'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
		          		'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		          		'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
		          		'directionalityltr', 'directionalityrtl', 'indent', '|',
		          		'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
		          		'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
		          		'simpleupload', 'pagebreak', '|',
		          		'horizontal', 'date', 'time', '|',
		          		'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
		          		'link','unlink', '|',
		          		'preview'
				]]
			});
			
		},
		buttons : '#dlg-buttons',
		modal : true
	});
	$('#savebutton').show();
}
//添加文本结束


//添加图片开始
function addImage() {
	var load = {
		"method" : "addImage"
	};
	editMeod='02';
	meod="addImage";
	var href = "page3020104.html";
	var title = "添加图片短信息";
	$('#d_dlg').show().dialog({
		title : title,
		width : 800,
		height : 500,
		closed : false,
		cache : false,
		href : href,
		onLoad : function() {
			ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
			$('#h_action').attr('value', 'webapi30201.json');
			$('#h_formName').attr('value', 'form30201');
			$('form').form('load', load);
			$('#showImg1').closest('.panel').hide();
			/*
			editor = KindEditor.create('textarea[name="tsmsg"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
			});
			*/
		},
		buttons : '#dlg-buttons',
		modal : true
	});
	$('#savebutton').show();
}
//添加图片结束


//添加图文开始
function addTextImage() {
	editMeod='03';
	var load = {
		"method" : "addTextImage"
	};
	editid="null";
	meod="addTextImage";
	var href = "page3020105.html";
	var title = "添加图文短信息";
	$('#d_dlg').show().dialog({
		title : title,
		width : 1100,
		height : 530,
		closed : false,
		cache : false,
		href : href,
		onLoad : function() {
			ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
			$('#h_action').attr('value', 'page3020105.html');			
			$('#h_formName').attr('value', 'form30201');
			$('form').form('load', load);
			
			$('#showImg').closest('.panel').hide();
			/*
			editor = KindEditor.create('textarea[name="mstsmess"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
			});*/
			editor = UE.getEditor('mstsmess',{
				toolbars:[[
		          		'fullscreen', 'source', '|', 'undo', 'redo', '|',
		          		'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
		          		'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		          		'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
		          		'directionalityltr', 'directionalityrtl', 'indent', '|',
		          		'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
		          		'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
		          		'simpleupload', 'pagebreak', '|',
		          		'horizontal', 'date', 'time', '|',
		          		'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
		          		'link','unlink', '|',
		          		'preview'
				]]
			});			
		},
		buttons : '#dlg-buttons',
		modal : true
	});
	$('#savebutton').hide();
}
//添加图片结束



//保存记录，添加修改分别调不同的路径
function save() {
	if("form30201"==$('#h_formName').val()){
		if(!("02" == editMeod)){
			if(meod!="addImage"){
				$("#tsmsg").val(editor.getContent());
			}
		}					
	}	
		
	var paras = $('#' + $('#h_formName').val()).serializeArray();
	var url = $('#h_action').val();
	
	//校验
	//if (!ydl.formValidate($('#h_formName').val())) return;
	if (!$("#"+$('#h_formName').val()).form("validate")) return;
	ydl.displayRunning(true);	
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : paras,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode == SUCCESS_CODE) {
				$('#dg').datagrid('reload');
				$('#d_dlg').dialog('close');
				editMeod="00";
			}
			ydl.displayRunning(false);
		},
		error : function() {
			$.messager.alert('错误', '网络连接出错！', 'error');
			ydl.displayRunning(false);
		}
	});
}

//删除公共短信息
function removemsg() {

	var ids = [];
	//var rows = $('#dg').datagrid('getSelections');
	//if (rows.length == 0) {
	//	$.messager.alert('提示', "请选择要删除的公共短信息！", 'info');
	//	return;
	//}
	//for (var i = 0; i < rows.length; i++) {
	//	ids.push({
	//		"name" : "listCommsgid",
	//		"value" : rows[i].commsgid
	//	});
	//}
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length == 0) {
		$.messager.alert('提示', "请选择要删除的公共短信息！", 'info');
		return;
	}

	$checkboxs.each(function(index){
		ids.push({name : "listCommsgid",value : this.value});
	});
	ids.push({name : "method",value : "delete"});

	$.messager.confirm('提示','是否要删除选中的公共短信息？', function(isDel){
		if (isDel) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30201.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					$.messager.alert('提示', data.msg, 'info');
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
					}
					ydl.displayRunning(false);
				},
				error : function() {
					$.messager.alert('错误', '网络连接出错！', 'error');
					ydl.displayRunning(false);
				}
			});
		}
	});
}

// 修改公共短信息
function edit() {
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', "请选择要一条修改的公共短信息！", 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', "请选择一条公共短信息进行修改！当前选中" + rows.length + "条。",'info');
		return;
	}
	// 判定当前选中记录的审批状态:已审批
	if('1' == rows[0].status){
		$.messager.alert('提示', "当前要修改的公共短消息已审批，不能再进行修改！",
				'info');
		return;
	}
	
	if("01" == rows[0].tsmsgtype){
		editMeod="01";
		var load = $.extend({"method" : "editText"}, rows[0]);
		var href = "page3020103.html";
		var title = "修改公共短信息";
		$('#d_dlg').show().dialog({
			title : title,
			width : 850,
			height : 500,
			closed : false,
			cache : false,
			href : href,
			onLoad : function() {
				ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
				$('#h_action').attr('value', 'webapi30201.json');
				$('#h_formName').attr('value', 'form30201');					
				$('form').form('load', load);
				//var arr = load.pusMessageType.split(',');
				//$('#pusMessageType').combobox('setValues', arr);
				// 加载信息中的图片
				loadImg(load.param2, '<%=request.getAttribute("downloadPath") == null ? ""
					: request.getAttribute("downloadPath").toString().replaceAll("\\\\", "\\\\\\\\")%>');
			/*
			editor = KindEditor.create('textarea[name="tsmsg"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
				});*/
			},
			buttons : '#dlg-buttons',
			modal : true
		});
		$('#savebutton').show();
	}else if("02" == rows[0].tsmsgtype){
		editMeod="02";
		var load = $.extend({"method" : "editImage"}, rows[0]);
		var href = "page3020104.html";
		var title = "修改公共短信息";
		$('#d_dlg').show().dialog({
			title : title,
			width : 850,
			height : 500,
			closed : false,
			cache : false,
			href : href,
			onLoad : function() {
				ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
				$('#h_action').attr('value', 'webapi30201.json');
				$('#h_formName').attr('value', 'form30201');					
				$('form').form('load', load);
				//var arr = load.pusMessageType.split(',');
				//$('#pusMessageType').combobox('setValues', arr);
				// 加载信息中的图片
				
				loadImg(load.param2, '<%=request.getAttribute("downloadPath") == null ? ""
					: request.getAttribute("downloadPath").toString().replaceAll("\\\\", "\\\\\\\\")%>');
			/*
			editor = KindEditor.create('textarea[name="tsmsg"]', {			
				allowFileManager : true,
				allowImageRemote : false,
				items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
				],
				uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
				});*/
			},
			buttons : '#dlg-buttons',
			modal : true
		});
		$('#savebutton').show();
	}else if("03" == rows[0].tsmsgtype){
		editid=rows[0].commsgid;
		editMeod="03";			
		var load = {
			"method" : "addTextImage"
		};
		
		meod="addTextImage";
		var href = "page3020105.html";
		var title = "添加图文短信息";
		$('#d_dlg').show().dialog({
			title : title,
			width : 1050,
			height : 530,
			closed : false,
			cache : false,
			href : href,
			onLoad : function() {
				ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
				$('#h_action').attr('value', 'page3020105.html');			
				$('#h_formName').attr('value', 'form30201');
				$('form').form('load', load);
				$('#showImg').closest('.panel').hide();
				/*
				editor = KindEditor.create('textarea[name="mstsmess"]', {			
					allowFileManager : true,
					allowImageRemote : false,
					items:[
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			        'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
			        'anchor', 'link', 'unlink', '|', 'about'
					],
					uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
				});	*/		
			},
			buttons : '#dlg-buttons',
			modal : true
		});	
		$('#savebutton').hide();	
	}	
}

// 推送信息
function send() {

	var ids = [];
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', "请选择一条要推送的公共短信息！", 'info');
		return;
	}
	var $checkboxs = $('input:checkbox:checked');
	if (rows.length >1 ) {
		$.messager.alert('提示', "请选择一条公共短信息进行推送！", 'info');
		return;
	}
	// 判定当前选中记录的审批状态:已审批以外的情况
	if('1' != rows[0].status){
		$.messager.alert('提示', "当前要推送的公共短消息未审批，请审批后再推送！",
				'info');
		return;
	}
	
	$('#tstype').show().dialog('open');
	var tsType = new Array();	
	if(rows[0].tsmsgtype=="01"){
		tsType[0]=pusMessageType[1];
		//大连没有微信20150815，注释掉，待有微信时打开此功能tsType[1]=pusMessageType[2];		
	}else if(rows[0].tsmsgtype=="02"){
		tsType[0]=pusMessageType[2];
	}else if(rows[0].tsmsgtype=="03"){
		tsType[0]=pusMessageType[2];
	}	
	$('#pusMessageType').combobox({
		mode : "local",
		data : tsType,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"
		//value : "00"
		//disabled : true
	});
	
	$('#freeuse1').val(rows[0].commsgid);
	$('#freeuse2').val(rows[0].tsmsgtype);
	/*
	
	ids.push({
		"name" : "commsgid",
		"value" : rows[0].commsgid
	});
	ids.push({
		name : "method",
		value : "send"
	});
	ids.push({
		"name":"tsmsgtype",
		"value":rows[0].tsmsgtype
	});
	$.messager.confirm('提示','是否要推送选中的公共短信息，可能需要较长时间来完成？', function(isSend){
		if (isSend) {
			ydl.displayRunning(true);			
			$.ajax({
				type : "POST",
				url : 'webapi30201.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
						$.messager.alert('提示', '推送成功完成，共发送['+data.count+']条此信息。', 'info');
					} 
					else $.messager.alert('提示', data.msg, 'info');
					ydl.displayRunning(false);
				},
				error : function() {
					$.messager.alert('错误', '网络连接出错！', 'error');
					ydl.displayRunning(false);
				}
			});			
		}
	});	
	*/
}
// 审批
function auth() {
	var ids = [];
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length == 0) {
		$.messager.alert('提示', "请选择要审批的公共短信息！", 'info');
		return;
	}
	$checkboxs.each(function(index){
		ids.push({name : "listCommsgid",value : this.value});
	});
	ids.push({name : "method",value : "auth"});
	
	$.messager.confirm('提示','是否要审批选中的公共短信息？', function(isAuth){
		if (isAuth) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30201.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
						$.messager.alert('提示', data.msg, 'info');
					} 
					else $.messager.alert('提示', data.msg, 'info');
					ydl.displayRunning(false);
				},
				error : function() {
					$.messager.alert('错误', '网络连接出错！', 'error');
					ydl.displayRunning(false);
				}
			});
		}
	});
}

function closetstype(){	
	$('#tstype').dialog('close');
}
function sendTSMess(){	
	
	var paras = $('#formTS').serializeArray();
	var url = "<%=request.getContextPath()%>/webapi30201.json?method=send";
	
	//校验
	//if (!ydl.formValidate($('#h_formName').val())) return;
	//if (!$("#"+$('#h_formName').val()).form("validate")) return;
	ydl.displayRunning(true);	
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : paras,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode == SUCCESS_CODE) {
				$('#dg').datagrid('reload');
				$('#tstype').dialog('close');
				editMeod="00";
			}
			ydl.displayRunning(false);
		},
		error : function() {
			$.messager.alert('错误', '网络连接出错！', 'error');
			ydl.displayRunning(false);
		}
	});
}

function editRow(commsgid) {
	$.ajax({
		type : 'POST',
		url : "<%=request.getContextPath()%>/webapi30201.json",
		dataType: 'json',
		data:{'method' : 'queryById',commsgid:commsgid},
		success : function(data) {
			if ("000000" == data.recode) {
				// 判定当前选中记录的审批状态:已审批
				if('1' == data.result.status){
					$.messager.alert('提示', "当前要修改的公共短消息已审批，不能再进行修改！",	'info');
					return;
				}
				if("01" == data.result.tsmsgtype){
					editMeod="01";
					var load = $.extend({"method" : "editText"}, data.result);
					var href = "page3020103.html";
					var title = "修改公共短信息";
					$('#d_dlg').show().dialog({
						title : title,
						width : 1100,
						height : 530,
						closed : false,
						cache : false,
						href : href,
						onLoad : function() {
							ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
							$('#h_action').attr('value', 'webapi30201.json');
							$('#h_formName').attr('value', 'form30201');					
							$('form').form('load', load);
							//var arr = load.pusMessageType.split(',');
							//$('#pusMessageType').combobox('setValues', arr);
							// 加载信息中的图片
							loadImg(load.param2, '<%=request.getAttribute("downloadPath") == null ? ""
								: request.getAttribute("downloadPath").toString().replaceAll("\\\\", "\\\\\\\\")%>');
							/*
							editor = KindEditor.create('textarea[name="tsmsg"]', {			
								allowFileManager : true,
								allowImageRemote : false,
								items:[
		        					'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        					'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        					'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        					'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        					'anchor', 'link', 'unlink', '|', 'about'
								],
								uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
							});*/
							
							
							editor = UE.getEditor('tsmsg',{
								toolbars:[[
						          		'fullscreen', 'source', '|', 'undo', 'redo', '|',
						          		'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
						          		'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
						          		'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
						          		'directionalityltr', 'directionalityrtl', 'indent', '|',
						          		'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
						          		'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
						          		'simpleupload', 'pagebreak', '|',
						          		'horizontal', 'date', 'time', '|',
						          		'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
						          		'link','unlink', '|',
						          		'preview'
								]]
							});
							
							
							
						},
						buttons : '#dlg-buttons',
						modal : true
					});
					$('#savebutton').show();
				}else if("02" == data.result.tsmsgtype){
					editMeod="02";
					var load = $.extend({"method" : "editImage"}, data.result);
					var href = "page3020104.html";
					var title = "修改公共短信息";
					$('#d_dlg').show().dialog({
						title : title,
						width : 850,
						height : 500,
						closed : false,
						cache : false,
						href : href,
						onLoad : function() {
							ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
							$('#h_action').attr('value', 'webapi30201.json');
							$('#h_formName').attr('value', 'form30201');					
							$('form').form('load', load);
							//var arr = load.pusMessageType.split(',');
							//$('#pusMessageType').combobox('setValues', arr);
							// 加载信息中的图片
				
							loadImg(load.param2, '<%=request.getAttribute("downloadPath") == null ? ""
								: request.getAttribute("downloadPath").toString().replaceAll("\\\\", "\\\\\\\\")%>');
							/*
							editor = KindEditor.create('textarea[name="tsmsg"]', {			
								allowFileManager : true,
								allowImageRemote : false,
								items:[
		        					'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
		        					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        					'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        					'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        					'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
		        					'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
		        					'anchor', 'link', 'unlink', '|', 'about'
								],
								uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
							});*/
						},
						buttons : '#dlg-buttons',
						modal : true
					});
					$('#savebutton').show();
				}else if("03" == data.result.tsmsgtype){
					editid=data.result.commsgid;
					editMeod="03";			
					var load = {
						"method" : "addTextImage"
					};
		
					meod="addTextImage";
					var href = "page3020105.html";
					var title = "添加图文短信息";
					$('#d_dlg').show().dialog({
						title : title,
						width : 1100,
						height : 530,
						closed : false,
						cache : false,
						href : href,
						onLoad : function() {
							ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
							$('#h_action').attr('value', 'page3020105.html');			
							$('#h_formName').attr('value', 'form30201');
							$('form').form('load', load);
							//$('#showImg').closest('.panel').hide();
							/*
							editor = KindEditor.create('textarea[name="mstsmess"]', {			
								allowFileManager : true,
								allowImageRemote : false,
								items:[
			        				'source', '|', 'undo', 'redo', '|', 'preview', 'print','code', 'cut', 'copy', 'paste',
			        				'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			        				'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        				'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        				'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        				'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			        				'insertfile', 'table', 'hr', 'emoticons', 'pagebreak',
			        				'anchor', 'link', 'unlink', '|', 'about'
								],
								uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
							});	*/	
							
							
							
							editor = UE.getEditor('mstsmess',{
								toolbars:[[
						          		'fullscreen', 'source', '|', 'undo', 'redo', '|',
						          		'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
						          		'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
						          		'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
						          		'directionalityltr', 'directionalityrtl', 'indent', '|',
						          		'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
						          		'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
						          		'simpleupload', 'pagebreak', '|',
						          		'horizontal', 'date', 'time', '|',
						          		'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
						          		'link','unlink', '|',
						          		'preview'
								]]
							});
							
							
							
							
								
						},
						buttons : '#dlg-buttons',
						modal : true
					});	
					$('#savebutton').hide();	
				}
			}else {
				$.messager.alert('提示',data.msg,'info');
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function authRow(commsgid) {
	var ids = [];
	ids.push({name : "listCommsgid",value : commsgid});
	ids.push({name : "method",value : "auth"});
	$.messager.confirm('提示','是否要审批此条公共短信息？', function(isAuth){
		if (isAuth) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30201.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
						$.messager.alert('提示', data.msg, 'info');
					} 
					else $.messager.alert('提示', data.msg, 'info');
					ydl.displayRunning(false);
				},
				error : function() {
					$.messager.alert('错误', '网络连接出错！', 'error');
					ydl.displayRunning(false);
				}
			});
		}
	});
}
function pushRow(commsgid,status,tsmsgtype) {
	// 判定当前选中记录的审批状态:已审批以外的情况
	if('1' != status){
		$.messager.alert('提示', "当前要推送的公共短消息未审批，请审批后再推送！",
				'info');
		return;
	}
	tsmsgtype = '0'+tsmsgtype;
	$('#tstype').show().dialog('open');
	var tsType = new Array();	
	if(tsmsgtype=="01"){
		tsType[0]=pusMessageType[1];
		tsType[1]=pusMessageType[2];
		//大连没有微信20150815，注释掉，待有微信时打开此功能tsType[1]=pusMessageType[2];
	}else if(tsmsgtype=="02"){
		tsType[0]=pusMessageType[2];
	}else if(tsmsgtype=="03"){
		tsType[0]=pusMessageType[2];
	}	
	$('#pusMessageType').combobox({
		mode : "local",
		data : tsType,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"
		//value : "00"
		//disabled : true
	});
	
	$('#freeuse1').val(commsgid);
	$('#freeuse2').val(tsmsgtype);
}
</script>
</head>
<body>
	<table id="dg"></table>
	<div id="tb">
		<div>
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="send()">推送公共短信息</a> -->
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加公共短信息</a> -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addText()">创建文本消息</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addImage()">创建图片消息</a><!--  -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTextImage()">创建图文消息</a><!-- -->
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">修改公共短信息</a> -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removemsg()">删除公共短信息</a>
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="auth()">审批公共短信息</a> -->
			
		</div>
	</div>
	<div id="d_dlg"></div>
	<div id="dlg-buttons">
		<a id="savebutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#d_dlg').dialog('close')">关闭</a>
	</div>
	<input type="hidden" id='h_action' />
	<input type="hidden" id='h_formName' />
	
	<div id="d_dlg_tw"></div>
	
	
	
	
	
	
	<div id="tstype" title="选择推送方式" class="easyui-dialog" style="width:330px;height:170px;padding:10px 20px" closed="true" modal="true" buttons="#tstype-buttons">
		<div class="formtitle">推送方式</div>	
		<form id="formTS" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
			<!-- <th><label for="pusMessageType">推送方式：</label></th>  -->
			<input id="pusMessageType" name="pusMessageType" class="easyui-combobox"  style="width:250px;" data-options="multiple:true"/>	
			<div id="tstype-buttons">        	
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="sendTSMess()">确认推送</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closetstype()">取消关闭</a>
			</div>
			
			<input type="hidden" id="freeuse1" name="freeuse1" />
			<input type="hidden" id="freeuse2" name="freeuse2" />
			
		</form>
	</div>	
</body>
</html>