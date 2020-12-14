/**
 * Created by FelixAn on 2016/8/9.
 */
var filter = new Vue({
    el: "#filter-form",
    data:{
        cu:{
            hide: true,
            selected: '',
            options: [{
                "text":"",
                "value":""
            }]
        },
        ch:{
            hide: true,
            selected: '',
            options: [{
                "text":"",
                "value":""
            }]
        }

    },
    methods: {
        filter: function(){
            var index = $(".channelAnalysis-tab a.on").index();
            businessTable.isFunds = false;
            $("#busAnalysis2").find("input[type=checkbox]").prop("checked",businessTable.isFunds);
            switch (index) {
                case 0:
                    busAnalysis.chanelStatistics();
                    break;
                case 1:
                    busAnalysis.getBstatistics(businessTable.isFunds);
                    break;
                case 2:
                    busAnalysis.userDStatistics();
                    break;
                case 3:
                    busAnalysis.timeStatistics();
                    break;
            }
        }
    }
});
/**
 * 资金类业务 vm
 * @items, items1, items2, items3 是非资金类业务
 * @fundsItems, 是资金类业务
 */
var businessTable = new Vue({
    el: "#busAnalysis2",
    data:{
        itemTitle:[],
        itemoptions:[],
        items:{
            classType: '',
            data: [
                {
                    appname:'',
                    data:[
                        {
                            name: '',
                            value: [
                                {
                                    classTitle: "",
                                    classCount: "",
                                    classValue: []
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        items1:{
            classType: '',
            data: [
                {
                    appname:'',
                    data:[
                        {
                            name: '',
                            value: [
                                {
                                    classTitle: "",
                                    classCount: "",
                                    classValue: []
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        items2:{
            classType: '',
            data: [
                {
                    appname:'',
                    data:[
                        {
                            name: '',
                            value: [
                                {
                                    classTitle: "",
                                    classCount: "",
                                    classValue: []
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        items3: {
            classType: '',
            data: [
                {
                    appname:'',
                    data:[
                        {
                            name: '',
                            value: [
                                {
                                    classTitle: "",
                                    classCount: "",
                                    classValue: []
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        itemLength: 0,
        item1Length: 0,
        item2Length: 0,
        item3Length: 0,
        fundsItems: [
            {
                name:'',
                data:[
                    {
                        name: '',
                        data: []
                    }
                ]
            }
        ],
        bgColor: ['#ffe7e7', '#fff3d0', '#e6fbff', '#ccffcc'],
        widthArr: {
            '3': '104px',
            '4': '74px'
        },
        isFunds: false, // 资金类业务: true 是资金类业务, false 不是资金类业务
        isShow: false
    }
});
businessTable.$watch('isFunds', function (val) {
    /* if(filter.cu.selected == "00087500"){
         this.isFunds = false;
         if(val){
             parent.Common.dialog({
                 type: "info",
                 text: "此功能暂未开放",
                 okShow: true,
                 cancelShow: false,
                 okText: "确定",
                 ok: function () {
                     this.isFunds = false;
                     $("#busAnalysis2").find("input[type=checkbox]").prop("checked",this.isFunds);
                 }
             });
         }

         return false;
     }else{
         parent.Common.loading(true);
         busAnalysis.getBstatistics(val);
     }*/

    parent.Common.loading(true);
    busAnalysis.getBstatistics(val);


});
var threeTab = new Vue({
    el: '#busAnalysis3',
    data: {
        datas: [
            {
                xsfwl: 0,
                xsxx: 0,
                xsyw: 0,
                xszb: "0%",
                xxyw: 0
            }
        ],
        xszj: 0,
        xxcx: 0,
        xxfb: 0,
        ywbl: 0,
        hdjl: 0,
        isReady: false
    },
    methods: {
        exportExcel: function () {
            busAnalysis.exportExcel(3);
        },
        /**
         * 千分符转换
         * */
        comdify: function (n){
            if(typeof n == 'number') n = n + '';
            var re=/\d{1,3}(?=(\d{3})+$)/g;
            var n1 = n.replace(/^(\d+)((\.\d+)?)$/,
                function(s,s1,s2){return s1.replace(re,"$&,")+s2;});
            return n1;
        }
    }
});
var fourTab = new Vue({
    el: '#busAnalysis4',
    data: {
        datas: [{
            "appRate": "0.00%", // 手机APP
            "appVal": 0,
            "hotLineRate": "0.00%", // 12329热线
            "hotLineVal": 0,
            "ishRate": "0.00%", // 网上大厅
            "ishVal": 0,
            "msgRate": "0.00%", // 12329短信
            "msgVal": 0,
            "selfRate": "0.00%", // 自助终端
            "selfVal": 0,
            "webRate": "0.00%", // 门户网站
            "webVal": 0,
            "wechatRate": "0.00%", // 官方微信
            "wechatVal": 0,
            "weiboRate": "0.00%", // 官方微博
            "weiboVal": 0,
            "zjRate": "0.00%", // 小计
            "zjVal": 0
        }],
        isReady: false
    },
    methods: {
        exportExcel: function () {
            busAnalysis.exportExcel(4);
        },
        /**
         * 千分符转换
         * */
        comdify: function (n){
            if(typeof n == 'number') n = n + '';
            var re=/\d{1,3}(?=(\d{3})+$)/g;
            var n1 = n.replace(/^(\d+)((\.\d+)?)$/,
                function(s,s1,s2){return s1.replace(re,"$&,")+s2;});
            return n1;
        },
        /**
         * 根据$index判断是不是小计的那行
         * */
        isCount: function ($index) {
            return $index == 7 || $index == 11 || $index == 16 || $index == 17 || $index == 21 || $index == 25 || $index == 30 || $index == 31;
        }
    }
});
var channelAnalysis = {
    clientDroplist: null,
    channelDroplist: null,
    startTime: null,
    endTime: null,
    createDroplist: function () {
        var self = this;
        var droplistData1 = ["客户1", "客户2", "客户3"],
            droplistData2 = ["渠道1", "渠道2", "渠道3"];
        self.clientDroplist = droplist({
            el: "#channelAnalysis-top-droplist-customerName",
            data: droplistData1,
            selectedChanged: function () {
            }
        });
        self.channelDroplist = droplist({
            el: "#channelAnalysis-top-droplist-channel",
            data: droplistData2,
            selectedChanged: function () {
            }
        });
        return self;
    },
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
    createECharts: function (el, xData, type, data) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(el));
        // 指定图表的配置项和数据
        var option = {
            color: ['#4e96f8'],
            title: {
                text: ''
            },
            tooltip: {},
            xAxis: {
                data: xData
            },
            yAxis: {
                name: "访问量（K次）",
                nameLocation: "middle",
                nameRotate: 90,
                nameGap: 35
            },
            series: [{
                name: '访问量（K次）',
                type: type,
                barWidth: 40,
                data: data
            }]
        };
        myChart.setOption(option);
        return this;
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
                    self.createECharts('views-canvas',
                        ["门户网站","网上业务大厅","自助终端","服务热线","手机短信","手机客户端","官方微信","官方微博"],
                        "bar",
                        [60, 40, 80, 70, 60, 40, 80, 70]);
                    break;
                case 1:
                    activity.stop().show().siblings("div").hide();
                    self.createECharts('activity-canvas',
                        ["账户余额查询","账户明细查询","结息对账单","提取金额查询","提取明细查询","贷款余额查询"],
                        "bar",
                        [60, 40, 80, 70, 60, 40]);
                    break;
                case 2:
                    period.stop().show().siblings("div").hide();
                    self.createECharts('period-canvas',
                        ["00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00","24:00"],
                        "line",
                        [60, 40, 80, 70, 60, 40,60, 40, 80, 70, 60, 40,60, 40, 80, 70, 60, 40,60, 40, 80, 70, 60, 40]);
                    break;
            }
        });
    }
};
var busAnalysis = {
    clientDroplist: null,
    channelDroplist: null,
    startTime: null,
    endTime: null,
    setInputDefault:function(){
        var startTime = $("#channelAnalysis-top-startTime");
        var endTime = $("#channelAnalysis-top-endTime");
        var date = new Date();
        var month = (date.getMonth()+1)>=10 ? (date.getMonth()+1) :"0"+(date.getMonth()+1) ;
        var day = date.getDate() >=10 ? date.getDate() : "0"+date.getDate();
        var firstDay = date.getFullYear() + "-" + month + "-01";
        var today = date.getFullYear() + "-" + month +"-"+day;
        startTime.val(firstDay);
        endTime.val(today);
    },
    createDroplist: function () {
        var self = this;
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
                    var cuArr = [];
                    for(var i = 0;i<data.mi001list.length;i++){
                        cuArr.push({"value":data.mi001list[i].centerid,"text":data.mi001list[i].centername});
                    };
                    filter.cu.options = cuArr;
                    filter.cu.selected = cuArr[0].value;
                    if(top.userInfo.centerid != '00000000') {
                        self.getChannel(top.userInfo.centerid);
                        $(".cn").prop('disabled', true);
                    } else {
                        self.getChannel(data.mi001list[0].centerid);
                        $(".cn").off().on("change", function () {
                            self.getChannel($(this).val());
                        });
                    }
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
        return self;
    },
    getChannel: function (centerid) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi04004.json',
            data: {
                'centerid': centerid,
                'page': 1,
                'rows': 999
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    // var cuArr = [{'value': '', 'text': '全部', "channel": ""}];
                    var cuArr = [];
                    for(var i = 0;i<data.rows.length;i++){
                        cuArr.push({"value": data.rows[i].pid, "text": data.rows[i].appname, "channel": data.rows[i].channel});
                    };
                    if(typeof filter.ch != 'undefined') {
                        filter.ch.options = cuArr;
                        if(cuArr.length > 0) {
                            filter.ch.selected = cuArr[0].value;
                        }
                    }
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    createDatepicker: function () {
        var self = this;
        var start = {
            elem: '#channelAnalysis-top-startTime',
            format: 'YYYY-MM-DD',
            istime: true,
            istoday: false,
            choose: function(datas){
                end.min = datas; //开始日选好后，重置结束日的最小日期
                //end.start = datas; //将结束日的初始值设定为开始日
                self.startTime = datas;
            }
        };
        var end = {
            elem: '#channelAnalysis-top-endTime',
            format: 'YYYY-MM-DD',
            istime: true,
            istoday: false,
            choose: function(datas){
                start.max = datas; //结束日选好后，重置开始日的最大日期
                self.endTime = datas;
            }
        };
        laydate(start);
        laydate(end);
        self.setInputDefault();
        laydate.skin('huanglv');
        return self;
    },
    createPieECharts: function (el, color, data, items, name,clickFn) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(el));
        // 指定图表的配置项和数据
        var option = {
            color: color,
            title: {
                text: name,
                x: 'center',
                top:"20",
                textStyle:{
                    fontSize:"18",
                    fontWeight:"normal"
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data
            },
            series : [
                {
                    name: name,
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '60%'],
                    data: items,
                    itemStyle: {
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
            if (clickFn) {
                clickFn(data);
            }
        });
        return this;
    },
    createLineCharts: function (el, xData, type, data) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(el));
        // 指定图表的配置项和数据
        var option = {
            color: ['#4e96f8'],
            title: {
                text: ''
            },
            tooltip: {},
            xAxis: {
                data: xData
            },
            yAxis: {
                name: "访问量（K次）",
                nameLocation: "middle",
                nameRotate: 90,
                nameGap: 35
            },
            series: [{
                name: '访问量（K次）',
                type: type,
                barWidth: 40,
                data: data
            }]
        };
        myChart.setOption(option);
        return this;
    },
    getBstatistics: function(val){
        var url = './webapi1071001.json';
        if(val) {
            //url = './webapi1071002.json';
            url = './webapi50025.json';
        }
        if(top.userInfo.centerid != '00000000') {
            filter.cu.selected = top.userInfo.centerid;
        }
        if ($("#channelAnalysis-top-startTime").val() == '' ||$("#channelAnalysis-top-endTime").val() == '') {
            return this;
        }
        var channelObj = filter.ch.options.filter(function (val) {
            if(filter.ch.selected == val.value) return val;
        });

        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url: url,
            data:{
                "centerid": filter.cu.selected,
                "centerId": filter.cu.selected,
                'pid': filter.ch.selected,
                "startdate": $("#channelAnalysis-top-startTime").val(),
                "enddate":  $("#channelAnalysis-top-endTime").val(),
                'channel': channelObj.length == 0 ? "" : channelObj[0].channel
            },
            datatype:'json',
            success:function(datas){
                if(typeof datas == 'string'){
                    datas = JSON.parse(datas);
                }
                if(datas.recode == '000000'){
                    if (!val) {
                        // 非资金类业务
                        var nullValue = {
                            classType: '',
                            count: 0,
                            data: [
                                {
                                    appname:'',
                                    data:[
                                        {
                                            name: '',
                                            value: [
                                                {
                                                    classTitle: "",
                                                    classCount: 0,
                                                    classValue: []
                                                }
                                            ]
                                        },
                                        {
                                            name: '',
                                            value: [
                                                {
                                                    classTitle: "",
                                                    classCount: 0,
                                                    classValue: []
                                                }
                                            ]
                                        },
                                        {
                                            name: '',
                                            value: [
                                                {
                                                    classTitle: "",
                                                    classCount: 0,
                                                    classValue: []
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        };
                        if(datas.rows1 != null && datas.rows1.data.length > 0) {
                            businessTable.items = datas.rows1;
                            businessTable.isShow = true;
                            try {
                                businessTable.itemLength = datas.rows1.data[0].data[0].value[0].classValue.length;
                            } catch (e) {
                                businessTable.itemLength = 0;
                            }
                        } else {
                            businessTable.items = nullValue;
                            businessTable.itemLength = 0;
                        }
                        if(datas.rows2 != null && datas.rows2.data.length > 0) {
                            businessTable.items1 = datas.rows2;
                            businessTable.isShow = true;
                            try {
                                businessTable.item1Length = datas.rows2.data[0].data[0].value[0].classValue.length;
                            } catch (e) {
                                businessTable.item1Length = 0;
                            }
                        } else {
                            businessTable.items1 = nullValue;
                            businessTable.item1Length = 0;
                        }
                        if(datas.rows3 != null && datas.rows3.data.length > 0) {
                            businessTable.items2 = datas.rows3;
                            businessTable.isShow = true;
                            try {
                                businessTable.item2Length = datas.rows3.data[0].data[0].value[0].classValue.length;
                            } catch (e) {
                                businessTable.item2Length = 0;
                            }
                        } else {
                            businessTable.items2 = nullValue;
                            businessTable.item2Length = 0;
                        }
                        if(datas.rows4 != null && datas.rows4.data.length > 0) {
                            businessTable.items3 = datas.rows4;
                            businessTable.isShow = true;
                            try {
                                businessTable.item3Length = datas.rows4.data[0].data[0].value[0].classValue.length;
                            } catch (e) {
                                businessTable.item3Length = 0;
                            }
                        } else {
                            businessTable.items3 = nullValue;
                            businessTable.item3Length = 0;
                        }
                        if(datas.rows1.data.length == 0 &&datas.rows2.data.length == 0 &&datas.rows3.data.length == 0 &&datas.rows4.data.length == 0) {
                            businessTable.isShow = false;
                        }
                    } else {
                        // 资金类业务
                        var dataObj = datas.rows;
                        if(dataObj != null && dataObj.length > 0) {
                            if(datas.rows != null) {
                                function commafyback(num)
                                {
                                    var x = num.split(',');
                                    return parseFloat(x.join(""));
                                }
                                if(datas.rows.length > 1) {
                                    var countArray = [];
                                    for(var rowsIndex = 0; rowsIndex < dataObj.length; rowsIndex++) {
                                        /* 渠道 */
                                        for(var fnIndex = 0; fnIndex < dataObj[rowsIndex].data.length; fnIndex++) {
                                            if(countArray.length < dataObj[rowsIndex].data.length) {
                                                countArray.push({
                                                    'name': dataObj[rowsIndex].data[fnIndex].name,
                                                    'value': []
                                                });
                                            }
                                            /* 业务量，业务成功数，业务失败数，金额 */
                                            // 竖列的合计
                                            var colsArray = [];
                                            for(var itemIndex = 0; itemIndex < dataObj[rowsIndex].data[fnIndex].value.length; itemIndex++) {
                                                /* 物业费提取, 重大疾病提取 */
                                                var rowsCount = 0;
                                                for(var i = 0; i < dataObj.length; i++) {
                                                    rowsCount += commafyback(dataObj[i].data[fnIndex].value[itemIndex].value + '');
                                                }
                                                if(dataObj[rowsIndex].data[fnIndex].name == '金额') {
                                                    // 当等于资金的时候
                                                    rowsCount = (rowsCount.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
                                                }
                                                colsArray.push({
                                                    'name': dataObj[rowsIndex].data[fnIndex].value[itemIndex].name,
                                                    'value': rowsCount
                                                });
                                            }
                                            countArray[fnIndex].value = colsArray;
                                        }
                                    }
                                    // 竖列的合计
                                    dataObj.push({
                                        'appname': '合计',
                                        'data': countArray
                                    });
                                }
                                for(var rowsIndex = 0; rowsIndex < dataObj.length; rowsIndex++) {
                                    /* 渠道 */
                                    for(var fnIndex = 0; fnIndex < dataObj[rowsIndex].data.length; fnIndex++) {
                                        /* 业务量，业务成功数，业务失败数，金额 */
                                        // 横列的合计
                                        var colsCount = 0;
                                        for(var itemIndex = 0; itemIndex < dataObj[rowsIndex].data[fnIndex].value.length; itemIndex++) {
                                            /* 物业费提取, 重大疾病提取 */
                                            colsCount += commafyback(dataObj[rowsIndex].data[fnIndex].value[itemIndex].value + '');
                                        }
                                        if(dataObj[rowsIndex].data[fnIndex].name == '金额') {
                                            // 当等于资金的时候
                                            colsCount = (colsCount.toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
                                        }
                                        dataObj[rowsIndex].data[fnIndex].value.push({
                                            'name': '合计',
                                            'value': colsCount
                                        });
                                    }
                                }
                            }
                            businessTable.fundsItems = dataObj;
                            businessTable.isShow = true;
                        } else {
                            businessTable.isShow = false;
                            businessTable.fundsItems = [];
                        }
                    }
                } else {
                    parent.Common.dialog({
                        type: "error",
                        text: datas.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    businessTable.isShow = false;
                    businessTable.items = [];
                }
                parent.Common.loading(false);
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
        return this;
    },
    chanelStatistics: function(){
        var self = this;
        var pDefaultColor = ["#ff6666", "#ff66ff", "#6666ff", "#66ccff", "#66ff66", "#ff9933", "#cc66ff", "#fdd342"];
        var para = {"centerid":"","startdate":"","enddate":""};
        if(top.userInfo.centerid != '00000000'){
            para.centerid = top.userInfo.centerid;
        }else{
            para.centerid = filter.cu.selected;
        }
        if(($("#channelAnalysis-top-startTime").val() == '') && ($("#channelAnalysis-top-endTime").val() == '')){
            para.startdate = laydate.now('', 'YYYY-MM-DD');
            para.enddate = laydate.now(-7, 'YYYY-MM-DD');
        }else{
            if($("#channelAnalysis-top-startTime").val() == ''){
                parent.Common.dialog({
                    type: "error",
                    text: '请输入开始日期！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                parent.Common.loading(false);
                return false;
            }
            if($("#channelAnalysis-top-endTime").val() == ''){
                parent.Common.dialog({
                    type: "error",
                    text: '请输入结束日期！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                parent.Common.loading(false);
                return false;
            }
            para.startdate = $("#channelAnalysis-top-startTime").val();
            para.enddate = $("#channelAnalysis-top-endTime").val();
        }
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi10708.json',
            data:para,
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    if(data.rows.length > 0){
                        $("#busAnalysis1").html('');
                        var pText = [];
                        var pChatData = [];
                        var pColor = [];
                        for (var i = 0; i < data.rows.length; i++) {
                            pText.push(data.rows[i].appname);
                            pChatData.push({"value":data.rows[i].count,"name":data.rows[i].appname});
                            pColor.push(pDefaultColor[i]);
                        }
                        self.createPieECharts('busAnalysis1', pColor, pText, pChatData, '业务量',clickFn);
                        //饼图点击事件
                        function clickFn(data) {
                            var nowChannel = data.name;
                            $(".channelAnalysis-tab a").removeClass("on").eq(1).addClass("on");
                            $("#busAnalysis2").stop().show().siblings("div").hide();
                            filter.ch.hide = true;
                            $("#channel option").each(function () {
                                var _this = $(this);
                                if(_this.text().trim() == nowChannel){
                                    filter.ch.selected = _this.val();
                                }
                            });
                            self.getBstatistics(businessTable.isFunds);
                        }
                    }else{
                        $("#busAnalysis1").html('<center style="height:30px;line-height:30px;color:#666;font-size:14px;padding:20px 0;">该条件下暂无数据</center>')
                    }
                }
                parent.Common.loading(false);
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
        return self;
    },
    userDStatistics: function(){
        parent.Common.loading(true);
        var self = this;
        var selectedYear = $("#year").val(),
            selectedMonth = $("#month").val();
        if(top.userInfo.centerid != '00000000') {
            filter.cu.selected = top.userInfo.centerid;
        }
        selectedMonth = parseInt(selectedMonth) < 10 ? '0' + selectedMonth : selectedMonth;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1071003.json',
            data: {
                'centerid': filter.cu.selected,
                'startdate': selectedYear + '-' + selectedMonth
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    threeTab.datas = data.datas;
                    threeTab.ywbl = data.ywbl;
                    threeTab.hdjl = data.hdjl;
                    threeTab.xszj = data.xszj;
                    threeTab.xxfb = data.xxfb;
                    threeTab.xxcx = data.xxcx;
                    $("#busAnalysis3").show();
                    threeTab.isReady = true;
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
            error:function(){
                parent.Common.ajaxError();
                threeTab.isReady = false;
            }
        });
    },
    timeStatistics: function(){
        parent.Common.loading(true);
        var self = this;
        var selectedYear = $("#year").val(),
            selectedMonth = $("#month").val();
        if(top.userInfo.centerid != '00000000') {
            filter.cu.selected = top.userInfo.centerid;
        }
        selectedMonth = parseInt(selectedMonth) < 10 ? '0' + selectedMonth : selectedMonth;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1071004.json',
            data: {
                'centerid': filter.cu.selected,
                'startdate': selectedYear + '-' + selectedMonth
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    fourTab.datas = data.datas;
                    $("#busAnalysis4").show();
                    fourTab.isReady = true;
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
            error:function(){
                parent.Common.ajaxError();
                fourTab.isReady = false;
            }
        });
    },
    exportExcel: function (index) {
        /**
         * 后两个表格的导出
         * @param {Number} index
         * */
        var url = 'webapi1071005.json';
        switch (index) {
            case 3:
                url = 'webapi1071005.json';
                break;
            case 4:
                url = 'webapi1071006.json';
                break;
        }
        if(top.userInfo.centerid != '00000000') {
            filter.cu.selected = top.userInfo.centerid;
        }
        parent.Common.loading(true);
        var self = this;
        var selectedYear = $("#year").val(),
            selectedMonth = $("#month").val();
        selectedMonth = parseInt(selectedMonth) < 10 ? '0' + selectedMonth : selectedMonth;
        location.href = url + '?centerid=' + filter.cu.selected + '&startdate=' + selectedYear + '-' + selectedMonth;
        parent.Common.loading(true);
        $.ajax({
            type:'GET',
            url: url,
            data: {
                'centerid': filter.cu.selected,
                'startdate': selectedYear + '-' + selectedMonth
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    btnClick: function () {
        var self = this,
            busAnalysisSub1 = $("#busAnalysis1"),
            busAnalysisSub2 = $("#busAnalysis2"),
            busAnalysisSub3 = $("#busAnalysis3"),
            busAnalysisSub4 = $("#busAnalysis4"),
            tab = $(".channelAnalysis-tab a");
        tab.on("click", function () {
            var _this = $(this),
                _index = _this.index();
            _this.addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    busAnalysisSub1.stop().show().siblings("div").hide();
                    filter.ch.hide = false;
                    $(".added").hide();
                    $(".before").css("display", "inline-block");
                    self.chanelStatistics();
                    // self.chanelStatistics();
                    break;
                case 1:
                    busAnalysisSub2.stop().show().siblings("div").hide();
                    filter.ch.hide = true;
                    $(".added").hide();
                    $(".before").css("display", "inline-block");
                    // self.getBstatistics(businessTable.isFunds);
                    break;
                case 2:
                    busAnalysisSub3.stop().show().siblings("div").hide();
                    filter.ch.hide = false;
                    $(".added").css("display", "inline-block");
                    $(".before").hide();
                    break;
                case 3:
                    busAnalysisSub4.stop().show().siblings("div").hide();
                    filter.ch.hide = false;
                    $(".added").css("display", "inline-block");
                    $(".before").hide();
                    break;
            }
        });
    }
};
var ue = {
    // 用户体验评价统计
    clientDroplist: null,
    startTime: null,
    endTime: null,
    createDroplist: function () {
        var self = this;
        var droplistData1 = ["客户1", "客户2", "客户3"];
        self.clientDroplist = droplist({
            el: "#channelUE-top-droplist-customerName",
            data: droplistData1,
            selectedChanged: function () {
            }
        });
        return self;
    },
    createDatepicker: function () {
        var self = this;
        laydate({
            elem: '#channelUE-top-startTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate({
            elem: '#channelUE-top-endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.endTime = datas;
            }
        });
        laydate.skin('huanglv');
        return self;
    },
    createECharts: function (el, color, data, items, name) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(el));
        // 指定图表的配置项和数据
        var option = {
            color: color,
            title: {
                text: '用户满意度及数量比例',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: data
            },
            series : [
                {
                    name: name,
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: items,
                    itemStyle: {
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
        return this;
    }
};
var userAnalysis = {
    clientDroplist: null,
    channelDroplist: null,
    startTime: null,
    endTime: null,
    tableObj: null,
    createDroplist: function () {
        var self = this;
        var droplistData1 = ["客户1", "客户2", "客户3"],
            droplistData2 = ["渠道1", "渠道2", "渠道3"];
        self.clientDroplist = droplist({
            el: "#channelAnalysis-top-droplist-customerName",
            data: droplistData1,
            selectedChanged: function () {
            }
        });
        self.channelDroplist = droplist({
            el: "#channelAnalysis-top-droplist-channel",
            data: droplistData2,
            selectedChanged: function () {
            }
        });
        return self;
    },
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
    calcYearAndMonth: function () {
        var self = this;
        var currYear = (new Date()).getFullYear(),
            currMonth = (new Date()).getMonth()+1,
            yearHtml = '',
            monthHtml = '';
        if(currMonth == 1) {
            currYear--;
            currMonth = 13;
        }
        for(var y = 2016; y <= currYear; y++ ) {
            if(y == currYear) {
                yearHtml += '<option value="' + y + '" selected>' + y + '年</option>';
            } else {
                yearHtml += '<option value="' + y + '">' + y + '年</option>';
            }
        }
        for(var m = 1; m < currMonth; m++ ) {
            if(m == currMonth - 1) {
                monthHtml += '<option value="' + m + '" selected>' + m + '月</option>';
            } else {
                monthHtml += '<option value="' + m + '">' + m + '月</option>';
            }
        }
        $("#year").html(yearHtml);
        $("#month").html(monthHtml);
        $("#year").on("change", function () {
            if($(this).val() == currYear) {
                $("#month").html(monthHtml);
                return;
            }
            var tempHtml = '';
            for(var m = 1; m < 13; m++ ) {
                tempHtml += '<option value="' + m + '">' + m + '月</option>';
            }
            $("#month").html(tempHtml);
        });
        return self;
    },
    createLineCanvas: function (data) {
        if(data) {
            data = data;
        } else {
            data = [
                {
                    name: '门户网站',
                    type: 'line',
                    data: [500, 345, 233, 565, 23, 247, 741]
                },
                {
                    name: '网上业务大厅',
                    type: 'line',
                    data: [400, 445, 133, 665, 223, 147, 441]
                },
                {
                    name: '自助终端',
                    type: 'line',
                    data: [600, 345, 433, 765, 213, 157, 451]
                },
                {
                    name: '服务热线',
                    type: 'line',
                    data: [300, 445, 423, 715, 13, 19, 51]
                },
                {
                    name: '手机短信',
                    type: 'line',
                    data: [234, 231, 532, 345, 674, 123, 543]
                },
                {
                    name: '手机客户端',
                    type: 'line',
                    data: [434, 201, 32, 245, 274, 143, 53]
                },
                {
                    name: '微信关注',
                    type: 'line',
                    data: [404, 101, 322, 205, 374, 193, 503]
                },
                {
                    name: '微信绑定',
                    type: 'line',
                    data: [234, 401, 342, 225, 304, 123, 303]
                },
                {
                    name: '官方微博',
                    type: 'line',
                    data: [244, 201, 312, 255, 324, 13, 33]
                }
            ];
        }
        var myChart = echarts.init(document.getElementById("growth-canvas"));
        var option = {
            color: ["#3498db", "#1abc9c", "#2ecc71", "#e6ac22", "#ea2323", "#f02f81", "#9b59b6", "#a4d7f2"],
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                bottom: '0px',
                data: ['门户网站', '网上业务大厅', '自助终端', '服务热线', '手机短信', '手机客户端', '微信关注', '微信绑定', '官方微博']
            },
            xAxis: {
                type: 'category',
                name: '',
                splitLine: {show: false},
                data: ['2016-08-02', '2016-08-03', '2016-08-04', '2016-08-05', '2016-08-06', '2016-08-07', '2016-08-08'],
                boundaryGap: false
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '10%',
                containLabel: true,
                x: "0"
            },
            yAxis: {
                type: 'log',
                name: ''
            },
            series: data
        };
        myChart.setOption(option);
        return this;
    },
    createPieCanvas: function () {
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
                    data:[
                        {value:335, name:'女性'},
                        {value:310, name:'男性'}
                    ],
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
    createBarCanvas: function () {
        var myChart = echarts.init(document.getElementById("age"));
        var option = {
            color: ['#4e96f8'],
            title: {
                text: ''
            },
            tooltip: {},
            xAxis: {
                data: ["门户网站", "网上业务大厅", "自助终端", "服务热线", "手机短信", "手机客户端", "官方微信", "官方微博"]
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
                data: [
                    { value: 60, name: '门户网站' },
                    { value: 42, name: '网上业务大厅' },
                    { value: 72, name: '自助终端' },
                    { value: 32, name: '服务热线' },
                    { value: 42, name: '手机短信' },
                    { value: 52, name: '手机客户端' },
                    { value: 22, name: '官方微信' },
                    { value: 62, name: '官方微博' }
                ]
            }]
        };
        myChart.setOption(option);
        return this;
    },
    btnClick: function () {
        var self = this;
        var data = [
            {
                name: '门户网站',
                type: 'line',
                data: [500, 345, 233, 565, 23, 247, 741]
            },
            {
                name: '网上业务大厅',
                type: 'line',
                data: [400, 445, 133, 665, 223, 147, 441]
            },
            {
                name: '自助终端',
                type: 'line',
                data: [600, 345, 433, 765, 213, 157, 451]
            },
            {
                name: '服务热线',
                type: 'line',
                data: [300, 445, 423, 715, 13, 19, 51]
            },
            {
                name: '手机短信',
                type: 'line',
                data: [234, 231, 532, 345, 674, 123, 543]
            },
            {
                name: '手机客户端',
                type: 'line',
                data: [434, 201, 32, 245, 274, 143, 53]
            },
            {
                name: '微信关注',
                type: 'line',
                data: [404, 101, 322, 205, 374, 193, 503]
            },
            {
                name: '微信绑定',
                type: 'line',
                data: [234, 401, 342, 225, 304, 123, 303]
            },
            {
                name: '官方微博',
                type: 'line',
                data: [244, 201, 312, 255, 324, 13, 33]
            }
        ];
        $(".growth-btns a").on("click", function () {
            var _index = $(this).index();
            $(this).addClass("on").siblings("a").removeClass("on");
            switch (_index) {
                case 0:
                    self.createLineCanvas(data);
                    break;
                case 1:
                    data = [
                        {
                            name: '门户网站',
                            type: 'line',
                            data: [420, 445, 133, 665, 223, 147, 441]
                        },
                        {
                            name: '网上业务大厅',
                            type: 'line',
                            data: [600, 325, 433, 765, 213, 157, 451]
                        },
                        {
                            name: '自助终端',
                            type: 'line',
                            data: [300, 445, 413, 715, 13, 19, 51]
                        },
                        {
                            name: '服务热线',
                            type: 'line',
                            data: [234, 231, 532, 325, 674, 123, 543]
                        },
                        {
                            name: '手机短信',
                            type: 'line',
                            data: [434, 201, 32, 245, 174, 13, 53]
                        },
                        {
                            name: '手机客户端',
                            type: 'line',
                            data: [404, 101, 322, 5, 374, 193, 503]
                        },
                        {
                            name: '微信关注',
                            type: 'line',
                            data: [234, 401, 342, 125, 304, 123, 303]
                        },
                        {
                            name: '微信绑定',
                            type: 'line',
                            data: [44, 201, 312, 255, 324, 13, 33]
                        },
                        {
                            name: '官方微博',
                            type: 'line',
                            data: [500, 305, 233, 565, 23, 247, 741]
                        }
                    ];
                    self.createLineCanvas(data);
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
                    self.createLineCanvas();
                    break;
                case 1:
                    gender.stop().show().siblings("div").hide();
                    self.createPieCanvas();
                    break;
                case 2:
                    age.stop().show().siblings("div").hide();
                    self.createBarCanvas();
                    break;
            }
        });
        return this;
    },
    createTable: function () {
        var self = this;
        var cols = [
            { title:'渠道', name:'test1' ,width:296, align: 'center' },
            { title:'新增人数', name:'test2' ,width:346, align: 'center'},
            { title:'累计人数', name:'test3' ,width:368, align: 'center'}
        ];
        var data = [
            {
                test1: "门户网站",
                test2: "98",
                test3: "12312344"
            },
            {
                test1: "网上业务大厅",
                test2: "928",
                test3: "12123312344"
            },
            {
                test1: "自助终端",
                test2: "28",
                test3: "1212344"
            },
            {
                test1: "服务热线",
                test2: "2128",
                test3: "1212344"
            },
            {
                test1: "手机短信",
                test2: "128",
                test3: "1212344"
            },
            {
                test1: "手机客户端",
                test2: "128",
                test3: "1212344"
            },
            {
                test1: "微信关注",
                test2: "128",
                test3: "1212344"
            },
            {
                test1: "微信绑定",
                test2: "128",
                test3: "1212344"
            },
            {
                test1: "官方微博",
                test2: "128",
                test3: "1212344"
            }
        ];
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
};
$(document).ready(function(){
    if($("#views").length > 0) {
        //parent.Common.loading(true);
        channelAnalysis.createDroplist()
            .createDatepicker()
            .createECharts('views-canvas',
                ["门户网站","网上业务大厅","自助终端","服务热线","手机短信","手机客户端","官方微信","官方微博"],
                "bar",
                [60, 40, 80, 70, 60, 40, 80, 70])
            .btnClick();
    } else if($("#busAnalysis1").length > 0) {
        if(top.userInfo.centerid != '00000000'){
            filter.cu.hide = false;
        }else{
            filter.cu.hide = true;
        }
        filter.ch.hide = false;
        busAnalysis.createDroplist()
            .createDatepicker()
            .btnClick()
    } else if($("#channelUE-top-droplist-customerName").length > 0) {
        ue.createDroplist()
            .createDatepicker()
            .createECharts('channelUE-canvas',
                ["#00cc00", "#b0de09", "#ffd801", "#ff9e01", "#ff6600", "#ccc"],
                ["非常满意", "满意", "基本满意", "一般", "不满意", "未评价"],
                [
                    {value:2000, name:'非常满意'},
                    {value:800, name:'满意'},
                    {value:600, name:'基本满意'},
                    {value:300, name:'一般'},
                    {value:400, name:'不满意'},
                    {value:20, name:'未评价'}
                ],
                '');
    } else if($("#growth").length > 0) {
        userAnalysis.createDroplist()
            .createDatepicker()
            .createLineCanvas()
            .btnClick()
            .createTable();
    }
    userAnalysis.calcYearAndMonth();
});