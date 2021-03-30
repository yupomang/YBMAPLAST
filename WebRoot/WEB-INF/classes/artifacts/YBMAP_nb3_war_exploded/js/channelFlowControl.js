/**
 * Modify by M on 2016/9/2.
 */

var pagesInit = pages({
	el: "#fcPages",
	itemLength: 10,
	pageSize: 10,
	pageChanged: function (pageIndex) {
		if(arg.isSearch == false){
			folwControl.getData(pageIndex-1,10);
			arg.pagemark = pageIndex-1;
		} else {
			folwControl.search(pageIndex-1,10);
		}
	}
});

var arg = {
	tableObj : null,
	itemFormExportExcel:null,
	pagemark:0,
	dataLength:0,   //加载后填写数据总长度
	pageIndex:1,    //分页默认从第一页开始的数据记录
	pageSize:10,     //分页默认每一页显示10条
	isSearch: false,
	centerid: ''
}

function showMsg(type, textmsg, callback){
	parent.Common.dialog({
		type: type,
		text: textmsg,
		okShow:true,
		cancelShow:false,
		okText:'确定',
		ok: callback || function(){},
	});
};


$('#orgManage-btn-add,.flowSet a').click(function(e){
    arg.centerid = top.userInfo.centerid == '00000000' ? arg.centerid : top.userInfo.centerid;
	$.ajax({
		type:'POST',
		url:'./webapi04004.json',
		data:{ "page": 1, 'rows': 10, 'centerid': arg.centerid },
		datatype:'json',
		success:function(data){
			//console.log(data)
			parent.Common.loading(false);
			if(typeof data == "string") {
				data = JSON.parse(data);
			}
			if (data.recode == "000000") {
				//回填select
				var channelHtml = '<option value="00" data-pid="00000000">全部</option>';
				for(var i=0;i<data.rows.length;i++){
					channelHtml += '<option value="'+ data.rows[i].channel +'" data-pid="'+ data.rows[i].pid +'">'+ data.rows[i].appname +'</option>';
				}
				$('#channelList').html(channelHtml);
				parent.$('#channelListEdit').attr('disabled', false);
				var createHTML = $(".orgManage-popup-edit-container").html();
				parent.Common.popupShow(createHTML);

			} else {
				parent.Common.dialog({
					type: "error",
					text: data.msg,
					okShow: true,
					cancelShow: false,
					okText: "确定",
					ok: function () {}
				});
			}
		},
		error: function(){
			parent.Common.loading(false);
		}
	});
});


$('#orgManage-btn-modify').click(function(e){
    arg.centerid = top.userInfo.centerid == '00000000' ? arg.centerid : top.userInfo.centerid;
	if(arg.tableObj && arg.tableObj.selectedRows().length > 0){
		$.ajax({
			type:'POST',
			url:'./webapi04004.json',
			data:{ "page": 1, 'rows': 10, 'centerid': arg.centerid },
			datatype:'json',
			success:function(data){
				//console.log(data)
				parent.Common.loading(false);
				if(typeof data == "string") {
					data = JSON.parse(data);
				}
				if (data.recode == "000000") {
					//回填select
					var channelHtml = '<option value="00" data-pid="00000000">全部</option>';
					for(var i=0;i<data.rows.length;i++){
						channelHtml += '<option value="'+ data.rows[i].channel +'" data-pid="'+ data.rows[i].pid +'">'+ data.rows[i].appname +'</option>';
					}
					$('#channelListEdit').html(channelHtml);
					//console.log(arg.tableObj.selectedRows()[0]);
					$('#buiCode1').attr("value", folwControl.tempData.userflow);
					$('#buiName1').attr("value", arg.tableObj.selectedRows()[0].bussinessflow);
					var createHTML2 = $(".orgManage-popup-edit-container1").html();
					parent.Common.popupShow(createHTML2);
					parent.$('#channelListEdit').attr('disabled', 'disabled');
					parent.$('#channelListEdit option[value="'+ arg.tableObj.selectedRows()[0].channel +'"]').prop('selected', true);
					parent.$('#isopenEdit option[value="'+ arg.tableObj.selectedRows()[0].isopen +'"]').prop('selected', true);

				} else {
					parent.Common.dialog({
						type: "error",
						text: data.msg,
						okShow: true,
						cancelShow: false,
						okText: "确定",
						ok: function () {}
					});
				}
			},
			error: function(){
				parent.Common.loading(false);
			}
		});
	} else {
		showMsg('error', '请先选中要修改的记录！');
	}
});

$('#orgManage-btn-del').click(function(){
    //var selected = $("#channelStateTable").find("tr.selected");
	if(arg.tableObj && arg.tableObj.selectedRows().length > 0){
		parent.Common.dialog({
			type: "tips",
			text: "确定删除该条记录？",
			okShow: true,
			cancelShow: true,
			okText: "确定",
			cancelText: "取消",
			ok: function () {
				folwControl.del();
			}
		});
	} else {
		showMsg('error', '请先选中要删除的记录！');
	}

});

$('.jtree a').click(function(){
    $(this).addClass('on').siblings('a').removeClass('on');
});

$('.jtree').height($(window).height());


