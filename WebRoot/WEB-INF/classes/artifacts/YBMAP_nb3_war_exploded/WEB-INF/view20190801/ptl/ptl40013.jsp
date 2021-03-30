<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<% 
String versionnolistInfo = request.getAttribute("versionnolist").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>APP业务日志管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body, html {width: 100%;height: 100%;overflow: hidden;margin:0;}
em {color:red;}
#detailInfo {width:1000px;height:108px;overflow-y: auto; overflow-x:hidden; border: 1px solid #dddddd;}
#detailInfo li {padding:1px 4px;}
#detailInfo label {font: 12px 微软雅黑,宋体;font-weight: bold;
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript" src="<%= _contexPath %>/scripts/datagrid-detailview.js"></script>
<script type="text/javascript">//<![CDATA[
$(function () { 
    //datagrid列表
    $('#dg').datagrid({
        iconCls: 'icon-edit',
        height:364,
        title:'APP业务日志查询结果',
        toolbar:'#toolbar',
        pagination:true,
        rownumbers:true,
        fitColumns:true,
        singleSelect:true,
        view: detailview,
		columns:[[
			//{title:'删除',field:'id',checkbox:true},
			{field:'id',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" name="seqno" value="' + row.seqno + '"/>';
			}},
			{title:'序号',field:'seqno',align:'center',width:20},
			{title:'中心ID/名称',field:'centerid',width:60,align:'center',formatter:function(value,row,index) {	
				 return $('#centerid option[value='+value+']').text();
			}},
			{title:'用户名',field:'userid',align:'center',width:30},
			{title:'业务日期',field:'transdate',align:'center',width:30},
			{title:'业务名称',field:'transname',align:'center',width:50},
			{title:'版本号',field:'versionno',align:'center',width:20},
			{title:'设备区分',field:'devtype',align:'center',width:30,formatter:function(value,row,index) {	
				  return $('#devtype option[value='+value+']').text();
			}},
			{title:'设备标识',field:'devid',align:'center',width:30},
			{title:'业务请求时间',field:'requesttime',align:'center',width:50},
			{title:'业务响应时间',field:'responsetime',align:'center',width:50},
			{title:'处理时间',field:'secondsafter',align:'center',width:30},
			{title:'处理状态',field:'freeuse1',align:'center',width:20,formatter:function(value,row,index) {	
				return $('#freeuse1 option[value='+value+']').text();
			}}
		]],
        detailFormatter:function(index,row){
            return '<div class="ddv" id="ddv-' + index + '" style="margin:5px 0"></div>';
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
            ddv.panel({
                height:110,
                border:false,
                cache:false,
                href:'ptl40013Detail.html?seqno='+row.seqno,
                onLoad:function(){
                    $('#dg').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#dg').datagrid('fixDetailRowHeight',index);
        }
    });
    
    //批量导出提交
    $('#download_submit_form').click(function () {
        $('#downloadform').form('submit',{  
            url: 'mi107ToExcel.do',
            onSubmit: function(){
                var fileName=$('#fileName').val();
                if(null == fileName || "" == fileName){
                    $.messager.alert('提示', "请填写生成文件名称后进行提交", 'error');
                    return false;
                }
            },
            success: function(data){
                    $('#dlgBatchDownload').dialog('close');
            },
            error :function(){
                $.messager.alert('错误','系统异常','error');                            
            }
        });
    });
    
    //级联选择
    $('#centerid').change(function (){

        //var info = {"00000001":[{"01":"001"},{"02":"002"}],"11":[]};
        var info = <%=versionnolistInfo%>;
        var versionInfo = info[this.value];
        $('#versionno').children().remove();
        $('#versionno').append('<option value="">请选择...</option>');
        $.each(versionInfo,function (index,ele){
            $.each(ele,function(key,value){
                $('#versionno').append('<option value="'+key+'">'+value+'</option>');
            });
        });
    });
    
    // MI中心前置使用
    //datagrid列表
    $('#midg').datagrid({
        iconCls: 'icon-edit',
        height:364,
        title:'MI中心前置业务日志查询结果',
        toolbar:'#mitoolbar',
        pagination:true,
        rownumbers:true,
        fitColumns:true,
        singleSelect:true,
		columns:[[
			//{title:'删除',field:'miid',checkbox:true},
			{field:'miid',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" name="miseqno" value="' + row.miseqno + '"/>';
			}},
			{title:'序号',field:'miseqno',align:'center',width:20},
			{title:'中心ID/名称',field:'micenterid',width:60,align:'center',formatter:function(value,row,index) {	
				return $('#micenterid option[value='+value+']').text();
			}},
			{title:'用户名',field:'miuserid',align:'center',width:30},
			{title:'业务日期',field:'mitransdate',align:'center',width:30},
			{title:'业务名称',field:'mitransname',align:'center',width:50},
			{title:'版本号',field:'miversionno',align:'center',width:20},
			{title:'设备标识',field:'midevid',align:'center',width:30},
			{title:'业务请求时间',field:'mirequesttime',align:'center',width:50},
			{title:'业务响应时间',field:'miresponsetime',align:'center',width:50},
			{title:'处理时间',field:'misecondsafter',align:'center',width:30},
			{title:'处理状态',field:'mifreeuse1',align:'center',width:20,formatter:function(value,row,index) {	
				return $('#mifreeuse1 option[value='+value+']').text();
			}}
		]],
        view: detailview,
        detailFormatter:function(index,row){
            return '<div class="ddv" id="ddv-' + index + '" style="padding:5px 0"></div>';
        },
        onExpandRow: function(index,row){
            var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
            ddv.panel({
                height:110,
                border:false,
                cache:false,
                href:'ptl4001301Detail.html?miseqno='+row.miseqno,
                onLoad:function(){
                    $('#midg').datagrid('fixDetailRowHeight',index);
                }
            });
            $('#midg').datagrid('fixDetailRowHeight',index);
        }
    });
    
    //批量导出提交
    $('#midownload_submit_form').click(function () {
        $('#midownloadform').form('submit',{  
            url: 'mi107ToExcel.do',
            onSubmit: function(){
                var fileName=$('#mifileName').val();
                if(null == fileName || "" == fileName){
                    $.messager.alert('提示', "请填写生成文件名称后进行提交", 'error');
                    return false;
                }
            },
            success: function(data){
                    $('#midlgBatchDownload').dialog('close');
            },
            error :function(){
                $.messager.alert('错误','系统异常','error');                            
            }
        });
    });
    
});

var url;
function delInfo(){
    //var rows = $('#dg').datagrid('getSelections');
    //if (rows.length > 0){
	var $checkboxs = $('input[name=seqno]:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if(r){
                //var para = rows[0].seqno;
                // for(var i=1;i<rows.length;i++){
                //     para = para + "," + rows[i].seqno;
                // }
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
                var map={seqno:para}
                
                $.ajax( {
                    type : 'POST',
                    url : '<%= _contexPath %>/ptl40013Del.json',
                    dataType: 'json',
                    data:map,
                    success : function(data) {
                        if(SUCCESS_CODE == data.recode) {
                            $.messager.alert('提示', data.msg, 'info');                            
                            $('#dg').datagrid('reload');
                        }else{
                            $.messager.alert('错误', data.msg, 'error');    
                        }
                    },
                    error :function(){
                        $.messager.alert('错误','系统异常','error');                            
                    }
                });
             }
        });
    }else{
        $.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
    }
}
function queryInfo() {
    // 表单内容校验
    if($('#centerid').val() == null || $('#centerid').val() == ""){
        $.messager.alert('提示', "请选择中心名称!", 'info' );
        return;
    }
    var transtypeVal = $('#transtype').combobox('getValue');
    if(transtypeVal == "0"){
        $.messager.alert('提示', "请选择业务类型!", 'info' );
        return;
    }
    if($('#versionno').val() == null || $('#versionno').val() == ""){
        $.messager.alert('提示', "请选择软件版本!", 'info' );
        return;
    }
    if($('#devtype').val() == null || $('#devtype').val() == ""){
        $.messager.alert('提示', "请选择设备区分!", 'info' );
        return;
    }
	
	//校验
	//if (!ydl.formValidate('form40013')) return;
	if (!$("#form40013").form("validate")) return;
    
    var arr = $('#form40013').serializeArray();
    var dataJson = convertToJson(arr);
    //datagrid列表
    $('#dg').datagrid({
        iconCls: 'icon-edit',
        //singleSelect: false,
        url: '<%= _contexPath %>/ptl40013PageQry.json',
        method:'post',
        queryParams:dataJson,
        onLoadSuccess:function(data){           
           if(data.recode!=SUCCESS_CODE)
              $.messager.alert('提示', data.msg, 'info' );
        }
    });
}
function reset(){
    document.getElementById("form40013").reset(); 
    $('#transtype').combobox('setValue', '0');
}
function batchDownload(){
    $('#dlgBatchDownload').dialog('open').dialog('setTitle','APP业务日志-批量导出');
}


