/**
 * Created by M on 2016/9/12.
 */
var user = top.userInfo;

var msgMould = {
    tabler:null,
    tree: null,
    tempData: null, // 当前修改的数据
    selectid:null,
    selectName:null,
    channelid:null,
    templateid:null,
    cacheTemplateId: null, // for updateSend 停用、启用
    cacheThemeId: null,
    demoStr: null,
    getCenterList: function () {
        var self = this;
        $.ajax({
            type: "POST",
            url: "./webappcomCenterId.json",
            datatype: "json",
            data: {},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var customerOptions = '<option value="">请选择</option>';
                    for(var i = 0;i<data.mi001list.length;i++){
                        if(user.centerid != '00000000'){
                            if(user.centerid == data.mi001list[i].centerid){
                                customerOptions = '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>'
                            }
                        } else {
                            customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                        }
                    }
                    $('#searchCustomer').html(customerOptions);
                    if(user.centerid != '00000000'){
                        $('.channelSearch').hide();
                        self.getAllData();
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
                }
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    createTree: function (message) {
        var self = this;
        self.tree = new Vue({
            el: ".jtree",
            data: {
                messageTopic: message,
                select: message[0].messageTopicType
            },
            methods: {
                checkCenter: function (id,name) {
                    parent.Common.loading(true);
                    self.selectid = id;
                    self.selectName = name;
                    self.getData(id);
                }
            }
        });
        if(message.length > 0) {
            self.getData(message[0].messageTopicType);
        }
        self.selectid = message[0].messageTopicType;
        self.selectName = message[0].messageTopicTypeName;
        parent.Common.loading(false);
    },
    getData: function (id) {
        var self = this,
            centerid = $('#searchCustomer').val();
        $.ajax({
            type:'POST',
            url:'./webapi411Query.json',
            data:{ "page": 1, 'rows': 10, 'centerid':centerid,'theme': id },
            datatype:'json',
            success:function(data){
                parent.Common.loading(false);
                self.cacheThemeId = id;
                //10:APP  20:微信  70:短信
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    var _html = '',
                        i = data.rows.length,
                        array = ['10','20','70'],
                        array2 = [],
                        arr3 = [];
                    // 显示已有的渠道
                    $.each(data.rows,function(index, el) {
                        var channelname = '',
                            _index = index + 1,
                            sendFont = '';
                        if(el.channel == '10')channelname='APP';
                        if(el.channel == '20')channelname='微信';
                        if(el.channel == '70')channelname='短信';
                        sendFont = el.freeuse2 == '0' ? '停用' : '启用';
                        _html += "<li><span>"+_index+"</span><span>"+channelname+"</span><span><a href='javascript:;' class='color-green' onclick='editChannel("+JSON.stringify(el)+",\""+channelname+"\")'>编辑</a> | <a href='javascript:;' class='del' onclick='delChannel(\""+el.templateid+"\")'>删除</a></span><span><a href='javascript:;' class='color-green' onclick='updateSend(\""+el.templateid+"\",\"" + el.freeuse2 + "\")'>"+sendFont+"</a></span></li>";
                        array2.push(el.channel)
                    });
                    for (key in array) {
                        var stra = array[key];
                        var count = 0;
                        for(var j= 0; j < array2.length; j++){
                            var strb = array2[j];
                            if(stra == strb) {
                                count++;
                            }
                        }
                        if(count===0) {
                            arr3.push(stra);
                        }
                    }
                    // 显示未有的渠道
                    $.each(arr3,function(index, el) {
                        var channelname = '',
                            _index = i + 1 + index;
                        if(el == '10')channelname='APP';
                        if(el == '20')channelname='微信';
                        if(el == '70')channelname='短信';
                        _html += "<li><span>"+_index+"</span><span>"+channelname+"</span><span><a href='javascript:;' class='color-green' onclick='addMould(\""+channelname+"\",\""+el+"\")'>添加</a></span><span></span></li>";
                    });

                    $('.contentRight .info').html(_html);

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
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
    getTableData: function (templateid) {
        var self = this;
        $.ajax({
            type:'POST',
            url:'./webapi411Detail.json',
            data: {'templateid':templateid},
            datatype:'json',
            success:function(data){
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
           { title:'序号', name:'id' ,width:100, align:'center', renderer: function (val, item, index) {
                        return index+1;
                    }},
           { title:'模板数据项', name:'templatedata' ,width:220, align:'center'},
           { title:'接口数据项', name:'apidata' ,width:220, align:'center' , renderer: function (val, item, index) {
                var temp = '';
                if(val == '' || !val && typeof(val)!="undefined" && val!=0){
                    temp += '<input type="text" value="" class="info-area-name show" />';
                }else{
                    temp += '<input type="text" value="'+ val +'" class="info-area-name show" />';
                }
                return temp;
            }},
           { title:'关键字', name:'templatekey' ,width:180, align:'center', renderer: function (val, item, index) {
                var temp = '';
                if(val == 1){
                    temp += '<input type="radio" name="temKey"  value="1" checked="checked" />';
                } else {
                    temp += '<input type="radio" name="temKey"  value="0" />';
                }
                return temp;
            }}
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#table').mmGrid({
                    multiSelect: false,// 多选
                    checkCol: false, // 选框列
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
    add: function () {
        var self = this;
        var centerid = $('#searchCustomer').val(),
            channel  = self.channelid,
            theme = self.selectid,
            channeltemplate = $("#channeltemplate").val(),
            templatecontent = $("#template-header").val() + '_&', // '_&'是大项分隔符，'_*'是li分隔符，'^&'是li下的左右分隔符
            isWrapBreak = $(".orgManage-popup-from input[name='isWrapBreak']:checked").val();
        if(channeltemplate.length < 1) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "渠道模板不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        // if(templatecontent.length < 1) {
        //     parent.$(".popup-container").hide();
        //     parent.Common.dialog({
        //         type: "warning",
        //         text: "模板内容不能为空！",
        //         okShow: true,
        //         cancelShow: false,
        //         okText: "确定",
        //         ok: function () {
        //         }
        //     });
        //     return;
        // }
        // 循环遍历模板内容
        var liLength = $("#template-container ul li").length,
            templateConArr = '';
        for(var i = 0; i<liLength; i++) {
            templateConArr+=$("#template-container ul li").eq(i).find(".template-left-title").val() + '^&' + $("#template-container ul li").eq(i).find(".template-right-info").val() + '_*';
        }
        templateConArr = templateConArr.substr(0, templateConArr.length-2);
        templatecontent = templatecontent + templateConArr + '_&' + $("#template-bottom").val();
        $.ajax({
            type:'POST',
            url:'./webapi411Add.json',
            data:{ 
                "centerid": centerid, 
                'channel': channel, 
                'theme': theme,
                'channeltemplate':channeltemplate,
                'templatecontent':templatecontent,
                'freeuse1': isWrapBreak
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
                            console.log(data.templateid);
                            self.getTableData(data.templateid);
                            self.getData(self.selectid);
                        }
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
            error:function () {
                parent.Common.ajaxError();
            }
        });
    },
    edit: function () {
        var self = this;
        var centerid = $('#searchCustomer').val(),
            channel  = self.channelid,
            theme = self.selectid,
            templateid = self.templateid,
            channeltemplate = $("#channeltemplate").val(),
            templatecontent = $("#template-header").val() + '_&', // '_&'是大项分隔符，'_*'是li分隔符，'^&'是li下的左右分隔符
            isWrapBreak = $(".orgManage-popup-from input[name='isWrapBreak']:checked").val();
        if(channeltemplate.length < 1) {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "渠道模板不能为空！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                }
            });
            return;
        }
        // if(templatecontent.length < 1) {
        //     parent.$(".popup-container").hide();
        //     parent.Common.dialog({
        //         type: "warning",
        //         text: "模板内容不能为空！",
        //         okShow: true,
        //         cancelShow: false,
        //         okText: "确定",
        //         ok: function () {
        //         }
        //     });
        //     return;
        // }
        // 循环遍历模板内容
        var liLength = $("#template-container ul li").length,
            templateConArr = '';
        for(var i = 0; i<liLength; i++) {
            templateConArr+=$("#template-container ul li").eq(i).find(".template-left-title").val() + '^&' + $("#template-container ul li").eq(i).find(".template-right-info").val() + '_*';
        }
        templateConArr = templateConArr.substr(0, templateConArr.length-2);
        parent.$(".popup-container").hide();
        parent.Common.dialog({
            type: "warning",
            text: "保存会覆盖原有的映射元素，是否保存？",
            okShow: true,
            cancelShow: true,
            okText: "确定",
            ok: function () {
                templatecontent = templatecontent + templateConArr + '_&' + $("#template-bottom").val();
                parent.Common.loading(true);
                parent.Common.popupClose();
                $.ajax({
                    type:'POST',
                    url:'./webapi411Update.json',
                    data:{ 
                        'templateid':templateid,
                        'centerid': centerid, 
                        'channel': channel, 
                        'theme': theme,
                        'channeltemplate':channeltemplate,
                        'templatecontent':templatecontent,
                        'freeuse1': isWrapBreak
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
                                    $("#save-demo-btn").show();
                                    self.getTableData(self.templateid);
                                    self.getData(self.tree.select);
                                }
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
                    error:function () {
                        parent.Common.ajaxError();
                    }
                });
            }
        });
        
    },
    del: function (templateid) {
        var self = this;
        parent.Common.loading(true);
        
        $.ajax({
            type: "POST",
            url: "./webapi411Remove.json",
            datatype: "json",
            data: {templateid:templateid},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getData(self.selectid)
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    save: function(arr){
        var self = this;
        parent.Common.loading(true);

        $.ajax({
            type: "POST",
            url: "./webapi412003.json",
            datatype: "json",
            data: {datalist:JSON.stringify(arr)},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getData(self.selectid)
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    getAllData:function(){
        var centerid = $('#searchCustomer').val();
        if(centerid == "") {
            parent.$(".popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请选择客户名称！",
                okShow: true,
                cancelShow: false,
                okText: "确定",
                ok: function () {
                    parent.$(".popup-container").show();
                }
            });
            return;
        }
         $.ajax({
            type: "POST",
            url: "./webapi12206.json",
            datatype: "json",
            data: {centerid:centerid,dicparam:"message_topic_type"},
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    msgMould.createTree(data.rows);
                    $('#page1sub').show();
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.ajaxError();
            }
        });
    },
    btnClick: function(){
        var self = this;

        $('#orgManage-btn-add,.flowSet a').click(function(e){
            var createHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(createHTML);
        });
        $('#orgManage-btn-modify').click(function(e){
            $('#buiCode1').attr("value", msgMould.tempData.userflow);
            $('#buiName1').attr("value", msgMould.tempData.bussinessflow);

            var createHTML2 = $(".orgManage-popup-edit-container1").html();
            parent.Common.popupShow(createHTML2);
        });

        $('#orgManage-info-goBack').click(function(){
            $('#page1').show();
            $('#page2').hide();
            $("#channeltemplate").val('');
            //$("#templatecontent").val('');
            // 清空模版内容
            $("#template-header").val('');
            $("#template-bottom").val('');
            $("#template-container ul").empty().append('<li>' +
                '<input type="text" class="template-left-title" placeholder="请填写关键字">' +
                '<input type="text" class="template-right-info template-gray-input" placeholder="{info}">' +
                '</li>');
            $(".orgManage-popup-from li input[name='isWrapBreak']").eq(0).prop('checked', true);
            changeDemoStatus("1");
            $(".right-contentBox").html("");
            $(".right-contentDemoBox").html("");
        });

        $('#btnquery').click(function(){
            self.getAllData();
        });

        $('.jtree a').click(function(){
            $(this).addClass('on').siblings('a').removeClass('on');
        });

        $('.jtree a span').click(function(){
            $(this).parent('a').addClass('on').siblings('a').removeClass('on');
        });

        $('.jtree').height($(window).height());

        // 添加模板
        $('#saveTem').click(function(){
            msgMould.add();
        });

        // 编辑模板
        $('#editTem').click(function(){
            msgMould.edit();
        });

        $('#orgManage-info-save').click(function(){
            var self = msgMould,
                arr = self.tabler.rows(),
                index = 0;
            // var centerid = $('#searchCustomer').val(),
            //     channeltemplate = $("#channeltemplate").val(),
            //     templatecontent = $("#templatecontent").val();
            // if(channeltemplate.length < 1) {
            //     parent.$(".popup-container").hide();
            //     parent.Common.dialog({
            //         type: "warning",
            //         text: "渠道模板不能为空！",
            //         okShow: true,
            //         cancelShow: false,
            //         okText: "确定",
            //         ok: function () {
            //         }
            //     });
            //     return;
            // }
            // if(templatecontent.length < 1) {
            //     parent.$(".popup-container").hide();
            //     parent.Common.dialog({
            //         type: "warning",
            //         text: "模板内容不能为空！",
            //         okShow: true,
            //         cancelShow: false,
            //         okText: "确定",
            //         ok: function () {
            //         }
            //     });
            //     return;
            // }
            $('#table input[name="temKey"]').each(function(i,t){
                if(t.checked) index = i;
            });

            arr.forEach(function (val,i,array) {
                val.apidata = $('#table .show').eq(i).val();
                i==index?val.templatekey=1:val.templatekey=0;
            });

            
            msgMould.save(arr);
        });

        // 添加关键词
        $(".temlate-container").on('click', '.template-add-keywords', function () {
            $(".temlate-container ul").append('<li>' +
                    '<input type="text" class="template-left-title" placeholder="请填写关键字">' +
                    '<input type="text" class="template-right-info template-gray-input" placeholder="{info}">' +
                    '<span class="template-li-del">删除</span>' +
                '</li>');
        });
        // 删除每条
        $(".temlate-container").on('click', '.template-li-del', function () {
            var _this = $(this);
            _this.parent('li').remove();
        });
        // 维护示例
        $("#save-demo-btn").off().on("click", function () {
            var editHTML = $('#demoPopup').html();
            parent.Common.popupShow(editHTML);
            parent.$("#demoPopupTextarea").val(self.demoStr.replace(/<br\s*\/?\s*>/ig,"\r").replace(/<\/a>/ig,""));
        });
    },
    saveDemo: function () {
        // 维护示例提交
        var self = this;
        var templatedemo = parent.document.getElementById('demoPopupTextarea').value;
        templatedemo = templatedemo.replace(/\r/ig, "").replace(/\n/ig, "<br />");
        parent.$('#popup-container').hide();
        parent.Common.loading(true);
        $.ajax({
            type: "POST",
            url: "./webapi411UpdateSend.json",
            datatype: "json",
            data: {
                "templateid": self.cacheTemplateId,
                "templatedemo": templatedemo
            },
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
                            msgMould.getData(self.cacheThemeId);
                            self.demoStr = templatedemo;
                            $(".right-contentDemoBox").eq(0).html(templatedemo);
                            $(".right-contentDemoBox").eq(1).text(templatedemo.replace(/<br\s*\/?\s*>/ig,"\r").replace(/<\/a>/g, ""));
                        }
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
                parent.Common.loading(false);
            },
            error: function(){
                parent.Common.loading(false);
                parent.Common.ajaxError();
            }
        });
    }
};


// 编辑该渠道下的模板
function editChannel(item,channelname){

    var centername = $('#searchCustomer').find("option:selected").text();
    $('#centerName').text(centername);
    $('#themeName').text(msgMould.selectName);
    $('#channelName').text(channelname);
    $("#channeltemplate").val(item.channeltemplate);
    $("#save-demo-btn").show();
    // for updateSend 停用、启用
    msgMould.cacheTemplateId = item.templateid;
    msgMould.cacheThemeId = item.theme;
    msgMould.demoStr = item.templatedemo;
    if(item.templatedemo != null) {
        $(".right-contentDemoBox").eq(0).html(item.templatedemo);
        $(".right-contentDemoBox").eq(1).text(item.templatedemo.replace(/<br\s*\/?\s*>/ig,"\r").replace(/<\/a>/g, ""));
    }
    // 消息模版内容回显
    try {
        var templateConObj = item.templatecontent.split('_&');
        $("#template-header").val(templateConObj[0]);
        if (item.freeuse1 == "1") {
            // 换行 有底部
            $("#template-container,.template-tips").show();
            $("#templatecontent").hide();
            $("#template-bottom").val(templateConObj[2]);
            var tempHtml = '',
                rightBrHtml = '',
                rightHtml = '',
                tempConContent = templateConObj[1].split('_*');
            tempConContent.forEach(function (val, index) {
                var lastArr = val.split('^&');
                if(index == 0) {
                    tempHtml += '<li>' +
                        '<input type="text" class="template-left-title" placeholder="请填写关键字" value="'+ lastArr[0] +'">' +
                        '<input type="text" class="template-right-info template-gray-input" placeholder="{info}" value="'+ lastArr[1] +'">' +
                        '</li>';
                } else {
                    tempHtml += '<li>' +
                        '<input type="text" class="template-left-title" placeholder="请填写关键字" value="'+ lastArr[0] +'">' +
                        '<input type="text" class="template-right-info template-gray-input" placeholder="{info}" value="'+ lastArr[1] +'">' +
                        '<span class="template-li-del">删除</span>' +
                        '</li>';
                }
                rightBrHtml += lastArr[0] + "<span>" + lastArr[1] + "</span><br />";
                rightHtml += lastArr[0] + "<span>" + lastArr[1] + "</span>";
            });
            $("#template-container ul").empty().append(tempHtml);
            if(templateConObj.length > 2) {
                templateConObj[0] = templateConObj[0].replace(/\{(.+?)\}/g, '<span>{$1}</span>');
                templateConObj[2] = templateConObj[2].replace(/\{(.+?)\}/g, '<span>{$1}</span>');
                rightBrHtml = templateConObj[0] + "<br />"+ rightBrHtml + templateConObj[2] + "<br />";
                rightHtml = templateConObj[0] + rightHtml + templateConObj[2];
            } else {
                templateConObj[0] = templateConObj[0].replace(/\{(.+?)\}/g, '<span>{$1}</span>');
                rightBrHtml = templateConObj[0] + "<br />"+ rightBrHtml;
                rightHtml = templateConObj[0] + rightHtml;
            }
            $(".right-contentBox").eq(0).empty().html(rightBrHtml);
            $(".right-contentBox").eq(1).empty().html(rightHtml);
        } else {
            // 不换行
            $("#template-container,.template-tips").hide();
            $("#templatecontent").show().val(item.templatecontent);
            $(".right-contentBox").text(item.templatecontent);
        }
        var checkboxIndex = item.freeuse1 == "1" ? 0 : 1;
        $(".orgManage-popup-from li input[name='isWrapBreak']").eq(checkboxIndex).prop('checked', true);
        changeDemoStatus(item.freeuse1);
    } catch (err) {
        // 原有逻辑 防止页面抛错
        $("#templatecontent").val(item.templatecontent);
    }

    msgMould.channelid = item.channel; 
    msgMould.templateid = item.templateid;

    msgMould.getTableData(item.templateid);

    $('#page1,#saveTem').hide();
    $('#page2,#editTem').show();

}

// 删除渠道
function delChannel(templateid){
    parent.Common.dialog({
        type: "tips",
        text: "确定删除该条记录？",
        okShow: true,
        cancelShow: true,
        okText: "确定",
        cancelText: "取消",
        ok: function () {

            msgMould.del(templateid);
        }
    });
    
}

// 开启、停用渠道
function updateSend(id, flag) {
    flag = flag == "1" ? "0" : "1";
    parent.Common.loading(true);
    $.ajax({
        type: "POST",
        url: "./webapi411UpdateSend.json",
        datatype: "json",
        data: {
            "templateid": id,
            "freeuse2": flag
        },
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
                        msgMould.getData(msgMould.cacheThemeId);
                    }
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
            parent.Common.loading(false);
        },
        error: function(){
            parent.Common.loading(false);
            parent.Common.ajaxError();
        }
    });
}

// 显示添加模板区域
function addMould(channelname,el){
    var centername = $('#searchCustomer').find("option:selected").text();
    $('#centerName').text(centername);
    $('#themeName').text(msgMould.selectName);
    $('#channelName').text(channelname);
    msgMould.channelid = el;
    msgMould.cacheThemeId = el;
    msgMould.demoStr = "";
    msgMould.getTableData('NULL');
    $("#save-demo-btn").hide();

    $('#page1,#editTem').hide();
    $('#page2,#saveTem').show();
}

// 更改模版右侧显示示例
function changeDemoStatus (index) {
    switch (index) {
        case "1":
            $("#template-demo-break").show();
            $("#template-demo-not-break").hide();
            break;
        case "0":
            $("#template-demo-break").hide();
            $("#template-demo-not-break").show();
            break;
    }
}

$(function(){
    
    parent.Common.loading(true);
    msgMould.getCenterList();
    msgMould.btnClick();

});