/**
 * Created by wanghonghu on 2016/10/24.
 */

var user = top.userInfo;
var channelAnalysis = {
    clientDroplist: null,
    channelDroplist: null,
    startTime: null,
    endTime: null,
    tabIndex: "#views",
    self: this,
    pageSize: 10,

    /*标签页切换及按钮事件*/
    btnClick: function () {
        var self = this,
            views = $("#views"),
            activity = $("#activity"),
            tab = $(".channelAnalysis-tab a");
        tab.on("click", function () {
            parent.Common.loading(true);
            var _this = $(this),
                _index = _this.index();
            _this.addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    views.stop().show().siblings("div").hide();
                    self.tabIndex = "#views";
                    self.getTableData(1, self.pageSize, true, self.tabIndex);
                    break;
                case 1:
                    activity.stop().show().siblings("div").hide();
                    self.tabIndex = "#activity";
                    self.getTableData(1, self.pageSize, true, self.tabIndex);
                    break;
            }
        });
        /*app业务日志信息查询*/
        $('#querybtn').click(function () {
            if ($("#customerName").val() == null || $("#customerName").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择中心名称！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if ($("#serviceType").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择业务类型！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if ($("#versionType").val() == null || $("#versionType").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择软件版本！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if ($("#equipmentDivision").val() == null || $("#equipmentDivision").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择设备区分！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            parent.Common.loading(true);
            self.getTableData(1, self.pageSize, true, self.tabIndex);
        });
        /*MI中心前置业务日志信息*/
        $('#querybtn-mi').click(function () {
            if ($("#customerName-mi").val() == null || $("#customerName-mi").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择中心名称！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if ($("mi-serviceType").val() == "") {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择业务类型！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            parent.Common.loading(true);
            self.getTableData(1, self.pageSize, true, self.tabIndex);
        });
        /*根据中心名称和设备区分的改变获取相应版本*/
        function getVersionnoSuccess(data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode == '000000' && data.rows) {
                var versionOptions = '';
                if(data.rows.length>0){
                    versionOptions = '<option value="">请选择</option>';
                }

                for (var i = 0; i < data.rows.length; i++) {
                    versionOptions += '<option value="' + data.rows[i].versionno + '">' + data.rows[i].versionno + '</option>';
                }
                $('#versionType').html(versionOptions);
            }
        }

        $("#equipmentDivision,#customerName").change(function () {
            var centerid = $("#customerName").val();
            var devtype = $("#equipmentDivision").val();
            var versionParam = {
                centerid: centerid,
                devtype: devtype
            };
            if (centerid != "" && devtype != "") {
                channelAnalysis.ajaxFn('post', 'ptl40013Verno.json', getVersionnoSuccess, versionParam);
            }

        });
        /*业务日志信息删除*/
        $("#orgManage-btn-del").click(function () {
            var selected = $("#setTable").find("tr.selected");
            if (selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "warning",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });
        /*MI中心前置业务日志删除*/
    },
    /*时间选择器*/
    createDatepicker: function () {
        var self = this;
        laydate({
            elem: '#channelAnalysis-top-endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function (datas) { //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate({
            elem: '#channelAnalysis-top-startTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function (datas) { //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate.skin('huanglv');
        return self;
    },
    /*删除某条或某几条数据*/
    del: function () {
        parent.Common.loading(true);
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];

        var url = null;
        var param = null;
        if (self.tabIndex == "#views") { // app业务日志信息
            url = './ptl40013Del.json';
            for (var i = 0; i < selected.length; i++) {
                delIds.push(selected[i].seqno);
            }
            param = {'seqno': delIds.join(",")};
        } else {                       //MI中心前置业务日志信息
            url = './ptl4001301Del.json';
            for (var j = 0; j < selected.length; j++) {
                delIds.push(selected[j].miseqno);
            }
            param = {'miseqno': delIds.join(",")};
        }
        function  delSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                self.getTableData(1, self.pageSize, true, self.tabIndex);
            } else {
                parent.Common.loading(false);
                parent.Common.dialog({
                    type: "error",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            }
        }
        self.ajaxFn("post",url,delSuccess,param);
    },
    /*获取表格数据*/
    convertToJson: function (formValues) {
        var result = {};
        for (var formValue, j = 0; j < formValues.length; j++) {
            formValue = formValues[j];
            var name = formValue.name;
            var value = formValue.value;
            if (name.indexOf('.') < 0) {
                result[name] = value;
                continue;
            } else {
                var simpleNames = name.split('.');
                // 构建命名空间
                var obj = result;
                for (var i = 0; i < simpleNames.length - 1; i++) {
                    var simpleName = simpleNames[i];
                    if (simpleName.indexOf('[') < 0) {
                        if (obj[simpleName] == null) {
                            obj[simpleName] = {};
                        }
                        obj = obj[simpleName];
                    } else { // 数组
                        // 分隔
                        var arrNames = simpleName.split('[');
                        var arrName = arrNames[0];
                        var arrIndex = parseInt(arrNames[1]);
                        if (obj[arrName] == null) {
                            obj[arrName] = []; // new Array();
                        }
                        obj = obj[arrName];
                        multiChooseArray = result[arrName];
                        if (obj[arrIndex] == null) {
                            obj[arrIndex] = {}; // new Object();
                        }
                        obj = obj[arrIndex];
                    }
                }

                if (obj[simpleNames[simpleNames.length - 1]]) {
                    var temp = obj[simpleNames[simpleNames.length - 1]];
                    obj[simpleNames[simpleNames.length - 1]] = temp;
                } else {
                    obj[simpleNames[simpleNames.length - 1]] = value;
                }

            }
        }
        return result;
    },
    getTableData: function (page, rows, pageRestBool, formWrap) {
        var self = this,
            url = null;
        var arr = $(formWrap).find('form').serializeArray();
        var dataJson = self.convertToJson(arr);
        dataJson.page = page;
        dataJson.rows = rows;
        if (formWrap == "#views") {
            url = './ptl40013PageQry.json';
            if (!dataJson.centerid) {
                dataJson.centerid = "000000"
            }
            if (!dataJson.transtype) {
                dataJson.transtype = "1"
            }
            if (!dataJson.versionno) {
                dataJson.versionno = "1"
            }
            if (!dataJson.devtype) {
                dataJson.devtype = "1"
            }
        } else {
            url = './ptl4001301PageQry.json';
            if (!dataJson.micenterid) {
                dataJson.micenterid = "000000"
            }
            if (!dataJson.mitranstype) {
                dataJson.mitranstype = "1"
            }
        }
        
        function  getTabelSuccess(data) {
            parent.Common.loading(false);
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000" || data.recode == "280001") {
                self.createTable(data.rows);
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize,
                    reset: pageRestBool
                });
            } else {
                parent.Common.dialog({
                    type: "error",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            }
        }
        self.ajaxFn("post",url,getTabelSuccess,dataJson);
    },
    /*创建表格*/
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            {
                title: '序号', name: 'seqno', width: 60, align: 'center', renderer: function (val, item, index) {
                return index+1;
            }
            },
            {
                title: '中心ID/名称', name: 'centerid', width: 100, align: 'center', renderer: function (val, item, index) {
                return $("#customerName option[value=" + val + "]").text();
            }
            },
            {
                title: '用户名',
                name: 'userid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            },
            {
                title: '业务日期', name: 'transdate', width: 100, align: 'center', renderer: function (val, item, index) {
                if(val == "" || val == undefined || val == null){
                    return "-";
                }else{
                    return val;
                }
            }
            },
            {
                title: '业务名称',
                name: 'transname',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            },
            {
                title: '版本号',
                name: 'versionno',
                width: 60,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            },
            {
                title: '设备区分',
                name: 'devtype',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return $('#equipmentDivision option[value=' + val + ']').text();
                }
            }, {
                title: '设备标识',
                name: 'devid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '业务请求时间',
                name: 'requesttime',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '业务响应时间',
                name: 'responsetime',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '处理时间',
                name: 'secondsafter',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '处理状态',
                name: 'freeuse1',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return $('#proccess-status option[value=' + val + ']').text();
                }
            },
        ];
        var colsMi = [
            {
                title: '序号', name: 'miseqno', width: 60, align: 'center', renderer: function (val, item, index) {
                return index+1;
            }
            },
            {
                title: '中心ID/名称',
                name: 'micenterid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return $("#customerName-mi option[value=" + val + "]").text();
                }
            },
            {
                title: '用户名',
                name: 'miuserid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            },
            {
                title: '业务日期', name: 'mitransdate', width: 100, align: 'center', renderer: function (val, item, index) {
                if(val == "" || val == undefined || val == null){
                    return "-";
                }else{
                    return val;
                }
            }
            },
            {
                title: '业务名称',
                name: 'mitransname',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            },
            {
                title: '版本号',
                name: 'miversionno',
                width: 60,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '设备标识',
                name: 'midevid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '业务请求时间',
                name: 'mirequesttime',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '业务响应时间',
                name: 'miresponsetime',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '处理时间',
                name: 'misecondsafter',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if(val == "" || val == undefined || val == null){
                        return "-";
                    }else{
                        return val;
                    }
                }
            }, {
                title: '处理状态',
                name: 'mifreeuse1',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return $('#proccess-status-mi option[value=' + val + ']').text();
                }
            }
        ];
        var realCols = cols;
        if (self.tabIndex == "#views") {
            var realCols = cols;
        } else {
            var realCols = colsMi;
        }
        if (self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#setTable').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                height: 'auto',
                cols: realCols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    },
    /*创建分页*/
    createPager: function (Id) {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#" + Id,
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false, self.tabIndex);
            }
        });
        self.getTableData(1, self.pageSize, true, self.tabIndex);
    },
    /*ajax方法*/
    ajaxFn: function (type, url, successFn, param) {
        $.ajax({
            type: type,
            url: url,
            datatype: 'json',
            data: param,
            success: successFn,
            error: function () {
                parent.Common.ajaxError();
            }
        });
    }


};


