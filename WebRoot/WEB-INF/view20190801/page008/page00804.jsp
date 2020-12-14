<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi202"%>
<%@page import="com.yondervision.mi.dto.Mi201"%>
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
<title>test</title>
<%@ include file = "/include/styles.jsp" %>
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
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7db9536a2794ce477288908163157bc6"></script>
<script type="text/javascript">
var url;
var addBanks = [];
$(function() {
	//屏蔽错误
	window.onerror=function(){
		return true;
	}
	//列表初始化
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'楼盘详细信息',
		singleSelect: true,
		height:369,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi00804.json",
		queryParams:arrToJson($('#form00804').serializeArray()),
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.housesId + '"/>';
			}},
			{title:'楼盘编码',field:'houseCode',align:'center',width:80,editor:'text'},
			{title:'所属区域',field:'areaId',width:70,align:'center',formatter:function(value,row,index) {	
				return $('#areaId option[value='+value+']').text(); 
			}},
			{title:'楼盘名称',field:'houseName',align:'center',width:130,editor:'text'},
			{title:'开发商名称',field:'developerName',align:'center',width:180,editor:'text'},
			{title:'楼盘地址',field:'address',align:'center',width:80,editor:'text'},
			{title:'楼盘类型',field:'houseType',align:'center',width:70,editor:'text'},
			{title:'联系人姓名',field:'contacterName',align:'center',width:120,editor:'text'},
			{title:'联系电话',field:'tel',align:'center',width:60,editor:'text'},
			{title:'合作银行',field:'bankNames',align:'center',width:60,editor:'text'},
			{title:'X坐标',field:'positionX',align:'center',width:90,editor:'text'},
			{title:'Y坐标',field:'positionY',align:'center',width:90,editor:'text'},
			{title:'图片',field:'imageUrl',hidden:true,align:'center',width:1},
			{title:'楼盘ID',field:'housesId',hidden:true,align:'center',width:1}
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【查询出错】：'+data.msg, 'info' );
        }		
	});
	//选择合作银行数据
	<%
		String data = "";
		List<Mi201> ary1=(List<Mi201>) request.getAttribute("mi201list");
		for(int i=0;i<ary1.size();i++){
			Mi201 mi201=ary1.get(i);
			String id=mi201.getWebsiteId();
			String val=mi201.getWebsiteName();
			data = (new StringBuilder(String.valueOf(data))).append(",{ value:\"").append(id).append("\",text:\"").append(val).append("\"}").toString();
		}
		if (ary1.size() == 0) data = "";
		else data = data.substring(1);
		out.println("var data = ["+data+"];");
	%>
	
	var banksObj = {};
	$.each(data,function (index,ele){
		banksObj[ele.text] = ele.value;
	});

	$('#bankNames1').prop('readOnly',true);
	//选择合作银行下拉列表
	$('#setbankNames').combobox({   
		valueField:'value',   
		textField:'text',
		width:205,
		panelHeight:298,
		hasDownArrow:false,
		onHidePanel:function (){
			if ($('#setbankNames').next().is(':visible')) $('#setbankNames').combobox('showPanel');
		},
		onChange:function (newValue, oldValue){
			$('#setbankNames').combobox('showPanel');
		},
		onSelect:function (record){
			$('#b_right').data('bankName',record.text);
		},
		data:data	
	}); 
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
		buttons:[{
			text:'确定',
			handler:function(){
				var value = $('#selnames ul li').map(function(index){
					return $(this).html();
				}).get().join(',');
				//$('#selectdBanks').val(value);
				//if ($('#definedBanks').val() != '') value = value + ',' + $('#definedBanks').val();
				$('#bankNames1').val(value);
				$('#dlgBankNames').dialog('close');
			}
		},{
			text:'重置',
			handler:function(){
				$('#setbankNames').combobox('loadData',data);
				$('#selnames ul li').remove();
				addBanks = [];
				$('#setbankNames').combobox('setText','');
				$('.combobox-item:visible').removeClass('combobox-item-selected');
			}
		},{
			text:'关闭',
			handler:function(){
				$('#dlgBankNames').dialog('close');
			}
		}]
	});
	
	//选择合作银行按钮
	$('#setBanks').click(function (){ 
		$('#dlgBankNames').show().dialog('open');
		//var data = $('#setbankNames').combobox('getData');
		$('#setbankNames').combobox('loadData',data);
		$('#setbankNames').combobox('showPanel');
		ydl.setDlgPosition('dlgBankNames');
		var names = $('#bankNames1').val();
		addBanks = names.split(/[,]/);
		
		//设置选择对话框默认值
		$('#selnames ul li').remove();
		//var definedBanks = '';
		$.each(addBanks,function (index,value){
			if (value != '' && banksObj[value]) {
				var $comboItem = $('.combobox-item[value='+banksObj[value]+']').detach();
				var $li = $('<li>'+value+'</li>');
				$li.data('item',$comboItem)
				$('#selnames ul').append($li);
			}
			//if (value != '' && !banksObj[value]) definedBanks = definedBanks + ',' + value;
			if (value != '' && !banksObj[value]) {
				var $li = $('<li>'+value+'</li>');
				$('#selnames ul').append($li);
			}
		});
		//if (definedBanks != '') $('#definedBanks').val(definedBanks.substring(1))
		//else $('#definedBanks').val('');
		
	});
	
	//右移按钮
	$('#b_right').click(function (){
		var bankName = $('#setbankNames').combobox('getText').trim();
		if ($('.combobox-item-selected:visible').length == 0 && bankName == '') {
			//$.messager.alert('提示', '请在左侧选择待添加的合作银行！', 'info');
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
				var $li = $('<li>'+$('#b_right').data('bankName')+'</li>');
				$li.data('item',$comboItem)
				$('#selnames ul').append($li);
			}
			//自定义添加
			else {
				$('#selnames ul').append('<li>'+bankName+'</li>');
			}
			//添加后清空文本框
			$('#setbankNames').combobox('setText','');
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
			$('.combo-panel:visible').append($li.data('item').removeClass('combobox-item-selected'));
		}
		$li.remove();
		addBanks.splice($.inArray(bankName,addBanks),1);
	});
	
})

