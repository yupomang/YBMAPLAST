<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<style type="text/css">
/*<![CDATA[*/
#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
body {width: 100%; hidden;margin:0;}
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}

.dlg_wrapper {padding:5px;}
#lr_buttons {margin:112px 0px 0px 205px;width:60px;}
#selnames {margin:-185px 0px 0px 265px;border: 1px solid #95B8E7;height:317px;}
#selnames div {background-color:#B2D6F5;padding: 2px 3px;}
#selnames ul {height:295px; overflow: auto;}
#selnames ul li{padding: 2px 3px;display:block;cursor:default;}

.selected {background-color:#FBEC88 !important;}
.hover {background-color:#EAF2FF;}

#b_left,#b_right{ margin-left: 3px;
    margin-right: 3px;
    padding-left: 1px;
    padding-right: 1px;
    width: 50px;}
	
.combobox-item {cursor:default;}	
/*]]>*/
</style>
<%
	UserContext user = UserContext.getInstance();

	if (user == null) {
		out.println("超时");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="contexPath" content="<%=_contexPath%>" />
		<title>微信功能策略配置</title>
		<%@ include file="/include/styles.jsp"%>
		
		<%@ include file="/include/scripts.jsp"%>
		<script type="text/javascript">
var message;
var allgn;
var policyrows={"rows":[]};
var centerrows={"rows":[]};
var gongeng = new Array();
var package = new Array();
var istrue=[{"value":true,"text":"可用"},{"value":false,"text":"不可用"}];
var map;
var editid;
var editnum;
var newcenter=1;
var addBanks = new Array();
var url;
var ctid;
var banksObj = {};
$(function() {
	
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/weixinapi00202.json",
		dataType: "json",					
		success : function(data) {
			if (data.errcode == 0) {
				allgn=data.rows;
				var n = 0;
				for(var i=0;i<data.rows.length;i++){
					gongeng.splice(i,0,{"value":data.rows[i].keyname,"text":data.rows[i].nickname});
					banksObj[data.rows[i].nickname] = data.rows[i].keyname;
					var num = 0;
					for(var j=0;j<package.length;j++){
						if(package[j].value==data.rows[i].regionId){
							num=1;
							break;															
						}						
					}
					if(num==0){
						package.splice(n,0,{"value":data.rows[i].regionId,"text":data.rows[i].regionId});
						n++;
					}					
				}				
				$("#gnid").combobox({
					 data:gongeng,
					 valueField:'value',
					 textField:'text'
				 });
				$("#package").combobox({
					 data:package,
					 valueField:'value',
					 textField:'text'
				 });
				 
				 $('#setbankNames').combobox({   
					data:gongeng,
					valueField:'value',   
					textField:'text',
					width:205,
					panelHeight:298,
					//hasDownArrow:false,
					onHidePanel:function (){
						if ($('#setbankNames').next().is(':visible')) $('#setbankNames').combobox('showPanel');
					},
					onChange:function (newValue, oldValue){
						$('#setbankNames').combobox('showPanel');
					},
					onSelect:function (record){						
						$('#b_right').data('bankName',record.text);
					}					
				}); 				 
				 
				 /*
				 $("#enable").combobox({
					 data:istrue,
					 valueField:'value',
					 textField:'text'
				 });*/
			}else{				
				$.messager.alert('提示',data.errmsg,'info');				
			}					
		},
		error :function() {
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
	
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'微信功能策略-功能信息',
		height:410,
		singleSelect: true,
		//pagination:true,
		rownumbers:true,
		fitColumns:true,
		data:policyrows,
		columns:[[
			//{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){				
			//	return '<input type="checkbox" name="animate" value="' + row.gnid+ '"/>';
			//}},	
			{title:'KEY',field:'gnid',align:'center',width:130,editor:'text'},
			{title:'功能描述',field:'msg',align:'center',width:130,editor:'text'},						
			{title:'包名',field:'package',align:'center',width:110,editor:'text'},			
			{title:'是否可用',field:'enable',align:'center',width:110,formatter:function (value,row,index){	
				return $('#enable option[value='+value+']').text(); 	
			}},
			{title:'行号',field:'rowsNumber',hidden:true,align:'center',width:130,editor:'text'},			
		]],
		toolbar: '#toolbar'		
	});	
	
	
	$('#dg_center').datagrid({   
		iconCls: 'icon-edit',
		title:'微信功能策略-中心信息',
		height:120,
		singleSelect: true,
		//pagination:true,
		rownumbers:true,
		fitColumns:true,
		data:policyrows,
		columns:[[			
			{title:'中心名称',field:'regionId_center',align:'center',width:110,formatter:function (value,row,index){	
				return $('#regionId_center option[value='+value+']').text(); 	
			}},			
			{title:'是否可用',field:'enable_center',align:'center',width:110,formatter:function (value,row,index){	
				return $('#enable_center option[value='+value+']').text(); 	
			}}			
		]],
		toolbar: '#toolbar_center'		
	});	
	
	
	$('#b_query').click(function (){		
		if (!$("#weixin00303").form("validate")) return;		
		var arr = $("#weixin00303").serializeArray();
		var dataJson = arrToJson(arr);		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/weixinapi00303.json",
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.errcode == 0) {					
					newcenter=1;
					message=data;
					policyrows={"rows":[]};							
					showData();
					$.messager.alert('提示','成功','info');
				}else{
					centerrows = {"rows":[]};
					policyrows={"rows":[]};
					editid=null;
					message=null;
					$('#dg').datagrid('loadData', policyrows);
					$('#dg_center').datagrid('loadData', centerrows);
					$.messager.alert('提示',data.errmsg,'info');					
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');				
			}
		});	
	});
	
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#weixin00303').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');		
		ydl.delErrorMessage('weixin00303');
	});
	//保存配置
	$('#b_save_all').click(function (){
		if(message.rows[0].storetype!=null){
			delete message.rows[0].storetype;
		}
		var ro=message.rows[0];		
		var mo=JSON.stringify(ro);
		
		var arr = {"value":mo};		
		//alert(mo);
		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/weixinapi00302.json",
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.errcode == 0) {
					centerrows = {"rows":[]};
					policyrows={"rows":[]};
					editid=null;
					message=null;
					$('#dg').datagrid('loadData', policyrows);
					$('#dg_center').datagrid('loadData', centerrows);
					$.messager.alert('提示','成功','info');
				}else{					
					$.messager.alert('提示',data.errmsg,'info');					
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');				
			}
		});
		
	});
	
	
	//增加
	$('#b_add').click(function (){
		//$('#dlg').dialog('open').dialog('setTitle','微信功能策略配置');		
		//$('#formtitle').text('微信功能策略配置');
		//$('#fm').form('clear');
		if(message==null)return;
		$('#dlgBankNames').dialog('open').dialog('setTitle','微信功能策略配置');
		$('#setbankNames').combobox('loadData',gongeng);
		$('#setbankNames').combobox('showPanel');
		ydl.setDlgPosition('dlgBankNames');			
		addBanks = new Array();
		for(var i=0;i<policyrows.rows.length;i++){
			addBanks.splice(i,0,{"value":policyrows.rows[i].gnid,"text":policyrows.rows[i].msg});
		}		
		//设置选择对话框默认值
		$('#selnames ul li').remove();	
		$('#setbankNames').combobox('setText','');
		for(var j=0;j<addBanks.length;j++){						
			if (addBanks[j].text != '' && banksObj[addBanks[j].text]) {					
				var $comboItem = $('.combobox-item[value='+banksObj[addBanks[j].text]+']').detach();
				var $li = $('<li id="'+addBanks[j].value+'">'+addBanks[j].text+'</li>');
				$li.data('item',$comboItem)
				$('#selnames ul').append($li);
			}				
			if (addBanks[j].text != '' && !banksObj[addBanks[j].text]) {						
				var $li = $('<li id="'+addBanks[j].value+'">'+addBanks[j].text+'</li>');
				$('#selnames ul').append($li);
			}
		}
	});	
	
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','微信功能配置策略修改');
				$('#formtitle').text('微信功能配置策略修改');
				$('#fm').form('load',row[0]);				
				$("#enable").combobox('select',String(row[0].enable));	
				editid = String(row[0].gnid);
				editnum = row[0].rowsNumber;					
			}
		}
		else $.messager.alert('提示', '请选中一条待修改微信功能策略信息！', 'info' );
	});
	
	//保存
	$('#b_save').click(function (){		
		if (!$("#fm").form("validate")) return;
		/*
		for(var i=0;i<policyrows.rows.length;i++){		
			if(policyrows.rows[i].gnid==$("#gnid").combobox('getValue')){
				if(editid!=null){
					
				}else{
					alert("此功能己经增加!");
					return;
				}				
			}
		}
		*/
		if(message.rows[0].function[$("#gnid").combobox('getValue')]!=null)	{						
			if(editid==$("#gnid").combobox('getValue'))	{				
				policyrows.rows.splice(editnum,1,{"gnid": $("#gnid").combobox('getValue'),"package":$("#package").combobox('getValue'),"enable":$("#enable").combobox('getValue'),"msg":$("#gnid").combobox('getText'),"rowsNumber":editnum});
				message.rows[0].function[editid].regionId=$("#package").combobox('getValue');
				message.rows[0].function[editid].enable=$("#enable").combobox('getValue');
			}else{
				if(editid==null){
					alert("此功能己经增加!");
					return;
				}else{
					alert("修改时只可修改本功能相关属性!");
					return;
				}
			}
		}else{
			if(policyrows.rows.length==null||policyrows.rows.length==0){						
				policyrows.rows.splice(0,0,{"gnid": $("#gnid").combobox('getValue'),"package":$("#package").combobox('getValue'),"enable":$("#enable").combobox('getValue'),"msg":$("#gnid").combobox('getText'),"rowsNumber":0});						
				var nmsg = {"regionId":$("#package").combobox('getValue'),"enable":$("#enable").combobox('getValue'),"addon": {}};
				message.rows[0].function[$("#gnid").combobox('getValue')]=nmsg;
			}else{
				var num = policyrows.rows.length;
				policyrows.rows.splice(num,0,{"gnid": $("#gnid").combobox('getValue'),"package":$("#package").combobox('getValue'),"enable":$("#enable").combobox('getValue'),"msg":$("#gnid").combobox('getText'),"rowsNumber":num});
				var nmsg = {"regionId":$("#package").combobox('getValue'),"enable":$("#enable").combobox('getValue'),"addon": {}};
				message.rows[0].function[$("#gnid").combobox('getValue')]=nmsg;
				//alert(message.rows[0].function[$("#gnid").combobox('getValue')].regionId);
			}
		}		
		$('#dg').datagrid('loadData', policyrows);
		$('#dlg').dialog('close');		
		editid=null;		
	});
	
	//删除
	$('#b_del').click(function (){		
		var $checkboxs = $('input[name=animate]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					var array=code.split(",");					
					for(var i=0;i<array.length;i++){						
						for(var j=0;j<policyrows.rows.length;j++){														
							if(policyrows.rows[j].gnid==array[i]){								
								policyrows.rows.splice(j,1);
								break;
							}
						} 
						delete message.rows[0].function[array[i]];
					}
				}
				$('#dg').datagrid('loadData', policyrows);
				//var ro=message.rows[0];		
				//var mo=JSON.stringify(ro);
				//alert(mo);				
			});
		}
		else $.messager.alert('提示', '请选中待删除信息！', 'info' );		
	});
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	//取消
	$('#b_cancel_center').click(function (){
		$('#dlg_center').dialog('close');
	});
	
	//增加
	$('#b_add_center').click(function (){
		//$('#fm_center').form('clear');
		$('#dlg_center').dialog('open').dialog('setTitle','微信功能策略配置');		
		$('#formtitle_center').text('微信功能策略配置');
	});	
	
	//保存
	$('#b_save_center').click(function (){		
		if (!$("#fm_center").form("validate")) return;
		if(newcenter==1){
			message = {"rows":[{"regionId": $("#regionId_center").combobox('getValue'),"enable": $("#enable_center").combobox('getValue'),"button":null,"function":{}}]};
			policyrows={"rows":[]};
			showData();		
		}else{
			message.rows[0].regionId=$("#regionId_center").combobox('getValue');
			message.rows[0].enable=$("#enable_center").combobox('getValue');			
			editid=null;
			centerrows.rows[0].regionId_center=$("#regionId_center").combobox('getValue');
			centerrows.rows[0].enable_center=$("#enable_center").combobox('getValue');
			$('#dg_center').datagrid('loadData', centerrows);
		}		
		$('#dlg_center').dialog('close');				
	});
	//修改
	$('#b_edit_center').click(function (){
		newcenter=2;
		var row = $('#dg_center').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				$('#dlg_center').dialog('open').dialog('setTitle','微信功能配置策略中心修改');
				$('#formtitle_center').text('微信功能配置策略中心修改');
				$('#fm_center').form('load',row[0]);				
				$("#enable_center").combobox('select',String(row[0].enable_center));
			}
		}
		else $.messager.alert('提示', '请选中一条待修改微信功能策略中心信息！', 'info' );
	});
	
	
	//var banksObj = {};
	/*$.each(gongeng,function (index,ele){		
		banksObj[ele.text] = ele.value;
	});*/
	
	$('#setbankNames').next().css('border-bottom-width','0px');
	//合作银行对话框
	$('#dlgBankNames').dialog({
		title : '选择合作银行',
		width : 500,
		height : 400,
		closed : true,
		cache : false,
		modal : true,
		draggable:false,
		onClose: function (){
			$('#setbankNames').combobox('hidePanel');
		},
		onMove: function (){
			//$('#setbankNames').combobox('showPanel');
		},
		buttons:[
		/*{
			text:'确定',
			handler:function(){
				var value = $('#selnames ul li').map(function(index){
					return $(this).html();
				}).get().join(',');
				//$('#selectdBanks').val(value);
				//if ($('#definedBanks').val() != '') value = value + ',' + $('#definedBanks').val();
				//$('#bankNames1').val(value);
				//alert(value);				
				$('#dlgBankNames').dialog('close');
			}
		},*/
		{
			text:'重置',
			handler:function(){
				$.messager.confirm('提示','重置清空功能配置?',function(r){ 
					if(r){
						$('#setbankNames').combobox('loadData',gongeng);
						$('#selnames ul li').remove();
						addBanks = [];
						$('#setbankNames').combobox('setText','');
						$('.combobox-item:visible').removeClass('combobox-item-selected');
						for(var i=0;i<policyrows.rows.length;i++){
							//alert(policyrows.rows[i].gnid);
							//alert(message.rows[0].function[policyrows.rows[i].gnid]);
							delete message.rows[0].function[policyrows.rows[i].gnid];
						}
						policyrows={"rows":[]};
						$('#dg').datagrid('loadData', policyrows);
						$('#dlgBankNames').dialog('close');
					}else{
						return;
					}					
				});
			}
		},{
			text:'关闭',
			handler:function(){
				$('#dlgBankNames').dialog('close');
			}
		}]
	});
	
	
	
	//右移按钮
	$('#b_right').click(function (){
		var bankName = $('#setbankNames').combobox('getText').trim();
		var bankValue = $('#setbankNames').combobox('getValue').trim();
		//alert(bankName+"     "+bankValue);
		if ($('.combobox-item-selected:visible').length == 0 && bankName == '') {
			$.messager.alert('提示', '请在左侧选择待添加的功能！', 'info');
			return; 
		}
		//校验
		//if (!ydl.formValidate('selectForm')) return;
		if (!$("#selectForm").form("validate")) return;
		if ($.inArray(bankName,addBanks) < 0) {			
			addBanks.push(bankName);
			//在下拉选择中
			if (banksObj[bankName]){
				var $comboItem = $('.combobox-item-selected:visible').detach();
				var $li = $('<li id="'+bankValue+'">'+$('#b_right').data('bankName')+'</li>');
				$li.data('item',$comboItem)
				$('#selnames ul').append($li);
				
				addBanks.splice(addBanks.length,0,{"value":bankValue,"text":bankName});
				
				if(policyrows.rows.length==null||policyrows.rows.length==0){					
					for(var i=0;i<allgn.length;i++){						
						if(bankValue==allgn[i].keyname){
							policyrows.rows.splice(0,0,{"gnid":allgn[i].keyname,"package":allgn[i].regionId,"enable":true,"msg":allgn[i].nickname,"rowsNumber":0});
							var nmsg = {"regionId":allgn[i].regionId,"enable":true,"addon": {}};
							message.rows[0].function[allgn[i].keyname]=nmsg;
							break;
						}
					}
				}else{
					for(var i=0;i<allgn.length;i++){						
						if(bankValue==allgn[i].keyname){
							var num = policyrows.rows.length;
							policyrows.rows.splice(num,0,{"gnid":allgn[i].keyname,"package":allgn[i].regionId,"enable":true,"msg":allgn[i].nickname,"rowsNumber":0});
							var nmsg = {"regionId":allgn[i].regionId,"enable":true,"addon": {}};
							message.rows[0].function[allgn[i].keyname]=nmsg;
							break;
						}
					}					
				}
			}
			//自定义添加
			//else {
				//$('#selnames ul').append('<li>'+bankName+'</li>');
			//}
			//添加后清空文本框
			$('#setbankNames').combobox('setText','');
			$('#dg').datagrid('loadData', policyrows);
		}
		else {			
			$('#selnames ul li').removeClass('selected');
			//$.messager.alert('提示', '已添加此银行！', 'info');
			$('#selnames ul li').each(function(index){
				if ($(this).text().trim() == bankName)	{
					$(this).addClass('selected');
					var position = $(this).position();
					var top = position.top;//330
					//考虑滚动条
					if (top > 330) $('#selnames ul').scrollTop($('#selnames ul').scrollTop() + top -330);
					else $('#selnames ul').scrollTop(0);
				}
			});
		}
	});
	
	//浮动到
	$('#selnames,#banklist').on('mouseover','li',function (){
		$('#selnames ul li').removeClass('hover');
		$(this).addClass('hover');
	}).on('mouseout','li',function (){
		$('#selnames ul li').removeClass('hover');
	}); 
	//点击已选择银行
	$('#selnames').on('click','li',function (){
		$('#selnames ul li').removeClass('selected');
		$(this).addClass('selected');
		$('#b_left').data('li',$(this));
	});
	//左移按钮
	$('#b_left').click(function (){
		//在下拉选择中		
		var $li = $('#b_left').data('li');
		var bankName = $li.text().trim();
		if (banksObj[bankName]){			
			$('.combo-panel:visible').append($li.data('item').removeClass('combobox-item-selected').eq(0));
			//alert($li.data('item').removeClass('combobox-item-selected').length);
		}
		$li.remove();		
		for(var i=0;i<policyrows.rows.length;i++){			
			//alert($li.prop('id'));
			if(policyrows.rows[i].gnid==$li.prop('id')){
				policyrows.rows.splice(i,1);
				delete message.rows[0].function[$li.prop('id')];
				break;
			}
		}		
		addBanks.splice($.inArray(bankName,addBanks),1);
		
		$('#dg').datagrid('loadData', policyrows);
	});
})




