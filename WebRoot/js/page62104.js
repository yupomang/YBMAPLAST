/**
 * Created by M on 2016/9/22.
 */

var user = top.userInfo;

var timeSet = {
    pageSize: 10,
    pager: null,
    tabler:null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#reservation-time-table-pages",
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
        rows = rows;
        $.ajax({
            type:'POST',
            url:'./webapi62104.json',
            data: { 
                'centerid': user.centerid,
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
    setTemplate:function(id){
        $(".reservation-box").hide();
        details.getTableData(id);
        details.appotemplateid = id;
        $("#setTemplate").show();
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'预约时段模版编号', name:'appotemplateid' ,width:296, align: 'center' },
            { title:'预约时段模版名称', name:'templatename' ,width:366, align: 'center'},
            { title:'操作', name:'appotemplateid' ,width:368, align: 'center', renderer: function (val) {
                return "<a href='javascript:;' class='green-link' onclick='timeSet.setTemplate(" + val +")'>设置模板</a>";
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#reservation-time-table').mmGrid({
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
        var templatename = parent.$('#templatename').val();
        if(templatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "预约时段模板名称不能为空！",
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
            url:'./webapi62101.json',
            data: { 
                'templatename': templatename,
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
    del: function (ids) {
        var self = this;
            
        $.ajax({
            type: "POST",
            url: "./webapi62102.json",
            data: { 'appotemplateid': ids },
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
        var self = this;

        var appotemplateid = parent.$('#appotemplateid').val(),
            templatename = parent.$('#templatename1').val();

        if(templatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "预约时段模板名称不能为空！",
                okShow: true,
                cancelShow: false,
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
            url: "./webapi62103.json",
            datatype: "json",
            data: { 
                'appotemplateid':appotemplateid,
                'templatename': templatename
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
    btnClick:function(){
        var self = this;

        // 模板功能按钮
        $("#reservation-time-btn-add").on("click", function () {
            var createHTML = $(".reservation-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#reservation-time-btn-edit").on("click", function () {
            var createHTML = $(".reservation-edit-box").html();
            parent.Common.popupShow(createHTML);

            var selected = self.tabler.selectedRows();

            parent.$('#appotemplateid').val(selected[0].appotemplateid);
            parent.$('#templatename1').val(selected[0].templatename);

        });
        $("#reservation-time-btn-del").on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    var ids = [];
                    selected.forEach(function (item) {
                        ids.push(item.appotemplateid);
                    });
                    self.del(ids.join(","));
                }
            });
        });

    }
}
   

var details = {
    tabler:null,
    tableData: null,
    appotemplateid:null,
    getTableData: function (id) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi62204.json',
            data: { 'appotemplateid': id},
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
            { title:'预约时段明细编号', name:'appotpldetailid' ,width:296, align: 'center' },
            { title:'预约时段明细名称', name:'timeinterval' ,width:366, align: 'center'},
            { title:'操作', name:'centerid' ,width:368, align: 'center', renderer: function (val, item, index) {
                var temp = "";
                temp += '<span class="setTemplate-table-links">';
                temp += "<a href='javascript:;' class='green-link' onclick='details.templateEdit(" + JSON.stringify(item) + ");' title='编辑'>编辑</a>";
                temp += '<span>|</span>';
                temp += '<a href="javascript:;" class="red-link" onclick="details.templateDel(\'' + item.appotpldetailid + '\');" title="删除">删除</a>';
                temp += '</span>';
                return temp;
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#setTemplate-table').mmGrid({
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
            selected = timeSet.tabler.selectedRows();
        var timeinterval = parent.$('#timeinterval').val(),
            appotemplateid = this.appotemplateid;

        if(timeinterval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "时段描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(!timeinterval.match(/\d+/g)){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(timeinterval.indexOf('-')==-1){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(timeinterval.indexOf(':')==-1){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        var temp = timeinterval.split('-');
        if(temp.length > 1) {
            var tempStart = temp[0].split(':'),
                tempEnd = temp[1].split(':'),
                start = '01',
                end = '01';
            if(parseInt(tempStart[0]) < 10) {
                start = '0' + parseInt(tempStart[0]);
            } else {
                start = parseInt(tempStart[0]);
            }
            if(parseInt(tempEnd[0]) < 10) {
                end = '0' + parseInt(tempEnd[0]);
            } else {
                end = parseInt(tempEnd[0]);
            }
            timeinterval = start + ':' + tempStart[1] + '-' + end + ':' + tempEnd[1];
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi62201.json',
            data: { 
                'appotemplateid':appotemplateid,
                'timeinterval':timeinterval
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(appotemplateid);
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
    edit: function () {
        var self = this;
        var appotemplateid = self.appotemplateid,
            appotpldetailid = parent.$('.reservation-edit').find('input').eq(0).val(),
            timeinterval = parent.$('.reservation-edit').find('input').eq(1).val();

        if(timeinterval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "时段描述不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(!timeinterval.match(/\d+/g)){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(timeinterval.indexOf('-')==-1){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(timeinterval.indexOf(':')==-1){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "配置时段描述请按照建议规则配置，如：8:30-9:00！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        var temp = timeinterval.split('-');
        if(temp.length > 1) {
            var tempStart = temp[0].split(':'),
                tempEnd = temp[1].split(':'),
                start = '01',
                end = '01';
            if(parseInt(tempStart[0]) < 10) {
                start = '0' + parseInt(tempStart[0]);
            } else {
                start = parseInt(tempStart[0]);
            }
            if(parseInt(tempEnd[0]) < 10) {
                end = '0' + parseInt(tempEnd[0]);
            } else {
                end = parseInt(tempEnd[0]);
            }
            timeinterval = start + ':' + tempStart[1] + '-' + end + ':' + tempEnd[1];
        }
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type:'POST',
            url:'./webapi62203.json',
            data: {
                'appotemplateid':appotemplateid,
                'appotpldetailid': appotpldetailid,
                'timeinterval':timeinterval
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(appotemplateid);
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
            newtable = self.tableData,
            appotemplateid = self.appotemplateid;
        newtable.forEach(function (val,i,array) {
            val.freeuse4 = i+1;
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi622SaveSort.json?datalist=" + JSON.stringify(self.tableData),
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(appotemplateid);
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
    templateDel: function (id) {
        var self = this;
        parent.Common.dialog({
            type: "tips",
            text: "确认是否删除？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            cancelText: "取消",
            ok: function () {
                $.ajax({
                    type: "POST",
                    url: "./webapi62202.json",
                    data: { 'appotpldetailid': id},
                    success: function(data) {
                        if(typeof data == "string") {
                            data = JSON.parse(data);
                        }
                        if (data.recode == "000000") {
                            self.getTableData(self.appotemplateid);
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
            }
        });
        
    },
    templateEdit: function (obj) {
        var editHTML = $(".reservation-setTemplate-edit-box").html();
        parent.Common.popupShow(editHTML);
        parent.$(".reservation-edit").find('input').eq(0).val(obj.appotpldetailid);
        parent.$(".reservation-edit").find('input').eq(1).val(obj.timeinterval);
    },
    btnClick: function(){
        var self = this; 

        // 模板明细功能按钮
        $("#setTemplate-info-add").on("click", function () {
            var createHTML = $(".reservation-setTemplate-create-box").html();
            parent.Common.popupShow(createHTML);
        });
        $("#setTemplate-info-del").on("click", function () {
            var selected = self.tabler.selectedRows(),
                delIds = [];
           
            selected.forEach(function (item) {
                delIds.push(item.appotpldetailid);
            });
            
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    $.ajax({
                        type: "POST",
                        url: "./webapi62202.json",
                        data: {'appotpldetailid':delIds.join(",")},
                        success: function(data) {
                            if(typeof data == "string") {
                                data = JSON.parse(data);
                            }
                            if (data.recode == "000000") {
                                self.getTableData(self.appotemplateid);
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
                }
            });
        });
        $("#setTemplate-info-goBack").on("click", function () {
            $(".reservation-box").show();
            $("#setTemplate").hide();
        });


        // 上移按钮
        $("#setTemplate-info-moveUp").off().on("click", function () {
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
                var temp = newData[selectedDataIndex[i]].freeuse4;
                newData[selectedDataIndex[i]].freeuse4 = newData[selectedDataIndex[i] - 1].freeuse4;
                newData[selectedDataIndex[i] - 1].freeuse4 = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val - 1);
            });
            self.tableData = newData;

        });

        // 下移按钮
        $("#setTemplate-info-moveDown").off().on("click", function () {
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
                var temp = newData[selectedDataIndex[i-1]].freeuse4;
                newData[selectedDataIndex[i-1]].freeuse4 = newData[selectedDataIndex[i - 1] + 1].freeuse4;
                newData[selectedDataIndex[i-1] + 1].freeuse4 = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val + 1);
            });
            self.tableData = newData;
        });

        $("#setTemplate-info-save").off().on("click", function () {
            self.subSave();
        });

    }
}






$(function(){
    timeSet.createPager();
    timeSet.btnClick();

    details.btnClick();

});