//MI中心前置使用
var miurl;
function midelInfo(){
    //var rows = $('#midg').datagrid('getSelections');
    //if (rows.length > 0){
	var $checkboxs = $('input[name=miseqno]:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if(r){
                //var para = rows[0].miseqno;
                // for(var i=1;i<rows.length;i++){
                //     para = para + "," + rows[i].miseqno;
                // }
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
                var map={miseqno:para}
                
                $.ajax( {
                    type : 'POST',
                    url : '<%= _contexPath %>/ptl4001301Del.json',
                    dataType: 'json',
                    data:map,
                    success : function(data) {
                        if(SUCCESS_CODE == data.recode) {
                            $.messager.alert('提示', data.msg, 'info');                            
                            $('#midg').datagrid('reload');
                        }else{
                            $.messager.alert('错误', data.msg, 'error');    
                        }
                    },
                    error :function(){
                        $.messager.alert('错误','系统异常','error');                            
                    }
                });
             }
        });
    }
	else $.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
}
function miqueryInfo() {
    // 表单内容校验
    if($('#micenterid').val() == null || $('#micenterid').val() == ""){
        $.messager.alert('提示', "请选择中心名称!", 'info' );
        return;
    }
    var transtypeVal = $('#mitranstype').combobox('getValue');
    if(transtypeVal == "0"){
        $.messager.alert('提示', "请选择业务类型!", 'info' );
        return;
    }
	
	//校验
	//if (!ydl.formValidate('form4001301')) return;
	if (!$("#form4001301").form("validate")) return;
    
    var arr = $('#form4001301').serializeArray();
    var dataJson = convertToJson(arr);
    //datagrid列表
    $('#midg').datagrid({
        iconCls: 'icon-edit',
        //singleSelect: false,
        url: '<%= _contexPath %>/ptl4001301PageQry.json',
        method:'post',
        queryParams:dataJson,
        onLoadSuccess:function(data){           
           if(data.recode!=SUCCESS_CODE)
              $.messager.alert('提示', data.msg, 'info' );
        }
    });
}
function mireset(){
    document.getElementById("form4001301").reset(); 
    $('#mitranstype').combobox('setValue', '0');
}
function mibatchDownload(){
    $('#midlgBatchDownload').dialog('open').dialog('setTitle','MI中心前置业务日志-批量导出');
}

