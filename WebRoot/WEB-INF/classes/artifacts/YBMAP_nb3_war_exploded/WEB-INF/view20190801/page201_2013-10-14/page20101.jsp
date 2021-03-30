<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	label{
		width:220px;
		display:block;
	}
</style>
<script type="text/javascript">
$(function() {
	$('#page20101').window('close');
	
	$('#consultType').combobox({
	    data:jsonConsultType,
	    valueField:'id',
	    textField:'text',
	    panelHeight:"auto"
	});
	
	$('#orderNo').numberbox({
		min:0
	});
})
function save20101() {
	var arr = $('#form20101').serializeArray();
	if (!$('#form20101').form( "validate" ,function(v){})) {
		alert($('#form20101').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20101.json",
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode==SUCCESS_CODE) {
				$('#page20101').window('close');
				$('#tab20104').treegrid('reload');
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function cancel20101() {
	$('#page20101').window('close');
}

function loadForm20101( row ) {
	$('#form20101').form('load',{
		consultType:row.id
	});
}
</script>

<div id="page20101" class="easyui-window" modal="true" title="添加业务咨询项" iconCls="icon-save" style="width:500px;height:300px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<form id="form20101">
				<div>
		            <label for="consultType">业务分类:</label>
		            <input id="consultType" name="consultType" disabled="disabled" >
		        </div>
		        <div>
		            <label for="orderNo">序号:</label>
		            <input id="orderNo" name="orderNo" required="true" class="easyui-validatebox">
		        </div>
		        <div>
		            <label for="conditionTitle">公共条件标题:</label>
		            <input id="conditionTitle" name="conditionTitle" class="easyui-validatebox" validType="length[0,100]">
		        </div>
		        <div>
		            <label for="itemName">项目名称:</label>
		            <input id="itemName" name="itemName" required="true" class="easyui-validatebox" validType="length[0,20]">
		        </div>
			</form>
		</div>
		<div region="south" border="false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save20101()">确认</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel20101()">取消</a>
		</div>
	</div>
</div>
