<%@ page language="java" contentType="text/html; charset=utf-8" %>
<% 
  /*
     文件名：ptl40002.jsp
     作者： 韩占远
     日期：2013-10-06 
     作用：角色管理
  */
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
  String attributeFlglistInfo = request.getAttribute("attributeFlgList").toString();
  String attributeFlglistInfoTmp = request.getAttribute("attributeFlgListTmp").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>角色管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container {width:300px; padding:5px;}
#form-container {padding-top:5px;padding-left:0px;padding-right:5px;padding-bottom:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">
$(function(){
	
	// 隐藏城市的下拉列表，只用于datagrid中名字的显示
	$("#attributeflgHidden").hide();
	
    $("#roleTab").datagrid({
		//width:300,
		width:$('#list-container').width(),
		height:$(document).height()-40,
		fitColumns: true,
		idField:'roleid',
		singleSelect:true,
		url:'ptl40002Qry.json?centerid='+'<%=user.getCenterid()%>',
		onClickRow:function(rowIndex, rowData){
			$('#roleid').prop('readOnly',true);
			$('#roleForm').form('load',rowData);
			$('#roleInfo').panel('setTitle','修改角色信息');
			if('00000000' == rowData.centerid){
				var dataAll = <%=attributeFlglistInfo%>;
				$('#attributeflg').combobox('loadData', dataAll);
			}else{
				var dataAll = <%=attributeFlglistInfoTmp%>;
				$('#attributeflg').combobox('loadData', dataAll);
			}
		},
		columns:[[
			{field:'op',title:'删除',width:100,formatter:function(v,row){
				return "<input type='checkbox' value='"+row["roleid"]+"' name='chk_roleid'>"
			}}, 
			{field:'roleid',title:'角色代码',width:130},
			{field:'rolename',title:'角色名称',width:180}
			<%
				JSONObject centeridTmpObj = (JSONObject)request.getAttribute("centerObjJson");
				String centeridTmp1 = centeridTmpObj.getString("centerid");
				if("00000000".equals(centeridTmp1)){
			%>
			,
			{field:'centerid',title:'所属中心',width:100,formatter:function(value,row,index) {
				return $('#centerid option[value='+value+']').text(); 	
			}},
			{field:'attributeflg',title:'所属标志',width:100,formatter:function(value,row,index) {
				return $('#attributeflgHidden option[value='+value+']').text(); 	
			}}
			<%}%>
		]],
		toolbar:[{
			id:'btnadd',
			text:'新建',
			iconCls:'icon-add', 
			handler:function(){
				  $('#roleid').prop('readOnly',false);
				  $('#roleForm').form('clear');
				  $('#roleInfo').panel('setTitle','添加角色信息');
			  }
			},{	
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove', 
			handler:function(){
			   var mi002data=[];  
			   $("[name='chk_roleid']:checked").each(function(i){
				  mi002data.push($.trim($(this).val()));
			   });
			   if(mi002data.length > 0){
					$.messager.confirm('提示','确认是否删除?',function(r){
					if (r) {
						var map={roleid:mi002data.join(",")}	      
			   			$.ajax({
							type : "POST",
							url : "ptl40002Del.json",
							dataType: "json",
							data:map,
							success :function(data) {			   
								$("#roleTab").datagrid("reload");
								roleForm.reset();
							}
			   			});
					}});
			   }
			   else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
		   }
		}]
    });
	
	$("#btnsave").click(function(){
		var mi002arry = $('#roleForm').serializeArray();        
		var url = null;     
		if ($(roleForm.roleid).attr("readonly")) url="ptl40002Upd.json";
		else url="ptl40002Add.json";
		//校验
		var tgbz = $("#roleForm").form("validate");      
		if (tgbz){          
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi002arry,
				success :function(data) {
					if(SUCCESS_CODE == data.recode) {
						$("#roleTab").datagrid("reload");
					}else{
						$.messager.alert('错误', data.msg, 'error');
					}
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');
				}
			});
		} 
	});
	
	//设置页面结构高度宽度
	$('#layout').height($(document).height()-30);
	$('#list-container,#form-container').height($(document).height()-40);
	//角色信息表单标题
	$('#roleInfo').panel({
		title:'添加角色信息', 
		height:$(document).height()-40
	});
	
	//级联选择
    $('#centerid').combobox({
    	onChange:function(newValue,oldValue){
    		var dataAll = <%=attributeFlglistInfo%>;
    		var dataTmp = <%=attributeFlglistInfoTmp%>;
        	$('#attributeflg').combobox('clear');
        	if ('00000000' == newValue) {
        		$('#attributeflg').combobox('loadData', dataAll);
        	}else {
        		$('#attributeflg').combobox('loadData', dataTmp);
        	}	
    }
});
})
</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="角色管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 角色列表开始 -->
		<table id="roleTab"></table>
		<!-- 角色列表结束 -->
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<div id="roleInfo"> 
		<form id="roleForm" class="dlg-form" novalidate="novalidate">        
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="roleid">角色代码：</label></td>
					<td> <input type='text' id="roleid" name="roleid" style="width:220px;" required="true" class="easyui-validatebox"  maxlength="8"/></td>
				</tr>
				<tr>
					<td><label for="rolename">角色名称：</label></td>
					<td><input type='text' id="rolename" name="rolename" style="width:220px;" required='true' class="easyui-validatebox" maxlength="30" /> </td>
				</tr>
				<tr>
					<td><label for="centeridLabel">所属中心：</label></td>
					<td>
						<select id="centerid" name="centerid" class="easyui-combobox" style="width:220px;" required="true" editable="false"><!-- panelHeight="auto"  -->
							<option value=""></option>
							<%
								String options = "";
           						JSONArray ary=(JSONArray) request.getAttribute("centerList");
           						for(int i=0;i<ary.size();i++){
              						JSONObject obj=ary.getJSONObject(i);
              						String itemid=obj.getString("centerid");
              						String itemval=obj.getString("centername");
              						options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
           						}
           						out.println(options);
          					%>
						</select>
						<!-- attributeflgHidden用于datagrid中所属标志的显示 -->
						<select id="attributeflgHidden" name="attributeflgHidden" style="width:220px;" panelHeight="auto">
							<%
								String flgOptionsTmp = "";
           						JSONArray attributeFlgAryTmp=(JSONArray) request.getAttribute("attributeFlgList");
           						for(int i=0;i<attributeFlgAryTmp.size();i++){
              						JSONObject obj=attributeFlgAryTmp.getJSONObject(i);
              						String itemid=obj.getString("itemid");
              						String itemval=obj.getString("itemval");
              						flgOptionsTmp = (new StringBuilder(String.valueOf(flgOptionsTmp))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
           						}
           						out.println(flgOptionsTmp);
          					%>
						</select>
					</td>
				</tr>
				<%
				JSONObject centeridObj = (JSONObject)request.getAttribute("centerObjJson");
				String centeridTmp = centeridObj.getString("centerid");
				if("00000000".equals(centeridTmp)){
				%>
				<tr>
					<td><label for="attributeflg">所属标志：</label></td>
					<td>
						<select id="attributeflg" name="attributeflg" class="easyui-combobox" style="width:220px;" required="true" panelHeight="auto" editable="false" data-options="valueField:'itemid',textField:'itemval'" >
							<option value=""></option>
							<%
								String flgOptions = "";
           						JSONArray attributeFlgAry=(JSONArray) request.getAttribute("attributeFlgList");
           						for(int i=0;i<attributeFlgAry.size();i++){
              						JSONObject obj=attributeFlgAry.getJSONObject(i);
              						String itemid=obj.getString("itemid");
              						String itemval=obj.getString("itemval");
              						flgOptions = (new StringBuilder(String.valueOf(flgOptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
           						}
           						out.println(flgOptions);
          					%>
						</select>
					</td>
				</tr>
				<% } %>
			</table>
			<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
		</form> 
		</div>
	</div>
</div>
</body>
</html>