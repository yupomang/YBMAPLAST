<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
	label{
		width:220px;
		display:block;
	}
</style>
<script type="text/javascript">
$(function() {
	$('#page20106').window('close');
	
	$('#orderNo').numberbox({
		min:0
	});
	
	$('#s20106iconId').combogrid({
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
			var url = $('#s20106iconId').combogrid("getText");
			$('#s20106iconIdImg').attr( "src", url );
			$('#s20106iconIdImg').show();
		}
	});
	
	$("img").error(function(){
	  $(this).hide();
	});
})
function save20106() {
	var arr = $('#form20106').serializeArray();
	if (!$('#form20106').form( "validate" ,function(v){})) {
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20106.json",
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			if (data.recode==SUCCESS_CODE) {
				$('#page20106').window('close');
				$('#tab20104').treegrid('reload');
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function cancel20106() {
	$('#page20106').window('close');
}

function loadForm20106( row, parentId ) {
	row.consultSubItemId = row.id;
	row.consultItemId = parentId;
	row.subitemName = row.name;
	$('#form20106').form('load',row);
	var url = $('#s20106iconId').combogrid("getText");
	$('#s20106iconIdImg').attr( "src", url );
	$('#s20106iconIdImg').show();
}
</script>

<div id="page20106" class="easyui-window" modal="true" title="修改业务咨询子项" iconCls="icon-save" style="width:450px;height:350px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<form id="form20106">
				<div>
		            <label for="consultSubItemId">咨询信息子项ID:</label>
		            <input id="s20106CconsultSubItemId" name="consultSubItemId" readonly="readonly" >
		        </div>
				<div>
		            <label for="consultItemId">咨询信息项目ID:</label>
		            <input id="s20106ConsultItemId" name="consultItemId" disabled="disabled" >
		        </div>
		        <div>
		            <label for="orderNo">序号:</label>
		            <input id="orderNo" name="orderNo" required="true" class="easyui-validatebox">
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
		            <select id="s20106iconId" name="iconId"></select>
		        </div>
		        <div>
		            <img id="s20106iconIdImg" src="">
		        </div>
			</form>
		</div>
		<div region="south" border="false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save20106()">确认</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel20106()">取消</a>
		</div>
	</div>
</div>
