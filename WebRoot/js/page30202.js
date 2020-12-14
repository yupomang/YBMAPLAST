/**
 * Created by FelixAn on 2016/10/18.
 */
var customMessage = {
    pager: null,
    tabler: null,
    viewTabler: null,
    pageSize: 10,
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#custom-message-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTable();
            }
        });
        self.getTable();
        $("#centerid").val(top.userInfo.centerid);
        $("#userid").val(top.userInfo.loginid);
    },
    getTable: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi30202.json",
            data: { 'method': 'query', 'centerid': top.userInfo.centerid },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
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
        var cols = [
            { title:'序号', name:'commsgid' ,width:40, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'消息ID', name:'commsgid' ,width:96, align:'center'},
            { title:'消息标题', name:'title' ,width:130, align:'center'},
            { title:'消息类型', name:'tsmsgtype' ,width:146, align:'center', renderer: function (val) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '文本消息';
                        break;
                    case '02':
                        temp = '图片';
                        break;
                    case '03':
                        temp = '富文本';
                        break;
                    case '04':
                        temp = '音频';
                        break;
                    case '05':
                        temp = '视频';
                        break;
                    case '06':
                        temp = '富文本多条';
                        break;
                }
                return temp;
            }},
            { title:'状态', name:'status' ,width:78, align:'center', renderer: function (val, item) {
                var temp = '';
                switch (val) {
                    case '0':
                        temp = '未推送';
                        break;
                    case '1':
                        temp = '已推送';
                        break;
                }
                return temp;
            }},
            { title:'推送方式', name:'timing' ,width:88, align:'center', renderer: function (val, item) {
                var temp = '';
                switch (val) {
                    case '1':
                        temp = '定时';
                        break;
                    case '0':
                        temp = '不定时';
                        break;
                }
                return temp;
            }},
            { title:'定时时间', name:'dsdate' ,width:130, align:'center'},
            // { title:'数量', name:'sumcount' ,width:40, align:'center'},
            { title:'待推送人数', name:'sumcount' ,width:74, align:'center'},
            { title:'来源', name:'msgsource' ,width:124, align:'center', renderer: function (val) {
                var temp = '';
                switch (val) {
                    case '10':
                        temp = '综合服务平台';
                        break;
                }
                return temp;
            }},
            { title:'操作', name:'msgsource' ,width:74, align:'center', renderer: function (val, item) {
                var temp = '';
                if(item.timing == "0") {
                    temp = '<a href="javascript:;" class="color-green" style="margin-right:10px" title="推送" onclick="customMessage.push(\'' + item.commsgid + '\')">推送</a>';
                }
                temp += '<a href="javascript:;" class="color-green" title="明细" onclick="customMessage.view(\'' + item.commsgid + '\' , \'1\')">明细</a>';
                return temp;
            } }
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#custom-message-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
                height: '400px',
                cols: cols,
                items: data,
                loadingText: "loading...",
                noDataText: "暂无数据。",
                loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                sortable: false
            });
        }
        parent.Common.loading(false);
        self.btnClick();
    },
    createViewTable: function (data) {
        var self = this;
        var cols = [
            { title:'用户名', name:'username' ,width:126, align:'center'},
            { title:'消息标题', name:'title' ,width:186, align:'center'},
            { title:'消息类型', name:'tsmsgtype' ,width:186, align:'center', renderer: function (val) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '文本消息';
                        break;
                    case '02':
                        temp = '图片';
                        break;
                    case '03':
                        temp = '富文本';
                        break;
                    case '04':
                        temp = '音频';
                        break;
                    case '05':
                        temp = '视频';
                        break;
                    case '06':
                        temp = '富文本多条';
                        break;
                }
                return temp;
            }},
            { title:'消息内容', name:'tsmsg' ,width:276, align:'center'},
            { title:'电话', name:'param1' ,width:196, align:'center'}
            // { title:'审批人', name:'freeuse1' ,width:116, align:'center'},
            // { title:'是否审批', name:'validflag' ,width:96, align:'center'}
        ];
        if(self.viewTabler != null) {
            self.viewTabler.load(data);
        } else {
            self.viewTabler = $('#custom-message-view-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
                height: '400px',
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
    btnClick: function () {
        var self = this;
        $("#customMessage-btn-input").off().on("click", function () {
            var addHTML = $(".message-import").html();
            parent.Common.popupShow(addHTML);
            parent.$("#importFile").on("change", function (e) {
                var name = e.currentTarget.files[0].name;
                name.length>15?name=name.substr(0,20)+'...':name=name;
                parent.$(".custom-message-upload span").text( name );
            });
            setTimeout(function(){
                // bind date picker
                var endTime = {
                    elem: '#dsdate',
                    format: 'YYYY-MM-DD hh:mm:ss',
                    min: parent.laydate.now(),
                    max: '2099-12-31',
                    istime: false,
                    istoday: false,
                    fixed: true,
                    choose: function(datas){
                    }
                };
                parent.laydate(endTime);
                parent.laydate.skin('huanglv');
            },100);
        });
        $("#customMessage-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: '是否删除选中项？',
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });
        $("#customMessage-btn-back").off().on("click", function () {
            $(".custom-message-view").hide();
            $(".custom-message-box").show();
        });
    },
    upload: function () {
        var self = this,
            excelFile=parent.$('#excelfile').text();
        if(null == excelFile || "" == excelFile || excelFile == '未选择文件'){
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: '请填写上传路径后进行提交',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        if(parent.$('input[name=timing]:checked').val() == '1' && parent.$("#dsdate").val().length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: '请填写定时时间',
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return false;
        }
        parent.Common.loading(true);
        parent.$('#fm').ajaxSubmit({
            dataType : "json",
            success : function(data) {
                if(data.recode == '000000'){
                    parent.$("#popup-container").hide();
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTable();
                        }
                    });
                } else if(data.recode == "299992"){
                    parent.$("#popup-container").hide();
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "tips",
                        text: '文件检查异常,是否下载检查结果',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            window.location.href = data.msg;
                            parent.Common.popupClose();
                        }
                    });
                } else {
                    parent.$("#popup-container").hide();
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            window.location.href = data.msg;
                            parent.Common.popupClose();
                        }
                    });
                }
            },
            error:function() {
                parent.$("#popup-container").hide();
                parent.Common.ajaxError();
            }
        });
    },
    del: function () {
        var self = this;
        var selected = self.tabler.selectedRows(),
            ids = [];
        selected.forEach(function (item) {
            ids.push(item.commsgid);
        });
        $.ajax({
            type:'POST',
            url:'./webapi30202.json?method=delete',
            data:{
                'centerid': top.userInfo.centerid,
                'commsgid': ids.join(',')
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTable();
                        }
                    });
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
                errMsg();
            }
        });
    },
    push: function (id) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi30202.json?method=send',
            data:{
                'centerid': top.userInfo.centerid,
                'commsgid': id
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTable();
                        }
                    });
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
                parent.Common.loading(false);
                errMsg();
            }
        });
    },
    view: function (id, pageIndex) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi3020201.json?method=query',
            data:{
                'centerid': top.userInfo.centerid,
                'commsgid': id,
                'page': pageIndex,
                'rows': self.pageSize
            },
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    $(".custom-message-view").show();
                    $(".custom-message-box").hide();
                    self.createViewTable(data.rows);
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
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize
                });
            },
            error:function(){
                errMsg();
            }
        });
    }
};

// 定制消息推送
customMessage.createPager();