<%@ page language="java" contentType="text/html; charset=utf-8" %>
<% 
  /*
     文件名：ptl40001.jsp
     作者： 韩占远
     日期：2013-10-06 
     作用：操作员管理
  */
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>操作员管理</title>
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
$(function() {
 //$("#p").height($(document).height()-30);
 
  $("[name='roleid']").each(function(){
       $(this).attr("checked",false)
  });
 $("#centerTab").datagrid({
    //width:300,
    height:$(document).height()-40,
    fitColumns: true,
	idField:'centerid',
	singleSelect:true,
	onClickRow:function(rowIndex, rowData){
	          var $grid=$("#operTab");
	          $grid.datagrid('options').queryParams={centerid:rowData.centerid};  
			  $grid.datagrid("reload");
			  document.all.centerid.value=rowData.centerid;
			  getRoleList(rowData.centerid);
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
 
 getRoleList('<%=UserContext.getInstance().getCenterid()%>');
 //$("#operarea").height(257).width($(document).width()-314);
 
 
 $("#operTab").datagrid({
    //width:$(document).width()-312,
	width:$('#form-container').width(),
    height:$(document).height()-300,
    idField:'centerid',
	singleSelect:true,
    url:'ptl40001.json',
    onClickRow:function(rowIndex, rowData){
      $("[name='loginid']").attr("readonly",true);
	  /*
      operForm.reset();  
      for(var key in rowData ){
        if(operForm[key]){
           if(rowData[key])
             operForm[key].value=rowData[key];
           else
             operForm[key].value="";
        }   
      }
		*/
		$('#operForm').form('clear').form('load',rowData);
      
      if(rowData["stat"]){
         $("[name='stat'][value='"+rowData["stat"]+"']").attr("checked",true);
      } 
      
      var roleobj=document.getElementsByName("role");
      for(var i=0;i<roleobj.length;i++){
          roleobj[i].checked=false; 
          for(var j=0;j<rowData.roleList.length;j++){ 
             if(roleobj[i].value==rowData.roleList[j].roleid){ 
                 roleobj[i].checked=true;
             }
          }  
      } 
	  $('#operarea').panel('setTitle','修改操作员信息');
    },
    columns:[[
     {field:'op',title:'操作',width:100,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["loginid"]+"' name='chk_loginid'>"
     }}, 
     {field:'loginid',title:'操作员代码',width:100},   
     {field:'opername',title:'操作员名称',width:200},
     {field:'phone',title:'电话',width:200},
     {field:'stat',title:'状态',width:200,formatter:function(v){
        var json={"0":"正常","1":"停用"};
        if(json[v]){
           return json[v]
        }else{
           return v;
        }
     }},
     {field:'createdate',title:'创建日期',width:200},
     {field:'createtime',title:'创建时间',width:200},
     {field:'modidate',title:'修改日期',width:200},
     {field:'moditime',title:'修改时间',width:200}
    ]],
    toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
		    //var centerid=document.forms[0].centerid.value;
			var centerid = $('#centerid').val();
		    document.forms[0].reset();
		    //document.forms[0].centerid.value=centerid;
			$('#centerid').val(centerid);
		    $("[name='loginid']").attr("readonly",false);
			$('#operarea').panel('setTitle','添加操作员信息');
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		       var mi002data=[];  
		       $("[name='chk_loginid']:checked").each(function(i){
		          mi002data.push($.trim($(this).val()));
		       });
		       if(mi002data.length > 0){
					$.messager.confirm('提示','确认是否删除?',function(r){
						if (r) {
							var map={loginid:mi002data.join(",")}	      
		       				$.ajax({
								type : "POST",
								url : "ptl40001Del.json",
								dataType: "json",
								data:map,
								success :function(data) {
									if(SUCCESS_CODE == data.recode){
										$("#operTab").datagrid("reload");
					    				$('#operTab').datagrid('unselectAll');
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
		}]
 });

 {
  var $grid=$("#operTab");
  $grid.datagrid('options').queryParams={centerid:'<%=UserContext.getInstance().getCenterid()%>'};  
  $grid.datagrid("reload"); 
 }
 
	//点击保存
	$("#btnsave").click(function(){
		var mi002arry = $('#operForm').serializeArray(); 
		var url=null;     
		if ($(operForm.loginid).attr("readonly")) url="ptl40001Upd.json";
		else url = "ptl40001Add.json";  
		var tgbz = $("#operForm").form("validate");      
		if (tgbz){          
			$.ajax({	 
				type: "POST",
				url: url,
				dataType: "json",
				data: mi002arry,
				success:function (data) {
					if(SUCCESS_CODE == data.recode){
						$("#operTab").datagrid("reload");
						$('#operTab').datagrid('unselectAll');
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
	$('#operarea').panel({
		title:'添加操作员信息', 
		height:254
	}).closest('.panel').css('padding-top','5px');
	
}); 
function getRoleList(centerid){
	$.ajax({
			type: "POST",
			url: 'ptl40001RoleList.json?centerid='+centerid,
			dataType: "json",
			data: '',
			success:function (data) {
				if(SUCCESS_CODE == data.recode){
					$('#rolelist li').remove();
					$.each(data.rolelist, function (index, ele){
						$('#rolelist').append('<li><input type="checkbox" id="role-'+index+'" name="role" value="'+ele.roleid.trim()+'" ><label for="role-'+index+'">'+ele.rolename+' </label></li>');
					});
				}
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
	});
}
</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="操作员管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 中心列表 开始-->     
		<table id="centerTab"></table>
		<!-- 中心列表 结束-->
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<!-- 操作员列表 开始-->     
		<table id="operTab"></table>
		<!-- 操作员列表 结束-->
		<div id="operarea" class="easyui-panel"> 
			<form id="operForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="centerid" name="centerid" value=""/>
			<input type='hidden' id="password"  name="password" />
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label for="loginid">操作代码：</label></td>
					<td><input type='text' id="loginid" name="loginid" required='true' class="easyui-validatebox"  maxlength=8/></td>
				</tr>
				<tr>
					<td><label for="opername">操作员名称：</label></td>
					<td><input type='text' id="opername" name="opername" required='true' class="easyui-validatebox" maxlength=30 /> </td>
				</tr>
				<tr>
					<td><label for="phone">联系电话：</label></td>
					<td><input type='text' id="phone" name="phone" required='true' class="easyui-validatebox"  maxlength=15/> </td>
				</tr>
				<tr>
					<td><label >状态：</label></td>
					<td>  	
						<ul class="multivalue horizontal">
							<li><input id="stat-0" name="stat" type="radio" value="0"/><label for="stat-0">正常</label></li>
							<li><input id="stat-1" name="stat" type="radio" value="1"/><label for="stat-1">停用</label></li>
						</ul>
					</td>          
				</tr>
				<tr>
					<td><label>角色：</label></td> 
					<td>
						<ul class="multivalue horizontal" id="rolelist">
						</ul>
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