//增加
function newUser(){
	//$('#houseCode').prop('disabled',false);            
	$('#dlg').dialog('open').dialog('setTitle','楼盘信息增加');
	$('#fm').form('clear');
	$('#houseCode')[0].focus();
	url = "<%=request.getContextPath()%>/webapi00801.json";
}
//修改
function editUser(){        
	var row = $('#dg').datagrid('getSelections');
	//$('#houseCode').prop('disabled',true);            
	if(row.length==1){
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','楼盘信息修改');
			$('#fm').form('load',row[0]);
			url = "<%=request.getContextPath()%>/webapi00803.json";
		}
	}
	else $.messager.alert('提示', "请选中一条待修改楼盘信息！", 'info' );
}
//保存
function saveUser(){            
	var arr = $('#fm').serializeArray();
	//校验
	//if (!ydl.formValidate('fm')) return;
	if (!$("#fm").form("validate")) return;		
	$.ajax({
		type : "POST",
		url : url,
		dataType: "json",
		data:arr,
		success : function(data) {					
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
			else {
				$.messager.alert('提示', data.msg, 'info' );
				$('#dlg').dialog('close');        // close the dialog
				$('#dg').datagrid('reload');    // reload the user data
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});						
}
//删除
function destroyUser(){
	//var row = $('#dg').datagrid('getSelections');           
	//if (row.length > 0){
	//	var code=row[0].housesId;            	
	//	for(var i=1;i<row.length;i++){
	//		code = code + ","+row[i].housesId;
	//	} 
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
		var code = $checkboxs.map(function(index){
			return this.value;
		}).get().join(',');
		var arr = [{name : "listHousesId",value : code}];
		$.messager.confirm('提示','确认是否删除?',function(r){
			if(r){
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi00802.json",
					dataType: "json",
					data:arr,
					success : function(data) {
						if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【删除出错】：'+data.msg, 'info' );
						else {
							$.messager.alert('提示', data.msg, 'info' );						
							$('#dg').datagrid('reload');    // reload the user data
						}
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');							
					}
				});
			}					
		});
	}
	else $.messager.alert('提示', "请选中待删除信息！", 'info' );
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}

