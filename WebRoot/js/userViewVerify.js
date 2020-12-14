/**
 * Created by FelixAn on 2016/11/25.
 */
var userViewVerify = {
    pager: null,
    pageSize: 10,
    tempCenterid: null,
    tabler: null,
    centerList: [],
    tempId: null,
    getCenter: function () {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webappcomCenterId.json',
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    var temp = '';
                    self.centerList = data.mi001list;
                    data.mi001list.forEach(function (item) {
                        temp += '<option value="' + item.centerid + '">' + item.centername + '</option>';
                    });
                    $("#customer-name-select").html(temp);
                    $("#create-custom-name").html(temp);
                    $("#edit-custom-name").html(temp);
                    self.createPager();
                    self.btnClick();
                    self.query(1, 10, true, data.mi001list[0].centerid);
                } else {
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
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#verify-userview-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.query(pageIndex, pageSize, false, self.tempCenterid);
            }
        });
    },
    btnClick: function () {
        var self = this;
        // add
        $("#verify-userview-btn-add").off().on('click', function () {
            var createHTML = $(".verify-userview-create").html();
            parent.Common.popupShow(createHTML);
        });
        // edit
        $("#verify-userview-btn-edit").off().on('click', function () {
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            var editHTML = $(".verify-userview-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$("#edit-custom-name").val(selectedData[0].centerid);
            parent.$("#edit-reqname").val(selectedData[0].reqname);
            parent.$("#edit-startreferer").val(selectedData[0].startreferer);
            parent.$("#edit-referer").val(selectedData[0].referer);
            parent.$("#edit-jsname").val(selectedData[0].jsname);
            self.tempId = selectedData[0].id;
        });
        // del
        $("#verify-userview-btn-del").off().on('click', function () {
            var selectedData = self.tabler.selectedRows();
            if(selectedData.length < 1) {
                parent.Common.delNone();
                return;
            }
            var tempIds = [];
            for(var i = 0; i < selectedData.length; i++) {
                tempIds.push(selectedData[i].id);
            }
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    self.del(tempIds.join(","));
                }
            });
        });
        // query
        $(".sysMgmt-fnDeploy-top-greenBtn").off().on("click", function () {
            self.query(1, self.pageSize, true, $("#customer-name-select").val());
        });
    },
    query: function (pageIndex, pageSize, resetBool, centerid) {
        var self = this;
        parent.Common.loading(true);
        self.tempCenterid = centerid;
        $.ajax({
            type:'POST',
            url:'./webapi05704.json',
            data: {
                centerid: centerid,
                page: pageIndex,
                rows: pageSize
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    self.table(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: pageSize,
                        reset: resetBool
                    });
                    parent.Common.loading(false);
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
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    verify: function (reqname, referer) {
        var verifyStatus = true,
            tipsWords = [];
        if(reqname.length < 1) {
            verifyStatus = false;
            tipsWords.push('插件调用者');
        }
        if(referer.length < 1) {
            verifyStatus = false;
            tipsWords.push('授权域名');
        }
        if (!verifyStatus) {
            var msg = tipsWords.join(',');
            if (tipsWords.length > 1) {
                msg += '等';
            }
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: msg + '不能为空！',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
        }
        return verifyStatus;
    },
    create: function () {
        var self = this;
        var centerid = parent.$("#create-custom-name").val(),
            reqname = parent.$("#create-reqname").val(),
            startreferer = parent.$("#create-startreferer").val(),
            referer = parent.$("#create-referer").val(),
            jsname = parent.$("#create-jsname").val();
        if (!self.verify(reqname, referer)) return;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi05701.json',
            data: {
                centerid: centerid,
                reqname: reqname,
                startreferer: startreferer,
                referer: referer,
                jsname: jsname
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.query(1, self.pageSize, true, self.tempCenterid);
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
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    edit: function () {
        var self = this;
        var centerid = parent.$("#edit-custom-name").val(),
            reqname = parent.$("#edit-reqname").val(),
            startreferer = parent.$("#edit-startreferer").val(),
            referer = parent.$("#edit-referer").val(),
            jsname = parent.$("#edit-jsname").val();
        if (!self.verify(reqname, referer)) return;
        parent.Common.popupClose();
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi05703.json',
            data: {
                centerid: centerid,
                reqname: reqname,
                startreferer: startreferer,
                referer: referer,
                jsname: jsname,
                id: self.tempId
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.query(1, self.pageSize, true, self.tempCenterid);
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
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    del: function (ids) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi05702.json',
            data: {
                id: ids
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.query(1, self.pageSize, true, self.tempCenterid);
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
            error:function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    table: function (data) {
        var self = this,
            centerList = self.centerList;
        if(self.tabler == null) {
            var cols = [
                { title:'客户名称', name:'centerid' ,width:266, align: 'center', renderer: function (val) {
                    var temp = '';
                    centerList.forEach(function (item) {
                        if(item.centerid == val) {
                            temp = item.centername;
                        }
                    });
                    return temp;
                } },
                { title:'插件调用者', name:'reqname' ,width:166, align: 'center'},
                { title:'启用开关', name:'startreferer' ,width:108, align: 'center', renderer: function (val) {
                    var temp = "";
                    switch (val) {
                        case '1':
                            temp = '启用';
                            break;
                        case '0':
                            temp = '不启用';
                            break;
                    }
                    return temp;
                }},
                { title:'授权域名', name:'referer' ,width:368, align: 'center'},
                { title:'调用JS名', name:'jsname' ,width:112, align: 'center'}
            ];
            self.tabler = $('#verify-userview-table').mmGrid({
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
        } else {
            self.tabler.load(data);
        }
    }
};
userViewVerify.getCenter();