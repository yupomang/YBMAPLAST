<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
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
<title>预约业务时间模版</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container {width: 28%; padding:5px;}
#form-container {width: 70%; padding-top:5px; padding-left:0px; padding-right:5px; padding-bottom:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">
var url;
$(function() {
  $("#templateTab").datagrid({
	height:$(document).height()-40,
    fitColumns: true,
	singleSelect:true,
	method:'post',
	//pagination:true,
	//rownumbers:true,
	queryParams:{'centerid':"<%=user.getCenterid()%>"},
    url:'<%= _contexPath %>/webapi62104.json',
    columns:[[
     {field:'id',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="radio" name="templateradio" value="' + row.appotemplateid + '"/>';
	 }}, 
     {field:'appotemplateid',title:'预约时段模版编号',width:200,align:'center'},   
     {field:'templatename',title:'预约时段模版名称',width:200,align:'center'}
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
		//onLoadSuccess:function(data){ 
		//	if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
       // },
		onClickRow:function(rowIndex, rowData){
	          var $grid=$("#detailTab");
	          $grid.datagrid('options').queryParams={appotemplateid:rowData.appotemplateid};  
			  $grid.datagrid("reload");
			  document.all.appotemplateid1.value=rowData.appotemplateid;
	}
 });
 
 $("#detailTab").datagrid({
	width:$('#form-container').width(),
    height:$(document).height()-200,
	singleSelect:true,
    url:'<%= _contexPath %>/webapi62204.json',
	queryParams:{'appotemplateid':$('#appotemplateid1').val()},
    onClickRow:function(rowIndex, rowData){
	  var value =$('#appotemplateid1').val();
	  $('#detailForm').form('clear').form('load',rowData);
	  $('#appotemplateid1').val(value);
	  $("#tr_detail").show();
	  $('#operarea').panel('setTitle','修改预约时段明细信息');
	  url="<%= _contexPath %>/webapi62203.json";
    },
    rownumbers:true,
    columns:[[
     {field:'op',title:'操作',width:100,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["appotpldetailid"]+"' name='chk_appotpldetailid'>"
     }}, 
     {field:'appotpldetailid',title:'预约时段明细编号',width:100},   
     {field:'timeinterval',title:'预约时段明细名称',width:200}
    ]],
    toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
			var appotemplateid = $('#appotemplateid1').val();
		    document.forms[0].reset();
			$('#appotemplateid1').val(appotemplateid);
			$('#operarea').panel('setTitle','添加预约时段明细信息');
			$("#tr_detail").hide();    
			url = "<%= _contexPath %>/webapi62201.json"; 
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		       var mi622data=[];  
		       $("[name='chk_appotpldetailid']:checked").each(function(i){
		          mi622data.push($.trim($(this).val()));
		       });
		       if(mi622data.length > 0){
					$.messager.confirm('提示','确认是否删除?',function(r){
						if (r) {
							var map={appotpldetailid:mi622data.join(",")}	      
		       				$.ajax({
								type : "POST",
								url : "<%= _contexPath %>/webapi62202.json",
								dataType: "json",
								data:map,
								success :function(data) {
									if(SUCCESS_CODE == data.recode){
										$("#detailTab").datagrid("reload");
					    				$('#detailTab').datagrid('unselectAll');
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
		       }else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
		   }
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
 function saveSort(){
	 var param="[";
	 var  data = $("#detailTab").datagrid('getRows');
	 if(data==null||data.length==0){
		 alert("表格中无数据");
		 return;
	 }
	 for(var i=0;i<data.length;i++){
		var row = $('#detailTab').datagrid('getData').rows[i];;
		if(i!=0)param+=",";
		param+="{'appotpldetailid':"+row['appotpldetailid']+",'freeuse4':"+i+"}";
		
	 }
	 param+="]";
	  $.ajax( {
			type : 'POST',
			url : '<%= _contexPath %>/webapi622SaveSort.json?datalist='+param,
			dataType: 'json',
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
		}); 
 }
 function MoveUp() {
	    var row = $("#detailTab").datagrid('getSelected');
	    if(row==null){
	    	return;
	    }
	    var index = $("#detailTab").datagrid('getRowIndex', row);
	    mysort(index, 'up', 'detailTab');
	     
	}
	//下移
	function MoveDown() {
	    var row = $("#detailTab").datagrid('getSelected');
	    if(row==null){
	    	return;
	    }
	    var index = $("#detailTab").datagrid('getRowIndex', row);
	    mysort(index, 'down', 'detailTab');
	     
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
	//点击保存
	$("#btnsave").click(function(){
		var mi622arry = $('#detailForm').serializeArray(); 
		var timeval=$('#timeinterval').val();
		if(!timeval.match(/\d+/g)){
			alert("配置时段描述请按照建议规则配置，如：8:30-9:00");
			return;
		}
		if(timeval.indexOf('-')==-1){
			alert("配置时段描述请按照建议规则配置，如：8:30-9:00");
			return;
		}
		if(timeval.indexOf(':')==-1){
			alert("配置时段描述请按照建议规则配置，如：8:30-9:00");
			return;
		}
		for(var key  in mi622arry){
			var obj = mi622arry[key];
			for(var key1 in obj){
				if(obj[key1] == "appotemplateid1"){
					obj[key1] = "appotemplateid";
				}
			}
		}  
		var tgbz = $("#detailForm").form("validate");      
		if (tgbz){          
			$.ajax({	 
				type: "POST",
				url: url,
				dataType: "json",
				data: mi622arry,
				success:function (data) {
					if(SUCCESS_CODE == data.recode){
						$("#detailTab").datagrid("reload");
						$('#detailTab').datagrid('unselectAll');
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
	
	$('#operarea').panel({
		title:'添加预约时段明细信息', 
		height:154
	}).closest('.panel').css('padding-top','5px');
	
}); 

function addInfo(){
    $('#dlg').dialog('open').dialog('setTitle','时段模版-增加');
    $('#fm').form('clear');
	$("#tr_template").hide();
    url = '<%= _contexPath %>/webapi62101.json';
}

function editInfo(){
    var rows = $('#templateTab').datagrid('getSelections');
	$('#appotemplateid').attr("readonly",true);
    if (rows.length != 1){
    	$.messager.alert('提示', "请选择一条记录进行修改!", 'info' );
    }else{
		$('#dlg').dialog('open').dialog('setTitle','时段模版-修改');
        $('#fm').form('load',rows[0]);
		$("#tr_template").show();
        url = '<%= _contexPath %>/webapi62103.json';
    }
}

function saveInfo(){
	//校验
	if (!$("#fm").form("validate")) return;
	var arr = $('#fm').serializeArray();
	$.ajax({
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlg').dialog('close');        // close the dialog
            $('#templateTab').datagrid('reload');    // reload the user data
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}

function delInfo(){
    var $checkboxs = $('input:radio:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除,删除模版同时会将模版下的所有时段明细删除?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={appotemplateid:para}
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi62102.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#templateTab').datagrid('reload');    // reload the user data
						$("#detailTab").datagrid("reload");
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

</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="预约时段管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 模版列表 开始-->     
		<table id="templateTab"></table>
		<!-- 模版列表 结束-->
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<!-- 明细列表 开始-->     
		<table id="detailTab"></table>
		<!-- 明细列表 结束-->
		<div id="operarea" class="easyui-panel"> 
			<form id="detailForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="appotemplateid1" name="appotemplateid1" value=""/>
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr id="tr_detail">
					<td><label for="appotpldetailid">预约时段明细编号：</label></td>
					<td><input type='text' id="appotpldetailid" name="appotpldetailid" readonly="true" class="easyui-validatebox"  maxlength=20/></td>
				</tr>
				<tr>
					<td><label for="timeinterval">时段描述：</label></td>
					<td><input type='text' id="timeinterval" name="timeinterval" required='true' class="easyui-validatebox" maxlength=30 />&nbsp;&nbsp;配置规则:开始具体时间点-结束具体时间点，如:8:30-9:00 </td>
				</tr>
			</table>
			<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
			</form> 
		</div>
	</div>	
</div> 
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">预约时段模板</div>
        <form id="fm" class="dlg-form" method="post" novalidate="novalidate">
			<table class="dlgcontainer">
			<tr id="tr_template">
				<td><label for="appotemplateid" >预约时段模板编号：</label></td>
				<td><input type="text" id="appotemplateid" name="appotemplateid" readonly="true" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,20]" class="easyui-validatebox" readonly="true"></td>
			</tr>
			<tr>
				<td><label for="templatename" >预约时段模板名称：</label></td>
                <td><input type="text" id="templatename" name="templatename" required="true" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" class="easyui-validatebox"></td>
			</tr>
			</table>
			
        </form>
    </div>
	<div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInfo()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
</body>
</html>