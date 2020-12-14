/**
 * Created by M on 2016/9/27.
 */
var user = top.userInfo;

var interfaceConfig = {
    tabler:null,
    getTableData: function () {
        var self = this,
        centerid = $('#customerName').val();
        $.ajax({
            type:'POST',
            url:'./ptl40007Query.json',
            data: { 
                'centerid': centerid
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
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
            { title:'中心ID', name:'centerid', width:'180', align: 'center' },
            { title:'中心名称', name:'centername', width:'202', align: 'center'},
            { title:'通讯接口类', name:'classname', width:'226', align: 'center'},
            { title:'HTTP通讯URL', name:'url', width:'418', align: 'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#centerViewTable').mmGrid({
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
    edit: function () {
        var self = this,
            centerid = parent.$('#centerid1').val(),
            classname = parent.$("#classname1").val(),
            url = parent.$("#url1").val();

        if(classname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "通讯接口类名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(url.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "通讯URL不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./ptl40007Mod.json",
            data: { 
                'opetype':'mod',
                'centerid': centerid, 
                'classname': classname, 
                'url': url
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData();
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
        parent.Common.popupClose();
    },
    add: function () {
        var self = this,
            centerid = parent.$('#centerid').val(),
            classname = parent.$("#classname").val(),
            url = parent.$("#url").val();
        if(classname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "通讯接口类名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(url.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "通讯URL不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./ptl40007Add.json",
            data: { 
                'opetype':'add',
                'centerid': centerid, 
                'classname': classname, 
                'url': url
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData();
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
        parent.Common.popupClose();
    },
    del: function (delIds) {
        var self = this;
            
        $.ajax({
            type: "POST",
            url: "./ptl40007Del.json",
            data: { 'centerid': delIds.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData();
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
    btnClick: function () {
        var self = this;

        $("#sysMgmt-btn-add").on("click", function () {
            var addHTML = $(".interfaceConfig-create").html();
            parent.Common.popupShow(addHTML);

            parent.$('#centerid').val($('#customerName').val());
        });
        $("#sysMgmt-btn-edit").on("click", function () {
            var selected = self.tabler.selectedRows();

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
            var addHTML = $(".interfaceConfig-edit").html();
            parent.Common.popupShow(addHTML);

            parent.$('#centerid1').val(selected[0].centerid);
            parent.$('#classname1').val(selected[0].classname);
            parent.$('#url1').val(selected[0].url);
        });
        $("#sysMgmt-btn-del").on("click", function () {
            var selected = self.tabler.selectedRows();

            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择一条记录进行删除！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    var delIds = [];
                    for(var i=0; i<selected.length; i++) {
                        delIds.push(selected[i].centerid);
                    }
                    self.del(delIds);
                }
            });
        });
        $(".btnquery").on("click", function () {
            self.getTableData();
        });
    }

};
$(document).ready(function(){
    $.ajax({
        type:'POST',
        url:'./ptl40003Qry.json',
        data:{'centerid':user.centerid},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '';
                for(var i = 0;i<data.rows.length;i++){
                    customerOptions += '<option value="'+data.rows[i].centerid+'">'+data.rows[i].centername+'</option>';
                    
                }
                $('#customerName').html(customerOptions);
                interfaceConfig.getTableData();
                interfaceConfig.btnClick();
            }

        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
});