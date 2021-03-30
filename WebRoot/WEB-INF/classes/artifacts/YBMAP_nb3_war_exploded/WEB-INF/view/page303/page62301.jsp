<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="com.yondervision.mi.dto.Mi620"%>
<%@page import="com.yondervision.mi.dto.Mi621"%>
<%@page import="com.yondervision.mi.dto.Mi622"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>预约网点管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#tree-container{width: 300px; height:100%; margin:5px;padding:5px;overflow-y: scroll;}
#form-container {width: 70%;height:100%;padding:5px;}
#website-container {width: 70%;}
#time-container {width: 70%;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %> 
<script type="text/javascript">//<![CDATA[
function reloadTree(){ 
	var node = $('#MyTree').tree('getSelected'); 
	if(node) $('#MyTree').tree('reload', node.target); 
	else $('#MyTree').tree('reload'); 
	$("#websiteTab").datagrid("reload");
	$("#timeTab").datagrid("reload");	
} 
function createTree(){
	$('#MyTree').tree({
		height:$(document).height()-50,
		onBeforeExpand:function(node,param){  
			$('#MyTree').tree('options').url = "page62301Qry.html?pid=" + node.id;    
		},
		onClick:function(node){  
			if(node.state){
				$('#MyTree').tree("expand",node.target); 
			}else{
				var id=node.id; 
				var $grid=$("#websiteTab");
				$grid.datagrid('options').queryParams={websiteCode:id};  
				$grid.datagrid("reload"); 
				$('#websiteCode').val(node.id);
				$grid.datagrid('uncheckAll');
				$('#timeTab').datagrid('uncheckAll');
				$('#timeTab').datagrid('loadData',{total:0,rows:[]});
				$('#timeTab').datagrid('clearData');
			}
		},
		onExpand:function(node){ 
			$('#MyTree').tree("select",node.target); 
		}              
	}).tree("loadData",[{"id":"00000000","text":"根","state":"closed"}]);	
	var node=$('#MyTree').tree("find","00000000");
	$('#MyTree').tree("expand",node.target); 
	$('#MyTree').tree("select",node.target); 
	return  $('#MyTree');
}

