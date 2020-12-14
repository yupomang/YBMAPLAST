//应用管理 
//xingmc 
//2016年8月8日
/**
 * Modified by M on 2016/9/9.
 */

//初始化分页信息
var user = top.userInfo;
var pageSizeTemp = 10;

var appm = {
    pager: null,
    tableObj: null,
    createPager: function (Id) {
        var _this = this;
        _this.pager = pages({
            el: "#channaelAppMan-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                pageSizeTemp = pageSize;
                init(false, pageIndex, pageSize, $('#customer-name').val());
            }
        });
    },
    customerName: '',  //客户名称
    channelName: '',   //渠道名称
};


//所有的错误信息
function errMsg() {
    parent.Common.dialog({
        type: 'error',
        text: '网络错误！请刷新页面或联系管理员！',
        isShow: true,
        cancelShow: true,
        okText: '确定',
        ok: function () {

        }
    });
}
// get customer name


var appManage = new Vue({
    el: '#cam',
    data: {
        isAdd: true,
        //title:['应用管理 - 增加','应用管理 - 修改'],
        form: {
            customerName: '',	//客户名称
            channelName: '',		//渠道名称
            appName: '',			//应用名称
            isUsing: '',			//是否启用
            serviceStart: '',	//服务开始时间
            serviceEnd: '',		//服务结束时间
        },
        authForm: {
            appid: '',
            appkey: '',
            appaes: '',
            apptoken: '',
            apptokentext: '',
            tokendate: ''
        }
    },
    methods: {
        clearForm: function () {
            var _this = this;
            _this.form.customerName = '';
            _this.form.channelName = '';
            _this.form.appName = '';
            _this.form.isUsing = '';
            _this.serviceStart = '';
            _this.serviceEnd = '';
        },
        clearAuth: function () {
            var _this = this;
            _this.authForm.appid = '';
            _this.authForm.appkey = '';
            _this.authForm.appaes = '';
            _this.authForm.apptoken = '';
            _this.authForm.apptokentext = '';
            _this.authForm.tokendate = '';
        }
    }
});


//获取全局的中转移
var centeridObj = [];
var channelObj = [];
//第一次加载的时候，从数据库读取客户名称
function getAll() {
    parent.Common.loading(true);
    $.ajax({
        type: 'POST',
        url: './webappcomCenterId.json',
        data: {},
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode == '000000') {
                var customerOptions = '';
                for (var i = 0; i < data.mi001list.length; i++) {
                    customerOptions += '<option value="' + data.mi001list[i].centerid + '">' + data.mi001list[i].centername + '</option>';
                    centeridObj.push(data.mi001list[i]);
                }
                $('#customerName').html(customerOptions);
            }


            $.ajax({
                type: 'POST',
                url: './webappcomChannel.json',
                data: {},
                datatype: 'json',
                success: function (data) {
                    if (typeof data == 'string') {
                        data = JSON.parse(data);
                    }
                    if (data.recode == '000000') {
                        var channelOptions = '<option pleaseSelect="true">请选择</option>';
                        for (var i = 0; i < data.mi007list.length; i++) {
                            channelOptions += '<option value="' + data.mi007list[i].itemid + '">' + data.mi007list[i].itemval + '</option>';
                            channelObj.push(data.mi007list[i]);
                        }
                        $('#channelName').html(channelOptions);
                    }
                    parent.Common.loading(false);
                    init(false, 1, pageSizeTemp, user.centerid);

                },
                error: function () {
                    errMsg();
                }
            });


        },
        error: function () {
            errMsg();
        }
    });


}


//画表格头部
var cols = [
    {
        title: '序号', name: 'pid', width: 40, align: 'center', renderer: function (val, item, index) {
        return index + 1;
    }
    },
    {
        title: '客户名称', name: 'centerid', width: 230, align: 'center', renderer: function (val, item, index) {
        for (var i = 0; i < centeridObj.length; i++) {
            if (item.centerid == centeridObj[i].centerid) {
                return centeridObj[i].centername;
            }
        }
    }
    },
    {
        title: '渠道类型', name: 'channel', width: 160, align: 'center', renderer: function (val, item, index) {

        for (var i = 0; i < channelObj.length; i++) {
            if (item.channel == channelObj[i].itemid) {
                return channelObj[i].itemval;
            }
        }

    }
    },
    {title: '渠道应用', name: 'appname', width: 120, align: 'center'},
    {
        title: '是否启用', name: 'startup', width: 102, align: 'center', renderer: function (val, item, index) {
        if (item.startup == '1') {
            return '是';
        }
        if (item.startup == '0') {
            return '否';
        }
    }
    },
    {title: '服务开始日期', name: 'effectivedaytart', width: 150, align: 'center'},
    {title: '服务结束日期', name: 'effectivedayend', width: 150, align: 'center'},
];


