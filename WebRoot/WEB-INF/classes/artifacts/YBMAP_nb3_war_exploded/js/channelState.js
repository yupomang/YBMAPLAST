parent.Common.loading(true);
var orgManagementPages = pages({
   el: "#orgManage-table-pages",
   itemLength: 2300,
   pageSize: 12,
   pageChanged: function (pageIndex, pageSize) {
       channelState.pageSize = pageSize;
       channelState.getData(channelState.centerid, pageIndex, pageSize, false, channelState.subSelect, channelState.searchConditions);
       }
   });

var cols = [
   { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
       return index + 1;
   } },
   { title:'渠道类型', name:'channel' ,width:100, align:'center', renderer: function (val) {
       var temp = '';
       channelState.channerList.forEach(function (item) {
           if(item.itemid == val) {
               temp = item.itemval;
           }
       });
       return temp;
   } },
   { title:'渠道应用', name:'pid' ,width:100, align:'center', renderer: function (val) {
       var temp = '';
       channelState.appList.forEach(function (item) {
           if(item.pid == val) {
               temp = item.appname;
           }
       });
       return temp;
   }},
   { title:'服务名称', name:'serviceid' ,width:100, align:'center', renderer: function (val) {
       var temp = '';
       channelState.serverList.forEach(function (item) {
           if(item.serviceid == val) {
               temp = item.servicename;
           }
       });
       return temp;
   }},
   { title:'启用状态', name:'startserver' ,width:70, align:'center', renderer: function (val) {
       var temp = '';
       switch (val) {
           case "1":
               temp = "启用";
               break;
           case "0":
               temp = "停用";
               break;
       }
       return temp;
   }},
   { title:'服务开始时间', name:'starttime' ,width:80, align:'center', renderer: function (val) {
       return val == "" ? "-" : val;
   }},
   { title:'服务结束时间', name:'endtime' ,width:80, align:'center', renderer: function (val) {
       return val == "" ? "-" : val;
   }},
   { title:'服务开始日', name:'freeuse2' ,width:70, align:'center', renderer: function (val) {
    var temp = '-';
     if(val != null) {
         if(val.split('-').length < 2) return temp;
         temp = val.split('-')[0];
     }
     return temp;
   }},
   { title:'服务结束日', name:'freeuse2' ,width:70, align:'center', renderer: function (val) {
       var temp = '-';
       if(val != null) {
           if(val.split('-').length < 2) return temp;
           temp = val.split('-')[1];
       }
       return temp;
   }},
];

var mmg = $('#channelStateTable').mmGrid({
    multiSelect: true,// 多选
    checkCol: true, // 选框列
	height: 'auto',
	cols: cols,
	items: [],
	loadingText: "loading...",
	noDataText: "暂无数据。",
	loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
	sortable: false
});


/**
 *  when day select changed, show the tips
 * */
function showDayTips (dayStart, dayEnd) {
    var dayStart = parent.$(dayStart).val(),
        dayEnd = parent.$(dayEnd).val();
    if(dayStart == '' || dayEnd == '') {
        parent.$(".day-tips").hide();
        return;
    }
    if(parseInt(dayStart) > parseInt(dayEnd)) {
        parent.$(".day-tips").show().find("p").eq("1").text('温馨提示：服务有效日为每月' + dayStart + '日至月底和每月1日至' + dayEnd + '日');
    } else {
        parent.$(".day-tips").show().find("p").eq("1").text('温馨提示：服务有效日为每月' + dayStart + '日至' + dayEnd + '日');
    }
}
$('#orgManage-btn-add').click(function(e){
	var createHTML = $(".orgManage-popup-create-container").html();
	parent.Common.popupShow(createHTML);
	parent.$("#startDay, #endDay").on("change", function () {
	    showDayTips("#startDay", "#endDay");
    });
});
$('#orgManage-btn-modify').click(function(e){
	var selected = mmg.selectedRows();
	if(selected.length < 1) {
        parent.Common.editNone();
    } 
    if(selected.length>1){
        parent.Common.editMore();
    }
    if(selected.length == 1) {
        var createHTML2 = $(".orgManage-popup-edit-container1").html();
		parent.Common.popupShow(createHTML2);
        parent.$("#editChannelName").val(selected[0].channel);
        parent.$("#editApplicationName").val(selected[0].pid);
        parent.$("#editCustomerName").val(selected[0].serviceid);
        if(selected[0].startserver == "0") {
            parent.$(".radioWrap input").eq(1).prop("checked", true);
            channelState.close();
        }
        if(selected[0].startserver == "1") {
            parent.$(".radioWrap input").eq(0).prop("checked", true);
        }
        parent.$("#editStartTime").attr("value", selected[0].starttime);
        parent.$("#editEndTime").attr("value", selected[0].endtime);
        parent.$("#editEndTime").attr("data-id", selected[0].id);
        setTimeout(function () {
            parent.$("#editStartDay, #editEndDay").on("change", function () {
                showDayTips("#editStartDay", "#editEndDay");
            });
            if(selected[0].freeuse2 == null) return;
            parent.$("#editStartDay").val(selected[0].freeuse2.split("-")[0]);
            parent.$("#editEndDay").val(selected[0].freeuse2.split("-")[1]).trigger("change");
        }, 0);
    }
});

