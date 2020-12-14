var desRule = {
    _tree: null,
    _tabler: null,
    _pager: null,
    _pageSize: 10,
    _selectedCenterId: null,
    _centerName: null,
    _cacheId: null,
    _tempRows: [],
    _tempRowFreeuse1: null,
    createPager: function () {
        var self = this;
        // create pages
        self._pager = pages({
            el: "#desRule-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self._pageSize = pageSize;
                self.query(pageIndex, pageSize, false);
            }
        });
        self.getCenterList();
        self.calcTreeHeight();
        self.bindClick();
    },
    calcTreeHeight: function () {
        $('#tree').height($(window).height());
    },
    bindClick: function () {
        var self = this;
        $("#btn-add").off().on("click", function () {
            var createHTML = $(".desRule-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#btn-edit").off().on("click", function () {
            var selected = self._tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selected.length > 1) {
                parent.Common.editMore();
                return;
            }
            var editHTML = $(".desRule-edit").html();
            parent.Common.popupShow(editHTML);
            self._cacheId = selected[0].id;
            parent.$("#edit-msg").val(selected[0].desensitizationid);
            parent.$("input[name='create-dateThem']").eq(selected[0].datethem).prop('checked', true);
            if(selected[0].datethem == '0') {
                parent.$('.is-not-date').show();
                parent.$('.is-date').hide();
            } else {
                parent.$('.is-not-date').hide();
                parent.$('.is-date').show();
                parent.$("#edit-dateType").val(selected[0].datetype);
            }
            parent.$("#edit-firstNum").val(selected[0].firstnum);
            parent.$("#edit-tailNum").val(selected[0].tailnum);
            parent.$("#edit-replaceChar").val(selected[0].replacechar);
            parent.$("#edit-demo1").val(selected[0].demo1);
            parent.$("#edit-demo2").val(selected[0].demo2);
            parent.$("#edit-detail").val(selected[0].detail);
            self._tempRowFreeuse1 = selected[0].freeuse1;
        });
        $("#btn-del").off().on("click", function () {
            var selected = self._tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: "是否删除选中数据？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    var ids = [];
                    selected.forEach(function (val) {
                        ids.push(val.id);
                    });
                    self.del(ids.join(','));
                }
            });
        });
        $("#btn-down").off().on("click", function () {
            self.exportExcel();
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
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    if(self._tree == null) {
                        self.createTree(data.rows);
                    } else {
                        self._tree.items = data.rows;
                        parent.Common.loading(false);
                    }
                    if(data.rows.length > 0) {
                        self._selectedCenterId = data.rows[0].centerid;
                        self._tree.selected = data.rows[0].centerid;
                        self._centerName = data.rows[0].centername;
                        self.getBmCode();
                    }
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
    getBmCode: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./getBmCode.json",
            datatype: "json",
            data: { 'bmcode': 'desensitizationid' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var dataRows = data.desensitizationid,
                        tempHtml = '';
                    dataRows.forEach(function (val) {
                        tempHtml += '<option value="' + val.itemid + '">' + val.itemval + '</option>';
                    });
                    $("#create-msg").html(tempHtml);
                    $("#edit-msg").html(tempHtml);
                    if(self._tree.items.length > 0) {
                        self.query(1, self._pageSize, true);
                    }
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
    createTree: function (data) {
        var self = this;
        self._tree = new Vue({
            el: '#tree',
            data: {
                items: data,
                selected: ''
            },
            methods: {
                query: function (id, centerName) {
                    parent.Common.loading(true);
                    this.selected = id;
                    self._selectedCenterId = id;
                    self._centerName = centerName;
                    self.query(1, self._pageSize, true);
                }
            }
        });
    },
    query: function (pageIndex, pageSize, pageRest) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi05904.json",
            datatype: "json",
            data: {
                'centerId': self._selectedCenterId,
                'pagenum': pageIndex,
                'ispaging': 1,
                'pagesize': pageSize
            },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self._pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRest
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
        if(data.length > 0 ) { $("#btn-down").show(); } else { $("#btn-down").hide(); }
        self._tempRows = data;
        if (self._tabler == null) {
            var cols = [
/*                { title:'序号', name:'id' ,width:32, align:'center', renderer: function (val, item, index) {
                    return index+1;
                }},*/
                { title:'脱敏数据项', name:'desensitizationmsg' ,width:90, align:'center' },
                { title:'首部字数', name:'firstnum' ,width:58, align:'center'},
                { title:'尾部字数', name:'tailnum' ,width:58, align:'center'},
                { title:'替换字符', name:'replacechar' ,width:58, align:'center'},
                { title:'脱敏前数据（示例）', name:'demo1' ,width:160, align:'center'},
                { title:'脱敏后数据（示例）', name:'demo2' ,width:160, align:'center'},
                { title:'描述', name:'detail' ,width:80, align:'center'},
                { title: '状态', name: 'freeuse1', width: 58, align: 'center', renderer: function (val, item, index) {
                    var temp = '';
                    if(val == '1' || val == null) {
                        temp = '<a href="javascript:;" style="color:#44b549" onclick="desRule.changeStatus(' + index + ')">启用</a>';
                    } else if (val == '0') {
                        temp = '<a href="javascript:;" style="color:red" onclick="desRule.changeStatus(' + index + ')">停用</a>';
                    }
                    return temp;
                } }
            ];
            self._tabler = $('#desRule-Table').mmGrid({
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
        } else {
            self._tabler.load(data);
        }
        parent.Common.loading(false);
    },
    exportExcel: function () {
        var self = this;
        location.href = './mi059ToExcel.do?centerId=' + self._selectedCenterId + '&fileName=' + self._centerName + '脱敏规则.xls';
/*        $.ajax({
            type: "GET",
            url: "./mi059ToExcel.do",
            datatype: "json",
            data: {
                'centerId': self._selectedCenterId,
                'fileName': self._centerName + '脱敏规则.xls'
            },
            success: function(data) {
                if (data.recode == "000000") {
                    parent.Common.loading(false);
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
        });*/
    },
    create: function () {
        var self = this;
        var desensitizationid = parent.$("#create-msg").val(),
            desensitizationMsg = parent.$("#create-msg option:selected").text(),
            firstNum = parent.$("#create-firstNum").val(),
            tailNum = parent.$("#create-tailNum").val(),
            replaceChar = parent.$("#create-replaceChar").val(),
            demo1 = parent.$("#create-demo1").val(),
            demo2 = parent.$("#create-demo2").val(),
            dateThem = parent.$("input[name='create-dateThem']:checked").val(),
            dateType = parent.$("#create-dateType").val(),
            detail = parent.$("#create-detail").val();
        parent.$("#popup-container").hide();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05901.json",
            datatype: "json",
            data: {
                'centerid': self._selectedCenterId,
                'desensitizationid': desensitizationid,
                'desensitizationmsg': desensitizationMsg,
                'firstnum': firstNum,
                'tailnum': tailNum,
                'replacechar': replaceChar,
                'demo1': demo1,
                'demo2': demo2,
                'datethem': dateThem,
                'datetype': dateType,
                'detail': detail,
                'validflag': 1,
                'freeuse1': '1'
            },
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
                            self.query(1, self._pageSize, true);
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
        var desensitizationid = parent.$("#edit-msg").val(),
            desensitizationMsg = parent.$("#edit-msg option:selected").text(),
            firstNum = parent.$("#edit-firstNum").val(),
            tailNum = parent.$("#edit-tailNum").val(),
            replaceChar = parent.$("#edit-replaceChar").val(),
            demo1 = parent.$("#edit-demo1").val(),
            demo2 = parent.$("#edit-demo2").val(),
            dateThem = parent.$("input[name='create-dateThem']:checked").val(),
            dateType = parent.$("#edit-dateType").val(),
            detail = parent.$("#edit-detail").val();
        parent.$("#popup-container").hide();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05902.json",
            datatype: "json",
            data: {
                'centerid': self._selectedCenterId,
                'id': self._cacheId,
                'desensitizationid': desensitizationid,
                'desensitizationmsg': desensitizationMsg,
                'firstnum': firstNum,
                'tailnum': tailNum,
                'replacechar': replaceChar,
                'demo1': demo1,
                'demo2': demo2,
                'datethem': dateThem,
                'datetype': dateType,
                'detail': detail,
                'validflag': 1,
                'freeuse1': self._tempRowFreeuse1
            },
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
                            self.query(1, self._pageSize, true);
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
        parent.$("#popup-container").hide();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05903.json",
            datatype: "json",
            data: {
                'centerid': self._selectedCenterId,
                'id': ids
            },
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
                            self.query(1, self._pageSize, true);
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
    changeStatus: function (index) {
        var self = this;
        var changeRow = self._tempRows[index];
        var desensitizationid = changeRow.desensitizationid,
            desensitizationMsg = changeRow.desensitizationmsg,
            firstNum = changeRow.firstnum,
            tailNum = changeRow.tailnum,
            replaceChar = changeRow.replacechar,
            demo1 = changeRow.demo1,
            demo2 = changeRow.demo2,
            dateThem = changeRow.datethem,
            dateType = changeRow.datetype,
            detail = changeRow.detail,
            freeuse1 = changeRow.freeuse1 == "0" ? "1" : "0";
        parent.$("#popup-container").hide();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi05902.json",
            datatype: "json",
            data: {
                'centerid': changeRow.centerid,
                'id': changeRow.id,
                'desensitizationid': desensitizationid,
                'desensitizationmsg': desensitizationMsg,
                'firstnum': firstNum,
                'tailnum': tailNum,
                'replacechar': replaceChar,
                'demo1': demo1,
                'demo2': demo2,
                'datethem': dateThem,
                'datetype': dateType,
                'detail': detail,
                'validflag': 1,
                'freeuse1': freeuse1
            },
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
                            self.query(1, self._pageSize, true);
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
desRule.createPager();