function createOperTable(){
	$("#websiteTab").datagrid({
		width:$('#form-container').width()-15,
		height:200,
		fitColumns: true,
		idField:'appobranchid',
		singleSelect:true,
		pagination:true,
		rownumbers:true,
		queryParams:{},
		url:'<%= _contexPath %>/webapi62304.json',
		columns:[[
			{field:'op',title:'删除标记',width:80,formatter:function(v,row){
				return "<input type='radio' value='"+row["appobranchid"]+"' name='chk_appobranchid'>"
			}}, 
			{field:'appobusiid',title:'业务类型',width:100,formatter:function(value,row,index) {	
				return $('#appobusiid option[value='+value+']').text(); 
			}},   
			{field:'maxdays',title:'最长可预约天数',width:100},
			{field:'appotemplateid',title:'预约时段模版',width:100,formatter:function(value,row,index) {
				
				return $('#appotemplateid option[value='+value+']').text(); 
			}},
			{field:'begindate',title:'预约业务启用日期',width:100},
			{field:'freeuse1',title:'当天是否可预约',width:100,formatter:function(value,row,index) {
				if('1' == value){
					return '是';
				}else{
					return '否';
				}
			}},
			{field:'freeuse2',title:'提前几个小时预约',width:100}
		]],
		toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
		    addInfo();
		}
		},{
		id:'btnupd',
		text:'修改',
		iconCls:'icon-edit', 
		handler:function(){
		    editInfo();
			}
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		    delInfo();}
		}],
		onClickRow:function(rowIndex, rowData){
			$('#appotemplateid1').val(rowData.appotemplateid);
			timeDetailData();
			$('#appobranchid1').val(rowData.appobranchid);
			var $grid=$("#timeTab");
			$grid.datagrid('options').queryParams={appobranchid:rowData.appobranchid};  
			$grid.datagrid("reload"); 
		}
	});
	return  $('#websiteTab');
}
 function createTimeTable(){
	$("#timeTab").datagrid({
		width:$('#form-container').width()-15,
		height:$(document).height()-240,
		fitColumns: true,
		idField:'appobrantimeid',
		singleSelect:true,
		queryParams:{},
	    rownumbers:true,
		url:'<%= _contexPath %>/webapi62404.json',
		columns:[[
			{field:'op',title:'删除标记',width:40,formatter:function(v,row){
				return "<input type='checkbox' value='"+row["appobrantimeid"]+"' name='chk_appobrantimeid'>"
			}}, 
			{field:'appotpldetailid',title:'预约时段模版明细',width:80,formatter:function(value,row,index) {
					for(var key in aa){
						if(aa[key]['appotpldetailid'] == value){
							return aa[key]['timeinterval'];
						}
					}
				
			}},   
			{field:'appocnt',title:'可预约人数上限',width:80}
		]],
		toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
		    addTimeInfo();
		}
		},{
		id:'btnupd',
		text:'修改',
		iconCls:'icon-edit', 
		handler:function(){
		    editTimeInfo();
		}
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		    delTimeInfo();}
		},'-',{
			
            text: '上移', 
            iconCls: 'icon-up', 
            handler: function () {
                MoveUp();}
        },{
            text: '下移', 
            iconCls: 'icon-down', 
            handler: function () {
                MoveDown();}
        },{
            text: '保存顺序', 
            iconCls: 'icon-save', 
            handler: function () {
                saveSort();}
        }]
	});
	return  $('#timeTab');
}
 function saveSort(){
	 var param="[";
	 var  data = $("#timeTab").datagrid('getRows');
	 if(data==null||data.length==0){
		 alert("表格中无数据");
		 return;
	 }
	 for(var i=0;i<data.length;i++){
		var row = $('#timeTab').datagrid('getData').rows[i];;
		if(i!=0)param+=",";
		param+="{'appobrantimeid':"+row['appobrantimeid']+",'freeuse4':"+i+"}";
		
	 }
	 param+="]";
	  $.ajax( {
			type : 'POST',
			url : '<%= _contexPath %>/webapi623SaveSort.json?datalist='+param,
			dataType: 'json',
			//data:{'datalist':data},
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');							
         		//$('#websiteTab').datagrid('reload');    // reload the user data
				//$("#timeTab").datagrid("reload");
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
		}); 
 }
 function MoveUp() {
	    var row = $("#timeTab").datagrid('getSelected');
	    if(row==null){
	    	return;
	    }
	    var index = $("#timeTab").datagrid('getRowIndex', row);
	    mysort(index, 'up', 'timeTab');
	     
	}
	//下移
	function MoveDown() {
	    var row = $("#timeTab").datagrid('getSelected');
	    if(row==null){
	    	return;
	    }
	    var index = $("#timeTab").datagrid('getRowIndex', row);
	    mysort(index, 'down', 'timeTab');
	     
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
function addInfo(){
    if ($('#websiteCode').val()== null||$('#websiteCode').val()==""){
    	$.messager.alert('提示', "请先选择网点!", 'info' );
    }else{
		$('#dlgwebsite').dialog('open').dialog('setTitle','预约业务网点信息-增加');
		var website = $('#websiteCode').val();
		$('#fmwebsite').form('clear');
		$('#websiteCode').val(website);
		url = '<%= _contexPath %>/webapi62301.json';
		$('#timeset').hide();
	}
}

function editInfo(){
    var rows = $('#websiteTab').datagrid('getSelections');
    if (rows.length != 1){
    	$.messager.alert('提示', "请选择一条记录进行修改!", 'info' );
    }else{
		$('#dlgwebsite').dialog('open').dialog('setTitle','预约业务网点信息-修改');
		//$('#fmwebsite').form('clear');
        $('#fmwebsite').form('load',rows[0]);
        url = '<%= _contexPath %>/webapi62303.json';
        if($('#freeuse1').val()=='0'){
        	$('#timeset').hide();
        }
    }
}
function saveInfo(){
	//校验
	if (!$("#fmwebsite").form("validate")) return;
	var arr = $('#fmwebsite').serializeArray();
	if($('#freeuse1').val()=='1'){
		if($('#freeuse2').val()==''||$('#freeuse2').val()==null){
			alert('请设置提前几个小时预约');
			return;
		}
	}
	$.ajax({
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlgwebsite').dialog('close');        // close the dialog
            $('#websiteTab').datagrid('reload'); 			// reload the user data
			$('#timeTab').datagrid('reload'); 
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}

function delInfo(){
    var $checkboxs = $('input:radio:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除,删除的同时会将时段模版下的所有时段明细删除?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={appobranchid:para}
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi62302.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#websiteTab').datagrid('reload');    // reload the user data
						$("#timeTab").datagrid("reload");
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
function addTimeInfo(){
	var rows = $('#websiteTab').datagrid('getSelections');
    if (rows.length != 1){
    	$.messager.alert('提示', "请先选择一条时段模版信息，才能在时段模版信息下新建时段明细信息!", 'info' );
    }else{
		$('#dlgtime').dialog('open').dialog('setTitle','预约业务网点时段信息-增加');
		var appotemplateid = $('#appotemplateid1').val();
		var appobranchid = $('#appobranchid1').val();
		var appobrantimeid = $('#appobrantimeid').val();
		$('#fmtime').form('clear');
		$('#appobranchid1').val(appobranchid);
		$('#appotemplateid1').val(appotemplateid);
		$('#appobrantimeid').val(appobrantimeid);
		url = '<%= _contexPath %>/webapi62401.json';
		timeDetailData();
	}
}

function editTimeInfo(){
    var rows = $('#timeTab').datagrid('getSelections');
    if (rows.length != 1){
    	$.messager.alert('提示', "请选择一条记录进行修改!", 'info' );
    }else{
		$.ajax( {
		type : 'POST',
		url : '<%= _contexPath %>/page62206.json',
		dataType: 'json',
		data:{'appotemplateid':$('#appotemplateid1').val()},
		success : function(data) {
			$('#appotpldetailid').combobox('clear');
			$('#appotpldetailid').combobox('loadData',data.result);
			
			$('#dlgtime').dialog('open').dialog('setTitle','预约业务网点时段信息-修改');
			$('#fmtime').form('load',rows[0]);
			url = '<%= _contexPath %>/webapi62403.json';
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	});
		
    }
}
function saveTimeInfo(){
	//校验
	if (!$("#fmtime").form("validate")) return;
	var arr = $('#fmtime').serializeArray();
	for(var key in arr){
		for(var key1 in arr[key]){
			if(arr[key][key1] == 'appobranchid1'){
				arr[key][key1] ='appobranchid';
			}else if(arr[key][key1] == 'appotemplateid1'){
				arr[key][key1] ='appotemplateid';
			}
		}
	}
	$.ajax({
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlgtime').dialog('close');        // close the dialog
            $('#timeTab').datagrid('reload');    // reload the user data
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}

function delTimeInfo(){
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={appobrantimeid:para}
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi62402.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');	
						$("#timeTab").datagrid("reload");
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
var aa;
function timeDetailData(){
	var map = {};
	if($('#appotemplateid1').val()!=null&&$('#appotemplateid1').val()!=""){
		map = {'appotemplateid':$('#appotemplateid1').val()};
	}
	$.ajax( {
		type : 'POST',
		url : '<%= _contexPath %>/page62206.json',
		dataType: 'json',
		data:map,
		success : function(data) {
			$('#appotpldetailid').combobox('clear');
			$('#appotpldetailid').combobox('loadData',data.result);
			aa = data.result;
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	});
}
$(function(){ 
	var $grid=createOperTable();
	var $grid1=createTimeTable();
	createTree();
	timeDetailData();
	//设置页面结构高度宽度
	$('#layout').height($(document).height()-30);
	$('#tree-container').height($(document).height()-52).width($('#tree-container').width()-5);
	$('#form-container').height($(document).height()-45);
	$('#freeuse1').change(function(){ 
		var p1=$(this).children('option:selected').val();//这就是selected的值 
		if(p1=='0'){
			$("#timeset").hide(); 
		}else{
			$("#timeset").show();
		}
	}) 
});
//]]></script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="预约业务网点及时段管理"> 
	<div id="tree-container" data-options="region:'west',split:false,border:true">
		<div id="MyTree"></div>
	</div>
	<div id="form-container" data-options="region:'center',border:false">
			<table id="websiteTab"></table>
			<table id="timeTab"></table>
	 </div>
	 <div id="dlgwebsite" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">预约业务网点配置</div>
        <form id="fmwebsite" class="dlg-form" method="post" novalidate="novalidate">
			<table class="websitecontainer">
			<tr id="tr_template">
				<td><label for="appobusiid" >预约业务：</label></td>
				<td><select id="appobusiid" name="appobusiid" panelHeight="auto">	        	
							<%
								String appobusiidOptions = "";
								List<Mi620> ary3=(List<Mi620>) request.getAttribute("mi620list");
								for(int i=0;i<ary3.size();i++){
									Mi620 mi620=ary3.get(i);
									String itemid=mi620.getAppobusiid();
									String itemval=mi620.getAppobusiname();
									appobusiidOptions = (new StringBuilder(String.valueOf(appobusiidOptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(appobusiidOptions);
							%>
						</select>
				</td>
			</tr>
			<tr>
				<td><label for="maxdays" >最长可预约天数：</label></td>
                <td><input type="text" id="maxdays" name="maxdays" required="true" class="easyui-numberbox" value="" data-options="min:1,max:200"></td>
			</tr>
			<tr id="tr_template">
				<td><label for="appotemplateid" >预约时段模板：</label></td>
				<td><select id="appotemplateid" name="appotemplateid" panelHeight="auto">	        	
							<%
								String templateOptions = "";
								List<Mi621> ary4=(List<Mi621>) request.getAttribute("mi621list");
								for(int i=0;i<ary4.size();i++){
									Mi621 mi621=ary4.get(i);
									String itemid=mi621.getAppotemplateid();
									String itemval=mi621.getTemplatename();
									templateOptions = (new StringBuilder(String.valueOf(templateOptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(templateOptions);
							%>
						</select>
				</td></tr>
			<tr>
				<td><label for="begindate" >预约业务启用日起：</label></td>
                <td><input type="text" id="begindate" name="begindate" class="easyui-datebox" validType="date"></td>
			</tr>
			<tr >
				<td><label for="freeuse1" >当天能否预约：</label></td>
                <td><select id="freeuse1" name="freeuse1" panelHeight="auto" >
                	<option value="0" selected>否</option>
                	<option value="1">是</option>
                </select>
                </td>
			</tr>
			<tr id='timeset'>
				<td><label for="freeuse2" >提前几个小时预约：</label></td>
                <td><input type="text" id="freeuse2" name="freeuse2" class="easyui-numberbox" value="" data-options="min:1,max:200" value="2">
                </td>
			</tr>
			</table>
			<input type='hidden' id="appobranchid" name="appobranchid" />
			<input type='hidden' id="websiteCode" name="websiteCode" />
        </form>
		<div id="dlgwebsite-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInfo()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgwebsite').dialog('close')">取消</a>
		</div>
    </div>
	<div id="dlgtime" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">预约业务网点时段配置</div>
        <form id="fmtime" class="dlg-form" method="post" novalidate="novalidate">
			<table class="timecontainer">
			<tr id="tr_detail">
				<td><label for="appotpldetailid" >预约时段明细：</label></td>
				<td><input id="appotpldetailid" name="appotpldetailid" class="easyui-combobox" data-options="valueField: 'appotpldetailid',	textField: 'timeinterval'" />
				</td>
			</tr>
			<tr>
				<td><label for="appocnt" >可预约人数上线：</label></td>
                <td><input type="text" id="appocnt" name="appocnt" required="true" class="easyui-numberbox" value="" data-options="min:1,max:200"></td>
			</tr>
			</table>
			<input type='hidden' id="appotemplateid1" name="appotemplateid1" value=""/>
			<input type='hidden' id="appobranchid1" name="appobranchid1" value=""/>
			<input type='hidden' id="appobrantimeid" name="appobrantimeid" value=""/>
        </form>
		<div id="dlgtime-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveTimeInfo()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgtime').dialog('close')">取消</a>
		</div>
    </div>
</div> 
</body>
</html>