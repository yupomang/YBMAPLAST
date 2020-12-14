<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
	UserContext user = UserContext.getInstance();

	if (user == null) {
		out.println("超时");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="contexPath" content="<%=_contexPath%>" />
		<title>中心微信基础信息配置</title>
		<%@ include file="/include/styles.jsp"%>
		<style type="text/css">
/*<![CDATA[*/
.datagrid {
	padding-top: 5px;
}

.panel-header,.panel-body {
	width: auto !important;
}
/*]]>*/
</style>
		<%@ include file="/include/scripts.jsp"%>
		<script type="text/javascript">
var url;
var ctid;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'中心微信基础信息',
		height:369,
		singleSelect: true,		
		//pagination:true,
		rownumbers:true,
		fitColumns:true,		
		method:'post',		
		url: "<%=request.getContextPath()%>/weixinapi00103.json",
		columns:[[
			//{field:'ck',title:'  ',align:'center',formatter:function (value,row,index){				
			//	return '<input type="checkbox" name="animate" value="' + row.animateid + '"/>';
			//}},			
			{title:'城市中心',field:'regionId',align:'center',width:110,editor:'text',formatter:function (value,row,index){	
				return $('#regionId option[value='+value+']').text(); 	
			}},			
			{title:'微信账号',field:'weixinId',align:'center',width:80,editor:'text'},			
			{title:'开发者ID',field:'appId',align:'center',width:100,editor:'text'},
			{title:'开发者密钥',field:'appScret',align:'center',width:130,editor:'text'},
			{title:'中心Token',field:'msgToken',align:'center',width:130,editor:'text'},
			{title:'链接地址',field:'msgUrl',align:'center',width:180,editor:'text'}						
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){
			if(data.errcode != '0') $.messager.alert('提示', data.errmsg, 'info' );
        }
	});
	
	//查询
	$('#b_query').click(function (){		
		if (!$("#formweixin001").form("validate")) return;		
		var arr = $("#formweixin001").serializeArray();
		var dataJson = arrToJson(arr);
		$('#dg').datagrid({
			url: "<%=request.getContextPath()%>/weixinapi00102.json",
			queryParams:dataJson	
		});	
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#formweixin001').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');
		//除去错误校验状态
		ydl.delErrorMessage('formweixin001');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','中心微信基础信息配置');		
		$('#formtitle').text('中心微信基础信息配置');
		$('#fm').form('clear');		
		url="<%=request.getContextPath()%>/weixinapi00101.json"		
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){							
				$('#dlg').dialog('open').dialog('setTitle','中心微信基础信息配置修改');
				$('#formtitle').text('中心微信基础信息配置修改');
				$('#fm').form('load',row[0]);
				url = "<%=request.getContextPath()%>/weixinapi00104.json";
			}
		}
		else $.messager.alert('提示', '请选中一条待修改中心微信基础信息！', 'info' );
	});
	
	//保存
	$('#b_save').click(function (){		
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();		
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.errcode == 0) {					
					$('#dg').datagrid("reload");    	// reload the user data
					$('#dlg').dialog('close');        	// close the dialog						
					$.messager.alert('提示','成功','info');
					alert(j);
				}else{
					$.messager.alert('提示',data.errmsg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
		
	});
	
	//删除
	$('#b_del').click(function (){
		//var row = $('#dg').datagrid('getSelections');        
		//if (row.length > 0) {
			//var code=row[0].versionid;            	
			//for(var i=1;i<row.length;i++){
			//	code = code + ","+row[i].versionid;
			//} 
		var $checkboxs = $('input[name=animate]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');			
			var arr = [{name : "animateid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi41102.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#dg').datagrid('reload');    // reload the user data
							}
							else $.messager.alert('提示',data.msg,'info');					
						},
						error :function(){
							$.messager.alert('错误','网络连接出错！','error');							
						}
					});
				}
				
			});
		}
		else $.messager.alert('提示', '请选中待删除信息！', 'info' );
	});
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	
	
})




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
		<script type="text/javascript"
			src="<%=_contexPath%>/scripts/jquery.form.js"></script>
		<!-- jquery.form.js 为页面提交json请求应用 -->
		<div class="easyui-panel" title="中心微信基础信息查询">
			<form id="formweixin001" class="dlg-form" novalidate="novalidate">
				<table class="container">				
					<col width="50%" />
					<col width="50%" />	
					<tr>
						<th>
							<label for="regionId">
								城市中心:
							</label>
						</th>
						<td>
							<select id="regionId" name="regionId" class="easyui-combobox" editable="false" style="width:250px;" ><!-- panelHeight="auto" -->
							<%if(!user.getCenterid().equals("00000000")){ 
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									if(mi001.getCenterid().equals(user.getCenterid())){
										String itemid=mi001.getCenterid();
										String itemval=mi001.getCentername();
										options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
										out.println(options);
										break;				
									}									
								}
							}else{%>
							<option value="">请选择...</option>
							<%  
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									String itemid=mi001.getCenterid();
									String itemval=mi001.getCentername();
									options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options);							
							%>					    
					    <%} %>	
					    </select>					
						</td>						
					</tr>				
				</table>
				<div class="buttons">
					<a id="b_query" class="easyui-linkbutton" iconCls="icon-search"
						href="javascript:void(0)">查询</a>
					<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear"
						href="javascript:void(0)">重置</a>
				</div>
			</form>
		</div>
		<table id="dg"></table>
		<div id="toolbar">
			<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">增加</a>
			<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a>			
		</div>
		<div id="dlg" class="easyui-dialog"
			style="width: 550px; height: 370px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons">
			<div class="formtitle" id="formtitle"></div>
			<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<col width="30%" />
					<col width="70%" />
					<tr>
						<td>
							<label for="regionId">
								城市中心:
							</label>
						</td>
						<td>
							<select id="regionId" name="regionId" class="easyui-combobox"
								style="width: 250px;" editable="false" required="true"><!-- panelHeight="auto" -->
								<%
									String options3 = "";
									List<Mi001> ary3 = (List<Mi001>) request.getAttribute("mi001list");
									for (int i = 0; i < ary3.size(); i++) {
										Mi001 mi003 = ary3.get(i);
										String itemid = mi003.getCenterid();
										String itemval = mi003.getCentername();
										options3 = (new StringBuilder(String.valueOf(options3)))
												.append("<option value=\"").append(itemid)
												.append("\">").append(itemval).append("</option>\n")
												.toString();
									}
									out.println(options3);
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label for="weixinId">
								微信账号:
							</label>
						</td>
						<td>
							<input type="text" id="weixinId" name="weixinId"
								weixinId"" required="true" validType="length[0,200]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="appId">
								开发账号:
							</label>
						</td>
						<td>
							<input type="text" id="appId" name="appId" required="true"
								validType="numletter&&length[0,200]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="appScret">
								开发密钥:
							</label>
						</td>
						<td>
							<input type="text" id="appScret" name="appScret" required="true"
								validType="numletter&&length[0,200]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="msgToken">
								中心Token:
							</label>
						</td>
						<td>
							<input type="text" id="msgToken" name="msgToken" required="true"
								validType="numletter&&length[0,200]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="msgUrl">
								链接地址:
							</label>
						</td>
						<td>
							<input type="text" id="msgUrl" name="msgUrl" required="true"
								validType="url&&length[0,500]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="dlg-buttons">
			<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">保存</a>
			<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>

	</body>
</html>