//查询
function select00804() {
	//校验
	//if (!ydl.formValidate('form00804')) return;
	if (!$("#form00804").form("validate")) return;
	//组织提交数据
	var arr = $('#form00804').serializeArray();
	var dataJson = arrToJson(arr);
	//datagrid列表
	$('#dg').datagrid({   
		url: "<%=request.getContextPath()%>/webapi00804.json",
		queryParams:dataJson	
	});	
}
//重置
function cancel20101() {	
	document.getElementById("form00804").reset();
}

//批量导入
function batchUpload(){
    $('#dlgBatchUpload').dialog('open').dialog('setTitle','楼盘信息-批量导入');
}

//批量导入提交
function submit_form() {
	var excelFile=$('#excelfile').val();
	if(null == excelFile || "" == excelFile){
		$.messager.alert('提示', "请填写上传路径后进行提交", 'error');
		return false;
	}
	$('#uploadform').ajaxSubmit({
		dataType : "json",
		success : function(data) {
						
    		if(data.recode == SUCCESS_CODE){
    			$.messager.confirm('文件检查通过','是否执行批量导入？',function(r){
           			if(r){
	           			var citycode = $('#citycode').val();
		    			var cityname = $('#cityname').val();
		    			var path = $('#path').val();
		    			var ruleMapStr = $('#ruleMapStr').val();    			
		    			var tableName = $('#tableName').val();
		    			var arr = [
							{name : "cityname",value : cityname},
							{name : "citycode",value : citycode},
							{name : "path",value : path},
							{name : "ruleMapStr",value : ruleMapStr},
							{name : "tableName",value : tableName},
							{name : "fileName",value : data.checkfile}					
						];
		    			
		    			$.ajax({
							type : 'POST',
							url : "<%=request.getContextPath()%>/excelMapToDB.do",
							dataType: 'json',
							data:arr,
							success : function(data) {
							
								if (data.recode == SUCCESS_CODE) {
									$.messager.alert('提示',data.msg,'info');
									$('#dg').datagrid('reload');
		           					$('#dlgBatchUpload').dialog('close');
								}
								else $.messager.alert('提示',data.msg,'info');
							},
							error :function(){
								$.messager.alert('错误','网络连接出错！','error');
							}
						});
					  }	    			
					});
           	}else if(data.recode == "000001"){     
           		$.messager.confirm('文件检查异常','是否下载检查结果？',function(r){
           			if(r){
           				window.location.href = data.checkfile;
           			}
				});
           	}  	
		},
		error:function() {
			$.messager.alert('错误', '网络连接出错！', 'error');
		}
	});
}

</script>
</head>
<body>
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script><!-- jquery.form.js 为页面提交json请求应用 -->
<!-- 查询条件 -->
<div class="easyui-panel" title="楼盘信息查询">
	<form id="form00804" class="dlg-form" novalidate="novalidate">
		<table class="container">
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="houseName">楼盘名称:</label></th>
				<td><input type="text" id="houseName" name="houseName" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
				<th><label for="developerName">开发商名称:</label></th>
				<td><input type="text" id="developerName" name="developerName" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
			</tr>
			<tr>		           
				<th><label for="address">楼盘地址:</label></th>
				<td><input type="text" id="address" name="address" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]" class="easyui-validatebox"/></td>
				<th><label for="houseType">楼盘类型:</label></th>
				<td><input type="text" id="houseType" name="houseType" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" class="easyui-validatebox"/></td>
			</tr>		       
			<tr>
				<th><label for="bankNames">合作银行:</label></th>
				<td><input type="text" id="bankNames" name="bankNames" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
				<th></th>
				<td>
				</td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select00804()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" onclick="cancel20101()">重置</a></div>
	</form>
</div>
<input type = "hidden" id= "cityNameHidden" value = "<%=user.getFreeUse1() %>"/>
<!-- 列表数据 -->
<table id="dg" ></table>
<div id="toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="editMap()">地图信息</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import-excel" plain="true" onclick="batchUpload()">批量导入</a>
	<a href="downloadimg.file?filePathParam=import_file_path&fileName=lpxxmb.xls&isFullUrl=false" class="easyui-linkbutton" iconCls="icon-download-exceltemp" plain="true">模板下载</a>
