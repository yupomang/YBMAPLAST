<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/init.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>业务咨询项配置</title>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="业务咨询项配置">


<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>

		<script type="text/javascript">
			var jsonConsultType = [{"id":"10","text":"缴存"}, 
									{"id":"20","text":"提取"},
									{"id":"30","text":"贷款"}];
		
			$(function() {
				
				$('#tab20104').treegrid( {
					title : '业务咨询项配置',
					iconCls : 'icon-tip',
					height:$(parent).height()-150,
					rownumbers: false,
					animate:true,
					collapsible:true,
					fitColumns:true,
					url:'webapi20104.json',
					idField:'treeId',
					treeField:'name',
					showFooter:true,
					queryParams:{"centerId":"00001"},//TODO 查询条件中心编码
					frozenColumns : [ [ {
						title : '删除',
						field : 'checkbox',
						width : 50,
						formatter : function(value) {
							var values = value.split('-');
							if (values.length && values.length==3) {
								var cbId = values[2]=="1" ? "listId" : values[2]=="2" ? "listConsultItemId" : "listConsultSubItemId";
								var cbValue = values[0];
								var cbPValue = values[1];
								
								return '<input id="'+cbId+cbValue+'" name="'+cbId+'" type="checkbox" onChange="checkClick(\''+cbId+cbValue+'\');" level="'+values[2]+'" value="'+cbValue+'" pvalue="'+cbPValue+'"></input>';
							}
							return value;
						}
					} ] ],
					columns : [ [ {
						field : 'name',
						title : '名称',
						width : 200,
						formatter : function(value) {
							$(jsonConsultType).each(function(  ) {
								if( this.id == value ) {
									value = this.text;
									return false;
								}
							});
							return value;
						}
					},{
						field : 'orderNo',
						title : '序号',
						width : 200
					},{
						field : 'level',
						title : '类别',
						width : 150,
						rowspan : 2,
						formatter : function(value) {
							if( value == "1" )
								return "业务类型";
							if( value == "2" )
								return "业务咨询项";
							if( value == "3" )
								return "业务咨询子项";
							return value;
						}
					}, {
						field : 'conditionTitle',
						title : '公共条件标题',
						width : 220
					}
					] ]
				});
				
				$('#tab20104').treegrid( "collapse", "10" );
				
				$('#b_del').click(function(){
					$.messager.confirm('提示','是否要删除选中的业务咨询项极其子项？', function(isDel){
						if (isDel) {
							var arr = $('#form20102').serializeArray();
							if ($('input[name=listConsultItemId]:checked').length==0) {
								if ($('input[name=listConsultSubItemId]:checked').length!=0) {
									$.ajax( {
										type : "POST",
										url : "<%=request.getContextPath()%>/webapi20107.json",
										dataType: "json",
										data:arr,
										success : function(data) {
											$.messager.alert('提示', data.msg, 'info');
											if (data.recode==SUCCESS_CODE) {
												$('#tab20104').treegrid('reload');
											}
										},
										error :function(){
											$.messager.alert('错误','网络连接出错！','error');
										}
									});
								} else {
									$.messager.alert('提示', '请选择要删除的业务咨询项或业务咨询子项！', 'info');
								}
							} else if($('input[name=listConsultSubItemId]:checked').length!=0) {
								$.ajax( {
									type : "POST",
									url : "<%=request.getContextPath()%>/webapi20107.json",
									dataType: "json",
									data:arr,
									success : function(data) {
										if (data.recode==SUCCESS_CODE){
											$.ajax( {
												type : "POST",
												url : "<%=request.getContextPath()%>/webapi20102.json",
												dataType: "json",
												data:arr,
												success : function(data) {
													$.messager.alert('提示', data.msg, 'info');
													if (data.recode==SUCCESS_CODE) {
														$('#tab20104').treegrid('reload');
													}
												},
												error :function(){
													$.messager.alert('错误','网络连接出错！','error');
												}
											});
										} else {
											$.messager.alert('提示', data.msg, 'info');
										}
									},
									error :function(){
										$.messager.alert('错误','网络连接出错！','error');
									}
								});
							} else {
								$.ajax( {
									type : "POST",
									url : "<%=request.getContextPath()%>/webapi20102.json",
									dataType: "json",
									data:arr,
									success : function(data) {
										$.messager.alert('提示', data.msg, 'info');
										if (data.recode==SUCCESS_CODE) {
											$('#tab20104').treegrid('reload');
										}
									},
									error :function(){
										$.messager.alert('错误','网络连接出错！','error');
									}
								});
							}
						}
					});
				});
				
				
				$('#b_add').click(function(){
					var row = $('#tab20104').treegrid('getSelected');
					if (row) {
						if (row.level == '3') {
							$.messager.alert('提示','请选择上业务类型或业务咨询项进行添加!','info');
							return;
						}
						if (row.level == '1' ) {
							$('#page20101').window('open');
							loadForm20101( row );
						}
						if (row.level == '2' ) {
							$('#page20105').window('open');
							loadForm20105( row );
						}
					} else {
						$.messager.alert('提示','请选择上业务类型或业务咨询项进行添加!','info');
					}
				});
				
				$('#b_edit').click(function(){
					var row = $('#tab20104').treegrid('getSelected');
					if (row) {
						if (row.level == '1') {
							$.messager.alert('提示','业务类型不可修改!','info');
							return;
						}
						var parent = $('#tab20104').treegrid('getParent', row.treeId);
						if (row.level == '2' ) {
							$('#page20103').window('open');
							loadForm20103( row, parent.id );
						}
						if (row.level == '3' ) {
							$('#page20106').window('open');
							loadForm20106( row, parent.id );
						}
					} else {
						$.messager.alert('提示','请选择业务咨询项或业务咨询子项进行修改!','info');
					}
				});
				
			});
			
			function checkClick(id){
				var level = $('#'+id).attr('level');
				if (level == 1) {
					var $checkbox2 = $('input[name=listConsultItemId][pvalue='+$('#'+id).val()+']')
					$checkbox2.prop('checked',$('#'+id).prop('checked')).change();
				}
				else if (level == 2) {
					$('input[name=listConsultSubItemId][pvalue='+$('#'+id).val()+']').prop('checked',$('#'+id).prop('checked'));
				}
				// TODO 下级由选中改为不选中时上级也要改为不选中；浏览器兼容性
			}
		</script>
	</head>

	<body>
	<form id="form20102">
		<table id="tab20104"></table>
		<a href="#" id="b_del" class="easyui-linkbutton" >删除</a> 
		<a href="#" id="b_add" class="easyui-linkbutton" >增加</a>
		<a href="#" id="b_edit" class="easyui-linkbutton" >修改</a>
	</form>
	<jsp:include page="page20101.jsp"></jsp:include>
	<jsp:include page="page20103.jsp"></jsp:include>
	<jsp:include page="page20105.jsp"></jsp:include>
	<jsp:include page="page20106.jsp"></jsp:include>
	</body>
</html>
