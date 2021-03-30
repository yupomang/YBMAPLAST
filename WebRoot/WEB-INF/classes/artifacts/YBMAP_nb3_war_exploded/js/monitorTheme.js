//渠道管理 - 渠道限额控制
//xingmc 
//2016年8月19日
var user = top.userInfo;
var pageSizeTemp = 10;
//初始化分页信息
var appm = {
	pager:null,
	tableObj:null,
	createPager: function(){
		var _this = this;
		_this.pager = pages({
            el: "#channael-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
				pageSizeTemp = pageSize;
                init(false,pageIndex, pageSize);
            }
        });
	},
	customerName:'',  //客户名称
	channelName:'',   //渠道名称
};


var centeridObj = [];

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

//第一次加载的时候，从数据库读取客户名称
function getAll(){
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
				var customerOptions = '<option value="">请选择</option>';
				for(var i = 0;i<data.mi001list.length;i++){
					if(user.centerid != '00000000'){
						if(user.centerid == data.mi001list[i].centerid){
							customerOptions = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>'
						}
					} else {
						customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
					}
					centeridObj.push(data.mi001list[i]);
				}
			}
		},
		error:function(){
			errMsg();
		}
	});
	
}


//画表格头部mi041
var cols = [
   { title:'序号', name:'controlid' ,width:130, align:'center',renderer: function(val,item,index){
   		return index +1;
   } },
   { title:'监控主题编码', name:'type' ,width:170, align:'center' },
   { title:'监控主题描述', name:'message' ,width:170, align:'center' },
   { title:'',name:'validflag',width:550,align:'center',renderer: function(val,item,index){
   		return '';
   }}
];

//init page
function init(changer,pageIndex,pageSize){
	$.ajax({
		type:'POST',
		url:'./webapi03904.json',
		data:{'centerid':user.centerid,'page':pageIndex,'rows':pageSize},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			pageData = data;
			if( appm.tableObj != null){
				appm.tableObj.load(data.rows);
			} else {
				appm.tableObj = $('#monitorThemeTable').mmGrid({
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
	$('#moniDescribe').attr('value','');
	$('#moniCode').attr('value','');
}

//点击添加
function create(){
	appManage.isAdd = true;
	appManage.clearForm();
	setTimeout(function(){
		var createHTML = $('.orgManage-popup-edit-container').html();
		parent.Common.popupShow(createHTML);
	},50);
	$('#addTitltSpan').text('监控主题 - 增加');
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
	},50);
	clearForm();
	
	//回显
	$('#moniDescribe').attr('value',selectedItem[0].message);
	$('#moniCode').attr('value',selectedItem[0].type);
	
	$('#addTitltSpan').text('监控主题 - 修改');
	//按钮显示隐藏
	$('.orgManage-popup-btns-ok:eq(0)').hide();
	$('.orgManage-popup-btns-ok:eq(1)').show();	
	parent.Common.loading(false);
	
}



var setForm = {
	verify:function(isAdd){
		var _this = this;
		var post = true;
		var dialogErrMsg = [];
		if(parent.$('#moniDescribe').val() == ''){
			post = false;
			dialogErrMsg.push('监控主题描述');
		}
		if(parent.$('#moniCode').val() == ''){
			post = false;
			dialogErrMsg.push('监控主题编码');
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
		} else {
			parent.Common.loading(true);
			
			var postData = {
				'message':parent.$('#moniDescribe').val(),
				'type':parent.$('#moniCode').val(),
			}
			if(isAdd){
				createData(postData);
			} else {
				itemModify();
			}
		}
	},
}

//添加
function createData(createData){
	$.ajax({
		type:'POST',
		url:'./webapi03901.json',
		data:createData,
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			parent.Common.loading(false);
			if(data.recode == '000000'){
				parent.$('#popup-container').hide();
				init(false,1,pageSizeTemp);
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
		selarr.push(selected[i].controlid);
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
			url:'./webapi03902.json',
			data:{ 'controlid':selarr },
			datatype:'json',
			success:function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				if(data.recode = '000000'){
					//隐藏loading
					parent.Common.loading(false);
					init(false,1,pageSizeTemp);
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
		'controlid':selectedItem[0].controlid,
		'message': parent.$('#moniDescribe').val(),
		'type': parent.$('#moniCode').val(),
	};
	$.ajax({
		type:'post',
		url:'./webapi03903.json',
		data:itemModifyData,
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode = '000000'){
				//隐藏loading
				parent.Common.loading(false);
				init(false,1,pageSizeTemp);
				parent.$('#popup-container').hide();
				init(false,1,pageSizeTemp);
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
			return ;
		} else {
			//创建修改html
			modify();
		}
	});
	$('#orgManage-btn-del').off().on('click',function(){
		var selectedItem = appm.tableObj.selectedRows();
		if(selectedItem.length == 0){
			parent.Common.delNone();
			return ;
		} else {
			deleteConfirm();
		}
		
	});
	
}

$(function(){
	//初始化页面
	appm.createPager();
	init(true,1,pageSizeTemp);
	getAll();
});



