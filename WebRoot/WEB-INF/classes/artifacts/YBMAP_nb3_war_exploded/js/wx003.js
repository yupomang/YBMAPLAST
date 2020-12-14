/**
 * Created by M on 2016/9/31.
 */
var user = top.userInfo;
var wx003 = {
    treeVue: null,
    taler: null,
    centerid: null,
    tableData:null,
    sontabler: null,
    sontableData:null,
    allData:null,
    index:null,//父菜单编辑用
    sonindex:null,//子菜单编辑用
    sub_button:null,//父菜单编辑用
    vue00202:null,
    keyname:null,
    createTable: function (data) {
        var self = this;
        self.tableData = data;
        var cols = [
            { title:'序号', name:'id', width:'30', align: 'center', renderer: function (val, item, index) {
                return index + 1;
            } },
            { title:'菜单名称', name:'name', width:'150', align: 'center'},
            { title:'有子菜单', name:'sub_button', width:'60', align: 'center', renderer: function (val, item, index) {
                if(typeof(val) == 'undefined'){
                    return '无';
                }else{
                    return '有';
                }
            }},
            { title:'菜单类型', name:'type', width:'60', align: 'center', renderer: function (val, item, index) {
                if(typeof(val) != 'undefined'){
                    if(val == 'view'){
                        return '链接事件';
                    }
                    if(val == 'click'){
                        return '点击事件';
                    }
                }else{
                    return '-';
                }
            }},
            { title:'菜单参数', name:'url', width:'350', align: 'center', renderer: function (val, item, index) {
                if(typeof(val) != 'undefined'){
                    return val;
                }else{
                    return '-';
                }
            }},
            { title:'操作', name:'url', width:'100', align: 'center', renderer: function (val, item, index) {
                var temp = "";
                if (typeof(val) == 'undefined') {
                    temp += "<a href='javascript:;' onclick='showSonMenu(" + JSON.stringify(item) + ", " +index+");' title='编辑子菜单'>编辑子菜单</a>";
                    temp += " | <a href='javascript:;' onclick='wx003.del(" + index + ");' class='red-link' title='删除'>删除</a>";
                } else {
                    temp = "<a href='javascript:;' onclick='wx003.del(" + index + ");' class='red-link' title='删除'>删除</a>";
                }
                return temp;
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#menu-table').mmGrid({
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
        }
        
        parent.Common.loading(false);
    },
    btnClick: function () {
        var self = this;

        // 查询
        $(".btnquery").off().on("click", function () {
            var centerid = $('#customerName').val();
            self.getTableData(centerid);
            self.get00202Data(centerid);
        });

        // 编辑微信号
        $("#sysMgmt-btn-addwx").off().on("click", function () {
            var centername = $('.wx003title p').eq(0).text(),
                weixinId = $('.wx003title span').text();
            
            var addHTML = $(".configMenu-createwx").html();
            parent.Common.popupShow(addHTML);
            parent.$('#centername').val(centername);
            parent.$('#wxNum').val(weixinId);
        });
        // 发布
        $("#sysMgmt-btn-send").off().on("click", function () {
            var centername = $('.wx003title p').eq(0).text();
            if(centername == ''){
                parent.Common.dialog({
                    type: "warning",
                    text: "微信号不能为空！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });

                return;
            }
            self.send();
        });

        // 返回
        $("#son-btn-back").off().on("click", function () {
            $('#menu_area').show();
            $('#son_menu_area').hide();
        });

        $("#sysMgmt-btn-add").off().on("click", function () {
            if(self.tableData.length > 2){
                parent.Common.dialog({
                    type: "warning",
                    text: "一级菜单数不能超过3个",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });

                return;
            }
            var addHTML = $(".configMenu-create").html();
            parent.Common.popupShow(addHTML);
        });
        $("#sysMgmt-btn-edit").off().on("click", function () {
            var selectedData = self.tabler.selectedRows(),
                selected = $("#menu-table").find("tr.selected");
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            
            var editHTML = $(".configMenu-edit").html();
            parent.Common.popupShow(editHTML);

            parent.$("#menu_name1").val(selectedData[0].name);
            parent.$("#type1").val(selectedData[0].type);
            parent.$("#key_vaule1").val(selectedData[0].url);

            if(typeof(selectedData[0].sub_button) == "undefined") {
                parent.$('input[name="hasSon1"]:first').attr('checked',false);
                parent.$('input[name="hasSon1"]:last').attr('checked','checked');
                parent.$('.edit div').eq(2).show();
                parent.$('.edit div').eq(3).show();
            } else {
                parent.$('input[name="hasSon1"]:first').attr('checked','checked');
                parent.$('input[name="hasSon1"]:last').attr('checked',false);
                parent.$('.edit div').eq(2).hide();
                parent.$('.edit div').eq(3).hide();
                self.sub_button = selectedData[0].sub_button;
            }

            self.index = selected.find('td:eq(1)').text() - 1;
        });

        // 父菜单上移按钮
        $("#sysMgmt-btn-up").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.tabler.selectedRowsIndex();
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
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val - 1);
            });
            self.tableData = newData;

        });

        // 父菜单下移按钮
        $("#sysMgmt-btn-down").off().on("click", function () {
            var infoData = self.tableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.tabler.selectedRowsIndex();
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
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.tabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.tabler.select(val + 1);
            });
            self.tableData = newData;
        });

        // 父菜单保存顺序
        $("#sysMgmt-btn-save").off().on("click", function () {
            self.subSave();
        });



        // 子菜单上移按钮
        $("#son-btn-up").off().on("click", function () {
            var infoData = self.sontableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.sontabler.selectedRowsIndex();
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
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i], selectedDataIndex[i] - 1);
            }
            self.sontabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.sontabler.select(val - 1);
            });
            self.sontableData = newData;

        });

        // 子菜单下移按钮
        $("#son-btn-down").off().on("click", function () {
            var infoData = self.sontableData,
                newData = infoData.slice(0),
                selectedDataIndex = self.sontabler.selectedRowsIndex();
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
                newData = an.arrayExchangeOrder(newData, selectedDataIndex[i - 1], selectedDataIndex[i - 1] + 1);
            }
            self.sontabler.load(newData);
            selectedDataIndex.forEach(function (val) {
                self.sontabler.select(val + 1);
            });
            self.sontableData = newData;
        });

        // 子菜单保存顺序
        $("#son-btn-save").off().on("click", function () {
            self.sonSubSave();
        });

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

        // 子菜单新增
        $("#son-btn-add").off().on("click", function () {
            if(self.sontableData.length > 4){
                parent.Common.dialog({
                    type: "warning",
                    text: "二级菜单数不能超过5个",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {
                    }
                });

                return;
            }
            var addHTML = $(".configMenu-create-son").html();
            parent.Common.popupShow(addHTML);
        });

        // 子菜单修改
        $("#son-btn-edit").off().on("click", function () {
            var selectedData = self.sontabler.selectedRows(),
                selected = $("#son-menu-table").find("tr.selected");
            if(selectedData.length < 1) {
                parent.Common.editNone();
                return;
            }
            if(selectedData.length != 1) {
                parent.Common.editMore();
                return;
            }
            
            var editHTML = $(".configMenu-edit-son").html();
            parent.Common.popupShow(editHTML);

            parent.$("#menu_name3").val(selectedData[0].name);
            parent.$("#type3").val(selectedData[0].type);
            parent.$("#key_vaule3").val(selectedData[0].url);


            self.sonindex = selected.find('td:eq(1)').text() - 1;

        });

    },
    createTreeVue: function () {
        var self = this;
        self.treeVue = new Vue({
            el: '.code-table-tree',
            data: {
                items: []
            },
            methods: {
                getSubTree: function (data) {
                    parent.Common.loading(true);
                    self.getSubTree(data);
                }
            }
        });
        // self.vue00202 = new Vue({
        //     el: '.linfo',
        //     data: {
        //         items: []
        //     },
        //     methods: {
        //         getFuncInfo: function (item) {
        //             console.log(item)
        //         }
        //     }
        // });
    },
    getTableData: function (centerid) {
        parent.Common.loading(true);
        var self = this;
        $.ajax({
            type: "POST",
            url: "./weixinapi00301.json",
            datatype: "json",
            data: { 'regionId': centerid },
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.errcode == "0") {
                    parent.Common.loading(false);
                    self.allData = data;
                    var _data = data.rows[0].button;
                    // self.menuList = _data;
                    
                    self.treeVue.items = _data;
                    self.createTable(_data);
                    showWX(data.rows[0]);
                    
                }else {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
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
    addwx: function(){
        var self = this,
            weixinId = parent.$('#wxNum').val();
        if(weixinId == ''){
            parent.Common.dialog({
                type: "warning",
                text: "微信号码不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });

            return;
        }
        $('.wx003title span').text(weixinId);
        self.allData.weixinId = weixinId;
        console.log(self.allData)
    },
    add: function () {

        initializeInput();
        
        var self = this;
        var name = parent.$("#menu_name").val(),
            hasSon = parent.$('input[name="hasSon"]:checked').val(),
            type = parent.$("#type").val(),
            url = parent.$("#key_vaule").val();


        if(name.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(hasSon != 1) {
            if(type.length < 1) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "warning",
                    text: "菜单类型不能为空！",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                return;
            }
            if(url.length < 1) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "warning",
                    text: "菜单参数不能为空！",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                return;
            }
        }

        parent.Common.loading(true);
        parent.Common.popupClose();

        var button = self.allData.rows[0].button;

        var newline = [];
        if(hasSon != 1){
            newline = {"name":name,"type":type,"url":url};
        }else{
            newline = {"name":name,"sub_button":[]};
        }

        button.push(newline)

        self.createTable(self.allData.rows[0].button);
    },
    edit: function () {
        var self = this,
            name = parent.$("#menu_name1").val(),
            hasSon = parent.$('input[name="hasSon1"]:checked').val(),
            type = parent.$("#type1").val(),
            url = parent.$("#key_vaule1").val();

        if(name.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(hasSon != 1) {
            if(type.length < 1) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "warning",
                    text: "菜单类型不能为空！",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                return;
            }
            if(url.length < 1) {
                parent.$("#popup-container").hide();
                parent.Common.dialog({
                    type: "warning",
                    text: "菜单参数不能为空！",
                    okShow: true,
                    cancelShow: true,
                    okText: "确定",
                    ok: function () {
                        parent.$("#popup-container").show();
                    }
                });
                return;
            }
        }

        parent.Common.loading(true);
        parent.Common.popupClose();

        var button = self.allData.rows[0].button;

        var newline = [];
        if(hasSon != 1){
            newline = {"name":name,"type":type,"url":url};
        }else{
            newline = {"name":name,"sub_button":self.sub_button};
        }
        var index = self.index;

        button.splice(index,1,newline)

        self.createTable(self.allData.rows[0].button);
    },
    del: function (index) {
        var self = this,
            button = self.allData.rows[0].button;
        parent.Common.dialog({
            type: "tips",
            text: "是否确认删除？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            ok: function () {
                button.splice(index,1)
                self.createTable(self.allData.rows[0].button);
            }
        });
    },
    addson: function () {

        initializeInput();
        
        var self = this;
        var name = parent.$("#menu_name2").val(),
            type = parent.$("#type2").val(),
            url = parent.$("#key_vaule2").val();


        if(name.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(url.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单参数不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }

        parent.Common.loading(true);
        parent.Common.popupClose();
        var index = self.index,
            sub_button = self.allData.rows[0].button[index].sub_button,
            newline = {"name":name,"type":type,"url":url};

        sub_button.push(newline)

        self.createTable(self.allData.rows[0].button);
        self.showSonTable(self.allData.rows[0].button[index].sub_button);
    },
    editson: function () {
        var self = this,
            name = parent.$("#menu_name3").val(),
            type = parent.$("#type3").val(),
            url = parent.$("#key_vaule3").val();

        if(name.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(url.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "菜单参数不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }

        parent.Common.loading(true);
        parent.Common.popupClose();

        var index = self.index,
            sub_button = self.allData.rows[0].button[index].sub_button,
            newline = {"name":name,"type":type,"url":url};
    
        sub_button.splice(self.sonindex,1,newline)

        self.createTable(self.allData.rows[0].button);
        self.showSonTable(self.allData.rows[0].button[index].sub_button);
    },
    delSon: function (item,i) {
        var self = this,
            index = self.index;
        if(typeof item == "string") {
            item = JSON.parse(item);
        }
        parent.Common.dialog({
            type: "tips",
            text: "是否确认删除？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            ok: function () {
                self.tableData[index].sub_button.splice(i,1);
                self.allData.button = self.tableData;
                console.log(JSON.stringify(self.allData.rows[0].button[index]))
                self.createTable(self.allData.rows[0].button);
                self.showSonTable(self.allData.rows[0].button[index].sub_button);
            }
        });
        
    },
    subSave: function(){
        var self = this,
            newtable = self.tableData;

        self.allData.rows[0].button = newtable;
        parent.Common.dialog({
            type: "success",
            text: "保存顺序成功！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {
                self.createTable(newtable);
                self.treeVue.items = newtable;
            }
        });
        
    },
    setURL: function(){
        parent.$(".showAdd,.showFunc").hide();
        parent.$(".showSetURL").show();
    },
    saveurl: function(){
        var appid = parent.$("#u_appid").val(),
            uri = parent.$("#u_uri").val(),
            state1 = parent.$("#u_wxid").val(),
            state2 = parent.$("#u_cond").val();

        if(appid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "appid不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(uri.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "uri不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(state1.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "微信账号不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(state2.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "功能不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }

        var urivalue = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+appid+'&redirect_uri=';
        if(uri.substring(0,7)=="http://"){          
            urivalue = urivalue+"http%3A%2F%2F"+uri.substring(7).replace("/","%2F");
        }else if(uri.substring(0,8)=="https://"){           
            urivalue = urivalue+"https%3A%2F%2F"+uri.substring(8).replace("/","%2F");
        }else{
            urivalue = urivalue+"http%3A%2F%2F"+uri.replace("/","%2F");
        }
        urivalue = urivalue+'&response_type=code&scope=snsapi_base&state='+state1+'.'+state2+'#wechat_redirect';

        parent.$('.key_vaule').val(urivalue);

        parent.$(".showAdd").show();
        parent.$(".showSetURL,.showFunc").hide();
    },
    closeurl:function(){
        parent.$(".showSetURL,.showFunc").hide();
        parent.$(".popup-relative").css('width','754px');
        parent.$(".showAdd").show();
    },
    showSonTable: function(data){
        var self = this;
        self.sontableData = data;
        var cols = [
            { title:'序号', name:'centerid', width:'30', align: 'center', renderer: function (val, item, index) {
                return index + 1;
            } },
            { title:'菜单名称', name:'name', width:'150', align: 'center'},
            { title:'菜单类型', name:'type', width:'60', align: 'center', renderer: function (val, item, index) {
                if(typeof(val) != 'undefined'){
                    if(val == 'view'){
                        return '链接事件';
                    }
                    if(val == 'click'){
                        return '点击事件';
                    }
                }else{
                    return '-';
                }
            }},
            { title:'菜单参数', name:'url', width:'495', align: 'center', renderer: function (val, item, index) {
                if(typeof(val) != 'undefined'){
                    return val;
                }else{
                    return '-';
                }
            }},
            { title:'操作', name:'url', width:'30', align: 'center', renderer: function (val, item, index) {
                var temp = "<a href='javascript:;' onclick='wx003.delSon(" + JSON.stringify(item) + ","+index+");' class='red-link' title='删除'>删除</a>";
                return temp;
            }}
        ];
        if(self.sontabler != null) {
            self.sontabler.load(data);
        } else {
            self.sontabler = $('#son-menu-table').mmGrid({
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
        }
        
        parent.Common.loading(false);
    },
    setFunc: function(){
        parent.$(".showAdd,.showSetURL").hide();
        parent.$(".popup-relative").css('width','800px');
        parent.$(".showFunc").show();
    },
    saveKeyname: function(keyname,id){
        var self = this;
        self.keyname = keyname;
        parent.$(".listI").css('background','#fff');
        parent.$("#"+id).css('background','#fbec88');
    },
    saveFunc: function(){
        var self = this;
        parent.$('.key_vaule').val(self.keyname);
        self.closeurl();
    },
    closeFunc:function(){
        var self = this;
        var k = parent.$('.key_vaule').val();
        k==''?parent.$('.key_vaule').val(''):parent.$('.key_vaule').val(k);
        self.closeurl();
    },
    sonSubSave: function(){
        var self = this,
            newtable = self.sontableData,
            index = self.index;

        self.allData.rows[0].button[index].sub_button = newtable;
        parent.Common.dialog({
            type: "success",
            text: "保存顺序成功！",
            okShow: true,
            cancelShow: false,
            okText: "确定",
            ok: function () {
                self.createTable(self.allData.rows[0].button);
                self.showSonTable(newtable);
                self.treeVue.items = self.allData.rows[0].button;
            }
        });
    },
    send: function(){
        //value:{"regionId":"00087100","weixinId":"gh_6d2daa0d92c1","enable":false,"button":[{"name":"特色服务1","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.ser_special_service#wechat_redirect"},{"name":"业务办理","sub_button":[{"name":"业务预约","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.ser_online_reserve#wechat_redirect"},{"name":"在线排队","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.line_info#wechat_redirect"},{"name":"业务办理资格校验","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.qual_check#wechat_redirect"}]},{"name":"我的","sub_button":[{"name":"注册与注销","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.ser_account_bind#wechat_redirect"},{"name":"账户查询","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.account_list#wechat_redirect"},{"name":"消息中心","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.message_center#wechat_redirect"},{"name":"消息推送渠道设置","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.ser_special_service#wechat_redirect"},{"name":"消息通知主题定制","type":"view","url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd4ace832d3c64b18&redirect_uri=http%3A%2F%2Fmp.wx.pangjiachen.com%2Fweixin%2Ffuncrouter&response_type=code&scope=snsapi_base&state=gh_6d2daa0d92c1.ser_special_service#wechat_redirect"}]}],"function":null}
        var self = this,
            button = self.allData.rows[0].button,
            regionId = $('#customerName').val(),
            weixinId = $('.wx003title span').text(),
            value = {"regionId":regionId,"weixinId":weixinId,"enable":false,"button":button,"function":null};
        console.log(JSON.stringify(value))
        $.ajax({
            type:'POST',
            url:'./weixinapi00302.json',
            data:{'value':JSON.stringify(value)},
            datatype:'json',
            success:function(data){
                
                if(data.errcode == 0){
                    parent.Common.dialog({
                        type: "success",
                        text: "发布成功！",
                        okShow: true,
                        cancelShow: true,
                        okText: "确定",
                        ok: function () {
                        }
                    });
                }else{
                    parent.Common.dialog({
                        type: "error",
                        text: data.errmsg,
                        okShow: true,
                        cancelShow: true,
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
    get00202Data:function (centerid){
        var self = this,
            _data = {'regionId':centerid};

        $.ajax({
            type:'POST',
            url:'./weixinapi00202.json',
            data: {'centerid':JSON.stringify(_data)},
            datatype:'json',
            success:function(data){
                if(typeof data == 'string'){
                    data = JSON.parse(data);
                }
                if(data.errcode == 0){
                    var handleHelper = Handlebars.registerHelper("addOne",function(index){
                        return index+1;
                    });
                    var myTemplate = Handlebars.compile($("#list-template").html());
                    $('.linfo').html(myTemplate(data.rows));
                    // self.vue00202.items = data.rows;
                }
            },
            error:function(){
                parent.Common.ajaxError();
            }
        });
    },
};
$(document).ready(function(){
    $.ajax({
        type:'POST',
        url:'./webappcomCenterId.json',
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                var customerOptions = '';
                for(var i = 0;i<data.mi001list.length;i++){
                    customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                }
                $('#customerName').html(customerOptions);
                wx003.getTableData(data.mi001list[0].centerid);
            }
        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    
    wx003.get00202Data(user.centerid);
    wx003.createTreeVue();
    wx003.btnClick();
    

    
});

function showWX(data){
    var centername = $("#customerName").find("option:selected").text();
    $('.wx003title p').eq(0).text(centername);
    $('.wx003title span').text(data.weixinId);
}

function initializeInput(){
    parent.$("#u_appid").val('');
    parent.$("#u_uri").val('');
    parent.$("#u_wxid").val('');
    parent.$("#u_cond").val('');
}

function showSonMenu(item,index){
    var self = wx003;
    $('#far_menu_name').text(item.name);
    $('#menu_area').hide();
    $('#son_menu_area').show();

    self.index = index;
    self.showSonTable(item.sub_button);
}


