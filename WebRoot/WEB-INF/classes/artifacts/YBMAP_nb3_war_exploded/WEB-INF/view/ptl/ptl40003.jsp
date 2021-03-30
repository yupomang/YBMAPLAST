<%@ page language="java" contentType="text/html; charset=utf-8" %>
<% 
  /*
     文件名：ptl40003.jsp
     作者： 韩占远
     日期：2013-10-06 
     作用：中心管理
  */
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="com.yondervision.mi.common.PermissionContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
  PermissionContext pc = PermissionContext.getInstance();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>中心管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container{width:650px; padding:5px;}
#form-container{padding:5px 0px;}
#listDlg {float: left; width: 55%; padding:5px;}
#formDlg {float: left; width: 40%;padding:5px;}
#dlg {width:790px; height:520px; padding:10px 20px;}
#areaformDlg {float: left; width: 40%;padding:5px;}
#areaDlg {width:790px; height:520px; padding:10px 20px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">
$(function(){

	$("#centerTab").datagrid({
		width:$('#list-container').width(),
		height:$(document).height()-40,
		fitColumns: true,
		idField:'centerid',
		singleSelect:true,
		queryParams:{centerid:'<%=user.getCenterid()%>'},
		url:'ptl40003Qry.json',
		onClickRow:function(rowIndex, rowData){
			$("[name='centerid']").prop("readOnly",true);
			  /*
			  for(var key in rowData ){
				if(centerForm[key]){
				   if(rowData[key])
					 centerForm[key].value=rowData[key];
				   else
					 centerForm[key].value="";
				}   
			  }  
			  */
			$('#centerForm').form('clear').form('load',rowData);
			$('#centerInfo').panel('setTitle','修改中心信息');
			$("#btnsave").linkbutton("enable");
		},
		columns:[[
			{field:'op',title:'操作',width:35,formatter:function(v,row){
				return "<input type='checkbox' value='"+row["centerid"]+"' name='chk_centerid'>";
			}}, 
			{field:'freeuse1',title:'城市名称',width:70},
			{field:'centerid',title:'中心代码',width:70},
			{field:'centername',title:'中心名称',width:150},
			{field:'contactname',title:'联系人',width:100},
			{field:'contacttel',title:'联系人电话',width:90},
			{field:'custsvctel',title:'客服电话',width:90},
			{field:'portalurl',title:'中心官网',width:100},
			{field:'postcode',title:'邮政编码',width:60},
			{field:'freeuse2',title:'备注',width:60},
			{field:'validflag',title:'状态',width:40,formatter:function(v,row){
				var stat;
				if('0' == row["validflag"]){
					stat="无效"; 
				}else{
					stat="有效"; 
				}
				return stat;
			}}
		]],
		toolbar:[{
			id:'btnadd',
			text:'新建',
			iconCls:'icon-add', 
			handler:function(){
				  $("[name='centerid']").prop("readOnly",false);
				  //centerForm.reset();
				  $('#centerForm').form('clear');
				  $('#centerInfo').panel('setTitle','添加中心信息');
			  }
			},{	
				id:'btndel',
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var mi002data=[];
				 	$("[name='chk_centerid']:checked").each(function(i){
						mi002data.push($.trim($(this).val()));
				 	});
				 	if(mi002data.length==0){
						$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				 	}else{
				 		$.messager.confirm('提示','确认是否删除?',function(r){
							if (r) {
								var map={centerid:mi002data.join(",")}	      
				   				$.ajax({	 
									type : "POST",
									url : "ptl40003Del.json?userCenterid="+'<%=user.getCenterid()%>',
									dataType: "json",
									data:map,
									success :function(data) {
										$.messager.alert('提示', data.msg, 'info');
										if(SUCCESS_CODE == data.recode){
											$("#centerTab").datagrid("reload");
											$('#centerForm').form('clear');	
										}
									},
										error :function(){
											$.messager.alert('错误','系统异常','error');							
										}
				   				});
							}
						})
					} 
				}
		},{	
				id:'btnact',
				text:'激活',
				iconCls:'icon-search', 
				handler:function(){
					var mi002data=[];  
				 	$("[name='chk_centerid']:checked").each(function(i){
						mi002data.push($.trim($(this).val()));
				 	});
				 	if(mi002data.length==0){
						$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				 	}else{
				 		$.messager.confirm('提示','确认是否激活?',function(r){
				 			if(r){
								var map={centerid:mi002data.join(",")}
								$.ajax({	 
									type : "POST",
									url : "ptl40003Activ.json?userCenterid="+'<%=user.getCenterid()%>',
									dataType: "json",
									data:map,
									success :function(data) {
										$.messager.alert('提示', data.msg, 'info');
										if(SUCCESS_CODE == data.recode){
											$("#centerTab").datagrid("reload");
											$('#centerForm').form('clear');
										}
									},
										error :function(){
											$.messager.alert('错误','系统异常','error');							
										}
								});
				 			}
				 		})
					} 
				}
		},{
			id:'opendlg',
			text:'客服通讯信息',
			iconCls:'icon-search', 
			handler:function(){
				  var mi001data=[];
				  $("[name='chk_centerid']:checked").each(function(i){
					  mi001data.push($.trim($(this).val()));
				   });
				  if(mi001data.length!=1){
					 //alert("客服通讯信息维护必须选中一条中心信息!");
					 $.messager.alert('提示', '客服通讯信息维护必须选中一条中心信息', 'info' );
				  }else{
						$('input[name=centerid]').eq(1).val(mi001data[0]);
						$('#dlg').dialog('open').dialog('setTitle','中心客服通讯信息维护');
						ydl.setDlgPosition('dlg');//对话框高度超出页面高度时，设置top为0px
						$("#centerTabDlg").datagrid({
						//width:400,
						//height:$(document).height()-40,
						height:450,
						fitColumns: true,
						idField:'serviceid',
						singleSelect:true,
						url:'webapi40208.json?centerid='+mi001data[0],
						onClickRow:function(rowIndex, rowData){
						  $("[name='serviceid']").prop("readOnly",true);
						  /*
						  for(var key in rowData ){
							if(centerFormDlg[key]){
							   if(centerFormDlg[key])
								 centerFormDlg[key].value=rowData[key];
							   else
								 centerFormDlg[key].value="";
							}   
						  }
						  */
							$('#centerFormDlg').form('clear').form('load',rowData);
							$('#oldcustsvctype').val(rowData.custsvctype);
							$('#oldcustsvcaccnum').val(rowData.custsvcaccnum);
							$('#custInfo').panel('setTitle','修改服务信息');
						},
						columns:[[
						 {field:'op',title:'操作',width:40,formatter:function(v,row){
							return "<input type='checkbox' value='"+row["serviceid"]+"' name='chk_serviceid'>"
						 }}, 
						 {field:'serviceid',title:'客服ID',hidden:'true',width:1},
						 {field:'centerid',title:'城市ID',hidden:'true',width:1},
						 {field:'custsvctype',title:'客服类型',width:99,formatter:custsvctypeformater},
						 {field:'custsvcaccnum',title:'客服帐号',width:150},
						 {field:'custsvcaccname',title:'客服帐户名称',width:150},
						 {field:'webaddress',title:'URL',width:150}		    
						]],
						toolbar:[{
							id:'btnaddDlg',
							text:'新建',
							iconCls:'icon-add', 
							handler:function(){						
								  $("[name='serviceid']").prop("readOnly",false);
								  //centerFormDlg.reset();
								  $('#centerFormDlg').form('clear');
								  $('#custsvctype').combobox('setValue', '0');
								  var mi001data=[];
				                  $("[name='chk_centerid']:checked").each(function(i){
					                 mi001data.push($.trim($(this).val()));
				                  });
				                  $('input[name=centerid]').eq(1).val(mi001data[0]);
								  //$("#custsvcaccnum").val("");
								  //$("#custsvcaccname").val("");
								  //$("#webaddress").val("");
								  $('#custInfo').panel('setTitle','添加服务信息');
							  }
							},{	
							id:'btndelDlg',
							text:'删除',
							iconCls:'icon-remove', 
							handler:function(){
									var mi102data=[];  
									$("[name='chk_serviceid']:checked").each(function(i){
										mi102data.push($.trim($(this).val()));
									});
									if(mi102data.length == 0){
										$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
									}else{
										$.messager.confirm('提示','确认是否删除?',function(r){
				  				   			if (r) {
												var map={serviceid:mi102data.join(",")}	      
								   				$.ajax({	 
													type : "POST",
													url : "webapi40206.json",
													dataType: "json",
													data:map,
													success :function(data) {
														$.messager.alert('提示', data.msg, 'info');
														if(SUCCESS_CODE == data.recode){
															$("#centerTabDlg").datagrid("reload");	
															centerFormDlg.reset();	
														}								    				   
													},
													error :function(){
														$.messager.alert('错误','系统异常','error');
													}
								   				});
											}
										})
									}
								}
							}]
						});
				  }  
			  }
			},{
			id:'buzAreaInfoDlg',
			text:'区域信息',
			iconCls:'icon-search',
			handler:function(){
				  var mi001data=[];
				  $("[name='chk_centerid']:checked").each(function(i){
					  mi001data.push($.trim($(this).val()));
				   });
				  if(mi001data.length!=1){
					 //alert("区域信息维护必须选中一条中心信息!");
					 $.messager.alert('提示', '区域信息维护必须选中一条中心信息', 'info' );
				  }else{	
						$('input[name=centerid]').eq(2).val(mi001data[0]);
						$('#areaDlg').dialog('open').dialog('setTitle','区域信息维护');
						ydl.setDlgPosition('areaDlg');//对话框高度超出页面高度时，设置top为0px
						$("#areaTabDlg").datagrid({
						//width:400,
						//height:$(document).height()-40,
						height:450,
						fitColumns: true,
						idField:'areaId',
						singleSelect:true,
						url:'webapi20204.json?centerid='+mi001data[0],
						onClickRow:function(rowIndex, rowData){
						  $("[name='areaId']").prop("readOnly",true);
						  /*
						  for(var key in rowData ){
							if(centerFormDlg[key]){
							   if(centerFormDlg[key])
								 centerFormDlg[key].value=rowData[key];
							   else
								 centerFormDlg[key].value="";
							}   
						  }
						  */
							$('#areaFormDlg').form('clear').form('load',rowData);
							$('#areaInfo').panel('setTitle','修改区域信息');
							$("[name='areaCode']").prop("readOnly",true);
							$("[name='areaName']").prop("readOnly",false);
						},
						columns:[[
						 {field:'op',title:'操作',width:40,formatter:function(v,row){
							return "<input type='checkbox' value='"+row["areaId"]+"' name='chk_areaId'>"
						 }}, 
						 {field:'areaId',title:'区域ID',hidden:'true',width:1},
						 {field:'centerid',title:'城市ID',hidden:'true',width:1},
						 {field:'freeuse4',title:'顺序号',hidden:'true',width:1},
						 {field:'areaCode',title:'区域代码',width:99},   
						 {field:'areaName',title:'区域名称',width:150}			    
						]],
						toolbar:[{
							id:'btnaddAreaDlg',
							text:'新建',
							iconCls:'icon-add', 
							handler:function(){						
								  $("[name='areaId']").prop("readOnly",false);
								  $('#areaInfo').panel('setTitle','添加区域信息');
								  $("[name='areaCode']").prop("readOnly",false);
								  $("[name='areaName']").prop("readOnly",false);
								  $("#areaCode").val("");
								  $("#areaName").val("");
							  }
							},{	
							id:'btndelAreaDlg',
							text:'删除',
							iconCls:'icon-remove', 
							handler:function(){
								   var mi202data=[];  
								   $("[name='chk_areaId']:checked").each(function(i){
									  mi202data.push($.trim($(this).val()));
								   });
								   if(mi202data.length==0){
					 					$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				  				   }else{
				  				   		$.messager.confirm('提示','确认是否删除?',function(r){
				  				   			if (r) {
				  				   				var map={areaId:mi202data.join(",")}
								   				$.ajax({	 
													type : "POST",
													url : "webapi20202.json",
													dataType: "json",
													data:map,
													success :function(data) {
														$.messager.alert('提示', data.msg, 'info');	
														if (SUCCESS_CODE == data.recode){
															$("#areaTabDlg").datagrid("reload");	
															areaFormDlg.reset();
														}			   		   
													},
													error :function(){
														$.messager.alert('错误','系统异常','error');							
													}
								   				});
				  				   			
				  				   			}
				  				   		})
				  				   }
							   }
							},
							{
							id:'b_moveup',
							text:'上移',
							iconCls:'icon-up', 
							handler:function(){					
								var row = $("#areaTabDlg").datagrid('getSelected'); 
								if(row==null){
									return;
								}
							    var index = $("#areaTabDlg").datagrid('getRowIndex', row);
							    mysort(index, 'up', 'areaTabDlg');
							  }
							},
							{
							id:'b_movedown',
							text:'下移',
							iconCls:'icon-down', 
							handler:function(){						
							    var row = $("#areaTabDlg").datagrid('getSelected');
								if(row==null){
									return;
								}
							    var index = $("#areaTabDlg").datagrid('getRowIndex', row);
							    mysort(index, 'down', 'areaTabDlg');
							  }
							},
							{
							id:'b_savesort',
							text:'保存顺序',
							iconCls:'icon-save', 
							handler:function(){	
								
								var param="[";
								 var  data = $("#areaTabDlg").datagrid('getRows');
								 if(data==null||data.length==0){
									 alert("表格中无数据");
									 return;
								 }
								 for(var i=0;i<data.length;i++){
									var row = $('#areaTabDlg').datagrid('getData').rows[i];;
									if(i!=0)param+=",";			
									param+="{'seqid':"+row['areaId']+",'num':"+(i+1)+"}";
									
								 }
								 param+="]"; 
								  $.ajax( {
										type : 'POST',
										url : '<%=request.getContextPath()%>/webapi20205.json?datalist='+param,
										dataType: 'json',
										success : function(data) {
											$.messager.alert('提示', data.msg, 'info');
										},
										error :function(){
											$.messager.alert('错误','系统异常','error');							
										}
									});
									
							  }
							}
							]
						});
				  }  
			  }
			}]
    });
    
    // 页面按钮权限控制-增加
	var addflg = <%=pc.isCan("add")%>;
	if(!addflg){
		//$("#btnadd").hide();
		$("#btnadd").linkbutton("disable");
		$("#btnsave").linkbutton("disable");
	}
	// 页面按钮权限控制-删除
	var delflg = <%=pc.isCan("del")%>;
	if(!delflg){
		$("#btndel").linkbutton("disable");
	}
	// 页面按钮权限控制-激活
	var actflg = <%=pc.isCan("active")%>;
	if(!actflg){
		$("#btnact").linkbutton("disable");
	}
	
	//保存
	$("#btnsave").click(function(){
		var mi002arry = $('#centerForm').serializeArray();        
		var url = null;     
		if ($(centerForm.centerid).attr("readonly")) url="ptl40003Upd.json";
		else url="ptl40003Add.json"; 
		var tgbz=$("#centerForm").form("validate");      
		if (tgbz){         
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi002arry,
				success :function(data) {
					$.messager.alert('提示', data.msg, 'info');
					if(SUCCESS_CODE == data.recode){
						$("#centerTab").datagrid("reload");
						$("#centerForm").form('clear');
					}			  
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');
				}
			});
		} 
	}); 
	//对话框保存
	$("#btnsaveDlg").click(function(){
	    var custsvctypeVal = $('#custsvctype').combobox('getValue');
    	if(custsvctypeVal == "0"){
        	$.messager.alert('提示', "请选择客服类型!", 'info' );
        	return;
    	}
    	
		var mi102arry = $('#centerFormDlg').serializeArray();        
		var url=null;     
		if($(centerFormDlg.serviceid).prop("readOnly")) url="webapi40207.json";
		else url="webapi40205.json";   
		var tgbz=$("#centerFormDlg").form("validate");
		
		if(tgbz){         
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi102arry,
				success :function(data) {
					$.messager.alert('提示', data.msg, 'info');
					if(SUCCESS_CODE == data.recode){
						$("#centerTabDlg").datagrid("reload");	
					}				  
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');							
				}
			});
		} 
	});
	
	//区域维护对话框保存
	$("#btnsaveAreaDlg").click(function(){				    
		var mi202arry = $('#areaFormDlg').serializeArray();        
		var url=null;     
		if($(areaFormDlg.areaId).prop("readOnly")) url="webapi20203.json";
		else url="webapi20201.json";   
		var tgbz=$("#areaFormDlg").form("validate");   
		if(tgbz){
			$.ajax({	 
					type : "POST",
					url : url,
					dataType: "json",
					data:mi202arry,
					success :function(data) {				    
						$.messager.alert('提示', data.msg, 'info');
						if (SUCCESS_CODE == data.recode){
							$("#areaTabDlg").datagrid("reload");
							$("#areaCode").val("");
							$("#areaName").val("");
						}
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');							
					}
			});
		}
	});
	
	
	//设置页面结构高度宽度
	$('#layout').panel({   
		width:$(document).width()-1,
		height:$(document).height()-1
	});
	$('#list-container,#form-container').height($(document).height()-30);
	//中心信息表单标题
	$('#centerInfo').panel({
		title:'添加中心信息', 
		//height:$(document).height()-40,
		height:$('#form-container').height()-10,
		width:$('#form-container').width(),
	});
	
})

