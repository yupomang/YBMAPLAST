<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="com.yondervision.mi.dto.Mi702"%>
<%@page import="com.yondervision.mi.dto.Mi704"%>
<%@page import="com.yondervision.mi.result.NewspapersTitleInfoBean"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@ include file = "/include/styles.jsp" %>
<%@ include file="/include/scripts.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>新闻发布</title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/ui/zxly/zxly.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/jqPagination/css/jqpagination.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="<%= _contexPath %>/scripts/jqPagination/js/jquery.jqpagination.min.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript">
var editor;
var imageUrl;
var uploadbutton;
KindEditor.ready(function(K) {
	uploadbutton = K.uploadbutton({
		button : K('#uploadButton')[0],
		fieldName : 'imgFile',
		url : '<%=request.getContextPath()%>/webapi70001_uploadimg.do',
		afterUpload : function(data) {
			if (data.error === 0) {
				var url = K.formatUrl(data.url, 'absolute');
				//K('#image').val(url);
				imageUrl = url;
				$('#imgView').attr('src', url);
			} else {
				alert(data.message);
			}
		},
		afterError : function(str) {
			alert('自定义错误信息: ' + str);
		}
	});
	uploadbutton.fileBox.change(function(e) {
		uploadbutton.submit();
	});
				
	editor = K.create('textarea[name="content"]', {
		allowFileManager : true,
		allowImageRemote : false,
		items:[
        'source', '|', 'undo', 'redo', '|', 'preview', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'lineheight', 'removeformat', '|', 'image',
        'insertfile', 'table', 'hr', 'pagebreak',
        'link', 'unlink'
		],
		
		uploadJson : '<%=request.getContextPath()%>/webapi70001_uploadimg.do'
	});		
	K('#imageBtn').click(function() {
		editor.loadPlugin('image', function() {
			
			editor.plugin.imageDialog({
				showRemote : false,
				imageUrl : K('#image').val(),
				clickFn : function(url, title, width, height, border, align) {
					K('#image').val(url);
					//imageUrl = url;
					//$('#imgView').attr('src', url),
					editor.hideDialog();
				}
			});
		});
	});
});
var url;
var result;
var pageSize = 5;