var folwControl = {
    tree: null,
    tempData: null, // 当前修改的数据
    getCenterList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40003Qry.json",
            datatype: "json",
            data: { 'centerid': top.userInfo.centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var temp = [];
                    data.rows.forEach(function (item) {
                        if(item.validflag == "1") {
                            temp.push(item);
                        }
                    });
                    if(self.tree == null) {
                        self.createTree(temp);
                    } else {
                        self.tree.centers = temp;
                    }
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTree: function (centers) {
        var self = this;
        self.tree = new Vue({
            el: ".jtree",
            data: {
                centers: centers,
                select: centers[0].centerid
            },
            methods: {
                checkCenter: function (id) {
                    parent.Common.loading(true);
                    this.select = id;
                    arg.centerid = id;
                    self.getData(id);
                }
            }
        });
        if(centers.length > 0) {
            self.getData(centers[0].centerid);
        }
        parent.Common.loading(false);
    },

	cols: [
		{ title:'渠道应用', name:'freeuse1' ,width:254, align:'center'},
		{ title:'访问量上限', name:'bussinessflow' ,width:254, align:'center'},
		{ title:'监控开关', name:'isopen' ,width:254, align:'center', renderer: function(val, item, index){
			return val == 1 ? '开' : '关';
		}}
	],
    getData: function (id, pageIndex, pageSize) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi04504.json',
            data:{ "page": 1, 'rows': 10, 'centerid': id },
            datatype:'json',
            success:function(data){
                //console.log(data)
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
	                $('#orgManage-btn-modify, #orgManage-btn-del, #orgManage-btn-add').show();

                    if(data.rows.length < 1) {
                    	self.isSet = false;
                        $('#orgManage-btn-modify, #orgManage-btn-del').hide();
                        $(".totleNumbers").hide();
                        $(".flowSet").show();
                        //$('#orgManage-btn-add').show();

                    } else {
                    	self.isSet = true;
                        self.tempData = data.rows[0];
                        $(".totleNumbers").show();
                        $(".flowSet").hide();
                        $(".totalIco2 p").text(data.rows[0].bussinessflow);
                        $(".totalIco1 p").text(data.rows[0].userflow);
                        //$('#orgManage-btn-add').hide();
                        //$('#orgManage-btn-modify, #orgManage-btn-del').show();

	                    /*查询表格*/
	                    if(arg.tableObj != null){
		                    arg.tableObj.load(data.rows);
	                    } else {
		                    arg.tableObj = $('#fcTable').mmGrid({
			                    multiSelect:false,// 多选
			                    checkCol: true, // 选框列
			                    height: 'auto',
			                    cols: self.cols,
			                    items: data.rows,
			                    loadingText: "loading...",
			                    noDataText: "暂无数据。",
			                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
			                    sortable: false
		                    });
	                    }
	                    arg.isSearch = false;
	                    pagesInit.reset({
		                    itemLength: data.total,
		                    pageSize: 10,
	                    });
                    }
                } else {
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            },
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
	isSet: false,

    add: function () {
        var self = this;
        var userflow = parent.$("#buiCode").val(),
            bussinessflow = parent.$("#buiDeve").val(),
	        channel = parent.$('#channelList option:selected').val(),
	        pid = parent.$('#channelList option:selected').attr('data-pid'),
	        isopen = parent.$('#isopen option:selected').val(),
            cnName = parent.$('#channelList option:selected').text();
        // 20161109 业务量上限替换为访问量上限  用户量上限隐藏
        /*if(userflow.length < 1) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "用户量上限不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }
        if(isNaN(userflow)) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "用户量上限必须为数字！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }*/
        if(bussinessflow.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "访问量上限不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(isNaN(bussinessflow)) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "访问量上限必须为数字！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();


        $.ajax({
            type:'POST',
            url:'./webapi04501.json',
            data:{
	            "userflow": userflow,
            	'bussinessflow': bussinessflow,
	            'centerid': self.tree.select,
                'channel' : channel,
	            'pid': pid,
	            'isopen': isopen,
	            'freeuse1': cnName
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    self.getData(self.tree.select);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            },
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
    edit: function () {
        var self = this;
        var userflow = parent.$("#buiCode1").val(),
            bussinessflow = parent.$("#buiName1").val(),
            channel = parent.$('#channelListEdit option:selected').val(),
	        pid = parent.$('#channelListEdit option:selected').attr('data-pid'),
	        isopen = parent.$('#isopenEdit option:selected').val(),
	        cnName = parent.$('#channelListEdit option:selected').text();

        // 20161109 业务量上限替换为访问量上限  用户量上限隐藏
        /*if(userflow.length < 1) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "用户量上限不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }
        if(isNaN(userflow)) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "用户量上限必须为数字！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }*/
        if(bussinessflow.length < 1) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "访问量上限不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }
        if(isNaN(bussinessflow)) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "访问量上限必须为数字！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi04503.json',
            data:{
            	"userflow": userflow,
	            'bussinessflow':bussinessflow,
	            'centerid': self.tree.select,
	            'channel': channel,
	            'pid': pid,
	            'isopen': isopen,
	            'freeuse1': cnName
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    self.getData(self.tree.select);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
            },
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
    del: function () {
        var self = this;
        parent.Common.loading(true);
        console.log(arg.tableObj.selectedRows());
        
        $.ajax({
            type:'POST',
            url:'./webapi04502.json',
            data:{
            	'centerid': arg.tableObj.selectedRows()[0].centerid,
	            'pid': arg.tableObj.selectedRows()[0].pid
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
	                        self.getData(self.tree.select);
                        }
                    });
                    //$('#orgManage-btn-modify, #orgManage-btn-del').hide();
                    $(".totleNumbers").show();
                    $(".flowSet").hide();
                    $('#orgManage-btn-add').show();
                } else {
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                }
                parent.Common.popupClose();
            },
            error:function () {
                parent.Common.ajaxError();
            }
        });
    }
};
parent.Common.loading(true);
folwControl.getCenterList();