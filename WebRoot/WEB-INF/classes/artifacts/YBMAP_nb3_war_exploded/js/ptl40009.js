/**
 * Created by wanghonghu on 2016/10/26.
 */

var user = top.userInfo;
var exceptionCode = {
    pageSize: 10,
    pager: null,
    tabler: null,
    tableData: null,
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
        self.getTableData(1, self.pageSize, true);
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
            page = page,
            rows = rows,
            centerid = $('#searchCustomer').val();
        var url = "./ptl40009Qry.json";

        function getDataSuccess(data) {
            if (typeof data == "string") {
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
                    ok: function () {
                    }
                });
            }
        }

        self.ajaxFn("post", url, getDataSuccess, {page: page, rows: rows, errcode: $('#errcode').val()})
    },
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            {
                title: '错误代码', name: 'errcode', width: 400, align: 'center', renderer: function (val, item, index) {
                return val;
            }
            },
            {
                title: '错误内容', name: 'errtext', width: 626, align: 'center', renderer: function (val, item, index) {
                return val;
            }
            }
        ];
        if (self.tabler != null) {
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
        var errorCode = parent.$('#create-errorcode').val(),
            errorText = parent.$('#create-errorcodeText').val();
        if (errorCode.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "错误代码不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if (errorText.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "错误内容不能为空！",
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

        function addDataSuccess(data) {
            if (typeof data == "string") {
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
        }

        self.ajaxFn("POST", "./ptl40009Add.json", addDataSuccess, {errcode: errorCode, errtext: errorText});
    },
    del: function () {
        parent.Common.loading(true);
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for (var i = 0; i < selected.length; i++) {
            delIds.push(selected[i].errcode);
        }
        function delSuccessFn(data) {
            if (typeof data == "string") {
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
        }

        self.ajaxFn("POST", "./ptl40009Del.json", delSuccessFn, {errcode: delIds.join(",")});
    },
    edit: function () {
        var self = this,
            selectedData = self.tabler.selectedRows();

        var errorCode = parent.$('#edit-errorcode').val(),
            errorText = parent.$('#edit-errorcodeText').val();
        if (errorText.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "错误内容不能为空！",
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

        function editSuccess(data) {

            if (typeof data == "string") {
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
        }

        self.ajaxFn("POST", "./ptl40009Mod.json", editSuccess, {errcode: errorCode, errtext: errorText});
    },
    submitExl: function() {
        var excelFile=parent.$('#excelfile').text();
        if(null == excelFile || "未上传" == excelFile || "" == excelFile){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "error",
                text: '请选择上传文件之后进行提交',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }else{
            parent.$("#uploadform").ajaxSubmit({
                dataType : "json",
                success:function(data){
                    parent.$("#popup-container").hide();
                }
            });
            parent.Common.popupClose();
        }

    },
    exportExl:function () {
        $('#exportForm').form('submit',{
            url: 'mi010ToExcel.do',
            onSubmit: function(param){
                var fileName = parent.$("#fileName").val();
                if(fileName == null || fileName == ""){
                    parent.$("#popup-container").hide();
                    parent.Common.dialog({
                        type: "error",
                        text: '请填写生成文件名称后进行提交',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            parent.$("#popup-container").show();
                        }
                    });
                    return false;
                }else{
                    if(fileName.indexOf(".xls") > -1){
                        var filenameArr = fileName.split(".");
                        param.fileName=filenameArr[0]+".xls";
                    }else{
                        param.fileName=fileName+".xls";
                    }
                }
            },
            success: function(data){
                parent.$("#popup-container").hide();
            },
            error :function(){
                parent.Common.ajaxError();
            }
        });
        parent.Common.popupClose();
    },
    btnClick: function () {
        var self = this;

        /*查询数据*/
        $('#btnquery').click(function () {
            self.getTableData(1, self.pageSize, true);
        });

        /*新建数据*/
        $('#orgManage-btn-add').click(function (e) {
            var createHTML = $(".codeTable-create").html();
            parent.Common.popupShow(createHTML);
        });

        /*删除数据*/
        $('#orgManage-btn-del').click(function (e) {
            var selected = $("#setTable").find("tr.selected");
            if (selected.length < 1) {
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
        $('#orgManage-btn-modify').click(function (e) {
            var selected = self.tabler.selectedRows();
            if (selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            if (selected.length > 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            var createHTML2 = $(".codeTable-edit").html();
            parent.Common.popupShow(createHTML2);

            parent.$('#edit-errorcode').val(selected[0].errcode);
            parent.$('#edit-errorcodeText').val(selected[0].errtext);

            if (selected[0].definitiontype == '1') {
                parent.$('.radioWrap').find('input[name="status1"]:first').attr('checked', 'checked');
                parent.$('.radioWrap').find('input[name="status1"]:last').attr('checked', false);
            } else {
                parent.$('.radioWrap').find('input[name="status1"]:first').attr('checked', false);
                parent.$('.radioWrap').find('input[name="status1"]:last').attr('checked', 'checked');
            }

        });

        // 批量导入
        $("#orgManage-btn-import").off().on("click", function () {
            var createHTML = $(".orgManage-popup-edit-container5").html();
            parent.Common.popupShow(createHTML);
            parent.$('#file').on('change', function( e ){

                var name = e.currentTarget.files[0].name;
                parent.$('#excelfile').text( name );

            });
        });

        // 批量导出
        $("#orgManage-btn-download").off().on("click", function () {
            var createHTML = $(".codeTable-export").html();
            parent.Common.popupShow(createHTML);
        });

    },
    /*ajax请求
     * type:"post"/"get"
     * url:请求路径
     * successFn:请求成功后回调函数
     * param:请求参数
     * */
    ajaxFn: function (type, url, successFn, param) {
        $.ajax({
            type: type,
            url: url,
            datatype: 'json',
            data: param,
            success: successFn,
            error: function () {
                parent.Common.ajaxError();
            }
        });
    }
};


$(function () {
    exceptionCode.createPager();
    exceptionCode.btnClick();
});






















