<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.form.ItemInfo"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
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
<title>应聘信息管理</title>
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
$(function() {
	//初始化列表	
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'应聘信息查询结果',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi99901.json",
		method:'post',
		queryParams:arrToJson($('#form99901').serializeArray()),
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		singleSelect:true,
		columns:[[
			{field:'id',title:'操作',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.seqno + '"/>';
			}},
			{title:'应聘区域',field:'applyarea',align:'center',width:100,editor:'text',formatter:applyareaformater},
			{title:'应聘职位',field:'freeuse2',align:'center',width:150,editor:'text'},
			{title:'姓名',field:'username',align:'center',width:60,editor:'text'},
			{title:'电话号码',field:'phone',width:80,align:'center'},
			{title:'电子邮箱',field:'email',align:'center',width:150,editor:'text'},
			{title:'应聘日期',field:'datecreated',align:'center',width:150,editor:'text'},
			{title:'是否已读',field:'freeuse1',align:'center',width:80,editor:'text',formatter:isreadformater},		
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('form99901')) return;
		if (!$("#form99901").form("validate")) return;
		//组织查询条件
		var arr = $("#form99901").serializeArray();
		var dataJson = arrToJson(arr);
		//查询列表数据
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form99901").reset(); 
		$('#form99901').form('clear');
		$('#applypositionQry').combobox('clear');
		$('#applypositionQry').combobox('loadData', '');
		//除去错误校验状态
		ydl.delErrorMessage('form99901');
	});
	
	var str = <%=request.getAttribute("applyareaQryObj")%>;
	var initApplyareaQry = str.rows[0].itemid;
	//级联选择
    var selectApplyarea = initApplyareaQry;
	$('#applyareaQry').combobox({
    	onChange:function(newValue,oldValue){
    	 	selectApplyarea = newValue;
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getApplyPostionJsonArray.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','applyarea':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#applypositionQry').combobox('clear');
						$('#applypositionQry').combobox('loadData', data.applyPostionJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
	
	//批量导出提交
	$('#download_submit_form').click(function () {
	    var $checkboxs = $('input:checkbox:checked');
		var para = $checkboxs.map(function(index){
			return this.value;
			}).get().join(',');
		$("#seqnos").val(para);
		$('#downloadform').form('submit',{  
			url: 'joboffersinfoToExcel.do',
			onSubmit: function(){
				var fileName=$('#fileName').val();
				var seqnos = para;
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
	
})

function applyareaformater(value,row,index){
 	return $('#applyareaQry option[value='+value+']').text();
}

function isreadformater(value,row,index){
 	return $('#isread option[value='+value+']').text();
}

function editInfo(){
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否设置成已读?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={seqnos:para}
            	          
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi99902.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#dg').datagrid('reload');    // reload the user data
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');							
					}
				});
            }
    	});
    }
    else $.messager.alert('提示', "请至少选择一条记录进行已读设置！", 'info');
}

function delInfo(){
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={seqnos:para}
            	          
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi99903.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#dg').datagrid('reload');    // reload the user data
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');							
					}
				});
            }
    	});
    }
    else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
}

function batchDownload(){
    $('#dlgBatchDownload').dialog('open').dialog('setTitle','应聘信息-批量导出');
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
<div class="easyui-panel" title="应聘信息查询">
	<form id="form99901" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="applyareaQry">应聘区域:</label></th>
				<td>
					<select id="applyareaQry" name="applyareaQry" editable="false" style="width:220px;" class="easyui-combobox" data-options="valueField:'itemid',textField:'itemval'" >				        	
							<option value=""></option>
							<%
								String options1 = "";
								List<ItemInfo> ary1=(List<ItemInfo>) request.getAttribute("applyAreaList");
								for(int i=0;i<ary1.size();i++){
									ItemInfo iteminfo=ary1.get(i);
									String itemid=iteminfo.getItemid();
									String itemval=iteminfo.getItemval();
									options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options1);
							%>
					</select>
				</td>
				<th><label for="applypositionQry">应聘职位:</label></th>
				<td>
					<select id="applypositionQry" name="applypositionQry" editable="false" class="easyui-combobox" style="width:220px;" data-options="valueField:'itemid',textField:'itemval'">
							<option value=""></option>
					</select>
				</td>
			</tr>
			<tr>		           
				<th><label for="startdate">开始日期:</label></th>
				<td><input type="text" id="startdate" name="startdate" style="width:220px;" class="easyui-datebox" editable="false" validType="date&&maxdate['enddate','【结束日期】']" maxlength="10"/></td>
				<th><label for="enddate">结束日期:</label></th>
				<td><input type="text" id="enddate" name="enddate" style="width:220px;" class="easyui-datebox" editable="false" validType="date&&mindate['startdate','【开始日期】']" maxlength="10"/></td>
			</tr>
			<tr>		           
				<th><label for="isread">是否已读:</label></th>
				<td>
					<select id="isread" name="isread" editable="false" style="width:220px;" class="easyui-combobox" panelHeight="auto">				        	
						<option value=""></option>
							<%
								String options2 = "";
								List<ItemInfo> ary2=(List<ItemInfo>) request.getAttribute("isreadList");
								for(int i=0;i<ary2.size();i++){
									ItemInfo iteminfo=ary2.get(i);
									String itemid=iteminfo.getItemid();
									String itemval=iteminfo.getItemval();
									options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options2);
							%>
					</select>
				</td>
			</tr>
		</table>  
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
		</div>
	</form>
</div>

<table id="dg" ></table>
<div id="toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editInfo()">已读设置</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除信息</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download-exceltemp" plain="true" onclick="batchDownload()">批量导出</a>
</div>

    <div id="dlgBatchDownload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
        <div class="formtitle">应聘信息批量导出</div>
        <form id = "downloadform" class="dlg-form" method="post" novalidate="novalidate">
			<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="fileName">生成文件名称：</label></td>
					<td><input type="text" id = "fileName" name="fileName" class="easyui-validatebox" required="true"/><span> (.xls结尾)</span></td>	        	
				</tr>
			</table>
			
			<input type="hidden" name="titles" value="applyarea,applyposition,username,phone,email,datecreated,freeuse1"/>
			<input type="hidden" name="expotrTableName" value="JobOffers"/> 
			<input type="hidden" type="text" id="seqnos" name="seqnos" value=""/> 
        </form>
        <div id="dlg-buttons">
        	<a id="download_submit_form" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >导出</a>
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchDownload').dialog('close')">取消</a>
    	</div>
    </div>
</body>
</html>
