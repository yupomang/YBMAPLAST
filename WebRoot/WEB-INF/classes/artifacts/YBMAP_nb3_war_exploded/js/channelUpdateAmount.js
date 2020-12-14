/**
 * Created by FelixAn on 2018/4/8.
 */
var user = top.userInfo;
var channelUpdateAmount = {
    startTime: null,
    endTime: null,
    tabler: null,
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
        $("#btnQuery").on("click", function () {
            self.getData(1, self.pageSize, true);
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
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
        return self;
    },
    getData: function(pagenum, pagerows, isResetPage){
        var self = this;
        var startdate = $("#channelAnalysis-top-startTime").val(),
            enddate = $("#channelAnalysis-top-endTime").val(),
            centerid = $("#searchCustomer").val();
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi10715.json',
            data: {
                'centerId': centerid,
                'startdate': startdate,
                'enddate': enddate
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    self.tabler.result = data.result;
                } else {
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    self.tabler.result = [];
                }
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        self.tabler = new Vue({
            el: '#table',
            data: {
                result: data
            }
        });
        parent.Common.loading(false);
    }
};
$(document).ready(function(){
    channelUpdateAmount.createDatepicker()
        .createDroplist()
        .createTable();
});