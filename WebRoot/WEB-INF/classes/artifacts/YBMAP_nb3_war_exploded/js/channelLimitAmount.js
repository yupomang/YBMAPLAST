//渠道管理 - 渠道限额控制
//xingmc 
//2016年8月17日
/**
 * Modified by M on 2016/9/2.
 */
var user = top.userInfo;
var pageSizeTemp = 10;
var modifyAppTemp = '';
//初始化分页信息
var appm = {
	pager:null,
	tableObj:null,
	createPager: function(){
		var _this = this;
		_this.pager = pages({
            el: "#channael-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
				pageSizeTemp = pageSize;
                //init(false,pageIndex, pageSize);
				setForm.search(pageIndex, false);
            }
        });
	},
	customerName:'',  //客户名称
	channelName:'',   //渠道名称
};


//所有的错误信息
function errMsg (){
	parent.Common.dialog({
		type:'error',
		text:'网络错误！请刷新页面或联系管理员！',
		isShow:true,
		cancelShow:true,
		okText:'确定',
		ok:function(){
			
		}
	});
}

var appManage = new Vue({
	el:'#cam',
	data:{
		isAdd:true,
		//title:['应用管理 - 增加','应用管理 - 修改'],
		form:{
			customerName:'',	//客户名称
			channelName:'',		//渠道名称
			appName:'',			//应用名称
			isUsing:'',			//是否启用
			serviceStart:'',	//服务开始时间
			serviceEnd:'',		//服务结束时间
		},
		authForm:{
			appid:'',
			appkey:'',
			appaes:'',
			apptoken:'',
			apptokentext:'',
			tokendate:''
		}
	},
	methods:{
		clearForm: function(){
			var _this = this;
			_this.form.customerName = '';
			_this.form.channelName  = '';
			_this.form.appName		= '';
			_this.form.isUsing		= '';
			_this.serviceStart		= '';
			_this.serviceEnd		= '';
		},
		clearAuth: function(){
			var _this = this;
			_this.authForm.appid = '';
			_this.authForm.appkey  = '';
			_this.authForm.appaes		= '';
			_this.authForm.apptoken		= '';
			_this.authForm.apptokentext		= '';
			_this.authForm.tokendate		= '';
		}
	}
});

//获取全局的中转移
var centeridObj = [];
var channelObj = [];
var appnameObj = [];
var busitypeObj = [];

var centeridData = null;
var channelData = null;
var appnameData = null;
var pageData = null;

var appNameHtml = '';
var channelHtml = '';

//第一次加载的时候，从数据库读取客户名称
function getAll(){
	parent.Common.loading(true);
	$.ajax({
		type:'POST',
		url:'./webappcomCenterId.json',
		data:{},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			centeridData = data;
			if(data.recode == '000000'){
				var customerOptions = '';
				for(var i = 0;i<data.mi001list.length;i++){
					if(user.centerid != '00000000'){
						if(user.centerid == data.mi001list[i].centerid){
							customerOptions = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
							// 获取渠道
							getChannel(data.mi001list[i].centerid, false);
						}
					} else {
						customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
						if(i == data.mi001list.length - 1) {
							// 获取渠道
							getChannel(data.mi001list[0].centerid, false);
						}
					}
					centeridObj.push(data.mi001list[i]);
				}
				$('#customerName').html(customerOptions);
				$('#searchCustomer').html(customerOptions);
				// 获取业务类型
				getType();
				// 切换的时候 发请求 去get渠道
				$('#searchCustomer').off().on("change", function () {
					getChannel($(this).val(), false);
				});
			}
		},
		error:function(){
			errMsg();
		}
	});
}

function getChannel (centerid, isPopup) {
	$.ajax({
		type:'POST',
		url:'./webappcomChannel.json',
		data: { 'centerid': centerid },
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			channelData = data;
			if(data.recode == '000000'){

				var channelOptions = '<option value="">请选择</option>';
				for(var i =0;i<data.mi007list.length;i++){
					channelOptions += '<option value="'+data.mi007list[i].itemid+'">'+data.mi007list[i].itemval+'</option>';
					channelObj.push(data.mi007list[i]);
				}
				if(isPopup) {
					parent.$('#channelName').html(channelOptions);
					getApp(centerid, '', true);
				} else {
					$('#channelName').html(channelOptions);
					$('#searchChannel').html(channelOptions);
					channelHtml = channelOptions;
				}
			}
			// 获取应用列表
			//getApp(centerid, channelObj[0].itemid, false);
			// 切换的时候 发请求 去get应用列表
			$('#searchChannel').off().on("change", function () {
				getApp(centerid, $(this).val(), false);
			});

		},
		error:function(){
			errMsg();
		}
	});
}

