
/**
 * Created by M on 2016/9/28.
 */
var user = top.userInfo;

var animationSet = {
    pageSize: 10,
    pager: null,
    tabler:null,
    infopager: null,
    infotabler:null,
    flag:false,
    centerid:null,
    createPager: function () {
        var self = this;
        // create pages
        self.pager = pages({
            el: "#orgManage-table-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex) {
                self.getTableData(pageIndex, self.pageSize, false);
            }
        });
        self.getTableData( 1, self.pageSize, true);
    },
    getTableData: function (page, rows, pageRestBool) {
        var self = this,
        centerid = $('#customerName').val(),
        devid = $('#sdevid').val(),
        animatename = $('#sanimatename').val(),
        animatecode = $('#sanimatecode').val();
        $.ajax({
            type:'POST',
            url:'./webapi41104.json',
            data: { 
              'centerid': centerid,
              'devid':devid,
              'animatename':animatename,
              'animatecode':animatecode,
              'page':page,
              'rows':rows
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000" | data.recode == '280001') {
                    self.createTable(data.rows);
                    self.pager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                }else {
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
           { title:'动画名称', name:'animatename' ,width:90, align:'center' },
           { title:'动画编号', name:'animatecode' ,width:100, align:'center' },
           { title:'动画功能描述', name:'animatedescript' ,width:140, align:'center'},
           { title:'客户名称', name:'centerid' ,width:100, align:'center', renderer: function (val, item, index) {
                return $('#customerName option[value='+val+']').text();
            }},
           { title:'设备区分', name:'devid' ,width:80, align:'center' ,renderer: function (val, item, index) {
                return $('#sdevid option[value='+val+']').text();
            }},
           { title:'间隔时间（秒）', name:'intervaltime' ,width:80, align:'center'},
           { title:'循环方式', name:'looptype' ,width:100, align:'center',renderer: function (val, item, index) {
                return $('#looptype option[value='+val+']').text();
            }},
           { title:'图片标准宽度（像素）', name:'imgwidth' ,width:140, align:'center'},
           { title:'图片标准高度（像素）', name:'imgheight' ,width:140, align:'center'},
        ];
        if(self.tabler != null) {
            self.tabler.load(data);
        } else {
            self.tabler = $('#setTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
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
        var self = this,
            table = self.tabler;
        // 新建
        $('#orgManage-btn-add').off().on("click", function () {
            var addHTML = $(".orgManage-popup-edit-container").html();
            parent.Common.popupShow(addHTML);

            parent.$('#centerid').val($('#customerName').val());
        });
        //删除
        $("#orgManage-btn-del").off().on("click", function () {
            var selected = self.tabler.selectedRows();
               
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
                type: "tips",
                text: "是否确认删除？",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    var delIds = [];
                    for(var i=0; i<selected.length; i++) {
                        delIds.push(selected[i].animateid);
                    }
                    self.del(delIds.join(","));
                }
            });
        });
        // 修改
        $('#orgManage-btn-modify').click(function(e){
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
            var editHTML = $(".orgManage-popup-edit-container1").html();
            parent.Common.popupShow(editHTML);

            parent.$('#animatename1').val(selected[0].animatename);
            parent.$('#animatecode1').val(selected[0].animatecode);
            parent.$('#animatedescript1').val(selected[0].animatedescript);
            parent.$('#centerid1').val(selected[0].centerid);
            parent.$('#devid1').val(selected[0].devid);
            parent.$('#intervaltime1').val(selected[0].intervaltime);
            parent.$('#looptype1').val(selected[0].looptype);
            parent.$('#imgwidth1').val(selected[0].imgwidth);
            parent.$('#imgheight1').val(selected[0].imgheight);
            
        });
        // 查询
        $('#btnQuery').off().on("click", function () {
            self.getTableData(1,self.pageSize,true);
        });
        // 图片动画明细设置
        $("#orgManage-btn-active").off().on("click", function () {
            var selected = self.tabler.selectedRows();
               
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
                return;
            }
            if(selected.length > 1){
                parent.Common.dialog({
                    type: "error",
                    text: "只能选择一条信息！",
                    okShow: true,
                    cancelShow: false,
                    okText: "确定",
                    ok: function () {}
                });
                return;
            }
            if(!self.flag){
                self.createInfoPager();
                self.flag = true;
            }else{
                self.getInfoTableData(1,10,true)
            }
            self.centerid = selected[0].centerid;
            
            $('#infoArea').show();
            $('#mainArea').hide();
        });
        // 返回
        $('#orgManage-info-goBack').off().on("click", function () {
            $('#infoArea').hide();
            $('#mainArea').show();
        });
        // 明细新建
        $('#info-btn-add').off().on("click", function () {
            var addHTML = $(".orgManage-popup-edit-container2").html();
            parent.Common.popupShow(addHTML);
            

            parent.$('#file').on('change', function(e){
                parent.$('#magecenterId').val(self.centerid);
                // var name = e.currentTarget.files[0].name;
                // $('#imgpath').text( name );
                parent.$('#formUpImg').ajaxSubmit({
                    dataType : "json",
                    success : function(data) {
                        console.log(data)                  
                        // if(data.recode == SUCCESS_CODE){
                        //     $('#showImg img').remove();
                        //     $('#showImg').append('<img id="imgTPDH" src="'+ data.downloadPath+ '"/>');
                        //     $('#magecenterId').val(data.fileName);
                        // }   
                    },
                    error:function() {
                        parent.Common.ajaxError();
                    }
                });
            });
        });


    },
    add: function () {
        var self = this;
        var animatename = parent.$('#animatename').val(),
            animatecode = parent.$('#animatecode').val(),
            animatedescript = parent.$('#animatedescript').val(),
            centerid = parent.$('#centerid').val(),
            devid = parent.$('#devid').val(),
            intervaltime = parent.$('#intervaltime').val(),
            looptype = parent.$('#looptype').val(),
            imgwidth = parent.$('#imgwidth').val(),
            imgheight = parent.$('#imgheight').val();

        if(animatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "动画名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(animatecode.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "动画编号不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(centerid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "客户名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(devid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "设备区分不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(intervaltime.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "间隔时间不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(looptype.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "循环方式不能为空！",
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
        $.ajax({
            type:'POST',
            url:'./webapi41101.json',
            data: { 
                'animatename': animatename,
                'animatecode':animatecode,
                'animatedescript':animatedescript, 
                'centerid': centerid, 
                'devid': devid ,
                'intervaltime': intervaltime,
                'looptype': looptype,
                'imgwidth':imgwidth,
                'imgheight':imgheight, 
                'animateid': ''
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(1,self.pageSize,true);
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
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    edit: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            animatename = parent.$('#animatename1').val(),
            animatecode = parent.$('#animatecode1').val(),
            animatedescript = parent.$('#animatedescript1').val(),
            centerid = parent.$('#centerid1').val(),
            devid = parent.$('#devid1').val(),
            intervaltime = parent.$('#intervaltime1').val(),
            looptype = parent.$('#looptype1').val(),
            imgwidth = parent.$('#imgwidth1').val(),
            imgheight = parent.$('#imgheight1').val(),
            animateid = selected[0].animateid;

        if(animatename.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "动画名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(animatecode.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "动画编号不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(centerid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "客户名称不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(devid.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "设备区分不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(intervaltime.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "间隔时间不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(looptype.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "循环方式不能为空！",
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
        $.ajax({
            type:'POST',
            url:'./webapi41103.json',
            data: { 
                'animatename': animatename,
                'animatecode':animatecode,
                'animatedescript':animatedescript, 
                'centerid': centerid, 
                'devid': devid ,
                'intervaltime': intervaltime,
                'looptype': looptype,
                'imgwidth':imgwidth,
                'imgheight':imgheight, 
                'animateid': animateid
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(1,self.pageSize,true);
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
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    del:function(animateid){
       parent.Common.loading(true);
       var self = this;
        $.ajax({
            type: "POST",
            url: "./webapi41102.json",
            data: {
                "animateid": animateid
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    self.getTableData(1,self.pageSize,true);
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
    createInfoPager: function () {
        var self = this;

        self.infopager = pages({
            el: "#orgManage-info-pages",
            itemLength: 2300,
            pageSize: 10,
            pageChanged: function (pageIndex) {
                self.getInfoTableData(pageIndex, self.pageSize, false);
            }
        });
        self.getInfoTableData( 1, self.pageSize, true);
    },
    getInfoTableData:function(page,rows,pageRestBool){
       var self = this,
           selected = self.tabler.selectedRows(),
           animateid = selected[0].animateid,
           centerid = selected[0].centerid;
        $.ajax({
            type: "POST",
            url: "./webapi41108.json",
            data: {
                "animateid": animateid,
                'cid':centerid,
                'page':page,
                'rows':rows
            },
            datatype: "json",
            success: function(data) {
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000" | data.recode == '280001') {
                    self.createInfoTable(data.rows);
                    self.infopager.reset({
                        itemLength: data.total,
                        pageSize: self.pageSize,
                        reset: pageRestBool
                    });
                }else {
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
    createInfoTable: function (data) {
        var self = this;
        var cols = [
            { title:'序号', name:'xh' ,width:80, align:'center'},
            { title:'图片名称', name:'imgpath' ,width:190, align:'center' },
            { title:'图片内容链接路径', name:'contentlink' ,width:300, align:'center' },
            { title:'图片切换显示', name:'displaydirection' ,width:140, align:'center',renderer: function (val, item, index) {
                return $('#displaydirection option[value='+val+']').text();
            }},
        ];
        if(self.infotabler != null) {
            self.infotabler.load(data);
        } else {
            self.infotabler = $('#setInfoTable').mmGrid({
                    multiSelect: true,// 多选
                    checkCol: true, // 选框列
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
    addInfo: function () {
        var self = this,
            selected = self.tabler.selectedRows(),
            xh = parent.$('#xh').val(),
            contentlink = parent.$('#contentlink').val(),
            displaydirection = parent.$('#displaydirection').val(),
            imgpath = parent.$('#magecenterId').val(),
            anid = selected[0].animateid;

        if(xh.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "序号不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(contentlink.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "图片内容链接路径不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(displaydirection.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "图片切换显示不能为空！",
                okShow: true,
                cancelShow: true,
                okText: "确定",
                ok: function () {
                    parent.$("#popup-container").show();
                }
            });
            return;
        }
        if(imgpath.length < 1) {
            parent.$("#popup-container").hide();
            parent.Common.dialog({
                type: "warning",
                text: "请选择图片动画！",
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
        $.ajax({
            type:'POST',
            url:'./webapi41105.json',
            data: { 
                'xh': xh,
                'contentlink':contentlink,
                'displaydirection': displaydirection, 
                'imgpath': imgpath ,
                'anid': anid,
                'mxid': '',
                'freeuse1': ''
            },
            datatype:'json',
            success:function(data){
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    parent.Common.loading(false);
                    parent.Common.dialog({
                        type: "success",
                        text: '成功',
                        okShow: true,
                        cancelShow: false,
                        okText: "确定",
                        ok: function () {
                            self.getTableData(1,self.pageSize,true);
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
                        ok: function () {}
                    });
                }
            }, 
            error:function(){
               parent.Common.ajaxError();
            }
                        
        });
    },
    upImg: function(){
        var self = this,
            selected = self.tabler.selectedRows(),
            centerid = selected[0].centerid,
            magecenterId = parent.$('#file').val();
        $.ajax({
            type:'POST',
            url:'./webapi41109.json',
            data: { 
                'magecenterId': centerid,
                'file':magecenterId
            },
            datatype:'json',
            success:function(data){
                console.log(data)
                if(typeof data == "string") {
                    data = JSON.parse(data);
                }
                if (data.recode == "000000") {
                    // parent.Common.dialog({
                    //     type: "success",
                    //     text: '成功',
                    //     okShow: true,
                    //     cancelShow: false,
                    //     okText: "确定",
                    //     ok: function () {
                    //         self.getTableData(1,self.pageSize,true);
                    //     }
                    // });
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
};

$(function(){
    $.ajax({
        type:'POST',
        url:'./page41101GetPara.json',
        data:{'centerid':user.centerid},
        datatype:'json',
        success:function(data){
            if(typeof data == 'string'){
                data = JSON.parse(data);
            }
            if(data.recode == '000000'){
                // 客户名称
                var customerOptions = '';
                for(var i = 0;i<data.mi001list.length;i++){
                    customerOptions += '<option value="'+data.mi001list[i].centerid+'">'+data.mi001list[i].centername+'</option>';
                    
                }
                $('#customerName,#centerid,#centerid1').html(customerOptions);

                // 设备区分
                var devicetype = '<option value="">请选择</option>';
                for(var i = 0;i<data.devicetypeList.length;i++){
                    devicetype += '<option value="'+data.devicetypeList[i].itemid+'">'+data.devicetypeList[i].itemval+'</option>';
                    
                }
                $('#sdevid,#devid,#devid1').html(devicetype);

                // 循环方式
                var looptype = '<option value="">请选择</option>';
                for(var i = 0;i<data.looptypeList.length;i++){
                    looptype += '<option value="'+data.looptypeList[i].itemid+'">'+data.looptypeList[i].itemval+'</option>';
                    
                }
                $('#looptype,#looptype1').html(looptype);

                // 图片切换显示
                var display = '<option value="">请选择</option>';
                for(var i = 0;i<data.displaydirectionList.length;i++){
                    display += '<option value="'+data.displaydirectionList[i].itemid+'">'+data.displaydirectionList[i].itemval+'</option>';
                    
                }
                $('#displaydirection,#displaydirection1').html(display);

                animationSet.createPager();
                animationSet.btnClick();
            }

        },
        error:function(){
            parent.Common.ajaxError();
        }
    });
    

});





