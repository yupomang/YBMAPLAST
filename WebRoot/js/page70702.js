/**
 * Created by M on 2016/10/24.
 */
var user = top.userInfo;
var msgTheme = {
    pageSize: 10,
    pager: null,
    tabler:null,
    tableData:null,
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
        var self = this,
            page = page,
            rows = rows,
            centerid = $('#searchCustomer').val(),
            itemval = $('#itemval').val(),
            servicename = $('#servicename').val();
        $.ajax({
            type:'POST',
            url:'./webapi70904.json',
            data: { 
                centerid:centerid,
                itemval:itemval,
                servicename:servicename,
                page:page,
                rows:rows
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
                }else {
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
        self.tableData = data;
        var cols = [
           { title:'序号', name:'id' ,width:80, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
           { title:'客户名称', name:'centerid' ,width:300, align:'center', renderer: function (val, item, index) {
                return $("#searchCustomer option[value="+val+"]").text();
            }},
           // { title:'栏目id', name:'itemid' ,width:80, align:'center'},
           { title:'栏目名称', name:'itemval' ,width:290, align:'center'},
           // { title:'服务id', name:'serviceid' ,width:80, align:'center'},
           { title:'服务名称', name:'servicename' ,width:320, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#setTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
                    height: 'auto',
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
        var self = this;
        var centerid = parent.$('#centerid').val(),
            itemid = parent.$('#lanname').val(),
            itemval = parent.$("#lanname option:selected").text(),
            serviceid = parent.$('#servname').val(),
            servicename = parent.$("#servname option:selected").text();
        if(centerid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "客户名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "消息主题描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(serviceid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "消息主题描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi70901.json',
            data: { 
                'centerid':centerid,
                'itemid':itemid,
                'itemval':itemval,
                'serviceid':serviceid,
                'servicename':servicename
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1, self.pageSize,true);
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
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].seqno);
        }
        $.ajax({
            type: "POST",
            url: "./webapi70902.json",
            data: { 'seqno': delIds.join(",") },
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
    edit: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            centerid = parent.$('#centerid1').val(),
            itemid = parent.$('#lanname1').val(),
            itemval = parent.$("#lanname1 option:selected").text(),
            serviceid = parent.$('#servname1').val(),
            servicename = parent.$("#servname1 option:selected").text(),
            seqno = selected[0].seqno;
        if(centerid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "客户名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "消息主题描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(serviceid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "消息主题描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi70903.json',
            data: { 
                'seqno':seqno,
                'centerid':centerid,
                'itemid':itemid,
                'itemval':itemval,
                'serviceid':serviceid,
                'servicename':servicename
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1, self.pageSize,true);
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
            self.getTableData( 1, self.pageSize, true);
            self.getLansList($('#searchCustomer').val());
        });

        $('#orgManage-btn-add').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);

            parent.$('#centerid').change(function(){
                self.getLansList($(this).val());
            });
        });

        $('#orgManage-btn-del').click(function(e){
            var selected = $("#setTable").find("tr.selected");
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "warning",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });

        // 编辑
        $('#orgManage-btn-modify').click(function(e){
            var  selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            } 
            if(selected.length>1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            

            var createHTML2 = $(".orgManage-popup-edit-container1").html();
            parent.Common.popupShow(createHTML2);

            parent.$('#centerid1').val(selected[0].centerid);
            parent.$('#lanname1').val(selected[0].itemid);
            parent.$('#servname1').val(selected[0].serviceid);

            parent.$('#centerid1').change(function(){
                self.getLansList($(this).val());
            });
            
        });

    },
    getLansList:function(centerid){
        // 获取栏目列表
        $.ajax({
            type:'POST',
            url:'./webapi70705.json',
            datatype:'json',
            data:{'centerid':centerid},
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    var customerOptions = '<option value="">请选择</option>';
                    for(var i = 0;i<data.rows.length;i++){
                        customerOptions += '<option value="'+data.rows[i].itemid+'">'+data.rows[i].itemval+'</option>';
                    }

                    $('#lanname,#lanname1').html(customerOptions);
                    parent.$('#lanname,#lanname1').html(customerOptions);
                }
                
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });

        // 获取服务列表
        $.ajax({
            type:'POST',
            url:'./webapi05106.json',
            datatype:'json',
            data:{'centerid':centerid},
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    var customerOptions = '<option value="">请选择</option>';
                    for(var i = 0;i<data.rows.length;i++){
                        customerOptions += '<option value="'+data.rows[i].serviceid+'">'+data.rows[i].servicename+'</option>';
                    }

                    $('#servname,#servname1').html(customerOptions);
                    parent.$('#servname,#servname1').html(customerOptions);
                }
                
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },

}
   


$(function(){

    $.ajax({
        type:'POST',
        url:'./webappcomCenterId.json',
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi001list.length;i++){
                    customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                }

                $('#searchCustomer,#centerid,#centerid1').html(customerOptions);

                if(user.centerid != '00000000'){
                    $('#searchCustomer,#centerid,#centerid1').hide();

                }
            }
            
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    
    

    msgTheme.createPager();
    msgTheme.btnClick();
    msgTheme.getLansList(user.centerid);

});





