</div>
<!-- 添加/修改对话框 -->
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true" modal="true" buttons="#dlg-buttons">  
	<div class="formtitle">楼盘信息</div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<tr>
				<td><label for="houseCode">楼盘编码:</label></td>
				<td><input type="text" id="houseCode" name="houseCode" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,20]"/></td>			        	
			</tr>
			<tr>
				<td><label for="areaId">楼盘区域:</label></td>
				<td>
					<select id="areaId" name="areaId" class="easyui-combobox" editable="false" required="true" style="width:130px;" required="true" panelHeight="auto">			        	
						<%
							String options = "";
							List<Mi202> ary=(List<Mi202>) request.getAttribute("mi202list");
							for(int i=0;i<ary.size();i++){
								Mi202 mi202=ary.get(i);
								String itemid=mi202.getAreaId();
								String itemval=mi202.getAreaName();
								options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options);
						%>
					</select>    
				</td>
			</tr>
			<tr>	        
				<td><label for="houseName">楼盘名称:</label></td>
				<td><input type="text" id="houseName" name="houseName" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
			</tr>
			<tr>
				<td><label for="developerName">开发商名称:</label></td>
				<td><input type="text" id="developerName" name="developerName" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
			</tr>
			<tr>		           
				<td><label for="address">楼盘地址:</label></td>
				<td><input type="text" id="address" name="address" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]"/></td>
			</tr>
			<tr>
				<td><label for="houseType">楼盘类型:</label></td>
				<td><input type="text" id="houseType" name="houseType" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]"/></td>
			</tr>
			<tr>
				<td><label for="contacterName">联系人姓名:</label></td>
				<td><input type="text" id="contacterName" name="contacterName" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,20]"/></td>		        
			</tr>
			<tr>
				<td><label for="tel">联系电话:</label></td>
				<td><input type="text" id="tel" name="tel" required="true" class="easyui-validatebox" validType="phone" maxlength="20"/></td>
			</tr>
			<tr>
				<td><label for="bankNames1">合作银行:</label></td>
				<td>
					<input type="text" id="bankNames1" name="bankNames" class="easyui-validatebox"/>
					<a id="setBanks" href="javascript:void(0)" class="easyui-linkbutton" >选择...</a>
					<input id="housesId" name="housesId" type='hidden' class="easyui-validatebox"/>		            
				</td>
			</tr>
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>
<!-- 地图 -->
<div id="dlgMap" class="easyui-dialog" style="width:850px;height:500px;padding:1px 2px" closed="true" modal="true" buttons="#dlgMap-buttons">            
	<table>
		<th><label for="mapcityname">城市:</label></th>
		<td><input type="text" id="mapcityname" name="mapcityname" class="easyui-validatebox" validType="length[0,40]"/></td>
		<th><label for="mapaddress">详细地址:</label></th>
		<td><input type="text" id="mapaddress" name="mapaddress" class="easyui-validatebox" validType="length[0,100]"/></td>
		<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="selectMap()">定位</a></td>
		<td>
			<form id="formUpImg" name="formUpImg" method="post" enctype="multipart/form-data" novalidate="novalidate">
				<table>
					<th><label for="imageurl">图片:</label></th>
					<td>			    
					<input type="hidden" name="magecenterId" value="<%=user.getCenterid() %>"/>
					<input type="hidden" name="imageid" id="imageid" value=""/>
					<input type="file" name="file" id="file" onchange="upImg()" class="easyui-validatebox" validType="fileType[['PNG']]" invalidMessage="请选择(PNG)等格式的图片"/></td>		    	    
				</table> 
			</form>   
		</td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="editMapImage()">图片预览</a>
		</td>
	</table>
	<div id="allmap"></div>