//init page
function init(changer, pageIndex, pageSize, centerid) {
    $.ajax({
        type: 'POST',
        url: './webapi04004.json',
        data: {'centerid': centerid, 'page': pageIndex, 'rows': pageSize},
        datatype: 'json',
        success: function (data) {
            parent.Common.loading(false);
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (appm.tableObj != null) {
                appm.tableObj.load(data.rows);
            } else {
                appm.tableObj = $('#changeAppManageTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
                    height: '390',
                    cols: cols,
                    items: data.rows,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
            }

            //修改分页
            appm.pager.reset({
                itemLength: data.total,
                pageSize: data.pageSize,
                reset: changer ? true : false
            });
            //绑定按钮事件
            btnBindEvent();
        },
        error: function () {
            parent.Common.loading(false);
            errMsg();
        }
    });
}

//清除表单内容\n
function clearForm() {
    $('#customerName option').attr('selected', false);
    $('#channelName option').attr('selected', false);
    $('#appName').attr('value', '');
    $('.radioWrap input').removeAttr('checked');
    $('#serviceStart').attr('value', '');
    $('#serviceEnd').attr('value', '');
}

//点击添加
function create() {
    appManage.isAdd = true;
    appManage.clearForm();
    setTimeout(function () {
        var createHTML = $('.orgManage-popup-edit-container').html();
        parent.Common.popupShow(createHTML);
        toggleTime();
    }, 50);
    $('#addTitltSpan').text('应用管理 - 增加');
    clearForm();
    $('.orgManage-popup-btns-ok:eq(0)').show();
    $('.orgManage-popup-btns-ok:eq(1)').hide();
}

var timeMark = false;
function toggleTime() {
    parent.$('.radioWrap input[name="isUsing"]:first').click(function () {
        parent.$('.servTimeLi').show(0);
        timeMark = true;
    });
    parent.$('.radioWrap input[name="isUsing"]:last').click(function () {
        parent.$('.servTimeLi').hide(0);
        timeMark = false;
    });

}

//点击修改
function modify() {
    appManage.isAdd = false;
    var selectedItem = appm.tableObj.selectedRows();
    parent.Common.loading(true);
    clearForm();
    setTimeout(function () {
        var editHTML = $('.orgManage-popup-edit-container').html();
        parent.Common.popupShow(editHTML);
        toggleTime();
    }, 50);
    $('#addTitltSpan').text('应用管理 - 修改');
    clearForm();

    //回显

    $('#customerName option[value="' + selectedItem[0].centerid + '"]').attr('selected', true);
    $('#channelName option[value="' + selectedItem[0].channel + '"]').attr('selected', true);

    $('#appName').attr('value', selectedItem[0].appname);


    if (selectedItem[0].startup == '1') {
        $('.radioWrap input[name="isUsing"]:first').attr('checked', 'checked');
        $('.radioWrap input[name="isUsing"]:last').attr('checked', false);
        $('.servTimeLi').show(0);
    } else {
        $('.radioWrap input[name="isUsing"]:last').attr('checked', 'checked');
        $('.radioWrap input[name="isUsing"]:first').attr('checked', false);
        $('.servTimeLi').hide();
    }


    $('#serviceStart').attr('value', selectedItem[0].effectivedaytart);
    $('#serviceEnd').attr('value', selectedItem[0].effectivedayend);
    //按钮显示隐藏
    $('.orgManage-popup-btns-ok:eq(0)').hide();
    $('.orgManage-popup-btns-ok:eq(1)').show();
    parent.Common.loading(false);

}

//添加设置认证
function createAuth() {
    appManage.isAdd = true,
        appManage.clearAuth();
    setTimeout(function () {
        var createHTML = $('.orgManage-popup-edit-container2').html();
        parent.Common.popupShow(createHTML);
    }, 50);

}

//回显认证设置的内容
function getAuth(selectedItem) {
    $.ajax({
        type: 'post',
        url: './webapi04005.json',
        data: {
            'pid': selectedItem.pid
        },
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
        },
        error: function () {
            errMsg()
        }
    });
}


