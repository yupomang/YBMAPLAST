<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>test</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.panel{padding:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
$(function () {
	//提交
	$('#submit_form').click(function () {
		//$('#ff').submit();//jquery方法，需要自己写校验
		
		$('#formImport').submit();
		//easyui方法 需要测下，用jsp返回json提示下载问题 
		/*
		$('#ff').form('submit',{  
			url: 'success.html',
		    success: function(data){   
		    	//alert(data)
				if(data) window.location.href = 'success.html';   
		        //var data = eval('(' + data + ')');  // change the JSON string to javascript object   
		        //if (data.success){   
		        //    window.location.href = 'success.html';   
		        //}   
		    }   
		});
		*/
	});
	//重填
	$('#clear_form').click(function () {
		$('#ff').form('clear');
	});
	//对话框按钮
	$('#b_dialog').click(function(){
		$('#d_test').dialog({   
		    title: 'My Dialog',   
		    width: 400,   
		    height: 500,   
		    closed: false,   
		    cache: false,   
		    href: 'success.html',   
		    modal: true  
		});  
	});
	
	
	//datagrid列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		singleSelect: true,
		url: 'json.html',
		method:'get',
		queryParams:{pageName:'test'},
		columns:[[
			{field:'itemid',title:'Item ID',width:80},
			{field:'productid',title:'Product',width:100},
			{field:'listprice',title:'List Price',width:80,sortable:true,align:'right'},
			{field:'unitcost',title:'Unit Cost',width:80,sortable:true,align:'right'},
			{field:'attr1',title:'Attribute',width:220,sortable:true},
			//{field:'status',title:'状态',width:60,sortable:true,align:'center'},
			{title:'状态',field:'status',width:120 ,formatter : function(value){
				return $('#stat option[value='+value+']').text();
			}}
		]]
	}); 

	//datagrid 带增删改查功能列表
	$('#dg2').datagrid({   
		iconCls: 'icon-edit',
		singleSelect: false,
		SelectOnCheck: false,
		checkOnSelect: false,
		//toolbar: '#tb',
		url: 'json.html',
		method:'get',
		//单击行
		onClickRow: function(index,field,value){
			var oldindex = $(this).data('oldindex');
			
			if (oldindex != index) {
				$(this).datagrid('selectRow', index).datagrid('beginEdit', index);
				$(this).datagrid('cancelEdit', oldindex);
				$(this).data('oldindex',index);
			}
			else $(this).datagrid('selectRow', oldindex);
			
		},
		queryParams:{pageName:'test'}, 
		toolbar: [
			//添加按钮
			{iconCls: 'icon-add',title:'添加',handler: function(){
				$('#dg2').datagrid('appendRow',{status:'选中'});
				var editIndex = $('#dg2').datagrid('getRows').length-1;
				$('#dg2').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}},
			{iconCls: 'icon-remove',handler: function(){
				
				alert('help')
			}},
			//保存按钮
			{iconCls: 'icon-save',handler: function(){
				$('#dg2').datagrid('acceptChanges');
			}},
			{iconCls: 'icon-undo',handler: function(){
				alert('help')
			}},
			{iconCls: 'icon-search',handler: function(){
				alert('help')
			}}
		]
	}); 
	
	//datagrid列表 checkbox
	$('#dg3').datagrid({   
		rownumbers:true,
		singleSelect: true,//单选或多选 
		url: 'json.html',
		method:'get',
		queryParams:{pageName:'test'}
	}); 
	
	$('#b_save').click(function (){
		//name 校验
		//if (!ydl.validator('name', {type: 'int',negative: true, range: [-10,10], except: '0'}))return false; 
		//alert(ydl.validator('name', {type: 'int',negative: true, range: [-10,10], except: '0'}));
		alert(ydl.validator('name', {silent:false,type: 'phone'}));
	
	});
	
	
	

	
});

//]]></script>
</head>

