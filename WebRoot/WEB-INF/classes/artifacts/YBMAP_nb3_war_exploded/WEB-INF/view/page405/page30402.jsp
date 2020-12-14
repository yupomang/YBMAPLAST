<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	/*
	   文件名：page30402.jsp
	   作者： syw
	   日期：2015-09-13 
	   作用：消息模板管理
	*/
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.*"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<!DOCTYPE html>
<html>
<head>
<title>消息模板管理</title>
<style type="text/css">
/*<![CDATA[*/
#list-container {
	width: 300px;
	padding: 5px;
}

#form-container {
	padding-top: 5px;
	padding-left: 0px;
	padding-right: 1px;
	padding-bottom: 5px;
}
/*]]>*/
</style>
<%@ include file="/include/init.jsp"%><!-- 引入_contexPath路径 -->
<%@ include file="/include/styles.jsp"%><!-- 引入common.css和easyui.css -->
<%@ include file="/include/scripts.jsp"%><!-- 引入js -->
<base target="main" />

<script type="text/javascript">
var centerTab;//模板列表
var operTab;//要素列表
//var operForm=$("#operForm").form();//模板表单
$(function() {
	centerTab=$("#centerTab").datagrid({
    height:$(document).height()-40,
    fitColumns: true,
	idField:'templateId',
	singleSelect:true,
	pagination:true,
	pageList:[15],
	url:'page30402Qry.json',
	onClickRow:function(rowIndex, rowData){
		var isedit = $('#isedit').val();
		if(isedit==1)
		{
		  var tn = $('#templateName').val();
		  var tc = $('#templateCode').val();
		  var tct = $('#templateContent').val();
		  var cti = $('#centerid').val();
		  var fl = $('#freeuse1').val();
		  var bknum = $('#batchkeynum').val();
		  var bsplit = $('#batchsplit').val();
		  var bsnum = $('#batchstartnum').val();
		 
		  if(tn!=''||tc!=''||tct!=''||(cti !=null && cti!='')|| fl !=''||bknum!=''||bsplit!=''||bsnum!='')
		  {
			  $.messager.confirm('提示','数据未保存，确定放弃吗?',function(r){
					if (r) {
						 operTab.datagrid('options').queryParams={templateId:rowData.templateId};
						 operTab.datagrid("load");
						 $("[name='templateId']").attr("readonly",true);
						 //表单数据的回显
						 $('#operForm').form('clear').form('load',rowData);
						 var ck_channels = rowData.channel.split(",");//获取那些渠道，然后赋值给复选框
						 for(var i=0;i<ck_channels.length;i++){
							 $("#operForm input[type='checkbox']").each(function(){
								 var checkValue = $(this).val();
								 if(ck_channels[i]==checkValue){
									 $(this).attr("checked",true);
								 }
								 $(this).attr("disabled","disabled");
							 });
						 }
						 $('#operarea').panel('setTitle', '查看信息模板');
						 $('#divupd').css("display","block");
						 $('#divsave').css("display","none");
						 $('#isedit').val("0");
						 $('#templateName').attr("readonly",true);
						 $('#templateCode').attr("readonly",true);
						 $('#templateContent').attr("readonly",true);
						 $('#centerid').combobox('disable');
						 $('#freeuse1').attr("readonly",true);
						 $('#batchkeynum').attr("readonly",true);
						 $('#batchsplit').attr("readonly",true);
						 $('#batchstartnum').attr("readonly",true);
						 $('#selectRow').val(rowIndex);
					}else
					{
						if($('#selectRow').val()!='')
						{
							$("#centerTab").datagrid('selectRow',$('#selectRow').val());
						}else
						{
							$("#centerTab").datagrid('unselectRow',rowIndex);
						}
					}
				});
		  }else
		  {
			  $('#divupd').css("display","block");
			  $('#divsave').css("display","none");
			  $('#isedit').val("0");
			  $('#templateName').attr("readonly",true);
			  $('#templateCode').attr("readonly",true);
			  $('#templateContent').attr("readonly",true);
			  $('#centerid').combobox('disable');
			  $('#freeuse1').attr("readonly",true);
			  operTab.datagrid('options').queryParams={templateId:rowData.templateId};
			  operTab.datagrid("load");
			  $("[name='templateId']").attr("readonly",true);
			  $('#batchkeynum').attr("readonly",true);
			  $('#batchsplit').attr("readonly",true);
			  $('#batchstartnum').attr("readonly",true);
			  
			  $('#operForm').form('clear').form('load',rowData);
			  var ck_channels = rowData.channel.split(",");//获取那些渠道，然后赋值给复选框
			  for(var i=0;i<ck_channels.length;i++){
				 $("#operForm input[type='checkbox']").each(function(){
					 var checkValue = $(this).val();
					 if(ck_channels[i]==checkValue){
					 	$(this).attr("checked",true);
					 }
					 $(this).attr("disabled","disabled");
				 });
			  }
			  $('#operarea').panel('setTitle', '查看信息模板');
			  $('#selectRow').val(rowIndex);
		  }
		}
		if(isedit==0)
		{
			$('#divupd').css("display","block");
			$('#divsave').css("display","none");
			$("[name='templateId']").attr("readonly",true);
			operTab.datagrid('options').queryParams={templateId:rowData.templateId};
			operTab.datagrid("load");
			$('#operForm').form('clear').form('load',rowData);
			var ck_channels = rowData.channel.split(",");
			 for(var i=0;i<ck_channels.length;i++){
				 $("#operForm input[type='checkbox']").each(function(){
					 var checkValue = $(this).val();
					 if(ck_channels[i]==checkValue){
					 	$(this).attr("checked",true);
					 }
					 $(this).attr("disabled","disabled");
				 });
			  }
			$('#operarea').panel('setTitle', '查看信息模板');
			$('#selectRow').val(rowIndex);
		}
	},
    columns:[[
	{field:'op',title:'删除',width:100,formatter:function(v,row){
	    return "<input type='checkbox' value='"+row["templateId"]+"' name='chk_itemId'>"
	 }}, 
     {field:'templateCode',title:'微信模板号',width:320},   
     {field:'templateName',title:'模板名称',width:320},
    {field:'channel',title:'渠道名称',width:350,formatter:function(v){
    	return v.replace(/10/g,"APP").replace(/20/g,"微信").replace(/30/g,"短信");
      }} 
    ]],toolbar:[{
		id:'btnadd',
		text:'新建',
		iconCls:'icon-add', 
		handler:function(){
			//取消选中当前行
			centerTab.datagrid('unselectAll');
			$("#operForm").form("clear");
			$('#isedit').val("1");
			$('#templateId').prop('readonly', false);
			 
			$('#operarea').panel('setTitle','添加信息模板');
			$('#divupd').css("display","none");
			$('#divsave').css("display","block");
			
			$('#templateName').removeAttr("readonly");
			$('#templateCode').removeAttr("readonly");
			$('#templateContent').removeAttr("readonly");
			$('#freeuse1').removeAttr("readonly");
			$("#operForm input[type='checkbox']").each(function(){
				 $(this).removeAttr("disabled");
		    });
			$('#centerid').combobox('enable');
			$('#batchkeynum').removeAttr("readonly");
			$('#batchsplit').removeAttr("readonly");
			$('#batchstartnum').removeAttr("readonly");
			
			operTab.datagrid('loadData', { total: 0, rows: [] }); 
		  }
		},{	
		id:'btndel',
		text:'删除',
		iconCls:'icon-remove', 
		handler:function(){
		       var mi405data=[];  
		       $("[name='chk_itemId']:checked").each(function(i){
		    	   mi405data.push($.trim($(this).val()));
		       });
		       console.info(mi405data);
		       if(mi405data.length > 0){
					$.messager.confirm('提示','确认是否删除?',function(r){
						if (r) {
							var map={itemId:mi405data.join(",")}	      
		       				$.ajax({
								type : "POST",
								url : "page30402Del.json",
								dataType: "json",
								data:map,
								success :function(data) {
									if(SUCCESS_CODE == data.recode){
										centerTab.datagrid("reload");
										operTab.datagrid("reload");
										$("#templateId").prop("readOnly", false);
										$("#operForm").form("clear");
										$('#divupd').css("display","none");
										$('#divsave').css("display","block");
										$('#isedit').val("1");
										$('#templateName').removeAttr("readonly");
										$('#templateCode').removeAttr("readonly");
										$('#templateContent').removeAttr("readonly");
										$('#freeuse1').removeAttr("readonly");
										$("#operForm input[type='checkbox']").each(function(){
											 $(this).removeAttr("disabled");
									    });
										('#centerid').combobox('enable');
										$('#batchkeynum').removeAttr("readonly");
										$('#batchsplit').removeAttr("readonly");
										$('#batchstartnum').removeAttr("readonly");
										$('#operarea').panel('setTitle','添加信息模板');
									}else{
										$.messager.alert('错误', data.msg, 'error');
									}
								},
								error :function(){
									$.messager.alert('错误','系统异常','error');
								}
			   				});
						}
					});
		       }else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
		   }
		}]
 });

 
//设置分页控件 
 var p = $("#centerTab").datagrid('getPager'); 
 $(p).pagination({ 
     pageSize: 15,//每页显示的记录条数，默认为10 
     showPageList:false,
     //pageList: [5,10,15],//可以设置每页记录条数的列表 
     beforePageText: '第',//页数文本框前显示的汉字 
     afterPageText: '页    共 {pages} 页', 
     displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
     /*onBeforeRefresh:function(){
         $(this).pagination('loading');
         alert('before refresh');
         $(this).pagination('loaded');
     }*/ 
 }); 
 /* datagrid结束 */
 operTab=$("#operTab").datagrid({
	width:$(document).width()-$('#list-container').width()-28,
    height:$(document).height()-$('#operarea').height()-74,
    idField:'templateId',
	singleSelect:true,
    url:'page3040201Qry.json',
    onDblClickRow:function(rowIndex, rowData){
	 // $('#operForm').form('clear').form('load',rowData);
    	editRow(rowData.templateDetailId);
    },
    columns:[[
     {field:'serialNumber',title:'要素序号',width:100},   
     {field:'templateDetailName',title:'要素占位符',width:200},
     {field:'templateDetailRemark',title:'要素名称',width:200,formatter:function(v){
         if(v==null)
       	 {
       	 	return "双击修改备注！";
       	 }else
   		 {
   		 	return v;
   		 }
      }},
     {field:'datecreated',title:'创建时间',width:200},
    ]]
 });
 
 function editRow(templateDetailId) {
		$.ajax({
			type : 'POST',
			url : "<%=request.getContextPath()%>/page3040202.json",
				dataType : 'json',
				data : {
					templateDetailId : templateDetailId
				},
				success : function(data) {
					if (SUCCESS_CODE == data.recode) {
						var load = data.result;
						//console.info(load);
						var href = "page3040203.html";
						var title = "编辑要素说明";
						$('#d_dlg').show().dialog({
							title : title,
							width : 850,
							height : 500,
							closed : false,
							cache : false,
							href : href,
							onLoad : function() {
								ydl.setDlgPosition('d_dlg');//对话框高度超出页面高度时，设置top为0px
								$('#h_formName').attr('value', 'form30402');
								$('#form30402').form('load', load);
								//var arr = load.pusMessageType.split(',');
								//$('#pusMessageType').combobox('setValues', arr);
							},
							buttons : '#dlg-buttons',
							modal : true
						});
						$('#savebutton').show();
					} else {
						$.messager.alert('提示', data.msg, 'info');
					}
				},
				error : function() {
					$.messager.alert('错误', '系统异常', 'error');
				}
			});
		}

		//点击保存
		$("#btnsave").click(function() {
			var mi002arry = $('#operForm').serializeArray();
			console.info(mi002arry);
			var url = null;
			if ($(operForm.templateId).attr("readonly"))
				url = "page30402Upd.json";
			else
				url = "page30402Add.json";
			var tgbz = $("#operForm").form("validate");
			if (tgbz) {
				$.ajax({
					type : "POST",
					url : url,
					dataType : "json",
					data : mi002arry,
					success : function(data) {
						if (SUCCESS_CODE == data.recode) {
							$('#divupd').css("display", "block");
							$('#divsave').css("display", "none");
							$('#isedit').val("0");
							$("#operForm input[type='checkbox']").each(function(){
								 $(this).attr("disabled","disabled");
							 });
							$('#templateName').attr("readonly", true);
							$('#templateCode').attr("readonly", true);
							$('#templateContent').attr("readonly", true);
							$('#centerid').combobox('disable');
							$('#freeuse1').attr("readonly",true);
							$("[name='templateId']").attr("readonly", true);
							$('#batchkeynum').attr("readonly",true);
							$('#batchsplit').attr("readonly",true);
							$('#batchstartnum').attr("readonly",true);
							$('#templateId').val(data.templateId);
							centerTab.datagrid("reload");
							operTab.datagrid('options').queryParams = {
								templateId : data.templateId
							};
							operTab.datagrid("reload");
						} else {
							$.messager.alert('错误', data.msg, 'error');
						}
					},
					error : function() {
						$.messager.alert('错误', '系统异常', 'error');
					}
				});
			}
		});

		//生成要素
		$("#btnbuild").click(function() {
			$.messager.confirm('提示','生成要素会覆盖之前的，确定生成吗？',function(r){
				if(r){
					var mi002arry = $('#operForm').serializeArray();
					var url = "page30402Build.json";
					var tgbz = $("#templateContent").val();
					if (tgbz == '')
						$.messager.alert('错误', '信息内容为空', 'error');
					if (tgbz != '') {
						$.ajax({
							type : "POST",
							url : url,
							dataType : "json",
							data : mi002arry,
							success : function(data) {
								if (SUCCESS_CODE == data.recode) {
									console.info(data);
									$("#operTab").datagrid('options').queryParams = {
										templateId : data.templateId
									};
									$("#operTab").datagrid("reload");
								} else {
									$.messager.alert('错误', data.msg, 'error');
								}
							},
							error : function() {
								$.messager.alert('错误', '系统异常', 'error');
							}
						});
					}
				}
			});
		});

		//编辑模版
		$("#btnupd").click(function() {
			$('#divupd').css("display", "none");
			$('#divsave').css("display", "block");
			$('#isedit').val("1");
			$('#templateName').removeAttr("readonly");
			$('#templateCode').removeAttr("readonly");
			$('#templateContent').removeAttr("readonly");
			$('#freeuse1').removeAttr("readonly");
			$("#operForm input[type='checkbox']").each(function(){
				 $(this).removeAttr("disabled");
			 });
			$('#centerid').combobox('enable');
			$('#batchkeynum').removeAttr("readonly");
			$('#batchsplit').removeAttr("readonly");
			$('#batchstartnum').removeAttr("readonly");
		});

		//设置页面结构高度宽度
		$('#layout').height($(document).height() - 30);
		$('#list-container,#form-container').height($(document).height() - 40);
	});
	/* 以上是页面初始化的内容 */
	
	
	
	function saveTemplateDetail() {
		var paras = $('#form30402').serializeArray();

		//校验
		//if (!ydl.formValidate($('#h_formName').val())) return;
		if (!$('#form30402').form("validate"))
			return;
		$.ajax({
			type : "POST",
			url : "page3040204.json",
			dataType : "json",
			data : paras,
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');
				if (data.recode == SUCCESS_CODE) {
					$('#operTab').datagrid('reload');
					$('#d_dlg').dialog('close');
				}
			},
			error : function() {
				$.messager.alert('错误', '网络连接出错！', 'error');
				ydl.displayRunning(false);
			}
		});
	}
