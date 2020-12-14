/**
 * Created by FelixAn on 2016/8/23.
 */
var codeTable = {
    tabler: null,
    treeVue: null,
    centerList: [],
    tempCenterid: null, // for create and edit
    tempUpdicid: null,
    olditemid: null,
    dicid: null, // for edit
    createTree: function () {
        var self = this;
        self.treeVue = new Vue({
            el: ".code-table-tree",
            data: {
                items: [],
                selected: {"id":"000000000","text":"码表管理","state":"closed","attributes":{"centerid":"000000000"}}
            },
            methods: {
                getSubTree: function (pid, centerid, item) {
                    this.selected = item;
                    self.getSubTree(centerid, pid);
                }
            }
        });
        self.getCenterList();
    },
    getCenterList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40003Qry.json",
            datatype: "json",
            data: { 'centerid': top.userInfo.centerid },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var centerArr = [];
                    data.rows.forEach(function (item) {
                        if(item.validflag == "1") {
                            console.log(item);
                            centerArr.push(item);
                        }
                    });
                    self.centerList = centerArr;
                    self.getBaseTree("000000000", "000000000");
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getBaseTree: function (centerid, pid) {
        var self = this;
        self.tempCenterid = centerid;
        self.tempUpdicid = pid;
        $.ajax({
            type: "POST",
            url: "./ptl40006Qry.json?pid=000000000&centerid=000000000",
            datatype: "json",
            data: { 'id': '000000000' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                self.treeVue.items = data.ary;
                self.getTable(centerid, pid);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getSubTree: function (centerid, pid, item) {
        parent.Common.loading(true);
        var self = this;
        self.tempCenterid = centerid;
        self.tempUpdicid = pid;
        if (item) {
            self.treeVue.selected = item;
        }
        $.ajax({
            type: "POST",
            url: "./ptl40006Qry.json",
            datatype: "json",
            data: { 'centerid': centerid, 'pid': pid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                var tempHtml = '';
                data.ary.forEach(function (item) {
                    var backgroundClass = '';
                    if(item.state == 'open') { backgroundClass = 'background-none'; }
                    tempHtml += '<dd>' +
                        '<p onclick=\'codeTable.getSubTree("' + item.attributes.centerid + '","' + item.id + '",' + JSON.stringify(item) +')\' class="' + backgroundClass + '">' + item.text + '</p>' +
                        '<dl id="dl' + item.id + '"></dl>' +
                        '</dd>';
                });
                if (pid == "0") {
                    $("#dl" + self.treeVue.selected.attributes.itemid).empty().append(tempHtml);
                } else {
                    $("#dl" + pid).empty().append(tempHtml);
                }
                self.getTable(centerid, pid);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getTable: function (centerid, parentfuncid) {
        var self = this;
        self.parentfuncid = parentfuncid;
        $.ajax({
            type: "POST",
            url: "./ptl40006Query.json",
            datatype: "json",
            data: { 'centerid': centerid, 'pid': parentfuncid },
            success: function(data) {
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
    btnClick: function () {
        var self = this;
        $(".code-table-tree").off().on("click", 'p', function () {
            var _this = $(this);
            $(".code-table-tree").find("p").removeClass("on");
            var pTags = $(".code-table-tree").find("p");
            _this.siblings("ul, dl").stop().slideToggle();
            for(var i = 0; i < pTags.length; i++) {
                if(pTags.eq(i).next("dl").css("display") == "block") {
                    pTags.eq(i).addClass("on");
                }
            }
        });
        $(".code-table-left h2").off().on("click", function () {
            self.getBaseTree("000000000", "000000000");
            self.treeVue.selected = {"id":"000000000","text":"码表管理","state":"closed","attributes":{"centerid":"000000000"}};
        });
        $("#sysMgmt-btn-add").off().on("click", function () {
            var centername = '',
                upCode = '';
            if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                centername = "-";
            } else {
                centername = self.treeVue.selected.attributes.centername;
            }
            if("000000000" == self.treeVue.selected.attributes.centerid){
                upCode = "-";
            } else {
                upCode = self.treeVue.selected.attributes.itemid + '-' + self.treeVue.selected.attributes.itemval;
            }
            var addHTML = $(".codeTable-create").html();
            parent.Common.popupShow(addHTML);
            parent.$(".reservation-popup-title span").eq(0).text("所属中心：" + centername);
            parent.$(".reservation-popup-title span").eq(1).text("上级编码：" + upCode);
        });
        $("#sysMgmt-btn-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selected.length > 1) {
                parent.Common.editMore();
                return;
            }
            var centername = '',
                upCode = '';
            if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                centername = "-";
            } else {
                centername = self.treeVue.selected.attributes.centername;
            }
            if("000000000" == self.treeVue.selected.attributes.centerid){
                upCode = "-";
            } else {
                upCode = self.treeVue.selected.attributes.itemid + '-' + self.treeVue.selected.attributes.itemval;
            }
            var editHTML = $(".codeTable-edit").html();
            parent.Common.popupShow(editHTML);
            self.olditemid = selected[0].itemid;
            self.dicid = selected[0].dicid;
            parent.$(".wechat-baseQuery-popup-create input").eq(0).val(selected[0].itemid);
            parent.$(".wechat-baseQuery-popup-create input").eq(1).val(selected[0].itemval);
            parent.$(".reservation-popup-title span").eq(0).text("所属中心：" + centername);
            parent.$(".reservation-popup-title span").eq(1).text("上级编码：" + upCode);
        });
        $("#sysMgmt-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
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
                    self.del();
                }
            });
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'centerid' ,width:40, align:'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            { title:'所属中心', name:'centername' ,width:230, align:'center', renderer: function (val, item, index) {
                if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                    return  "-";
                }
                return self.treeVue.selected.attributes.centername;
            }},
            { title:'上级编码', name:'updicname' ,width:188, align:'center', renderer: function (val, item, index) {
                if("000000000" == self.treeVue.selected.attributes.centerid){
                    return  "-";
                }
                return self.treeVue.selected.attributes.itemid + '-' + self.treeVue.selected.attributes.itemval;
            }},
            { title:'当前编码', name:'itemid' ,width:148, align:'center'},
            { title:'当前名称', name:'itemval' ,width:168, align:'center'}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#codeTable').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
                height: '460px',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
        self.btnClick();
    },
    add: function () {
        var self = this;
        var itemid = parent.$(".wechat-baseQuery-popup-create input").eq(0).val(),
            itemval = parent.$(".wechat-baseQuery-popup-create input").eq(1).val();
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "编码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "名称不能为空！",
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
            type: "POST",
            url: "./ptl40006Add.json",
            datatype: "json",
            data: { 'centerid':  self.tempCenterid, 'updicid': self.tempUpdicid, 'itemid': itemid, 'itemval': itemval },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
    edit: function () {
        var self = this;
        var itemid = parent.$(".wechat-baseQuery-popup-create input").eq(0).val(),
            itemval = parent.$(".wechat-baseQuery-popup-create input").eq(1).val();
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "编码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "名称不能为空！",
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
            type: "POST",
            url: "./ptl40006Mod.json",
            datatype: "json",
            data: { 'task': 'mod', 'centerid':  self.tempCenterid, 'updicid': self.tempUpdicid, 'dicid': self.dicid, 'olditemid': self.olditemid, 'itemid': itemid, 'itemval': itemval  },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
    del: function () {
        var self = this;
        var seleced = self.tabler.selectedRows(),
            ids = [];
        seleced.forEach(function (item) {
            ids.push(item.dicid);
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./ptl40006Del.json",
            datatype: "json",
            data: { 'centerid': self.tempCenterid, 'dicids': ids.join(',') },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
                        ok: function () {
                        }
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    }
};
$(document).ready(function(){
    if($("#codeTable").length > 0) {
        codeTable.createTree();
        codeTable.createTable();
    }
});