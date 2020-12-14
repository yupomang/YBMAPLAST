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
<title>通讯方式管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container {width: 25%; padding:5px;}
#form-container {width: 75%; padding-top:5px; padding-left:0px; padding-right:5px; padding-bottom:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">
$(function() {

 $("#centerTab").datagrid({
    height:$(document).height()-40,
    fitColumns: true,
	idField:'centerid',
	singleSelect:true,
	onClickRow:function(rowIndex, rowData){
	          var $grid=$("#detailInfoTab");
	          $grid.datagrid('options').queryParams={centerid:rowData.centerid};  
			  $grid.datagrid("reload"); 
			  detailInfoForm.reset();
	},
    columns:[[
     {field:'centerid',title:'中心代码',width:100},   
     {field:'centername',title:'中心名称',width:200}    
    ]]
 }).datagrid("loadData",<%
     if(request.getAttribute("clist")==null){
        out.print("{}");
     }else{
        out.println(request.getAttribute("clist"));
     } 
 %>).datagrid("selectRecord",'<%=UserContext.getInstance().getCenterid()%>');
 
 $("#detailInfoTab").datagrid({
    width:$('#form-container').width(),
    height:$(document).height()-300,
    idField:'centerid',
	singleSelect:true,
    url:'ptl40007Query.json',
    onClickRow:function(rowIndex, rowData){
      detailInfoForm.reset();  
      $("[name='centerid']").attr("readonly",true);
      for(var key in rowData ){
        if(detailInfoForm[key]){
           if(rowData[key])
             detailInfoForm[key].value=rowData[key];
           else
             detailInfoForm[key].value="";
        }   
      }      
      if(rowData["stat"]){
         $("[name='stat'][value='"+rowData["stat"]+"']").attr("checked",true);
      }
      $('#detailInfoarea').panel('setTitle','修改通讯信息');
      document.forms[0].opetype.value="mod";
    },
    columns:[[
     {field:'op',title:'操作',width:50,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["centerid"]+"' name='chk_centerid'>"
     }}, 
     {field:'centerid',title:'中心ID',width:100},   
     {field:'centername',title:'中心名称',width:150},
     {field:'classname',title:'通讯接口类',width:150},
     {field:'url',title:'HTTP通讯URL',width:340},
    ]],
    toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
			var row = $('#centerTab').datagrid('getSelected');
			if(row == null || row.centerid == null){
				$.messager.alert('提示', "至少选中一条中心信息进行维护！", 'info');
			}else{
		    	var centerid=row.centerid
		    	document.forms[0].reset();
		    	document.forms[0].centerid.value=centerid;
		    	document.forms[0].opetype.value="add";
		    	$("[name='centerid']").attr("readonly",true);
		    	$('#detailInfoarea').panel('setTitle','添加通讯信息');
			}
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
				var mi011data=[];  
		       	$("[name='chk_centerid']:checked").each(function(i){
					mi011data.push($.trim($(this).val()));
		       	});
		       	if(mi011data.length==0){
					$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				}else{
				$.messager.confirm('提示','确认是否删除?',function(r){
					if (r){
						var map={centerid:mi011data.join(",")}
		       			$.ajax({	 
							type : "POST",
							url : "ptl40007Del.json",
							dataType: "json",
							data:map,
							success :function(data) {
								$.messager.alert('提示', data.msg, 'info');	
								if (SUCCESS_CODE == data.recode){
									$("#detailInfoTab").datagrid("reload");   
									detailInfoForm.reset();
								}			   		   
							},
							error :function(){
								$.messager.alert('错误','系统异常','error');							
							}
			   			});
					}
				})
			}
		}
	}]
 });

 {
  var $grid=$("#detailInfoTab");
  $grid.datagrid('options').queryParams={centerid:'<%=UserContext.getInstance().getCenterid()%>'};  
  $grid.datagrid("reload"); 
 }
 $("#btnsave").click(function(){  
      
     var mi011arry = $('#detailInfoForm').serializeArray(); 
     var url=null;     
     if('mod' == $('#opetype').val()){
         url="ptl40007Mod.json";
     }else{
         url="ptl40007Add.json";
     }     
     var tgbz=$("#detailInfoForm").form("validate");      
     if(tgbz){          
          $.ajax({	 
					type : "POST",
					url : url,
					dataType: "json",
					data:mi011arry,
					success : function(data) {	
                	if(SUCCESS_CODE == data.recode) {
                		$.messager.alert('提示', data.msg, 'info');
                		$("#detailInfoTab").datagrid("reload");	
                		detailInfoForm.reset();
                	}else{
                		$.messager.alert('错误',data.msg,'error');		
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
$('#detailInfoarea').panel({
	title:'添加通讯信息', 
	height:254
}).closest('.panel').css('padding-top','5px');
}); 
</script>
</head> 
<body style="margin:0px;"> 
<div id="layout" class="easyui-panel easyui-layout" title="通讯方式管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 中心列表 开始-->     
		<table id="centerTab"></table>
		<!-- 中心列表 结束-->
	</div>
    <div id="form-container" data-options="region:'center',border:false">
    	<!-- 通讯信息列表 开始-->
    	<table id="detailInfoTab"></table>
    	<!-- 通讯信息列表 结束-->
    	<div id="detailInfoarea" class="easyui-panel">
       		<form id="detailInfoForm" class="dlg-form" novalidate="novalidate">
        		<table class="container">
					<col width="30%" /><col width="70%" />
         			<tr>
         				<td><label for="opetype">中心ID：</label></td>
         				<td><input type="hidden" id="opetype" name="opetype"/><input type="text" name="centerid" required='true' class="easyui-validatebox"  maxlength=8/></td>
         			</tr>
         			<tr>
         				<td><label for="classname">通讯接口类名：</label></td>
         				<td><input id="classname" type="text" name="classname" required='true' class="easyui-validatebox"  maxlength=200 size=30/> </td>
         			</tr>
          			<tr>
          				<td><label for="url">通讯URL：</label></td>
          				<td> 
          					<input id="url" type="text" name="url" required='true' class="easyui-validatebox"  maxlength=200 size=60/> 
          				</td>          
         			</tr>
        	</table>
        	<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
        </form> 
    </div>
	</div>
</div> 
</body>
</html>