var setForm = {
    verify: function (isAdd) {
        var _this = this;
        var post = true;
        var dialogErrMsg = [];
        if (parent.$('#customerName').val() == '请选择') {
            post = false;
            dialogErrMsg.push('客户名称');
        }
        if (parent.$('#channelName').val() == '请选择') {
            post = false;
            dialogErrMsg.push('渠道类型');
        }
        if (parent.$('#appName').val().length < 1) {
            post = false;
            dialogErrMsg.push('渠道应用名称');
        }
        if (parent.$('.radioWrap').find('input[name="isUsing"]:checked').val() == '') {
            post = false;
            dialogErrMsg.push('是否启用');
        }
        var timeMark = parent.$('.radioWrap').find('input[name="isUsing"]:checked').val();
        if (timeMark == '1') {
            if (parent.$('#serviceStart').val() == '') {
                post = false;
                dialogErrMsg.push('服务开始时间');
            }
            if (parent.$('#serviceEnd').val() == '') {
                post = false;
                dialogErrMsg.push('服务结束时间');
            }
        }

        if (!post) {
            var errmsg = dialogErrMsg.join(',');
            if (dialogErrMsg.length > 1) {
                errmsg += '等';
            }
            parent.$('#popup-container').hide();

            parent.dialogBox.type('error');
            parent.dialogBox.text(errmsg + '不能为空');
            parent.dialogBox.ok(function () {
                parent.$('#popup-container').show();
            });
            parent.dialogBox.show();
        } else {
            parent.Common.loading(true);
            var postData = {
                centerid: parent.$('#customerName').val(),
                // centerid:user.centerid,
                channel: parent.$('#channelName').val(),
                appname: parent.$('#appName').val(),
                startup: parent.$('.radioWrap').find('input[name="isUsing"]:checked').val(),
                effectivedaytart: parent.$('#serviceStart').val(),
                effectivedayend: parent.$('#serviceEnd').val()
            }
            if (isAdd) {
                createData(postData);
            } else {
                itemModify();
            }
        }
    },
    //设置认证的表单检查
    checkAuth: function (isAdd) {
        var _this = this;
        var post = true;
        var dialogErrMsg = [];
        if (parent.$('#appid').val().length < 1) {
            post = false;
            dialogErrMsg.push('应用ID');
        }
        if (parent.$('#appKey').val().length < 1) {
            post = false;
            dialogErrMsg.push('应用Key');
        }
        if (parent.$('#appAes').val().length < 1) {
            post = false;
            dialogErrMsg.push('渠道应用名称');
        }
        var token = parent.$('#apptoken').find('input[name="isUsing"]:checked').val();
        if (token == '') {
            post = false;
            dialogErrMsg.push('启用Token');
        }
        if (token == '1' && parent.$('#tokenDate').val().length < 1) {
            post = false;
            dialogErrMsg.push('Token有效期');
        }
        if (!post) {
            var errmsg = dialogErrMsg.join(',');
            if (dialogErrMsg.length > 1) {
                errmsg += '等';
            }
            parent.$('#popup-container').hide();

            parent.dialogBox.type('error');
            parent.dialogBox.text(errmsg + '不能为空');
            parent.dialogBox.ok(function () {
                parent.$('#popup-container').show();
            });
            parent.dialogBox.show();
        }
        if (isNaN(parent.$('#tokenDate').val())) {
            parent.$('#popup-container').hide();

            parent.dialogBox.type('error');
            parent.dialogBox.text('请输入正确的Token有效期（秒数）');
            parent.dialogBox.ok(function () {
                parent.$('#popup-container').show();
            });
            parent.dialogBox.show();
        } else {
            parent.Common.loading(true);
            setAuth();
        }
    }
}

//添加
function createData(createData) {
    $.ajax({
        type: 'POST',
        url: './webapi04001.json',
        data: createData,
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == '000000') {
                parent.$('#popup-container').hide();
                init(false, 1, pageSizeTemp, user.centerid);
                parent.Common.dialog({
                    type: 'success',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                        //添加成功之后重新执行查询操作
                    }
                });
            } else {
                parent.$('#popup-container').hide();
                parent.Common.dialog({
                    type: 'error',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                    }
                });
            }
        },
        error: function () {
            errMsg();
        }
    });
}


