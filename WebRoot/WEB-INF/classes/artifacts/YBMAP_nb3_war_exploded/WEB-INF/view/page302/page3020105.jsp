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
<!--  
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/kindeditor/themes/default/default.css" />
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/lang/zh_CN.js"></script>
-->

<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/themes/default/css/ueditor.css" />
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/ueditor.all.js"> </script>
<script type="text/javascript" src="<%=_contexPath%>/ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
var url;
var number="";
var value={};
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
	
	if(!(editid=="null")){
		value.commsgid=editid;
		$('#number').val(editid);
	}else{
		value.commsgid=number;
	}
	
	$('#tsmx').datagrid({   
		iconCls: 'icon-edit',
		title:'图文信息列表',
		singleSelect: true,
		//height:469,
		//pagination:true,
		//rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi30201.json?method=queryTextImage",
		
		onClickRow:function(rowIndex, rowData){
			//$('#roleid').prop('readOnly',true);
			$('#form30201_tsmx').form('load',rowData);
			//$('#theme').combobox().select(rowData.theme);
			editor.setContent(rowData.mstsmess);
			//editor.getContent();			
			$('#msmscommsgid').val(rowData.msmscommsgid);
			$('#showImg div').remove();			
			// 加载信息中的图片
			loadImg(rowData.msparam2, '<%=request.getAttribute("downloadPath") == null ? ""
					: request.getAttribute("downloadPath").toString().replaceAll("\\\\", "\\\\\\\\")%>');
			
			
		},
		
		queryParams:value,
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.msmscommsgid + '"/>';
			}},
			{title:'主ID',field:'msmscommsgid',hidden:true,align:'center',width:1,editor:'text'},
			{title:'批次ID',field:'commsgid',hidden:true,align:'center',width:1,editor:'text'},							
			{title:'序号',field:'freeuse4',hidden:true,align:'center',width:1,editor:'text'},
			{title:'主题',field:'theme',hidden:true,align:'center',width:50,editor:'text'},
			{title:'标题',field:'mstitle',align:'center',width:130,editor:'text'},	
			{title:'摘要',field:'msdetail',hidden:true,align:'center',width:1,editor:'text'},			
			{title:'图片',field:'msparam2',hidden:true,align:'center',width:1,editor:'text'},
			{title:'详细内容',field:'mstsmess',hidden:true,align:'center',width:1,editor:'text'},
			{title:'类型',field:'mstsmesstype',hidden:true,align:'center',width:1,editor:'text'}
									
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【查询出错】：'+data.msg, 'info' );
        }		
	});
	
	
	//浮动到公共条件分组和内容
	$('#showImg').on('mouseover',' div',function (){
		$(this).find('span').css('visibility', 'visible');
	}).on('mouseout',' div',function (){
		$(this).find('span').css('visibility', 'hidden');
	}); 
	
	//删除图片
	$('#showImg').on('click','div span',function (){
		var $img = $(this).prev();
		var fileName = $img[0].id.replace('-', '.');
		$.messager.confirm('提示', '是否要删除图片？', function(isDel) {
			if (isDel) {
				$img.closest('div').remove();
				var param2 = $('#msparam2').val();
				var pParam2 = param2.split(',');
				for ( var i = 0; i < pParam2.length; i++) {
					if (pParam2[i] == fileName) {
						pParam2.splice(i, 1);
					}
				}
				param2 = pParam2.join(',');
				$('#msparam2').attr('value', param2);
			}
		});
	});
	
	
	$('#tsmxadd').click(function (){
		var paras = {};
		paras.mstitle = $('#mstitle').val();
		paras.mstsmess = editor.getContent();
		//paras.msnum = $('#msnum').val();
		paras.theme = $('#theme').combobox('getValue');		
		paras.msparam2 = $('#msparam2').val();
		paras.msmscommsgid=$('#msmscommsgid').val();
		paras.msdetail=$('#msdetail').val();
		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi30201.json?method=addTextImage&commsgid="+$('#number').val(),
			dataType: "json",
			data:paras,
			success : function(data) {					
				if (data.recode != SUCCESS_CODE) {
					$.messager.alert('提示', data.msg, 'info' );					
				} else {
					$('#number').val(data.commsgid);	
																
					$.messager.alert('提示', data.msg, 'info' );
					//$('#dlg').dialog('close');        // close the dialog
					value.commsgid=data.commsgid;
					$('#mstitle').val("");	
					editor.setContent("");
					$('#msparam2').val("");
					//$('#msnum').val("");
					//$('#msnum').numberbox('setValue',"");					
					$('#msdetail').val("");
					$('#showImg div').remove();
					$('#tsmx').datagrid('reload',value);    // reload the user data	
					$('#dg').datagrid('reload');
					$('#theme').combobox().clear();
				}
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
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
		
	$('#infoShow').click(function (){ 
		$('#dlg-phoneShow').html('<div id="pinfoShow" ><div class="phonebg"><div class="screenbg"><h1 class="item-title">短消息</h1><div id="content" class="item-content"><div id="phoneNo">客服电话：<%=request.getAttribute("custsvctel") %></div><div id="phoneNo">消息标题：<label id="infoTitle"></label></div><div id="phoneNo">标题图片：<ul id="phoneImg"></ul></div><div id="phoneNo">以下为消息正文：</div><div><h3 id="infoDetail"></h3></div></div></div></div></div></div>');
		var title = $('#mstitle').val(); 
		$('#phoneImg').children().remove();		
		$.each($('#showImg').find('img'),function (){
			var $img = $(this).clone();			
			$img.removeAttr('id');			
			
			var $li = $('<li></li>').append($img);
			$('#phoneImg').append($li);
		
		});
		
		if($('#showImg').find('img').length==0){
			var $li = $('<li>< 预览标题图片处 ></li>');
			$('#phoneImg').append($li);
		}
		
		$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : title);
		var detail = editor.getContent();//.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
		
		//var infoDetailms = $('#detail').val().replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		//$('#infoDetailms').html(infoDetailms == '' ? '< 预览消息描述处 >': infoDetailms);
		
		$('#dlg-phoneShow').show().dialog('open');
		ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
	});
	
	
	$('#b_moveup').click(function (){
		var row = $("#tsmx").datagrid('getSelected'); 
		if(row==null){
			return;
		}
	    var index = $("#tsmx").datagrid('getRowIndex', row);
	    mysort(index, 'up', 'tsmx');
	});
	$('#b_movedown').click(function (){
		var row = $("#tsmx").datagrid('getSelected');
		if(row==null){
			return;
		}
	    var index = $("#tsmx").datagrid('getRowIndex', row);
	    mysort(index, 'down', 'tsmx');
	});
	
	//点击保存
	$("#b_savesort").click(function(){
		
		var param="[";
		 var  data = $("#tsmx").datagrid('getRows');
		 if(data==null||data.length==0){
			 alert("表格中无数据");
			 return;
		 }
		 
		//var grid = $('#tsmx');
		//var options = grid.datagrid('getPager').data("pagination").options;
		//var curr = options.pageNumber;
		//var total = options.total;
		 
		 for(var i=0;i<data.length;i++){
			var row = $('#tsmx').datagrid('getData').rows[i];;
			if(i!=0)param+=",";			
			param+="{'seqid':"+row['msmscommsgid']+",'num':"+(i+1)+"}";
			
		 }
		 param+="]"; 
		  $.ajax( {
				type : 'POST',
				url : '<%=request.getContextPath()%>/webapi30201.json?method=orderbynum&datalist='+param,
				dataType: 'json',
				success : function(data) {
					$.messager.alert('提示', data.msg, 'info');
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');							
				}
			}); 
			
	});
	
	
	
	
});


