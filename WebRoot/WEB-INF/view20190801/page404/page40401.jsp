<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>意见反馈</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}

#confirmdetail {height:204px;width:362px;margin:3px 0px;}
.caption {background-color:#EAF2FF;}
#dlg {width:400px;height:320px;padding:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
var url;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'意见反馈信息',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi40401.json",
		method:'post',
		queryParams:arrToJson($('#form40401').serializeArray()),
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',checkbox:true},
			{title:'用户名',field:'userid',width:80,align:'center'},
			{title:'版本号',field:'versionno',align:'center',width:60,editor:'text'},
			{title:'设备区分',field:'devtype',align:'center',width:60,editor:'text',formatter:function (value,row,index){	
				return $('#devType option[value='+value+']').text(); 	
			}},
			{title:'设备标识',field:'devid',hidden:'true',align:'center',width:80,editor:'numberbox'},
			{title:'反馈内容',field:'detail',align:'center',width:250,editor:'text'},
			{title:'确认人',field:'confirmid',align:'center',width:80,editor:'text'},
			{title:'确认状态',field:'status',align:'center',width:60,editor:'text',formatter:function(value,row,index){	
				return $('#status option[value='+value+']').text(); 	
			}},
			{title:'确认日期',field:'confirmtime',align:'center',width:100,editor:'text'},
			{title:'确认人意见',field:'confirmdetail',align:'center',width:250,editor:'text',formatter:function (value,row,index){	
				return $('#confirmdetail option[value='+value+']').text(); 	
			}},
			{title:'序号',field:'seqno',hidden:true,align:'center',width:60,editor:'text'}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#form40401").form("validate")) return;
		//组织查询条件
		var arr = $('#form40401').serializeArray();
		/*
		if(document.getElementById("startdate").value==""){
			arr[2].value="";
		}
		if(document.getElementById("enddate").value==""){
			arr[3].value="";
		}
		*/
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40401").reset();	
		$('#form40401').form('clear');
		//设置combobox默认值
		$('#devType').combobox('select','');
		$('#status').combobox('select','');
		$('#centerid').combobox('select','');
		ydl.delErrorMessage('form40401');
	});	
	
	//跟踪处理
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelected');           
		if (row){
			if(row.status!=1){//未确认时进行确认
				$('#dlg').dialog('open').dialog('setTitle','确认意见');
				$('#fm').form('load',row);
				url = "<%=request.getContextPath()%>/webapi40402.json";
			}
			else $.messager.alert('提示', '该意见已经确认！', 'info' );
		}else{
			$.messager.alert('提示', '请选中一条待确认意见反馈信息！', 'info' );
		}
	});	
	
	//保存
	$('#b_save').click(function (){
		var arr = $('#fm').serializeArray();
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#fm").form("validate")) return;
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
	<div class="easyui-panel" title="意见反馈信息查询">
		<form id="form40401" class="dlg-form" novalidate="novalidate">
			<table class="container">	
				<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
		        <tr>	        
			        <th><label for="devtype">设备区分:</label></th>
			        <td><!-- <input id="devtype" name="devtype" class="easyui-validatebox" maxlength="1"/> -->
			        <select id="devType" name="devType" class="easyui-combobox" editable="false" style="width:220px;" panelHeight="auto">
			        	<option value="">请选择...</option>
							<%
								String options1 = "";
	       						List<Mi007> ary1=(List<Mi007>) request.getAttribute("devicetypeList");
	       						for(int i=0;i<ary1.size();i++){
	          						Mi007 mi007=ary1.get(i);
	          						String itemid=mi007.getItemid();
	          						String itemval=mi007.getItemval();
	          						options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	       						}
	       						out.println(options1);
	         				%>
			        </select>
			        </td>
		            <th><label for="status">确认状态:</label></th>
		            <td><!-- <input id="status" name="status" class="easyui-validatebox" maxlength="1"/> -->
		            <select id="status" name="status" class="easyui-combobox" editable="false" style="width:220px;" panelHeight="auto">			        	
						<option value="">请选择...</option>
							<%
								String options2 = "";
	       						List<Mi007> ary2=(List<Mi007>) request.getAttribute("statusList");
	       						for(int i=0;i<ary2.size();i++){
	          						Mi007 mi007=ary2.get(i);
	          						String itemid=mi007.getItemid();
	          						String itemval=mi007.getItemval();
	          						options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	       						}
	       						out.println(options2);
	         				%>
			        </select>
		            </td>
		        </tr>
		        <tr>		           
		            <th><label for="centerid">中心名称:</label></th>
		            <td>		            
		            	<select id="centerid" name="centerid" class="easyui-combobox" editable="false" style="width:220px;"><!-- panelHeight="auto" 这个参数设置上下拉选无滚动条 -->
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
					    </select>
		            
		            </td>
		            <th><label for="versionno">版本编号:</label></th>
		            <td><input id="versionno" type="text" name="versionno" class="easyui-validatebox" style="width:220px;" validType="versionno" maxlength="20" /></td>
		        </tr>
		        <tr>		           
		            <th><label for="startdate">开始日期:</label></th>
		            <td><input id="startdate" name="startdate" editable="false" class="easyui-datebox" style="width:220px;"  maxlength="10" validType="date&&maxdate['enddate','【结束日期】']"/></td>
		            <th><label for="enddate">结束日期:</label></th>
		            <td><input id="enddate" name="enddate" editable="false" class="easyui-datebox" style="width:220px;" maxlength="10" validType="date&&mindate['startdate','【开始日期】']"/></td>
		        </tr>
	        </table> 
	        <div class="buttons">
				<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
				<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
			</div>
		</form>
	</div>
	
	<table id="dg"></table>
	<div id="toolbar">	        
	    <a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >跟踪处理</a>	        
    </div>
	
	<div id="dlg" class="easyui-dialog" closed="true" style="width:400px;height:270px;padding:10px 20px"  modal="true"  buttons="#dlg-buttons">  
		<div class="formtitle">确认信息</div>
        <form id="fm" method="post" class="dlg-form" novalidate="novalidate">
        	<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>
					<th><label for="status">确认状态：</label></th>
			        <td><!-- <textarea id="confirmdetail" name="confirmdetail" required="true" class="easyui-validatebox" ></textarea> -->
			        <select id="status" name="status" class="easyui-combobox" editable="false" style="width:160px;" panelHeight="auto" required="true">	
							<%
								String options4 = "";
	       						List<Mi007> ary4=(List<Mi007>) request.getAttribute("statusList");
	       						for(int i=0;i<ary4.size();i++){
	          						Mi007 mi007=ary4.get(i);
	          						String itemid=mi007.getItemid();
	          						String itemval=mi007.getItemval();
	          						options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	       						}
	       						out.println(options4);
	         				%>
			        </select>			        	        
			        </td>			        	
		        </tr>   
				<tr><th><label for="confirmdetail">确认人意见：</label></th>
			        <td><!-- <textarea id="confirmdetail" name="confirmdetail" required="true" class="easyui-validatebox" ></textarea> -->
			        <select id="confirmdetail" name="confirmdetail" class="easyui-combobox" style="width:160px;" editable="false" panelHeight="auto" required="true">
							<%
								String options3 = "";
	       						List<Mi007> ary3=(List<Mi007>) request.getAttribute("yjfkList");
	       						for(int i=0;i<ary3.size();i++){
	          						Mi007 mi007=ary3.get(i);
	          						String itemid=mi007.getItemid();
	          						String itemval=mi007.getItemval();
	          						options3 = (new StringBuilder(String.valueOf(options3))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	       						}
	       						out.println(options3);
	         				%>
			        </select>			        	        
			        </td>			        	
		        </tr>   
	        </table>  
	        <input id="seqno" name="seqno" type="hidden" />	
        </form>
    </div>
    <div id="dlg-buttons">
        <a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
        <a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
    </div>	
</body>
</html>
