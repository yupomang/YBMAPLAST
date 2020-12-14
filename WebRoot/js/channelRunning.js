var channelRunning = {
    pageSize: 10,
    pager: null,
    tabler: null,
    item: null,
    vuer: null,
    timer: null,
    cacheCenterId: null,
    hasTimer: false,
    createPager: function () {
        var self = this;
        self.pager = pages({
           el: "#channelRunning-table-pages",
           itemLength: 0,
           pageSize: self.pageSize,
           pageChanged: function (pageIndex, pageSize) {
               self.pageSize = pageSize;
               self.getTableData(self.item, pageIndex, pageSize, false);
           }
        });
        self.createVue();
    },
    getCenterList: function () {
        var self = this;
        $.ajax({
            type:'POST',
            data: { 'centerid': top.userInfo.centerid },
            url:'./ptl40003Qry.json',
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                parent.Common.loading(false);
                if(data.recode == '000000'){
                    var tempHtml = '<option value="' + top.userInfo.centerid + '">请选择</option>';
                    data.rows.forEach(function (item) {
                        if(item.validflag == "1") {
                            tempHtml += '<option value="' + item.centerid + '">' + item.centername + '</option>';
                        }
                    });
                    $("#centerList").html(tempHtml);
                    $("#centerList").off().on("change", function () {
                        self.hasTimer = false;
                        self.getData($(this).val());
                        if(self.timer != null) {
                            clearTimeout(self.timer);
                        }
                    });
					setTimeout(function () {
						// 当url中centerNb=true时，默认选中宁波中心
						var url = location.href;
						if(url.split("?centerNb=").length > 1) {
							$("#centerList").val('00057400');
							self.getData('00057400');
						}
					}, 0);
                } else {
                    parent.Common.dialog({
                        type:'error',
                        text:data.msg,
                        okShow:true,
                        cancelShow:false,
                        okText:'确定',
                        ok:function(){}
                    });
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    getData: function (centerid) {
        var self = this;
        //parent.Common.loading(true);
        //if(self.hasTimer) {
        //    top.$("body").append("<p id='timerTips' style='position:fixed;z-index:9999;width:100%;text-align:center;font-size:20px;color:#e7ebef;top:50%;margin-top:30px;'>正在获取最新渠道应用运行状态......</p>");
        //}
        $.ajax({
            type:'POST',
            data: { 'centerid': centerid },
            url: './webapitest01.json',
            datatype:'json',
            success:function(data){
                self.cacheCenterId = centerid;
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.recode == '000000'){
                    //self.vuer.items = data.rows;
                    $.ajax({
                        type:'POST',
                        url:'./webapi04105.json',
                        data: { 'centerid': self.cacheCenterId },
                        datatype:'json',
                        success:function(data){
                            if(typeof data == 'string'){
                                data = JSON.parse(data);
                            }
                            parent.$("#timerTips").remove();
                            parent.Common.loading(false);
                            if(data.recode == '000000'){
                                self.vuer.items = data.rows;
                                self.timer = setTimeout(function () {
                                    clearTimeout(self.timer);
                                    self.getData(self.cacheCenterId);
                                }, 30000);
                                self.hasTimer = true;
                            } else {
                                parent.$('#popup-container').hide();
                                parent.Common.dialog({
                                    type:'error',
                                    text:data.msg,
                                    okShow:true,
                                    cancelShow:false,
                                    okText:'确定',
                                    ok:function(){}
                                });
                            }
                        },
                        error:function(){
                            parent.Common.ajaxError();
                        }
                    });
                } else {
                    parent.Common.loading(false);
                    parent.$('#popup-container').hide();
                    parent.Common.dialog({
                        type:'error',
                        text:data.msg,
                        okShow:true,
                        cancelShow:false,
                        okText:'确定',
                        ok:function(){}
                    });
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
        if(self.pager == null) {
            self.createPager();
        }
    },
    createVue: function () {
        var self = this;
        self.vuer = new Vue({
            el: ".channelRunning-ul",
            data: {
                items: []
            },
            methods: {
                checkHistory: function (item) {
                    self.item = item;
                    self.getTableData(item, 1, self.pageSize, true);
                }
            }
        });
    },
    getTableData: function (item, page, pageSize, pageResetBool) {
        var self = this;
        var open = item.open == "open" ? 1 : 0;
        $.ajax({
            type:'POST',
            url:'./webapi04204.json',
            data: { 'centerid': item.centerid, 'channel': item.channel, 'pid': item.pid, 'open': open, 'page': page, 'rows': pageSize},
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                parent.Common.loading(false);
                if(data.recode == '000000'){
                    $(".channelRunning-name").text("应用名称：" + item.pidname);
                    $(".channelRunning-ul").hide();
                    $(".centerList").hide();
                    $(".channelRunning-container").show();
                    self.createTable(data.rows);
                } else {
                    parent.$('#popup-container').hide();
                    parent.Common.dialog({
                        type:'error',
                        text:data.msg,
                        okShow:true,
                        cancelShow:false,
                        okText:'确定',
                        ok:function(){}
                    });
                }
                self.pager.reset({
                    itemLength: data.total,
                    reset: pageResetBool
                });
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'断开时间', name:'datecreated' ,width:262, align:'center' },
            { title:'恢复时间', name:'datemodified' ,width:262, align:'center' },
            { title:'中断时间', name:'interval' ,width:262, align:'center' },
            { title:'断开描述', name:'cause' ,width:262, align:'center' }
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#channelRunning-table').mmGrid({
                multiSelect: false,// 多选
                checkCol: false, // 选框列
                indexCol: false,
                height: '460px',
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
    btnClick: function () {
        $("#back").off().on("click", function () {
            $(".channelRunning-container").hide();
            $(".centerList").show();
            $(".channelRunning-ul").show();
        });
    }
};
parent.Common.loading(true);
channelRunning.getCenterList();