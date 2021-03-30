var page = "0";
var allpage;
var url = window.location.href;
var initid;
var allresult;
var allpicid;
$(function(){
	$(document).click(function(e){ 
		console.log(e.target)
		var vid = e.target.id;
		if(vid.indexOf("radio_") >= 0 || vid.indexOf("radiolable_") >= 0){
			return;
		}
		if($(e.target).is("#movepichide,#deletepichide,#newgroupdiv,#renamediv,#deletegroup")){ 
			
		}else{
			if($(e.target).is("#movepicgroup,#deletepic,#newgroup,#rename,#deletediv,#movegroupbuttonstyle,#deteledivdetail,#deletepichideid,#renameinput,#groupname,#creategroup,#updategroupname,input[type='checkbox']")){
				 
			}else{
			   $("#movepichide").addClass("hide");
			   $("#deletepichide").addClass("hide");
			   $("#newgroupdiv").addClass("hide");
			   $("#renamediv").addClass("hide");
			   $("#deletediv").addClass("hide");
			}
		}
	});
	
	$("#picdiv").on('mouseover',".edit",function(){
		$(this).attr("src","scripts/foddermanager/image/edit0.png");
		var id = $(this).attr("id");
		getAlt(id,"编辑名称");
	});
	
	$("#picdiv").on('mouseout',".edit",function(){
		$("#altpositon").addClass('hide');
		$(this).attr("src","scripts/foddermanager/image/edit1.png");
	});
	
	$("#picdiv").on('mouseover',".switch",function(){
		$(this).attr("src","scripts/foddermanager/image/switch0.png");
		var id = $(this).attr("id");
		getAlt(id,"移动分组");
	});
	
	$("#picdiv").on('mouseout',".switch",function(){
		$("#altpositon").addClass('hide');
		$(this).attr("src","scripts/foddermanager/image/switch1.png");
	});
	
	$("#picdiv").on('mouseover',".del",function(){
		$(this).attr("src","scripts/foddermanager/image/del0.png");
		var id = $(this).attr("id");
		getAlt(id,"删除");
	});
	
	$("#picdiv").on('mouseout',".del",function(){
		$("#altpositon").addClass('hide');
		$(this).attr("src","scripts/foddermanager/image/del1.png");
	});
	
	var id = url.split("?")[1];
	if(typeof(id) != "undefined"){
		$("p").removeClass("stylepchange");
		$("#" + id).addClass("stylepchange");	
		var groupname = $("#" + id).text();
		var aa = groupname.split("(");
		if(id == initid){
			$(".changegroupname").addClass("hide");
			$("#changegroupname").html(aa[0]);
			$(".guize").css("margin","15px 0 0 120px");
		}else{
			$("#changegroupname").html(aa[0]);
			$(".changegroupname").removeClass("hide");
			$(".guize").css("margin","15px 0 0 10px");
		}
	}
	var dirname = $(".stylepchange").attr("id");
	$("#dataTid").data("dirname", dirname);
	$("#newgroup").click(function(){
		$("#movepichide").addClass("hide");
		$("#renamediv").addClass("hide");
		$("#deletediv").addClass("hide");
		$("#deletepichide").addClass("hide");
		$("#editpicnamediv").addClass("hide");
		$("#movepicbyone").addClass("hide");
		$("#deletepicbyone").addClass("hide");
		$("#newgroupdiv").removeClass("hide");
	});
	$("#cancleCG").click(function(){
		$("#newgroupdiv").addClass("hide");
	});
	$("#cancleupdate").click(function(){
		$("#renamediv").addClass("hide");
	});
	$("#deletegroup").click(function(){
		$("#movepichide").addClass("hide");
		$("#renamediv").addClass("hide");
		$("#newgroupdiv").addClass("hide");
		$("#deletepichide").addClass("hide");
		$("#editpicnamediv").addClass("hide");
		$("#movepicbyone").addClass("hide");
		$("#deletepicbyone").addClass("hide");
		$("#deletediv").removeClass("hide");
	});
	$("#cancledelete").click(function(){
		$("#deletediv").addClass("hide");
	});
	$("#movepicgroup").click(function(){
		$("#newgroupdiv").addClass("hide");
		$("#renamediv").addClass("hide");
		$("#deletediv").addClass("hide");
		$("#deletepichide").addClass("hide");
		$("#editpicnamediv").addClass("hide");
		$("#movepicbyone").addClass("hide");
		$("#deletepicbyone").addClass("hide");
		$("#movepichide").removeClass("hide");
	});
	$("#cancleupdatepicname").click(function(){
		$("#editpicnamediv").addClass("hide");
	});
	$("#concledeletepicbyone").click(function(){
		
		$("#deletepicbyone").addClass("hide");
	});

	$("#commitdeletepicbyone").click(function(){
		var idarr = allpicid;
		$.ajax({
			url:"webapi13006.json",
			data:{
				idarr:idarr
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var id = $("#dataTid").data("dirname");
				window.location.href=url.split("?")[0] + "?" + id;
			},
			error:function(){
				$("#deletepichide").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	$("#currentpageright").click(function(){
		page = parseInt(page) + 1;
		pageLeftRight();
		var id = $("#dataTid").data("dirname");
		getImageInfoByGroup(id,page-1,"12");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		document.documentElement.scrollTop = document.body.scrollTop =0;
	});

	$("#currentpageleft").click(function(){
		page = parseInt(page) - 1;
		pageLeftRight();
		var id = $("#dataTid").data("dirname");
		getImageInfoByGroup(id,page-1,"12");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		document.documentElement.scrollTop = document.body.scrollTop =0;
	});

	$("#gotonewpage").click(function(){
		var num = $("#systempagenum").val();
		if($.trim(num) == ""){
			return;
		}
		var reg = new RegExp("^[0-9]*$");  
		if(!reg.test(num)){
			alert("请输入正确的页码");
			return;
		}
		if(num > allpage){
			alert("请输入正确的页码");
			return;
		}
		page = num;
		pageLeftRight();
		var id = $("#dataTid").data("dirname");
		getImageInfoByGroup(id,page-1,"12");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		$("#systempagenum").val("");
		document.documentElement.scrollTop = document.body.scrollTop =0;
	});
	
	$("#updatepicname").click(function(){
		var newname = $("#renamepicname").val();
		if(newname == ""){
			alert("请输入图片名称");
			return;
		}
		$.ajax({
			url:"webapi13009.json",
			data:{
				newname:newname,
				allpicid:allpicid
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var id = $("#dataTid").data("dirname");
				window.location.href=url.split("?")[0] + "?" + id;
			},
			error:function(){
				$("#editpicnamediv").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	$("#movepichide").on('click',"#movegroupcancle",function(){
		$("#movepichide").addClass("hide");
	});

	$("#movepicbyone").on('click',"#movegroupcancle",function(){
		$("#movepicbyone").addClass("hide");
	});

	$("#movepichide").on('click',".radiolable",function(){
		var id = $(this).attr("id");
		var radioid = id.split("_")[1];
		$("#radio_" + radioid).attr("checked","checked");
	});
	
	$("#movepichide").on('click',".radiostyle",function(){
		$(this).attr("checked","checked");
	});

	$("#movepicbyone").on('click',".radiolable",function(){
		var id = $(this).attr("id");
		var radioid = id.split("_")[1];
		$("#radioby_" + radioid).attr("checked","checked");
	});
	
	$("#movepichide").on('click',"#movegroupcommit",function(){
		var val=$('input:radio[name="movegroupradio"]:checked').val();
		if(typeof(val) == "undefined"){
			alert("请选择分组");
			return;
		}
		var id = $('input:radio[name="movegroupradio"]:checked').attr('id');
		var groupid = id.split("_")[1];
		var result = $("input[name='checklist']:checked");
		var picidarr = "";
		for(var i = 0; i < result.length; i++){
			if(i == result.length - 1){
				picidarr += $(result[i]).attr("id").split("_")[1];
			}else{
				picidarr += $(result[i]).attr("id").split("_")[1] + ",";
			}
		}
		$.ajax({
			url:"webapi13008.json",
			data:{
				groupid:groupid,
				picidarr:picidarr
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				if(info.recode == "000000"){
					var id = $("#dataTid").data("dirname");
					window.location.href=url.split("?")[0] + "?" + id;
				}else{
					window.location.href=window.location.href;
				}
				
			},
			error:function(){
				$("#movepichide").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});

	$("#movepicbyone").on('click',"#movegroupcommit",function(){
		var val=$('input:radio[name="movegroupradio"]:checked').val();
		if(typeof(val) == "undefined"){
			alert("请选择分组");
			return;
		}
		var id = $('input:radio[name="movegroupradio"]:checked').attr('id');
		var groupid = id.split("_")[1];
		var picidarr = allpicid;
		$.ajax({
			url:"webapi13008.json",
			data:{
				groupid:groupid,
				picidarr:picidarr
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				if(info.recode == "000000"){
					var id = $("#dataTid").data("dirname");
					window.location.href=url.split("?")[0] + "?" + id;
				}else{
					//alert(info.msg);
					window.location.href=window.location.href;
				}
				
			},
			error:function(){
				$("#movepichide").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	
	$("#checkall").click(function(){
		if ($("#checkall").attr("checked")) {  
			$("input[name='checklist']").attr("checked",true);
		} else {  
			$("input[name='checklist']").attr("checked",false);
		}  
	});

	$('body').on('click',"input[type='checkbox']",function(){
		if($("input[name='checklist']:checked").length <= 0){
			$("#movepicgroup").attr({"disabled":"disabled"});
			$("#deletepic").attr({"disabled":"disabled"});
			$("#movepicgroup").removeClass("newlanmulidiv");
			$("#deletepic").removeClass("newlanmulidiv");
			$("#movepicgroup").addClass("oldlanmulidiv");
			$("#deletepic").addClass("oldlanmulidiv");
		}else{
			$("#movepicgroup").removeAttr("disabled");
			$("#deletepic").removeAttr("disabled");
			$("#movepicgroup").removeClass("oldlanmulidiv");
			$("#deletepic").removeClass("oldlanmulidiv");
			$("#movepicgroup").addClass("newlanmulidiv");
			$("#deletepic").addClass("newlanmulidiv");
		}
	});

	$('#deletepic').click(function(){
		$("#newgroupdiv").addClass("hide");
		$("#renamediv").addClass("hide");
		$("#deletediv").addClass("hide");
		$("#movepichide").addClass("hide");
		$("#editpicnamediv").addClass("hide");
		$("#movepicbyone").addClass("hide");
		$("#deletepicbyone").addClass("hide");
		$("#deletepichide").removeClass("hide");
	});

	$("#cancledeletepicall").click(function(){
		$("#deletepichide").addClass("hide");
	});

	$("#deletepicall").click(function(){
		var result = $("input[name='checklist']:checked");
		var idarr = "";
		for(var i = 0; i < result.length; i++){
			if(i == result.length - 1){
				idarr += $(result[i]).attr("id").split("_")[1];
			}else{
				idarr += $(result[i]).attr("id").split("_")[1] + ",";
			}
		}
		$.ajax({
			url:"webapi13006.json",
			data:{
				idarr:idarr
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var id = $("#dataTid").data("dirname");
				window.location.href=url.split("?")[0] + "?" + id;
			},
			error:function(){
				$("#deletepichide").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	
	$("#creategroup").click(function(){
		var groupname = $("#groupname").val();
		if(groupname == "" || groupname.length >6){
			alert("分组名字为 1-6个字符");
			return;
		}
		$.ajax({
			url:"webapi13001.json",
			data:{
				groupname:groupname
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var id = $("#dataTid").data("dirname");
				window.location.href=url.split("?")[0] + "?" + id;
			},
			error:function(){
				$("#newgroupdiv").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	$("#rename").click(function(){
		$("#newgroupdiv").addClass("hide");
		$("#deletepichide").addClass("hide");
		$("#deletediv").addClass("hide");
		$("#movepichide").addClass("hide");
		$("#editpicnamediv").addClass("hide");
		$("#movepicbyone").addClass("hide");
		$("#deletepicbyone").addClass("hide");
		$("#renamediv").removeClass("hide");
		var id = $(".stylepchange").attr("id");
		var name = $("#" + id).text();
		$("#renameinput").val(name.split("(")[0]);
		$("#renameinput").focus();
		$("#renameinput").select();
	});
	$("p").click(function(){
		var id = $(this).attr('id');
		if(id == "newgroup"){
			return;
		}
		$("p").removeClass("stylepchange");
		$("#" + id).addClass("stylepchange");
		var groupname = $("#" + id).text();
		var aa = groupname.split("(");
		if(id == initid){
			$(".changegroupname").addClass("hide");
			$("#changegroupname").html(aa[0]);
			$(".guize").css("margin","15px 0 0 120px");
		}else{
			$("#changegroupname").html(aa[0]);
			$(".changegroupname").removeClass("hide");
			$(".guize").css("margin","15px 0 0 10px");
		}
		dirname = $(".stylepchange").attr("id");
		$("#dataTid").data("dirname", dirname);
		initdiyUpload(dirname);
		getGroupInfo("#movepichide","radio_");
	});

	$("#updategroupname").click(function(){
		var groupname = $("#renameinput").val();
		var id = $(".stylepchange").attr("id");
		if(groupname == "" || groupname.length > 6){
			alert("分组名字为 1-6个字符");
			return;
		}
		$.ajax({
			url:"webapi13004.json",
			data:{
				id:id,
				groupname:groupname
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				if(info.recode == "000000"){
					var id = $("#dataTid").data("dirname");
					window.location.href = url.split("?")[0] + "?" + id;
				}else{
					$("#renamediv").addClass("hide");
					//alert(info.msg);
					window.location.href = window.location.href;
				}
			},
			error:function(){
				$("#renamediv").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});

	$("#completedeletegroup").click(function(){
		var id = $(".stylepchange").attr("id");
		$.ajax({
			url:"webapi13005.json",
			data:{
				id:id
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				if(info.recode == "000000"){
					window.location.href = url.split("?")[0] + "?" + initid;
				}else{
					$("#deletediv").addClass("hide");
					//alert(info.msg);
					window.location.href = window.location.href;
				}
			},
			error:function(){
				$("#deletediv").addClass("hide");
				alert("系统错误，请联系管理员");
			}
		});
	});
	var id = $("#dataTid").data("dirname");
	initdiyUpload(id);
	getImageInfoByGroup(id,page,"12");
	getGroupInfo("#movepichide","radio_");
	initPage(id);
	
});
function getGroupInfo(htmlid,radioid){
	$.ajax({
		url:"webapi13007.json",
		data:{
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var recode = info.recode;
			var result;
			if(recode != "000000"){
				//alert(info.msg);
				window.location.href = window.location.href;
			}else{
				var content = "";
				result = info.result;
				var id = $(".stylepchange").attr("id");
				if(result.length == 1){
					content = content + '<div class="nogroupinfo">没有分组信息，请先创建分组</div>';
				}else{
					for(var i = 0; i < result.length; i++){
						if(result[i].groupid == id){
							continue;
						}
						content = content + '<div class="radiodivstyle"><input type="radio" class="radiostyle" name="movegroupradio" id="' + radioid + result[i].groupid + '"/><label class="radiolable" id="radiolable_' + result[i].groupid + '">' + result[i].groupname + '</label></div>';
					}
				}
				content = content + '<div class="movegroupbuttonstyle" id="movegroupbuttonstyle"><button class="buttonstyle1" id="movegroupcommit">确定</button>';
				content = content + '<button class="buttonstyle2" id="movegroupcancle">取消</button><div/>';
				$(htmlid).html(content);
			}
		},
		error:function(){
			alert("系统错误，请联系管理员");
		}
	});
}

function initdiyUpload(groupid){
	$('#test').diyUpload({
		url:'webapi13002.do?dirname=' + groupid,
		success:function( data ) {
			console.info( data );
			window.location.href = url.split("?")[0] + "?" + groupid;
		},
		error:function( err ) {
			console.info( err );	
		}
	});
}

function getImageInfoByGroup(groupid,page,num){
	$.ajax({
		url:"webapi13003.json",
		data:{
			groupid:groupid,
			page:page,
			num:num
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var recode = info.recode;
			//var result;
			if(recode != "000000"){
				//alert(info.msg);
				window.location.href = window.location.href;
			}else{
				$("#picdiv").empty();
				allresult = info.result;
				var content = ""; 
				if(allresult.length == 0){
					content = content + '<div class="nopicstyle">该分组暂时没有图片素材</div>';
				}else{
					for(var i = 0; i < allresult.length; i++){
						content = content + '<div class="picstyle">';
						content = content + '<img src="' + allresult[i].picurl + '" />';
						if(allresult[i].realname.length > 10){
							var name  = allresult[i].realname.substring(0,10) + "...";
							content = content + '<input type="checkbox" name="checklist" class="piccheckbox" id="input_' + allresult[i].picid + '"/>' + name;
						}else{
							content = content + '<input type="checkbox" name="checklist" class="piccheckbox" id="input_' + allresult[i].picid + '"/>' + allresult[i].realname;
						}
						content = content + '<div class="picedit">';
						content = content +	'<img class="edit" onclick="editPicName(' + i + ')" src="scripts/foddermanager/image/edit1.png" id="edit_pic_' + allresult[i].picid + '"/>';
						content = content +	'<img class="switch" onclick="movePivByone(' + i + ')" src="scripts/foddermanager/image/switch1.png" id="move_pic_' + allresult[i].picid + '"/>';
						content = content +	'<img class="del" onclick="deletePicByone(' + i + ')" src="scripts/foddermanager/image/del1.png" id="del_pic_' + allresult[i].picid + '"/>';
						content = content + '</div>';
						content = content + '</div>';
					}
				}
				$("#picdiv").html(content);
			}
		},
		error:function(){
			alert("系统错误，请联系管理员");
		}
	});
}

function selectPicCountByGroup(groupid){
	page = 0;
	getImageInfoByGroup(groupid,"0","12");
	initPage(groupid);
}

function editPicName(num){
	$("#movepichide").addClass("hide");
	$("#deletepichide").addClass("hide");
	$("#newgroupdiv").addClass("hide");
	$("#renamediv").addClass("hide");
	$("#deletediv").addClass("hide");
	$("#movepicbyone").addClass("hide");
	$("#deletepicbyone").addClass("hide");
	allpicid = allresult[num].picid
	var obj = $("#edit_pic_" + allpicid);
	var offset = obj.offset();   
	var left = offset.left;
	var top = offset.top;
	$("#editpicnamediv").css({position: "absolute",'top':top + 20,'left':left - 120,'z-index':99999}); 
	$("#editpicnamediv").removeClass('hide');
	var oldname = allresult[num].realname;
	$("#renamepicname").val(oldname);
	$("#renamepicname").select();
}
function movePivByone(num){
	$("#movepichide").addClass("hide");
	$("#deletepichide").addClass("hide");
	$("#newgroupdiv").addClass("hide");
	$("#renamediv").addClass("hide");
	$("#deletediv").addClass("hide");
	$("#editpicnamediv").addClass("hide");
	$("#deletepicbyone").addClass("hide");
	getGroupInfo("#movepicbyone","radioby_");
	allpicid = allresult[num].picid
	var obj = $("#move_pic_" + allpicid);
	var offset = obj.offset();   
	var left = offset.left;
	var top = offset.top;
	$("#movepicbyone").css({position: "absolute",'top':top + 25,'left':left - 130,'z-index':99999}); 
	$("#movepicbyone").removeClass('hide');
}
function deletePicByone(num){
	$("#movepichide").addClass("hide");
	$("#deletepichide").addClass("hide");
	$("#newgroupdiv").addClass("hide");
	$("#renamediv").addClass("hide");
	$("#deletediv").addClass("hide");
	$("#editpicnamediv").addClass("hide");
	$("#movepicbyone").addClass("hide");
	allpicid = allresult[num].picid
	var obj = $("#del_pic_" + allpicid);
	var offset = obj.offset();   
	var left = offset.left;
	var top = offset.top;
	$("#deletepicbyone").css({position: "absolute",'top':top + 25,'left':left - 130,'z-index':99999}); 
	$("#deletepicbyone").removeClass('hide');
}
function initPage(groupid){
	$.ajax({
		url:"webapi13010.json",
		data:{
			groupid:groupid
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			if(info.recode == "000000"){
				allpage = info.page
				if(allpage > 1){
					$("#allpagenum").text(allpage);
					page = parseInt(page) + 1;
					$("#currentpagenum").text(page);
					$("#allcurrentpage").removeClass("hide");
				}else{
					$("#allcurrentpage").addClass("hide");
				}
				pageLeftRight();
			}else{
				//alert(info.msg);
				window.location.href = window.location.href;
			}
		},
		error:function(){
			alert("系统错误，请联系管理员");
		}
	});
}
function pageLeftRight(){
	if(page == allpage){
		$("#currentpageright").addClass("hide");
		$("#currentpageleft").removeClass("hide");
	}else if(page == 1){
		$("#currentpageleft").addClass("hide");
		$("#currentpageright").removeClass("hide");
	}else{
		$("#currentpageleft").removeClass("hide");
		$("#currentpageright").removeClass("hide");
	}
}

function getAlt(id,name){
	var offset = $("#" + id).offset();   
	var left = offset.left;
	var top = offset.top;
	$("#altpositon").css({position: "absolute",'top':top-30,'left':left-20,'z-index':99999}); 
	$("#altpositon").html(name);
	$("#altpositon").removeClass('hide');
}