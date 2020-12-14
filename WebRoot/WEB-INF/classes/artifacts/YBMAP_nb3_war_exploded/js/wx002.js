/**
 * Created by M on 2016/9/5.
 */

var wx002 = {
    tabler:null,
    getTableData: function () {
        var self = this,
            _data = {'regionId':'00087100'}
        $.ajax({
            type:'POST',
            url:'./weixinapi00202.json',
            data: {'centerid':JSON.stringify(_data)},
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.createTable(data.rows);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
           { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'包名', name:'regionId' ,width:150, align:'center' },
           { title:'类名', name:'funcName' ,width:160, align:'center'},
           { title:'路径', name:'className' ,width:300, align:'center'},
           { title:'KEY', name:'keyname' ,width:150, align:'center'},
           { title:'描述', name:'nickname' ,width:200, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#wx002Table').mmGrid({
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
        var self = this,
            regionId = parent.$("#regionId").val(),
            funcName = parent.$("#funcName").val(),
            className = parent.$("#className").val(),
            keyname = parent.$("#keyname").val(),
            nickname = parent.$("#nickname").val();

        if(regionId.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "包名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(funcName.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "类名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(className.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "路径不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(keyname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "KEY不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(nickname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "描述不能为空！",
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
            url: "./weixinapi00201.json",
            data: { 
                'regionId': regionId, 
                'funcName': funcName, 
                'className': className, 
                'keyname': keyname,
                'nickname' :nickname
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData('');
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
    edit: function () {
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();

        var regionId =  selectedData[0].regionId,

            funcName = parent.$("#funcName").val(),
            className = parent.$("#className").val(),
            keyname = parent.$("#keyname").val(),
            nickname = parent.$("#nickname").val();
            msgUrl = parent.$("#msgUrl").val();

        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./weixinapi00201.json",
            datatype: "json",
            data: { 
                'regionId': regionId, 
                'funcName': funcName, 
                'className': className, 
                'keyname': keyname,
                'nickname' :nickname,
                'msgUrl':msgUrl
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    self.getTableData('');
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
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

        $('#orgManage-btn-add').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
        });
        
        
        $('#orgManage-btn-modify').click(function(e){
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


            parent.$("#regionId1").val(selected[0].regionId),
            parent.$("#funcName1").val(selected[0].funcName),
            parent.$("#className1").val(selected[0].className),
            parent.$("#keyname1").val(selected[0].keyname),
            parent.$("#nickname1").val(selected[0].nickname);
            parent.$("#msgUrl1").val(selected[0].msgUrl);
            
        });
        

        
    }
}





$(function(){

    wx002.getTableData('');
    wx002.btnClick();
    
});












