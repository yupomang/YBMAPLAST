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
<title>APP图片动画</title>
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
var ctid;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'APP图片动画信息查询结果',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi41104.json",
		method:'post',
		queryParams:arrToJson($('#form41101').serializeArray()),
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){				
				return '<input type="checkbox" name="animate" value="' + row.animateid + '"/>';
			}},
			{title:'动画名称',field:'animatename',align:'center',width:60,editor:'text'},
			{title:'动画编号',field:'animatecode',width:120,align:'center'},
			{title:'动画功能描述',field:'animatedescript',align:'center',width:100,editor:'text'},
			{title:'城市中心',field:'centerid',align:'center',width:150,editor:'text',formatter:function (value,row,index){	
				return $('#centerid option[value='+value+']').text(); 	
			}},
			{title:'设备区分',field:'devid',align:'center',width:100,editor:'text',formatter:function (value,row,index){	
				return $('#devid option[value='+value+']').text();
			}},
			{title:'间隔时间(秒)',field:'intervaltime',align:'center',width:130,editor:'text'},
			{title:'循环方式',field:'looptype',align:'center',width:100,editor:'text',formatter:function (value,row,index){	
				return $('#looptype option[value='+value+']').text();
			}},
			{title:'图片标准宽度(像素)',field:'imgwidth',align:'center',width:110,editor:'text'},
			{title:'图片标准高度(像素)',field:'imgheight',align:'center',width:110,editor:'text'},
			{title:'动画ID',field:'animateid',hidden:true,align:'center',width:100}						
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }
	});
	
	//查询
	$('#b_query').click(function (){		//校验
		if (!$("#form41101").form("validate")) return;
		//组织查询条件
		var arr = $("#form41101").serializeArray();
		var dataJson = arrToJson(arr);
		//查询列表数据
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#form41101').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');
		//除去错误校验状态
		ydl.delErrorMessage('form41101');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','APP图片动画信息增加');		
		$('#formtitle').text('APP图片动画信息增加');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/webapi41101.json";
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','APP图片动画信息修改');
				$('#formtitle').text('APP图片动画信息修改');
				$('#fm').form('load',row[0]);
				url = "<%=request.getContextPath()%>/webapi41103.json";
			}
		}
		else $.messager.alert('提示', '请选中一条待修改APP图片动态信息！', 'info' );
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
	
	//删除
	$('#b_del').click(function (){
		//var row = $('#dg').datagrid('getSelections');        
		//if (row.length > 0) {
			//var code=row[0].versionid;            	
			//for(var i=1;i<row.length;i++){
			//	code = code + ","+row[i].versionid;
			//} 
		var $checkboxs = $('input[name=animate]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');			
			var arr = [{name : "animateid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi41102.json",
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
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	
	//查明细
	$('#openTPMX').click(function (){
		var row = $('#dg').datagrid('getSelections');		
		if (row.length == 1) {
			var code=row[0].animateid;  
			$('#dlg_MX').dialog('open').dialog('setTitle','APP图片动画明细信息查询结果');		
			$('#formtitle_MX').text('APP图片动画明细信息增加');		
			$('#anid_m').val(code);	
			ctid=row[0].centerid;
					
			//初始化列表			
			var arr = {animateid:code,cid:row[0].centerid};
			$('#dg_MX').datagrid({   
				iconCls: 'icon-edit',
				title:'APP图片动画信息明细查询结果',
				height:410,
				singleSelect: true,
				url: "<%=request.getContextPath()%>/webapi41108.json",
				method:'post',
				queryParams:arr,
				pagination:true,
				rownumbers:true,
				fitColumns:true,
				columns:[[
					{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){				
						return '<input type="checkbox" name="mxid" value="' + row.mxid + '"/>';
					}},
					{title:'序号',field:'xh',align:'center',width:60,editor:'text'},
					{title:'图片名称',field:'imgpath',hidden:true,width:120,align:'center'},
					{title:'图片内容链接路径',field:'contentlink',align:'center',width:100,editor:'text'},
					{title:'图片切换显示',field:'displaydirection',align:'center',width:150,editor:'text',formatter:function (value,row,index){	
						return $('#displaydirection option[value='+value+']').text(); 	
					}},				
					{title:'动画明细ID',field:'mxid',hidden:true,align:'center',width:1},
					{title:'图片地址',field:'freeuse1',hidden:true,align:'center',width:1}						
				]],
				toolbar: '#toolbar_MX',
				onLoadSuccess:function(data){ 
					if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
		        }
			});          	
		}else{
			$.messager.alert('提示', '请选中待处理图片动画信息！', 'info' );
		}
	});
	
	//增加
	$('#b_add_MX_M').click(function (){				
		$('#showImg img').remove();
		$('#dlg_MX_M').dialog('open').dialog('setTitle','APP图片动画明细信息增加');		
		$('#formtitle_MX_M').text('APP图片动画明细信息增加');
		$('#fm_MX_M').form('clear');
		
		
		var tempForm = document.createElement('form');  
		$('#file').before(tempForm);  
		$(tempForm).append($('#file'));  
		tempForm.reset();  
		$(tempForm).after($('#file'));  
		$(tempForm).remove();
		
		
		$('#magecenterId').val(ctid);
		url = "<%=request.getContextPath()%>/webapi41105.json";
	});
	
	//修改
	$('#b_edit_MX_M').click(function (){
		
		var row = $('#dg_MX').datagrid('getSelections');		
		if (row.length == 1){
			if (row){		
				$('#showImg img').remove();
				$('#dlg_MX_M').dialog('open').dialog('setTitle','APP图片动画信息修改');
				$('#formtitle_MX_M').text('APP图片动画信息修改');
				$('#fm_MX_M').form('load',row[0]);
				var tempForm = document.createElement('form');  
				$('#file').before(tempForm);  
				$(tempForm).append($('#file'));  
				tempForm.reset();  
				$(tempForm).after($('#file'));  
				$(tempForm).remove();
				url = "<%=request.getContextPath()%>/webapi41107.json";
				$('#magecenterId').val(ctid);
				if(!row[0].freeuse1==""||!row[0].freeuse1==null){
					$('#showImg').append('<img id="imgTPDH" src="'+ row[0].freeuse1+ '"/>');
				}
				
			}
		}
		else $.messager.alert('提示', '请选中一条待修改APP图片明细动态信息！', 'info' );
	});
	
	//保存
	$('#b_save_MX_M').click(function (){
		//校验
		//if (!ydl.formValidate('fm')) return;
		if (!$("#fm_MX_M").form("validate")) return;
		if($("#imgpath").val()==""||$("#imgpath").val()==null){
			$.messager.alert('提示',"请选择图片动画",'info');
			return;
		}
		//if (!$("#imgpath").val()) return;
		var code = $('#anid_m').val();
		$('#anid').val(code);
		
		var arr = $('#fm_MX_M').serializeArray();		
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dlg_MX_M').dialog('close');        	// close the dialog
					$('#dg_MX').datagrid('reload');    	// reload the user data
				}
				$.messager.alert('提示',data.msg,'info');	
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//删除
	$('#b_del_MX_M').click(function (){
		//var row = $('#dg').datagrid('getSelections');        
		//if (row.length > 0) {
			//var code=row[0].versionid;            	
			//for(var i=1;i<row.length;i++){
			//	code = code + ","+row[i].versionid;
			//} 
		var $checkboxs = $('input[name=mxid]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');		
				
			var arr = [{name : "mxid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi41106.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#dg_MX').datagrid('reload');    // reload the user data
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
	
	//取消
	$('#b_cancel_MX_M').click(function (){
		$('#dlg_MX_M').dialog('close');
	});
	
	//取消
	$('#b_cancel_MX').click(function (){
		$('#dlg_MX').dialog('close');
	});
	
	$('#file').change(function (){
		if (!$("#formUpImg").form("validate")) return;	
		$('#formUpImg').ajaxSubmit({
			dataType : "json",
			success : function(data) {						
	    		if(data.recode == SUCCESS_CODE){
	    			$('#showImg img').remove();
	    			$('#showImg').append('<img id="imgTPDH" src="'+ data.downloadPath+ '"/>');
	    			$('#imgpath').val(data.fileName);
	    		}	
			},
			error:function() {
				$.messager.alert('错误', '网络连接出错！', 'error');
			}
		});
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
	$('#formUpImg').ajaxSubmit({
		dataType : "json",
		success : function(data) {						
    		if(data.recode == SUCCESS_CODE){
    			$('#showImg img').remove();
    			$('#showImg').append('<img id="imgTPDH" src="'+ data.downloadPath+ '"/>');
    			$('#imgpath').val(data.fileName);
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
<div class="easyui-panel" title="APP图片动画">
	<form id="form41101" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>
				<th><label for="centerid">城市中心:</label></th>
				<td>
					<select id="centerid" name="centerid" class="easyui-combobox" editable="false" style="width:220px;" panelHeight="auto">
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
					    <%} %>
				</td>
				<th><label for="devid">设备区分:</label></th>
				<td>
					<select id="devid" name="devid" class="easyui-combobox" editable="false" style="width:220px;" panelHeight="auto">
			        	<option value="">请选择...</option>
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
				<th><label for="animatename">动画名称:</label></th>
				<td><input type="text" id="animatename" name="animatename" style="width:220px;" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]"/></td>
				<th><label for="animatecode">动画编号:</label></th>
				<td><input type="text" id="animatecode" name="animatecode" style="width:220px;" class="easyui-validatebox" validType="numletter&&length[0,30]"/></td>
			</tr>
		</table>  
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
		</div>
	</form>
</div>

<table id="dg" ></table>
<div id="toolbar">
	<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >增加</a>
	<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
	<a id="b_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
	<a id="openTPMX" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >图片动画明细设置</a>	        	        
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:370px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons">
	<div class="formtitle" id="formtitle"></div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="animatename">动画名称:</label></td>
				<td><input type="text" id="animatename" name="animatename" required="true" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td><label for="animatecode">动画编号:</label></td>
				<td><input type="text" id="animatecode" name="animatecode" required="true" validType="numletter&&length[0,30]" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td><label for="animatedescript">动画功能描述:</label></td>
				<td><input type="text" id="animatedescript" name="animatedescript" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td><label for="centerid">城市中心:</label></td>
				<td>
					<select id="centerid" name="centerid" class="easyui-combobox" style="width:130px;" editable="false" required="true" panelHeight="auto">				        	
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
				<td><label for="devid">设备区分:</label></td>
				<td>
					<select id="devid" name="devid" class="easyui-combobox" style="width:130px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options2 = "";
							List<Mi007> ary2=(List<Mi007>) request.getAttribute("devicetypeList");
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
				<td><label for="intervaltime">间隔时间(秒):</label></td>
				<td><input type="text" id="intervaltime" name="intervaltime" required="true" class="easyui-numberbox" validType="length[0,10]"/></td>
			</tr>
			<tr>		           
				<td><label for="looptype">循环方式:</label></td>
				<td>					
					<select id="looptype" name="looptype" class="easyui-combobox" style="width:130px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options6 = "";
							List<Mi007> ary6=(List<Mi007>) request.getAttribute("looptypeList");
							for(int i=0;i<ary6.size();i++){
								Mi007 mi007=ary6.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options6 = (new StringBuilder(String.valueOf(options6))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options6);
						%>
					</select>	
				</td>
			</tr>	
			<tr>
				<td><label for="imgwidth">图片标准宽度(像素):</label></td>
				<td><input type="text" id="imgwidth" name="imgwidth" class="easyui-numberbox" validType="length[0,10]"/></td>
			</tr>
			<tr>
				<td><label for="imgheight">图片标准高度(像素):</label></td>
				<td><input type="text" id="imgheight" name="imgheight" class="easyui-numberbox" validType="length[0,10]"/>
					<input id="animateid" name="animateid" type='hidden' class="easyui-validatebox"/>
				</td>
			</tr>
					
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>



<div id="dlg_MX" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px" modal="true" closed="true" buttons="#dlg_MX-buttons">
	<table id="dg_MX" ></table>
	<div id="toolbar_MX">
	<a id="b_add_MX_M" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >增加</a>
	<a id="b_edit_MX_M" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改及预览</a>
	<a id="b_del_MX_M" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
	<div>
</div>
<div id="dlg_MX-buttons">	
	<a id="b_cancel_MX" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
<div id="dlg_MX_M" class="easyui-dialog" style="width:600px;height:500px;padding:10px 20px" modal="true" closed="true" buttons="#dlg_MX_M-buttons">

	<div class="formtitle" id="formtitle_MX_M"></div>
	<form id="fm_MX_M" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="xh">序号:</label></td>
				<td><input type="text" id="xh" name="xh" required="true" validType="length[0,100]" class="easyui-numberbox"/></td>
			</tr>
			<!-- 
			<tr>
				<td><label for="imgpath">图片名称:</label></td>
				<td><input type="text" id="imgpath" name="imgpath" required="true" validType="numletter" class="easyui-validatebox"/></td>
			</tr>
			-->
			<tr>
				<td><label for="contentlink">图片内容链接路径:</label></td>
				<td><input type="text" id="contentlink" name="contentlink" required="true" validType="url&&length[0,200]" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<td><label for="displaydirection">图片切换显示:</label></td>
				<td>
					<select id="displaydirection" name="displaydirection" class="easyui-combobox" style="width:130px;" editable="false" required="true" panelHeight="auto">				        	
						<%
							String options5 = "";
							List<Mi007> ary5=(List<Mi007>) request.getAttribute("displaydirectionList");
							for(int i=0;i<ary5.size();i++){
								Mi007 mi007=ary5.get(i);
								String itemid=mi007.getItemid();
								String itemval=mi007.getItemval();
								options5 = (new StringBuilder(String.valueOf(options5))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options5);
						%>
					</select>	
					<input id="mxid" name="mxid" type='hidden' class="easyui-validatebox"/>
					<input id="anid" name="anid" type='hidden' class="easyui-validatebox"/>
					<input id="imgpath" name="imgpath" type='hidden' lass="easyui-validatebox"/>
					<input id="freeuse1" name="freeuse1" type='hidden' lass="easyui-validatebox"/>		        
				</td>
			</tr>	
		  </table>
	</form>
	
	<input id="anid_m" name="anid_m" type='hidden' class="easyui-validatebox"/>
	<div class="formtitle">图片动画预览</div>
	<form id="formUpImg" name="formUpImg" class="dlg-form" action="webapi41109.do" method="post" enctype="multipart/form-data" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="imageurl">选择图片动画:</label></td>
				<td>			    
					<input type="hidden" name="magecenterId" id="magecenterId" />			
					<input type="file" name="file" id="file"  class="easyui-validatebox" validType="fileType[['PNG']]" invalidMessage="请选择(PNG)等格式的图片"/>
				</td>
			</tr>		    	    
		</table> 
	</form>
	<div id="showImg" class="dlg-form"></div>
	
	<div id="dlg_MX_M-buttons">
		<a id="b_save_MX_M" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a id="b_cancel_MX_M" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
</div>








</body>
</html>
