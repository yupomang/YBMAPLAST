<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi702"%>
<%@page import="com.yondervision.mi.result.NewspapersTitleInfoBean"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="com.yondervision.mi.dto.Mi701"%>
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
<title>评论管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
#list-container {width: 33%; margin-top: 10px; padding-left:1px;float:left;}
#form-container {width: 66%; float:right; margin-top: 10px;padding-left:1px;padding-right:1px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
$(function() {

	var str = <%=request.getAttribute("timesJsonObj")%>;
	var classification = str.rows[0].itemid;
	$("#newsTab").datagrid({
		//width:300,
		height:$(document).height() + 138-335,
		title:'新闻标题列表',
		fitColumns: true,
		singleSelect:true,
		pagination:true,
		rownumbers:true,
		url: "<%=request.getContextPath()%>/webapi70305.json",
		method:'post',
		queryParams:{classification:classification,newspaperforum:'',newspapercolumns:'',title:''},
		onClickRow:function(rowIndex, rowData){
			var $grid=$("#commentTab");
			$grid.datagrid('options').queryParams={newsseqno:rowData.seqno};
			$grid.datagrid('options').url="<%=request.getContextPath()%>/webapi70306.json";
			$grid.datagrid("reload");
		},
    	columns:[[
     		{field:'seqno',title:'新闻ID',width:100,hidden:true},
     		{field:'title',title:'新闻标题',width:200}     
    	]],
		onLoadSuccess:function(data){ 
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }
 	});
	$("#commentTab").datagrid({
		//width:$(document).width()-312,
		width:$('#form-container').width(),
		height:$(document).height()-138-60,
		title:'评论列表',
		singleSelect:true,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		url:"<%=request.getContextPath()%>/webapi70304.json",
		method:'post',
		queryParams:{classification:classification,newspaperforum:'',newspapercolumns:'',title:''},
    	columns:[[
			{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){
				return "<input type='checkbox' value='" + row.seqno + "' name='chk_seqno'/>"
			}},
     		{field:'devtype',title:'评论来源',width:85,formatter:function(value,row,index) {	
				return $('#devtypeList option[value='+value+']').text(); 	
			}},
     		{field:'userid',title:'评论人',width:80},   
     		{field:'content',title:'评论内容',width:300},
     		{field:'datecreated',title:'评论时间',width:80},
     		{field:'praisecounts',title:'点赞数',width:50}
    	]],
    	toolbar:[{	
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove', 
			handler:function(){
				var $checkboxs = $('input:checkbox:checked');
				if ($checkboxs.length > 0) {
					var code = $checkboxs.map(function(index){
						return this.value;
					}).get().join(',');
					var arr = [{name : "seqnoList",value : code}];
					$.messager.confirm('提示','请确认是否删除？',function(r){
						if(r){
							$.ajax({
								type : "POST",
								url : "<%=request.getContextPath()%>/webapi70302.json",
								dataType: "json",
								data:arr,
								success : function(data) {
									if (data.recode == SUCCESS_CODE) {
										$('#commentTab').datagrid('reload');    // reload the user data
									}
									else $.messager.alert('提示',data.msg,'info');					
								},
								error :function(){
									$.messager.alert('错误','网络连接出错！','error');							
								}
							});
						}				
					});
				}else $.messager.alert('提示', '请至少选择一条记录进行删除！', 'info' );
		   }
		}],
		onLoadSuccess:function(data){ 
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }
 	});
 
	//查询
	$('#b_query').click(function (){
		//校验
		if (!$("#form70304").form("validate")) return;
		
		var arr = $('#form70304').serializeArray();
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#newsTab').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#newsTab').datagrid('reload');
		var $grid=$("#commentTab");
		$('#commentTab').datagrid('options').queryParams = dataJson;//修改查询条件
		$grid.datagrid('options').url="<%=request.getContextPath()%>/webapi70304.json";
		$grid.datagrid("reload");
	});
	
	//重置
	$('#b_reset').click(function (){
		$('#form70304').form('clear');
		$('#classification').combobox('select','');
		$('#newspaperforum').combobox('select','');
		$('#newspapercolumns').combobox('select','');
		ydl.delErrorMessage('form70304');
	});

    //级联选择
    var selectTimes = classification;
	$('#classification').combobox({
    	onChange:function(newValue,oldValue){
    	 	selectTimes = newValue;
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getForumColumnsJsonArray.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspaperforum').combobox('clear');
						$('#newspapercolumns').combobox('clear');
						$('#newspaperforum').combobox('loadData', data.forumJsonArray);
						$('#newspapercolumns').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
	$('#newspaperforum').combobox({
    	onChange:function(newValue,oldValue){
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getColumnsJsonArray.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':selectTimes,'newspaperforum':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspapercolumns').combobox('clear');
						$('#newspapercolumns').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
    
    
	//设置页面结构高度宽度
	//$('#layout').height($(document).height()-30);
	//$('#list-container,#form-container').height($(document).height()-40);
}) 

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
<div id="layout" class="easyui-panel easyui-layout" title="评论管理">
	<form id="form70304" class="dlg-form" novalidate="novalidate" >
		<table class="container">	
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="classification">报刊期次:</label></th>
				<td>
				<select id="classification" name="classification" class="easyui-combobox" editable="false" style="width:250px;" >
						<%
							String options = "";
							List<NewspapersTitleInfoBean> ary=(List<NewspapersTitleInfoBean>) request.getAttribute("newsItemIdList");
							for(int i=0;i<ary.size();i++){
								NewspapersTitleInfoBean timesBean=ary.get(i);
								String itemid=timesBean.getItemid();
								String itemval=timesBean.getItemval();
								options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options);
						%>
				</select>
				</td>
				<th><label for="newspaperforum">报刊版块:</label></th>
				<td>
				<select id="newspaperforum" name="newspaperforum" class="easyui-combobox" editable="false" style="width:250px;" data-options="valueField:'itemid',textField:'itemval'">
						<!-- <option value="">请选择...</option> -->
						<option value=""></option>
						<%
							String options1 = "";
							List<NewspapersTitleInfoBean> ary1=(List<NewspapersTitleInfoBean>) request.getAttribute("newspaperforumList");
							for(int i=0;i<ary1.size();i++){
								NewspapersTitleInfoBean timesBean=ary1.get(i);
								String itemid=timesBean.getItemid();
								String itemval=timesBean.getItemval();
								options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options1);
						%>
				</select>
				</td>
			</tr>
			<tr>	        
				<th><label for="newspapercolumns">报刊栏目:</label></th>
				<td>
				<select id="newspapercolumns" name="newspapercolumns" class="easyui-combobox" editable="false" style="width:250px;" data-options="valueField:'itemid',textField:'itemval'">
						<!-- <option value="">请选择...</option> -->
						<option value=""></option>
						<%
							String options2 = "";
							List<NewspapersTitleInfoBean> ary2=(List<NewspapersTitleInfoBean>) request.getAttribute("newspapercolumnsList");
							for(int i=0;i<ary2.size();i++){
								NewspapersTitleInfoBean timesBean=ary2.get(i);
								String itemid=timesBean.getItemid();
								String itemval=timesBean.getItemval();
								options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options2);
						%>
				</select>
				</td>
				<th><label for="title">新闻标题:</label></th>
				<td>
					<input type="text" id = "title" name="title" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" style="width:250px;"/>
				</td>
			</tr>
		</table> 
		<div class="buttons">
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
		</div>
	</form>
	</div> 
	<div id="list-container" >
		<!-- 新闻列表 开始-->     
		<table id="newsTab"></table>
		<!-- 新闻列表 结束-->
	</div>
	<div id="form-container">
		<!-- 评论列表 开始-->     
		<table id="commentTab"></table>
		<!-- 评论列表 结束-->
	</div>

<div style="display:none">
	<select id="devtypeList" name="devtypeList" editable="false" class="easyui-combobox">				        	
	<%
		String options4 = "";
		List<Mi007> ary4=(List<Mi007>) request.getAttribute("devtypeList");
		for(int i=0;i<ary4.size();i++){
			Mi007 mi007=ary4.get(i);
			String itemidTmp=mi007.getItemid();
			String itemvalTmp=mi007.getItemval();
			options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemidTmp).append("\">").append(itemvalTmp).append("</option>\n").toString();
		}
		out.println(options4);
	%>
	</select>
</div>
</body>
</html>