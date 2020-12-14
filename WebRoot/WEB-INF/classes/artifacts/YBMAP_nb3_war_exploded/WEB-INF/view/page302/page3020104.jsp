<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<title></title>
</head>
<body>
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/kindeditor/themes/default/default.css" />
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">

$(function() {

	var theme123 = themeType;
	$('#theme').combobox({
		mode : "local",
		data : theme123,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"//,
		//value : "00"
		//disabled : true
	});
	
	//var pusMessage123 = pusMessageType;	
	var pusMessage123 = new Array();
	pusMessage123[0]=pusMessageType[1];
	pusMessage123[1]=pusMessageType[2];
	//alert(pusMessage123.length);
	//for(var i=0;i<pusMessage123.length;i++){
	//	if(pusMessage123[i].itemid=='01'){			
	//		delete pusMessage123[i].itemid;//删掉个人短信息
	//		delete pusMessage123[i].itemval;//删掉个人短信息
	//	}
	//}
	$('#pusMessageType').combobox({
		mode : "local",
		data : pusMessage123,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"//,
		//value : "00"
		//disabled : true
	});

	//判断ie8设置透明
	if ($.browser.msie && parseInt($.browser.version) < 9) {
		$('#file').css('filter', 'alpha(opacity=0)');
	}
	
	//浮动到公共条件分组和内容
	$('#showImg1').on('mouseover',' div',function (){
		$(this).find('span').css('visibility', 'visible');
	}).on('mouseout',' div',function (){
		$(this).find('span').css('visibility', 'hidden');
	}); 
	
	//删除图片
	$('#showImg1').on('click','div span',function (){
		var $img = $(this).prev();
		var fileName = $img[0].id.replace('-', '.');
		$.messager.confirm('提示', '是否要删除图片？', function(isDel) {
			if (isDel) {
				$img.closest('div').remove();
				var param2 = $('#param2').val();
				var pParam2 = param2.split(',');
				for ( var i = 0; i < pParam2.length; i++) {
					if (pParam2[i] == fileName) {
						pParam2.splice(i, 1);
					}
				}
				param2 = pParam2.join(',');
				$('#param2').attr('value', param2);
			}
		});
	});
	
	//信息预览对话框
	$('#dlg-phoneShow').dialog({
		title : '信息预览',
		width : 400,
		height : 526,
		closed : true,
		cache : false,
		modal : true
	});
	
	//点击信息预览按钮
	$('#infoShow').click(function (){ 
		$('#dlg-phoneShow').html('<div id="pinfoShow" ><div class="phonebg"><div class="screenbg"><h1 class="item-title">短消息</h1><div id="content" class="item-content"><div id="phoneNo">客服电话：<%=request.getAttribute("custsvctel") %></div><div id="phoneNo">消息标题：<label id="infoTitle"></label></div><div id="phoneNo">图片：<ul id="phoneImg"></ul></div></div></div></div></div>');
		var title = $('#title').val(); 
		$('#phoneImg').children().remove();		
		$.each($('#showImg1').find('img'),function (){
			var $img = $(this).clone();			
			$img.removeAttr('id');			
			
			var $li = $('<li></li>').append($img);
			$('#phoneImg').append($li);
		
		});
		
		if($('#showImg1').find('img').length==0){
			var $li = $('<li>< 预览图片处 ></li>');
			$('#phoneImg').append($li);
		}
		
		$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : title);
		
		/*var detail = editor.html();//.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
		
		var infoDetailms = $('#detail').val().replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		$('#infoDetailms').html(infoDetailms == '' ? '< 预览消息描述处 >': infoDetailms);
		*/
		$('#dlg-phoneShow').show().dialog('open');
		ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
	});
});

function loadImg(param2, downloadPath) {
	//如果有图片
	
	if (param2 != '') {	
		var pParam2 = param2.split(',');
		for ( var i = 0; i < pParam2.length; i++) {
		
			var fileName = pParam2[i];
			$('#showImg1').append('<div><img id="'+ fileName.replace('\.', '-') + '" src="'
				+ downloadPath.replace("mi401Img", fileName)
				+ '" /><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');			
		}
		$('#showImg1').closest('.panel').show();
	}
	else $('#showImg1').closest('.panel').hide();
}

