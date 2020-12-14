<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>内容展现配置</title>
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
	var url = null;

	var str = <%=request.getAttribute("newsViewItemsList")%>;
	var itemId = str.rows[0].itemid;
	var newsViewItem = itemId;
  
	// 初始
	document.all.newsviewitemid.value = itemId;
  
    $("[name='columns']").each(function(){
       $(this).attr("checked",false)
  	});
  
 $("#newsViewItemsTab").datagrid({
    height:$(document).height()-40,
    fitColumns: true,
	singleSelect:true,
	idField:'itemid',
	onClickRow:function(rowIndex, rowData){
	          var $grid=$("#forumColumnsTab");
	          $grid.datagrid('options').queryParams={qry_newsviewitemid:rowData.itemid};  
			  $grid.datagrid("reload");
			  $('#forumColumnsTab').datagrid('unselectAll');
			  document.all.newsviewitemid.value=rowData.itemid;
			  getNewsconfigsList();
			  newsViewItem = rowData.itemid;
			  $('#forumColumnsarea').panel('setTitle','添加配置信息');
	},
    columns:[[
     {field:'itemid',title:'编号',hidden:true},   
     {field:'itemval',title:'内容展现项',width:240}
    ]]
 }).datagrid("loadData",<%
     if(request.getAttribute("newsViewItemsList")==null){
        out.print("{}");
     }else{
        out.println(request.getAttribute("newsViewItemsList"));
     } 
 %>).datagrid("selectRecord",itemId);
 
 getNewsconfigsList();
 
 $("#forumColumnsTab").datagrid({
	width:$('#form-container').width(),
    height:$(document).height()-300,
	idField:'newsviewitemid',
	singleSelect:true,
	queryParams:{qry_newsviewitemid:itemId},
    url:'webapi7070204.json',
    onClickRow:function(rowIndex, rowData){
			$('#forumColumnsForm').form('clear').form('load',rowData);
			//document.all.forumdicid.value=rowData.dicid;
      
      		var columnsobj=document.getElementsByName("columns");
      		for(var i=0;i<columnsobj.length;i++){
				columnsobj[i].checked=false; 
          		for(var j=0;j<rowData.newspapercolumnsList.length;j++){ 
					if(columnsobj[i].value==rowData.newspapercolumnsList[j].itemid){ 
						columnsobj[i].checked=true;
					}
				}  
			} 
	  		$('#forumColumnsarea').panel('setTitle','修改配置信息');
	  		url="webapi7070201.json"
    },
    columns:[[
     {field:'op',title:'操作',width:100,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["newsviewitemid"]+"' name='chk_newsviewitemid'>"
     }},  
     {field:'newspapercolumns',title:'配置项',width:470,formatter:function(value,row,index) {
			var columnsStr='';
			var columnsObj = row.newspapercolumnsList;
			for(var j=0;j<columnsObj.length;j++){
				if('' == columnsStr){
					columnsStr = $('#columnsList option[value='+columnsObj[j].itemid+']').text();
				}else{
					columnsStr = columnsStr + "，" + $('#columnsList option[value='+columnsObj[j].itemid+']').text();
				}
			}
			return columnsStr; 	
		}},
     {field:'newsviewitemid',title:'内容展现项id',hidden:true}
    ]],
    toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
      		$("[name='columns']").each(function(){
       			$(this).attr("checked",false)
  			});
  			$('#forumColumnsTab').datagrid('unselectAll');
			var newsviewitemid = $('#newsviewitemid').val();
			$('#newsviewitemid').val(newsviewitemid);
			$('#forumColumnsarea').panel('setTitle','添加配置信息');
			url = "webapi7070201.json";
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		       var mi707data=[];  
		       $("[name='chk_newsviewitemid']:checked").each(function(i){
		          mi707data.push($.trim($(this).val()));
		       });
		       if(mi707data.length > 0){
						$.messager.confirm('提示','确认是否删除?',function(r){
							if (r) {
								var map={newsviewitemid:newsViewItem}	      
		       					$.ajax({
									type : "POST",
									url : "webapi7070202.json",
									dataType: "json",
									data:map,
									success :function(data) {
										if(SUCCESS_CODE == data.recode){
											$("#forumColumnsTab").datagrid("reload");
					    					$('#forumColumnsTab').datagrid('unselectAll');
      										$("[name='columns']").each(function(){
       											$(this).attr("checked",false)
  											});
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
  var $grid=$("#forumColumnsTab");
  $grid.datagrid('options').queryParams={qry_newsviewitemid:itemId};  
  $grid.datagrid("reload"); 
 }
 
	//点击保存
	$("#btnsave").click(function(){
		var mi707arry = $('#forumColumnsForm').serializeArray();    
		var tgbz = $("#forumColumnsForm").form("validate");      
		if (tgbz){          
			$.ajax({	 
				type: "POST",
				url: url,
				dataType: "json",
				data: mi707arry,
				success:function (data) {
					if(SUCCESS_CODE == data.recode){
						$("#forumColumnsTab").datagrid("reload");
      					$("[name='columns']").each(function(){
       						$(this).attr("checked",false)
  						});
						
						
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
	//配置信息表单标题
	$('#forumColumnsarea').panel({
		title:'添加配置信息', 
		height:254
	}).closest('.panel').css('padding-top','5px');
	
}); 

function getNewsconfigsList(){
	$.ajax({
			type: "POST",
			url: 'webapi707NewsConfigsList.json',
			dataType: "json",
			data: '',
			success:function (data) {
				if(SUCCESS_CODE == data.recode){
					$('#newsconfigslist li').remove();
					$.each(data.newsconfigslist, function (index, ele){
						$('#newsconfigslist').append('<li><input type="checkbox" id="columns-'+index+'" name="columns" value="'+ele.itemid.trim()+'" ><label for="columns-'+index+'">'+ele.itemval+' </label></li>');
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
<div id="layout" class="easyui-panel easyui-layout" title="内容展现配置"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 内容展现项 开始-->     
		<table id="newsViewItemsTab"></table>
		<!-- 内容展现项  结束-->
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<!-- 内容展现项配置列表 开始-->     
		<table id="forumColumnsTab"></table>
		<!-- 内容展现项配置列表 结束-->
		<div id="forumColumnsarea" class="easyui-panel"> 
			<form id="forumColumnsForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="newsviewitemid" name="newsviewitemid" value=""/>
			<!-- <input type='hidden' id="forumdicid" name="forumdicid" value=""/> -->
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label >配置内容项：</label></td>
					<td>  	
						<ul class="multivalue horizontal" id="newsconfigslist">
						</ul>
					</td>          
				</tr>
			</table>
			<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
			</form> 
		</div>
	</div>	
</div> 
<div style="display:none">
	<select id="columnsList" name="columnsList" editable="false" class="easyui-combobox">				        	
	<%
		String options2 = "";
		List<Mi007> ary2=(List<Mi007>) request.getAttribute("columnsList");
		for(int i=0;i<ary2.size();i++){
			Mi007 mi007=ary2.get(i);
			String itemidTmp=mi007.getItemid();
			String itemvalTmp=mi007.getItemval();
			options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemidTmp).append("\">").append(itemvalTmp).append("</option>\n").toString();
		}
		out.println(options2);
	%>
	</select>
</div>
</body>
</html>