// 获取应用列表
function getApp(centerid, itemid, isPopup) {
	$.ajax({
		type:'POST',
		url:'./webapi04007.json',
		data:{
			'centerid': centerid,
			'channel': itemid
		},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			appnameData = data;
			if(data.recode == '000000'){
				var appnameOptions = '<option value="">请选择</option>';
				for(var i =0;i<data.applist.length;i++){
					appnameOptions += '<option value="'+data.applist[i].pid+'">'+data.applist[i].appname+'</option>';
					appnameObj.push(data.applist[i]);
				}
				if(isPopup) {
					parent.$('#appName').html(appnameOptions);
					parent.$('#appName').val(modifyAppTemp);
				} else {
					$('#appName').html(appnameOptions);
					$('#searchAppname').html(appnameOptions);
					appNameHtml = appnameOptions
				}
			}
		},
		error:function(){
			errMsg();
		}
	});
}

function getAllChannelType() {
	// 取所有渠道类型
	parent.Common.loading(true);
	$.ajax({
		type:'POST',
		url:'./webappcomChannel.json',
		data:{},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode == '000000'){
				channelObj = data.mi007list;
				init(false,1,pageSizeTemp);
			}

		},
		error:function(){
			errMsg();
		}
	});
}

// 获取业务类型
function getType() {
	$.ajax({
		type:'POST',
		url:'./getApptranstype.json',
		data:{},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode == '000000'){
				var busitypeOptions = '<option value="">请选择</option>';
				for(var i =0;i<data.mi007list.length;i++){
					if(data.mi007list[i].ismoneytype == "1") {
						busitypeOptions += '<option value="'+data.mi007list[i].itemid+'">'+data.mi007list[i].itemval+'</option>';
					}
					busitypeObj.push(data.mi007list[i]);
				}
				$('#busiType').html(busitypeOptions);
				$('#searchBusiType').html(busitypeOptions);
			}
			getAllChannelType();
			parent.Common.loading(false);
		},
		error:function(){
			errMsg();
		}
	});
}

//init page
function init(changer,pageIndex,pageSizeTemp){
	//画表格头部mi041
	var cols = [
		{title:'序号', name:'centerid' ,width:50, align:'center',renderer: function(val,item,index){
			return index+1;
		}},
		{ title:'客户名称', name:'centerid' ,width:240, align:'center',renderer: function(val,item,index){
			for(var i = 0;i<centeridObj.length;i++){
				if(item.centerid == centeridObj[i].centerid){
					return centeridObj[i].centername;
				}
			}
		} },
		{ title:'渠道类型', name:'channel' ,width:160, align:'center', renderer: function(val,item,index){
			for(var i = 0;i<channelObj.length;i++){
				if(item.channel == channelObj[i].itemid){
					return channelObj[i].itemval;
				}
			}
		} },
		{ title:'渠道应用', name:'appname' ,width:160, align:'center'},
		{ title:'业务类型', name:'buztype' ,width:180, align:'center',renderer: function(val ,item, index){
			for(var i=0;i<busitypeObj.length;i++){
				if(item.buztype == busitypeObj[i].itemid){
					return busitypeObj[i].itemval;
				}
			}
		}},
		{ title:'单日限额', name:'dayquotai' ,width:100, align:'center'},
		{ title:'单笔限额', name:'onequotai' ,width:100, align:'center'},
	];
	$.ajax({
		type:'POST',
		url:'./webapi04304.json',
		data:{'centerid':user.centerid,'page':pageIndex,'rows':pageSizeTemp},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			pageData = data;
			if( appm.tableObj != null){
				appm.tableObj.load(data.rows);
			} else {
				appm.tableObj = $('#channelLimitAmountTable').mmGrid({
					multiSelect: true,// 多选
					checkCol: true, // 选框列
					height: 'auto',
					cols: cols,
					items: data.rows,
					loadingText: "loading...",
					noDataText: "暂无数据。",
					loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
					sortable: false
				});	
			}
			
			//修改分页
			appm.pager.reset({
				itemLength:data.total,
	            pageSize:data.pageSize,
	            reset:changer?true:false
			});
			//绑定按钮事件
			btnBindEvent();
		},
		error:function(){
			errMsg();
		}
	});
}


