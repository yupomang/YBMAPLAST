<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>栏目管理</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %> 
<style type="text/css">
/*<![CDATA[*/
#tree-container{width: 300px; height:100%; margin:5px;padding:5px;overflow-y: scroll;}
#form-container {width: 70%;padding:5px;}
/*]]>*/
</style>
<script type="text/javascript">	
function reloadTree(){ 
   var node = $('#code-tree').tree('getSelected'); 
   if(node){
	 updateNode(node);
	 $('#code-tree').tree('reload', node.target); 
   }else{ 
     $('#code-tree').tree('reload'); 
   } 
   $("#codeTab").datagrid("reload");	
 } 
function updateNode(node){ 
   if(node){ 
      $('#code-tree').tree('update', {
      		target: node.target,
      		text: node.text
      }); 
   }else{ 
     $('#code-tree').tree('update'); 
   } 
}
function appendTree(data){ 
	var node = $('#code-tree').tree('getSelected');
	updateNode(node);
	$('#code-tree').tree('append', {
		parent: node.target,
		data: [data]
	});
	$("#codeTab").datagrid("reload");
}
function createTree(){
    $('#code-tree').tree({
        height:$(document).height()-50,
        onBeforeExpand:function(node,param){  
          $('#code-tree').tree('options').url = "page70701Qry.html?pid=" + node.id+"&centerid="+node.attributes.centerid;    
    },
    onClick:function(node){  
       $('#code-tree').tree("expand",node.target); 
    },
    onExpand:function(node){ 
      $('#code-tree').tree("select",node.target); 
    },
    onSelect:function(node){
       var id=node.id; 	 
       var $grid=$("#codeTab");
       $grid.datagrid('options').queryParams={pid:id,centerid:node.attributes.centerid};  
	   $grid.datagrid("reload"); 
	   //treeForm.parentfuncid.value=id; 
	   treeForm.reset();
    }               
  }).tree("loadData",[{"id":"000000000","text":"栏目管理","state":"closed",
  			"attributes":{   
        		"centerid":"000000000"
  }}]);
  //$("#p").height($(document).height()-30); 
  var node=$('#code-tree').tree("find","000000000");
  $('#code-tree').tree("expand",node.target); 
  $('#code-tree').tree("select",node.target); 
  return  $('#code-tree');
}
function createOperTable(){
	$("#codeTab").datagrid({
		width:$('#formInfo').width(),
		height:$(document).height()-196,
		fitColumns: true,
		idField:'dicid',
     	singleSelect:true,
      	url:'page70701Query.json',
      	onClickRow:function(rowIndex, rowData){
           
          for(var key in rowData ){
              if(treeForm[key]){
                 if(rowData[key])
                     $(treeForm[key]).val(rowData[key]);
                 else
                     treeForm[key].value="";
              }   
           } 
           treeForm.olditemid.value=rowData.itemid;
           treeForm.updicid.value=rowData.updicid;
           treeForm.oldstat.value=rowData.status;
           treeForm.oldfreeuse1.value=rowData.freeuse1;
           $("#freeuse1").datebox("setValue",rowData.freeuse1);
           treeForm.task.value='mod';
           $('#formInfo').panel('setTitle','修改栏目信息');
           $("[name='itemid']").attr("readonly",false);
     	   $("[name='itemval']").attr("readonly",false);
     	   
			if(rowData["status"]){
         		$("[name='stat'][value='"+rowData["status"]+"']").attr("checked",true);
      		} 
       },
      columns:[[
        {field:'op',title:'操作',width:100,formatter:function(v,row){
            return "<input type='checkbox' value='"+row["dicid"]+"' name='chk_dicid'>"
          }}, 
        {field:'centername',title:'所属中心',width:200,formatter : function(value){
				var node = $('#code-tree').tree('getSelected'); 
				if (node) {
					if("000000000"==node.id && "000000000"==node.attributes.centerid){
						return  "-";
					}
					return node.attributes.centername;
				}
			}},
        {field:'centerid',title:'中心ID',width:100,hidden:true},
        {field:'updicname',title:'上级编码',width:200,formatter : function(value){
				var node = $('#code-tree').tree('getSelected'); 
				if (node) {
					if("000000000"==node.attributes.centerid){
						return  "-";
					}
					return node.attributes.itemid+'-'+node.attributes.itemval;
				}
			}},
        {field:'updicid',title:'所属上级编码',width:100,hidden:true},
        {field:'dicid',title:'当前字段编码dicid',width:200,hidden:true},
        {field:'itemid',title:'当前编码',width:200},
        {field:'itemval',title:'当前名称',width:200},
 		{field:'status',title:'状态',width:200,formatter:function(v){
 			var node = $('#code-tree').tree('getSelected'); 
 			if(node && "000000000"==node.attributes.centerid){
 				return  "-";
 			}else {
				var json={"1":"开放","0":"关闭"};
        		if(json[v]){
           			return json[v]
        		}else{
           			return v;
        		}
 			}
     	}},
     	{field:'freeuse1',title:'信息过期时间',width:200,formatter:function(value){
 			if(""==value || value == null){
 				return  "-";
 			}else {
				return value;
 			}
     	}},
      ]],
      toolbar:[{	
	   id:'btnadd',
	   text:'新建',
	   iconCls:'icon-add', 
	   handler:function(){		       
	       //treeForm.reset();
	       $('#treeForm').form('clear');
	        var node = $('#code-tree').tree('getSelected'); 
            if(node){
				var centerid = node.attributes.centerid;
				if('000000000' == node.id && '000000000' == centerid) {
					$.messager.alert('错误','请到中心管理菜单进行维护后，再进行此相关的栏目管理！','error');
					return;
				} 
            	treeForm.task.value='add';
            	treeForm.centerid.value=node.attributes.centerid;
            	treeForm.updicid.value=node.attributes.dicid;
            	$("[name='itemid']").attr("readonly",false);
     			$("[name='itemval']").attr("readonly",false);
            	$('#formInfo').panel('setTitle','添加栏目信息');
            	$("[name='stat'][value='1']").attr("checked",true);
            }else{ 
                treeForm.updicid.value=000000000;
            }
	   }
	},{	
	   id:'btndel',
	   text:'删除',
	   iconCls:'icon-remove', 
	   handler:function(){
	       var funcs=[];  
	       var centerid;
	       $("[name='chk_dicid']:checked").each(function(i){
				funcs.push($.trim($(this).val()));
	       });
	       
	       if(funcs.length == 0){
	       		$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
	       }else{
	       		var node = $('#code-tree').tree('getSelected'); 
				if(node){
					centerid = node.attributes.centerid;
				}
	       
	       		var map={dicids:funcs.join(","),centerid:centerid}	      
	       		$.ajax({	 
					type : "POST",
					url : "page70701Del.json",
					dataType: "json",
					data:map,
					success :function(data) {				   
						if(SUCCESS_CODE == data.recode) {
                			$.messager.alert('提示', data.msg, 'info');
				    		reloadTree();
				    		treeForm.reset(); 
                		}else{
                			$.messager.alert('错误',data.msg,'error');		
                		}
				   
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');							
					}
		  		}); 
	       }
	   }
	}]
 });
 return  $('#codeTab');
}
$(function(){ 
	var $grid = createOperTable();
	createTree();
	$grid.datagrid('options').queryParams={pid:'000000000',centerid:'000000000'};
    $grid.datagrid("reload");
    
    $("[name='itemid']").attr("readonly",true);
    $("[name='itemval']").attr("readonly",true);
    
	$("#btnsave").click(function(){
   		var mi007arry = $('#treeForm').serializeArray();
    	var url=null;   
    	if('add' == $('#task').val()){
			url="page70701Add.json";
		}else{
			url="page70701Mod.json";
		}     
		var tgbz=$("#treeForm").form("validate");
		if(tgbz){          
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi007arry,
				success :function(data) {
					if(SUCCESS_CODE == data.recode) {
						$.messager.alert('提示', data.msg, 'info');
						if ('add' == $('#task').val()) {
						appendTree(data.addTreeData);
						}else{
							//updateNode();
							reloadTree()
						}
					}else{
                		$.messager.alert('错误',data.msg,'error');
                	}			  
				},
				error :function(){
						$.messager.alert('错误','系统异常','error');
				}
	     	});
     	} 
	}); 
	
	//设置页面结构高度宽度
	$('#layout').height($(document).height()-30);
	$('#tree-container').height($(document).height()-52).width($('#tree-container').width()-5);
	$('#form-container').height($(document).height()-45);
	//菜单信息表单标题
	$('#formInfo').panel({
		title:'添加栏目信息', 
		height:150,
		width:$('#formInfo').width()
	}).closest('.panel').css('padding-top','5px');
});
</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="栏目管理"> 
	<div id="tree-container" data-options="region:'west',split:false,border:true">
		<div id="code-tree"></div>
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<table id="codeTab"></table>
		<div id="formInfo" class="easyui-panel"> 
			<form method="post" action="" id="treeForm" class="dlg-form" novalidate="novalidate">
				<input type="hidden" id="task" name="task" />
				<input type="hidden" id="centerid" name="centerid" />
				<input type="hidden" id="updicid" name="updicid" />
				<input type="hidden" id="dicid" name="dicid" />
				<input type="hidden" id="olditemid" name="olditemid" /><!-- 修改时使用 -->
				<input type="hidden" id="oldstat" name="oldstat" /><!-- 修改时使用 -->
				<input type="hidden" id="oldfreeuse1" name="oldfreeuse1" /><!-- 修改时使用 -->
				<table class="container">
					<col width="20%" /><col width="30%" /><col width="20%" /><col width="30%" />
	    			<tr>
	    				<th><label>栏目编码：</label></th>
						<td><input type="text" name="itemid" id="itemid" class="easyui-validatebox" required="true"/></td>
						<th><label>栏目名称：</label></th>
						<td><input type="text" name="itemval" id="itemval" class="easyui-validatebox" required="true"/></td>
	    			</tr>
					<tr>
						<th><label >栏目状态：</label></th>
						<td>  	
							<ul class="multivalue horizontal">
								<li><input id="stat-1" name="stat" type="radio" value="1"/><label for="stat-1">开放</label></li>
								<li><input id="stat-0" name="stat" type="radio" value="0"/><label for="stat-0">关闭</label></li>
							</ul>
						</td>
						<th><label>信息过期时间：</label></th>
			        	<td>
			        		<input type="text" id="freeuse1" name="freeuse1" class="easyui-datebox"  validType="date"/>
			        	</td>        
					</tr>
				</table>
				<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
			</form>
		</div>
	</div> 
</div> 
</body>
</html>