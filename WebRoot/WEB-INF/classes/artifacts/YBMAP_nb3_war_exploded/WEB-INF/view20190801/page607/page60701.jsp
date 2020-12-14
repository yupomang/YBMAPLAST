<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>黑名单</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
body {width: 100%; hidden;margin:0;}
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}

.dlg_wrapper {padding:5px;}
#lr_buttons {margin:112px 0px 0px 205px;width:60px;}
#selnames {margin:-185px 0px 0px 265px;border: 1px solid #95B8E7;height:317px;}
#selnames div {background-color:#B2D6F5;padding: 2px 3px;}
#selnames ul {height:295px; overflow: auto;}
#selnames ul li{padding: 2px 3px;display:block;cursor:default;}

.selected {background-color:#FBEC88 !important;}
.hover {background-color:#EAF2FF;}

#b_left,#b_right{ margin-left: 3px;
    margin-right: 3px;
    padding-left: 1px;
    padding-right: 1px;
    width: 50px;}
	
.combobox-item {cursor:default;}	
/*]]>*/
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7db9536a2794ce477288908163157bc6"></script>
<script type="text/javascript">
var url;
$(function() {

	//列表初始化
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'黑名单',
		singleSelect: true,
		height:369,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi60702.json",
		columns:[[
			{title:'编号',field:'blacklistid',align:'center',width:150,editor:'text'},
			{title:'用户名',field:'freeuse2',align:'center',width:60,editor:'text'},
			{title:'有效标记',field:'validflag',align:'center',width:60,editor:'text',formatter:function(v){
		        var json={"0":"无效","1":"有效"};
		        if(json[v]){
		           return json[v];
		        }else{
		           return v;
		        }
		    }},
		    {title:'渠道平台',field:'channel',align:'center',width:130,editor:'text',formatter:function(v){
		        var json={"10":"app","20":"微信","30":"web","40":"前置"};
		        if(json[v]){
		           return json[v];
		        }else{
		           return v;
		        }
		    }},
			{title:'中心码',field:'centerid',align:'center',width:100,editor:'text'},
			{title:'操作员码',field:'loginid',align:'center',width:100,editor:'text'},
			{title:'创建日期',field:'datecreated',align:'center',width:180,editor:'text'},
			{title:'最近修改日期',field:'datemodified',align:'center',width:180,editor:'text'},
			{title:'用户名',field:'blackuserid',align:'center',width:130,editor:'text',hidden:'true'},
			{title:'备用字段1',field:'freeuse1',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'备用日期',field:'freeuse3',align:'center',width:90,editor:'text',hidden:'true'},
			{title:'备用数值',field:'freeuse4',align:'center',width:90,editor:'text',hidden:'true'	}
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【查询出错】：'+data.msg, 'info' );
        }		
	});
})

