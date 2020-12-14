/**
 * Created by FelixAn on 2018/4/8.
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
            hide: false,
            selected: '',
            options: [{
                "text":"",
                "value":""
            }]
        }

    },
    methods: {
        filter: function(){
            customerSatisfaction.getData();
        }
    }
});
var customerSatisfaction = {
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
        return this;
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
        for(var y = 2017; y <= currYear; y++ ) {
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
                        $(".cn").prop('disabled', true);
                    }
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
        return self;
    },
    getData: function(){
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
            url:'./webapi50050.json',
            data: {
                'centerId': filter.cu.selected,
                'startdate': selectedYear + selectedMonth
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    $("#totalnum").text("总笔数:" + data.totalnum + ', 用户满意度:' + (100 - Math.round(data.failsum / data.totalnum * 10000) / 100.00 + "%"));
                    self.createPieCanvas([
                        {
                            value: data.succsum,
                            name: '非常满意'
                        },
                        {
                            value: data.counts,
                            name: '满意'
                        },
                        {
                            value: data.failsum,
                            name: '不满意'
                        }
                    ]);
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
            }
        });
    },
    createPieCanvas: function (data) {
        var myChart = echarts.init(document.getElementById("canvas"));
        var option = {
            color: ["#ed7056", "#52d1b5", "#22CF66"],
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
                data: ['非常满意','满意', '不满意']
            },
            series : [
                {
                    name: '笔数',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%', '50%'],
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
    }
};
$(document).ready(function(){
    if(top.userInfo.centerid != '00000000'){
        filter.cu.hide = false;
    }else{
        filter.cu.hide = true;
    }
    filter.ch.hide = false;
    customerSatisfaction.createDroplist()
        .setInputDefault()
        .calcYearAndMonth();
});