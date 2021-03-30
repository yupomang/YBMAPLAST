/**
 * Created by FelixAn on 2016/8/11.
 */
var interfaceConfig = {
    createDroplist: function () {
        var self = this;
        var interfaceConfigDroplist = droplist({
            el: "#interfaceConfig-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "华信永道(北京)科技股份有限公司", "永道在线咨询", "华信永道门户管理", "南京市住房公积金管理中心", "华北油田住房公积金管理中心"]
        });
        self.btnClick();
    },
    createViewTable: function (data) {
        // right center view table
        data = {"recode":"000000","CMi011":{"centername":null,"centerid":"00000000","classname":null,"url":null},"rows":[{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"},{"centername":"移动互联应用服务管理平台测试服务器","centerid":"00000000","classname":"MapMessageUtil","url":"http://api.map.baidu.com/geocoder/v2/?city={0}&address={1}&output=json&ak=7db9536a2794ce477288908163157bc6"}],"msg":"成功"};
        var cols = [
            { title:'中心ID', name:'centerid', width:'192', align: 'center' },
            { title:'中心名称', name:'centername', width:'202', align: 'center'},
            { title:'通讯接口类', name:'classname', width:'226', align: 'center'},
            { title:'HTTP通讯URL', name:'url', width:'352', align: 'center'},
            { title:'操作', name:'centerid', width:'66', align: 'center', renderer: function (val, item) {
                return '<a href="javascript:;" title="编辑" onclick=\'interfaceConfig.edit('+ JSON.stringify(item) +');\'>编辑</a>';
            }}
        ];
        $('#sysMgmt-interfaceConfig-centerViewTable').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: 'auto',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
    },
    edit: function (item) {
        var addHTML = $(".interfaceConfig-edit").html();
        parent.Common.popupShow(addHTML);
    },
    btnClick: function () {
        $("#sysMgmt-btn-add").on("click", function () {
            var addHTML = $(".interfaceConfig-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    //parent.Common.loading(true);
                }
            });
        });
    }
};
var pushMessage = {
    createDroplist: function () {
        var self = this;
        var pushMessageDroplist = droplist({
            el: "#pushMessage-droplist",
            data: ["移动互联应用服务管理平台测试服务器", "华信永道(北京)科技股份有限公司", "永道在线咨询", "华信永道门户管理", "南京市住房公积金管理中心", "华北油田住房公积金管理中心"]
        });
        self.btnClick();
    },
    createViewTable: function (data) {
        // right center view table
        data = {"recode":"000000","rows":[{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"},{"centername":"吓怕肯德基阿娇","centerid":"00000001","certificatename":"test1","certificatepassword":"1231","apikey":"123123","secritkey":"123123"}],"msg":"成功","CMi012":{"centername":null,"centerid":"00000001","certificatename":null,"certificatepassword":null,"apikey":null,"secritkey":null}};
        var cols = [
            { title:'中心ID', name:'centerid', width:'102', align: 'center' },
            { title:'中心名称', name:'centername', width:'192', align: 'center'},
            { title:'IOC证书名', name:'certificatename', width:'126', align: 'center'},
            { title:'证书密码', name:'certificatepassword', width:'162', align: 'center'},
            { title:'百度API_KEY', name:'apikey', width:'174', align: 'center'},
            { title:'百度SECRIT_KEY', name:'secritkey', width:'192', align: 'center'},
            { title:'操作', name:'centerid', width:'66', align: 'center', renderer: function (val, item) {
                return '<a href="javascript:;" title="编辑" onclick=\'pushMessage.edit('+ JSON.stringify(item) +');\'>编辑</a>';
            }}
        ];
        $('#sysMgmt-pushMessage-centerViewTable').mmGrid({
            multiSelect: true,
            checkCol: true,
            height: 'auto',
            cols: cols,
            items: data.rows,
            loadingText: "loading...",
            noDataText: "暂无数据。",
            loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
            sortable: false
        });
    },
    edit: function (item) {
        var editHTML = $(".pushMessage-edit").html();
        parent.Common.popupShow(editHTML);
    },
    btnClick: function () {
        $("#sysMgmt-btn-add").on("click", function () {
            var addHTML = $(".pushMessage-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    //parent.Common.loading(true);
                }
            });
        });
    }
};
var fnDeploy = {
    customerLevel: null,
    customerDropListObj: null,
    pageSize: 10,
    pager: null,
    tabler: null,
    oldFuncid: null,
    subItem: null,
    subTabler: null,
    parentName: null,
    oldSubname: null,
    subTableData: null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#sysMgmt-fnDeploy-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize);
                //self.config(self.subItem, self.parentName, pageIndex, false);
            }
        });
    },
    getCustomerLevel: function () {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./customerLevel.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.customerLevel = data.mi007list;
                    self.createDroplist();
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
    createDroplist: function () {
        var self = this,
            dataArr = self.customerLevel,
            droplistArr = ["全部"];
        var createSelect = $("#fnDeploy-create-select"),
            editSelect = $("#fnDeploy-edit-select"),
            tempHTML = '';
        for(var i=0; i < dataArr.length; i++) {
            droplistArr.push(dataArr[i].itemval);
            tempHTML += '<option value="' + dataArr[i].itemid + '">' + dataArr[i].itemval + '</option>';
        }
        self.customerDropListObj = droplist({
            el: "#sysMgmt-fnDeploy-droplist",
            data: droplistArr
        });
        createSelect.html(tempHTML);
        editSelect.html(tempHTML);
        self.getTableData(1, self.pageSize, true);
    },
    getTableData: function (page, rows, resetBool) {
        var self = this,
            funcid = $("#funcid").val(),
            funcname = $("#funcname").val(),
            level = self.customerDropListObj.getSelected(),
            page = page,
            rows = rows,
            dataArr = self.customerLevel;
        if(level  == "请选择" || level == "全部") {
            level = '';
        }
        for(var i=0; i < dataArr.length; i++) {
            if(level == dataArr[i].itemval) {
                level = dataArr[i].itemid;
            }
        }
        $.ajax({
            type: "POST",
            url: "./webapi05504.json",
            data: { 'funcid': funcid, 'funcname': funcname, 'uselevel': level, 'page': page, 'rows': rows },
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
            { title:'序号', name:'funcid' ,width:40, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'功能编号', name:'funcid' ,width:230, align:'center'},
            { title:'功能名称', name:'funcname' ,width:260, align:'center'},
            { title:'客户级别', name:'uselevel' ,width:230, align:'center', renderer: function (val, item) {
                var temp = '';
                switch (val) {
                    case "1":
                        temp = "普通";
                        break;
                    case "2":
                        temp = "高级";
                        break;
                    case "3":
                        temp = "合作伙伴";
                        break;
                }
                return temp;
            }},
            { title:'操作', name:'subfunction' ,width:286, align:'center', renderer: function (val, item) {
                var temp = '';
                if(val == "0") {
                    temp = '<a href="javascript:;" title="无子功能" style="color:#999">无子功能</a>';
                } else {
                    temp = '<a href="javascript:;" title="配置子功能" onclick="fnDeploy.config(\'' + item.funcid + '\',\'' + item.funcname + '\', 1, true)">配置子功能</a>';
                }
                return temp;
            } }
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#sysMgmt-fnDeploy-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
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
        var self = this;
        $("#fnDeploy-btn-add").off().on("click", function () {
            var addHTML = $(".fnDeploy-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#fnDeploy-btn-edit").off().on("click", function () {
            self.edit();
        });
        $("#fnDeploy-btn-del").off().on("click", function () {
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
        $(".sysMgmt-fnDeploy-top-greenBtn").off().on("click", function () {
            self.getTableData(1, self.pageSize, true);
        });
    },
    create: function () {
        var self = this;
        var funcid = parent.$('#create-funcid').val(),
            funcname = parent.$('#create-funcname').val(),
            customerLevel = parent.$("#fnDeploy-create-select").val(),
            subfunction = parent.$("#fnDeploy-create-subFn").val();
        if(funcid.replace(/^\s+/g,"") == "") {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能编号不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(/^\d{8}$/g.test(funcid) == false) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能编号必须是8位的数字！",
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
            url: "./webapi05501.json",
            datatype: "json",
            data: { 'funcid': funcid, 'funcname': funcname, 'uselevel': customerLevel, 'subfunction': subfunction },
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
            selectedData = self.tabler.selectedRows();
        if(selectedData.length < 1) {
            parent.Common.editNone();
            return;
        }
        if(selectedData.length != 1) {
            parent.Common.editMore();
            return;
        }
        self.oldFuncid = selectedData[0].funcid;
        var editHTML = $(".fnDeploy-edit").html();
        parent.Common.popupShow(editHTML);
        parent.$("#edit-funcid").val(selectedData[0].funcid);
        parent.$("#edit-funcname").val(selectedData[0].funcname);
        parent.$("#fnDeploy-edit-select").val(selectedData[0].uselevel);
        parent.$("#fnDeploy-create-subFn").val(selectedData[0].subfunction);
    },
    editPost: function () {
        var self = this;
        var funcid = parent.$('#edit-funcid').val(),
            funcname = parent.$('#edit-funcname').val(),
            customerLevel = parent.$("#fnDeploy-edit-select").val(),
            subfunction = parent.$("#fnDeploy-edit-subFn").val();
        if(funcid.replace(/^\s+/g,"") == "") {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能编号不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(/^\d{8}$/g.test(funcid) == false) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能编号必须是8位的数字！",
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
            url: "./webapi05503.json",
            datatype: "json",
            data: { 'funcid': funcid, "oldFuncid": self.oldFuncid, 'funcname': funcname, 'uselevel': customerLevel, 'subfunction': subfunction },
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
            selectedData = self.tabler.selectedRows(),
            delIds = [];
        if(selectedData.length < 1) {
            parent.Common.delNone();
            return;
        }
        for(var i=0; i<selectedData.length; i++) {
            delIds.push(selectedData[i].funcid);
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05502.json",
            datatype: "json",
            data: { 'funcid': delIds.join(",") },
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
                        ok: function () {}
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    config: function (funcid, funcname, page, resetBool) {
        var self = this;
        self.subItem = funcid;
        self.parentName = funcname;
        $(".sysMgmt-fnDeploy-sub-title").text("父功能名称：" + funcname);
        parent.Common.loading(true);
        $(".sysMgmt-fnDeploy-menu").hide();
        $(".sysMgmt-fnDeploy-sub").show();
        $.ajax({
            type: "POST",
            url: "./ptl40014Qry.json",
            data: { 'funcid': funcid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createSubTable(data.rows);
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
    createSubTable: function (data) {
        var self = this;
        self.subTableData = data;
        var cols = [
            { title:'子功能编号', name:'subname' ,width:240, align:'center'},
            { title:'子功能名称', name:'subdesc' ,width:808, align:'left'}
        ];
        if(self.subTabler != null) {
            self.subTabler.load(data);
        } else {
            self.subTabler = $('#sysMgmt-fnDeploy-sub-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: true,
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
        self.subBtnClick();
    },
    subBtnClick: function () {
        var self = this;
        $("#fnDeploy-sub-back").off().on("click", function () {
            $(".sysMgmt-fnDeploy-menu").show();
            $(".sysMgmt-fnDeploy-sub").hide();
        });
        $("#fnDeploy-sub-add").off().on("click", function () {
            $("#create-parentName").text(self.parentName);
            var addHTML = $(".fnDeploy-sub-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#fnDeploy-sub-edit").off().on("click", function () {
            $("#edit-parentName").text(self.parentName);
            var selectedData = self.subTabler.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            self.oldSubname = selectedData[0].subname;
            self.subItem = selectedData[0].funcid;
            $("#edit-number").attr("value", selectedData[0].subno);
            $("#edit-sub-number").attr("value", selectedData[0].subname);
            $("#edit-sub-name").attr("value", selectedData[0].subdesc);
            var editHTML = $(".fnDeploy-sub-edit").html();
            parent.Common.popupShow(editHTML);
        });
        $("#fnDeploy-sub-del").off().on("click", function () {
            var selectedData = self.subTabler.selectedRows();
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
                    parent.Common.loading(true);
                    self.subDel();
                }
            });
        });
        $("#fnDeploy-sub-down").off().on("click", function () {
            var infoData = self.subTableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.subTabler.selectedRowsIndex();
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
                var temp = newData[selectedDataIndex[i-1]].subno;
                newData[selectedDataIndex[i-1]].subno = newData[selectedDataIndex[i - 1] + 1].subno;
                newData[selectedDataIndex[i-1] + 1].subno = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.subTabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.subTabler.select(val + 1);
            });
            self.subTableData = newData;
        });
        $("#fnDeploy-sub-up").off().on("click", function () {
            var infoData = self.subTableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.subTabler.selectedRowsIndex();
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
                var temp = newData[selectedDataIndex[i]].subno;
                newData[selectedDataIndex[i]].subno = newData[selectedDataIndex[i] - 1].subno;
                newData[selectedDataIndex[i] - 1].subno = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.subTabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.subTabler.select(val - 1);
            });
            self.subTableData = newData;
        });
        $("#fnDeploy-sub-save").off().on("click", function () {
            self.subSave();
        });
    },
    subAdd: function () {
        var self = this,
            subname = parent.$("#create-sub-number").val(),
            subno = parent.$("#create-number").val(),
            subdesc = parent.$("#create-sub-name").val(),
            funcid = self.subItem;
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi01401.json",
            data: { 'funcid': funcid, 'subdesc': subdesc, 'subno': subno, 'subname': subname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.config(self.subItem, self.parentName);
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
    subEdit: function () {
        var self = this,
            subname = parent.$("#edit-sub-number").val(),
            subno = parent.$("#edit-number").val(),
            subdesc = parent.$("#edit-sub-name").val(),
            funcid = self.subItem;
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi01403.json",
            data: { 'funcid': funcid, "oldSubname": self.oldSubname, "oldFuncid": funcid, 'subdesc': subdesc, 'subno': subno, 'subname': subname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createSubTable(data.rows);
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
    subDel: function () {
        var self = this,
            selectedData = self.subTabler.selectedRows(),
            funcnames = [];
        if(selectedData.length < 1) {
            return;
        }
        selectedData.forEach(function (val) {
            funcnames.push(val.subname);
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi01402.json",
            datatype: "json",
            data: { 'funcid': selectedData[0].funcid, "subname": funcnames.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.config(self.subItem, self.parentName);
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
    subSave: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi01405.json?datalist=" + JSON.stringify(self.subTableData),
            datatype: "json",
            //data: { 'datalist': JSON.stringify(self.subTableData) },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.config(self.subItem, self.parentName);
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
var customerFn = {
    centerList: null,
    centerListData: [],
    vuer: null,
    centerid: null,
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
                            centerArr.push(item);
                        }
                    });
                    self.centerListData = centerArr;
                    self.createDroplist(centerArr);
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
        self.btnClick();
    },
    createDroplist: function (data) {
        var self = this,
            droplistData = [],
            nowCenterid = top.userInfo.centerid,
            nowCenterName = '';
        for(var i = 0; i < data.length; i++){
            droplistData.push(data[i].centername);
            if(nowCenterid == data[i].centerid) {
                nowCenterName = data[i].centername;
            }
        }
        self.centerList = droplist({
            el: "#customerFn-droplist",
            data: droplistData,
            selectedChanged: function (selectedValue) {
                self.centerChange(selectedValue);
            }
        });
        self.centerList.setSelected(nowCenterName);
    },
    centerChange: function (selectedValue) {
        var self = this,
            selectedCenter = selectedValue,
            centerData = self.centerListData;
        centerData.forEach(function (val) {
            if(selectedCenter == val.centername) {
                self.centerid = val.centerid;
                self.queryTree(val.centerid);
                self.btnClick();
                return;
            }
        });
    },
    queryTree: function (centerid) {
        var self = this,
            centerid = centerid;
        $.ajax({
            type: "POST",
            url: "./ptl40015Qry.json",
            data: { 'centerid': centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    if(self.vuer != null) {
                        self.vuer.func = data.functionjson;
                        self.vuer.postData.length = 0;
                    } else {
                        self.createVue(data.functionjson);
                    }
                    self.vuer.centerid = self.centerid;
                    // set Data
                    if(data.permissionMenuJson!=null) {
                        for(var i=0;i<data.permissionMenuJson.length;i++){
                            var qx = data.permissionMenuJson[i];
                            var funcid = qx.funcid;
                            var centerid = qx.centerid;
                            var permission = qx.permission;
                            if("00000000" != permission){
                                var seqnos = permission.split('');
                                for(var j=0;j<seqnos.length;j++){
                                    if(seqnos[j] == "1"){
                                        self.vuer.postData.push(funcid+"_"+centerid+"_"+Math.pow(2,(8-j-1)));
                                    }
                                }
                            }else{
                                self.vuer.postData.push(funcid + "_" + centerid + "_0");
                            }
                        }
                    }
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    btnClick: function () {
        var self = this;
        $(".common-btns-save").off().on("click", function () {
            self.save();
        });
        $(".customerFn-table").off().on("click", "h2", function () {
            $(this).siblings("dl").stop().slideToggle();
        });
    },
    createVue: function (func) {
        var self = this;
        self.vuer = new Vue({
            el: '.customerFn-table',
            data: {
                func: func,
                centerid: self.centerid,
                postData: []
            }
        });
    },
    save: function () {
        parent.Common.loading(true);
        var self = this,
            checkedData = self.vuer.postData,
            postData = 'centerid=' + self.centerid + '&';
        // checkedData.forEach(function (val) {
        //     postData += 'permission=' + val + '&';
        // });
        // if(checkedData.length == 0) {
        //     postData += 'permission=\"\"\"';
        // }
        $.ajax({
            type: "POST",
            url: "./ptl40015Upd.json",
            //data: postData.substring(0, postData.length - 1),
            data: { 'centerid': self.centerid, "permission": checkedData.join(",") },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(true);
                    self.queryTree(self.centerid);
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
    }
};
var configMenu = {
    pager: null,
    pageSize: 10,
    tabler: null,
    tableData: null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#configMenu-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData("", pageIndex, pageSize, false);
            }
        });
        self.getTableData("", 1, self.pageSize, true);
    },
    getTableData: function (urlname, page, rows, pageRestBool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05604.json",
            data: { 'urlname': urlname, 'page': page, 'rows': rows },
            datatype: "json",
            success: function(data) {
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            { title:'序号', name:'orderid', width:'202', align: 'center'},
            { title:'菜单名称', name:'urlname', width:'880', align: 'left'}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#configMenu-table').mmGrid({
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
        self.btnClick();
    },
    btnClick: function () {
        var self = this;
        $("#sysMgmt-btn-add").off().on("click", function () {
            var addHTML = $(".configMenu-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-edit").off().on("click", function () {
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length == 0) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            $("#edit-orderid").attr("value", selectedData[0].orderid);
            $("#edit-urlname").attr("value", selectedData[0].urlname);
            var editHTML = $(".configMenu-edit").html();
            parent.Common.popupShow(editHTML);
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
            self.getTableData($(".sysMgmt-configMenu-top input").eq(0).val(), 1, self.pageSize, true);
        });
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
        $("#sysMgmt-btn-save").off().on("click", function () {
            self.save();
        });
    },
    add: function () {
        var self = this,
            orderid = parent.$("#create-orderid").val(),
            urlname = parent.$("#create-urlname").val();
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi05601.json",
            data: { 'orderid': orderid, 'urlname': urlname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData("", 1, self.pageSize, true);
                    self.pager.reset({
                        itemLength: data.total,
                        reset: true
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
        var self = this,
            selectedData = self.tabler.selectedRows(),
            orderid = parent.$("#edit-orderid").val(),
            urlname = parent.$("#edit-urlname").val();
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi05603.json",
            data: { 'cdid': selectedData[0].cdid, 'orderid': orderid, 'urlname': urlname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData("", 1, self.pageSize, true);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: true
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
            selectedData = self.tabler.selectedRows(),
            ids = [];
        selectedData.forEach(function (val, index) {
            ids.push(val.cdid);
        });
        var idsStr = ids.join(",");
        $.ajax({
            type: "POST",
            url: "./webapi05602.json",
            data: { 'cdid': idsStr },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData("", 1, self.pageSize, true);
                    self.pager.reset({
                        itemLength: data.total,
                        reset: true
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
    save: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05605.json",
            data: { 'datalist': JSON.stringify(self.tableData) },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData("", 1, self.pageSize, false);
                    self.pager.reset({
                        itemLength: data.total,
                        reset: true
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
var roleConfig = {
    pager: null,
    tabler: null,
    centerListData: null,
    flag: {
        "0": "公有",
        "1": "私有"
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#roleConfig-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                //self.getTableData(pageIndex, false);
            }
        });
        self.queryCenter();
        self.getFlagBool();
    },
    getFlagBool: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40002Param.json",
            datatype: "json",
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var temp = '';
                    if (top.userInfo.centerid != "00000000") {
                        data.attributeFlgListTmp.forEach(function (val) {
                            temp += '<option value="' + val.itemid + '">' + val.itemval + '</option>';
                        });
                    } else {
                        data.attributeFlgList.forEach(function (val) {
                            temp += '<option value="' + val.itemid + '">' + val.itemval + '</option>';
                        });
                    }
                    $("#create-flag").html(temp);
                    $("#edit-flag").html(temp);
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
    queryCenter: function () {
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
                            centerArr.push(item);
                        }
                    });
                    self.centerListData = centerArr;
                    self.queryTable();
                    self.setCenterList();
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
    setCenterList: function () {
        var self = this,
            centerList = self.centerListData,
            tempHtml = '';
        centerList.forEach(function (val) {
            tempHtml += '<option value="' + val.centerid + '">' + val.centername + '</option>';
        });
        $("#create-customer-name").html(tempHtml);
        $("#edit-customer-name").html(tempHtml);
    },
    queryTable: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40002Qry.json",
            data: { 'centerid': top.userInfo.centerid },
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
            { title:'序号', name:'roleid', width:'62', align: 'center', renderer: function (val, item, index) {
                return index + 1;
            } },
            { title:'角色代码', name:'roleid', width:'180', align: 'center'},
            { title:'角色名称', name:'rolename', width:'266', align: 'center'},
            { title:'客户名称', name:'centerid', width:'260', align: 'center', renderer: function (val) {
                var placeholder = "-";
                self.centerListData.forEach(function (listVal) {
                    if(listVal.centerid == val) {
                        placeholder = listVal.centername;
                    }
                });
                return placeholder;
            }},
            { title:'所属标志', name:'attributeflg', width:'260', align: 'center', renderer: function (val) {
                return self.flag[val];
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#roleConfig-table').mmGrid({
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
        self.btnClick();
    },
    btnClick: function () {
        var self = this;
        $("#sysMgmt-btn-add").on("click", function () {
            var addHTML = $(".roleConfig-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-edit").on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selected.length != 1) {
                parent.Common.editMore();
                return;
            }
            var editHTML = $(".roleConfig-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#edit-code").val(selected[0].roleid);
            parent.$("#edit-name").val(selected[0].rolename);
            parent.$("#edit-customer-name").val(selected[0].centerid);
            parent.$("#edit-flag").val(selected[0].attributeflg);
        });
        $("#sysMgmt-btn-del").on("click", function () {
            var selected = self.tabler.selectedRows();
            if (selected.length < 1) {
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
                    parent.Common.loading(true);
                    self.del();
                }
            });
        });
    },
    add: function () {
        var self = this,
            roleid = parent.$("#create-code").val(),
            rolename = parent.$("#create-name").val(),
            centerid = parent.$("#create-customer-name").val(),
            attributeflg = parent.$("#create-flag").val();
        if(roleid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "角色代码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(rolename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "角色名称不能为空！",
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
            type: "POST",
            url: "./ptl40002Add.json",
            data: { 'roleid': roleid, 'rolename': rolename, 'centerid': centerid, 'attributeflg': attributeflg },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryTable();
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
            roleid = parent.$("#edit-code").val(),
            rolename = parent.$("#edit-name").val(),
            centerid = parent.$("#edit-customer-name").val(),
            attributeflg = parent.$("#edit-flag").val();
        if(roleid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "角色代码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(rolename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "角色名称不能为空！",
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
            type: "POST",
            url: "./ptl40002Upd.json",
            data: { 'roleid': roleid, 'rolename': rolename, 'centerid': centerid, 'attributeflg': attributeflg },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryTable();
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
            ids = [];
        selected.forEach(function (val) {
            ids.push(val.roleid);
        });
        $.ajax({
            type: "POST",
            url: "./ptl40002Del.json",
            data: { 'roleid': ids.join(",") },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryTable();
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
var rolePermission = {
    centerListData: null,
    roleListData: null,
    centerList: null,
    roleList: null,
    vuer: null,
    centerid: null,
    baseRoles: [],
    tempRoles: [], // 切换中心的时候，拼接角色下拉菜单的容器
    isAdmin: false,
    pageInitialize: function () {
        // 页面初始化
        var self = this;
        var currentUserId = top.userInfo.centerid;
        self.getBaseRole();
        if(currentUserId != "00000000") {
            $(".sysMgmt-rolePermission-top p").eq(0).hide();
            $("#rolePermission-droplist").hide();
            self.getRole(currentUserId);
        } else {
            self.isAdmin = true;
            self.getCenterList();
        }
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
                            centerArr.push(item);
                        }
                    });
                    self.centerListData = centerArr;
                    self.createCenterSelect();
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
    getBaseRole: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40002Qry.json",
            data: { 'centerid': '00000000' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    data.rows.forEach(function (item) {
                        if(item.attributeflg == "0") {
                            self.baseRoles.push(item);
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
    getRole: function (centerid) {
        var self = this;
        self.centerid = centerid;
        $.ajax({
            type: "POST",
            url: "./ptl40002Qry.json",
            data: { 'centerid': centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.tempRoles.length = 0;
                    self.baseRoles.forEach(function (commonRole) {
                        self.tempRoles.push(commonRole);
                    });
                    data.rows.forEach(function (item) {
                        if(item.attributeflg == "1") {
                            self.tempRoles.push(item);
                        }
                    });
                    self.roleListData = self.tempRoles;
                    self.createRolelist();
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
    createCenterSelect: function () {
        var self = this,
            centerList = self.centerListData,
            centerFontList = [];
        centerList.forEach(function (val) {
            centerFontList.push(val.centername);
        });
        self.centerList = droplist({
            el: "#rolePermission-droplist",
            data: centerFontList,
            selectedChanged: function (val) {
                parent.Common.loading(true);
                centerList.forEach(function (item) {
                    if(item.centername == val) {
                        self.getRole(item.centerid);
                        return;
                    }
                });
            }
        });
        self.btnClick();
    },
    createRolelist: function () {
        var self = this,
            roleList = self.roleListData,
            roleFontList = ['全部角色'];
        roleList.forEach(function (val) {
            roleFontList.push(val.rolename);
        });
        if (roleFontList.length < 2) {
            $("#rolePermission-name").css("display", "inline-block").text("尚未配置角色");
            $("#rolePermission-droplist-role").hide();
            $(".rolePermission-table-box, .rolePermission-btns").hide();
            $(".sysMgmt-rolePermission-top a").addClass("disabled");
        } else {
            if (self.roleList != null) {
                self.roleList.resetData(roleFontList);
            } else {
                self.roleList = droplist({
                    el: "#rolePermission-droplist-role",
                    data: roleFontList
                });
                self.roleList.setSelected('全部角色');
            }
            $(".sysMgmt-rolePermission-top a").removeClass("disabled");
            $("#rolePermission-name").css("display", "inline-block").text("角色");
            $("#rolePermission-droplist-role").css("display", "inline-block");
        }
        parent.Common.loading(false);
    },
    btnClick: function () {
        var self = this;
        $(".customerFn-table h2").off().on("click", function () {
            $(".customerFn-table").find("dl").stop().slideUp();
            $(this).siblings("dl").stop().slideDown();
        });
        $(".sysMgmt-rolePermission-top a").off().on("click", function () {
            if($(this).hasClass("disabled")) return;
            self.queryTree();
        });
        $(".common-btns-save").off().on("click", function () {
            self.save();
        });
    },
    queryTree: function () {
        var self = this,
            centerid = '',
            selectedCenterName = self.centerList.getSelected(),
            centerList = self.centerListData;
        centerList.forEach(function (val) {
            if(val.centername == selectedCenterName) {
                centerid = val.centerid;
            }
        });
        function filerRole (roleSelected, data) {
            var temp = [];
            if(roleSelected == '全部角色') {
                temp = data;
            } else {
                data.forEach(function (val) {
                    if (val.rolename == roleSelected) {
                        temp[0] = val;
                    }
                });
            }
            return temp;
        }
        $.ajax({
            type: "POST",
            url: "./ptl40004Get.json",
            data: { 'centerid': centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var roleSelected = self.roleList.getSelected();
                    if(self.vuer != null) {
                        self.vuer.menu = data.menujson;
                        self.vuer.postData.length = 0;
                        self.vuer.roles.length = 0;
                        self.vuer.roles = filerRole(roleSelected, data.rolejson);
                    } else {
                        data.rolejson = filerRole(roleSelected, data.rolejson);
                        self.createVuer(data.menujson, data.rolejson);
                    }
                    // set Data
                    if(data.permissionMenuJson!=null) {
                        var permissionMenuJson = data.permissionMenuJson;
                        for(var i=0;i<permissionMenuJson.length;i++){
                            var qx=permissionMenuJson[i];
                            var funcid=qx.funcid;
                            var roleid=qx.roleid;
                            var permission = qx.permission;
                            if("00000000" != permission){
                                var seqnos = permission.split('');
                                for(var j=0;j<seqnos.length;j++){
                                    if(seqnos[j] == "1"){
                                        self.vuer.postData.push(funcid+"_"+ roleid +"_"+Math.pow(2,(8-j-1)));
                                    }
                                }
                            }else{
                                self.vuer.postData.push(funcid + "_" + roleid + "_0" );
                            }
                        }
                    }
                    $(".rolePermission-table-box, .rolePermission-btns").show();
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.loading(true);
                parent.Common.ajaxError();
            }
        });
    },
    createVuer: function (menu, role) {
        var self = this;
        self.vuer = new Vue({
            el: '.rolePermission-table-box',
            data: {
                menu: menu,
                centerid: self.centerid,
                roles: role,
                postData: []
            }
        });
    },
    save: function () {
        parent.Common.loading(true);
        var self = this,
            checkedData = self.vuer.postData,
            postData = 'centerid=' + self.centerid + '&';
        checkedData.forEach(function (val) {
            postData += 'permission=' + val + '&';
        });
        if(checkedData.length == 0) {
            postData += 'permission=\"\"\"';
        }
        $.ajax({
            type: "POST",
            url: "./ptl40004Upd.json",
            data: postData.substring(0, postData.length - 1),
            //data: { 'centerid': self.centerid, "permission": checkedData.join(",") },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(true);
                    self.queryTree();
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
    }
};
var userManagement = {
    pager: null,
    centerListData: null,
    roleList: null,
    tabler: null,
    centerid: null,
    isAdmin: false,
    userStatus: {
        "0": "正常",
        "1": "停用"
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#userManagement-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                //self.getTableData(pageIndex, false);
            }
        });
        // 判断是不是超级用户
        var currentUserId = top.userInfo.centerid;
        if(currentUserId != '00000000') {
            parent.Common.loading(true);
            $(".sysMgmt-userManagement-top").hide();
            $(".userManagement-btns, .userManagement-box").css("visibility", 'visible');
            self.queryRole(currentUserId);
            self.centerid = currentUserId;
        } else {
            self.isAdmin = true;
            self.getCenterList();
        }
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
                    self.centerListData = data.rows;
                    self.createDroplist();
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
    createDroplist: function () {
        var self = this,
            data = self.centerListData,
            temp = [];
        for(var i = 0; i<data.length; i++) {
            temp.push(data[i].centername);
        }
        droplist({
            el: "#userManagement-droplist",
            data: temp,
            selectedChanged: function (val) {
                parent.Common.loading(true);
                $(".userManagement-btns, .userManagement-box").css("visibility", 'visible');
                self.queryRole(val);
            }
        });
    },
    queryRole: function (val) {
        var self = this,
            centerListData = self.centerListData,
            centerid = '';
        if (self.isAdmin) {
            $(".reservation-popup-title").text(val);
            centerListData.forEach(function (item) {
                if(item.centername == val) {
                    centerid = item.centerid;
                }
            });
        } else {
            $(".reservation-popup-title").remove();
            centerid = val;
        }
        $.ajax({
            type: "POST",
            url: "./ptl40001RoleList.json",
            datatype: "json",
            data: { 'centerid': centerid },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.roleList = data.rolelist;
                    self.setRoleList();
                    self.queryUser(centerid);
                    self.centerid = centerid;
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
    setRoleList: function () {
        var self = this,
            roleList = self.roleList,
            tempHtml = '';
        roleList.forEach(function (val) {
            tempHtml += '<label><input type="checkbox" value="' + val.roleid + '"><span>' + val.rolename + '</span></label>';
        });
        $("#create-role-list").html(tempHtml);
        $("#edit-role-list").html(tempHtml);
    },
    queryUser: function (centerid) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./ptl40001.json",
            datatype: "json",
            data: { 'centerid': centerid },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
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
    createTable: function (data) {
        var self = this,
            roleList = self.roleList;
        function getRoleName (id) {
            var name = '';
            self.roleList.forEach(function (item) {
                if(item.roleid == id) {
                    name = item.rolename;
                }
            });
            return name;
        }
        var cols = [
            { title:'序号', name:'centerid', width:'62', align: 'center', renderer: function (val, item, index) {
                return index + 1;
            } },
            { title:'系统用户代码', name:'loginid', width:'180', align: 'center'},
            { title:'系统用户名称', name:'opername', width:'166', align: 'center'},
            { title:'电话', name:'phone', width:'120', align: 'center'},
            { title:'角色', name:'roleList', width:'240', align: 'center', renderer: function (val) {
                var temp = [];
                if(val.length < 1) return '-';
                for(var i = 0; i < val.length; i++) {
                    temp.push(getRoleName(val[i].roleid));
                }
                return temp.join(",");
            }},
            { title:'状态', name:'stat', width:'250', align: 'center', renderer: function (val) {
                return self.userStatus[val];
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#userManagement-table').mmGrid({
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
        self.btnClick();
    },
    btnClick: function () {
        var self = this;
        $("#sysMgmt-btn-add").on("click", function () {
            var addHTML = $(".userManagement-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-edit").on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) return;
            if(selected.length != 1) {
                parent.Common.dialog({
                    type: "warning",
                    text: "修改只能选择一条数据！",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            var editHTML = $(".userManagement-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#edit-loginid").text(selected[0].loginid);
            parent.$("#edit-opername").val(selected[0].opername);
            parent.$("#edit-phone").val(selected[0].phone);
            parent.$("#edit-stat").find('input[name="status"]').eq(selected[0].stat).prop("checked", true);
            selected[0].roleList.forEach(function (val) {
                parent.$("#edit-role-list").find('input[value="' + val.roleid.replace(/ /g, "") + '"]').prop("checked", true);
            });
        });
        $("#sysMgmt-btn-del").on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) return;
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.Common.loading(true);
                    self.del();
                }
            });
        });
    },
    verification: function (loginid, opername, phone) {
        if(loginid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "系统用户代码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        if(opername.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "系统用户名称不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        if(phone.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "电话不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        if(phone.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "电话不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "电话格式有误！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        return true;
    },
    add: function () {
        var self = this;
        var postData = '',
            roleList = [],
            loginid = parent.$("#create-loginid").val(),
            opername = parent.$("#create-opername").val(),
            phone = parent.$("#create-phone").val(),
            stat = parent.$("#create-stat").find('input[name="status"]:checked').val(),
            checked = parent.$("#create-role-list").find('input:checked');
        if(!self.verification(loginid, opername, phone)) return;
        for(var i=0; i<checked.length; i++) {
            //roleList += '&role=' + parent.$("#create-role-list").find('input:checked').eq(i).val();
            roleList.push(parent.$("#create-role-list").find('input:checked').eq(i).val());
        }
        //postData += 'centerid=' + self.centerid + '&password=202cb962ac59075b964b07152d234b70&loginid=' + loginid + '&opername="' + opername + '"&phone=' + phone + '&stat=' + stat + roleList;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./ptl40001Add.json",
            datatype: "json",
            //data: postData,
            data: {
                'centerid': self.centerid,
                'password': '202cb962ac59075b964b07152d234b70',
                'loginid': loginid,
                'opername': opername,
                'phone': phone,
                'stat': stat,
                'roles': roleList.join(',')
            },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryUser(self.centerid);
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
    edit: function () {
        var self = this;
        var postData = '',
            roleList = [],
            loginid = parent.$("#edit-loginid").text(),
            opername = parent.$("#edit-opername").val(),
            phone = parent.$("#edit-phone").val(),
            stat = parent.$("#edit-stat").find('input[name="status"]:checked').val(),
            checked = parent.$("#edit-role-list").find('input:checked');
        if(!self.verification(loginid, opername, phone)) return;
        for(var i=0; i<checked.length; i++) {
            //roleList += '&role=' + parent.$("#edit-role-list").find('input:checked').eq(i).val();
            roleList.push(parent.$("#edit-role-list").find('input:checked').eq(i).val());
        }
        //postData += 'centerid=' + self.centerid + '&password=202cb962ac59075b964b07152d234b70&loginid=' + loginid + '&opername=' + opername + '&phone=' + phone + '&stat=' + stat + roleList;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./ptl40001Upd.json",
            datatype: "json",
            data: {
                'centerid': self.centerid,
                'password': '202cb962ac59075b964b07152d234b70',
                'loginid': loginid,
                'opername': opername,
                'phone': phone,
                'stat': stat,
                'roles': roleList.join(',')
            },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryUser(self.centerid);
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
    del: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            ids = [];
        if(selected.length < 1) return;
        parent.Common.loading(true);
        selected.forEach(function (val) {
            ids.push(val.loginid);
        });
        $.ajax({
            type: "POST",
            url: "./ptl40001Del.json",
            datatype: "json",
            data: { 'loginid': ids.join(",") },
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.queryUser(self.centerid);
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
    }
};
$(document).ready(function(){
    if($("#interfaceConfig-droplist").length > 0) {
        interfaceConfig.createDroplist();
        interfaceConfig.createViewTable();
        interfaceConfig.btnClick();
    } else if($("#pushMessage-droplist").length > 0) {
        pushMessage.createDroplist();
        pushMessage.createViewTable();
        pushMessage.btnClick();
    } else if($("#sysMgmt-fnDeploy-table").length > 0) {
        // 功能配置
        fnDeploy.createPager();
        fnDeploy.getCustomerLevel();
        fnDeploy.btnClick();
    } else if($("#customerFn-droplist").length > 0) {
        // 客户功能分配
        parent.Common.loading(true);
        customerFn.getCenterList();
    } else if($("#configMenu-table").length > 0) {
        // 菜单配置
        configMenu.createPager();
    } else if($("#roleConfig-table").length > 0) {
        // 角色配置
        parent.Common.loading(true);
        roleConfig.createPager();
    } else if($("#rolePermission-droplist").length > 0) {
        // 角色权限分配
        rolePermission.pageInitialize();
    } else if($("#userManagement-droplist").length > 0) {
        // 系统用户管理
        userManagement.createPager();
        userManagement.createTable();
    }
});