</div>
<div id="dlgMap-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveMap()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgMap').dialog('close')">关闭</a>
</div>   
<!-- 批量导入 -->
<div id="dlgBatchUpload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true" buttons="#dlgExcel-buttons">
	<div class="formtitle">楼盘信息批量导入</div>
	<form id = "uploadform" class="dlg-form" method="post" action="excelMapToCheck.do" ENCTYPE="multipart/form-data" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="excelfile">上传路径:</label></td>
				<td><input type="file" id="excelfile" name="excelfile" required="true"/></td>
			</tr>
		</table>
		<input type="hidden" id="citycode" name="citycode" value="<%=user.getCenterid() %>"/>
		<input type="hidden" id="cityname" name="cityname" value="<%=user.getCenterName() %>"/>
		<input type="hidden" id="path" name="path" value=""/>
		<input type="hidden" id="ruleMapStr" name="ruleMapStr" value="{houseCode:'#2',houseName:'#3',developerName:'#4',areaId:'#5',houseType:'#6',address:'#7',contacterName:'#8',tel:'#9',bankNames:'#10'}"/>
		<input type="hidden" id="tableName" name="tableName" value="mi203"/>
	</form>
	<div id="dlgExcel-buttons">
		<a id="submit_form" href="javascript:void(0)" onclick="submit_form()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchUpload').dialog('close')">关闭</a>
	</div>
