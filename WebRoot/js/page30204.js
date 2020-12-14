/**
 * Created by M on 2016/9/24.
 * Updated by an on 2017/8/28.
 */

var user = top.userInfo;

var sendlist1 = {
    startTime: null,
    endTime: null,
    pageSize: 10,
    pager: null,
    tabler:null,
    pager2: null,
    tabler2:null,
    flag:0,
    isMass: false, // 记录当前查询的是不是群推
    isWeChat: false, // 点击第二个table时更改，判断是微信渠道的才可以“打开关闭评论”和“修改”
    thdCommsgid: null, // 查询第三个table的缓存条件，方便 开关评论和删除已推数据 刷新列表用
    thdPid: null, // 查询第三个table的缓存条件，方便 开关评论和删除已推数据 刷新列表用
    createDatepicker: function () {
        var self = this;
        laydate({
            elem: '#startTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate({
            elem: '#endTime',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.endTime = datas;
            }
        });
        laydate.skin('huanglv');
        return self;
    },
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#first-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTableData(pageIndex, pageSize, false);
            }
        });
        self.getTableData( 1, self.pageSize, true);
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
            page = page,
            rows = rows,
            pusMessageType = $('#msgtype').val(),
            msgsource = $('#msgsource').val(),
            isToday = $("#isToday").prop('checked') ? 1 : 0;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi30203.json?method=query',
            data: { 
                'centerid': user.centerid,
                'page': page, 
                'rows': rows,
                'pusMessageType': pusMessageType,
                'startdate': self.startTime,
                'enddate': self.endTime,
                'isToday': isToday
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == "string") {
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
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'id' ,width:30, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'消息标题', name:'title' ,width:80, align: 'center'},
            { title:'消息总条数', name:'successnum' ,width:70, align: 'center'},
            /*{ title:'业务渠道来源', name:'msgsource' ,width:100, align: 'center', renderer: function (val, item, index) {
                if(val == '10'){
                    return '综合服务平台';
                }
                if(val == '20'){
                    return '业务系统';
                }
                if(val == '30'){
                    return '渠道';
                }
            }},*/
            { title:'业务日期', name:'datecreated' ,width:243, align: 'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#first-table').mmGrid({
                    multiSelect: false,
                    checkCol: false, 
                    height: 'auto',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        self.tabler.off("cellSelected").on("cellSelected", function (e, item, rowIndex, colIndex) {
            $(".netManagement-top").show();
            $(".netManagement-bottom").hide();
            // 判断类型是不是群推，是群推设置isMass为true
            self.isMass = item.pusMessageType == '01' ? true : false;
            self.getSecTableData(1,10,true,item.commsgid);
            if(self.flag > 1){
                self.createThdTable([]);
                self.pager3.reset({
                    itemLength: 0,
                    pageSize: 0,
                    reset: true
                });
            }
            
        });
    },
    createPager2: function () {
        var self = this;
        // create pages
        self.pager2 = pages({
            el: "#second-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getSecTableData(pageIndex, pageSize, false,'');
            }
        });
        // self.getSecTableData( 1, self.pageSize, true,'');
    },
    getSecTableData: function (page,rows,pageRestBool,commsgid) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi30203.json?method=queryTwo',
            data: { 
                'centerId':user.centerid,
                'commsgid': commsgid,
                'page': page, 
                'rows': rows
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createSecTable(data.rows);
                    self.pager2.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
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
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createSecTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'id' ,width:66, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'发送人', name:'username' ,width:160, align: 'center', renderer: function (val, item) {
                var temp = '';
                if (self.isMass) {
                    temp = "所有人";
                } else {
                    temp = val;
                }
                return temp;
            }},
            { title:'证件号码', name:'freeuse2' ,width:338, align: 'center', renderer: function (val, item) {
                var temp = '';
                if (self.isMass) {
                    temp = "--";
                } else {
                    temp = val;
                }
                return temp;
            }},
        ];
        if(self.tabler2 != null) {
            self.tabler2.load(data);
        } else {
            self.tabler2 = $('#second-table').mmGrid({
                    multiSelect: false,
                    checkCol: false,
                    height: '225',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }

        self.tabler2.off("cellSelected").on("cellSelected", function (e, item, rowIndex, colIndex) {
            if (item.freeuse1 == "官方微信") {
                self.isWeChat = true;
            } else {
                self.isWeChat = false;
            }
            self.getThdTableData( 1, self.pageSize, true,item.commsgid,item.pid);
            $('.netManagement-bottom').show();
        });
    },
    createPager3: function (commsgid,pid) {
        var self = this;
        self.pager3 = pages({
            el: "#third-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getThdTableData(pageIndex, pageSize, false,commsgid,pid);
            }
        });
        self.getThdTableData( 1, self.pageSize, true,'1728','80000134');
    },
    getThdTableData: function (page,rows,pageRestBool,commsgid,pid) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi3020401.json?method=query',
            data: { 
                'centerId':user.centerid,
                'commsgid':commsgid,
                'pid':pid,
                'page': page, 
                'rows': rows
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createThdTable(data.rows);
                    self.pager3.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                    // cache the query
                    self.thdCommsgid = commsgid;
                    self.thdPid = pid;
                } else {
                    if(data.recode != "280001"){
                        parent.Common.loading(false);
                        parent.Common.dialog({
                            type: "error",
                            text: data.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {}
                        });
                    } else {
						self.createThdTable([]);
						self.pager3.reset({
							itemLength: data.total,
							pageSize: self.pageSize,
							reset: pageRestBool
						});
					}                    
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createThdTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'id' ,width:30, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'消息标题', name:'title' ,width:200, align: 'center'},
            { title:'详细类型', name:'tsmsgtype' ,width:123, align: 'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '文本消息';
                        break;
                    case '02':
                        temp = '图片消息';
                        break;
                    case '03':
                        temp = '富文本消息';
                        break;
                    case '04':
                        temp = '音频消息';
                        break;
                    case '05':
                        temp = '视频消息';
                        break;
                    case '06':
                        temp = '富文本多条，微信专用';
                        break;
                }
                return temp;
            }},
            { title:'推送渠道', name:'freeuse1' ,width:120, align: 'center'},
            { title:'操作', name:'tsmsg' ,width:71, align: 'center', renderer: function (val, item, index) {
                var temp = '-';
                if(item.tsmsgtype == '06'){
                    // 多图文
                    temp = "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.jumpToNext(" + JSON.stringify(item) + ", $(this));' title='详情'>详情</a>";
                } else if(item.tsmsgtype == '03') {
                    // 单图文
                    if(item.freeuse1 == '官方微信') {
                        if (item.freeuse4 == "1") {
                            temp = "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.changeComment(" + JSON.stringify(item) + ", 1);' title='关闭评论'>关闭评论</a> | ";
                        } else {
                            temp = "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.changeComment(" + JSON.stringify(item) + ", 0);' title='打开评论'>打开评论</a> | ";
                        }
                        temp += "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.showInfo(" + JSON.stringify(item) + ", $(this));' title='预览'>预览</a>";
                    } else {
                        temp = "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.showInfo(" + JSON.stringify(item) + ", $(this));' title='预览'>预览</a>";
                    }
                }
                // 微信渠道的图文和视频才可以删除
                if (item.freeuse1 == '官方微信' && (item.tsmsgtype == '03' || item.tsmsgtype == '05')) {
                    temp += " | <a href='javascript:;' style='color:#66cc66' onclick='sendlist1.delItem(" + JSON.stringify(item) + ");' title='删除'>删除</a>";
                }
                return temp;
            }}
        ];
        if(self.tabler3 != null) {
            self.tabler3.load(data);
        } else {
            self.tabler3 = $('#third-table').mmGrid({
                    multiSelect: false,
                    checkCol: false,
                    width: 'auto',
                    height: '225',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        self.flag = self.flag + 1;
        $("#third-table").css('width', 'auto');
    },
    createPager4: function (commsgid) {
        var self = this;
        self.pager4 = pages({
            el: "#fourth-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getFouTableData(pageIndex, pageSize, false,commsgid);
            }
        });
        // 此处用不到分页，暂时隐藏
        $("#fourth-table-pages").hide();
        self.getFouTableData( 1, self.pageSize, true);
    },
    getFouTableData: function (page,rows,pageRestBool,commsgid) {
        var self = this;
        parent.Common.loading(true);
        $.ajax({
            type:'POST',
            url:'./webapi30201.json?method=queryTextImage',
            data: { 
                'centerid':user.centerid,
                'commsgid':commsgid,
                'page': page, 
                'rows': rows
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createFouTable(data.rows);
                    self.pager4.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                } else {
                    if(data.recode != "280001"){
                        parent.Common.dialog({
                            type: "error",
                            text: data.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {}
                        });
                    }
                    
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    createFouTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'id' ,width:80, align:'center', renderer: function (val, item, index) {
                return index+1;
            }},
            { title:'消息标题', name:'title' ,width:270, align: 'center'},
            { title:'消息摘要', name:'detail' ,width:320, align: 'center',},
            { title:'消息类型', name:'tsmsgtype' ,width:180, align: 'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case '01':
                        temp = '文本消息';
                        break;
                    case '02':
                        temp = '图片消息';
                        break;
                    case '03':
                        temp = '富文本消息';
                        break;
                    case '04':
                        temp = '音频消息';
                        break;
                    case '05':
                        temp = '视频消息';
                        break;
                    case '06':
                        temp = '富文本多条，微信专用';
                        break;
                }
                return temp;
            }},
            { title:'操作', name:'tsmsg' ,width:210, align: 'center', renderer: function (val, item, index) {
                var temp = "<a href='javascript:;' style='color:#66cc66' onclick='sendlist1.showInfo06(" + JSON.stringify(item) + ", $(this));' title='预览'>预览</a>";
                if (item.freeuse4 == "1") {
                    temp += " | <a href='javascript:;' style='color:#66cc66' onclick='sendlist1.changeComment(" + JSON.stringify(item) + ", 1);' title='关闭评论'>关闭评论</a>";
                } else {
                    temp += " | <a href='javascript:;' style='color:#66cc66' onclick='sendlist1.changeComment(" + JSON.stringify(item) + ", 0);' title='打开评论'>打开评论</a>";
                }
                temp += " | <a href='javascript:;' style='color:#66cc66' onclick='sendlist1.delItem(" + JSON.stringify(item) + ");' title='删除'>删除</a>";
                return temp;
            }},
        ];
        if(self.tabler4 != null) {
            self.tabler4.load(data);
        } else {
            self.tabler4 = $('#fourth-table').mmGrid({
                    multiSelect: false,
                    checkCol: false, 
                    height: '425',
                    cols: cols,
                    items: data,
                    loadingText: "loading...",
                    noDataText: "暂无数据。",
                    loadErrorText: "数据加载异常，请刷新页面重试，或联系网站管理员。",
                    sortable: false
                });
        }
        $('#fourth-table').css("width", "auto");
        parent.Common.loading(false);
    },
    changeComment: function (item, state) {
        /**
         * 打开关闭评论
         * @param {Object} item 当前修改数据项
         * @param {Number} state 0代表关闭，1代表开启
         * */
        var self = this,
            isThd = false;
        parent.Common.loading(true);
        // 没有msmscommsgid属性的时候说明是第三个table，ajax回调调用getThdTableData方法查询第三个table的数据
        if (!item.hasOwnProperty('msmscommsgid')) {
            item.msmscommsgid = '';
            isThd = true;
        }
        $.ajax({
            type:'POST',
            url:'./webapi30201.json?method=commentCtrl',
            data: {
                'centerId': item.centerid,
                'msmscommsgid': item.msmscommsgid,
                'commsgid': item.commsgid,
                'freeuse4': state
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
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
                            if (isThd) {
                                self.getThdTableData(1, self.pageSize, true, self.thdCommsgid, self.thdPid);
                            } else {
                                self.getFouTableData(1, self.pageSize, true, item.commsgid);
                            }
                        }
                    });
                } else {
                    if(data.recode != "280001"){
                        parent.Common.dialog({
                            type: "error",
                            text: data.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {}
                        });
                    }

                }
            },
            error:function(){
                parent.Common.ajaxError();
            }

        });
    },
    delItem: function (item) {
        /**
         * 删除已发送图文信息
         * @param {Object} item 当前删除数据项
         * */
        var self = this,
            isThd = false;
        parent.Common.loading(true);
        // 没有msmscommsgid属性的时候说明是第三个table，ajax回调调用getThdTableData方法查询第三个table的数据
        if (!item.hasOwnProperty('msmscommsgid')) {
            item.msmscommsgid = '';
            isThd = true;
        }
        $.ajax({
            type:'POST',
            url:'./webapi30201.json?method=delSend',
            data: {
                'centerId': item.centerid,
                'msmscommsgid': item.msmscommsgid,
                'commsgid': item.commsgid
            },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
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
                            if (isThd) {
                                self.getThdTableData(1, self.pageSize, true, self.thdCommsgid, self.thdPid);
                            } else {
                                self.getFouTableData(1, self.pageSize, true, item.commsgid);
                            }
                        }
                    });
                } else {
                    if(data.recode != "280001"){
                        parent.Common.dialog({
                            type: "error",
                            text: data.msg,
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {}
                        });
                    }

                }
            },
            error:function(){
                parent.Common.ajaxError();
            }

        });
    },
    btnClick:function(){
        var self = this;

        $('#btnQuery').click(function(){
            self.getTableData(1, self.pageSize, true);
            self.createSecTable([]);
            self.createThdTable([]);
            self.pager2.reset({
                itemLength: 0,
                pageSize: 10,
                reset: true
            });
            self.pager3.reset({
                itemLength: 0,
                pageSize: 10,
                reset: true
            });
        });

        $('#orgManage-info-goBack').click(function(){
            $('#page1').show();
            $('#page2').hide();
        });

        /**
         * 点击“查询今天”，显示隐藏 开始和结束日期区域
         * */
        $("#isToday").on("change", function () {
            var _this = $(this),
                dateArea = $(".date-show");
            _this.prop("checked") ? dateArea.hide() : dateArea.show();
        });
    },
    showInfo: function(item){

        var self = this;

        var mobile = $(".mobile").html();
        parent.Common.popupShow(mobile);

        parent.$('#title').val(item.title);
        parent.$(".mobile-list-title").text(item.title);
        parent.$(".mobile-content").html(item.tsmsg);
        parent.$(".popup-preview-fontImageOne").hide();
        parent.$(".popup-preview-fontImageMore").hide();
        parent.$(".mobile-popup-btns a").eq(0).hide();
        parent.$(".mobile-popup-btns a").eq(1).hide();
        parent.$(".popup-preview-font").hide();
        parent.$(".popup-preview-img").hide();

        switch (item.tsmsgtype) {
            case '01':
                // 文本
                parent.$(".popup-preview-font").show();
                break;
            case '02':
                parent.$(".popup-preview-img").show();
                break;
            case '03':
                // 富文本
                parent.$(".popup-preview-fontImageOne").show();
                break;
            // case '06':
                // 多条图文,微信专用
                // parent.$(".popup-preview-fontImageMore").show();
                // parent.$(".mobile-popup-btns a").eq(1).show();
                // self.getFouTableData(1,10,true,item.commsgid);
                // $('#page1').hide();
                // $('#page2').show();
                // break;
        }
        
        self.popupBtnClick();
    },
    popupBtnClick: function () {
        parent.$(".mobile-popup-btns a").off().on("click", function () {
            var _this = $(this),
                _index = $(this).index();
            switch (_index) {
                case 0:
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".mobile-list").show();
                    parent.$(".mobile-content").hide();
                    break;
                case 1:
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".mobile-list").hide();
                    parent.$(".mobile-content").show();
                    break;
                case 2:
                    parent.Common.popupClose();
                    break;
            }
        });
    },
    jumpToNext: function(item){
        var self = this;
        self.getFouTableData(1,10,true,item.commsgid);
        $('#page1').hide();
        $('#page2').show();
    },
    showInfo06: function(item){

        var self = this;
        var mobile = $(".mobile").html();
        parent.Common.popupShow(mobile);

        parent.$('#title').val(item.title);
        parent.$(".mobile-list-title").text(item.title);
        parent.$(".mobile-content").html(item.tsmsg);
        parent.$(".popup-preview-fontImageOne").hide();
        parent.$(".popup-preview-fontImageMore").hide();
        parent.$(".mobile-popup-btns a").eq(0).hide();
        parent.$(".mobile-popup-btns a").eq(1).hide();
        parent.$(".popup-preview-font").hide();
        parent.$(".popup-preview-img").hide();

        // 多条图文,微信专用
        parent.$(".popup-preview-fontImageMore").show();
        parent.$(".mobile-popup-btns a").eq(1).show();
        
        self.popupBtnClick();
    },
}
   


$(function(){
    sendlist1.createDatepicker();
    sendlist1.createPager3();
    sendlist1.createPager4();
    sendlist1.btnClick();
    sendlist1.createPager();
    sendlist1.createPager2();
});
