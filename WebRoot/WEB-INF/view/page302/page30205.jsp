<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
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
<title>推送消息主题类型设置</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
var url;
var topictype=<%=request.getAttribute("topictype")%>;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'消息主题信息',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi12204.json",
		method:'post',
		queryParams:{centerid:''},
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.seqid + '"/>';
			}},
			{title:'中心名称',field:'centerid',width:80,align:'center',formatter:function(value,row,index) {	
				return $('#centerid option[value='+value+']').text(); 	
			}},			
			{title:'消息主题描述',field:'messageTopicType',align:'center',width:130,formatter : function(value) {
					$(topictype).each(function() {					
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			//{title:'消息主题描述',field:'message_topic_type_name',align:'center',width:180,editor:'text'},
			{title:'序号',field:'num',align:'center',hidden:'true',width:10,editor:'text'},					
			{title:'强制推送标记',field:'mustsend',align:'center',width:120,formatter:function(value,row,index) {	
				return row.mustsend=='0'?'消息主题未选定不推送':'消息主题未选定强制推送'; 	
			}}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});	
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#form30205").form("validate")) return;		
		var arr = $('#form30205').serializeArray();
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});
	
	//增加
	$('#b_add').click(function (){		
		$('#dlg').dialog('open').dialog('setTitle','消息主题增加');		
		$('#formtitle').text('消息主题增加');		
		$('#fm').form('clear');		
		url = "<%=request.getContextPath()%>/webapi12201.json";		
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			$('#dlg').dialog('open').dialog('setTitle','消息主题修改');
			$('#formtitle').text('消息主题修改');
			$('#fm').form('load',row[0]);
			url = "<%=request.getContextPath()%>/webapi12203.json";
		}
		else $.messager.alert('提示', '请选中一条待修改消息主题信息！', 'info' );
	});
	
	//保存
	$('#b_save').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();		
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dlg').dialog('close');        	// close the dialog
					$('#dg').datagrid('reload');    	// reload the user data
				}
				$.messager.alert('提示',data.msg,'info');	
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
		var $checkboxs = $('input:checkbox:checked');
		if ($checkboxs.length > 0) {
			var code = $checkboxs.map(function(index){
				return this.value;
			}).get().join(',');
			var arr = [{name : "listSeqid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi12202.json",
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
	
	
	$('#b_tck_cancel').click(function (){
		$('#dlg_tck').dialog('close');
	});
	
	
	$('#b_moveup').click(function (){
		var row = $("#dg").datagrid('getSelected'); 
		if(row==null){
			return;
		}
	    var index = $("#dg").datagrid('getRowIndex', row);
	    mysort(index, 'up', 'dg');
	});
	$('#b_movedown').click(function (){
		var row = $("#dg").datagrid('getSelected');
		if(row==null){
			return;
		}
	    var index = $("#dg").datagrid('getRowIndex', row);
	    mysort(index, 'down', 'dg');
	});
	
	//点击保存
	$("#b_savesort").click(function(){
		
		var param="[";
		 var  data = $("#dg").datagrid('getRows');
		 if(data==null||data.length==0){
			 alert("表格中无数据");
			 return;
		 }
		 
		var grid = $('#dg');
		var options = grid.datagrid('getPager').data("pagination").options;
		var curr = options.pageNumber;
		var total = options.total;
		 
		 for(var i=0;i<data.length;i++){
			var row = $('#dg').datagrid('getData').rows[i];;
			if(i!=0)param+=",";			
			param+="{'seqid':"+row['seqid']+",'num':"+(((curr-1)*options.pageSize)+i+1)+"}";
			
		 }
		 param+="]";
		 
		  
		  $.ajax( {
				type : 'POST',
				url : '<%=request.getContextPath()%>/webapi12205.json?datalist='+param,
				dataType: 'json',
				success : function(data) {
					$.messager.alert('提示', data.msg, 'info');
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');							
				}
			}); 
			
	}); 
	
}) 

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
<div class="easyui-panel" title="消息主题信息查询">
	<form id="form30205" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="30%" /><col width="70%" />
			<tr>
				<th><label for="centerid">中心名称:</label></th>
				<td>
				<select id="centerid" name="centerid" class="easyui-combobox" editable="false" style="width:230px;" ><!-- panelHeight="auto" -->
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
			</tr>
		</table> 
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
		</div>
	</form>
</div>

<table id="dg"></table>

<div id="toolbar">
	<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >增加</a>
	<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
	<a id="b_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
	<a id="b_moveup" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-up" plain="true" >上移</a>
	<a id="b_movedown" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-down" plain="true" >下移</a>
	<a id="b_savesort" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" >保存顺序</a>
	<!-- <a id="b_publish" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >发布</a> -->	        
</div>

<div id="dlg" class="easyui-dialog" style="width:500px;height:320px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons">
	<div class="formtitle" id="formtitle"></div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="centerid1">中心名称:</label></td>
				<td><!-- <input id="devtype" name="devtype" required="true" class="easyui-validatebox" maxlength="1"/>-->
					<select id="centerid1" name="centerid" class="easyui-combobox" style="width:230px;" editable="false" required="true" ><!-- panelHeight="auto" -->				        	
						<%
							String options3 = "";
							List<Mi001> ary3=(List<Mi001>) request.getAttribute("mi001list");
							for(int i=0;i<ary3.size();i++){
								Mi001 mi003=ary3.get(i);
								String itemid=mi003.getCenterid();
								String itemval=mi003.getCentername();
								options3 = (new StringBuilder(String.valueOf(options3))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options3);
						%>
					</select>			        
				</td>			        	
			</tr>
			<tr>
				<td><label for="message_topic_type">消息主题描述:</label></td>
				<td>					
					<select id="messageTopicType" name="messageTopicType" class="easyui-combobox" style="width:230px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options1 = "";
							List<Mi007> ary1=(List<Mi007>) request.getAttribute("message_topic_type");
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
			</tr>			
			<tr>		           
				<td><label for="mustsend">必须推送标记:</label></td>
				<td>
					<select id="mustsend" name="mustsend" style="width:230px;" required="true" panelHeight="auto" class="easyui-combobox" editable="false" validType="length[0,200]">
						<option value="0">消息主题未选定不推送</option>
						<option value="1">消息主题未选定强制推送</option>
					</select>
					<input id="seqid" name="seqid" hidden="true" class="easyui-validatebox""/>					
				</td>
			</tr>	        
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>