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
           { title:'序号', name:'name' ,width:40, align:'center' , renderer: function (val, item, index) {
                return index+1;
            }},
           { title:'客户名称', name:'username' ,width:200, align:'center' },
           { title:'身份证号', name:'certinum' ,width:200, align:'center'},
           { title:'手机号', name:'tel' ,width:140, align:'center'},
           { title:'级别', name:'level' ,width:150, align:'center', renderer: function (val, item, index) {
                if(val == '1'){
                    return '普通用户';
                }else{
                    return '高级用户';
                }
            }},
           { title:'VIP', name:'vip' ,width:120, align:'center', renderer: function (val, item, index) {
                if(val == '1'){
                    return '是';
                }else{
                    return '否';
                }
            }},
           { title:'敏感用户', name:'sensitive' ,width:120, align:'center', renderer: function (val, item, index) {
                if(val == '1'){
                    return '是';
                }else{
                    return '否';
                }
            }},
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
    createChannelTable: function (data) {
        var self = this;
        var cols = [
           { title:'序号', name:'name' ,width:50, align:'center' , renderer: function (val, item, index) {
                return index+1;
            }},
           { title:'用户ID', name:'userid' ,width:200, align:'center'},
           { title:'昵称', name:'nickname' ,width:200, align:'center'},
           { title:'渠道名称', name:'channelname' ,width:200, align:'center'},
           { title:'应用名称', name:'appname' ,width:200, align:'center'},
           { title:'推送信息', name:'sendmessage' ,width:120, align:'center', renderer: function (val, item, index) {
                if(val == '1'){
                    return '同意';
                }else{
                    return '不同意';
                }
            }}
        ];
        if(self.ctabler != null) {
            self.ctabler.load(data);
        } else {
            
            self.ctabler = $('#channelInfo').mmGrid({
                    multiSelect: false,// 多选
                    checkCol: false, // 选框列
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
    add: function () {
        var self = this,
            selected = self.stabler.selectedRows(),
            personalid = selected[0].personalid,
            vip = parent.$('#vip').prop("checked") ? '1' : '0',
            sensitive = parent.$('#sensitive').prop("checked") ? '1' : '0';

        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi02903.json',
            data: { 
                'personalid': personalid,
                'vip':vip,  
                'sensitive':sensitive
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                parent.Common.loading(false);
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(1, self.pageSize,true);
                        }
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
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    del: function () {
        var cause = parent.$('#cause').val();
        if(cause == ''){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
                text: "请填写加入黑名单的理由！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });

            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        
        var self = this,
            selected = self.stabler.selectedRows(),
            centerid = selected[0].centerid,
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].personalid);
        }

        $.ajax({
            type: "POST",
            url: "./webapi02906.json",
            data: { 'personalid': delIds.join(",") ,'centerid':centerid,'cause':cause},
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
    showChannelInfo:function(personalid){
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi02905.json',
            data: { 
                'personalid': personalid
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createChannelTable(data.rows);
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
    btnClick:function(){
        var self = this;
        $('#btnquery').click(function(){
            self.search();
        });

        $('#orgManage-btn-set').click(function(e){
            var selected = self.stabler.selectedRows();
                

            if(selected.length != 1){
                parent.Common.dialog({
                    type: "error",
                    text: "请选中一条记录进行设置！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });

                return;
            }

            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);

            parent.$('#name').text(selected[0].username);
            if(selected[0].vip == '1'){
                parent.$('input:checkbox').eq(0).attr("checked",'true');
            }else{
                $("input:checkbox").eq(0).remove();
            }
            if(selected[0].sensitive == '1'){
                parent.$('input:checkbox').eq(1).attr("checked",'true');
            }else{
                $("input:checkbox").eq(0).remove();
            }


        });
        $('#orgManage-btn-query').click(function(e){
            var selected = self.stabler.selectedRows();
                
            if(selected.length != 1){
                parent.Common.dialog({
                    type: "error",
                    text: "请选中一条记录进行查看！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });

                return;
            }
            var id = selected[0].personalid;
            self.showChannelInfo(id);

            $('#userCh').show();
            $('#main').hide();
        });
    
        $('#orgManage-btn-black').click(function(){
            var selected = self.stabler.selectedRows();
                
            if(selected.length < 1){
                parent.Common.dialog({
                    type: "error",
                    text: "请选择要加入黑名单的用户！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });

                return;
            }

            var createHTML = $(".orgManage-popup-edit-container2").html();
            parent.Common.popupShow(createHTML);

        });

        $('#orgManage-info-goBack').click(function(){
            $('#userCh').hide();
            $('#main').show();
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
                userManagement.createPager();
                userManagement.btnClick();
            }

        },
        error:function(){
            parent.Common.ajaxError();
        }

        
    });


});



