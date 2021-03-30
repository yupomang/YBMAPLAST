/**
 * Created by FelixAn on 2016/7/23.
 */
// create pages
// var orgManagementPages = pages({
//     el: "#orgManage-table-pages",
//     itemLength: 2300,
//     pageSize: 12,
//     pageChanged: function (pageIndex) {
//         console.log("genggaihgahaha", pageIndex);
//     }
// });
var user = top.userInfo;
function orgAJAXErrorMsg () {
    parent.Common.dialog({
        type: "error",
        text: '网络错误！请刷新页面或联系管理员！',
        okShow: true,
        cancelShow: false,
        okText: "确定",
        ok: function () {}
    });
}
// create&edit Vue
var orgManageEdit = new Vue({
    el: ".orgManage-popup-edit",
    data: {
        isAdd: true,
        title: ["添加机构信息", "修改机构信息"],
        form: {
            freeuse1: "", // 城市名称
            centerid: "", // 机构代码
            centername: "", // 机构名称
            uselevel: "1", // 机构名称
            contactname: "", // 联系人
            contacttel: "", // 联系人电话
            custsvctel: "", // 客服电话
            portalurl: "", // 机构官网
            postcode: "", // 邮政编码
            freeuse2: "" // 备注
        }
    },
    methods: {
        clearInput: function () {
            var self = this;
            self.form.freeuse1 = "";
            self.form.centerid = "";
            self.form.centername = "";
            self.form.uselevel = "1";
            self.form.contactname = "";
            self.form.contacttel = "";
            self.form.custsvctel = "";
            self.form.portalurl = "";
            self.form.postcode = "";
            self.form.freeuse2 = "";
        }
    }
});
// click methods
function create () {
    orgManageEdit.isAdd = true;
    orgManageEdit.clearInput();
    setTimeout(function(){
        var createHTML = $(".orgManage-popup-edit-container").html();
        parent.Common.popupShow(createHTML);
    }, 100);
}
function edit (item) {
    orgManageEdit.isAdd = false;
    orgManageEdit.form.freeuse1 = item.freeuse1;
    orgManageEdit.form.centerid = item.centerid;
    orgManageEdit.form.centername = item.centername;
    $("#orgManageCenterLevel").find("option").eq(parseInt(item.uselevel) - 1).attr("selected",true);
    orgManageEdit.form.uselevel = item.uselevel;
    orgManageEdit.form.contactname = item.contactname;
    orgManageEdit.form.contacttel = item.contacttel;
    orgManageEdit.form.custsvctel = item.custsvctel;
    orgManageEdit.form.portalurl = item.portalurl;
    orgManageEdit.form.postcode = item.postcode;
    orgManageEdit.form.freeuse2 = item.freeuse2;
    setTimeout(function(){
        var editHTML = $(".orgManage-popup-edit-container").html();
        parent.Common.popupShow(editHTML);
    }, 100);
}
function del (item) {
    if (item) {
        parent.OrgManageEditFn.deleteConfirm(item);
    } else {
        parent.Common.delNone();
    }
}
function active (ids) {
    // get select
    var selectedRow = $("#orgManage-table").find("tr.selected");
    if(selectedRow.length < 1) {
        parent.Common.dialog({
            type: "warning",
            text: "至少选中一条记录激活！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    } else {
        parent.Common.dialog({
            type: "tips",
            text: "确认是否激活？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            cancelText: "取消",
            ok: function () {
                activeData(ids);
            }
        });
    }
}
// info click functions
function infoVerify (domObj) {
    // get value
    var tds = domObj.parents("tr").find("td"),
        areaCode = tds.eq(1).find(".info-area-code").val(),
        areaName = tds.eq(2).find(".info-area-name").val(),
        isVerify = true,
        tipsArr = [],
        tips = "";
    // verify
    if (areaCode.length < 1) {
        isVerify = false;
        tipsArr.push("区域代码");
    }
    if (areaName.length < 1) {
        isVerify = false;
        tipsArr.push("区域名称");
    }
    if (!isVerify) {
        if (tipsArr.length > 1) {
            tips = tipsArr.join(",") + "等不能为空！";
        } else {
            tips = tipsArr[0] + "不能为空！";
        }
        parent.Common.dialog({
            type: "error",
            text: tips,
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
        return false;
    } else {
        return {
            areaCode: tds.eq(1).find(".info-area-code").val(),
            areaName: tds.eq(2).find(".info-area-name").val(),
            areaId: "",
            centerid: window.centerId
        };
    }
}
function infoEdit (item, obj) {
    var tr = obj.parents("tr").find("td");
    tr.eq(1).find(".info-placeholder-span").hide();
    tr.eq(1).find("input").show();
    tr.eq(2).find(".info-placeholder-span").hide();
    tr.eq(2).find("input").show();
    tr.eq(3).find(".orgManage-table-links").hide();
    tr.eq(3).find(".info-save-link").show();
    window.areaId = item.areaId;
}
function infoDel (areaId) {
    if(typeof areaId != 'undefined') {
        parent.Common.dialog({
            type: "tips",
            text: "是否确认删除？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            ok: function () {
                parent.Common.loading(true);
                infoDeletePost(areaId);
            }
        });
    }
}
function infoEditPost (domObj) {
    var postData = infoVerify(domObj);
    if(!postData) {
        return false;
    }
    postData.areaId = window.areaId;
    parent.Common.loading(true);
    $.ajax({
        type: "POST",
        url: "./webapi20203.json",
        data: postData,
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.Common.loading(true);
                        getInfoData(window.centerId);
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
function infoDeletePost (ids) {
    $.ajax({
        type: "POST",
        url: "./webapi20202.json",
        data: { "areaId": ids },
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.Common.loading(true);
                        getInfoData(window.centerId);
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
function infoCreatePost (domObj) {
    var createData = infoVerify(domObj);
    if(!createData) {
        return false;
    }
    parent.Common.loading(true);
    $.ajax({
        type: "POST",
        url: "./webapi20201.json",
        data: createData,
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.Common.loading(true);
                        getInfoData(window.centerId);
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
// info table
var infoCols = [
    { title:'区域代码', name:'areaCode' ,width:120, align: 'center', renderer: function (val, item, rowIndex) {
        var temp = '';
        if (val.length > 0) {
            temp += '<span class="info-placeholder-span">' + val + '</span><input type="text" value="'+ val +'" class="info-area-code" />';
        } else {
            temp = '<input type="text" value="" class="info-area-code show" />';
        }
        return temp;
    } },
    { title:'区域名称', name:'areaName' ,width:654, align: 'left', renderer: function (val, item, rowIndex) {
        var temp = '';
        if (val.length > 0) {
            temp += '<span class="info-placeholder-span">' + val + '</span><input type="text" value="'+ val +'" class="info-area-name" />';
        } else {
            temp = '<input type="text" value="" class="info-area-name show" />';
        }
        return temp;
    }},
    { title:'操作', name:'areaName',width:242, align: 'center', renderer: function (val, item, rowIndex) {
        var temp = "";
        if (val.length > 0) {
            temp += '<span class="orgManage-table-links">';
            temp += "<a href='javascript:;' onclick='infoEdit(" + JSON.stringify(item) + ", $(this));' title='编辑'>编辑</a>";
            temp += '<span>|</span>';
            temp += '<a href="javascript:;" class="red-link" onclick="infoDel(' + item.areaId + ');" title="删除">删除</a>';
            temp += '</span>';
            temp += '<a href="javascript:;" title="保存" class="info-save-link" onclick="infoEditPost($(this))">保存</a>';
        } else {
            temp = '<a href="javascript:;" title="新建" class="info-create-link" onclick="infoCreatePost($(this))">新建</a>';
        }
        return temp;
    } }
];
function regionalInfo (tableObj) {
    // get select
    var selectedRow = $("#orgManage-table").find("tr.selected");
    if(selectedRow.length != 1) {
        if(selectedRow.length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: "请选择一条中心信息！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {}
            });
        } else {
            parent.Common.dialog({
                type: "warning",
                text: "区域信息维护只能选中一条中心信息！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {}
            });
        }
    } else {
        var postData = tableObj.selectedRows();
        getInfoData(postData[0].centerid);
        // show table base
        $(".orgManage-box").hide();
        $(".orgManage-popup-info-container").show();
    }
}
function getInfoData (centerid) {
    // get info data
    $.ajax({
        type: "POST",
        url: "./webapi20204.json",
        data: { "centerid": centerid },
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                // create table
                if(typeof infoTable != "undefined" && infoTable != null) {
                    infoTable.load(data.rows);
                } else {
                    infoTable = $('#orgManage-popup-info-table').mmGrid({
                        multiSelect: true,
                        checkCol: true,
                        width: '1100px',
                        height: '286px',
                        cols: infoCols,
                        items: data.rows,
                        loadingText: "loading...",
                        noDataText: "暂无数据。",
                        loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                        sortable: false
                    });
                }
            } else {
                if(typeof infoTable != "undefined" && infoTable != null) {
                    infoTable.load([]);
                } else {
                    infoTable = $('#orgManage-popup-info-table').mmGrid({
                        multiSelect: true,
                        checkCol: true,
                        width: '1100px',
                        height: '286px',
                        cols: infoCols,
                        items: [],
                        loadingText: "loading...",
                        noDataText: "暂无数据。",
                        loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                        sortable: false
                    });
                }
                parent.Common.dialog({
                    type: "error",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
            }
            if(typeof infoTable != 'undefined') {
                infoBtnsClick(infoTable, data.rows);
            } else {
                infoBtnsClick(undefined, data.rows);
            }
            window.centerId = centerid;
        },
        error: function(){
            orgAJAXErrorMsg();
        }
    });
}
// table header
var cols = [
    { title:'城市名称', name:'freeuse1' ,width:88, align: 'center' },
    { title:'客户代码', name:'centerid' ,width:132, align: 'center'},
    { title:'客户名称', name:'centername' ,width:220, align: 'center'},
    { title:'客户服务级别', name:'uselevel' ,width:100, align: 'center', renderer: function (val) {
        var temp = "高级";
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
    { title:'联系人', name:'contactname' ,width:118, align: 'center'},
    { title:'联系人电话', name:'contacttel' ,width:154, align: 'center'},
    { title:'状态', name:'validflag',width:40, align: 'center', renderer: function (val,item,rowIndex) {
        var temp = "";
        if(val == "1") {
            temp = "有效";
        } else if (val == "0") {
            temp = "<span style='color:#999'>无效</span>";
        }
        return temp;
    } },
    { title:'操作', name:'validflag',width:120, align: 'center', renderer: function (val, item, rowIndex) {
        var temp = "";
        temp += '<span class="orgManage-table-links">';
        temp += "<a href='javascript:;' onclick='edit(" + JSON.stringify(item) + ");' title='编辑'>编辑</a>";
        temp += '<span>|</span>';
        temp += '<a href="javascript:;" class="red-link" onclick="del(\'' + item.centerid + '\');" title="删除">删除</a>';
        temp += '</span>';
        return temp;
    } }
];
// AJAX
function getTableData () {
    $.ajax({
        type: "POST",
        url: "./ptl40003Qry.json",
        data: { "centerid": "00000000" },
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            // create table
            if(typeof orgTable != "undefined" && orgTable != null) {
                orgTable.load(data.rows);
            } else {
                orgTable = $('#orgManage-table').mmGrid({
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
            }
            btnsClickBind(orgTable);
        },
        error: function(){
            orgAJAXErrorMsg();
        }
    });
}
function tableDelete (ids) {
    $.ajax({
        type: "POST",
        url: "./ptl40003Del.json",
        data: { "centerid": ids ,'userCenterid':user.centerid},
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                orgTable.load(data.rows);
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
            orgAJAXErrorMsg();
        }
    });
}
function createData (creatDate) {
    $.ajax({
        type: "POST",
        url: "./ptl40003Add.json",
        data: creatDate,
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        getTableData();
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
function editData (editData) {
    $.ajax({
        type: "POST",
        url: "./ptl40003Upd.json",
        data: editData,
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        getTableData();
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
function activeData (ids) {
    $.ajax({
        type: "POST",
        url: "./ptl40003Activ.json",
        data: { "centerid": ids },
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        getTableData();
                    }
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
            orgAJAXErrorMsg();
        }
    });
}
function infoSaveData (arr) {
    $.ajax({
        type: "POST",
        url: "./webapi20205.json",
        data: { "datalist": JSON.stringify(arr) },
        datatype: "json",
        success: function(data) {
            if(typeof data == "string") {
                data = JSON.parse(data);
            }
            parent.Common.loading(false);
            if (data.recode == "000000") {
                parent.Common.dialog({
                    type: "success",
                    text: data.msg,
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        getTableData();
                    }
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
            getInfoData(window.centerId);
        },
        error: function(){
            orgAJAXErrorMsg();
        }
    });
}
function btnsClickBind (tableObj) {
    function getArr (field) {
        if(typeof tableObj != 'undefined') {
            var dataArr = tableObj.selectedRows(),
                tempArr = [];
            for(var i = 0; i < dataArr.length; i++) {
                tempArr.push(dataArr[i][field]);
            }
            return tempArr.join(",");
        }
    }
    // create
    $("#orgManage-btn-add").off().on("click", function () {
        create();
    });
    // delete
    $("#orgManage-btn-del").off().on("click", function () {
        del(getArr('centerid'));
    });
    // active
    $("#orgManage-btn-active").off().on("click", function () {
        active(getArr('centerid'));
    });
    // regional info
    $("#orgManage-btn-info").off().on("click", function () {
        regionalInfo(tableObj);
    });
}
// info btns to click
function infoBtnsClick (tableObj, infoData) {
    var tableObj = tableObj;
    function getArr (field) {
        if(typeof tableObj != 'undefined') {
            var dataArr = tableObj.selectedRows(),
                tempArr = [];
            for(var i = 0; i < dataArr.length; i++) {
                tempArr.push(dataArr[i][field]);
            }
            return tempArr.join(",");
        }
    }
    $("#orgManage-info-add").off().on("click", function () {
        tableObj.addRow({
            "areaId": "",
            "centerid": "",
            "areaCode": "",
            "areaName": "",
            "validflag": "1",
            "datemodified": "2014-05-28 09:53:10.342",
            "datecreated": "2014-05-05 18:46:04.088",
            "loginid": "adminWH",
            "freeuse1": null,
            "freeuse2": null,
            "freeuse3": null,
            "freeuse4": 1
        }, 0);
    });
    $("#orgManage-info-del").off().on("click", function () {
        if(tableObj.selectedRows().length < 1) {
            parent.Common.delNone();
            return;
        }
        infoDel(getArr("areaId"));
    });
    $("#orgManage-info-moveUp").off().on("click", function () {
        var newData = infoData.slice(0),
            selectedData = tableObj.selectedRowsIndex();
        for(var i = 0; i < selectedData.length; i++) {
            if(selectedData[i] == 0) {
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
            newData = an.arrayExchangeOrder(newData, selectedData[i], selectedData[i] - 1);
        }
        tableObj.load(newData);
        selectedData.forEach(function (val) {
            tableObj.select(val - 1);
        });
        infoData = newData;
    });
    $("#orgManage-info-moveDown").off().on("click", function () {
        var newData = infoData.slice(0),
            selectedData = tableObj.selectedRowsIndex();
        for(var i = selectedData.length; i >= 1; i--) {
            if(selectedData[i - 1] == newData.length - 1) {
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
            newData = an.arrayExchangeOrder(newData, selectedData[i - 1], selectedData[i - 1] + 1);
        }
        tableObj.load(newData);
        selectedData.forEach(function (val) {
            tableObj.select(val + 1);
        });
        infoData = newData;
    });
    $("#orgManage-info-save").off().on("click", function () {
        var postData = [],
            dataList = infoData;
        for(var i = 1; i <= dataList.length; i++) {
            postData.push({
                'seqid': dataList[i-1].areaId,
                'num': i
            });
        }
        infoSaveData(postData);
    });
    $("#orgManage-info-goBack").off().on("click", function () {
        $(".orgManage-box").show();
        $(".orgManage-popup-info-container").hide();
        getTableData();
    });
};
$(document).ready(function(){
    var orgTable = null,
        infoTable = null,
        centerId = null;
    getTableData();
});