<body>
<div class="easyui-panel" title="个人信息">
<form id="ff" method="post" action="success.html">
	<table class="container">
		<tr>
			<th><label for="name">必输项：</label></th>
			<td><input id="name" class="easyui-validatebox" type="text" name="name" data-options="required:true" /></td>
			<th><label for="email">测试组合验证时用：</label></th>
			<td><input class="easyui-validatebox" type="text" name="email" validType="validchar[['1','3']]&&zipcode"/></td>
		</tr>
		<tr>
			<th><label for="name1">电话：</label></th>
			<td><input id="name1" class="easyui-validatebox" type="text" name="name1" validType="phone" /></td>
			<th><label for="email1">特殊字符：</label></th>
			<td><input class="easyui-validatebox" type="text" name="email1" validType="validchar[['!','%','^','&','*','$','~','#','@']]" /></td>
		</tr>
		<tr>
			<th><label for="name2">邮编：</label></th>
			<td><input id="name2" class="easyui-validatebox" type="text" name="name2" validType="zipcode" /></td>
			<th><label for="email2">日期：</label></th>
			<td><input class="easyui-validatebox" type="text" name="email2" validType="date" /></td>
		</tr>
		<tr>
			<th><label for="birthdate">邮件：</label></th>
			<td><input id="birthdate" type="text" class="easyui-datebox" validType="email"></td>
			<th><label for="age">年龄：</label></th>
			<td><input id="age" type="text" class="easyui-numberbox" value="" data-options="min:1,max:100"></td>
		</tr>
		<tr>
			<th><label for="bdate">起始日期：</label></th>
			<td><input id="bdate" type="text" class="easyui-datebox" validType="date&&maxdate['edate','【结束日期】']"></td>
			<th><label for="edate">结束日期：</label></th>
			<td><input id="edate" type="text" class="easyui-datebox" validType="date&&mindate['bdate','【起始日期】']"></td>
		</tr>
		<tr>
			<th><label for="length">长度限制：</label></th>
			<td><input id="length" class="easyui-validatebox" type="text" name="length" validType="length[2,4]"/></td>
			<th><label for="dept">部门：</label></th>
			<td colspan="1"><select id="stat" class="easyui-combobox" name="dept">
				<option value="0">启用</option>
				<option value="1">停用</option>
			</select></td>
		</tr>
		<tr>
			<td><label for="numletter">数字与字母：</label></td>
			<td><input id="numletter" class="easyui-validatebox" type="text"  validType="numletter"></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<div class="container">
		<a id="submit_form" href="javascript:void(0)" class="easyui-linkbutton">提交</a>
		<a id="clear_form" href="javascript:void(0)" class="easyui-linkbutton">重填</a>
		<a id="b_dialog" href="javascript:void(0)" class="easyui-linkbutton">对话框</a>
		<button id="b_save" >保存</button>
		
	</div>
</form>

</div>
<!-- datagrid 单纯列表 -->
<table id="dg" class="easyui-datagrid container" title="base DataGrid">
<!--  
	<thead>
		<tr>
			<th data-options="field:'itemid',width:80">Item ID</th>
			<th data-options="field:'productid',width:100,editor:'text'">Product</th>
			<th data-options="field:'listprice',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">List Price</th>
			<th data-options="field:'unitcost',width:80,align:'right',editor:'numberbox'">Unit Cost</th>
			<th data-options="field:'attr1',width:250,editor:'text'">Attribute</th>
			<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'选中',off:''}}">Status</th>
		</tr>
	</thead>
-->
</table>
<!-- datagrid 带增删改查功能列表 -->
<table id="dg2" class="easyui-datagrid container" title="add Edit del DataGrid">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'itemid',width:80">Item ID</th>
			<th data-options="field:'productid',width:100,editor:'text'">Product</th>
			<th data-options="field:'listprice',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">List Price</th>
			<th data-options="field:'unitcost',width:80,align:'right',editor:'numberbox'">Unit Cost</th>
			<th data-options="field:'attr1',width:250,editor:'text'">Attribute</th>
			<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'选中',off:''}}">Status</th>
		</tr>
	</thead>
</table>
<table id="dg3" class="easyui-datagrid container" title="CheckBox Selection on DataGrid" >
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th data-options="field:'itemid',width:80">Item ID</th>
			<th data-options="field:'productid',width:100">Product</th>
			<th data-options="field:'listprice',width:80,align:'right'">List Price</th>
			<th data-options="field:'unitcost',width:80,align:'right'">Unit Cost</th>
			<th data-options="field:'attr1',width:220">Attribute</th>
			<th data-options="field:'status',width:60,align:'center'">Status</th>
		</tr>
	</thead>
</table>

<div id="d_test"></div>
</body>
</html>
