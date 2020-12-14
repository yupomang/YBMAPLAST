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
<title>版块栏目管理</title>
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

	var str = <%=request.getAttribute("newsItemIdList")%>;
	var itemId = str.rows[0].itemid;
	var newspapertimes = itemId;
	var publishflagVal = str.rows[0].publishflag;
  
	// 初始
	document.all.newsitemid.value = itemId;
  
    $("[name='forum']").each(function(){
       $(this).attr("checked",false)
  });
      $("[name='columns']").each(function(){
       $(this).attr("checked",false)
  });
  
 $("#newsItemidTab").datagrid({
    height:$(document).height()-40,
    fitColumns: true,
	singleSelect:true,
	idField:'itemid',
	onClickRow:function(rowIndex, rowData){
	          var $grid=$("#forumColumnsTab");
	          $grid.datagrid('options').queryParams={qry_newsitemid:rowData.itemid};  
			  $grid.datagrid("reload");
			  $('#forumColumnsTab').datagrid('unselectAll');
			  document.all.newsitemid.value=rowData.itemid;
			  getNewspaperforumList();
			  getNewspapercolumnsList();
			  newspapertimes = rowData.itemid;
			  publishflagVal = rowData.publishflag;
			  $('#forumColumnsarea').panel('setTitle','添加配置信息');
	},
    columns:[[
     {field:'itemid',title:'编号',hidden:true},   
     {field:'itemval',title:'期次名称',width:240},
     {field:'publishflag',title:'发布标志',hidden:true}    
    ]]
 }).datagrid("loadData",<%
     if(request.getAttribute("newsItemIdList")==null){
        out.print("{}");
     }else{
        out.println(request.getAttribute("newsItemIdList"));
     } 
 %>).datagrid("selectRecord",itemId);
 
 getNewspaperforumList();
 getNewspapercolumnsList();
 
 $("#forumColumnsTab").datagrid({
	width:$('#form-container').width(),
    height:$(document).height()-300,
	idField:'newsitemid',
	singleSelect:true,
	queryParams:{qry_newsitemid:itemId},
    url:'webapi70404.json',
    onClickRow:function(rowIndex, rowData){
    	if (publishflagVal==1){
    		$.messager.alert('提示', '对应该配置的期次已经发布，不能进行编辑操作，请确认后提交！', 'info' );
    	}else{
			$('#forumColumnsForm').form('clear').form('load',rowData);
			document.all.forumdicid.value=rowData.dicid;    
		  
      		if(rowData["newspaperforum"]){
         		$("[name='forum'][value='"+rowData["newspaperforum"]+"']").attr("checked",true);
      		} 
      
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
	  		url="webapi70403.json"
    	}
    },
    columns:[[
     {field:'op',title:'操作',width:100,formatter:function(v,row){
        return "<input type='checkbox' value='"+row["dicid"]+"' name='chk_dicid'>"
     }}, 
     {field:'newspaperforum',title:'版块',width:200,formatter:function(value,row,index) {	
				return $('#forumList option[value='+value+']').text(); 	
		}},   
     {field:'newspapercolumns',title:'栏目',width:470,formatter:function(value,row,index) {
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
     {field:'newsitemid',title:'期次编号',hidden:true},
     {field:'dicid',title:'序号',hidden:true}
    ]],
    toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
    		$("[name='forum']").each(function(){
       			$(this).attr("checked",false)
  			});
      		$("[name='columns']").each(function(){
       			$(this).attr("checked",false)
  			});
  			$('#forumColumnsTab').datagrid('unselectAll');
			var newsitemid = $('#newsitemid').val();
		    //document.forms[0].reset();
			$('#newsitemid').val(newsitemid);
			$('#forumColumnsarea').panel('setTitle','添加配置信息');
			url = "webapi70401.json";
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		       var mi704data=[];  
		       $("[name='chk_dicid']:checked").each(function(i){
		          mi704data.push($.trim($(this).val()));
		       });
		       if(mi704data.length > 0){
		       	    if (publishflagVal==1){
		       	    	$.messager.alert('提示', '对应该配置的期次已经发布，不能进行删除，请确认后提交！', 'info' );
		       	    }else{
						$.messager.confirm('提示','确认是否删除?',function(r){
							if (r) {
								var map={dicidList:mi704data.join(","),newspapertimes:newspapertimes}	      
		       					$.ajax({
									type : "POST",
									url : "webapi70402.json",
									dataType: "json",
									data:map,
									success :function(data) {
										if(SUCCESS_CODE == data.recode){
											$("#forumColumnsTab").datagrid("reload");
					    					$('#forumColumnsTab').datagrid('unselectAll');
					    					$("[name='forum']").each(function(){
       											$(this).attr("checked",false)
  											});
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
		       	    }
		       }else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
		   }
		}]
 });

 {
  var $grid=$("#forumColumnsTab");
  $grid.datagrid('options').queryParams={qry_newsitemid:itemId};  
  $grid.datagrid("reload"); 
 }
 
	//点击保存
	$("#btnsave").click(function(){
		var mi704arry = $('#forumColumnsForm').serializeArray();    
		var tgbz = $("#forumColumnsForm").form("validate");      
		if (tgbz){          
			$.ajax({	 
				type: "POST",
				url: url,
				dataType: "json",
				data: mi704arry,
				success:function (data) {
					if(SUCCESS_CODE == data.recode){
						$("#forumColumnsTab").datagrid("reload");
						//$('#forumColumnsTab').datagrid('unselectAll');
						$("[name='forum']").each(function(){
       						$(this).attr("checked",false)
  						});
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

function getNewspaperforumList(){
	$.ajax({
			type: "POST",
			url: 'webapi704NewspaperforumList.json',
			dataType: "json",
			data: '',
			success:function (data) {
				if(SUCCESS_CODE == data.recode){
					$('#newspaperforumlist li').remove();
					$.each(data.newspaperforumlist, function (index, ele){
						$('#newspaperforumlist').append('<li><input type="radio" id="forum-'+index+'" name="forum" value="'+ele.itemid.trim()+'" ><label for="forum-'+index+'">'+ele.itemval+' </label></li>');
					
					});
				}
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
	});
}

function getNewspapercolumnsList(){
	$.ajax({
			type: "POST",
			url: 'webapi704NewspapercolumnsList.json',
			dataType: "json",
			data: '',
			success:function (data) {
				if(SUCCESS_CODE == data.recode){
					$('#newspapercolumnslist li').remove();
					$.each(data.newspapercolumnslist, function (index, ele){
						$('#newspapercolumnslist').append('<li><input type="checkbox" id="columns-'+index+'" name="columns" value="'+ele.itemid.trim()+'" ><label for="columns-'+index+'">'+ele.itemval+' </label></li>');
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
<div id="layout" class="easyui-panel easyui-layout" title="版块栏目管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<!-- 期次列表 开始-->     
		<table id="newsItemidTab"></table>
		<!-- 期次列表 结束-->
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<!-- 版块栏目列表 开始-->     
		<table id="forumColumnsTab"></table>
		<!-- 版块栏目列表 结束-->
		<div id="forumColumnsarea" class="easyui-panel"> 
			<form id="forumColumnsForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="newsitemid" name="newsitemid" value=""/>
			<input type='hidden' id="forumdicid" name="forumdicid" value=""/>
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<td><label >版块：</label></td>
					<td>  	
						<ul class="multivalue horizontal" id="newspaperforumlist">
						</ul>
					</td>          
				</tr>
				<tr>
					<td><label>栏目：</label></td> 
					<td>
						<ul class="multivalue horizontal" id="newspapercolumnslist">
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
	<select id="forumList" name="forumList" editable="false" class="easyui-combobox">				        	
	<%
		String options1 = "";
		List<Mi007> ary1=(List<Mi007>) request.getAttribute("forumList");
		for(int i=0;i<ary1.size();i++){
			Mi007 mi007=ary1.get(i);
			String itemidTmp=mi007.getItemid();
			String itemvalTmp=mi007.getItemval();
			options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemidTmp).append("\">").append(itemvalTmp).append("</option>\n").toString();
		}
		out.println(options1);
	%>
	</select>
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