//获取日期
function regDate() {
    var startTime = {
        elem: '#serviceStart',
        format: 'YYYY-MM-DD',
        max: '2099-12-31', //最大日期
        istime: false,
        istoday: false,
        fixed: false,
        choose: function (datas) {
            endTime.min = datas; //开始日选好后，重置结束日的最小日期
            endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    var endTime = {
        elem: '#serviceEnd',
        format: 'YYYY-MM-DD',
        min: $("#serviceStart").val(),
        max: '2099-12-31',
        istime: false,
        istoday: false,
        fixed: true,
        choose: function (datas) {
            startTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    parent.laydate(startTime);
    parent.laydate(endTime);
    parent.laydate.skin('huanglv');
}


//确认是否删除事件
function deleteConfirm() {
    var selected = appm.tableObj.selectedRows();
    if (selected != 'undefined') {
        parent.Common.dialog({
            type: "warning",
            text: "确定要删除选中项吗？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            cancelText: '取消',
            ok: function () {
                itemDelete();
            }
        });

    }
}

//删除事件
function itemDelete() {
    var selected = appm.tableObj.selectedRows();
    var selarr = [];
    //获取选中的数组
    for (var i = 0; i < selected.length; i++) {
        selarr.push(selected[i].pid);
    }
    //数组转换成字符串
    selarr = selarr.join(',');
    if (selected.length < 1) {
        parent.Common.dialog({
            type: "error",
            text: "至少选中一条记录！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {
            }
        });
    } else {
        //显示Loading
        parent.Common.loading(true);
        $.ajax({
            type: 'POST',
            url: './webapi04002.json',
            data: {'pid': selarr},
            datatype: 'json',
            success: function (data) {
                if (typeof data == 'string') {
                    data = JSON.parse(data);
                }
                if (data.recode = '000000') {
                    //隐藏loading
                    parent.Common.loading(false);
                    init(false, 1, pageSizeTemp, $('#customer-name').val());
                } else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: 'error',
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: '确定',
                        ok: function () {

                        }
                    });
                }
            },
            error: function () {
                errMsg();
            }
        });
    }
}

//修改事件
function itemModify() {
    parent.Common.loading(true);
    //执行修改操作
    var selectedItem = appm.tableObj.selectedRows();
    var itemModifyData = {
        pid: selectedItem[0].pid,
        centerid: parent.$('#customerName').val(),
        channel: parent.$('#channelName').val(),
        appname: parent.$('#appName').val(),
        startup: parent.$('.radioWrap').find('input[name="isUsing"]:checked').val(),
        effectivedaytart: parent.$('#serviceStart').val(),
        effectivedayend: parent.$('#serviceEnd').val()
    };
    $.ajax({
        type: 'post',
        url: './webapi04003.json',
        data: itemModifyData,
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode = '000000') {
                //隐藏loading
                parent.Common.loading(false);
                init(false, 1, pageSizeTemp, $('#customer-name').val());
                parent.$('#popup-container').hide();
                init(false, 1, pageSizeTemp, $('#customer-name').val());
                parent.Common.dialog({
                    type: 'success',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                        //添加成功之后重新执行查询操作
                    }
                });
            } else {
                parent.Common.loading(false);
                parent.Common.dialog({
                    type: 'error',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {

                    }
                });
            }
        },
        error: function () {
            errMsg();
        }
    });

}


//认证设置
function setAuth() {
    var selectedItem = appm.tableObj.selectedRows();
    $.ajax({
        type: "post",
        url: "./webapi04005.json",
        data: {
            'pid': selectedItem[0].pid,
            'appid': parent.$('#appid').val(),
            'appkey': parent.$('#appKey').val(),
            'secretkey': parent.$('#appAes').val(),
            'checktoken': parent.$('#apptoken').find('input[name="checktoken"]:checked').val(),
            'validtime': parent.$('#tokenDate').val()
        },
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == '000000') {
                parent.$('#popup-container').hide();
                init(false, 1, pageSizeTemp, $('#customer-name').val());
                parent.Common.dialog({
                    type: 'success',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                        //添加成功之后重新执行查询操作
                    }
                });
            } else {
                parent.$('#popup-container').hide();
                parent.Common.dialog({
                    type: 'error',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                    }
                });
            }
        },
        error: function () {
            errMsg();
        }
    });

}