//修改
function editUser(){     
	var row = $('#dg').datagrid('getSelections');
	if(row.length==1){
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','黑名单信息修改');
			$('#fm').form('load',row[0]);
			$('#loginid').val("<%=user.getLoginid()%>");
			url = "<%=request.getContextPath()%>/webapi60701.json";
		}
	}
	else $.messager.alert('提示', "请选中一条待修改楼盘信息！", 'info' );
}
//保存
function saveUser(){            
	var arr = $('#fm').serializeArray();
	//校验
	if (!$("#fm").form("validate")) return;		
	$.ajax({
		type : "POST",
		url : url,
		dataType: "json",
		data:arr,
		success : function(data) {					
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
			else {
				$.messager.alert('提示', data.msg, 'info' );
				$('#dlg').dialog('close');        // close the dialog
				$('#dg').datagrid('reload');    // reload the user data
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});						
}

//查询
function select60702() {
	//校验
	if (!$("#form60702").form("validate")) return;
	var begindate=$('#begindate').datebox('getValue');
	var enddate=$('#enddate').datebox('getValue');
	var datestr=begindate+""+enddate;
	if(datestr.length==10){
		$.messager.alert('提示', '起始日期与终止日期需同时填写', 'info');
		return;
	}
	if(begindate>enddate){
		$.messager.alert('提示', '请输入有效的时间区间', 'info');
		return;
	}
	//组织提交数据
	var arr = $('#form60702').serializeArray();
	var dataJson = arrToJson(arr);
	//datagrid列表
	$('#dg').datagrid({   
		url: "<%=request.getContextPath()%>/webapi60702.json",
		queryParams:dataJson	
	});	
}

//重置
function cancel60702() {	
	document.getElementById("form60702").reset();
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}
</script>
</head>
  
<body>

<div class="easyui-panel" title="黑名单查询">
	<form id="form60702" class="dlg-form" novalidate="novalidate">
		<table class="container">
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>
				<th><label for="validflag">有效标记:</label></th>
				<td>
					<select id="validflag" name="validflag" readOnly="true">
                        <option value="">请选择...</option>
                        <option value="0" >无效</option>
                        <option value="1" >有效</option>
                    </select>
				</td>		           
				<th><label for="channel">渠道平台:</label></th>
				<td>
					<select id="channel" name="channel">
                        <option value="">请选择...</option>
                        <option value="10" >app</option>
                        <option value="20" >微信</option>
                        <option value="30" >web</option>
                        <option value="40" >前置</option>
                    </select>
				</td>
			</tr>		       
			<tr>
				<th><label for="begindate">开始日期:</label></th>
				<td><input type="text" id="begindate" name="begindate" class="easyui-datebox""/></td>
				<th><label for="enddate">结束日期:</label></th>
				<td><input type="text" id="enddate" name="enddate" class="easyui-datebox""/></td>
				<th></th>
			</tr>
			<tr hidden="true">	        
				<th><label for="blackuserid">用户名:</label></th>
				<td><input type="text" id="blackuserid" name="blackuserid" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select60702()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" onclick="cancel60702()">重置</a></div>
	</form>
</div>    

<!-- 列表数据 -->
<table id="dg" ></table>
<div id="toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
</div>
<!-- 添加/修改对话框 -->
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true" modal="true" buttons="#dlg-buttons">  
	<div class="formtitle">黑名单信息修改</div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<tr hidden="true">	        
				<td><label for="blacklistid">编号:</label></td>
				<td><input type="text" id="blacklistid" name="blacklistid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr>	        
				<td><label for="freeuse2">用户名:</label></td>
				<td><input type="text" id="freeuse2" name="freeuse2" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr>	        
				<td><label for="validflag">有效标记:</label></td>
				<td>  	
					<ul class="multivalue horizontal">
						<li><input id="validflag-0" name="validflag" type="radio" value="0"/><label for="validflag-0">无效</label></li>
						<li><input id="validflag-1" name="validflag" type="radio" value="1"/><label for="validflag-1">有效</label></li>
					</ul>
				</td>
			</tr>
			<tr hidden="true">
				<td><label for="blackuserid">用户名:</label></td>
				<td><input id="blackuserid" name="blackuserid" validType="length[1,300]" required="true" class="easyui-validatebox" readOnly="true"/></td>		        	
			</tr>
			<tr hidden="true">	        
				<td><label for="channel">渠道平台:</label></td>
				<td>
					<input type="text" id="channel" name="channel" class="easyui-validatebox" readOnly="true"/>
				</td>
				
			</tr>
			<tr hidden="true">	        
				<td><label for="centerid">中心码:</label></td>
				<td><input type="text" id="centerid" name="centerid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="loginid">操作人员:</label></td>
				<td><input type="text" id="loginid" name="loginid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="datecreated">创建日期:</label></td>
				<td><input type="text" id="datecreated" name="datecreated" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="datemodified">最近修改日期:</label></td>
				<td><input type="text" id="datemodified" name="datemodified" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse1">备用字段1:</label></td>
				<td><input type="text" id="freeuse1" name="freeuse1" class="easyui-validatebox" readOnly="true" /></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse3">备用日期:</label></td>
				<td><input type="text" id="freeuse3" name="freeuse3" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse4">备用数字:</label></td>
				<td><input type="text" id="freeuse4" name="freeuse4" class="easyui-validatebox" readOnly="true"/></td>
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
