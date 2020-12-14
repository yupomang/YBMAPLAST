/**
 * Created by FelixAn on 2016/9/5.
 */
var material = {
    vuer: null,
    pager: null,
    pageSize: 10,
    selectedMaterial: null, // for Material move group
    createVue: function () {
        var self = this;
        self.vuer = new Vue({
            el: '.upload-vue',
            data: {
                groups: [],
                currentGroup: null,
                currentGroupId: null,
                materials: [],
                selectedMaterials: [],
                selectedAll: false
            },
            methods: {
                createGroup: function () {
                    self.popupCreateGroup();
                },
                renameGroup: function () {
                    self.popupRenameGroup();
                },
                delGroup: function () {
                    parent.Common.dialog({
                        type: "tips",
                        text: "是否确认删除当前分组（仅删除分组，不删除素材，组内素材将自动归入未分组）？",
                        okShow: true,
                        cancelShow: true,
                        okText: "确定",
                        ok: function () {
                            self.delGroup();
                        }
                    });
                },
                getMaterial: function (group) {
                    parent.Common.loading(true);
                    this.currentGroup = group;
                    this.currentGroupId = group.groupid;
                    self.getMaterial(group.groupid, 0, self.pageSize, true);
                },
                delMaterial: function (id) {
                    if (id) {
                        parent.Common.dialog({
                            type: "tips",
                            text: "确定删除选中的素材吗？",
                            okShow: true,
                            cancelShow: true,
                            okText: "确定",
                            ok: function () {
                                self.delMaterial(id);
                            }
                        });
                    } else {
                        var ids = this.selectedMaterials;
                        if (ids.length < 1) {
                            parent.Common.dialog({
                                type: "warning",
                                text: "请至少选择一条素材进行删除！",
                                okShow: true,
                                cancelShow: true,
                                okText: "确定",
                                ok: function () {
                                }
                            });
                        } else {
                            parent.Common.dialog({
                                type: "tips",
                                text: "确定删除选中的素材吗？",
                                okShow: true,
                                cancelShow: true,
                                okText: "确定",
                                ok: function () {
                                    self.delMaterial(ids.join(','));
                                }
                            });
                        }
                    }
                },
                selectAllFile: function () {
                    var _this = this;
                    var tempIdArr = [];
                    if (_this.selectedAll) {
                        _this.materials.forEach(function (material) {
                            tempIdArr.push(material.picid);
                        });
                    }
                    _this.selectedMaterials = tempIdArr;
                },
                renameMaterial: function (material) {
                    var renameHTML = $(".material-rename").html();
                    parent.Common.popupShow(renameHTML);
                    parent.$("#rename-material-name").val(material.realname);
                    parent.$("#rename-material-name").attr("data-picid", material.picid);
                },
                moveGroup: function (id) {
                    if (id) {
                        self.selectedMaterial = id;
                    } else {
                        var ids = this.selectedMaterials;
                        if (ids.length < 1) {
                            parent.Common.dialog({
                                type: "warning",
                                text: "请至少选择一条素材移动分组！",
                                okShow: true,
                                cancelShow: false,
                                okText: "确定",
                                ok: function () {
                                }
                            });
                            return;
                        } else {
                            self.selectedMaterial = ids.join(",");
                        }
                    }
                    var moveHTML = $(".move-group").html();
                    parent.Common.popupShow(moveHTML);
                }
            }
        });
        self.getGroupList();
        self.createPager();
    },
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#upload-pager",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex) {
                self.getMaterial(self.vuer.currentGroupId, pageIndex - 1, self.pageSize, false);
            }
        });
    },
    getGroupList: function (group) {
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
                    self.vuer.groups = data.groupList;
                    var selectHtml = '';
                    data.groupList.forEach(function (item) {
                        selectHtml += '<option value="' + item.groupid + '">' + item.groupname + '</option>';
                    });
                    $("#group-list").html(selectHtml);
                    var groupId;
                    if(group) {
                        self.vuer.currentGroup = group;
                        self.vuer.currentGroupId = group.groupid;
                        groupId = group.groupid;
                    } else {
                        self.vuer.currentGroup = data.groupList[0];
                        self.vuer.currentGroupId = data.groupList[0].groupid;
                        groupId = data.groupList[0].groupid;
                    }
                    self.getMaterial(groupId, 0, self.pageSize, true);
                    // bind upload event
                    self.upload();
                    // for 图文素材
                    self.btnClick();
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
    getMaterial: function (groupid, page, num, resetBool) {
        /*
        *  page:  current page
        *  num:   page size
        *  reset: for pager
        * */
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi13003.json",
            data: { 'groupid': groupid, 'page': page, 'num': num },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.materials = data.rows;
                    self.vuer.selectedMaterials = []; // clear selected materials
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: data.pageSize,
                        reset: resetBool
                    });
                } else {
                    parent.Common.loading(false);
                    self.vuer.materials = [];
                    self.vuer.selectedMaterials = [];
                    self.pager.reset({
                        itemLength: 0,
                        pageSize: self.pageSize,
                        reset: resetBool
                    });
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
    popupCreateGroup: function () {
        var createHTML = $(".group-create").html();
        parent.Common.popupShow(createHTML);
    },
    createGroup: function () {
        var self = this;
        var groupname = parent.$("#create-group-name").val();
        if(groupname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "分组名称不能为空！",
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
            url: "./webapi13001.json",
            data: { 'groupname': groupname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getGroupList();
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
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
    popupRenameGroup: function () {
        var self = this;
        var renameHTML = $(".group-rename").html();
        parent.Common.popupShow(renameHTML);
        parent.$("#rename-group-name").val(self.vuer.currentGroup.groupname);
    },
    renameGroup: function () {
        var self = this;
        var groupname = parent.$("#rename-group-name").val();
        if(groupname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "分组名称不能为空！",
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
            url: "./webapi13004.json",
            data: { 'id': self.vuer.currentGroup.groupid, 'groupname': groupname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    self.getGroupList();
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
    delGroup: function () {
        var self = this;
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi13005.json",
            data: { 'id': self.vuer.currentGroup.groupid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    self.getGroupList();
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
    upload: function () {
        var self = this;
        var oBtn = document.getElementById("uploadFile2");
        new AjaxUpload(oBtn, {
            action: "./webapi13002.json",
            name: "file",
            data: {
                'dirname': self.vuer.currentGroup.groupid,
                'uploadtype': 'image'
            },
            onSubmit: function (file,ext) {
                /*
                *  ("image", "gif,jpg,jpeg,png,bmp");
                *  ("flash", "swf,flv");
                *  ("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
                *  ("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");                *
                * */
                if(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)){
                    //ext是后缀名
                    this.setData({
                        'dirname': self.vuer.currentGroup.groupid,
                        'uploadtype': 'image'
                    });
                    oBtn.disabled = "disabled";
                } else if(ext && /^(swf|flv)$/.test(ext)) {
                    this.setData({
                        'dirname': self.vuer.currentGroup.groupid,
                        'uploadtype': 'flash'
                    });
                    oBtn.disabled = "disabled";
                } else if(ext && /^(swf|flv|mp3|wav|wma|wmv|mid|avi|mpg|asf|rm|rmvb)$/.test(ext)) {
                    this.setData({
                        'dirname': self.vuer.currentGroup.groupid,
                        'uploadtype': 'media'
                    });
                    oBtn.disabled = "disabled";
                } else if(ext && /^(doc|docx|xls|xlsx|ppt|htm|html|txt|zip|rar|gz|bz2|pdf)$/.test(ext)) {
                    this.setData({
                        'dirname': self.vuer.currentGroup.groupid,
                        'uploadtype': 'file'
                    });
                    oBtn.disabled = "disabled";
                } else {
                    parent.Common.dialog({
                        type: "warning",
                        text: "上传格式错误，素材格式只能是gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf中的一种！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    return false;
                }
                parent.Common.loading(true);
            },
            onComplete:function(file, response){
                if(response.length > 1) {
                    if(typeof response == "string") {
                        response = JSON.parse(response);
                    }
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: response.message,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                } else if (file.length > 1) {
                    self.getGroupList(self.vuer.currentGroup);
                    parent.Common.dialog({
                        type: "success",
                        text: "上传成功！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }
            }
        });
    },
    delMaterial: function (ids) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi13006.json",
            data: { 'idarr': ids },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    self.getGroupList();
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
    renameMaterial: function () {
        var self = this;
        var newname = parent.$("#rename-material-name").val(),
            allpicid = parent.$("#rename-material-name").attr("data-picid");
        if(newname.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "素材名称不能为空！",
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
            url: "./webapi13009.json",
            data: { 'allpicid': allpicid, 'newname': newname },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    self.getGroupList();
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
    moveGroup: function () {
        var self = this;
        var groupid = parent.$("#group-list").val(),
            picidarr = self.selectedMaterial;
        parent.Common.loading(true);
        parent.Common.popupClose();
        $.ajax({
            type: "POST",
            url: "./webapi13008.json",
            data: { 'groupid': groupid, 'picidarr': picidarr },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    self.getGroupList();
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
    btnClick: function () {
        var self = this;
        $("#uploadFileFont").off().on("click.imgFont", function () {
            $(".create").show();
            $(".upload-vue").hide();
            $(".upload-pager-box").hide();
        });
        $(".create-title a").off().on("click", function () {
            $(".create").hide();
            $(".upload-vue").show();
            $(".upload-pager-box").show();
        });
        // 创建editor
        UE.getEditor('create-left-editor');
        // create upload
        var oBtn = document.getElementById("create-left-upload-icon");
        new AjaxUpload(oBtn, {
            action: "./webapi13002.json",
            name: "file",
            data: {
                'dirname': self.vuer.currentGroup.groupid,
                'uploadtype': 'image'
            },
            onSubmit: function (file,ext) {
                if(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)){
                    //ext是后缀名
                    oBtn.disabled = "disabled";
                } else {
                    parent.Common.dialog({
                        type: "warning",
                        text: "上传格式错误，素材格式只能是gif,jpg,jpeg,png,bmp中的一种！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                    return false;
                }
                parent.Common.loading(true);
            },
            onComplete: function(file, response){
                parent.Common.loading(false);
                response = JSON.parse(response);
                try {
                    if(response.error == 0) {
                        $(".create-left-icons").empty().append('<img width="138" height="77" src="' + response.url + '" />');
                    } else {
                        parent.Common.dialog({
                            type: "error",
                            text: response.message,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    }
                } catch (e) {
                    console.log(e);
                    parent.Common.dialog({
                        type: "error",
                        text: "上传失败！请刷新页面重试！",
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }
            }
        });
    }
};
parent.Common.loading(true);
material.createVue();