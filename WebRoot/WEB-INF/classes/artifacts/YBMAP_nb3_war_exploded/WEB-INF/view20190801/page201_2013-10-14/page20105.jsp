<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	label{
		width:220px;
		display:block;
	}
</style>
<script type="text/javascript">
$(function() {
	$('#page20105').window('close');
	
	$('#i20105orderNo').numberbox({
		min:0
	});
	
	$('#iconId').combogrid({
		panelWidth:300,
		value:'006',
		idField:'iconId',
		textField:'url',
		url:'webapi20105.json?method=getIcon',
		columns:[[
			{field:'iconId',title:'图标ID',width:60},
			{field:'url',title:'图标',width:200, formatter:function(value){
				return '<img src="'+value+'">';
			}}
		]],
		onHidePanel:function() {
			var url = $('#iconId').combogrid("getText");
			$('#iconIdImg').attr( "src", url );
			$('#iconIdImg').show();
		}
	});
	
	$("img").error(function(){
	  $(this).hide();
	});
})
function save20105() {
	var arr = $('#form20105').serializeArray();
	if (!$('#form20105').form( "validate" ,function(v){})) {
		alert($('#form20105').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20105.json?method=add",
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode==SUCCESS_CODE) {
				$('#page20105').window('close');
				$('#tab20104').treegrid('reload');
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function cancel20105() {
	$('#page20105').window('close');
}

function loadForm20105( row ) {
	$('#form20105').form('load',{
		consultItemId:row.id,
		conditionTitle:row.conditionTitle
	});
}
</script>

<div id="page20105" class="easyui-window" modal="true" title="添加业务咨询子项" iconCls="icon-save" style="width:450px;height:350px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<form id="form20105">
				<div>
		            <label for="consultItemId">咨询信息项目ID:</label>
		            <input id="consultItemId" name="consultItemId" readonly="readonly" >
		        </div>
		        <div>
		            <label for="orderNo">序号:</label>
		            <input id="i20105orderNo" name="orderNo" required="true" class="easyui-validatebox">
		        </div>
		        <div>
		            <label for="conditionTitle">公共条件标题:</label>
		            <input id="conditionTitle" name="conditionTitle" disabled="disabled">
		        </div>
		        <div>
		            <label for="subitemName">子项名称:</label>
		            <input id="subitemName" name="subitemName" required="true" class="easyui-validatebox" validType="length[0,100]">
		        </div>
		        <div>
		            <label for="iconId">图标:</label>
		            <select id="iconId" name="iconId"></select>
		        </div>
		        <div>
		            <img id="iconIdImg" src="">
		        </div>
			</form>
		</div>
		<div region="south" border="false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save20105()">确认</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel20105()">取消</a>
		</div>
	</div>
</div>
