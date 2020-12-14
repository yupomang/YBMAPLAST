<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="contexPath" content="<%=_contexPath%>" />
		<title>已推送公共短信息查询</title>
		<%@ include file="/include/styles.jsp"%>
		<link rel="stylesheet" type="text/css"
			href="<%= _contexPath %>/ui/message/comMessage.css" />
		<%@ include file="/include/scripts.jsp"%>
		<script type="text/javascript"
			src="<%=_contexPath%>/scripts/datagrid-detailview.js">
		</script>
		<script type="text/javascript">
var pusMessageType= <%=request.getAttribute("pusMessageType")%>;
$(function() {
	//信息预览对话框
	$('#dlg-phoneShow').dialog({
		title : '信息预览',
		width : 400,
		height : 526,
		closed : true,
		cache : false,
		modal : true
	});

	$('#dg').datagrid({
		title : '已推送公共短信息查询',
		height : $(parent).height() - 85,
		url : 'webapi30203.json',
		method : 'post',
		queryParams : {'method' : 'query'},
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		columns : [[
			{field : 'commsgid',checkbox : true},
			{title : '消息标题',field : 'title',width : 220},
			{title : '消息类型',field : 'pusMessageType',width : 80,formatter : function(value) {
					$(pusMessageType).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '消息内容',field : 'detail',width : 480},
			{title : '客服电话',field : 'param1',width : 120},
			{title : '是否有图片',field : 'param2',width : 80,formatter : function(value) {
					return value == null || value == "" ? "否": "是";
				}
			} 
		]],
		onClickRow:function(rowIndex, rowData){
			$('#phoneNo').text('客服电话：'+rowData.param1);
			var title = rowData.title; 
			$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : title);
			var detail = rowData.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
			$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
			$('#dlg-phoneShow').show().dialog('open');
			ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
		},
		onLoadSuccess: function (){
			$('.datagrid-cell').attr('title','点击行查看详细信息');
		}
	});
	$('#dlg-buttons,#d_dlg2,#d_dlg').hide();
});

</script>
	</head>
	<body>
		<table id="dg"></table>
		<div id="dlg-phoneShow">
			<div id="pinfoShow">
				<div class="phonebg">
					<div class="screenbg">
						<h1 class="item-title">
							短消息
						</h1>
						<div id="content" class="item-content">
							<div id="phoneNo"></div>
							<div>
								<h2 id="infoTitle"></h2>
							</div>
							<div>
								<h3 id="infoDetail"></h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>