</script>
</head>
<body>
	<div id="layout" class="easyui-panel easyui-layout" title="信息模板管理">
		<div id="list-container"
			data-options="region:'west',split:false,border:false">
			<!-- 模板列表 开始-->
			<table id="centerTab"></table>
			<!-- 模板列表 结束-->
		</div>
		<div id="d_dlg"></div>
		<div id="dlg-buttons">
			<a id="savebutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveTemplateDetail()">保存</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="$('#d_dlg').dialog('close')">关闭</a>
		</div>
		<input type="hidden" id='h_action' /> <input type="hidden"
			id='h_formName' />
		<div id="form-container" data-options="region:'center',border:false">
			<div id="operarea" class="easyui-panel" title="添加信息模板">
				<br /> &nbsp;&nbsp;&nbsp;注：信息内容中 {key} 作为替换内容，自动添加要素信息
				<form id="operForm" class="dlg-form" novalidate="novalidate">
					<input type='hidden' id="templateId" name="templateId" value="" />
					<input type='hidden' id="isedit" name="isedit" value="1" /> 
					<input type='hidden' id="selectRow" name="selectRow" value="" />
					<table class="container">
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
						<tr>
							<td><label for="channel" >所属渠道：</label></td>
							<td>
								<input type="checkbox" name="channel" value="10"/>APP
								<input type="checkbox" name="channel" value="20"/>微信 
								<input type="checkbox" name="channel" value="30"/>短信
								<input type="checkbox" name="channel" value="40"/>通用
								<%-- <select id="channelItemId" name="channelItemId"
								class="easyui-combobox" style="width:220px;" required="true"
								editable="false">
								<option value="04">通用</option>
									 panelHeight="auto"  
									<%
										String options = "";
										JSONArray ary = (JSONArray) request.getAttribute("channelList");
										for (int i = 0; i < ary.size(); i++) {
											JSONObject obj = ary.getJSONObject(i);
											String itemid = obj.getString("itemid");
											String itemval = obj.getString("itemval");
											options = (new StringBuilder(String.valueOf(options)))
													.append("<option value=\"").append(itemid)
													.append("\">").append(itemval).append("</option>\n")
													.toString();
										}
										out.println(options);
									%>
							</select>  --%>
						</td>
						<td><label for="centerid">所属城市中心：</label></td>
							<td><select id="centerid" name="centerid"
								class="easyui-combobox" style="width: 230px;" required="true"
								editable="false"> panelHeight="auto"
								<option value=""/>请选择...
									<%
								String options = "";
								List<Mi001> mi001List = (List<Mi001>) request.getAttribute("mi001List");
								for (int i = 0; i < mi001List.size(); i++) {
									String centername = mi001List.get(i).getCentername();
									String centerid = mi001List.get(i).getCenterid();
									options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(centerid)
											.append("\">").append(centername).append("</option>\n").toString();
								}
								out.println(options);
							%>
							</select></td>
						</tr>

						<tr>
							<td><label for="freeuse1">业务模板号：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="freeuse1" name="freeuse1" required='true'
								class="easyui-validatebox" maxlength=300 /></td>
							<td><label for="templateName">模板名称：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="templateName" name="templateName" required='true'
								class="easyui-validatebox" maxlength=30 /></td>
						</tr>
						<tr>
							<td><label for="templateCode">微信模板号：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="templateCode" name="templateCode" required='true'
								class="easyui-validatebox" maxlength=15 /></td>
							<td><label for="batchkeynum">批量关键字序号：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="batchkeynum" name="batchkeynum" required='true'
								class="easyui-validatebox" maxlength=15 /></td>
						</tr>
						<tr>
							<td><label for="batchsplit">批量文件分割符：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="batchsplit" name="batchsplit" required='true'
								class="easyui-validatebox" maxlength=15 /></td>
							<td><label for="batchstartnum">批量记录起始位：</label></td>
							<td><input style="width: 230px; height: 20px;" type='text'
								id="batchstartnum" name="batchstartnum" required='true'
								class="easyui-validatebox" maxlength=15 /></td>
						</tr>
						<tr>
							<td><label>信息内容：</label></td>
							<td colspan="3"><textarea id="templateContent" name="templateContent"
									class="easyui-validatebox" rows="3" cols="102"
									style="resize: none;" required='true'></textarea></td>
							
						</tr>
					</table>
					<div class="buttons" id="divupd" style="display: none">
						<a style="width: 66px;" class="easyui-linkbutton"
							href="javascript:void(0)" id="btnupd">编辑模版</a>
					</div>
					<div class="buttons" id="divsave" style="display: block">
						<a style="width: 62px;" class="easyui-linkbutton"
							iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a> <a
							style="width: 86px;" class="easyui-linkbutton"
							iconCls="icon-build" href="javascript:void(0)" id="btnbuild">生成要素</a>
					</div>
				</form>
			</div>
			<div style="padding-top: 5px;"></div>
			<!-- 要素列表 开始-->
			<table id="operTab" title="要素信息列表"></table>
			<!-- 要素列表 结束-->
		</div>
	</div>

</body>
</html>