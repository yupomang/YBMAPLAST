/**
 * Created by M on 2016/9/9.
 */
var codeTable = {
    tabler: null,
    treeVue: null,
    centerList: [],
    tempCenterid: null, // for create and edit
    tempUpdicid: null,
    olditemid: null,
    dicid: null, 
    oldstat:null,
    oldfreeuse1:null,// for edit
    createTree: function () {
        var self = this;
        self.treeVue = new Vue({
            el: ".code-table-tree",
            data: {
                items: [],
                selected: {"id":"000000000","text":"栏目管理","state":"closed","attributes":{"centerid":"000000000"}}
            },
            methods: {
                getSubTree: function (pid, centerid, item) {
                    this.selected = item;
                    self.getSubTree(centerid, pid);
                }
            }
        });
        // self.getCenterList();
        self.getBaseTree(top.userInfo.centerid, "00000000");
    },
    getCenterList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./page70701Qry.json",
            datatype: "json",
            data: { 'centerid': top.userInfo.centerid ,pid:top.userInfo.centerid},
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
    getBaseTree: function (centerid, pid) {
        var self = this;
        self.tempCenterid = centerid;
        self.tempUpdicid = pid;
        $.ajax({
            type: "POST",
            url: "./page70701Qry.html?pid=000000000&centerid=000000000",
            datatype: "json",
            data: { 'id': '000000000' },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                self.treeVue.items = data;
                self.getTable(centerid, pid);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getSubTree: function (centerid, pid, item) {
    	if(centerid!="000000000"){
	    	$.ajax({
	            type: 'POST',
	            url:'./webapi04004.json',
	            data: {
	                'centerid': centerid,
	                'page': 1,
	                'rows': 999
	            },
	            datatype: 'json',
	            success: function (data) {
	                if (typeof data == 'string') {
	                    data = JSON.parse(data);
	                }
	                if (data.recode == '000000') {
	                    var channelOptions = '';
	                    for (var i = 0; i < data.rows.length; i++) {
	                        channelOptions += '<option value="' + data.rows[i].pid + '">' + data.rows[i].appname + '</option>';
	                    }
	                    $('#freeuse3').html(channelOptions);
	                    $('#freeuse32').html(channelOptions);
	                }
	            },
	            error: function () {
	                errMsg();
	            }
	        });
    	}
        parent.Common.loading(true);
        var self = this;
        self.tempCenterid = centerid;
        self.tempUpdicid = pid;
        if (item) {
            self.treeVue.selected = item;
        }
        $.ajax({
            type: "POST",
            url: "./page70701Qry.html?pid="+pid+"&centerid="+centerid,
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
                        '<p onclick=\'codeTable.getSubTree("' + item.attributes.centerid + '","' + item.id + '",' + JSON.stringify(item) +')\' class="' + backgroundClass + '">' + item.text + '</p>' +
                        '<dl id="dl' + item.id + '"></dl>' +
                        '</dd>';
                });
                if (pid == "0") {
                    $("#dl" + self.treeVue.selected.attributes.itemid).empty().append(tempHtml);
                } else {
                    $("#dl" + pid).empty().append(tempHtml);
                }
                self.getTable(centerid, pid);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getTable: function (centerid, parentfuncid) {
        var self = this;
        self.parentfuncid = parentfuncid;
        $.ajax({
            type: "POST",
            url: "./page70701Query.json",
            datatype: "json",
            data: { 'centerid': centerid, 'pid': parentfuncid },
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
        $(".code-table-tree").off().on("click", 'p', function () {
            var _this = $(this);
            $(".code-table-tree").find("p").removeClass("on");
            var pTags = $(".code-table-tree").find("p");
            _this.siblings("ul, dl").stop().slideToggle();
            for(var i = 0; i < pTags.length; i++) {
                if(pTags.eq(i).next("dl").css("display") == "block") {
                    pTags.eq(i).addClass("on");
                }
            }
        });
        $(".code-table-left h2").off().on("click", function () {
            self.getBaseTree("000000000", "000000000");
            self.treeVue.selected = {"id":"000000000","text":"栏目管理","state":"closed","attributes":{"centerid":"000000000"}};
        });
        $("#sysMgmt-btn-add").off().on("click", function () {
            var centername = '',
                upCode = '';
            if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                centername = "-";
            } else {
                centername = self.treeVue.selected.attributes.centername;
            }
            if("000000000" == self.treeVue.selected.attributes.centerid){
                upCode = "-";
            } else {
                upCode = self.treeVue.selected.attributes.itemid;
            }
            var addHTML = $(".codeTable-create").html();
            parent.Common.popupShow(addHTML);
            parent.$('#freeuse3').multipleSelect({
            	placeholder: "请选择",
            	selectAll: false,
            	maxHeight: 120,
            });
            parent.$(".ms-parent").attr("style","width:300px;margin-top:0px");
            parent.$(".ms-drop").attr("style","margin-top:0px");
            parent.$(".reservation-popup-title span").eq(0).text("所属中心：" + centername);
            parent.$(".reservation-popup-title span").eq(1).text("上级编码：" + upCode);

            setTimeout(function(){
                regDate(1);
            },100);
        });
        $("#sysMgmt-btn-edit").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selected.length > 1) {
                parent.Common.editMore();
                return;
            }
            var centername = '',
                upCode = '';
            if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                centername = "-";
            } else {
                centername = self.treeVue.selected.attributes.centername;
            }
            if("000000000" == self.treeVue.selected.attributes.centerid){
                upCode = "-";
            } else {
                upCode = self.treeVue.selected.attributes.itemid;
            }
            var editHTML = $(".codeTable-edit").html();
            parent.Common.popupShow(editHTML);
            parent.$('#freeuse32').multipleSelect({
            	placeholder: "请选择",
            	selectAll: false,
            	maxHeight: 120,
            });
            parent.$(".ms-parent").attr("style","width:300px;margin-top:0px");
            parent.$(".ms-drop").attr("style","margin-top:0px");
            if(selected[0].freeuse3!=null && selected[0].freeuse3!="")
            parent.$('#freeuse32').multipleSelect('setSelects',selected[0].freeuse3.split(","));
            self.olditemid = selected[0].itemid;
            self.dicid = selected[0].dicid;
            self.oldstat = selected[0].status;
            self.oldfreeuse1 = selected[0].freeuse1;

            parent.$("#itemid1").val(selected[0].itemid);
            parent.$("#itemval1").val(selected[0].itemval);

            if(selected[0].status == '1'){
                parent.$('input[name="stat1"]:first').attr('checked','checked');
                parent.$('input[name="stat1"]:last').attr('checked',false);
            } else {
                parent.$('input[name="stat1"]:last').attr('checked','checked');
                parent.$('input[name="stat1"]:first').attr('checked',false);
            }
            parent.$("#freeuse11").val(selected[0].freeuse1);
            parent.$(".reservation-popup-title span").eq(0).text("所属中心：" + centername);
            parent.$(".reservation-popup-title span").eq(1).text("上级编码：" + upCode);

            setTimeout(function(){
                regDate(0);
            },100);
        });
        $("#sysMgmt-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
            if(selected.length < 1) {
                parent.Common.delNone();
                return;
            }
            parent.Common.dialog({
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    self.del();
                }
            });
        });
    },
    createTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'centerid' ,width:40, align:'center', renderer: function (val, item, index) {
                return index + 1;
            }},
            { title:'所属中心', name:'centername' ,width:180, align:'center', renderer: function (val, item, index) {
                if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                    return  "-";
                }
                return self.treeVue.selected.attributes.centername;
            }},
            { title:'上级编码', name:'updicname' ,width:188, align:'center', renderer: function (val, item, index) {
                if("000000000" == self.treeVue.selected.attributes.centerid){
                    return  "-";
                }
                return self.treeVue.selected.attributes.itemid + '-' + self.treeVue.selected.attributes.itemval;
            }},
            { title:'当前编码', name:'itemid' ,width:100, align:'center'},
            { title:'当前名称', name:'itemval' ,width:100, align:'center'},
            { title:'状态', name:'status' ,width:40, align:'center', renderer: function (val, item, index) {
                if("000000000" == self.treeVue.selected.id && "000000000" == self.treeVue.selected.attributes.centerid){
                    return  "-";
                }else{
                    if(val == '1'){
                        return "开放";
                    }else{
                        return "关闭";
                    }
                }
            }},
            { title:'信息过期时间', name:'freeuse1' ,width:100, align:'center',renderer: function (val, item, index) {
                if(""== val || val == null){
                    return  "-";
                }else{
                    return val;
                }
            }},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#codeTable').mmGrid({
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
    },
    add: function () {
        var self = this;
        var itemid = parent.$(".wechat-baseQuery-popup-create input").eq(0).val(),
            itemval = parent.$(".wechat-baseQuery-popup-create input").eq(1).val(),
            freeuse3=parent.$("#freeuse3").val();
            stat = parent.$('input[name="stat"]:checked').val(),
            freeuse4=parent.$('input[name="freeuse4"]:checked').val();
            freeuse1 = parent.$('#freeuse1').val();
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "编码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "名称不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(freeuse3==null) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "渠道不能为空！",
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
            url: "./page70701Add.json",
            datatype: "json",
            data: { 
                'task':'add',
                'centerid':  self.tempCenterid, 
                'updicid': self.tempUpdicid,
                'itemid': itemid,
                'itemval': itemval ,
                'stat':stat,
                'freeuse1':freeuse1,
                'freeuse3':freeuse3.toString(),
                'freeuse4':freeuse4
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
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
    edit: function () {
        var self = this;
        console.log(parent.$('input[name="stat1"]:checked').val())
        var itemid = parent.$("#itemid1").val(),
            itemval = parent.$("#itemval1").val(),
            stat = parent.$('input[name="stat1"]:checked').val(),
            freeuse1 = parent.$('#freeuse11').val();
        	freeuse32=parent.$("#freeuse32").val();
        	freeuse42=parent.$('input[name="freeuse42"]:checked').val();
        if(itemid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "编码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(itemval.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "名称不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(freeuse32==null) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "渠道不能为空！",
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
            url: "./page70701Mod.json",
            datatype: "json",
            data: { 
                'task': 'mod', 
                'centerid':  self.tempCenterid, 
                'updicid': self.tempUpdicid, 
                'dicid': self.dicid, 
                'olditemid': self.olditemid, 
                'oldstat':self.oldstat,
                'oldfreeuse1':self.oldfreeuse1,
                'itemid': itemid, 
                'itemval': itemval , 
                'stat':stat,
                'freeuse1':freeuse1,
                'freeuse3':freeuse32.toString(),
                'freeuse4':freeuse42
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
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
    del: function () {
        var self = this;
        var seleced = self.tabler.selectedRows(),
            ids = [];
        seleced.forEach(function (item) {
            ids.push(item.dicid);
        });
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./page70701Del.json",
            datatype: "json",
            data: { 'centerid': self.tempCenterid, 'dicids': ids.join(',') },
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
                            self.getSubTree(self.tempCenterid, self.tempUpdicid);
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
    }
};
$(document).ready(function(){
    if($("#codeTable").length > 0) {
        codeTable.createTree();
        // codeTable.createTable();
        // codeTable.getTable();
    }
});
//获取日期
function regDate(v){
    var elem = '#freeuse1';
    (v==1)?elem='#freeuse1':elem='#freeuse11';
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