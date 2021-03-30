
var channelObj = [];
var cols = [
   { title:'序号', name:'channel' ,width:100, align:'center', renderer: function (val, item, index) {
       return index + 1;
   } },
   { title:'渠道类型', name:'channel' ,width:300, align:'center' , renderer: function (val, item, index) {
       for(var i = 0;i<channelObj.length;i++){
        if(item.channel == channelObj[i].itemid){
            return channelObj[i].itemval;
        }
    }
   }},
    // 2017-8-29 暂时隐藏
/*   { title:'用户量', name:'countUser' ,width:175, align:'center'},*/

   // 静态数据用
   // { title:'用户量', name:'channel' ,width:175, align:'center', renderer: function (val, item, index) {
   //     var t = ''
   //     switch (val) {
   //              case '10':
   //                  t = '1';
   //                  break;
   //              case '20':
   //                  t = '7';
   //                  break;
   //             case '30':
   //                  t = '11';
   //                  break;
   //              case '40':
   //                  t = '3';
   //                  break;
   //              case '50':
   //                  t = '9';
   //                  break;
   //              case '60':
   //                  t = '4';
   //                  break;
   //              case '70':
   //                  t = '6';
   //                  break;
   //              case '80':
   //                  t = '2';
   //                  break;
   //          }
   //      return t;
   // }},
   // { title:'业务量', name:'countBusinesses' ,width:170, align:'center'},
   { title:'访问量', name:'countBusinesses' ,width:245, align:'center'},//20161109 暂时替换为访问量上限

   // 静态数据用
   // { title:'业务量', name:'channel' ,width:170, align:'center', renderer: function (val, item, index) {
   //     var t = ''
   //     switch (val) {
   //              case '10':
   //                  t = '19';
   //                  break;
   //              case '20':
   //                  t = '22';
   //                  break;
   //             case '30':
   //                  t = '25';
   //                  break;
   //              case '40':
   //                  t = '36';
   //                  break;
   //              case '50':
   //                  t = '29';
   //                  break;
   //              case '60':
   //                  t = '14';
   //                  break;
   //              case '70':
   //                  t = '16';
   //                  break;
   //              case '80':
   //                  t = '8';
   //                  break;
   //          }
   //      return t;
   // }},
   { title:'操作', name:'channel' ,width:140, align:'center', renderer: function (val, item, index) {
       return '<a href="javascript:;" onclick="flowMonitor.map(\' ' + val + ' \')" style="color:#666;padding:3px 10px;border:solid 1px #ddd;border-radius:3px;">查看历史</a>';
   }},
];

var mmg = $('#channelStateTable').mmGrid({
    multiSelect: false,// 多选
    checkCol: false, // 选框列
	height: '200px',
	cols: cols,
	items: [],
	loadingText: "loading...",
	noDataText: "暂无数据。",
	loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
	sortable: false
});



