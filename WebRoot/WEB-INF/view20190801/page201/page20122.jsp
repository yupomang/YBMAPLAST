<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件项目添加/修改</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body,html{height:100%;}
.panel{padding:5px;}
#detail{
	height:265px;
}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
var editIndex = undefined;
$(function () {
	//初始化
	var result = <%=request.getAttribute("result") %>;	//返回结果
	//parent.parent.$('#running-overlay').hide(); //隐藏等待显示
	ydl.displayRunning(false);
	//初始化 取值赋值
	var field = decodeURIComponent(location.search);
	//var task = field.replace(/.*&task=(.*?)/g, "$1");	//操作方式
	var task = '<%=request.getParameter("task") %>';	//操作方式
	var enter = '<%=request.getParameter("enter") %>';	//是否允许输入回车
	
	if(task == 'edit'){
		var detail = field.replace(/.*&detail=(.*)&task=edit/g, "$1");
		$('#detail').val(detail.replace(/<br>/g, '\n').replace(/&nbsp;/g,' ').replace(/　　　　/g,'\t'));
	}
	
	//当是业务咨询内容标题时，不允许输入回车
	$('#detail').keydown(function (e){
		if (enter == 'false' && e.keyCode == '13') return false;
	});
	
	//保存--添加/修改
	$('#b_save').click(function (){
		var paras;
		var value = $('#detail').val();
		var maxlength = 1000;
		if (enter == 'false') maxlength = 64;
		//校验内容长度
		if (value.length2() > maxlength || value.length < 1) {
			$.messager.alert('提示','【咨询内容】的长度应在1~'+maxlength+'个字符之间，您输入了'+value.length2()+'个字符，请重新输入！','info');
			return;
		}
		//添加
		if(task == 'add'){
			//var consultItemId = field.replace(/^\?consultItemId=(.*?)&.*/g, "$1");		//业务咨询子项id
			var parentId = field.replace(/.*&parentId=(.*?)&.*/g, "$1");				//上级向导内容id
			var stepId = field.replace(/.*&stepId=(.*?)&.*/g, "$1");					//步骤id
			var orderNo = field.replace(/.*&orderNo=(.*?)&.*/g, "$1");					//顺序号
			//上传参数
			paras = [
				{name:'method',value:'add'},
				{name:'stepId',value:stepId},
				{name:'orderNo',value:orderNo},
				{name:'parentId',value:parentId},
				{name:'detail',value:$('#detail').val()}
			];
			//添加
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/webapi20124.json",
				dataType: "json",
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						parent.$('#conDetail').treegrid('reload');
						parent.$('#subContent').hide();
						parent.$('#detailInfo').hide();
						parent.$('#detail_content')[0].src='about:blank';
					}
					else $.messager.alert('提示',data.msg,'info');
					
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
			
		}
		//修改
		else if (task == 'edit'){
			var consultId = field.replace(/.*&consultId=(.*?)&.*/g, "$1");					
			paras = {method: 'edit',consultId:consultId,detail:$('#detail').val()};	
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/webapi20124.json",
				dataType: "json",
				data:paras,
				success : function(data) {
					//$.messager.alert('提示',data.msg,'info');
					if (data.recode == SUCCESS_CODE) {
						parent.$('#conDetail').treegrid('reload');
						parent.$('#subContent').hide();
						parent.$('#subTitle').html('业务咨询向导内容详情');
						parent.$('#detailInfo').hide();
						parent.$('#detail_content')[0].src='about:blank';
					}
					else $.messager.alert('提示',data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});			
			
		}
	});
	//取消
	$('#b_cancel').click(function (){
		parent.$('#detailInfo').hide();
	});

	$('#detail').width($('#detail').parent().width());
	
	 
});

//]]></script>
</head>

<body>
<div class="easyui-panel" title="咨询内容">	
	<div class="container">
		<textarea id="detail" name="detail" ></textarea>
	</div>
</div>
<div id="dlg-buttons" class="buttons">
    <a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a id="b_cancel"  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
</div>
</body>
</html>