$('#orgManage-btn-del').click(function(){
	var selected = mmg.selectedRows(),
        ids = [];
    if(selected.length < 1) {
        parent.Common.delNone();
        return;
    }
    selected.forEach(function (item) {
        ids.push(item.id);
    });
	parent.Common.dialog({
        type: "tips",
        text: "确定删除选中项？",
        okShow: true,
        cancelShow: true,
        okText: "确定",
        cancelText: "取消",
        ok: function () {
            channelState.del(ids.join(","));
        }
    });
});
$('#btnquery').click(function(){
    parent.Common.loading(true);
    var queryCondions = $("#query_sn").val();
    channelState.getData(channelState.centerid, "1", channelState.pageSize, true, channelState.subSelect, queryCondions);
});


// afx 2016/08/31
var channelState = {
    tree: null,
    startTime: null,
    endTime: null,
    pageSize: 10,
    centerid: null,
    channerList: [],
    serverList: [],
    appList: [],
    tableData: [],
    subSelect: '',
    cachePage: 1, // for edit & del
    searchConditions: '', // for search
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
                    var centerArr = [];
                    data.rows.forEach(function (item) {
                        if(item.validflag == "1") {
                            centerArr.push(item);
                        }
                    });
                    if(self.tree == null) {
                        self.createTree(centerArr);
                    } else {
                        self.tree.centers = centerArr;
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
        self.getChannel(top.userInfo.centerid);
        self.getServerName();
        //self.createDatepicker();
    },
    createTree: function (centers) {
        var self = this;
        self.tree = new Vue({
            el: ".jtree",
            data: {
                centers: centers,
                select: centers[0].centerid,
                apps: [],
                subSelect: ''
            },
            methods: {
                checkCenter: function (id) {
                    parent.Common.loading(true);
                    this.select = id;
                    this.subSelect = "";
                    self.getChannel(id);
                    self.centerid = id;
                },
                filterApp: function (pid) {
                    self.subSelect = pid;
                    this.subSelect = pid;
                    self.getData(self.centerid, 1, self.pageSize, true, pid, '');
                }
            }
        });
        if(centers.length > 0) {
            self.getChannel(centers[0].centerid);
            self.centerid = centers[0].centerid;
        }
        parent.Common.loading(false);
    },
    getData: function (id, page, pageSize, bool, pid, searchConditions) {
        var self = this;
        pid = pid ? pid : "";
        self.cachePage = page;
        self.searchConditions = typeof searchConditions == 'undefined' ? '' : searchConditions;
        $.ajax({
            type: "POST",
            url: "./webapi05304.json",
            datatype: "json",
            data: { 'centerid': id, "page": page, "rows": pageSize, 'pid': pid, 'serviceid': searchConditions },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    mmg.load(data.rows);
                    self.tableData = data.rows;
                    orgManagementPages.reset({
                        itemLength: data.total,
                        pageSize: pageSize,
                        reset: bool
                    });
                    parent.Common.loading(false);
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
    getChannel: function (centerid) {
        // 取渠道信息
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webappcomChannel.json",
            data: { 'centerid': centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var temp = [];
                    data.mi007list.forEach(function (val) {
                        temp.push('<option value="' + val.itemid + '">' + val.itemval + '</option>');
                    });
                    $("#channelName").html(temp.join(""));
                    $("#editChannelName").html(temp.join(""));
                    self.channerList = data.mi007list;
                    self.getAppName(centerid, data.mi007list[0].itemid);
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
    getServerName: function () {
        // 取服务名称
        var self = this;
        $.ajax({
            type: "POST",
            // url: "./getApptranstype.json",
            url: "./webapi05104.json",
            datatype: "json",
            data:{'page':'1','rows':'9999'},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var temp = [];
                    // data.mi007list.forEach(function (val) {
                    //     temp.push('<option value="' + val.itemid + '">' + val.itemval + '</option>');
                    // });
                    data.rows.forEach(function (val) {
                        temp.push('<option value="' + val.serviceid + '">' + val.servicename + '</option>');
                    });
                    $("#customerName").html(temp);
                    $("#editCustomerName").html(temp);
                    self.serverList = data.rows;
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
    getAppName: function (id, itemid) {
        // 取应用名称
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi04007.json",
            datatype: "json",
            data: { 'centerid': id, 'channel': '' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var temp = [];
                    data.applist.forEach(function (val) {
                        temp.push('<option value="' + val.pid + '">' + val.appname + '</option>');
                    });
                    $("#applicationName").html(temp);
                    $("#editApplicationName").html(temp);
                    self.appList = data.applist;
                    self.tree.apps = data.applist;
                    self.getData(id, 1, self.pageSize, true, "", '');
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
    add: function () {
        var self = this;
        var channel = parent.$("#channelName").val(),
            pid = parent.$("#applicationName").val(),
            serviceid = parent.$("#customerName").val(),
            start = parent.$(".radioWrap input:checked").val(),
            starttime = parent.$("#startTime").val(),
            endtime = parent.$("#endTime").val(),
            startDay = parent.$("#startDay").val(),
            endDay = parent.$("#endDay").val();
        starttime = starttime == "" ? "" : starttime;
        endtime = endtime == "" ? "" : endtime;
        var timeRegex = /^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][[0-9]$/,
            formatError = false;
        function IsValidTime(time, msg) {
            if(time == '') return;
            var isValid = time.match(timeRegex) !=null;
            if(!isValid) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "error",
                    text: msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                formatError = true;
            }
        }
        IsValidTime(starttime, '服务开始时间格式错误，请输入HH:mm:ss格式，例如：09:59:01');
        IsValidTime(endtime, '服务结束时间格式错误，请输入HH:mm:ss格式，例如：09:59:01');
        if(formatError) return;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05301.json",
            datatype: "json",
            data: { 'centerid': self.centerid, 'channel': channel, 'pid': pid, 'serviceid': serviceid, 'startserver': start, 'starttime': starttime, 'endtime': endtime, 'startDay': startDay, 'endDay': endDay },
            success: function(data) {
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
                    self.getData(self.centerid, 1, self.pageSize, true, self.subSelect, self.searchConditions);
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
    edit: function () {
        var self = this;
        var channel = parent.$("#editChannelName").val(),
            pid = parent.$("#editApplicationName").val(),
            serviceid = parent.$("#editCustomerName").val(),
            start = parent.$(".radioWrap input:checked").val(),
            starttime = parent.$("#editStartTime").val(),
            endtime = parent.$("#editEndTime").val(),
            id = parent.$("#editEndTime").attr("data-id"),
            startDay = parent.$("#editStartDay").val(),
            endDay = parent.$("#editEndDay").val();
        starttime = starttime == "" ? "" : starttime;
        endtime = endtime == "" ? "" : endtime;
        var timeRegex = /^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][[0-9]$/,
            formatError = false;
        function IsValidTime(time, msg) {
            if(time == '') return;
            var isValid = time.match(timeRegex) !=null;
            if(!isValid) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "error",
                    text: msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                formatError = true;
            }
        }
        IsValidTime(starttime, '服务开始时间格式错误，请输入HH:mm:ss格式，例如：09:59:01');
        IsValidTime(endtime, '服务结束时间格式错误，请输入HH:mm:ss格式，例如：09:59:01');
        if(formatError) return;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05303.json",
            datatype: "json",
            data: { 'centerid': self.centerid, 'id': id, 'channel': channel, 'pid': pid, 'serviceid': serviceid, 'startserver': start, 'starttime': starttime, 'endtime': endtime, 'startDay': startDay, 'endDay': endDay },
            success: function(data) {
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
                    self.getData(self.centerid, self.cachePage, self.pageSize, false, self.subSelect, self.searchConditions);
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
    del: function (ids) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05302.json",
            datatype: "json",
            data: { 'id': ids, 'centerid': self.centerid },
            success: function(data) {
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
                    self.getData(self.centerid, self.cachePage, self.pageSize, false, self.subSelect, self.searchConditions);
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
    open: function () {
        var _length = parent.$(".channelShortForm li").length;
        //parent.$(".channelShortForm li").eq(_length - 4).show();
        parent.$(".channelShortForm li").eq(_length - 2).show();
        parent.$(".channelShortForm li").eq(_length - 3).show();
        if(parent.$("#startDay").length > 0) {
            showDayTips("#startDay", "#endDay");
        } else {
            showDayTips("#editStartDay", "#editEndDay");
        }
    },
    close: function () {
        var _length = parent.$(".channelShortForm li").length;
        parent.$(".channelShortForm li").eq(_length - 2).hide();
        parent.$(".channelShortForm li").eq(_length - 3).hide();
        //parent.$(".channelShortForm li").eq(_length - 4).hide();
    }
};
channelState.getCenterList();