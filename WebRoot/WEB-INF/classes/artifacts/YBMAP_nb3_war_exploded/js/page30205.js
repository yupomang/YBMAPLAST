/**
 * Created by M on 2016/9/7.
 */

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
        centerid = $('#searchCustomer').val();
        $.ajax({
            type:'POST',
            url:'./webapi12204.json',
            data: { 
                centerid:centerid,
                page:page,
                rows:rows
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000" || data.recode == "280001") {
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
           { title:'序号', name:'id' ,width:60, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'客户名称', name:'centerid' ,width:350, align:'center' ,renderer: function(val ,item, index){
                return $('#searchCustomer option[value='+val+']').text();               
           }},
           { title:'消息主题描述', name:'messageTopicType' ,width:300, align:'center' ,renderer: function(val ,item, index){
                if(val == ''){
                    return '-';
                }else{
                    return $('#messageTopicType option[value='+val+']').text();
                }
            }},
           { title:'强制推送标记', name:'mustsend' ,width:200, align:'center',renderer: function(val ,item, index){
                return val=='0'?'消息主题未选定不推送':'消息主题未选定强制推送';
            }},
           { title:'定制主题', name:'definitiontype' ,width:100, align:'center',renderer: function(val ,item, index){
                return val=='0'?'否':'是';
           }},
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
            messageTopicType = parent.$('#messageTopicType').val(),
            mustsend = parent.$('#mustsend').val(),
            definitiontype = parent.$('.radioWrap').find('input[name="status"]:checked').val();
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
        if(messageTopicType.length < 1) {
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
            url:'./webapi12201.json',
            data: { 
                centerid:centerid,
                messageTopicType:messageTopicType,
                mustsend:mustsend,
                definitiontype:definitiontype
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
            delIds.push(selected[i].seqid);
        }
        $.ajax({
            type: "POST",
            url: "./webapi12202.json",
            data: { 'listSeqid': delIds.join(",") },
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
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();

         var centerid = parent.$('#centerid1').val(),
            messageTopicType = parent.$('#messageTopicType1').val(),
            mustsend = parent.$('#mustsend1').val(),
            definitiontype = parent.$('.radioWrap').find('input[name="status1"]:checked').val(),
            seqid = selectedData[0].seqid;

        if(messageTopicType.length < 1) {
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

        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi12203.json",
            datatype: "json",
            data: { 
                centerid:centerid,
                messageTopicType:messageTopicType,
                mustsend:mustsend,
                seqid:seqid,
                definitiontype:definitiontype
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1, self.pageSize, true);
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
            }
        });
    },
    subSave: function(){
        parent.Common.loading(true);
        var self = this,
            newtable = self.tableData;
        newtable.forEach(function (val,i,array) {
            val.num = i+1;
        });
        $.ajax({
            type: "POST",
            url: "./webapi12205.json?datalist=" + JSON.stringify(newtable),
            datatype: "json",
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
        $('#btnquery').click(function(){
            self.getTableData( 1, self.pageSize, true);
        });

        $('#orgManage-btn-add').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
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
            parent.$('#messageTopicType1').val(selected[0].messageTopicType);
            parent.$('#mustsend1').val(selected[0].mustsend);

            if(selected[0].definitiontype == '1'){
                parent.$('.radioWrap').find('input[name="status1"]:first').attr('checked','checked');
                parent.$('.radioWrap').find('input[name="status1"]:last').attr('checked',false);
            } else {
                parent.$('.radioWrap').find('input[name="status1"]:first').attr('checked',false);
                parent.$('.radioWrap').find('input[name="status1"]:last').attr('checked','checked');
            }
            
        });

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

    }
}
   


$(function(){

    $.ajax({
        type:'POST',
        url:'./page30205GetParam.json',
        data:{},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '<option value="">请选择</option>';
                for(var i = 0;i<data.mi001list.length;i++){
                    if(top.userInfo.centerid == '00000000') {
                        customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                    } else {
                        if(top.userInfo.centerid == data.mi001list[i].centerid) {
                            customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                        }
                    }
                }

                $('#searchCustomer,#centerid,#centerid1').html(customerOptions);

                var msgDesc = '<option value="">请选择</option>';
                for(var i = 0;i<data.message_topic_type.length;i++){
                    msgDesc += '<option value="'+data.message_topic_type[i].itemid+'">'+data.message_topic_type[i].itemval+'</option>';
                }

                $('#messageTopicType,#messageTopicType1').html(msgDesc);

            }
            
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });

    msgTheme.createPager();
    msgTheme.btnClick();

});





















