<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>报盘短信息明细</title>
</head>
<body>
<script type="text/javascript">
var seqid= <%=request.getAttribute("seqid")%>;
var transtype = '0'+<%=(String)request.getAttribute("transtype")%>;
var themeType= <%=request.getAttribute("themeType")%>;
var msgid;
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
	
	//列表
	$('#dg'+seqid).datagrid({
		title : '已推送短信息明细',
		height : 350,
		width : 1050,
		url : 'webapi3020401.json',
		method : 'post',
		queryParams : {
			'method' : 'query',
			'seqid' : seqid,
			'transtype' : transtype
		},
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		columns : [[
			{title : '用户名',field : 'userid',width : 250},
			{title : '消息标题',field : 'title',width : 230},
			{title : '消息类型',field : 'pusMessageType',width : 150,formatter : function(value) {
					$(pusMessageType).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '主题类型',field : 'theme',width : 120,formatter : function(value) {
					$(themeType).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '消息内容',hidden:true,field : 'detail',width : 470},
			{title : '客服电话',field : 'param1',width : 120},
			{title : '图片',hidden:true,field : 'param2',width : 120},
			{title : '类型',hidden:true,field : 'tsmsgtype',width : 20},
			{title : '内容',hidden:true,field : 'tsmsg',width : 20},
			{title : 'ID',hidden:true,field : 'commsgid',width : 20},
		]],
		onClickRow:function(rowIndex, rowData){		
			
			
			if(rowData.tsmsgtype=='01'||rowData.tsmsgtype=='02'){
				$('#phonetel').text('客服电话：'+rowData.param1);
				var title = rowData.title; 
				$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : '消息标题：'+title);
				//var detail = rowData.tsmsg.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
				var detail = rowData.tsmsg;
				
				$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
				$('#dlg-phoneShow').show().dialog('open');
				ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
			}else if(rowData.tsmsgtype=='03'){
				
				seeMi404(rowData.commsgid);
				/*
				$('#phoneNo').text('客服电话：'+rowData.param1);
				var title = rowData.title; 
				$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : title);
				var detail = rowData.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
				$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
				$('#dlg-phoneShow').show().dialog('open');
				ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
				*/
			}else{
				alert("预览信息有误");
			}
		
			
		},
		onLoadSuccess: function (){
			$('.datagrid-cell').attr('title','点击行查看详细信息');
		}
	});
});


function seeMi404(id){
	
	var href = "page3020402.html?msgid="+id;
	var title = "己发送图文信息";	
	$('#d_dlg').dialog('open').dialog('setTitle','已推送图文信息');
	
	//列表
	$('#dgtw').datagrid({
		//title : '已推送短图文信息明细',
		height : 425,
		width : 930,
		url : 'webapi3020402.json',
		method : 'post',
		queryParams : {
			'method' : 'query',
			'seqid' : id
		},
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		columns : [[			
			{title : '消息标题',field : 'mstitle',width : 880},
			{title : '消息类型',hidden:true,field : 'mspusMessageType',formatter : function(value) {
					$(pusMessageType).each(function() {
						if( this.itemid == value ) {
							value = this.itemval;
							return false;
						}
					});
					return value;
				}
			},
			{title : '消息内容',hidden:true,field : 'msdetail'},
			{title : '客服电话',hidden:true,field : 'msparam1'},
			{title : '图片',field : 'msparam2',width : 60},
			{title : '类型',hidden:true,field : 'mstemesstype'},
			{title : '内容',hidden:true,field : 'mstsmess'},
			{title : 'ID',hidden:true,field : 'msmscommsgid'},
		]],
		onClickRow:function(rowIndex, rowData){	
			
			$('#phonetel').text('客服电话：'+rowData.msparam1);
			var title = rowData.msdetail; 
			$('#infoTitle').text(title == '' ? '< 预览消息标题处 >' : '消息标题：'+title);
			//var detail = rowData.tsmsg.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
			var detail = rowData.mstsmess;
			
			$('#infoDetail').html(detail == '' ? '< 预览消息内容处 >': detail);
			$('#dlg-phoneShow').show().dialog('open');
			ydl.setDlgPosition('dlg-phoneShow');//对话框高度超出页面高度时，设置top为0px
			
		},
		onLoadSuccess: function (){
			$('.datagrid-cell').attr('title','点击行查看详细信息');
		}
	});
	
	/*
	$('#d_dlg').show().dialog({
		title : title,
		width : 950,
		height : 500,
		closed : false,
		cache : false,
		href : href,		
		buttons : '#dlg-buttons',
		modal : true
	});
	*/
}

</script>
<table id="dg<%=request.getAttribute("seqid")%>"></table>
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

<div id="d_dlg" class="easyui-dialog" style="width:950px;height:500px;" closed="true" modal="true" buttons="#dlg-buttons">
	<table id="dgtw"></table>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#d_dlg').dialog('close')">取消</a>
</div>

</body>
</html>