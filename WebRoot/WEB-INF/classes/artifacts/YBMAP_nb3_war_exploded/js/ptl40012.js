/**
 * Created by M on 2016/9/28.
 */
var user = top.userInfo;

var pushMessage = {
    tabler:null,
    getTableData: function () {
        var self = this,
        centerid = $('#customerName').val();
        $.ajax({
            type:'POST',
            url:'./ptl40012Query.json',
            data: { 'centerid': centerid},
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
            { title:'中心ID', name:'centerid', width:'102', align: 'center' },
            { title:'中心名称', name:'centername', width:'192', align: 'center'},
            { title:'IOC证书名', name:'certificatename', width:'126', align: 'center'},
            { title:'证书密码', name:'certificatepassword', width:'162', align: 'center'},
            { title:'百度API_KEY', name:'apikey', width:'174', align: 'center'},
            { title:'百度SECRIT_KEY', name:'secritkey', width:'258', align: 'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#sysMgmt-pushMessage-centerViewTable').mmGrid({
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
    btnClick: function () {
        var self = this,
            table = self.tabler;
        // 新建
        $('#sysMgmt-btn-add').off().on("click", function () {
            var addHTML = $(".pushMessage-create").html();
            parent.Common.popupShow(addHTML);

            parent.$('#centerid').val($('#customerName').val());
        });
        //删除
        $("#sysMgmt-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows(),
                centerid = $('#customerName').val();
               
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
                    self.del(delIds.join(","),centerid);
                }
            });
        });
        // 修改
        $('#sysMgmt-btn-edit').click(function(e){
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
            var editHTML = $(".pushMessage-edit").html();
            parent.Common.popupShow(editHTML);

            parent.$('#centerid1').val(selected[0].centerid);
            parent.$('#certificatename1').val(selected[0].certificatename);
            parent.$('#certificatepassword1').val(selected[0].certificatepassword);
            parent.$('#apikey1').val(selected[0].apikey);
            parent.$('#secritkey1').val(selected[0].secritkey);
            
        });
        // 查询
        $('#btnQuery').off().on("click", function () {
            self.getTableData();
        });

    },
    add: function () {
        var self = this;
        var centerid = parent.$('#centerid').val(),
            certificatename = parent.$('#certificatename').val(),
            certificatepassword = parent.$('#certificatepassword').val(),
            apikey = parent.$('#apikey').val(),
            secritkey = parent.$('#secritkey').val();   
        if(certificatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "IOC证书名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(certificatepassword.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "证书密码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(apikey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "百度API_KEY不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(secritkey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "百度SECRIT_KEY不能为空！",
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
            url:'./ptl40012Add.json',
            data: { 
                'opetype': '',
                'centerid':centerid,
                'certificatename':certificatename, 
                'certificatepassword': certificatepassword, 
                'apikey': apikey ,
                'secritkey': secritkey
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData();
                        }
                    });
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
    del:function(ids,centerid){
       parent.Common.loading(true);
        var self = this;
            
        $.ajax({
            type: "POST",
            url: "./ptl40012Del.json",
            data: {
                "centerid": ids
            },
            datatype: "json",
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
    edit: function () {
        var self = this;
        var centerid = parent.$('#centerid1').val(),
            certificatename = parent.$('#certificatename1').val(),
            certificatepassword = parent.$('#certificatepassword1').val(),
            apikey = parent.$('#apikey1').val(),
            secritkey = parent.$('#secritkey1').val(); 

        if(certificatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "IOC证书名不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(certificatepassword.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "证书密码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(apikey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "百度API_KEY不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(secritkey.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "百度SECRIT_KEY不能为空！",
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
            url:'./ptl40012Mod.json',
            data: { 
                'opetype': 'mod',
                'centerid':centerid,
                'certificatename':certificatename, 
                'certificatepassword': certificatepassword, 
                'apikey': apikey ,
                'secritkey': secritkey
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData();
                        }
                    });
                    
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
};

$(function(){
    parent.Common.loading(true);
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

                pushMessage.getTableData();
                pushMessage.btnClick();
            }

        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    

});



