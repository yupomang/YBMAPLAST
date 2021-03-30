/**
 * Created by M on 2016/9/14.
 */
var user = top.userInfo;
var userManagement = {
    pageSize: 10,
    pager: null,
    stabler: null,
    ctabler: null,
    cacheCenterid: null,
    cacheUsername: null,
    cacheCertinum: null,
    cachePage: null,
    cacheTel: null,
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
        self.getTableData( 1, self.pageSize, true);
    },
    getTableData: function (page, rows, pageRestBool) {
        /*
        *  @pageRestBool 代表是否走重置分页状态，当pageRestBool为true的时候表示走dom，为false的时候表示走cache
        *
        */
        var self = this,
            page = page,
            rows = rows,
            centerid = pageRestBool ? $("#searchCustomer").val() : self.cacheCenterid,
            username = pageRestBool ? $('#username').val() : self.cacheUsername,
            certinum = pageRestBool ? $('#certinum').val() : self.cacheCertinum,
            tel = pageRestBool ? $('#tel').val() : self.cacheTel;
            parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi02904.json',
            data: { 
                'centerid': centerid,
                'personalname': username,
                'certinum': certinum,
                'tel': tel,
                'page': page, 
                'rows': rows 
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                    self.cachePage = page;
                    self.cacheCenterid = centerid;
                    self.cacheUsername = username;
                    self.cacheCertinum = certinum;
                    self.cacheTel = tel;
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
    },
    search: function () {
        var self = this,
            page = 1,
            rows = 10,
            centerid = $('#searchCustomer').val(),
            username = $('#username').val(),
            certinum = $('#certinum').val(),
            tel = $('#tel').val();
            parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi02904.json',
            data: { 
                'centerid': centerid,
                'personalname': username,
                'certinum': certinum,
                'tel': tel,
                'page': page, 
                'rows': rows 
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: true
                    });
                    self.cachePage = page;
                    self.cacheCenterid = centerid;
                    self.cacheUsername = username;
                    self.cacheCertinum = certinum;
                    self.cacheTel = tel;
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
    },
    createTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'name' ,width:130, align:'center' , renderer: function (val, item, index) {
                return index+1;
            }},
           { title:'客户名称', name:'username' ,width:300, align:'center' },
           { title:'身份证号', name:'certinum' ,width:300, align:'center'},
           { title:'手机号', name:'tel' ,width:240, align:'center'}
        ];
        if(self.stabler != null) {
            self.stabler.load(data);
        } else {
            self.stabler = $('#channelSetTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
                    height: '390',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        // 如果是消息群发调用的弹出界面，绑定table点击事件
        self.stabler.off("cellSelected").on("cellSelected", function (e, item) {
            if(typeof item == 'undefined') return;
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "tips",
                text: "您选择了\"" + item.username + "\"，是否发送预览信息？",
                okShow: true,
                cancelShow: true,
                okText: "是",
                cancelText: "否",
                ok: function () {
                    top.frames['main'].bulkMessage.sendPerview(item.certinum);
                },
                cancel: function () {
                    parent.$("#popup-container").show();
                }
            });
        });
        parent.Common.loading(false);
    },
    btnClick:function(){
        var self = this;
        $('#btnquery').click(function(){
            self.search();
        });
    }
};


$(function(){
    $.ajax({
        type:'POST',
        url:'./webappcomCenterId.json',
        data:{centerid:user.centerid},
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
                userManagement.createPager();
                userManagement.btnClick();
            }

        },
        error:function(){
            parent.Common.ajaxError();
        }

        
    });


});



