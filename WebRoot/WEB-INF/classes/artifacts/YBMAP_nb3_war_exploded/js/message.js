/**
 * Created by FelixAn on 2016/9/18.
 * update on 2018/4/11
 */
var bulkMessage = {
    pager: null,
    tabler: null,
    pageSize: 10,
    editor: null,
    isAdd: true,
    fileName: null,
    imgPath: null, // 存放上传完的图片的路径
    channelArr: [],
    selectedChannel: '',
    commsgid: null, // 新建多条的时候 存储用
    msmscommsgid: null, // 多条的子信息
    moreVuer: null, // 多条的时候，左侧的vue
    editId: null, // 修改的时候用的id
    editChannel: '',
    newsType: '01',
    indexForSort:'0',
    materials: null, // for 素材库 search
    pushedRow: false, // 要查看的信息, 是否是"已发送"的信息, 在修改的时候控制是否可编辑
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#bulk-message-table-pages",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
            }
        });
        self.getTable(true);
        // 创建富文本
        self.createEditor();
        // 创建素材库vue
        self.createVue();
        // 绑定图片、音频、视频上传
        self.createUpload();
        // 获取消息主题
        //self.getNewsList();
        // 创建多条vuer
        self.createMoreVue();
    },
    getTable: function (resetBool) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=query",
            data: { 'centerid': top.userInfo.centerid },
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
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize,
                    reset: resetBool
                });
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getNewsList: function (resetBool) {
        /**
         *  获取消息主题
         *  暂时没用到
         * */
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page30205GetParam.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var list = data.message_topic_type,
                        tempHtml = '';
                    list.forEach(function (item) {
                        tempHtml += '<option value="' + item.itemid + '">' + item.itemval + '</option>';
                    });
                    $("#create-left-columns").html(tempHtml);
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
                self.pager.reset({
                    itemLength: data.total,
                    pageSize: self.pageSize,
                    reset: resetBool
                });
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getChannel: function (channel, callbackSetValue) {
        /**
         *  根据渠道取当前渠道下的应用，每次新建或修改之前调用
         *  @channel {String} 渠道编号： 10-APP， 20-微信， 30-门户网站， 40-网上业务大厅， 50-自助终端， 60-服务热线， 70-手机短信， 80-官方微博
         *  @callbackSetValue {bool} 回调后是否设置选中
         * */
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi04007.json",
            data: {
                'centerid': top.userInfo.centerid,
                'channel': channel
            },
            datatype: "json",
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.channelArr = data.applist;
                    // 每次取完pid都和channel对比, 隐藏没有应用的渠道
                    var hasChannel = [];
                    data.applist.forEach(function (item) {
                        if(!self.isAdd) {
                            // 修改回显渠道
                            var editChannel = self.editChannel.split(",");
                            editChannel.forEach(function (val) {
                                if(val == item.pid) {
                                    $(".create-send-channel input[value=" + item.channel + "]").trigger("click").prop("checked", true);
                                }
                            });
                        }
                        if(!(item.channel in hasChannel)) {
                            hasChannel.push(item.channel);
                        }
                    });
                    // 每次取完pid都和channel对比, 隐藏没有应用的渠道
                    $(".create-send-channel label").hide();
                    hasChannel.forEach(function (val) {
                        $(".create-send-channel input[value=" + val + "]").parent("label").show();
                    });
                    if (callbackSetValue) {
                        var channels = channel.split(',');
                        channels.forEach(function (val) {
                            $(".create-send-channel label input[value='" + val + "']").prop('checked', true);
                        });
                    }
                } else {
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
                // 单图文，微信才可以预览
                if (self.newsType == '03' && $(".create-send-channel input[value='20']").prop("checked")) {
                    $("#create-perview-btn").show();
                } else if(self.newsType == '03' && $(".create-send-channel input[value='80']").prop("checked")) {
                    // 单图文并且微博勾选,显示微博类型
                    $("#isNormalWeibo").show();
                } else {
                    $("#create-perview-btn").hide();
                }
            },
            error: function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'num' ,width:40, align:'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            { title:'消息ID', name:'commsgid' ,width:126, align:'center'},
            { title:'消息标题', name:'title' ,width:154, align:'center'},
            //{ title:'消息主题', name:'freeuse1' ,width:142, align:'center'},
            { title:'消息类型', name:'tsmsgtype' ,width:142, align:'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '文本';
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
                        temp = '富文本多条，微信专用';
                        break;
                }
                return temp;
            }},
            { title:'状态', name:'approve' ,width:118, align:'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case '0':
                        temp = '未审批';
                        break;
                    case '1':
                        temp = '审批中';
                        break;
                    case '2':
                        temp = '审批通过';
                        break;
                }
                return temp;
            }},
            { title:'推送方式', name:'timing' ,width:128, align:'center', renderer: function (val, item, index) {
                var temp = '';
                // 2018-04-11 更新, status不为0时, 全部为已发送
                if(item.status != '0') return '已发送';
                switch (val) {
                    case '0':
                        temp = "手动";
                        break;
                    case '1':
                        temp = "自动";
                        break;
                }
                return temp;
            }},
            { title:'日期', name:'dsdate' ,width:140, align:'center'},
            { title:'操作', name:'datecreated' ,width:144, align:'center', renderer: function (val, item, index) {
                var temp = '';
                // 2018-04-11 更新, status不为0时, 当前消息为已发送, 增加查看详细按钮
                if (item.status != '0') {
                    temp = '<a href="javascript:;" class="color-green" title="查看" onclick="bulkMessage.viewPushed(\'' + index + '\')">查看</a>';
                } else if (item.approve == "2" && item.timing != "1") {
                    temp = '<a href="javascript:;" class="color-green" title="发送" onclick="bulkMessage.pushMessage(\'' + item.commsgid + '\')">发送</a>';
                } else if (item.approve == "0") {
                    temp += '<a href="javascript:;" class="color-org" title="审批" onclick="bulkMessage.approval(\'' + item.commsgid + '\')">审批</a>';
                }
                return temp;
            } }
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#bulk-message-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                indexCol: false,
                height: '440px',
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
    formReset: function () {
        var self = this;
        // clear form
        $("#create-left-columns").val($("#create-left-columns").find("option").eq(0).val());
        $("input[value='0']").prop('checked', true);
        $("input[name='isOpenComment'][value='1']").prop('checked', true);
        $(".create-left-icons").empty();
        $("#release-date").val("").hide();
        $("#create-left-title").val("");
        $("#create-left-summary").val("");
        UE.getEditor('create-left-editor').setContent("", false);
        $(".create-send-channel label input").prop('checked', false).prop('disabled', false);
        $(".bulk-message-box").hide();
        $("#pic-and-font").show();
        $("#create-left-row-textarea").val('');
        $("#create-left-upload-audio-box").empty();
        $("#create-left-upload-video-box").empty();
        // 隐藏 单图文 ‘发送预览信息’ 按钮
        $("#create-perview-btn").hide();
        // hide isOpenComment & whoCanComment 隐藏是否开启评论
        $("#isOpenComment").hide();
        $("#whoCanComment").hide();
        // hide isNormal weibo 隐藏微博类型
        $("#isNormalWeibo").hide();
        $('#weibo-headleads').hide();
        $("#headleads").val('');
        // clear cache file name and img path
        self.fileName = null;
        self.imgPath = null;
        // 清除多条缓存
        self.moreVuer.mainImg = 'images/upload_placeholder.jpg';
        self.moreVuer.mainTitle = '';
        self.moreVuer.subs = [];
        // 隐藏右侧浮动知识库和素材库
        self.vuer.isMoreLines = false;
        // 显示右侧知识库和素材库主体部分，当是多条的时候，默认隐藏
        self.vuer.moreLinesIsShow = true;
        // 解绑渠道微博渠道点击事件
        $(".create-send-channel input[value='80']").off("click.isNormalWeibo");
        // 不是已发送状态的, 还原原始状态, input和textarea设置为可编辑
        $(".create-left-btns,#create-left-upload-icon").show();
        self.editor.setEnabled();
        $(".create-left input,.create-left textarea").prop("disabled", false);
    },
    clearInput: function () {
        var self = this;
        $(".create-left-icons").empty();
        $("input[value='0']").prop('checked', true);
        $("input[name='isOpenComment'][value='1']").prop('checked', true);
        $("input[name='isNormalWeibo'][value='0']").prop('checked', true);
        $("#release-date").val("").hide();
        $("#create-left-title").val("");
        $("#create-left-summary").val("");
        UE.getEditor('create-left-editor').setContent("", false);
        $("#create-left-row-textarea").val('');
        self.editId = null;
    },
    btnClick: function () {
        var self = this;
        // 渠道选中微博的时候, 显示"微博类型"
        function bindWeiboTypeEvent() {
            $(".create-send-channel input[value='80']").on("click.isNormalWeibo", function () {
                var _isChecked = $(this).prop("checked");
                if(_isChecked) {
                    $("#isNormalWeibo").show();
                } else {
                    $("#isNormalWeibo").hide();
                }
            });
        }
        // 创建图文消息 单条
        // 2017-8月更新：单图文增加评论开关
        // 2017-10月更新：选中推送渠道为微博,并且新建/修改的是"单图文"的时候显示"微博类型"
        $("#bulkMessage-btn-picFontOne").off().on("click", function () {
            self.newsType = '03';
            parent.Common.loading(true);
            self.formReset();
            // 渠道选中微博的时候, 显示"微博类型"
            bindWeiboTypeEvent();
            // 隐藏多条创建区域
            $(".created-more").hide();
            // 显示右侧知识库
            $(".create-right").show();
            self.isAdd = true;
            $(".create-title p").text("创建图文信息（单条）");
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createOneNews();
            });
            // 隐藏创建文本部分文字输入框
            $("#create-left-row-textarea,#message-audio,#message-video").hide();
            $("#create-left-editor").show();
            $("#message-content").show();
            // 显示图文部分输入 摘要 和 图片上传
            $("#message-desc,#message-icon").show();
            // 显示是否打开评论
            $("#isOpenComment").show();
            $("#whoCanComment").show();
            // 获取群推应用 APP,微信,微博
            self.getChannel("10,20,80");
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='10']").parent('label').show();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='80']").parent('label').show();
            // 显示素材库
            self.vuer.isShow = true;
            self.vuer.select = 1;
        });
        // 创建图文消息 多条
        $("#bulkMessage-btn-picFontMore").off().on("click", function () {
            self.newsType = '06';
            parent.Common.loading(true);
            self.formReset();
            // 显示多条创建区域
            $(".created-more").show();
            self.isAdd = true;
            $(".create-title p").text("创建图文信息（多条）");
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createMoreNews();
            });
            // 隐藏创建文本部分文字输入框
            $("#create-left-row-textarea,#message-audio,#message-video").hide();
            $("#create-left-editor").show();
            $("#message-content").show();
            // 显示图文部分输入 摘要 和 图片上传
            $("#message-desc,#message-icon").show();
            // 获取群推应用 APP,微信
            self.getChannel("20");
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='20']").prop('checked', true).prop('disabled', true);
            // 显示是否打开评论
            $("#isOpenComment").show();
            $("#whoCanComment").show();
            // 显示素材库
            self.vuer.isShow = true;
            self.vuer.select = 1;
            self.vuer.isMoreLines = true;
            // 隐藏素材库主体部分
            self.vuer.moreLinesIsShow = false;
            // clear cache
            self.commsgid = null;
            self.msmscommsgid = null;
        });
        // font add 创建文本消息
        $("#bulkMessage-btn-font").off().on("click", function () {
            self.newsType = '01';
            parent.Common.loading(true);
            self.formReset();
            // 隐藏多条创建区域
            $(".created-more").hide();
            // 显示右侧知识库
            $(".create-right").show();
            self.isAdd = true;
            $(".create-title p").text("创建文本消息");
            // 隐藏创建图文部分富文本编辑器
            $("#create-left-row-textarea").show();
            $("#create-left-editor").hide();
            $("#message-desc").show();
            $("#message-icon,#message-audio,#message-video").hide();
            $("#message-content").show();
            // 获取群推应用 APP,微信
            self.getChannel("10,20,70,80");
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='10']").parent('label').show();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='70']").parent('label').show();
            $(".create-send-channel label input[value='80']").parent('label').show();
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createFontNews();
            });
            // 隐藏素材库
            self.vuer.isShow = false;
            self.vuer.select = 0;
        });
        // img add 创建图片消息
        $("#bulkMessage-btn-pic").off().on("click", function () {
            self.newsType = '02';
            parent.Common.loading(true);
            self.formReset();
            // 隐藏多条创建区域
            $(".created-more").hide();
            // 显示右侧知识库
            $(".create-right").show();
            self.isAdd = true;
            $(".create-title p").text("创建图片消息");
            // 隐藏创建图文部分富文本编辑器
            $("#create-left-row-textarea").hide();
            $("#message-content,#message-audio,#message-video").hide();
            $("#message-desc").show();
            // 显示上传图片部分
            $("#message-icon").show();
            // 获取群推应用 APP,微信,微博
            self.getChannel("10,20,80");
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='10']").parent('label').show();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='80']").parent('label').show();
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createImgNews();
            });
            // 隐藏素材库
            self.vuer.isShow = false;
            self.vuer.select = 0;
        });
        // audio add 创建音频消息
        $("#bulkMessage-btn-aduio").off().on("click", function () {
            self.newsType = '04';
            parent.Common.loading(true);
            self.formReset();
            // 隐藏多条创建区域
            $(".created-more").hide();
            // 显示右侧知识库
            $(".create-right").show();
            self.isAdd = true;
            $(".create-title p").text("创建音频消息");
            // 隐藏创建图文部分富文本编辑器，隐藏上传图片
            $("#create-left-row-textarea").hide();
            $("#message-content").hide();
            $("#message-desc").show();
            $("#message-icon,#message-video").hide();
            // 显示上传音频部分
            $("#message-audio").show();
            // 获取群推应用 微信，并且设置选中
            self.getChannel("20", true);
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='20']").prop('checked', true).prop('disabled', true);
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createAudioNews();
            });
            // 隐藏素材库
            self.vuer.isShow = false;
            self.vuer.select = 0;
        });
        // video add 创建视频消息
        $("#bulkMessage-btn-video").off().on("click", function () {
            self.newsType = '05';
            parent.Common.loading(true);
            self.formReset();
            // 隐藏多条创建区域
            $(".created-more").hide();
            // 显示右侧知识库
            $(".create-right").show();
            self.isAdd = true;
            $(".create-title p").text("创建视频消息");
            // 隐藏创建图文部分富文本编辑器
            $("#create-left-row-textarea").hide();
            $("#message-content").hide();
            $("#message-desc").show();
            $("#message-icon,#message-audio").hide();
            // 隐藏上传图片部分
            $("#message-video").show();
            // 获取群推应用 微信，并且设置选中
            self.getChannel("20", true);
            // 显示渠道
            $(".create-send-channel label").hide();
            $(".create-send-channel label input[value='20']").parent('label').show();
            $(".create-send-channel label input[value='20']").prop('checked', true).prop('disabled', true);
            // bind save event
            $("#create-save").off("click").on("click", function () {
                self.createVideoNews();
            });
            // 隐藏素材库
            self.vuer.isShow = false;
            self.vuer.select = 0;
        });
        // list del
        $("#bulkMessage-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            var ids = [];
            selected.forEach(function (item) {
                ids.push(item.commsgid);
            });
            parent.Common.dialog({
                type: "tips",
                text: "确定删除选中项？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    self.del(ids.join(','));
                }
            });
        });
        // list edit
        $("#bulkMessage-btn-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
            } else if (selected.length > 1) {
                parent.Common.editMore();
            } else {
                /**
                 case '01':'文本';
                 case '02':'图片';
                 case '03':'富文本';
                 case '04':'音频';
                 case '05':'视频';
                 case '06':'富文本多条，微信专用';
                 */
                self.editId = null;
                switch (selected[0].tsmsgtype) {
                    case '01':
                        self.newsType = '01';
                        $("#bulkMessage-btn-font").trigger("click");
                        $(".create-title p").text("修改文本消息");
                        self.isAdd = false;
                        self.editId = selected[0].commsgid;
                        // set value
                        $("input[value='" + selected[0].timing + "']").trigger("click"); // 推送方式
                        $("#release-date").val(selected[0].dsdate); // 推送方式-定时-时间
                        $("#create-left-title").val(selected[0].title); // 消息标题
                        $("#create-left-summary").val(selected[0].detail); // 消息描述
                        $("#create-left-row-textarea").val(selected[0].tsmsg); // 正文内容
                        self.editChannel = selected[0].pid;
                        // 隐藏素材库
                        self.vuer.isShow = false;
                        self.vuer.select = 0;
                        break;
                    case '02':
                        self.newsType = '02';
                        $("#bulkMessage-btn-pic").trigger("click");
                        $(".create-title p").text("修改图片消息");
                        self.isAdd = false;
                        self.editId = selected[0].commsgid;
                        // set value
                        $("input[value='" + selected[0].timing + "']").trigger("click"); // 推送方式
                        $("#release-date").val(selected[0].dsdate); // 推送方式-定时-时间
                        $("#create-left-title").val(selected[0].title); // 消息标题
                        $("#create-left-summary").val(selected[0].detail); // 消息描述
                        $("#create-left-row-textarea").val(selected[0].tsmsg); // 正文内容
                        self.editChannel = selected[0].pid;
                        self.fileName = selected[0].param2;
                        var imgBehandPath = 'downloadimg.file?filePathParam=push_msg_img&fileName=' + top.userInfo.centerid + '/',
                            imgEndPath = '&isFullUrl=true',
                            imgUrl = imgBehandPath + selected[0].param2 + imgEndPath;
                        $(".create-left-icons").empty().html('<img width="138" height="77" src="' + imgUrl + '" />');
                        // 隐藏素材库
                        self.vuer.isShow = false;
                        self.vuer.select = 0;
                        break;
                    case '03':
                        self.newsType = '03';
                        $("#bulkMessage-btn-picFontOne").trigger("click");
                        $(".create-title p").text("修改图文消息（单条）");
                        self.isAdd = false;
                        self.editId = selected[0].commsgid;
                        self.commsgid = selected[0].commsgid;
                        // set value
                        $("input[value='" + selected[0].timing + "']").trigger("click"); // 推送方式
                        $("#release-date").val(selected[0].dsdate); // 推送方式-定时-时间
                        $("#create-left-title").val(selected[0].title); // 消息标题
                        $("#create-left-summary").val(selected[0].detail); // 消息描述
                        $("#create-left-row-textarea").val(selected[0].tsmsg); // 正文内容
                        // 微博类型回显
                        $("input[name='isNormalWeibo']").eq(selected[0].isheadline).trigger("click");
                        // 微博导语回显
                        $("#headleads").val(selected[0].headleads);
                        self.editChannel = selected[0].pid;
                        self.fileName = selected[0].param2;
                        var imgBehandPath = 'downloadimg.file?filePathParam=push_msg_img&fileName=' + top.userInfo.centerid + '/',
                            imgEndPath = '&isFullUrl=true',
                            imgUrl = imgBehandPath + selected[0].param2 + imgEndPath;
                        $(".create-left-icons").empty().html('<img width="138" height="77" src="' + imgUrl + '" />');
                        // 显示素材库
                        UE.getEditor('create-left-editor').execCommand('insertHtml', selected[0].tsmsg);
                        self.vuer.isShow = true;
                        self.vuer.select = 1;
                        break;
                    case '04':
                        self.newsType = '04';
                        $("#bulkMessage-btn-aduio").trigger("click");
                        $(".create-title p").text("修改音频消息");
                        self.isAdd = false;
                        self.editId = selected[0].commsgid;
                        self.commsgid = selected[0].commsgid;
                        // set value
                        $("input[value='" + selected[0].timing + "']").trigger("click"); // 推送方式
                        $("#release-date").val(selected[0].dsdate); // 推送方式-定时-时间
                        $("#create-left-title").val(selected[0].title); // 消息标题
                        $("#create-left-summary").val(selected[0].detail); // 消息描述
                        $("#create-left-row-textarea").val(selected[0].tsmsg); // 正文内容
                        self.editChannel = selected[0].pid;
                        self.fileName = selected[0].param2;
                        var imgBehandPath = 'downloadimg.file?filePathParam=push_msg_img&fileName=' + top.userInfo.centerid + '/',
                            imgEndPath = '&isFullUrl=true',
                            imgUrl = imgBehandPath + selected[0].param2 + imgEndPath;
                        $("#create-left-upload-audio-box").empty().html('<audio src="' + imgUrl + '" controls></audio>');
                        // 显示素材库
                        self.vuer.isShow = false;
                        self.vuer.select = 0;
                        break;
                    case '05':
                        self.newsType = '05';
                        $("#bulkMessage-btn-video").trigger("click");
                        $(".create-title p").text("修改视频消息");
                        self.isAdd = false;
                        self.editId = selected[0].commsgid;
                        self.commsgid = selected[0].commsgid;
                        // set value
                        $("input[value='" + selected[0].timing + "']").trigger("click"); // 推送方式
                        $("#release-date").val(selected[0].dsdate); // 推送方式-定时-时间
                        $("#create-left-title").val(selected[0].title); // 消息标题
                        $("#create-left-summary").val(selected[0].detail); // 消息描述
                        $("#create-left-row-textarea").val(selected[0].tsmsg); // 正文内容
                        self.editChannel = selected[0].pid;
                        self.fileName = selected[0].param2;
                        var imgBehandPath = 'downloadimg.file?filePathParam=push_msg_img&fileName=' + top.userInfo.centerid + '/',
                            imgEndPath = '&isFullUrl=true',
                            imgUrl = imgBehandPath + selected[0].param2 + imgEndPath;
                        $("#create-left-upload-video-box").empty().html('<video width="320" height="200" src="' + imgUrl + '" controls></video>');
                        // 显示素材库
                        self.vuer.isShow = false;
                        self.vuer.select = 0;
                        break;
                    case '06':
                        self.newsType = '06';
                        $("#bulkMessage-btn-picFontMore").trigger("click");
                        $(".create-title p").text("修改图文信息（多条）");
                        self.isAdd = false;
                        self.commsgid = selected[0].commsgid;
                        self.queryTextImage(selected[0].commsgid);
                        // 显示素材库
                        self.vuer.isShow = true;
                        self.vuer.select = 1;
                        // 隐藏素材库主体部分
                        self.vuer.moreLinesIsShow = false;
                        break;
                }
                if(selected[0].status != '0') {
                    $(".create-left-btns,#create-left-upload-icon").hide();
                    self.editor.setDisabled();
                    $(".create-left input,.create-left textarea").prop("disabled", true);
                } else {
                    $(".create-left-btns,#create-left-upload-icon").show();
                    self.editor.setEnabled();
                    $(".create-left input,.create-left textarea").prop("disabled", false);
                }
            }
        });
        // 预览
        $("#create-mobile").off().on("click", function () {
            var myDate = new Date();
            var nowDate = myDate.toLocaleDateString(),
                centerName = parent.userInfo.centerName,
                mobile = $(".mobile").html();
            parent.Common.popupShow(mobile);
            parent.$(".message-title").text(centerName);
            parent.$(".mobile-content").html(UE.getEditor('create-left-editor').getContent());
            
            switch (self.newsType) {
                case '01':
                    // 文本
                    parent.$(".popup-preview-fontImageOne").hide();
                    parent.$(".popup-preview-fontImageMore").hide();
                    parent.$(".popup-preview-font").show().find('.mobile-content').html('<div id="mobile-font-wechat"></div>' +
                        '<div id="mobile-font-sms"></div>' +
                        '<div id="mobile-font-weibo"></div>' +
                        '<div id="mobile-font-app"></div>');
                    parent.$(".popup-preview-img").hide();
                    parent.$(".mobile-popup-btns a").eq(0).hide();
                    parent.$(".mobile-popup-btns a").eq(1).hide();
                    parent.$("#popup-app").addClass("on");
                    parent.$(".mobile-fixed").removeClass('popup-wechat popup-sms popup-weibo').addClass("popup-app");
                    parent.$(".mobile-content").show();
                    parent.$(".mobile-content").find("#mobile-font-wechat, #mobile-font-sms, #mobile-font-weibo, #mobile-font-app").html($("#create-left-row-textarea").val());
                    parent.$("#mobile-font-app").show();
                    parent.$(".message-title").text($("#create-left-title").val());
                    break;
                case '02':
                    // 图片
                    parent.$(".popup-preview-fontImageOne").hide();
                    parent.$(".popup-preview-fontImageMore").hide();
                    parent.$(".popup-preview-font").hide();
                    parent.$(".popup-preview-img").show();
                    parent.$(".mobile-popup-btns a").eq(0).hide();
                    parent.$(".mobile-popup-btns a").eq(1).hide();
                    // 隐藏文本消息四个渠道的预览按钮
                    parent.$("#popup-app, #popup-wechat, #popup-sms, #popup-weibo").hide();
                    parent.$(".mobile-content").html($(".create-left-icons").html()).show();
                    break;
                case '03':
                    // 富文本
                    parent.$(".popup-preview-fontImageOne").show();
                    parent.$(".popup-preview-fontImageMore").hide();
                    parent.$(".popup-preview-font").hide();
                    parent.$(".popup-preview-img").hide();
                    parent.$(".mobile-popup-btns").eq(1).show();
                    // 隐藏文本消息四个渠道的预览按钮
                    parent.$("#popup-app, #popup-wechat, #popup-sms, #popup-weibo").hide();

                    var _html = '<h3>'+$("#create-left-title").val()+'</h3>';
                    _html += '<span class="nd">'+nowDate+'</span><span class="cn">'+centerName+'</span>';
                    _html += UE.getEditor('create-left-editor').getContent();
                    parent.$(".mobile-content").html(_html);
                    parent.$(".mobile-content").hide();

                    var temp = '<li class="mobile-message-list-main"><img src="' + $(".create-left-icons img").attr('src') + '" alt="" width="100%" height="95"><p>' + $("#create-left-title").val() + '</p></li>';
                    parent.$("#popup-preview-fontImageOne").empty().html(temp);
                    break;
                case '04':
                    // 音频
                    parent.$(".popup-preview-fontImageOne").hide();
                    parent.$(".popup-preview-fontImageMore").hide();
                    parent.$(".popup-preview-font").show().find('.mobile-content').html('<div id="mobile-font-wechat"></div>');
                    parent.$(".popup-preview-img").hide();
                    parent.$(".mobile-popup-btns a").hide();
                    parent.$("#popup-wechat, #popup-close").show();
                    parent.$("#popup-wechat").addClass("on");
                    parent.$(".mobile-fixed").removeClass('popup-app popup-sms popup-weibo').addClass("popup-wechat");
                    parent.$(".mobile-content").show();
                    var audioTag = $("#create-left-upload-audio-box"),
                        parentAudioTag = parent.$("#mobile-font-wechat");
                    parentAudioTag.html(audioTag.html());
                    parentAudioTag.addClass('wechat-audio-show');
                    if(audioTag.find('audio').length > 0) {
                        parentAudioTag.append(Math.ceil(audioTag.find('audio')[0].duration) + '\'\'');
                        parentAudioTag.off().on("click", function () {
                            if(parentAudioTag.hasClass("run-play")) {
                                parentAudioTag.removeClass('run-play');
                                parentAudioTag.find('audio')[0].pause();
                            } else {
                                parentAudioTag.addClass('run-play');
                                parentAudioTag.find('audio')[0].play();
                            }
                        });
                        parentAudioTag.find('audio').on('ended',function () {
                            parentAudioTag.removeClass('run-play');
                        });
                    }
                    parent.$("#mobile-font-wechat").show();
                    parent.$("#mobile-font-wechat,#mobile-font-sms,#mobile-font-weibo,#mobile-font-app").hide();
                    parent.$("#mobile-font-wechat").show();
                    var centerName = parent.userInfo.centerName;
                    parent.$(".message-title").text(centerName);
                    break;
                case '05':
                    // 视频
                    parent.$(".popup-preview-fontImageOne").hide();
                    parent.$(".popup-preview-fontImageMore").hide();
                    parent.$(".popup-preview-font").hide();
                    parent.$(".popup-preview-img").show();
                    parent.$(".mobile-popup-btns a").eq(0).hide();
                    parent.$(".mobile-popup-btns a").eq(1).hide();
                    // 隐藏文本消息四个渠道的预览按钮
                    parent.$("#popup-app, #popup-sms, #popup-weibo").hide();
                    parent.$("#popup-wechat").addClass("on");
                    var showMobCon = parent.$(".mobile-content");
                    showMobCon.html("<h1 style='text-align:center;font-size:16px'>" + $("#create-left-title").val() + "</h1>");
                    showMobCon.append("<p style='font-size:13px;padding:14px 0'>" + $("#create-left-summary").val() + "</p>");
                    showMobCon.append($("#create-left-upload-video-box").html());
                    showMobCon.find("video").css('width', "244px");
                    break;
                case '06':
                    // 多条图文
                    parent.$(".popup-preview-fontImageOne").hide();
                    parent.$(".popup-preview-fontImageMore").show();
                    parent.$(".popup-preview-font").hide();
                    parent.$(".popup-preview-img").hide();
                    parent.$(".mobile-popup-btns a").eq(1).show();
                    // 隐藏文本消息四个渠道的预览按钮
                    parent.$("#popup-app, #popup-wechat, #popup-sms, #popup-weibo").hide();
                    var _html = '<h3>'+$("#create-left-title").val()+'</h3>';
                    _html += '<span class="nd">'+nowDate+'</span><span class="cn">'+centerName+'</span>';
                    if(self.moreVuer.subs.length > 0) {
                        _html += self.moreVuer.subs[self.indexForSort].tsmsg;
                    } else {
                        _html += UE.getEditor('create-left-editor').getContent();
                    }
                    parent.$(".mobile-content").html(_html);
                    parent.$(".mobile-content").hide();
                    var temp = '<li class="mobile-message-list-main"><img src="' + self.moreVuer.imgBehandPath + self.moreVuer.mainImg + self.moreVuer.imgEndPath + '" alt="" width="100%" height="95"><p>' + self.moreVuer.mainTitle + '</p></li>';
                    self.moreVuer.subs.forEach(function (item, index) {
                        if(index != 0) {
                            temp += '<li><p>' + item.title + '</p><img src="' + self.moreVuer.imgBehandPath + item.param2 + self.moreVuer.imgEndPath + '" alt="" width="40" height="40"></li>';
                        }
                    });
                    parent.$("#popup-preview-fontImageMore").empty().html(temp);
                    break;
            }
            // 绑定弹出切换事件
            self.popupBtnClick();
        });
        // 返回
        $(".create-title a").off().on("click", function () {
            $(".bulk-message-box").show();
            $("#pic-and-font").hide();
        });
        // 单图文 发送预览信息
        $("#create-perview-btn").off().on("click", function () {
            self.moreVuer.getUserList();
        });
        // bind date picker
        laydate({
            elem: '#release-date',
            format: 'YYYY-MM-DD hh:mm:ss', // 该例子表示只显示年月
            festival: true, //显示节日
            istime: true,
            choose: function(datas){ //选择日期完毕的回调
            }
        });
        laydate.skin('huanglv');
        // create-send-channel
        $(".create-send-channel input[type='checkbox']").on("change", function () {
            var selected = $(".create-send-channel input[type='checkbox']:checked"),
                selectedChanel = [],
                pidArr = [];
            for(var i = 0; i < selected.length; i++) {
                selectedChanel.push(selected.eq(i).val());
            }
            self.channelArr.forEach(function (item) {
                if($.inArray(item.channel, selectedChanel) > -1) {
                    pidArr.push(item.pid);
                }
            });
            self.selectedChannel = pidArr.join(',');
        });
    },
    createEditor: function () {
        var self = this;
        self.editor = UE.getEditor('create-left-editor');
    },
    popupBtnClick: function () {
        parent.$(".mobile-popup-btns a").off().on("click", function () {
            var _this = $(this),
                _id = $(this).attr('id');
            switch (_id) {
                case 'popup-list':
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".mobile-message-list").show();
                    parent.$(".mobile-content").hide();
                    break;
                case 'popup-view':
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".mobile-message-list").hide();
                    parent.$(".mobile-content").show();
                    break;
                case 'popup-close':
                    parent.Common.popupClose();
                    break;
                case 'popup-app':
                    parent.$(".mobile-fixed").removeClass('popup-wechat popup-sms popup-weibo').addClass("popup-app");
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$("#mobile-font-wechat,#mobile-font-sms,#mobile-font-weibo,#mobile-font-app").hide();
                    parent.$("#mobile-font-app").show();
                    parent.$(".message-title").text($("#create-left-title").val());
                    break;
                case 'popup-wechat':
                    parent.$(".mobile-fixed").removeClass('popup-app popup-sms popup-weibo').addClass("popup-wechat");
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$("#mobile-font-wechat,#mobile-font-sms,#mobile-font-weibo,#mobile-font-app").hide();
                    parent.$("#mobile-font-wechat").show();
                    var centerName = parent.userInfo.centerName;
                    parent.$(".message-title").text(centerName);
                    break;
                case 'popup-sms':
                    parent.$(".mobile-fixed").removeClass('popup-wechat popup-app popup-weibo').addClass("popup-sms");
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$("#mobile-font-wechat,#mobile-font-sms,#mobile-font-weibo,#mobile-font-app").hide();
                    parent.$("#mobile-font-sms").show();
                    var centerName = parent.userInfo.centerName;
                    parent.$(".message-title").text(centerName);
                    break;
                case 'popup-weibo':
                    parent.$(".mobile-fixed").removeClass('popup-wechat popup-sms popup-app').addClass("popup-weibo");
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$("#mobile-font-wechat,#mobile-font-sms,#mobile-font-weibo,#mobile-font-app").hide();
                    parent.$("#mobile-font-weibo").show();
                    var centerName = parent.userInfo.centerName;
                    parent.$(".message-title").text(centerName);
                    break;
            }
        });
    },
    createVue: function () {
        // 创建/修改 部分右侧vue
        var self = this;
        self.vuer = new Vue({
            el: '.create-right',
            data: {
                groups: [],
                currentGroup: null,
                currentGroupId: null,
                materials: [],
                selectedMaterials: [],
                selectedAll: false,
                isShow: true, // 是否隐藏素材
                select: 1, // 右侧知识库和素材，0：知识库 1：素材
                isMoreLines: false,  // 是否是多条图文
                moreLinesIsShow: false, // 多条的时候 右侧是否展开
                searchResult: [],
                searchText: ''
            },
            methods: {
                getMaterial: function (group) {
                    parent.Common.loading(true);
                    this.currentGroup = group;
                    this.currentGroupId = group.groupid;
                    self.getMaterial(group.groupid, 0, self.pageSize, true);
                    this.searchText = '';
                },
                changeTag: function (index) {
                    if(this.isMoreLines) {
                        this.moreLinesIsShow = true;
                    }
                    this.select = index;
                },
                closeRight: function () {
                    this.moreLinesIsShow = false;
                },
                insetHtml: function (material) {
                    var tempHtml = '';
                    switch (material.materialtype) {
                        case "image":
                            tempHtml = '<img src="' + material.picurl + '" />';
                            break;
                        case "flash":
                            tempHtml = '<object width="135" height="135" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=4,0,2,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"><param value="' + material.picurl + '" name="movie"><param value="high" name="quality"><param value="transparent" name="wmode"><param value="exactfit" name="SCALE"><embed width="135" height="135" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"quality="high" src="' + material.picurl + '">';
                            break;
                        case "media":
                            tempHtml = '<audio src="' + material.picurl + '" controls></audio>';
                            break;
                        case "video":
                            tempHtml = '<video src="' + material.picurl + '" controls></video>';
                            break;
                        case "file":
                            tempHtml = '<a href="' + material.picurl + '">下载文件</a>';
                            break;
                        default:
                            tempHtml = '<img src="' + material.picurl + '" />';
                            break;
                    }
                    UE.getEditor('create-left-editor').execCommand('insertHtml', tempHtml);
                },
                search: function () {
                    var _this = this,
                        _input = _this.searchText,
                        arr = [],
                        reg = new RegExp(_input.replace('\.', '\\\.'), 'igm');
                    if(_input == '') {
                        _this.reset();
                        return;
                    }
                    self.materials.forEach(function (item) {
                        if(reg.test(item.realname)) {
                            item.realname = item.realname.replace(reg, '$&');
                            arr.push(item);
                        }
                    });
                    _this.searchResult = arr;
                },
                reset: function () {
                    parent.Common.loading(true);
                    self.getGroupList();
                    this.searchText = '';
                }
            }
        });
        self.getGroupList();
    },
    createMoreVue: function () {
        // 多条图文信息 左侧
        var self = this;
        self.moreVuer = new Vue({
            el: '.created-more',
            data: {
                mainImg: 'images/upload_placeholder.jpg',
                mainTitle: '',
                subs: [],
                imgBehandPath: 'downloadimg.file?filePathParam=push_msg_img&fileName=' + top.userInfo.centerid + '/',
                imgEndPath: '&isFullUrl=true',
                selected: '',
                indexForSort: '',
                canUp: true,
                canDown: true
            },
            methods: {
                edit: function (item, index) {
                    var _this = this;
                    self.commsgid = item.commsgid;
                    _this.selected = item.msmscommsgid;
                    self.msmscommsgid = item.msmscommsgid;
                    _this.indexForSort = index;
                   self.indexForSort = index;
                    // for sort
                    if (index == 0) {
                        _this.canUp = false;
                    } else {
                        _this.canUp = true;
                    }
                    if (index == _this.subs.length - 1) {
                        _this.canDown = false;
                    } else {
                        _this.canDown = true;
                    }
                    $("#create-left-title").val(item.title);
                    $("#create-left-summary").val(item.detail);
                    $("input[name='type'][value='" + item.timing + "']").prop('checked', true);
                    if(item.timing == "1") {
                        $("#release-date").show();
                    } else {
                        $("#release-date").hide();
                    }
                    $("#release-date").val(item.dsdate);
                    // 评论
                    $("input[name='isOpenComment'][value='" + item.freeuse4 + "']").prop('checked', true);
                    if(item.freeuse4 == "1") {
                        $("#whoCanComment").show();
                    } else {
                        $("#whoCanComment").hide();
                    }
                    $("input[name='whoCanComment'][value='" + item.freeuse3 + "']").prop('checked', true);
                    UE.getEditor('create-left-editor').setContent("", false);
                    UE.getEditor('create-left-editor').execCommand('insertHtml', item.tsmsg);
                    $(".create-left-icons").empty().append('<img width="138" height="77" src="' + _this.imgBehandPath + item.param2 + _this.imgEndPath + '" />');
                    self.fileName = item.param2;
                },
                clearForm: function () {
                    var _this = this;
                    self.clearInput();
                    self.msmscommsgid = null;
                    _this.selected = '';
                    _this.indexForSort = '';
                },
                moveUp: function () {
                    // 排序上移
                    var _this = this;
                    if (_this.indexForSort > 0) {
                        _this.indexForSort--;
                        _this.subs = an.arrayExchangeOrder(_this.subs, _this.indexForSort + 1, _this.indexForSort);
                    }
                    if (_this.indexForSort == 0) {
                        _this.canUp = false;
                        _this.canDown = true;
                    } else {
                        _this.canUp = true;
                    }
                },
                moveDown: function () {
                    // 排序下移
                    var _this = this;
                    if (_this.indexForSort < _this.subs.length - 1) {
                        _this.indexForSort++;
                        _this.subs = an.arrayExchangeOrder(_this.subs, _this.indexForSort - 1, _this.indexForSort);
                    }
                    if (_this.indexForSort == _this.subs.length - 1) {
                        _this.canDown = false;
                        _this.canUp = true;
                    } else {
                        _this.canDown = true;
                    }
                },
                saveList: function () {
                    var _this = this,
                        sortArr = [],
                        index = 1;
                    _this.subs.forEach(function (item) {
                        sortArr.push({
                            'seqid': item.msmscommsgid,
                            'num': index++
                        });
                    });
                    self.saveList(JSON.stringify(sortArr));
                },
                subDel: function () {
                    self.delItems(this.selected);
                },
                getUserList: function () {
                    /**
                     * 发送预览信息，先获取用户列表
                     * */
                    var channelUserManagement = $(".select-user").html();
                    parent.Common.popupShow(channelUserManagement);
                    parent.$("#select-user").append($("#user-list-html-template").html()).append($("#user-list-js-template").val());
                }
            }
        });
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
                    data.groupList.forEach(function (item) {
                        item.materials = [];
                    });
                    self.vuer.groups = data.groupList;
                    self.getMaterial(data.groupList[0].groupid);
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
    getMaterial: function (groupid) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi13003.json",
            data: { 'groupid': groupid, 'page': 0, 'num': '10000' },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.vuer.groups.forEach(function (group) {
                        if(group.groupid == data.rows[0].groupid) {
                            group.materials = data.rows;
                        }
                    });
                    self.vuer.currentGroupId = groupid;
                    self.vuer.materials = data.rows;
                    self.vuer.searchResult = data.rows;
                    self.materials = data.rows;
                    self.vuer.selectedMaterials = [];
                } else {
                    parent.Common.loading(false);
                    self.vuer.materials = [];
                    self.vuer.searchResult = [];
                    self.materials = [];
                    self.vuer.selectedMaterials = [];
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
    createUpload: function () {
        var self = this;
        /**
         *  绑定图片上传
         * */
        var oBtn = document.getElementById("create-left-upload-icon");
        new AjaxUpload(oBtn, {
            action: "./webapi30201_uploadimg.do",
            name: 'file',
            data: {
                'centerid': top.userInfo.centerid
            },
            onSubmit: function (file,ext) {
                if(ext && /^(jpg)$/.test(ext)){
                    //ext是后缀名
                    //oBtn.disabled = "disabled";
                } else {
                    parent.Common.dialog({
                        type: "warning",
                        text: "上传格式错误，素材格式只能是jpg！",
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
                    if(response.recode == '000000') {
                        $(".create-left-icons").empty().append('<img width="138" height="77" src="' + response.downloadPath + '" />');
                        self.fileName = response.fielName;
                        self.imgPath = response.downloadPath;
                    } else {
                        parent.Common.dialog({
                            type: "error",
                            text: response.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    }
                } catch (e) {
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
        /**
         *  绑定音频上传
         * */
        var aBtn = document.getElementById("create-left-upload-audio");
        new AjaxUpload(aBtn, {
            action: "./webapi30201_uploadAudio.do",
            name: 'file',
            data: {
                'centerid': top.userInfo.centerid
            },
            onSubmit: function (file,ext) {
                if(ext && /^(amr||mp3)$/.test(ext)){
                    //ext是后缀名
                    //oBtn.disabled = "disabled";
                } else {
                    parent.Common.dialog({
                        type: "warning",
                        text: "上传格式错误，音频格式只能是AMR或者MP3！",
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
                    if(response.recode == '000000') {
                        $("#create-left-upload-audio-box").empty().append('<audio width="320" height="32" src="' + response.downloadPath + '" controls></audio>');
                        self.fileName = response.fielName;
                        self.imgPath = response.downloadPath;
                    } else {
                        parent.Common.dialog({
                            type: "error",
                            text: response.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    }
                } catch (e) {
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
        /**
         *  绑定视频上传
         * */
        var vBtn = document.getElementById("create-left-upload-video");
        new AjaxUpload(vBtn, {
            action: "./webapi30201_uploadVideo.do",
            name: 'file',
            data: {
                'centerid': top.userInfo.centerid
            },
            onSubmit: function (file,ext) {
                if(ext && /^(mp4)$/.test(ext)){
                    //ext是后缀名
                    //oBtn.disabled = "disabled";
                } else {
                    parent.Common.dialog({
                        type: "warning",
                        text: "上传格式错误，视频格式只能是MP4！",
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
                    if(response.recode == '000000') {
                        $("#create-left-upload-video-box").empty().append('<video width="320" height="200" src="' + response.downloadPath + '" controls></video>');
                        self.fileName = response.fielName;
                        self.imgPath = response.downloadPath;
                    } else {
                        parent.Common.dialog({
                            type: "error",
                            text: response.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                    }
                } catch (e) {
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
    },
    pushMessage: function (commsgid) {
        // 推送公共短消息
        parent.Common.loading(true);
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=groupSend",
            data: {
                'centerid': top.userInfo.centerid,
                'commsgid': commsgid
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
                        ok: function () {
                            self.getTable(true);
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
    approval: function (commsgid) {
        // 审批
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=auth",
            data: { 'commsgid': commsgid },
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
                        ok: function () {
                            self.getTable(true);
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
    createOneNews: function () {
        // 创建图文消息 单条
        var self = this;
        if (self.fileName == null) {
            parent.Common.dialog({
                type: "warning",
                text: "请上传图片！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        var tsmsg = UE.getEditor('create-left-editor').getContent(), // 正文内容
            commsgid = '',
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息摘要
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            isOpenComment = $("input[name='isOpenComment']:checked").val(), // 是否打开评论
            whoCanComment = $("input[name='whoCanComment']:checked").val(), // 是否打开评论
            isNormalWeibo = $("input[name='isNormalWeibo']:checked").val(), // 是普通微博还是头条文章
            headleads = $("#headleads").val(); // 微博头条导语
        if(isNormalWeibo == '1' && title.length > 32) {
            parent.Common.dialog({
                type: "warning",
                text: "微博头条文章标题限定在32字以内, 请删减后重试！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
            //theme = $("#create-left-columns").val(); // 消息主题
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
            url = "./webapi30201.json?method=addText";
        } else {
            commsgid = self.commsgid;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'centerid': top.userInfo.centerid,
                'tsmsgtype': '03',
                'tsmsg': tsmsg,
                'timing': timing,
                'dsdate': dsdate,
                'userid': top.userInfo.loginid,
                'title': title,
                'detail': detail,
                'pid': self.selectedChannel, // 渠道应用
                'param2': self.fileName,
                'msgsource': 10,
                'freeuse4': isOpenComment,
                'freeuse3': whoCanComment,
                'isheadline': isNormalWeibo,
                'headleads': headleads
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
    createMoreNews: function () {
        // 创建图文消息 多条
        var self = this;
        var commsgid = self.commsgid == null ? '' : self.commsgid,
            msmscommsgid = self.msmscommsgid == null ? '' : self.msmscommsgid,
            tsmsg = UE.getEditor('create-left-editor').getContent(), // 正文内容
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息摘要
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            isOpenComment = $("input[name='isOpenComment']:checked").val(), // 是否打开评论
            whoCanComment = $("input[name='whoCanComment']:checked").val(); // 谁可以评论
            //theme = $("#create-left-columns").val(); // 消息主题
        var pids = [];
        self.channelArr.forEach(function (item) {
            pids.push(item.pid);
        });
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
        }
        url = "./webapi30201.json?method=addTextImage";
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'msmscommsgid': msmscommsgid,
                'centerid': top.userInfo.centerid,
                'tsmsg': tsmsg,
                'timing': timing,
                'dsdate': dsdate,
                'userid': top.userInfo.loginid,
                'title': title,
                'detail': detail,
                'pid': pids.join(','), // 渠道应用
                'param2': self.fileName,
                'msgsource': 10,
                'freeuse4': isOpenComment,
                'freeuse3': whoCanComment
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.queryTextImage(data.commsgid);
                    self.getTable(true);
                    parent.Common.dialog({
                        type: "success",
                        text: data.msg,
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.commsgid = data.commsgid;
                            self.clearInput();
                            self.msmscommsgid = null;
                            self.moreVuer.selected = '';
                            if(commsgid == '') {
                            }
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
    queryTextImage: function (id) {
        /**
         * 查询图文多条
         * */
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=queryTextImage",
            data: {
                'centerid': top.userInfo.centerid,
                'commsgid': id
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    self.moreVuer.subs = data.rows;
                    self.moreVuer.mainImg = data.rows[0].param2;
                    self.moreVuer.mainTitle = data.rows[0].title;
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
    createFontNews: function () {
        // 创建文本信息
        var self = this;
        var //theme = $("#create-left-columns").val(), // 消息主题
            commsgid = '',
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息描述
            tsmsg = $("#create-left-row-textarea").val(); // 正文内容
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
            url = "./webapi30201.json?method=addText";
        } else {
            commsgid = self.editId;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'centerid': top.userInfo.centerid,
                'tsmsgtype': '01',
                //'theme': theme,
                'pid': self.selectedChannel, // 渠道应用
                'timing': timing,
                'dsdate': dsdate,
                'title': title,
                'detail': detail,
                'tsmsg': tsmsg,
                'userid': top.userInfo.loginid,
                'msgsource': 10
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
    createImgNews: function () {
        // 创建图片信息
        var self = this;
        var //theme = $("#create-left-columns").val(), // 消息主题
            commsgid = '',
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息描述
            tsmsg = $("#create-left-row-textarea").val(); // 正文内容
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
            url = "./webapi30201.json?method=addImage";
        } else {
            commsgid = self.editId;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'centerid': top.userInfo.centerid,
                //'theme': theme,
                'pid': self.selectedChannel, // 渠道应用
                'timing': timing,
                'dsdate': dsdate,
                'title': title,
                'detail': detail,
                'tsmsg': tsmsg,
                'param2': self.fileName,
                'userid': top.userInfo.loginid,
                'msgsource': 10
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
    createAudioNews: function () {
        // 创建音频信息
        var self = this;
        var commsgid = '',
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息描述
            tsmsg = $("#create-left-row-textarea").val(); // 正文内容
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
            url = "./webapi30201.json?method=addAudio";
            var pids = [];
            self.channelArr.forEach(function (item) {
                pids.push(item.pid);
            });
            self.selectedChannel = pids.join(",");
        } else {
            commsgid = self.editId;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'centerid': top.userInfo.centerid,
                'pid': self.selectedChannel, // 渠道应用
                'timing': timing,
                'dsdate': dsdate,
                'title': title,
                'detail': detail,
                'tsmsg': tsmsg,
                'param2': self.fileName,
                'userid': top.userInfo.loginid,
                'msgsource': 10
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    createVideoNews: function () {
        // 创建视频信息
        var self = this;
        var commsgid = '',
            timing = $("input[name='type']:checked").val(), // 推送方式
            dsdate = $("#release-date").val(), // 推送方式-定时-时间
            title = $("#create-left-title").val(), // 消息标题
            detail = $("#create-left-summary").val(), // 消息描述
            tsmsg = $("#create-left-row-textarea").val(); // 正文内容
        var url = './webapi30201.json?method=edit';
        if(self.isAdd) {
            url = "./webapi30201.json?method=addVideo";
            var pids = [];
            self.channelArr.forEach(function (item) {
                pids.push(item.pid);
            });
            self.selectedChannel = pids.join(",");
        } else {
            commsgid = self.editId;
        }
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: url,
            data: {
                'commsgid': commsgid,
                'centerid': top.userInfo.centerid,
                'pid': self.selectedChannel, // 渠道应用
                'timing': timing,
                'dsdate': dsdate,
                'title': title,
                'detail': detail,
                'tsmsg': tsmsg,
                'param2': self.fileName,
                'userid': top.userInfo.loginid,
                'msgsource': 10
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    },
    del: function (ids) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=delete",
            data: {
                'listCommsgid': ids
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
                        ok: function () {
                            $(".create-title a").trigger("click");
                            self.getTable(true);
                            parent.Common.loading(true);
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
    delItems: function (id) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30202.json?method=deletems",
            data: {
                'msmscommsgid': id
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
                        ok: function () {
                            self.moreVuer.clearForm();
                            self.queryTextImage(self.commsgid);
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
    saveList: function (sortStr) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=orderbynum",
            data: {
                'datalist': sortStr
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
                        ok: function () {
                            self.moreVuer.clearForm();
                            self.queryTextImage(self.commsgid);
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
    sendPerview: function (certinum) {
        /**
         * 选择用户后，发送预览消息请求
         *
         * @param certinum {String} 身份证号
         */
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi30201.json?method=perviewSend",
            datatype: "json",
            data: {
                centerid: top.userInfo.centerid,
                commsgid: self.commsgid,
                certinum: certinum
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
    /**
     * 2018-04-11 新增, 已推送消息查看详细, 调用修改按钮点击事件
     * */
    viewPushed: function (rowIndex) {
        var self = this;
        // 全部取消选择, 并选中当前
        self.tabler.deselect("all");
        self.tabler.select(rowIndex);
        setTimeout(function(){
            $("#bulkMessage-btn-edit").trigger("click");
        }, 0);
    }
};
// 消息群发
bulkMessage.createPager();