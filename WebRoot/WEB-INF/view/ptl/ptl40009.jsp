<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>错误代码管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body, html {width: 100%;height: 100%;overflow: hidden;margin:0;}
.datagrid {padding-top:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
$(function () {
	//datagrid列表
	$('#dg').datagrid({
		iconCls: 'icon-edit',
		height:369,
		url: '<%= _contexPath %>/ptl40009Qry.json',
		method:'post',
		queryParams:{errcode:$('#errcode').val()},
		title:'错误代码信息查询结果',
		toolbar:'#toolbar',
		pagination:true,
		pageSize:10,
        rownumbers:true,
        fitColumns:true,
        singleSelect:true,
		columns:[[
			{field:'id',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.errcode + '"/>';
			}},
			{title:'错误代码',field:'errcode',align:'center',width:30},
			{title:'错误内容',field:'errtext',align:'center',width:100}
		]],
        onLoadSuccess:function(data){
           if(data.recode!=SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }
	});	
	
	//批量导入提交
	$('#submit_form').click(function () {
		$('#uploadform').form('submit',{  
			url: 'excelToDB.do',
			onSubmit: function(){   
				var excelFile=$('#excelfile').val();
				if(null == excelFile || "" == excelFile){
					$.messager.alert('提示', "请填写上传路径后进行提交", 'error');
					return false;
				}
			},
			success: function(data){  
					$('#dg').datagrid('reload'); 
		    		$('#dlgBatchUpload').dialog('close');
		    },
			error :function(){
				$.messager.alert('错误','系统异常','error');
			}
		});
	});
	
	//批量导出提交
	$('#download_submit_form').click(function () {
		$('#downloadform').form('submit',{  
			url: 'mi010ToExcel.do',
			onSubmit: function(){
				var fileName=$('#fileName').val();
				if(null == fileName || "" == fileName){
					$.messager.alert('提示', "请填写生成文件名称后进行提交", 'error');
					return false;
				}
			},
			success: function(data){
		    		$('#dlgBatchDownload').dialog('close');
		    },
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
		});
	});
});
var url;
function addInfo(){
    $('#dlg').dialog('open').dialog('setTitle','错误代码-增加');
    $("[name='errcode']").attr("readonly",false);
    $('#fm').form('clear');
    
    url = '<%= _contexPath %>/ptl40009Add.json';
}
function editInfo(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','错误代码-修改');
        $('#fm').form('load',row);
        $('input[name=errcode]').eq(1).attr("readonly",true);
        url = '<%= _contexPath %>/ptl40009Mod.json?id='+row.id;
    }else{
    	$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
    }
}
function saveInfo(){
    var arr = $('#fm').serializeArray();
	if (!$("#fm").form("validate")) return;
	$.ajax( {
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			if(SUCCESS_CODE == data.recode) {
				$.messager.alert('提示', data.msg, 'info');
				$('#dlg').dialog('close');        // close the dialog
            	$('#dg').datagrid('reload');    // reload the user data
			}else{
				$.messager.alert('错误',data.msg,'error');
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function delInfo(){
    //var rows = $('#dg').datagrid('getSelections');
    //if (rows.length > 0){
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if (r){
                //var para = rows[0].errcode;
             	//for(var i=1;i<rows.length;i++){
             	//	para = para + "," + rows[i].errcode;
             	//}
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={errcode:para}
            	
                $.ajax( {
				type : 'POST',
				url : '<%= _contexPath %>/ptl40009Del.json',
				dataType: 'json',
				data:map,
				success : function(data) {
					if(SUCCESS_CODE == data.recode) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#dg').datagrid('reload');    // reload the user data
					}else{
						$.messager.alert('错误',data.msg,'error');	
					}
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');							
				}
			});
         }
    });
    }else{
    	$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
    }
}
function queryInfo() {
	//校验
	//if (!ydl.formValidate('form40009')) return;
	if (!$("#form40009").form("validate")) return;
	
	var $grid=$('#dg');
    $grid.datagrid('options').queryParams={}; 
    var queryParams = $grid.datagrid('options').queryParams; 
	$("#form40009 input").each(function(index){
		 if($(this).val()!="")
			queryParams[$(this).attr("name")]=$(this).val();
    }); 
	$grid.datagrid("reload");	
}
function batchUpload(){
    $('#dlgBatchUpload').dialog('open').dialog('setTitle','错误代码-批量导入');
}
function batchDownload(){
    $('#dlgBatchDownload').dialog('open').dialog('setTitle','错误代码-批量导出');
}
//]]></script>
</head>
<body>
	<div class="easyui-panel" title="错误代码信息">
		<form id="form40009" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<th><label for="errcode">错误代码：</label></th>
					<td colspan="3"><input type="text" id = "errcode" name="errcode"/></td>
				</tr>
			</table>
			<div class="container">
				<a href="javascript:void(0)" iconCls="icon-search" class="easyui-linkbutton" onclick="queryInfo()">查询</a>
			</div>
		</form>
	</div>
	<!-- 错误代码信息查询结果 -->
	<table id="dg" ></table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addInfo()">增加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editInfo()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="batchUpload()">批量导入</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="batchDownload()">批量导出</a>
    </div>
    
   <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="formtitle">错误代码信息</div>
        <form id="fm" method="post" class="dlg-form" novalidate="novalidate">
			<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="errcode">错误代码：</label></td>
					<td><input type="text" id="errcode" name="errcode" class="easyui-validatebox" required="true" length="6"/></td>	        	
				</tr>
				<tr>
					<td><label for="errtext">错误内容：</label></td>
					<td><input type="text" id="errtext" name="errtext" class="easyui-validatebox" required="true"/></td>	        	
				</tr>
			</table>
        </form>
    	<div id="dlg-buttons">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInfo()">保存</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    	</div>
    </div>

    
    <div id="dlgBatchUpload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="formtitle">错误代码信息批量导入</div>
        <form id = "uploadform" class="dlg-form" method="post"  ENCTYPE="multipart/form-data" novalidate="novalidate">
			<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="excelfile">上传路径：</label></td>
					<td><input type="file" id="excelfile" name="excelfile" required="true"/></td>	        	
				</tr>
			</table>
			<input type="hidden" name="path" value="ptl/ptl40009"/>
			<input type="hidden" name="ruleMapStr" value="{errcode:'#1',errtext:'#2'}"/>
			<input type="hidden" name="tableName" value="mi010"/>
        </form>
        <div id="dlg-buttons">
        	<a id="submit_form" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">导入</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchUpload').dialog('close')">取消</a>
    	</div>
    </div>
    
    <div id="dlgBatchDownload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="formtitle">错误代码信息批量导出</div>
        <form id = "downloadform" class="dlg-form" method="post" novalidate="novalidate">
			<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="fileName">生成文件名称：</label></td>
					<td><input type="text" id = "fileName" name="fileName" class="easyui-validatebox" required="true"/><span> (.xls结尾)</span></td>	        	
				</tr>
			</table>
			<!-- <input type="hidden" name="forwardpath" value="ptl/ptl40009"/> -->
			<input type="hidden" name="titles" value="errcode,errtext"/>
			<input type="hidden" name="expotrTableName" value="mi010"/>
        </form>
        <div id="dlg-buttons">
        	<a id="download_submit_form" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >导出</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchDownload').dialog('close')">取消</a>
    	</div>
    </div>

</body>
</html>
