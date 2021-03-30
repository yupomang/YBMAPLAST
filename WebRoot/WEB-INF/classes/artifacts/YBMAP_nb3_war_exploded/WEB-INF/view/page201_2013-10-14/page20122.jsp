<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件项目添加/修改</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body,html{height:97%;}
.panel{padding:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
var editIndex = undefined;
$(function () {
	$('#tab20122').datagrid({   
	    url:'webapi20122.json',   
	    queryParams: {method:'2',consultSubItemId:$('#consultSubItemId').val()}, //查询条件
	    singleSelect: true,
	    columns:[[   
	        {field:'conditionItemName',title:'项目名称',width:240},   
	        {field:'conditionGroupId',title:'分组名称',width:240,
				formatter:function(value,row){
					return row.groupName;
				},
	        	editor:{
	        		type:'combobox',
	        		options:{valueField: 'conditionGroupId',textField: 'groupName',data:[],
	        			onChange: function (newValue, oldValue) {
	        				//级联查询todo
							//当前行记录
	        				var row = $('#tab20122').datagrid('getSelected');
	        				if(row){
								//当前行索引
								var rowindex = $('#tab20122').datagrid('getRowIndex',row);
								//第3列条件内容编辑器
								var ed3 = $('#tab20122').datagrid('getEditor', {index: rowindex,field:'conditionId'});
								$.each(row.conditionGroup,function(index,ele){
									if(ele.conditionGroupId == newValue){
										$(ed3.target).combobox('clear').combobox('loadData',ele.condition);
										//设置默认值
										if(newValue != '') $(ed3.target).combobox('setValue', ele.condition[0].conditionId);
										return;
									}
								});
								
							}
	        			}
	        		}
	        	}
	        },   
	        {field:'conditionId',title:'条件内容',width:240,
	        	formatter:function(value,row){
					return row.conditionDetail;
				},
	        	editor:{
	        		type:'combobox',
	        		options:{valueField: 'conditionId',textField: 'conditionDetail',data:[]}
	        	}
	        }   
	    ]],
	    loadFilter:function(data){
			if(data.rows.length > 0){
				//遍历数组,设置默认值
				$.each(data.rows,function(index,ele){
					$.each(ele.conditionGroup,function(cgindex,cgele){
						if(cgele.selected) {
							ele.groupName = cgele.groupName;
							$.each(cgele.condition,function(ctindex,ctele){
								if(ctele.selected){
									ele.conditionDetail = ctele.conditionDetail;
									ele.conditionId = ctele.conditionId;
									return;
								}
							})
							return;
						}
					});
				});
				$('#tabTitle').closest('.panel').show();//显示公共条件组合面板
			}
			else $('#tabTitle').closest('.panel').hide();//隐藏公共条件组合面板

	    	return data;
	    },
	    onClickRow:function(rowIndex){
	    	//alert(editIndex != rowIndex)
            if (editIndex != rowIndex){
				if (endEditing()){
					//设置当前行编辑状态
					$('#tab20122').datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
					editIndex = rowIndex;
					//填充第二列下拉列表
					var row = $('#tab20122').datagrid('getSelected');
					var ed2 = $('#tab20122').datagrid('getEditor', {index: rowIndex,field:'conditionGroupId'});
					$(ed2.target).combobox('loadData',row.conditionGroup);
				} 
				else $('#tab20122').datagrid('selectRow', editIndex);
			}
        }
	}); 
	 
});
//设置编辑状态
// TODO 修改后的显示值问题OK
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#tab20122').datagrid('validateRow', editIndex)){
		var ed2 = $('#tab20122').datagrid('getEditor', {index:editIndex,field:'conditionGroupId'});
		$('#tab20122').datagrid('getRows')[editIndex]['groupName'] = $(ed2.target).combobox('getText');
		var ed3 = $('#tab20122').datagrid('getEditor', {index:editIndex,field:'conditionId'});
		$('#tab20122').datagrid('getRows')[editIndex]['conditionDetail'] = $(ed3.target).combobox('getText');
		$('#tab20122').datagrid('getRows')[editIndex]['conditionId'] = $(ed3.target).combobox('getValue');
		$('#tab20122').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} 
	else return false;
}
//校验  条件组合只能全选或全不选。待修改~~
function formvalidate(){
	//alert($('#form20122').form( "validate" ,function(v){}))
	//若需要校验条件组合 
	if(!$('#tab20122').data('default')){
		//取消编辑
		if (endEditing())$('#tab20122').datagrid('endEdit', editIndex);
		
		//if(!(changes.length == 0 || changes.length == $('#tab20122').datagrid('getRows').length)) {
		//	$.messager.alert('提示', "请输入完整 公共条件组合信息或清空!", 'info' );
		//	return false;
		//}
	}
	if (!$('#form20122').form( "validate" ,function(v){})) {
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return false;
	}
	else return true;
}
//列表提交数据
// TODO 目前BUG：选择最后一个条件后如不点击其它行直接保存最后一个listConditionId取不到值--OK
function formSubmit(){
	//有默认值，不传id值
	if($('#tab20122').data('default')) return [];
	else {
		var rows = $('#tab20122').datagrid('getRows');//得到本页记录
		var reData = [];
		for(var i = 0; i < rows.length; i++){
			//如果是正在编辑的行
			if(i == editIndex) {
				var ed3 = $('#tab20122').datagrid('getEditor', {index:editIndex,field:'conditionId'});
				rows[i].conditionId = $(ed3.target).combobox('getValue');
			}
			reData[i] = {name:'listConditionId',value:rows[i].conditionId};
		}
		return reData;
	}
	//取消编辑状态
	$('#tab20122').datagrid('endEdit', editIndex);
	editIndex = undefined;
}

