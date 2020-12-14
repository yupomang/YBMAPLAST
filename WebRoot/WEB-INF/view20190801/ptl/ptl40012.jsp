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
<title>推送消息通讯参数配置管理</title>
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
    url:'ptl40012Query.json',
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
      $('#detailInfoarea').panel('setTitle','修改推送消息通讯参数配置信息');
      document.forms[0].opetype.value="mod";
    },
    columns:[[
     {field:'op',title:'操作',width:50,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["centerid"]+"' name='chk_centerid'>"
     }}, 
     {field:'centerid',title:'中心ID',width:90},   
     {field:'centername',title:'中心名称',width:150},
     {field:'certificatename',title:'IOC证书名',width:150},
     {field:'certificatepassword',title:'证书密码',width:120},
     {field:'apikey',title:'百度API_KEY',width:120},
     {field:'secritkey',title:'百度SECRIT_KEY',width:120},
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
		    	$('#detailInfoarea').panel('setTitle','添加推送消息通讯参数配置信息');
			}
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
				var mi012data=[];  
		       	$("[name='chk_centerid']:checked").each(function(i){
					mi012data.push($.trim($(this).val()));
		       	});
		       	if(mi012data.length==0){
					$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				}else{
				$.messager.confirm('提示','确认是否删除?',function(r){
					if (r){
						var map={centerid:mi012data.join(",")}
		       			$.ajax({	 
							type : "POST",
							url : "ptl40012Del.json",
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
      
     var mi012arry = $('#detailInfoForm').serializeArray(); 
     var url=null;     
     if('mod' == $('#opetype').val()){
         url="ptl40012Mod.json";
     }else{
         url="ptl40012Add.json";
     }     
     var tgbz=$("#detailInfoForm").form("validate");      
     if(tgbz){          
          $.ajax({	 
					type : "POST",
					url : url,
					dataType: "json",
					data:mi012arry,
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
//推送消息表单标题
$('#detailInfoarea').panel({
	title:'添加推送消息通讯参数配置信息', 
	height:254
}).closest('.panel').css('padding-top','5px');
}); 
</script>
</head> 
<body style="margin:0px;"> 
<div id="layout" class="easyui-panel easyui-layout" title="推送消息通讯参数配置管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 中心列表 开始-->     
		<table id="centerTab"></table>
		<!-- 中心列表 结束-->
	</div>
    <div id="form-container" data-options="region:'center',border:false">
    	<!-- 推送消息通讯参数配置信息列表 开始-->
    	<table id="detailInfoTab"></table>
    	<!-- 推送消息通讯参数配置信息列表 结束-->
    	<div id="detailInfoarea" class="easyui-panel">
       		<form id="detailInfoForm" class="dlg-form" novalidate="novalidate">
        		<table class="container">
					<col width="30%" /><col width="70%" />
         			<tr>
						<td><label for="opetype">中心ID：</label></td>
         				<td><input type="hidden" id="opetype" name="opetype"/><input type="text" name="centerid" required='true' class="easyui-validatebox"  maxlength="8"/></td>
         			</tr>
         			<tr>
						<td><label for="certificatename">IOC证书名：</label></td>
         				<td><input id="certificatename" type="text" name="certificatename" required='true' class="easyui-validatebox"  maxlength="50" size="60"/> </td>
         			</tr>
         			<tr>
						<td><label for="certificatepassword">证书密码：</label></td>
         				<td><input id="certificatepassword" type="text" name="certificatepassword" required='true' class="easyui-validatebox"  maxlength="50" size="60"/> </td>
         			</tr>
          			<tr>
						<td><label for="apikey">百度API_KEY：</label></td>
          				<td><input id="apikey" type="text" name="apikey" required='true' class="easyui-validatebox"  maxlength="50" size="60"/></td>          
         			</tr>
         			<tr>
						<td><label for="secritkey">百度SECRIT_KEY：</label></td>
          				<td><input id="secritkey" type="text" name="secritkey" required='true' class="easyui-validatebox"  maxlength="50" size="60"/></td>          
         			</tr>
        	</table>
        	<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
        </form> 
    </div>
	</div>
</div> 
</body>
</html>