//点击生成appkey
function createAppKey() {
    var selectedItem = appm.tableObj.selectedRows();
    $.ajax({
        type: "post",
        url: "./webapi04006.json",
        data: {
            'pid': selectedItem[0].pid
        },
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == '000000') {
                parent.$('#appKey').val(data.appkey);
            } else {
                parent.$('#popup-container').hide();
                parent.Common.dialog({
                    type: 'error',
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: '确定',
                    ok: function () {
                    }
                });
            }
        },
        error: function () {
            errMsg();
        }
    });
}


//按钮绑定
function btnBindEvent() {
    $('#orgManage-btn-add').off().on('click', function () {
        create();
        setTimeout(function () {
            regDate();
        }, 100);
    });

    $('#orgManage-btn-modify').off().on('click', function () {
        var selectedItem = appm.tableObj.selectedRows();
        if (selectedItem < 1) {
            parent.Common.editNone();
            return;
        }
        if (selectedItem.length > 1) {
            parent.Common.editMore();
        } else {
            //创建修改html
            modify();
            setTimeout(function () {
                regDate();
            }, 100);
        }
    });
    $('#orgManage-btn-del').off().on('click', function () {
        var selectedItem = appm.tableObj.selectedRows();
        if (selectedItem.length == 0) {
            parent.Common.delNone();
        } else {
            deleteConfirm();
        }

    });
    $('#btnSetAuth').off().on('click', function () {
        //选中的行
        var selectedItem = appm.tableObj.selectedRows();
        if (selectedItem.length != 1) {
            parent.Common.dialog({
                type: "error",
                text: "只能选择一条信息设置认证！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        console.log(selectedItem[0].appid)
        appManage.isAdd = true,
            appManage.clearAuth();

        var createHTML = $('.orgManage-popup-edit-container2').html();
        parent.Common.popupShow(createHTML);

        parent.$('#appid').val(selectedItem[0].appid);
        parent.$('#appKey').val(selectedItem[0].appkey);
        parent.$('#appAes').val(selectedItem[0].secretkey);

        if (selectedItem[0].checktoken == '1') {
            parent.$('input[name="checktoken"]:first').attr('checked', 'checked');
            parent.$('input[name="checktoken"]:last').attr('checked', false);
        } else {
            parent.$('input[name="checktoken"]:last').attr('checked', 'checked');
            parent.$('input[name="checktoken"]:first').attr('checked', false);
            parent.$('.token').hide();
        }
        parent.$('#tokenDate').val(selectedItem[0].validtime);
    });
    // app软件更新
    $('#btnSoftWareUpdate').on('click', function () {
        var customerName = $('#customer-name').val();
        if (customerName.length < 1) {
            parent.Common.dialog({
                type: "error",
                text: "请选择一个客户名称！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return false;
        }
        $('.channelBox').hide();
        $('.interfaceBox').show();
        interfaceSet.btnClick();
        $("#interface-name").text($("#customer-name option[value="+customerName+"]").text());
        if(interfaceSet.pager){
            interfaceSet.getTableData(1, interfaceSet.pageSize, true)
        }else{
            interfaceSet.createPager();
        }

    });

    /*返回*/
    $('#orgManage-info-goBack').click(function () {
        $('.channelBox').show();
        $('.interfaceBox').hide();
    });
}

//app更新
var interfaceSet = {
    pageSize: 10,
    pager: null,
    tabler: null,
    tableData: null,
    filene: "",
    filept: "",
    load: "",
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#orgManage-table-pages",
            itemLength: 2300,
            pageSize: self.pageSize,
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
            rows = rows;
        var url = "./webapi40304.json";

        function getDataSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.rows) {
                self.createTable(data.rows);
                self.pager.reset({
                    itemLength: data.total ? data.total : 0,
                    pageSize: self.pageSize,
                    reset: pageRestBool
                });
            }
            if (data.recode !== "000000") {
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

        self.ajaxFn("post", url, getDataSuccess, {page: page, rows: rows, centerid: $('#customer-name').val()})
    },
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            {
                title: '中心名称', name: 'centerid', width: 100, align: 'center', renderer: function (val, item, index) {
                return $('#customer-name option[value=' + val + ']').text();
            }
            },
            {
                title: '设备区分', name: 'devtype', width: 60, align: 'center', renderer: function (val, item, index) {
                return $('#app-add-devtype option[value=' + val + ']').text();
            }
            }, {
                title: '版本编号', name: 'versionno', width: 100, align: 'center'
            }, {
                title: '发布日期', name: 'releasedate', width: 100, align: 'center'
            }, {
                title: '下载地址', name: 'downloadurl', width: 200, align: 'center'
            }, {
                title: '更新内容',name: 'releasecontent',width: 130,align: 'center'
            }, {
                title: '版本是否可用',
                name: 'usableflag',
                width: 100,
                align: 'center',
                renderer: function (val, item, index) {
                    return $('#app-add-usableflag option[value=' + val + ']').text();
                }
            },
            {
                title: '发布人', name: 'publisher', width: 100, align: 'center'
            },
            {
                title: '是否发布', name: 'publishflag', width: 70, align: 'center', renderer: function (val, item, index) {
                if(parseInt(val) == 0){
                    return "未发布"
                }else{
                    return "已发布"
                }
            }},
            {
                title: '版本ID',name: 'versionid',hidden: true,width: 100,align: 'center'
            },
            {
                title: '二维码',name: 'freeuse1',hidden: true,width: 100,align: 'center'
            },
        ];
        if (self.tabler != null) {
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
    verify: function (flag) {
        var self = this;
        var post = true;
        var dialogErrMsg = [];
        if (parent.$('#app-add-customerName').val() == '' || parent.$('#app-add-customerName').val() == null) {
            post = false;
            dialogErrMsg.push('客户名称');
        }
        if (parent.$('#app-add-devtype').val() == '' || parent.$('#app-add-devtype').val() == null) {
            post = false;
            dialogErrMsg.push('设备区分');
        }
        if (parent.$('#app-add-versionno').val() == '' || parent.$('#app-add-versionno').val() == null) {
            post = false;
            dialogErrMsg.push('版本编号');
        }
        if (parent.$('#app-add-releasedate').val() == '' || parent.$('#app-add-releasedate').val() == null) {
            post = false;
            dialogErrMsg.push('发布日期');
        }
        if (parent.$('#app-add-downloadurl').val() == '' || parent.$('#app-add-downloadurl').val() == null) {
            post = false;
            dialogErrMsg.push('下载地址');
        }
        if (parent.$('#app-add-releasecontent').val() == '' || parent.$('#app-add-releasecontent').val() == null) {
            post = false;
            dialogErrMsg.push('更新内容');
        }
        if (parent.$('#app-add-usableflag').val() == '' || parent.$('#app-add-usableflag').val() == null) {
            post = false;
            dialogErrMsg.push('版本是否可用');
        }

        if (!post) {
            var errmsg = dialogErrMsg.join(',');
            if (dialogErrMsg.length > 1) {
                errmsg += '等';
            }
            parent.$('#popup-container').hide();

            parent.dialogBox.type('error');
            parent.dialogBox.text(errmsg + '不能为空');
            parent.dialogBox.ok(function () {
                parent.$('#popup-container').show();
            });
            parent.dialogBox.show();
        } else {
            parent.Common.loading(true);
            var postData = {
                centerid: parent.$('#app-add-customerName').val(),
                devtype: parent.$('#app-add-devtype').val(),
                versionno: parent.$('#app-add-versionno').val(),
                releasedate: parent.$('#app-add-releasedate').val(),
                downloadurl: parent.$('#app-add-downloadurl').val(),
                releasecontent: parent.$('#app-add-releasecontent').val(),
                usableflag: parent.$('#app-add-usableflag').val()
            };
            if (flag) {
                self.add(postData);
            } else {
                var selected = self.tabler.selectedRows();
                postData.versionid = selected[0].versionid;
                self.modify(postData);
            }
        }
    },
    add: function (postData) {
        var self = this;

        function addDataSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                parent.Common.loading(false);
                parent.Common.popupClose();
                self.getTableData(1, self.pageSize, true);
            } else {
                parent.Common.loading(false);
                parent.Common.popupClose();
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

        self.ajaxFn("POST", "webapi40301.json", addDataSuccess, postData);
    },
    modify: function (postData) {
        var self = this;

        function editDataSuccess(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                parent.Common.loading(false);
                parent.Common.popupClose();
                self.getTableData(1, self.pageSize, true);
            } else {
                parent.Common.loading(false);
                parent.Common.popupClose();
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

        self.ajaxFn("POST", "webapi40303.json", editDataSuccess, postData);
    },
    del: function () {
        parent.Common.loading(true);
        var self = this,
            selected = self.tabler.selectedRows(),
            delIds = [];
        for (var i = 0; i < selected.length; i++) {
            delIds.push(selected[i].versionid);
        }
        function delSuccessFn(data) {
            if (typeof data == "string") {
                data = JSON.parse(data);
            }
            if (data.recode == "000000") {
                parent.Common.popupClose();
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

        self.ajaxFn("POST", "./webapi40302.json", delSuccessFn, {listVersionId: delIds.join(",")});
    },
    edit: function () {
        var self = this,
            selectedData = self.tabler.selectedRows();

        var logcode = parent.$('#edit-logcode').val(),
            logtext = parent.$('#edit-logText').val();
        if (logtext.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "日志内容不能为空！",
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

        self.ajaxFn("POST", "./ptl40008Mod.json", editSuccess, {logcode: logcode, logtext: logtext});
    },
    twoBarCodesCreate: function () {
        var self = this;
        var arr = [{
            name: "downloadurl",
            value: parent.$('#twoBarCodes-downloadUrl').val()
        }, {
            name: "freeuse2",
            value: self.filene
        }, {
            name: "versionid",
            value: parent.$('#versionid_pic').val()
        }, {
            name: "centerid",
            value: parent.$('#magecenterId').val()
        }];
        self.ajaxFn("POST", "webapi40307.json", createCodeSuccess, arr);
        function createCodeSuccess(data) {
            if (data.recode == "000000") {
                self.getTableData(1, self.pageSize, true);
                parent.$('#showTwoImg').html("");
                parent.$('#showTwoImg').append('<div style="text-align:center"><img style="width:200px;height:200px;" id="' + data.fileName + '" src="'
                    + data.downloadPath + '"/></div>');
                self.load = data.downloadPath;
            }
        }
    },
    twoBarCodesDownload: function () {
        var self = this;
        if (self.load == '') {
            alert('尚未生成二维码！');
            //$(this).attr('href',load);
        } else {
            window.location.href = self.load;
        }
    },
    btnClick: function () {
        var self = this;
        /*新建数据*/
        $('#orgManage-info-add').click(function (e) {
            var createHTML = $(".app-update-add-container").html();
            parent.Common.popupShow(createHTML);
            parent.$("#orgManage-popup-title").text("软件更新信息添加");
            parent.$("#orgManage-info-add-confirm").show();
            parent.$("#orgManage-info-modify-confirm").hide();
            setTimeout(function () {
                showDate();
            }, 100);
        });

        /*删除数据*/
        $('#orgManage-info-del').click(function (e) {
            var selected = $("#orgManage-popup-info-table").find("tr.selected");
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
        $('#orgManage-info-modify').click(function (e) {
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
            var createHTML2 = $(".app-update-add-container").html();
            parent.Common.popupShow(createHTML2);
            parent.$('#app-add-customerName').val(selected[0].centerid);
            parent.$('#app-add-devtype').val(selected[0].devtype);
            parent.$('#app-add-versionno').val(selected[0].versionno);
            parent.$('#app-add-releasedate').val(selected[0].releasedate);
            parent.$('#app-add-downloadurl').val(selected[0].downloadurl);
            parent.$('#app-add-releasecontent').val(selected[0].releasecontent);
            parent.$('#app-add-usableflag').val(selected[0].usableflag);
            parent.$("#orgManage-info-add-confirm").hide();
            parent.$("#orgManage-info-modify-confirm").show();
            parent.$("#orgManage-popup-title").text("软件更新信息修改");
            if (selected[0].publishflag == "1" || selected[0].publishflag == 1) {
                parent.$(".publishflag").hide();
                parent.$("#app-add-downloadurl").attr("disabled", true);
            }
        });

        // 发布
        $("#orgManage-info-release").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if (selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行发布！",
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
                    text: "只能选择一条记录进行发布！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }
            function releaseSuccessFn(data) {
                if (typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.popupClose();
                    parent.Common.dialog({
                        type: "info",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
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


            if (selected[0].publishflag == 0) {
                self.ajaxFn("POST", "./webapi40305.json", releaseSuccessFn, selected[0]);
            } else {
                parent.Common.dialog({
                    type: "error",
                    text: "版本信息已发布",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            }
        });

        // 二维码
        $("#orgManage-info-twoBarCodes").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if (selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行操作！",
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
                    text: "只能选择一条记录进行操作！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return;
            }

            var createHTML2 = $(".app-update-twoBarCodes").html();
            parent.Common.popupShow(createHTML2);
            parent.$("#twoBarCodes-downloadUrl").val(selected[0].downloadurl);
            parent.$("#magecenterId").val(selected[0].centerid);
            parent.$("#versionid_pic").val(selected[0].versionid);
            parent.$("#devtype_pic").val(selected[0].devtype);
            parent.$("#downloadurl_p").val(selected[0].downloadurl);

            parent.$("#twoBarCodes-logo").on("change", function (e) {
                var name = e.currentTarget.files[0].name;
                var uploadImage = parent.$('#twoBarCodes-logo').val();
                parent.$("#excelfile").text(name);
                if (null == uploadImage || "" == uploadImage) {
                    parent.$("#popup-container").hide();
                    parent.Common.dialog({
                        type: "error",
                        text: "请选择LOGO后进行提交",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    return false;
                }
                parent.$("#formUpImg").ajaxSubmit({
                    //dataType : "json", // 一般使用这个
                    dataType: "text", //旧系统使用这个
                    url: "webapi40306.do",
                    success: function (data) {
                        parent.$("#popup-container").hide();
                        var data1 = eval("(" + data + ")");
                        if (data1.recode != "000000") {
                            $('#twoBarCodes-logo').val("");
                            //load='';
                            parent.Common.dialog({
                                type: "info",
                                text: data1.msg,
                                okShow: true,
                                cancelShow: false,
                                okText: "确定",
                                ok: function () {
                                    parent.$("#popup-container").show();
                                }
                            });
                        } else {
                            self.filene = data1.fileName;
                            self.filept = data1.downloadPath;
                            parent.Common.dialog({
                                type: "info",
                                text: "LOGO上传成功",
                                okShow: true,
                                cancelShow: false,
                                okText: "确定",
                                ok: function () {
                                    parent.$("#popup-container").show();
                                }
                            });
                        }
                    }
                });
            })


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
    //初始化页面
    appm.createPager();
    init(true, 1, pageSizeTemp, user.centerid);
    getAll();
    $.ajax({
        type: 'POST',
        // url: './ptl40003Qry.json',
        url: './page41101GetPara.json',
        data: {'centerid': top.userInfo.centerid},
        datatype: 'json',
        success: function (data) {
            if (typeof data == 'string') {
                data = JSON.parse(data);
            }
            if (data.recode == '000000') {
                var customerOptions = '';
                var deviceOptions = '';
                for (var i = 0; i < data.mi001list.length; i++) {
                    customerOptions += '<option value="' + data.mi001list[i].centerid + '">' + data.mi001list[i].centername + '</option>';
                }
                for (var j = 0; j < data.devicetypeList.length; j++) {
                    deviceOptions += '<option value="' + data.devicetypeList[j].itemid + '">' + data.devicetypeList[j].itemval + '</option>';
                }

                $('#customer-name,#app-add-customerName').html(customerOptions);
                $('#customer-name').off().on("change", function () {
                    var val = $(this).val();
                    parent.Common.loading(true);
                    init(true, 1, pageSizeTemp, val);
                });
                $('#app-add-devtype').html(deviceOptions);
            }
        },
        error: function () {
            parent.Common.ajaxError();
        }
    });
});

function showDate() {
    var startTime = {
        elem: '#app-add-releasedate',
        format: 'YYYY-MM-DD',
        min: parent.laydate.now(), //设定最小日期为当前日期
        max: '2099-12-31', //最大日期
        istime: false,
        istoday: false,
        fixed: false,
        choose: function (datas) {
            // endTime.min = datas; //开始日选好后，重置结束日的最小日期
            // endTime.start = datas //将结束日的初始值设定为开始日
        }
    };
    parent.laydate(startTime);
    parent.laydate.skin('huanglv');
}



