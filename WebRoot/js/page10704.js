/**
 * Created by FelixAn on 2016/10/20.
 */
var page10704 = {
    vuer: null,
    getCenterList: function () {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webappcomCenterId.json',
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    var temp = '';
                    data.mi001list.forEach(function (item) {
                        temp += '<option value="' + item.centerid + '">' + item.centername + '</option>';
                    });
                    $("#custom-name").html(temp);
                    if(top.userInfo.centerid != '00000000') {
                        $("#custom-name").val(top.userInfo.centerid);
                        $("#custom-name").hide();
                        $(".channelAnalysis-top p").eq(0).hide();
                    }
                    self.query($("#custom-name").val(), getNowYYYYMMDD(false), getNowYYYYMMDD(true));
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
            error:function(){
                parent.Common.ajaxError();
            }
        });
        self.createVuer();
        self.bindDateEvent();
        self.bindClick();
        function getNowYYYYMMDD (isNow) {
            var nowDate = new Date(),
                yyyy = nowDate.getFullYear(),
                mm = nowDate.getMonth(),
                dd = nowDate.getDate(),
                str = '';
            if(isNow) {
                mm++;
            } else {
                if (dd < 7) {
                    var month = parseInt(mm,10);
                    var d = new Date(yyyy, month, 0); // 上个月多少天
                    dd = dd - 7 + d.getDate();
                } else {
                    mm++;
                    dd = dd - 7;
                }
            }
            mm = mm > 9 ? mm : "0" + mm;
            dd = dd > 9 ? dd : "0" + dd;
            str = yyyy + '-' + mm + '-' + dd;
            return str;
        }
    },
    createVuer: function () {
        var self = this;
        self.vuer = new Vue({
            el: '#table-vue',
            data: {
                rows: [{
                    appname: '',
                    data: []
                }],
                isNull: false
            }
        });
    },
    bindDateEvent: function () {
        laydate({
            elem: '#channelAnalysis-top-startTime',
            format: 'YYYY-MM-DD',
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
            }
        });
        laydate({
            elem: '#channelAnalysis-top-endTime',
            format: 'YYYY-MM-DD',
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
            }
        });
        laydate.skin('huanglv');
    },
    bindClick: function () {
        var self = this;
        $(".channelAnalysis-top-queryBtn").off().on("click", function () {
            var centerid = $("#custom-name").val(),
                startdate = $("#channelAnalysis-top-startTime").val(),
                enddate = $("#channelAnalysis-top-endTime").val();
            if(startdate.length < 1) {
                parent.Common.dialog({
                    type: "warning",
                    text: '请输入开始时间！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if(enddate.length < 1) {
                parent.Common.dialog({
                    type: "warning",
                    text: '请输入结束时间！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if(enddate < startdate) {
                parent.Common.dialog({
                    type: "warning",
                    text: '结束时间不能比开始时间早！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            self.query(centerid, startdate, enddate);
        });
    },
    query: function (centerid, startdate, enddate) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi1070501.json',
            datatype:'json',
            data: {
                centerid: centerid,
                startdate: startdate,
                enddate: enddate
            },
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    if (data.rows.length > 0) {
                        self.vuer.rows = data.rows;
                        self.vuer.isNull = false;
                    } else {
                        self.vuer.isNull = true;
                    }
                    $("#table-vue").show();
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
                parent.Common.loading(false);
            },
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    }
};
page10704.getCenterList();