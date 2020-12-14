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
<title>中心客户功能分配</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %> 
<script type="text/javascript">
     var functionjson=<%=request.getAttribute("functionjson")%>     
     var permissionMenuJson=<%=request.getAttribute("permissionMenuJson")%>
     function dispRoleChk(funcid,centerid,pid,leafFlg){
       if(pid=="00000000" || leafFlg == "0")
          return "";
       else {
       	  var seqno;
		  if(funcid.length > 8){
			seqno = funcid.substring(8);
			return "<input type='checkbox' name='permission' value='"+funcid.substring(0,8)+"_"+centerid+"_"+Math.pow(2,(8-seqno))+"'/>";
		  }else{
		  	seqno = "0";
		  	return "<input type='checkbox' name='permission' value='"+funcid+"_"+centerid+"_"+seqno+"'/>";
		  }
       }
	}
	 $(function(){
	     $('#tt').treegrid({  
            treeField:'funcname',
            //title:'中心客户功能分配',  
            toolbar:[
				{   
					id:'btnsave',
					text:'保存', 
					iconCls:'icon-save',
					handler:function(){ 
					    var fromData=$('#permissionForm').serializeArray()
					    var url="ptl40015Upd.json";
					    $.ajax({	 
					     type : "POST",
					     url : url,
					     dataType: "json",
					     data:fromData,
					     success :function(data) {
					       //alert(data.msg)	 
						   $.messager.alert('提示', data.msg, 'info' );
					     }
		              });				 
					}
				}
		    ],
            height:$(document).height()-121,
            frozenColumns:[[
            	{title:'功能',field:'funcname',width:180} 
            ]],
            columns:[[ 
              {title:'功能',field:'funcname1',width:180,hidden:true}
              <%
                 JSONArray ary=(JSONArray) request.getAttribute("centerjson");
                 for(int i=0;i<ary.size();i++){
                    JSONObject obj=ary.getJSONObject(i);
                    out.println(",{field:'R"+obj.get("centerid")+"',title:'"+obj.get("centername")+"',formatter:function(v,rows){return dispRoleChk(rows['funcid'],'"+obj.get("centerid")+"',rows['pid'],rows['leafFlg'])}}");
                 }
              %> 
            ]] 
        }).treegrid("loadData",functionjson);
       
        if(permissionMenuJson!=null){
        	for(var i=0;i<permissionMenuJson.length;i++){
              var qx = permissionMenuJson[i];              
              var funcid = qx.funcid;
              var centerid = qx.centerid;
              var permission = qx.permission;
              if("00000000" != permission){
             	 var seqnos = permission.split('');
             	 for(var j=0;j<seqnos.length;j++){
             	 	if(seqnos[j] == "1"){
             	 		$("[name='permission'][value='"+funcid+"_"+centerid+"_"+Math.pow(2,(8-j-1))+"']").attr("checked",true);
             	 	}
             	 }
              }else{
             	 $("[name='permission'][value='"+funcid+"_"+centerid+"_0']").attr("checked",true);
              }
          }
        }
        
		//确定
		$('#b_confirm').click(function (){
			if (!$("#form40015").form("validate")) return;
			var choseEle = $('#centerChose').combobox('getValue');
			var opts = $('#tt').datagrid('getColumnFields');
			if('' == choseEle){
				$.each(opts,function(index, ele){
					if (ele == 'funcname1') {
						$('#tt').treegrid('hideColumn', ele);
					}else{
						$('#tt').treegrid('showColumn', ele);
					}
				});
			}else{
				$.each(opts,function(index, ele){
					if (ele == 'R'+choseEle) {
						$('#tt').treegrid('showColumn', ele);
					} else {
						$('#tt').treegrid('hideColumn',ele);
					}
				});
			}
		});
	}); 
</script>
</head> 
<body style="margin:0px;">
<div class="easyui-panel" title="中心客户功能分配">
	<form id="form40015" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<th><label for="centerChoseLabel">待分配客户：</label></th>
				<td>
					<select id="centerChose" name="centerChose" style="width:220px;" class="easyui-combobox" ><!-- panelHeight="auto" -->
						<option value="">全部客户</option>
						<%
							String options1 = "";
       						JSONArray ary1=(JSONArray) request.getAttribute("centerjson");
       						for(int i=0;i<ary1.size();i++){
          						JSONObject obj1=ary1.getJSONObject(i);
          						String itemid=obj1.getString("centerid");
          						String itemval=obj1.getString("centername");
          						options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
       						}
       						out.println(options1);
      					%>
					</select>
				</td>
			</tr>
		</table>
		<div class="buttons">
			<a id="b_confirm" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >确定</a>
		</div>
	</form>
</div>
<form method="post" action="" id="permissionForm" novalidate="novalidate">
<table id=tt></table>
</form>
</body>
</html>