function mysort(index, type, gridname) {
    if ("up" == type) {
        if (index != 0) {
            var toup = $('#' + gridname).datagrid('getData').rows[index];
            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
            $('#' + gridname).datagrid('getData').rows[index] = todown;
            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index - 1);
            $('#' + gridname).datagrid('selectRow', index - 1);
        }
    } else if ("down" == type) {
        var rows = $('#' + gridname).datagrid('getRows').length;
        if (index != rows - 1) {
            var todown = $('#' + gridname).datagrid('getData').rows[index];
            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridname).datagrid('getData').rows[index] = toup;
            $('#' + gridname).datagrid('refreshRow', index);
            $('#' + gridname).datagrid('refreshRow', index + 1);
            $('#' + gridname).datagrid('selectRow', index + 1);
        }
    }
 
} 


function custsvctypeformater(value,row,index){
 	return $('#custsvctype option[value='+value+']').text();
}
</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="中心管理"> 
	<div id="list-container" data-options="region:'west',split:false,border:false">
		<table id="centerTab"></table>
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<!-- 中心列表开始 -->
		<div id="centerInfo"> 
		<form id="centerForm" class="dlg-form" novalidate="novalidate">        
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label>城市名称：</label></td>
				<td><input type='text' name="freeuse1" required='true' class="easyui-validatebox"  maxlength="20" size="30"/></td>
			</tr>
			<tr>
				<td><label>中心代码：</label></td>
				<td><input type='text' name="centerid" required='true' class="easyui-validatebox"  maxlength="8" size="30" validType="numletter"/></td>
			</tr>
			<tr>
				<td><label for="centername">中心名称：</label></td>
				<td><input type='text' id="centername" name="centername" required='true' class="easyui-validatebox" maxlength="50" size="30"/> </td>
			</tr>
			<tr>
				<td><label for="contactname">联系人：</label></td>
				<td><input type='text' id="contactname" name="contactname" required='true' class="easyui-validatebox" maxlength="20" size="30"/> </td>
			</tr>
			<tr>
				<td><label for="contacttel">联系人电话：</label></td>
				<td><input type='text' id="contacttel" name="contacttel" required='true' class="easyui-validatebox" maxlength="20" size="30" validType="phone"/> </td>
			</tr>
			<tr>
				<td><label for="custsvctel">客服电话：</label></td>
				<td><input type='text' id="custsvctel" name="custsvctel" required='true' class="easyui-validatebox" maxlength="20" size="30" validType="phone"/> </td>
			</tr>
			<tr>
				<td><label for="portalurl">中心官网：</label></td>
				<td><input type='text' id="portalurl" name="portalurl" required='true' class="easyui-validatebox" maxlength="50" size="30"/> </td>
			</tr>
			<tr>
				<td><label for="postcode">邮政编码：</label></td>
				<td>
					<input type='text' id="postcode" name="postcode" required='true' class="easyui-validatebox" maxlength="6" size="30" validType="zipcode"/>
					<input type="hidden" id="validflag" name="validflag" />
				</td>
			</tr>
			<tr>
				<td><label for="freeuse2">备注：</label></td>
				<td>
					<input type='text' id="freeuse2" name="freeuse2"  class="easyui-validatebox" maxlength="40" size="30" />
				</td>
			</tr>         
		</table>
		<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
		</form> 
		</div>
		<!-- 中心列表结束 -->
	 </div>
