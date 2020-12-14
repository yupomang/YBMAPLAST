//渠道管理 - 消息通知通讯录
//xingmc 
//2016年8月18日
/**
 * Modified by M on 2016/9/3.
 */
var user = top.userInfo;
var pageSizeTemp = 10;
//初始化分页信息
var appm = {
	pager:null,
	tableObj:null,
	tableObj2:null,
	createPager: function(){
		var _this = this;
		_this.pager = pages({
            el: "#channael-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
				pageSizeTemp = pageSize;
                init(false,pageIndex,pageSize,$('#centerList').val());
            }
        });
	},
	customerName:'',  //客户名称
	channelName:'',   //渠道名称
};


var centeridObj = [];
var typeObj = [];

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
	getCustomer();
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
	
	//获取监控主题 复选框
	$.ajax({
		type:'post',
		url:'./webapi03905.json',
		data:{},
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			var checkboxHtml = '';
			for(var i=0;i<data.rows.length;i++){
				checkboxHtml += '<label><input type="checkbox" name="monitorTheme" value="'+data.rows[i].type+'"> '+data.rows[i].message+'</label>　';
			};
			typeObj = data.rows;
			$('.monitorThemeWrap').html(checkboxHtml);
		}
	});
	
	
}

// 获取当前中心能够获取到的客户名称
function getCustomer () {
	//获取应用名称
	$.ajax({
		type:'POST',
		url:'./ptl40003Qry.json',
		data:{
			'centerid': user.centerid
		},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode == '000000'){
				window.customerList = data.rows;
				var tempHtml = '<option value="' + user.centerid + '">请选择</option>';
				for(var i =0;i<data.rows.length;i++){
					if(data.rows[i].validflag == "1") {
						tempHtml += '<option value="'+data.rows[i].centerid+'">'+data.rows[i].centername+'</option>';
					}
				}
				$('#centerList').html(tempHtml);
				$('#centerList').off().on("change", function () {
					parent.Common.loading(true);
					init(true, 1,pageSizeTemp, $(this).val());
				});
				init(true,1,pageSizeTemp,user.centerid);
			}
			parent.Common.loading(false);
		},
		error:function(){
			errMsg();
		}
	});
}

