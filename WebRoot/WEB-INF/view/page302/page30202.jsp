<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>短信息报盘推送</title>
<%@ include file="/include/styles.jsp"%>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/message/comMessage.css" />
<%@ include file="/include/scripts.jsp"%>
<script type="text/javascript"src="<%=_contexPath%>/scripts/datagrid-detailview.js"></script>
<script type="text/javascript">
var pusMessageType= <%=request.getAttribute("pusMessageType")%>;
var themeType= <%=request.getAttribute("themeType")%>;
$(function() {
	$('#theme').combobox({
		mode : "local",
		data : themeType,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"//,
		//value : "00"
		//disabled : true
	});
	$('#theme_wx').combobox({
		mode : "local",
		data : themeType,
		valueField : "itemid",
		textField : "itemval",
		panelHeight : "auto"//,
		//value : "00"
		//disabled : true
	});
	ydl.displayRunning(false);
	$('#dg').datagrid({
		title : '短信息报盘推送管理',
		height : $(parent).height() - 85,
		url : 'webapi30202.json',
		method : 'post',
		queryParams : {
			'method' : 'query',
			'seqid' : '111'
		},
		singleSelect: true,
		toolbar : '#tb',
		columns : [[
			//{field : 'seqno',checkbox : true},
			{field:'seqno',field:'checkbox',title:'选择',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.seqno + '"/>';
			}},
			{title : '消息类型',field : 'transKeyword1',width : 150},
			{title : '推送条数',field : 'transCount',width : 180},
			{title : '拒绝条数',field : 'freeuse1',width : 180},
			{title : '业务日期',field : 'transdate',width : 180},
			//{title : '',field : 'nodate',width : 570}
			{field:'action',title:'操作',align:'center',width:300,formatter:function (value,row,index){

                var a = '<a href="#" onclick="authRow('+row.seqno+')">审批</a> ';
                var p = '<a href="#" onclick="pushRow('+row.transSeqid+')">推送</a>';
				return a+p;
			}}
		]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="margin:5px 0"></div>';
		},
		onExpandRow : function(index, row) {
			$('#ddv-' + index).panel({
				height : 350,
				width : 1050,
				border : false,
				cache : false,
				href : 'page3020201.html?seqid='+ row.transSeqid,
				onLoad : function() {
					$('#dg').datagrid('fixDetailRowHeight',index);
				}
			});
			$('#dg').datagrid('fixDetailRowHeight', index);
		}
	});
	
	
	
	
	//APP取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	//APP保存
	$('#b_save').click(function (){
			
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();
		if($('#importFile').val()==null||$('#importFile').val()==''){
			$.messager.alert('提示','请选择要上传文件!','info');
			return;
		}
		
		
		if($('#importFile').val()!=null && $('#importFile').val()!="") {
			$.messager.confirm('提示','是否要上传文件['+getPath(document.getElementById("importFile"))+']', function(isImport){
				if (isImport) {
					ydl.displayRunning(true);				
					$('#fm').submit();
				} 
				else {
					$('#fm').value='';
				}
			});
		}		
					
	});
	
	
	//微信取消
	$('#b_cancel_wx').click(function (){
		$('#dlg_wx').dialog('close');
	});
	//保存
	$('#b_save_wx').click(function (){
			
		if (!$("#fm_wx").form("validate")) return;
		var arr = $('#fm_wx').serializeArray();
		if($('#importFileWX').val()==null||$('#importFileWX').val()==''){
			$.messager.alert('提示','请选择要上传文件!','info');
			return;
		}
		
		
		if($('#importFileWX').val()!=null && $('#importFileWX').val()!="") {
			$.messager.confirm('提示','是否要上传文件['+getPath(document.getElementById("importFileWX"))+']', function(isImport){
				if (isImport) {
					ydl.displayRunning(true);				
					$('#fm_wx').submit();
				} 
				else {
					$('#fm_wx').value='';
				}
			});
		}		
					
	});
	
	
	
	
	
	//判断ie8设置透明
	if($.browser.msie && parseInt($.browser.version) < 9){
		$('#importFile').css('filter','alpha(opacity=0)');
		
		$('#importFileWX').css('filter','alpha(opacity=0)');
	}
});