var classificationQryTmp;
var newspaperforumQryTmp;
var newspapercolumnsQryTmp;
var keywordTmp;
var selectTimesDialog="";
$(function(){

	var str = <%=request.getAttribute("timesJsonObj")%>;
	var initClassification = str.rows[0].itemid;
	
	$("[name='attentionFlg'][value='0']").attr("checked",true);
	initData();
	//查询
	$('#b_query').click(function (){
		$('.pagination').jqPagination('destroy');
		classificationQryTmp = $('#classificationQry').combobox('getValue');
		newspaperforumQryTmp = $("#newspaperforumQry").combobox("getValue");
		newspapercolumnsQryTmp = $("#newspapercolumnsQry").combobox("getValue");
		keywordTmp = $("#keyword").val();
		loadData();
	});	
	
	//重置
	$('#b_reset').click(function (){
		$('#form70201').form('clear');
		$('#classificationQry').combobox('select','');
		$('#newspaperforumQry').combobox('select','');
		$('#newspapercolumnsQry').combobox('select','');
		ydl.delErrorMessage('form70201');
	});
	
	//添加
	$('#b_add').click(function (){
		$("input[name=imgFile]").css("width","61px");
		clearDlgData();
		$("#imgView").attr('src', '/YBMAPZH/ui/zxly/images/news_icon.png');
		$('#dlg').dialog('open').dialog('setTitle','新闻_添加');
		url = '<%= _contexPath %>/webapi70201.json';
	});
	
	//保存
	$('#b_save').click(function (){
		if(editor.isEmpty()){
			//$("#content").val("0");
			$.messager.alert('错误',"请输入内容！",'error');
			return;
		}else{
			$("#content").val(editor.html());
		}
		$("#contentTmp").val(editor.text());

		$("#image").val(imageUrl);
		var arr = $('#fm').serializeArray();
		if (!$("#fm").form("validate")) return;
		$.ajax( {
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				$.messager.alert('提示', data.msg, 'info');
				if ("000000" == data.recode){
					$('#dlg').dialog('close');        // close the dialog
					$('.pagination').jqPagination('destroy');
					loadData();
				}
			},
			error :function(){
				$.messager.alert('错误',data.msg,'error');
			}
		});
	});	
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	//校验标题长度大于0，小于100
	$.extend($.fn.validatebox.defaults.rules, {
		titleLength: {
			validator: function (value, param) {
				return value.length > param[0] && value.length <= param[1];
        	},
        	message:'标题长度不能超过50个汉字'
    	}
	});
	
    //级联选择
    var selectTimes = initClassification;
	$('#classificationQry').combobox({
    	onChange:function(newValue,oldValue){
    	 	selectTimes = newValue;
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getForumColumnsJsonArrayFromMi704.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspaperforumQry').combobox('clear');
						$('#newspapercolumnsQry').combobox('clear');
						$('#newspaperforumQry').combobox('loadData', data.forumJsonArray);
						$('#newspapercolumnsQry').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
	$('#newspaperforumQry').combobox({
    	onChange:function(newValue,oldValue){
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getColumnsJsonArrayFromMi704.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':selectTimes,'newspaperforum':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspapercolumnsQry').combobox('clear');
						$('#newspapercolumnsQry').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
	
	$('#classification').combobox({
    	onChange:function(newValue,oldValue){
    	 	selectTimesDialog = newValue;
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getForumColumnsJsonArrayFromMi704.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspaperforum').combobox('clear');
						$('#newspapercolumns').combobox('clear');
						$('#newspaperforum').combobox('loadData', data.forumJsonArray);
						$('#newspapercolumns').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
	$('#newspaperforum').combobox({
    	onChange:function(newValue,oldValue){
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/getColumnsJsonArrayFromMi704.json',
				dataType: "json",
				data:{'centeridTmp':'<%=user.getCenterid()%>','classification':selectTimesDialog,'newspaperforum':newValue},
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$('#newspapercolumns').combobox('clear');
						$('#newspapercolumns').combobox('loadData', data.columnsJsonArray);
					}
				},
				error :function() {
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}  
	}); 
})
function initData(){
	var arr = $('#form70201').serializeArray();
	//校验
	if (!$("#form70201").form("validate")) return;
	
	classificationQryTmp = $('#classificationQry').combobox('getValue');
	newspaperforumQryTmp = $('#newspaperforumQry').combobox('getValue');
	newspapercolumnsQryTmp = $('#newspapercolumnsQry').combobox('getValue');
	keywordTmp = $("#keyword").val();
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70204.json",
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
					var page = 1;
					var imgUrl;
					if("" == item.image){
						imgUrl = item.freeuse1;
					}else{
						imgUrl = item.image;
					}
					var page = 1;
					var emVal;
					if ('0'== item.freeuse2){
						emVal = "<em class='tipsRes' style='display:block;'></em>";
					}else{
						emVal = "<em class='tips' style='display:block;'><i>●</i>本期看点</em>";
					}
					$("#listContainer").append(
                    	"<li class='message_item item1' id='"+item.seqno+"'>"+
							"<div  class='user_inf inf1' >"+
								"<a target='_blank' class='avatar'>"+
                    				"<img style='width:50px; height:50px; float:left; margin:0 10px 0 13px;' src='"+imgUrl+"'/>"+
                				"</a>"+
                				"<div class='inf2'>"+
                					"<a target='_blank' class='remark_name' >"+item.title+"</a>"+
               						"<div class='message_status status1'>"+emVal+"</div>"+
            						"<div class='message_status status1'>"+$('#newspapercolumnsQry option[value='+item.newspapercolumns+']').text()+"</div>"+
            						"<div class='message_status status1'>"+$('#newspaperforumQry option[value='+item.newspaperforum+']').text()+"</div>"+
            						"<div class='wxMsg wxMsg1'>"+item.introduction+"</div>"+
            						"<div class='news_message_info info2'>"+
            							"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/zan.png' width='18' height='18' style='margin:1px; display:block; float:left;'/>"+item.praisecounts+"</div>"+
           								"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/ping.png' width='18' height='18' style='margin:2px; display:block; float:left;'/>"+item.commentcounts+"</div>"+
									"</div>"+
                    			"</div>"+
                    		"</div>"+
							"<div class='opr2' >"+
								"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/zxly/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
								"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/zxly/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
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
	var arr = $('#form70201').serializeArray();
	//校验
	if (!$("#form70201").form("validate")) return;
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70204.json",
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
					var page = 1;
					var imgUrl;
					if("" == item.image){
						imgUrl = item.freeuse1;
					}else{
						imgUrl = item.image;
					}
					var page = 1;
					var emVal;
					if ('0'== item.freeuse2){
						emVal = "<em class='tipsRes' style='display:block;'></em>";
					}else{
						emVal = "<em class='tips' style='display:block;'><i>●</i>本期看点</em>";
					}
					$("#listContainer").append(
                    	"<li class='message_item item1' id='"+item.seqno+"'>"+
							"<div  class='user_inf inf1' >"+
								"<a target='_blank' class='avatar'>"+
                    				"<img style='width:50px; height:50px; float:left; margin:0 10px 0 13px;' src='"+imgUrl+"'/>"+
                				"</a>"+
                				"<div class='inf2'>"+
                					"<a target='_blank' class='remark_name' >"+item.title+"</a>"+
               						"<div class='message_status status1'>"+emVal+"</div>"+
            						"<div class='message_status status1'>"+$('#newspapercolumnsQry option[value='+item.newspapercolumns+']').text()+"</div>"+
            						"<div class='message_status status1'>"+$('#newspaperforumQry option[value='+item.newspaperforum+']').text()+"</div>"+
            						"<div class='wxMsg wxMsg1'>"+item.introduction+"</div>"+
            						"<div class='news_message_info info2'>"+
            							"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/zan.png' width='18' height='18' style='margin:1px; display:block; float:left;'/>"+item.praisecounts+"</div>"+
           								"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/ping.png' width='18' height='18' style='margin:2px; display:block; float:left;'/>"+item.commentcounts+"</div>"+
									"</div>"+
                    			"</div>"+
                    		"</div>"+
							"<div class='opr2' >"+
								"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/zxly/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
								"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/zxly/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
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
	$.ajax({
		type : 'POST',
		url: "<%=request.getContextPath()%>/webapi70204.json?page="+page,
		dataType: 'json',
		//data:arr,
		data:{classificationQry:classificationQryTmp,newspaperforumQry:newspaperforumQryTmp,newspapercolumnsQry:newspapercolumnsQryTmp,keyword:keywordTmp},
		success : function(data) {
			$("#listContainer").empty();
			$.each(data.rows, function(i, item) {
				var imgUrl;
				if("" == item.image){
					imgUrl = item.freeuse1;
				}else{
					imgUrl = item.image;
				}
				if ('0'== item.freeuse2){
					emVal = "<em class='tipsRes' style='display:block;'></em>";
				}else{
					emVal = "<em class='tips' style='display:block;'><i>●</i>本期看点</em>";
				}
				$("#listContainer").append(
                    	"<li class='message_item item1' id='"+item.seqno+"'>"+
							"<div  class='user_inf inf1' >"+
								"<a target='_blank' class='avatar'>"+
                    				"<img style='width:50px; height:50px; float:left; margin:0 10px 0 13px;' src='"+imgUrl+"'/>"+
                				"</a>"+
                				"<div class='inf2'>"+
                					"<a target='_blank' class='remark_name' >"+item.title+"</a>"+
               						"<div class='message_status status1'>"+emVal+"</div>"+
            						"<div class='message_status status1'>"+$('#newspapercolumnsQry option[value='+item.newspapercolumns+']').text()+"</div>"+
            						"<div class='message_status status1'>"+$('#newspaperforumQry option[value='+item.newspaperforum+']').text()+"</div>"+
            						"<div class='wxMsg wxMsg1'>"+item.introduction+"</div>"+
            						"<div class='news_message_info info2'>"+
            							"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/zan.png' width='18' height='18' style='margin:1px; display:block; float:left;'/>"+item.praisecounts+"</div>"+
           								"<div class='message_status status2'><img src='<%=_contexPath%>/ui/zxly/images/ping.png' width='18' height='18' style='margin:2px; display:block; float:left;'/>"+item.commentcounts+"</div>"+
									"</div>"+
                    			"</div>"+
                    		"</div>"+
							"<div class='opr2' >"+
								"<a onClick=javascript:modNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='编辑' src='<%=_contexPath%>/ui/zxly/images/edit_icon.jpg' width='24' height='24'  alt=''/></a>"+
								"<a id='b_qryRecord' onClick=javascript:delNews("+i+","+item.seqno+","+page+") href='javascript:void(null)'><img title='删除' src='<%=_contexPath%>/ui/zxly/images/del_icon.jpg' width='24' height='24' alt=''/></a>"+
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
function modNews(i, seqno, page) {
	$("input[name=imgFile]").css("width","61px");
	imageUrl = "";
	editor.html('');
	$("#contentTmp").val("");
	$("#userid").val("");
	$("#imgView").attr('src', '');
	$("[name='attentionFlg'][value='0']").attr("checked",true);	
	$.ajax({
		type : 'POST',
		url : "<%=request.getContextPath()%>/webapi70205.json",
		dataType: 'json',
		data:{seqno:seqno},
		success : function(data) {
			if ("000000" == data.recode) {
				$('#fm').form('load',data.result);
				selectTimesDialog = data.result.classification;
				$("#image").val(data.result.image);
				imageUrl = data.result.image;
				if("" == data.result.image){
					$('#imgView').attr('src', data.result.freeuse1);
				}else{
					$('#imgView').attr('src', data.result.image);
				}
				editor.html(data.result.content);
				$("[name='attentionFlg'][value='"+data.result.freeuse2+"']").attr("checked",true);
				$('#newspaperforum').combobox('loadData', data.result.forumJsonArray);
				$('#newspapercolumns').combobox('loadData',data.result.columnsJsonArray);	

				$('#dlg').dialog('open').dialog('setTitle','新闻-修改');
				url = '<%= _contexPath %>/webapi70203.json';
			}else {
				$.messager.alert('提示',data.msg,'info');
			}
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}
function delNews(i, seqno, page) {
	$.messager.confirm('提示','确认是否删除?',function(r){
		if (r) {
			$.ajax({
				type : 'POST',
				url : "<%=request.getContextPath()%>/webapi70202.json",
				dataType: 'json',
				data:{seqno:seqno},
				success : function(data) {
					if ("000000" == data.recode){
						$('.pagination').jqPagination('destroy');
						loadData();
						$.messager.alert('提示','删除成功','info');
					}else{
						$.messager.alert('提示',data.msg,'info');
					}
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');
				}
			});
		}
	});
}
function clearDlgData(){
	imageUrl = "";
	$('#classification').combobox('setValue','');
	$('#newspaperforum').combobox('setValue','');
	$('#newspapercolumns').combobox('setValue','');
	$("#citedtitle").val("");
	$("#title").val("");
	$("#subtopics").val("");
	$("#source").val("");
	$("#image").val("");
	$("#introduction").val("");
	$("#blurbs").val("");
	$("#content").val("");
	editor.html('');
	$("#seqno").val("");  
	$("#centerid").val("");
	$("#contentTmp").val("");
	$("#devid").val("");
	$("#devtype").val("");
	$("#userid").val("");
	$("#page").val("");
	$("#imgView").attr('src', '');
	$("[name='attentionFlg'][value='0']").attr("checked",true);
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
					<h2> 
						新闻发布
					</h2>
					<div class="title_tab" id="topTab" style="width: 1000px; height:90px; border-bottom-color: #eaeaea; border-bottom-width: 1px;border-bottom-style: solid;">
						<form id="form70201" class="dlg-form" novalidate="novalidate">
							<div class="title_tab1" id="topTab1" style="width: 1000px; height:40px;">
								<p style="margin-left: 30px; display: inline;">
									报刊期次:
									<select id="classificationQry" name="classificationQry" editable="false" class="easyui-combobox" style="max-width:200px;" required="true" data-options="valueField:'itemid',textField:'itemval'">
										<%
											String options2 = "";
											List<Mi702> ary2=(List<Mi702>) request.getAttribute("classificationlist");
	      									for(int i=0;i<ary2.size();i++){
	         									Mi702 mi702=ary2.get(i);
	         									String itemid=mi702.getItemid();
	         									String itemval=mi702.getItemval();
	         									options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(options2);
	        							%>
									</select>
								</p>
								<p style="margin-left: 30px; display: inline;">
									报刊版块:
									<select id="newspaperforumQry" name="newspaperforumQry" editable="false" class="easyui-combobox" style="max-width:200px;" data-options="valueField:'itemid',textField:'itemval'">
										<option value=""></option>
										<%
											String options3 = "";
											List<Mi704> ary3=(List<Mi704>) request.getAttribute("newspaperforumlistInit");
	      									for(int i=0;i<ary3.size();i++){
	         									Mi704 mi704=ary3.get(i);
	         									String itemid=mi704.getItemid();
	         									String itemval=mi704.getItemval();
	         									options3 = (new StringBuilder(String.valueOf(options3))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(options3);
	        							%>
									</select>
								</p>
								<p style="margin-left: 30px; display: inline;">
									报刊栏目:
									<select id="newspapercolumnsQry" name="newspapercolumnsQry" editable="false" class="easyui-combobox" style="max-width:200px;" data-options="valueField:'itemid',textField:'itemval'">
										<option value=""></option>
										<%
											String options4 = "";
											List<NewspapersTitleInfoBean> ary4=(List<NewspapersTitleInfoBean>) request.getAttribute("newspapercolumnslistInit");
	      									for(int i=0;i<ary4.size();i++){
	         									NewspapersTitleInfoBean bean4=ary4.get(i);
	         									String itemid=bean4.getItemid();
	         									String itemval=bean4.getItemval();
	         									options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      									}
	      									out.println(options4);
	        							%>
									</select>
								</p>
								<p style="margin-left: 30px; display: inline;">
									关键字:
									<input type="text" id="keyword" name="keyword" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" class="easyui-validatebox"/>
								</p>
							</div>
							<div class="title_tab2" id="topTab2" style="width: 1000px; text-align:center">
								<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
								<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
								<a id="b_add" class="easyui-linkbutton" iconCls="icon-add" href="javascript:void(0)" >添加新闻</a>
							</div>
						</form>
					</div>
				</div>
				
				<div class="main_bd">
					<ul class="message_list" id="listContainer">	
					</ul>
					<div class="tool_area" style="float:right; margin-top:20px;">
						<div class="pagination_wrp pageNavigator">
            					<div id="pagination" class="pagination">
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
		<!-- </div>
	</div> -->
	
		
	<div id="dlg" class="easyui-dialog" closed="true" style="width:880px;height:500px;padding:10px  10px"  modal="true"  buttons="#dlg-buttons">  
		<div class="formtitle">新闻</div>
        <form id="fm" method="post" class="dlg-form" novalidate="novalidate">
        	<table class="dlgcontainer">
				
				<tr>
					<th><label for="classification" style="font-size: 14px;font-weight: normal;">所属期次：</label></th>
			        <td>
						<select id="classification" name="classification" editable="false"  required="true" class="easyui-combobox" style="width:200px;" data-options="valueField:'itemid',textField:'itemval'">
							<option value=""></option>
							<%
								String options1 = "";
								List<Mi702> ary1=(List<Mi702>) request.getAttribute("classificationlist");
								for(int i=0;i<ary1.size();i++){
									Mi702 mi702=ary1.get(i);
									String itemid=mi702.getItemid();
									String itemval=mi702.getItemval();
									options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options1);
							%>
						</select>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="newspaperforum" style="font-size: 14px;font-weight: normal;">所属版块：</label></th>
			        <td>
						<select id="newspaperforum" name="newspaperforum" editable="false"  required="true" class="easyui-combobox" style="width:200px;" data-options="valueField:'itemid',textField:'itemval'">
								<option value=""></option>
								<%
									String options5 = "";
									List<Mi007> ary5=(List<Mi007>) request.getAttribute("newspaperforumlist");
	      							for(int i=0;i<ary5.size();i++){
	         							Mi007 mi7007=ary5.get(i);
	         							String itemid=mi7007.getItemid();
	         							String itemval=mi7007.getItemval();
	         							options5 = (new StringBuilder(String.valueOf(options5))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      							}
	      							out.println(options5);
	        					%>
						</select>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>  
				<tr>
					<th><label for="newspapercolumns" style="font-size: 14px;font-weight: normal;">所属栏目：</label></th>
			        <td>
						<select id="newspapercolumns" name="newspapercolumns" editable="false" class="easyui-combobox" style="width:200px;" data-options="valueField:'itemid',textField:'itemval'">
							<option value=""></option>
							<%
								String options6 = "";
								List<Mi007> ary6=(List<Mi007>) request.getAttribute("newspapercolumnslist");
	      						for(int i=0;i<ary6.size();i++){
	         						Mi007 mi7007=ary6.get(i);
	         						String itemid=mi7007.getItemid();
	         						String itemval=mi7007.getItemval();
	         						options6 = (new StringBuilder(String.valueOf(options6))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
	      						}
	      						out.println(options6);
	        				%>
						</select>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="newspapercolumns" style="font-size: 14px;font-weight: normal;">本期看点：</label></th>
			        <td >
						<li><input id="attentionFlg-0" name="attentionFlg" type="radio" value="0"/><label for="attentionFlg-0" style="font-size: 14px;font-weight: normal;">否</label></li>
						<li><input id="attentionFlg-1" name="attentionFlg" type="radio" value="1"/><label for="attentionFlg-1" style="font-size: 14px;font-weight: normal;">是</label></li>
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
		          <!-- <tr ><th><label for="img" style="font-size:14px;font-weight:normal;">新闻图标：</label></th>
			        <td style="position: relative;">
			        	<input type="hidden" id="image" name="image" readonly="readonly" style="font-size:12px;font-weight:normal;width:400px;height:25px;"/> 
						<img id="imgView" name="imgView"  style="width:80px;height:80px;border:1px solid #95B8E7;"/>
						&nbsp&nbsp&nbsp<input id="imageBtn" type="button" value="选择图标" style="line-height:1.5em;font-family:Arial, Helvetica;font-size:0.8em;font-style:nomal;position: absolute; bottom: 6px;" >
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr>  -->
		       <tr ><th><label for="img" style="font-size:14px;font-weight:normal;">新闻图标：</label></th>
			        <td>
						<img id="imgView" name="imgView"  style="width:80px;height:80px;border:1px solid #95B8E7;"/>
							<input class="ke-input-text" type="hidden" id="image" name="image" readonly="readonly" />
							<input type="button" id="uploadButton" value="选择图标" />
					
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="citedtitle" style="font-size: 14px;font-weight: normal;">新闻引题：</label></th>
			        <td>
			        	<input type="text" id="citedtitle" name="citedtitle" class="easyui-validatebox" validType="titleLength[1,200]" style="font-size: 12px;font-weight: normal;width:650px;height:25px;" >
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="title" style="font-size: 14px;font-weight: normal;">新闻正题：</label></th>
			        <td>
			        	<input type="text" id="title" name="title" class="easyui-validatebox" required="true" validType="titleLength[1,200]" style="font-size: 12px;font-weight: normal;width:650px;height:25px;" >
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr> 
				<tr>
					<th><label for="subtopics" style="font-size: 14px;font-weight: normal;">新闻副题：</label></th>
			        <td>
			        	<input type="text" id="subtopics" name="subtopics" class="easyui-validatebox" validType="titleLength[1,200]" style="font-size: 12px;font-weight: normal;width:650px;height:25px;" >
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>
				<tr>
					<th><label for="source" style="font-size: 14px;font-weight: normal;">来源/作者：</label></th>
			        <td>
			        	<input type="text" id="source" name="source" class="easyui-validatebox" validType="titleLength[1,200]" style="font-size: 12px;font-weight: normal;width:650px;height:25px;" >
			        </td>	
		        	<th></th>
		        	<td></td>
		        </tr>   
				<tr>
					<th><label for="introduction" style="font-size:14px;font-weight:normal;">新闻摘要：</label></th>
			        <td colspan="3" ><textarea rows="3" id="introduction" name="introduction" class="easyui-validatebox" style="font-size:12px;font-weight:normal;width:650px;"></textarea>
			        </td>
		        </tr>
				<tr>
					<th><label for="blurbs" style="font-size:14px;font-weight:normal;">新闻导语：</label></th>
			        <td colspan="3" ><textarea rows="3" id="blurbs" name="blurbs" class="easyui-validatebox" style="font-size:12px;font-weight:normal;width:650px;"></textarea>
			        </td>
		        </tr>
				<tr><th><label for="content" style="font-size: 14px;font-weight: normal;">详细内容：</label></th>
			        <td><textarea id="content" name="content" style="width:700px;height:180px;visibility:hidden;" ></textarea>
			        </td>
		        	<th></th>
		        	<td></td>
		        </tr>
	        </table>
	        <input id="contentTmp" name="contentTmp" type="hidden" />
	        <input id="seqno" name="seqno" type="hidden" />
	        <input type="hidden" id="centerid" name="centerid" />
	        <input type="hidden" id="devid" name="devid" />
	        <input type="hidden" id="devtype" name="devtype" />
	        <input type="hidden" id="userid" name="userid" />
	        <input type="hidden" id="page" name="page" />
	        	
        </form>
    </div>
    	
    <div id="dlg-buttons">
        <a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
        <a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
    </div>
</body>
</html>