</div>
<!-- 对话框开始 -->
<div id="dlg" class="easyui-dialog" closed="true" buttons="#dlg-buttons"> 
	<div id="listDlg">
		<!-- 中心客服通迅信息列表开始 -->
		<table id="centerTabDlg"></table>
		<!-- 中心客服通迅信息列表结束 -->
	</div>
	<div id="formDlg">
		<div id="custInfo" class="easyui-panel" title="添加服务信息" data-options="height:450"> 
		<form id="centerFormDlg" class="dlg-form" novalidate="novalidate">
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="custsvctype">客服类型：</label></td>
				<td>
				     <select id="custsvctype" name="custsvctype" required="true" class="easyui-combobox" panelHeight="auto">
                            <option value="0"></option>
                            <%
                                String options = "";
                                   JSONArray ary=(JSONArray) request.getAttribute("contacttypelist");
                                   for(int i=0;i<ary.size();i++){
                                      JSONObject obj=ary.getJSONObject(i);
                                      String itemid=obj.getString("itemid");
                                      String itemval=obj.getString("itemval");
                                      options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(options);
                              %>
                        </select>
				</td>
			</tr>
			<tr>
				<td><label for="custsvcaccnum">客服帐号：</label></td>
				<td><input type='text' id="custsvcaccnum" name="custsvcaccnum" required='true' class="easyui-validatebox" maxlength="30" size="30"/> </td>
			</tr>
			<tr>
				<td><label for="custsvcaccname">客服帐户名称：</label></td>
				<td><input type='text' id="custsvcaccname" name="custsvcaccname" required='true' class="easyui-validatebox" maxlength="50" size="30"/> </td>
			</tr>
			<tr>
				<td><label for="webaddress">URL：</label></td>
				<td><input type='text' id="webaddress" name="webaddress" class="easyui-validatebox" maxlength="200" size="30"/> </td>
			</tr>               
		</table>
		<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsaveDlg">保存</a></div>
		<input type='hidden' name="serviceid" class="easyui-validatebox"  maxlength="8" size="30"/>
		<input type='hidden' name="centerid" class="easyui-validatebox"  maxlength="8" size="30"/>
		<input type='hidden' id="oldcustsvctype" name="oldcustsvctype"/>
		<input type='hidden' id="oldcustsvcaccnum" name="oldcustsvcaccnum"/>
		</form> 
		</div>
	</div>
	<div id="dlg-buttons">
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a> -->
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>
</div>
<!-- 对话框结束 -->
<!-- 区域信息维护对话框开始 -->
<div id="areaDlg" class="easyui-dialog" closed="true" buttons="#dlg-buttons"> 
	<div id="listDlg">
		<!-- 区域信息列表开始 -->
		<table id="areaTabDlg"></table>
		<!-- 区域信息列表结束 -->
	</div>
	<div id="areaformDlg">
		<div id="areaInfo" class="easyui-panel" title="添加区域信息" data-options="height:450"> 
		<form id="areaFormDlg" class="dlg-form" novalidate="novalidate">        
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="areaCode">区域代码：</label></td>
				<td> <input type='text' id="areaCode" name="areaCode" required='true' class="easyui-validatebox" readonly="readonly" maxlength="20" size="30"/></td>
			</tr>
			<tr>
				<td><label for="areaName">区域名称：</label></td>
				<td><input type='text' id="areaName" name="areaName" required='true' class="easyui-validatebox" readonly="readonly" maxlength="100" size="30"/> </td>
			</tr>                
		</table>
		<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsaveAreaDlg">保存</a></div>
		<input type='hidden' name="areaId" class="easyui-validatebox"  maxlength="8" size="30"/>
		<input type='hidden' name="centerid" class="easyui-validatebox"  maxlength="8" size="30"/>
		</form> 
		</div>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#areaDlg').dialog('close')">关闭</a>
	</div>
</div>
<!-- 区域信息维护对话框结束 -->
</body>
</html>