//填充表单
function formload(load){
	//填充表单
	load.method='1';
	$('form').form('clear').form('load',load);
	//是否显示向导信息id
	if (load.task == 'add') $('#'+load.id).closest('tr').hide();//添加时隐藏
	else $('#'+load.id).closest('tr').show();//修改时显示
	
	//有默认值时（修改时，或添加的是上级已添加过）
	if(load.mi307) {
		$('#tab20122').data('default',true);
		$('#tab20122').datagrid('options').columns[0][1].editor.options.disabled = true;
		$('#tab20122').datagrid('options').columns[0][2].editor.options.disabled = true;
	}
	else{
		$('#tab20122').data('default',false);
		$('#tab20122').datagrid('options').columns[0][1].editor.options.disabled = false;
		$('#tab20122').datagrid('options').columns[0][2].editor.options.disabled = false;
	}
	
	$('#tab20122').datagrid('load',$.extend({method:'2',consultSubItemId:load.consultSubItemId},load.mi307));
	editIndex = undefined;
}
//]]></script>
</head>

<body>
 
<form id="form20122" method="post" action="success.html">
<div id="tabTitle" class="easyui-panel" title="公共条件组合">	<!-- TODO 无条件时隐藏此DIV OK-->
	<table id="tab20122" class="container"></table>
</div>
<div class="easyui-panel" title="向导信息">	
	<!-- TODO 向导步骤ID、上级向导信息ID改为对应的名称，这两个ID改为隐藏框。向导步骤名称stepName，上级向导信息内容从添加/修改按钮传入 OK-->
	<table class="container">
		<!--  
		<tr>
			<th><label for="consultId">向导信息ID：</label></th>
			<td><input id="consultId" name="consultId" type="text" class="easyui-validatebox" required="required" readonly="readonly" /></td>
		</tr>
		<tr>
			<th><label for="parentId">上级向导信息ID：</label></th>
			<td><input id="parentId" name="parentId" type="text" value="fdsafsdaf" readonly="readonly" class="easyui-validatebox" required="required" readonly="readonly" /></td>
		</tr>
		-->
		<tr>
			<td><label for="parentName">上级向导信息内容：</label></td>
			<td><input id="parentName" name="parentName" class="easyui-validatebox" type="text"  readonly="readonly" /></td>
		</tr>
		<tr>
			<td><label for="stepId">向导步骤ID：</label></td>
			<td><input id="stepId" name="stepId" class="easyui-validatebox" type="text" data-options="required:true" readonly="readonly" /></td>
		</tr>
		<tr>
			<td><label for="stepName">向导步骤名称：</label></td>
			<td><input id="stepName" name="stepName" class="easyui-validatebox" type="text" data-options="required:true" readonly="readonly" /></td>
		</tr>
		<tr>
			<td><label for="orderNo">咨询信息序号：</label></td>
			<td><input id="orderNo" name="orderNo" type="text" class="easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<td><label for="detail">向导信息内容：</label></td>
			<td><textarea id="detail" name="detail" class="easyui-validatebox" required="required" ></textarea></td>
		</tr>
	</table>
</div>
<input id="method" type="hidden" name="method" value="1" />
<input id="consultSubItemId" type="hidden" name="consultSubItemId" />
<input id="consultId" type="hidden" name="consultId" />
<input id="parentId" type="hidden" name="parentId" />

</form>
</body>
</html>
