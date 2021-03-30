/**
 * Created by wanghonghu on 2016/11/2.
 */
var ue = {
    // 用户体验评价统计
    clientDroplist: null,
    startTime: null,
    endTime: null,
    userInfo: top.userInfo,
    init: function () {
        var self = this;
        self.createDroplist()
            .createDatepicker()
            .btnClick();
    },
    createDroplist: function () {
        parent.Common.loading(true);
        var self = this;
        /*获取客户名称*/
        function getCustomerNameSuccess(data) {
            parent.Common.loading(false);
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode == '000000') {
                var customerOptions = '';
                for (var i = 0; i < data.mi001list.length; i++) {
                    if (self.userInfo.centerid != "00000000") {
                        if (self.userInfo.centerid == data.mi001list[i].centerid) {
                            customerOptions += '<option value="' + data.mi001list[i].centerid + '">' + data.mi001list[i].centername + '</option>';
                        }
                    } else {
                        customerOptions += '<option value="' + data.mi001list[i].centerid + '">' + data.mi001list[i].centername + '</option>';
                    }
                }

                $('#channelUE-top-droplist-customerName').html(customerOptions);
                $('#channelUE-top-droplist-customerName').find("option").eq(0).attr("selected",true);

            }else{
                parent.Common.dialog({
                    type: "info",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            }

        }

        self.ajaxFn('post', './page41101GetPara.json', getCustomerNameSuccess);
        return self;
    },
    createDatepicker: function () {
        var self = this;
        var start = {
            elem: '#channelUE-top-startTime',
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
            elem: '#channelUE-top-endTime',
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
    /*创建echarts图表 -- 饼图，
     param:{
     el:创建图表元素,
     title:图表标题,
     color:图表颜色,
     legendData:图例数据,
     items:图表数据,
     name：图表名称,
     clickFn:图表点击事件
     }
     */
    createECharts: function (param) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(param.el));
        // 指定图表的配置项和数据
        var option = {
            color: param.color,
            title: {
                show: param.title.show,
                text: param.title.text ? param.title.text : "",
                x: 'center',
                y: 'top',
                textStyle: {
                    color: '#313131',
                    fontSize: 16
                }
            },
            /* tooltip: {
             trigger: 'item',
             formatter: "{a} <br/>{b} : {c} ({d}%)"
             },*/
            legend: {
                show: param.legend.showFlag,
                orient: 'vertical',
                left: 'left',
                data: param.legend.data
            },
            series: [
                {
                    name: param.name,
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: param.items,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: " {d}%({c}) \n {b} "
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
        myChart.on('click', function eConsole(data) {
            if (param.clickFn) {
                param.clickFn(data);
            }
        });
        return this;
    },
    /*创建echarts图表 -- 柱状图
     * param = {
     *   color:图表颜色,
     *   legendData: 图例，
     *   xAxis:{data,rotate}横轴
     *   yAxis：{name}纵轴名称
     *   series: {name,data}
     *
     * }
     * */
    createEchartsBar: function (param) {
        var myChart = echarts.init(document.getElementById(param.el));
        var options = {
            color: param.color ? param.color : ['#4E96F8'],
            grid: {
                left: '4%',
                right: '4%',
                bottom: '16%',
                top: '3%',
                containLabel: true
            },
            legend: {
                itemWidth: 10,
                itemHeight: 10,
                data: param.legendData,
                orient: "horizontal",
                bottom: "0"
            },
            xAxis: [
                {
                    splitLine: {show: true},
                    type: 'category',
                    data: param.xAxis.data,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel: {
                        interval: 0,//横轴信息全部显示
                        rotate: param.xAxis.rotate,//60度角倾斜显示，
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitNumber: 5,
                    min: 0,
                    max: 40,
                    splitLine: {show: true},
                    name: param.yAxis.name,
                    nameLocation: "middle",
                    nameGap: 30
                }
            ],
            series: [
                {
                    name: param.series.name,
                    type: 'bar',
                    barWidth: param.series.barWidth ? param.series.barWidth : '30px',
                    data: param.series.data
                }
            ]
        };
        myChart.setOption(options);
        myChart.on('click', function eConsole(data) {
            if (param.clickFn) {
                param.clickFn(data);
            }
        });
        return this;
    },
    btnClick: function () {
        var _self = this;
        $('#orgManage-info-goBack').click(function () {
            $('.channelAnalysis-top,.channelUE-box').show();
            $('.orgManage-box').hide();
        });
        $('#eval-backBtn').click(function () {
            $('.channelAnalysis-top,.channelUE-box').show();
            $('.eval-box').hide();
        });
        $('#netPoint-backBtn').click(function () {
            $('.orgManage-box').show();
            $('.netPoint-manager').hide();
        });
        $("#channelUE-top-queryBtn").click(function () {
            var startTime = $("#channelUE-top-startTime").val(),
                endTime = $("#channelUE-top-endTime").val(),
                centerId = $("#channelUE-top-droplist-customerName").val();
            if (startTime == "" || startTime == null) {
                parent.Common.dialog({
                    type: "info",
                    text: "开始时间为空",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            } else if (endTime == "" || endTime == null) {
                parent.Common.dialog({
                    type: "info",
                    text: "结束时间为空",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            } else {
                parent.Common.loading(true);
                /*在线客服传参*/
                var param = {
                    centerId: centerId,
                    startdate: startTime,
                    enddate: endTime
                };
                /*柜面传参*/
                var transParam = {
                    centerid: centerId,
                    startdate: startTime,
                    enddate: endTime
                };
                _self.ajaxFn('GET', 'webapi05801.json', getEvaluateSuccess, param);  //获取在线客服评价
                _self.ajaxFn('POST', 'webapi05803.json', getTransSuccess, transParam);      //获取柜面评价
            }

        });

        /*获取在线客服评价成功的返回函数*/
        function getEvaluateSuccess(data) {
            parent.Common.loading(false);
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                var dataResult = data.result;
                if(dataResult.code == "0000"){
                    var legend = [],
                        dataItems = dataResult.datas,
                        allEvaluate = 0,
                        satisfaction = 0;
                    for (var i = 0; i < dataResult.datas.length; i++) {
                        var nowData = dataResult.datas[i],
                            item = {value: nowData.value, name: nowData.name};
                        legend.push(nowData.name);
                        // dataItems.push(item);
                        if (nowData.key !== "-") {
                            allEvaluate += nowData.value;
                        }
                        if (nowData.key !== "-" && nowData.key !== "-1") {
                            satisfaction += nowData.value;
                        }
                    }
                    var satisficationPercent = ((satisfaction / allEvaluate) * 100).toFixed(2);
                    if (allEvaluate == 0) {
                        satisficationPercent = 0;
                    }
                    $("#channelUE-classification").parent().removeClass("hide");
                    $("#channelUE-classification").text(satisficationPercent);

                    var onlineEvaluate = {
                        el: "channelUE-canvas",
                        title: {show: true, text: "在线客户服务评价"},
                        color: ["#448eb5", "#44b549", "#ef8f06", "#7F7F7F"],
                        legend: {showFlag: false, data: legend},
                        items: dataItems,
                        name: "在线客户服务评价",
                        clickFn: function (data) {
                            parent.Common.loading(true);
                            var key = data.data.key;
                            var startTime = $("#channelUE-top-startTime").val(),
                                endTime = $("#channelUE-top-endTime").val(),
                                centerId = $("#channelUE-top-droplist-customerName").val();
                            var param = {
                                centerId: centerId,
                                startdate: startTime,
                                enddate: endTime,
                                selecttype: key
                            };
                            _self.ajaxFn('GET', 'webapi05802.json', getEvalueateDetailSuccess, param);

                        }
                    };
                    _self.createECharts(onlineEvaluate);
                }else{
                    parent.Common.dialog({
                        type: "error",
                        text: dataResult.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }

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

        /*获取柜面评价成功的返回函数*/
        function getTransSuccess(data) {
            parent.Common.loading(false);
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                var dataResult = data.result;
                var dataItem = [],
                    allEvaluate = 0,
                    satisfaction = 0;
                if (dataResult.dataList.length > 0) {
                    for (var i = 0; i < dataResult.dataList.length; i++) {
                        var nowData = dataResult.dataList[i],
                            item = {name: nowData.name, value: nowData.value};
                        dataItem.push(item);
                        if (nowData.name !== "未评价") {
                            allEvaluate += nowData.value;
                        }
                        if (nowData.name !== "未评价" && nowData.name !== "不满意") {
                            satisfaction += nowData.value;
                        }
                    }
                } else {
                    dataItem = [{name: "满意", value: 0}, {name: "基本满意", value: 0}, {name: "不满意", value: 0}];
                }


                var satisficationPercent = ((satisfaction / allEvaluate) * 100).toFixed(2);
                if (allEvaluate == 0) {
                    satisficationPercent = 0;
                }
                $("#channelUE-table-classification").parent().removeClass("hide");
                $("#channelUE-table-classification").text(satisficationPercent);
                var tableEvaluate = {
                    el: "channelUE-canvas-table",
                    title: {show: true, text: dataResult.title},
                    color: ["#44b549", "#448eb5", "#ef8f06", "#7F7F7F"],
                    legend: {showFlag: false, data: dataResult.typeList},
                    items: dataItem,
                    name: dataResult.title,
                    clickFn: function (data) {
                        if (data.name == "不满意" && data.value != 0) {
                           parent.Common.loading(true);
                            var startTime = $("#channelUE-top-startTime").val(),
                                endTime = $("#channelUE-top-endTime").val(),
                                centerId = $("#channelUE-top-droplist-customerName").val();
                            var param = {
                                centerid: centerId,
                                startdate: startTime,
                                enddate: endTime
                            };
                            _self.ajaxFn('POST', 'webapi05804.json', getUnSatisficationSuccess, param);
                            function getUnSatisficationSuccess(data) {
                                parent.Common.loading(false);
                                if (typeof data == "string") {
                                    data = Json.parse(data);
                                }
                                if(data.recode == "000000"){
                                    var dataResult = data.result;
                                    var legend = dataResult.typeList,
                                        dataItem = dataResult.dataList;
                                    $('.channelAnalysis-top,.channelUE-box').hide();
                                    $('.orgManage-box').show(0, function () {
                                        var serviceEvaluate = {
                                            el: "channelUE-table-detail",
                                            title: {show: false},
                                            color: ["#448eb5", "#44b549", "#ef8f06", "#7F7F7F"],
                                            legend: {showFlag: false, data: legend},
                                            items: dataItem,
                                            name: "在线客服服务评价",
                                            clickFn: function (data) {
                                                if (data.name == "业务政策" && data.value != 0) {
                                                    parent.Common.loading(true);
                                                    _self.ajaxFn('POST', 'webapi05805.json', getPolicySuccess, param);
                                                    /*获取业务政策方面不满意原因成功*/
                                                    function getPolicySuccess(data) {
                                                        parent.Common.loading(false);
                                                        if (typeof data == "string") {
                                                            data = JSON.parse(data);
                                                        }
                                                        if (data.recode == "000000") {
                                                            var dataResult = data.result;
                                                            var fundTakenLegend = [],
                                                                fundTakenItem = [],
                                                                foundLoanLegend = [],
                                                                foundLoanItem = [],
                                                                fundTakenAll = 0,
                                                                foundLoanAll = 0;

                                                            //获取公积金提取政策
                                                            if (dataResult.dataList1.length > 0) {
                                                                for (var i = 0; i < dataResult.dataList1.length; i++) {
                                                                    var nowData = dataResult.dataList1[i];
                                                                    fundTakenLegend.push(nowData.name);
                                                                    fundTakenItem.push(nowData.value);
                                                                    fundTakenAll += nowData.value;
                                                                }
                                                            }
                                                            if (dataResult.dataList2.length > 0) {
                                                                for (var j = 0; j < dataResult.dataList2.length; j++) {
                                                                    var nowData2 = dataResult.dataList2[j];
                                                                    foundLoanLegend.push(nowData2.name);
                                                                    foundLoanItem.push(nowData2.value);
                                                                    foundLoanAll += nowData2.value;
                                                                }
                                                            }
                                                            $("#extractNum").text(fundTakenAll);
                                                            $("#loanNum").text(foundLoanAll);
                                                            $(".channelUE-table-policy").show();
                                                            $(".channelUE-table-service").hide();
                                                            var fundTeken = {
                                                                el: "channelUE-fund-taken",
                                                                legendData: ['提取原因'],
                                                                xAxis: {
                                                                    data: fundTakenLegend,
                                                                    rotate: 20
                                                                },
                                                                yAxis: {name: "评价数量"},
                                                                series: {
                                                                    name: "提取原因",
                                                                    data: fundTakenItem
                                                                }
                                                            };
                                                            var fundLoan = {
                                                                el: "channelUE-fund-loan",
                                                                legendData: ["贷款类型"],
                                                                xAxis: {
                                                                    data: foundLoanLegend,
                                                                    rotate: 0
                                                                },
                                                                yAxis: {name: "评价数量"},
                                                                series: {
                                                                    barWidth: "30%",
                                                                    name: "贷款类型",
                                                                    data: foundLoanItem
                                                                }
                                                            };
                                                            ue.createEchartsBar(fundTeken);
                                                            ue.createEchartsBar(fundLoan);
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
                                                } else if (data.name == "服务态度" && data.value != 0) {
                                                    parent.Common.loading(true);
                                                    _self.ajaxFn('POST', 'webapi05806.json', getServiceEvaluateSuccess, param);
                                                    function getServiceEvaluateSuccess(data) {
                                                        parent.Common.loading(false);
                                                        if (typeof data == "string") {
                                                            data = JSON.parse(data);
                                                        }
                                                        if (data.recode == "000000") {
                                                            var dataReuslt = data.result;
                                                            var netLegend = [],
                                                                netItem = [];
                                                            if (dataReuslt.dataList.length > 0) {
                                                                for (var k = 0; k < dataReuslt.dataList.length; k++) {
                                                                    var nowData3 = dataReuslt.dataList[k];
                                                                    netLegend.push(nowData3.name);
                                                                    netItem.push(nowData3.value);
                                                                }
                                                            }
                                                            $(".channelUE-table-policy").hide();
                                                            $(".channelUE-table-service").show();
                                                            var serviceOption = {
                                                                el: "channelUE-service",
                                                                legendData: ["网点名称"],
                                                                xAxis: {
                                                                    data: netLegend,
                                                                    rotate: 30
                                                                },
                                                                yAxis: {name: "评价数量"},
                                                                series: {
                                                                    name: "公积金贷款政策",
                                                                    data: netItem
                                                                },
                                                                clickFn: function (data) {
                                                                    if (data.value > 0) {
                                                                        parent.Common.loading(true);
                                                                        var serviceParam = param;
                                                                        serviceParam.agentinstmsg = encodeURIComponent(data.name);
                                                                        _self.ajaxFn('POST', 'webapi05807.json', getNetDetailSuccess, serviceParam);
                                                                        function getNetDetailSuccess(data) {
                                                                            parent.Common.loading(false);
                                                                            if (typeof data == "string") {
                                                                                data = JSON.parse(data);
                                                                            }
                                                                            if (data.recode == "000000") {
                                                                                var resultData = data.result;
                                                                                resultData.totalCount = resultData.dataList.length;

                                                                                $("#channelUE-manager .channelUE-title").text(data.name);
                                                                                $('.orgManage-box').hide();
                                                                                $('.netPoint-manager').show();
                                                                                if (netService.pager) {
                                                                                    netService.customerData = resultData;
                                                                                    netService.getpagesInfo(1, true);
                                                                                } else {
                                                                                    netService.customerData = resultData;
                                                                                    netService.createPagerCustomerService();
                                                                                }
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
                                                                    }

                                                                }
                                                            };
                                                            ue.createEchartsBar(serviceOption);
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
                                                }
                                            }
                                        };
                                        ue.createECharts(serviceEvaluate);

                                    });
                                }else{
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
                        }
                    }
                };
                _self.createECharts(tableEvaluate);
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

        /*点击饼图，查看相应满意度*/
        function getEvalueateDetailSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode !== "000000") {
                parent.Common.dialog({
                    type: "error",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            } else {
                var resultData = data.result;
                if(resultData.code == "0000"){
                    $('.channelAnalysis-top,.channelUE-box').hide();
                    $('.eval-box').show();
                    if (customerService.pager1 && customerService.pager2) {
                        customerService.customerData = resultData;
                        customerService.getpagesInfo(1, true);
                    } else {
                        customerService.customerData = resultData;
                        customerService.createPagerCustomerService();
                    }
                }else{
                    parent.Common.dialog({
                        type: "error",
                        text: resultData.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }

            }

        }
    },
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
/*柜面网点评价不满意统计*/
var netService = {
    pageSize: 10,
    pager: null,//网点服务态度
    tabler: null, //网点服务态度
    tableData1: null,  //语音坐席
    tableData2: null,   //互联网坐席
    tableData3: null,   //网点服务
    customerData: null,   //柜面业务服务返回数据
    createPagerCustomerService: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#customer-service-net", //柜面业务服务不满意原因分类统计 - 服务态度
            itemLength: self.customerData.totalCount ? self.customerData.totalCount : 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getpagesInfo(pageIndex);
            }
        });
        self.getpagesInfo(1);
    },
    getCustomerServiceTableData: function (pageRestBool, datas) {
        var self = this;
        self.createTable(datas.tableData);
        self.pager.reset({
            itemLength: datas.totalCount ? datas.totalCount : 0,
            pageSize: self.pageSize,
            reset: pageRestBool
        });
    },
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            {
                title: '序号', name: 'countername', width: 50, align: 'center', renderer: function (val, item, index) {
                return index + 1;
            }
            },
            {
                title: '柜员姓名',
                name: 'countername',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            }, {
                title: '柜员工号',
                name: 'counternum',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            }, {
                title: '业务日期',
                name: 'transdate',
                width: 132,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            }, {
                title: '业务类型',
                name: 'tradetype',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            }, {
                title: '业务流水号',
                name: 'transid',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return val;
                }
            }
        ];
        if (self.tabler != null) {
            self.tabler.load(self.tableData);
        } else {
            self.tabler = $('#customer-service-net-table').mmGrid({
                multiSelect: false,// 多选
                checkCol: false, // 选框列
                height: 'auto',
                cols: cols,
                items: self.tableData,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    },
    /*前端分页获取数据
     * indexPage:当前页
     * datas:获取的数据列表
     * */
    getpagesInfo: function (indexPage, isresetBool) {
        var _self = this;
        var curentPage = indexPage,
            pagesize = _self.pageSize,
            result = _self.customerData;
        result.tableData = [];
        for (var i = (curentPage - 1) * pagesize; i < pagesize * curentPage; i++) {
            if (result.dataList[i]) {
                result.tableData.push(result.dataList[i]);
            }
        }
        _self.getCustomerServiceTableData(isresetBool ? isresetBool : false, result);
    }
};
/*客户服务评价满意分类统计*/
var customerService = {
    pageSize: 10,
    pager1: null, //语音坐席
    pager2: null, //互联网坐席
    pager3: null,//网点服务态度
    tabler1: null, //语音坐席
    tabler2: null, //互联网坐席
    tabler3: null, //网点服务态度
    satisfy1:null,
    satisfy2:null,
    tableData1: null,  //语音坐席
    tableData2: null,   //互联网坐席
    tableData3: null,   //网点服务
    customerData: null,   //在线客服返回数据
    filene: "",
    filept: "",
    load: "",
    createPagerCustomerService: function () {
        var self = this;
        // create pages
        self.pager1 = pages({
            el: "#customer-service-voice", //语音
            itemLength: self.customerData.totalCount ? self.customerData.totalCount : 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getVoicePageInfo(pageIndex);
            }
        });
        self.pager2 = pages({
            el: "#customer-service-internet", //坐席
            itemLength: self.customerData.totalCount ? self.customerData.totalCount : 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getpagesInfo(pageIndex);
            }
        });
        self.getpagesInfo(1);
        self.getVoicePageInfo(1);
    },
    /*语音坐席  reset table*/
    getVoiceServiceTableData: function (pageRestBool, datas) {
        var self = this;
        self.createVoiceTable(datas);
        self.pager1.reset({
            itemLength: 0,
            pageSize: self.pageSize,
            reset: pageRestBool
        });
    },
    /*互联网坐席---reset table*/
    getCustomerServiceTableData: function (pageRestBool, datas) {
        var self = this;
        self.createTable(datas);
        self.pager2.reset({
            itemLength: datas.totalCount ? datas.totalCount : 0,
            pageSize: self.pageSize,
            reset: pageRestBool    //是否重新生成pager
        });
    },
    /*画语音坐席表格*/
    createVoiceTable: function (data) {
        var self = this;
        self.tableData1 = [];
        var cols = [
            {
                title: '序号', name: 'chat_id', width: 50, align: 'center', renderer: function (val, item, index) {
                return index + 1;
            }
            },
            {
                title: '坐席工号',
                name: 'org_user_name',
                width: 60,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == null || val == undefined) {
                        return "-";
                    } else {
                        return val;
                    }
                }
            }, {
                title: '服务评价',
                name: 'satisfy_degree',
                width: 120,
                align: 'center',
                hidden:true,
                renderer: function (val, item, index) {
                    if (val == null || val == undefined || val == "-") {
                        self.satisfy1 = "-";
                        return "-";
                    } else {
                        self.satisfy1 = self.getStart(val);
                        return self.getStart(val);
                    }
                }
            },{
                title: '坐席姓名',
                name: 'org_user_nickname',
                width: 160,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == null || val == undefined) {
                        return "-";
                    } else {
                        return '<span style="vertical-align: bottom">'+val+' 【 </span>'+ self.satisfy1 + '<span style="vertical-align: bottom"> 】</span>  ';
                    }
                }
            },{
            title: '服务时间',
                name: 'createtime',
                width: 172,
                align: 'center',
                renderer: function (val, item, index) {
                if (val == null || val == undefined) {
                    return "-";
                } else {
                    return val;
                }
            }
        }
        ];
        if (self.tabler1 != null) {
            self.tabler1.load(self.tableData1);
        } else {
            self.tabler1 = $('#customer-service-voice-table').mmGrid({
                multiSelect: false,// 多选
                checkCol: false, // 选框列
                height: 'auto',
                cols: cols,
                items: self.tableData1,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    },
    /*画互联网坐席表格*/
    createTable: function (data) {
        var self = this;
        self.tableData2 = data.tableData;
        var cols = [
            {
                title: '序号', name: 'chat_id', width: 50, align: 'center', renderer: function (val, item, index) {
                return index + 1;
            }
            },
            {
                title: '坐席工号',
                name: 'org_user_name',
                width: 60,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == null || val == undefined) {
                        return "-";
                    } else {
                        return val;
                    }
                }
            }, {
                title: '服务评价',
                name: 'satisfy_degree',
                width: 120,
                align: 'center',
                hidden:true,
                renderer: function (val, item, index) {
                    if (val == null || val == undefined || val == "-") {
                        self.satisfy2 =  "-";
                        return "-";
                    } else {
                        self.satisfy2 = self.getStart(val);
                        return self.getStart(val);
                    }
                }
            },{
                title: '坐席姓名',
                name: 'org_user_nickname',
                width: 160,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == null || val == undefined) {
                        return "-";
                    } else {
                        return '<span style="vertical-align: bottom">'+val+' 【 </span>'+ self.satisfy2 + '<span style="vertical-align: bottom"> 】</span>';
                    }
                }
            },
            {
                title: '服务时间',
                name: 'createtime',
                width: 172,
                align: 'center',
                renderer: function (val, item, index) {
                    if (val == null || val == undefined) {
                        return "-";
                    } else {
                        return val;
                    }
                }
            }
        ];
        if (self.tabler2 != null) {
            self.tabler2.load(self.tableData2);
        } else {
            self.tabler2 = $('#customer-service-internet-table').mmGrid({
                multiSelect: false,// 多选
                checkCol: false, // 选框列
                height: 'auto',
                cols: cols,
                items: self.tableData2,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    },
    /*前端分页获取   语音坐席 数据
     *   说明： 此处数据还没有，如何解析还没有出来。
     * indexPage:当前页
     * datas:获取的数据列表
     * */
    getVoicePageInfo: function (indexPage, isresetBool) {
        var _self = this;
        var curentPage = indexPage,
            pagesize = _self.pageSize,
            result = _self.customerData;
        result.tableData = [];
        for (var i = (curentPage - 1) * pagesize; i < pagesize * curentPage; i++) {
            if (result.datas[i]) {
                result.tableData.push(result.datas[i]);
            }
        }
        _self.getVoiceServiceTableData(isresetBool ? isresetBool : false, result);
    },
    /*前端分页获取   互联网坐席 数据
     * indexPage:当前页
     * datas:获取的数据列表
     * */
    getpagesInfo: function (indexPage, isresetBool) {
        var _self = this;
        var curentPage = indexPage,
            pagesize = _self.pageSize,
            result = _self.customerData;
        result.tableData = [];
        for (var i = (curentPage - 1) * pagesize; i < pagesize * curentPage; i++) {
            if (result.datas[i]) {
                result.tableData.push(result.datas[i]);
            }
        }
        _self.getCustomerServiceTableData(isresetBool ? isresetBool : false, result);
    },
    /*获取星星*/
    getStart: function (val) {
        var satisfication = val * 100;
        var html = "";
        var startEmpty = 'images/starempty.png',
            starrhalf = 'images/starhalf.png',
            starfull = 'images/starfull.png';
        if (satisfication < 10) {
            for (var i = 0; i < 5; i++) {
                html += '<img src="' + startEmpty + '" />';
            }
        } else if (satisfication >= 10 && satisfication < 20) {
            html += '<img src="' + starrhalf + '"/>';
            for (i = 0; i < 4; i++) {
                html += '<img src="' + startEmpty + '" />';
            }
        } else if (satisfication >= 20 && satisfication < 30) {
            html += '<img src="' + starfull + '"/>';
            for (i = 0; i < 4; i++) {
                html += '<img src="' + startEmpty + '" />';
            }
        } else if (satisfication >= 30 && satisfication < 40) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starrhalf + '"/>';
            for (i = 0; i < 3; i++) {
                html += '<img src="' + startEmpty + '"/>';
            }
        } else if (satisfication >= 40 && satisfication < 50) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            for (i = 0; i < 3; i++) {
                html += '<img src="' + startEmpty + '"/>';
            }
        } else if (satisfication >= 50 && satisfication < 60) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starrhalf + '"/>';
            html += '<img src="' + startEmpty + '"/>';
            html += '<img src="' + startEmpty + '"/>';
        } else if (satisfication >= 60 && satisfication < 70) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + startEmpty + '"/>';
            html += '<img src="' + startEmpty + '"/>';
        } else if (satisfication >= 70 && satisfication < 80) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starrhalf + '"/>';
            html += '<img src="' + startEmpty + '"/>';
        } else if (satisfication >= 80 && satisfication < 90) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + startEmpty + '"/>';
        } else if (satisfication >= 90 && satisfication < 100) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starrhalf + '"/>';
        } else if (satisfication == 100) {
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
            html += '<img src="' + starfull + '"/>';
        }
        return html;
    }
};
$(document).ready(function () {
    ue.init();
});
