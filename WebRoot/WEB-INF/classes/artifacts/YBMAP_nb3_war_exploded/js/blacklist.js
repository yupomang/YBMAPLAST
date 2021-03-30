/**
 * Created by M on 2016/9/19.
 */

var user = top.userInfo;
var blacklist = {
    pageSize: 10,
    pager: null,
    stabler: null,
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
            itemLength: 1,
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
            url:'./webapi60702.json',
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
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'name' ,width:40, align:'center' , renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'客户名称', name:'username' ,width:120, align:'center'},
            { title:'身份证号', name:'certinum' ,width:150, align:'center'},
            { title:'手机号', name:'tel' ,width:140, align:'center'},
            { title:'拉黑原因', name:'cause' ,width:220, align:'center'},
            { title:'操作员', name:'loginid' ,width:120, align:'center'},
            { title:'拉黑时间', name:'datecreated' ,width:200, align:'center'},
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
        parent.Common.loading(false);
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
            url:'./webapi60702.json',
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
    del: function () {
        parent.Common.loading(true);
        parent.Common.popupClose();
        
        var self = this,
            selected = self.stabler.selectedRows(),
            centerid = selected[0].centerid,
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].blacklistid);
        }

        $.ajax({
            type: "POST",
            url: "./webapi60701.json",
            data: { 'blacklistid': delIds.join(",")},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData( 1, self.pageSize, true);
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
    
    btnClick:function(){
        var self = this;
        $('#btnquery').off().click(function(){
            self.search();
        });

        $('#orgManage-btn-white').off().click(function(e){
            var selected = self.stabler.selectedRows();

            if(selected.length < 1){
                parent.Common.dialog({
                    type: "error",
                    text: "请选中一条记录进行返回！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });

                return;
            }

            blacklist.del();
        });
    
        $('#btnquery').off().click(function(){
            self.getTableData( 1, self.pageSize, true);            
        });
    

    }
}


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

                blacklist.createPager();
                blacklist.btnClick();
            }
            
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });

});