$(document).ready(function () {
    /*获取中心名称和设备区分*/
    function getCustomerNameSuccess(data) {
        if (typeof data == 'string') {
            data = JSON.parse(data);
        }
        if (data.recode == '000000') {
            var customerOptions = '<option value="">请选择</option>';
            var deviceOptions = '<option value="">请选择</option>';
            for (var i = 0; i < data.mi001list.length; i++) {
                customerOptions += '<option value="' + data.mi001list[i].centerid + '">' + data.mi001list[i].centername + '</option>';
            }
            for (var j = 0; j < data.devicetypeList.length; j++) {
                deviceOptions += '<option value="' + data.devicetypeList[j].itemid + '">' + data.devicetypeList[j].itemval + '</option>';
            }

            $('#customerName-mi,#customerName').html(customerOptions);
            $('#equipmentDivision').html(deviceOptions);
        }

    }

    channelAnalysis.ajaxFn('post', './page41101GetPara.json', getCustomerNameSuccess);


    /*获取业务类型*/
    function getServiceTypeSuccess(data) {
        if (typeof data == 'string') {
            data = JSON.parse(data);
        }
        if (data.recode == '000000') {
            var serviceTypeOptions = '<option value="">请选择</option>';
            for (var i = 0; i < data.mi007list.length; i++) {
                serviceTypeOptions += '<option value="' + data.mi007list[i].itemid + '">' + data.mi007list[i].itemval + '</option>';
            }
            $('#serviceType,#mi-serviceType').html(serviceTypeOptions);
        }
    }

    channelAnalysis.ajaxFn('post', './getApptranstype.json', getServiceTypeSuccess);


    channelAnalysis.btnClick();
    channelAnalysis.createDatepicker();
    channelAnalysis.createPager("orgManage-table-pages");


});
