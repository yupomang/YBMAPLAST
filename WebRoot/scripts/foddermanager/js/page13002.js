var dirname;
var page = 0;
var allpage;
var allresult;
var Rpicurl;
var sysname;
$(function(){
	$("#bagpic").click(function(){
		$("#mainbodyhide").addClass("hide");
		resetR();
	});
	
	$("#floatyes").click(function(){
		resetR();
	});
	
	$("#tthideendchildspancommit").click(function(){
		var picurl = $("#savepicurl").data("savepicurl");
		$.ajax({
			url:"webapi13012.json",
			data:{
				sysname:sysname,
				realpicurl:Rpicurl,
				savepicurl:picurl
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				if(info.recode != "000000"){
					alert(info.msg);
					window.location.href = window.location.href; 
				}else{
					resetR();
					$("#mainbodyhide").addClass("hide");
					$("#repicurl").data("repicurl", info.imgUrl);
					$("#serverimg").data("serverimg", info.serverimg);
					commenfunc();
				}
			},
			error:function(){
				alert("系统错误，请联系管理员");
			}
		});
	});
	
	$("#tthideendchildspancancle").click(function(){
		$("#mainbodyhide").addClass("hide");
		resetR();
	});
	
	$("#currentpageleft").click(function(){
		$("#grouppicdiv").empty();
		resetR();
		page = parseInt(page) - 1;
		pageLeftRight();
		var id = $(".ttstylepchange").attr("id");
		getImageInfoByGroup(id,page-1,"10");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		//document.documentElement.scrollTop = document.body.scrollTop =0;
	});
	
	$("#currentpageright").click(function(){
		$("#grouppicdiv").empty();
		resetR();
		page = parseInt(page) + 1;
		pageLeftRight();
		var id = $(".ttstylepchange").attr("id");
		getImageInfoByGroup(id,page-1,"10");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		//document.documentElement.scrollTop = document.body.scrollTop =0;
	});
	
	$("#gotonewpage").click(function(){
		var num = $("#systempagenum").val();
		if($.trim(num) == ""){
			return false;
		}
		var reg = new RegExp("^[0-9]*$");  
		if(!reg.test(num)){
			alert("请输入正确的页码");
			return false;
		}
		if(num > allpage){
			alert("请输入正确的页码");
			return false;
		}
		$("#grouppicdiv").empty();
		resetR();
		page = num;
		pageLeftRight();
		var id = $(".ttstylepchange").attr("id");
		getImageInfoByGroup(id,page-1,"10");
		$("#currentpagenum").text(page);
		$("#allcurrentpage").removeClass("hide");
		$("#systempagenum").val("");
		//document.documentElement.scrollTop = document.body.scrollTop =0;
	});
	
	$("#ttleft").on('click',"p",function(){
		$("#grouppicdiv").empty();
		resetR();
		var id = $(this).attr('id');
		$("p").removeClass("ttstylepchange");
		$("#" + id).addClass("ttstylepchange");
		page = 0;
		getImageInfoByGroup(id,"0","10");
		initdiyUpload(id);
		initPage(id);
	});
	getGrouInfo("");
});
//查询分组信息
function getGrouInfo(groupid){
	page = 0;
	$.ajax({
		url:"page13001.json",
		data:{
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var arr = info.groupList;
			var content = "";
			for(var i = 0; i < arr.length; i++){
				if(groupid == ""){
					if(i == 0){
						content = content + '<p class="ttstylep ttstylepchange" id="'+ arr[i].groupid + '">' + arr[i].groupname + '(' + arr[i].jl + ')</p>';
					}else{
						content = content + '<p class="ttstylep" id="'+ arr[i].groupid + '">' + arr[i].groupname + '(' + arr[i].jl + ')</p>';
					}
				}else{
					if(arr[i].groupid == groupid){
						content = content + '<p class="ttstylep ttstylepchange" id="'+ arr[i].groupid + '">' + arr[i].groupname + '(' + arr[i].jl + ')</p>';
					}else{
						content = content + '<p class="ttstylep" id="'+ arr[i].groupid + '">' + arr[i].groupname + '(' + arr[i].jl + ')</p>';
					}
				}
			}
			$("#ttleft").html(content);
			dirname = $(".ttstylepchange").attr("id");
			$("#dataTid").data("dirname", dirname);
			initdiyUpload(dirname);
			getImageInfoByGroup(dirname,page,"10");
			initPage(dirname);
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
			getGrouInfo(groupid);
		},
		error:function( err ) {
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
			if(recode != "000000"){
				window.location.href = window.location.href;
			}else{
				$("#grouppicdiv").empty();
				allresult = info.result;
				var content = ""; 
				if(allresult.length == 0){
					content = content + '<div class="groupnopic">该分组暂时没有图片素材</div>';
				}else{
					for(var i = 0; i < allresult.length; i++){
						content = content + '<div class="grouppicstyle" onclick="selectPic(' + i + ')" id="edit_' + allresult[i].picid + '">';
						content = content + '<img src="' + allresult[i].picurl + '" />';
						if(allresult[i].realname.length > 8){
							var name  = allresult[i].realname.substring(0,8) + "...";
							content = content + '<div>' + name + '</div>';
						}else{
							content = content + allresult[i].realname;
						}
						content = content + '</div>';
					}
				}
				$("#grouppicdiv").html(content);
			}
		},
		error:function(){
			alert("系统错误，请联系管理员");
		}
	});
}

function initPage(groupid){
	$.ajax({
		url:"webapi13011.json",
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

function selectPic(num){
	allpicid = allresult[num].picid;
	Rpicurl = allresult[num].picurl;
	sysname = allresult[num].sysname;
	var obj = $("#edit_" + allpicid);
	var offset = obj.offset();   
	var left = offset.left;
	var top = offset.top;
	$("#floatyes").css({position: "absolute",'top':top,'left':left,'z-index':99999}); 
	$("#floatyes").removeClass('hide');
	$("#selectpicnum").text(1);
	$('#tthideendchildspancommit').removeAttr("disabled"); 
	$("#tthideendchildspancommit").addClass("buttoncolor");
}

function resetR(){
	$("#floatyes").addClass("hide");
	$("#tthideendchildspancommit").removeClass("buttoncolor");
	$('#tthideendchildspancommit').attr('disabled',"true");
	$("#selectpicnum").text(0);
}