function loadImg(param2, downloadPath) {
	//如果有图片
	
	if (param2 != '') {
		var pParam2 = param2.split(',');
		for ( var i = 0; i < pParam2.length; i++) {
			var fileName = pParam2[i];
			$('#showImg').append('<div><img id="'+ fileName.replace('\.', '-') + '" src="'
				+ downloadPath.replace("mi401Img", fileName)
				+ '" /><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');
		}
		$('#showImg').closest('.panel').show();
	}
	else $('#showImg').closest('.panel').hide();
}


function upImg() {
	if (!$('#formUpImg').form("validate", function(v) {})) {
		$.messager.alert('提示', "请选择要上传的图片!", 'info');
		return;
	}
	$('#showImg').closest('.panel').show();
	$('#formUpImg').ajaxSubmit({
		dataType : "json",
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode == SUCCESS_CODE) {
				$('#showImg').closest('.panel').show();				
				var fileName = data.fielName;
				var param2 = $('#msparam2').val();
				if (param2.length > 0) {
					param2 += ",";
				}
				param2 += fileName;
				$('#msparam2').val(param2);
				$('#showImg').append('<div><img id="' + fileName.replace('\.', '-')+ '" src="'
					+ data.downloadPath + '"/><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');				
			}
		},
		error:function() {
			$.messager.alert('错误', '网络连接出错！', 'error');
		}
	});
}

function newUserMX(){	
	$('#mstitle').val("");	
	editor.setContent("");
	$('#msparam2').val("");
	$('#msdetail').val("");
	$('#msmscommsgid').val("");
	$('#showImg div').remove();
	//$('#msnum').val("");
	//$('#msnum').numberbox('setValue',"");
	$('#theme').combobox().clear();	
}

