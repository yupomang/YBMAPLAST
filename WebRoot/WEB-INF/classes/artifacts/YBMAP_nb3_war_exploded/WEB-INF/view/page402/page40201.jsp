<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>中心管理已作不用</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<style type="text/css">
body, html,#allmap {width: 100%;height: 90%;overflow: hidden;margin:0;}</style>
<base target="main" />

<script type="text/javascript">
		 
		var url;
        function newUser(){
            $('#dlg').dialog('open').dialog('setTitle','中心客服通讯信息增加');
            $('#fm').form('clear');
            url = "<%=request.getContextPath()%>/webapi40201.json";
        }
        function editUser(){        
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','中心客服通讯信息修改');
                $('#fm').form('load',row);
                url = "<%=request.getContextPath()%>/webapi40203.json";
            }
        }
       
        function saveUser(){            
			//校验
			//if (!ydl.formValidate('fm')) return;
			if (!$("#fm").form("validate")) return;
			
            var arr = $('#fm').serializeArray();
			$.ajax( {
				type : "POST",
				url : url,
				dataType: "json",
				data:arr,
				success : function(data) {
					$.messager.alert('提示', data.msg, 'info');
					$('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
				},
				error :function(){
					$.messager.alert('错误',data.msg,'error');
				}
			});
        }
        function destroyUser(){
            var row = $('#dg').datagrid('getSelected');           
            if (row){
                $.messager.confirm('提示','确认是否删除?',function(r){
                    $.ajax( {
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi40202.json",
						dataType: "json",
						data:row,
						success : function(data) {
							$.messager.alert('提示', data.msg, 'info');							
		                    $('#dg').datagrid('reload');    // reload the user data
						},
						error :function(){
							$.messager.alert('错误',data.msg,'error');							
						}
					});
                });
            }
        }


$(function() {
	$('#page40201').window('close');
	
	$('#consultType').combobox({
	    data:jsonConsultType,
	    valueField:'id',
	    textField:'text',
	    panelHeight:"auto"
	});
	
	$('#orderNo').numberbox({
		min:0
	});

})

function convertToJson(formValues) {
    var result = {};
    for(var formValue,j=0;j<formValues.length;j++) {
    formValue = formValues[j];
    var name = formValue.name;
    var value = formValue.value;
    if (name.indexOf('.') < 0) {
    result[name] = value;
    continue;
    } else {
    var simpleNames = name.split('.');
    // 构建命名空间
    var obj = result;
    for ( var i = 0; i < simpleNames.length - 1; i++) {
    var simpleName = simpleNames[i];
    if (simpleName.indexOf('[') < 0) {
    if (obj[simpleName] == null) {
    obj[simpleName] = {};
    }
    obj = obj[simpleName];
    } else { // 数组
    // 分隔
    var arrNames = simpleName.split('[');
    var arrName = arrNames[0];
    var arrIndex = parseInt(arrNames[1]);
    if (obj[arrName] == null) {
    obj[arrName] = []; // new Array();
    }
    obj = obj[arrName];
    multiChooseArray = result[arrName];
    if (obj[arrIndex] == null) {
    obj[arrIndex] = {}; // new Object();
    }
    obj = obj[arrIndex];
    }
    }
 
    if(obj[simpleNames[simpleNames.length - 1]] ) {
    var temp = obj[simpleNames[simpleNames.length - 1]];
    obj[simpleNames[simpleNames.length - 1]] = temp;
    }else {
    obj[simpleNames[simpleNames.length - 1]] = value;
    }
 
    }
    }
    return result;
}

function select40204() {
	var arr = $('#form40204').serializeArray();
	var dataJson = convertToJson(arr);
	if (!$('#form40204').form( "validate" ,function(v){})) {
		//alert($('#form40204').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入正确数据!", 'info' );
		return;
	}	
	//datagrid列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi40204.json",
		method:'post',
		queryParams:dataJson		
	});	
}

function cancel40201() {
	$('#page40201').window('close');
}

function loadform40204( row ) {
	$('#form40204').form('load',{
		consultType:row.id
	});
}
</script>
</head>

<body>
	<div class="easyui-panel" title="中心客服通讯信息查询">
		<form id="form40204" novalidate="novalidate">
			<table class="container">				
		        <tr>	        
			        <th><label for="centerid">中心名称:</label></th>
			        <td><input id="centerid" name="centerid" class="easyui-validatebox"/></td>
		            <td>
			            <a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select40204()">查询</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel40201()">取消</a>
					</td>
		        </tr>
	        </table> 
		</form>
	</div>
	
	<table id="dg" class="easyui-datagrid" title="版本详细信息" toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">		
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'devtype',align:'center',width:80">设备区分</th>
				<th data-options="field:'versionno',align:'center',width:130,editor:'text'">版本编号</th>
				<th data-options="field:'releasedate',align:'center',width:180,editor:'text'">发布日期</th>
				<th data-options="field:'downloadurl',width:120,align:'center',editor:'text'">下载地址</th>
				<th data-options="field:'releasecontent',align:'center',width:250,editor:'text'">更新内容</th>
				<th data-options="field:'usableflag',width:120,align:'center',editor:'text'">版本是否可用标志</th>	
				<th data-options="field:'versionid',hidden:true,width:1,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}}">版本ID</th>			
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>	        
    </div>
	<div id="dlg" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        
        <form id="fm" method="post" novalidate="novalidate">
        	<table class="container">
				<tr>
					<th><label for="devtype">设备区分:</label></th>
			        <td><input id="devtype" name="devtype" required="true" class="easyui-validatebox"/></td>			        	
		        </tr>
		        <tr>
		        	<th><label for="versionno">版本编号:</label></th>
		            <td><input id="versionno" name="versionno" required="true" class="easyui-validatebox"/></td>
		        </tr>
		        <tr>	        
			        <th><label for="releasedate">发布日期:</label></th>
			        <td><input id="releasedate" name="releasedate" required="true" class="easyui-validatebox"/></td>
			    </tr>
			   	<tr>
		            <th><label for="downloadurl">下载地址:</label></th>
		            <td><input id="downloadurl" name="downloadurl" required="true" class="easyui-validatebox" validType="length[0,200]"/></td>
		        </tr>
		        <tr>		           
		            <th><label for="releasecontent">更新内容:</label></th>
		            <td><input id="releasecontent" name="releasecontent" required="true" class="easyui-validatebox"/>
		            <input id="versionid" name="versionid" type='hidden' class="easyui-validatebox"/>
		            </td>
		        </tr>		        
		      </table>
            
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
    </div>
	
</body>
</html>