function upImg1() {
	if (!$('#formUpImg1').form("validate", function(v) {})) {
		$.messager.alert('提示', "请选择要上传的图片!", 'info');
		return;
	}
	
	
	
	/*
	var type=document.formUpImg.file.value.match(/^(.*)(\.)(.{1,8})$/)[3];
	
	type=type.toUpperCase();
	if(type=="JPG"){
		//alert("上传正常");
	   //return true;
	}
	else{
	   alert("上传类型有误,非JPG图片格式！");
	   return false;
	}	
	*/
	$('#formUpImg1').ajaxSubmit({
		dataType : "json",
		success : function(data) {			
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode == SUCCESS_CODE) {
				$('#showImg1').closest('.panel').show();
				var fileName = data.fielName;
				var param2 = $('#param2').val();
				if (param2.length > 0) {
					param2 += ",";
				}
				param2 += fileName;
				$('#param2').attr('value', param2);
				$('#showImg1').append('<div><img id="' + fileName.replace('\.', '-')+ '" src="'
					+ data.downloadPath + '"/><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');
			}
		},
		error:function() {
			$.messager.alert('错误', '网络连接出错！', 'error');
		}
	});	
}
</script>
<div id="dlg-panel" class="easyui-panel" title="信息文字内容">
	<form id="form30201" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
		<table class="container">
			<col width="15%" /><col width="85%" />
			<tr>
				<th><label for="title">消息标题：</label></th>
				<td><input id="title" name="title" class="easyui-validatebox" validType="length[1,50]" type="text" required="required" /></td>
			</tr>
			<tr>
				<th><label for="theme">主题类型：</label></th>
				<td><input id="theme" name="theme" class="easyui-combobox" required="required" editable="false" style="width:250px;" data-options="multiple:false"/></td>
			</tr>
			<!-- 
			<tr>
				<th><label for="detail">消息描述：</label></th>
				<td><textarea id="detail" name="detail" style="width:100%;height:50px;"></textarea></td>
			</tr>
			 <tr>
				<th><label for="tsmsg">消息内容：</label></th>
				<td><textarea id="tsmsg" name="tsmsg" style="width:300px;height:280px;visibility:hidden;"></textarea></td>
			</tr>
			 -->
		</table>		
		<input type="hidden" id="commsgid" name="commsgid" />
		<input type="hidden" id="param2" name="param2" />
		<input type="hidden" id="method" name="method" />
	</form>
</div>
<div id="info-buttons">
	<a href="#" id="infoShow" class="easyui-linkbutton" iconCls="icon-search" plain="true">信息预览</a>
	<a id="addImg" href="#" class="easyui-linkbutton" iconCls="icon-import-excel" plain="true">添加图片
		<form id="formUpImg1" name="formImport1" action="webapi30201_uploadimg.do" method="post" enctype="multipart/form-data" novalidate="novalidate">
			<input id="file" name="file" type="file" onkeydown="return false;" accept="image" onchange="upImg1()" />
		</form> 
	</a>
</div>
<div class="easyui-panel" title="标题图片">
	<div id="showImg1"></div>
</div >

<div id="dlg-phoneShow">
	<div id="pinfoShow" >
		<div class="phonebg">
			<div class="screenbg">
				<h1 class="item-title">短消息</h1>
				<div id="content" class="item-content">
					<div id="phoneNo">客服电话：<%=request.getAttribute("custsvctel") %></div>
					<div id="phoneNo">消息标题：<label id="infoTitle"></label></div>
					<div id="phoneNo">图片：<ul id="phoneImg"></ul></div>
					<!-- <div id="phoneNo">消息描述：<h3 id="infoDetailms"></h3></div>
					<div id="phoneNo">以下为消息正文：</div>	
					<div><h3 id="infoDetail"></h3></div> -->				
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>