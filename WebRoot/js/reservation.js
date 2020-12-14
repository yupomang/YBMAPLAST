/**
 * Created by FelixAn on 2016/8/1.
 */
var reservationType = {
    hasCreatedTable: false,
    tableObj: null,
    pager: null,
    pageSize: 10,
    btnsClick: function () {
        var self = this,
            table = self.tableObj;
        $("#reservation-btn-add").off().on("click", function () {
            var createHTML = $(".reservation-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#reservation-btn-edit").off().on("click", function () {
            var selectedData = table.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            // set edit value
            $(".edit-box").find("input").eq(0).attr("value", selectedData[0].appobusiid);
            $(".edit-box").find("input").eq(1).attr("value", selectedData[0].appobusiname);
            // inner html
            var editHTML = $(".reservation-edit").html();
            parent.Common.popupShow(editHTML);
        });
        $("#reservation-btn-del").off().on("click", function () {
            var selectedData = table.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.delNone();
                return;
            }
            var tempIds = [];
            for(var i = 0; i < selectedData.length; i++) {
                tempIds.push(selectedData[i].appobusiid);
            }
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    self.del(tempIds.join(","));
                }
            });
        });
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#reservation-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
    },
    createTable: function (data, itemLength, resetBool) {
        var self = this;
        if(!self.hasCreatedTable) {
            var cols = [
                { title:'预约业务类型编号', name:'appobusiid' ,width:296, align: 'center' },
                { title:'预约业务类型名称', name:'appobusiname' ,width:366, align: 'center'},
                { title:'中心ID/名称  ', name:'centerid' ,width:368, align: 'center'}
            ];
            var reservationTable = $('#reservation-table').mmGrid({
                multiSelect: true,
                checkCol: true,
                height: 'auto',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
            self.tableObj = reservationTable;
            self.hasCreatedTable = true;
        } else {
            self.tableObj.load(data);
        }
        self.pager.reset({
            itemLength: itemLength,
            pageSize: self.pageSize,
            reset: resetBool
        });
        self.btnsClick();
    },
    getTableData: function (pageIndex, pageSize, resetBool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi62004.json",
            data: {
                "centerid": top.userInfo.centerid,
                "page": pageIndex ? pageIndex : 1,
                "rows": pageSize
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                parent.Common.loading(false);
                if (data.recode == "000000") {
                    self.createTable(data.rows, data.total, resetBool);
                } else {
                    self.createTable([], 0);
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
    add: function() {
        var self = this,
            postData = parent.$(".reservation-create").find("input").eq(0).val();
        if(postData.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
                text: "预约业务名称不能为空！",
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
        $.ajax({
            type: "POST",
            url: "./webapi62001.json",
            data: {
                "appobusiid": "",
                "appobusiname": postData
            },
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
        parent.Common.popupClose();
    },
    edit: function () {
        var self = this;
        var appobusiid = parent.$(".edit-box").find("input").eq(0).val(),
            appobusiname = parent.$(".edit-box").find("input").eq(1).val();
        if(appobusiid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
                text: "预约业务编号不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(appobusiname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
                text: "预约业务名称不能为空！",
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
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62003.json",
            data: {
                "appobusiid": appobusiid,
                "appobusiname": appobusiname
            },
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
    del: function (ids) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62002.json",
            data: {
                "appobusiid": ids
            },
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
var reservationTime = {
    pageSize: 10,
    btnsClick: function (table) {
        var table = table;
        $("#reservation-time-btn-add").on("click", function () {
            var createHTML = $(".reservation-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#reservation-time-btn-edit").on("click", function () {
            console.log("run this");
            var createHTML = $(".reservation-edit-box").html();
            parent.Common.popupShow(createHTML);
        });
        $("#reservation-time-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
    },
    createPager: function () {
        var self = this;
        // create pages
        var reservationTimeTablePager = pages({
            el: "#reservation-time-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                console.log("genggaihgahaha", pageIndex);
            }
        });
        return reservationTimeTablePager;
    },
    createTable: function () {
        var cols = [
            { title:'预约时段模版编号', name:'appotemplateid' ,width:296, align: 'center' },
            { title:'预约时段模版名称', name:'templatename' ,width:366, align: 'center'},
            { title:'操作', name:'centerid' ,width:368, align: 'center', renderer: function (val) {
                return "<a href='javascript:;' class='green-link' onclick='reservationTime.setTemplate(" + val +")'>设置模板</a>";
            }}
        ];
        var typeData = {"recode":"000000","rows":[{"appotemplateid":"9","centerid":"00063100","datecreated":"2016-07-22 09:44:05.169       ","datemodified":"2016-07-22 09:44:05.169       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":0,"loginid":"adminWH","templatename":"测试1","validflag":"1"},{"appotemplateid":"10","centerid":"00063100","datecreated":"2016-07-22 09:48:54.728       ","datemodified":"2016-07-22 09:48:54.728       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":0,"loginid":"adminWH","templatename":"测试2","validflag":"1"}],"msg":"成功","CMi621":{"centerId":"00063100","centreName":"威海市住房公积金管理中心","userid":"adminWH","username":"毕晓文","appotemplateid":null,"templatename":null,"centerid":"00063100","validflag":null,"datemodified":null,"datecreated":null,"loginid":null,"freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":null}};
        var reservationTable = $('#reservation-time-table').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: 'auto',
            cols: cols,
            items: typeData.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.btnsClick(reservationTable);
    },
    setTemplate: function (id) {
        $(".reservation-box").hide();
        $("#setTemplate").show();
        this.createTemplateTable();
    },
    createTemplateTable: function () {
        var cols = [
            { title:'预约时段明细编号', name:'appotpldetailid' ,width:296, align: 'center' },
            { title:'预约时段明细名称', name:'timeinterval' ,width:366, align: 'center'},
            { title:'操作', name:'centerid' ,width:368, align: 'center', renderer: function (val, item, index) {
                var temp = "";
                temp += '<span class="setTemplate-table-links">';
                temp += "<a href='javascript:;' class='green-link' onclick='reservationTime.templateEdit(" + JSON.stringify(item) + ");' title='编辑'>编辑</a>";
                temp += '<span>|</span>';
                temp += '<a href="javascript:;" class="red-link" onclick="reservationTime.templateDel(\'' + item.centerid + '\');" title="删除">删除</a>';
                temp += '</span>';
                return temp;
            }}
        ];
        var timeData = {"recode":"000000","rows":[{"appotemplateid":"12","appotpldetailid":"41","datecreated":"2016-08-01 15:43:42.164       ","datemodified":"2016-08-01 15:43:42.164       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":1,"loginid":"adminWH","timeinterval":"9:00 - 10:00","validflag":"1"},{"appotemplateid":"12","appotpldetailid":"42","datecreated":"2016-08-01 15:43:56.095       ","datemodified":"2016-08-01 15:43:56.095       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":2,"loginid":"adminWH","timeinterval":"10:00 - 11:00","validflag":"1"}],"msg":"成功","CMi622":{"centerId":"00063100","centreName":"威海市住房公积金管理中心","userid":"adminWH","username":"毕晓文","longinip":"10.11.99.185","roleid":null,"appotpldetailid":null,"appotemplateid":"12","timeinterval":null,"validflag":null,"datemodified":null,"datecreated":null,"loginid":null,"freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":null}};
        var reservationTable = $('#setTemplate-table').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: 'auto',
            cols: cols,
            items: timeData.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.templateBtnsClick(reservationTable);
    },
    templateBtnsClick: function (table) {
        var table = table,
            self = this;
        $("#setTemplate-info-add").on("click", function () {
            var createHTML = $(".reservation-setTemplate-create-box").html();
            parent.Common.popupShow(createHTML);
        });
        $("#setTemplate-info-del").on("click", function () {
            self.templateDel();
        });
        $("#setTemplate-info-goBack").on("click", function () {
            $(".reservation-box").show();
            $("#setTemplate").hide();
        });
    },
    templateDel: function (ids) {
        parent.Common.dialog({
            type: "tips",
            text: "确认是否删除？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            cancelText: "取消",
            ok: function () {
                // post data
            }
        });
    },
    templateEdit: function (id) {
        var editHTML = $(".reservation-setTemplate-edit-box").html();
        parent.Common.popupShow(editHTML);
    }
};
var note = {
    pager: null,
    pageSize: 10,
    tabler: null,
    editSelectedRow: null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#note-table-pages",
            itemLength: 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                parent.Common.loading(true);
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
        self.getTableData(1, self.pageSize, true);
    },
    getTableData: function (page, rows, resetBool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi62604.json",
            data: {
                'page': page,
                'rows': rows
            },
            datatype: "json",
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
                        ok: function () {}
                    });
                    self.createTable([]);
                }
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize,
                    reset: resetBool
                });
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'编号', name:'appoattenid' ,width:180, align: 'center' },
            { title:'注意事项', name:'templatename' ,width:288, align: 'center'},
            { title:'有效标记', name:'validflag' ,width:106, align: 'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case "0":
                        temp = "禁用";
                        break;
                    case "1":
                        temp = "启用";
                        break;
                }
                return temp;
            }},
            { title:'中心码', name:'centerid' ,width:104, align: 'center'},
            { title:'操作员码', name:'loginid' ,width:100, align: 'center'},
            { title:'创建日期', name:'datecreated' ,width:102, align: 'center'},
            { title:'最近修改日期', name:'datemodified' ,width:104, align: 'center'}
        ];
        if (self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#note-table').mmGrid({
                multiSelect: true,
                checkCol: true,
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
        self.noteBtnsClick();
    },
    noteBtnsClick: function () {
        var self = this,
            table = self.tabler;
        $("#note-btn-add").off().on("click", function () {
            var createHTML = $(".reservation-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#note-btn-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            } else if (selected.length > 1) {
                parent.Common.editMore();
                return;
            }
            self.editSelectedRow = selected[0];
            var editHTML = $(".reservation-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("input[value='" + selected[0].validflag + "']").prop('checked', true);
            parent.$("#editTemplatename").val(selected[0].templatename);
        });
        $("#note-btn-del").off().on("click", function () {
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
                        ids.push(item.appoattenid);
                    });
                    self.del(ids.join(","));
                }
            });
        });
    },
    add: function () {
        var self = this;
        var validflag = parent.$("input[name='effective']:checked").val(),
            templatename = parent.$("#templatename").val();
        if(templatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "注意事项不能为空！",
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
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62601.json",
            data: {
                'validflag': validflag,
                'templatename': templatename
            },
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
    edit: function () {
        var self = this;
        var validflag = parent.$("input[name='effective']:checked").val(),
            templatename = parent.$("#editTemplatename").val();
        if(templatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "注意事项不能为空！",
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
        parent.Common.loading(true);
        self.editSelectedRow.validflag = validflag;
        self.editSelectedRow.templatename = templatename;
        $.ajax({
            type: "POST",
            url: "./webapi62603.json",
            data: self.editSelectedRow,
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
    del: function (ids) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62602.json",
            data: {
                'appoattenid': ids
            },
            datatype: "json",
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
                            parent.Common.loading(true);
                            self.getTableData(1, self.pageSize, true);
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
var netQuery = {
    pager: null,
    busSelect: null, // 预约业务下拉对象
    netSelect: null, // 预约网点下拉对象
    busList: [], // 预约业务数据
    netList: [], // 预约网点数据
    pageSize: 10,
    busSelected: null, // 预约业务选中的名称
    netSelected: null, // 预约网点选中的名称
    appobranchid: null,
    appobusiid: null,
    tabler: null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#netQuery-table-pages",
            itemLength: 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
            }
        });
        self.getList();
    },
    getList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page62505.json",
            data: {
                "centerid": top.userInfo.centerid
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                parent.Common.loading(false);
                if (data.recode == "000000") {
                    self.busList = data.mi620list;
                    self.netList = data.mi623list;
                    var busNameArr = [],
                        netNameArr = [];
                    data.mi620list.forEach(function (item) {
                        busNameArr.push(item.appobusiname);
                    });
                    data.mi623list.forEach(function (item) {
                        netNameArr.push(item.website_name);
                    });
                    self.createBusList(busNameArr);
                    self.createNetList(netNameArr);
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
    getTableData: function (yearmonth, appodate, page, rows, resetBool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi62506.json",
            data: {
                'appobranchid': self.appobranchid,
                'appobusiid': self.appobusiid,
                'yearmonth': yearmonth,
                'appodate': appodate,
                'page': page,
                'rows': rows
            },
            // data: {
            //     'appobranchid': '1',
            //     'appobusiid': '001',
            //     'yearmonth': '2014-08',
            //     'appodate': '2014-08-26',
            //     'page': 1,
            //     'rows': 10
            // },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createNetQueryTable(data.rows);
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
                    self.createNetQueryTable([]);
                }
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize,
                    reset: resetBool
                });
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createNetList: function (data) {
        var self = this;
        self.netSelect = droplist({
            el: "#netQuery-netList",
            data: data,
            selectedChanged: function (val) {
                self.netList.forEach(function (item) {
                    if(item.website_name == val) {
                        self.netSelected = item.website_code;
                    }
                });
            }
        });
        if(data.length > 0) {
            self.netSelect.setSelected(data[0]);
        }
    },
    createBusList: function (data) {
        var self = this;
        self.busSelect =  droplist({
            el: "#netQuery-busList",
            data: data,
            selectedChanged: function (val) {
                self.busList.forEach(function (item) {
                    if(item.appobusiname == val) {
                        self.busSelected = item.appobusiid;
                    }
                });
            }
        });
        if(data.length > 0) {
            self.busSelect.setSelected(data[0]);
        }
    },
    createNetQueryTable: function (data) {
        var self = this;
        var cols = [
            { title:'预约业务凭证号', name:'apponum' ,width:100, align: 'center' },
            { title:'业务名称', name:'appobusiname' ,width:102, align: 'center'},
            { title:'状态', name:'appostate' ,width:74, align: 'center', renderer: function (val) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '已预约';
                        break;
                    case '04':
                        temp = '已办结';
                        break;
                    case '08':
                        temp = '已撤销';
                        break;
                }
                return temp;
            }},
            { title:'用户名', name:'username' ,width:72, align: 'center'},
            { title:'联系方式', name:'tel' ,width:100, align: 'center'},
            { title:'创建日期', name:'datecreated' ,width:92, align: 'center'},
            { title:'办结日期', name:'completedate' ,width:92, align: 'center'},
            { title:'撤销日期', name:'datecanceled' ,width:92, align: 'center'}
        ];
        if(self.tabler == null) {
            self.tabler = $('#netQuery-table').mmGrid({
                multiSelect: true,
                checkCol: false,
                height: '400px',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        } else {
            self.tabler.load(data);
        }
    },
    createDataPicker: function () {
        var today = new Date();
        $('#netQuery-month').val(today.getFullYear()+"-"+(today.getMonth()>8?"":"0")+(today.getMonth() + 1));
        function auto(flag){
            var yearStr=$('#netQuery-month').val();
            if (!checkyear(yearStr+"-01")){
                parent.Common.dialog({
                    type: "warning",
                    text: "输入正确的月份[yyyy-mm]",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        // post data
                    }
                });
                return;
            }
            if(yearStr!=""){
                var newYearMonth;
                if(flag=="add"){
                    newYearMonth = getNewDateSubNum(yearStr+"-28",-10);
                }
                if(flag=="sub"){
                    newYearMonth = getNewDateSubNum(yearStr+"-28",30);

                }
                newYearMonth=newYearMonth.substring(0,7);
                $('#netQuery-month').val(newYearMonth);
            }
        }
        //校验日期格式 yyyy-mm-dd
        function checkyear(year){
            var reg=/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/ig;
            return reg.test(year);
        }
        // 日期减去天数等于第二个日期
        function getNewDateSubNum(date,dNum){
            var date = new Date(date);
            date = date.valueOf();
            date = date - dNum * 24 * 60 * 60 * 1000;
            date = new Date(date);
            return date.getFullYear()+"-"+(date.getMonth()>8?"":"0")+(date.getMonth() + 1)+"-"+date.getDate();
        }
        $("#netQuery-month-sub").on("click", function () {
            auto("sub");
        });
        $("#netQuery-month-add").on("click", function () {
            auto("add");
        });
    },
    query: function () {},
    createCalendar: function () {
        var self = this;
        self.appobranchid = self.netSelected;
        self.appobusiid = self.busSelected;
        $('#fullCalendar').fullCalendar({
            fit : true,
            border : true,
            firstDay : 0,
            url : "./webapi62505.json",
            para:{
                "appobranchid": self.netSelected,
                "appobusiid": self.busSelected,
                "yearmonth": $('#netQuery-month').val(),
                "appodate": ""
            },
            year: $('#netQuery-month').val().split("-")[0],
            month: $('#netQuery-month').val().split("-")[1],
            current: new Date(),
            onSelect : function (date, target) {
                var abbr=target.abbr.split(',');
                var a = abbr[0]+"-"+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2];
                console.log(a, date, target, arguments);
                var yearmonth = abbr[0]+"-"+abbr[1],
                    appodate = abbr[0]+"-"+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2];
                $('#appodate').val(abbr[0]+"-"+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2]);
                $(".netQuery-table-box").show();
                self.getTableData(yearmonth, appodate, 1, self.pageSize, true);
            },
            onChange : function (year, month) {
                //document.getElementById("form62703").reset();
            },
            onLoadSuccess: function (data) {
                console.log(data);
            }
        });
    }
};
var busQuery = {
    pager: null,
    busList: [],
    netPlaceList: [],
    stateList: [
        {
            'value': '',
            'text': '请选择'
        },
        {
            'value': '01',
            'text': '已预约'
        },
        {
            'value': '04',
            'text': '已办结'
        },
        {
            'value': '08',
            'text': '已撤销'
        }
    ],
    channelList: [
        {
            'value': '',
            'text': '请选择'
        },
        {
            'value': '10',
            'text': 'APP'
        },
        {
            'value': '20',
            'text': '微信'
        },
        {
            'value': '30',
            'text': 'WEB端'
        }
    ],
    busSelected: "",
    netSelected: "",
    stateSelected: "",
    channelSelected: "",
    tabler: null,
    pageSize: 10,
    ids: '',
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#busQuery-table-pages",
            itemLength: 0,
            pageSize: self.pageSize,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.query(pageIndex, pageSize, false);
            }
        });
        self.getBusList();
    },
    getBusList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page62505.json",
            data: {
                'centerid': top.userInfo.centerid
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getNetPlaceList();
                    self.busList = data.mi620list;
                    var busNameArr = ["请选择"];
                    data.mi620list.forEach(function (item) {
                        busNameArr.push(item.appobusiname);
                    });
                    self.createBusList(busNameArr);
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
    getNetPlaceList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page62301Qry.html",
            data: {
                'id': "00000000",
                'pid': '00000000'
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                // 页面初始化
                self.createStateList();
                self.createChannelList();
                self.createDatepicker();
                self.query(1, self.pageSize, true);
                $(".busQuery-table-box").show();
                self.btnClick();
                self.netPlaceList = data;
                var netNameArr = ["请选择"];
                data.forEach(function (item) {
                    netNameArr.push(item.text);
                });
                self.createNetList(netNameArr);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createBusList: function (data) {
        var self = this;
        droplist({
            el: "#busQuery-droplist1",
            data: data,
            selectedChanged: function (val) {
                var hasSame = false;
                self.busList.forEach(function (item) {
                    if(item.appobusiname == val) {
                        self.busSelected = item.appobusiid;
                        hasSame = true;
                    }
                });
                if(!hasSame) {
                    self.busSelected = "";
                }
            }
        });
    },
    createNetList: function (data) {
        var self = this;
        droplist({
            el: "#busQuery-droplist2",
            data: data,
            selectedChanged: function (val) {
                var hasSame = false;
                self.netPlaceList.forEach(function (item) {
                    if(item.text == val) {
                        self.netSelected = item.id;
                        hasSame = true;
                    }
                });
                if(!hasSame) {
                    self.netSelected = "";
                }
            }
        });
    },
    createStateList: function () {
        var self = this,
            data = ["请选择", "已预约", "已办结", "已撤销"];
        droplist({
            el: "#busQuery-droplist3",
            data: data,
            selectedChanged: function (val) {
                self.stateList.forEach(function (item) {
                    if(item.text == val) {
                        self.stateSelected = item.value;
                    }
                });
            }
        });
    },
    createChannelList: function () {
        var self = this,
            data = ["请选择", "APP", "微信", "WEB端"];
        droplist({
            el: "#busQuery-droplist4",
            data: data,
            selectedChanged: function (val) {
                self.channelList.forEach(function (item) {
                    if(item.text == val) {
                        self.channelSelected = item.value;
                    }
                });
            }
        });
    },
    query: function (pageIndex, pageSize, resetBool) {
        var self = this;
        var apponum = $("#apponum").val(),
            certinum = $("#certinum").val(),
            personalname = $("#accname").val(),
            areaid = self.netSelected,
            appobusiid = self.busSelected,
            appostate = self.stateSelected,
            channel = self.channelSelected,
            startdate = $("#busQuery-beginTime").val(),
            enddate = $("#busQuery-endTime").val();
        $.ajax({
            type: "POST",
            url: "./webapi62504.json",
            data: {
                'apponum': apponum,
                'certinum': certinum,
                'personalname': personalname,
                'areaid': areaid,
                'appobusiid': appobusiid,
                'appostate': appostate,
                'channel': channel,
                'startdate': startdate,
                'enddate': enddate,
                'page': pageIndex,
                'rows': pageSize
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        reset: resetBool
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
                    self.pager.reset({
                        itemLength: 0,
                        reset: resetBool
                    });
                    self.createTable([]);
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'更新状态', name:'appoid' ,width:60, align: 'center', renderer: function (val) {
                return '<input type="checkbox" value="' + val + '" />';
            } },
            { title:'预约凭证号', name:'apponum' ,width:100, align: 'center'},
            { title:'预约网点', name:'appobranchname' ,width:88, align: 'center'},
            { title:'预约日期', name:'appodate' ,width:82, align: 'center'},
            { title:'创建日期', name:'datecreated' ,width:82, align: 'center', renderer: function (val) {
				return val.substr(0, 10);
			}},
            { title:'预约业务', name:'appobusiid' ,width:74, align: 'center', renderer: function (val, item, index) {
                var temp = "";
                self.busList.forEach(function (bus) {
                    if(bus.appobusiid == val) {
                        temp = bus.appobusiname;
                    }
                });
                if (temp == "") temp = val;
                return temp;
            }},
            { title:'预约状态', name:'appostate' ,width:76, align: 'center', renderer: function (val, item, index) {
                var temp = '';
                self.stateList.forEach(function (state) {
                    if(state.value == val) {
                        temp =  state.text;
                    }
                });
                return temp;
            }},
            { title:'用户名', name:'userid' ,width:104, align: 'center'},
            { title:'证件号', name:'certinum' ,width:120, align: 'center'},
            { title:'姓名', name:'username' ,width:90, align: 'center'},
            { title:'联系方式', name:'tel' ,width:112, align: 'center'}
        ];
        if(self.tabler == null) {
            self.tabler = $('#busQuery-table').mmGrid({
                multiSelect: true,
                checkCol: false,
                height: 'auto',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        } else {
            self.tabler.load(data);
        }
        parent.Common.loading(false);
    },
    createDatepicker: function () {
        laydate({
            elem: '#busQuery-beginTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                //alert('得到：'+datas);
            }
        });
        laydate({
            elem: '#busQuery-endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                //alert('得到：'+datas);
            }
        });
        laydate.skin('huanglv');
    },
    changeStatus: function () {
        var self = this;
        var checked = $('#busQuery-table input[type=checkbox]:checked');
        if(checked.length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: "请至少选择一条记录进行变更!",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        } else {
            var idsArr = [];
            for(var i=0; i<checked.length; i++) {
                idsArr.push($('#busQuery-table input[type=checkbox]:checked').eq(i).val());
            }
            self.ids = idsArr.join(',');
            var popupHTML = $(".busQuery-popup").html();
            parent.Common.popupShow(popupHTML);
        }
    },
    changeStatusFn: function () {
        var self = this;
        var value = parent.$(".reservation-edit input:checked").val();
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62503.json",
            data: {
                'appoid': self.ids,
                'appostate': value
            },
            datatype: "json",
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
                            self.query(1, self.pageSize, true);
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
    btnClick: function () {
        var self = this;
        $(".busQuery-query-btn").off().on("click", function () {
            self.query(1, self.pageSize, true);
        });
        $(".busQuery-change-btn").off().on("click", function () {
            self.changeStatus();
        });
    }
};
var netManagement = {
    pageSize: 10,
    createTree: function () {
        var self = this;
        var zTreeObj;
        var setting = {
            async: {
                enable: true,
                autoParam: ['id'],
                url: "./page62301Qry.html",
                type: "post"
            },
            callback: {
                onClick: function (a) {
                    $(".netManagement-top").show();
                    self.createTable();
                }
            }
        };

        var zNodes =[
            { name:"市区", open:true,
                children: [
                    { name:"高区管理部",
                        children: [
                            { name:"高区"},
                            { name:"高区"},
                            { name:"高区"},
                            { name:"高区"}
                        ]},
                    { name:"文登",
                        children: [
                            { name:"荣成"},
                            { name:"石岛"},
                            { name:"石岛"},
                            { name:"石岛"}
                        ]},
                    { name:"乳山", isParent:true}
                ]},
            { name:"高技术开发区",
                children: [
                    { name:"经济技术开发区1", open:true,
                        children: [
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"}
                        ]},
                    { name:"经济技术开发区2",
                        children: [
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"}
                        ]},
                    { name:"经济技术开发区3",
                        children: [
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"},
                            { name:"经济技术开发区"}
                        ]}
                ]},
            { name:"经济技术开发区", isParent:true}

        ];

        zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
    },
    createPager: function () {
        var self = this;
        // create pages
        var reservationNetManagementTablePager = pages({
            el: "#netManagement-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                console.log("genggaihgahaha", pageIndex);
            }
        });
        return reservationNetManagementTablePager;
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'业务类型', name:'appobranchid' ,width:100, align: 'center' },
            { title:'最长可预约天数', name:'maxdays' ,width:120, align: 'center'},
            { title:'预约时段模版', name:'appotemplateid' ,width:124, align: 'center'},
            { title:'预约业务启用日期', name:'begindate' ,width:136, align: 'center'},
            { title:'当天是否可预约', name:'freeuse1' ,width:110, align: 'center'},
            { title:'提前几个小时预约', name:'freeuse2' ,width:132, align: 'center'}
        ];
        data = {"total":1,"CMi623":{"centerId":"00076000","centreName":"中山市住房公积金管理中心","userid":"adminZS","username":"admin","longinip":"222.172.223.90","roleid":null,"appobranchid":null,"websiteCode":"002","appobusiid":null,"centerid":null,"maxdays":null,"appotemplateid":null,"begindate":null,"validflag":null,"datemodified":null,"datecreated":null,"loginid":null,"freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":null},"recode":"000000","msg":"成功","rows":[{"appobranchid":"113","website_code":"002","appobusiid":"008","centerid":"00076000","maxdays":"3","appotemplateid":"5","begindate":"2014-09-18","validflag":"1","datemodified":"2014-10-24 17:02:17.630       ","datecreated":"2014-09-18 16:04:52.685       ","loginid":"adminZS","freeuse1":"1","freeuse2":"2"},{"appobranchid":"113","website_code":"002","appobusiid":"008","centerid":"00076000","maxdays":"3","appotemplateid":"5","begindate":"2014-09-18","validflag":"1","datemodified":"2014-10-24 17:02:17.630       ","datecreated":"2014-09-18 16:04:52.685       ","loginid":"adminZS","freeuse1":"1","freeuse2":"2"},{"appobranchid":"113","website_code":"002","appobusiid":"008","centerid":"00076000","maxdays":"3","appotemplateid":"5","begindate":"2014-09-18","validflag":"1","datemodified":"2014-10-24 17:02:17.630       ","datecreated":"2014-09-18 16:04:52.685       ","loginid":"adminZS","freeuse1":"1","freeuse2":"2"},{"appobranchid":"113","website_code":"002","appobusiid":"008","centerid":"00076000","maxdays":"3","appotemplateid":"5","begindate":"2014-09-18","validflag":"1","datemodified":"2014-10-24 17:02:17.630       ","datecreated":"2014-09-18 16:04:52.685       ","loginid":"adminZS","freeuse1":"1","freeuse2":"2"},{"appobranchid":"113","website_code":"002","appobusiid":"008","centerid":"00076000","maxdays":"3","appotemplateid":"5","begindate":"2014-09-18","validflag":"1","datemodified":"2014-10-24 17:02:17.630       ","datecreated":"2014-09-18 16:04:52.685       ","loginid":"adminZS","freeuse1":"1","freeuse2":"2"}]};
        var reservationTable = $('#netManagement-table').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: '192px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        reservationTable.on("cellSelected", function (e, item, rowIndex, colIndex) {
            $(".netManagement-bottom").show();
            self.createBottomTable();
        });
        this.topBtnsClick(reservationTable);
    },
    createBottomTable: function (data) {
        var cols = [
            { title:'预约时段模版明细', name:'datecreated' ,width:348, align: 'center' },
            { title:'可预约人数上限', name:'appocnt' ,width:420, align: 'center'}
        ];
        data = {"recode":"000000","CMi624":{"centerId":"00063100","centreName":"威海市住房公积金管理中心","userid":"adminWH","username":"毕晓文","longinip":"10.11.99.185","roleid":null,"appobrantimeid":null,"appobranchid":"117","appotpldetailid":null,"appotemplateid":null,"centerid":null,"appocnt":null,"validflag":null,"datemodified":null,"datecreated":null,"loginid":null,"freeuse1":null,"freeuse2":null,"freeuse3":null,"freeuse4":null},"rows":[{"appobranchid":"117","appobrantimeid":"249","appocnt":1,"appotemplateid":"12","appotpldetailid":"41","centerid":"00063100","datecreated":"2016-08-01 20:14:32.552       ","datemodified":"2016-08-01 20:14:32.552       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":167,"loginid":"adminWH","validflag":"1"},{"appobranchid":"117","appobrantimeid":"250","appocnt":1,"appotemplateid":"12","appotpldetailid":"42","centerid":"00063100","datecreated":"2016-08-02 11:13:10.336       ","datemodified":"2016-08-02 11:13:10.336       ","freeuse1":"","freeuse2":"","freeuse3":"","freeuse4":168,"loginid":"adminWH","validflag":"1"}],"msg":"成功"};
        var reservationTable = $('#netManagement-table-template').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: '142px',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
        this.bottomBtnsClick(reservationTable);
    },
    topBtnsClick: function (table) {
        var table = table;
        $("#netManagement-btn-add").on("click", function () {
            var busListData = ["退休提取公积金", "12", "12", "12"],
                timeListData = ["测试1", "测试2", "测试3", "测试4"],
                boolData = ["是", "否"],
                busListHTML = '',
                timeListHTML = '',
                boolHTML = '';
            busListData.forEach(function (val, index) {
                busListHTML += "<option>" + val +"</option>";
            });
            $("#busListSelect").html(busListHTML);
            timeListData.forEach(function (val, index) {
                timeListHTML += "<option>" + val +"</option>";
            });
            $("#timeListSelect").html(timeListHTML);
            boolData.forEach(function (val, index) {
                boolHTML += "<option>" + val +"</option>";
            });
            $("#boolListSelect").html(boolHTML);
            // inner html
            var createHTML = $(".netManagement-create").html();
            parent.Common.popupShow(createHTML);
            // create datePicker
            parent.reservation.createDatepicker(".netManagement-create-datepicker");
        });
        $("#netManagement-btn-edit").on("click", function () {
            var busListData = ["退休提取公积金", "12", "12", "12"],
                timeListData = ["测试1", "测试2", "测试3", "测试4"],
                boolData = ["是", "否"],
                busListHTML = '',
                timeListHTML = '',
                boolHTML = '';
            busListData.forEach(function (val, index) {
                busListHTML += "<option>" + val +"</option>";
            });
            $("#busListSelect").html(busListHTML);
            timeListData.forEach(function (val, index) {
                timeListHTML += "<option>" + val +"</option>";
            });
            $("#timeListSelect").html(timeListHTML);
            boolData.forEach(function (val, index) {
                boolHTML += "<option>" + val +"</option>";
            });
            $("#boolListSelect").html(boolHTML);
            // inner html
            var createHTML = $(".netManagement-create").html();
            parent.Common.popupShow(createHTML);
            // create datePicker
            parent.reservation.createDatepicker(".netManagement-create-datepicker");
        });
        $("#netManagement-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
    },
    bottomBtnsClick: function (table) {
        var table = table;
        $("#netManagement-template-btn-add").on("click", function () {
            var timeListData = ["08:45-09:00", "09:00-09:15", "09:15-09:30", "10:00-10:15"],
                timeListHTML = '';
            timeListData.forEach(function (val, index) {
                timeListHTML += "<option>" + val +"</option>";
            });
            $("#netManagement-time-select").html(timeListHTML);
            // inner html
            var createHTML = $(".netManagement-bottom-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#netManagement-template-btn-edit").on("click", function () {
            var timeListData = ["08:45-09:00", "09:00-09:15", "09:15-09:30", "10:00-10:15"],
                timeListHTML = '';
            timeListData.forEach(function (val, index) {
                timeListHTML += "<option>" + val +"</option>";
            });
            $("#netManagement-time-edit-select").html(timeListHTML);
            // inner html
            var editHTML = $(".netManagement-bottom-edit").html();
            parent.Common.popupShow(editHTML);
        });
        $("#netManagement-template-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
    }
};
$(document).ready(function(){
    if($("#reservation-table").length > 0) {
        parent.Common.loading(true);
        reservationType.createPager();
        reservationType.getTableData(1, 10, true);
    } else if($("#reservation-time-table").length > 0) {
        // time
        reservationTime.createPager();
        reservationTime.createTable();
    } else if($("#note-table").length > 0) {
        // note 注意事项
        parent.Common.loading(true);
        note.createPager();
    } else if($("#netQuery-table").length > 0) {
        // net query
        netQuery.createPager();
        netQuery.createDataPicker();
        $("#netQuery-btn").on("click", function () {
            netQuery.createCalendar();
        });
    } else if($("#busQuery-table").length > 0) {
        parent.Common.loading(true);
        busQuery.createPager();
    } else if($(".netManagement-tree").length > 0){
        netManagement.createPager();
        netManagement.createTree();
    }
});