function destroyUserMX(){
	var $checkboxs = $('input:checkbox:checked');	
	if ($checkboxs.length > 0) {
		var code = $checkboxs.map(function(index){
			return this.value;
		}).get().join(',');	
		
		var arr = [{name : "msmscommsgid",value : code}];
		$.messager.confirm('提示','确认是否删除?',function(r){
			if(r){				
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi30202.json?method=deletems",
					dataType: "json",
					data:arr,
					success : function(data) {
						if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【删除出错】：'+data.msg, 'info' );
						else {
							$.messager.alert('提示', data.msg, 'info' );						
							//$('#dg').datagrid('reload');    // reload the user data
							value.commsgid=$('#number').val();
							
							$('#tsmx').datagrid('reload',value); 
							
							$('#mstitle').val("");	
							editor.setContent("");
							$('#msparam2').val("");
							$('#msdetail').val("");
							$('#msmscommsgid').val("");
							$('#showImg div').remove();
							//$('#msnum').val("");
							//$('#msnum').numberbox('setValue',"");
							$('#theme').combobox().clear();	
							
							
						}
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');							
					}
				});
			}					
		});
	}
	else $.messager.alert('提示', "请选中待删除信息！", 'info' );
}

function mysort(index, type, gridname) {
    if ("up" == type) {
        if (index != 0) {
            var toup = $('#' + gridname).datagrid('getData').rows[index];
            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
            $('#' + gridname).datagrid('getData').rows[index] = todown;
            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index - 1);
            $('#' + gridname).datagrid('selectRow', index - 1);
        }
    } else if ("down" == type) {
        var rows = $('#' + gridname).datagrid('getRows').length;
        if (index != rows - 1) {
            var todown = $('#' + gridname).datagrid('getData').rows[index];
            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridname).datagrid('getData').rows[index] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index + 1);
            $('#' + gridname).datagrid('selectRow', index + 1);
        }
    }
 
} 


</script>

<div class="easyui-layout" data-options="fit:true">		
	<div data-options="region:'west',split:true,border:false" style="width:250px">
		<div id="tsmxdiv">	
			<div id="toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUserMX()">增加</a>
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUserMX()">修改</a> -->
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUserMX()">删除</a>	
				<a id="b_savesort" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" >保存顺序</a>	
				<a id="b_moveup" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-up" plain="true" >上移</a>
				<a id="b_movedown" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-down" plain="true" >下移</a>
							
			</div>					
			<table id="tsmx"></table>
		</div>
	</div>
	<div data-options="region:'center',border:false">						
		<div id="dlg-panel" class="easyui-panel" title="消息内容">
			<form id="form30201_tsmx" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
				<table class="container">
					<col width="15%" /><col width="85%" />
					<tr>
						<th><label for="mstitle">消息标题：</label></th>
						<td><input id="mstitle" name="mstitle" class="easyui-validatebox" style="width:85%" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[1,50]" type="text" required="required" /></td>
					</tr>
					<!-- 
					<tr>
						<th><label for="msnum">序号：</label></th>
						<td><input id="msnum" name="msnum" class="easyui-numberbox" precision="0" style="width:85%" validType="length[1,4]" type="text" required="required"/></td>
					</tr>
					 -->
					<tr>
						<th><label for="theme">主题类型：</label></th>
						<td><input id="theme" name="theme" class="easyui-combobox" required="required" editable="false" style="width:250px;" data-options="multiple:false"/></td>
					</tr>
					
					<tr>
						<th><label for="msdetail">消息描述：</label></th>
						<td><input id="msdetail" name="msdetail" class="easyui-validatebox" style="width:85%" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[1,500]" type="text" required="required" /></td>
					</tr>					
					<tr>
						<th><label for="mstsmess">详细内容：</label></th>
						<td><textarea id="mstsmess" name="mstsmess" style="width:700px;height:280px;"></textarea></td>
					</tr>			
				</table>
				
				<input type="hidden" id="msparam2" name="msparam2" />
				<input type="hidden" id="msmscommsgid" name="msmscommsgid" />					
			</form>				
			<div id="info-buttons">
				<a href="#" id="infoShow" class="easyui-linkbutton" iconCls="icon-search" plain="true">信息预览</a>
				<a id="addImg" href="#" class="easyui-linkbutton" iconCls="icon-import-excel" plain="true">添加标题图片
					<form id="formUpImg" name="formImport" action="webapi30201_uploadimg.do" method="post" enctype="multipart/form-data" novalidate="novalidate">
						<input id="file" name="file" type="file" onkeydown="return false;" accept="image" onchange="upImg()" />
					</form>
				</a>		
				<a href="#" id="tsmxadd" class="easyui-linkbutton" iconCls="icon-ok" plain="true">确定</a>
			</div>
			<div class="easyui-panel" title="标题图片">
				<div id="showImg"></div>
				<div id="dlg-phoneShow">
					<div id="pinfoShow" >
						<div class="phonebg">
							<div class="screenbg">
								<h1 class="item-title">短消息</h1>
								<div id="content" class="item-content">
									<div id="phoneNo">客服电话：<%=request.getAttribute("custsvctel") %></div>
									<div id="phoneNo">消息标题：<label id="infoTitle"></label></div>
									<div id="phoneNo">标题图片：<ul id="phoneImg"></ul></div>									
										<div id="phoneNo">以下为消息正文：</div>	
										<div><h3 id="infoDetail"></h3></div>				
									</div>
								</div>
							</div>
						</div>
					</div>
		    	</div>
			</div>
	</div>
	
</div>
<input type="hidden" id="number" name="number" />
</body>
</html>