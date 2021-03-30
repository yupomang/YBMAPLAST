<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="com.yondervision.mi.dto.Mi601"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ include file="/include/init.jsp"%>
<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>在线留言</title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ui/zxly/zxly.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/jqPagination/css/jqpagination.css" />
<script type="text/javascript" src="<%= _contexPath %>/scripts/jqPagination/js/jquery.jqpagination.min.js"></script>
<script type="text/javascript" src="<%= _contexPath %>/scripts/pagination-self.js"></script>
<script type="text/javascript">
var url;
var result;
var pageSize = 5;

var statusTmp;
var startdateTmp;
var enddateTmp;
$(function(){ 
	initData();
	//查询
	$('#b_query').click(function (){
		$('.pagination').jqPagination('destroy');
		statusTmp = $('#status').combobox('getValue');
		startdateTmp = $("#startdate").datebox("getValue");
		enddateTmp = $("#enddate").datebox("getValue")
		loadData();
	});	
	
	//重置
	$('#b_reset').click(function (){
		$('#form60001').form('clear');
		//设置combobox默认值
		$('#status').combobox('select','');
		ydl.delErrorMessage('form60001');
	});
})
function initData(){
	var arr = $('#form60001').serializeArray();
	//校验
	if (!$("#form60001").form("validate")) return;
	
	statusTmp = $('#status').combobox('getValue');
	startdateTmp = $("#startdate").datebox("getValue");
	enddateTmp = $("#enddate").datebox("getValue")
	
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi60001.json",
		dataType: 'json',
		data:arr,
		success : function(data) {
			$("#listContainer").empty();
			if ("000000" == data.recode){
				$('.pagination').jqPagination({
					current_page: 1, //设置当前页 默认为1
					max_page : data.totalPage, //设置最大页 默认为1
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						getDataByPage(page);
      				}
				});
			
				$.each(data.rows, function(i, item) {
					var emVal;
					if ('0'== item.status){
						emVal = "<em class='tips' style='display:block;'><i>●</i>未回复</em>";
					}else{
						emVal = "<em class='tipsRes' style='display:block;'><i>●</i>已回复</em>";
					}
					var page = 1;
					$("#listContainer").append(
                    	"<li class='message_item' id='"+item.seqno+"'>"+
							"<div class='message_opr'>"+
            					"<a id='b_qryRecord' onClick=javascript:ShowResRecord("+i+","+item.seqno+","+item.status+") href='javascript:void(null)'><img title='查看回复' src='<%=_contexPath%>/ui/zxly/images/history.jpg' width='24' height='24' alt=''/></a>"+
								"<a onClick=javascript:ShowResDialog("+i+") href='javascript:void(null)'><img title='快捷回复' src='<%=_contexPath%>/ui/zxly/images/reply.jpg' width='24' height='24'  alt=''/></a>"+
        					"</div>"+
							"<div class='message_info'>"+
								"<div class='message_status'>"+
									emVal+
								"</div>"+
								"<div  class='message_time' style='margin-left:30px;'>来源："+$('#channel option[value='+item.freeuse6+']').text()+"</div>"+
								"<div class='message_time'>"+item.detaildate+"</div>"+
								"<div class='user_info'>"+
									"<a class='remark_name'>"+item.freeuse2+"</a>"+
									"<a class='avatar' target='_blank' >"+
										"<img src='<%=_contexPath%>/ui/zxly/images/login_icon.jpg'/>"+
									"</a>"+
								"</div>"+
							"</div>"+
							"<div class='message_content text'>"+
								"<div class='wxMsg'>"+item.detail+"</div>"+
							"</div>"+
							"<div id='resRecord_"+i+"' style='display:none;' class='bbb_"+i+"'>"+
							
							"</div>"+
							"<div class='quick_reply_box aaa_"+i+"' style='display:none;' id='quickRes_"+i+"'>"+
								"<label class='frm_label' for=''>"+
									"快速回复:"+
								"</label>"+
								 "<div class='emoion_editor_wrp js_editor'>"+
									"<div class='emotion_editor'>"+
										"<div class='edit_area js_editorArea ccc_"+i+"' id='resMsg_"+i+"' style='display:block; width:800px; height:100px; overflow-y:auto; border:1px solid #ccc;' contenteditable='true'></div>"+
									"</div>"+
								"</div>"+        
								"<p class='quick_reply_box_tool_bar ddd_"+i+"'>"+
									"<span class='btn btn_primary btn_input'>"+
										"<button onClick=javascript:sendRes("+i+","+item.seqno+",'"+item.centerid+"','"+item.devtype+"','"+item.userid+"',"+page+",'"+item.freeuse6+"') >发送</button>"+
                  					"</span>"+
                  					"<a class='btn btn_default pickup' onClick=javascript:CloseResDialog("+i+") href='javascript:void(null)'>收起</a>"+
 								"</p>"+     
							"</div>"+
						"</li>"
                    );
        		});
			}else{
				$.messager.alert('提示',data.msg,'info');
				$('.pagination').jqPagination({
					current_page: 1, //设置当前页 默认为1
					max_page : 1, //设置最大页 默认为1
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						//getDataByPage(page);
      				}
				});
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function loadData(){
	var arr = $('#form60001').serializeArray();
	//校验
	if (!$("#form60001").form("validate")) return;
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi60001.json",
		dataType: 'json',
		data:arr,
		success : function(data) {
			$("#listContainer").empty();
			if ("000000" == data.recode){
				$('.pagination').jqPagination('option', 'current_page', 1);
				$('.pagination').jqPagination('option', 'max_page', data.totalPage);
				$('.pagination').jqPagination({
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						getDataByPage(page);
      				}
				});
			
				$.each(data.rows, function(i, item) {
					var emVal;
					if ('0'== item.status){
						emVal = "<em class='tips' style='display:block;'><i>●</i>未回复</em>";
					}else{
						emVal = "<em class='tipsRes' style='display:block;'><i>●</i>已回复</em>";
					}
					var page = 1;
					$("#listContainer").append(
                    	"<li class='message_item' id='"+item.seqno+"'>"+
							"<div class='message_opr'>"+
            					"<a id='b_qryRecord' onClick=javascript:ShowResRecord("+i+","+item.seqno+","+item.status+") href='javascript:void(null)'><img title='查看回复' src='<%=_contexPath%>/ui/zxly/images/history.jpg' width='24' height='24' alt=''/></a>"+
								"<a onClick=javascript:ShowResDialog("+i+") href='javascript:void(null)'><img title='快捷回复' src='<%=_contexPath%>/ui/zxly/images/reply.jpg' width='24' height='24'  alt=''/></a>"+
        					"</div>"+
							"<div class='message_info'>"+
								"<div class='message_status'>"+
									emVal+
								"</div>"+
								"<div  class='message_time' style='margin-left:30px;'>来源："+$('#channel option[value='+item.freeuse6+']').text()+"</div>"+
								"<div class='message_time'>"+item.detaildate+"</div>"+
								"<div class='user_info'>"+
									"<a class='remark_name'>"+item.freeuse2+"</a>"+
									"<a class='avatar' target='_blank' >"+
										"<img src='<%=_contexPath%>/ui/zxly/images/login_icon.jpg'/>"+
									"</a>"+
								"</div>"+
							"</div>"+
							"<div class='message_content text'>"+
								"<div class='wxMsg'>"+item.detail+"</div>"+
							"</div>"+
							"<div id='resRecord_"+i+"' style='display:none;' class='bbb_"+i+"'>"+
							
							"</div>"+
							"<div class='quick_reply_box aaa_"+i+"' style='display:none;' id='quickRes_"+i+"'>"+
								"<label class='frm_label' for=''>"+
									"快速回复:"+
								"</label>"+
								 "<div class='emoion_editor_wrp js_editor'>"+
									"<div class='emotion_editor'>"+
										"<div class='edit_area js_editorArea ccc_"+i+"' id='resMsg_"+i+"' style='display:block; width:800px; height:100px; overflow-y:auto; border:1px solid #ccc;' contenteditable='true'></div>"+
									"</div>"+
								"</div>"+        
								"<p class='quick_reply_box_tool_bar ddd_"+i+"'>"+
									"<span class='btn btn_primary btn_input'>"+
										"<button onClick=javascript:sendRes("+i+","+item.seqno+",'"+item.centerid+"','"+item.devtype+"','"+item.userid+"',"+page+",'"+item.freeuse6+"') >发送</button>"+
                  					"</span>"+
                  					"<a class='btn btn_default pickup' onClick=javascript:CloseResDialog("+i+") href='javascript:void(null)'>收起</a>"+
 								"</p>"+     
							"</div>"+
						"</li>"
                    );
        		});
			}else{
				$.messager.alert('提示',data.msg,'info');
				$('.pagination').jqPagination('option', 'current_page', 1);
				$('.pagination').jqPagination('option', 'max_page', 1);
				$('.pagination').jqPagination({
					page_string : '{current_page} / {max_page}',
					paged : function(page) {
						//getDataByPage(page);
      				}
				});
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function getDataByPage(page){
    //var arr = $('#form60001').serializeArray();
	//校验
	//if (!$("#form60001").form("validate")) return;
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi60001.json?page="+page,
		dataType: 'json',
		//data:arr,
		data:{status:statusTmp,startdate:startdateTmp,enddate:enddateTmp},
		success : function(data) {
			$("#listContainer").empty();
			$.each(data.rows, function(i, item) {
				var emVal;
				if ('0'== item.status){
					emVal = "<em class='tips' style='display:block;'><i>●</i>未回复</em>";
				}else{
					emVal = "<em class='tips' style='display:none;'>已回复</em>";
				}
				
				$("#listContainer").append(
                    	"<li class='message_item' id='"+item.seqno+"'>"+
							"<div class='message_opr'>"+
            					"<a id='b_qryRecord' onClick=javascript:ShowResRecord("+i+","+item.seqno+","+item.status+") href='javascript:void(null)'><img title='查看回复' src='<%=_contexPath%>/ui/zxly/images/history.jpg' width='24' height='24' alt=''/></a>"+
								"<a onClick=javascript:ShowResDialog("+i+") href='javascript:void(null)'><img title='快捷回复' src='<%=_contexPath%>/ui/zxly/images/reply.jpg' width='24' height='24'  alt=''/></a>"+
        					"</div>"+
							"<div class='message_info'>"+
								"<div class='message_status'>"+
									emVal+
								"</div>"+
								"<div  class='message_time' style='margin-left:30px;'>来源："+$('#channel option[value='+item.freeuse6+']').text()+"</div>"+
								"<div class='message_time'>"+item.detaildate+"</div>"+
								"<div class='user_info'>"+
									"<a class='remark_name'>"+item.freeuse2+"</a>"+
									"<a class='avatar' target='_blank' >"+
										"<img src='<%=_contexPath%>/ui/zxly/images/login_icon.jpg'/>"+
									"</a>"+
								"</div>"+
							"</div>"+
							"<div class='message_content text'>"+
								"<div class='wxMsg'>"+item.detail+"</div>"+
							"</div>"+
							"<div id='resRecord_"+i+"' style='display:none;' class='bbb_"+i+"'>"+
							
							"</div>"+
							"<div class='quick_reply_box aaa_"+i+"' style='display:none;' id='quickRes_"+i+"'>"+
								"<label class='frm_label' for=''>"+
									"快速回复:"+
								"</label>"+
								 "<div class='emoion_editor_wrp js_editor'>"+
									"<div class='emotion_editor'>"+
										"<div class='edit_area js_editorArea ccc_"+i+"' id='resMsg_"+i+"' style='display:block; width:800px; height:100px; overflow-y:auto; border:1px solid #ccc;' contenteditable='true'></div>"+
									"</div>"+
								"</div>"+        
								"<p class='quick_reply_box_tool_bar ddd_"+i+"'>"+
									"<span class='btn btn_primary btn_input'>"+
										"<button onClick=javascript:sendRes("+i+","+item.seqno+",'"+item.centerid+"','"+item.devtype+"','"+item.userid+"',"+page+",'"+item.freeuse6+"') >发送</button>"+
                  					"</span>"+
                  					"<a class='btn btn_default pickup' onClick=javascript:CloseResDialog("+i+") href='javascript:void(null)'>收起</a>"+
 								"</p>"+     
							"</div>"+
						"</li>"
                    );
        	});
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function ShowResDialog(i) {
	var pid = $("#listContainer").find(".aaa_"+i+"").attr("id");
    var lbmc = eval(pid);
    if (lbmc.style.display == 'none') {
        lbmc.style.display = '';
    }
    else {
        lbmc.style.display = 'none';
    }
}
function CloseResDialog(i) {
	//var pObj = $("#listContainer").find(".aaa")[i];
	var pid = $("#listContainer").find(".aaa_"+i+"").attr("id");
    var lbmc = eval(pid);
    if (lbmc.style.display == '') {
        lbmc.style.display = 'none';
    }
}

function ShowResRecord(i, seqno, status) {
	if("0" == status){
		$.messager.alert('提示','还未回复该留言!','info');
		return;
	}
	var pid = $("#listContainer").find(".bbb_"+i+"").attr("id");
	var lbmc = eval(pid);
    if (lbmc.style.display != 'none') {
       lbmc.style.display = 'none';
       return;
    }
	$.ajax({
		type : 'POST',
		url : "<%=request.getContextPath()%>/webapi60004.json",
		dataType: 'json',
		data:{seqno:seqno},
		success : function(data) {
			$("#"+pid).empty();
			$.each(data.resultList, function(i, item) {
				var content="";
				content += "<div style='width: 900px; height: 100px; padding-top: 10px; margin-top: 10px; margin-left: 20px; border-top-color: #ccc; border-right-color: #ccc; border-bottom-color: #ccc; border-left-color: #ccc; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;'>"+
								"<div style='width: 60px; height: 23px; float: left;'>"+
									"&nbsp; &nbsp;回复："+
								"</div>"+
								"<div class='message_info'>"+
									"<div class='message_time' style='margin-right: 62px;'>"+
										item.detaildate+
									"</div>"+
									"<div class='user_info' style='margin-left: 10px;'>"+
										"<a class='remark_name' target='_blank' >"+item.loginid+"</a>"+
									"</div>"+
								"</div>"+
								"<div class='message_content text' style='margin-left: 10px;'>"+
									"<div class='wxMsg' style='margin-left: 50px;'>"+
										item.detail+
									"</div>"+
								"</div>"+
							"</div>";
				$("#"+pid).append(content);
				lbmc.style.display = '';
			});
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function sendRes(i, seqno, centerid, devtype, requserid, page, freeuse6){
	var pid = $("#listContainer").find(".ccc_"+i+"").attr("id");
	if("" == $("#"+pid).text()){
		$.messager.alert('提示','请输入回复内容!','info');
		return;
	}
	var answerdetail = $("#"+pid).text();
	$.ajax({
		type : 'POST',
		url : "<%=request.getContextPath()%>/webapi60002.json",
		dataType: 'json',
		data:{seqno:seqno,
			  centerid:centerid,
			  userid:requserid,
			  devtype:devtype,
			  devid:"",
			  answerdetail:answerdetail,
			 // freeuse5:freeuse5,
			  freeuse6:freeuse6},
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			//CloseResDialog(i);
			//initData();
			getDataByPage(page);
			$("#"+pid).empty();
			
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});

}
//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}
</script>
</head>
<body>
	<div id="body" class="body page_message">
		<!-- <div id="js_container_box" class="container_box cell_layout side_l">
			<div class="col_main"> -->
				<div class="main_hd">
					<!-- <h2> 
						留言管理 
					</h2> -->
					<div class="title_tab" id="topTab" style="width: 1000px; height:50px; border-bottom-color: #eaeaea; border-bottom-width: 1px;border-bottom-style: solid;">
						<form id="form60001" class="dlg-form" novalidate="novalidate">
							<div>
								<p style="margin-left: 30px; display: inline;">
									开始日期:
									<input id="startdate" name="startdate" editable="false" class="easyui-datebox"  maxlength="10" validType="date&&maxdate['enddate','【结束日期】']"/>
								</p>
								<p style="margin-left: 30px; display: inline;">
									结束日期:
									<input id="enddate" name="enddate" editable="false" class="easyui-datebox"  maxlength="10" validType="date&&mindate['startdate','【开始日期】']"/>
								</p>
								<p style="margin-left: 30px; display: inline;">
									留言状态:
									<select id="status" name="status" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">			        	
										<option value="">请选择...</option>
										<%
											String options2 = "";
	      										List<Mi007> ary2=(List<Mi007>) request.getAttribute("msgStatusList");
	      										for(int i=0;i<ary2.size();i++){
	         										Mi007 mi007=ary2.get(i);
	         										String itemid=mi007.getItemid();
	         										String itemval=mi007.getItemval();
	         										options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      										}
	      										out.println(options2);
	        								%>
		        					</select>
								</p>
								<p style="margin-left: 30px; display: none;">
									设备区分:
									<select id="devtype" name="devtype" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">			        	
										<option value="">请选择...</option>
										<%
											String optionsAdd = "";
											JSONArray aryAdd=(JSONArray) request.getAttribute("devTypeJsonArray");
											for(int i=0;i<aryAdd.size();i++){
												JSONObject obj=aryAdd.getJSONObject(i);
												String itemid=obj.getString("itemid");
												String itemval=obj.getString("itemval");
												optionsAdd = (new StringBuilder(String.valueOf(optionsAdd))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
											}
											out.println(optionsAdd);
										%>
		        					</select>
								</p>
								<p style="margin-left: 30px; display: none;">
									来源:
									<select id="channel" name="channel" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">			        	
										<option value="">请选择...</option>
										<%
											String optionsSource = "";
											JSONArray arySource=(JSONArray) request.getAttribute("channelJsonArray");
											for(int i=0;i<arySource.size();i++){
												JSONObject obj=arySource.getJSONObject(i);
												String itemid=obj.getString("itemid");
												String itemval=obj.getString("itemval");
												optionsSource = (new StringBuilder(String.valueOf(optionsSource))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
											}
											out.println(optionsSource);
										%>
		        					</select>
								</p>
								<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
								<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
								
							</div>
						</form>
					</div>
				</div>
				
				<div class="main_bd">
					<ul class="message_list" id="listContainer">	
					</ul>
					<div class="tool_area">
						<div class="pagination_wrp pageNavigator">
            					<div id="pagination" class="pagination" >
    								<a href="#" class="first" data-action="first" style="width:25px;height:30px;">&laquo;</a>
    								<a href="#" class="previous" data-action="previous" style="width:25px;height:30px;">&lsaquo;</a>
    								<!-- <input type="text" readonly="readonly" data-max-page="40" /> -->
    								<input type="text" data-max-page="1" style="height:30px;"/>
    								<a href="#" class="next" data-action="next" style="width:25px;height:30px;">&rsaquo;</a>
    								<a href="#" class="last" data-action="last" style="width:25px;height:30px;">&raquo;</a>
								</div>
						</div>
					</div>
				</div>
			</div>
	<!-- 	</div>
	</div> -->
</body>
</html>