//清除表单内容
function clearForm(){
	$('#customerName option').attr('selected',false);
	$('#channelName option').attr('selected',false);
	$('#appName option').attr('selected',false);
	$('#busiType option').attr('selected',false);
	$('#singleDay').attr('value','');
	$('#singleTime').attr('value','');
}

//点击添加
function create(){
	appManage.isAdd = true;
	appManage.clearForm();
	setTimeout(function(){
		var createHTML = $('.orgManage-popup-edit-container').html();
		parent.Common.popupShow(createHTML);
		// 切换的时候 发请求 去get渠道
		var centerid = parent.$('#customerName').val();
		parent.$('#customerName').off().on("change", function () {
			centerid = $(this).val();
			getChannel($(this).val(), true);
		});
		parent.$('#channelName').off().on("change", function () {
			getApp(centerid, $(this).val(), true);
		});
	},50);
	$('#addTitltSpan').text('渠道限额控制 - 增加');
	clearForm();
	$('.orgManage-popup-btns-ok:eq(0)').show();
	$('.orgManage-popup-btns-ok:eq(1)').hide();	
}
//点击修改
function modify(){
	appManage.isAdd = false;
	
	var selectedItem = appm.tableObj.selectedRows();
	parent.Common.loading(true);
	setTimeout(function(){
		var editHTML = $('.orgManage-popup-edit-container').html();
		parent.Common.popupShow(editHTML);
		// 切换的时候 发请求 去get渠道
		var centerid = parent.$('#customerName').val();
		parent.$('#customerName').off().on("change", function () {
			centerid = $(this).val();
			getChannel($(this).val(), true);
		});
		parent.$('#channelName').off().on("change", function () {
			getApp(centerid, $(this).val(), true);
		});
		getApp(centerid, parent.$('#channelName').val(), true);
	},50);
	clearForm();
	
	//回显
	$('#customerName option[value="'+selectedItem[0].centerid+'"]').attr('selected',true);
	$('#channelName option[value="'+selectedItem[0].channel+'"]').attr('selected',true);
	$('#appName option[value="'+selectedItem[0].pid+'"]').attr('selected',true);
	$('#busiType option[value="'+selectedItem[0].buztype+'"]').attr('selected',true);
	$('#singleDay').attr('value',selectedItem[0].dayquotai);
	$('#singleTime').attr('value',selectedItem[0].onequotai);
	modifyAppTemp = selectedItem[0].pid;
	
	$('#addTitltSpan').text('渠道限额控制 - 修改');
	//按钮显示隐藏
	$('.orgManage-popup-btns-ok:eq(0)').hide();
	$('.orgManage-popup-btns-ok:eq(1)').show();	
	parent.Common.loading(false);
	
}