$('#orgManage-btn-add').click(function(e){
	var createHTML = $(".orgManage-popup-edit-container").html();
	parent.Common.popupShow(createHTML);
});
$('#orgManage-btn-modify').click(function(e){
	var selected = $("#channelStateTable").find("tr.selected");
	var valBuiCode		= selected.find('td:eq(1)').text(),
		valBuiArae		= selected.find('td:eq(2)').text(),
		valBuiName		= selected.find('td:eq(3)').text(),
		valBuiDeve		= selected.find('td:eq(4)').text(),
		valBuiAddr		= selected.find('td:eq(5)').text(),
		valBuiType		= selected.find('td:eq(6)').text(),
		valBuiContacter	= selected.find('td:eq(7)').text(),
		valBuiTel		= selected.find('td:eq(8)').text();
	if(selected.length < 1) {
        parent.Common.dialog({
            type: "error",
            text: "至少选中一条记录！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    } 
    if(selected.length>1){
    	parent.Common.dialog({
            type: "error",
            text: "选多啦！只能选中一条！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    }
    if(selected.length == 1) {
    	
    	$('#buiCode1').attr("value", valBuiCode);
		$('#buiArea1').attr("value",valBuiArae);
		$('#buiName1').attr("value",valBuiName);
		$('#buiDeve1').attr("value",valBuiDeve);
		$('#buiAddr1').attr("value",valBuiAddr);
		$('#buiType1').attr("value",valBuiType);
		$('#buiContacter1').attr("value",valBuiContacter);
		$('#buiTel1').attr("value",valBuiTel);
    	
        var createHTML2 = $(".orgManage-popup-edit-container1").html();
		parent.Common.popupShow(createHTML2);
    }
});

$('#orgManage-btn-del').click(function(){
	var selected = $("#channelStateTable").find("tr.selected");
	parent.Common.dialog({
        type: "warning",
        text: "确定删除选中项？",
        okShow: true,
        cancelShow: false,
        okText: "确定",
        ok: function () {
        	parent.$(selected).slideUp(0);
        }
    });
});

// $('.jtree a').click(function(){
// 	$(this).addClass('on').siblings('a').removeClass('on');
// });
// $('.jtree a:first').addClass('on');



var flowMonitor = {
    tree: null,
    startTime: null,
    endTime: null,
    channel: null,
    checkedCenterid: null,
    timer: null,
    hasTimer: false,
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
        self.createDatepicker();
        self.btnClick();
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
                    self.hasTimer = false;
                    self.getData(id);
                }
            }
        });
        if(centers.length > 0) {
            self.getData(centers[0].centerid);
        }
        parent.Common.loading(false);
    },
    getData: function (id) {
        var self = this;
        parent.Common.loading(true);
        if(self.hasTimer) {
            top.$("body").append("<p id='timerTips' style='position:fixed;z-index:9999;width:100%;text-align:center;font-size:20px;color:#e7ebef;top:50%;margin-top:30px;'>正在获取最新渠道流量......</p>");
        }
        $.ajax({
            type: "POST",
            url: "./webapi04606.json",
            datatype: "json",
            data: { 'centerid': id },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                parent.$("#timerTips").remove();
                if (data.recode == "000000") {
                    $('.userStatbg1 p').text(data.rows.userFlowTop);
                    $('.userStatbg2 p').text(data.rows.bussinessflowTOP);
                    $('.userStatbg3 p').text(data.rows.userFlow);
                    $('.userStatbg4 p').text(data.rows.bussinessflow);
                    
                    // 静态数据用
                    // $('.userStatbg3 p').text('32');
                    // $('.userStatbg4 p').text('169');
                    mmg.load(data.rows.channelbusinesses);
                    parent.Common.loading(false);
                    self.timer = setTimeout(function () {
                        clearTimeout(self.timer);
                        self.hasTimer = true;
                        self.getData(self.checkedCenterid);
                    }, 30000);
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
                    // if there is no data
                    mmg.load([]);
                    $('.userStatbg1 p').text(0);
                    $('.userStatbg2 p').text(0);
                    $('.userStatbg3 p').text(0);
                    $('.userStatbg4 p').text(0);
                }
                self.checkedCenterid = id;
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    map: function (channel, startTime, endTime) {
        var self = this;
        $(".statTitle, .statCont").show();
        self.channel = channel;
        if(!startTime) {
            endTime = new Date();
            var timestamp, newDate;
            if(!(endTime instanceof Date)){
                endTime = new Date(endTime.replace(/-/g, '-'));
            }
            timestamp = endTime.getTime();
            newDate = new Date(timestamp - 7 * 24 * 3600 * 1000);
            startTime =  [[newDate.getFullYear(), (newDate.getMonth() + 1) < 10 ? "0"+ (newDate.getMonth() + 1) : (newDate.getMonth() + 1), newDate.getDate()].join('-'), [newDate.getHours(), newDate.getMinutes(), newDate.getSeconds()].join(':')].join(' ');
            endTime = [[endTime.getFullYear(), (endTime.getMonth() + 1) < 10 ? "0"+ (endTime.getMonth() + 1) : (endTime.getMonth() + 1), endTime.getDate()].join('-'), [endTime.getHours(), endTime.getMinutes(), endTime.getSeconds()].join(':')].join(' ');
        }
        console.log(startTime);
        $.ajax({
            type: "POST",
            url: "./webapi04605.json",
            datatype: "json",
            data: { 'centerid': self.tree.select, 'channel': channel, 'startTime': startTime, 'endTime': endTime },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var date = [],
                        userData = [],
                        busData = [];
                    data.rows.forEach(function (val) {
                        date.push(val.datecreated);
                        userData.push(val.countuser);
                        busData.push(val.countbusiness);
                    });
                    self.createLineCanvas([
                        {
                            name: '用户量',
                            type: 'line',
                            data: userData
                        },
                        {
                            name: '业务量',
                            type: 'line',
                            data: busData
                        }
                    ], date);
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
    createLineCanvas: function (data, date) {
        if(date.length < 1) {
            document.getElementById("mainExcel").innerHTML = "暂无数据。";
            return;
        }
        var myChart = echarts.init(document.getElementById("mainExcel"));
        var option = {
            color: ["#3498db", "#1abc9c"],
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                bottom: '0px',
                data: ['用户量', '业务量']
            },
            axisLabel: {
                formatter:function(val){
                    if(typeof val == 'string'){
                        val = val.substr(0, val.length - 4);
                        return val.split(" ").join("\n");
                    } else {
                        return val;
                    }
                }
            },
            xAxis: {
                type: 'category',
                name: '',
                splitLine: {show: false},
                data: date,
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
                type: 'value',
                name: ''
            },
            series: data
        };
        myChart.setOption(option);
        return this;
    },
    createDatepicker: function () {
        var self = this;
        var start = {
            elem: '#query-start-time',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            max: '2099-06-16 23:59:59',
            choose: function(datas){ //选择日期完毕的回调
                end.min = datas;
                self.startTime = datas;
            }
        };
        var end = {
            elem: '#query-end-time',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            min: '1999-06-16 23:59:59',
            choose: function(datas){ //选择日期完毕的回调
                self.endTime = datas;
                start.max = datas;
            }
        };
        laydate(start);
        laydate(end);
        laydate.skin('huanglv');
    },
    btnClick: function () {
        var self = this;
        $("#query-btn").off().on("click", function () {
            self.map(self.channel, self.startTime, self.endTime);
        });
    },
    getChannelList: function(){
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
                    for(var i =0;i<data.mi007list.length;i++){
                        channelObj.push(data.mi007list[i]);
                    }
                }

            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    }
};
parent.Common.loading(true);
flowMonitor.getCenterList();
flowMonitor.getChannelList();