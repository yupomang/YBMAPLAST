<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="java.util.List"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<title>利率维护</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
var url;
$(function () {
	// 隐藏城市的下拉列表，只用于datagrid中名字的显示
	$("#centeridQry").hide();
	
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'利率信息',
		height:369,
		url: "<%=request.getContextPath()%>/webapi00904.json",
		method:'post',
		queryParams:{ratetype:$('#ratetype').val()},
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		singleSelect:true,
		columns:[[
			{field:'id',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.rateid + '"/>';
			}},
			{title:'利率ID',field:'rateid',width:20,align:'center',editor:'text'},
			{title:'中心ID/名称',field:'centerid',align:'center',width:60,editor:'text',formatter:centerformater},
			{title:'利率类型',field:'ratetype',align:'center',width:30,editor:'text',formatter:ratetypeformater},
			{title:'月数期限',field:'terms',align:'center',width:30,editor:'text', formatter:termsformater},
			{title:'基准利率',field:'rate',align:'center',width:30,editor:'text',formatter:rateformater},
			{title:'生效日期',field:'effective_date',align:'center',width:30,editor:'text'},
			{title:'修改时间',field:'datemodified',align:'center',width:60,editor:'text'},
			{title:'创建时间',field:'datecreated',align:'center',width:60,editor:'text'}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});

	//校验月数期限大于0，小于等于360期
	$.extend($.fn.validatebox.defaults.rules, {
		range: {
			validator: function (value, param) {
				return value >= param[0] && value <= param[1];
        	},
        	message:'必须输入1~360之间的月数'
    	}
	});
	
	//校验基准利率大于0，小于100
	$.extend($.fn.validatebox.defaults.rules, {
		rateRange: {
			validator: function (value, param) {
				return value > param[0] && value < param[1];
        	},
        	message:'输入内容必须大于0且小于100'
    	}
	});
	
	//查询
	$('#b_query').click(function (){
		//校验
		if (!$("#form00904").form("validate")) return;
		
		var arr = $('#form00904').serializeArray();
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});
});

function addInfo(){
    $('#dlg').dialog('open').dialog('setTitle','利率维护-增加');
    $('#fm').form('clear');
    url = '<%= _contexPath %>/webapi00901.json';
}

function editInfo(){
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length != 1){
    	$.messager.alert('提示', "请选择一条记录进行修改!", 'info' );
    }else{
		$('#dlg').dialog('open').dialog('setTitle','利率维护-修改');
        $('#fm').form('load',rows[0]);
        $('#oldratetype').val(rows[0].ratetype);
        $('#oldterms').val(rows[0].terms);
        $('#oldeffectiveDate').val(rows[0].effective_date);
        url = '<%= _contexPath %>/webapi00903.json';
    }
}

function saveInfo(){
    var arr = $('#fm').serializeArray();
	//校验
	//if (!ydl.formValidate('fm')) return;
	if (!$("#fm").form("validate")) return;
	$.ajax({
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlg').dialog('close');        // close the dialog
            $('#dg').datagrid('reload');    // reload the user data
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}

function delInfo(){
    // var rows = $('#dg').datagrid('getSelections');
    // if (rows.length > 0){
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if (r) {
             	//var para = rows[0].rateid;
             	//for(var i=1;i<rows.length;i++){
             	//	para = para + "," + rows[i].rateid;
             	//}
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={rateid:para}
            	          
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi00902.json',
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

function centerformater(value,row,index){
 	return $('#centeridQry option[value='+value+']').text();
}
function ratetypeformater(value,row,index){
 	return $('#ratetype option[value='+value+']').text();
}
function termsformater(value,row,index){
	return row.terms+'期';
}
function rateformater(value,row,index){
	return row.rate+'%';
}
function flagformater(value,row,index){
 	if ("0" == row.validflag) return "无效数据";
 	else return "有效数据";
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}
//]]></script>
</head>
<body>
	<div class="easyui-panel" title="利率信息">
		<form id="form00904" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<th><label for="ratetypeQryLabel">利率类型：</label></th>
					<td>
						<select id="ratetypeQry" name="ratetypeQry" class="easyui-combobox" panelHeight="auto">
							<option value="">请选择...</option>
							<%
								String options = "";
           						JSONArray ary=(JSONArray) request.getAttribute("ratetypelist");
           						for(int i=0;i<ary.size();i++){
              						JSONObject obj=ary.getJSONObject(i);
              						String itemid=obj.getString("itemid");
              						String itemval=obj.getString("itemval");
              						options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
           						}
           						out.println(options);
          					%>
						</select>
						<select id="centeridQry" name="centeridQry" panelHeight="auto">	        	
							<%
								String optionsCenterid = "";
								List<Mi001> ary3=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary3.size();i++){
									Mi001 mi003=ary3.get(i);
									String itemid=mi003.getCenterid();
									String itemval=mi003.getCentername();
									optionsCenterid = (new StringBuilder(String.valueOf(optionsCenterid))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(optionsCenterid);
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
	
	<table id="dg" >
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addInfo()">增加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editInfo()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除</a>
    </div>
    
   <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">利率信息</div>
        <form id="fm" class="dlg-form" method="post" novalidate="novalidate">
			<table class="dlgcontainer">
			<tr>
				<td><label for="ratetypeLabel" >利率类型：</label></td>
				<td>
					<select id="ratetype" class="easyui-combobox" name="ratetype" required="true" panelHeight="auto">
						<!--
						<option value="">请选择...</option>
						-->
							<%
								String optionsAdd = "";
								JSONArray aryAdd=(JSONArray) request.getAttribute("ratetypelist");
								for(int i=0;i<aryAdd.size();i++){
									JSONObject obj=aryAdd.getJSONObject(i);
									String itemid=obj.getString("itemid");
									String itemval=obj.getString("itemval");
									optionsAdd = (new StringBuilder(String.valueOf(optionsAdd))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(optionsAdd);
							%>
					</select>
				</td>
			</tr>
			<tr>
				<td><label for="terms" >月数期限：</label></td>
                <td><input type="text" id="terms" name="terms" class="easyui-numberbox" precision="0" required="true" maxlength=3 validType="range[1,360]"><span> 期</span></td>
			</tr>
			<tr>
				<td><label for="rate" >基准利率：</label></td>
				<td><input type="text" id="rate" name="rate" class="easyui-numberbox" precision="2" required="true" validType="rateRange[0,100]"><span> %</span></td>
			</tr>
			<tr>
				<td><label for="effective_date" >生效日期：</label></td>
                <td><input type="text" id="effective_date" name="effective_date" class="easyui-datebox" required="true" validType="date"></td>
			</tr>
			</table>
			<!-- <input type="hidden" id="centerId" name="centerId" value="<%=user.getCenterid()%>"/> -->
			<input type="hidden" id="rateid" name="rateid" />
			<input type="hidden" id="oldratetype" name="oldratetype" />
			<input type="hidden" id="oldterms" name="oldterms" />
			<input type="hidden" id="oldeffectiveDate" name="oldeffectiveDate" />
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInfo()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
</body>
</html>
