/**
 * Created by FelixAn on 2016/9/5.
 */
var policyConfig = {
    centerList: null,
    centerSelector: null,
    fnList: null,
    regionId: null,
    enable: false,
    tempFn: [],
    getCenterList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webappcomCenterId.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.centerList = data.mi001list;
                    self.regionId = data.mi001list[0].centerid;
                    self.createCityList(data.mi001list);
                    self.getFn();
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
    getFn: function () {
        var self = this,
            centerid = {'regionId': self.regionId };
        $.ajax({
            type: "POST",
            url: "./weixinapi00202.json",
            data: { 'centerid': JSON.stringify(centerid) },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "000000") {
                    parent.Common.loading(false);
                    self.fnList = data.rows;
                    var packName = [],
                        fnHtml = '',
                        packHtml = '';
                    data.rows.forEach(function (item) {
                        if(packName.indexOf(item.regionId) == -1) {
                            packName.push(item.regionId);
                        }
                        fnHtml += '<option value="' + item.keyname + '">' + item.nickname + '</option>';
                    });
                    packName.forEach(function (pack) {
                        packHtml += '<option value="' + pack + '">' + pack + '</option>';
                    });
                    $("#wechat-policyConfig-popup-features-city2").html(fnHtml);
                    $("#wechat-policyConfig-popup-bagName2").html(packHtml);
                    packName = null;
                    fnHtml = null;
                    packHtml = null;
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
    createCityList: function (data) {
        var self = this,
            vals = [],
            temp = '';
        data.forEach(function (item) {
            vals.push(item.centername);
            temp += '<option value="' + item.centerid + '">' + item.centername + '</option>';
        });
        self.centerSelector = droplist({
            el: "#wechat-policyConfig-top-droplist",
            data: vals
        });
        self.centerSelector.setSelected(vals[0]);
        $("#wechat-policyConfig-popup-select-city, #wechat-policyConfig-popup-edit-city").html(temp);
        self.btnClick();
    },
    btnClick: function () {
        var self = this;
        $("#query").off().on("click", function () {
            parent.Common.loading(true);
            var selected = self.centerSelector.getSelected(),
                centerid = '';
            self.centerList.forEach(function (item) {
                if(item.centername == selected) {
                    centerid = item.centerid;
                }
            });
            self.query(centerid);
        });
        $("#save").off().on("click", function () {
            if(self.tabler == null) return;
            parent.Common.loading(true);
            var tempData = self.tabler.rows(),
                fn = {};
            tempData.forEach(function (val) {
                if (typeof val == 'undefined') {
                    fn = {};
                } else {
                    fn[val.keyName] = {
                        'regionId': val.regionId,
                        'enable': val.enable,
                        'addon': {}
                    };
                }
            });
            self.save({
                'regionId': self.regionId,
                'enable': self.enable,
                'function': fn
            });
        });
    },
    query: function (centerid) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./weixinapi00303.json",
            data: { 'regionId': centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    self.createTable(data.rows[0].function);
                    var centername = '';
                    self.centerList.forEach(function (item) {
                        if(item.centerid == data.rows[0].regionId) {
                            centername = item.centername;
                        }
                    });
                    $(".wechat-policyConfig-info-box ul li").eq(0).find('span').text(centername);
                    $(".wechat-policyConfig-info-box ul li").eq(1).find('span').text(data.rows[0].enable == true ? "可用" : "不可用");
                    $(".wechat-policyConfig-info-box h2").hide().siblings("ul").show();
                    // set data to memory
                    self.tempFn = data.rows[0].function;
                    self.regionId = data.rows[0].regionId;
                    self.enable = data.rows[0].enable;
                    self.getFn();
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
                    $(".wechat-policyConfig-info-box ul").hide().siblings("h2").show();
                    self.createTable([]);
                    self.tempFn = [];
                }
                $(".wechat-policyConfig-box").show();
                self.setFnSelected();
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    setFnSelected: function () {
        var self = this;
        // set select fn
        var tempHtmlLeft = '',
            tempHtmlRight = '';
        self.fnList.forEach(function (item) {
            if(typeof self.tempFn[item.keyname] != 'undefined' && item.regionId == self.tempFn[item.keyname].regionId) {
                tempHtmlRight += '<option value="' + item.regionId + '" data-key="' + item.keyname + '">' + item.nickname + '</option>';
            } else {
                tempHtmlLeft += '<option value="' + item.regionId + '" data-key="' + item.keyname + '">' + item.nickname + '</option>';
            }
        });
        $("#selectLeft select").html(tempHtmlLeft);
        $("#selectRight select").html(tempHtmlRight);
    },
    createTable: function (data) {
        var self = this,
            arr = [],
            fns = self.fnList;
        var hasProp = false;
        for (var prop in data) {
            hasProp = true;
            break;
        }
        if(hasProp) {
            for(var row in data) {
                var keyDesc = '';
                fns.forEach(function (val) {
                    if(val.keyname == row && data[row].regionId == val.regionId) {
                        keyDesc = val.nickname;
                    }
                });
                arr.push({
                    'keyName': row,
                    'keyDesc': keyDesc,
                    'regionId': data[row].regionId,
                    'enable': data[row].enable
                });
            }
        }
        var cols = [
            { title:'序号', name:'keyName' ,width:40, align: 'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            { title:'KEY', name:'keyName' ,width:182, align: 'center' },
            { title:'功能描述', name:'keyDesc' ,width:380, align: 'center'},
            { title:'包名', name:'regionId' ,width:198, align: 'center'},
            { title:'是否可用', name:'enable' ,width:230, align: 'center', renderer: function (val, item, index) {
                return val ? "可用" : "不可用";
            }}
        ];
        if(self.tabler == null) {
            self.tabler = $('#wechat-policyConfig-table').mmGrid({
                checkCol: true,
                multiSelect: false,
                indexCol: false,
                height: '300px',
                cols: cols,
                items: arr,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
            self.topBtnClick();
            self.bottomBtnClick();
        } else {
            self.tabler.load(arr);
        }
    },
    topBtnClick: function () {
        var self = this;
        $("#wechat-orgInfo-add").off().on("click", function () {
            var createHTML = $(".wechat-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#wechat-orgInfo-edit").off().on("click", function () {
            if(self.regionId == null) {
                parent.Common.dialog({
                    type: "warning",
                    text: '暂无数据！',
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            var editHTML = $(".wechat-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#wechat-policyConfig-popup-edit-city").val(self.regionId);
            var status = self.enable ? "1" : "0";
            parent.$("#wechat-policyConfig-popup-edit-numberStatus").val(status);
        });
    },
    bottomBtnClick: function () {
        var self = this;
        $("#wechat-policy-add").off().on("click", function () {
            self.setFnSelected();
            var createHTML = $(".wechat-policy-create").html();
            parent.Common.popupShow(createHTML);
            /* popup tactics begin */
            (function(obj1,obj2){
                var leftSel = obj1;
                var rightSel = obj2;

                parent.$("#moveToRight").off().on("click",function(){
                    leftSel.find("option:selected").each(function(){
                        $(this).remove().appendTo(rightSel.find('select'));
                    });
                });
                parent.$("#moveToLeft").off().on("click",function(){
                    rightSel.find("option:selected").each(function(){
                        $(this).remove().appendTo(leftSel.find('select'));
                    });
                });
                leftSel.dblclick(function(){
                    $(this).find("option:selected").each(function(){
                        $(this).remove().appendTo(rightSel.find('select'));
                    });
                });
                rightSel.dblclick(function(){
                    $(this).find("option:selected").each(function(){
                        $(this).remove().appendTo(leftSel.find('select'));
                    });
                });
            })(parent.$('#selectLeft'), parent.$('#selectRight'));


            self.searchList = function(strValue) {
                var count = 0;
                if (strValue != "") {
                    parent.$('#selectLeft option').each(function(i) {
                        var contentValue = $(this).text();
                        if (
                            (contentValue.indexOf(strValue.toLowerCase()) < 0)
                            && (contentValue.indexOf(strValue.toUpperCase()) < 0)
                        ) {
                            $(this).css("display", "none");
                            count++;
                        } else {
                            $(this).css("display", "block");
                            $(this).attr('selected','selected')
                        }
                    });
                } else {
                    showAllOptions();
                }
            };
            self.checkThisText = function(strValue){
                if(strValue == ''){
                    showAllOptions();
                }
            };
            function showAllOptions(){
                parent.$('#selectLeft option').each(function(i) {
                    $(this).css("display", "block");
                });
            }
            /* popup tactics end */
        });
        $("#wechat-policy-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            }
            var editHTML = $(".wechat-policy-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#wechat-policyConfig-popup-features-city2").val(selected[0].keyName);
            parent.$("#wechat-policyConfig-popup-bagName2").val(selected[0].regionId);
            parent.$("#wechat-policyConfig-popup-able2").val(selected[0].enable ? "1": "0");
        });
    },
    save: function (data) {
        var self = this,
            _data = data;
        $.ajax({
            type: "POST",
            url: "./weixinapi00302.json",
            data: { 'value': JSON.stringify(_data) },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            parent.Common.loading(true);
                            self.query(_data.regionId);
                        }
                    });
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
                    $(".wechat-policyConfig-info-box ul").hide().siblings("h2").show();
                    self.createTable([]);
                }
                $(".wechat-policyConfig-box").show();
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    centerAdd: function () {
        var self = this;
        // set memory
        self.regionId = parent.$("#wechat-policyConfig-popup-select-city").val();
        var status = parent.$("#wechat-policyConfig-popup-select-numberStatus").val();
        self.enable = status == "1" ? true : false;
        // set page
        self.tempFn = [];
        self.createTable(self.tempFn);
        var centername = parent.$("#wechat-policyConfig-popup-select-city option:selected").text();
        $(".wechat-policyConfig-info-box ul li").eq(0).find('span').text(centername);
        $(".wechat-policyConfig-info-box ul li").eq(1).find('span').text(self.enable == true ? "可用" : "不可用");
        // dom show
        $(".wechat-policyConfig-info-box ul").show().siblings("h2").hide();
        parent.Common.popupClose();
    },
    centerEdit: function () {
        var self = this;
        // set memory
        self.regionId = parent.$("#wechat-policyConfig-popup-edit-city").val();
        var status = parent.$("#wechat-policyConfig-popup-edit-numberStatus").val();
        self.enable = status == "1" ? true : false;
        // set page
        self.tempFn = [];
        self.createTable(self.tempFn);
        var centername = parent.$("#wechat-policyConfig-popup-edit-city option:selected").text();
        $(".wechat-policyConfig-info-box ul li").eq(0).find('span').text(centername);
        $(".wechat-policyConfig-info-box ul li").eq(1).find('span').text(self.enable == true ? "可用" : "不可用");
        // dom show
        $(".wechat-policyConfig-info-box ul").show().siblings("h2").hide();
        parent.Common.popupClose();
    },
    fnCreate: function () {
        var selected = parent.$("#selectRight select option"),
            self = this;
        self.tempFn = {};
        for(var i = 0; i < selected.length; i++) {
            self.tempFn[selected.eq(i).attr("data-key")] = {
                'addon': {},
                'enable': true,
                'regionId': selected.eq(i).val()
            }
        }
        self.createTable(self.tempFn);
        parent.Common.popupClose();
    },
    fnEdit: function () {
        var self = this,
            selected = self.tabler.selectedRowsIndex(),
            tableData = self.tabler.rows(),
            keyName = '',
            keyDesc = '',
            regionId = '',
            enable = '';
        keyName = parent.$("#wechat-policyConfig-popup-features-city2").val();
        keyDesc = parent.$("#wechat-policyConfig-popup-features-city2 option:selected").text();
        regionId = parent.$("#wechat-policyConfig-popup-bagName2").val();
        enable = parent.$("#wechat-policyConfig-popup-able2").val();
        enable = enable == "1" ? true : false;
        tableData[selected[0]].keyName = keyName;
        tableData[selected[0]].regionId = regionId;
        tableData[selected[0]].enable = enable;
        tableData[selected[0]].keyDesc = keyDesc;
        parent.Common.popupClose();
        self.tabler.load(tableData);
    }
};
policyConfig.getCenterList();