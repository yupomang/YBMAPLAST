<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@ include file="/include/PhotoGallery.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<%
  session.setAttribute("beiyong","newsImg");
%>
<title>信息管理</title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ui/xxfb/xxfb.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/jqPagination/css/jqpagination.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/kindeditor/themes/default/default.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/themes/default/css/ueditor.css" />
<script type="text/javascript" src="<%= _contexPath %>/scripts/jqPagination/js/jquery.jqpagination.min.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.all.js"> </script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
//var editor;
var imageUrl;
var uploadbutton;
KindEditor.ready(function(K) {

	uploadbutton = K.uploadbutton({
		button : K('#uploadButton')[0],
		fieldName : 'imgFile',
		url : '<%=request.getContextPath()%>/webapi70001_uploadimg.do',
		afterUpload : function(data) {
			if (data.error === 0) {
				var url = K.formatUrl(data.url, 'absolute');
				imageUrl = url;
				$('#imgView').attr('src', url);
			} else {
				alert(data.message);
			}
		},
		afterError : function(str) {
			alert('自定义错误信息: ' + str);
		}
	});
	uploadbutton.fileBox.change(function(e) {
		uploadbutton.submit();
	});
		
	K('#imageBtn').click(function() {
		editor.loadPlugin('image', function() {
			
			editor.plugin.imageDialog({
				showRemote : false,
				imageUrl : K('#image').val(),
				clickFn : function(url, title, width, height, border, align) {
					K('#image').val(url);
					editor.hideDialog();
				}
			});
		});
	});

	
});

var url;
var result;
var pageSize = 5;

