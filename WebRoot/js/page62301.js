/**
 * Created by M on 2016/9/23.
 */
 var user = top.userInfo;
var codeTable = {
    tabler: null,
    botabler: null,
    tableData:null,
    treeVue: null,
    pageSize:10,
    pager: null,
    bopager: null,
    website_code:null,
    appotemplateid:null,//编辑预约时段模版明细用
    appobranchid:null,//编辑预约时段模版明细用
    flag:false,

    pid:null,
    centerList: [],
    tempCenterid: null, // for create and edit
    dicid: null, 

    createTree: function () {
        var self = this;
        self.treeVue = new Vue({
            el: ".code-table-left",
            data: {
                items: [],
                selected: {"id":"000000000","text":"目录","state":"closed","attributes":{"centerid":"000000000"}}
            },
            methods: {
                getSubTree: function (pid) {
                    self.getSubTree( pid);
                }
            }
        });
        // self.getCenterList();
        self.getBaseTree("00000000");
    },
    getCenterList: function () {

        var self = this;
        $.ajax({
            type: "POST",
            url: "./page62301Qry.html",
            datatype: "json",
            data: {pid:top.userInfo.centerid},
            success: function(data) {
                parent.Common.loading(false);
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.centerList = data.rows;
                    self.getBaseTree(top.userInfo.centerid, "00000000");
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
                parent.Common.ajaxError();
            }
        });
    },
    getBaseTree: function (pid) {
        parent.Common.loading(true);
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page62301Qry.html?pid=00000000",
            datatype: "json",
            data: { 'id': '000000000' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                self.treeVue.items = data;
                self.createPager(pid);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getSubTree: function (pid,state) {
        parent.Common.loading(true);
        var self = this;
        self.website_code = null;
        
        if(typeof(state) != 'undefined'){
            self.website_code = pid;
            self.getTable(1,self.pageSize,pid,true);
            if(self.flag){
                self.getBottomTable('');
            }
        }
        $.ajax({
            type: "POST",
            url: "./page62301Qry.html?pid="+pid,
            datatype: "json",
            data: { 'id': pid  },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                var tempHtml = '';
                data.forEach(function (item) {
                    var backgroundClass = '';
                    if(item.state == 'open') { backgroundClass = 'background-none'; }
                    tempHtml += '<dd>' +
                        // '<p onclick=\'codeTable.getSubTree("' +  item.id + '")\' class="' + backgroundClass + '">' + item.text + '</p>' +
                        '<p onclick=\'codeTable.getSubTree("' + item.id + '",' + JSON.stringify(item) +')\' class="' + backgroundClass + '">' + item.text + '</p>' +
                        '<dl id="dl' + item.id + '"></dl>' +
                        '</dd>';
                });
                if (pid == "0") {
                    $("#dl" + self.treeVue.selected.id).empty().append(tempHtml);
                } else {
                    $("#dl" + pid).empty().append(tempHtml);
                }
                
                parent.Common.loading(false);

            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createPager: function (pid) {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#netManagement-table-pages",
            itemLength: 0,
            pageSize: 10,
            pageChanged: function (pageIndex, pageSize) {
                self.pageSize = pageSize;
                self.getTable(pageIndex, pageSize, false);
            }
        });
        self.getTable( 1, self.pageSize, pid,true);
    },
    getTable: function (page,rows,parentwebcode,pageRestBool) {
        var self = this;
        // self.parentwebcode = parentwebcode;
        $.ajax({
            type: "POST",
            url: "./webapi62304.json",
            datatype: "json",
            data: { 'websiteCode': parentwebcode, 'page': page ,'rows':rows},
            success: function(data) {
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
                        ok: function () {
                        }
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    btnClick: function () {
        var self = this;
        $(".code-table-left").off().on("click", 'p', function () {
            var _this = $(this);
            $(".code-table-left").find("p").removeClass("on");
            var pTags = $(".code-table-left").find("p");
            _this.siblings("ul, dl").stop().slideToggle();
            for(var i = 0; i < pTags.length; i++) {
                if(pTags.eq(i).next("dl").css("display") == "block") {
                    pTags.eq(i).addClass("on");
                }
            }
        });
        $(".code-table-left h2").off().on("click", function () {
            self.getBaseTree("00000000");
            self.treeVue.selected = {"id":"00000000","text":"目录","state":"closed","attributes":{"centerid":"000000000"}};
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
            }},
            { title:'业务类型', name:'appobusiid' ,width:130, align: 'center' ,renderer: function (val, item, index) {
                return $('#busListSelect option[value='+val+']').text();
            }},
            { title:'最长可预约天数', name:'maxdays' ,width:100, align: 'center'},
            { title:'预约时段模版', name:'appotemplateid' ,width:144, align: 'center',renderer: function (val, item, index) {
                return $('#timeListSelect option[value='+val+']').text();
            }},
            { title:'预约业务启用日期', name:'begindate' ,width:120, align: 'center'},
            { title:'当天是否可预约', name:'freeuse1' ,width:75, align: 'center',renderer: function (val, item, index) {
                return val=='0'?'否':'是';
            }},
            { title:'提前几个小时预约', name:'freeuse2' ,width:120, align: 'center'}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#netManagement-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
                height: '190px',
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
        self.tabler.on("cellSelected", function (e, item, rowIndex, colIndex) {
            $(".netManagement-bottom").show();
            self.appobranchid = item.appobranchid;
            self.appotemplateid = item.appotemplateid;
            self.flag = true;
            self.getBottomTable(item.appobranchid);
            getDetailList(item.appotemplateid)
        });
        this.topBtnsClick(self.tabler);
    },
    getBottomTable: function (appobranchid) {
        parent.Common.loading(true);
        parent.Common.popupClose();
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi62404.json",
            datatype: "json",
            data: { 'appobranchid': appobranchid},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createBottomTable(data.rows);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createBottomTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            { title:'序号', name:'id' ,width:40, align:'center', renderer: function (val, item, index) {
                        return index+1;
            }},
            { title:'预约时段模版明细', name:'appotpldetailid' ,width:348, align: 'center',renderer: function (val, item, index) {
                return $('#appotpldetailid option[value='+val+']').text();
            } },
            { title:'可预约人数上限', name:'appocnt' ,width:380, align: 'center'}
        ];
        if(self.botabler != null) {
            self.botabler.load(data);
        } else {
            self.botabler = $('#netManagement-table-template').mmGrid({
                multiSelect: true,
                checkCol: true,
                height: '142px',
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
    topBtnsClick: function () {
        
        var self = this;
        $("#netManagement-btn-add").on("click", function () {
            if(!self.website_code){
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "warning",
                    text: "请先选择网点！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                return;
            }
            var createHTML = $(".netManagement-create").html();
            parent.Common.popupShow(createHTML);
            
            setTimeout(function(){
                regDate(1);
            },100);
        });
        $("#netManagement-btn-edit").on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            } 
            if(selected.length>1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }

            var createHTML = $(".netManagement-create1").html();
            parent.Common.popupShow(createHTML);

            parent.$('#busListSelect-edit').val(selected[0].appobusiid);
            parent.$('#maxDay-edit').val(selected[0].maxdays);
            parent.$('#timeListSelect-edit').val(selected[0].appotemplateid);
            parent.$('#begindate1').val(selected[0].begindate);
            parent.$('#boolListSelect-edit').val(selected[0].freeuse1);
            parent.$('#freeuse21').val(selected[0].freeuse2);

            setTimeout(function(){
                regDate(0);
            },100);
        });
        $("#netManagement-btn-del").on("click", function () {
            var  selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "warning",
                text: "确认是否删除,删除的同时会将时段模版下的所有时段明细删除？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    self.topDel();
                }
            });
        });
    },
    topAdd: function () {
        var self = this;
        var appobusiid = parent.$("#busListSelect").val(),
            maxdays = parent.$("#maxDay").val(),
            appotemplateid = parent.$('#timeListSelect').val(),
            begindate = parent.$('#begindate').val(),
            freeuse1 = parent.$('#boolListSelect').val(),
            freeuse2 = parent.$('#freeuse2').val(),
            website_code = self.website_code;
        if(maxdays.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "最长可预约天数不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(begindate.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "预约业务启用日期不能为空！",
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
            url: "./webapi62301.json",
            datatype: "json",
            data: { 
                'appobusiid':appobusiid,
                'maxdays':  maxdays, 
                'appotemplateid': appotemplateid,
                'begindate': begindate,
                'freeuse1': freeuse1 ,
                'freeuse2':freeuse2,
                'appobranchid':'',
                'websiteCode':website_code
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
                            self.getTable(1,self.pageSize,website_code,true);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    topEdit: function () {
        var self = this,
            selected = self.tabler.selectedRows();
        var appobusiid = parent.$("#busListSelect-edit").val(),
            maxdays = parent.$("#maxDay-edit").val(),
            appotemplateid = parent.$('#timeListSelect-edit').val(),
            begindate = parent.$('#begindate1').val(),
            freeuse1 = parent.$('#boolListSelect-edit').val(),
            freeuse2 = parent.$('#freeuse21').val(),
            appobranchid = selected[0].appobranchid,
            website_code = selected[0].website_code;
        if(maxdays.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "最长可预约天数不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(begindate.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "预约业务启用日期不能为空！",
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
            url: "./webapi62303.json",
            datatype: "json",
            data: { 
                'appobusiid':appobusiid,
                'maxdays':  maxdays, 
                'appotemplateid': appotemplateid,
                'begindate': begindate,
                'freeuse1': freeuse1 ,
                'freeuse2':freeuse2,
                'appobranchid':appobranchid,
                'websiteCode':website_code
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
                            self.getTable(1,self.pageSize,website_code,true);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    topDel: function () {
        var self = this;
        var seleced = self.tabler.selectedRows(),
            ids = [];
        seleced.forEach(function (item) {
            ids.push(item.appobranchid);
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62302.json",
            datatype: "json",
            data: { 'appobranchid': ids.join(',') },
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
                            self.getTable(1,self.pageSize,self.website_code,true);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    BtnsClick: function (table) {
        var table = table;
        $("#netManagement-template-btn-add").on("click", function () {
           
            var createHTML = $(".netManagement-bottom-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#netManagement-template-btn-edit").on("click", function () {
            var timeListData = ["08:45-09:00", "09:00-09:15", "09:15-09:30", "10:00-10:15"],
                timeListHTML = '';
            timeListData.forEach(function (val, index) {
                timeListHTML += "<option>" + val +"</option>";
            });
            $("#netManagement-time-edit-select").html(timeListHTML);
            // inner html
            var editHTML = $(".netManagement-bottom-edit").html();
            parent.Common.popupShow(editHTML);
        });
        $("#netManagement-template-btn-del").on("click", function () {
            parent.Common.dialog({
                type: "tips",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                cancelText: "取消",
                ok: function () {
                    // post data
                }
            });
        });
    },
    bottomAdd: function () {
        var self = this;
        var appotpldetailid = parent.$("#appotpldetailid").val(),
            appocnt = parent.$("#appocnt").val(),
            appotemplateid = self.appotemplateid,
            appobranchid = self.appobranchid;

        if(appocnt.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "可预约人数上线不能为空！",
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
            url: "./webapi62401.json",
            datatype: "json",
            data: { 
                'appotpldetailid':appotpldetailid,
                'appocnt':  appocnt, 
                'appotemplateid': appotemplateid,
                'appobranchid': appobranchid
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
                            self.getBottomTable(self.appobranchid);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    bottomEdit: function () {
        var self = this,
            selected = self.botabler.selectedRows();
        var appotpldetailid = parent.$("#appotpldetailid1").val(),
            appocnt = parent.$("#appocnt1").val(),
            appotemplateid = self.appotemplateid,
            appobranchid = self.appobranchid,
            appobrantimeid = selected[0].appobrantimeid;

        if(appocnt.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "可预约人数上线不能为空！",
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
            url: "./webapi62403.json",
            datatype: "json",
            data: { 
                'appotpldetailid':appotpldetailid,
                'appocnt':  appocnt, 
                'appotemplateid': appotemplateid,
                'appobranchid': appobranchid,
                'appobrantimeid':appobrantimeid
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
                            self.getBottomTable(self.appobranchid);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    bottomDel: function () {
        var self = this;
        var seleced = self.botabler.selectedRows(),
            ids = [];
        seleced.forEach(function (item) {
            ids.push(item.appobrantimeid);
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi62402.json",
            datatype: "json",
            data: { 'appobrantimeid': ids.join(',') },
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
                            self.getBottomTable(item.appobranchid);
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
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    bottomBtnsClick: function () {
        var self = this;
        $("#netManagement-template-btn-add").on("click", function () {
            var createHTML = $(".netManagement-bottom-create").html();
            parent.Common.popupShow(createHTML);
        });
        $("#netManagement-template-btn-edit").on("click", function () {
            var selected = self.botabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            } 
            if(selected.length>1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条记录进行修改！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            var editHTML = $(".netManagement-bottom-edit").html();
            parent.Common.popupShow(editHTML);

            parent.$('#appotpldetailid1').val(selected[0].appotpldetailid);
            parent.$('#appocnt1').val(selected[0].appocnt);
        });
        $("#netManagement-template-btn-del").on("click", function () {
            var selected = self.botabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "error",
                    text: "请至少选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "warning",
                text: "确认是否删除？",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    self.bottomDel();
                }
            });
        });
        // 上移按钮
        $("#netManagement-template-btns-moveUp").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.botabler.selectedRowsIndex();
            for(var i = 0; i < selectedDataIndex.length; i++) {
                if(selectedDataIndex[i] == 0) {
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
                if(selectedDataIndex[i] == 0) return;
                var temp = newData[selectedDataIndex[i]].freeuse4;
                newData[selectedDataIndex[i]].freeuse4 = newData[selectedDataIndex[i] - 1].freeuse4;
                newData[selectedDataIndex[i] - 1].freeuse4 = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.botabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.botabler.select(val - 1);
            });
            self.tableData = newData;

        });

        // 下移按钮
        $("#netManagement-template-btns-moveDown").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.botabler.selectedRowsIndex();
            for(var i = selectedDataIndex.length; i >= 1; i--) {
                if(selectedDataIndex[i - 1] == newData.length - 1) {
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
                var temp = newData[selectedDataIndex[i-1]].freeuse4;
                newData[selectedDataIndex[i-1]].freeuse4 = newData[selectedDataIndex[i - 1] + 1].freeuse4;
                newData[selectedDataIndex[i-1] + 1].freeuse4 = temp;
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.botabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.botabler.select(val + 1);
            });
            self.tableData = newData;
        });

        $("#netManagement-template-btns-save").off().on("click", function () {
            self.subSave();
        });
    },
    subSave: function(){
        var self = this,
            newtable = self.tableData;
        newtable.forEach(function (val,i,array) {
            val.freeuse4 = i+1;
        });
        $.ajax({
            type: "POST",
            url: "./webapi623SaveSort.json?datalist=" + JSON.stringify(self.tableData),
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getBottomTable(item.appobranchid);
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
    },
};
$(document).ready(function(){

    codeTable.createTree();
    codeTable.bottomBtnsClick();
    getBusiList();
    getTemplateList();

});
//获取日期
function regDate(v){
    var elem = '#begindate';
    (v==1)?elem='#begindate':elem='#begindate1';
    var deadline = {
        elem: elem,
        format: 'YYYY-MM-DD',
        min: parent.laydate.now(), //设定最小日期为当前日期
        max: '2099-12-31', //最大日期
        istime: false,
        istoday: false,
        fixed: false,
        choose: function(datas){
        }
    };
    parent.laydate(deadline);
    parent.laydate.skin('huanglv');
}
// 获取预约业务类型列表
function getBusiList() {
    $.ajax({
        type:'POST',
        url:'./webapi62004.json',
        data:{
            'centerid': user.centerid,
            'page': 1,
            'rows':200
        },
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var listhtml = '',
                    len = data.rows.length;
                for(var i =0;i<len;i++){
                    listhtml += '<option value="'+data.rows[i].appobusiid+'">'+data.rows[i].appobusiname+'</option>';
                }
                $('#busListSelect,#busListSelect-edit').html(listhtml);
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
}
// 获取预约时段模板列表
function getTemplateList() {
    $.ajax({
        type:'POST',
        url:'./webapi62104.json',
        data:{
            'centerid': user.centerid,
            'page': 1,
            'rows':200
        },
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var timehtml = '',
                    len = data.rows.length;
                for(var i =0;i<len;i++){
                    timehtml += '<option value="'+data.rows[i].appotemplateid+'">'+data.rows[i].templatename+'</option>';
                }
                $('#timeListSelect,#timeListSelect-edit').html(timehtml);
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
}
// 获取预约业务网点时段列表
function getDetailList(appotemplateid) {
    $.ajax({
        type:'POST',
        url:'./page62206.json',
        data:{
            'appotemplateid': appotemplateid
        },
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var timehtml = '',
                    result = data.result
                    len = result.length;
                for(var i =0;i<len;i++){
                    timehtml += '<option value="'+result[i].appotpldetailid+'">'+result[i].timeinterval+'</option>';
                }
                $('#appotpldetailid,#appotpldetailid1').html(timehtml);
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
}