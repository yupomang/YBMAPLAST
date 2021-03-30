/**
 * Created by FelixAn on 2016/7/22.
 */
function calcLayout () {
    var pageWidth = document.body.scrollWidth,
        pageHeight = document.body.clientHeight,
        headerHeight = 122, // header's height + gray area'height + main's top border
        mainLeftWidth = 213, // left width
        mainHeight = 647,
        mainRightWidth = 1154;
    mainHeight = pageHeight - headerHeight;
    mainRightWidth = pageWidth - mainLeftWidth;

    // change dom
    var navigation = an._$("navigation"),
        main = an._$("mainBody");
    if(navigation) {
        navigation.height = mainHeight;
    }
    if(main) {
        main.width = mainRightWidth;
        main.height = mainHeight;
    }
}
an.eventListenr(window, "load", calcLayout);
an.eventListenr(window, "resize", calcLayout);

var Common = {
    loading: function (isShow) {
        var loading = $("#loading");
        if (isShow) {
            loading.show();
        } else {
            loading.hide();
        }
    },
    popupShow: function (html) {
    // top popup
        an._$("popup-container").innerHTML = html;
        an._$("popup-container").style.display = "block";
    },
    popupClose: function () {
        an._$("popup-container").innerHTML = "";
    },
    dialog: function (parameter) {
        // common show dialog
        var parObj = parameter;
        if('type' in parObj) {
            dialogBox.type(parObj.type);
        }
        if('text' in parObj) {
            dialogBox.text(parObj.text);
        }
        if('okShow' in parObj) {
            dialogBox.okShow(parObj.okShow);
        }
        if('cancelShow' in parObj) {
            dialogBox.cancelShow(parObj.cancelShow);
        }
        if('okText' in parObj) {
            dialogBox.okText(parObj.okText);
        }
        if('cancelText' in parObj) {
            dialogBox.cancelText(parObj.cancelText);
        }
        if('ok' in parObj) {
            dialogBox.ok(parObj.ok);
        }
        if('cancel' in parObj) {
            dialogBox.cancel(parObj.cancel);
        }
        dialogBox.show();
    },
    ajaxError: function () {
        this.loading(false);
        this.dialog({
            type: "error",
            text: '网络错误！请刷新页面或联系管理员！',
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    },
    logout: function () {
    	//var obj = window.userInfo;
    	//ssoUtils.ssoLoginOutNotice(obj.loginid ,"YBMAPZH","",function(){
    	
	        var self = this;
	        $.ajax({
	            type: 'POST',
	            url: 'logout.do',
	            dataType: 'json',
	            success: function(obj){
	                if(obj.rancode != '000000'){
	                    self.dialog({
	                        type: "error",
	                        text: obj.msg,
	                        okShow: true,
	                        cancelShow: false,
	                        okText: "跳转登录页",
	                        ok: function () {
	                            window.location.href="login.html";
	                        }
	                    });
	                }else{
	                    window.location.href="login.html";
	                }
	            },
	            error: function(xhr, errorType, error){
	                self.dialog({
	                    type: "error",
	                    text: "登录失败！",
	                    okShow: true,
	                    cancelShow: false,
	                    okText: "跳转登录页",
	                    ok: function () {
	                        window.location.href="login.html";
	                    }
	                });
	            }
	        });
		//});
    },
    editNone: function () {
        // 点击修改，但是选择0条记录
        this.dialog({
            type: "warning",
            text: "请至少选择一条记录进行修改！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    },
    editMore: function () {
        // 点击修改，但是选择多条记录
        this.dialog({
            type: "warning",
            text: "只能选择一条记录进行修改！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    },
    delNone: function () {
        // 点击删除，但是选择0条记录
        this.dialog({
            type: "warning",
            text: "请至少选择一条记录进行删除！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {}
        });
    },
    checkPhone: function (phone) {
        if (phone != '') {
            var pattern=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
            if(pattern.test(phone) == false) {
                return false;
            } else{
                return true;
            }
        }
    },
    checkMobile: function (mobile) {
        if (mobile != '') {
            var pattern=/^(((13[0-9]{1})|15[0-9]{1}|18[0-9]{1}|)+\d{8})$/;
            if(pattern.test(mobile) == false) {
                return false;
            } else{
                return true;
            }
        }
    }
};

var OrgManageEditFn = {
    el: {
        orgManageFreeuse1: "#orgManageFreeuse1",
        orgManageCenterid: "#orgManageCenterid",
        orgManageCenterName: "#orgManageCenterName",
        orgManageCenterLevel: "#orgManageCenterLevel",
        orgManageContactName: "#orgManageContactName",
        orgManageContactTel: "#orgManageContactTel",
        orgManageCustsvctel: "#orgManageCustsvctel",
        orgManagePortalUrl: "#orgManagePortalUrl",
        orgManagePostCode: "#orgManagePostCode",
        orgManageFreeuse2: "#orgManageFreeuse2",
        popupContainer: "#popup-container"
    },
    orgVerify: function (isAdd) {
        var self = this,
            canPost = true,
            dialogErrorMessage = [];
        // verify inputs
        if($(self.el.orgManageFreeuse1).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("城市名称");
        }
        if($(self.el.orgManageCenterid).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("机构代码");
        }
        if($(self.el.orgManageCenterName).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("机构名称");
        }
        if($(self.el.orgManageContactName).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("联系人");
        }
        if($(self.el.orgManageContactTel).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("联系人电话");
        }
        if($(self.el.orgManageCustsvctel).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("客服电话");
        }
        if($(self.el.orgManagePortalUrl).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("机构官网");
        }
        if($(self.el.orgManagePostCode).val().length < 1) {
            canPost = false;
            dialogErrorMessage.push("邮政编码");
        }

        if(!canPost) {
            // alert error message
            var errorMessage = dialogErrorMessage.join(",");
            if(dialogErrorMessage.length > 1) {
                errorMessage += "等";
            }
            $(self.el.popupContainer).hide();
            // dialog
            dialogBox.type("error");
            dialogBox.text(errorMessage + "不能为空！");
            dialogBox.ok(function () {
                $(self.el.popupContainer).show();
            });
            dialogBox.show();
        } else {
            Common.loading(true);
            // send data
            var postData = {
                freeuse1: $(self.el.orgManageFreeuse1).val(),
                centerid: $(self.el.orgManageCenterid).val(),
                centername: $(self.el.orgManageCenterName).val(),
                uselevel: $(self.el.orgManageCenterLevel).val(),
                contactname: $(self.el.orgManageContactName).val(),
                contacttel: $(self.el.orgManageContactTel).val(),
                custsvctel: $(self.el.orgManageCustsvctel).val(),
                portalurl: $(self.el.orgManagePortalUrl).val(),
                postcode: $(self.el.orgManagePostCode).val(),
                freeuse2: $(self.el.orgManageFreeuse2).val()
            };
            if (isAdd) {
                window.frames['main'].createData(postData);
            } else {
                window.frames['main'].editData(postData);
            }
            $(self.el.popupContainer).hide();
        }
    },
    getData: function () {},
    deleteConfirm: function (itemId) {
        // dialog
        dialogBox.type("tips");
        dialogBox.text("确认是否删除！");
        dialogBox.okShow(true);
        dialogBox.cancelShow(true);
        dialogBox.okText("确定");
        dialogBox.cancelText("取消");
        dialogBox.ok(function () {
            // send Data
            Common.loading(true);
            window.frames['main'].tableDelete(itemId);
        });
        dialogBox.show();
    }
};

var reservation = {
    createDatepicker: function (el) {
        console.log($(el));
        laydate({
            elem: el,
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
            }
        });
        laydate.skin('huanglv');
    }
};

if (typeof $ != "undefined") {
    (function ($) {
        // register global object "dialogBox" for dialog
        window.dialogBox = dialog({
            el: "#dialog",
            type: "warning",
            okText: "确定",
            okShow: true,
            cancelShow: false,
            ok: function () {
                $("#popup-container").hide();
            },
            text: ""
        });
    })(jQuery);
    // get user info
    $.ajax({
        type: 'POST',
        url: './getUserMessage.json',
        dataType: 'json',
        success: function(obj){
            if(obj.recode != '000000'){
                top.Common.dialog({
                    type: "error",
                    text: obj.msg + "页面将在三秒后跳转，点击确定直接跳转。",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        top.location.href = "./login.html";
                    }
                });
                setTimeout(function () {
                    top.location.href="./login.html";
                }, 3000);
            }else{
                var timer = setInterval(function () {
                    if(window.frames['header'].document.getElementById('userName') != null) {
                        window.frames['header'].document.getElementById('centerName').innerText = obj.centerName;
                        window.frames['header'].document.getElementById('userName').innerText = obj.opername;
                        clearInterval(timer);
                    }
                }, 500);
                window.userInfo = obj;
                window.localStorage.setItem("nav", obj.funcJson);
                console.log(obj)
                //ssoUtils.ssoLoginNotice(obj.loginid ,"YBMAPZH","");
            }
        },
        error: function(xhr, errorType, error){
            top.Common.dialog({
                type: "error",
                text: "登录失败！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    top.location.href = "./login.html";
                }
            });
        }
    });
    window.addEventListener("DOMContentLoaded",function(){
        Common.loading(false);
    },false);
}