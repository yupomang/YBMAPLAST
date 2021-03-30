<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi001"%>
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
<title>版本更新</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
var url;
var filene='';
var filept='';
var load='';
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'版本详细信息',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi40304.json",
		method:'post',
		queryParams:{centerid:''},
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.versionid + '"/>';
			}},
			{title:'中心名称',field:'centerid',width:80,align:'center',formatter:function(value,row,index) {	
				return $('#centerid option[value='+value+']').text(); 	
			}},
			{title:'设备区分',field:'devtype',width:80,align:'center',formatter:function(value,row,index) {	
				return $('#devtype option[value='+value+']').text(); 	
			}},
			{title:'版本编号',field:'versionno',align:'center',width:130,editor:'text'},
			{title:'发布日期',field:'releasedate',align:'center',width:180,editor:'text'},
			{title:'下载地址',field:'downloadurl',align:'center',width:120,editor:'text'},
			{title:'更新内容',field:'releasecontent',align:'center',width:250,editor:'text'},
			{title:'版本是否可用',field:'usableflag',align:'center',width:120,formatter:function(value,row,index) {	
				return $('#usableflag option[value='+value+']').text(); 	
			}},
			{title:'发布人',field:'publisher',align:'center',width:100,editor:'text'},			
			{title:'是否发布',field:'publishflag',align:'center',width:70,formatter:function(value,row,index) {	
				return $('#publishflagList option[value='+value+']').text(); 	
			}},
			{title:'版本ID',field:'versionid',hidden:true,align:'center',width:120},
			{title:'二维码',field:'freeuse1',hidden:true,align:'center',width:20}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});	
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#form40304").form("validate")) return;
		
		var arr = $('#form40304').serializeArray();
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','软件更新信息增加');
		$('#formtitle').text('软件更新信息增加');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/webapi40301.json";
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				if(row[0].publishflag==0){										
					$('#dlg').dialog('open').dialog('setTitle','软件更新信息修改');
					$('#formtitle').text('软件更新信息修改');
					$('#fm').form('load',row[0]);
					url = "<%=request.getContextPath()%>/webapi40303.json";
				}else{
					//$.messager.alert('提示', '版本信息己发布不可修改！', 'info' );
					$('#formtitle_tck').text('己发布版本信息只可以修改描述或是否可用');					
					$('#dlg_tck').dialog('open').dialog('setTitle','软件更新信息修改');					
					$('#releasecontent_tck').val(row[0].releasecontent);					
					$('#downloadurl_tck').val(row[0].downloadurl);
					$('#versionid_tck').val(row[0].versionid);					
					$('#fbxg').val('1');
					$('#usableflag_tck').combobox('select',row[0].usableflag);
				}
			}
		}
		else $.messager.alert('提示', '请选中一条待修改版本信息！', 'info' );
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//二维码
	$('#b_add_pic').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				//alert(row[0].freeuse1==null||row[0].freeuse1=='');
				filene = '';
				filept = '';	
							
				if(row[0].freeuse1==null||row[0].freeuse1==''){
					$('#file').val("");
					load='';
					$('#showTwoImg').html('');
				}else{
					$('#showTwoImg').html("");
					$('#file').val("");
					load = "downloadimg.file?filePathParam=push_twodimensional&fileName="+row[0].centerid+"/"+row[0].freeuse1+"&isFullUrl=true";
					$('#showTwoImg').closest('.panel').show();
					$('#showTwoImg').append('<div style="text-align:center"><img id="' + row[0].freeuse1 + '" src="'
						+ load + '"/><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');
				}
				$('#dlg_pic').dialog('open').dialog('setTitle','软件版本信息二维码');
				$('#downloadurl_pic').val(row[0].downloadurl);
				$('#versionid_pic').val(row[0].versionid);
				$('#devtype_pic').val(row[0].devtype);
				$('#downloadurl_p').val(row[0].downloadurl);
				$('#magecenterId').val(row[0].centerid);
				
			}
		}
		else $.messager.alert('提示', '请选中一条版本信息生成二维码！', 'info' );
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//保存
	$('#b_save').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dlg').dialog('close');        	// close the dialog
					$('#dg').datagrid('reload');    	// reload the user data
				}
				$.messager.alert('提示',data.msg,'info');	
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	
	//保存己发布修改
	$('#b_tck_save').click(function (){
		
		if (!$("#fm_tck").form("validate")) return;
		var arr = $('#fm_tck').serializeArray()
		url = "<%=request.getContextPath()%>/webapi40303.json";;
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dlg_tck').dialog('close');        	// close the dialog
					$('#dg').datagrid('reload');    	// reload the user data
				}
				$.messager.alert('提示',data.msg,'info');	
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//生成二维码
	$('#b_pic_save').click(function (){
		
		if (!$("#formUpImg").form("validate")) return;
		//var arr = $('#fm_pic').serializeArray()
		var arr = [{name : "downloadurl",value : $('#downloadurl_pic').val()},{name : "freeuse2",value : filene},{name : "versionid",value : $('#versionid_pic').val()},{name : "centerid",value : $('#magecenterId').val()}];
		url = "<%=request.getContextPath()%>/webapi40307.json";
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dg').datagrid('reload');    	// reload the user data					
					$('#showTwoImg').html("");
					$('#showTwoImg').closest('.panel').show();
					$('#showTwoImg').append('<div style="text-align:center"><img id="' + data.fileName + '" src="'
					+ data.downloadPath + '"/><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div>');	
					load = data.downloadPath;									
				}
				$.messager.alert('提示',data.msg,'info');	
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//下载二维码
	$('#b_pic_download').bind("click",function(){
		if(load==''){
			alert('尚未生成二维码！');
			//$(this).attr('href',load);
		}else{
			$(this).attr('href',load);
		}
	});
	
	
	
	
	
	//删除
	$('#b_del').click(function (){
		//var row = $('#dg').datagrid('getSelections');        
		//if (row.length > 0) {
			//var code=row[0].versionid;            	
			//for(var i=1;i<row.length;i++){
			//	code = code + ","+row[i].versionid;
			//} 
		var $checkboxs = $('input:checkbox:checked');
		if ($checkboxs.length > 0) {
			var code = $checkboxs.map(function(index){
				return this.value;
			}).get().join(',');
			var arr = [{name : "listVersionId",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi40302.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#dg').datagrid('reload');    // reload the user data
							}
							else $.messager.alert('提示',data.msg,'info');					
						},
						error :function(){
							$.messager.alert('错误','网络连接出错！','error');							
						}
					});
				}				
			});
		}
		else $.messager.alert('提示', '请选中待删除信息！', 'info' );
	});
	
	//发布
	$('#b_publish').click(function (){		
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				if(row[0].publishflag==0){					
					$('#fm').form('load',row[0]);
					url = "<%=request.getContextPath()%>/webapi40305.json";					
					$.ajax({
						type : "POST",
						url : url,
						dataType: "json",
						data:row[0],
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#dlg').dialog('close');        	// close the dialog
								$('#dg').datagrid('reload');    	// reload the user data
							}
							$.messager.alert('提示',data.msg,'info');	
						},
						error :function() {
							$.messager.alert('错误','网络连接出错！','error');
						}
					});
					
				}else{
					$.messager.alert('提示', '版本信息己经发布！', 'info' );
				}
			}
		}
		else $.messager.alert('提示', '请选中一条待发布版本信息！', 'info' );
	});
	
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	
	$('#b_tck_cancel').click(function (){
		$('#dlg_tck').dialog('close');
	});
	
	$('#b_pic_cancel').click(function (){
		$('#dlg_pic').dialog('close');
		$('#showTwoImg').html("");
		load='';
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


function upImg(){	
	if (!$("#formUpImg").form("validate")) return;
	//$('#showImg').closest('.panel').show();
	$('#formUpImg').form('submit',{
		url: 'webapi40306.do',
		dataType:"text",
		onSubmit: function(){	
			var uploadImage=$('#file').val();
			if(null == uploadImage || "" == uploadImage){
				$.messager.alert('提示', "请选择LOGO后进行提交", 'error');
				return false;
			}
		},
		success: function(data){
		
			var data1 = eval("("+data+")");			
			if (data1.recode != SUCCESS_CODE){
				$('#file').val("");
				//load=''; 
				$.messager.alert('提示', data1.msg, 'info' );				
			}else{
				filene = data1.fileName;
				filept = data1.downloadPath;
				$.messager.alert('提示', 'LOGO上传成功', 'info' );
			}
			//$('#dg').datagrid('reload');				 
	    	//$('#dlgBatchUpload').dialog('close');
	    	//$('#showImg').append('<div style="text-align:center"><img id="' + data1.fileName + '" src="'
			//		+ data1.downloadPath + '"/><span class="icon icon_del" style="visibility: hidden;" title="删除"></span></div><div class="formtitle" id="formtitle_pic1" height="70%"></div>');
	    },
		error :function(){
			$.messager.alert('错误',"网络连接出错！",'error');							
		}
	});
	//$('#dg').datagrid('reload');
}




</script>
</head>
<body>
<div class="easyui-panel" title="软件更新信息查询">
	<form id="form40304" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="30%" /><col width="70%" />
			<tr>	        
				<th><label for="centerid">中心名称:</label></th>
				<td>
				<select id="centerid" name="centerid" class="easyui-combobox" editable="false" style="width:220px;" ><!-- panelHeight="auto" -->
					<option value="">请选择...</option>
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
				</select>
				</td>
			</tr>
		</table> 
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
		</div>
	</form>
</div>

<table id="dg"></table>

<div id="toolbar">
	<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >增加</a>
	<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
	<a id="b_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
	<a id="b_publish" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >发布</a>
	<a id="b_add_pic" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >二维码</a>	        
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:330px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons">
	<div class="formtitle" id="formtitle"></div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="centerid1">中心名称:</label></td>
				<td><!-- <input id="devtype" name="devtype" required="true" class="easyui-validatebox" maxlength="1"/>-->
					<select id="centerid1" name="centerid" class="easyui-combobox" style="width:180px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options3 = "";
							List<Mi001> ary3=(List<Mi001>) request.getAttribute("mi001list");
							for(int i=0;i<ary3.size();i++){
								Mi001 mi003=ary3.get(i);
								String itemid=mi003.getCenterid();
								String itemval=mi003.getCentername();
								options3 = (new StringBuilder(String.valueOf(options3))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options3);
						%>
					</select>			        
				</td>			        	
			</tr>
			
			<tr>
				<td><label for="devtype">设备区分:</label></td>
				<td><!-- <input id="devtype" name="devtype" required="true" class="easyui-validatebox" maxlength="1"/>-->
					<select id="devtype" name="devtype" class="easyui-combobox" style="width:180px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options1 = "";
							List<Mi007> ary1=(List<Mi007>) request.getAttribute("devicetypeList");
							for(int i=0;i<ary1.size();i++){
								Mi007 mi007=ary1.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options1);
						%>
					</select>			        
				</td>			        	
			</tr>
			<tr>
				<td><label for="versionno">版本编号:</label></td>
				<td><input type="text" id="versionno" name="versionno" required="true" style="width:178px;" validType="versionno" class="easyui-validatebox" maxlength="20"/></td>
			</tr>
			<tr>	        
				<td><label for="releasedate">发布日期:</label></td>
				<td><input type="text" id="releasedate" name="releasedate" required="true" style="width:180px;" class="easyui-datebox" validType="date" maxlength="10"/></td>
			</tr>
			<tr>
				<td><label for="downloadurl">下载地址:</label></td>
				<td><input type="text" id="downloadurl" name="downloadurl" required="true" style="width:178px;" class="easyui-validatebox" validType="length[0,200]"/></td>
			</tr>
			<tr>		           
				<td><label for="releasecontent">更新内容:</label></td>
				<td>
					<input type="text" id="releasecontent" name="releasecontent" style="width:178px;" required="true" class="easyui-validatebox" validType="length[0,200]" />
					<input id="versionid" name="versionid" type='hidden' class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<td><label for="usableflag">版本是否可用:</label></td>
				<td>
					<select id="usableflag" name="usableflag" class="easyui-combobox" style="width:180px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options5 = "";
							List<Mi007> ary5=(List<Mi007>) request.getAttribute("isnoList");
							for(int i=0;i<ary5.size();i++){
								Mi007 mi007=ary5.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options5 = (new StringBuilder(String.valueOf(options5))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options5);
						%>
					</select>	
				</td>
			</tr>		        
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>






<div id="dlg_tck" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-tck-buttons">
	<div class="formtitle" id="formtitle_tck"></div>
	<form id="fm_tck" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			
			<tr>		           
				<td><label for="releasecontent_tck">更新内容:</label></td>
				<td>
					<input type="text" id="releasecontent_tck" style="width:180px;" name="releasecontent" class="easyui-validatebox" validType="length[0,200]" />
					<input id="versionid_tck" name="versionid" type='hidden' class="easyui-validatebox"/>
					<input id="fbxg" name="fbxg" type='hidden' class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<td><label for="usableflag_tck">版本是否可用:</label></td>
				<td>
					<select id="usableflag_tck" name="usableflag"  class="easyui-combobox" style="width:180px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options15 = "";
							List<Mi007> ary15=(List<Mi007>) request.getAttribute("isnoList");
							for(int i=0;i<ary15.size();i++){
								Mi007 mi007=ary15.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options15 = (new StringBuilder(String.valueOf(options15))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options15);
						%>
					</select>	
				</td>
			</tr>
			<tr>		           
				<td><label for="downloadurl_tck">下载地址:</label></td>
				<td>
					<input type="text" id="downloadurl_tck" name="downloadurl" style="width:180px;" class="easyui-validatebox" readonly="ture" validType="length[0,200]" />									
				</td>
			</tr>		        
		  </table>
	</form>
</div>
<div id="dlg-tck-buttons">
	<a id="b_tck_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_tck_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>








<!-- 二维码处理 -->
<div id="dlg_pic" class="easyui-dialog" style="width:500px;height:460px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-pic-buttons">
	<div class="formtitle" id="formtitle_pic"></div>	
		<form id="formUpImg" name="formUpImg" method="post" enctype="multipart/form-data" novalidate="novalidate">	
			<table class="dlgcontainer">
				<col width="30%" /><col width="70%" />
				<tr>		           
					<td><label for="downloadurl_pic">下载地址:</label></td>
					<td>
						<input type="text" id="downloadurl_pic" name="downloadurl_pic" style="width:180px;" class="easyui-validatebox" readonly="ture" validType="length[0,200]" />									
					</td>
				</tr>
				<tr>
					<td><label for="releasecontent_tck">LOGO图片:</label></td>
					<td>
						<input id="magecenterId" name="magecenterId" type="hidden" class="easyui-validatebox"/>
						<input id="versionid_pic" name="versionid_pic" type='hidden' class="easyui-validatebox"/>
						<input id="devtype_pic" name="devtype_pic" type='hidden' class="easyui-validatebox"/>
						<input id="downloadurl_p" name="downloadurl_p" type='hidden' class="easyui-validatebox"/>	
						<input type="file" name="file" id="file" onchange="upImg()" class="easyui-validatebox" validType="fileType[['JPG','PNG']]" invalidMessage="请选择(JPG)等格式的图片"/>
					</td>
			  </table>			  
		  </form>
	<div id="showImg"></div>
	<div id="showTwoImg"></div>
</div>
<div id="dlg-pic-buttons">
	<a id="b_pic_download" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-down">下载二维码</a>
	<a id="b_pic_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">生成二维码</a>
	<a id="b_pic_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<!-- 二维码处理 -->


















<div style="display:none">
	<select id="publishflagList" name="publishflagList" editable="false" class="easyui-combobox">				        	
	<%
		String options4 = "";
		List<Mi007> ary4=(List<Mi007>) request.getAttribute("publishflagList");
		for(int i=0;i<ary4.size();i++){
			Mi007 mi007=ary4.get(i);
			String itemid=mi007.getItemid();
			String itemval=mi007.getItemval();
			options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
		}
		out.println(options4);
	%>
	</select>
</div>
</body>
</html>