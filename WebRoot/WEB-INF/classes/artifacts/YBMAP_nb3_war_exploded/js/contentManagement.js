/**
 * Created by FelixAn on 2016/9/10.
 */
var contentManagement = {
    pager: null,
    tabler: null,
    pageSize: 10,
    editor: null,
    startTime: null,
    endTime: null,
    releaseTime: null,
    isAdd: true,
    columns: [],
    qudaos: null,
    infoSource: [],
    editCenterid: '',
    editSeqno: '',
    dropVuer: null,
    dropVuer2: null,
    materials: null, // for 素材库 search
    createPager: function () {
        var self = this;
        self.pager = pages({
            el: "#content-pager",
            itemLength: 2300,
            pageSize: 12,
            pageChanged: function (pageIndex, pageSize) {
                var column = self.dropVuer.selectValue,
                    startdate = self.startTime,
                    enddate = self.endTime,
                    keyword = $("#search-keyword").val(),
                    pubStatusQry = $("#status-list").val(),
                    pubQudaoQry = $("#qudao-list").val(),
                    sourceQry = "",
                    page = pageIndex,
                    row = pageSize;
                self.pageSize = pageSize;
                column = column == "-1" ? "" : column;
                pubStatusQry = pubStatusQry == "-1" ? "" : pubStatusQry;
                parent.Common.loading(true);
                self.getTable(column, startdate, enddate, keyword, pubStatusQry,pubQudaoQry, sourceQry, page, row, false);
            }
        });
        self.getColumns();
        self.bindDatePicker();
        self.createUpload();
        self.createDropVuer();
    },
    createDropVuer: function () {
        var self = this;
        self.dropVuer = new Vue({
            'el': '#content-management-select1',
            'data': {
                isShow: false,
                tree: [],
                selectText: '全部',
                selectValue: ''
            },
            'methods': {
                check: function (value, text) {
                    this.selectText = text;
                    this.selectValue = value;
                    this.isShow = false;
                }
            }
        });
        self.dropVuer2 = new Vue({
            'el': '#content-management-select2',
            'data': {
                isShow: false,
                tree: [],
                selectText: '全部',
                selectValue: ''
            },
            'methods': {
                check: function (value, text) {
                    this.selectText = text;
                    this.selectValue = value;
                    this.isShow = false;
                    $.ajax({
                        type: "POST",
                        url: "./page707Query.json?itemid="+value,
                        datatype: "json",
                        success: function(data) {
                        	var data =data.mi707.freeuse3;
                        	var qudaoHtml2='';
                        	if(data==null){
                        		parent.Common.loading(false);
                                parent.Common.dialog({
                                    type: "error",
                                    text: "所选栏目暂未配置渠道，请先到内容管理下栏目设置配置该栏目对应的渠道！",
                                    okShow: true,
                                    cancelShow: false,
                                    okText: "确定",
                                    ok: function () {
                                    }
                                });
                        	}else{
        		                 for(var o in self.qudaos){
        		                	for(var j in data.split(",")){
        		                		if(self.qudaos[o].pid==data.split(",")[j]){
        		                			qudaoHtml2 += '<option value="' + self.qudaos[o].pid + '">' + self.qudaos[o].appname + '</option>';
        		                		}
        		                	}
        		                 }
                        	}
        	                 $("#freeuse8").html(qudaoHtml2);
        	                 $('#freeuse8').multipleSelect({
        	                 	placeholder: "请选择",
        	                 	selectAll: false,
        	                 	maxHeight: 120,
        	                 });
                        },
                        error: function(){
                            parent.Common.ajaxError();
                        }
                    });
                }
            }
        });
    },
    bindDatePicker: function () {
        var self = this;
        laydate({
            elem: '#start-date',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.startTime = datas;
            }
        });
        laydate({
            elem: '#end-date',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.endTime = datas;
            }
        });
        laydate({
            elem: '#release-date',
            format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true, //显示节日
            choose: function(datas){ //选择日期完毕的回调
                self.releaseTime = datas;
            }
        });
        laydate.skin('huanglv');
    },
    getColumns: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page70001.json",
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var columnsHtml = '<option value="-1">全部</option>',
                        createColumns = '',
                        statusHtml = '<option value="-1">全部</option>';
                    	qudaoHtml = '<option value="-1">全部</option>';
                    var tempArr = [];
                    function calleeArr(arr) {
                        arr.forEach(function (item) {
                            tempArr.push(item);
                            if('children' in item) {
                                calleeArr(item.children);
                            }
                        });
                    }
                    calleeArr(data.ary);
                    self.columns = tempArr;
                    self.qudaos=data.qudaolist;
                    self.dropVuer.tree = data.ary;
                    self.dropVuer2.tree = data.ary;
                    self.dropVuer2.selectValue = data.ary[0].id;
                    self.dropVuer2.selectText = data.ary[0].text;
                    self.infoSource = data.infoSource;
                    data.ary.forEach(function (item) {
                        columnsHtml += '<option value="' + item.id + '">' + item.text + '</option>';
                        createColumns += '<option value="' + item.id + '">' + item.text + '</option>';
                    });
                    data.attrlist.forEach(function (item) {
                        statusHtml += '<option value="' + item.itemid + '">' + item.itemval + '</option>';
                    });
                    data.qudaolist.forEach(function (item) {
                        qudaoHtml += '<option value="' + item.pid + '">' + item.appname + '</option>';
                    });
                    $("#content-management-list").html(columnsHtml);
                    $("#create-left-columns").html(createColumns);
                    $("#status-list").html(statusHtml);
                    $("#qudao-list").html(qudaoHtml);
                    self.getTable('', '', '', '', '', '','', 1, self.pageSize, true);
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
    getTable: function (column, startdate, enddate, keyword, pubStatusQry, pubQudaoQry,sourceQry, page, row, resetBool) {
        var self = this;
        if(startdate != null && startdate.length > 1) {
            startdate = startdate + ' 00:00:00';
        }
        if(enddate != null && enddate.length > 1) {
            enddate = enddate + ' 23:59:59';
        }
        $.ajax({
            type: "POST",
            url: "./webapi70004.json",
            data: { 'classificationQry': column, 'startdate': startdate, 'enddate': enddate, 'keyword': keyword, 'pubStatusQry': pubStatusQry, 'pubQudaoQry': pubQudaoQry,'sourceQry': sourceQry, 'page': page, 'rows': row },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: data.pageSize,
                        reset: resetBool
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
                    self.createTable([]);
                    self.pager.reset({
                        itemLength: 0,
                        pageSize: self.pageSize,
                        reset: resetBool
                    });
                }
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTable: function (data) {
        var self = this,
            columns = self.columns,
            infoSource = self.infoSource;
        var cols = [
            { title:'序号', name:'centerid' ,width:30, align:'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            { title:'标题及摘要', name:'image' ,width:358, align:'center', renderer: function (val, item, index) {
                var tempHtml = '',
                    tempImgSrc = 'images/none_img.png';
                if(val != null) tempImgSrc = val;
                tempHtml += '<div class="table-img"><img alt="" src="" style="width:0;height:100%;padding:0;margin:0;border:0;visibility:hidden"><img src="' + tempImgSrc + '" /></div>';
                tempHtml += '<div class="table-title">' + item.title + '</div>';
                tempHtml += '<div class="table-desc">' + item.introduction + '</div>';
                return tempHtml;
            }},
            { title:'栏目', name:'classification' ,width:90, align:'center', renderer: function (val, item, index) {
                var temp = '';
                columns.forEach(function (column) {
                    if(column.id == val) {
                        temp = column.text;
                    }
                });
                return temp;
            }},
            { title:'渠道', name:'freeuse8' ,width:100, align:'center', renderer: function (val, item, index) {
            	var temp = "";
            	if(val!=null){
	            	for(var o in val.split(",")){
	            		for(var j in self.qudaos){
	            			if(self.qudaos[j].pid == val.split(",")[o]) {
	            				temp += self.qudaos[j].appname+",";
	            			}
	            		}
	            	}
	            	temp=temp.substring(0,temp.length-1);
            	}
                return temp;
            }},
            { title:'来源', name:'validflag' ,width:80, align:'center', renderer: function (val, item, index) {
                var temp = '';
                infoSource.forEach(function (info) {
                    if(val == info.itemid) {
                        temp = info.itemval;
                    }
                });
                return temp;
            }},
            { title:'状态', name:'freeuse3' ,width:60, align:'center', renderer: function (val, item, index) {
                var temp = '';
                switch (val) {
                    case "0":
                        temp = "草稿";
                        break;
                    case "1":
                        temp = "发布";
                        break;
                    case "2":
                        temp = "审批中";
                        break;
                    case "3":
                        temp = "已审批";
                        break;
                }
                return temp;
            }},
            { title:'审核时间', name:'freeuse10' ,width:90, align:'center', renderer: function (val) {
                if(val == null) {
                    return "-";
                } else {
                    return val;
                }
            }},
            { title:'发布时间', name:'releasetime' ,width:90, align:'center'},
            { title:'操作', name:'freeuse3' ,width:102, align:'center', renderer: function (val, item, index) {
                var temp = "";
                if(val == '1') {
                    temp += '<span class="gray-color" title="已发布">已发布</span>';
                } else {
                    //temp += '<a href="javascript:;" class="green-color" title="审核" onclick="contentManagement.check(\'' + item.centerid + '\',\'' + item.seqno + '\', \'' + item.freeuse3 + '\');">审核</a>';
                    //temp += '<span class="table-splic">|</span>';
                    temp += '<a href="javascript:;" class="green-color" title="发布" onclick="contentManagement.release(\'' + item.centerid + '\',\'' + item.seqno + '\', \'' + item.freeuse3 + '\');">发布</a>';
                }
                return temp;
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#content-table').mmGrid({
                multiSelect: true,// 多选
                checkCol: true, // 选框列
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
        self.createEditor();
        self.createVue();
    },
    createEditor: function () {
        var self = this;
        // self.editor = KindEditor.create('textarea[name="editor"]', {
        //     uploadJson : './ueditor1_4_3-utf8-jsp/jsp/controller.jsp?action=uploadimage'
        // });
        self.editor = UE.getEditor('create-left-editor');
    },
    btnClick: function () {
        var self = this;
        $("#search-btn").off().on("click", function () {
            var column = self.dropVuer.selectValue,
                startdate = self.startTime,
                enddate = self.endTime,
                keyword = $("#search-keyword").val(),
                pubStatusQry = $("#status-list").val(),
                pubQudaoQry = $("#qudao-list").val(),
                sourceQry = 1,
                page = 1,
                row = self.pageSize;
            column = column == "-1" ? "" : column;
            pubStatusQry = pubStatusQry == "-1" ? "" : pubStatusQry;
            parent.Common.loading(true);
            self.getTable(column, startdate, enddate, keyword, pubStatusQry, pubQudaoQry,sourceQry, page, row, true);
        });
        $("#reset-btn").off().on("click", function () {
            self.dropVuer.selectValue = '';
            self.dropVuer.selectText = '全部';
            self.startTime = '';
            self.endTime = '';
            $('#start-date').val('');
            $('#end-date').val('');
            $("#search-keyword").val('');
            $("#status-list").val('-1');
            $("#qudao-list").val('-1');
        });
        $("#content-btn-add").off().on("click", function () {
            $(".content-management-top, .content-management-box").hide();
            $(".create").show();
            self.isAdd = true;
            $.ajax({
                type: "POST",
                url: "./page707Query.json?itemid="+self.columns[0].id,
                datatype: "json",
                success: function(data) {
                	var data =data.mi707.freeuse3;
                	var qudaoHtml2='';
                	if(data==null){
                		parent.Common.loading(false);
                        parent.Common.dialog({
                            type: "error",
                            text: "所选栏目暂未配置渠道，请先到内容管理下栏目设置配置该栏目对应的渠道！",
                            okShow: true,
                            cancelShow: false,
                            okText: "确定",
                            ok: function () {
                            }
                        });
                	}else{
		                 for(var o in self.qudaos){
		                	for(var j in data.split(",")){
		                		if(self.qudaos[o].pid==data.split(",")[j]){
		                			qudaoHtml2 += '<option value="' + self.qudaos[o].pid + '">' + self.qudaos[o].appname + '</option>';
		                		}
		                	}
		                 }
                	}
	                 $("#freeuse8").html(qudaoHtml2);
	                 $('#freeuse8').multipleSelect({
	                 	placeholder: "请选择",
	                 	selectAll: false,
	                 	maxHeight: 120,
	                 });
                },
                error: function(){
                    parent.Common.ajaxError();
                }
            });
            // clear form
            $("#create-left-columns").val($("#create-left-columns").find("option").eq(0).val());
            self.dropVuer2.isShow = false;
            self.dropVuer2.selectValue = self.columns[0].id;
            self.dropVuer2.selectText = self.columns[0].text;
            $(".create-left-icons").empty();
            $("#release-date").val("");
            $("#create-left-title").val("");
            $("#create-left-summary").val("");
            UE.getEditor('create-left-editor').setContent("", false);
            $(".create-title p").text("信息内容创建");
        });
        $("#content-btn-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
            } else if(selected.length > 1) {
                parent.Common.editMore();
            } else {
                $(".content-management-top, .content-management-box").hide();
                $(".create").show();
                $("#create-left-columns").val(selected[0].classification);
                self.dropVuer2.isShow = false;
                self.dropVuer2.selectValue = selected[0].classification;
                self.columns.forEach(function (item) {
                    if(item.id == selected[0].classification) {
                        self.dropVuer2.selectText = item.text;
                    }
                });
                $.ajax({
                    type: "POST",
                    url: "./page707Query.json?itemid="+selected[0].classification,
                    datatype: "json",
                    success: function(data) {
                    	var data =data.mi707.freeuse3;
                    	var qudaoHtml2='';
                    	if(data==null){
                    		parent.Common.loading(false);
                            parent.Common.dialog({
                                type: "error",
                                text: "所选栏目暂未配置渠道，请先到内容管理下栏目设置配置该栏目对应的渠道！",
                                okShow: true,
                                cancelShow: false,
                                okText: "确定",
                                ok: function () {
                                }
                            });
                    	}else{
    		                 for(var o in self.qudaos){
    		                	for(var j in data.split(",")){
    		                		if(self.qudaos[o].pid==data.split(",")[j]){
    		                			qudaoHtml2 += '<option value="' + self.qudaos[o].pid + '">' + self.qudaos[o].appname + '</option>';
    		                		}
    		                	}
    		                 }
                    	}
    	                 $("#freeuse8").html(qudaoHtml2);
    	                 $('#freeuse8').multipleSelect({
    	                 	placeholder: "请选择",
    	                 	selectAll: false,
    	                 	maxHeight: 120,
    	                 });
    	                 $('#freeuse8').multipleSelect('setSelects',selected[0].freeuse8.split(","));
                    },
                    error: function(){
                        parent.Common.ajaxError();
                    }
                });
                $(".create-left-icons").empty().append('<img width="138" height="77" src="' + selected[0].image + '" />');
                $("#release-date").val(selected[0].releasetime);
                $("#create-left-title").val(selected[0].title);
                $("#create-left-summary").val(selected[0].introduction);
                parent.Common.loading(true);
                self.queryContent(selected[0].seqno);
                self.editCenterid = selected[0].centerid;
                self.editSeqno = selected[0].seqno;
                self.isAdd = false;
                $(".create-title p").text("信息内容修改");
            }
        });
        $("#content-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
            } else {
                var seqno = [];
                selected.forEach(function (item) {
                    seqno.push(item.seqno);
                });
                parent.Common.dialog({
                    type: "warning",
                    text: "确定要删除选中项吗？",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    cancelText:'取消',
                    ok: function () {
                        self.del(selected[0].centerid, seqno.join(","));
                    }
                });
            }
        });
        $("#content-btn-check").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "warning",
                    text: "请至少选择一条数据进行审核！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            } else {
                var seqno = [];
                selected.forEach(function (item) {
                    seqno.push(item.seqno);
                });
                self.check(selected[0].centerid, seqno.join(","), selected[0].freeuse3);
            }
        });
        $("#content-btn-release").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.dialog({
                    type: "warning",
                    text: "请至少选择一条数据进行发布！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });
            } else {
                var seqno = [];
                selected.forEach(function (item) {
                    seqno.push(item.seqno);
                });
                self.release(selected[0].centerid, seqno.join(","), selected[0].freeuse3);
            }
        });
        $(".create-title a").off().on("click", function () {
            $(".content-management-top, .content-management-box").show();
            $(".create").hide();
        });
        $("#create-save").off().on("click", function () {
            self.save();
        });
        // show
        $("#create-mobile").off().on("click", function () {
            var mobile = $(".mobile").html();
            parent.Common.popupShow(mobile);
            parent.$(".mobile-list-title").text($("#create-left-title").val());
            parent.$(".mobile-list-date").text($("#release-date").val());
            parent.$(".mobile-list-desc").text($("#create-left-summary").val());
            parent.$(".mobile-con-title").text($("#create-left-title").val());
            parent.$(".mobile-con-date").text('发布日期：' + $("#release-date").val());
            parent.$(".mobile-content-html").html(UE.getEditor('create-left-editor').getContent());
            // 隐藏内容区域
            // parent.$(".mobile-content").hide();
            parent.$(".mobile-list").hide();
            parent.$(".mobile-content").show();
            self.popupBtnClick();
        });
        $("#create-pc").off().on("click", function () {
            var mobile = $(".pc").html();
            parent.Common.popupShow(mobile);
            parent.$(".pc-columns-title").text($("#create-left-columns option:selected").text());
            parent.$(".pc-list-img").find("img").eq(1).attr("src", $(".create-left-icons").find("img").eq(0).attr("src"));
            parent.$(".pc-list-fonts h2").text($("#create-left-title").val());
            parent.$(".pc-list-fonts-content").text($("#create-left-summary").val());
            parent.$(".pc-content-title").text($("#create-left-title").val());
            parent.$(".pc-content-date").text('发布日期：' + $("#release-date").val());
            parent.$(".pc-content-box").html(UE.getEditor('create-left-editor').getContent());

            parent.$(".pc-list").hide();
            parent.$(".pc-content-wrap").show();
            self.popupBtnClick();
        });
    },
    popupBtnClick: function () {
        parent.$(".mobile-popup-btns a").off().on("click", function () {
            var _this = $(this),
                _index = $(this).index();
            switch (_index) {
                // case 0:
                //     parent.$(".mobile-popup-btns a").removeClass("on");
                //     _this.addClass("on");
                //     parent.$(".mobile-list").show();
                //     parent.$(".mobile-content").hide();
                //     break;
                case 0:
                    parent.$(".mobile-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".mobile-list").hide();
                    parent.$(".mobile-content").show();
                    break;
                case 1:
                    parent.Common.popupClose();
                    break;
            }
        });
        parent.$(".pc-popup-btns a").off().on("click", function () {
            var _this = $(this),
                _index = $(this).index();
            switch (_index) {
                // case 0:
                //     parent.$(".pc-popup-btns a").removeClass("on");
                //     _this.addClass("on");
                //     parent.$(".pc-list").show();
                //     parent.$(".pc-content").hide();
                //     break;
                case 0:
                    parent.$(".pc-popup-btns a").removeClass("on");
                    _this.addClass("on");
                    parent.$(".pc-list").hide();
                    parent.$(".pc-content").show();
                    break;
                case 1:
                    parent.Common.popupClose();
                    break;
            }
        });
    },
    createVue: function () {
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
                select: 1,
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
                    this.select = index;
                },
                insetHtml: function (material) {
                    var tempHtml = '';
                    switch (material.freeuse1) {
                        case "image":
                            tempHtml = '<img src="' + material.picurl + '" />';
                            break;
                        case "flash":
                            tempHtml = '<object width="135" height="135" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=4,0,2,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"><param value="' + material.picurl + '" name="movie"><param value="high" name="quality"><param value="transparent" name="wmode"><param value="exactfit" name="SCALE"><embed width="135" height="135" wmode="transparent" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"quality="high" src="' + material.picurl + '">';
                            break;
                        case "media":
                            tempHtml = '<audio controls><source  src="' + material.picurl + '" ></audio>  ';
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
                    console.log(self.materials);
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
        var oBtn = document.getElementById("create-left-upload-icon");
        new AjaxUpload(oBtn, {
            action: "./webapi70001_uploadimg.do",
            name: 'imgFile',
            onSubmit: function (file,ext) {
                if(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)){
                    //ext是后缀名
                    //oBtn.disabled = "disabled";
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
    },
    save: function () {
        var self = this,
            url = '',
            postData = null;
        // verify
        if($("#freeuse8").val()==null) {
            parent.Common.dialog({
                type: "warning",
                text: "渠道不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        if($("#create-left-title").val().length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: "标题不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        if($("#release-date").val().length < 1) {
            parent.Common.dialog({
                type: "warning",
                text: "发布日期不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        parent.Common.loading(true);
        var classification = self.dropVuer2.selectValue,
            image = $(".create-left-icons img").attr("src"),
            releasetime = $("#release-date").val(),
            title = $("#create-left-title").val(),
            introduction = $("#create-left-summary").val(),
            content = UE.getEditor('create-left-editor').getContent(),
            contentTmp = UE.getEditor('create-left-editor').getContentTxt(),
            freeuse3 = 0;
        	freeuse8 = $("#freeuse8").val();
        if(contentTmp.length < 1 && content.length > 0) {
            contentTmp = " ";
        }
        if (self.isAdd) {
            url = './webapi70001.json';
            postData = {
                'classification': classification,
                'image': image,
                'releasetime': releasetime,
                'title': title,
                'introduction': introduction,
                'content': content,
                'contentTmp': contentTmp,
                'freeuse3': freeuse3,
                'freeuse8': freeuse8.toString()
            };
        } else {
            url = './webapi70003.json';
            postData = {
                'centerid': self.editCenterid,
                'seqno': self.editSeqno,
                'classification': classification,
                'image': image,
                'releasetime': releasetime,
                'title': title,
                'introduction': introduction,
                'content': content,
                'contentTmp': contentTmp,
                'freeuse3': freeuse3,
                'freeuse8': freeuse8.toString()
            };
        }
        $.ajax({
            type: "POST",
            url: url,
            data: postData,
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
                            parent.Common.loading(true);
                            self.getTable('', '', '', '', '', '','', 1, self.pageSize, true);
                            $(".content-management-top, .content-management-box").show();
                            $(".create").hide();
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
    check: function (centerid, seqno, freeuse3) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi70008.json",
            data: {
                'centerid': centerid,
                'seqnos': seqno,
                'freeuse3': freeuse3
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
    release: function (centerid, seqno, freeuse3) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi70007.json",
            data: {
                'centerid': centerid,
                'seqnos': seqno,
                'freeuse3': freeuse3
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
                            $("#search-btn").trigger('click');
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
                            $("#search-btn").trigger('click');
                        }
                    });
                }
            },
            error: function () {
                parent.Common.ajaxError();
            }
        });
    },
    del: function (centerid, seqno) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi70002.json",
            data: {
                'centerid': centerid,
                'seqnos': seqno
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
                            $("#search-btn").trigger("click");
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
    queryContent: function (seqno) {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi70005.json",
            data: {
                'seqno': seqno
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    UE.getEditor('create-left-editor').ready(function(){
                        parent.Common.loading(false);
                        UE.getEditor('create-left-editor').setContent(data.resultList[0].content, false);
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
    }
};
parent.Common.loading(true);
contentManagement.createPager();