var setForm = {
	verify:function(isAdd){
		var _this = this,
		 	post = true,
		 	dialogErrMsg = [],
		 	singleDay = parent.$('#singleDay').val(),
			singleTime = parent.$('#singleTime').val();
		if(parent.$('#customerName').val().length < 1){
			post = false;
			dialogErrMsg.push('客户名称');
		}
		if(parent.$('#channelName').val().length < 1){
			post = false;
			dialogErrMsg.push('渠道类型');
		}
		if(parent.$('#appName').val().length < 1){
			post = false;
			dialogErrMsg.push('渠道应用名称');
		}
		if(parent.$('#busiType').val().length < 1){
			post = false;
			dialogErrMsg.push('业务类型');
		}
		if(singleDay == ''){
			post = false;
			dialogErrMsg.push('单日累积限额');
		}
		if(singleTime == ''){
			post = false;
			dialogErrMsg.push('单笔限额');
		}
		if(!post){
			var errmsg = dialogErrMsg.join(',');
			if(dialogErrMsg.length > 1){
				errmsg+='等';
			}
			parent.$('#popup-container').hide();
			
			parent.dialogBox.type('error');
			parent.dialogBox.text(errmsg + '不能为空');
			parent.dialogBox.ok(function(){
				parent.$('#popup-container').show();
			});
			parent.dialogBox.show();
			return;
		} 
		var pattern =/^[0-9]+([.]\d{1,2})?$/;

		if(!pattern.test(singleDay)){
			parent.$('#popup-container').hide();
			parent.dialogBox.type('error');
			parent.dialogBox.text('请正确输入单日累积限额！');
			parent.dialogBox.ok(function(){
				parent.$('#popup-container').show();
			});
			parent.dialogBox.show();
			return;
		}
		if(!pattern.test(singleTime)){
			parent.$('#popup-container').hide();
			parent.dialogBox.type('error');
			parent.dialogBox.text('请正确输入单笔限额！');
			parent.dialogBox.ok(function(){
				parent.$('#popup-container').show();
			});
			parent.dialogBox.show();
			return;
		}
		
		parent.Common.loading(true);
		var postData = {
			centerid:parent.$('#customerName').val(),
			channel:parent.$('#channelName').val(),
			pid:parent.$('#appName').val(),
			buztype:parent.$('#busiType').val(),
			dayquotai:parent.$('#singleDay').val(),
			onequotai:parent.$('#singleTime').val(),
		}
		if(isAdd){
			createData(postData);
		} else {
			itemModify();
		}
	},
	search: function(pageIndex, resetBool){
		var searchData = {
			'centerid': $("#searchCustomer").val(),
			'channel':$('#searchChannel').val(),
			'pid':$('#searchAppname').val(),
			'buztype':$('#searchBusiType').val(),
			'page': pageIndex,
			'rows': pageSizeTemp
		};
		parent.Common.loading(true);
		$.ajax({
			type:'post',
			url:'./webapi04304.json',
			data: searchData,
			datatype:'json',
			success: function(data){
				parent.Common.loading(false);
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				appm.tableObj.load(data.rows);
				//修改分页
				appm.pager.reset({
					itemLength:data.total,
					pageSize:data.pageSize,
					reset: resetBool
				});
			},
		});
	},
}

//添加
function createData(createData){
	$.ajax({
		type:'POST',
		url:'./webapi04301.json',
		data:createData,
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			parent.Common.loading(false);
			if(data.recode == '000000'){
				parent.$('#popup-container').hide();
				//init(false,1,pageSizeTemp);
				// 用search操作代替直接init
				setForm.search(1, true);
				parent.Common.dialog({
					type:'success',
					text:data.msg,
					okShow:true,
					cancelShow:false,
					okText:'确定',
					ok:function(){
						//添加成功之后重新执行查询操作
					}
				});
			} else {
				parent.$('#popup-container').hide();
				parent.Common.dialog({
					type:'error',
					text:data.msg,
					okShow:true,
					cancelShow:false,
					okText:'确定',
					ok:function(){}
				});
			}
		},
		error:function(){
			errMsg();
		}
	});
}



//确认是否删除事件
function deleteConfirm (){
	var selected = appm.tableObj.selectedRows();
	if(selected != 'undefined'){
		parent.Common.dialog({
            type: "warning",
            text: "确定要删除选中项吗？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            cancelText:'取消',
            ok: function () {
            	itemDelete();
            }
        });
	}
}

//删除事件
function itemDelete(){
	var selected = appm.tableObj.selectedRows();
	var selarr = [];
	//获取选中的数组
	for(var i = 0 ;i < selected.length ;i++){
		selarr.push(selected[i].quotaid);
	}
	//数组转换成字符串
	selarr = selarr.join(',');
	if(selected.length < 1){
		parent.Common.delNone();
	} else {
		//显示Loading
		parent.Common.loading(true);
		$.ajax({
			type:'POST',
			url:'./webapi04302.json',
			data:{ 'quotaid':selarr },
			datatype:'json',
			success:function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				if(data.recode = '000000'){
					//隐藏loading
					parent.Common.loading(false);
					//init(false,1,pageSizeTemp);
					// 用search操作代替直接init
					setForm.search(1, true);
				} else {
					parent.Common.loading(false);
					parent.Common.dialog({
						type:'error',
						text:data.msg,
						okShow:true,
						cancelShow:false,
						okText:'确定',
						ok:function(){
							
						}
					});
				}
			},
			error:function(){
				errMsg();
			}
		});
	}
}



