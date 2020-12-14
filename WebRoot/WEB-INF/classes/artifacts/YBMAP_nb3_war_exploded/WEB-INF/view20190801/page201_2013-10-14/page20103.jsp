<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	label{
		width:220px;
		display:block;
	}
</style>
<script type="text/javascript">
$(function() {
	$('#page20103').window('close');
	
	$('#i20103ConsultType').combobox({
	    data:jsonConsultType,
	    valueField:'id',
	    textField:'text',
	    panelHeight:"auto"
	});
	
	$('#orderNo').numberbox({
		min:0
	});
})
function save20103() {
	var arr = $('#form20103').serializeArray();
	if (!$('#form20103').form( "validate" ,function(v){})) {
		alert($('#form20103').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20103.json",
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode==SUCCESS_CODE) {
				$('#page20103').window('close');
				$('#tab20104').treegrid('reload');
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function cancel20103() {
	$('#page20103').window('close');
}

function loadForm20103( row, parentId ) {
	row.consultItemId = row.id;
	row.itemName = row.name;
	row.consultType = parentId;
	$('#form20103').form('load',row);
}
</script>

<div id="page20103" class="easyui-window" modal="true" title="修改业务咨询项" iconCls="icon-save" style="width:450px;height:300px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<form id="form20103">
				<div>
		            <label for="consultItemId">咨询信息项目ID:</label>
		            <input id="consultItemId" name="consultItemId" readonly="readonly" >
		        </div>
				<div>
		            <label for="consultType">业务分类:</label>
		            <input id="i20103ConsultType" name="consultType" disabled="disabled" >
		        </div>
		        <div>
		            <label for="orderNo">序号:</label>
		            <input id="orderNo" name="orderNo" required="true" class="easyui-validatebox">
		        </div>
		        <div>
		            <label for="conditionTitle">公共条件标题:</label>
		            <input id="conditionTitle" name="conditionTitle" required="true" class="easyui-validatebox" validType="length[0,100]">
		        </div>
		        <div>
		            <label for="itemName">项目名称:</label>
		            <input id="itemName" name="itemName" required="true" class="easyui-validatebox" validType="length[0,20]">
		        </div>
			</form>
		</div>
		<div region="south" border="false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save20103()">确认</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel20103()">取消</a>
		</div>
	</div>
</div>
