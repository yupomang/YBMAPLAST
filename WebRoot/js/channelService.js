/**
 * Created by M on 2016/8/31.
 * updated by afx on 2017/7/28
 */
var _serviceid = '';
var pageSizeTemp = 10;
var servicetypeArr = [];

var channelServer = {
    pageSize: 10,
    pager: null,
    tabler:null,
    stabler: null,
    cacheUserLevel: null,
    cacheCurrentPage: 1,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#orgManage-table-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                pageSizeTemp = pageSize;
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
        self.getUserLevel();
    },
    getUserLevel: function () {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./getCustomLevel.json',
            data: {},
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var tempHtml = '<option value="">请选择</option>';
                    data.customlevel.forEach(function (val) {
                        tempHtml += '<option value="' + val.itemid + '">' + val.itemval + '</option>';
                    });
                    $("#level,#level1").empty().html(tempHtml);
                    self.cacheUserLevel = data.customlevel;
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
                self.getTableData( 1, self.pageSize, true);
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
        page = page,
        rows = rows,
        servicename = $('#query_sn').val();
        $.ajax({
            type:'POST',
            url:'./webapi05104.json',
            data: { 
                'servicename': servicename,
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
                    self.cacheCurrentPage = page;
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
           { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'服务名称', name:'servicename' ,width:120, align:'center' },
           { title:'服务描述', name:'servicemsg' ,width:130, align:'center' },
           { title:'服务类型', name:'servicetype' ,width:80, align:'center', renderer: function (val) {
               var tempVal = '',
                   hasValue = false;
               servicetypeArr.forEach(function (item) {
                   if(item.itemid == val) {
                       tempVal = item.itemval;
                       hasValue = true;
                   } else if(!hasValue) {
                       tempVal = val;
                   }
               });
               return tempVal;
           }},
           { title:'二级服务名称', name:'subservicetypename' ,width:120, align:'center', renderer: function (val) {
               var tempVal = '-';
               if(val.length > 0 || val != null) return val;
               return tempVal;
           }},
           { title:'客户级别', name:'uselevel' ,width:100, align:'center',renderer: function(val ,item, index){
               var temp = "-";
               self.cacheUserLevel.forEach(function (val) {
                   if(item.uselevel == val.itemid) {
                       temp = val.itemval;
                   }
               });
               return temp;
           }},
           { title:'服务状态', name:'status' ,width:60, align:'center',renderer: function(val ,item, index){
                if(item.status == '1'){
                    return '正常';
                }
                if(item.status == '0'){
                    return '关闭';
                }
           }},
           { title:'资金类标识', name:'moneytype' ,width:100, align:'center',renderer: function(val ,item, index){
                if(item.moneytype == '1'){
                    return '是';
                }
                if(item.moneytype == '0'){
                    return '否';
                }
           }},
           { title:'是否取缓存', name:'opencouch' ,width:100, align:'center',renderer: function(val ,item, index){
                if(item.opencouch == '1'){
                    return '是';
                }
                if(item.opencouch == '0'){
                    return '否';
                }
           }},
           { title:'缓存时间（秒）', name:'couchtime' ,width:80, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#channelSetTable').mmGrid({
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
    add: function () {
        var self = this;
        var servicename = parent.$('#servicename').find("option:selected").text(),
            buztype = parent.$('#servicename').val(),
            servicemsg = parent.$('#servicemsg').val(),
            servicetype = parent.$('#servicetype').val(),
            uselevel = parent.$('#level').val(),
            status = parent.$('.radioWrap').find('input[name="status"]:checked').val(),
            moneytype = parent.$('.radioWrap').find('input[name="moneytype"]:checked').val(),
            opencouch = parent.$('.radioWrap').find('input[name="opencouch"]:checked').val(),
            couchtime = parent.$('#couchtime').val(),
            subservicetype = parent.$("#subservicetype").val(); // 二级服务类型
        if(servicename == '请选择') {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "服务名称不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(servicemsg.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "服务描述不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(servicetype == "") {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "服务类型不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(subservicetype == "") {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "二级服务类型不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(uselevel.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "客户级别不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(opencouch == 1 && couchtime.length < 1){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "缓存时间不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(isNaN(couchtime)){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请输入正确的缓存时间！",
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
        $.ajax({
            type:'POST',
            url:'./webapi05101.json',
            data: { 
                'servicename': servicename,
                'buztype':buztype,  
                'servicemsg':servicemsg,
                'servicetype':servicetype, 
                'uselevel': uselevel,
                'status': status ,
                'moneytype': moneytype, 
                'opencouch': opencouch, 
                'couchtime': couchtime,
                'freeuse1': subservicetype // 二级服务类型
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
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].serviceid);
        }
        $.ajax({
            type: "POST",
            url: "./webapi05102.json",
            data: { 'serviceid': delIds.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData( self.cacheCurrentPage, self.pageSize, false);
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
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();

        var serviceid =  selectedData[0].serviceid,
            apiname = parent.$('#servicename1').val(),
            buztype = selectedData[0].buztype,
            servicemsg = parent.$('#servicemsg1').val(),
            servicetype = parent.$('#servicetype1').val(),
            uselevel = parent.$('#level1').val(),
            status = parent.$('.radioWrap').find('input[name="status1"]:checked').val(),
            moneytype = parent.$('.radioWrap').find('input[name="moneytype1"]:checked').val(),
            opencouch = parent.$('.radioWrap').find('input[name="opencouch1"]:checked').val(),
            couchtime = parent.$('#couchtime1').val(),
            subservicetype = parent.$("#subservicetype1").val(); // 二级服务类型;
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi05103.json",
            datatype: "json",
            data: { 
                'serviceid':serviceid,
                'apiname': apiname,
                'buztype': buztype,
                'servicemsg': servicemsg,
                'servicetype': servicetype,
                'uselevel': uselevel,
                'status': status,
                'moneytype': moneytype,
                'opencouch': opencouch,
                'couchtime': couchtime,
                'freeuse1': subservicetype
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(self.cacheCurrentPage, self.pageSize, false);
                } else {
                    parent.Common.loading(false);
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
            error: function(){
                parent.Common.ajaxError();
                parent.Common.loading(false);
            }
        });
    },
    getSubService: function (dicid, backSelectId) {
        /**
         * 根据一级服务类型dicid获取二级服务类型
         * @param {String} dicid 一级服务类型id
         * @param {String} backSelectId 二级服务类型subdicid，反显的时候用到，不需要的时候传null
         * */
        if(dicid == '') {
            // 当一级服务名称选择“请选择”的时候，清空二级服务名称
            parent.$("#subservicetype,#subservicetype1").empty().html('<option value="">请选择</option>');
            return;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./getSubServicetype.json",
            datatype: "json",
            data: {
                dicid: dicid
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var tempHtml = '<option value="">请选择</option>';
                    data.mi007list.forEach(function (val) {
                        tempHtml += '<option value="' + val.dicid + '">' + val.itemval + '</option>';
                    });
                    parent.$("#subservicetype,#subservicetype1").empty().html(tempHtml);
                    if (backSelectId != null) {
                        parent.$('#subservicetype1').val(backSelectId);
                    }
                    parent.Common.loading(false);
                } else {
                    parent.Common.loading(false);
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
            error: function(){
                parent.Common.ajaxError();
                parent.Common.loading(false);
            }
        });
    },
    btnClick:function(){
        var self = this;
        $('#btnquery').click(function(){
            self.getTableData( 1, self.pageSize, true);
        });

        $('#orgManage-btn-add').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
            // 新建的时候，更改一级服务名称，根据dicid获取二级服务名称
            parent.$("#servicetype").on("change", function () {
                self.getSubService($(this).find("option:selected").attr("data-dicid"), null);
            });
        });

        $('#orgManage-btn-del').click(function(e){
            var selected = $("#channelSetTable").find("tr.selected");
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
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });

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


            parent.$('#servicename1').val(selected[0].servicename);
            parent.$('#servicemsg1').val(selected[0].servicemsg);
            parent.$('#servicetype1').val(selected[0].servicetype);
            parent.$('#servicetype1 option:contains("' + selected[0].servicetype + '")').prop('selected', true);
            // 获取二级服务名称并回显
            self.getSubService(selected[0].dicid, selected[0].subdicid);
            // 修改的时候，更改一级服务名称，根据dicid获取二级服务名称
            parent.$("#servicetype1").on("change", function () {
                self.getSubService($(this).find("option:selected").attr("data-dicid"), null);
            });
            parent.$('#level1').val(selected[0].uselevel);

            if(selected[0].status == '1'){
                parent.$('.radioWrap').find('input[name="status1"]:first').prop('checked','checked');
                parent.$('.radioWrap').find('input[name="status1"]:last').prop('checked',false);
            } else {
                parent.$('.radioWrap').find('input[name="status1"]:first').prop('checked',false);
                parent.$('.radioWrap').find('input[name="status1"]:last').prop('checked','checked');
            }
            
            if(selected[0].moneytype == '1'){
                parent.$('.radioWrap').find('input[name="moneytype1"]:first').prop('checked','checked');
                parent.$('.radioWrap').find('input[name="moneytype1"]:last').prop('checked',false);
            } else {
                parent.$('.radioWrap').find('input[name="moneytype1"]:first').prop('checked',false);
                parent.$('.radioWrap').find('input[name="moneytype1"]:last').prop('checked','checked');
            }

            if(selected[0].opencouch == '1'){
                parent.$('.radioWrap').find('input[name="opencouch1"]:first').prop('checked','checked');
                parent.$('.radioWrap').find('input[name="opencouch1"]:last').prop('checked',false);
                parent.$(".showTime").show();
            } else {
                parent.$('.radioWrap').find('input[name="opencouch1"]:first').prop('checked',false);
                parent.$('.radioWrap').find('input[name="opencouch1"]:last').prop('checked','checked');
                parent.$(".showTime").hide();
            }
            parent.$('.radioWrap').find('input[name="opencouch1"]').on("change", function () {
                parent.$(".showTime").toggle();
            });

            parent.$('#couchtime1').val(selected[0].couchtime);

            
        });

        

        // 接口配置按钮
        $('#btnInterfaceSet').click(function(){
            var selected = self.tabler.selectedRows();
            if(selected.length != 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请选择一条信息进行接口配置！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            $('.channelSearch,.channelBox').hide();
            $('.interfaceBox').show(0,function(){
                interfaceSet.getTableData(selected[0].serviceid);
                $('.name').text(selected[0].servicename);
                _serviceid = selected[0].serviceid;
            });
        });
        // 返回按钮
        $('#orgManage-info-goBack').click(function(){
            $('.channelSearch,.channelBox').show();
            $('.interfaceBox').hide();
        });
       
         // 添加接口按钮
        $('#orgManage-info-add').click(function(e){

            var createHTML = $(".orgManage-popup-edit-container3").html();
            parent.Common.popupShow(createHTML);

            parent.$('#sname').val($('.name').text());

            // 接口信息反显
            parent.$('#apiname').change(function(){
                var descri = parent.$("#apiname").find("option:selected").attr("key");
                parent.$('#apimsg').val(descri);
            });

        });

        

    }
}
   

var interfaceSet = {
    tabler:null,
    tableData: null,
    getTableData: function (serviceid) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi05105.json',
            data: { 'serviceid': serviceid},
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
        self.tableData = data;
        var cols = [
            { title:'序号', name:'id' ,width:100, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
            { title:'接口名称', name:'apiname' ,width:300, align:'center' },
            { title:'接口描述', name:'apimsg' ,width:300, align:'center'},
            { title:'统计标识', name:'freeuse1' ,width:300, align:'center', renderer: function (val) {
                var temp = '';
                if(val == '0') {
                    temp = '<input type="checkbox" class="subFlagFreesuse1"/>';
                } else if (val == '1') {
                    temp = '<input type="checkbox" class="subFlagFreesuse1" checked/>';
                }
                return temp;
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#orgManage-popup-info-table').mmGrid({
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
        var apiid = parent.$('#apiname').val(),
            orderid = parent.$('#aid').val();

        if(apiid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "接口名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(orderid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "序号不能为空！",
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
            url:'./webapi05201.json',
            data: { 
                'orderid':orderid,
                'serviceid': _serviceid,
                'apiid':apiid
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(_serviceid);
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
    subSave: function(){
        var self = this,
            newtable = self.tableData;
        newtable.forEach(function (val,i,array) {
            val.serviceid = _serviceid;
            val.orderid = i+1;
        });
        $.ajax({
            type: "POST",
            url: "./webapi05205.json?datalist=" + JSON.stringify(self.tableData),
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(_serviceid);
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
    subSaveFlag: function(){
        var self = this,
            apiids = [],
            freeuse1s = [],
            tableData = self.tabler.rows();
        tableData.forEach(function (val) {
            apiids.push(val.apiid);
        });
        for(var i=0; i<$(".subFlagFreesuse1").length; i++) {
            var booler = $(".subFlagFreesuse1").eq(i).prop('checked');
            if (booler) {
                freeuse1s.push('1');
            } else {
                freeuse1s.push('0');
            }
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05107.json",
            datatype: "json",
            data: {
                serviceid: _serviceid,
                apiid: apiids.join(','),
                freeuse1: freeuse1s.join(',')
            },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(_serviceid);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    del: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for(var i=0; i<selected.length; i++) {
            delIds.push(selected[i].apiid);
        }
        $.ajax({
            type: "POST",
            url: "./webapi05206.json",
            data: { 'apiid': delIds.join(","),'serviceid':_serviceid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(_serviceid);
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
    btnClick: function(){
        var self = this; 

        // 上移按钮
        $("#orgManage-info-moveUp").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.tabler.selectedRowsIndex();
            for(var i = 0; i < selectedDataIndex.length; i++) {
                if(selectedDataIndex[i] == 0) {
                    parent.Common.dialog({
                        type: "warning",
                        text: "选中值中包含当前页第一条，不能上移！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    return;
                }
                if(selectedDataIndex[i] == 0) return;
                var temp = newData[selectedDataIndex[i]].orderid;
                newData[selectedDataIndex[i]].orderid = newData[selectedDataIndex[i] - 1].orderid;
                newData[selectedDataIndex[i] - 1].orderid = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val - 1);
            });
            self.tableData = newData;

        });

        // 下移按钮
        $("#orgManage-info-moveDown").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.tabler.selectedRowsIndex();
            for(var i = selectedDataIndex.length; i >= 1; i--) {
                if(selectedDataIndex[i - 1] == newData.length - 1) {
                    parent.Common.dialog({
                        type: "warning",
                        text: "选中值中包含当前页最后一条，不能下移！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {}
                    });
                    return;
                }
                var temp = newData[selectedDataIndex[i-1]].orderid;
                newData[selectedDataIndex[i-1]].orderid = newData[selectedDataIndex[i - 1] + 1].orderid;
                newData[selectedDataIndex[i-1] + 1].orderid = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val + 1);
            });
            self.tableData = newData;
        });

        $("#orgManage-info-save").off().on("click", function () {
            self.subSave();
        });

        $("#orgManage-info-saveFlag").off().on("click", function () {
            self.subSaveFlag();
        });

        $('#orgManage-info-del').click(function(){
            // var selected = self.tabler.selectedRows();
            // if(selected.length < 1) {
            //     parent.Common.dialog({
            //         type: "error",
            //         text: "请至少选择一条信息！",
            //         okShow: true,
            //         cancelShow: false,
            //         okText: "确定",
            //         ok: function () {
            //         }
            //     });
            //     parent.Common.delNone();
            //     return;
            // }
            parent.Common.dialog({
                type: "warning",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.Common.loading(true);
                    self.del();
                }
            });
        });

    }
}






$(function(){
    // 获取服务名称
    $.ajax({
        type:'POST',
        url:'./getApptranstype.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){

                var name = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi007list.length;i++){
                    name += '<option value="'+data.mi007list[i].itemid+'">'+data.mi007list[i].itemval+'</option>'
                }
                $('#servicename').html(name);
                
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });

    // 获取接口名称
    $.ajax({
        type:'POST',
        url:'./webapi05006.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var name = '<option value="">请选择</option>';
                for(var i = 0;i<data.rows.length;i++){
                    name += '<option key="'+data.rows[i].apimsg+'" value="'+data.rows[i].apiid+'">'+data.rows[i].apiname+'</option>'
                }
                $('#apiname').html(name);
                
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });

    // 获取服务类型
    $.ajax({
        type:'POST',
        url:'./getServicetype.json',
        datatype:'json',
        success:function(data){
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                console.log(data);
                servicetypeArr = data.mi007list;
                var tempHtml = '<option value="">请选择</option>';
                data.mi007list.forEach(function (item) {
                    tempHtml += '<option value="' + item.itemid + '" data-dicid="' + item.dicid + '">' + item.itemval + '</option>';
                });
                $("#servicetype1, #servicetype").html(tempHtml);
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

    channelServer.createPager();
    channelServer.btnClick();

    interfaceSet.btnClick();


});





