</div>
<!-- 图片查看 -->
<div id="dlgImageLook" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px" closed="true" modal="true" buttons="#dlgdlgImageLook-buttons">
	<div class="formtitle">楼盘图片</div>
	<div id="showImg" class="dlg-form"></div>
	<div id="dlgdlgImageLook-buttons">        	
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeMapImage()">关闭</a>
	</div>
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
			<div>已添加合作银行列表：</div>
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
<script type="text/javascript">
// 百度地图API功能
var mapValue=0;
var overlay=0;
var maparr;
var housesId;
var houseCode;
var positionX;
var positionY;
var map = new BMap.Map("allmap");            
//var point = new BMap.Point(116.404, 39.915);    
map.centerAndZoom(document.getElementById('cityNameHidden').value,13);                
map.enableScrollWheelZoom();                            
map.addControl(new BMap.NavigationControl()); 
map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));
map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_TOP_RIGHT}));   
map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));     
function showInfo(e){	
 	var pointAction = new BMap.Point(e.point.lng, e.point.lat);
 	var marker = new BMap.Marker(pointAction);  
	positionX=e.point.lng;
	positionY=e.point.lat;	
	if(overlay==1){
		map.addOverlay(marker);
		overlay=0;
		$.messager.alert('提示', "地理信息已确认，请点击保存！", 'info' );
	}              
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
}
map.addEventListener("click", showInfo);
//地图信息
function editMap(){ 	
    //var row = $('#dg').datagrid('getSelected');
    var row = $('#dg').datagrid('getSelections');
           
    if(row.length==1){
	    document.getElementById('mapaddress').value = row[0].address;
	    document.getElementById('mapcityname').value = document.getElementById('cityNameHidden').value;
	    positionX=row[0].positionX;
	    positionY=row[0].positionY;
	    if (row){ 
	    	$('#dlgMap').dialog('open').dialog('setTitle','楼盘信息修改');
	    	mapValue=1; 
	    	overlay=1;  	
		    map.reset();
		    map.clearOverlays();
		   	housesId=row[0].housesId;
		   	houseCode=row[0].houseCode; 
	     } 
	     url = "<%=request.getContextPath()%>/webapi00803.json"; 
	     	if(positionX&&positionY){    			
    			var point1 = new BMap.Point(positionX,positionY);
    			map.centerAndZoom(point1, 16);
    			map.addControl(new BMap.NavigationControl());     			
    			var marker1 = new BMap.Marker(point1);
    			map.addOverlay(marker1);
				window.setTimeout(function(){
				    map.panTo(point1);
				},0);				 
    		}
			else selectMap();
	 }
	 else $.messager.alert('提示', '请选中一条楼盘信息！', 'info' );
}
function selectMap(){
	if (document.getElementById('mapcityname').value==""){		
		$.messager.alert('提示', '城市信息不可以为空！', 'error', function(){$('#mapcityname').focus();} );		
		return false;
	}
	if (document.getElementById('mapaddress').value==""){
		$.messager.alert('提示', '详细地址信息不可以为空！', 'error', function(){$('#mapaddress').focus();} );		
		return false;
	}	
	
	if (mapValue==1){	
		overlay=1;    	
		map.reset();
		map.clearOverlays();
		var myGeo = new BMap.Geocoder();			
		var address = document.getElementById('mapaddress').value; 
		var cityname=document.getElementById('mapcityname').value;    		   		
		myGeo.getPoint(address, function(point){
			if (point) {
				map.centerAndZoom(point, 16);			    
				map.addOverlay(new BMap.Marker(point));
				window.setTimeout(function(){
					map.panTo(point);
				},0);
			}
			else{
				document.getElementById('mapaddress').value="";
				document.getElementById('mapcityname').value="";
				//alert("城市信息或详细地址信息不正确，未查询到相应位置！");
				$.messager.alert('提示', '城市信息或详细地址信息不正确，未查询到相应位置！', 'info' );
			}			 
		}, cityname);					
	 }
}
function saveMap(){	
	//校验
	//if (!ydl.formValidate('formUpImg')) return;
	if (!$("#formUpImg").form("validate")) return;
	var arr = [
		{name : "housesId",value : housesId},
		{name : "houseCode",value : houseCode},
		{name : "positionX",value : positionX},
		{name : "positionY",value : positionY} 	
	];	
	$.ajax({
		type : "POST",
		url : url,
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlgMap').dialog('close');        
            $('#dg').datagrid('reload');    
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}
function closeMapImage(){
	$('#img' + housesId).remove();
	$('#dlgImageLook').dialog('close');
}
//图片预览
function editMapImage(){
	//$('#img' + housesId).remove();
	$("#showImg").html("");	
    var arr = [{name : "houseId",value : housesId}];	
	$.ajax({
		type : "POST",
		url : "webapi00807.json",
		dataType: "json",
		data:arr,
		success : function(data) {			
			if(data.recode==SUCCESS_CODE){
				if(data.imageURL==null){
					$.messager.alert('提示', '楼盘图片信息未设置！', 'info' );
					//$('#dlgImageLook').dialog('close');
				}
				else {
					$('#dlgImageLook').dialog('open').dialog('setTitle','楼盘图片信息');    		    
					$('#showImg').append('<img id="img'+housesId+'" src="'+ data.imageURL+ '"/>');
				}
			}
			else {
				$.messager.alert('提示', data.msg, 'info');
				//$('#dlgImageLook').dialog('close');
			}
			
			//$('#dlgMap').dialog('close');        
            //$('#dg').datagrid('reload');    
		},
		error :function(){
			$.messager.alert('错误',"图片获取失败",'error');
		}
	});    	
}

function saveImage(){
	$('#formUpImg').form('submit',{  
		url: 'webapi00806.do',
		onSubmit: function(){			   
			var uploadImage=$('#file').val();
			if(null == uploadImage || "" == uploadImage){
				$.messager.alert('提示', "请选择楼盘图片后进行提交", 'error');
				return false;
			}
		}
		/*,
		success: function(data){
		alert(222);
				$('#dg').datagrid('reload');				 
	    		$('#dlgBatchUpload').dialog('close');
	    		if(data.recode!=SUCCESS_CODE)
              		$.messager.alert('提示', data.msg, 'info' );
	    },
		error :function(){
			$.messager.alert('错误',data.msg,'error');							
		}*/
	});
}

function upImg(){
	document.getElementById('imageid').value = housesId;
	if (!$("#formUpImg").form("validate")) return;
	$('#formUpImg').form('submit',{  
		url: 'webapi00805.do',
		dataType:'text',		
		onSubmit: function(){			   
			var uploadImage=$('#file').val();
			if(null == uploadImage || "" == uploadImage){
				$.messager.alert('提示', "请选择楼盘图片后进行提交", 'error');
				return false;
			}
		},
		success: function(data){		
			$('#dg').datagrid('reload');				 
	    	//$('#dlgBatchUpload').dialog('close');
	    },
		error :function(){
			$.messager.alert('错误',"网络连接出错！",'error');							
		}
	});
	$('#dg').datagrid('reload');
}
</script>