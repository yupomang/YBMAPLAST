<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
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
		<title>微信菜单配置</title>
		<%@ include file="/include/styles.jsp"%>
		<style type="text/css">
/*<![CDATA[*/
.datagrid {
	padding-top: 5px;
}

.panel-header,.panel-body {
	width: auto !important;
}
/*]]>*/
</style>
<%@ include file="/include/scripts.jsp"%>
<script type="text/javascript">
var message;
var selid;
var istype = [{"value":"view","text":"链接事件"},{"value":"click","text":"点击事件"}];
var gongeng = new Array();
$(function() {	
	/*
	$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/weixinapi00202.json",
			dataType: "json",					
			success : function(data) {
				if (data.errcode == 0) {					
					for(var i=0;i<data.rows.length;i++){
						gongeng.splice(i,0,{"value":data.rows[i].keyname,"text":data.rows[i].nickname});
					}					
				}else{
					$.messager.alert('提示',data.errmsg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	*/
	
	
	//初始化列表
	$('#dg_key').datagrid({   
		iconCls: 'icon-edit',
		title:'微信功能',
		height:350,
		singleSelect: true,		
		//pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',		
		url: "<%=request.getContextPath()%>/weixinapi00202.json",
		columns:[[
			//{field:'ck',title:'',align:'center',formatter:function (value,row,index){				
			//	return '<input type="checkbox" name="animate" value="' + row.animateid + '"/>';
			//}},					
			{title:'包名',field:'regionId',align:'center',width:100,editor:'text'},			
			{title:'类名',field:'funcName',align:'center',width:100,editor:'text'},
			{title:'路径',field:'className',align:'center',width:160,editor:'text'},
			{title:'KEY',field:'keyname',align:'center',width:100,editor:'text'},
			{title:'描述',field:'nickname',align:'center',width:100,editor:'text'}						
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.errcode != 0) $.messager.alert('提示', data.errmsg, 'info' );
        }
	});
	
	
	
			
	//查询
	$('#b_query').click(function (){
		if (!$("#weinxin00301").form("validate")) return;		
		var arr = $("#weinxin00301").serializeArray();
		var dataJson = arrToJson(arr);		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/weixinapi00301.json",
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.errcode == 0) {					
					message=data;		
					showTree();
					$.messager.alert('提示','成功','info');
				}else{					
					$("#add_tree_p1").children().remove();
					$("#add_tree_p2").children().remove();
					$.messager.alert('提示',data.errmsg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});		
	});	
	
	$('#add_tree_p1').tree({ 	
	    onClick: function(node){
	    	$("#add_tree_p2").children().remove();
	    	selid = node.id;    	    	 	
	    	show_add(node.id,node.text);	    		
	    } 	
	}); 
	
	//重置
	$('#b_reset').click(function (){
		$('#weinxin00301').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');
		//除去错误校验状态
		ydl.delErrorMessage('weinxin00301');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','微信菜单配置');		
		$('#formtitle').text('微信菜单配置');
		$('#fm').form('clear');
		$('#add_tree').children().remove();		
	});
	
	//修改
	$('#b_edit').click(function (){
		if(message.rows[0].storetype!=null){
			delete message.rows[0].storetype;
		}
		message.rows[0].function=null;
		var ro=message.rows;		
		var mo=JSON.stringify(ro);
		var arr = mo.substring(1).substring(0,mo.length-2);
		var arr1 = [{name : "value",value : arr}];
		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/weixinapi00302.json",
			dataType: "json",
			data:arr1,
			success : function(data) {
				if (data.errcode == 0) {
					$('#dlg').dialog('close');        	// close the dialog					
					$.messager.alert('提示','成功','info');
				}else{				
					$.messager.alert('提示',data.errmsg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});	
		$("#add_tree_p2").children().remove();
	});	
	
	$('#b_save').click(function (){
		if (!$("#fm").form("validate")) return;		
		$('#add_tree_p1').children().remove();			
		var t = $('#add_tree_p1');		
		message={rows:[{"regionId":$("#centerid").combobox('getValue'),"weixinId":$("#funcName").val(),"enable":true,"button":[],"function":null}]};	
		showTree();					
		$('#dlg').dialog('close');
	});
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	$('#b_save_key').click(function (){
		var row = $('#dg_key').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				$("#key_vaule").val(row[0].keyname);
				$('#'+$("#key_name").val()).val(row[0].keyname);
			}
		}
		else{
			$.messager.alert('提示', '请选中一条微信功能信息！', 'info' );
			return;
		}
		$('#dlg_key').dialog('close');
	});
	
	//取消
	$('#b_cancel_key').click(function (){
		$('#dlg_key').dialog('close');
	});
	
	//取消
	$('#b_cancel_uri').click(function (){
		$('#dlg_uri').dialog('close');
	});
	
	$('#b_save_uri').click(function (){	
		if (!$("#fm_uri").form("validate")) return;
		var urivalue = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+$("#appid").val()+'&redirect_uri=';
		//http%3A%2F%2Fwww.12329app.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state='+$("#state1").val()+'.'+$("#state2").val()+'#wechat_redirect
		if($("#uri").val().substring(0,7)=="http://"){			
			urivalue = urivalue+"http%3A%2F%2F"+$("#uri").val().substring(7).replace("/","%2F");
		}else if($("#uri").val().substring(0,8)=="https://"){			
			urivalue = urivalue+"https%3A%2F%2F"+$("#uri").val().substring(8).replace("/","%2F");
		}else{
			urivalue = urivalue+"http%3A%2F%2F"+$("#uri").val().replace("/","%2F");
		}
		urivalue = urivalue+'&response_type=code&scope=snsapi_base&state='+$("#state1").val()+'.'+$("#state2").val()+'#wechat_redirect';
		
		//$("#key_vaule").val(urivalue);
		$('#'+$("#uri_name").val()).val(urivalue);
		
		$('#dlg_uri').dialog('close');
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
	arrayObj4.splice(num,1);
}

function one_method(val,num){	
	if(1==val){
		var menu = $('#menuA'+num).val();
		var type = $("#typeA"+num).combobox('getValue');
		var value = $('#keyA'+num).val();		
		message.rows[0].button[num].name=menu;
		message.rows[0].button[num].type=type;		
		if(message.rows[0].button[num].url==null){
			if(type=='view'){
				delete message.rows[0].button[num].key;
				message.rows[0].button[num].url=value;
			}else{
				message.rows[0].button[num].key=value;
			}
		}else{
			if(type=='key'){
				delete message.rows[0].button[num].url;
				message.rows[0].button[num].key=value;
			}else{
				message.rows[0].button[num].url=value;				
			}
		}				
	}else if(2==val){
		var menu = $('#menuB'+num).val();
		message.rows[0].button[num].name=menu;		
	}else if(3==val){		
		var n1;
		var n2;
		num=num+'';
		if(num.length==1){
			n1 = '0';
			n2 = num;
		}else{
			n1 = num.substring(0,1);
			n2 = num.substring(1);
		}						
		var menu = $("#menuC"+n1+n2).val();
		var type = $("#typeC"+n1+n2).combobox('getValue');
		var value = $("#keyC"+n1+n2).val();
		message.rows[0].button[n1].sub_button[n2].name=menu;
		message.rows[0].button[n1].sub_button[n2].type=type;		
		if(message.rows[0].button[n1].sub_button[n2].url==null){
			if(type=='view'){
				delete message.rows[0].button[n1].sub_button[n2].key;
				message.rows[0].button[n1].sub_button[n2].url=value;
			}else{
				message.rows[0].button[n1].sub_button[n2].key=value;
			}
		}else{
			if(type=='key'){
				delete message.rows[0].button[n1].sub_button[n2].url;
				message.rows[0].button[n1].sub_button[n2].key=value;
				
			}else{
				message.rows[0].button[n1].sub_button[n2].url=value;
			}
		}		
	}else if(4==val){
		message.rows[0].weixinId = $("#weixincode").val();		
	}
	showTree();	
}

function delete_method(val,num){
	if(1==val){
		message.rows[0].button.splice(num,1);
	}else if(2==val){		
		if(confirm("确定要删除该菜单吗？")){	      
	       message.rows[0].button.splice(num,1);
	    }else{
	    	return;
	    }
	}else if(3==val){
		var n1;
		var n2;
		num=num+'';
		if(num.length==1){
			n1 = '0';
			n2 = num;
		}else{
			n1 = num.substring(0,1);
			n2 = num.substring(1);
		}		
		var menu = $("#menuC"+n1+n2).val();
		var type = $("#typeC"+n1+n2).combobox('getValue');
		var value = $("#keyC"+n1+n2).val();
		delete message.rows[0].button[n1].sub_button[n2].name;
		delete message.rows[0].button[n1].sub_button[n2].type;		
		if(message.rows[0].button[n1].sub_button[n2].url==null){			
			delete message.rows[0].button[n1].sub_button[n2].key;
		}else{			
			delete message.rows[0].button[n1].sub_button[n2].url;			
		}
		message.rows[0].button[n1].sub_button.splice(n2,1);
	}else{
		return;
	}	
	selid=null;
	showTree();
	$("#add_tree_p2").children().remove();
}

function showTree(){
	$('#add_tree_p1').children().remove();			
	var t = $('#add_tree_p1');						
	t.tree('append', 
				{parent: (null),
				 data: [{id:99999,text: $('#regionId option[value='+message.rows[0].regionId+']').text()}]});					
	for(var i=0;i<message.rows[0].button.length;i++){
		if(message.rows[0].button[i].sub_button==null){							
			var findid = t.tree('find',99999);
			if(findid!=null){									
				t.tree('append', 
					{parent: (findid.target),
					 data: [{id:'A'+i,text: message.rows[0].button[i].name}]});									
			}							
		}else{		
			var findid = t.tree('find',99999);
			if(findid!=null){
				t.tree('append',{parent: (findid.target),
				 data: [{id:'B'+i,text: message.rows[0].button[i].name}]});
			}
			for(var j=0;j<message.rows[0].button[i].sub_button.length;j++){
				var findid = t.tree('find','B'+i);
				if(findid!=null){	
					var num = 'C'+i+j;											
					t.tree('append', 
						{parent: (findid.target),
						 data: [{id:num,text: message.rows[0].button[i].sub_button[j].name}]});										
				}
			}
		}
	}	
	if(selid!=null){
		$("#add_tree_p2").children().remove();
		var fidid = t.tree('find',selid);
		show_add(fidid.id,fidid.text);	
	}	
}



function add_method(num,val,id){	
	if(1==num){
		if(message.rows[0].button.length<3){
			var menu = $("#add_tree_1_name").val();
			var type = $('#add_tree_1_type').combobox('getValue');			
			var value = $("#add_tree_1_value").val();
			if(menu==null||type==null||value==null||menu==''||type==''||value==''){
				alert("参数不可以为空！");
				return ;
			}else{
				var n = message.rows[0].button.length;
				if('click'==type){
					message.rows[0].button.splice(n,0,{'name':menu,'type':type,'key':value});
				}else{
					message.rows[0].button.splice(n,0,{'name':menu,'type':type,'url':value});
				}
			}
		}else{
			alert("主菜单不可以超过3个！");
			return ;
		}
	}else if(2==num){
		if(message.rows[0].button.length<3){
			var menu = $("#add_tree_2_name").val();
			if(menu==null||menu==''){
				alert("参数不可以为空！");
				return ;
			}else{
				var n = message.rows[0].button.length;
				message.rows[0].button.splice(n,0,{'name':menu,'sub_button':[]});				
			}
		}else{
			alert("主菜单不可以超过3个！");
			return ;
		}
	}else{		
		var menu = $("#add_tree_3_name").val();
		var type = $("#add_tree_3_type").combobox('getValue');
		var value = $("#add_tree_3_value").val();		
		if(menu==null||type==null||value==null||menu==''||type==''||value==''){
			alert("参数不可以为空！");
			return ;
		}
		if(message.rows[0].button[val].sub_button==null){
			message.rows[0].button[val]={"sub_button":[]};
			if('click'==type){				
				message.rows[0].button[val].sub_button.splice(0,0,{'name':menu,'type':type,'key':value});
			}else{
				message.rows[0].button[val].sub_button.splice(0,0,{'name':menu,'type':type,'url':value});
			}
		}else{
			if(message.rows[0].button[val].sub_button.length<5){
				var n = message.rows[0].button[val].sub_button.length;
				if('click'==type){
					message.rows[0].button[val].sub_button.splice(n,0,{'name':menu,'type':type,'key':value});
				}else{
					message.rows[0].button[val].sub_button.splice(n,0,{'name':menu,'type':type,'url':value});
				}
			}else{
				alert("子菜单不可以超过5个！");
				return; 
			}
		}	
	}
	//$("#add_tree_p2").children().remove();
	selid = id;	
	showTree();	
}

function more_method(more,num,val){	
	if(1==num){
		if(1==more){					
			if(0!=val){					
				selid = selid.substring(0,1)+(parseInt(selid.substring(1)-1));
				var y = message.rows[0].button[val];
				var m = message.rows[0].button[val-1];				
				message.rows[0].button.splice(val-1,1,y);
				message.rows[0].button.splice(val,1,m);
			}else{
				alert("当前位置不可以上移！");
				return;
			}	
		}else{	
			selid = selid.substring(0,1)+(parseInt(selid.substring(1))+1);
			var lg = message.rows[0].button.length;			
			if((lg-1)!=val){
				var y = message.rows[0].button[val];
				var le = 1+parseInt(val);				
				var m = message.rows[0].button[le];				
				message.rows[0].button.splice(val,1,m);
				message.rows[0].button.splice(le,1,y);				
			}else{
				alert("当前位置不可以下移！");
				return;
			}
		}
	}else if(2==num){
		if(1==more){					
			if(0!=val){							
				selid = selid.substring(0,1)+(parseInt(selid.substring(1)-1));												
				var y = message.rows[0].button[val];
				var m = message.rows[0].button[val-1];				
				message.rows[0].button.splice(val-1,1,y);
				message.rows[0].button.splice(val,1,m);
			}else{
				alert("当前位置不可以上移！");
				return;
			}	
		}else{
			var lg = message.rows[0].button.length;			
			if((lg-1)!=val){
				selid = selid.substring(0,1)+(parseInt(selid.substring(1))+1);				
				var y = message.rows[0].button[val];
				var le = 1+parseInt(val);				
				var m = message.rows[0].button[le];				
				message.rows[0].button.splice(val,1,m);
				message.rows[0].button.splice(le,1,y);				
			}else{
				alert("当前位置不可以下移！");
				return;
			}
		}
	}else if(3==num){
		var n1;
		var n2;
		val=val+'';
		if(val.length==1){
			n1 = '0';
			n2 = val;
		}else{
			n1 = val.substring(0,1);
			n2 = val.substring(1);
		}
		if(1==more){					
			if(0!=n2){	
				selid = selid.substring(0,2)+(parseInt(selid.substring(2)-1));							
				var y = message.rows[0].button[n1].sub_button[n2];
				var m = message.rows[0].button[n1].sub_button[n2-1];				
				message.rows[0].button[n1].sub_button.splice(n2-1,1,y);
				message.rows[0].button[n1].sub_button.splice(n2,1,m);
			}else{
				alert("当前位置不可以上移！");
				return;
			}	
		}else{		
			var lg = message.rows[0].button[n1].sub_button.length;			
			if((lg-1)!=n2){
				selid = selid.substring(0,2)+(parseInt(selid.substring(2))+1);				
				var y = message.rows[0].button[n1].sub_button[n2];
				var le = 1+parseInt(n2);				
				var m = message.rows[0].button[n1].sub_button[le];				
				message.rows[0].button[n1].sub_button.splice(n2,1,m);
				message.rows[0].button[n1].sub_button.splice(le,1,y);				
			}else{
				alert("当前位置不可以下移！");
				return;
			}
		}
	}
	//$("#add_tree_p2").children().remove();	
	showTree();
}

function show_add(nodeid,text){	
	var id = nodeid;
	if(typeof(id)=="string"){	 
   		if(id.indexOf('A')==0){
   			var num=id.substring(1);	    			
   			var livalue = '<tr><td>菜单名称 :</td><td> <input type="text" id="menu'+nodeid+'" name="menu'+nodeid+'" value="'+text+'"  class="easyui-validatebox"	style="width: 180px;"/></td>';		    		
    		//$("#add_tree_p2").append(livalue);
    		livalue = livalue +'<td>    菜单类型 : </td><td><select id="type'+nodeid+'" name="type'+nodeid+'" class="easyui-combobox" style="width:180px;" panelHeight="auto"></select></td><td></td></tr>';		    		
    		$("#add_tree_p2").append(livalue);	
    		$("#type"+nodeid).combobox({
			 	data:istype,
			 	valueField:'value',
				textField:'text'
		 	});
		 	$("#type"+nodeid).combobox('setValue',message.rows[0].button[num].type);   		
    		
    		if(message.rows[0].button[num].url==null){
    			livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="key'+nodeid+'" name="key'+nodeid+'" value="'+message.rows[0].button[num].key+'" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st1" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(1)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(1)">设置对应链接</a></td></tr>';
    		}else{
    			livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="key'+nodeid+'" name="key'+nodeid+'" value="'+message.rows[0].button[num].url+'" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st1" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(1)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(1)">设置对应链接</a></td></tr>';
    		}
    		$("#add_tree_p2").append(livalue);
    		
    		
    		livalue = '<tr><td><a id="one_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="one_method(1,'+num+')">确认修改</a></td>';
    		livalue = livalue + '<td><a id="one_del" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="delete_method(1,'+num+')">确认删除</a></td>';
    		livalue = livalue + '<td><a id="more_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(1,1,'+num+')">上移</a></td>';
    		livalue = livalue + '<td><a id="more_down" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(2,1,'+num+')">下移</a></td><td></td>';
    		$("#add_tree_p2").append(livalue);
    				    		
   		}else if(id.indexOf('B')==0){
   			var num=id.substring(1);
   			var livalue = '<tr><td>    菜单名称 :</td><td colspan=4> <input type="text" id="menu'+nodeid+'" name="menu'+nodeid+'" value="'+text+'" class="easyui-validatebox"	style="width: 180px;"/></td></tr>';
   			livalue = livalue + '<tr><td><a id="two_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="one_method(2,'+num+')">确认修改</a></td>';		    		
    		livalue = livalue + '<td><a id="one_del" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="delete_method(2,'+num+')">确认删除</a></td>';
    		livalue = livalue + '<td><a id="more_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(1,2,'+num+')">上移</a></td>';
    		livalue = livalue + '<td><a id="more_down" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(2,2,'+num+')">下移</a></td><td></td>';
    		$("#add_tree_p2").append(livalue);
    		livalue = '<tr><td colspan=5></td><tr><tr><td colspan=5>子菜单添加</td></td>';
   		 	livalue = livalue + '<tr><td>菜单名称 : </td><td><input type="text" id="add_tree_3_name" name="add_tree_3_name" class="easyui-validatebox"	style="width: 180px;"/></td>';
   		 	livalue = livalue + '<td>菜单类型 : </td><td><select id="add_tree_3_type" name="add_tree_3_type" class="easyui-combobox" style="width:180px;" panelHeight="auto"></select></td><td></td></tr>';
   		 	$("#add_tree_p2").append(livalue);	
   		 	$("#add_tree_3_type").combobox({
			 	data:istype,
			 	valueField:'value',
				textField:'text'
		 	});
   		 	livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="add_tree_3_value" name="add_tree_3_value" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(3)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(3)">设置对应链接</a></td><tr>';
   		 	livalue = livalue + '<tr><td colspan=5><a id="add_tree_3" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="add_method(3,'+num+')">确认添加</a></td></tr>';
   		 	$("#add_tree_p2").append(livalue);	
   		}else if(id.indexOf('C')==0){	    			
   			var num=id.substring(1).substring(0,1);	    			
   			var number=id.substring(1).substring(1);	    			
   			var livalue = '<tr><td>    菜单名称 : </td><td><input type="text" id="menu'+nodeid+'" name="menu'+nodeid+'" value="'+text+'" class="easyui-validatebox"	style="width: 180px;"/></td>';
    		livalue = livalue+'<td>    菜单类型 : </td><td><select id="type'+nodeid+'" name="type'+nodeid+'" class="easyui-combobox" style="width:180px;" panelHeight="auto"></select></td><td></td></tr>';		    		
    		
    		$("#add_tree_p2").append(livalue);	
    		$("#type"+nodeid).combobox({
			 	data:istype,
			 	valueField:'value',
				textField:'text'
		 	});
		 	$("#type"+nodeid).combobox('setValue',message.rows[0].button[num].sub_button[number].type);  
    		
    			    		
    		if(message.rows[0].button[num].sub_button[number].url==null){
    			livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="key'+nodeid+'" name="key'+nodeid+'" value="'+message.rows[0].button[num].sub_button[number].key+'" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st2" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(1)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(1)">设置对应链接</a></td><tr>';
    		}else{
    			livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="key'+nodeid+'" name="key'+nodeid+'" value="'+message.rows[0].button[num].sub_button[number].url+'" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st2" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(1)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(1)">设置对应链接</a></td><tr>';
    		}
    		var nn=id.substring(1);
    		livalue = livalue + '<tr><td><a id="one_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="one_method(3,'+nn+')">确认修改</a></td>';
    		livalue = livalue + '<td><a id="one_del" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="delete_method(3,'+nn+')">确认删除</a></td>';
    		livalue = livalue + '<td><a id="more_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(1,3,'+nn+')">上移</a></td>';
    		livalue = livalue + '<td><a id="more_down" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="more_method(2,3,'+nn+')">下移</a></td><td></td></tr>';
    		$("#add_tree_p2").append(livalue);
   		}
   	}else{	    		 
   		 var livalue = '<tr><td>微信账号 : </td><td colspan=4><input type="text" id="weixincode" name="weixincode" value="'+message.rows[0].weixinId+'" class="easyui-validatebox"	style="width: 180px;"/></td></tr>';
   		
   		 livalue = livalue + '<tr><td colspan=5><a id="one_up" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="one_method(4,0)">确认修改</a></td></tr>';
   		 livalue = livalue + '<tr><td colspan=5>无子菜单添加</td></tr>';
   		 livalue = livalue + '<tr><td colspan=5></td><tr><tr><td>菜单名称 : </td><td><input type="text" id="add_tree_1_name" name="add_tree_1_name" class="easyui-validatebox"	style="width: 180px;"/></td>';
   		 livalue = livalue + '<td>菜单类型 : </td><td><select id="add_tree_1_type" name="add_tree_1_type" class="easyui-combobox" style="width:180px;" panelHeight="auto"></select></td><td></td></tr>';
 		 $("#add_tree_p2").append(livalue);	
 		 $("#add_tree_1_type").combobox({
			 data:istype,
			 valueField:'value',
			 textField:'text'
		 });		
   		 livalue = '<tr><td>菜单参数 : </td><td colspan=3><input type="text" id="add_tree_1_value" name="add_tree_1_value" class="easyui-validatebox"	style="width: 470px;"/></td><td><a id="st3" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(2)">选择对应功能</a><br><a id="st4" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectUri(2)">设置对应链接</a></td></tr>';
   		 livalue = livalue + '<tr><td colspan=5><a id="add_tree_1" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="add_method(1,0)">确认添加</a></td></tr>';
   		 $("#add_tree_p2").append(livalue);	
   		 livalue = '<tr><td colspan=5>有子菜单添加</td></tr><tr><td colspan=5></td><tr>';
   		 livalue = livalue + '<tr><td>菜单名称 : </td><td colspan=4><input type="text" id="add_tree_2_name" name="add_tree_2_name" class="easyui-validatebox"	style="width: 180px;"/></td></tr>';
   		 livalue = livalue + '<tr><td colspan=5><a id="add_tree_2" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="add_method(2,0)">确认添加</a></td></tr>';
   		 $("#add_tree_p2").append(livalue);	    		 
   	}
}

function selectType(val){
	$('#dlg_key').dialog('open').dialog('setTitle','微信菜单功能配置');		
	$('#fm_key').form('clear');
	$('#formtitle_key').text('微信菜单功能配置');
	if(1==val){		
		$('#key_name').val("key"+selid);
	}else if(2==val){		
		$('#key_name').val("add_tree_1_value");
	}else if(3==val){
		$('#key_name').val("add_tree_3_value");
	}else if(4==val){
		$('#key_name').val("state2");
	}
}

function selectUri(val){
	$('#dlg_uri').dialog('open').dialog('setTitle','微信菜单链接配置');		
	$('#fm_uri').form('clear');
	$('#formtitle_uri').text('微信菜单链接配置');	
	if(1==val){		
		$('#uri_name').val("key"+selid);
	}else if(2==val){		
		$('#uri_name').val("add_tree_1_value");
	}else if(3==val){
		$('#uri_name').val("add_tree_3_value");
	}
}

</script>
	</head>

	<body>
		<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script>		
		<div class="easyui-panel" title="微信菜单查询">
			<form id="weinxin00301" class="dlg-form" novalidate="novalidate">
				<table class="container">
					<col width="50%" />
					<col width="50%" />					
					<tr>
						<th>
							<label for="regionId">
								城市中心:
							</label>
						</th>
						<td>
							<select id="regionId" name="regionId" class="easyui-combobox" editable="false" style="width:250px;" ><!-- panelHeight="auto" -->
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
				</div>
			</form>
		</div>
		
		<div id="panel2" class="easyui-panel" title="微信菜单"
							style="height: 410px; padding: 10px 20px">
			<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">增加</a>
			<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">保存</a>			
			<!-- 
			<table id="dg" class="container">
				<col width="40%" />
				<col width="60%" />
				<tr>
					<td>											
						<div id="p1" class="easyui-panel" title="菜单样式" style="width:400px;height:330px;">
							<ul class="easyui-tree" id="add_tree_p1" name="add_tree_p1" data-options="animate: true">
							</ul>
						</div>
					</td>
					<td>
						<div id="p2" class="easyui-panel" title="配置属性" style="width:650px;height:330px;">							
							<ul id="add_tree_p2" name="add_tree_p2" data-options="animate: true">
							</ul>
						</div>
					</td>
				</tr>				
			</table>
			 -->
			<div id="cc"  class="easyui-layout" style="height:410px;">
			     <div region="west" split="true" title="菜单样式" style="width:350px;">
			     	<ul class="easyui-tree" id="add_tree_p1" name="add_tree_p1" data-options="animate: true">
					</ul>
			     </div>  
			     <div region="center" title="配置属性" style="padding:5px;background:#eee;">
			     	<!-- <ul id="add_tree_p2" name="add_tree_p2" data-options="animate: true">
					</ul> -->
					<table class="container" id="add_tree_p2" name="add_tree_p2" style="border:0">
					</table>
					<div id="mx_button" class="buttons">
						
					</div>
			     </div>  
			</div> 
			
		</div>
		<div id="dlg" class="easyui-dialog"
			style="width: 400px; height: 270px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons">
			<div class="formtitle" id="formtitle"></div>
			<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<tr>
						<td>
							<label for="centerid">
								城市中心:
							</label>
						</td>
						<td>
							<select id="centerid" name="centerid" class="easyui-combobox" required="true"
								editable="false" style="width: 200px;" ><!-- panelHeight="auto" -->
								<%
									String options = "";
									List<Mi001> ary = (List<Mi001>) request.getAttribute("mi001list");
									for (int i = 0; i < ary.size(); i++) {
										Mi001 mi001 = ary.get(i);
										String itemid = mi001.getCenterid();
										String itemval = mi001.getCentername();
										options = (new StringBuilder(String.valueOf(options))).append(
												"<option value=\"").append(itemid).append("\">")
												.append(itemval).append("</option>\n").toString();
									}
									out.println(options);
								%>

							</select>
						</td>
					<tr>
						<td>
							<label for="funcName">
								中心微信账号:
							</label>
						</td>
						<td>
							<input type="text" id="funcName" name="funcName" required="true"
								validType="length[0,200]" class="easyui-validatebox"	style="width: 200px;" />
						</td>
					</tr>
					<!-- 
					<tr>
						<td>
							<label for="istrue">
								是否可用:
							</label>
						</td>
						<td>
							<select id="istrue" name="istrue" required="true" class="easyui-combobox"	editable="false" style="width: 130px;" panelHeight="auto">
								<option value="true">可用</option>
								<option value="false">不可用</option>
							</select>
						</td>
					</tr>
					 -->
				</table>
			</form>					
		</div>
		<div id="dlg-buttons">
			<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">保存</a>
			<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		
		<div id="dlg_key" class="easyui-dialog"
			style="width: 750px; height: 470px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons-key">
			<div class="formtitle" id="formtitle_key"></div>
			<table id="dg_key"></table>			
		</div>
		<div id="dlg-buttons-key">
			<a id="b_save_key" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">保存</a>
			<a id="b_cancel_key" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		<input type="hidden" id="key_vaule" name="key_vaule" class="easyui-validatebox" style="width: 130px;" />
		<input type="hidden" id="key_name" name="key_name" class="easyui-validatebox" style="width: 130px;" />
		
		<div id="dlg_uri" class="easyui-dialog"
			style="width: 450px; height: 270px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons-uri">
			<div class="formtitle" id="formtitle_uri"></div>
			<form id="fm_uri" class="dlg-form" method="post" novalidate="novalidate">
				<table id="dg_key" class="dlgcontainer">				
					<tr>
						<td>
							<label for="appid">
								appid:
							</label>
						</td>
						<td>
							<input type="text" id="appid" name="appid" required="true"
								validType="length[0,200]" class="easyui-validatebox"	style="width: 200px;" />
						</td>
					<tr>
						<td>
							<label for="uri">
								uri:
							</label>
						</td>
						<td>
							<input type="text" id="uri" name="uri" required="true"
								validType="length[0,200]" class="easyui-validatebox"	style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="state1">
								微信账号:
							</label>
						</td>
						<td>
							<input type="text" id="state1" name="state1" required="true"
								validType="length[0,200]" class="easyui-validatebox"	style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<td>
							<label for="state2">
								功能:
							</label>
						</td>
						<td>
							<input type="text" id="state2" name="state2" required="true"
								validType="length[0,200]" class="easyui-validatebox"	style="width: 200px;" />
							<a id="st5" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="selectType(4)">功能</a>
						</td>
					</tr>					
				</table>	
			</form>		
		</div>
		<div id="dlg-buttons-uri">
			<a id="b_save_uri" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">保存</a>
			<a id="b_cancel_uri" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		<input type="hidden" id="uri_value" name="key_uri" class="easyui-validatebox" style="width: 130px;" />
		<input type="hidden" id="uri_name" name="key_uri" class="easyui-validatebox" style="width: 130px;" />
		
		
	</body>
</html>