var classificationQryTmp;
var startdateTmp;
var enddateTmp;
var keywordTmp;
var pubStatusQryTmp;
var sourceQryTmp;
var editFlg;
var centeridTmp;
var ue;
$(function(){
	centeridTmp = '<%=user.getCenterid()%>';
	initData();
	initClassfication();
	//window.UEDITOR_HOME_URL = '<%=request.getContextPath()%>';
	ue = UE.getEditor('content',{
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
	//查询
	$('#b_query').click(function (){
		$('.pagination').jqPagination('destroy');
		classificationQryTmp = $('#classificationQry').combotree('getValue');
		startdateTmp = $("#startdate").datebox("getValue");
		enddateTmp = $("#enddate").datebox("getValue");
		keywordTmp = $("#keyword").val();
		pubStatusQryTmp = $("#pubStatusQry").combobox('getValue');
		if ("00041100" == centeridTmp || "00085200"== centeridTmp){
			sourceQryTmp = $("#sourceQry").combobox('getValue');
		}else{
			sourceQryTmp = "";
		}
		loadData();
	});	
	
	//重置
	$('#b_reset').click(function (){
		$('#form70001').form('clear');
		$('#classificationQry').combotree('setValue','');
		ydl.delErrorMessage('form70001');
	});
	
	//添加
	$('#b_add').click(function (){
		$("input[name=imgFile]").css("width","61px");
		clearDlgData();
		$('#dlg').dialog('open').dialog('setTitle','信息_添加');
		$("[name='freeuse3'][value='0']").attr("checked",true);
		var curr_time = new Date();     
		$('#releasetime').datebox("setValue",myformatter(curr_time)); 
		editFlg = '0';
		url = '<%= _contexPath %>/webapi70001.json';
	});
	
	//保存
	$('#b_save').click(function (){
		if(!ue.hasContents()){
			$.messager.alert('错误',"请输入内容！",'error');
			return;
		}else{
			$("#content").val(ue.getContent());
		}
		if('' == ue.getContentTxt() || ' ' == ue.getContentTxt()){
			$("#contentTmp").val(' ');
		}else{
			$("#contentTmp").val(ue.getContentTxt());
		}
		$("#image").val(imageUrl);
		$("#freeuse3").val("0");
		var arr = $('#fm').serializeArray();
		if (!$("#fm").form("validate")) return;
		$.ajax( {
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');
				if ("000000" == data.recode){
					$('#dlg').dialog('close');        // close the dialog
					$('.pagination').jqPagination('destroy');
					loadData();
				}
			},
			error :function(){
				$.messager.alert('错误',data.msg,'error');
			}
		});
	});	

	//保存并发布
	$('#b_save_pub').click(function (){
		if(!ue.hasContents()){
			$.messager.alert('错误',"请输入内容！",'error');
			return;
		}else{
			$("#content").val(ue.getContent());
		}
		if('' == ue.getContentTxt() || ' ' == ue.getContentTxt()){
			$("#contentTmp").val(' ');
		}else{
			$("#contentTmp").val(ue.getContentTxt());
		}
		$("#image").val(imageUrl);
		$("#freeuse3").val("1");
		var arr = $('#fm').serializeArray();
		if (!$("#fm").form("validate")) return;
		$.ajax( {
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');
				if ("000000" == data.recode){
					$('#dlg').dialog('close');        // close the dialog
					$('.pagination').jqPagination('destroy');
					loadData();
				}
			},
			error :function(){
				$.messager.alert('错误',data.msg,'error');
			}
		});
	});	
		
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	//校验标题长度大于0，小于100
	$.extend($.fn.validatebox.defaults.rules, {
		titleLength: {
			validator: function (value, param) {
				return value.length > param[0] && value.length <= param[1];
        	},
        	message:'标题长度不能超过50个汉字'
    	}
	});
	
	// 增加修改窗口的combox联动处理
	$('#classification').combotree({
		onSelect: function(node){
			if ('0' == editFlg){
				$.ajax({
					type : 'POST',
					url : "<%=request.getContextPath()%>/webapi70006.json",
					dataType: 'json',
					data:{classification:node.id},
					success : function(data) {
						$("#imgView").attr('src', data.defaultImgUrl);
					},
					error :function(){
						$.messager.alert('错误','所属栏目默认图标获取失败','error');
					}
				});
			}else{
				
			}
		}
	});
	
	$("#selectpic").click(function(){
		$("#mainbodyhide").removeClass("hide");
		$("#mainbodyhide").css("min-height",$("#submainbody").height());
	});	
	
});
function initClassfication(){
 	var ary = <%=request.getAttribute("ary")%>;
	$('#classificationQry').combotree('loadData', ary);
	$('#classification').combotree('loadData', ary);
}
function initData(){
	var arr = $('#form70001').serializeArray();
	//校验
	if (!$("#form70001").form("validate")) return;
	
	classificationQryTmp = $('#classificationQry').combotree('getValue');
	startdateTmp = $("#startdate").datebox("getValue");
	enddateTmp = $("#enddate").datebox("getValue");
	keywordTmp = $("#kdyword").val();
	pubStatusQryTmp = $("#pubStatusQry").combobox('getValue');
	if ("00041100" == centeridTmp || "00085200"== centeridTmp){
		sourceQryTmp = $("#sourceQry").combobox('getValue');
	}else{
		sourceQryTmp = "";
	}

	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70004.json",
		dataType: 'json',
		data:arr,
		success : function(data) {
			$("#listContainer").empty();
			
			if ("000000" == data.recode){
				$('.pagination').jqPagination({
					current_page: 1, //设置当前页 默认为1
					max_page : data.totalPage, //设置最大页 默认为1
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						getDataByPage(page);
      				}
				});
				$.each(data.rows, function(i, item) {
					var page = 1;
					var imgUrl;
					if("" == item.image){
						imgUrl = item.freeuse1;
					}else{
						imgUrl = item.image;
					}
					var page = 1;
					if("" == item.freeuse5 || item.freeuse5 == null){// 数据来源项无值
						// 发布标记为未发布的视图显示
						if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
							view2(i,page,imgUrl,item);
						}else{
							view1(i,page,imgUrl,item);
						}
					}else{
						// 发布标记为未发布的视图显示
						if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
							view4(i,page,imgUrl,item);
						}else{
							view3(i,page,imgUrl,item);
						}
					}
        		});
			}else{
				$.messager.alert('提示',data.msg,'info');
				$('.pagination').jqPagination({
					current_page: 1, //设置当前页 默认为1
					max_page : 1, //设置最大页 默认为1
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						//getDataByPage(page);
      				}
				});
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function loadData(){
	var arr = $('#form70001').serializeArray();
	//校验
	if (!$("#form70001").form("validate")) return;
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70004.json",
		dataType: 'json',
		data:arr,
		success : function(data) {
			$("#listContainer").empty();
			if ("000000" == data.recode){
				$('.pagination').jqPagination('option', 'current_page', 1);
				$('.pagination').jqPagination('option', 'max_page', data.totalPage);
				$('.pagination').jqPagination({
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						getDataByPage(page);
      				}
				});
				$.each(data.rows, function(i, item) {
					var page = 1;
					var imgUrl;
					if("" == item.image){
						imgUrl = item.freeuse1;
					}else{
						imgUrl = item.image;
					}
					var page = 1;
					if("" == item.freeuse5 || item.freeuse5 == null){// 数据来源项无值
						// 发布标记为未发布的视图显示
						if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
							view2(i,page,imgUrl,item);
						}else{
							view1(i,page,imgUrl,item);
						}
					}else{
						// 发布标记为未发布的视图显示
						if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
							view4(i,page,imgUrl,item);
						}else{
							view3(i,page,imgUrl,item);
						}
					}
        		});
			}else{
				$.messager.alert('提示',data.msg,'info');
				$('.pagination').jqPagination('option', 'current_page', 1);
				$('.pagination').jqPagination('option', 'max_page', 1);
				$('.pagination').jqPagination({
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						//getDataByPage(page);
      				}
				});
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function getDataByPage(page){
    //var arr = $('#form70001').serializeArray();
	//校验
	//if (!$("#form70001").form("validate")) return;
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70004.json?page="+page,
		dataType: 'json',
		//data:arr,
		data:{classificationQry:classificationQryTmp,startdate:startdateTmp,enddate:enddateTmp,keyword:keywordTmp,pubStatusQry:pubStatusQryTmp,sourceQry:sourceQryTmp},
		success : function(data) {
			$("#listContainer").empty();
			$.each(data.rows, function(i, item) {
				var imgUrl;
				if("" == item.image){
					imgUrl = item.freeuse1;
				}else{
					imgUrl = item.image;
				}
				if("" == item.freeuse5 || item.freeuse5 == null){// 数据来源项无值
					// 发布标记为未发布的视图显示
					if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
						view2(i,page,imgUrl,item);
					}else{
						view1(i,page,imgUrl,item);
					}
				}else{
					// 发布标记为未发布的视图显示
					if ("" == item.freeuse3 ||item.freeuse3 == null || "1" == item.freeuse3){
						view4(i,page,imgUrl,item);
					}else{
						view3(i,page,imgUrl,item);
					}
				}
        	});
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function modNews(i, seqno, page) {
	$("input[name=imgFile]").css("width","61px");
	clearDlgData();
	editFlg = '1';
	$.ajax({
		type : 'POST',
		url : "<%=request.getContextPath()%>/webapi70005.json",
		dataType: 'json',
		data:{seqno:seqno},
		success : function(data) {
			if ("000000" == data.recode) {
				$.each(data.resultList, function(i, item) {
					$('#classification').combotree('setValue',item.classification);
					$("#title").val(item.title);
					$("#image").val(item.image);
					imageUrl = item.image;
					//alert(item.image)
					if("" == item.image){
						$('#imgView').attr('src', item.freeuse1);
					}else{
						$('#imgView').attr('src', item.image);
					}
					$("#releasetime").datebox("setValue",item.releasetime);
					$("#introduction").val(item.introduction);
					ue.setContent(item.content);
					$("#seqno").val(item.seqno);
					$("#page").val(page);
					//$("[name='freeuse3'][value='"+item.freeuse3+"']").attr("checked",true);
					$("#freeuse3").val(item.freeuse3);
				});
				$('#dlg').dialog('open').dialog('setTitle','信息-修改');
				url = '<%= _contexPath %>/webapi70003.json';
			}else {
				$.messager.alert('提示',data.msg,'info');
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function delNews(i, seqno, page) {
	$.messager.confirm('提示','确认是否删除?',function(r){
		if (r) {
			$.ajax({
				type : 'POST',
				url : "<%=request.getContextPath()%>/webapi70002.json",
				dataType: 'json',
				data:{seqno:seqno},
				success : function(data) {
					if ("000000" == data.recode){
						//getDataByPage(page);
						$('.pagination').jqPagination('destroy');
						loadData();
						$.messager.alert('提示','删除成功','info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');
				}
			});
		}
	});
}

function pubNews(i, seqno, freeuse3, page) {
	if ("0" != freeuse3){
		$.messager.alert('提示', "该信息已发布！", 'info');
	}else{
		$.messager.confirm('提示','确认是否发布?',function(r){
			if (r) {
				$.ajax({
					type : 'POST',
					url : "<%=request.getContextPath()%>/webapi70007.json",
					dataType: 'json',
					data:{seqno:seqno,freeuse3:freeuse3},
					success : function(data) {
						if ("000000" == data.recode){
							//getDataByPage(page);
							$('.pagination').jqPagination('destroy');
							loadData();
							$.messager.alert('提示','发布成功','info');
						}else{
							$.messager.alert('提示',data.msg,'info');
						}
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');
					}
				});
			}
		});
	}
}
function clearDlgData(){
	editFlg = "";
	imageUrl = "";
	$('#classification').combotree('setValue','');
	$("#title").val("");
	$("#image").val("");
	$("#introduction").val("");
	$("#content").val("");
	//editor.html('');
	ue.setContent('');
	$("#seqno").val("");  
	$("#centerid").val("");
	$("#contentTmp").val("");
	$("#devid").val("");
	$("#devtype").val("");
	$("#userid").val("");
	$("#page").val("");
	$("#releasetime").datebox("setValue", "");
	$("#imgView").attr('src', '');
	$("#freeuse3").val("");
}
//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}

function myformatter(date){  
    var y = date.getFullYear();  
    var m = date.getMonth()+1;  
    var d = date.getDate();  
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);  
}

function view1(i,page,imgUrl,item) {// 数据来源项无值、发布标记为未发布的视图显示
	$("#listContainer").append(
		"<li class='message_item' id='"+item.seqno+"'>"+
			"<div class='message_opr'>"+
				"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/xxfb/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
				"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/xxfb/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
				"<a id='b_pubRecord' onClick=javascript:pubNews("+i+","+item.seqno+","+item.freeuse3+","+page+") href='javascript:void(null)'><img title='发布' src='<%=_contexPath%>/ui/xxfb/images/pub_icon.jpg' width='24' height='24' alt=''/></a>"+
			"</div>"+
			"<div class='message_info'>"+
				"<div class='message_pub' style='margin-left:10px;'></div>"+
				"<div class='message_status' style='margin-left:10px;'>状态：未发布</div>"+
				"<div class='message_time'  style='margin-left:10px;'>栏目："+$('#classificationHidden option[value='+item.classification+']').text()+"</div>"+
				"<div class='user_info'>"+
					"<a class='remark_name'>"+item.title+"</a>"+
					"<a class='avatar' target='_blank' >"+
						"<img src='"+imgUrl+"'/>"+
					"</a>"+
				"</div>"+
			"</div>"+
			"<div class='message_content text'>"+
				"<div class='wxMsg'>"+item.introduction+"</div>"+
			"</div>"+
		"</li>"
	);
}

function view2(i,page,imgUrl,item) {// 数据来源项无值、发布标记为已发布的视图显示
	$("#listContainer").append(
		"<li class='message_item' id='"+item.seqno+"'>"+
			"<div class='message_opr'>"+
				"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/xxfb/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
				"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/xxfb/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
				"<a id='b_pubRecord' onClick=javascript:pubNews("+i+","+item.seqno+","+item.freeuse3+","+page+") href='javascript:void(null)'><img title='发布' src='<%=_contexPath%>/ui/xxfb/images/pub_icon.jpg' width='24' height='24' alt=''/></a>"+
			"</div>"+
			"<div class='message_info'>"+
				"<div class='message_pub' style='margin-left:10px;'>"+item.releasetime+"</div>"+
				"<div class='message_status' style='margin-left:10px;'>状态：<em style='color:#e15f63;font-style: normal;'>已发布</em></div>"+
				"<div class='message_time'  style='margin-left:10px;'>栏目："+$('#classificationHidden option[value='+item.classification+']').text()+"</div>"+
				"<div class='user_info'>"+
					"<a class='remark_name'>"+item.title+"</a>"+
					"<a class='avatar' target='_blank' >"+
						"<img src='"+imgUrl+"'/>"+
					"</a>"+
				"</div>"+
			"</div>"+
			"<div class='message_content text'>"+
				"<div class='wxMsg'>"+item.introduction+"</div>"+
			"</div>"+
		"</li>"
	);
}
function view3(i,page,imgUrl,item) {// 数据来源项有值、发布标记为未发布的视图显示
	$("#listContainer").append(
		"<li class='message_item' id='"+item.seqno+"'>"+
			"<div class='message_opr'>"+
				"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/xxfb/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
				"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/xxfb/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
				"<a id='b_pubRecord' onClick=javascript:pubNews("+i+","+item.seqno+","+item.freeuse3+","+page+") href='javascript:void(null)'><img title='发布' src='<%=_contexPath%>/ui/xxfb/images/pub_icon.jpg' width='24' height='24' alt=''/></a>"+
			"</div>"+
			"<div class='message_info'>"+
				"<div class='message_pub_time' style='margin-left:10px;'></div>"+
				"<div class='message_pub' style='margin-left:10px;'>状态：未发布</div>"+
				"<div class='message_status' style='margin-left:10px;'>来源："+$('#sourceQry option[value='+item.freeuse5+']').text()+"</div>"+
				"<div class='message_time'  style='margin-left:10px;'>栏目："+$('#classificationHidden option[value='+item.classification+']').text()+"</div>"+
				"<div class='user_info'>"+
					"<a class='remark_name'>"+item.title+"</a>"+
					"<a class='avatar' target='_blank' >"+
						"<img src='"+imgUrl+"'/>"+
					"</a>"+
				"</div>"+
			"</div>"+
			"<div class='message_content text'>"+
				"<div class='wxMsg'>"+item.introduction+"</div>"+
			"</div>"+
		"</li>"
	);
}

function view4(i,page,imgUrl,item) {// 数据来源项有值、发布标记为已发布的视图显示
	$("#listContainer").append(
		"<li class='message_item' id='"+item.seqno+"'>"+
			"<div class='message_opr'>"+
				"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/xxfb/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
				"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/xxfb/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
				"<a id='b_pubRecord' onClick=javascript:pubNews("+i+","+item.seqno+","+item.freeuse3+","+page+") href='javascript:void(null)'><img title='发布' src='<%=_contexPath%>/ui/xxfb/images/pub_icon.jpg' width='24' height='24' alt=''/></a>"+
			"</div>"+
			"<div class='message_info'>"+
				"<div class='message_pub_time' style='margin-left:10px;'>"+item.releasetime+"</div>"+
				"<div class='message_pub' style='margin-left:10px;'>状态：<em style='color:#e15f63;font-style: normal;'>已发布</em></div>"+
				"<div class='message_status' style='margin-left:10px;'>来源："+$('#sourceQry option[value='+item.freeuse5+']').text()+"</div>"+
				"<div class='message_time'  style='margin-left:10px;'>栏目："+$('#classificationHidden option[value='+item.classification+']').text()+"</div>"+
				"<div class='user_info'>"+
					"<a class='remark_name'>"+item.title+"</a>"+
					"<a class='avatar' target='_blank' >"+
						"<img src='"+imgUrl+"'/>"+
					"</a>"+
				"</div>"+
			"</div>"+
			"<div class='message_content text'>"+
				"<div class='wxMsg'>"+item.introduction+"</div>"+
			"</div>"+
		"</li>"
	);
}

function commenfunc(){
	var repicurl = $("#repicurl").data("repicurl");//获得图片绝对路劲
	var serverimg =$("#serverimg").data("serverimg");//获得图片服务器路劲
	imageUrl = repicurl;
	$('#imgView').attr('src', repicurl);
   // realurl = serverimg;
}
</script>
</head>
<body>
	<div id="body" class="body page_message">
		<!-- <div id="js_container_box" class="container_box cell_layout side_l">
			<div class="col_main"> -->
				<div class="main_hd">
					<h2> 
						信息管理 
					</h2>
					<div class="title_tab" id="topTab" style="width: 1000px; height:130px; border-bottom-color: #eaeaea; border-bottom-width: 1px;border-bottom-style: solid;">
						<form id="form70001" >
							<div class="title_tab1" id="topTab1" style="width: 1000px; height:40px;">
								<p style="margin-left: 100px; display: inline;">
									栏目:
									<select id="classificationQry" name="classificationQry" class="easyui-combotree" style="width:200px;"></select>
									
									<select id="classificationHidden" name="classificationHidden" style="display:none">
										<option value="">请选择...</option>
										<%
											String options2 = "";
											List<Mi007> ary2=(List<Mi007>) request.getAttribute("classificationlist");
	      									for(int i=0;i<ary2.size();i++){
	         									Mi007 mi007=ary2.get(i);
	         									String itemid=mi007.getItemid();
	         									String itemval=mi007.getItemval();
	         									options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(options2);
	        							%>
									</select>
								</p>
								<p style="margin-left: 50px; display: inline;">
									开始日期:
									<input id="startdate" name="startdate" editable="false" class="easyui-datebox"  maxlength="10" validType="date&&maxdate['enddate','【结束日期】']" style="width:150px;"/>
								</p>
								<p style="margin-left: 50px; display: inline;">
									结束日期:
									<input id="enddate" name="enddate" editable="false" class="easyui-datebox"  maxlength="10" validType="date&&mindate['startdate','【开始日期】']" style="width:150px;"/>
								</p>
							</div>
							<%if("00041100".equals(user.getCenterid()) || "00085200".equals(user.getCenterid())){
							%>
							<div class="title_tab1" id="topTab3" style="width: 1000px; height:40px;">
								<p style="margin-left: 100px; display: inline;">
									状态:
									<select id="pubStatusQry" name="pubStatusQry" class="easyui-combobox" style="width:200px;" panelHeight="auto">
										<option value=""></option>
										<%
											String optionsAttr = "";
											List<Mi007> aryAttr=(List<Mi007>) request.getAttribute("attrlist");
	      									for(int i=0;i<aryAttr.size();i++){
	         									Mi007 mi007=aryAttr.get(i);
	         									String itemid=mi007.getItemid();
	         									String itemval=mi007.getItemval();
	         									optionsAttr = (new StringBuilder(String.valueOf(optionsAttr))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(optionsAttr);
	        							%>
									</select>
								</p>
								<p style="margin-left: 50px; display: inline;">
									数据来源:
									<select id="sourceQry" name="sourceQry"  class="easyui-combobox" style="width:150px;" panelHeight="auto">
										<option value=""></option>
										<%
											String optionsSourceQry = "";
											List<Mi007> arySourceQry=(List<Mi007>) request.getAttribute("infoSource");
	      									for(int i=0;i<arySourceQry.size();i++){
	         									Mi007 mi007=arySourceQry.get(i);
	         									String itemid=mi007.getItemid();
	         									String itemval=mi007.getItemval();
	         									optionsSourceQry = (new StringBuilder(String.valueOf(optionsSourceQry))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(optionsSourceQry);
	        							%>
									</select>
								</p>
								<p style="margin-left: 50px; display: inline;">
									关键字:
									<input type="text" id="keyword" name="keyword" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" class="easyui-validatebox" style="width:165px; height:20px"/>
								</p>
							</div>
							<%}else{%>
							<div class="title_tab1" id="topTab4" style="width: 1000px; height:40px;">
								<p style="margin-left: 100px; display: inline;">
									状态:
									<select id="pubStatusQry" name="pubStatusQry" class="easyui-combobox" style="width:200px;" panelHeight="auto">
										<option value=""></option>
										<%
											String optionsAttr1 = "";
											List<Mi007> aryAttr1=(List<Mi007>) request.getAttribute("attrlist");
	      									for(int i=0;i<aryAttr1.size();i++){
	         									Mi007 mi007=aryAttr1.get(i);
	         									String itemid=mi007.getItemid();
	         									String itemval=mi007.getItemval();
	         									optionsAttr1 = (new StringBuilder(String.valueOf(optionsAttr1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(optionsAttr1);
	        							%>
									</select>
									<select id="sourceQry" name="sourceQry" style="display:none">		
										<%
											String optionsSourceQry = "";
											List<Mi007> arySourceQry=(List<Mi007>) request.getAttribute("infoSource");
	      									for(int i=0;i<arySourceQry.size();i++){
	         									Mi007 mi007=arySourceQry.get(i);
	         									String itemid=mi007.getItemid();
	         									String itemval=mi007.getItemval();
	         									optionsSourceQry = (new StringBuilder(String.valueOf(optionsSourceQry))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(optionsSourceQry);
	        							%>
									</select>
								</p>
								<p style="margin-left: 50px; display: inline;">
									关键字:
									<input type="text" id="keyword" name="keyword" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" class="easyui-validatebox" style="width:435px; height:20px"/>
								</p> 
							</div>
							<%} %>
							<div class="title_tab2" id="topTab2" style="width: 1000px; text-align:center">
								<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
								<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
								<a id="b_add" class="easyui-linkbutton" iconCls="icon-add" href="javascript:void(0)" >添加信息</a>
							</div>
						</form>
					</div>
				</div>
				
				<div class="main_bd">
					<ul class="message_list" id="listContainer">	
					</ul>
					<div class="tool_area">
						<div class="pagination_wrp pageNavigator">
            					<div id="pagination" class="pagination">
    								<a href="#" class="first" data-action="first" style="width:25px;height:30px;">&laquo;</a>
    								<a href="#" class="previous" data-action="previous" style="width:25px;height:30px;">&lsaquo;</a>
    								<!-- <input type="text" readonly="readonly" data-max-page="40" /> -->
    								<input type="text" data-max-page="1" style="height:30px;"/>
    								<a href="#" class="next" data-action="next" style="width:25px;height:30px;">&rsaquo;</a>
    								<a href="#" class="last" data-action="last" style="width:25px;height:30px;">&raquo;</a>
								</div>
						</div>
					</div>
				</div>
			</div>
		<!-- </div>
	</div> -->
	
		
	<div id="dlg" class="easyui-dialog" closed="true" style="width:880px;height:500px;padding:10px  10px"  modal="true"  buttons="#dlg-buttons">  
		<div class="formtitle">信息</div>
        <form id="fm" method="post" class="dlg-form" novalidate="novalidate">
        	<table class="dlgcontainer">
				<col width="10%" /><col width="40%" /><col width="10%" /><col width="40%" />
				<!-- <tr>
					<th><label for="freeuse3" style="font-size: 14px;font-weight: normal;">信息属性：</label></th>
					<td>  	
						<ul class="multivalue horizontal">
							<li><input id="freeuse3-0" name="freeuse3" type="radio" value="0"/><label for="freeuse3-0" style="font-size: 12px;font-weight: normal;">草稿</label></li>
							<li style="margin-left:10px;"><input id="freeuse3-1" name="freeuse3" type="radio" value="1"/><label for="freeuse3-1" style="font-size: 12px;font-weight: normal;">发布</label></li>
						</ul>
					</td>          
				</tr> -->
				<tr>
					<th><label for="classification" style="font-size: 14px;font-weight: normal;">所属栏目：</label></th>
			        <td>
						<select id="classification" name="classification" editable="false"  required="true" class="easyui-combotree" style="width:155px;">
						</select>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr> 
		       <!-- <tr ><th><label for="img" style="font-size:14px;font-weight:normal;">信息图标：</label></th>
			        <td style="position: relative;">
			        	<input type="hidden" id="image" name="image" readonly="readonly" style="font-size:12px;font-weight:normal;width:400px;height:25px;"/> 
						<img id="imgView" name="imgView"  style="width:80px;height:80px;border:1px solid #95B8E7;"/>
						&nbsp&nbsp&nbsp<input id="imageBtn" type="button" value="选择图标" style="line-height:1.5em;font-family:Arial, Helvetica;font-size:0.8em;font-style:nomal;position: absolute; bottom: 6px;" >
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr> --> 
		        <tr ><th><label for="img" style="font-size:14px;font-weight:normal;">新闻图标：</label></th>
			        <td>
						<img id="imgView" name="imgView"  style="width:80px;height:80px;border:1px solid #95B8E7;"/>
						<%if("00053100".equals(user.getCenterid())){%>
							<input type="hidden" id="image" name="image"/>
							<a id="selectpic" href="javascript:void(0)" class="easyui-linkbutton" style="background:#eeeeee;" data-options="plain:true">图片库选择</a>
						<%}else{%>
							<input class="ke-input-text" type="hidden" id="image" name="image" readonly="readonly" />
							<input type="button" id="uploadButton" value="选择图标" />
						<%} %>
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="releasetime" style="font-size: 14px;font-weight: normal;">发布日期：</label></th>
			        <td>
			        	<input type="text" id="releasetime" name="releasetime" class="easyui-datebox"  required="true" validType="date"/>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="title" style="font-size: 14px;font-weight: normal;">信息标题：</label></th>
			        <td>
			        	<input type="text" id="title" name="title" class="easyui-validatebox" required="true" validType="titleLength[1,200]" style="font-size: 12px;font-weight: normal;width:650px;height:25px;" >
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>   
				<tr>
					<th><label for="introduction" style="font-size:14px;font-weight:normal;">信息摘要：</label></th>
			        <td colspan="3" ><textarea rows="3" id="introduction" name="introduction" class="easyui-validatebox" style="font-size:12px;font-weight:normal;width:650px;"></textarea>
			        </td>
		        </tr>
				<tr><th><label for="content" style="font-size: 14px;font-weight: normal;">详细内容：</label></th>
			        <td><textarea id="content" name="content" style="width:700px;height:180px;" ></textarea>
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr>
	        </table>
	        <input id="contentTmp" name="contentTmp" type="hidden" />
	        <input id="seqno" name="seqno" type="hidden" />
	        <input type="hidden" id="centerid" name="centerid" />
	        <input type="hidden" id="devid" name="devid" />
	        <input type="hidden" id="devtype" name="devtype" />
	        <input type="hidden" id="userid" name="userid" />
	        <input type="hidden" id="page" name="page" />
	        <input id="freeuse3" name="freeuse3" type="hidden" />
        </form>
    </div>
    <div id="dlg-buttons">
    	<a id="b_save_pub" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >保存并发布</a>
        <a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
        <a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
    </div>
    
<%--选择完成图片返回图片绝对路劲--%>
<input type="hidden" id="repicurl" />
<%--返回服务器上的真实路劲，供删除使用	--%>
<input type="hidden" id="serverimg" />
</body>
</html>