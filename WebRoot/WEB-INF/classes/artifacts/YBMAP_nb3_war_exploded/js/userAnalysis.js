/**
 * Created by M on 2016/10/15.
 */
var user = top.userInfo;
var userAnalysis = {
    startTime: null,
    endTime: null,
    tableObj: null,
    tabIndex: 0,
    userIndex: 0,
    timeList:[],//新增、累计用户统计用
    nameList:[],//新增、累计用户统计用
    infoList:[],//新增、累计用户统计用
    infoList1:[],//新增用户统计用
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
    getUserData:function(){
        var self = this,
            centerid = $('#searchCustomer').val(),
            pid = $('#searchChannel').val(),
            startdate = self.startTime==null?laydate.now(-6):self.startTime,
            enddate = self.endTime==null?laydate.now():self.endTime;
        $("#channelAnalysis-top-startTime").val(startdate);
        $("#channelAnalysis-top-endTime").val(enddate);
        if(startdate == enddate){
            parent.Common.dialog({
                type: "error",
                text: '请选择两天以上的时间进行查询！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {}
            });
            return;
        }
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi10707User.json',
            data:{
                centerid:centerid,
                pid:pid,
                startdate:startdate,
                enddate:enddate
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                var countnum = [];
                var bottomData = [];

                if(data.recode == '000000'){
                    self.infoList = [];
                    self.infoList1 = [];
                    self.nameList = [];
                    self.timeList = [];
                    if(data.rows2.length > 0){
                        $.each(data.rows2, function(index, val) {
                            self.infoList.push({data:val.countnum,type:'line',name:val.appname});
                            self.nameList.push(val.appname);
                            if(index == 0){
                                self.timeList = val.date;
                            }
                            var num = 0
                            $.each(val.countnum, function(i, v) {
                                num += v;
                            });
                            countnum.push(num)
                        });
                    }else{
                        self.infoList = {};
                        self.nameList = [];
                        self.timeList = [];
                    }

                    if(data.rows1.length > 0){

                        $.each(data.rows1, function(index, val) {
                            self.infoList1.push({data:val.countsum,type:'line',name:val.appname});
                            
                            var sum = 0
                            $.each(val.countsum, function(i, v) {
                                sum = v;
                            });
                            bottomData.push({appname: val.appname,countnum: countnum[index],countsum: sum})
                        });
                    }
                    

                    

                    if(self.userIndex == 0){
                        self.createLineCanvas(self.infoList,self.nameList,self.timeList);
                    }else{
                        self.createLineCanvas(self.infoList1,self.nameList,self.timeList);
                    }

                    
                    self.createTable(bottomData);
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
                parent.Common.loading(false);
                self.startTime = startdate;
                self.endTime = enddate;
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    getSexData: function(){
        var self = this;
        var centerid = $('#searchCustomer').val(),
            pid = $('#searchChannel').val(),
            startdate = self.startTime,
            enddate = self.endTime,
            info = [];

        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1070702.json',
            data:{
                centerid:centerid,
                pid:pid,
                startdate:startdate,
                enddate:enddate
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    var count = 0;
                    for(var tempKey in data.rows[0]) {
                        count++;
                    }
                    if(count == 0) {
                        parent.Common.dialog({
                            type: "error",
                            text: '当前时间段暂无数据！',
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {}
                        });
                        $("#gender").hide();
                        parent.Common.loading(false);
                        return;
                    }
                    $.each(data.rows, function(index, val) {
                        info.push({value:val.countsum,name:val.sex});
                    });
                    $("#gender").show();
                    self.createPieCanvas(info);
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
                parent.Common.loading(false);
                self.startTime = startdate;
                self.endTime = enddate;
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    getAgeData: function(){
        var self = this;
        var centerid = $('#searchCustomer').val(),
            pid = $('#searchChannel').val(),
            startdate = self.startTime,
            enddate = self.endTime,
            nameList = [],
            infoList = [];

        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1070701.json',
            data:{
                centerid:centerid,
                pid:pid,
                startdate:startdate,
                enddate:enddate
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    $.each(data.rows, function(index, val) {
                        infoList.push({value:val.average,name:val.appname});
                        nameList.push(val.appname)
                    });
                    self.createBarCanvas(nameList,infoList);
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
                parent.Common.loading(false);
                self.startTime = startdate;
                self.endTime = enddate;
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    createLineCanvas: function (seriesData,legendData,xAxisData) {
        var myChart = echarts.init(document.getElementById("growth-canvas"));
        var option = {
            color: ["#3498db", "#1abc9c", "#2ecc71", "#e6ac22", "#ea2323", "#f02f81", "#9b59b6", "#a4d7f2"],
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:legendData
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data:xAxisData,
                    splitLine: {show: false},
                }
            ],
            grid: {
                top:'8%',
                left: '3%',
                right: '4%',
                bottom: '5%',
                containLabel: true,
                x: "0"
            },
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : seriesData
        };
        myChart.setOption(option);
        return this;
    },
    createPieCanvas: function (data) {
        var myChart = echarts.init(document.getElementById("gender"));
        var option = {
            color: ["#ed7056", "#52d1b5"],
            title : {
                text: ''
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['女性','男性']
            },
            series : [
                {
                    name: '访问量',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        normal:{
                            label:{
                                show: true,
                                formatter: '{b} : {c} ({d}%)'
                            },
                            labelLine :{show:true}
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
        return this;
    },
    createBarCanvas: function (v,t) {
        var myChart = echarts.init(document.getElementById("age"));
        var option = {
            color: ['#4e96f8'],
            title: {
                text: ''
            },
            tooltip: {},
            xAxis: {
                // data: ["门户网站", "网上业务大厅", "自助终端", "服务热线", "手机短信", "手机客户端", "官方微信", "官方微博"]
                data: v
            },
            yAxis: {
                name: "平均年龄（岁）",
                nameLocation: "middle",
                nameRotate: 90,
                nameGap: 35
            },
            series: [{
                name: '平均年龄（岁）',
                type: 'bar',
                barWidth: 40,
                // data: [
                //     { value: 60, name: '门户网站' },
                //     { value: 42, name: '网上业务大厅' },
                //     { value: 72, name: '自助终端' },
                //     { value: 32, name: '服务热线' },
                //     { value: 42, name: '手机短信' },
                //     { value: 52, name: '手机客户端' },
                //     { value: 22, name: '官方微信' },
                //     { value: 62, name: '官方微博' }
                // ]
                data: t
            }]
        };
        myChart.setOption(option);
        return this;
    },
    btnClick: function () {
        var self = this;
        $('#btnQuery').off().on('click',function() {
            var _index = self.tabIndex,
                startdate = $('.startTime').val(),
                enddate = $('.endTime').val(),
                pid = $('#searchChannel').val();

            if(Number(_index) == 1 && pid == ''){
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
                    self.getUserData();
                    break;
                case 1:
                    self.getSexData();
                    break;
                case 2:
                    self.getAgeData();
                    break;
            }
        });

        $('#searchCustomer').off().on('change',function(){
            self.getAppByCID();
        });

        $(".growth-btns a").on("click", function () {
            var _index = $(this).index();
            $(this).addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    self.userIndex = 0;
                    self.createLineCanvas(self.infoList,self.nameList,self.timeList);
                    break;
                case 1:
                    self.userIndex = 1;
                    self.createLineCanvas(self.infoList1,self.nameList,self.timeList);
                    break;
            }
        });
        // tab btn
        var growth = $("#growth"),
            gender = $("#gender"),
            age = $("#age"),
            tab = $(".channelAnalysis-tab a");
        tab.on("click", function () {
            var _this = $(this),
                _index = _this.index();
            _this.addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    growth.stop().show().siblings("div").hide();
                    self.tabIndex = 0;
                    break;
                case 1:
                    gender.stop().show().siblings("div").hide();
                    self.tabIndex = 1;
                    break;
                case 2:
                    age.stop().show().siblings("div").hide();
                    self.tabIndex = 2;
                    break;
            }
        });

        return this;
    },
    createTable: function (data) {
        console.log(JSON.stringify(data))
        var self = this;
        var cols = [
            { title:'渠道', name:'appname' ,width:296, align: 'center' },
            { title:'新增人数', name:'countnum' ,width:346, align: 'center'},
            { title:'累计人数', name:'countsum' ,width:368, align: 'center'}
        ];
        if(self.tableObj != null) {
            self.tableObj.load(data);
        } else {
            self.tableObj = $('#growth-table').mmGrid({
                multiSelect: false,
                checkCol: false,
                indexCol: true,
                height: 'auto',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        
    },
    getAppByCID:function (){
        // 渠道 
        var centerid = '';
        user.centerid!='00000000'?centerid=user.centerid:centerid=$('#searchCustomer').val();

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
                userAnalysis.getUserData();

            }
            
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    userAnalysis.getAppByCID();
    userAnalysis.createDatepicker();
    userAnalysis.btnClick();

    
    
});
function date(f){

    var myDate = new Date();
    var y = myDate.getFullYear();
    var m = myDate.getMonth()+1;
    var d = myDate.getDate();
    if(f == 0){
        d = myDate.getDate()-6;
    }
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