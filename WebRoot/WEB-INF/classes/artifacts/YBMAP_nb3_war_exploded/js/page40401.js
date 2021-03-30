/**
 * Created by wanghonghu on 2016/12/27.
 * 意见反馈跟踪处理
 */

var feedback = {
    pageSize: 10,
    pager: null,
    tabler: null,
    tableData: null,
    userInfo: top.userInfo,
    init: function () {
        var _self = this;
        _self.getSelectInfo();  //获取下拉数据
        _self.createDatepicker(); //时间选择器
        _self.createPager();
        _self.btnClick();

    },
    //获取下拉数据
    getSelectInfo: function () {
        var self = this;
        //    获取确认状态,确认人信息，中心名称和设备区分
        function getStatusSuccess(data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode == '000000') {
                var statusList = data.statusList,
                    centerList = data.mi001list,
                    deviceList = data.devicetypeList,
                    confirmList = data.yjfkList,
                    statusOptions = '<option value="">请选择</option>',
                    customerOptions = '<option value="">请选择</option>',
                    deviceOptions = '<option value="">请选择</option>',
                    confirmOptions = '<option value="">请选择</option>';
                for (var k = 0; k < statusList.length; k++) {
                    statusOptions += '<option value="' + statusList[k].itemid + '">' + statusList[k].itemval + '</option>';
                }

                for (var i = 0; i < centerList.length; i++) {
                    if (self.userInfo.centerid != "00000000") {
                        if (self.userInfo.centerid == centerList[i].centerid) {
                            customerOptions += '<option value="' + centerList[i].centerid + '">' +centerList[i].centername + '</option>';
                        }
                    } else {
                        customerOptions += '<option value="' + centerList[i].centerid + '">' + centerList[i].centername + '</option>';
                    }

                }
                for (var j = 0; j < deviceList.length; j++) {
                    deviceOptions += '<option value="' + deviceList[j].itemid + '">' + deviceList[j].itemval + '</option>';
                }
                for (var q = 0; q < confirmList.length; q++) {
                    confirmOptions += '<option value="' + confirmList[q].itemid + '">' + confirmList[q].itemval + '</option>';
                }

                $('#centerName').html(customerOptions);
                $('#deviceType').html(deviceOptions);
                $('#status,#statusPop').html(statusOptions);
                $('#confirmdetail').html(confirmOptions);
            }
        }

        feedback.ajaxFn('get', './get40401.json', getStatusSuccess);
    },
    dealWith: function () {
        var self = this;
        parent.Common.loading(true);
        var  selected = self.tabler.selectedRows(),
            seqno = selected[0].seqno,
            confirmdetail = parent.$('#confirmdetail').val(),
            statusPop = parent.$('#statusPop').val();
        var param = {
            status: statusPop,
            confirmdetail: confirmdetail,
            seqno: seqno
        };

        function dealWithSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.popupClose();
            if (data.recode == "000000") {
                self.getTableData(1, self.pageSize, true);
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

        self.ajaxFn("POST", "./webapi40402.json", dealWithSuccess, param);
    },
    /*时间选择器*/
    createDatepicker: function () {
        var self = this;
        var start = {
            elem: '#channelAnalysis-top-startTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            istoday: true,
            max: laydate.now(), //最大为今天
            choose: function (datas) { //选择日期完毕的回调
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas;
            }
        };
        var end = {
            elem: '#channelAnalysis-top-endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            istoday: true,
            max: laydate.now(), //最大为今天
            choose: function (datas) { //选择日期完毕的回调
                start.max = datas;
            }
        };
        laydate(start);
        laydate(end);
        laydate.skin('huanglv');
        return self;
    },
    btnClick: function () {
        var self = this;
        /*查询*/
        $("#querybtn").click(function () {
            var _this = $(this);
            parent.Common.loading(true);
            feedback.getTableData(1, self.pageSize, true);
        });

        /*跟踪处理*/
        $("#orgManage-btn-tracking").click(function () {
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
                return;
            } else if (selected.length > 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            } else {
                var selectText = $(selected[0]).text();
                if (selectText.indexOf("已确认") >0) {
                    parent.Common.dialog({
                        type: "info",
                        text: "该意见已经确认！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                } else {
                    var createHTML = $(".tracking-item").html();
                    parent.Common.popupShow(createHTML);
                }

            }
        });
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
            page = page,
            rows = rows;
        var arr = $("#feedbackForm").serializeArray();
        var dataJson = self.convertToJson(arr);
        var param = {
            page: page,
            rows: rows
        };
        jQuery.extend(dataJson, param);
        $.ajax({
            type: 'POST',
            url: './webapi40401.json',
            data: dataJson,
            datatype: 'json',
            success: function (data) {
                parent.Common.loading(false);
                if (typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000" || data.recode == '280001') {
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
            },
            error: function () {
                parent.Common.ajaxError();
            }

        });
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#orgManage-table-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
        self.getTableData(1, self.pageSize, true);
    },
    /*创建表格*/
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            {
                title: '序号',
                name: 'seqno',
                hidden: 'true',
                width: 80,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            },
            {
                title: '用户名', name: 'userid', width: 80, align: 'center', renderer: function (val, item, index) {
                return val;
            }
            },
            {
                title: '版本号',
                name: 'versionno',
                width: 60,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            },
            {
                title: '设备区分', name: 'devtype', width: 80, align: 'center', renderer: function (val, item, index) {
                return $('#deviceType option[value=' + val + ']').text();
            }
            },
            {
                title: '设备标识',
                name: 'devid',
                width: 180,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            },
            {
                title: '反馈内容',
                name: 'detail',
                width: 180,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            },
            {
                title: '确认人',
                name: 'confirmid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == undefined || val == null || val == "") {
                        return "-"
                    } else {
                        return val;
                    }
                }
            }, {
                title: '确认状态',
                name: 'status',
                width: 80,
                align: 'center',
                renderer: function (val, item, index) {
                    return $('#status option[value=' + val + ']').text();
                }
            }, {
                title: '确认日期',
                name: 'confirmtime',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == undefined || val == null || val == "") {
                        return "-"
                    } else {
                        return val;
                    }
                }
            }, {
                title: '确认人意见',
                name: 'confirmdetail',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == undefined || val == null || val == "") {
                        return "-"
                    } else {
                        return $('#confirmdetail option[value=' + val + ']').text();
                    }

                }
            }
        ];


        if (self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#setTable').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                height: 'auto',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    },
    /* 转换表单数据 */
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
    feedback.init();
});
