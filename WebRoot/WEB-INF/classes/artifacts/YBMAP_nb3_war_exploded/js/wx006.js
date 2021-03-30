/**
 * Created by FelixAn on 2016/9/6.
 */
var focusInfoConfig = {
    vuer: null,
    imgPlaceHolder: 'images/upload_placeholder.jpg',
    realurl: '',
    groupList: null,
    createVue: function () {
        var self = this;
        self.vuer = new Vue({
            el: "#wechat-focusInfoConfig-Vue",
            data: {
                title: "",
                welcomeWord: "",
                startIndex: 1,
                img: self.imgPlaceHolder, // 图片地址
                items: [], // 功能 array
                isLib: false, // 图片库的显示隐藏 true显示
                select: null, // 素材选中
                groupList: [], // 素材组
                currentGrounp: null, // 当前素材组的groupid
                libPics: [] // 当前素材组的图片
            },
            computed: {
                checked: function () {
                    var itemsArr = this.items,
                        temp = [];
                    for(var i = 0; i<itemsArr.length; i++) {
                        if(itemsArr[i].isShow) {
                            temp.push(itemsArr[i]);
                        }
                    }
                    return temp;
                }
            },
            methods: {
                imgDel: function () {
                    self.imgDel();
                },
                save: function () {
                    self.save();
                },
                selectPic: function () {
                    if (this.select == null) {
                        return;
                    } else {
                        self.checkImg(this.select);
                        this.isLib = false;
                    }
                },
                getLibPics: function (list) {
                    self.getPicLib(list.groupid);
                },
                selectImg: function (pic) {
                    if(pic.freeuse1 != 'image') {
                        parent.Common.dialog({
                            type: "warning",
                            text: "选择了非图片格式，请重新选择！",
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    } else {
                        this.select = pic;
                    }
                }
            }
        });
        self.btnClick();
    },
    getGroupList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page13001.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.groupList = data.groupList;
                    self.getFns();
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getFns: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./weixinapi00404.json",
            data: { 'regionId': top.userInfo.centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    var rows = JSON.parse(data.rows);
                    rows.result.forEach(function (item) {
                        self.vuer.items.push({
                                isShow: false,
                                value: item.name
                            });
                    });
                    self.getPicLib(self.vuer.groupList[0].groupid);
                    self.query();
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
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    getPicLib: function (groupid) {
        var self = this;
        self.vuer.currentGrounp = groupid;
        $.ajax({
            type: "POST",
            url: "./webapi13003.json",
            data: { 'groupid': groupid, 'page': 0, 'num': 1000 },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.libPics = data.rows;
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
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    query: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./weixinapi00408.json",
            data: { 'regionId': top.userInfo.centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    var rows = data.rows;
                    self.vuer.title = rows.title;
                    self.vuer.img = rows.imgUrl;
                    self.realurl = rows.realurl;
                    self.vuer.welcomeWord = rows.welcome;
                    for(var row in rows.funcArray) {
                        self.vuer.items.forEach(function (item) {
                            if (rows.funcArray[row] == item.value) {
                                item.isShow = true;
                            }
                        });
                    }
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
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    btnClick: function () {
        var self = this;
        $(".wechat-focusInfoConfig-img-upfile input").on("change", function () {
            var file = an._$("uploadfile").files,
                lim = 1024 * 1024;
            var pictype = file[0].type;
            if(pictype == "image/jpeg" || pictype == "image/png" || pictype == "image/jpg" || pictype == "image/bmp" || pictype == "image/gif"){
            }else{
                parent.Common.dialog({
                    type: "warning",
                    text: "上传图片格式有误，图片格式为jpg,jpeg,bmp,png！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            }
            if(file[0].size > lim){
                parent.Common.dialog({
                    type: "warning",
                    text: file[0].name + "大小超过限制，大小不能超过1M",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            }
            $("#imageform").ajaxForm({
                dataType:'json',
                type:'POST',
                clearForm:true,
                resetForm:true,
                success:function(data) {
                    self.vuer.img = data.imgUrl;
                    self.realurl = data.realUrl;
                }
            }).submit();
        });
        $("#uploadfile_lib").on("change", function () {
            var file = an._$("uploadfile_lib").files,
                lim = 1024 * 1024;
            var pictype = file[0].type;
            if(pictype == "image/jpeg" || pictype == "image/png" || pictype == "image/jpg" || pictype == "image/bmp" || pictype == "image/gif"){
            }else{
                parent.Common.dialog({
                    type: "warning",
                    text: "上传图片格式有误，图片格式为jpg,jpeg,bmp,png！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            }
            if(file[0].size > lim){
                parent.Common.dialog({
                    type: "warning",
                    text: file[0].name + "大小超过限制，大小不能超过1M",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                return false;
            }
            $("#imageform_lib").ajaxForm({
                dataType:'json',
                type:'POST',
                clearForm:true,
                resetForm:true,
                success:function(data) {
                },
                error: function (data) {
                    if(data.statusText == "OK") {
                        self.getGroupList();
                        parent.Common.dialog({
                            type: "success",
                            text: "上传成功！",
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                                self.getPicLib(self.vuer.currentGrounp);
                            }
                        });
                    } else {
                        parent.Common.dialog({
                            type: "error",
                            text: "上传失败！",
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    }
                }
            }).submit();
        });
    },
    imgDel: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./weixinapi00402.json",
            data: { 'realurl': self.realurl },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.img = self.imgPlaceHolder;
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
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    save: function () {
        var self = this,
            vuer = self.vuer,
            _index = 0;
        if (vuer.title.length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: '标题不能为空！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        if (vuer.img == self.imgPlaceHolder) {
            parent.Common.dialog({
                type: "warning",
                text: '请选择图片！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        if (vuer.welcomeWord.length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: '请输入欢迎语！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        var postData = {
            'title': vuer.title,
            'imgUrl': vuer.img,
            'realurl': self.realurl,
            'welcome': vuer.welcomeWord,
            'centerid': top.userInfo.centerid
        };
        vuer.items.forEach(function (item) {
            if(item.isShow) {
                postData['funcArray[' + _index++ + ']'] = item.value;
            }
        });
        if (_index == 0) {
            parent.Common.dialog({
                type: "warning",
                text: '请选择功能！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        $.ajax({
            type: "POST",
            url: "./weixinapi00407.json",
            data: postData,
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
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
                }
            },
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    checkImg: function (item) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi13012.json",
            data: { 'sysname': item.sysname, 'realpicurl': item.picurl, 'savepicurl': "weixin/subscribe/" + item.centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.img = data.imgUrl;
                    self.realurl = data.serverimg;
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
            error: function () {
                parent.Common.ajaxError();
            }
        });
    }
};
focusInfoConfig.createVue();
focusInfoConfig.getGroupList();