function submitImport() {
	if($('#importFile').val()!=null && $('#importFile').val()!="") {
		$.messager.confirm('提示','是否要上传文件['+getPath(document.getElementById("importFile"))+']', function(isImport){
			if (isImport) {
				ydl.displayRunning(true);
				$('#formImport').submit();
			} 
			else {
				$('#importFile').value='';
			}
		});
	}
}

function submitImportwx() {
	if($('#importFileWX').val()!=null && $('#importFileWX').val()!="") {
		$.messager.confirm('提示','是否要上传文件['+getPath(document.getElementById("importFileWX"))+']', function(isImport){
			if (isImport) {
				ydl.displayRunning(true);				
				$('#formImportWX').submit();
			} 
			else {
				$('#importFileWX').value='';
			}
		});
	}
}

// 推送信息
function send() {
	var ids = [];
	var rows = $('#dg').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert('提示', "请选择要一条推送的报盘短信息流水！", 'info');
		return;
	}
	if (rows.length > 1) {
		$.messager.alert('提示', "请选择一条要推送的报盘短信息流水！当前选中" + rows.length + "条。",
				'info');
		return;
	}
	ids.push( {// MI402表中批次号
		"name" : "seqid",
		"value" : rows[0].transSeqid
	});
	ids.push( {
		name : "method",
		value : "send"
	});
	$.messager.confirm('提示','是否要推送选中的报盘短信息，可能需要较长时间来完成？', function(isSend){
		if (isSend) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30202.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
						$.messager.alert('提示', '推送成功完成，共推送['+data.count+']条信息。', 'info');
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

//删除推送信息
function remove() {
	var ids = [];
	//var rows = $('#dg').datagrid('getSelections');
	//if (rows.length == 0) {
	//	$.messager.alert('提示', "请选择要删除的公共短信息！", 'info');
	//	return;
	//}
	//for ( var i = 0; i < rows.length; i++) {
	//	ids.push({
	//		"name" : "listSeqno",
	//		"value" : rows[i].seqno
	//	});
	//}
	//if (ids.lenght == 0) {
	//	$.messager.alert('提示', "请选择要删除的公共短信息！", 'info');
	//	return;
	//}
	
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length == 0) {
		$.messager.alert('提示', "请选择要删除的推送消息！", 'info');
		return;
	}
	$checkboxs.each(function(index){
		ids.push({name : "listSeqno",value : this.value});
	});
	ids.push({name : "method",value : "delete"});
	
	$.messager.confirm('提示','是否要删除选中的推送消息？', function(isDel){
		if (isDel) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST", 
				url : 'webapi30202.json',
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
	
	$.messager.confirm('提示','是否要审批选中的报盘短信息？', function(isAuth){
		if (isAuth) {
			ydl.displayRunning(true);
			
			$.ajax({
				type : "POST",
				url : 'webapi30202.json',
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

function authRow(seqno) {
	var ids = [];
	ids.push({name : "listCommsgid",value : seqno});
	ids.push({name : "method",value : "auth"});
	$.messager.confirm('提示','是否要审批此条报盘短信息？', function(isAuth){
		if (isAuth) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30202.json',
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
function pushRow(transSeqid) {
	var ids = [];
	ids.push( {// MI402表中批次号
		"name" : "seqid",
		"value" : transSeqid
	});
	ids.push( {
		name : "method",
		value : "send"
	});
		$.messager.confirm('提示','是否要推送此条报盘短信息，可能需要较长时间来完成？', function(isSend){
		if (isSend) {
			ydl.displayRunning(true);
			$.ajax({
				type : "POST",
				url : 'webapi30202.json',
				dataType : "json",
				data : ids,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#dg').datagrid('reload');
						if (0 == data.count){
							$.messager.alert('提示', '推送的用户皆暂无有效推送设备，推送['+data.count+']个用户，请稍后重新导入报盘文件后重试！', 'info');
						} else {
							$.messager.alert('提示', '推送成功完成，共推送['+data.count+']个用户。', 'info');
						}
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

function selectTheme(type){
	if(type==1){
		$('#dlg').dialog('open').dialog('setTitle','导入APP短信息');		
		$('#formtitle').text('导入APP短信息');
	}else if(type==2){
		$('#dlg_wx').dialog('open').dialog('setTitle','导入微信短信息');		
		$('#formtitle_wx').text('导入微信短信息');
	}
	
	
}
</script>
</head>
<body>
	<table id="dg"></table>
	<div id="tb" style="height: auto">
		<div style="margin-bottom: 5px">
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="send()">推送短信息</a> -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-import-excel" style="position: relative;" onclick="selectTheme(1)" plain="true">导入APP短信息
				<!--  
				<form id="formImport" name="formImport" action="webapi30202.do"
					method="post" enctype="multipart/form-data" novalidate="novalidate">
					<input id="importFile" name="importFile" type="file" onkeydown="return false;" onclick=""
						style="position: absolute; left: 0; top: 0; width: 100%; height: 100%; z-index: 999; opacity: 0;"
						accept="application/vnd.ms-excel" onchange="submitImport();" />
					<input type="hidden" name="method" value="import" />
				</form>-->
			</a>
			<!-- 
			<a href="#" class="easyui-linkbutton" iconCls="icon-import-excel" style="position: relative;"  onclick="selectTheme(2)" plain="true">导入微信短信息 -->
				<!-- 
				<form id="formImportWX" name="formImportWX" action="webapi30202.do"
					method="post" enctype="multipart/form-data" novalidate="novalidate">
					<input id="importFileWX" name="importFileWX" type="file" onkeydown="return false;"
						style="position: absolute; left: 0; top: 0; width: 100%; height: 100%; z-index: 999; opacity: 0;"
						accept="application/vnd.ms-excel" onchange="submitImportwx();" />
					<input type="hidden" name="method" value="importWX" />
				</form> -->
			<!-- 
			</a>			
			 -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove()">删除短信息</a>
			<a href="downloadimg.file?filePathParam=import_file_path&fileName=mi30202.xls&isFullUrl=false" class="easyui-linkbutton" iconCls="icon-download-exceltemp" plain="true">模板下载</a>
			
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="auth()">审批报盘短信息</a> -->
			
		</div>
	</div>
	<input type="hidden" id='h_action' />
	<input type="hidden" id='h_formName' />
	
	
	
<div id="dlg" class="easyui-dialog" style="width:500px;height:320px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons">
<div class="formtitle" id="formtitle"></div>
	<form id="fm" class="dlg-form" method="post" name="formImport" action="webapi30202.do"
					method="post" enctype="multipart/form-data" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />			
			<tr>
				<th><label for="theme">主题类型：</label></th>
				<td><input id="theme" name="theme" class="easyui-combobox" required="required" editable="false" style="width:250px;" data-options="multiple:false"/></td>
			</tr>
			<tr>
				<th><label for="importFile">上传文件：</label></th>
				<td>
					<input id="importFile" name="importFile" type="file" onkeydown="return false;" style="width:87%" accept="application/vnd.ms-excel" />
					<input type="hidden" name="method" value="import" />
				</td>
			</tr> 
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
	
	

<div id="dlg_wx" class="easyui-dialog" style="width:500px;height:320px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons-wx">
<div class="formtitle" id="formtitle_wx"></div>
	<form id="fm_wx" class="dlg-form" method="post" name="formImport" action="webapi30202.do"
					method="post" enctype="multipart/form-data" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />			
			<tr>
				<th><label for="theme_wx">主题类型：</label></th>
				<td><input id="theme_wx" name="theme" class="easyui-combobox" required="required" editable="false" style="width:250px;" data-options="multiple:false"/></td>
			</tr>
			<tr>
				<th><label for="importFileWX">上传文件：</label></th>
				<td>
					<input id="importFileWX" name="importFileWX" type="file" onkeydown="return false;" style="width:87%" accept="application/vnd.ms-excel" />
					<input type="hidden" name="method" value="importWX" />
				</td>
			</tr> 
		  </table>
	</form>
</div>
<div id="dlg-buttons-wx">
	<a id="b_save_wx" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel_wx" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>



	
	
	
	
	
	
</body>
</html>