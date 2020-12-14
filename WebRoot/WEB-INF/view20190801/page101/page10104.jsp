<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi202"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
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
<title>网点信息</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#allmap {width: 100%;height: 90%;overflow: hidden;margin:0;}
body{width: 100%; hidden;margin:0;}
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7db9536a2794ce477288908163157bc6"></script>
<script type="text/javascript">
var url;
$(function() {
	//屏蔽错误
	window.onerror=function(){
		return true;
	}
	
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		height:369,
		title:'网点详细信息',
		singleSelect: true,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi10104.json",
		queryParams:arrToJson($('#form10104').serializeArray()),
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.websiteId + '"/>';
			}},
			{title:'网点名称',field:'websiteName',align:'center',width:130,editor:'text'},
			{title:'所属区域',field:'areaId',width:70,align:'center',formatter:function(value,row,index) {	
				return $('#areaId option[value='+value+']').text(); 
			}},
			{title:'网点类型',field:'websiteType',width:70,align:'center',formatter:function(value,row,index) {	
				return $('#websiteType option[value='+value+']').text(); 
			}},
			{title:'业务类型',field:'businessType',align:'center',width:120,editor:{type:'numberbox',options:{precision:1}}},
			{title:'联系电话',field:'tel',align:'center',width:150,editor:'text'},
			{title:'营业时间',field:'serviceTime',align:'center',width:150,editor:'text'},
			{title:'网点地址',field:'address',align:'center',width:280,editor:'numberbox'},
			{title:'X坐标',field:'positionX',align:'center',width:50,editor:'text'},
			{title:'Y坐标',field:'positionY',align:'center',width:50,editor:'text'},
			{title:'图片',field:'imageUrl',hidden:true,align:'center',width:1},
			{title:'网点ID',field:'websiteId',hidden:true,align:'center',width:1}
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if(data.recode!=SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }	
	});	
})

function newUser(){
	//$('#websiteCode').prop('disabled',false);
	$('#dlg').dialog('open').dialog('setTitle','网点信息增加');
	$('#fm').form('clear');
	url = "<%=request.getContextPath()%>/webapi10101.json";
}
function editUser(){        
	var row = $('#dg').datagrid('getSelections');
	// $('#websiteCode').prop('disabled',true);            
	if(row.length==1){
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','网点信息修改');
			$('#fm').form('load',row[0]);
			url = "<%=request.getContextPath()%>/webapi10103.json";
		}
	}
	else $.messager.alert('提示', '请选中一条待修改网点信息！', 'info' );
}

