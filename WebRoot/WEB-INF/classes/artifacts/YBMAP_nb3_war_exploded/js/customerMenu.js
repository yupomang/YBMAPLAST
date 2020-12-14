/**
 * Created by FelixAn on 2016/8/20.
 */
// 客户菜单分配
parent.Common.loading(true);
var customerMenu = {
    pager: null,
    pageSize: 10,
    menuList: null,
    fnList: null,
    treeVue: null,
    taler: null,
    parentfuncid: null,
    centerid: null,
    uselevel: null,
    isBase: true, // 是否为根目录
    tableData:null,
    getMenuList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05606.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.menuList = data.rows;
                    self.getFnList();
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
    getFnList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05505.json",
            datatype: "json",
            data: { 'centerid': self.centerid, 'uselevel': self.uselevel },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.fnList = data.rows;
                    self.setPopupSelect();
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
    setPopupSelect: function () {
        var self = this,
            menuHtml = '',
            fnHtml = '',
            menuData = self.menuList,
            fnData = self.fnList;
        menuData.forEach(function (val) {
            menuHtml += '<option orderid="'+ val.orderid +'" value="'+ val.cdid +'">' + val.urlname + '</option>';
        });
        fnData.forEach(function (val) {
            fnHtml += '<option value="'+ val.funcid +'">' + val.funcname + '</option>';
        });
        $("#create-menu-name").html(menuHtml);
        $("#create-fn-name").html(fnHtml);
        $("#edit-menu-name").html(menuHtml);
        $("#edit-fn-name").html(fnHtml);
    },
    leftTreeClick: function () {
        var self = this;
        $(".customerMenu-left").off("click.slide").on("click.slide", "a, h1", function () {
            $(".customerMenu-left").find("a, h1").removeClass("on");
            $(this).addClass("on");
            if($(this).parents("dl").length > 0) return;
            $(".customerMenu-left-tree li dl").stop().slideUp();
            self.isBase = false;
        });
        $(".customerMenu-left").off("click.h1").on("click.h1", "h1", function () {
            parent.Common.loading(true);
            $("#create-parent-name").attr("value", "根目录");
            $("#edit-parent-name").attr("value", "根目录");
            $(".sysMgmt-configMenu-top input").val("");
            $("#customer-table-pages").hide();
            self.getBiggestTree(self.centerid, "00000000");
            self.isBase = true;
        });
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#customer-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex) {
                self.query($(".sysMgmt-configMenu-top input").val(), pageIndex, false);
            }
        });
        self.getCenterList();
        self.createTreeVue();
    },
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            { title:'序号', name:'centerid', width:'62', align: 'center', renderer: function (val, item, index) {
                return index + 1;
            } },
            { title:'菜单名称', name:'funcname', width:'240', align: 'center'},
            { title:'功能名称', name:'funname', width:'176', align: 'center'},
            { title:'URL', name:'url', width:'300', align: 'center'}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#customer-table').mmGrid({
                multiSelect: true,
                checkCol: true,
                height: '370px',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        self.btnClick();
        parent.Common.loading(false);
    },
    btnClick: function () {
        var self = this;
        $("#sysMgmt-btn-add").off().on("click", function () {
            //$("#popup-icons-create").append($(".menu-icon-list").html());
            var addHTML = $(".configMenu-create").html();
            parent.Common.popupShow(addHTML);
            if(self.isBase) {
                parent.$(".wechat-baseQuery-popup-create").find("p").eq(3).show();
                parent.$(".popup-icon-border").show();
            } else {
                parent.$(".wechat-baseQuery-popup-create").find("p").eq(3).hide();
                parent.$(".popup-icon-border").hide();
            }
        });
        $("#sysMgmt-btn-edit").off().on("click", function () {
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            //$("#popup-icons-edit").append($(".menu-icon-list").html());
            var editHTML = $(".configMenu-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#edit-menu-name").val(selectedData[0].cdid);
            parent.$("#edit-fn-name").val(selectedData[0].id || selectedData[0].funcid);
            parent.$(".popup-icon-border i").attr("class", '').addClass("icon iconfont " + selectedData[0].image);
            parent.$(".popup-icon-border span").text(selectedData[0].image);
            parent.$(".wechat-baseQuery-popup-content textarea").val(selectedData[0].url);
            parent.$(".wechat-baseQuery-popup-content").val(selectedData[0].url);
            if(selectedData[0].parentfuncid == "00000000") {
                parent.$(".wechat-baseQuery-popup-create").find("p").eq(3).show();
                parent.$(".popup-icon-border").show();
            } else {
                parent.$(".wechat-baseQuery-popup-create").find("p").eq(3).hide();
                parent.$(".popup-icon-border").hide();
            }
        });
        $("#sysMgmt-btn-del").off().on("click", function () {
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length < 1) {
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
        $(".sysMgmt-configMenu-top a").off().on("click", function () {
            if($(".sysMgmt-configMenu-top input").val().length < 1) return;
            parent.Common.loading(true);
            self.query($(".sysMgmt-configMenu-top input").val(), 1, true);
        });

        // 上移按钮
        $("#sysMgmt-btn-up").off().on("click", function () {
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
        $("#sysMgmt-btn-down").off().on("click", function () {
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

        $("#sysMgmt-btn-save").off().on("click", function () {
            self.subSave();
        });
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
                    // create center drop list
                    var droplistData = [];
                    for(var i = 0; i < data.rows.length; i++){
                        if(data.rows[i].validflag == "1") {
                            droplistData.push(data.rows[i].centername);
                        }
                    }
                    var customerFnDroplist = droplist({
                        el: "#customerMenu-select",
                        data: droplistData
                    });
                    customerFnDroplist.setSelected(data.rows[0].centername);
                    $(".customerMenu-select-center a").on("click", function () {
                        var centerid = '',
                            funcname = '';
                        for(var i = 0; i < data.rows.length; i++){
                            if(data.rows[i].centername == customerFnDroplist.getSelected()) {
                                centerid = data.rows[i].centerid;
                                funcname = data.rows[i].funcname;
                                self.uselevel = data.rows[i].uselevel;
                                break;
                            }
                        }
                        parent.Common.loading(true);
                        self.centerid = centerid;
                        self.funcname = funcname;
                        self.getBiggestTree(centerid, "00000000");
                        $(".customerMenu-select-center").hide();
                        $(".customerMenu-container").css("opacity", 1);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getBiggestTree: function (centerid, parentfuncid) {
        var self = this;
        self.parentfuncid = parentfuncid;
        $.ajax({
            type: "POST",
            url: "./webapi00505.json",
            datatype: "json",
            data: { 'centerid': centerid, 'parentfuncid': parentfuncid },
            success: function(data) {
                self.getMenuList();
                self.leftTreeClick();
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.treeVue.items = data.ary;
                    self.createTable(data.ary);
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            window.location.reload();
                        }
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTreeVue: function () {
        var self = this;
        self.treeVue = new Vue({
            el: '.customerMenu-left-tree',
            data: {
                items: []
            },
            methods: {
                getSubTree: function (centerid, parentfuncid, text) {
                    parent.Common.loading(true);
                    $(".sysMgmt-configMenu-top input").val("");
                    $("#customer-table-pages").hide();
                    self.parentfuncid = centerid;
                    $("#create-parent-name").attr("value", text);
                    $("#edit-parent-name").attr("value", text);
                    self.getTableData(self.centerid, centerid);
                }
            }
        });
    },
    getTableData: function (centerid, parentfuncid) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi00505.json",
            datatype: "json",
            data: { 'centerid': centerid, 'parentfuncid': parentfuncid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.updateTree(parentfuncid, data.ary);
                    if(parentfuncid == self.centerid) {
                        self.treeVue.items = data.ary;
                    }
                    if($(".sysMgmt-configMenu-top input").val().length > 0) {
                        // if has search val
                        return;
                    }
                    self.createTable(data.ary);
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
    updateTree: function (id, data) {
        var tempHtml = '';
        data.forEach(function (val) {
            tempHtml += '<dd><a href="javascript:;" title="'+ val.funcname +'"><i class="icon iconfont '+ val.image +'"></i>' + val.funcname + '</a></dd>';
        });
        $("#dl" + id).empty().html(tempHtml).slideDown();
    },
    iconsDisplay: function (bool, name) {
        if(bool) {
            parent.$(".popup-relative").hide();
            parent.$("#popup-icons-edit, #popup-icons-create").show();
        } else {
            if(name) {
                parent.$(".popup-icon-border").find("i").removeClass().addClass('icon iconfont ' + name);
                parent.$(".popup-icon-border").find("span").text(name);
            }
            parent.$(".popup-relative").show();
            parent.$("#popup-icons-edit, #popup-icons-create").hide();
        }
    },
    add: function () {
        parent.Common.loading(true);
        var self = this;
        var funcid = parent.$("#create-fn-name").val(),
            cdid = parent.$("#create-menu-name").val(),
            image = parent.$(".popup-icon-border span").text(),
            url = parent.$(".wechat-baseQuery-popup-content textarea").val(),
            orderid = parent.$("#create-menu-name").find("option:selected").attr("orderid"),
            funcname = parent.$("#create-menu-name").find("option:selected").text(),
            funname = parent.$("#create-fn-name").find("option:selected").text(),
            parentfuncid = self.isBase ? "00000000" : self.parentfuncid;
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi00501.json",
            datatype: "json",
            data: { 'funcid': funcid, 'centerid': self.centerid, 'cdid': cdid, 'funcname': funcname, 'parentfuncid': parentfuncid, 'url': url, "image": image, 'orderid': orderid, 'funname': funname },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    $(".sysMgmt-configMenu-top input").val("");
                    $("#customer-table-pages").hide();
                    self.getTableData(self.centerid, self.parentfuncid);
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
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows();
        var funcid = parent.$("#edit-fn-name").val(),
            cdid = parent.$("#edit-menu-name").val(),
            image = parent.$(".popup-icon-border span").text(),
            url = parent.$(".wechat-baseQuery-popup-content textarea").val(),
            orderid = parent.$("#edit-menu-name").find("option:selected").attr("orderid"),
            funcname = parent.$("#edit-menu-name").find("option:selected").text();
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi00503.json",
            datatype: "json",
            data: { 'oldFuncid': selectedData[0].id || selectedData[0].funcid, 'oldCenterid': selectedData[0].centerid, 'oldCdid': selectedData[0].cdid, 'funcid': funcid, "centerid": selectedData[0].centerid, "cdid": cdid, "funcname": funcname, "parentfuncid": selectedData[0].parentfuncid, "url": url, "image": image, "orderid": orderid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    if($(".sysMgmt-configMenu-top input").val().length > 0) {
                        self.query($(".sysMgmt-configMenu-top input").val(), 1, true);
                    } else {
                        self.getTableData(self.centerid, self.parentfuncid);
                    }
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
        parent.Common.loading(true);
        var self = this,
            selectedData = self.tabler.selectedRows(),
            funids = [],
            cdids = [];
        if(selectedData.length < 1) return;
        selectedData.forEach(function (val) {
            var temp = val.id ? val.id : val.funcid;
            funids.push(temp);
            cdids.push(val.cdid);
        });
        $.ajax({
            type: "POST",
            url: "./webapi00502.json",
            datatype: "json",
            data: { 'funcid': funids.join(","), 'centerid': selectedData[0].centerid, 'cdid': cdids.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    if($(".sysMgmt-configMenu-top input").val().length > 0) {
                        self.query($(".sysMgmt-configMenu-top input").val(), 1, true);
                    } else {
                        self.getTableData(self.centerid, self.parentfuncid);
                    }
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
    query: function (menuName, index, bool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi00504.json",
            datatype: "json",
            data: { 'funcname': menuName, 'page': index, 'rows': 10, 'centerid': self.centerid },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(self.centerid, self.parentfuncid);
                    self.tabler.load(data.rows);
                    $("#customer-table-pages").show();
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: 10,
                        reset: bool
                    });
                } else {
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
        var self = this,
            newtable = self.tableData;
        newtable.forEach(function (val,i,array) {
            val.funcid = val.id;
            val.freeuse4 = i+1;
        });
        $.ajax({
            type: "POST",
            url: "./webapi00506.json?datalist=" + JSON.stringify(self.tableData),
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: '保存顺序成功！',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(self.centerid, self.parentfuncid);
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
    }
};
$(document).ready(function(){
    customerMenu.createPager();
});