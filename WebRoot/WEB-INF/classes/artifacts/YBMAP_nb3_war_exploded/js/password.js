/**
 * Created by FelixAn on 2016/9/18.
 */
var pwd = {
    vuer: null,
    createVue: function () {
        var self = this;
        self.vuer = new Vue({
            el: '.password-bg',
            data: {
                userName: top.userInfo.loginid,
                oldPwd: '',
                newPwd: '',
                newPwdAgain: ''
            },
            methods: {
                verify: function () {
                    var _this = this,
                        verifyStatus = true,
                        verifyTips = [],
                        tipsMsg = '';
                    if(_this.userName.length < 1) {
                        verifyTips.push("用户名");
                        verifyStatus = false;
                    }
                    if(_this.oldPwd.length < 1) {
                        verifyTips.push("原始密码");
                        verifyStatus = false;
                    }
                    if(_this.newPwd.length < 1) {
                        verifyTips.push("新密码");
                        verifyStatus = false;
                    }
                    if(_this.newPwdAgain.length < 1) {
                        verifyTips.push("确认新密码");
                        verifyStatus = false;
                    }
                    // 有空值
                    if(!verifyStatus) {
                        if(verifyTips.length > 1) {
                            tipsMsg = verifyTips.join(",") + "等不能为空！";
                        } else {
                            tipsMsg = verifyTips[0] + "不能为空！";
                        }
                        parent.Common.dialog({
                            type: "warning",
                            text: tipsMsg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    } else {
                        if(_this.newPwd == _this.newPwdAgain) {
                            parent.Common.loading(true);
                            $.ajax({
                                type: "POST",
                                url: "./ptl40011Upd.json",
                                data: {
                                    'centerid': '',
                                    'loginid': _this.userName,
                                    'password': _this.oldPwd,
                                    'newpassword': _this.newPwd,
                                    'renewpassword': _this.newPwdAgain
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
                                            ok: function () {}
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
                        } else {
                            parent.Common.dialog({
                                type: "warning",
                                text: "两次输入密码不匹配！",
                                okShow: true,
                                cancelShow: false,
                                okText: "确定",
                                ok: function () {
                                }
                            });
                        }
                    }
                }
            }
        });
    }
};
pwd.createVue();