//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}

function del(num){	
	$('#row'+num).remove();
	array1.splice(num,1);
}

function showData(){	
	map = message.rows[0].function;	
	var i=0;
	if(allgn==null){
		alert("页面加载数据中...");
		return;
	}
	for(var key in map){
		for(var j=0;j<gongeng.length;j++){
			if(allgn[j].regionId==map[key].regionId&&allgn[j].keyname==key){
				policyrows.rows.splice(i,0,{"gnid": key,"package":map[key].regionId,"enable":map[key].enable,"msg":allgn[j].nickname,"rowsNumber":i});
				i++;
			}
		}		
	}
	centerrows.rows.splice(0,1,{"regionId_center": message.rows[0].regionId,"enable_center": message.rows[0].enable});	
	$('#dg').datagrid('loadData', policyrows);
	$('#dg_center').datagrid('loadData', centerrows);
}
</script>
	</head>

	<body>
		<script type="text/javascript"
			src="<%=_contexPath%>/scripts/jquery.form.js"></script>
		<!-- jquery.form.js 为页面提交json请求应用 -->
		<div class="easyui-panel" title="微信功能策略查询">
			<form id="weixin00303" class="dlg-form" novalidate="novalidate">
				<table class="container">
					<col width="50%" />
					<col width="50%" />					
					<tr>
						<th>
							<label for="regionId" style="font-size:12px;">
								城市中心:
							</label>
						</th>
						<td>
							<select id="regionId" name="regionId" class="easyui-combobox" editable="false" style="width:250px;"><!--  panelHeight="auto" -->
							<%if(!user.getCenterid().equals("00000000")){ 
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									if(mi001.getCenterid().equals(user.getCenterid())){
										String itemid=mi001.getCenterid();
										String itemval=mi001.getCentername();
										options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
										out.println(options);
										break;				
									}									
								}
							}else{%>
							<option value=" ">请选择...</option>
							<%  
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									String itemid=mi001.getCenterid();
									String itemval=mi001.getCentername();
									options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options);							
							%>					    
					    <%} %>	
					    </select>					
						</td>
					</tr>	
				</table>
				<div class="buttons">
					<a id="b_query" class="easyui-linkbutton" iconCls="icon-search"
						href="javascript:void(0)">查询</a>
					<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear"
						href="javascript:void(0)">重置</a>
					<a id="b_save_all" class="easyui-linkbutton" iconCls="icon-save"
						href="javascript:void(0)">保存配置</a>
				</div>
			</form>
		</div>
		<table id="dg_center"></table>
		<div id="toolbar_center">	
			<a id="b_add_center" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">新增</a>				
			<a id="b_edit_center" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a>			
		</div>
		<table id="dg"></table>
		<div id="toolbar">
			<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">策略</a>
			<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">策略明细修改</a>
			<!-- <a id="b_del" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
			 -->
		</div>

		<div id="dlg" class="easyui-dialog"
			style="width: 400px; height: 270px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons">
			<div class="formtitle" id="formtitle"></div>
			<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<col width="30%" />
					<col width="70%" />
					<tr>
						<th>
							<label for="gnid" style="font-size:12px;">
								功能:
							</label>
						</th>
						<td>
							<select id="gnid" name="gnid" class="easyui-combobox" required="true"
								editable="false" style="width:200px;" panelHeight="height:180px;"></select>
						</td>						
					</tr>	
					<tr>
						<th>
							<label for="package" style="font-size:12px;">
								包名:
							</label>
						</th>
						<td>
							<select id="package" name="package" class="easyui-combobox" required="true"
								editable="false" style="width:200px;" panelHeight="auto"></select>
						</td>
					</tr>
					<tr>
						<th>
							<label for="enable" style="font-size:12px;">
								是否可用:
							</label>
						</th>
						<td>
							<select id="enable" name="enable" class="easyui-combobox" required="true"
								editable="false" style="width:200px;" panelHeight="auto">
								<option value="true">可用</option>
								<option value="false">不可用</option> 
							</select>
						</td>
					</tr>										
				</table>
			</form>			
			
		</div>
		<div id="dlg-buttons">			
			<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">确定</a>
			<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		
		<div id="dlg_center" class="easyui-dialog"
			style="width: 400px; height: 270px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons_center">
			<div class="formtitle" id="formtitle_center"></div>
			<form id="fm_center" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<col width="30%" />
					<col width="70%" />
					<tr>
						<th>
							<label for="regionId_center" style="font-size:12px;">
								城市中心:
							</label>
						</th>
						<td>
							<select id="regionId_center" required="true" name="regionId_center" class="easyui-combobox" editable="false" style="width:200px;" ><!-- panelHeight="auto" -->
							<%if(!user.getCenterid().equals("00000000")){ 
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									if(mi001.getCenterid().equals(user.getCenterid())){
										String itemid=mi001.getCenterid();
										String itemval=mi001.getCentername();
										options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
										out.println(options);
										break;				
									}									
								}
							}else{ 
								String options = "";
								List<Mi001> ary=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary.size();i++){
									Mi001 mi001=ary.get(i);
									String itemid=mi001.getCenterid();
									String itemval=mi001.getCentername();
									options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options);							
							} %>	
					    </select>					
						</td>					
					</tr>						
					<tr>
						
						<th>
							<label for="enable_center" style="font-size:12px;">
								微信账号:
							</label>
						</th>
						<td>
						<select id="enable_center" name="enable_center" class="easyui-combobox" required="true"
								editable="false" style="width:200px;" panelHeight="auto">
								<option value="true">可用</option>
								<option value="false">不可用</option> 
						</select>
						</td>
					</tr>															
				</table>
			</form>	
		</div>
		<div id="dlg-buttons_center">			
			<a id="b_save_center" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">确定</a>
			<a id="b_cancel_center" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		
		<!-- 合作银行选择对话框 -->
		<div id="dlgBankNames">
			<div class="dlg_wrapper">
				<form id="selectForm" novalidate="novalidate">
					<input type="text" id="setbankNames" name="setbankNames" class="easyui-validatebox" validType="validchar[[',']]"/>				
				</form>
				<div id="lr_buttons">
					<a id="b_right" href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-right">添加</a>
					<a id="b_left" href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-left">删除</a>
				</div>
				<div id="selnames">
					<div>已添加策略列表：</div>
					<ul></ul>
				</div>
			<!--
			<input type="hidden" id="selectdBanks" />
			<input type="hidden" id="definedBanks" />
			-->
			</div>
		</div>
		
	</body>
</html>
