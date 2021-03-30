<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>业务咨询向导维护</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container{float: left; width: 80%; padding:0px;overflow: hidden}
#tree-container {float: left; width: 19%;overflow-y: scroll;margin-right:5px;background-color:#E6EEF8;}
#form20119 {margin-left:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
var loadData = {};
var init = true;
$(function () {
	//业务咨询向导查询列表
	$('#tab20121').treegrid({
		title:'业务咨询向导查询',
		iconCls:'icon-tip',
		toolbar:'#toolbar',
		height:$('#tree-container').height()+2,
		//width:$('#form20119').width(),
		singleSelect: true,
		checkOnSelect: false,
		rownumbers: true,
		animate:true,
		collapsible:true,
		//fitColumns:true,
		url:'webapi20121.json',
		method: 'post',
		idField:'id',
		treeField:'name',
		queryParams: {"centerId":"00001"}, //TODO 查询条件中心编码
		//与page20104一样了
		frozenColumns : [ [ {
			title : '删除',
			field : 'checkbox',//
			width : 30,
			formatter : function(value) {
				var values = value.split('-');
				if (values.length && values.length==3) {
					var cbId = values[2]=="1" ? "listStepId" : "listConsultId";
					var cbValue = values[0];
					var cbPValue = values[1];
					var cbPId = values[2]=="1" ? "src" : values[2]=="2" ? "listStepId" : "listConsultId";
					return '<input id="'+cbId+cbValue+'" name="'+cbId+'" type="checkbox" onChange="checkClick(\''+cbId+cbValue+'\');" level="'+values[2]+'" value="'+cbValue+'" pvalue="'+cbPValue+'" pid="'+cbPId+cbPValue+'"></input>';
				}
				return value;
			}
		} ] ],
		columns:[[
			//{title:'删除',field:'ck',checkbox:true,width:60},
			{title:'业务咨询向导步骤名称',field:'name',width:400,align:'left'},
			{title:'类别',field:'level',width:150, formatter : function(value){
				return value=='1' ? '业务咨询向导步骤' : value=='2' ? '业务咨询向导标题' : '业务咨询向导信息';
			}},
			{title:'向导步骤号',field:'step',width:80,formatter : function(value){
				return "第"+ value + "步";
			}},
			{title:'序号',field:'orderNo',width:60},		// TODO 易于维护
		//	{title:'向导步骤ID',field:'stepId',width:60},
		//	{title:'业务咨询子项ID',field:'consultSubItemId',width:80},
			{title:'已配置公共条件组合',field:'conditionItemGroupId',width:120 ,formatter : function(value){
				return value==null || value=="" ? "否" : "是";
			}}
		]],
		onClickRow:function(row){
			//alert(row.id)
		}
	});
	
	//左侧树
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20100.json",
		dataType: "json",
		data:putPubData([{"name":"level", "value":"3"}]),
		success : function(data) {
			var treeData = data.result;
			$('#ptree').tree({   
				data:treeData, 
				onClick: function(node){
					if(node.attributes.level==3){
						$('#tab20121').treegrid('options').queryParams.consultSubItemId = node.attributes.id;//修改查询条件
						$('#tab20121').treegrid('reload'); //刷新列表
					}
				}
			}); 
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
	
	
	//隐藏
	$('#dlg-buttons,#d_dlg2,#d_dlg').hide();
	//加载iframe
	$('#opendlgIframe')[0].onload = function(){
		window.frames['opendlgIframe'].window.formload(loadData);
	}
	
});
//添加向导步骤
function add(){
	var row = $('#ptree').tree('getSelected');
	//alert(row)
	if(row && row.attributes.level==3){
		//alert(row.attributes.id)
		var href = 'page20118.html';
		var title = '添加业务咨询向导步骤';
		var load = {'id':'stepId', 'consultSubItemId':row.attributes.id};
		$('#d_dlg').show().dialog({   
		    title: title,   
		    width: 400,   
		    height: 300,   
		    closed: false,   
		    cache: false,   
		    href: href,
		    onLoad:function(){ 
		    	$('#'+load.id).closest('tr').hide();
		    	$('#h_action').attr('value','webapi20118.json');
		    	$('#h_formName').attr('value','form20118');
		        $('form').form('load',load);
		    },
		    buttons:'#dlg-buttons',   
		    modal: true  
		});  
	}
	else $.messager.alert('提示','请选择业务咨询子项进行添加!','info');
}
//增加下级信息
function addChildren(){
	var row = $('#tab20121').treegrid('getSelected');
	if(row){
		href = 'page20122.html';
		title = '添加业务向导信息'
		var load = $.extend({},row);
		load.id = 'consultId';
		load.action = 'webapi20122.json';
		load.formName = 'form20122';	
		if (row.level == 1){
			load.parentId = 'src';
			load.parentName = '';
		} else {			
			load.parentId = row.consultId;
			load.parentName = row.name;
		}
		load.task = 'add'; 
		
       	$('#dlg-buttons,#d_dlg2').show();
       	
		$('#d_dlg2').dialog({   
		    title: title,   
		    width: 800,   
		    height: 500,   
		    closed: false,   
		    cache: false,   
		    onOpen:function(){    
		    	//$('#'+load.id).closest('tr').hide();//添加时隐藏
		    	$('#h_action').attr('value',load.action);
		    	$('#h_formName').attr('value',load.formName);
		    	if (!init) window.frames['opendlgIframe'].window.formload(load);
		    	else {
		    		loadData = load;
		    		init = false;
		    	}
		    },
		    buttons:'#dlg-buttons',   
		    modal: true  
		});
       	
		//$('#d_dlg2').dialog('open'); 
		
		
	}
	else $.messager.alert('提示','请选择公共条件项目或公共条件分组进行添加！','info');
}
//删除记录
function del(){
	$.messager.confirm('提示','是否要删除选中项极其子项？', function(isDel){
		if (isDel) {	// IF1 确认删除
			var arr = $('#form20119').serializeArray();
			if ($('input[name=listConsultId]:checked').length==0) {	// IF2 未选中业务咨询向导内容
				if ($('input[name=listStepId]:checked').length!=0) {	// IF3 选中业务咨询向导步骤
					$.ajax( {	// 删除选中业务咨询向导步骤
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi20119.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							$.messager.alert('提示', data.msg, 'info');
							if (data.recode==SUCCESS_CODE) {
								$('#tab20121').treegrid('reload'); //刷新列表
							}
						},
						error :function(){
							$.messager.alert('错误','网络连接出错！','error');
						}
					});
				}	// END IF3 选中业务咨询向导步骤
				else {	// ELSE1 未选中删除复选框
					$.messager.alert('提示', '请选择要删除的向导步骤或向导内容！', 'info');
				}	// END ELSE1 未选中删除复选框
			}	// END IF2 未选中公共向导内容
			else {	// ESLE2 选中业务咨询向导内容，可能选中业务咨询向导步骤
				$.ajax( {	// 删除业务咨询向导内容
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20123.json",
					dataType: "json",
					data:arr,
					success : function(data) {
						if (data.recode==SUCCESS_CODE) {	// IF4 删除业务咨询向导内容成功
							if ($('input[name=listStepId]:checked').length!=0) {	// IF5 选中业务咨询向导步骤
								$.ajax( {	// 删除选中业务咨询向导步骤
									type : "POST",
									url : "<%=request.getContextPath()%>/webapi20119.json",
									dataType: "json",
									data:arr,
									success : function(data) {
										$.messager.alert('提示', data.msg, 'info');
										if (data.recode==SUCCESS_CODE) {
											$('#tab20121').treegrid('reload'); //刷新列表
										}
									},
									error :function(){
										$.messager.alert('错误','网络连接出错！','error');
									}
								});
							}	// END IF5 选中业务咨询向导步骤
							else {	// 未选中业务咨询向导步骤
								$('#tab20121').treegrid('reload'); //刷新列表
							}
						}	// END IF4 删除业务咨询向导内容成功
						else {	// 删除业务咨询向导内容失败
							$.messager.alert('提示', data.msg, 'info');
						}
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}	// END ESLE2 选中业务咨询向导内容，可能选中业务咨询向导步骤
		}	// END IF1 确认删除
	});
	
}
//保存记录，添加修改分别调不同的路径
function save(){
	//alert($('#h_action').val());
	//alert($('#h_formName').val());
	var paras = $('#'+$('#h_formName').val()).serializeArray();
	var url = $('#h_action').val();
	//如果是20122
	if($('#h_formName').val() == 'form20122'){
		//组织提交数据
		paras = $(window.frames['opendlgIframe'].document.getElementById('form20122')).serializeArray();
	//alert(JSON.stringify(paras));
		var submitData = window.frames['opendlgIframe'].window.formSubmit();
	//alert(JSON.stringify(submitData));
		$.merge(paras,submitData);
	//alert(JSON.stringify(paras));
		var row = $('#tab20121').treegrid('getSelected');
		if(row && row.mi307)paras[paras.length]={name:'conditionItemGroupId',value:row.conditionItemGroupId};
		
		//alert(JSON.stringify(paras));
		
		//校验
		if(!window.frames['opendlgIframe'].window.formvalidate()) return;
	}
	//校验
	else{
		if (!$('from').form( "validate" ,function(v){})) {
			alert($('from').form( "validate" ,function(v){} ));
			$.messager.alert('提示', "请输入完整数据!", 'info' );
			return;
		}
	}
	//alert(JSON.stringify(paras));
	//查询
	$.ajax( {
		type : "POST",
		url : url,
		dataType: "json",
		data: paras,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#tab20121').treegrid('reload');
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});

	if($('#h_formName').val() == 'form20122') $('#d_dlg2').dialog('close');
	else $('#d_dlg').dialog('close');
}
//修改记录
function edit(){
	var row = $('#tab20121').treegrid('getSelected');
	if(row){
		var href = '';
		var title = '';
		var load = $.extend({},row);
		if (row.level == 1){
			href = 'page20118.html';
			title = '修改向导步骤';
			load.id = 'stepId';
			load.action = 'webapi20120.json';
			load.formName = 'form20118';
			
			load.step = row.orderNo;
			load.stepName = row.name;
			$('#d_dlg').show().dialog({   
			    title: title,   
			    width: 400,   
			    height: 300,   
			    closed: false,   
			    cache: false,   
			    href: href,
			    onLoad:function(){   
			    	$('#'+load.id).closest('tr').show();
			    	$('#h_action').attr('value',load.action);
			    	$('#h_formName').attr('value',load.formName);
			        $('form').form('load',load);
			    },
			    buttons:'#dlg-buttons',   
			    modal: true  
			}); 
			
		}
		else if(row.level >= 2){
			title = '修改向导内容';
			load.id = 'consultId';
			load.action = 'webapi20124.json';
			load.formName = 'form20122';
			load.detail = row.name;
			var parentNode = $('#tab20121').treegrid('getParent',row.id);
			load.parentName = parentNode.name;
			load.task = 'edit'; 
			$('#dlg-buttons,#d_dlg2').show();
			$('#d_dlg2').dialog({   
			    title: title,   
			    width: 800,   
			    height: 500,   
			    closed: false,   
			    cache: false,   
			    onOpen:function(){    
			    	$('#h_action').attr('value',load.action);
			    	$('#h_formName').attr('value',load.formName);
			        if (!init) window.frames['opendlgIframe'].window.formload(load);
			    	else {
			    		loadData = load;
			    		init = false;
			    	}
			    },
			    buttons:'#dlg-buttons',   
			    modal: true  
			});
		}
		 
	}
	else $.messager.alert('提示','请选择要修改的记录!','info');
}
//删除复选框onChange事件
function checkClick(id){
	var level = $('#'+id).attr('level');

	var $checkbox2 = $('input[name=listConsultId][pid='+id+']')
	$checkbox2.prop('checked',$('#'+id).prop('checked')).change();

	// TODO 下级取消选中时上级也要取消选中；浏览器兼容性
}
//关闭对话框
function closeDlg(){
	if ($('#d_dlg').is(':visible')) $('#d_dlg').dialog('close');
	else $('#d_dlg2').dialog('close');
}


//]]></script>
</head>

<body class="easyui-layout">

<div class="page-header" data-options="region:'north',split:false,border:false">
	业务咨询->业务咨询向导配置
</div>
<div id="tree-container" data-options="region:'west',split:false,border:true">
	<ul id="ptree" ></ul>
</div>
<div id="list-container" data-options="region:'center',border:false">
	<form id='form20119'>
		<table id="tab20121" class="container"></table>
	</form>
	<div id="toolbar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add()">业务咨询向导步骤</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addChildren()">下级</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改</a>
	</div>
	<div id="d_dlg"></div>
	<div id="dlg-buttons">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeDlg()">取消</a>
	 </div>
	 <div id="d_dlg2">
		<iframe  id='opendlgIframe' frameborder="0"  src="page20122.html" style="width:100%;height:100%;"></iframe>
	 </div>
	 <input type="hidden" id='h_action' />
	 <input type="hidden" id='h_formName' />
</div>
</body>
</html>
