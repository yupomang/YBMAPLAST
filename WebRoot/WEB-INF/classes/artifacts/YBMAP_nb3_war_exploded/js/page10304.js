/**
 * Created by M on 2016/10/11.
 */

var user = top.userInfo;
var channelAnalysis = {
    clientDroplist: null,
    channelDroplist: null,
    startTime: null,
    endTime: null,
    tabIndex: 0,
    createDatepicker: function () {
        var self = this;
        laydate({
            elem: '#channelAnalysis-top-startTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate({
            elem: '#channelAnalysis-top-endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.endTime = datas;
            }
        });
        laydate.skin('huanglv');
        return self;
    },
    createECharts: function (el, xData, type, data, title,barWidth,axisLabel) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(el));
        // 指定图表的配置项和数据
        var option = {
            color: ['#3398DB'],
            title: {
                text: title,
                x: 'center'
            },
            tooltip : {
            },

            grid: {
                left: '3%',
                right: '4%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : xData,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel:axisLabel
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name: "服务量（次）",
                    nameLocation: "middle",
                    nameRotate: 90,
                    nameGap: 40
                }
            ],
            series : [
                {
                    hoverable:true,
                    name:'服务量',
                    type:type,
                    barWidth: barWidth,
                    data:data
                }
            ]
        };
        myChart.setOption(option);
        return this;
    },
    getCvisitsData: function(){
        var self = this,
            title = '',
            barWidth = '40',
            axisLabel = {},
            name = [],
            count = [],
            centerid = $('#searchCustomer').val(),
            pid = $('#searchChannel').val(),
            startdate = self.startTime==null?date():self.startTime,
            enddate = self.endTime==null?date():self.endTime;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1070601.json',
            data:{
                centerid:centerid,
                startdate:startdate,
                enddate:enddate,
                pid:pid
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    $.each(data.rows, function(index, val) {
                        name.push(val.appname)
                        count.push(val.count)
                    });
                    title = $("#searchCustomer").find("option:selected").text();
                    self.createECharts('views-canvas',name,"bar",count,title,barWidth,axisLabel);
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
                parent.Common.loading(false);
            },
            error:function(){
                parent.Common.ajaxError();
                parent.Common.loading(false);
            }
        });
    },
    getActivityData: function(isTime){
        /**
         *  isTime： 0 代表查询渠道功能活跃度统计
         *  isTime： 1 访问时间段分布统计
         * */
        var self = this,
            title = '',
            barWidth = '40',
            axisLabel = {},
            name = [],
            count = [],
            timeName = [],
            timeCount = [],
            centerid = $('#searchCustomer').val(),
            startdate = $('.startTime').val(),
            enddate = $('.endTime').val(),
            pid = $('#searchChannel').val();
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1070602.json',
            data:{
                'centerid': centerid,
                'startdate':startdate,
                'enddate':enddate,
                'pid':pid,
                'mifreeuse4': isTime
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    if(isTime == 0) {
                        // 渠道功能活跃度统计
                        $.each(data.rows1, function(index, val) {
                            name.push(val.transname);
                            count.push(val.count);
                        });
                        if(name.length > 10){
                            barWidth = '60%';
                            axisLabel = {'rotate': '60','interval': '0'};
                        }
                        title = $("#searchChannel").find("option:selected").text();
                        self.createECharts('activity-canvas',name,"bar",count,title,barWidth,{'rotate': '60','interval': '0'});
                    } else if(isTime == 1) {
                        //访问时间段分布统计
                        $.each(data.rows2, function(index, val) {
                            timeName.push(val.time);
                            timeCount.push(val.count);
                        });
                        self.createECharts('period-canvas',timeName,"line",timeCount,title,barWidth,axisLabel);
                    }
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
            error:function(){
                parent.Common.ajaxError();
                parent.Common.loading(false);
            }
        });
    },
    btnClick: function () {
        var self = this,
            views = $("#views"),
            activity = $("#activity"),
            period = $("#period"),
            tab = $(".channelAnalysis-tab a");
        tab.on("click", function () {
            var _this = $(this),
                _index = _this.index();
            _this.addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    views.stop().show().siblings("div").hide();
                    self.tabIndex = 0;
                    break;
                case 1:
                    activity.stop().show().siblings("div").hide();
                    self.tabIndex = 1;
                    break;
                case 2:
                    period.stop().show().siblings("div").hide();
                    self.tabIndex = 2;
                    break;
            }
        });

        $('#btnQuery').off().on('click',function() {
            var _index = self.tabIndex,
                startdate = $('.startTime').val(),
                enddate = $('.endTime').val(),
                pid = $('#searchChannel').val();

            if(Number(_index) > 0 && pid == ''){
                parent.Common.dialog({
                    type: "error",
                    text: '请选择渠道应用！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }

            if(startdate == ''){
                parent.Common.dialog({
                    type: "error",
                    text: '请输入开始日期！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            if(enddate == ''){
                parent.Common.dialog({
                    type: "error",
                    text: '请输入结束日期！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            if(getDate(enddate) < getDate(startdate)){
                parent.Common.dialog({
                    type: "error",
                    text: '结束日期不能大于开始日期！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            switch (_index) {
                case 0:
                    self.getCvisitsData();
                    break;
                case 1:
                    self.getActivityData(0);
                    break;
                case 2:
                    self.getActivityData(1);
                    break;
            }
        });

        $('#searchCustomer').off().on('change',function(){
            self.getAppByCID();
        });
    },
    getAppByCID:function (){
        // 渠道
        var centerid = '';
        centerid = user.centerid!='00000000'? user.centerid : $('#searchCustomer').val();

        $.ajax({
            type:'POST',
            url:'./webapi04007.json',
            data:{'centerid': centerid},
            datatype:'json',
            success:function(data){
                var channelOptions = '<option value="">全部</option>';
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    for(var i = 0;i<data.applist.length;i++){
                        channelOptions += '<option value="'+data.applist[i].pid+'">'+data.applist[i].appname+'</option>';
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
                $('#searchChannel').html(channelOptions);

            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    }
};


$(document).ready(function(){
    // 客户名称
    $.ajax({
        type:'POST',
        url:'./webappcomCenterId.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '';
                for(var i = 0;i<data.mi001list.length;i++){
                    if(user.centerid != '00000000'){
                        if(user.centerid == data.mi001list[i].centerid){
                            customerOptions = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>'
                        }
                    } else {
                        customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                    }
                }
                $('#searchCustomer').html(customerOptions);
                if(user.centerid != '00000000'){
                    $('.cn').hide();
                }

                channelAnalysis.getAppByCID();
                channelAnalysis.createDatepicker();
                channelAnalysis.btnClick();
                channelAnalysis.getCvisitsData();
                $('.startTime').val(date());
                $('.endTime').val(date());
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
        error:function(){
            parent.Common.ajaxError();
        }
    });



});

function date(){

    var myDate = new Date();
    var y = myDate.getFullYear();
    var m = myDate.getMonth()+1;
    var d = myDate.getDate();

    return y+'-'+m+'-'+d;
}
function getDate(date){
    var dates = date.split("-");
    var dateReturn = '';

    for(var i=0; i<dates.length; i++){
        dateReturn+=dates[i];
    }
    return dateReturn;
}