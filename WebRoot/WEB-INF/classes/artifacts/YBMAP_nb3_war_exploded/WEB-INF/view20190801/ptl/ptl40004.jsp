<%@ page language="java" contentType="text/html; charset=utf-8" %>
<% 
  /*
     文件名：ptl40004.jsp
     作者： 韩占远
     日期：2013-10-06 
     作用：权限管理
  */
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>角色权限分配</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %> 
<script type="text/javascript">
	var menujson=<%=request.getAttribute("menujson")%>;
	var permissionMenuJson=<%=request.getAttribute("permissionMenuJson")%>;
	var centeridObj = <%=request.getAttribute("centeridJson")%>;
	var centerid = centeridObj.centerid;
	
     function dispRoleChk(funcid,roleid,pid,leafFlg){
		if(pid=="00000000" || leafFlg == "0")
			return "";
		else{
			if(funcid.length > 8){
       			seqno = funcid.substring(8);
       			return "<input type='checkbox' name='permission' value='"+funcid.substring(0,8)+"_"+roleid+"_"+Math.pow(2,(8-seqno))+"'/>";
			}else{
				return "<input type='checkbox' name='permission' value='"+funcid+"_"+roleid+"_0'>";
			}
		}
	}
	 $(function(){

	     $('#tt').treegrid({  
            treeField:'funcname',
            //title:'角色权限分配',  
            toolbar:[
				{   
					id:'btnsave',
					text:'保存', 
					iconCls:'icon-save',
					handler:function(){ 
					    var fromData=$('#permissionForm').serializeArray();
					    var url="ptl40004Upd.json?centerid="+centerid;
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
            	{title:'菜单',field:'funcname',width:180} 
            ]],
            columns:[[ 
              {title:'菜单',field:'funcname1',width:180,hidden:true} 
              <%
                 JSONArray ary=(JSONArray) request.getAttribute("rolejson");
                 for(int i=0;i<ary.size();i++){
                    JSONObject obj=ary.getJSONObject(i);
                    out.println(",{field:'R"+obj.get("roleid")+"',title:'"+obj.get("rolename")+"',formatter:function(v,rows){return dispRoleChk(rows['funcid'],'"+obj.get("roleid")+"',rows['pid'],rows['leafFlg'])}}");
                 }
              %> 
            ]] 
        }).treegrid("loadData",menujson);   
       
        if(permissionMenuJson!=null)
          for(var i=0;i<permissionMenuJson.length;i++){
              var qx=permissionMenuJson[i];              
              var funcid=qx.funcid;
              var roleid=qx.roleid;
			  var permission = qx.permission;
			  if("00000000" != permission){
			     var seqnos = permission.split('');
             	 for(var j=0;j<seqnos.length;j++){
             	 	if(seqnos[j] == "1"){
             	 		$("[name='permission'][value='"+funcid+"_"+roleid+"_"+Math.pow(2,(8-j-1))+"']").attr("checked",true);
             	 	}
             	 }
			  }else{
			  	$("[name='permission'][value='"+funcid+"_"+roleid+"_0']").attr("checked",true);
			  }
          }
	        
		//查询
		$('#b_confirm').click(function (){
			if (!$("#form40004").form("validate")) return;
			var choseEle = $('#roleChose').combobox('getValue');
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
<% String reqPath1 = request.getHeader("Referer"); if (reqPath1.contains("ptl40004Pre.html")) {%>
<div class="easyui-panel" title=<%=request.getAttribute("centerName") %>-角色权限分配>
<% } else {%>
<div class="easyui-panel" title="角色权限分配">
<%} %>
	<form id="form40004" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<th><label for="roleChoseLabel">待分配角色：</label></th>
				<td>
					<select id="roleChose" name="roleChose" class="easyui-combobox" panelHeight="auto">
						<option value="">全部角色</option>
						<%
							String options1 = "";
       						JSONArray ary1=(JSONArray) request.getAttribute("rolejson");
       						for(int i=0;i<ary1.size();i++){
          						JSONObject obj1=ary1.getJSONObject(i);
          						String itemid=obj1.getString("roleid");
          						String itemval=obj1.getString("rolename");
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
			<%
				String reqPath = request.getHeader("Referer");
				if(reqPath.contains("ptl40004Pre.html")){ %>
					<a id="b_back" class="easyui-linkbutton" iconCls="icon-back" href="javascript:history.go(-1)" >返回</a>
			<%} %>
		</div>
	</form>
</div>
<form method="post" action="" id="permissionForm" novalidate="novalidate">
<table id=tt></table>
</form>
</body>
</html>