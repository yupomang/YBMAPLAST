//渠道管理 - 渠道限额控制
//xingmc 
//2016年8月17日
var user = top.userInfo;
var pageSizeTemp = 10,
	pageSizeTemp2 = 10;
//初始化分页信息
var appm = {
	pager:null,
	pager2:null,
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
                init(false,pageIndex,pageSize);
            }
        });
        //init(true,1,pageSizeTemp);
	},
	createPager2: function(){
		var _this = this;
		_this.pager2 = pages({
            el: "#channael-table-pages2",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
				pageSizeTemp2 = pageSize;
                init2(false,pageIndex, pageSize);
            }
        });
        //init2(true,1,pageSizeTemp2);
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
	//读取客户名称
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
							getChannel();
						}
					} else {
						customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
					}
					centeridObj.push(data.mi001list[i]);
				}
				$('#customerName').html(customerOptions);
				// 获取渠道
				getChannel(data.mi001list[0].centerid);
				// 获取业务类型
				getType();
				// 切换的时候 发请求 去get渠道
				$('#customerName').off().on("change", function () {
					getChannel($(this).val());
				});
			}

		},
		error:function(){
			errMsg();
		}
	});
	
	
	
	
	
	
}
function getChannel (centerid) {
	//获取渠道名称
	$.ajax({
		type:'POST',
		url:'./webappcomChannel.json',
		data:{'centerid': centerid},
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
				$('#channelName').html(channelOptions);
				channelHtml = channelOptions;
			}
			// 获取应用列表
			getApp(centerid, channelObj[0].itemid);
			// 切换的时候 发请求 去get应用列表
			$('#channelName').off().on("change", function () {
				getApp(centerid, $(this).val());
			});

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
				init2(false,1,pageSizeTemp2);
			}

		},
		error:function(){
			errMsg();
		}
	});
}

function getType() {
	//获取业务类型
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
			}
			getAllChannelType();

		},
		error:function(){
			errMsg();
		}
	});
}









//init page
function init(changer,pageIndex,pageSize){//画表格头部mi041

	var cols1 = [
		{ title:'序号', name:'id' ,width:60, align:'center',renderer: function(val,item,index){
			return index+1;
		} },
		{ title:'用户ID', name:'userid' ,width:96, align:'center'},
		{ title:'姓名', name:'username' ,width:100, align:'center'},
		{ title:'客户名称', name:'centerid' ,width:100, align:'center',renderer: function(val,item,index){
			for(var i = 0;i<centeridObj.length;i++){
				if(item.centerid == centeridObj[i].centerid){
					return centeridObj[i].centername;
				}
			}
		} },
		{ title:'渠道类型', name:'channel' ,width:100, align:'center', renderer: function(val,item,index){
			for(var i = 0;i<channelObj.length;i++){
				if(item.channel == channelObj[i].itemid){
					return channelObj[i].itemval;
				}
			}
		} },
		{ title:'渠道应用', name:'appname' ,width:100, align:'center' },
		{ title:'业务类型', name:'buztype' ,width:100, align:'center',renderer: function(val ,item, index){
			for(var i=0;i<busitypeObj.length;i++){
				if(item.buztype == busitypeObj[i].itemid){
					return busitypeObj[i].itemval;
				}
			}
		}},
		{ title:'单笔限额', name:'onequotai' ,width:100, align:'center'},
		{ title:'交易金额', name:'quotai' ,width:100, align:'center'},
		{ title:'交易时间', name:'datecreated' ,width:100, align:'center'},
	];
	$.ajax({
		type:'POST',
		url:'./webapi04404.json',
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
				appm.tableObj = $('#channelOverflowTable1').mmGrid({
					multiSelect: true,// 多选
					checkCol: true, // 选框列
					height: 'auto',
					cols: cols1,
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
		},
		error:function(){
			errMsg();
		}
	});
}



function init2(changer,pageIndex,pageSize){
	var cols2 = [
		{ title:'序号', name:'id' ,width:50, align:'center',renderer: function(val,item,index){
			return index+1;
		} },
		{ title:'用户ID', name:'userid' ,width:80, align:'center'},
		{ title:'姓名', name:'username' ,width:85, align:'center'},
		{ title:'客户名称', name:'centerid' ,width:90, align:'center',renderer: function(val,item,index){
			for(var i = 0;i<centeridObj.length;i++){
				if(item.centerid == centeridObj[i].centerid){
					return centeridObj[i].centername;
				}
			}
		} },
		{ title:'渠道类型', name:'channel' ,width:90, align:'center', renderer: function(val,item,index){
			for(var i = 0;i<channelObj.length;i++){
				if(item.channel == channelObj[i].itemid){
					return channelObj[i].itemval;
				}
			}
		} },
		{ title:'渠道应用', name:'appname' ,width:90, align:'center' },
		{ title:'业务类型', name:'buztype' ,width:90, align:'center',renderer: function(val ,item, index){
			for(var i=0;i<busitypeObj.length;i++){
				if(item.buztype == busitypeObj[i].itemid){
					return busitypeObj[i].itemval;
				}
			}
		}},
		{ title:'当日累积上限额', name:'quotai' ,width:100, align:'center'},
		{ title:'超出额', name:'dayquotai' ,width:90, align:'center'},
		{ title:'交易金额', name:'onequotai' ,width:90, align:'center'},
		{ title:'交易时间', name:'datecreated' ,width:90, align:'center'},
	];
	var centerid = $('#customerName').val() ? $('#customerName').val() : user.centerid;
	$.ajax({
		type:'POST',
		url:'./webapi03604.json',
		data:{'centerid':centerid,'page':pageIndex,'rows':pageSize},
		datatype:'json',
		success:function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			//pageData = data;
			if( appm.tableObj2 != null){
				appm.tableObj2.load(data.rows);
			} else {
				appm.tableObj2 = $('#channelOverflowTable2').mmGrid({
					multiSelect: true,// 多选
					checkCol: true, // 选框列
					height: 'auto',
					cols: cols2,
					items: data.rows,
					loadingText: "loading...",
					noDataText: "暂无数据。",
					loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
					sortable: false
				});	
			}
			
			//修改分页
			appm.pager2.reset({
				itemLength:data.total,
	            pageSize:data.pageSize,
	            reset:changer?true:false
			});
			parent.Common.loading(false);
		},
		error:function(){
			errMsg();
		}
	});
}