function saveUser(){            
	var arr = $('#fm').serializeArray();
	//校验
	//if (!ydl.formValidate('fm')) return;
	if (!$("#fm").form("validate")) return;
	$.ajax( {
		type : "POST",
		url : url,
		dataType: "json",
		data:arr,
		success : function(data) {
			if(data.recode!=SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
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
function destroyUser(){
	//var row = $('#dg').datagrid('getSelections');                      
	//if (row.length > 0){
	//	var code=row[0].websiteId;            	
	//	for(var i=1;i<row.length;i++){
	//		code = code + ","+row[i].websiteId;
	//	} 
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
		var code = $checkboxs.map(function(index){
			return this.value;
		}).get().join(',');
		var arr = [{name : "deletes",value : code}];
		
		$.messager.confirm('提示','确认是否删除?',function(r){
			if(r){
				$.ajax( {
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi10102.json",
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
	else $.messager.alert('提示', '请选中待删除信息！', 'info' );
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
function select10104() {
	//校验
	//if (!ydl.formValidate('form10104')) return;
	if (!$('#form10104').form('validate')) return;
	
	var arr = $('#form10104').serializeArray();
	var dataJson = arrToJson(arr);

	//datagrid列表
	$('#dg').datagrid({   
		url: "<%=request.getContextPath()%>/webapi10104.json",
		queryParams:dataJson
	});	
}
//重置
function cancel10101() {	
	//document.getElementById("form10104").reset();	

	$('#form10104').form('clear');
	//设置combobox默认值
	$('#areaid').combobox('select','');
	ydl.delErrorMessage('form10104');
}

//批量导入
function batchUpload(){
    $('#dlgBatchUpload').dialog('open').dialog('setTitle','网点信息-批量导入');
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
<div class="easyui-panel" title="网点信息查询">
	<form id="form10104" class="dlg-form" novalidate="novalidate">
		<table class="container">		
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="websiteName">网点名称:</label></th>
				<td><input type="text" id="websiteName" name="websiteName" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" class="easyui-validatebox"/></td>
				<th><label for="areaid">所在区域:</label></th>
				<td>
				<select id="areaid" name="areaid" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
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
				<th><label for="serviceTime">营业时间:</label></th>
				<td><input type="text" id="serviceTime" name="serviceTime" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" class="easyui-validatebox"/></td>
				<th><label for="address">网点地址:</label></th>
				<td><input type="text" id="address" name="address" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]" class="easyui-validatebox"/></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select10104()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" onclick="cancel10101()">重置</a>
		</div>
	</form>
</div>
<input type = 'hidden' id = 'cityNameHidden' value = "<%=user.getFreeUse1() %>"/>
<!-- 网点详细信列表 -->
<table id="dg" ></table>
<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="editMap()">地图信息</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import-excel" plain="true" onclick="batchUpload()">批量导入</a>
		<a href="downloadimg.file?filePathParam=import_file_path&fileName=wdxxmb.xls&isFullUrl=false" class="easyui-linkbutton" iconCls="icon-download-exceltemp" plain="true">模板下载</a>	        
</div>
<!-- 添加/修改对话框 -->
<div id="dlg" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px" closed="true" modal="true" buttons="#dlg-buttons">
	<div class="formtitle">网点信息</div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<tr>
				<td><label for="websiteCode">网点编码:</label></td>
				<td><input type="text" id="websiteCode" name="websiteCode" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,20]"/></td>			        	
			</tr>
			<tr>
				<td><label for="websiteName">网点名称:</label></td>
				<td><input type="text" id="websiteName" name="websiteName" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
			</tr>
			<tr>
				<td><label for="areaId">网点区域:</label></td>
				<td><!-- <input id="areaId" name="areaId" required="true" class="easyui-validatebox" maxlength="20"/> -->
					<select id="areaId" name="areaId" class="easyui-combobox" editable="false" style="width:130px;" required="true" panelHeight="auto">				        	
						<%
							String options1 = "";
							List<Mi202> ary1=(List<Mi202>) request.getAttribute("mi202list");
							for(int i=0;i<ary1.size();i++){
								Mi202 mi202=ary1.get(i);
								String itemid=mi202.getAreaId();
								String itemval=mi202.getAreaName();
								options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options1);
						%>
					</select>
				</td>
			</tr>
			
			<tr>
				<td><label for="websiteType">网点类型:</label></td>
				<td><!-- <input id="areaId" name="areaId" required="true" class="easyui-validatebox" maxlength="20"/> -->
					<select id="websiteType" name="websiteType" class="easyui-combobox" editable="false" style="width:130px;" required="true" panelHeight="auto">				        	
						<%
							String options2 = "";
							List<Mi007> ary2=(List<Mi007>) request.getAttribute("mi007list");
							for(int i=0;i<ary2.size();i++){
								Mi007 mi007=ary2.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options2);
						%>
					</select>
				</td>
			</tr>		
			
			
					        
			<tr>
				<td><label for="businessType">业务类型:</label></td>
				<td><textarea id="introduction" name="businessType" class="easyui-validatebox"  validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,500]"></textarea>
				<!--  <input type="text" id="businessType" name="businessType" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,500]"/>-->
				</td>
			</tr>
			<tr>		           
				<td><label for="address">网点地址:</label></td>
				<td><input type="text" id="address" name="address" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]"/></td>
			</tr>
			<tr>
				<td><label for="serviceTime">营业时间:</label></td>
				<td><input type="text" id="serviceTime" name="serviceTime" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]"/></td>
			</tr>		        
			<tr>
				<td><label for="tel">联系电话:</label></td>
				<td><input type="text" id="tel" name="tel" required="true"  class="easyui-validatebox" maxlength="100"/><!-- validType="phone" -->
					<input id="websiteId" name="websiteId" type='hidden' class="easyui-validatebox"/>
				</td>
			</tr>
		 </table>            
	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

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
					<input type="file" name="file" id="file" onchange="upImg()" class="easyui-validatebox" validType="fileType['PNG']" invalidMessage="请选择(PNG)等格式的图片"/></td>		    	    
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

<div id="dlgMapImage" class="easyui-dialog" style="width:550px;height:360px;padding:1px 2px" closed="true" modal="true" buttons="#dlgMapImage-buttons">
	<table>
		<th><label for="imageurl">所在城市:</label></th>
		<td><input type="file" name="upload" id="upload"  class="easyui-validatebox" validType="fileType['BMP|GIF|JPG|JPEG|ICO|PNG|TIF']" required="true" invalidMessage="请选择(BMP|GIF|JPG|JPEG|ICO|PNG|TIF)等格式的图片"/></td>		    	    
	</table>        
</div>
<div id="dlgMapImage-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveMap()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgMap').dialog('close')">关闭</a>
</div>

<div id="dlgBatchUpload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true" buttons="#dlgExcel-buttons">
	<div class="formtitle">网点信息批量导入</div>
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
		<input type="hidden" id="ruleMapStr" name="ruleMapStr" value="{websiteCode:'#2',businessType:'#3',websiteName:'#4',areaId:'#5',address:'#6',tel:'#7',serviceTime:'#8'}"/>
		<input type="hidden" id="tableName" name="tableName" value="mi201"/>
	</form>
	<div id="dlgExcel-buttons">
		<a id="submit_form" href="javascript:void(0)" onclick="submit_form()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchUpload').dialog('close')">关闭</a>
	</div>
</div>

<!-- 图片查看 -->
<div id="dlgImageLook" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px"
		closed="true" modal="true" buttons="#dlgdlgImageLook-buttons">
	<div class="formtitle">网点图片</div>
	<div id="showImg" class="dlg-form"></div>
	<div id="dlgdlgImageLook-buttons">        	
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeMapImage()">关闭</a>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
// 百度地图API功能
var mapValue=0;
var overlay=0;
var maparr;
var websiteId;
var websiteCode;
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
		//alert("地理信息已确认，请点击保存！");
		$.messager.alert('提示', '地理信息已确认，请点击保存！', 'info' );
	}	           
	marker.setAnimation(BMAP_ANIMATION_BOUNCE); 
}
map.addEventListener("click", showInfo);

function editMap(){ 	
    //var row = $('#dg').datagrid('getSelected');
    
    var row = $('#dg').datagrid('getSelections');
           
    if(row.length==1){    
	    document.getElementById('mapaddress').value = row[0].address;
	    document.getElementById('mapcityname').value = document.getElementById('cityNameHidden').value;
	    positionX=row[0].positionX;
	    positionY=row[0].positionY;
	    if (row){ 
	    	$('#dlgMap').dialog('open').dialog('setTitle','网点信息修改');    	
	    	mapValue=1;
	    	overlay=1;   	
		    map.reset();
		    map.clearOverlays();
		   	websiteId=row[0].websiteId;
		   	websiteCode=row[0].websiteCode;	    
	     } 
	     url = "<%=request.getContextPath()%>/webapi10103.json"; 
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
	else $.messager.alert('提示', '请选中一条网点信息！', 'info' );    	
}
function selectMap(){
	if(document.getElementById('mapcityname').value==""){		
		$.messager.alert('提示', '城市信息不可以为空！', 'error', function(){$('#mapcityname').focus();} );		
		return false;
	}
	if(document.getElementById('mapaddress').value==""){
		$.messager.alert('提示', '详细地址信息不可以为空！', 'error', function(){$('#mapaddress').focus();} );		
		return false;
	}	
	
	if(mapValue==1){
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
				$.messager.alert('提示', '城市信息或详细地址信息不正确，未查询到相应位置！', 'info' );
			}
		}, cityname);
	 }
}
function saveMap(){
	var arr = [
		{name : "websiteId",value : websiteId},
		{name : "websiteCode",value : websiteCode},
		{name : "positionX",value : positionX},
		{name : "positionY",value : positionY} 	
	];
	
	$.ajax( {
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
	$('#img' + websiteId).remove();
	$('#dlgImageLook').dialog('close');
}
//图片预览
function editMapImage(){
	//$('#img' + websiteId).remove();
	$("#showImg").html("");		    
    var arr = [{name : "websiteId",value : websiteId}];	
	$.ajax( {
		type : "POST",
		url : "webapi10106.json",
		dataType: "json",
		data:arr,
		success : function(data) {			
			if(data.recode==SUCCESS_CODE){
				if (data.imageURL == null){
					$.messager.alert('提示', '网点图片信息未设置！', 'info' );
					//$('#dlgImageLook').dialog('close');
				}
				else {
					$('#dlgImageLook').dialog('open').dialog('setTitle','网点图片信息');  
					$('#showImg').append('<img id="img'+websiteId+'" src="' + data.imageURL + '"/>');
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
			$.messager.alert('错误',"网络连接出错！",'error');
		}
	});    	
}

function saveImage(){
	
	$('#formUpImg').form('submit',{  
		url: 'webapi10105.do',
		onSubmit: function(){			   
			var uploadImage=$('#file').val();
			if(null == uploadImage || "" == uploadImage){
				$.messager.alert('提示', "请选择网点图片后进行提交", 'error');
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
	document.getElementById('imageid').value = websiteId;
	if (!$("#formUpImg").form("validate")) return;
	$('#formUpImg').form('submit',{  
		url: 'webapi10105.do',
		dataType:'text',		
		onSubmit: function(){			   
			var uploadImage=$('#file').val();
			if(null == uploadImage || "" == uploadImage){
				$.messager.alert('提示', "请选择网点图片后进行提交", 'error');
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