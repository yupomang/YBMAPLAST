/**
 * Created by FelixAn on 2018/4/8.
 */
var user = top.userInfo;
var hotSort = {
    startTime: null,
    endTime: null,
    pager: null,
    tabler: null,
    pageSize: 10,
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#hotSort-pager",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                var page = pageIndex,
                    row = pageSize;
                self.pageSize = pageSize;
                self.getData(page, row, false);
            }
        });
        return this;
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
            coreflag = $("#questionType").val(),
            centerid = $("#searchCustomer").val();
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi50052.json',
            data: {
                'centerId': centerid,
                'startdate': startdate,
                'enddate': enddate,
                'coreflag': coreflag,
                'ispaging': 1,
                'pagenum': pagenum,
                'pagerows': pagerows
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    self.createTable(data.detail);
                    self.pager.reset({
                        itemLength: data.consum,
                        pageSize: data.pageSize,
                        reset: isResetPage
                    });
                } else {
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    self.createTable([]);
                    self.pager.reset({
                        itemLength: 0,
                        pageSize: self.pageSize,
                        reset: true
                    });
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
        var cols = [
            { title:'序号', name:'centerid' ,width:190, align:'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            // { title:'交易日期', name:'transdate' ,width:268, align:'center'},
            { title:'问题描述', name:'paramdes' ,width:488, align:'center'},
            { title:'个数', name:'totalnum' ,width:338, align:'center'}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#hotSort-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
                height: '460px',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
    }
};
$(document).ready(function(){
    hotSort.createPager()
        .createDatepicker()
        .createDroplist();
});