//init page
function init(changer,pageIndex,pageSize, centerid){
	$.ajax({
		type:'POST',
		url:'./webapi04704.json',
		data:{'centerid': centerid,'page':pageIndex,'rows':pageSize},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			pageData = data;
			if( appm.tableObj != null){
				appm.tableObj.load(data.rows);
			} else {
				//画表格头部mi041
				var cols = [
					{ title:'序号', name:'communicationid' ,width:40, align:'center',renderer: function(val,item,index){
						return index +1;
					} },
					{ title:'客户名称', name:'centerid' ,width:236, align:'center', renderer: function (val, item) {
						var temp = '';
						customerList.forEach(function (customer) {
							if(val == customer.centerid) {
								temp = customer.centername;
							}
						});
						return temp;
					} },
					{ title:'姓名', name:'name' ,width:100, align:'center' },
					{ title:'手机号', name:'phone' ,width:160, align:'center' },
					{ title:'部门', name:'department' ,width:120, align:'center' },
					{ title:'职位', name:'position' ,width:106, align:'center' },
					{ title:'监控主题描述', name:'controlid' ,width:220, align:'center',renderer: function(val,item,index){
						var cidArrText = [],
							controlid = item.controlid.split(',');
						for(var i=0;i<typeObj.length;i++){
							for(var j=0;j<controlid.length;j++){
								if(typeObj[i].type == controlid[j]){
									cidArrText.push(typeObj[i].message);
								}
							}
						};
						return cidArrText.join('、').toString();
					} },

				];
				appm.tableObj = $('#channelMailListTable').mmGrid({
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
			parent.Common.loading(false);
		},
		error:function(){
			errMsg();
		}
	});
}


//清除表单内容
function clearForm(){
	$('#name').attr('value','');
	$('#phone').attr('value','');
	$('#position').attr('value','');
	$('#department').attr('value','');
	$('.monitorThemeWrap input').attr('checked',false);
}

//点击添加
function create(){
	appManage.isAdd = true;
	appManage.clearForm();
	setTimeout(function(){
		var createHTML = $('.orgManage-popup-edit-container').html();
		parent.Common.popupShow(createHTML);
	},50);
	$('#addTitltSpan').text('消息通知通讯录 - 增加');
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
	$('#name').attr('value',selectedItem[0].name);
	$('#phone').attr('value',selectedItem[0].phone);
	$('#position').attr('value',selectedItem[0].position);
	$('#department').attr('value',selectedItem[0].department);
	var mobj = selectedItem[0].controlid.split(',');
	for(var i=0;i<mobj.length;i++){
		$('.monitorThemeWrap input[value="'+mobj[i]+'"]').attr('checked',true);
	}
	
	$('#addTitltSpan').text('消息通知通讯录 - 修改');
	//按钮显示隐藏
	$('.orgManage-popup-btns-ok:eq(0)').hide();
	$('.orgManage-popup-btns-ok:eq(1)').show();	
	parent.Common.loading(false);
	
}



var setForm = {
	getMtValut: function(){
		var moniarr = [];
		var inputobj = parent.$('.monitorThemeWrap input[name="monitorTheme"]:checked');
		inputobj.each(function(index,element){
			moniarr.push($(this).val());
		});
		moniarr.join(',');
		return moniarr;
		
	},
	verify:function(isAdd){
		var _this = this;
		var post = true;
		var dialogErrMsg = [];
		if(parent.$('#name').val() == ''){
			post = false;
			dialogErrMsg.push('姓名');
		}
		if(parent.$('#phone').val() == ''){
			post = false;
			dialogErrMsg.push('手机号');
		} else if(!(/^1[3|4|5|7|8]\d{9}$/.test(parent.$('#phone').val()))) {
			parent.$("#popup-container").hide();
			parent.Common.dialog({
				type: "warning",
				text: "手机号格式有误，请检查后重新填写！",
				okShow: true,
				cancelShow: false,
				okText: "确定",
				ok: function () {
					parent.$("#popup-container").show();
				}
			});
			return false;
		}
		if(_this.getMtValut().length<1){
			post = false;
			dialogErrMsg.push('监控主题');
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
				'centerid':user.centerid,
				'name':parent.$('#name').val(),
				'phone':parent.$('#phone').val(),
				'position':parent.$('#position').val(),
				'department':parent.$('#department').val(),
				'controlid':_this.getMtValut().toString()
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
		url:'./webapi04701.json',
		data:createData,
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			parent.Common.loading(false);
			if(data.recode == '000000'){
				parent.$('#popup-container').hide();
				init(false,1,pageSizeTemp,$('#centerList').val());
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
		selarr.push(selected[i].communicationid);
	}
	//数组转换成字符串
	selarr = selarr.join(',');
	if(selected.length < 1){
		parent.Common.dialog({
            type: "error",
            text: "至少选中一条记录！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
	} else {
		//显示Loading
		parent.Common.loading(true);
		$.ajax({
			type:'POST',
			url:'./webapi04702.json',
			data:{ 'communicationid':selarr },
			datatype:'json',
			success:function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				if(data.recode = '000000'){
					//隐藏loading
					parent.Common.loading(false);
					init(false,1,pageSizeTemp,$('#centerList').val());
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
	function getMtVal2(){
		var moniarr = [];
		var inputobj = parent.$('.monitorThemeWrap input[name="monitorTheme"]:checked');
		inputobj.each(function(index,element){
			moniarr.push($(this).val());
		});
		moniarr.join(',');
		return moniarr;
	}
	parent.Common.loading(true);
	//执行修改操作
	var selectedItem = appm.tableObj.selectedRows();
	var itemModifyData = {
		'communicationid': selectedItem[0].communicationid,
		'centerid':user.centerid,
		'name':parent.$('#name').val(),
		'phone':parent.$('#phone').val(),
		'position':parent.$('#position').val(),
		'department':parent.$('#department').val(),
		'controlid':getMtVal2().toString()
	};
	$.ajax({
		type:'post',
		url:'./webapi04703.json',
		data:itemModifyData,
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			if(data.recode = '000000'){
				//隐藏loading
				parent.Common.loading(false);
				init(false,1,pageSizeTemp,$('#centerList').val());
				parent.$('#popup-container').hide();
				init(false,1,pageSizeTemp,$('#centerList').val());
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


//点击从系统中选择名字
function showgo(){
	//切换显示
	parent.$('#popup-container').hide(0);
	$('.channelBox').hide(0);
	$('.getUsername').show(0);
	
	var cols2 = [
	   { title:'序号', name:'communicationid' ,width:160, align:'center',renderer: function(val,item,index){
	   		return index +1;
	   } },
	   { title:'姓名', name:'opername' ,width:170, align:'center' },
	   { title:'手机号', name:'phone' ,width:170, align:'center' },
	];
	
	$.ajax({
		type:'post',
		url:'ptl40001.json',
		data:{centerid:user.centerid},
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			appm.tableObj2 = $('#nameTable').mmGrid({
				multiSelect: true,// 多选
				checkCol: true, // 选框列
				width:'600px',
				height: 'auto',
				cols: cols2,
				items: data.rows,
				loadingText: "loading...",
				noDataText: "暂无数据。",
				loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
				sortable: false
			});
		},
		error: function(){
			errMsg();
		}
	});
}


//不现实了
function showback(){
	parent.$('#popup-container').show(0);
	$('.channelBox').show(0);
	$('.getUsername').hide(0);
}

function getUser(){
	var selectedItem = appm.tableObj2.selectedRows();
	if(selectedItem.length < 1){
		parent.Common.dialog({
			type: "warning",
            text: "请至少选择一条信息！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
		});
		return ;
	} else if(selectedItem.length > 1){
		parent.Common.dialog({
			type: "warning",
            text: "最多只能选择一条信息！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
		});
		return ;
	} else {
		postName();
	}
};

function postName(){
	var selectedItem = appm.tableObj2.selectedRows();
	parent.$('#name').attr('value',selectedItem[0].opername);
	parent.$('#phone').attr('value',selectedItem[0].phone);
	parent.$('#popup-container').show(0);
	$('.channelBox').show(0);
	$('.getUsername').hide(0);
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


$(function(){
	//初始化页面
	appm.createPager();
	getAll();
});