//修改事件
function itemModify(){
	parent.Common.loading(true);
	//执行修改操作
	var selectedItem = appm.tableObj.selectedRows();
	var itemModifyData = {
		quotaid: selectedItem[0].quotaid,
		centerid:parent.$('#customerName').val(),
		channel:parent.$('#channelName').val(),
		pid:parent.$('#appName').val(),
		buztype:parent.$('#busiType').val(),
		dayquotai:parent.$('#singleDay').val(),
		onequotai:parent.$('#singleTime').val(),
	};
	$.ajax({
		type:'post',
		url:'./webapi04303.json',
		data:itemModifyData,
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode = '000000'){
				//隐藏loading
				parent.Common.loading(false);
				//init(false,1,pageSizeTemp);
				parent.$('#popup-container').hide();
				//init(false,1,pageSizeTemp);
				// 用search操作代替直接init
				setForm.search(1, true);
				parent.Common.dialog({
					type:'success',
					text:data.msg,
					okShow:true,
					cancelShow:false,
					okText:'确定',
					ok:function(){
						//添加成功之后重新执行查询操作
					}
				});
			} else {
				parent.Common.loading(false);
				parent.Common.dialog({
					type:'error',
					text:data.msg,
					okShow:true,
					cancelShow:false,
					okText:'确定',
					ok:function(){
						
					}
				});
			}
		},
		error: function(){
			errMsg();
		}
	});
	
}


//按钮绑定
function btnBindEvent (){
	$('#orgManage-btn-add').off().on('click',function(){
		create();
	});
	
	$('#orgManage-btn-modify').off().on('click',function(){
		var selectedItem = appm.tableObj.selectedRows();
		if(selectedItem.length < 1) {
			parent.Common.editNone();
			return;
		}
		if(selectedItem.length > 1){
			parent.Common.editMore();
		} else {
			//创建修改html
			modify();
		}
	});
	$('#orgManage-btn-del').off().on('click',function(){
		var selectedItem = appm.tableObj.selectedRows();
		if(selectedItem.length == 0){
			parent.Common.delNone();
		} else {
			deleteConfirm();
		}
		
	});
	
}


function getAppName(pid){
	for(var i=0;i<appnameData.applist.length;i++){
		if(pid == appnameData.applist[i].pid){
			return appnameData.applist[i].appname;
		}
	}
}


//搜索框变化的时候绑定值
function bindChange(){
	// $('#searchChannel').bind('change',function(){
	// 	var val = $(this).val();
	// 	var appnameOption='<option value="">请选择</option>';
	// 	for(var i=0;i<pageData.rows.length;i++){
	// 		if(val == pageData.rows[i].channel){
	// 			appnameOption+= '<option value="'+pageData.rows[i].pid+'">'+getAppName(pageData.rows[i].pid)+'</option>';
	// 		}
	// 		if(val == ''){
	// 			appnameOption = appNameHtml;
	// 		}
	// 	}
	// 	$('#searchAppname').html(appnameOption);
	// });
	// if($('#searchChannel').attr('value') == 'undefined'){
	// 	$('#searchAppname').bind('change',function(){
	// 		var val = $(this).val();
	// 		for(var i=0;i<appnameData.applist.length;i++){
	// 			if(val == appnameData.applist[i].pid){
	// 				appnameData.applist[i].channel
	// 				$('#searchChannel option[value="'+appnameData.applist[i].channel+'"]').attr('selected',true);
	// 			}
	// 			if(val == ''){
	// 				$('#searchChannel option:first').attr('selected',true);
	// 			}
	// 		}
	// 	});
	// }
}

$(function(){
	//初始化页面
	appm.createPager();
	init(true,1,pageSizeTemp);
	getAll();
	setTimeout(function(){
		bindChange();
	},100);
});



