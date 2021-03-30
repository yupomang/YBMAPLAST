<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>已发送图文信息</title>
</head>
<body>
<script type="text/javascript">
$(function() {
	var seqid = <%=request.getAttribute("msgid")%>;
	//var timestamp = Date.parse(new Date()); 
	
	//信息预览对话框
	/*
	$('#dlg-phoneShows').dialog({
		title : '信息预览',
		width : 400,
		height : 526,
		closed : true,
		cache : false,
		modal : true
	});
	*/
	alert(1);
	
	//列表
	$('#dgtw').datagrid({
		//title : '已推送短图文信息明细',
		height : 425,
		width : 930,
		url : 'webapi3020402.json',
		method : 'post',
		queryParams : {
			'method' : 'query',
			'seqid' : seqid
		},
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		columns : [[			
			{title : '消息标题',field : 'mstitle',width : 330},
			{title : '消息类型',hidden:true,field : 'mspusMessageType',width : 180,formatter : function(value) {
					$(pusMessageType).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '消息内容',hidden:true,field : 'msdetail',width : 470},
			{title : '客服电话',hidden:true,field : 'msparam1',width : 120},
			{title : '图片',field : 'msparam2',width : 25},
			{title : '类型',hidden:true,field : 'mstemesstype',width : 20},
			{title : '内容',hidden:true,field : 'mstsmess',width : 20},
			{title : 'ID',hidden:true,field : 'msmscommsgid',width : 20},
		]],
		onClickRow:function(rowIndex, rowData){	
			/*
			$('#phonetel').text('客服电话：'+rowData.msparam1);
			var title = rowData.msdetail; 
			$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : '消息标题：'+title);
			//var detail = rowData.tsmsg.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
			var detail = rowData.mstsmess;
			
			$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
			$('#dlg-phoneShow').show().dialog('open');
			ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
			*/
		},
		onLoadSuccess: function (){
			$('.datagrid-cell').attr('title','点击行查看详细信息');
		}
	});
	alert(1);

});
</script>
1111111111111
<table id="dgtw"></table>

<!-- 
<div id="dlg-phoneShow">
	<div id="pinfoShow" >
		<div class="phonebg">
			<div class="screenbg">
				<h1 class="item-title">短消息</h1>
				<div id="content" class="item-content">
					<div id="phoneNo"><h2 id="phonetel"></h2></div>
					<div id="phoneNo"><h2 id="infoTitle"></h2></div>
					<div><h3 id="infoDetail"></h3></div>
				</div>
			</div>
		</div>
	</div>
</div>
 -->


</body>
</html>