function quoSearch(){
	if($('.dateInput:first').val().length != $('.dateInput:last').val().length){
		parent.Common.dialog({
			type: "error",
            text: "时间输入有误，请检查起止日期！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {
            }
		});
	}
	var searchData = {
		'centerid':$('#customerName').val(),
		'channel':$('#channelName').val(),
		'pid':$('#appName').val(),
		'buztype':$('#busiType').val(),
		'startDate':$('#dateStart').val(),
		'endDate':$('#dateEnd').val(),
		'page':1,
		'rows':'10'
	};
	parent.Common.loading(true);
	$.ajax({
		type:'post',
		url:'./webapi04404.json',
		data: searchData,
		datatype:'json',
		success: function(data){
			if(typeof data == 'string'){
				data = JSON.parse(data);
			}
			appm.tableObj.load(data.rows);
		},
		error: function(){
			errMsg();
		}
	});
	$.ajax({
		type:'post',
		url:'./webapi03604.json',
		data:searchData,
		datatype:'json',
		success:function(data){
			if(typeof data =='string'){
				data = JSON.parse(data);
			}
			appm.tableObj2.load(data.rows);
			parent.Common.loading(false);
		}
	});
	
}

//获取日期
function regDate(){
	var startTime = {
		elem: '#dateStart',
	    format: 'YYYY-MM-DD',
	    min: laydate.now(), //设定最小日期为当前日期
	    max: '2099-12-31', //最大日期
	    istime: false,
	    istoday: false,
	    fixed: false,
	    choose: function(datas){
	         endTime.min = datas; //开始日选好后，重置结束日的最小日期
	         endTime.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var endTime = {
		elem: '#dateEnd',
	    format: 'YYYY-MM-DD',
	    min: laydate.now(),
	    max: '2099-12-31',
	    istime: false,
	    istoday: false,
	    fixed: true,
	    choose: function(datas){
	        startTime.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(startTime);
	laydate(endTime);
	laydate.skin('huanglv');
}


//搜索框变化的时候绑定值
function bindChange(){
	// $('#channelName').bind('change',function(){
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
	// 	$('#appName').html(appnameOption);
	// });
	// $('#appName').bind('change',function(){
	// 	var val = $(this).val();
	// 	for(var i=0;i<appnameData.applist.length;i++){
	// 		if(val == appnameData.applist[i].pid){
	// 			appnameData.applist[i].channel
	// 			$('#channelName option[value="'+appnameData.applist[i].channel+'"]').attr('selected',true);
	// 		}
	// 		if(val == ''){
	// 			$('#channelName option:first').attr('selected',true);
	// 		}
	// 	}
	// });
}

//检查两个文本框输入条件
var check = {
	checkNopress: function(){
		document.keyup(function(){
			return false;
		})
	},
	check2Date: function(){
		
	}
}


$(function(){
	getAll();
	//初始化页面
	appm.createPager();
	appm.createPager2();
	
	regDate();
	
	setTimeout(function(){
		bindChange();
	},100);

	$('#customerName').off().on('change',function(event) {
		getApp($(this).val());
	});
});


function getApp(centerid, itemid){
	//获取应用名称
	$.ajax({
		type:'POST',
		url:'./webapi04007.json',
		data:{
			'centerid': centerid, 'channel': itemid
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
				$('#appName').html(appnameOptions);
				appNameHtml = appnameOptions;
				
			}
			
			
			
		},
		error:function(){
			errMsg();
		}
	});
}