function midevtypeformater(value,row,index){
     return $('#midevtype option[value='+value+']').text();
}



function convertToJson(formValues) {
    var result = {};
    for(var formValue,j=0;j<formValues.length;j++) {
    formValue = formValues[j];
    var name = formValue.name;
    var value = formValue.value;
    if (name.indexOf('.') < 0) {
    result[name] = value;
    continue;
    } else {
    var simpleNames = name.split('.');
    // 构建命名空间
    var obj = result;
    for ( var i = 0; i < simpleNames.length - 1; i++) {
    var simpleName = simpleNames[i];
    if (simpleName.indexOf('[') < 0) {
    if (obj[simpleName] == null) {
    obj[simpleName] = {};
    }
    obj = obj[simpleName];
    } else { // 数组
    // 分隔
    var arrNames = simpleName.split('[');
    var arrName = arrNames[0];
    var arrIndex = parseInt(arrNames[1]);
    if (obj[arrName] == null) {
    obj[arrName] = []; // new Array();
    }
    obj = obj[arrName];
    multiChooseArray = result[arrName];
    if (obj[arrIndex] == null) {
    obj[arrIndex] = {}; // new Object();
    }
    obj = obj[arrIndex];
    }
    }
 
    if(obj[simpleNames[simpleNames.length - 1]] ) {
    var temp = obj[simpleNames[simpleNames.length - 1]];
    obj[simpleNames[simpleNames.length - 1]] = temp;
    }else {
    obj[simpleNames[simpleNames.length - 1]] = value;
    }
 
    }
    }
    return result;
}
//]]></script>
</head>
<body>
<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
    <div title="APP业务日志信息" style="padding:10px">
        <form id="form40013" method="post" novalidate="novalidate">
            <table class="container">
				<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
                <tr>
                    <th><label for="centerid">中心名称：</label></th>
                    <td>
                        <select id="centerid" name="centerid" required="true">
                            <option value="">请选择...</option>
                            <%
                                String options = "";
                                   List<Mi001> ary=(List<Mi001>) request.getAttribute("centerlist");
                                   for(int i=0;i<ary.size();i++){
                                      Mi001 mi001=ary.get(i);
                                      String itemid=mi001.getCenterid();
                                      String itemval=mi001.getCentername();
                                      options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(options);
                              %>
                        </select><em>*</em>
                    </td>
                    <th><label for="userid">用户ID：</label></th>
                    <td>
                        <input type="text" id = "userid" name="userid"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="transtype">业务类型：</label></th>
                    <td>
                        <select id="transtype" name="transtype" class="easyui-combobox" required="true">
                            <option value="0">请选择...</option>
                            <%
                                String transtypeoptions = "";
                                   JSONArray transtypeAryAdd=(JSONArray) request.getAttribute("consulttypelist");
                                   for(int i=0;i<transtypeAryAdd.size();i++){
                                      JSONObject obj=transtypeAryAdd.getJSONObject(i);
                                      String itemid=obj.getString("itemid");
                                      String itemval=obj.getString("itemval");
                                      transtypeoptions = (new StringBuilder(String.valueOf(transtypeoptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(transtypeoptions);
                              %>
                        </select><em>*</em>
                    </td>
                    <th><label for="versionno">软件版本：</label></th>
                    <td>
                        <select id="versionno" name="versionno" required="true">
                        </select><em>*</em>
                    </td>
                </tr>
                <tr>
                    <th><label for="freeuse1">处理状态：</label></th>
                    <td >
                        <select id="freeuse1" name="freeuse1">
                            <option value="">请选择...</option>
                            <option value="0" >正常完毕</option>
                            <option value="1" >错误异常</option>
                            <option value="2" >无应答</option>
                        </select>
                    </td>
                    <th><label for="transdate">业务日期：</label></th>
                    <td>
                        <input type="text" name="transdate" class="easyui-datebox" >
                    </td>
                </tr>
                <tr>
                    <th><label for="devtype">设备区分：</label></th>
                    <td>
                        <select id="devtype" name="devtype" required="true">
                            <option value="">请选择...</option>
                            <%
                                String devtypeoptions = "";
                                   JSONArray devtypeAryAdd=(JSONArray) request.getAttribute("devicetypelist");
                                   for(int i=0;i<devtypeAryAdd.size();i++){
                                      JSONObject obj=devtypeAryAdd.getJSONObject(i);
                                      String itemid=obj.getString("itemid");
                                      String itemval=obj.getString("itemval");
                                      devtypeoptions = (new StringBuilder(String.valueOf(devtypeoptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(devtypeoptions);
                              %>
                        </select><em>*</em>
                    </td>
                    <th><label for="devid">设备标识：</label></th>
                    <td>
                        <input type="text" name="devid" id="devid">
                    </td>
                </tr>
            </table>
            <div class="buttons">
                <a href="javascript:void(0)" iconCls="icon-search" class="easyui-linkbutton" onclick="queryInfo()">查询</a>
                <a href="javascript:void(0)" iconCls="icon-clear" class="easyui-linkbutton" onclick="reset()">重置</a>
            </div>
        </form>
		<!-- APP业务日志查询结果 -->
		<table id="dg" ></table>
        <div id="toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除</a>
            <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="batchDownload()">批量导出</a>  -->
        </div>
    
        <div id="dlgBatchDownload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                closed="true" buttons="#dlg-buttons">
            <div class="ftitle">APP业务日志批量导出</div>
            <form id = "downloadform" method="post" novalidate="novalidate">
                <div class="fitem">
                    <label>生成文件名称</label>
                    <!-- <input type="hidden" name="forwardpath" value="ptl/ptl40008"/> -->
                    <input type="hidden" name="titles" value="seqno,centerid,userid,channeltype,transdate,transtype,transname,versionno,devtype,devid,requesttime,responsetime,secondsafter,validflag,freeuse1,freeuse2,freeuse3,freeuse4"/>
                    <input type="hidden" name="expotrTableName" value="mi107"/>
                    <input type="text" id = "fileName" name="fileName" class="easyui-validatebox" required="true"/><span> (.xls结尾)</span>
                </div>
            </form>
            <div id="dlg-buttons">
                <a id="download_submit_form" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >导出</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBatchDownload').dialog('close')">取消</a>
            </div>
        </div>
    </div>
    
    <div title="MI中心前置业务日志信息" style="padding:10px">
        <form id="form4001301" method="post" novalidate="novalidate">
            <table class="container">
				<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
                <tr>
                    <th><label for="micenterid">中心名称：</label></th>
                    <td>
                        <select id="micenterid" name="micenterid" required="true">
                            <option value="">请选择...</option>
                            <%
                                String mioptions = "";
                                   List<Mi001> miary=(List<Mi001>) request.getAttribute("centerlist");
                                   for(int i=0;i<miary.size();i++){
                                      Mi001 mi001=miary.get(i);
                                      String itemid=mi001.getCenterid();
                                      String itemval=mi001.getCentername();
                                      mioptions = (new StringBuilder(String.valueOf(mioptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(mioptions);
                              %>
                        </select><em>*</em>
                    </td>
                    <th><label for="miuserid">用户ID：</label></th>
                    <td>
                        <input type="text" id = "miuserid" name="miuserid"/>
                    </td>
                </tr>
                <tr>
                    <th><label for="mitranstype">业务类型：</label></th>
                    <td>
                        <select id="mitranstype" name="mitranstype" class="easyui-combobox" required="true">
                            <option value="0">请选择...</option>
                            <%
                                String mitranstypeoptions = "";
                                   JSONArray mitranstypeAryAdd=(JSONArray) request.getAttribute("miconsulttypelist");
                                   for(int i=0;i<mitranstypeAryAdd.size();i++){
                                      JSONObject obj=mitranstypeAryAdd.getJSONObject(i);
                                      String itemid=obj.getString("itemid");
                                      String itemval=obj.getString("itemval");
                                      mitranstypeoptions = (new StringBuilder(String.valueOf(mitranstypeoptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
                                   }
                                   out.println(mitranstypeoptions);
                              %>
                        </select><em>*</em>
                    </td>
                    <th><label for="midevid">设备标识：</label></th>
                    <td>
                        <input type="text" name="midevid" id="midevid">
                    </td>
                </tr>
                <tr>
                    <th><label for="mifreeuse1">处理状态：</label></th>
                    <td >
                        <select id="mifreeuse1" name="mifreeuse1">
                            <option value="">请选择...</option>
                            <option value="0" >正常完毕</option>
                            <option value="1" >错误异常</option>
                            <option value="2" >无应答</option>
                        </select>
                    </td>
                    <th><label for="mitransdate">业务日期：</label></th>
                    <td>
                        <input type="text" name="mitransdate" class="easyui-datebox" >
                    </td>
                </tr>
            </table>
            <div class="buttons">
                <a href="javascript:void(0)" iconCls="icon-search" class="easyui-linkbutton" onclick="miqueryInfo()">查询</a>
                <a href="javascript:void(0)" iconCls="icon-clear" class="easyui-linkbutton" onclick="mireset()">重置</a>
            </div>
        </form>
		<!-- MI中心前置业务日志查询结果 -->
		<table id="midg" ></table>
        <div id="mitoolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="midelInfo()">删除</a>
            <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="mibatchDownload()">批量导出</a>  -->
        </div>
    
        <div id="midlgBatchDownload" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
            <div class="ftitle">MI中心前置业务日志批量导出</div>
            <form id = "midownloadform" method="post" novalidate="novalidate">
                <div class="fitem">
                    <label>生成文件名称</label>
                    <!-- <input type="hidden" name="miforwardpath" value="ptl/ptl40008"/> -->
                    <input type="hidden" name="mititles" value="seqno,centerid,userid,channeltype,transdate,transtype,transname,versionno,devtype,devid,requesttime,responsetime,secondsafter,validflag,freeuse1,freeuse2,freeuse3,freeuse4"/>
                    <input type="hidden" name="miexpotrTableName" value="mi107"/>
                    <input type="text" id = "mifileName" name="mifileName" class="easyui-validatebox" required="true"/><span> (.xls结尾)</span>
                </div>
            </form>
            <div id="midlg-buttons">
                <a id="midownload_submit_form" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >导出</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#midlgBatchDownload').dialog('close')">取消</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
